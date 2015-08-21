package com.dgsoft.common.system.business;

import com.dgsoft.common.jbpm.TaskInstanceListCache;
import com.dgsoft.common.system.FilterBusinessCategory;
import com.dgsoft.common.system.model.BusinessCategory;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.*;

/**
 * Created by cooper on 5/25/15.
 */
@Name("allTaskAdapterCacheList")
@Scope(ScopeType.SESSION)
@AutoCreate
public class AllTaskAdapterCacheList extends TaskInstanceListCache {

    @Override
    protected Set<TaskInstanceAdapter> initAllTaskInstances() {



        List<TaskInstance> taskInstanceList = ManagedJbpmContext.instance().getTaskList(getActor().getId());;

        ArrayList groupIds = new ArrayList( getActor().getGroupActorIds() );

        groupIds.add(getActor().getId());

        taskInstanceList.addAll(ManagedJbpmContext.instance().getGroupTaskList(groupIds));



        Set<TaskInstanceAdapter> result = new HashSet<TaskInstanceAdapter>();
        for (TaskInstance taskInstance : taskInstanceList) {
            result.add(new TaskInstanceAdapter(taskInstance));
        }
        return result;
    }

    @Override
    public List<TaskInstanceAdapter> initResultTask() {


        Map<String, FilterBusinessCategory> categoryMap = new HashMap<String, FilterBusinessCategory>();

        for (TaskInstanceAdapter task : getCurTypeTasks()) {


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

        //--------------

        List<TaskInstanceAdapter> resultList = new ArrayList<TaskInstanceAdapter>(getCurTypeTasks());
        List<TaskInstanceAdapter> filterList = new ArrayList<TaskInstanceAdapter>();

        if ((selectCategory != null) && (selectDefineId != null) && !selectDefineId.trim().equals("")) {
            for (TaskInstanceAdapter task : resultList) {
                if (task.getBusinessDefine().getId().equals(selectDefineId)) {
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        } else if (selectCategory != null) {
            for (TaskInstanceAdapter task : resultList) {
                if (task.getBusinessDefine().getBusinessCategory().getId().equals(selectCategory.getCategory().getId())) {
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }


        resultList = TaskFilter.instance().filter(resultList);

        return resultList;
    }

    public enum TaskFilterType {
        ALL("allTask"), OWNER("myBussiness"), POOLED("todoBussiness"),CHECK("checkBusiness");

        private String messageKey;

        public String getMessageKey() {
            return messageKey;
        }

        TaskFilterType(String messageKey) {
            this.messageKey = messageKey;
        }
    }

    private TaskFilterType filterType = TaskFilterType.ALL;

    public TaskFilterType getFilterType() {
        return filterType;
    }

    public void setFilterTypeName(String typeName) {
        filterType = TaskFilterType.valueOf(typeName);
    }

    public String getFilterTypeName() {
        return filterType.name();
    }


    protected List<TaskInstanceAdapter> getTasksByType(TaskFilterType type) {

        List<TaskInstanceAdapter> result = new ArrayList<TaskInstanceAdapter>();
        if (TaskFilterType.ALL.equals(type)) {
            result.addAll(getAllTask());
        }else if (TaskFilterType.CHECK.equals(type)){
            for (TaskInstanceAdapter task : getAllTask()){
                if (task.getTaskDescription().isCheckTask()){
                    result.add(task);
                }
            }
        }else {
            for (TaskInstanceAdapter task : getAllTask()) {
                if (TaskFilterType.OWNER.equals(type) && task.isMyTask()) {
                    result.add(task);
                } else if (TaskFilterType.POOLED.equals(type) && !task.isMyTask()) {
                    result.add(task);
                }
            }
        }
        return result;
    }

    protected List<TaskInstanceAdapter> getCurTypeTasks() {
        return getTasksByType(filterType);
    }

    private List<FilterBusinessCategory> categories;

    public void clearCondition() {
        TaskFilter.instance().clearCondition();
        refreshResult();
    }

    @Override
    public void initTaskList(){
        TaskFilter.instance().clearCondition();
        super.initTaskList();
    }


    private FilterBusinessCategory selectCategory;

    private String selectDefineId;


    public FilterBusinessCategory getSelectCategory() {
        return selectCategory;
    }

    public void setSelectCategory(FilterBusinessCategory selectCategory) {
        this.selectCategory = selectCategory;
    }

    public String getSelectCategoryId() {
        if (selectCategory == null) {
            return null;
        } else {
            return selectCategory.getCategory().getId();
        }
    }

    public void setSelectCategoryId(String id) {
        if ((id == null) || id.trim().equals("")) {
            selectCategory = null;
        } else {
            for (FilterBusinessCategory category : getFilterCategorys()) {
                if (category.getCategory().getId().equals(id)) {
                    selectCategory = category;
                    return;
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

    public List<FilterBusinessCategory> getFilterCategorys() {
        if (categories == null) {


        }
        return categories;
    }

    public int getOwnerSize() {
        return getTasksByType(TaskFilterType.OWNER).size();
    }

    public int getPooledSize() {
        return getTasksByType(TaskFilterType.POOLED).size();
    }

    public int getCheckSize(){return getTasksByType(TaskFilterType.CHECK).size();}

    public boolean isEmptyTask() {
        return getCurTypeTasks().isEmpty();
    }

    public void clearDateFrom() {
        TaskFilter.instance().getSearchDateArea().setDateFrom(null);
        reset();
    }

    public void clearDateTo() {
        TaskFilter.instance().getSearchDateArea().setDateTo(null);
        reset();
    }

    public List<TaskInstanceAdapter> getTaskByKey(String key) {
        List<TaskInstanceAdapter> result = new ArrayList<TaskInstanceAdapter>();
        for (TaskInstanceAdapter task : getAllTask()) {
            if (task.getTaskDescription().getBusinessKey().equals(key)) {
                result.add(task);
            }
        }
        return result;
    }


}
