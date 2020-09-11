package top.soliloquize.exception;

/**
 * @author wb
 * @date 2020/8/3
 */
public class NoStackTraceException extends RuntimeException {
    public NoStackTraceException(String message) {
        super(message);
    }
}
