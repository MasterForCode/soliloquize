package top.soliloquize.lang;

import top.soliloquize.map.LoopWithIndex;
import top.soliloquize.map.Maps;

import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * 链式操作map
 *
 * @author wb
 * @date 2020/7/3
 */
public class ChainingMap<K, V> {
    private HashMap<K, V> map;

    public HashMap<K, V> getMap() {
        return map;
    }

    public ChainingMap() {
        this.map = new HashMap<>();
    }

    public ChainingMap(HashMap<K, V> map) {
        this.map = map;
    }

    public ChainingMap<K, V> put(K key, V value) {
        this.map.put(key, value);
        return this;
    }

    public V get(K key) {
        return this.map.get(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = this.map.get(key);
        return null == value ? defaultValue : value;
    }

    public void forEach(BiConsumer<K, V> consumer) {
        this.map.forEach(consumer);
    }

    public void loop(LoopWithIndex action) {
        Maps.loopWithIndex(this.map, action);
    }

}
