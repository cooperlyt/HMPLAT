package com.dgsoft.common.system.business;

import com.dgsoft.common.SearchDateArea;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public SearchDateArea getSearchDateArea() {
        return searchDateArea;
    }

    public void setSearchDateArea(SearchDateArea searchDateArea) {
        this.searchDateArea = searchDateArea;
    }


    public List<SystemTaskInstanceListCache.TaskInstanceAdapter> filter(List<SystemTaskInstanceListCache.TaskInstanceAdapter> tasks){

        List<SystemTaskInstanceListCache.TaskInstanceAdapter> filterList = new ArrayList<SystemTaskInstanceListCache.TaskInstanceAdapter>();

        List<SystemTaskInstanceListCache.TaskInstanceAdapter> resultList = new ArrayList<SystemTaskInstanceListCache.TaskInstanceAdapter>(tasks);

        if (searchDateArea.getDateFrom() != null){
            for(SystemTaskInstanceListCache.TaskInstanceAdapter task: resultList){
                if (task.getTaskDescription().getCreateTime().compareTo(searchDateArea.getDateFrom()) >= 0 ){
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }

        if (searchDateArea.getSearchDateTo() != null){
            for(SystemTaskInstanceListCache.TaskInstanceAdapter task: resultList){
                if (task.getTaskDescription().getCreateTime().compareTo(searchDateArea.getSearchDateTo()) <= 0 ){
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }

        if ((getSearchKey() != null) && (!getSearchKey().trim().equals(""))){
            for(SystemTaskInstanceListCache.TaskInstanceAdapter task: resultList){
                if (task.getTaskDescription().getDescription().contains(getSearchKey()) ||
                        task.getTaskDescription().getBusinessKey().contains(getSearchKey())){
                    filterList.add(task);
                }
            }
            resultList.clear();
            resultList.addAll(filterList);
            filterList.clear();
        }




        switch (sortBy) {

            case BUSINESS_CODE:
                Collections.sort(resultList, new Comparator<SystemTaskInstanceListCache.TaskInstanceAdapter>() {
                    @Override
                    public int compare(SystemTaskInstanceListCache.TaskInstanceAdapter o1, SystemTaskInstanceListCache.TaskInstanceAdapter o2) {
                        return o1.getTaskDescription().getBusinessKey().compareTo(o2.getTaskDescription().getBusinessKey());
                    }
                });
                break;

            case BUSINESS_DEFINE:
                Collections.sort(resultList, new Comparator<SystemTaskInstanceListCache.TaskInstanceAdapter>() {
                    @Override
                    public int compare(SystemTaskInstanceListCache.TaskInstanceAdapter o1, SystemTaskInstanceListCache.TaskInstanceAdapter o2) {
                        return o1.getBusinessDefine().getId().compareTo(o2.getBusinessDefine().getId());
                    }
                });
                break;
            case TASK_NAME:
                Collections.sort(resultList, new Comparator<SystemTaskInstanceListCache.TaskInstanceAdapter>() {
                    @Override
                    public int compare(SystemTaskInstanceListCache.TaskInstanceAdapter o1, SystemTaskInstanceListCache.TaskInstanceAdapter o2) {
                        return o1.getTaskInstance().getName().compareTo(o2.getTaskInstance().getName());
                    }
                });

                break;
            case BUSINESS_CREATE_TIME:
                Collections.sort(resultList, new Comparator<SystemTaskInstanceListCache.TaskInstanceAdapter>() {
                    @Override
                    public int compare(SystemTaskInstanceListCache.TaskInstanceAdapter o1, SystemTaskInstanceListCache.TaskInstanceAdapter o2) {
                        return o1.getTaskDescription().getCreateTime().compareTo(o2.getTaskDescription().getCreateTime());
                    }
                });
                break;
            case TASK_TIME:
                Collections.sort(resultList, new Comparator<SystemTaskInstanceListCache.TaskInstanceAdapter>() {
                    @Override
                    public int compare(SystemTaskInstanceListCache.TaskInstanceAdapter o1, SystemTaskInstanceListCache.TaskInstanceAdapter o2) {
                        return o1.getTaskInstance().getCreate().compareTo(o2.getTaskInstance().getCreate());
                    }
                });
                break;

        }

        return resultList;
    }

}
