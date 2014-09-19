package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.model.BusinessHouseOwner;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/14.
 */
@Name("nowHouseOwnerSubscribe")
public class NowHouseOwnerSubscribe extends BaseHouseOwnerSubscribe {


    @Override
    protected BusinessHouseOwner.HouseOwnerType getType() {
        return BusinessHouseOwner.HouseOwnerType.NOW_HOUSE_OWNER;
    }
}
