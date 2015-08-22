package com.dgsoft.house.owner.business;

import com.dgsoft.common.Entry;
import com.dgsoft.common.jbpm.TaskInstanceListCache;
import com.dgsoft.common.system.AuthenticationInfo;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.AllTaskAdapterCacheList;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.core.Events;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 8/20/15.
 */
@Name("checkTaskOperation")
public class CheckTaskOperation {

    @In(create = true)
    private AllTaskAdapterCacheList allTaskAdapterCacheList;

    @In
    private AuthenticationInfo authInfo;

    @In(required = false, scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false, scope = ScopeType.BUSINESS_PROCESS)
    private String transitionComments;


    @In(required = false, scope = ScopeType.BUSINESS_PROCESS)
    @Out(required = false, scope = ScopeType.BUSINESS_PROCESS)
    private String transitionType;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In(create = true)
    private FacesMessages facesMessages;

    private Long selectTaskId;

    public Long getSelectTaskId() {
        return selectTaskId;
    }

    public void setSelectTaskId(Long selectTaskId) {
        this.selectTaskId = selectTaskId;
    }

    public String getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(String transitionType) {
        this.transitionType = transitionType;
    }

    public String getTransitionComments() {
        return transitionComments;
    }

    public void setTransitionComments(String transitionComments) {
        this.transitionComments = transitionComments;
    }

    private boolean singleEnd(long taskid, TaskOper.OperType operType) {

        for (TaskInstanceListCache.TaskInstanceAdapter task : allTaskAdapterCacheList.getResultTask()) {
            if (task.getTaskInstance().getId() == taskid) {
                return singleEnd(task, operType);
            }
        }
        return false;

    }

    private boolean singleEnd(TaskInstanceListCache.TaskInstanceAdapter task, TaskOper.OperType operType) {
        ownerBusinessHome.setId(task.getTaskDescription().getBusinessKey());
        businessDefineHome.setId(task.getTaskDescription().getBusinessDefineKey());

        businessDefineHome.setTaskName(task.getTaskInstance().getName());

        if (TaskOper.OperType.CHECK_ACCEPT.equals(operType)) {
            if (businessDefineHome.isCompletePass()) {
                businessDefineHome.completeTask();
            } else {
                return false;
            }
        }

        TaskInstance taskInstance = ManagedJbpmContext.instance().getTaskInstanceForUpdate(task.getTaskInstance().getId());


        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                operType,
                ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(), transitionComments, task.getTaskDescription().getDescription()));


        taskInstance.end();
        ownerBusinessHome.update();

        return true;
    }

    private Entry<Integer,Integer> mulitEnd(TaskOper.OperType operType) {
        int successCount = 0;
        int failCount = 0;
        for (TaskInstanceListCache.TaskInstanceAdapter task : allTaskAdapterCacheList.getResultTask()) {
            if (task.isSelected()) {
                if (singleEnd(task, operType)){
                    successCount++;
                }else{
                    failCount++;
                }
            }
        }
        return new Entry<Integer, Integer>(successCount,failCount);
    }

    @Transactional
    public void check() {

        TaskOper.OperType operType = TaskOper.OperType.valueOf(transitionType);
        transitionType = TaskOper.OperType.CHECK_ACCEPT.name();
        if (selectTaskId == null) {

            Entry<Integer,Integer> result = mulitEnd(operType);

            StatusMessage.Severity severity;
            if (result.getKey().compareTo(0) <= 0){
                severity = StatusMessage.Severity.ERROR;
            } else if(result.getValue().compareTo(0) > 0){
                severity = StatusMessage.Severity.WARN;
            }else {
                severity = StatusMessage.Severity.INFO;
            }


            facesMessages.addFromResourceBundle(severity,"CheckMessage", result.getKey(), result.getValue() );
        } else {
           if (singleEnd(selectTaskId, operType)){
               facesMessages.addFromResourceBundle(StatusMessage.Severity.INFO,"CheckSuccess");
           }else{
               facesMessages.addFromResourceBundle(StatusMessage.Severity.ERROR,"CheckFail");
           }
        }
        Events.instance().raiseTransactionSuccessEvent("org.jboss.seam.endTask");
    }

}
