package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskDescription;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 9/4/14.
 */
@Name("ownerTaskHandle")
@Scope(ScopeType.CONVERSATION)
public class OwnerTaskHandle {

    public enum TransitionType{
        NEXT,BACK,REJECT
    }


    @In
    private BusinessDefineHome businessDefineHome;

    @In(required = false,scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false,scope = ScopeType.BUSINESS_PROCESS)
    private String transitionType;

    @In(required = false,scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false,scope = ScopeType.BUSINESS_PROCESS)
    private String transitionComments;

    @In
    private AuthenticationInfo authInfo;

    @In
    private TaskInstance taskInstance;

    @In
    private TaskDescription taskDescription;

    public String getTransitionComments() {
        return transitionComments;
    }

    public void setTransitionComments(String transitionComments) {
        this.transitionComments = transitionComments;
    }

    @Transactional
    @EndTask
    public String back(){
        transitionType = TaskOper.OperType.BACK.name();
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                taskDescription.isCheckTask() ? TaskOper.TaskType.CHECK :TaskOper.TaskType.TASK,
                TaskOper.OperType.BACK,ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments,false));
        return "taskCompleted";
    }

    @Transactional
    @EndTask
    public String complete() {
        transitionType = TaskOper.OperType.NEXT.name();

        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                taskDescription.isCheckTask() ? TaskOper.TaskType.CHECK :TaskOper.TaskType.TASK,
                TaskOper.OperType.NEXT,ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments,false));
        if ("updated".equals(ownerBusinessHome.update())) {
            return "taskCompleted";
        }
        return null;

    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    public String saveTask() {
        if ("success".equals(businessDefineHome.saveSubscribe())) {
            return ownerBusinessHome.update();
        }
        return null;
    }

    protected String completeTask() {
        if ("updated".equals(ownerBusinessHome.update())) {
            return "taskCompleted";
        }
        return null;
    }


}
