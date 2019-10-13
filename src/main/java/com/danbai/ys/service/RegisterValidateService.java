package com.danbai.ys.service;

/**
 * @author danbai
 * @date 2019/10/13
 */
public interface RegisterValidateService {
    /**
     * 发送注册验证码
     *
     * @param email 接收邮箱
     * @return String
     */
    String senValidate(String email);
}
