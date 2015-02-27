package com.dgsoft.common.system.business;

import com.dgsoft.common.exception.ProcessDefineException;
import com.dgsoft.common.system.action.BusinessDefineHome;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.log.Logging;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cooper on 1/14/15.
 */

@Name("taskPrepare")
public class TaskPrepare {

    @In(create = true)
    private TaskPublish taskPublish;

    @In
    private TaskInstance taskInstance;

    @In(create = true)
    private BusinessDefineHome businessDefineHome;


    @BeginTask(flushMode = FlushModeType.MANUAL)
    public String beginTask() {

        Logging.getLog(getClass()).debug(taskInstance.getVariables().size());
        businessDefineHome.setId(taskInstance.getVariable("businessDefineId"));
        businessDefineHome.setTaskName(taskInstance.getName());
        taskPublish.setTaskNameAndPublish(taskInstance.getName());
        return TaskDescription.getTaskDescription(taskInstance.getId()).getTaskOperComponent().beginTask(taskInstance);
    }

}