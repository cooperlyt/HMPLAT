package com.dgsoft.house.owner.business;

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

    @In(required = false)
    @Out(required = false)
    private String transitionComments;


    @In(required = false)
    @Out(required = false)
    private String transitionType;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

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

    private void singleEnd(long taskid, TaskOper.OperType operType){

        for(TaskInstanceListCache.TaskInstanceAdapter task: allTaskAdapterCacheList.getResultTask()){
            if (task.getTaskInstance().getId() == taskid){
                singleEnd(task,operType);
                return;
            }
        }

    }

    private void singleEnd(TaskInstanceListCache.TaskInstanceAdapter task, TaskOper.OperType operType ){
        ownerBusinessHome.setId(task.getTaskDescription().getBusinessKey());
        businessDefineHome.setId(task.getTaskDescription().getBusinessDefineKey());

        TaskInstance taskInstance = ManagedJbpmContext.instance().getTaskInstanceForUpdate(task.getTaskInstance().getId());


        ownerBusinessHome.getInstance().getTaskOpers().add(new TaskOper(taskInstance.getId(),
                operType,
                ownerBusinessHome.getInstance(),
                authInfo.getLoginEmployee().getId(), authInfo.getLoginEmployee().getPersonName(),
                taskInstance.getName(), transitionComments, task.getTaskDescription().getDescription()));


        taskInstance.end();
        ownerBusinessHome.update();
    }

    private void mulitEnd(TaskOper.OperType operType){
        for(TaskInstanceListCache.TaskInstanceAdapter task: allTaskAdapterCacheList.getResultTask()){
            if (task.isSelected()){
                singleEnd(task,operType);
            }
        }
    }

    @Transactional
    public void check(){

        TaskOper.OperType operType = TaskOper.OperType.valueOf(transitionType);
        transitionType = TaskOper.OperType.CHECK_ACCEPT.name();
        if (selectTaskId == null){

            mulitEnd(operType);

        }else{
            singleEnd(selectTaskId,operType);
        }
        Events.instance().raiseTransactionSuccessEvent("org.jboss.seam.endTask");
    }

}
