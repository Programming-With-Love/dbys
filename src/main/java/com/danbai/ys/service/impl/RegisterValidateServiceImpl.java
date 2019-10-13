package com.danbai.ys.service.impl;

import com.danbai.ys.service.RegisterValidateService;
import com.danbai.ys.utils.SendEmail;
import org.springframework.stereotype.Service;

import java.util.Random;

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

    @Override
    public String senValidate(String email) {
        if (email == null) {
            return null;
        }
        Random r = new Random();
        String yzm = "";
        for (int i = 0; i < MAXINT; i++) {
            yzm += String.valueOf(r.nextInt(10));
        }
        String content = "欢迎注册淡白影视,您的验证码是:" + yzm;
        SendEmail.send(email, content);
        return yzm;
    }
}
