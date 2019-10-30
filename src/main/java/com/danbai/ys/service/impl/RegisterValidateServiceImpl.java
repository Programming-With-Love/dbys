package com.danbai.ys.service.impl;

import com.danbai.ys.service.RegisterValidateService;
import com.danbai.ys.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Component
@Service
public class RegisterValidateServiceImpl implements RegisterValidateService {


    /**
     * 验证码位数
     */
    public static int MAXINT = 8;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    EmailUtil emailUtil;
    @Override
    public void senValidate(String email) {
        Random r = new Random();
        String yzm = "";
        for (int i = 0; i < MAXINT; i++) {
            yzm += String.valueOf(r.nextInt(10));
        }
        String content = "欢迎注册淡白影视,您的验证码是:" + yzm+",有效时间4分钟";
        emailUtil.sendEmail("淡白影视注册邮件",content,email);
        redisTemplate.opsForValue().set(email,yzm,4, TimeUnit.MINUTES);
    }

    @Override
    public String getVerificationCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteVerificationCode(String email) {
        redisTemplate.delete(email);
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

}
