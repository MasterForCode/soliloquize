package top.soliloquize.mail;

import top.soliloquize.core.ImDefinition;
import top.soliloquize.core.Transmitter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author wb
 * @date 2020/8/21
 */
public class MailTransmitter implements Transmitter {
    private Transport transport;
    private Session session;

    @Override
    public MailTransmitter start(Properties properties) throws Exception {
        // 使用的协议（JavaMail规范要求）
        properties.put("mail.transport.protocol", ((MailProperties) properties).getProtocol().trim().toLowerCase());
        // 发件人的邮箱的 SMTP 服务器地址
        properties.put("mail." + ((MailProperties) properties).getProtocol().trim().toLowerCase() + ".host", ((MailProperties) properties).getServer());
        // 需要请求认证
        properties.put("mail." + ((MailProperties) properties).getProtocol().trim().toLowerCase() + ".auth", ((MailProperties) properties).isAuth());
        properties.put("mail." + ((MailProperties) properties).getProtocol().trim().toLowerCase() + ".port", ((MailProperties) properties).getPort());

        // 根据配置创建会话对象, 用于和邮件服务器交互
        this.session = Session.getInstance(properties);
        // 设置为debug模式，可以查看详细的发送log
        this.session.setDebug(true);
        // 根据session获取邮件传输对象
        this.transport = this.session.getTransport();
        return this;
    }

    @Override
    public Transmitter send(ImDefinition imDefinition) throws Exception {
        // 创建一封邮件
        MimeMessage message = this.createMimeMessage(imDefinition);

        // "afcmxzdoybanchef"
        if (!transport.isConnected()) {
            transport.connect(imDefinition.getSender(), imDefinition.getPassword());
        }

        transport.sendMessage(message, message.getAllRecipients());

        this.transport.close();

        System.out.println("邮件发送成功");
        return this;
    }

    private MimeMessage createMimeMessage(ImDefinition imDefinition) throws Exception {

        // 创建一封邮件
        MimeMessage message = new MimeMessage(this.session);
        // 发件人
        message.setFrom(new InternetAddress(imDefinition.getSender(), imDefinition.getSender(), "UTF-8"));
        // 收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(imDefinition.getReceiver(), imDefinition.getReceiver(), "UTF-8"));
        // 邮件主题
        message.setSubject(imDefinition.getSubject(), "UTF-8");

        // 纯内容
        if ((imDefinition.getResource() == null || imDefinition.getResource().size() == 0) && imDefinition.getAnnex() == null) {
            // 纯邮件
            message.setContent(imDefinition.getMsg(), "text/html;charset=utf-8");
        }

        // 富文本
        if (imDefinition.getAnnex() == null && imDefinition.getResource() != null && imDefinition.getResource().size() > 0) {
            // 构建一个总的邮件块
            MimeMultipart mixed = new MimeMultipart("mixed");

            // 设置邮件内容: 多功能用户邮件 (related)
            MimeMultipart related = new MimeMultipart("related");
            this.setMixed(message, mixed, related);

            // 构建多功能邮件块内容
            MimeBodyPart content = new MimeBodyPart();
            this.addResource(related, imDefinition.getResource());

            // 设置具体内容 文本
            content.setContent(imDefinition.getMsg(), "text/html;charset=UTF-8");

            related.addBodyPart(content);
        }

        // 纯内容 + 附件
        if (imDefinition.getAnnex() != null && imDefinition.getResource() == null) {
            MimeMultipart related = new MimeMultipart("related");
            // 构建多功能邮件块内容 文本  图片资源
            MimeBodyPart content = new MimeBodyPart();
            MimeBodyPart annex = new MimeBodyPart();
            related.addBodyPart(content);
            related.addBodyPart(annex);
            content.setContent(imDefinition.getMsg(), "text/html;charset=UTF-8");
            this.addAnnex(annex, Collections.singletonList(imDefinition.getAnnex()));
            message.setContent(related);
        }

        // 富文本 + 附件
        if (imDefinition.getAnnex() != null && imDefinition.getResource() != null && imDefinition.getResource().size() > 0) {
            // 构建一个总的邮件块
            MimeMultipart mixed = new MimeMultipart("mixed");

            // 设置邮件内容: 多功能用户邮件 (related)
            MimeMultipart related = new MimeMultipart("related");
            // 构建多功能邮件块内容
            MimeBodyPart content = new MimeBodyPart();
            // 设置具体内容 文本
            content.setContent(imDefinition.getMsg(), "text/html;charset=UTF-8");
            this.addResource(related, imDefinition.getResource());

            this.setMixed(message, mixed, related);
            // 附件
            MimeBodyPart annex = new MimeBodyPart();
            this.addAnnex(annex, Collections.singletonList(imDefinition.getAnnex()));

            related.addBodyPart(content);
            related.addBodyPart(annex);
        }

        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();

        return message;
    }

    /**
     * 设置富文本的结构
     */
    private void setMixed(MimeMessage message, MimeMultipart mixed, MimeMultipart related) throws Exception {
        // 总邮件块，设置到邮件对象中
        message.setContent(mixed);
        // 正文部分（文本+图片资源）
        MimeBodyPart bodyPart = new MimeBodyPart();
        // 设置到总邮件块
        mixed.addBodyPart(bodyPart);

        // 设置到总邮件快的正文中
        bodyPart.setContent(related);
    }

    /**
     * 添加富文本中的資源
     */
    private void addResource(MimeMultipart related, List<File> files) throws Exception {
        for (File file : files) {
            MimeBodyPart resource = new MimeBodyPart();
            // 设置具体内容资源(图片)
            DataSource ds = new FileDataSource(file);
            DataHandler handler = new DataHandler(ds);
            resource.setDataHandler(handler);
            // 设置资源名称，给外键引用
            resource.setContentID(MimeUtility.encodeText(file.getName()));
            resource.setFileName(MimeUtility.encodeText(file.getName()));
            related.addBodyPart(resource);
        }
    }

    /**
     * 添加附件
     */
    private void addAnnex(MimeBodyPart annex, List<File> files) throws Exception {
        for (File file : files) {
            // 设置具体内容资源(图片)
            DataSource ds = new FileDataSource(file);
            DataHandler handler = new DataHandler(ds);
            annex.setDataHandler(handler);
            // 设置资源名称，给外键引用
            annex.setContentID(MimeUtility.encodeText(file.getName()));
            annex.setFileName(MimeUtility.encodeText(file.getName()));
        }
    }

    @Override
    public Transmitter accept() {
        return this;
    }
}
