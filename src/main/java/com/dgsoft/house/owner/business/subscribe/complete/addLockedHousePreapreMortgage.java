package com.dgsoft.house.owner.business.subscribe.complete;

import cc.coopersoft.house.LockType;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.HouseEntityLoader;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseBusiness;
import com.dgsoft.house.owner.model.LockedHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by wxy on 2017-03-20.
 */
@Name("addLockedHousePreapreMortgage")
public class addLockedHousePreapreMortgage implements TaskCompleteSubscribeComponent {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private HouseEntityLoader houseEntityLoader;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AuthenticationInfo authInfo;


    @Override
    public void valid() {

    }

    @Override
    public boolean isPass() {
        return true;
    }

    @Override
    public void complete() {
        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            ownerEntityLoader.getEntityManager().persist(new LockedHouse(houseBusiness.getHouseCode(), LockType.PREAPRE_MORTGAGE,ownerBusinessHome.getInstance().getDefineName(), authInfo.getLoginEmployee().getId(),
                    authInfo.getLoginEmployee().getPersonName(), new Date(),houseBusiness.getAfterBusinessHouse().getBuildCode()));

            ownerEntityLoader.getEntityManager().flush();
        }

    }
}
