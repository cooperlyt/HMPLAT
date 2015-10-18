package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wxy on 2015-09-02.
 * 去掉一个抵押状态
 */
@Name("movePledge")
public class MovePledge implements TaskCompleteSubscribeComponent {

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
                houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseStatus.PLEDGE, houseBusiness, true));
            }
    }
}
