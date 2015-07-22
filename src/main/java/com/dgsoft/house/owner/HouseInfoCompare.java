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



    public List<String> compare(HouseInfo srcHouseInfo, HouseInfo descHouseInfo, Map<String,String> titles, boolean area){
        List<String> result = new ArrayList<String>();
        for(Method m :HouseInfo.class.getDeclaredMethods()){
            try {
               if (! m.invoke(srcHouseInfo).equals(m.invoke(descHouseInfo))){
                    titles.get("house_field_" + m.getName().replaceFirst("^get" , "").trim());

                   //+ "%" +
               }
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }
        return result;

    }

    public static void main(String[] args){
        System.out.println("--------");
       for(Method f :HouseInfo.class.getDeclaredMethods()){
           System.out.println(f.getName());
       }
        System.out.println("--------");
    }


}
