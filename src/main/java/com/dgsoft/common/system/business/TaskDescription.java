package com.dgsoft.common.system.business;

import org.jboss.seam.Component;
import org.jboss.seam.log.Logging;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 10/28/13
 * Time: 5:24 PM
 */
public class TaskDescription {

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
            Logging.getLog(this.getClass()).warn("TaskDescription get task OperationPage fail",e);
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

    public String getValue(String key){

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            Logging.getLog(this.getClass()).warn("TaskDescription get task Description fail",e);
            return null;
        }
    }

}
