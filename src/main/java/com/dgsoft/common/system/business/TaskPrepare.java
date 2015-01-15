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
        return getTaskDescription(taskInstance.getId()).getTaskOperComponent().beginTask(taskInstance);
    }

    @BypassInterceptors
    public TaskDescription getTaskDescription(long taskId){
        TaskInstance targetTaskInstance = ManagedJbpmContext.instance().getTaskInstanceForUpdate(taskId);
        if (targetTaskInstance != null){
            String taskJSONDescription = targetTaskInstance.getDescription();
            Logging.getLog(this.getClass()).debug("task Description json str:" + taskJSONDescription);
            try {
                return new TaskDescription(new JSONObject(taskJSONDescription));
            } catch (JSONException e) {
                Logging.getLog(this.getClass()).error("jbpm process Define error task Description JSON ERROR", e);
                throw new ProcessDefineException("jbpm process Define error task Description JSON ERROR");
            }
        } else{
            Logging.getLog(this.getClass()).warn("taskInstance not found.");
            return null;
        }
    }
}
