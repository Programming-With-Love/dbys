package com.danbai.ys.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.security.Security;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author danbai
 * @date 2019/10/13
 */
public class SendEmail {

    private static final String HOST = "smtp.163.com";
    private static final String PROTOCOL = "smtp";
    private static final int PORT = 465;
    private static final String FROM = "danbaiyingshi@163.com";
    private static final String PWD = "hjj225";

    private static JavaMailSenderImpl javaMailSender;
    static {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setProtocol(PROTOCOL);
        javaMailSender.setHost(HOST);
        //链接服务器
        javaMailSender.setPort(PORT);
        //默认使用25端口发送
        javaMailSender.setUsername(FROM);
        //账号
        javaMailSender.setPassword(PWD);
        //密码
        javaMailSender.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        //开启认证
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        //设置ssl端口
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailSender.setJavaMailProperties(properties);
    }

    /**
     * @方法名: sendEmail<br>
     * @描述: 发送邮件<br>
     * @创建者: lidongyang<br>
     * @修改时间: 2017年12月20日 下午3:59:15<br>
     * @param title
     * @param content
     * @param toMail 多个用英文格式逗号分隔
     */
    public static void sendEmail(final String title, final String content, final String toMail) {
        //开启线程异步发送  防止发送请求时间过长
        new Thread(new Runnable() {
            @Override
            public void run() {
                    SimpleMailMessage mailMessage = new SimpleMailMessage();
                    mailMessage.setFrom(FROM);
                    mailMessage.setSubject(title);
                    mailMessage.setText(content);

                    String[] toAddress = toMail.split(",");
                    mailMessage.setTo(toAddress);
                    //发送邮件
                    javaMailSender.send(mailMessage);
            }
        }).start();
    }
}