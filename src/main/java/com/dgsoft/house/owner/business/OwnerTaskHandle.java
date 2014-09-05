package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.TaskHandle;
import com.dgsoft.house.owner.OwnerEntityLoader;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/4/14.
 */
@Name("ownerTaskHandle")
public class OwnerTaskHandle extends TaskHandle{

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @Override
    protected String completeTask() {

        return null;
    }


}
