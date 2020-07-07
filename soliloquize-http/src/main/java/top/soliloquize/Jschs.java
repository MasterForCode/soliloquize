package top.soliloquize;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author wb
 * @date 2020/7/3
 */
public class Jschs {

    private Session session;
    private String username;
    private String password;
    private String host;
    private int port = 22;
    private int maxRetry = 3;

    public Jschs(String username, String password, String host) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.session = connect(username, password, host, port);
    }

    public Jschs(String username, String password, String host, int port, int maxRetry) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.maxRetry = maxRetry;
        this.session = connect(username, password, host, port);
    }

    // source ~/.bash_profile
    // source /etc/profile;source ~/.bash_profile;source ~/.bashrc;java -version
    public static void main(String[] args) throws Exception {
//        Session session = connect("root", "wangbin_123", "192.168.88.126", 22);
//        String re = execCmdAndOutput("ls && ifconfig", "root", "wangbin_123", "192.168.88.125", 22);
//        log.error(re);
        Jschs t = new Jschs("root", "wangbin_123", "192.168.88.125");
//        Thread.sleep(110000);
        String re = t.execCmdAndOutput("ping");
        System.out.println(re);
    }

    public Session connect(String username, String password, String host, int port) {
        int retry = 1;
        JSch jsch = new JSch();
        try {
            session = jsch.getSession(username, host, port);
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("ssh会话失败");
            return session;
        }
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        //第一次登陆不需输入yes
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        do {
            System.out.println(String.format("ssh开始第%s次连接", retry));
            try {
                if (!session.isConnected()) {
                    session.connect();
                }
            } catch (JSchException e) {
                retry++;
                if (retry > this.maxRetry) {
                    e.printStackTrace();
                    System.out.println(String.format("%s次尝试连接到%s失败，可能连接远程端口：%s无效或用户名：%s密码：%s错误或者网络错误", this.maxRetry, host, port, username, password));
                    session.disconnect();
                }
            }
        } while (retry <= this.maxRetry && !session.isConnected());
        System.out.println("ssh会话连接成功");
        return session;
    }


    public String execCmdAndOutput(String command) {
        if (!session.isConnected()) {
            connect(username, password, host, port);
            if (!session.isConnected()) {
                return "ssh会话失败";
            }
        }
        ChannelExec channel = null;
        ByteArrayOutputStream baos = null;
        InputStream is = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            channel = (ChannelExec) session.openChannel("exec");
            channel.setInputStream(null);
            baos = new ByteArrayOutputStream();
            channel.setErrStream(baos);
            channel.setCommand(command);
            channel.connect();
            is = channel.getInputStream();
            byte[] tmp = new byte[1024];
            do {
                while (is.available() > 0) {
                    int i = is.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    stringBuilder.append(new String(tmp, 0, i));
                }
            } while (!channel.isClosed());
            if (baos.size() != 0) {
                stringBuilder.append(baos.toString());
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取流时发生错误");
            return "读取流时发生错误";
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println("执行命令发生异常");
            return "执行命令发生异常";
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
