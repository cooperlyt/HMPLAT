package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessDataValid;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.common.system.model.BusinessCreateDataValid;
import com.dgsoft.house.owner.action.OwnerBuildGridMap;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;


/**
 * Created by cooper on 8/28/14.
 */
@Name("houseBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessStart {

    @In
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private FacesMessages facesMessages;


    private static final String BUSINESS_INFO_PAGE = "/business/houseOwner/BizStartSubscribe.xhtml";
    private static final String BUSINESS_FILE_PAGE = "/business/houseOwner/BizStartFileUpload.xhtml";
    private static final String BUSINESS_PRINT_PAGE = "/business/houseOwner/BizStartConfirm.xhtml";


    @In
    private OwnerBuildGridMap ownerBuildGridMap;

    public void validSelectHouse(){
        for(BusinessCreateDataValid valid: businessDefineHome.getInstance().getBusinessCreateDataValids()){
            BusinessDataValid.ValidResult result = ((BusinessDataValid) Component.getInstance(valid.getValidation(), true)).valid(ownerBuildGridMap.getSelectBizHouse());
            if (result.getResult().equals(TaskSubscribeComponent.ValidResult.FATAL)){
                throw new IllegalArgumentException(result.getMsgKey());
            }
            if (!result.getResult().equals(TaskSubscribeComponent.ValidResult.SUCCESS)){
                facesMessages.addFromResourceBundle(result.getResult().getSeverity(),result.getMsgKey(),result.getParams());
            }
        }
    }

    public String singleHouseSelected() {

        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), ownerBuildGridMap.getSelectBizHouse()));

        return houseIsSelected();
    }

    private void initBusinessData() {

        ownerBusinessHome.getInstance().setDefineId(businessDefineHome.getInstance().getId());
        ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName());

        ownerBusinessHome.getInstance().getBusinessEmps().add(new BusinessEmp(ownerBusinessHome.getInstance(),
                BusinessEmp.EmpType.CREATE_EMP,authInfo.getLoginEmployee().getId(),
                authInfo.getLoginEmployee().getPersonName()));
        ownerBusinessHome.getInstance().setId(businessDefineHome.getInstance().getId() + "-" + OwnerNumberBuilder.instance().useDayNumber("businessId"));
        Logging.getLog(getClass()).debug("businessID:" + ownerBusinessHome.getInstance().getId());
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(OwnerNumberBuilder.instance().useNumber("createBusinessId") * -1 ,ownerBusinessHome.getInstance(), authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName()));

    }

    private String houseIsSelected(){
        initBusinessData();


        if (businessDefineHome.getEditSubscribeDefines().isEmpty()){
            return BUSINESS_INFO_PAGE;
        } else{
            if (RunParam.instance().getBooleanParamValue("BusinessPrintFirst")){
                return  BUSINESS_PRINT_PAGE;
            }else if (businessDefineHome.haveNeedFile(null)) {
                return BUSINESS_FILE_PAGE;
            } else {
                return BUSINESS_PRINT_PAGE;
            }
        }

    }


    public String mulitHouseSelect() {

        return houseIsSelected();
    }

    @In
    private AuthenticationInfo authInfo;




}
