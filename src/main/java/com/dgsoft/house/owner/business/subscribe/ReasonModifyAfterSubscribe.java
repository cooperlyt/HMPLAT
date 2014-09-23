package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-29
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */
@Name("modifyAfterReasonSubscribe")
public class ReasonModifyAfterSubscribe extends OwnerEntityHome<Reason> {
    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public Reason createInstance(){
        return  new Reason(Reason.ReasonType.MODIFY_AFTER_RENSON);
    }

    @Override
    public void create(){
        super.create();
        for(Reason reason: ownerBusinessHome.getInstance().getReasons()){
            if(reason.getType().equals(Reason.ReasonType.MODIFY_AFTER_RENSON)){
                setId(reason.getId());
                return;
            }
        }
        getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
        ownerBusinessHome.getInstance().getReasons().add(getInstance());
    }


}
