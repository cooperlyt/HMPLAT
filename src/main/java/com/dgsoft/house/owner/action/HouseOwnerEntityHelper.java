package com.dgsoft.house.owner.action;

import com.dgsoft.common.EntityHomeAdapter;
import com.dgsoft.common.system.PersonEntityHelper;
import com.dgsoft.common.system.model.Person;
import com.dgsoft.house.owner.model.BusinessOwner;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/14.
 */
@Name("houseOwnerEntityHelper")
public class HouseOwnerEntityHelper extends PersonEntityHelper<BusinessOwner> {

    @In
    private BusinessOwnerHome businessOwnerHome;

    @Override
    protected EntityHomeAdapter<BusinessOwner> getEntityHome() {
        return businessOwnerHome;
    }

    @Override
    protected void fillPerson(Person person){
        businessOwnerHome.getInstance().setRootAddress(person.getCredentialsOrgan());
    }
}
