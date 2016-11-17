package com.dgsoft.house.owner.ws;

import cc.coopersoft.house.sale.data.*;
import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.developersale.DeveloperSaleService;
import com.dgsoft.developersale.wsinterface.DESUtil;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.SalePayType;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import com.dgsoft.house.owner.model.ContractNumber;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.PowerPerson;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cc.coopersoft.house.sale.data.PowerPerson.ContractPersonType.BUYER;

/**
 * Created by cooper on 9/26/15.
 */
@Name("outsideBusinessCreate")
public class OutsideBusinessCreate {

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Out(scope = ScopeType.BUSINESS_PROCESS)
    private String businessDefineId;

    @In
    private Events events;


    @Transactional
    public String submitContract(String contract, String userId){

        businessDefineId = RunParam.instance().getStringParamValue("NewHouseContractBizId");

        DeveloperLogonKey key = houseEntityLoader.getEntityManager().find(DeveloperLogonKey.class, userId);

        ObjectMapper mapper = new ObjectMapper();
        cc.coopersoft.house.sale.data.HouseContract outsideContract;
        try {
            outsideContract = mapper.readValue(DESUtil.decrypt(contract,key.getSessionKey()), cc.coopersoft.house.sale.data.HouseContract.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("contract data error",e);
        } catch (Exception e) {
            throw new IllegalArgumentException("contract decrypt error",e);
        }

        HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class,outsideContract.getHouseCode());
        BusinessHouse businessHouse;
        if (houseRecord == null){
            House house = houseEntityLoader.getEntityManager().find(House.class,outsideContract.getHouseCode());
            if (house == null){
                throw new IllegalArgumentException("house not found:" + outsideContract.getHouseCode());
            }
            businessHouse = new BusinessHouse(house);
        }else {
            businessHouse = houseRecord.getBusinessHouse();
        }



        businessDefineHome.setId(businessDefineId);

        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);

        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));


        for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){
            BusinessDataValid.ValidResult result;
            try {
                result = valid.valid(businessHouse);
            }catch (Exception e){
                Logging.getLog(getClass()).error(e.getMessage(),e,"config error:" + valid.getClass().getSimpleName());
                throw new IllegalArgumentException("config error:" + valid.getClass().getSimpleName());
            }
            if (result.getResult().equals(BusinessDataValid.ValidResultLevel.FATAL)){
                throw new IllegalArgumentException(result.getMsgKey());
            }

            if (Integer.valueOf(result.getResult().getPri()).compareTo(BusinessDataValid.ValidResultLevel.WARN.getPri()) > 0){
                throw new IllegalArgumentException("valid error:" + valid.getClass().getSimpleName());
            }
        }


        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }

        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setSaleInfo(new SaleInfo(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse(),outsideContract.getSalePayType(),outsideContract.getPrice(),ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseArea()));

        BusinessPersion businessPersion = new BusinessPersion(ownerBusinessHome.getInstance(),BusinessPersion.PersionType.PRE_SALE_ENTRUST);
        businessPersion.setPersonName(outsideContract.getSaleProxyPerson().getPersonName());
        businessPersion.setCredentialsType(outsideContract.getSaleProxyPerson().getCredentialsType());
        businessPersion.setCredentialsNumber(outsideContract.getSaleProxyPerson().getCredentialsNumber());
        businessPersion.setPhone(outsideContract.getSaleProxyPerson().getTel());
        ownerBusinessHome.getInstance().getBusinessPersions().add(businessPersion);

        ownerBusinessHome.getInstance().getBusinessPersions().add(businessPersion);

        ContractSubmit contractSubmit = new ContractSubmit(outsideContract.getAttachEmpId(),outsideContract.getAttachEmpName(),outsideContract.getContext(),outsideContract.getContractVersion());
        HouseContract houseContract = new HouseContract(outsideContract.getId(),
                outsideContract.getType(),
                outsideContract.getCreateTime(),
                outsideContract.getNewHouseContract().getProjectCerNumber(),
                ownerBusinessHome.getSingleHoues(),contractSubmit);
        contractSubmit.setHouseContract(houseContract);

        for(cc.coopersoft.house.sale.data.ContractNumber cn : outsideContract.getContractNumbers()){
            ContractNumber contractNumber = ownerBusinessHome.getEntityManager().find(ContractNumber.class, cn.getContractNumber());
            if (contractNumber == null || !ContractNumber.ContractNumberStatus.OUT.equals(contractNumber.getStatus())){

                return DeveloperSaleService.CommitResult.CONTRACT_NUMBER_ERROR.name();
            }else{
                contractNumber.setStatus(ContractNumber.ContractNumberStatus.USED);
                contractNumber.setContractSubmit(contractSubmit);
                contractSubmit.getContractNumbers().add(contractNumber);
            }
        }

        ownerBusinessHome.getSingleHoues().setHouseContract(houseContract);
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setPoolType(outsideContract.getPoolType());


        int i = 0;
        for(cc.coopersoft.house.sale.data.PowerPerson pp: outsideContract.getBusinessPoolList()){
            if (BUYER.equals( pp.getContractPersonType())) {
                PowerPerson contractOwner = new PowerPerson(PowerPerson.PowerPersonType.CONTRACT, false);
                ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getPowerPersons().add(contractOwner);
                if (i == 0) {
                    ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setMainOwner(contractOwner);
                }

                contractOwner.setPersonName(pp.getPersonName());
                contractOwner.setCredentialsType(pp.getCredentialsType());
                contractOwner.setCredentialsNumber(pp.getCredentialsNumber());
                contractOwner.setRelation(pp.getRelation());
                contractOwner.setPoolArea(pp.getPoolArea());
                contractOwner.setPoolPerc(pp.getPoolPerc());
                contractOwner.setLegalPerson(pp.getLegalPerson());
                contractOwner.setLegalType(pp.getLegalType());
                contractOwner.setPhone(pp.getPhone());
                contractOwner.setRootAddress(pp.getRootAddress());
                contractOwner.setAddress(pp.getAddress());
                contractOwner.setBirthday(pp.getBirthday());
                contractOwner.setSex(pp.getSex());
                contractOwner.setPriority(i++);
                if (pp.getPowerProxyPerson() != null) {
                    contractOwner.setProxyPerson(new ProxyPerson());
                    contractOwner.getProxyPerson().setProxyType(pp.getPowerProxyPerson().getProxyType());
                    contractOwner.getProxyPerson().setPersonName(pp.getPowerProxyPerson().getPersonName());
                    contractOwner.getProxyPerson().setCredentialsType(pp.getPowerProxyPerson().getCredentialsType());
                    contractOwner.getProxyPerson().setCredentialsNumber(pp.getPowerProxyPerson().getCredentialsNumber());
                    contractOwner.getProxyPerson().setPhone(pp.getPowerProxyPerson().getPhone());
                }
            }
        }


        if (businessDefineHome.isSubscribesPass() && businessDefineHome.isCompletePass()) {
            businessDefineHome.completeTask();

            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());

            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(), TaskOper.OperType.CREATE,"提交合同","outside",key.getAttachEmployee().getPersonName(),"",new Date()));
            String ownerBusinessId = ownerBusinessHome.getInstance().getId();
            if (!"persisted".equals(ownerBusinessHome.persist())){
                throw new IllegalArgumentException("persited ownerBusiness fail");
            }

            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(),ownerBusinessId);

            events.raiseTransactionSuccessEvent("com.dgsoft.BusinessCreated." + businessDefineHome.getInstance().getId());


            return DeveloperSaleService.CommitResult.COMMIT_OK.name();
        }

        throw new IllegalArgumentException("fail");
    }



    public static OutsideBusinessCreate instance()
    {
        return (OutsideBusinessCreate) Component.getInstance(OutsideBusinessCreate.class,true);
    }


    //建立旧的网签业务
//    @Transactional
//    public void createOldContract(LockedHouse lh,ContractOwner co){
//
//            House house = houseEntityLoader.getEntityManager().createQuery("select h from House h where h.id = :hid",House.class).setParameter("hid",lh.getHouseCode()).getSingleResult();
//
//            businessDefineId = RunParam.instance().getStringParamValue("NewHouseContractBizId");
//            businessDefineHome.setId(businessDefineId);
//
//
//
//            ownerBusinessHome.clearInstance();
//            ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);
//            ownerBusinessHome.getInstance().setDefineId(businessDefineId);
//            ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName());
//            ownerBusinessHome.getInstance().setApplyTime(co.getContractDate());
//            ownerBusinessHome.getInstance().setRecorded(false);
//            ownerBusinessHome.getInstance().getContractOwners().add(co);
//            co.setOwnerBusiness(ownerBusinessHome.getInstance());
//
//
//            BusinessHouse businessHouse = new BusinessHouse(house);
//
//            ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));
//
//            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(co);
//
//
//            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());
//
//
//
//            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
//            String ownerBusinessId = ownerBusinessHome.getInstance().getId();
//            ownerBusinessHome.getEntityManager().remove(lh);
//
//            for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
//                component.fillData();
//            }
//
//            businessDefineHome.completeTask();
//
//            if (!"persisted".equals(ownerBusinessHome.persist())){
//                throw new IllegalArgumentException("persited ownerBusiness fail");
//            }
//            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(),ownerBusinessId);
//
//            Logging.getLog(getClass()).debug("crete business:" + ownerBusinessHome.getInstance().getId() + "by:" + lh.getId());
//
//
//
//
//    }

}
