package com.dgsoft.common;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cooper on 4/18/16.
 */
public class TimeAreaHelper {

    private TimeArea timeArea;

    private TimeArea.TimeShowType type;

    private Long toSize;

    public TimeAreaHelper(TimeArea timeArea) {
        this.timeArea = timeArea;
        if (timeArea.getTimeShowType() == null){
            timeArea.setTimeShowType(TimeArea.TimeShowType.DATE_TIME);
        }
        calcToSize();
        type = timeArea.getTimeShowType();
    }

    public TimeArea getTimeArea() {
        return timeArea;
    }

    public void setTimeArea(TimeArea timeArea) {
        this.timeArea = timeArea;
    }

    public Long getToSize() {
        return toSize;
    }

    public void setToSize(Long toSize) {
        this.toSize = toSize;
    }

    public void fromTimeChange(){
        timeArea.setToTime(calcToTime(timeArea.getTimeShowType(),toSize));
    }

    public void toSizeChange(){
        if (TimeArea.TimeShowType.DATE_TIME.equals(timeArea.getTimeShowType())){
            type = TimeArea.TimeShowType.DAY;
        }else
            type = timeArea.getTimeShowType();

        timeArea.setToTime(calcToTime(timeArea.getTimeShowType(),toSize));
    }

    public void typeChange(){
        if (!timeArea.getTimeShowType().equals(type)){
            Long beforDay = calcBeforDay();
            timeArea.setToTime(calcToTime(TimeArea.TimeShowType.DAY,beforDay));
            calcToSize();
        }
    }

    private Long calcBeforDay(){

        if (type == null || timeArea.getFromTime() == null){
            return null;
        }
        if (type.equals(TimeArea.TimeShowType.DATE_TIME)){
            if (timeArea.getToTime() == null){
                return null;
            }else{
               return (timeArea.getToTime().getTime() - timeArea.getFromTime().getTime()) /  (24  *  3600  *  1000);
            }
        }else{

            Date toDate = calcToTime(type,toSize);
            if (toDate == null){
                return null;
            }else{
                return (toDate.getTime() - timeArea.getFromTime().getTime())/  (24  *  3600  *  1000);
            }

        }

    }

    public void toTimeChange(){
        if (TimeArea.TimeShowType.DATE_TIME.equals(timeArea.getTimeShowType())){
            if (timeArea.getFromTime() != null || timeArea.getToTime() != null){
                toSize = (timeArea.getToTime().getTime() - timeArea.getFromTime().getTime()) /  (24  *  3600  *  1000);
                type = TimeArea.TimeShowType.DAY;
            }else{
                toSize = null;
                type = null;
            }
        }
    }

    private void calcToSize(){
        if (timeArea.getToTime() == null || timeArea.getFromTime() == null){
            toSize = null;
            return;
        }

        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(timeArea.getFromTime());
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        c2.setTime(timeArea.getToTime());
        int[]  p1  =  {  c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)  };
        int[]  p2  =  {  c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)  };
        type = timeArea.getTimeShowType();
        switch (timeArea.getTimeShowType()){

            case DATE_TIME:
                type = TimeArea.TimeShowType.DAY;
            case DAY:
                toSize =  (c2.getTimeInMillis()  -  c1.getTimeInMillis())  /  (24  *  3600  *  1000);
                break;
            case YEAR:
                toSize =  Long.valueOf(p2[0]  -  p1[0]);
                break;
            case MONTH:

                toSize =  Long.valueOf(p2[0]  *  12  +  p2[1]  -  p1[0]  *  12  -  p1[1]);
                break;

        }

    }

    private Date calcToTime(TimeArea.TimeShowType calcType, Long toSize){
        if (TimeArea.TimeShowType.DATE_TIME.equals(calcType)){
            return timeArea.getToTime();
        }
        if(timeArea.getFromTime() == null || toSize == null){
            return null;
        }

        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(timeArea.getFromTime());

        switch (calcType){

            case YEAR:
                c1.add(Calendar.YEAR,toSize.intValue());
                return c1.getTime();
            case MONTH:
                c1.add(Calendar.MONTH,toSize.intValue());
                return  c1.getTime();
            case DAY:
                c1.add(Calendar.DATE,toSize.intValue());
                return c1.getTime();
        }
        throw new IllegalArgumentException("show type not define:" + timeArea.getTimeShowType());

    }




}
