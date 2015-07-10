package com.dgsoft.house.owner.action;

import com.dgsoft.common.jbpm.ProcessInstanceHome;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.house.owner.model.OwnerBusiness;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityNotFoundException;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.*;

/**
 * Created by cooper on 7/6/15.
 */
@Name("ownerBusinessView")
public class OwnerBusinessView {

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;
    

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private ProcessInstanceHome processInstanceHome;

    @In
    private AuthenticationInfo authInfo;

    @In
    private Map<String,String> messages;

    public void setId(String id){
        if ((id == null) || id.trim().equals("")){
            ownerBusinessHome.createInstance();
            businessDefineHome.clearInstance();
            processInstanceHome.setKey(null);
            return;
        }
        ownerBusinessHome.setId(id.trim());
        try {

            businessDefineHome.setId(ownerBusinessHome.getInstance().getDefineId());
            businessDefineHome.setTaskName(Subscribe.BUSINESS_VIEW_TASK_NAME);
            try {
                processInstanceHome.setKey(new ProcessInstanceHome.ProcessInstanceKey(businessDefineHome.getInstance().getWfName(),id));
            }catch (EntityNotFoundException e){
                businessDefineHome.clearInstance();
            }
        }catch (EntityNotFoundException e){
            ownerBusinessHome.clearInstance();
        }
    }

    public String getId(){
       return (String)ownerBusinessHome.getId();
    }


    @Transactional
    public void suspendBiz(){
        if (OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus()) &&
                (processInstanceHome.getInstance() != null)) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.SUSPEND);
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(null, TaskOper.TaskType.MANAGER, TaskOper.OperType.SUSPEND, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), messages.get(TaskOper.OperType.SUSPEND.name()), comments, true));
            processInstanceHome.suspend();
            ownerBusinessHome.update();
        }
    }

    @Transactional
    public void resumeBiz(){
        if (OwnerBusiness.BusinessStatus.SUSPEND.equals(ownerBusinessHome.getInstance().getStatus()) &&
                (processInstanceHome.getInstance() != null)) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.RUNNING);
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(null, TaskOper.TaskType.MANAGER, TaskOper.OperType.CONTINUE, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), messages.get(TaskOper.OperType.CONTINUE.name()), comments, true));
            processInstanceHome.suspend();
            ownerBusinessHome.update();
        }
    }

    @Transactional
    public void terminationBiz(){
        if ((OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus()) || OwnerBusiness.BusinessStatus.SUSPEND.equals(ownerBusinessHome.getInstance().getStatus())) &&
                (processInstanceHome.getInstance() != null) && !ownerBusinessHome.getInstance().isRecorded()) {
            ownerBusinessHome.getInstance().setStatus(OwnerBusiness.BusinessStatus.ABORT);
            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(null, TaskOper.TaskType.MANAGER, TaskOper.OperType.TERMINATION, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), messages.get(TaskOper.OperType.TERMINATION.name()), comments, true));
            processInstanceHome.stop();
            ownerBusinessHome.update();
        }
    }

    private String assignActorId;

    private Long taskInstanceId;

    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAssignActorId() {
        return assignActorId;
    }

    public void setAssignActorId(String assignActorId) {
        this.assignActorId = assignActorId;
    }

    public Long getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(Long taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    @Transactional
    public void assignTo(){
        if ((OwnerBusiness.BusinessStatus.RUNNING.equals(ownerBusinessHome.getInstance().getStatus())) &&
                (processInstanceHome.getInstance() != null)) {

            ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(null, TaskOper.TaskType.MANAGER, TaskOper.OperType.ASSIGN, ownerBusinessHome.getInstance(),
                    authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(), messages.get(TaskOper.OperType.ASSIGN.name()), comments, true));

            if (taskInstanceId == null){
                processInstanceHome.assign(assignActorId);
            }else{
                processInstanceHome.assign(taskInstanceId,assignActorId);
            }
            ownerBusinessHome.update();
        }
    }
}
