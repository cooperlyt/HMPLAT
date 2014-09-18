package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 9/9/14.
 */
@Name("backTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class BackTaskHandle {

    @In
    private TaskPublish taskPublish;

    @In
    private AuthenticationInfo authInfo;

    @In
    private TaskInstance taskInstance;

    @In(required = false,scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false,scope = ScopeType.BUSINESS_PROCESS)
    private String lastBackComments;


    public String getLastBackComments() {
        return lastBackComments;
    }

    public void setLastBackComments(String lastBackComments) {
        this.lastBackComments = lastBackComments;
    }

    @Transactional
    @EndTask(transition = "NEXT")
    public String complete() {
        if ("success".equals(taskPublish.save())) {
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),taskInstance.getName()));
            lastBackComments = null;
            return completeTask();
        }
        return null;
    }

    public void saveTask(){
        if ("success".equals(taskPublish.save())) {
            ownerBusinessHome.update();
        }
    }

    @Transactional
    @EndTask(transition = "BACK")
    public String back(){
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(TaskOper.OperType.TASK_OPER,ownerBusinessHome.getInstance(),authInfo.getLoginEmployee().getId(),authInfo.getLoginEmployee().getPersonName(),taskInstance.getName(),lastBackComments,false));
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
