package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.TaskHandle;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.HouseBusinessHome;
import com.dgsoft.house.owner.action.ProjectBusinessHome;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

/**
 * Created by cooper on 9/4/14.
 */
@Name("ownerTaskHandle")
public class OwnerTaskHandle extends TaskHandle{

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(required = false)

    @Override
    protected String completeTask() {

        return null;
    }


}
