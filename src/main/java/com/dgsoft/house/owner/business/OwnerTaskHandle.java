package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskDescription;
import com.dgsoft.common.system.business.TaskSubscribeComponent;
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
                taskDescription.isCheckTask() ? TaskOper.OperType.CHECK_BACK : TaskOper.OperType.BACK,ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments));
        return "taskCompleted";
    }

    @Transactional
    @EndTask
    public String complete() {
        transitionType = TaskOper.OperType.NEXT.name();

        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                taskDescription.isCheckTask() ? TaskOper.OperType.CHECK_ACCEPT :TaskOper.OperType.NEXT,
                ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments));


        return completeTask();

    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    public String saveTask() {
        //TODO needFile

        TaskSubscribeComponent.ValidResult result = businessDefineHome.validSubscribes();

        if (result.getPri() > TaskSubscribeComponent.ValidResult.WARN.getPri()){
            return result.name();
        }

        if (businessDefineHome.saveSubscribes() && "updated".equals(ownerBusinessHome.update())) {
             return result.name();
        }else{
            return TaskSubscribeComponent.ValidResult.FATAL.name();
        }

    }

    protected String completeTask() {
        //TODO no subscribe

        businessDefineHome.completeTask();
        if ("updated".equals(ownerBusinessHome.update())) {
            return "taskCompleted";
        }else
        return null;
    }




}
