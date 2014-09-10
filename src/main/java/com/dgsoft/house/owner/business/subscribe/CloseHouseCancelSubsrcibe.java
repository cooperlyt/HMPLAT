package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.HouseCloseCancel;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-10
 * Time: 下午3:43
 * To change this template use File | Settings | File Templates.
 */
@Name("closeHouseCancelSubsrcibe")
public class CloseHouseCancelSubsrcibe extends OwnerEntityHome<HouseCloseCancel> {
    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void create()
    {
        super.create();
        if (!ownerBusinessHome.getInstance().getHouseCloseCancels().isEmpty()) {
            setId(ownerBusinessHome.getInstance().getHouseCloseCancels().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getHouseCloseCancels().add(getInstance());
        }
    }

}
