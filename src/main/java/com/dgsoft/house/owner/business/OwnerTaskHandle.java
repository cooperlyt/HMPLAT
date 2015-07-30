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

    @In(required = false,scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false,scope = ScopeType.BUSINESS_PROCESS)
    private String backTaskName;

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
    @EndTask
    public String reject(){
        if (!taskDescription.isCheckTask()){
            throw new IllegalArgumentException("only check Task can call reject");
        }
        transitionType = TaskOper.OperType.CHECK_BACK.name();
        backTaskName = taskInstance.getName();
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                TaskOper.OperType.CHECK_BACK, ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),transitionComments));
        if ("updated".equals(ownerBusinessHome.update())) {
            return "taskCompleted";
        }
        throw new IllegalArgumentException("backFail");
    }

    @Transactional
    @End
    public String back(){

        if (taskDescription.isCheckTask()){
            throw new IllegalArgumentException("check Task can't call back");
        }


        transitionType = TaskOper.OperType.BACK.name();
        backTaskName = taskInstance.getName();
        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                TaskOper.OperType.BACK, ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName() + transitionName,transitionComments));

        if ("updated".equals(ownerBusinessHome.update())) {
            businessProcess.endTask(transitionName);
            return "taskCompleted";
        }
        throw new IllegalArgumentException("backFail");

    }

    public void init(){
       if(taskDescription.isCheckTask() && taskInstance.getName().equals(backTaskName)){
           transitionComments = null;
       }
    }

    public String getBackComments(){
        if (TaskOper.OperType.BACK.name().equals(transitionType)
                && (transitionComments != null) && !transitionComments.trim().equals("")){
            return transitionComments;
        }
        return null;
    }

    public String getCheckComments(){
        if (TaskOper.OperType.CHECK_BACK.name().equals(transitionType)
                && (transitionComments != null) && !transitionComments.trim().equals("")){
            return transitionComments;
        }
        return null;
    }

    @Transactional
    @EndTask
    public String complete() {
        if (taskDescription.isCheckTask()) {
            transitionType = TaskOper.OperType.CHECK_ACCEPT.name();
        }else if (taskInstance.getName().equals(backTaskName)){
            transitionComments = null;
            transitionType = null;
        }

        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                taskDescription.isCheckTask() ? TaskOper.OperType.CHECK_ACCEPT :TaskOper.OperType.NEXT,
                ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(),taskDescription.isCheckTask() ? transitionComments : null));

        if (businessDefineHome.isCompletePass() && businessDefineHome.isSubscribesPass()){
            businessDefineHome.completeTask();
            if ("updated".equals(ownerBusinessHome.update())) {
                return "taskCompleted";
            }
        }

        throw new IllegalArgumentException("completeFail");

    }

    @In
    private OwnerBusinessHome ownerBusinessHome;

    public String saveTask() {
        //TODO needFile
        if (businessDefineHome.isHaveNextEditGroup()){
            if(businessDefineHome.nextEditGroup())
                ownerBusinessHome.update();
            return "CONTINUE";
        }else {
            if (businessDefineHome.saveEditSubscribes() && "updated".equals(ownerBusinessHome.update())){
                return "SUCCESS";
            }else{
                return "CONTINUE";
            }
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

    public String operationTask(){

        if (businessDefineHome.isHaveEditSubscribe()){
            businessDefineHome.firstEditGroup();
            return "EDIT";
        }
        return "COMPLETE";

    }


}
