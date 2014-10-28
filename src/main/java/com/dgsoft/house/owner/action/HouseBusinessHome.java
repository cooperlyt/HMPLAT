package com.dgsoft.house.owner.action;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.model.BusinessHouse;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 10/28/14.
 */
@Name("houseBusinessHome")
public class HouseBusinessHome extends OwnerEntityHome<HouseBusiness> {

    @Factory(value = "poolTypes")
    public BusinessHouse.PoolType[] getPoolTypes(){
        return BusinessHouse.PoolType.values();
    }
}
