package com.dgsoft.house.owner;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.PersonIDCard;
import com.dgsoft.house.owner.model.BusinessHouseOwner;

/**
 * Created by cooper on 9/17/15.
 */
public class HouseOwnerHelper extends PersonHelper<BusinessHouseOwner>{


    public HouseOwnerHelper(BusinessHouseOwner entity) {
        super(entity);
    }

    @Override
    protected void fillPerson(PersonIDCard person){
        super.fillPerson(person);
        getPersonEntity().setAddress(person.getAddress());
        //TODO rootAddress
    }
}
