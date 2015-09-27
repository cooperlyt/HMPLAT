package com.dgsoft.common.system;

import com.dgsoft.common.CalendarBean;
import com.dgsoft.common.DataFormat;
import com.dgsoft.common.system.model.NumberPool;
import com.dgsoft.common.system.model.SystemParam;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.log.Logging;
import org.jbpm.JbpmContext;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 6/8/13
 * Time: 4:56 PM
 */
@Name("numberBuilder")
@Scope(ScopeType.APPLICATION)
@AutoCreate
@Startup
@Synchronized
public class NumberBuilder {

    private static final int DEFAULT_STEP = 10;

    private class Numbers {
        long maxNumber;
        long nextNumber;

        Numbers() {
            maxNumber = DEFAULT_STEP + 1;
            nextNumber = 1;
        }

        Numbers(long nextNumber, long maxNumber){
            this.maxNumber = maxNumber;
            this.nextNumber = nextNumber;
        }

    }

    private Map<String, Numbers> numbers = new HashMap<String, Numbers>();


    private EntityManager getSystemEntityManger(){
        return (EntityManager) Component.getInstance("systemEntityManager", ScopeType.APPLICATION, true);
    }

    @Destroy
    public void onDestroy(){
//        EntityManager entityManager = getSystemEntityManger();
//        for(Map.Entry<String, Numbers> entry: numbers.entrySet()){
//            NumberPool numberPool = entityManager.find(NumberPool.class,entry.getKey());
//            numberPool.setNumber(entry.getValue().nextNumber);
//        }
//        entityManager.flush();
    }

    private Date dayNumberDate = DataFormat.halfTime(new Date());

    private Map<String,Long> dayNumbers = new HashMap<String, Long>();

    @Deprecated
    public String getDayNumber(String type){

        if (dayNumberDate.getTime() != DataFormat.halfTime(new Date()).getTime()){
            dayNumbers.clear();
            dayNumberDate =DataFormat.halfTime(new Date());
        }

        Long result = dayNumbers.get(type);
        if (result == null){
            result = new Long(1);
        }else{
            result++;
        }
        dayNumbers.put(type,result);
        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());

        return datePart + "-" + result;
    }

    @In
    private RunParam runParam;

    @Transactional
    public String getNumber(String type, int length){
        long number = getNumber(type);
        StringBuffer result =  new StringBuffer(String.valueOf(number));
        while (result.length() < length){
            result.insert(0,'0');
        }
        return result.toString();
    }

    @Transactional
    public long getNumber(String type) {

        Numbers result = numbers.get(type);
        long resultNumber;
        if (result == null) {

            EntityManager entityManager = getSystemEntityManger();
            NumberPool numberPool = entityManager.find(NumberPool.class,type);
            Numbers newNumber;


            if (numberPool == null){
                newNumber = new Numbers();
                entityManager.persist(new NumberPool(type,newNumber.maxNumber,DEFAULT_STEP));
                entityManager.flush();
                Logging.getLog(this.getClass()).debug("flush entityManager from numberBuild");
            }else{
                newNumber = new Numbers(numberPool.getNumber(),
                        numberPool.getPoolSize() + numberPool.getNumber());
                numberPool.setNumber(newNumber.maxNumber);
                entityManager.flush();
                Logging.getLog(this.getClass()).debug("flush entityManager from numberBuild");
            }
            numbers.put(type,newNumber);
            resultNumber = newNumber.nextNumber;
            newNumber.nextNumber = newNumber.nextNumber + 1;

        } else {
            resultNumber = result.nextNumber;
            result.nextNumber = result.nextNumber + 1;
            if (result.nextNumber >= result.maxNumber){
                EntityManager entityManager = getSystemEntityManger();
                NumberPool pool = entityManager.find(NumberPool.class,type);
                pool.setNumber(result.nextNumber + pool.getPoolSize());
                entityManager.flush();
                Logging.getLog(this.getClass()).debug("flush entityManager from numberBuild");
                result.maxNumber = pool.getNumber();
            }
        }
        return resultNumber;
    }

    @Deprecated
    public synchronized String getSampleNumber(String type) {
        return runParam.getRunCount() + "-" + getNumber(type);
    }

    private Map<String,Long> dayNumberMap = new HashMap<String, Long>();

    @Transactional
    public synchronized String getDateNumber(String type) {

        if(!CalendarBean.isSameDayDate(new Date(),runParam.getDayStartTime())){
            dayNumberMap.clear();
            runParam.setDayStartTime(new Date());
            runParam.setDayRunCount(1);
            EntityManager entityManager = getSystemEntityManger();
            SystemParam timeParam = entityManager.find(SystemParam.class, RunParam.PARAM_SERVER_START_TIME_ID);
            timeParam.setValue(String.valueOf(new Date().getTime()));
            entityManager.flush();
        }



        Long result = dayNumberMap.get(type);
        if (result == null){
            result = Long.valueOf(1);
        }else{
            result = result + 1;
        }
        dayNumberMap.put(type,result);
        SimpleDateFormat numberDateformat = new SimpleDateFormat("yyyyMMdd");
        String datePart = numberDateformat.format(new Date());
        String runCountParam;
        if (runParam.getDayRunCount() < 10){
            runCountParam = "0" + runParam.getDayRunCount();
        }else{
            runCountParam = String.valueOf(runParam.getDayRunCount());
        }

        return datePart+runCountParam+result;
    }

    public static NumberBuilder instance()
    {
        if ( !Contexts.isEventContextActive() )
        {
            throw new IllegalStateException("no active event context");
        }
        return (NumberBuilder) Component.getInstance(NumberBuilder.class, ScopeType.APPLICATION);
    }

}
