package com.dgsoft.house.owner;

import com.dgsoft.house.HouseInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cooper on 7/22/15.
 */
public class HouseInfoCompare {

    private static final String[] AREA_METHOD_NAME = {
            "getHouseArea",
            "getUseArea",
            "getCommArea",
            "getShineArea",
            "getLoftArea",
            "getCommParam"
    };

    public static class ChangeData{

        private String method;

        private Object value1;

        private Object value2;

        public ChangeData(String method, Object value1, Object value2) {
            this.method = method;
            this.value1 = value1;
            this.value2 = value2;
        }

        public String getMethod() {
            return method;
        }

        public String getField(){
            return method.replaceFirst("^get" , "").replaceFirst("^is","").trim();
        }

        public Object getValue1() {
            return value1;
        }

        public Object getValue2() {
            return value2;
        }
    }


    public static List<ChangeData> compare(HouseInfo srcHouseInfo, HouseInfo descHouseInfo){
        List<ChangeData> result = new ArrayList<ChangeData>();
        for(Method m :HouseInfo.class.getDeclaredMethods()){
            try {
               if (! m.invoke(srcHouseInfo).equals(m.invoke(descHouseInfo))){
                  result.add(new ChangeData(m.getName(),m.invoke(srcHouseInfo),m.invoke(descHouseInfo)));
               }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return result;

    }



}
