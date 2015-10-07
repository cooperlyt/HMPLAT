package com.dgsoft.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: cooperlee
 * Date: 11/6/13
 * Time: 11:15 AM
 */
public class DataFormat {

    public static Integer strToInt(String s){
        if ((s == null) || "".equals(s.trim())){
            return null;
        }
        String result = s.trim().replace("一","1");
        result = result.replace("二","2");
        result = result.replace("三","3");
        result = result.replace("四","4");
        result = result.replace("五","5");
        result = result.replace("六","6");
        result = result.replace("七","7");
        result = result.replace("八","8");
        result = result.replace("九","9");
//            result = result.replace("壹","1");
//            result = result.replace("贰","2");
//            result = result.replace("叁","3");
//            result = result.replace("肆","4");
//            result = result.replace("伍","5");
//            result = result.replace("陆","6");
//            result = result.replace("柒","7");
//            result = result.replace("捌","8");
//            result = result.replace("玖","9");


        boolean isNegative = result.startsWith("-");

        result = result.replaceAll("\\D","");
        if (result.equals("")){
            return null;
        }
        if (isNegative){
            result = "-" + result;
        }
        return Integer.parseInt(result);

    }


    public static BigDecimal halfUpCurrency(BigDecimal number, Locale locale) {
        NumberFormat currencyFormat = DecimalFormat.getCurrencyInstance(locale);
        try {
            return new BigDecimal(currencyFormat.parse(currencyFormat.format(number)).toString());
        } catch (ParseException e) {
            return number;
        }
    }

    public static BigDecimal halfUpCurrency(BigDecimal number) {
        return halfUpCurrency(number,Locale.CHINA);
    }



    public static BigDecimal format(BigDecimal value, String formatStr) {
        DecimalFormat df = new DecimalFormat(formatStr);
        df.setGroupingUsed(false);
        df.setRoundingMode(RoundingMode.HALF_UP);
        try {
            return new BigDecimal(df.parse(df.format(value)).toString());
        } catch (ParseException e) {
            return value;
        }

    }

    @Deprecated
    public static Date halfTime(Date value){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(value);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static Date getDayBeginTime(Date value){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(value);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }

    public static Date getDayEndTime(Date value){
        return new Date(getDayBeginTime(value).getTime() + 24 * 60 * 60 * 1000 - 1);
    }

    public static boolean isEmpty(String value){
        return (value == null) || (value.trim().equals(""));
    }

    public static boolean isEmpty(BigDecimal value) {
        return (value == null) || (value.compareTo(BigDecimal.ZERO) == 0);
    }

}
