package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2015-09-16.
 * 灭籍原因
 */
@Name("reasonDestroy")
public class ReasonDestroy extends OwnerEntityHome<Reason> {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Reason createInstance(){
        return new Reason (Reason.ReasonType.DESTROY);
    }

    @Override
    public void create(){
        super.create();
        for(Reason reason: ownerBusinessHome.getInstance().getReasons()){
            if (reason.getType().equals(Reason.ReasonType.DESTROY)){
                setId(reason.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getReasons().add(getInstance());
    }
}
