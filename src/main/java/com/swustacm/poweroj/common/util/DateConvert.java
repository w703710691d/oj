package com.swustacm.poweroj.common.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式转换工具
 * @author xingzi
 */
public enum DateConvert {
    /*

     */
    AUTUMN_TERM("autumn"),
    SPRING_TERM("spring"),
      //年月日时间
     YEAR_DATE_TIME("yyyy-MM-dd HH:mm:ss"),
     YEAR_DATE("yyyy-MM-dd"),
     //秋季开始时间
     AUTUMN_TERM_BEGIN ("08-01 00:00:00"),
     //秋季结束时间
     AUTUMN_TERM_END ("01-31 00:00:00"),
     //春季开始时间
     SPRING_TERM_BEGIN ("02-01 00:00:00"),
     //春季结束时间
     SPRING_TERM_END ("07-31 00:00:00");
    private final String value;
//    public static final String YEAR_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
//    public static final String YEAR_DATE = "yyyy-MM-dd";
//    public static final String AUTUMN_TERM_BEGIN = "08-01";
//    public static final String AUTUMN_TERM_END = "01-31";
//    public static final String SPRING_TERM_BEGIN = "02-01";
//    public static final String SPRING_TERM_END = "07-31";

    private static final ZoneId ZONE = ZoneId.systemDefault();

    DateConvert(String value) {
        this.value = value;
    }

    /**
     *
     * @param time 时间戳
     * @return
     */
    public static LocalDateTime coverLongToTime(long time){
            Instant instant = Instant.ofEpochSecond(time);
            return LocalDateTime.ofInstant(instant,ZONE);
    }

    /**
     * String 转 时间戳
     * @param time 时间字符串
     * @param format 格式化
     * @return
     */
    public static Long coverTimeToLong(String time,DateConvert format){
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format.value);
        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZONE).toInstant().getEpochSecond();
    }

    /**
     * String 转 Time
     * @param time 时间字符串
     * @param format 格式
     * @return
     */
    public static LocalDateTime coverTimeToTime(String time,DateConvert format){
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern(format.value);
        LocalDateTime parse = LocalDateTime.parse(time, ftf);
        return LocalDateTime.from(parse).atZone(ZONE).toLocalDateTime();
    }

    /**
     * 根据年份获取学期时间
     * @param year 年份
     * @param dateConvert 上/下学期 的时间
     * @return
     */
    public static Long getSemesterByYear(String year,DateConvert dateConvert){
        //年月日字符串
        String time = year + "-" + dateConvert.value;
        return coverTimeToLong(time,DateConvert.YEAR_DATE_TIME);
    }

    /**
     * Time 转 String
     * @Param time 时间（Integer)
     * @Param
     * @author zcy
     */
    public static String getTimeToString(Long time,DateConvert format){
        LocalDateTime dataTime = LocalDateTime.ofEpochSecond(time,0, ZoneOffset.ofHours(8));

        return dataTime.format(DateTimeFormatter.ofPattern(format.value));
    }
    /**
     * 获取时间戳
     */
    public static int getTime(){
        return (int) (System.currentTimeMillis() / 1000);
    }
}
