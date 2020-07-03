package top.soliloquize.lang;

/**
 * @author wb
 * @date 2020/7/2
 */
public class Tuple3<T,E,F> extends Tuple2<T,E> {
    private F third;

    public Tuple3() {
    }

    public Tuple3(T first, E second, F third) {
        super(first, second);
        this.third = third;
    }

    public F getThird() {
        return third;
    }

    public Tuple3<T,E,F> setThird(F third) {
        this.third = third;
        return this;
    }
}
