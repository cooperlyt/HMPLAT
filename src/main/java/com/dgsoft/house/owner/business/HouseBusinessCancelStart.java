package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;

import java.util.Date;
import java.util.Map;

/**
 * Created by cooper on 4/8/16.
 */
@Name("houseBusinessCancelStart")
public class HouseBusinessCancelStart {

    @RequestParameter
    private String selectBusinessId;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In
    private Map<String,String> messages;


    public String startCancel(){

        OwnerBusiness selectOwnerBusiness = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, selectBusinessId);
        businessDefineHome.setId(selectOwnerBusiness.getDefineId());
        businessDefineHome.setTaskType(BusinessInstance.BusinessType.CANCEL_BIZ);
        selectOwnerBusiness.setStatus(BusinessInstance.BusinessStatus.MODIFYING);

        ownerBusinessHome.clearInstance();
        ownerBusinessHome.getInstance().setType(BusinessInstance.BusinessType.CANCEL_BIZ);
        ownerBusinessHome.getInstance().setSelectBusiness(selectOwnerBusiness);
        ownerBusinessHome.getInstance().setDefineName(businessDefineHome.getInstance().getName() + " " + messages.get(BusinessInstance.BusinessType.CANCEL_BIZ.name()));

        for(HouseBusiness hb: selectOwnerBusiness.getHouseBusinesses()){
            HouseBusiness cancelHouseBusiness = new HouseBusiness(ownerBusinessHome.getInstance());
            cancelHouseBusiness.setHouseCode(hb.getHouseCode());
            cancelHouseBusiness.setCanceled(false);
            cancelHouseBusiness.setStartBusinessHouse(hb.getAfterBusinessHouse());
            cancelHouseBusiness.setAfterBusinessHouse(hb.getStartBusinessHouse());

            for(AddHouseStatus ahs: hb.getAddHouseStatuses()){
                AddHouseStatus cancelStatus = new AddHouseStatus(ahs.getStatus(),cancelHouseBusiness,!ahs.isRemove());
                cancelHouseBusiness.getAddHouseStatuses().add(cancelStatus);
            }

            ownerBusinessHome.getInstance().getHouseBusinesses().add(cancelHouseBusiness);
        }



       // businessCreateTime = new Date();

        return businessDefineHome.getInstance().getModifyPage();
    }


}
