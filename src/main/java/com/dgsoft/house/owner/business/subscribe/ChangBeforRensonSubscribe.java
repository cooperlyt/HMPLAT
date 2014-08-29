package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.model.Reason;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-8-29
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
@Name("changBeforRensonSubscribe")
public class ChangBeforRensonSubscribe extends OwnerEntityHome<Reason> {

    @In
    private HouseBusinessHome houseBusinessHome;

    @Override
    public Reason createInstance(){
        return new Reason(Reason.ReasonType.CHANG_BEFOR_RESON);
    }

    @Override
    public void create(){
        super.create();
        for(Reason reason:houseBusinessHome.getInstance().getReasons()){
            if (reason.getType().equals(Reason.ReasonType.CHANG_BEFOR_RESON)){
                setId(reason.getId());
                return;
            }

        }
        getInstance().setOwnerBusiness(houseBusinessHome.getInstance());
        houseBusinessHome.getInstance().getReasons().add(getInstance());

    }

}
