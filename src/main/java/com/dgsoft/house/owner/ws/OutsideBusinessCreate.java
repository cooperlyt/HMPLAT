package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.PersonEntity;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreate;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessDefine;
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

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        DeveloperLogonKey key = houseEntityLoader.getEntityManager().find(DeveloperLogonKey.class, userId);

        JSONObject contractObj;
        try {
            contractObj = new JSONObject(DESUtil.decrypt(contract, key.getSessionKey()));
        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }

        String houseCode;
        ContractOwner contractOwner;
        PoolType poolType;
        SaleInfo saleInfo;
        BusinessPersion businessPersion;
        List<BusinessPool> businessPools = null;
        try {
            houseCode = contractObj.getString("houseCode");

            String legalPerson;
            try {
                legalPerson = contractObj.getString("legalPerson");
            }catch (JSONException e){
                legalPerson = null;
            }

            poolType = PoolType.valueOf(contractObj.getString("poolType"));

            if(!PoolType.SINGLE_OWNER.equals(poolType)) {
                businessPools = new ArrayList<BusinessPool>();
                JSONArray poolArray = contractObj.getJSONArray("pool");


                for (int i = 0; i < poolArray.length(); i++) {
                    JSONObject poolObj = poolArray.getJSONObject(i);
                    String poolLegalPerson;
                    try {
                        poolLegalPerson = poolObj.getString("legalPerson");
                    } catch (JSONException e) {
                        poolLegalPerson = null;
                    }

                    String perc = null;
                    BigDecimal poolArea = null;
                    if (PoolType.SHARE_OWNER.equals(poolType)){
                        try{
                            perc = poolObj.getString("perc");
                        }catch (JSONException e){
                        }
                        try{
                            poolArea = BigDecimal.valueOf(poolObj.getDouble("poolArea"));
                        }catch (JSONException e){
                        }
                    }


                    BusinessPool businessPool = new BusinessPool(poolObj.getString("name"),
                            PersonEntity.CredentialsType.valueOf(poolObj.getString("credentialsType")),
                            poolObj.getString("credentialsNumber"),
                            poolObj.getString("relation"), poolArea,
                            perc, poolObj.getString("tel"), new Date(), poolLegalPerson);
                    businessPools.add(businessPool);
                }

            }


            businessPersion = new BusinessPersion(BusinessPersion.PersionType.PRE_SALE_ENTRUST);
            businessPersion.setPersonName(contractObj.getString("proxyPerson"));
            businessPersion.setCredentialsType(PersonEntity.CredentialsType.valueOf(contractObj.getString("proxyCredentialsType")));
            businessPersion.setCredentialsNumber(contractObj.getString("proxyCredentialsNumber"));
            businessPersion.setPhone(contractObj.getString("proxyTel"));

            saleInfo = new SaleInfo(SalePayType.valueOf(contractObj.getString("salePayType")),BigDecimal.valueOf(contractObj.getDouble("contractPrice")));



            contractOwner = new ContractOwner(contractObj.getString("name"),
                    PersonEntity.CredentialsType.valueOf(contractObj.getString("credentialsType")),
                    contractObj.getString("credentialsNumber"),contractObj.getString("tel"),
                    contractObj.getString("rootAddress"),contractObj.getString("address"),
                    legalPerson,contractObj.getString("id"),
                    SaleType.valueOf(contractObj.getString("type")),new Date(contractObj.getLong("createTime")),houseCode);

            contractOwner.setProjectRshipNumber(contractObj.getString("projectCerNumber"));

            HouseContract houseContract = new HouseContract(contractObj.getString("attachEmpId"),
                    contractObj.getString("attachEmpName"),
                    contractObj.getJSONObject("contract").toString(),contractObj.getInt("contractVersion"));

            contractOwner.setHouseContract(houseContract);
            houseContract.setContractOwner(contractOwner);


            JSONArray numberArray = contractObj.getJSONArray("contractNumber");
            if (numberArray.length() <= 0){
                return DeveloperSaleService.CommitResult.CONTRACT_NUMBER_ERROR.name();
            }

            for(int i = 0; i < numberArray.length(); i++){
                ContractNumber contractNumber = ownerBusinessHome.getEntityManager().find(ContractNumber.class, numberArray.get(i));
                if (contractNumber == null || !ContractNumber.ContractNumberStatus.OUT.equals(contractNumber.getStatus())){

                    return DeveloperSaleService.CommitResult.CONTRACT_NUMBER_ERROR.name();
                }else{
                    contractNumber.setStatus(ContractNumber.ContractNumberStatus.USED);
                    contractNumber.setHouseContract(houseContract);
                    houseContract.getContractNumbers().add(contractNumber);
                }
            }





        } catch (JSONException e) {
            Logging.getLog(getClass()).debug("property not found" , e);
            throw new IllegalArgumentException(e);
        }


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

        businessDefineId = RunParam.instance().getStringParamValue("NewHouseContractBizId");
        businessDefineHome.setId(businessDefineId);

        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);
        contractOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));
        if (businessHouse.getOldOwner() != null){
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setOldOwner(businessHouse.getOldOwner());
        }

        saleInfo.setBusinessHouse(ownerBusinessHome.getSingleHoues().getAfterBusinessHouse());
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getSaleInfos().add(saleInfo);

        businessPersion.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getBusinessPersions().add(businessPersion);

        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(contractOwner);
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setPoolType(poolType);
        ownerBusinessHome.getInstance().getContractOwners().clear();
        ownerBusinessHome.getInstance().getContractOwners().add(contractOwner);
        if (businessPools != null){
            for (BusinessPool bp : businessPools) {
                bp.setOwnerBusiness(ownerBusinessHome.getInstance());
            }
            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().addAll(businessPools);
        }

        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }



        if (businessDefineHome.isSubscribesPass() && businessDefineHome.isCompletePass()) {
            businessDefineHome.completeTask();

            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());

            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
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
    @Transactional
    public void createOldContract(LockedHouse lh,ContractOwner co){





            House house = houseEntityLoader.getEntityManager().createQuery("select h from House h where h.id = :hid",House.class).setParameter("hid",lh.getHouseCode()).getSingleResult();


            businessDefineId = RunParam.instance().getStringParamValue("NewHouseContractBizId");
            businessDefineHome.setId(businessDefineId);



            ownerBusinessHome.clearInstance();
            ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);
            ownerBusinessHome.getInstance().setDefineId(businessDefineId);
            ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName());
            ownerBusinessHome.getInstance().setApplyTime(co.getContractDate());
            ownerBusinessHome.getInstance().setRecorded(false);
            ownerBusinessHome.getInstance().getContractOwners().add(co);
            co.setOwnerBusiness(ownerBusinessHome.getInstance());


            BusinessHouse businessHouse = new BusinessHouse(house);

            ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));

            ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(co);


            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());



            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
            String ownerBusinessId = ownerBusinessHome.getInstance().getId();
            ownerBusinessHome.getEntityManager().remove(lh);

            for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
                component.fillData();
            }

            businessDefineHome.completeTask();

            if (!"persisted".equals(ownerBusinessHome.persist())){
                throw new IllegalArgumentException("persited ownerBusiness fail");
            }
            BusinessProcess.instance().createProcess(businessDefineHome.getInstance().getWfName(),ownerBusinessId);

            Logging.getLog(getClass()).debug("crete business:" + ownerBusinessHome.getInstance().getId() + "by:" + lh.getId());




    }

}
