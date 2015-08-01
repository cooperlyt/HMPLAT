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
import org.jboss.seam.international.StatusMessage;
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
            BusinessDataValid.ValidResult result;
            try {
                result = valid.valid(ownerBuildGridMap.getSelectBizHouse());
            }catch (Exception e){
                Logging.getLog(getClass()).error(e,"config error:" + valid.getClass().getSimpleName());
                throw new IllegalArgumentException("config error:" + valid.getClass().getSimpleName());
            }
            if (result.getResult().equals(BusinessDataValid.ValidResultLevel.FATAL)){
                throw new IllegalArgumentException(result.getMsgKey());
            }
            if (!result.getResult().equals(BusinessDataValid.ValidResultLevel.SUCCESS)){
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
        if ((businessDefineHome.getInstance().getPickBusinessDefineId() != null) &&  !businessDefineHome.getInstance().getPickBusinessDefineId().trim().equals("") ){
            allowSelectBizs.addAll(ownerBusinessHome.getEntityManager().createQuery("select distinct houseBusiness.ownerBusiness from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status = 'COMPLETE' and houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.defineId =:defineId",OwnerBusiness.class)
                    .setParameter("houseCode", ownerBuildGridMap.getSelectBizHouse().getHouseCode())
                    .setParameter("defineId", businessDefineHome.getInstance().getPickBusinessDefineId().trim()).getResultList());
        }
        if (allowSelectBizs.isEmpty()) {
            ownerBusinessHome.getInstance().getHouseBusinesses().clear();
            ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), ownerBuildGridMap.getSelectBizHouse()));
        } else if (allowSelectBizs.size() == 1){
            ownerBusinessHome.getInstance().setSelectBusiness(allowSelectBizs.get(0));
            return businessSelected();
        }else{
            return BUSINESS_PICK_BIZ_PAGE;
        }
        return dataSelected();
    }

    public String businessSelected(){
        ownerBusinessHome.getInstance().setSelectBusiness(selectedBusiness);
        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        if (selectedBusiness.getHouseBusinesses().isEmpty()){
            throw new IllegalArgumentException("config exception not hove house");
        }
        for (HouseBusiness houseBusiness: selectedBusiness.getHouseBusinesses()){
            ownerBusinessHome.getInstance().getHouseBusinesses().add(
                    new HouseBusiness(ownerBusinessHome.getInstance(),
                            ownerBusinessHome.getEntityManager().find(HouseRecord.class,houseBusiness.getHouseCode()).getBusinessHouse()));
        }

        return dataSelected();
    }

    private String dataSelected(){
        for(BusinessDataFill component: businessDefineHome.getCreateDataFillComponents()){
            component.fillData();
        }


        return beginEdit();

    }

    public String beginEdit(){
        if (businessDefineHome.isHaveEditSubscribe()){
            businessDefineHome.firstEditGroup();
            return BUSINESS_INFO_PAGE;
        } else{
            return getInfoCompletePath();
        }
    }

    private String getInfoCompletePath(){

        if (RunParam.instance().getBooleanParamValue("CreateUploadFile") || businessDefineHome.isHaveNeedFile()){
            return  BUSINESS_FILE_PAGE;
        }else {
            return BUSINESS_CONFIRM_PAGE;
        }
    }

    public String infoComplete(){

        if (businessDefineHome.isHaveNextEditGroup()){
            businessDefineHome.nextEditGroup();
            return BUSINESS_INFO_PAGE;
        }else{
            if (businessDefineHome.saveEditSubscribes()){
                return getInfoCompletePath();
            }else{
                return BUSINESS_INFO_PAGE;
            }

        }

    }


    public String mulitHouseSelect() {

        return dataSelected();
    }


    @In(required = false)
    private OwnerBusinessFile ownerBusinessFile;

    public boolean isNeedFilePass(){
        if (businessDefineHome.isHaveNeedFile()){
            return (ownerBusinessFile != null) && ownerBusinessFile.isPass();
        }
        return true;
    }



}
