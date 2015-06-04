package com.dgsoft.common.system.business;

import com.dgsoft.common.OrderBeanComparator;
import com.dgsoft.common.jbpm.TaskInstanceListCache;
import com.dgsoft.common.system.SystemEntityLoader;
import com.dgsoft.common.system.model.BusinessCategory;
import com.dgsoft.common.system.model.BusinessDefine;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.Actor;
import org.jboss.seam.log.Logging;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by cooper on 5/25/15.
 */
@Name("allTaskAdapterCacheList")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AllTaskAdapterCacheList extends TaskInstanceListCache {


    @Override
    protected Set<TaskInstance> searchTaskInstances() {
        List<TaskInstance> taskInstanceList = (List<TaskInstance>) Component.getInstance("org.jboss.seam.bpm.taskInstanceList");
        taskInstanceList.addAll((List<TaskInstance>) Component.getInstance("org.jboss.seam.bpm.pooledTaskInstanceList"));
        return new HashSet<TaskInstance>(taskInstanceList);
    }

    public enum TaskFilterType{
        ALL("allTask"),OWNER("myBussiness"),POOLED("todoBussiness");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        private TaskFilterType(String messageKey){
            this.messageKey = messageKey;
        }
    }

    private TaskFilterType filterType = TaskFilterType.ALL;

    public TaskFilterType getFilterType() {
        return filterType;
    }

    public void setFilterTypeName(String typeName){
        filterType = TaskFilterType.valueOf(typeName);
    }

    public String getFilterTypeName(){
        return filterType.name();
    }

    private List<TaskInstanceAdapter> allTasks;

    private List<TaskInstanceAdapter> resultList;

    protected List<TaskInstanceAdapter> getAllTasks(){
        if (allTasks == null){
            allTasks = new ArrayList<TaskInstanceAdapter>();
            for (TaskInstance taskInstance: super.getTaskInstanceCreateList()){
                TaskInstanceAdapter task = new TaskInstanceAdapter(taskInstance, actor.getId().equals(taskInstance.getActorId()), systemEntityLoader);
                allTasks.add(task);
            }
        }
        return allTasks;
    }

    protected List<TaskInstanceAdapter> getTasksByType(TaskFilterType type){
        List<TaskInstanceAdapter> result = new ArrayList<TaskInstanceAdapter>();
        if (TaskFilterType.ALL.equals(type)){
            result.addAll(getAllTasks());
        }else{
            for(TaskInstanceAdapter task: getAllTasks()){
                if (TaskFilterType.OWNER.equals(type) && task.isMyTask()){
                    result.add(task);
                }else if (TaskFilterType.POOLED.equals(type) && !task.isMyTask()){
                    result.add(task);
                }
            }
        }
        return result;
    }

    protected List<TaskInstanceAdapter> getCurTypeTasks(){
        return getTasksByType(filterType);
    }

    @In(create = true)
    private SystemEntityLoader systemEntityLoader;

    @In
    private TaskFilter taskFilter;

    private List<FilterBusinessCategory> categories;

    @In
    private Actor actor;

    @Override
    public void refresh(){
        super.refresh();
        allTasks = null;
        reset();
    }

    public void reset(){
        resultList = null;
        categories = null;
    }

    public void clearCondition(){
        taskFilter.clearCondition();
        reset();
    }


    private FilterBusinessCategory selectCategory;

    private String selectDefineId;


    public FilterBusinessCategory getSelectCategory() {
        return selectCategory;
    }

    public void setSelectCategory(FilterBusinessCategory selectCategory) {
        this.selectCategory = selectCategory;
    }

    public String getSelectCategoryId(){
        if (selectCategory == null){
            return null;
        }else{
            return selectCategory.getCategory().getId();
        }
    }

    public void setSelectCategoryId(String id){
        if ((id == null) || id.trim().equals("")){
            selectCategory = null;
        }else{
            for (FilterBusinessCategory category: getFilterCategorys()){
                if (category.getCategory().getId().equals(id)){
                    selectCategory = category;
                    return ;
                }
            }
        }
    }

    public String getSelectDefineId() {
        return selectDefineId;
    }

    public void setSelectDefineId(String selectDefineId) {
        this.selectDefineId = selectDefineId;
    }

    public List<FilterBusinessCategory> getFilterCategorys(){
        if (categories == null) {
            Map<String, FilterBusinessCategory> categoryMap = new HashMap<String, FilterBusinessCategory>();

            for (TaskInstanceAdapter task: getCurTypeTasks()) {


                BusinessCategory category = task.getBusinessDefine().getBusinessCategory();
                FilterBusinessCategory fCategory = categoryMap.get(category.getId());
                if (fCategory == null) {
                    fCategory = new FilterBusinessCategory(category);
                    categoryMap.put(category.getId(), fCategory);
                }
                fCategory.putDefine(task.getBusinessDefine());

            }
            categories = new ArrayList<FilterBusinessCategory>(categoryMap.values());
            Collections.sort(categories, new Comparator<FilterBusinessCategory>() {
                @Override
                public int compare(FilterBusinessCategory o1, FilterBusinessCategory o2) {
                    return new Integer(o1.getCategory().getPriority()).compareTo(o2.getCategory().getPriority());
                }
            });
            for(FilterBusinessCategory category: categories){
                Collections.sort(category.getDefineList(),OrderBeanComparator.getInstance());
            }

        }
        return categories;
    }

    public int getAllSize(){
        return super.getTaskInstanceCreateList().size();
    }

    public int getOwnerSize(){
        return getTasksByType(TaskFilterType.OWNER).size();
    }

    public int getPooledSize(){
        return getTasksByType(TaskFilterType.POOLED).size();
    }

    public boolean isEmptyTask(){
        return getCurTypeTasks().isEmpty();
    }

    public List<TaskInstanceAdapter> getResultList(){
        if (resultList == null){
            resultList = new ArrayList<TaskInstanceAdapter>(getCurTypeTasks());
            List<TaskInstanceAdapter> filterList = new ArrayList<TaskInstanceAdapter>();

            if ((selectCategory != null) && (selectDefineId != null) && !selectDefineId.trim().equals("")){
                for(TaskInstanceAdapter task: resultList){
                    if (task.getBusinessDefine().getId().equals(selectDefineId)){
                        filterList.add(task);
                    }
                }
                resultList.clear();
                resultList.addAll(filterList);
                filterList.clear();
            } else if (selectCategory != null){
                for(TaskInstanceAdapter task: resultList){
                    if (task.getBusinessDefine().getBusinessCategory().getId().equals(selectCategory.getCategory().getId())){
                        filterList.add(task);
                    }
                }
                resultList.clear();
                resultList.addAll(filterList);
                filterList.clear();
            }


            resultList = taskFilter.filter(resultList);

        }



        return resultList;
    }

    public void clearDateFrom(){
        taskFilter.getSearchDateArea().setDateFrom(null);
        reset();
    }

    public void clearDateTo(){
        taskFilter.getSearchDateArea().setDateTo(null);
        reset();
    }

    public static class TaskInstanceAdapter{

        private TaskInstance taskInstance;

        private TaskDescription taskDescription;

        private BusinessDefine businessDefine;

        private boolean myTask;

        public TaskInstanceAdapter(TaskInstance taskInstance, boolean myTask, SystemEntityLoader entityLoader){
            this.myTask = myTask;
            this.taskInstance = taskInstance;

            try {
                taskDescription = new TaskDescription(new JSONObject(taskInstance.getDescription()));
                this.businessDefine = entityLoader.getEntityManager().createQuery("select define from BusinessDefine define left join fetch define.businessCategory where define.id = :id", BusinessDefine.class)
                        .setParameter("id", taskDescription.getBusinessDefineKey()).getSingleResult();
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

        public boolean isMyTask() {
            return myTask;
        }

        public boolean isMyOnly(){
            if (myTask) return taskInstance.getPooledActors().isEmpty(); else return false;
        }
    }

    public static class FilterBusinessCategory{

        private BusinessCategory category;

        private Set<BusinessDefine> businessDefines = new HashSet<BusinessDefine>();

        public FilterBusinessCategory(BusinessCategory category) {
            this.category = category;
        }

        public void putDefine(BusinessDefine define){
            businessDefines.add(define);
        }

        public BusinessCategory getCategory() {
            return category;
        }

        public List<BusinessDefine> getDefineList(){
            List<BusinessDefine> result = new ArrayList<BusinessDefine>(businessDefines);
            Collections.sort(result, OrderBeanComparator.getInstance());
            return result;
        }
    }


}
