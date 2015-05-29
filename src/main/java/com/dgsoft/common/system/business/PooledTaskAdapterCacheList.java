package com.dgsoft.common.system.business;

import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jbpm.taskmgmt.exe.TaskInstance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cooper on 5/25/15.
 */
@Name("pooledTaskAdapterCacheList")
@Scope(ScopeType.SESSION)
@AutoCreate
public class PooledTaskAdapterCacheList extends SystemTaskInstanceListCache{


    @Override
    protected Set<TaskInstance> searchTaskInstances() {
        List<TaskInstance> pooledTaskInstanceList = (List<TaskInstance>) Component.getInstance("org.jboss.seam.bpm.pooledTaskInstanceList");
        return new HashSet<TaskInstance>(pooledTaskInstanceList);
    }
}
