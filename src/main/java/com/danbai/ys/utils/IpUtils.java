package com.danbai.ys.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author danbai
 * @date 2019-10-14 15:50
 */
public class IpUtils {

    private static final int MAXIP=15;
    private static final String LOCALHOST ="127.0.0.1";
    private static final String UN="unknown";
    private static final String FGF=",";
    /**
     * 通过request访问者获取ip
     * @param request 请求
     * @return String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || UN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || UN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals(LOCALHOST)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > MAXIP){
            if(ip.indexOf(FGF)>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
