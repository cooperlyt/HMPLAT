package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.action.BusinessHouseOwnerHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/14.
 */
@Name("businessHouseOwnerSubscribe")
public class BusinessHouseOwnerSubscribe extends BusinessHouseOwnerHome{

    @In
    private HouseBusinessHome houseBusinessHome;

    @Override
    public Class<BusinessHouseOwner> getEntityClass(){
        return BusinessHouseOwner.class;
    }

    @Override
    public void create()
    {
        super.create();
        for (BusinessHouseOwner owner : houseBusinessHome.getInstance().getBusinessHouseOwners()) {
            if (owner.getType().equals(BusinessHouseOwner.HouseOwnerType.OWNER_PERSON)) {
                setId(owner.getId());
                return;
            }
        }
        getInstance().setHouseBusiness(houseBusinessHome.getInstance());
        houseBusinessHome.getInstance().getBusinessHouseOwners().add(getInstance());
    }

    @Override
    protected BusinessHouseOwner createInstance(){
        return new BusinessHouseOwner(BusinessHouseOwner.HouseOwnerType.OWNER_PERSON);
    }


}
