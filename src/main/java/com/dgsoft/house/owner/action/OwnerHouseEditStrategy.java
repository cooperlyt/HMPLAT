package com.dgsoft.house.owner.action;

import com.dgsoft.house.HouseEditStrategy;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.model.House;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Created by cooper on 10/18/14.
 */

@Name("ownerHouseEditStrategy")
@Scope(ScopeType.STATELESS)
public class OwnerHouseEditStrategy implements HouseEditStrategy {

    @Override
    @BypassInterceptors
    public boolean isCanEdit(House house) {
        return (HouseInfo.HouseStatus.SALEING.equals(house.getMasterStatus()) ||
                HouseInfo.HouseStatus.CANTSALE.equals(house.getMasterStatus())) &&
                !house.getInitRegStatus().equals(HouseInfo.InitRegStatus.INIT_REG) && !house.getLockStatus().isLock();
    }

}
