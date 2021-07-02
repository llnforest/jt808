package org.yzh.web.commons;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class.getSimpleName());

    public static final FastDateFormat yyMMddHHmmss = FastDateFormat.getInstance("yyMMddHHmmss");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static String simpleDateFormat = "yyyy-MM-dd HH:mm:ss";

    public static Date parse(String str) {
        try {
            return yyMMddHHmmss.parse(str);
        } catch (ParseException e) {
            log.error("日期格式错误：[{}]", str);
            return null;
        }
    }
    /**
     * 默认时间格式
     */

    /**
     * 获取当前时间字符串（默认时间格式）
     * 2018年8月22日
     * @return
     * author:Lynn
     */
    public static String getNowDateStr(){
        return  DateFormatUtils.format(new Date(), simpleDateFormat);
    }
    /**
     * 获取当前时间字符串（根据传入的格式）
     * 2018年8月22日
     * @param pattern
     * @return
     * author:Lynn
     */
    public static String getNowDateStr(String pattern){
        if (pattern == null){
            pattern = simpleDateFormat;
        }
        return  DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 获取传入日期的字符串（默认时间格式）
     * 2018年8月22日
     * @param date
     * @return
     * author:Lynn
     */
    public static String getDateStr(Date date){
        if (date == null){
            return "";
        }
        return  DateFormatUtils.format(date, simpleDateFormat);
    }


    /**
     * 获取传入日期的字符串（根据传入格式）
     * 2018年8月22日
     * @param date
     * @param pattern
     * @return
     * author:Lynn
     */
    public static String getDateStr(Date date,String pattern){
        if (date == null){
            return "";
        }
        return  DateFormatUtils.format(date, pattern);
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param pattern
     * @return
     */
    public static String timeStamp2Date(String seconds,String pattern) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(pattern == null || pattern.isEmpty()){
            pattern = simpleDateFormat;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }

    /**
     * 将传入格式字符串按照默认格式转为日期
     * 2018年8月22日
     * @param dateStr
     * @return
     * author:Lynn
     */
    public static Date getDate(String dateStr){
        SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将传入格式字符串按照指定格式转为日期
     * 2018年8月22日
     * @param pattern
     * @return
     * author:Lynn
     */
    public static Date getDate(String dateStr,String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取多长时间之后的时间
     * 2019年2月22日
     * @param date
     * @param seconds
     * @return
     * @author:Lynn
     */
    public static Date addSeconds(Date date,int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static Date getNextDate(Date date,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + (num));
        return calendar.getTime();
    }

    /**
     * 获取近几天的日期
     * @param days
     * @return
     */
    public static List<String> getDaysBetwwen(int days){ //最近几天日期
        List<String> dayss = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(new Date());
        Long startTIme = start.getTimeInMillis();

        Calendar end = Calendar.getInstance();
        end.setTime(getDateAdd(days));
        Long endTime = end.getTimeInMillis();
        Long oneDay = 1000 * 60 * 60 * 24l;
        Long time = startTIme;
        while (time <= endTime) {
            Date d = new Date(time);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println(df.format(d));
            dayss.add(df.format(d));
            time += oneDay;
        }
        return dayss;
    }

    private static  Date getDateAdd(int days){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
    }
}