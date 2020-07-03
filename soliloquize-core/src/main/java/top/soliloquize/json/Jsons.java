package top.soliloquize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json相关
 *
 * @author wb
 * @date 2020/7/3
 */
public class Jsons {
    private static final Logger logger = LoggerFactory.getLogger(Jsons.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Map的JavaType
     *
     * @param keyClass   键class
     * @param valueClass 值class
     * @param <K>        K
     * @param <V>        V
     * @return Map的JavaType
     */
    public static <K, V> JavaType getMapType(Class<K> keyClass, Class<V> valueClass) {
        return Jsons.OBJECT_MAPPER.getTypeFactory().constructParametricType(HashMap.class, keyClass, valueClass);
    }

    /**
     * List的JavaType
     *
     * @param elementClass 元素class
     * @param <T>          T
     * @return List的JavaType
     */
    public static <T> JavaType getListType(Class<T> elementClass) {
        return Jsons.OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, elementClass);
    }

    /**
     * bean转json
     *
     * @param obj 待转化对象
     * @return json字符串
     * @throws JsonProcessingException 转化发生异常
     */
    public static String bean2json(Object obj) throws JsonProcessingException {
        return Jsons.OBJECT_MAPPER.writeValueAsString(obj);
    }

    /**
     * bean转json,不抛异常
     *
     * @param obj 待转化对象
     * @return json字符串
     */
    public static String bean2JsonEx(Object obj) {
        try {
            return Jsons.bean2json(obj);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * json转对象
     *
     * @param json 待转化的json
     * @param clz  转化成的对象的class
     * @param <T>  T
     * @return 对象
     * @throws JsonProcessingException 转化发生异常
     */
    public static <T> T json2Bean(String json, Class<T> clz) throws JsonProcessingException {
        return Jsons.OBJECT_MAPPER.readValue(json, clz);
    }

    /**
     * json转对象,不抛异常
     *
     * @param json 待转化的json
     * @param clz  转化成的对象的class
     * @param <T>  T
     * @return 对象
     */
    public static <T> T json2BeanEx(String json, Class<T> clz) {
        try {
            return Jsons.json2Bean(json, clz);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * json转对象
     *
     * @param json 待转化的json
     * @param type 转化成的对象的type
     * @param <T>  T
     * @return 对象
     * @throws JsonProcessingException 转化发生异常
     */
    public static <T> T json2Bean(String json, Type type) throws IOException {
        JavaType javaType = Jsons.OBJECT_MAPPER.getTypeFactory().constructType(type);
        return Jsons.OBJECT_MAPPER.readValue(json, javaType);
    }

    /**
     * json转对象,不抛异常
     *
     * @param json 待转化的json
     * @param type 转化成的对象的type
     * @param <T>  T
     * @return 对象
     */
    public static <T> T json2BeanEx(String json, Type type) {
        try {
            return Jsons.json2Bean(json, type);
        } catch (IOException e) {
            logger.error("JsonProcessingException");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * json转map
     *
     * @param json       待转化字符串
     * @param keyClass   键class
     * @param valueClass 值class
     * @param <K>        K
     * @param <V>        V
     * @return map
     * @throws IOException 转化异常
     */
    public static <K, V> Map<K, V> json2Map(String json, Class<K> keyClass, Class<V> valueClass) throws IOException {
        JavaType javaType = Jsons.getMapType(keyClass, valueClass);
        return Jsons.json2Bean(json, javaType);
    }

    /**
     * json转map, 不抛异常
     *
     * @param json       待转化字符串
     * @param keyClass   键class
     * @param valueClass 值class
     * @param <K>        K
     * @param <V>        V
     * @return map
     */
    public static <K, V> Map<K, V> json2MapEx(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            return Jsons.json2Map(json, keyClass, valueClass);
        } catch (IOException e) {
            logger.error("IoException");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * json转List
     *
     * @param json         待转化字符串
     * @param elementClass 元素class
     * @param <T>          T
     * @return List
     * @throws IOException 转化异常
     */
    public static <T> List<T> json2List(String json, Class<T> elementClass) throws IOException {
        JavaType javaType = Jsons.getListType(elementClass);
        return Jsons.json2Bean(json, javaType);
    }

    /**
     * json转List, 不抛异常
     *
     * @param json         待转化字符串
     * @param elementClass 元素class
     * @param <T>          T
     * @return List
     */
    public static <T> List<T> json2ListEx(String json, Class<T> elementClass) {
        try {
            return Jsons.json2List(json, elementClass);
        } catch (IOException e) {
            logger.error("IoException");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
