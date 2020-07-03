package top.soliloquize.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 对字符串进行摘要
 *
 * @author wb
 * @date 2020/7/2
 */
@SuppressWarnings("all")
public class Digests {
    private static final Logger logger = LoggerFactory.getLogger(Digests.class);
    private static final String MD5 = "md5";
    private static final String SHA1 = "SHA-1";
    private static final String SHA256 = "SHA-256";
    private static final Charset defaultCharset = StandardCharsets.UTF_8;

    private Digests() {}

    /**
     * 获得摘要生成器
     *
     * @param algorithm 摘要算法
     * @return 摘要生成器
     */
    private static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            // 不会发生异常
            logger.error("NoSuchAlgorithm");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以md5算法生成摘要,并以hex编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String md5Hex(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.MD5).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.bytes2HexString(secretBytes);
    }

    /**
     * 以md5算法生成摘要,并以base64编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String md5Base64(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.MD5).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.base64Encode(secretBytes);
    }

    /**
     * 以sha1算法生成摘要,并以hex编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String sha1Hex(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.SHA1).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.bytes2HexString(secretBytes);
    }

    /**
     * 以sha1算法生成摘要,并以base64编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String sha1Base64(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.SHA1).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.base64Encode(secretBytes);
    }

    /**
     * 以sha256算法生成摘要,并以hex编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String sha256Hex(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.SHA256).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.bytes2HexString(secretBytes);
    }

    /**
     * 以sha256算法生成摘要,并以base64编码
     *
     * @param source 字符串
     * @return 编码后的摘要
     */
    public static String sha256Base64(String source) {
        byte[] secretBytes = Digests.getMessageDigest(Digests.SHA256).digest(
                source.getBytes(Digests.defaultCharset));
        return Codec.base64Encode(secretBytes);
    }

}
