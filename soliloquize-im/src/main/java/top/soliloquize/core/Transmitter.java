package top.soliloquize.core;

import javax.mail.MessagingException;
import java.util.Properties;

/**
 * @author wb
 * @date 2020/8/21
 */
public interface Transmitter {
    Transmitter start(Properties properties) throws Exception;

    Transmitter send(ImDefinition imDefinition) throws Exception;

    Transmitter accept();

}
