package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.HouseState;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Logging;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-7-29.
 * 去掉一个备案状态
 */
@Name("moveContractsRecord")
public class MoveContractsRecord implements TaskCompleteSubscribeComponent {


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
        Logging.getLog(getClass()).debug("2222");
        List<HouseState> houseStateList = new ArrayList<HouseState>();
        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            houseStateList.clear();
            Logging.getLog(getClass()).debug("moveContractsRecord--:"+houseBusiness.getAfterBusinessHouse().getHouseStates().size());
            for(HouseState houseState:houseBusiness.getAfterBusinessHouse().getHouseStates()){
                Logging.getLog(getClass()).debug("moveContractsRecord+111111111111111");


                if(houseState.getState().equals(HouseInfo.HouseStatus.CONTRACTS_RECORD)){
                    houseStateList.add(houseState);
                    break;
                }
            }
            if (!houseStateList.isEmpty()){
                houseBusiness.getAfterBusinessHouse().getHouseStates().removeAll(houseStateList);
            }

        }
    }
}
