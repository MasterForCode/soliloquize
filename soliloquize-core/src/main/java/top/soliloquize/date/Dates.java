package top.soliloquize.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间相关
 *
 * @author wb
 * @date 2020/7/3
 */
public class Dates {
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
     * 格式化localDate
     *
     * @param localDate localDate
     * @param format    格式
     * @return 格式化后的字符串
     */
    public static String formatLocalDate(LocalDate localDate, String format) {
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

}
