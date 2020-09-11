package top.soliloquize.mail;

import java.util.Properties;

/**
 * @author wb
 * @date 2020/8/21
 */
public class MailProperties extends Properties {
    private String protocol;
    private String server;
    private int port;
    private boolean auth;
    private boolean ssl;

    public String getProtocol() {
        return protocol;
    }

    public MailProperties setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getServer() {
        return server;
    }

    public MailProperties setServer(String server) {
        this.server = server;
        return this;
    }

    public int getPort() {
        return port;
    }

    public MailProperties setPort(int port) {
        this.port = port;
        return this;
    }

    public boolean isAuth() {
        return auth;
    }

    public MailProperties setAuth(boolean auth) {
        this.auth = auth;
        return this;
    }

    public boolean isSsl() {
        return ssl;
    }

    public MailProperties setSsl(boolean ssl) {
        this.ssl = ssl;
        return this;
    }
}
