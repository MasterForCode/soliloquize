package top.soliloquize.consumer;

import top.soliloquize.date.Dates;
import top.soliloquize.lang.ChainingList;
import top.soliloquize.lang.ChainingMap;

import java.util.Iterator;
import java.util.function.Function;

/**
 * @author wb
 * @date 2020/7/7
 */
public class PullConsumer<E> implements Iterator<E> {
    private Function<E, Iterator<E>> dataFunction;
    private Iterator<E> dataIterator;
    private E element;
    private int cursor = 0;
    private int max = Integer.MAX_VALUE;
    public PullConsumer() {
    }

    public PullConsumer(Function<E, Iterator<E>> dataFunction) {
        this.dataFunction = dataFunction;
    }

    public static void main(String[] args) {
        PullConsumer<ChainingMap<String, Object>> pullConsumer = new PullConsumer<>();
        pullConsumer.setDataFunction(data -> {
            if (data == null) {
                return new ChainingList<ChainingMap<String, Object>>()
                        .add(new ChainingMap<String, Object>().put("a", 1))
                        .add(new ChainingMap<String, Object>().put("b", 2))
                        .add(new ChainingMap<String, Object>().put("c", 3))
                        .iterator();
            } else {
                return new ChainingList<ChainingMap<String, Object>>()
                        .add(new ChainingMap<String, Object>().put("aa", 11))
                        .add(new ChainingMap<String, Object>().put("bb", 22))
                        .add(new ChainingMap<String, Object>().put("cc", 33))
                        .iterator();
            }
        });
        pullConsumer.setMax(5000);
        while (pullConsumer.hasNext()) {
//            pullConsumer.next().forEach((key, value) -> {
//                System.out.println("key:" + key + ",value:" + value);
//            });
        }
    }

    public Function<E, Iterator<E>> getDataFunction() {
        return dataFunction;
    }

    public void setDataFunction(Function<E, Iterator<E>> dataFunction) {
        this.dataFunction = dataFunction;
    }

    public Iterator<E> getDataIterator() {
        return dataIterator;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        if (dataFunction == null) {
            return false;
        }
        if (cursor == 0) {
            return returnLastElement2PullData();
        } else {
            if (this.dataIterator.hasNext()) {
                return this.hNext();
            } else {
                return returnLastElement2PullData();
            }
        }

    }

    private boolean returnLastElement2PullData() {
        long date = Dates.now();
        this.dataIterator = this.dataFunction.apply(element);
        System.out.println("already action data: " + this.cursor + ".spend time:" + (Dates.now() - date) / 1000 + "s");
        if (this.dataIterator != null) {
            if (this.dataIterator.hasNext()) {
                return hNext();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean hNext() {
        if (this.cursor < this.max) {
            this.cursor++;
            this.element = this.dataIterator.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E next() {
        return element;
    }
}
