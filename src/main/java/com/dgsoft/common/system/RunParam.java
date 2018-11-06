package com.dgsoft.common.system;

import com.dgsoft.common.CalendarBean;
import com.dgsoft.common.DataFormat;
import com.dgsoft.common.system.model.SystemParam;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.framework.EntityQuery;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/8/13
 * Time: 3:52 PM
 */
@Name("runParam")
@AutoCreate
@Scope(ScopeType.APPLICATION)
@Startup
public class RunParam {

    private static final String PARAM_RUN_COUNT_ID = "system_run_count";

    private static final String PARAM_DAY_RUN_COUNT_ID = "system_day_run_count";

    public static final String PARAM_SERVER_START_TIME_ID="system_start_time";

    private int runCount;

    private long dayRunCount;

    private Date dayStartTime;

    private Map<String, SystemParam> systemParams = new HashMap<String, SystemParam>();

    @Create
    @Transactional
    public void load() {

        EntityQuery<SystemParam> systemParamList = (EntityQuery<SystemParam>) Component.getInstance("systemParamList", true, true);

        loadParams(systemParamList);

        SystemParam runCounterParam = systemParams.get(PARAM_RUN_COUNT_ID);

        SystemParam startTime = systemParams.get(PARAM_SERVER_START_TIME_ID);

        SystemParam dayRunCountParam = systemParams.get(PARAM_DAY_RUN_COUNT_ID);

        if (startTime == null || !CalendarBean.isSameDayDate(new Date(Long.parseLong(startTime.getValue())), new Date())){
            dayRunCount = 1;
            if (dayRunCountParam != null){
                dayRunCountParam.setValue("1");
            }

        }else {
            if (dayRunCountParam == null){
                dayRunCount = 1;
            }else{
                dayRunCount = Long.parseLong(dayRunCountParam.getValue()) + 1;
                dayRunCountParam.setValue(String.valueOf(dayRunCount));
            }
        }

        if (dayRunCountParam == null){
            systemParamList.getEntityManager().persist(new SystemParam(PARAM_DAY_RUN_COUNT_ID,SystemParam.ParamType.INTEGER,"1"));
        }

        dayStartTime = new Date();

        if (startTime == null){
            systemParamList.getEntityManager().persist(new SystemParam(PARAM_SERVER_START_TIME_ID,SystemParam.ParamType.INTEGER,String.valueOf(dayStartTime.getTime())));
        }else {
            startTime.setValue(String.valueOf(dayStartTime.getTime()));
        }

        if (runCounterParam != null){
            runCount = Integer.valueOf(runCounterParam.getValue());
            runCount++;
            runCounterParam.setValue(String.valueOf(runCount));
        }else{
            runCounterParam = new SystemParam(PARAM_RUN_COUNT_ID,SystemParam.ParamType.INTEGER,"1");
            runCount = 1;
            systemParamList.getEntityManager().persist(runCounterParam);
        }
        systemParamList.getEntityManager().flush();
    }

    private void loadParams(EntityQuery<SystemParam> systemParamList){
        systemParams.clear();

        for (SystemParam param : systemParamList.getResultList()) {
            systemParams.put(param.getId(), param);
        }

    }

    public void refresh(){
        loadParams((EntityQuery<SystemParam>) Component.getInstance("systemParamList", true, true));
    }



    public int getIntParamValue(String name){
        SystemParam systemParam = systemParams.get(name);
        if (systemParam == null){
            throw new IllegalArgumentException("not have system Param:" + name);
        }else if (!systemParam.getType().equals(SystemParam.ParamType.INTEGER)){
            throw new IllegalArgumentException("system Param:" + name + " type not a Integer");
        }else{
            return Integer.parseInt(systemParam.getValue());
        }
    }

    public float getFloatParamValue(String name){
        SystemParam systemParam = systemParams.get(name);
        if (systemParam == null){
            throw new IllegalArgumentException("not have system Param:" + name);
        }else if (!systemParam.getType().equals(SystemParam.ParamType.FLOAT)){
            throw new IllegalArgumentException("system Param:" + name + " type not a FLOAT");
        }else{
            return Float.parseFloat(systemParam.getValue());
        }
    }

    public String getStringParamValue(String name){
        SystemParam systemParam = systemParams.get(name);
        if (systemParam == null){
            throw new IllegalArgumentException("not have system Param:" + name);
        }else if (!systemParam.getType().equals(SystemParam.ParamType.STRING)){
            throw new IllegalArgumentException("system Param:" + name + " type not a STRING");
        }else{
            return systemParam.getValue();
        }
    }


    public double getDoubleParamValue(String name){
        SystemParam systemParam = systemParams.get(name);
        if (systemParam == null){
            throw new IllegalArgumentException("not have system Param:" + name);
        }else if (!systemParam.getType().equals(SystemParam.ParamType.DOUBLE)){
            throw new IllegalArgumentException("system Param:" + name + " type not a DOUBLE");
        }else{
            return Double.parseDouble(systemParam.getValue());
        }
    }

    public boolean getBooleanParamValue(String name){
        SystemParam systemParam = systemParams.get(name);
        if (systemParam == null){
            throw new IllegalArgumentException("not have system Param:" + name);
        }else if (!systemParam.getType().equals(SystemParam.ParamType.BOOLEAN)){
            throw new IllegalArgumentException("system Param:" + name + " type not a BOOLEAN");
        }else{
            return systemParam.getValue().trim().toLowerCase().equals("true") || systemParam.getValue().trim().equals('1');
        }
    }

    public int getRunCount() {
        return runCount;
    }

    public long getDayRunCount() {
        return dayRunCount;
    }

    public void setDayRunCount(long count){
        this.dayRunCount = count;
    }

    public Date getDayStartTime() {
        return dayStartTime;
    }

    public void setDayStartTime(Date date){
        this.dayStartTime = date;
    }


    public static RunParam instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (RunParam) Component.getInstance(RunParam.class, ScopeType.APPLICATION);
    }

}
