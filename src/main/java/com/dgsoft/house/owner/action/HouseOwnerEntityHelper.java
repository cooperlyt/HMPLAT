package com.dgsoft.house.owner.action;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.PersonEntityHelper;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.house.owner.model.BusinessHouseOwner;

/**
 * Created by cooper on 8/26/14.
 */

public class HouseOwnerEntityHelper extends PersonEntityHelper<BusinessHouseOwner> {

    public HouseOwnerEntityHelper(BusinessHouseOwnerHome businessHouseOwnerHome) {
        this.businessHouseOwnerHome = businessHouseOwnerHome;
    }

    private BusinessHouseOwnerHome businessHouseOwnerHome;

    @Override
    protected EntityHomeAdapter<BusinessHouseOwner> getEntityHome() {
        return businessHouseOwnerHome;
    }

    @Override
    protected void fillPerson(Person person){
        businessHouseOwnerHome.getInstance().setRootAddress(person.getCredentialsOrgan());
    }
}
