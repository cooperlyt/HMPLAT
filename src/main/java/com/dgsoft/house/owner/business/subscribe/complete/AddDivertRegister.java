package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.HouseStatus;
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
 * 添加房屋转移预告登记
 */
@Name("addDivertRegister")
public class AddDivertRegister implements TaskCompleteSubscribeComponent {


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
            houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseStatus.DIVERT_REGISTER,houseBusiness));
            List<HouseStatus> houseStatusList = OwnerHouseHelper.instance().getHouseAllStatus(houseBusiness.getHouseCode());
            houseStatusList.add(HouseStatus.DIVERT_REGISTER);
            Collections.sort(houseStatusList, new HouseStatus.StatusComparator());
            houseBusiness.getAfterBusinessHouse().setMasterStatus(houseStatusList.get(0));

        }


    }
}
