package top.soliloquize.lang;

import top.soliloquize.collection.Collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author wb
 * @date 2020/7/3
 */
public class ChainingList<E> {
    private ArrayList<E> list;

    public ArrayList<E> getList() {
        return list;
    }

    public ChainingList() {
        this.list = new ArrayList<>();
    }

    public ChainingList(ArrayList<E> list) {
        this.list = list;
    }

    public ChainingList<E> add(E e) {
        this.list.add(e);
        return this;
    }

    public E get(int index) {
        return this.list.get(index);
    }

    public E getOrDefault(int index, E defaultValue) {
        E e = this.get(index);
        return null == e ? defaultValue : e;
    }

    public ChainingList<E> subList(int fromIndex, int toIndex) {
        this.list = (ArrayList<E>) this.list.subList(fromIndex, toIndex);
        return this;
    }

    public void forEach(Consumer<E> consumer) {
        this.list.forEach(consumer);
    }

    public void forEach(BiConsumer<Integer, E> indexConsumer) {
        Collections.loop(this.list, indexConsumer);
    }

    public Iterator<E> iterator() {
        return this.list.iterator();
    }

    public static void main(String[] args) {
        ChainingList<Integer> data = new ChainingList<Integer>().add(1).add(2);
        data.forEach((i,e) -> System.out.println("i:" + i + ",e:" + e));
    }
}
