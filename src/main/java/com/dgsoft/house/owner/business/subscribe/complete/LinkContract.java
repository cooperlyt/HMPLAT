package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.SaleType;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseContract;
import org.jboss.seam.annotations.In;

import java.util.EnumSet;

/**
 * Created by cooper on 04/12/2016.
 */
public abstract class LinkContract implements TaskCompleteSubscribeComponent {

    protected abstract EnumSet<SaleType> getLinkTypes();

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        for (HouseBusiness hb: ownerBusinessHome.getInstance().getHouseBusinesses()){
            for(HouseContract hc: hb.getStartBusinessHouse().getHouseContracts()){
                if (getLinkTypes().contains(hc.getType())){
                    hb.getAfterBusinessHouse().getHouseContracts().add(hc);
                }
            }
        }
    }
}
