package com.htsec.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by bernard on 2017/3/27.
 */
public class TimeUtil {
    public static List<String> findDates(String begin, String end) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = sdf.parse(begin);
        Date dEnd = sdf.parse(end);
        List<Date> dates = findDates(dBegin, dEnd);
        List<String> dateString = new ArrayList<String>();
        for (Date day : dates) {
            dateString.add(sdf.format(day));
        }
        return dateString;

    }

    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }



    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    public static String getNow(){
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd").format(date);

    }

    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     *
     * @return
     * @author zhaoxuepu
     * @throws ParseException
     */
    public static List<String> getThisWeek() throws ParseException {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        return findDates(imptimeBegin,imptimeEnd);
    }



    /**
     * 根据当前日期获得所在周的日期区间（周一和周日日期）
     *
     * @return
     * @author zhaoxuepu
     * @throws ParseException
     */
    public static List<String> getThisWeekBeginEnd() throws ParseException {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }

        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        List<String>  list = new ArrayList<>();
        list.add(imptimeBegin);
        list.add(imptimeEnd);
        return list;
    }


    /**
     * 根据当前日期获得上周的日期区间（上周周一和周日日期）
     *
     * @return
     * @author zhaoxuepu
     */
    public static List<String> getLastWeek() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 7);
        // System.out.println(sdf.format(calendar1.getTime()));// last Monday
        String lastBeginDate = sdf.format(calendar1.getTime());
        // System.out.println(sdf.format(calendar2.getTime()));// last Sunday
        String lastEndDate = sdf.format(calendar2.getTime());
        return findDates(lastBeginDate,lastEndDate);
    }


    public static String getYesterday() {
        Calendar   cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        return new SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());

    }

    public static String  getMonth(){
        Date date = new Date();
        return new SimpleDateFormat("yyyyMM").format(date);
    }

    public static void main(String [] args){
        System.out.print(getMonth());
    }
}
