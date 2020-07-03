package top.soliloquize.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wb
 * @date 2020/6/30
 */
public class Strings {

    private static final Logger logger = LoggerFactory.getLogger(Strings.class);

    private Strings() {}

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


}
