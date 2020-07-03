package top.soliloquize;

/**
 * Job状态
 *
 * @author wb
 * @date 2020/7/3
 */
public enum Status {
    /**
     * 任务结束
     */
    FINISHED(1),
    /**
     * 任务正在运行
     */
    RUNNING(2),
    /**
     * 任务可被执行
     */
    AVAILABLE(3);

    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
