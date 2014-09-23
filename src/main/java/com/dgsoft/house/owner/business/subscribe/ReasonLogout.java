package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-23
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@Name("reasonLogout")
public class ReasonLogout extends OwnerEntityHome<Reason> {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Reason createInstance(){
      return new Reason (Reason.ReasonType.LOGOUT);
    }

    @Override
    public void create(){
        super.create();
        for(Reason reason: ownerBusinessHome.getInstance().getReasons()){
            if (reason.getType().equals(Reason.ReasonType.LOGOUT)){
                setId(reason.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getReasons().add(getInstance());
    }
}
