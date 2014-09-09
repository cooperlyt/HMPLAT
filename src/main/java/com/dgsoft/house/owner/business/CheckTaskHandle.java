package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.bpm.EndTask;

/**
 * Created by cooper on 9/9/14.
 */
@Name("checkTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class CheckTaskHandle {

    @In
    private TaskPublish taskPublish;

    @In(scope = ScopeType.BUSINESS_PROCESS)
    @Out(scope = ScopeType.BUSINESS_PROCESS)
    private String lastCheckComments;

    public String getLastCheckComments() {
        return lastCheckComments;
    }

    public void setLastCheckComments(String lastCheckComments) {
        this.lastCheckComments = lastCheckComments;
    }

    @Create
    public void init(){
        lastCheckComments = "";
    }

    @Transactional
    @EndTask(transition = "NEXT")
    public String accept() {
        if ("success".equals(taskPublish.save())) {
            return completeTask();
        }
        return null;
    }

    @Transactional
    @EndTask(transition = "BACK")
    public String unAccept(){
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
