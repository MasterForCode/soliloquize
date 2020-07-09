package top.soliloquize.consumer;

import top.soliloquize.date.Dates;
import top.soliloquize.lang.ChainingList;
import top.soliloquize.lang.ChainingMap;

import java.util.Iterator;
import java.util.function.Function;

/**
 * 将最后一个元素返回,用于构造获取数据的条件,并主动获取数据
 *
 * @author wb
 * @date 2020/7/7
 */
public class PullConsumer<E> implements Iterator<E> {
    /**
     * 数据获取器,根据返回的最后一个元素获取数据
     */
    private Function<E, Iterator<E>> dataFetcher;
    /**
     * 数据迭代器
     */
    private Iterator<E> dataIterator;
    /**
     * 当前元素
     */
    private E element;
    /**
     * 游标
     */
    private int cursor = 0;
    /**
     * 最大的处理量
     */
    private int max = Integer.MAX_VALUE;

    public PullConsumer() {
    }

    public PullConsumer(Function<E, Iterator<E>> dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    public static void main(String[] args) {
        PullConsumer<ChainingMap<String, Object>> pullConsumer = new PullConsumer<>();
        pullConsumer.setDataFetcher(data -> {
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

    public void setDataFetcher(Function<E, Iterator<E>> dataFetcher) {
        this.dataFetcher = dataFetcher;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        if (dataFetcher == null) {
            return false;
        }
        // 初始获取数据
        if (cursor == 0) {
            return returnLastElement2PullData();
        } else {
            if (this.dataIterator.hasNext()) {
                return this.hNext();
            } else {
                // 数据获取处理完,主动pull数据处理
                return returnLastElement2PullData();
            }
        }
    }

    /**
     * 将最后一个元素返回, 并获取数据
     *
     * @return 获取到数据返回true, 获取不到数据返回false
     */
    private boolean returnLastElement2PullData() {
        long date = Dates.now();
        // 获取数据
        this.dataIterator = this.dataFetcher.apply(this.element);
        System.out.println((Dates.now() - date) / 1000 + "s to get data, current cursor is " + this.cursor);
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

    /**
     * 处理cursor和当前元素
     *
     * @return cursor小于设定的值, cursor可以移动
     */
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
        return this.element;
    }
}
