package com.dgsoft.house.owner.business.subscribe.complete;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskCompleteSubscribeComponent;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.Date;

/**
 * Created by cooper on 1/24/16.
 */
@Name("projectCardLockHouse")
public class ProjectCardLockHouse implements TaskCompleteSubscribeComponent {

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

        for(BusinessBuild bb: ownerBusinessHome.getInstance().getBusinessProject().getBusinessBuilds()){

            ownerBusinessHome.getEntityManager().createQuery("DELETE FROM LockedHouse lh where lh.buildCode = :buildCode and lh.type =:lockType")
                .setParameter("buildCode",bb.getBuildCode()).setParameter("lockType", LockedHouse.LockType.CANT_SALE).executeUpdate();
            for(ProjectExceptHouse eh: bb.getProjectExceptHouses()){
                ownerBusinessHome.getEntityManager().persist(
                        new LockedHouse(eh.getHouseCode(),
                                LockedHouse.LockType.CANT_SALE,
                                authInfo.getLoginEmployee().getId(),
                                authInfo.getLoginEmployee().getPersonName(), new Date(),bb.getBuildCode()));
            }
        }

        ownerBusinessHome.getEntityManager().flush();
    }
}
