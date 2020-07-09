package top.soliloquize.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * @author wb
 * @date 2020/7/9
 */
public class Collections {
    /**
     * 遍历集合
     *
     * @param collections 集合
     * @param action      对下表和元素的操作
     * @param <E>         元素类型
     */
    public static <E> void loop(Collection<E> collections, BiConsumer<Integer, E> action) {
        Iterator<E> iterator = collections.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            action.accept(i++, iterator.next());
        }
    }

    public static void main(String[] args) {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        data.add(6);
        System.out.println(data.toString());
        System.out.println(reverse(data).toString());
        System.out.println(data.equals(reverse(data)));
    }

    /**
     * 反转list,不会生成新对象
     *
     * @param data 列表
     * @param <E>  元素类型
     * @return 反转后元素
     */
    public static <E> List<E> reverse(List<E> data) {
        int middle = 2;
        int size = data.size();
        Collections.loop(data, (i, e) -> {
            if (size % middle == 0) {
                if (i <= size / middle) {
                    data.set(i, data.get(size - i - 1));
                    data.set(size - i - 1, e);
                }
            } else {
                if (i < size / middle) {
                    data.set(i, data.get(size - i - 1));
                    data.set(size - i - 1, e);
                }
            }
        });
        return data;
    }

    /**
     * 找到集合中的第一个元素
     *
     * @param collections 集合
     * @param test        条件
     * @param <E>         元素类型
     * @return 找到的元素, 否则为null
     */
    public static <E> E findFirst(Collection<E> collections, Predicate<E> test) {
        for (E data : collections) {
            if (test.test(data)) {
                return data;
            }
        }
        return null;
    }
}
