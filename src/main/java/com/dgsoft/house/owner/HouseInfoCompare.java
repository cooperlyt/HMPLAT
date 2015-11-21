package com.dgsoft.house.owner;

import com.dgsoft.house.BuildInfo;
import com.dgsoft.house.HouseInfo;
import com.dgsoft.house.ProjectInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

    private static final String[] WORD_METHOD_NAME = {

     "getHouseType",

     "getUseType",

     "getKnotSize",

     "getEastWall",

     "getWestWall",

     "getSouthWall",

     "getNorthWall",

     "getDirection",

     "getBuildSize", "getBuildType"
    };

    public static class ChangeData {

        private String method;

        private Object value1;

        private Object value2;

        public ChangeData(String method, Object value1, Object value2) {
            this.method = method;
            this.value1 = value1;
            this.value2 = value2;
        }

        private Object getValue(){
            if (value1 != null){
                return value1;
            }
            return value2;
        }

        public boolean isDate(){
            if (getValue() != null){
                return getValue() instanceof Date;
            }
            return false;
        }

        public boolean isBoolean(){
            if (getValue() != null){
                return getValue() instanceof Boolean;
            }
            return false;
        }

        public boolean isWord(){
            return Arrays.asList(WORD_METHOD_NAME).contains(method);
        }


        public boolean isEnum(){
            if (getValue() != null){
                return getValue() instanceof Enum;
            }
            return false;
        }

        public boolean isString(){
            return !isDate() && !isBoolean() && !isWord() && !isEnum();
        }

        public String getMedthodKey(){
            return "House_" + getField  ();
        }

        public String getMethod() {
            return method;
        }

        public String getField() {
            return method.replaceFirst("^get", "").replaceFirst("^is", "").trim();
        }

        public Object getValue1() {
            return value1;
        }

        public Object getValue2() {
            return value2;
        }
    }


    public static List<ChangeData> compare(HouseInfo srcHouseInfo, HouseInfo descHouseInfo,boolean compareArea) {
        if (descHouseInfo == null || descHouseInfo == null){
            return new ArrayList<ChangeData>(0);
        }
        List<ChangeData> result = new ArrayList<ChangeData>();
        for (Method m : HouseInfo.class.getMethods()) {
            if (!"getMapTime".equals(m.getName()) && (compareArea || !Arrays.asList(AREA_METHOD_NAME).contains(m.getName()) ))
                try {
                    Object srcValue = m.invoke(srcHouseInfo);
                    Object descValue = m.invoke(descHouseInfo);
                    if (srcValue != null) {
                        if (!srcValue.equals(descValue)) {
                            result.add(new ChangeData(m.getName(), srcValue, descValue));
                        }
                    } else if (descValue != null) {
                        result.add(new ChangeData(m.getName(), srcValue, descValue));
                    }

                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                } catch (InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
        }
        if (!result.isEmpty()){
            result.add(new ChangeData("getMapTime", srcHouseInfo.getMapTime(), descHouseInfo.getMapTime()));
        }
        return result;

    }

    public static List<ChangeData> compare(ProjectInfo projectInfo1, ProjectInfo projectInfo2){

        return new ArrayList<ChangeData>(0);
    }

    public static List<ChangeData> compare(BuildInfo buildInfo1, BuildInfo buildInfo2){

        return new ArrayList<ChangeData>(0);
    }

}
