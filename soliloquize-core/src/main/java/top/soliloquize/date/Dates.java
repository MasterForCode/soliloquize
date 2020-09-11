package top.soliloquize.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * 时间相关
 *
 * @author wb
 * @date 2020/7/3
 */
public class Dates {
    public static final String DATE = "yyyy-MM-dd";
    public static final String TIME = "hh:mm:ss";
    public static final String DATE_TIME = "yyyy-MM-dd hh:mm:ss";
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    /**
     * 获取当前时间毫秒数
     *
     * @return 当前时间毫秒数
     */
    public static long now() {
        return Instant.now().toEpochMilli();
    }

    /**
     * date转localDate
     *
     * @param date 被转化时间
     * @return 转化后时间
     */
    public static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(Dates.DEFAULT_ZONE_ID).toLocalDate();
    }

    /**
     * date转localDateTime
     *
     * @param date 被转化时间
     * @return 转化后时间
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(Dates.DEFAULT_ZONE_ID).toLocalDateTime();
    }

    /**
     * localDate转date
     *
     * @param localDate 被转化时间
     * @param zoneId    时区
     * @return 转化后时间
     */
    public static Date localDate2Date(LocalDate localDate, ZoneId zoneId) {
        if (null == localDate) {
            return null;
        }
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * localDate转date
     *
     * @param localDate 被转化时间
     * @return 转化后时间
     */
    public static Date localDate2Date(LocalDate localDate) {

        return Dates.localDate2Date(localDate, Dates.DEFAULT_ZONE_ID);
    }

    /**
     * date添加天数后转化程localDate
     *
     * @param date 时间
     * @param days 添加的天数
     * @return localDate
     */
    public static LocalDate dateAddDays2LocalDate(Date date, int days) {
        return Dates.date2LocalDate(date).plusDays(days);
    }

    /**
     * date添加天数
     *
     * @param date 时间
     * @param days 添加的天数
     * @return date
     */
    public static Date dateAddDays(Date date, int days) {
        return Dates.localDate2Date(Dates.date2LocalDate(date).plusDays(days));
    }

    /**
     * 解析时间
     *
     * @param dateStr 时间字符串
     * @param format  格式
     * @return date
     */
    public static Date str2Date(String dateStr, String format) {
        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
        return Dates.localDate2Date(localDate);
    }

    /**
     * 格式化时间
     *
     * @param date   时间
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String format) {
        return Dates.date2LocalDate(date).format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化时间为yyyy-MM-dd格式
     *
     * @param date   时间
     * @return 格式化后的字符串
     */
    public static String formatDate2Date(Date date) {
        return Dates.formatDate(date, Dates.DATE);
    }

    /**
     * 格式化时间为hh:mm:ss格式
     *
     * @param date   时间
     * @return 格式化后的字符串
     */
    public static String formatDate2Time(Date date) {
        return Dates.formatDate(date, Dates.TIME);
    }

    /**
     * 格式化时间为yyyy-MM-dd hh:mm:ss格式
     *
     * @param date   时间
     * @return 格式化后的字符串
     */
    public static String formatDate2DateTime(Date date) {
        return Dates.formatDate(date, Dates.DATE_TIME);
    }

    /**
     * 格式化localDate
     *
     * @param localDate localDate
     * @param format    格式
     * @return 格式化后的字符串
     */
    public static String formatLocalDate(LocalDate localDate, String format) {
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * 格式化localDate为yyyy-MM-dd
     *
     * @param localDate localDate
     * @return 格式化后的字符串
     */
    public static String formatLocalDate2Date(LocalDate localDate) {
        return Dates.formatLocalDate(localDate, Dates.DATE);
    }

    /**
     * 格式化localDate为hh:mm:ss
     *
     * @param localDate localDate
     * @return 格式化后的字符串
     */
    public static String formatLocalDate2Time(LocalDate localDate) {
        return Dates.formatLocalDate(localDate, Dates.TIME);
    }

    /**
     * 格式化localDate为yyyy-MM-dd hh:mm:ss
     *
     * @param localDate localDate
     * @return 格式化后的字符串
     */
    public static String formatLocalDate2DateTime(LocalDate localDate) {
        return Dates.formatLocalDate(localDate, Dates.DATE_TIME);
    }

    /**
     * date字符串添加天数
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @param days 添加的天数
     * @return 日期
     */
    public static Date plusDays(String dateStr, String format, int days) {
        Date date = Dates.str2Date(dateStr, format);
        return Dates.dateAddDays(date, days);
    }

    /**
     * date字符串添加天数
     * @param dateStr 日期字符串
     * @param format 日期格式
     * @param days 添加的天数
     * @return 日期字符串
     */
    public static String plusDays1(String dateStr, String format, int days) {
        return Dates.formatDate(Dates.plusDays(dateStr, format, days), format);
    }

    /**
     * 判断时间段是否有交集, 不限制时间段的顺序,2020-01-01~2020-02-02或2020-02-02~2020-01-01
     * @param period1First 时间段1的时间
     * @param period1Second 时间段1的时间
     * @param period2First 时间段2的时间
     * @param period2Second 时间段2的时间
     * @return true有交集,false没交集
     */
    public static boolean dateCrossPredicate(Date period1First, Date period1Second, Date period2First, Date period2Second) {
        Date period1End = Collections.max(Arrays.asList(period1First, period1Second));
        Date period1Start = Collections.min(Arrays.asList(period1First, period1Second));
        Date period2End = Collections.max(Arrays.asList(period2First, period2Second));
        Date period2Start = Collections.min(Arrays.asList(period2First, period2Second));
        return period1End.getTime() >= period2Start.getTime() && period2End.getTime() >= period1Start.getTime();
    }
}
