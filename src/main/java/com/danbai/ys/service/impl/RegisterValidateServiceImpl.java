package com.danbai.ys.service.impl;

import com.danbai.ys.service.RegisterValidateService;
import com.danbai.ys.utils.IpUtils;
import com.danbai.ys.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class RegisterValidateServiceImpl implements RegisterValidateService {
    /**
     * 验证码位数
     */
    public static int MAXINT = 4;

    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public void senValidate(String email) {
        Random r = new Random();
        String yzm = "";
        for (int i = 0; i < MAXINT; i++) {
            yzm += String.valueOf(r.nextInt(10));
        }
        String content = "欢迎注册淡白影视,您的验证码是:" + yzm+",有效时间4分钟";
        SendEmail.send(email, content);
        redisTemplate.opsForValue().set(email,yzm,4, TimeUnit.MINUTES);
    }

    @Override
    public String getVerificationCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteVerificationCode(String email) {
        redisTemplate.opsForValue().set(email,0);
    }
}
