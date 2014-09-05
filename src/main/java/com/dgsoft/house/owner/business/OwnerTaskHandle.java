package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.TaskHandle;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

/**
 * Created by cooper on 9/4/14.
 */
@Name("ownerTaskHandle")
public class OwnerTaskHandle extends TaskHandle{

    @In
    private OwnerBusinessHome ownerBusinessHome;

    @Override
    protected String completeTask() {
        if ("persisted".equals(ownerBusinessHome.update())){
            return "taskCompleted";
        }
        return null;
    }


}
