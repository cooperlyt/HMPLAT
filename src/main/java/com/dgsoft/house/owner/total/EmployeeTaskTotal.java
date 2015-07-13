package com.dgsoft.house.owner.total;

import com.dgsoft.house.owner.OwnerEntityLoader;
import com.dgsoft.house.owner.model.TaskOper;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;

import java.util.*;

/**
 * Created by cooper on 7/13/15.
 */
@Name("employeeTaskTotal")
public class EmployeeTaskTotal {

    @In(create = true)
    private OwnerEntityLoader ownerEntityLoader;

    @In
    private Credentials credentials;


    private Map<TaskOper.OperType,List<TaskOperTotalData>> curEmpData;


    public List<Map.Entry<TaskOper.OperType,List<TaskOperTotalData>>> getCurEmpChartData(){
        if (curEmpData == null){
            totalCurEmpData();
        }
        List<Map.Entry<TaskOper.OperType,List<TaskOperTotalData>>> result = new ArrayList<Map.Entry<TaskOper.OperType, List<TaskOperTotalData>>>(curEmpData.entrySet());

        Collections.sort(result, new Comparator<Map.Entry<TaskOper.OperType, List<TaskOperTotalData>>>() {
            @Override
            public int compare(Map.Entry<TaskOper.OperType, List<TaskOperTotalData>> o1, Map.Entry<TaskOper.OperType, List<TaskOperTotalData>> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        return result;
    }


    public void totalCurEmpData(){

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        List<TaskOperTotalData> result = ownerEntityLoader.getEntityManager().createQuery("select new com.dgsoft.house.owner.total.TaskOperTotalData(day(taskOper.operTime),count(taskOper.id),taskOper.operType) from TaskOper taskOper where taskOper.operType in (:operType) and taskOper.empCode = :empCode and year(taskOper.operTime) =year(CURRENT_DATE()) and month(taskOper.operTime) = month(CURRENT_DATE()) group by taskOper.operType, day(taskOper.operTime)", TaskOperTotalData.class)
                .setParameter("operType", new ArrayList<TaskOper.OperType>(EnumSet.of(TaskOper.OperType.BACK, TaskOper.OperType.CHECK_ACCEPT,TaskOper.OperType.CHECK_BACK, TaskOper.OperType.CREATE,TaskOper.OperType.NEXT)))
                .setParameter("empCode",credentials.getUsername()).getResultList();


        curEmpData = new HashMap<TaskOper.OperType, List<TaskOperTotalData>>();

        for (TaskOperTotalData data: result){
            List<TaskOperTotalData> vs = curEmpData.get(data.getOperType());
            if (vs == null){
                vs = new ArrayList<TaskOperTotalData>();
                curEmpData.put(data.getOperType(),vs);
            }
            vs.add(data);
        }


        for(TaskOper.OperType o: curEmpData.keySet()){

            List<TaskOperTotalData> data = curEmpData.get(o);
            Map<Integer,TaskOperTotalData> dayData = new HashMap<Integer, TaskOperTotalData>();
            for (TaskOperTotalData d: data){
                dayData.put(d.getTimeBlock(),d);
            }
            for(int i = 0 ; i < cal.get(Calendar.DAY_OF_MONTH); i ++){
                if (dayData.get(i + 1) == null){
                    curEmpData.get(o).add(new TaskOperTotalData(Integer.valueOf(i+1),Long.valueOf(0),o));
                }
            }
            Collections.sort(data);
        }


    }

}
