package com.dgsoft.house;

import com.dgsoft.common.system.RunParam;

import java.util.Comparator;

/**
 * Created by cooper on 8/6/16.
 */
public class AutoGridMapComparator {

    public static Comparator<String> getUnitComparator(){
       String param = RunParam.instance().getStringParamValue("idleGirdMapUnitSort");
        if (param.equals("desc")){
            return new DescUnitComparator();
        }else {
            return new DefaultUnitComparator();
        }
    }

    public static Comparator<String> getFloorComparator(){
        return new DefaultFloorComparator();
    }

    public static Comparator<HouseInfo> getHouseComparator(){
        return new DefaultHouseComparator();
    }

    public static class DefaultUnitComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o1.trim().compareTo(o2.trim());
        }
    }

    public static class DescUnitComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o2.trim().compareTo(o1.trim());
        }
    }

    public static class DefaultFloorComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return o2.trim().compareTo(o1.trim());
        }
    }

    public static class DefaultHouseComparator implements Comparator<HouseInfo>{


        @Override
        public int compare(HouseInfo o1, HouseInfo o2) {
            return o1.getHouseOrder().compareTo(o2.getHouseOrder());
        }
    }

}
