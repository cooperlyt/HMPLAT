package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/19/14.
 */
@Name("newHouseOwnerSubscribe")
public class NewHouseOwnerSubscribe extends BaseHouseOwnerSubscribe{


    @Override
    protected BusinessHouseOwner.HouseOwnerType getType() {
        return BusinessHouseOwner.HouseOwnerType.NEW_HOUSE_OWNER;
    }
}
