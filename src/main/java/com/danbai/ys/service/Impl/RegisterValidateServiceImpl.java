package com.danbai.ys.service.Impl;

import com.danbai.ys.service.RegisterValidateService;
import com.danbai.ys.utils.SendEmail;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class RegisterValidateServiceImpl implements RegisterValidateService {
    @Override
    public String senValidate(String email) {
        if (email==null){
            return null;
        }
        Random r = new Random();
        String yzm="";
        for(int i=0;i<4;i++){
            yzm+=String.valueOf(r.nextInt(10));
        }
        String content="欢迎注册淡白影视,您的验证码是:"+yzm;
        SendEmail.send(email,content);
        return yzm;
    }
}
