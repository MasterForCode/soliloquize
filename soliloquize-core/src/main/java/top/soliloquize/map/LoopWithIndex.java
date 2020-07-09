package top.soliloquize.map;

/**
 * @author wb
 * @date 2020/7/9
 */
@FunctionalInterface
public interface LoopWithIndex {
    /**
     * 迭代map,并获取下标
     *
     * @param index 下表
     * @param key   key
     * @param value value
     */
    void loopWithIndex(int index, Object key, Object value);
}
