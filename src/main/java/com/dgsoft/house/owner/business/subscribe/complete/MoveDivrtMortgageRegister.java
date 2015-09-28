package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseStatus;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.action.OwnerHouseHelper;
import com.dgsoft.house.owner.model.AddHouseStatus;
import com.dgsoft.house.owner.model.HouseBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wxy on 2015-09-14.
 * 去掉一个房屋所有权预告抵押状态
 */
@Name("moveDivrtMortgageRegister")
public class MoveDivrtMortgageRegister implements TaskCompleteSubscribeComponent {

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
                if(!ownerBusinessHome.getInstance().getType().equals(BusinessInstance.BusinessType.MODIFY_BIZ)) {
                    houseBusiness.getAddHouseStatuses().add(new AddHouseStatus(HouseStatus.DIVERT_MORTGAGE_REGISTER, houseBusiness, true));
                }

                List<HouseStatus> houseStatusList = new ArrayList<HouseStatus>();
                houseStatusList = OwnerHouseHelper.instance().getHouseAllStatus(houseBusiness.getHouseCode());
                houseStatusList.remove(HouseStatus.DIVERT_MORTGAGE_REGISTER);
                Collections.sort(houseStatusList, new HouseStatus.StatusComparator());
                houseBusiness.getAfterBusinessHouse().setMasterStatus(houseStatusList.get(0));

            }
        }

}

