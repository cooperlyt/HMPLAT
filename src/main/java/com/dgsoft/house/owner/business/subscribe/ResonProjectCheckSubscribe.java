package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by wxy on 2019-09-07.
 */
@Name("resonProjectCheckSubscribe")
public class ResonProjectCheckSubscribe extends OwnerEntityHome<Reason> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Reason createInstance(){
        return new Reason(Reason.ReasonType.PROJECT_CHECK);
    }

    @Override
    public void create(){
        super.create();
        for(Reason reason: ownerBusinessHome.getInstance().getReasons()){
            if (reason.getType().equals(Reason.ReasonType.PROJECT_CHECK)){
                setId(reason.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getReasons().add(getInstance());

    }
}
