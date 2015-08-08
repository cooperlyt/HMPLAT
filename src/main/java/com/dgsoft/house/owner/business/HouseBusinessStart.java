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


    private static final String BUSINESS_PICK_BIZ_PAGE = "";

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
        return ownerBusinessStart.dataSelected();
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

        return ownerBusinessStart.dataSelected();
    }


    public String mulitHouseSelect() {

        return ownerBusinessStart.dataSelected();
    }






}
