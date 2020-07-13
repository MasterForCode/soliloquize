package top.soliloquize.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 对字符串进行编码
 *
 * @author wb
 * @date 2020/7/2
 */
public class Codec {
    private static final String UTF8 = "UTF-8";
    private static final String GBK = "GBK";
    private static final String UTF16LE = "UTF-16LE";
    private static final String UNICODE = "Unicode";
    private static final String HEX_PATTERN = "[0-9a-fA-F]+";
    private static final String BASE64_PATTERN = "[0-9a-zA-Z]+";
    private static Logger logger = LoggerFactory.getLogger(Codec.class);

    private Codec() {
    }

    /**
     * Base64编码
     *
     * @param source 待编码字符串
     * @return 编码后字符串
     */
    public static String base64Encode2String(String source) {
        return Base64.getEncoder().encodeToString(source.getBytes());
    }


    /**
     * Base64编码
     *
     * @param source 待编码数组
     * @return 编码后字符串
     */
    public static String base64Encode(byte[] source) {
        return Base64.getEncoder().encodeToString(source);
    }

    /**
     * Base64解码
     *
     * @param source 待解码字符串
     * @return 解码后字符串
     */
    public static String base64Decode2String(String source) {
        return new String(Base64.getDecoder().decode(source));
    }


    /**
     * Base64解码
     *
     * @param source 待解码字符串
     * @return 解码后数组
     */
    public static byte[] base64Decode(String source) {
        return Base64.getDecoder().decode(source);
    }

    /**
     * 以默认编码方式对输入字符串进行URL编码
     *
     * @param source 待编码字符串
     * @return 编码后字符串
     */
    public static String urlEncodeWithDefault(String source) {
        try {
            // 没有配置就是系统编码格式否则由-Dfile.encoding=UTF-8决定
            return URLEncoder.encode(source, Charset.defaultCharset().name());
        } catch (UnsupportedEncodingException e) {
            // 不会发生异常
            logger.error("UnsupportedEncoding");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 以utf8编码方式对输入字符串进行URL编码
     *
     * @param source 待编码字符串
     * @return 编码后字符串
     */
    public static String urlEncodeWithUtf8(String source) {
        try {
            return URLEncoder.encode(source, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // 不会发生异常
            logger.error("UnsupportedEncoding");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 以gbk编码方式对输入字符串进行URL编码
     *
     * @param source 待编码字符串
     * @return 编码后字符串
     */
    public static String urlEncodeWithGbk(String source) {
        try {
            return URLEncoder.encode(source, "GBK");
        } catch (UnsupportedEncodingException e) {
            // 不会发生异常
            logger.error("UnsupportedEncoding");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 数组转字符串并以hex编码
     *
     * @param source 数组
     * @return 编码后的字符串
     */
    public static String bytes2HexString(byte[] source) {
        StringBuilder result = new StringBuilder();
        for (byte b : source) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }

    /**
     * 字符串转数组,并以hex编码
     *
     * @param src 字符串
     * @return 数组
     */
    public static byte[] hexString2Bytes(String src) {
        int len = src.length() / 2;
        byte[] ret = new byte[len];
        for (int i = 0; i < len; i++) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * 字符串转字符串,字符先以utf8解码,再以hex编码
     *
     * @param source 字符串
     * @return 字符串
     */
    public static String string2HexUtf8(String source) {

        return string2HexString(source, Codec.UTF8);
    }

    /**
     * 字符串转字符串,字符先以utf16le解码,再以hex编码
     *
     * @param source 字符串
     * @return 字符串
     */
    public static String string2HexUtf16Le(String source) {

        return string2HexString(source, Codec.UTF16LE);
    }

    /**
     * 字符串转字符串,字符先以unicode解码,再以hex编码
     *
     * @param source 字符串
     * @return 字符串
     */
    public static String string2HexUnicode(String source) {

        return string2HexString(source, Codec.UNICODE);
    }

    /**
     * 字符串转字符串,字符先以gbk解码,再以hex编码
     *
     * @param source 字符串
     * @return 字符串
     */
    public static String string2HexGbk(String source) {

        return string2HexString(source, Codec.GBK);
    }

    /**
     * 字符串转字符串,字符先以enc解码,再以hex编码
     *
     * @param source 字符串
     * @param enc    解码模式
     * @return 字符串
     */
    public static String string2HexString(String source, String enc) {
        try {
            return bytes2HexString(source.getBytes(enc));
        } catch (Exception e) {
            logger.error("UnsupportedEncoding");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 不保证完全正确,只能判断是否符合格式
     * 判断字符是否是hex格式的
     *
     * @param content 待验证字符
     * @return true:hex编码, false: 非hex编码
     */
    public static boolean isHexed(String content) {
        return content.matches(Codec.HEX_PATTERN);
    }

    /**
     * 不保证完全正确,只能判断是否符合格式
     * 判断字符是否是base64格式的
     *
     * @param content 待验证字符
     * @return true:base64编码, false: 非base64编码
     */
    public static boolean isBase64(String content) {
        return content.matches(Codec.BASE64_PATTERN);
    }

}
