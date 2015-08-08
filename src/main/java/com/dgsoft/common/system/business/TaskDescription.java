package com.dgsoft.common.system.business;

import com.dgsoft.common.exception.ProcessDefineException;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.bpm.ManagedJbpmContext;
import org.jboss.seam.log.Logging;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import static org.jboss.seam.annotations.Install.BUILT_IN;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 10/28/13
 * Time: 5:24 PM
 */
@Scope(ScopeType.STATELESS)
@Name("taskDescription")
@BypassInterceptors
@Install(precedence=BUILT_IN, dependencies="org.jboss.seam.bpm.jbpm")
public class TaskDescription {



    public static TaskDescription getTaskDescription(long taskId){
        TaskInstance targetTaskInstance = ManagedJbpmContext.instance().getTaskInstanceForUpdate(taskId);
        if (targetTaskInstance != null){
            String taskJSONDescription = targetTaskInstance.getDescription();
            Logging.getLog(TaskDescription.class).debug("task Description json str:" + taskJSONDescription);
            try {
                return new TaskDescription(new JSONObject(taskJSONDescription));
            } catch (JSONException e) {
                Logging.getLog(TaskDescription.class).error("jbpm process Define error task Description JSON ERROR", e);
                throw new ProcessDefineException("jbpm process Define error task Description JSON ERROR");
            }
        } else{
            Logging.getLog(TaskDescription.class).warn("taskInstance not found.");
            return null;
        }
    }


    @Unwrap
    public TaskDescription getTaskDescription(){
        return getTaskDescription(org.jboss.seam.bpm.TaskInstance.instance().getId());
    }

    //{"description":"","operPage":"/func/erp/biz/custom/OrderPay.xhtml"}

    private static final String DEFAULT_TASK_OPERPAGE = "/business/taskOperator/AutoTaskOperate.xhtml";

    private JSONObject jsonObject;

    public TaskDescription() {

    }

    public TaskDescription(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getTaskOperComponentName(){

        String result = null;
        try {
            result = jsonObject.getString("operComponent");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).warn("TaskDescription get task OperationPage fail",e);
        }
        return result;
    }

    public TaskOperComponent getTaskOperComponent(){
        String componentName = getTaskOperComponentName();
        if ((componentName == null) || "".equals(componentName.trim())){
            return null;
        }
        return (TaskOperComponent) Component.getInstance(componentName,true,true);
    }

    public boolean isCheckTask(){
        try {
            return jsonObject.getBoolean("isCheck");
        } catch (JSONException e) {
            //Logging.getLog(this.getClass()).warn("TaskDescription get task OperationPage fail",e);
            return false;
        }
    }

    public String getTaskOperationPage() {
        String result = null;
        try {
            result = jsonObject.getString("operPage");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).warn("TaskDescription get task OperationPage fail",e);
        }
        if ((result == null) || result.trim().equals("")){
            result = DEFAULT_TASK_OPERPAGE;
        }
        return result;
    }

    public String getDescription(){

        try {
            return jsonObject.getString("description");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).warn("TaskDescription get task Description fail",e);
            return "";
        }
    }

    public String getBusinessKey(){
        try {
            return jsonObject.getString("businessKey");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).error("businessKey is null", e);
            throw new IllegalArgumentException("businessKey is null", e);
        }
    }


    public String getBusinessDefineKey(){
        try {
            return jsonObject.getString("businessDefineKey");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).error("businessDefineName is null", e);
            throw new IllegalArgumentException("businessDefineName is null", e);
        }
    }

    public String getBusinessName(){
        try {
            return jsonObject.getString("businessName");
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).error("businessName is null", e);
            //throw new IllegalArgumentException("businessName is null", e);
            return null;
        }
    }

    public String getValue(String key){

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).warn("TaskDescription get task Description fail",e);
            return null;
        }
    }

    public Date getCreateTime(){
        try {

            return new Date(jsonObject.getLong("createTime"));
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).error("createTime is null", e);
            throw new IllegalArgumentException("createTime is null", e);
        }
    }

}
