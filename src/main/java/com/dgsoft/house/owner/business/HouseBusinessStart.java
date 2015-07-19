package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.*;
import com.dgsoft.house.owner.action.OwnerBuildGridMap;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerNumberBuilder;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.List;


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
    private static final String BUSINESS_CONFIRM_PAGE = "/business/houseOwner/BizStartConfirm.xhtml";
    private static final String BUSINESS_PICK_BIZ_PAGE = "";


    @In
    private OwnerBuildGridMap ownerBuildGridMap;

    public void validSelectHouse(){
        for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){
            BusinessDataValid.ValidResult result = valid.valid(ownerBuildGridMap.getSelectBizHouse());
            if (result.getResult().equals(TaskSubscribeComponent.ValidResult.FATAL)){
                throw new IllegalArgumentException(result.getMsgKey());
            }
            if (!result.getResult().equals(TaskSubscribeComponent.ValidResult.SUCCESS)){
                facesMessages.addFromResourceBundle(result.getResult().getSeverity(),result.getMsgKey(),result.getParams());
            }
        }
    }


    @DataModel
    private List<OwnerBusiness> allowSelectBizs;

    @DataModelSelection
    private OwnerBusiness selectedBusiness;

    public String singleHouseSelected() {

        allowSelectBizs = new ArrayList<OwnerBusiness>();
        for(BusinessPickSelect component: businessDefineHome.getCreateBizSelectComponents()){
            for(BusinessInstance bizInstance: component.getAllowSelectBusiness(ownerBuildGridMap.getSelectBizHouse()))
                allowSelectBizs.add((OwnerBusiness)bizInstance);
        }
        if (allowSelectBizs.isEmpty()) {
            ownerBusinessHome.getInstance().getHouseBusinesses().clear();
            ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), ownerBuildGridMap.getSelectBizHouse()));
        } else if (allowSelectBizs.size() == 1){
            ownerBusinessHome.getInstance().setSelectBusiness(allowSelectBizs.get(0));
        }else{
            return BUSINESS_PICK_BIZ_PAGE;
        }
        return dataSelected();
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

    public String businessSelected(){
        ownerBusinessHome.getInstance().setSelectBusiness(selectedBusiness);
        return dataSelected();
    }

    private String dataSelected(){
        initBusinessData();
        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }


        if (!businessDefineHome.getEditSubscribeDefines().isEmpty()){
            businessDefineHome.initEditSubscribes();
            return BUSINESS_INFO_PAGE;
        } else{
            return getInfoCompletePath();
        }

    }

    private String getInfoCompletePath(){

        if (RunParam.instance().getBooleanParamValue("CreateUploadFile") || businessDefineHome.haveNeedFile(Subscribe.CREATE_TASK_NAME)){
            return  BUSINESS_FILE_PAGE;
        }else {
            return BUSINESS_CONFIRM_PAGE;
        }
    }

    public String infoComplete(){
        TaskSubscribeComponent.ValidResult result = businessDefineHome.validSubscribes();

        if (result.getPri() > TaskSubscribeComponent.ValidResult.WARN.getPri()){
            return null;
        }


        if (businessDefineHome.saveSubscribes()){
            return getInfoCompletePath();
        }else{
            return null;
        }

    }



    public String mulitHouseSelect() {

        return dataSelected();
    }

    @In
    private AuthenticationInfo authInfo;




}
