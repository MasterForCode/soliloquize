package top.soliloquize.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.soliloquize.lang.Tuple2;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * 对字符串进行加解密
 *
 * @author wb
 * @date 2020/7/2
 */
public class Cryptions {
    private static final Logger logger = LoggerFactory.getLogger(Cryptions.class);
    private static final String AES = "AES";
    private static final String DES = "DES";
    private static final String RSA = "RSA";
    private static final String BASE64 = "BASE64";
    private static final String HEX = "HEX";

    private Cryptions() {
    }

    /**
     * 返回生成指定算法密钥生成器的 Key 对象
     *
     * @param key       密钥
     * @param algorithm 算法
     * @return Key 对象
     */
    private static Key genKey(String key, String algorithm) {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance(algorithm);
            if (Cryptions.AES.equalsIgnoreCase(algorithm)) {
                //AES 要求密钥长度为 128
                kg.init(128, new SecureRandom(key.getBytes()));
            } else if (Cryptions.DES.equalsIgnoreCase(algorithm)) {
                //DES 要求密钥长度为 56
                kg.init(56, new SecureRandom(key.getBytes()));
            }
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), algorithm);
        } catch (NoSuchAlgorithmException e) {
            // 不会发生异常
            logger.error("Get key failed");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成密码容器
     *
     * @param key       密钥
     * @param algorithm 加密算法
     * @param mode      加密/解密
     * @return 密码容器
     */
    private static Cipher getCipher(String key, String algorithm, int mode, Key rsaKey) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(algorithm);
            if (rsaKey != null) {
                cipher.init(mode, rsaKey);
            } else {
                cipher.init(mode, Cryptions.genKey(key, algorithm));
            }
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            logger.error("Get cipher failed");
            e.printStackTrace();
        }
        return cipher;
    }

    /**
     * 加密
     *
     * @param source     待加密字符串
     * @param key        密钥
     * @param type       加密算法
     * @param encodeType 编码模式
     * @return 加密后被编码后的字符串
     */
    private static String encrypt(String source, String key, String type, String encodeType, Key rsaKey) {
        String encrypt = "";
        Cipher cipher = Cryptions.getCipher(key, type, Cipher.ENCRYPT_MODE, rsaKey);
        try {
            byte[] result = cipher.doFinal(source.getBytes());
            if (Cryptions.BASE64.equals(encodeType)) {
                encrypt = Codec.base64Encode(result);
            } else if (Cryptions.HEX.equals(encodeType)) {
                encrypt = Codec.bytes2HexString(result);
            }
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("Encrypt failed");
            e.printStackTrace();
        }
        return encrypt;
    }

    /**
     * 解密
     *
     * @param source     待解密字符串
     * @param key        密钥
     * @param type       解密算法
     * @param encodeType 加密后编码的模式
     * @return 解密后字符串
     */
    private static String decrypt(String source, String key, String type, String encodeType, Key rsaKey) {
        Cipher cipher = Cryptions.getCipher(key, type, Cipher.DECRYPT_MODE, rsaKey);
        String decrypt = "";
        try {
            byte[] sourceByte = null;
            if (Cryptions.BASE64.equals(encodeType)) {
                sourceByte = Codec.base64Decode(source);
            } else if (Cryptions.HEX.equals(encodeType)) {
                sourceByte = Codec.hexString2Bytes(source);
            }
            assert sourceByte != null;
            byte[] result = cipher.doFinal(sourceByte);
            decrypt = new String(result);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("Decrypt failed");
            e.printStackTrace();
        }
        return decrypt;
    }

    /**
     * AES模式加密,并以base64编码加密字符串
     *
     * @param source 待加密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String aesEncryptBase64(String source, String key) {
        return Cryptions.encrypt(source, key, Cryptions.AES, Cryptions.BASE64, null);
    }

    /**
     * AES模式加密,并以hex编码加密字符串
     *
     * @param source 待加密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String aesEncryptHex(String source, String key) {
        return Cryptions.encrypt(source, key, Cryptions.AES, Cryptions.HEX, null);
    }

    /**
     * AES模式解密,密文的编码模式为base64
     *
     * @param source 待解密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String aesDecryptBase64(String source, String key) {
        return Cryptions.decrypt(source, key, Cryptions.AES, Cryptions.BASE64, null);
    }

    /**
     * AES模式解密,密文的编码模式为hex
     *
     * @param source 待解密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String aesDecryptHex(String source, String key) {
        return Cryptions.decrypt(source, key, Cryptions.AES, Cryptions.HEX, null);
    }

    /**
     * DES模式加密,并以base64编码加密字符串
     *
     * @param source 待加密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String desEncryptBase64(String source, String key) {
        return Cryptions.encrypt(source, key, Cryptions.DES, Cryptions.BASE64, null);
    }

    /**
     * DES模式加密,并以hex编码加密字符串
     *
     * @param source 待加密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String desEncryptHex(String source, String key) {
        return Cryptions.encrypt(source, key, Cryptions.DES, Cryptions.HEX, null);
    }

    /**
     * DES模式解密,密文的编码模式为base64
     *
     * @param source 待解密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String desDecryptBase64(String source, String key) {
        return Cryptions.decrypt(source, key, Cryptions.DES, Cryptions.BASE64, null);
    }

    /**
     * DES模式解密,密文的编码模式为hex
     *
     * @param source 待解密字符串
     * @param key    密钥
     * @return 加密并编码后字符串
     */
    public static String desDecryptHex(String source, String key) {
        return Cryptions.decrypt(source, key, Cryptions.DES, Cryptions.HEX, null);
    }


    /**
     * RSA模式加密,并以base64编码加密字符串
     *
     * @param source    待加密字符串
     * @param publicKey 密钥
     * @return 加密并编码后字符串
     */
    public static String rsaEncryptBase64(String source, Key publicKey) {
        return Cryptions.encrypt(source, null, Cryptions.RSA, Cryptions.BASE64, publicKey);
    }

    /**
     * RSA模式加密,并以hex编码加密字符串
     *
     * @param source    待加密字符串
     * @param publicKey 密钥
     * @return 加密并编码后字符串
     */
    public static String rsaEncryptHex(String source, Key publicKey) {
        return Cryptions.encrypt(source, null, Cryptions.RSA, Cryptions.HEX, publicKey);
    }

    /**
     * RSA模式解密,密文的编码模式为base64
     *
     * @param source     待解密字符串
     * @param privateKey 密钥
     * @return 加密并编码后字符串
     */
    public static String reaDecryptBase64(String source, Key privateKey) {
        return Cryptions.decrypt(source, null, Cryptions.RSA, Cryptions.BASE64, privateKey);
    }

    /**
     * RSA模式解密,密文的编码模式为hex
     *
     * @param source     待解密字符串
     * @param privateKey 密钥
     * @return 加密并编码后字符串
     */
    public static String rsaDecryptHex(String source, Key privateKey) {
        return Cryptions.decrypt(source, null, Cryptions.RSA, Cryptions.HEX, privateKey);
    }

    /**
     * 生成私钥和公钥
     *
     * @return 私钥和公钥
     */
    public static Tuple2<PrivateKey, PublicKey> genKeyPair() {
        return Cryptions.genKeyPair(1024);
    }

    /**
     * 生成私钥和公钥
     *
     * @param keySize 密钥长度
     * @return 私钥和公钥
     */
    public static Tuple2<PrivateKey, PublicKey> genKeyPair(int keySize) {
        int minKeySize = 96;
        int maxKeySize = 1024;
        if (keySize < minKeySize || keySize > maxKeySize) {
            logger.error("keySize is between 96 and 2014");
            throw new RuntimeException();
        }
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(Cryptions.RSA);
            keyPairGen.initialize(keySize);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            return new Tuple2<>(keyPair.getPrivate(), keyPair.getPublic());
        } catch (NoSuchAlgorithmException e) {
            // 不会发生异常
            logger.error("NoSuchAlgorithm");
            e.printStackTrace();
        }
        return new Tuple2<>();
    }

    public static void main(String[] args) {
        System.out.println(aesEncryptBase64("aaa" + System.currentTimeMillis(), "aaa"));
        System.out.println(aesDecryptBase64("vhv/Q8FyVlT3SFDJceBB0Q==", "aaa"));
        System.out.println(aesEncryptHex("aaa", "aaa"));
        System.out.println(aesDecryptHex("BE1BFF43C1725654F74850C971E041D1", "aaa"));
    }
}
