package top.soliloquize.collection;

import top.soliloquize.lang.ChainingList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * @author wb
 * @date 2020/7/22
 */
public class Collectors {
    /**
     * @param classifier classifier函数将输入元素映射到键
     * @param <T>        输入元素的类型
     * @param <A>        键的类型
     * @return 实现分组操作, 不去除null
     */
    public static <T, A> Collector<T, ?, Map<A, List<T>>> groupingByWithNullKeys(Function<? super T, ? extends A> classifier) {
        return java.util.stream.Collectors.toMap(
                classifier,
                Collections::singletonList,
                (List<T> oldList, List<T> newEl) -> {
                    List<T> newList = new ArrayList<>(oldList.size() + 1);
                    newList.addAll(oldList);
                    newList.addAll(newEl);
                    return newList;
                });
    }

    public static void main(String[] args) {
        User u1 = new User("jack", 1);
        User u2 = new User("tom", 2);
        User u3 = new User("lucy", 3);
        User u4 = new User(null, 4);
        User u5 = new User(null, 5);
        ChainingList<User> c = new ChainingList<User>().add(u1).add(u2).add(u3).add(u4).add(u5);
        Map<String, List<User>> g = c.stream().collect(Collectors.groupingByWithNullKeys(User::getName));
        System.out.println(g.size());
    }
}
