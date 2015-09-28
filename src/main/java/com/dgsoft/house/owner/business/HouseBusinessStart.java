package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.*;
import com.dgsoft.house.owner.action.OwnerBuildGridMap;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
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

import java.util.*;


/**
 * Created by cooper on 8/28/14.
 */
@Name("houseBusinessStart")
@Scope(ScopeType.CONVERSATION)
public class HouseBusinessStart {


    private static final String BUSINESS_PICK_BIZ_PAGE = "/business/houseOwner/HouseBusinessSelect.xhtml";

    @In
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private FacesMessages facesMessages;

    @In
    private OwnerBuildGridMap ownerBuildGridMap;

    @In(create = true)
    private OwnerBusinessStart ownerBusinessStart;

    public void validSelectHouse(){

        validSelectHouseResult();
    }

    private boolean validSelectHouseResult(){
        boolean success = true;
        for(BusinessDataValid valid: businessDefineHome.getCreateDataValidComponents()){
            BusinessDataValid.ValidResult result;
            try {
                result = valid.valid(ownerBuildGridMap.getSelectBizHouse());
            }catch (Exception e){
                Logging.getLog(getClass()).error(e.getMessage(),e,"config error:" + valid.getClass().getSimpleName());
                throw new IllegalArgumentException("config error:" + valid.getClass().getSimpleName());
            }
            if (result.getResult().equals(BusinessDataValid.ValidResultLevel.FATAL)){
                throw new IllegalArgumentException(result.getMsgKey());
            }
            if (!result.getResult().equals(BusinessDataValid.ValidResultLevel.SUCCESS)){
                facesMessages.addFromResourceBundle(result.getResult().getSeverity(),result.getMsgKey(),result.getParams());
            }

            if (success && Integer.valueOf(result.getResult().getPri()).compareTo(BusinessDataValid.ValidResultLevel.WARN.getPri()) > 0){
                success = false;
            }
        }
        return success;
    }


    //@DataModel("houseStartAllowBusiness")
    private List<OwnerBusiness> allowSelectBizs;

    private String selectBizId;

    public String getSelectBizId() {
        return selectBizId;
    }

    public void setSelectBizId(String selectBizId) {
        this.selectBizId = selectBizId;
    }

    public List<OwnerBusiness> getAllowSelectBizs() {
        return allowSelectBizs;
    }

    public void setAllowSelectBizs(List<OwnerBusiness> allowSelectBizs) {
        this.allowSelectBizs = allowSelectBizs;
    }

    //    @DataModelSelection
//    private OwnerBusiness selectedBusiness;

    public String singleHouseSelected() {

        allowSelectBizs = new ArrayList<OwnerBusiness>();
        for(BusinessPickSelect component: businessDefineHome.getCreateBizSelectComponents()){
            for(BusinessInstance bizInstance: component.getAllowSelectBusiness(ownerBuildGridMap.getSelectBizHouse()))
                allowSelectBizs.add((OwnerBusiness)bizInstance);
        }
        if ((businessDefineHome.getInstance().getPickBusinessDefineId() != null) &&  !businessDefineHome.getInstance().getPickBusinessDefineId().trim().equals("") ){

            allowSelectBizs.addAll(ownerBusinessHome.getEntityManager().createQuery("select distinct houseBusiness.ownerBusiness from HouseBusiness houseBusiness where houseBusiness.ownerBusiness.status = 'COMPLETE' and houseBusiness.houseCode =:houseCode and houseBusiness.ownerBusiness.defineId in (:defineIds)", OwnerBusiness.class)
                    .setParameter("houseCode", ownerBuildGridMap.getSelectBizHouse().getHouseCode())
                    .setParameter("defineIds", Arrays.asList(businessDefineHome.getInstance().getPickBusinessDefineId().split(","))).getResultList());
        }

        if (businessDefineHome.getInstance().isRequiredBiz() && allowSelectBizs.isEmpty()){

            facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"SelectBusinessIsRequired");
            return null;
        }

        ownerBusinessHome.getInstance().getHouseBusinesses().clear();
        ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), ownerBuildGridMap.getSelectBizHouse()));

        if (allowSelectBizs.isEmpty()){
            return ownerBusinessStart.dataSelected();
        }else{
            Collections.sort(allowSelectBizs, new Comparator<OwnerBusiness>() {
                @Override
                public int compare(OwnerBusiness o1, OwnerBusiness o2) {
                    return o2.getApplyTime().compareTo(o1.getApplyTime());
                }
            });
            return BUSINESS_PICK_BIZ_PAGE;
        }

    }

    public String businessSelected(){
        for(OwnerBusiness ob: allowSelectBizs){
            if (ob.getId().equals(selectBizId)){
                ownerBusinessHome.getInstance().setSelectBusiness(ob);
                return ownerBusinessStart.dataSelected();
            }
        }
        throw new IllegalArgumentException("business id not found");
    }


    private boolean lastMulitAddOK;

    public boolean isLastMulitAddOK() {
        return lastMulitAddOK;
    }

    public void setLastMulitAddOK(boolean lastMulitAddOK) {
        this.lastMulitAddOK = lastMulitAddOK;
    }

    public void fastAddToMulit(){
        if (ownerBuildGridMap.getSelectBizHouses().contains(ownerBuildGridMap.getSelectBizHouse())){
            ownerBuildGridMap.getSelectBizHouses().remove(ownerBuildGridMap.getSelectBizHouse());
            lastMulitAddOK = true;
        }else {

            lastMulitAddOK = validSelectHouseResult();
            if (lastMulitAddOK) {
                ownerBuildGridMap.getSelectBizHouses().add(ownerBuildGridMap.getSelectBizHouse());
            }
        }
        Logging.getLog(getClass()).debug("fast add house result:" + lastMulitAddOK);
    }


    public String mulitHouseSelect() {
        ownerBusinessHome.getInstance().getHouseBusinesses().clear();

        for(BusinessHouse businessHouse: ownerBuildGridMap.getSelectBizHouses())
            ownerBusinessHome.getInstance().getHouseBusinesses().add(new HouseBusiness(ownerBusinessHome.getInstance(), businessHouse));


        return ownerBusinessStart.dataSelected();
    }






}
