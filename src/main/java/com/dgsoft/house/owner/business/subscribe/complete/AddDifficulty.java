package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 15-7-29.
 * 添加异议
 */
@Name("addDifficulty")
public class AddDifficulty implements TaskCompleteSubscribeComponent {
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
        for (HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseInfo.HouseStatus.DIFFICULTY,houseBusiness));
            List<HouseInfo.HouseStatus> houseStatusList = new ArrayList<HouseInfo.HouseStatus>();
            houseStatusList = OwnerHouseHelper.instance().getHouseAllStatus(houseBusiness.getHouseCode());
            houseStatusList.add(HouseInfo.HouseStatus.DIFFICULTY);
            Collections.sort(houseStatusList, new HouseInfo.StatusComparator());
            houseBusiness.getAfterBusinessHouse().setMasterStatus(houseStatusList.get(0));

        }


    }
}
