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
import org.jboss.seam.bpm.BusinessProcess;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @In
    private BusinessProcess businessProcess;

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

    public String transitionName;

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }

    @Transactional
    @End
    public String back(){
        transitionType = TaskOper.OperType.BACK.name();


        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                taskDescription.isCheckTask() ? TaskOper.OperType.CHECK_BACK : TaskOper.OperType.BACK,ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments));
        businessProcess.endTask(transitionName);
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


        if (businessDefineHome.saveSubscribes() && "updated".equals(ownerBusinessHome.update())) {
             return "SUCCESS";
        }else{
            return "ERROR";
        }
    }

    @In(create = true)
    private TaskOwnerBusinessFile taskOwnerBusinessFile;

    public boolean isNeedFilePass(){
        if (businessDefineHome.isHaveNeedFile()){
            return taskOwnerBusinessFile.isPass();
        }
        return true;
    }

    protected String completeTask() {
        //TODO no subscribe
        if (businessDefineHome.isCompletePass() && businessDefineHome.isSubscribesPass()){
            businessDefineHome.completeTask();
            if ("updated".equals(ownerBusinessHome.update())) {
                return "taskCompleted";
            }
        }

        throw new IllegalArgumentException("completeFail");
    }

    private List<String> backTransitions;

    public List<String> getBackTransitions(){
        if (backTransitions == null){
            backTransitions = new ArrayList<String>(taskInstance.getProcessInstance().getProcessDefinition().getNode(taskInstance.getName()).getLeavingTransitionsMap().keySet());
            backTransitions.remove(null);
            Collections.sort(backTransitions);
        }
        return backTransitions;
    }

    public boolean isCanBack(){
        return !getBackTransitions().isEmpty();
    }


}
