package com.danbai.ys.service;

import com.danbai.ys.entity.User;

/**
 * @author danbai
 * @date 2019/10/13
 */
public interface UserService {
    /**
     * 根据传入用户的属性获取一个用户
     *
     * @param user 传入用户
     * @return User
     */
    User getUser(User user);

    /**
     * 根据传入用户新增一个用户
     *
     * @param user 传入用户
     * @return boolean
     */
    boolean addUser(User user);

    /**
     * 根据传入邮箱获取用户
     *
     * @param email 传入用户
     * @return boolean
     */
    User getUserByEmail(String email);

    /**
     * 给用户更新头像
     *
     * @param username 用户名
     * @param url      头像链接
     * @return boolean
     */
    boolean upheadimg(String username, String url);

    /**
     * 统计用户
     *
     * @return int
     */
    int contUser();
}
