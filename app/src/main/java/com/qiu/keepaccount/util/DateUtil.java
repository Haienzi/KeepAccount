package com.qiu.keepaccount.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转化类
 */
public class DateUtil {
    public static String DATE_YMD_FORMAT = "yyyy-MM-dd";
    public static String DATE_YM_FORMAT = "yyyy-MM";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String dateYMDToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_YMD_FORMAT);
        String s = dateFormat.format(date);
        return s;
    }

    public static String dateYMToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_YM_FORMAT);
        String s = dateFormat.format(date);
        return s;
    }

    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String s = dateFormat.format(date);
        return s;
    }

    public static Date stringToYMDDate(String date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_YMD_FORMAT);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date stringToYMDate(String date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_YM_FORMAT);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static Date stringToDate(String date){
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }



}
