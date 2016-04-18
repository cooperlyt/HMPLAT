package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.TimeAreaHelper;
import com.dgsoft.house.owner.OwnerEntityHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.CloseHouse;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-9-10
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
@Name("closeHouseSubsrcibe")
public class CloseHouseSubsrcibe extends OwnerEntityHome<CloseHouse> {
    @In
    private OwnerBusinessHome ownerBusinessHome;



    @Override
    public void create()
    {
        super.create();
        if (!ownerBusinessHome.getInstance().getCloseHouses().isEmpty()) {
            setId(ownerBusinessHome.getInstance().getCloseHouses().iterator().next().getId());
        }else {
            getInstance().setOwnerBusiness(ownerBusinessHome.getInstance());
            ownerBusinessHome.getInstance().getCloseHouses().add(getInstance());
        }
        timeAreaHelper = getInstance().getTimeArea();
    }

    private TimeAreaHelper timeAreaHelper;

    public TimeAreaHelper getTimeAreaHelper() {
        return timeAreaHelper;
    }


}
