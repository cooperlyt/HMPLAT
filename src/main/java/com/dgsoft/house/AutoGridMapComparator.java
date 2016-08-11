package com.dgsoft.house;

import com.dgsoft.common.system.RunParam;
import org.jboss.seam.annotations.In;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cooper on 8/6/16.
 */
public class AutoGridMapComparator {

    public interface IndexExtract{
        int getIndex(String str);
    }

    public static Comparator<String> getUnitComparator(){
       String param = RunParam.instance().getStringParamValue("idleGirdMapUnitSort");
        if (param.equals("desc")){
            return new DescUnitComparator();
        }else {
            return new DefaultUnitComparator();
        }
    }

    public static Comparator<Integer> getFloorComparator(){
        return new DefaultFloorComparator();
    }

    public static Comparator<HouseInfo> getHouseComparator(){
        return new DefaultHouseComparator();
    }

    public static IndexExtract getFloorIndexExtract(){
        return new DefaultFloorIndexExtract();
    }

    private static class DefaultFloorIndexExtract implements IndexExtract{

        @Override
        public int getIndex(String str) {
            return getNumberByString(str);
        }
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

    public static int getNumberByString(String str){
        Pattern p = Pattern.compile("-?\\d+");

        Matcher m = p.matcher(str);
        if (m.find()){
            return Integer.parseInt(m.group());
        }else{
            return 0;
        }
    }

    public static class DefaultFloorComparator implements Comparator<Integer>{

        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

    public static class DefaultHouseComparator implements Comparator<HouseInfo>{


        @Override
        public int compare(HouseInfo o1, HouseInfo o2) {
            return o1.getHouseOrder().compareTo(o2.getHouseOrder());
        }
    }

}
