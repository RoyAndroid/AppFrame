package com.appframe.lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Roy
 * Date: 16/1/1
 */
public class DateUtils {
    public static String dayNames[] = {"日", "一", "二", "三", "四", "五", "六"};
    public static String monthNames[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};

    /**
     * <pre>
     * 判断date和当前日期是否在同一周内
     * 注:
     * Calendar类提供了一个获取日期在所属年份中是第几周的方法，对于上一年末的某一天
     * 和新年初的某一天在同一周内也一样可以处理，例如2012-12-31和2013-01-01虽然在
     * 不同的年份中，但是使用此方法依然判断二者属于同一周内
     * </pre>
     *
     * @param date
     * @return
     */
    public static boolean isSameWeekWithToday(Date date) {

        if (date == null) {
            return false;
        }

        // 0.先把Date类型的对象转换Calendar类型的对象
        Calendar todayCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();

        todayCal.setTime(new Date());
        dateCal.setTime(date);

        // 1.比较当前日期在年份中的周数是否相同
        return todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal
                .get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取时间部分 20:10
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    /**
     * 本周六 20:00
     *
     * @param date
     * @return
     */
    public static String friendlyTimeOnWeek(Date date) {
        String friendTime = "";
        if (isSameWeekWithToday(date)) {
            friendTime += "本周";
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            friendTime += dayNames[dayOfWeek - 1];
            friendTime += getTime(date);
        } else {
            friendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        }
        return friendTime;
    }

    /**
     * 今天是否在两个时间之内
     *
     * @param begin
     * @param end
     * @return
     */
    public static boolean isBetweenWithToday(Date begin, Date end) {
        Date today = new Date();
        return today.getTime() > begin.getTime() && today.getTime() <= end.getTime();
    }

    /**
     * 只是用于处理AVOSCloud的推送消息时间格式
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date AVParseFromString(String date) {
        SimpleDateFormat df = new SimpleDateFormat();
        try {
            //为了处理AVOSCloud的推送消息的时间格式
            df.applyPattern("yy-MM-dd HH:mm:ss.SSS");
            date = date.replace("T", "").replace("Z", "");
            return df.parse(date);
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        //默认过期时间是1天
        return new Date(System.currentTimeMillis() + 3600 * 1000 * 24);
    }


    public static String dealwithDate(String time) {
        if (StringUtils.isEmpty(time))
            return "";
        return dealwithDate(Long.valueOf(time));
    }

    public static String dealwithDate(long time) {
        if (time == 0) {
            return "刚刚";
        }

        long min1 = 60 * 1000;
        long h1 = 60 * min1;
        SimpleDateFormat sdfShowYear = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MM月dd日");

        Date date = new Date(time);
        String timeStr;
        long timelong = new Date().getTime() - date.getTime();
        if (timelong > h1) {
            if (timelong / h1 > 24) {
                if (sdfYear.format(date).equals(sdfYear.format(new Date()))) {
                    timeStr = sdfMonth.format(date);
                } else {
                    timeStr = sdfShowYear.format(date);
                }
            } else {
                int timeInt = (int) (timelong / h1);
                timeInt = timeInt == 0 ? 1 : timeInt;
                timeStr = timeInt + "小时前";
            }
        } else if (timelong / min1 >= 1) {
            int timeInt = (int) (timelong / min1);
            timeInt = timeInt == 0 ? 1 : timeInt;
            timeStr = timeInt + "分钟前";
        } else {
            timeStr = "刚刚";
        }
        return timeStr;
    }

    /* 返回MM月dd日 */
    public static String format(long time) {
        SimpleDateFormat mformat = new SimpleDateFormat("MM");
        SimpleDateFormat dformat = new SimpleDateFormat("dd");
        Date date = new Date();
        date.setTime(time);
        return mformat.format(date) + "月" + dformat.format(date) + "日";
    }

    /* 返回yyyy年MM月dd日 */
    public static String formatyy(long time) {
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        date.setTime(time);
        return sf2.format(date);
    }

    /* 返回yyyy-MM-dd */
    public static String formatyymmdd(long time) {
        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(time);
        return sf2.format(date);
    }

    /* 返回汉字月 */
    public static String formatMonthCh(long time) {
        SimpleDateFormat mformat = new SimpleDateFormat("MM");
        Date date = new Date();
        date.setTime(time);
        int month = Integer.valueOf(mformat.format(date));
        return monthNames[month - 1] + "月";
    }

    /* 返回dd */
    public static String formatdd(long time){
        SimpleDateFormat dformat = new SimpleDateFormat("dd");
        Date date = new Date();
        date.setTime(time);
        return dformat.format(date);
    }

    /* 返回yyyy */
    public static String formatYear(long time){
        SimpleDateFormat yformat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        date.setTime(time);
        return yformat.format(date);
    }
}
