package com.dgsoft.house.owner.helper;

import com.dgsoft.common.system.RunParam;
import com.dgsoft.common.system.business.BusinessInstance;
import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.OwnerBusiness;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.log.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by cooper on 1/1/16.
 */
@Name("businessInfoProvider")
public class BusinessInfoProvider implements RestDataProvider{

    @RequestParameter
    private String id;

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In(create = true)
    private Map<String,String> messages;

    @Override
    public JSONObject getJsonData() {
        OwnerBusiness ob = ownerEntityLoader.getEntityManager().find(OwnerBusiness.class,id);
        if (ob == null){
            return null;
        }

        JSONObject resultObj = new JSONObject();

        try {

            resultObj.put("businessName", BusinessInstance.BusinessType.NORMAL_BIZ.equals(ob.getType()) ?  ob.getDefineName() : (ob.getDefineName() + messages.get(ob.getType().name())));
            resultObj.put("applyTime", ob.getApplyTime().getTime());
            resultObj.put("status",messages.get(ob.getStatus().name()));
            resultObj.put("statusKey",ob.getStatus().name());
            resultObj.put("complete",ob.isRecorded());
            resultObj.put("regTime", ob.getRegTime() == null ? Long.valueOf(0) : ob.getRegTime().getTime());
            resultObj.put("checked",ob.getCheckEmp() != null);
            resultObj.put("recordTime",ob.getRecordTime() == null ? Long.valueOf(0) : ob.getRecordTime().getTime());

            JSONArray jsonArray = new JSONArray();
            List<TaskOper> taskOpers = new ArrayList<TaskOper>(ob.getTaskOpers());
            Collections.sort(taskOpers, new Comparator<TaskOper>() {

                @Override
                public int compare(TaskOper o1, TaskOper o2) {
                    return o2.getOperTime().compareTo(o1.getOperTime());
                }
            });
            for(TaskOper taskOper: taskOpers){
                JSONObject operObject = new JSONObject();
                operObject.put("time",taskOper.getOperTime().getTime());
                operObject.put("empName",taskOper.getEmpName());
                if (taskOper.getOperType().isManager() ){
                    operObject.put("task",messages.get(taskOper.getOperType().name()));

                }else{
                    operObject.put("task",taskOper.getTaskName());
                }
                operObject.put("comments",taskOper.getComments());
                operObject.put("operTypeKey",taskOper.getOperType().name());
                operObject.put("description",taskOper.getTaskDescription());

                jsonArray.put(operObject);
            }
            resultObj.put("oper",jsonArray);
            return resultObj;

        } catch (JSONException e) {
            Logging.getLog(getClass()).error(e.getMessage(),e);
            return null;
        }
    }
}
