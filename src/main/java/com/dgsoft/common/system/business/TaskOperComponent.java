package com.dgsoft.common.system.business;

import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Created by cooper on 1/14/15.
 */
public interface TaskOperComponent {

    public abstract String beginTask(TaskInstance taskInstance);

}
