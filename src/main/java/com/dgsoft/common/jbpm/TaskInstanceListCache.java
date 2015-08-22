package com.dgsoft.common.jbpm;

import com.dgsoft.common.system.business.BusinessDefineCache;
import com.dgsoft.common.system.business.TaskDescription;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Logging;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 2/11/14
 * Time: 10:12 PM
 */
public abstract class TaskInstanceListCache {

    protected abstract Set<TaskInstanceAdapter> initAllTaskInstances();

    private boolean allTaskListChange = false;

    private boolean resultTaskListChange = false;

    private Set<TaskInstanceAdapter> allTask;

    private List<TaskInstanceAdapter> resultTask;

    private List<TaskInstanceAdapter> comeTask = new ArrayList<TaskInstanceAdapter>();


    public abstract List<TaskInstanceAdapter> initResultTask();

    private Actor actor;


    @Create
    public void register() {
        actor = Actor.instance();
        ((BpmTaskChangePublish) Component.getInstance(BpmTaskChangePublish.class, ScopeType.APPLICATION, true)).subscribe(this);
        refresh();
    }

    @Destroy
    public void unRegister(){
        ((BpmTaskChangePublish) Component.getInstance(BpmTaskChangePublish.class, ScopeType.APPLICATION, true)).unSubscribe(this);
    }

    public Actor getActor() {
        return actor;
    }

    public void initTaskList(){
        refresh();
        allTaskListChange = false;
        resultTaskListChange = false;
    }

    public void refreshResult(){
        refresh();
        allTaskListChange = false;
        resultTaskListChange = false;
    }

    public void refresh() {


        Set<TaskInstanceAdapter> newTasks = initAllTaskInstances();

        if (allTask != null ){
            Set<Long> selectedTask = new HashSet<Long>();
            for(TaskInstanceAdapter task: allTask){
                if (task.isSelected()){
                    selectedTask.add(task.getTaskInstance().getId());
                }
            }

            Set<TaskInstanceAdapter> temp = new HashSet<TaskInstanceAdapter>(newTasks);
            for(TaskInstanceAdapter task: newTasks){
                if (selectedTask.contains(Long.valueOf(task.getTaskInstance().getId()))){
                    task.setSelected(true);
                }

                if (allTask.remove(task)){
                    temp.remove(task);
                }
            }

            if (!allTaskListChange && (!temp.isEmpty() || !allTask.isEmpty())){
                allTaskListChange = true;
            }

            for(TaskInstanceAdapter task: temp){
                boolean existsCome = false;
                for(TaskInstanceAdapter comeinTask: comeTask){
                    if (comeinTask.getTaskInstance().getId() == task.getTaskInstance().getId()){
                        existsCome = true;
                        break;
                    }
                }
                if (!existsCome){
                    comeTask.add(task);
                }
            }

        }

        allTask = newTasks;

        reset();
    }

    public void reset(){
        List<TaskInstanceAdapter> newTasks = initResultTask();
        if (!resultTaskListChange && resultTask != null){
            Set<TaskInstanceAdapter> temp = new HashSet<TaskInstanceAdapter>(newTasks);
            for(TaskInstanceAdapter task: newTasks){
                if (resultTask.contains(task)){
                    temp.remove(task);
                    resultTask.remove(task);
                }
            }
            if (!temp.isEmpty() || !resultTask.isEmpty()){
                resultTaskListChange = true;
            }
        }


        resultTask = newTasks;

    }

    public int getAllSize(){
        if (allTask == null){
            return 0;
        }
        return allTask.size();
    }

    protected Set<TaskInstanceAdapter> getAllTask() {
        return allTask;
    }

    public boolean isAllTaskListChange() {
        return allTaskListChange;
    }

    public boolean isResultTaskListChange() {
        return resultTaskListChange;
    }

    public void clearResultChangeTag(){

        allTaskListChange = false;
        resultTaskListChange = false;
    }

    public void clearCome(){
        comeTask.clear();
    }

    public void clearResultCome(){
        for(TaskInstanceAdapter task: getResultTask()){
            comeTask.remove(task);
        }
    }

    public void clearAllTaskChangeTag(){
        allTaskListChange = false;
    }

    public List<TaskInstanceAdapter> getResultTask() {
        return resultTask;
    }

    public List<TaskInstanceAdapter> getComeTask() {
        return comeTask;
    }

    public boolean isCome(long taskId){
        for(TaskInstanceAdapter taskInstanceAdapter : getComeTask()){
            if (taskInstanceAdapter.getTaskInstance().getId() == taskId){
                return true;
            }
        }
        return false;
    }

    public boolean isHaveComeTask(){
        return !comeTask.isEmpty();
    }

    public void setAllSelect(boolean select){
        for(TaskInstanceAdapter task: allTask){
            if (resultTask.contains(task)){
                task.setSelected(select);
            }else {
                task.setSelected(false);
            }
        }
    }

    public boolean isAllSelect(){
        if (resultTask.isEmpty()){
            return false;
        }
        for(TaskInstanceAdapter task: resultTask){
            if (!task.isSelected()){
                return false;
            }
        }
        return true;
    }

    public static class TaskInstanceAdapter{

        private TaskInstance taskInstance;

        private TaskDescription taskDescription;

        private BusinessDefine businessDefine;

        //private boolean myTask;

        private boolean selected = false;

        public TaskInstanceAdapter(TaskInstance taskInstance){
            this.taskInstance = taskInstance;

            try {
                taskDescription = new TaskDescription(new JSONObject(taskInstance.getDescription()));

                this.businessDefine = BusinessDefineCache.instance().getDefine(taskDescription.getBusinessDefineKey());;
            } catch (JSONException e) {
                Logging.getLog(getClass()).debug("taskDescription Error",e);
                throw new IllegalArgumentException("taskDescription Error",e);
            }
        }

        public TaskInstance getTaskInstance() {
            return taskInstance;
        }

        public TaskDescription getTaskDescription() {
            return taskDescription;
        }

        public BusinessDefine getBusinessDefine() {
            return businessDefine;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isMyTask() {
            return  Actor.instance().getId().equals(taskInstance.getActorId());
        }

        public boolean isMyOnly(){
            if (isMyTask()) return (taskInstance.getPooledActors() == null) || taskInstance.getPooledActors().isEmpty() ; else return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TaskInstanceAdapter that = (TaskInstanceAdapter) o;

            if (isMyTask() != that.isMyTask()) return false;
            return (taskInstance.getId() == that.taskInstance.getId());

        }

        @Override
        public int hashCode() {
            int result = (int) (taskInstance.getId() ^ (taskInstance.getId() >>> 32));
            result = 31 * result + (isMyTask() ? 1 : 0);
            return result;
        }
    }

}
