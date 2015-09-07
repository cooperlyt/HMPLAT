package com.dgsoft.house;

import com.dgsoft.common.DataFormat;

import java.util.Comparator;

/**
 * Created by cooper on 9/4/15.
 */
public class OrderComparator implements Comparator<String> {

    private static OrderComparator instance;

    public static OrderComparator getInstance(){
        if (instance == null){
            instance = new OrderComparator();
        }
        return instance;
    }

    @Override
    public int compare(String o1, String o2) {
        String number1 = o1.trim();
        String number2 = o2.trim();

        if (number1 == null){
            number1 = "";
        }
        if (number2 == null){
            number2 = "";
        }
        if (number1.equals(number2)){
            return 0;
        }
        if ((number1.equals("")) && (number2.equals(""))){
            return 0;
        }
        if (number2.equals("")){
            return 1;
        }
        if (number1.equals("")){
            return -1;
        }

        Integer i1 = DataFormat.strToInt(number1);
        Integer i2 = DataFormat.strToInt(number2);


        if ((i1 == null) && (i2 == null)){
            return 0;
        }
        if (i2 == null){
            return 1;
        }
        if (i1 == null){
            return -1;
        }

        return new Integer(i1).compareTo(i2);
    }
}

