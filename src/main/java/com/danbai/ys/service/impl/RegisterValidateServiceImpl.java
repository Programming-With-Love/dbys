package com.danbai.ys.service.impl;

import com.danbai.ys.service.RegisterValidateService;
import com.danbai.ys.utils.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Component
@Service
public class RegisterValidateServiceImpl implements RegisterValidateService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
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
        String content = "欢迎使用淡白影视,您的验证码是:" + yzm + ",有效时间4分钟";
        emailUtil.sendEmail("淡白影视验证码邮件", content, email);
        redisTemplate.opsForValue().set(email, yzm, 4, TimeUnit.MINUTES);
        logger.info("向邮箱"+email+"发送验证邮件");
    }
    @Override
    public String getVerificationCode(String email) {
        return (String) redisTemplate.opsForValue().get(email);
    }

    @Override
    public void deleteVerificationCode(String email) {
        redisTemplate.delete(email);
    }
}
