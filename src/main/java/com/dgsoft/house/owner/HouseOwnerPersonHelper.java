package com.dgsoft.house.owner;

import com.dgsoft.common.system.PersonHelper;
import com.dgsoft.common.system.PersonIDCard;
import com.dgsoft.house.owner.model.BusinessHouseOwner;

/**
 * Created by cooper on 10/20/15.
 */
public class HouseOwnerPersonHelper extends PersonHelper<BusinessHouseOwner> {

    private static final String ROOT_ADDRESS_FLAGE = "公安局";


    public HouseOwnerPersonHelper(BusinessHouseOwner entity) {
        super(entity);
    }

    @Override
    protected void fillPerson(PersonIDCard person) {
        super.fillPerson(person);
        int flagIndex = person.getCredentialsOrgan().indexOf(ROOT_ADDRESS_FLAGE);
        if(flagIndex > 0){
            getPersonEntity().setRootAddress(person.getCredentialsOrgan().substring(0,flagIndex));
        }
        getPersonEntity().setAddress(person.getAddress());

    }

    @Override
    protected void clearInfo(){
        super.clearInfo();
        getPersonEntity().setRootAddress(null);
        getPersonEntity().setAddress(null);
    }


}
