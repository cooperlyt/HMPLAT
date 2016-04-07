package com.dgsoft.house.owner.business;

import com.dgsoft.common.system.business.AllTaskAdapterCacheList;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

import java.util.List;

/**
 * Created by cooper on 7/21/15.
 */
@Name("taskCompletedOper")
public class TaskCompletedOper {


    @In
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private AllTaskAdapterCacheList allTaskAdapterCacheList;

    private List<AllTaskAdapterCacheList.TaskInstanceAdapter> tasks;

    public List<AllTaskAdapterCacheList.TaskInstanceAdapter> getTasks(){
        if (tasks == null){
            allTaskAdapterCacheList.refresh();
            tasks =  allTaskAdapterCacheList.getTaskByKey(ownerBusinessHome.getInstance().getId());
        }
        return tasks;
    }

    public boolean isHaveNextTask(){
        return !getTasks().isEmpty();
    }
}
