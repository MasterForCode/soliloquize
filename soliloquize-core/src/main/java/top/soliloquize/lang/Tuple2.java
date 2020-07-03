package top.soliloquize.lang;

import java.io.Serializable;

/**
 * @author wb
 * @date 2020/7/2
 */
public class Tuple2<T,E> implements Serializable {
    private T first;
    private E second;

    public Tuple2() {
    }

    public Tuple2(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public Tuple2<T,E> setFirst(T first) {
        this.first = first;
        return this;
    }

    public E getSecond() {
        return second;
    }

    public Tuple2<T,E> setSecond(E second) {
        this.second = second;
        return this;
    }
}
