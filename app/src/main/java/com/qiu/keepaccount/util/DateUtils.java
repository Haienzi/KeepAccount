package com.qiu.keepaccount.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期转化类
 */
public class DateUtils {
    public static String DATE_YMD_FORMAT = "yyyy-MM-dd";
    public static String DATE_YM_FORMAT = "yyyy-MM";
    public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT = "yyyy-MM-dd HH:mm";
    public static String TIME_FORMAT = "hh:mm a";
    /**  */
    public static final String FORMAT_MAIN_TAB = "yyyy年MM月";
    /** 月和日格式 */
    public static final String FORMAT_MONTH_DAY = "MM月dd日";
    /** 单获取天格式 */
    public static final String FORMAT_DAY = "dd";
    private static String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

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

    public static String dateToString(Date date,String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String s = dateFormat.format(date);
        return s;
    }

    public static int getDays(String date){
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(stringToYMDDate(date));
      int days = calendar.get(Calendar.DAY_OF_MONTH);
      return days;


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

    /**
     * 将指定的日期字符串转换为 Date 对象。
     * @param date 日期字符串
     * @return Date 对象
     */
    public static Date getDateWithDateString(String date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据指定日期字符串，获取指定月后的日期。
     * @param dateStr 指定的日期，如：2017-01
     * @param format 日期格式，如：yyyy-MM
     * @param next 多少个月后，如：1
     * @return 指定月后的日期，如：2017-02
     */
    public static String getDateNxtMonth(String dateStr, String format, int next){
        Date date = getDateWithDateString(dateStr, format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, next);
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.CHINA);
        return f.format(c.getTime());
    }

    /**
     * 将日期对象，格式化为指定日期格式的日期字符串。
     * @param date      日期对象
     * @param format    日期格式
     * @return 根据指定日期格式格式话后的字符串，如：2017-04-07
     */
    public static String getDateText(Date date, String format){
        SimpleDateFormat f = new SimpleDateFormat(format != null ? format : FORMAT, Locale.CHINA);
        return f.format(date);
    }

    /**
     * 将日期对象，格式化为指定日期格式的日期字符串。
     * @param c         日期对象
     * @param format    日期格式
     * @return 根据指定日期格式格式话后的字符串，如：2017-04-07
     */
    public static String getDateText(Calendar c, String format){
        SimpleDateFormat f = new SimpleDateFormat(format != null ? format : FORMAT, Locale.CHINA);
        return f.format(c.getTime());
    }

    /**
     * 将时间戳，格式化为指定日期格式的日期字符串。
     * @param time      时间戳
     * @param format    日期格式
     * @return 根据指定日期格式格式话后的字符串，如：2017-04-07
     */
    public static String getDateText(long time, String format){
        SimpleDateFormat f = new SimpleDateFormat(format != null ? format : FORMAT, Locale.CHINA);
        return f.format(time);
    }

    /**
     * 获取带星期的日期。
     * @param date     日期对象
     * @param format   日期格式
     * @return 如：2017-04-07 星期一
     */
    public static String getWeekDate(Date date, String format){
        SimpleDateFormat f = new SimpleDateFormat(format != null ? format : FORMAT, Locale.CHINA);
        return f.format(date).concat(" ").concat(getWeekForDate(date));
    }

    /**
     * 通过 Date 对象获取对应的星期。
     * @param date 日期对象。
     * @return 如：星期一
     */
    public static String getWeekForDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return weeks[c.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 根据指定格式获取当前日期
     * @param format 日期格式
     * @return 当前日期
     */
    public static String getCurrentDate(String format){
        Calendar c = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat f = new SimpleDateFormat(format, Locale.CHINA);
        return f.format(c.getTime());
    }


}
