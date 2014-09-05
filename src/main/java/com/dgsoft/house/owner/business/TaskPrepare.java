package com.dgsoft.house.owner.business;

import com.dgsoft.common.exception.ProcessDefineException;
import com.dgsoft.common.system.action.BusinessDefineHome;
import com.dgsoft.common.system.business.TaskDescription;
import com.dgsoft.common.system.business.TaskPublish;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.action.OwnerBusinessHome;
import com.dgsoft.house.owner.model.OwnerBusiness;
import org.jboss.seam.Component;
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
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 10/28/13
 * Time: 5:21 PM
 */
@Name("taskPrepare")
public class TaskPrepare {

    @In(create = true)
    private BusinessDefineHome businessDefineHome;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private TaskPublish taskPublish;

    @In(create = true)
    private OwnerBusinessHome ownerBusinessHome;

    @In
    private TaskInstance taskInstance;

    @BeginTask(flushMode = FlushModeType.MANUAL)
    public String beginTask() {
        OwnerBusiness ob = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class, taskInstance.getProcessInstance().getKey());
        businessDefineHome.setId(ob.getDefineId());
        ownerBusinessHome.setId(ob.getId());


        taskPublish.setTaskNameAndPublish(taskInstance.getName());
        return getTaskDescription(taskInstance.getId()).getTaskOperationPage();
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
