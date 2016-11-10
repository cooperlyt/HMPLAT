package com.dgsoft.common;

import java.util.Date;

/**
 * Created by cooper on 4/18/16.
 */
public interface TimeArea {

    public enum TimeShowType{
        DATE_TIME("-"),YEAR("年"),MONTH("月"),DAY("天");

        private String label;

        public String getLabel() {
            return label;
        }

        TimeShowType(String label) {
            this.label = label;
        }
    }



    TimeShowType getTimeShowType();

    void setTimeShowType(TimeShowType timeShowType);


    Date getFromTime();

    void setFromTime(Date date);

    Date getToTime();

    void setToTime(Date date);


}
