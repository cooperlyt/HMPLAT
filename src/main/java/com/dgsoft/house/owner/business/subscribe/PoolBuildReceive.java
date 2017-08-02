package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2017-08-02.
 * 东港共有建筑
 */
@Name("poolBuildReceive")
public class PoolBuildReceive extends OwnerEntityHome<Reason> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Reason createInstance(){
        return new Reason(Reason.ReasonType.POOL_BUILD);
    }


    @Override
    public void create()
    {
        super.create();
        for(Reason reason: ownerBusinessHome.getInstance().getReasons()){
            if (reason.getType().equals(Reason.ReasonType.POOL_BUILD)){
                setId(reason.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getReasons().add(getInstance());

    }


}
