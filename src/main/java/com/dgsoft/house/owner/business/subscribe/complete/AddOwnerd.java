package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseState;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by Administrator on 15-7-29.
 * 添加已办产权状态
 */
@Name("addOwnerd")
public class AddOwnerd implements TaskCompleteSubscribeComponent {
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
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()) {
            if (!houseBusiness.getAfterBusinessHouse().getAllStatusList().contains(HouseInfo.HouseStatus.OWNERED)) {
                houseBusiness.getAfterBusinessHouse().addStatus(HouseInfo.HouseStatus.OWNERED);
            }
        }
    }
}
