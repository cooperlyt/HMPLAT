package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.house.owner.HouseOwnerPersonHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-23.
 * 档案补录原产权人
 */
@Name("businessHouseOwnerOldSubscribe")
public class BusinessHouseOwnerOldSubscribe extends OwnerEntityHome<BusinessHouseOwner> {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public  BusinessHouseOwner createInstance(){
        BusinessHouseOwner result = new BusinessHouseOwner();
        result.setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getSingleHoues().getStartBusinessHouse().setBusinessHouseOwner(result);
        return result;

    }

    @Override
    public void create() {
        super.create();

        if (ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner() != null) {

            if (ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner().getId() == null) {
                setInstance(ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner());
            } else{
                setId(ownerBusinessHome.getSingleHoues().getStartBusinessHouse().getBusinessHouseOwner().getId());
            }
        }else{
            clearInstance();
            ownerBusinessHome.getSingleHoues().getStartBusinessHouse().setBusinessHouseOwner(getInstance());
        }
    }

    private HouseOwnerPersonHelper personHelper;

    public HouseOwnerPersonHelper getPersonInstance() {
        if ((personHelper == null) || (personHelper.getPersonEntity() != getInstance())) {
            personHelper = new HouseOwnerPersonHelper(getInstance());
        }
        return personHelper;
    }
}
