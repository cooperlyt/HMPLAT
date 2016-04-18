package com.dgsoft.common;

import org.jboss.seam.log.Logging;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by cooper on 4/18/16.
 */
public class TimeAreaHelper {

    private TimeArea timeArea;

    //private TimeArea.TimeShowType type;

    //private Long toSize;

    public TimeAreaHelper(TimeArea timeArea) {
        this.timeArea = timeArea;
        if (timeArea.getTimeShowType() == null){
            timeArea.setTimeShowType(TimeArea.TimeShowType.DATE_TIME);
        }
        //calcToSize();
        //type = timeArea.getTimeShowType();
    }

    public TimeArea getTimeArea() {
        return timeArea;
    }

    public void setTimeArea(TimeArea timeArea) {
        this.timeArea = timeArea;
    }

    public Long getToSize() {
        if (timeArea.getToTime() == null || timeArea.getFromTime() == null){
            return null;
        }

        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(timeArea.getFromTime());
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        c2.setTime(timeArea.getToTime());
        int[]  p1  =  {  c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH)  };
        int[]  p2  =  {  c2.get(Calendar.YEAR), c2.get(Calendar.MONTH), c2.get(Calendar.DAY_OF_MONTH)  };
        //type = timeArea.getTimeShowType();
        switch (timeArea.getTimeShowType()){

            case DATE_TIME:
                return null;
            case DAY:
                return (c2.getTimeInMillis()  -  c1.getTimeInMillis())  /  (24  *  3600  *  1000);

            case YEAR:
                return Long.valueOf(p2[0]  -  p1[0]);

            case MONTH:
                return Long.valueOf(p2[0]  *  12  +  p2[1]  -  p1[0]  *  12  -  p1[1]);

        }

        throw new IllegalArgumentException("type not define");
    }

    public void setToSize(Long toSize) {

        if(timeArea.getFromTime() == null){
            timeArea.setToTime(null);
        }

        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(timeArea.getFromTime());

        switch (timeArea.getTimeShowType()){

            case YEAR:
                c1.add(Calendar.YEAR,toSize.intValue());
                timeArea.setToTime(c1.getTime());
            case MONTH:
                c1.add(Calendar.MONTH,toSize.intValue());
                timeArea.setToTime(c1.getTime());
            case DAY:
                c1.add(Calendar.DATE,toSize.intValue());
                timeArea.setToTime(c1.getTime());
        }

       // timeArea.setToTime(null);
    }

    public void clearToDate(){
        timeArea.setToTime(null);
    }


}
