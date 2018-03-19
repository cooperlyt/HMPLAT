package com.dgsoft.house.owner.ws;

import cc.coopersoft.house.SubmitType;
import cc.coopersoft.house.sale.data.HouseSource;
import cc.coopersoft.house.sale.data.SubmitResult;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.developersale.wsinterface.DESUtil;
import com.dgsoft.house.DescriptionDisplay;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.model.Agencies;
import com.dgsoft.house.model.AttachEmployee;
import com.dgsoft.house.model.DeveloperLogonKey;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import com.dgsoft.house.owner.model.ContractNumber;
import com.dgsoft.house.owner.model.HouseContract;
import com.dgsoft.house.owner.model.PowerPerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Events;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;

import java.io.IOException;
import java.util.Date;

import static cc.coopersoft.house.sale.data.PowerPerson.ContractPersonType.SELLER;

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

    private String createBusiness(String empId,String empName,String taskName){
        if (businessDefineHome.isCompletePass()) {
            businessDefineHome.completeTask();

            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());

            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(), TaskOper.OperType.CREATE,taskName,empId,empName,"",new Date()));
            String ownerBusinessId = ownerBusinessHome.getInstance().getId();
            if (!"persisted".equals(ownerBusinessHome.persist())){
                throw new IllegalArgumentException("persited ownerBusiness fail");
            }

            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(),ownerBusinessId);

            events.raiseTransactionSuccessEvent("com.dgsoft.BusinessCreated." + businessDefineHome.getInstance().getId());


            return ownerBusinessId;
        }
        return null;
    }



    private void genHouseBusiness(String defineId,String houseCode){
        businessDefineId = defineId;
        HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class,houseCode);
        BusinessHouse businessHouse;
        if (houseRecord == null){
            House house = houseEntityLoader.getEntityManager().find(House.class,houseCode);
            if (house == null){
                throw new IllegalArgumentException("house not found:" + houseCode);
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


    }

    private String submitContract(AttachEmployee attachEmployee ,cc.coopersoft.house.sale.data.HouseContract outsideContract, String defineId){

        genHouseBusiness(defineId,outsideContract.getHouseCode());


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
                SaleType.OLD_SELL.equals(outsideContract.getType()) ? null : outsideContract.getNewHouseContract().getProjectCerNumber(),
                ownerBusinessHome.getSingleHoues(),contractSubmit);
        houseContract.setPayType(outsideContract.getSalePayType());
        houseContract.setSaleArea(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getHouseArea());
        houseContract.setSumPrice(outsideContract.getPrice());
        contractSubmit.setHouseContract(houseContract);

        for(cc.coopersoft.house.sale.data.ContractNumber cn : outsideContract.getContractNumbers()){
            ContractNumber contractNumber = ownerBusinessHome.getEntityManager().find(ContractNumber.class, cn.getContractNumber());
            if (contractNumber == null || !ContractNumber.ContractNumberStatus.OUT.equals(contractNumber.getStatus())){

                throw new IllegalArgumentException("commit contract number error");
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
                                                            // 备案和交易业务合并，所以不是备案人而是产权人
                PowerPerson contractOwner = new PowerPerson(PowerPerson.PowerPersonType.OWNER, SELLER.equals( pp.getContractPersonType()));
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
                    contractOwner.setPowerProxyPerson(new ProxyPerson());
                    contractOwner.getProxyPerson().setProxyType(pp.getPowerProxyPerson().getProxyType());
                    contractOwner.getProxyPerson().setPersonName(pp.getPowerProxyPerson().getPersonName());
                    contractOwner.getProxyPerson().setCredentialsType(pp.getPowerProxyPerson().getCredentialsType());
                    contractOwner.getProxyPerson().setCredentialsNumber(pp.getPowerProxyPerson().getCredentialsNumber());
                    contractOwner.getProxyPerson().setPhone(pp.getPowerProxyPerson().getPhone());
                }

        }

        String businessId = createBusiness(attachEmployee.getId(),
                attachEmployee.getPersonName(),"提交合同");

        if (businessId != null){
            return businessId;
        }else
            throw new IllegalArgumentException("fail");
    }

    @Deprecated
    @Transactional
    public String submitContract(String contract, String userId){


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

        return submitContract(key.getAttachEmployee(),outsideContract,RunParam.instance().getStringParamValue("NewHouseContractBizId"));

    }


    @Transactional
    public SubmitResult submitBusiness(SubmitType type,String data,String attrId){
        DeveloperLogonKey key = houseEntityLoader.getEntityManager().find(DeveloperLogonKey.class, attrId);
        try {
            String jsonData = DESUtil.decrypt(data,key.getSessionKey());
            Logging.getLog(getClass()).debug("data:" + jsonData);
            switch (type){
                case SALE_CONTRACT:
                    return submitSellContract(key.getAttachEmployee(), jsonData);
                case MORTGAGE_CONTRACT:
                    break;
                case HOUSE_SOURCE:
                    return submitHouseSource(key.getAttachEmployee(), jsonData);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(),e);
        }
        throw new IllegalArgumentException("unknow type:" + type);
    }

    private SubmitResult submitSellContract(AttachEmployee attachEmployee , String data) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        cc.coopersoft.house.sale.data.HouseContract outsideContract = mapper.readValue(data,cc.coopersoft.house.sale.data.HouseContract.class);

        String defineIdParamName = SaleType.OLD_SELL.equals(outsideContract.getType()) ? "OldHouseContractBizId" : "NewHouseContractBizId" ;

        String businessId = submitContract(attachEmployee,outsideContract,RunParam.instance().getStringParamValue(defineIdParamName));


        if (businessId != null){
            SubmitResult result = new SubmitResult(SubmitResult.SubmitStatus.SUCCESS,"合同已提交，业务编号：" + businessId);
            result.setBusinessId(businessId);
            return result;
        }else{
            return new SubmitResult(SubmitResult.SubmitStatus.FAIL,"合同提交失败！");
        }
    }

    private SubmitResult submitMortgageContract(AttachEmployee attachEmployee , String data){

        //TODO mortgage contract
        return null;
    }

    private SubmitResult submitHouseSource(AttachEmployee attachEmployee , String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        HouseSource houseSource = mapper.readValue(data,HouseSource.class);

        //TODO check data
        businessDefineId = RunParam.instance().getStringParamValue("HouseSourceBizId");
        HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class,houseSource.getHouseCode());
        if (houseRecord == null){
            throw new IllegalArgumentException("houseSource not have HouseRecord!");
        }
        businessDefineHome.setId(businessDefineId);
        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);

        Agencies agencies = houseEntityLoader.getEntityManager().find(Agencies.class,houseSource.getGroupId());

        HouseSourceBusiness houseSourceBusiness = new HouseSourceBusiness(ownerBusinessHome.getInstance(),data,houseSource.getSourceId(),false,houseSource.getGroupId(),agencies.getName());

        houseSourceBusiness.setHouse(houseRecord.getBusinessHouse());

        String searchKey = houseRecord.getSearchKey();
        searchKey += "[" + houseSource.getGroupId() + "][" + agencies.getName() + "]";

        houseSourceBusiness.setSearchKey(searchKey);

        DescriptionDisplay display =  DescriptionDisplay.instance(houseRecord.getDisplay());

        display.newLine(DescriptionDisplay.DisplayStyle.NORMAL);
        display.addData(DescriptionDisplay.DisplayStyle.LABEL, "提交机构");
        display.addData(DescriptionDisplay.DisplayStyle.PARAGRAPH,agencies.getName());
        houseSourceBusiness.setDisplay(DescriptionDisplay.toStringValue(display));

        ownerBusinessHome.getInstance().getHouseSourceBusinesses().clear();
        ownerBusinessHome.getInstance().getHouseSourceBusinesses().add(houseSourceBusiness);




        String businessId = createBusiness(attachEmployee.getId(),attachEmployee.getPersonName(),"提交房源");

        if (businessId != null){
            SubmitResult result = new SubmitResult(SubmitResult.SubmitStatus.SUCCESS,"房源审核中，审核业务编号：" + businessId);
            result.setBusinessId(businessId);
            return result;
        }else{
            return new SubmitResult(SubmitResult.SubmitStatus.FAIL,"房源验证失败！");
        }

    }


    public static OutsideBusinessCreate instance()
    {
        return (OutsideBusinessCreate) Component.getInstance(OutsideBusinessCreate.class,true);
    }


}
