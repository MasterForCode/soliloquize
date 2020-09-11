package top.soliloquize.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.soliloquize.exception.NoStackTraceException;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author wb
 * @date 2020/6/30
 */
public class Strings {

    private static final Logger logger = LoggerFactory.getLogger(Strings.class);

    private Strings() {
    }

    /**
     * 去除空格
     *
     * @param src 输入字符串
     * @return 格式化后字符串
     */
    public static String trim(String src) {
        if (src == null) {
            return "";
        }
        // 1. 常规字符串
        // 2. 中文字符串空格
        // 3. 网页实体&nbsp;空格
        return src.replaceAll("[ |　]", " ").trim().replaceAll("\u00A0", "").trim();
    }

    /**
     * 大写字符串
     *
     * @param str   字符串
     * @param begin 大写的起始位置
     * @param end   大写的截止位置
     * @return 大写后的字符串
     */
    public static String upCase(String str, int begin, int end) {
        if (str == null || str.length() == 0) {
//            throw new NoStackTraceException("输入字符串不能为null");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (end >= i && i >= begin) {
                sb.append(new String(new char[]{str.charAt(i)}).toUpperCase());
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }


    /**
     * 将字符串的第一个字符大写
     *
     * @param str 字符串
     * @return 首字母大写的字符串
     */
    public static String upFirst(String str) {
        return Strings.upCase(str, 0, 0);
    }

    /**
     * 将字符串的最后一个字符大写
     *
     * @param str 字符串
     * @return 最后字母大写的字符串
     */
    public static String upLast(String str) {
        return Strings.upCase(str, str.length() - 1, str.length() - 1);
    }

    /**
     * 删除字符串的部分内容
     *
     * @param str   字符串
     * @param begin 保留字符的起始位置
     * @param end   保留字符的截止位置
     * @return 截取后的字符串
     */
    public static String remove(String str, int begin, int end) {
        int length = str.length();
        char[] chars = new char[length - 1];
        str.getChars(begin, end, chars, 0);
        return Arrays.toString(chars);
    }

    /**
     * 删除字符串的最后一个字符
     *
     * @param str 字符串
     * @return 删除后的字符
     */
    public static String removeLast(String str) {
        return Strings.remove(str, 0, str.length() - 1);
    }

    /**
     * 删除字符串的第一个字符
     *
     * @param str 字符串
     * @return 删除后的字符
     */
    public static String removeFirst(String str) {
        return Strings.remove(str, 1, str.length());
    }

    public static String join(String prefix, CharSequence delimiter,Iterable<? extends CharSequence> elements, String suffix) {
        return prefix + String.join(delimiter, elements) + suffix;
    }

    public static String join(CharSequence delimiter,Iterable<? extends CharSequence> elements) {
        return Strings.join("", delimiter, elements, "");
    }

    public static void main(String[] args) {
        System.out.println(upCase(null, 1, 2));
    }


}
