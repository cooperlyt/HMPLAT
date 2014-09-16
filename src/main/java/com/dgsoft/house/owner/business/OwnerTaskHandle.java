package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 9/4/14.
 */
@Name("ownerTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class OwnerTaskHandle{

    @In
    private TaskPublish taskPublish;


    @In
    private AuthenticationInfo authInfo;

    @In
    private TaskInstance taskInstance;

    @Transactional
    @EndTask
    public String complete() {
        if ("success".equals(taskPublish.save())) {
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),taskInstance.getName()));
            return completeTask();
        }
        return null;
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
