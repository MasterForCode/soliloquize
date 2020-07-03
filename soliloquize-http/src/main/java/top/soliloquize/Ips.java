package top.soliloquize;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

/**
 * @author wb
 * @date 2020/7/1
 */
public class Ips {
    private static final String IP6_FLAG = ":";
    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    /**
     * 获取真实IP
     *
     * @param request HttpServletRequest
     * @return IP
     */
    public static String getIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteHost();
    }

    /**
     * 将字符串形式的ip地址转换为BigInteger
     *
     * @param ipInString 字符串形式的ip地址
     * @return 整数形式的ip地址
     */
    private static BigInteger stringToBigInt(String ipInString) {
        ipInString = ipInString.replace(" ", "");
        byte[] bytes;
        if (ipInString.contains(IP6_FLAG)) {
            bytes = ipv6ToBytes(ipInString);
        } else {
            bytes = ipv4ToBytes(ipInString);
        }
        return new BigInteger(bytes);
    }

    /**
     * ipv6地址转有符号byte[17]
     *
     * @param ipv6 字符串形式的IP地址
     * @return big integer number
     */
    private static byte[] ipv6ToBytes(String ipv6) {
        byte[] ret = new byte[17];
        ret[0] = 0;
        int ib = 16;
        // ipv4混合模式标记
        boolean comFlag = false;
        // 去掉开头的冒号
        if (ipv6.startsWith(IP6_FLAG)) {
            ipv6 = ipv6.substring(1);
        }
        String[] groups = ipv6.split(IP6_FLAG);
        // 反向扫描
        for (int ig = groups.length - 1; ig > -1; ig--) {
            if (groups[ig].contains(".")) {
                // 出现ipv4混合模式
                byte[] temp = ipv4ToBytes(groups[ig]);
                ret[ib--] = temp[4];
                ret[ib--] = temp[3];
                ret[ib--] = temp[2];
                ret[ib--] = temp[1];
                comFlag = true;
            } else if ("".equals(groups[ig])) {
                // 出现零长度压缩,计算缺少的组数
                int zlg = 9 - (groups.length + (comFlag ? 1 : 0));
                // 将这些组置0
                while (zlg-- > 0) {
                    ret[ib--] = 0;
                    ret[ib--] = 0;
                }
            } else {
                int temp = Integer.parseInt(groups[ig], 16);
                ret[ib--] = (byte) temp;
                ret[ib--] = (byte) (temp >> 8);
            }
        }
        return ret;
    }

    /**
     * ipv4地址转有符号byte[5]
     *
     * @param ipv4 字符串的IPV4地址
     * @return big integer number
     */
    private static byte[] ipv4ToBytes(String ipv4) {
        byte[] ret = new byte[5];
        ret[0] = 0;
        // 先找到IP地址字符串中.的位置
        int position1 = ipv4.indexOf(".");
        int position2 = ipv4.indexOf(".", position1 + 1);
        int position3 = ipv4.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ret[1] = (byte) Integer.parseInt(ipv4.substring(0, position1));
        ret[2] = (byte) Integer.parseInt(ipv4.substring(position1 + 1,
                position2));
        ret[3] = (byte) Integer.parseInt(ipv4.substring(position2 + 1,
                position3));
        ret[4] = (byte) Integer.parseInt(ipv4.substring(position3 + 1));
        return ret;
    }

    /**
     * @param ip 要限制的Ip 包括Ipv6
     * @return Boolean true通过
     * false 受限制
     */
    public static boolean isIp(String ip, String[][] range) {
        boolean flag = false;
        //intIp ip 转bigInt
        BigInteger intIp = Ips.stringToBigInt(ip);

        for (String[] strings : range) {
            for (int j = 0; j < strings.length; j++) {
                // startIntIp: 起始ip转bigInt
                BigInteger startIntIp = Ips.stringToBigInt(strings[j]);
                j = j + 1;
                // endIntIp: 截止ip转bigInt
                BigInteger endIntIp = Ips.stringToBigInt(strings[j]);
                //将大数进行比较
                //如果相等则退出循环
                if ((intIp.compareTo(startIntIp)) == 0) {
                    flag = true;
                    break;
                }
                //如果不相等则比较大小，在区间内正常
                if (((intIp.compareTo(startIntIp)) >= 0) && ((intIp.compareTo(endIntIp)) <= 0)) {
                    flag = true;
                    break;
                }

            }
        }
        return flag;
    }
}
