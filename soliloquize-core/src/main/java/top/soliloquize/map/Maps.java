package top.soliloquize.map;

import java.util.Iterator;
import java.util.Map;

/**
 * @author wb
 * @date 2020/7/9
 */
public class Maps {
    /**
     * 遍历map
     *
     * @param data   数据
     * @param action 对下表,key,value的操作
     * @param <K>    key类型
     * @param <V>    value类型
     */
    public static <K, V> void loopWithIndex(Map<K, V> data, LoopWithIndex action) {
        Iterator<K> iterator = data.keySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            K key = iterator.next();
            action.loopWithIndex(i++, key, data.get(key));
        }
    }
}
