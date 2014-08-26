package com.dgsoft.house.owner.business.subscribe;

import com.dgsoft.common.system.business.TaskSubscribeComponent;
import com.dgsoft.house.owner.action.BusinessOwnerHome;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 8/26/14.
 */
@Name("businessOwner")
public class BusinessOwner implements TaskSubscribeComponent {

    @In(create = true)
    private BusinessOwnerHome businessOwnerHome;

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    public void init() {
       // ownerBusinessHome.getSingleHoues()
    }

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public boolean wire() {
        return false;
    }
}
