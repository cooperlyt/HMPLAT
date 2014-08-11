package com.dgsoft.common;

/**
 * Created by cooper on 8/6/14.
 */
public class GBT {

    //    (((((((10+a n )|| 10 × 2)| 11 +a n+1 )|| 10 × 2)| 11
//            + ⋯ +a i )|| 10 × 2)| 11 + ⋯ + a 1 )|| 10 = 1 (A. 0.1)
//    式中: n ——包括校验码在内的字符串的字符数目;
//    i——表示某字符在包括校验码在内的字符串中从右到左
//            的位置序号;
//    a i ——第i位置上某字符的字符值(当a i 为*时,a i 取 0);
//    || 10 ——除以 10 后的余数,如果其值为零,则用 10 代替;
//    | 11 ——除以 11 后的余数,在经过上述处理后余数的值不会
//    为 0。
    public static int getGB17710(String str) {
        int NUM = str.length() + 2;


        int[] a = new int[NUM];
        int[] p = new int[NUM];
        int temp;


        for (int i = 0; i < str.length(); i++) {
            try {
                a[i + 1] = Integer.parseInt(String.valueOf((str.charAt(i))));
            } catch (NumberFormatException e) {
                a[i + 1] = 0;
            }

        }

        p[1] = 10;
        for (int i = 2; i < NUM; i++) {
            temp = (p[i - 1] + a[i - 1]) % 10;
            if (temp == 0) {
                p[i] = (10 * 2) % 11;
            } else {
                p[i] = temp * 2 % 11;
            }
        }

        return (11 - p[NUM - 1]) % 10;

    }


    public enum HouseIdGenType {
        JDJT246_3,// 竣工时间法
        JDJT246_4, //坐标法
        JDJT246_5, //分宗法
        JDJT246_6;// 分幅法
    }

    public enum HouseIdBuildCodePath {
        MAP_BLOCK,// 丘号
        LAND_BLOCK; //宗地号
    }

    public static String formatCode(String intCode, int length) {
        String result = intCode.trim();
        if (result.length() > length) {
            return result.substring(result.length() - length);
        } else if (result.length() < length) {
            int j = length - result.length();
            for (int i = 0; i < j ; i++) {
                result = "0" + result;
            }

        }
        return result;
    }

    public static String getJDJT246(String dictrictCode, String buildCode, String houseCode){
        String result = formatCode(dictrictCode,9) + formatCode(buildCode,12) + formatCode(houseCode,4);
        return result + getGB17710(result);
    }

    public static String getJDJT246(String buildCode, int houseOrder){
        String result = formatCode(buildCode,21) + formatCode(String.valueOf(houseOrder),4);
        return result + getGB17710(result);
    }



}
