package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.EndTask;

/**
 * Created by cooper on 9/9/14.
 */
@Name("backTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class BackTaskHandle {

    @In
    private TaskPublish taskPublish;


    @Transactional
    @EndTask(transition = "NEXT")
    public String complete() {
        if ("success".equals(taskPublish.save())) {
            return completeTask();
        }
        return null;
    }

    @Transactional
    @EndTask(transition = "BACK")
    public String back(){
        return "taskCompleted";
    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    protected String completeTask() {
        if ("updated".equals(ownerBusinessHome.update())){
            return "taskCompleted";
        }
        return null;
    }

}
