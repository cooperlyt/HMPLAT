package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2018-07-08.
 * 备案完成后 设置 house.mainOwner
 */
@Name("addContractsPowerPerson")
public class AddContractsPowerPerson implements TaskCompleteSubscribeComponent {


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

        for (HouseBusiness houseBusiness : ownerBusinessHome.getInstance().getHouseBusinesses()) {

            houseBusiness.getAfterBusinessHouse().setMainOwner(houseBusiness.getAfterBusinessHouse().getMainContractPerson());

        }

    }
}
