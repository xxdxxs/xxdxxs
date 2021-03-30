package com.xxdxxs.utils;

import com.xxdxxs.support.conversion.Conversion;
import com.xxdxxs.support.conversion.LocalDateConversion;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 日期时间工具
 *
 * @author xxdxxs
 */
public abstract class DateUtils {

    /**
     * 默认日期格式 yyyy-MM-dd
     */
    public static final DateTimeFormatter DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * 默认日期时间格式 yyyy-MM-ddTHH:mm:ss
     */
    public static final DateTimeFormatter DATETIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * 常用日期时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static final DateTimeFormatter COMMON = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 简单格式
     */
    public static final SimpleDateFormat SIMPLE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 默认时区
     */
    public static final ZoneId ZONE = ZoneId.of("Asia/Shanghai");

    /**
     * 支持的unix时间戳范围
     */
    public static final Pair<Long, Long> UNIXTIME_RANGE = Pair.of(-95617555200L, 95617555200L);

    /**
     * 可解析的格式
     */
    public static final List<DateTimeFormatter> PARSABLE = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            COMMON,
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("eee MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
    );

    /**
     * 将Date转换为LocalDate
     *
     * @param date 日期
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 将LocalDate转换为Date
     *
     * @param date 日期
     * @return Date
     */
    public static Date fromLocalDate(LocalDate date) {
        return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将Date转换为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将String转换为LocalDateTime
     *
     * @param str
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String str) {
        return LocalDateTime.parse(str, COMMON);
    }

    /**
     * 将LocalDateTime转换为Date
     *
     * @param datetime 时间
     * @return Date
     */
    public static Date fromLocalDateTime(LocalDateTime datetime) {
        return Date.from(datetime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将String转换为Date
     *
     * @param str 时间字符串
     * @return Date
     */
    public static Date parseString(String str) {
        Date date;
        try {
            LocalDateTime localDateTime = toLocalDateTime(str);
            date = fromLocalDateTime(localDateTime);
        } catch (DateTimeParseException e) {
            date = fromLocalDate(ConvertUtil.LOCALDATE.convert(str).get());
        }
        return date;
    }


    /**
     * 将Date转换为LocalTime
     *
     * @param date 时间
     * @return LocalTime
     */
    public static LocalTime toLocalTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalTime();
    }

    /**
     * 将LocalTime转换为Date
     *
     * @param time LocalTime
     * @param year  年
     * @param month 月
     * @param day 日
     * @return Date
     */
    public static Date fromLocalTime(LocalTime time, int year, int month, int day) {
        return Date.from(time.atDate(LocalDate.of(year, month, day)).atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将Date转换为String
     *
     * @param date 时间
     * @return String
     */
    public static String toString(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof Time) {
            return date.toString();
        }
        return COMMON.format(toLocalDateTime(date));
    }


}
