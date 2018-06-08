package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.TimeAreaHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CloseHouse;
import com.dgsoft.house.owner.model.LeaseHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-01-10.
 */
@Name("leaseHouseSubsrcibe")
public class LeaseHouseSubsrcibe extends OwnerEntityHome<LeaseHouse> {
    @In
    private OwnerBusinessHome ownerBusinessHome;


    @Override
    public void create()
    {
        super.create();
        if (!ownerBusinessHome.getInstance().getLeaseHouses().isEmpty()) {
            setId(ownerBusinessHome.getInstance().getLeaseHouses().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getLeaseHouses().add(getInstance());
        }
        timeAreaHelper = getInstance().getTimeArea();
    }

    private TimeAreaHelper timeAreaHelper;

    public TimeAreaHelper getTimeAreaHelper() {
        return timeAreaHelper;
    }

}
