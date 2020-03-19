package com.danbai.ys.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * @author danbai
 * @date 2019-10-29 16:26
 */
@Component
@Service
public class EmailUtil {
    private static final String HOST = "smtpdm.aliyun.com";
    private static final String PROTOCOL = "smtp";
    private static final int PORT = 465;
    private static final String FROM = "dbys@p00q.cn";
    private static final String PWD = "";

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

    @Async
    public void sendEmail(final String title, final String content, final String toMail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            String nick=javax.mail.internet.MimeUtility.encodeText("淡白影视");
            mailMessage.setFrom(String.valueOf(new InternetAddress(nick+" <"+FROM+">")));
            mailMessage.setSubject(title);
            mailMessage.setText(content);
            String[] toAddress = toMail.split(",");
            mailMessage.setTo(toAddress);
            //发送邮件
            javaMailSender.send(mailMessage);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
