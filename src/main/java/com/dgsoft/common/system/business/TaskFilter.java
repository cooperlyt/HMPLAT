package com.dgsoft.common.system.business;

import com.dgsoft.common.SearchDateArea;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Logging;

import java.util.*;


/**
 * Created by cooper on 5/26/15.
 */
@Name("taskFilter")
@Scope(ScopeType.SESSION)
@AutoCreate
public class TaskFilter {

    public enum SortField{
        BUSINESS_CODE, BUSINESS_DEFINE, TASK_NAME, BUSINESS_CREATE_TIME, TASK_TIME
    }

    private SortField sortBy = SortField.TASK_TIME;

    private boolean desc;

    private String searchKey;

    private SearchDateArea searchDateArea = new SearchDateArea();

    public SortField[] getSortFields(){
        return SortField.values();
    }

    public SortField getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortField sortBy) {
        this.sortBy = sortBy;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    private boolean createDate = true;

    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }

    public boolean isCreateDate() {
        return createDate;
    }

    public void setCreateDate(boolean createDate) {
        this.createDate = createDate;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public List<AllTaskAdapterCacheList.TaskInstanceAdapter> filter(List<AllTaskAdapterCacheList.TaskInstanceAdapter> tasks){

        List<AllTaskAdapterCacheList.TaskInstanceAdapter> filterList = new ArrayList<AllTaskAdapterCacheList.TaskInstanceAdapter>();

        List<AllTaskAdapterCacheList.TaskInstanceAdapter> resultList = new ArrayList<AllTaskAdapterCacheList.TaskInstanceAdapter>(tasks);

        if (searchDateArea.getDateFrom() != null){
            for(AllTaskAdapterCacheList.TaskInstanceAdapter task: resultList){
                Logging.getLog(getClass()).debug("createDate:" + createDate + "|searchDateArea from:" + searchDateArea.getDateFrom());
                if (createDate) {
                    if (task.getTaskDescription().getCreateTime().compareTo(searchDateArea.getDateFrom()) >= 0) {
                        filterList.add(task);
                    }
                }else{
                    if (task.getTaskInstance().getCreate().compareTo(searchDateArea.getDateFrom()) >= 0) {
                        filterList.add(task);
                    }
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }

        if (searchDateArea.getSearchDateTo() != null){
            for(AllTaskAdapterCacheList.TaskInstanceAdapter task: resultList){
                Logging.getLog(getClass()).debug("createDate:" + createDate + "|searchDateArea from:" + searchDateArea.getSearchDateTo());
                if (createDate) {
                    if (task.getTaskDescription().getCreateTime().compareTo(searchDateArea.getSearchDateTo()) <= 0) {
                        filterList.add(task);
                    }
                }else{
                    if (task.getTaskInstance().getCreate().compareTo(searchDateArea.getSearchDateTo()) <= 0) {
                        filterList.add(task);
                    }
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }

        if ((getSearchKey() != null) && (!getSearchKey().trim().equals(""))){
            for(AllTaskAdapterCacheList.TaskInstanceAdapter task: resultList){
                if (task.getTaskDescription().getDescription().toUpperCase().contains(getSearchKey().toUpperCase()) ||
                        task.getTaskDescription().getBusinessKey().toUpperCase().contains(getSearchKey().toUpperCase())){
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }




        switch (sortBy) {

            case BUSINESS_CODE:
                Collections.sort(resultList, new Comparator<AllTaskAdapterCacheList.TaskInstanceAdapter>() {
                    @Override
                    public int compare(AllTaskAdapterCacheList.TaskInstanceAdapter o1, AllTaskAdapterCacheList.TaskInstanceAdapter o2) {
                        if (desc){
                            return o2.getTaskDescription().getBusinessKey().compareTo(o1.getTaskDescription().getBusinessKey());
                        }else
                            return o1.getTaskDescription().getBusinessKey().compareTo(o2.getTaskDescription().getBusinessKey());

                    }
                });
                break;

            case BUSINESS_DEFINE:
                Collections.sort(resultList, new Comparator<AllTaskAdapterCacheList.TaskInstanceAdapter>() {
                    @Override
                    public int compare(AllTaskAdapterCacheList.TaskInstanceAdapter o1, AllTaskAdapterCacheList.TaskInstanceAdapter o2) {
                        if (desc){
                            return o2.getBusinessDefine().getId().compareTo(o1.getBusinessDefine().getId());
                        }else{
                            return o1.getBusinessDefine().getId().compareTo(o2.getBusinessDefine().getId());
                        }

                    }
                });
                break;
            case TASK_NAME:
                Collections.sort(resultList, new Comparator<AllTaskAdapterCacheList.TaskInstanceAdapter>() {
                    @Override
                    public int compare(AllTaskAdapterCacheList.TaskInstanceAdapter o1, AllTaskAdapterCacheList.TaskInstanceAdapter o2) {
                        if (desc){
                            return o2.getTaskInstance().getName().compareTo(o1.getTaskInstance().getName());
                        }else{
                            return o1.getTaskInstance().getName().compareTo(o2.getTaskInstance().getName());
                        }

                    }
                });

                break;
            case BUSINESS_CREATE_TIME:
                Collections.sort(resultList, new Comparator<AllTaskAdapterCacheList.TaskInstanceAdapter>() {
                    @Override
                    public int compare(AllTaskAdapterCacheList.TaskInstanceAdapter o1, AllTaskAdapterCacheList.TaskInstanceAdapter o2) {
                        if (desc){
                            return o1.getTaskDescription().getCreateTime().compareTo(o2.getTaskDescription().getCreateTime());
                        }else

                            return o2.getTaskDescription().getCreateTime().compareTo(o1.getTaskDescription().getCreateTime());
                    }
                });
                break;
            case TASK_TIME:
                Collections.sort(resultList, new Comparator<AllTaskAdapterCacheList.TaskInstanceAdapter>() {
                    @Override
                    public int compare(AllTaskAdapterCacheList.TaskInstanceAdapter o1, AllTaskAdapterCacheList.TaskInstanceAdapter o2) {
                        if (desc){
                            return o1.getTaskInstance().getCreate().compareTo(o2.getTaskInstance().getCreate());

                        }else
                            return o2.getTaskInstance().getCreate().compareTo(o1.getTaskInstance().getCreate());
                    }
                });
                break;

        }

        return resultList;
    }


    public void clearCondition(){
        getSearchDateArea().setDateTo(null);
        getSearchDateArea().setDateFrom(null);
        setSearchKey(null);
    }


}
