package top.soliloquize;

import top.soliloquize.core.ImDefinition;
import top.soliloquize.core.ProtocolConst;
import top.soliloquize.mail.MailProperties;
import top.soliloquize.mail.MailTransmitter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wb
 * @date 2020/8/21
 */
public class Test {
    public static void main(String[] args) throws Exception {
        MailTransmitter transmitter = new MailTransmitter().start(new MailProperties().setAuth(false).setPort(587).setProtocol(ProtocolConst.SMTP).setSsl(false).setServer("smtp.qq.com"));
//        for (int i = 0; i < 10; i++) {
        List<File> resource = new ArrayList<>();
        resource.add(new File("C:\\Users\\wb\\Desktop\\idea.jpg"));
        resource.add(new File("C:\\Users\\wb\\Desktop\\test1.jpg"));
        resource.add(new File("C:\\Users\\wb\\Desktop\\test2.jpg"));
//            transmitter.send(new ImDefinition().setMsg("纯文本").setSender("3485713420@qq.com").setReceiver("2514275491@qq.com").setPassword("afcmxzdoybanchef").setSubject("测试"));
//            transmitter.send(new ImDefinition().setMsg("<img src='cid:idea.jpg'/>  富文本！<img src='cid:test1.jpg'/>  富文本！<img src='cid:test2.jpg'/>  富文本！").setSender("3485713420@qq.com").setReceiver("2514275491@qq.com").setPassword("afcmxzdoybanchef").setSubject("测试").setResource(resource));
        transmitter.send(new ImDefinition().setMsg("纯文 + 附件").setSender("*******@qq.com").setReceiver("*******@qq.com").setPassword("afcmxzdoybanchef").setSubject("测试").setAnnex(new File("C:\\Users\\wb\\Desktop\\common.log")));
        transmitter.send(new ImDefinition().setMsg("<img src='cid:idea.jpg'/> <a href=\"https://www.baidu.com\">百度</a> 富文本 + 附件！<img src='cid:test1.jpg'/>  富文本 + 附件！<img src='cid:test2.jpg'/>  富文本 + 附件！").setSender("3485713420@qq.com").setReceiver("2514275491@qq.com").setPassword("afcmxzdoybanchef").setSubject("测试").setAnnex(new File("C:\\Users\\wb\\Desktop\\common.log")).setResource(resource));
//        }
    }
}
