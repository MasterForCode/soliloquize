package top.soliloquize.core;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * @author wb
 * @date 2020/8/21
 */
public class ImDefinition {
    /**
     * 发送者
     */
    private String sender;
    /**
     * 发送者密码(授权码)
     */
    private String password;
    /**
     * 接收者
     */
    private String receiver;
    /**
     * 主题
     */
    private String subject;
    /**
     * 信息
     */
    private String msg;
    /**
     * 附件
     */
    private File annex;
    /**
     * 内容中包含的内容信息
     */
    private List<File> resource;

    public String getSender() {
        return sender;
    }

    public ImDefinition setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ImDefinition setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public ImDefinition setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ImDefinition setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ImDefinition setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public File getAnnex() {
        return annex;
    }

    public ImDefinition setAnnex(File annex) {
        this.annex = annex;
        return this;
    }

    public List<File> getResource() {
        return resource;
    }

    public ImDefinition setResource(List<File> resource) {
        this.resource = resource;
        return this;
    }
}
