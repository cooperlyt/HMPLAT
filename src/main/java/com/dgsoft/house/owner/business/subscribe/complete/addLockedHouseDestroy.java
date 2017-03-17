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
 * Created by wxy on 2017-03-17.
 */

@Name("addLockedHouseDestroy")
public class addLockedHouseDestroy implements TaskCompleteSubscribeComponent {

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

        System.out.println("11111");

        for(HouseBusiness houseBusiness:ownerBusinessHome.getInstance().getHouseBusinesses()){
            ownerEntityLoader.getEntityManager().persist(new LockedHouse(houseBusiness.getHouseCode(), LockType.OTHER_REG,ownerBusinessHome.getInstance().getDefineName(), authInfo.getLoginEmployee().getId(),
                    authInfo.getLoginEmployee().getPersonName(), new Date(),houseBusiness.getAfterBusinessHouse().getBuildCode()));
            System.out.println("333333");
            ownerEntityLoader.getEntityManager().flush();
        }
        System.out.println("222222");

    }
}
