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
     */
    void senValidate(String email);

    /**
     * 根据邮箱获取验证码
     * @param email 邮箱
     * @return String
     */
    String getVerificationCode(String email);

    /**
     * 删除验证码
     * @param email 邮箱
     */
    void deleteVerificationCode(String email);
}
