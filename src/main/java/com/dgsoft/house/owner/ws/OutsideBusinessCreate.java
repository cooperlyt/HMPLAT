package com.dgsoft.house.owner.ws;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessCreate;
import com.dgsoft.common.system.business.BusinessDataFill;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.model.BusinessDefine;
import com.dgsoft.developersale.DeveloperSaleService;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.PoolType;
import com.dgsoft.house.model.House;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.log.Logging;
import org.jbpm.graph.def.ProcessDefinition;

import java.util.Date;
import java.util.List;

/**
 * Created by cooper on 9/26/15.
 */
@Name("outsideBusinessCreate")
public class OutsideBusinessCreate extends BusinessCreate {

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @Override
    protected boolean verifyCreate() {
        boolean verify;
        if (businessDefineHome.isSubscribesPass() && businessDefineHome.isCompletePass()){
            businessDefineHome.completeTask();

//            ownerBusinessHome.getInstance().getBusinessEmps().add(new BusinessEmp(ownerBusinessHome.getInstance(),
//                    BusinessEmp.EmpType.CREATE_EMP, authInfo.getLoginEmployee().getId(),
//                    authInfo.getLoginEmployee().getPersonName(), new Date()));
//            ownerBusinessHome.getInstance().getTaskOpers().add(
//                    new TaskOper(ownerBusinessHome.getInstance(), TaskOper.OperType.CREATE,
//                            RunParam.instance().getStringParamValue("CreateBizTaskName"),
//                            authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), businessDefineHome.getDescription()));
//

            ProcessDefinition definition = ManagedJbpmContext.instance().getGraphSession().findLatestProcessDefinition(businessDefineHome.getInstance().getWfName());

            ownerBusinessHome.getInstance().setDefineVersion(definition == null ? null : definition.getVersion());
            String result = ownerBusinessHome.persist();
            verify = ((result != null) && result.equals("persisted") && (businessDefineHome.getInstance().getWfName() != null) &&
                    !businessDefineHome.getInstance().getWfName().trim().equals(""));

        }else{
            verify = false;
        }

        if (!verify){
            throw new IllegalArgumentException("create business fail");
        }

        return verify;
    }

    public DeveloperSaleService.CommitResult createNewHouseContrict(String houseCode, ContractOwner contractOwner,PoolType poolType, List<BusinessPool> businessPool){
        businessDefineHome.setId(RunParam.instance().getStringParamValue("NewHouseContractBizId"));
        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setSource(BusinessInstance.BusinessSource.BIZ_OUTSIDE);
        HouseRecord houseRecord = ownerBusinessHome.getEntityManager().find(HouseRecord.class,houseCode);
        BusinessHouse businessHouse;
        if (houseRecord == null){
            House house = houseEntityLoader.getEntityManager().find(House.class,houseCode);
            if (house == null){
                return DeveloperSaleService.CommitResult.HOUSE_ERROR;
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
                return DeveloperSaleService.CommitResult.HOUSE_ERROR;
            }
        }

        contractOwner.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setContractOwner(contractOwner);
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().setPoolType(poolType);

        for(BusinessPool bp: businessPool){
            bp.setOwnerBusiness(ownerBusinessHome.getInstance());
        }
        ownerBusinessHome.getSingleHoues().getAfterBusinessHouse().getBusinessPools().addAll(businessPool);
        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }
        if ("businessCreated".equals(super.createBusiness())){
            return DeveloperSaleService.CommitResult.COMMIT_OK;
        }else{
            return DeveloperSaleService.CommitResult.ERROR;
        }

    }

    @Override
    protected String getBusinessKey() {
        return ownerBusinessHome.getInstance().getId();
    }

    @Override
    protected BusinessDefine getBusinessDefine() {
        return businessDefineHome.getInstance();
    }
}
