package com.dgsoft.house.owner.action;

import com.dgsoft.common.jbpm.ProcessInstanceHome;
import com.dgsoft.common.system.DictionaryWord;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.Subscribe;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
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

    private boolean showAll = false;

    public void setId(String id){
        ownerBusinessHome.setId(id);
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

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public List<TaskHistory> getTaskHistoryList(){
        List<TaskHistory> result = new ArrayList<TaskHistory>();
        if (showAll){
            Map<Long,TaskOper> operMap = new HashMap<Long, TaskOper>();
            for (TaskOper oper: ownerBusinessHome.getInstance().getTaskOpers()){
                operMap.put(oper.getId(),oper);
            }
            for(TaskInstance instance: processInstanceHome.getTaskInstanceList()){
                result.add(new TaskHistory(instance ,operMap.get(instance.getId())));
            }

        }else {
            for(TaskOper oper: ownerBusinessHome.getTaskOperList()){
                result.add(new TaskHistory(oper));
            }
        }
        return result;
    }

    public List<TaskInstance> getOpenTasks(){
        List<TaskInstance> result = new ArrayList<TaskInstance>();
        if (processInstanceHome.getInstance() != null){
            for(TaskInstance instance: processInstanceHome.getTaskInstanceList()){
                if (instance.isOpen()){
                    result.add(instance);
                }
            }
        }
        return result;
    }

    public static class TaskHistory{

        private TaskInstance taskInstance;

        private TaskOper taskOper;

        public TaskHistory(TaskInstance taskInstance) {
            this.taskInstance = taskInstance;
        }

        public TaskHistory(TaskOper taskOper) {
            this.taskOper = taskOper;
        }

        public TaskHistory(TaskInstance taskInstance, TaskOper taskOper) {
            this.taskInstance = taskInstance;
            this.taskOper = taskOper;
        }

        public TaskInstance getTaskInstance() {
            return taskInstance;
        }

        public TaskOper getTaskOper() {
            return taskOper;
        }

        public Long getId(){
            if (taskInstance != null){
                return taskInstance.getId();
            }else{
                return taskOper.getId();
            }
        }

        public String getName(){
            if (taskInstance != null){
                return taskInstance.getName();
            }else{
                return taskOper.getTaskName();
            }
        }

        public Date getCreate(){
            if (taskInstance != null){
                return taskInstance.getCreate();
            }else{
                return null;
            }
        }

        public Date getEnd(){
            if (taskInstance != null){
                return taskInstance.getEnd();
            }else{
                return taskOper.getOperTime();
            }
        }

        public String getEmpId(){
            if (taskInstance != null){
                return taskInstance.getActorId();
            }else{
                return taskOper.getEmpCode();
            }
        }

        public String getEmpName(){
            if (taskOper != null){
               return taskOper.getEmpName();
            }else{
                return DictionaryWord.instance().getEmpNameById(taskInstance.getActorId());
            }
        }

        public Boolean getOpen(){
            if (taskInstance != null){
                return taskInstance.isOpen();
            }
            return null;
        }

    }

}
