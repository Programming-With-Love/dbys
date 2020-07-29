package com.danbai.ys.service;

import com.danbai.ys.entity.Token;
import com.danbai.ys.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 验证user是否正确
     *
     * @param user     待验证user
     * @param request  请求
     * @param response 返回
     * @return boolean
     */
    boolean yzUser(User user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 注册
     *
     * @param user  用户信息
     * @param model 视图返回
     * @param yzm   验证码
     */
    void reg(User user, Model model, String yzm);

    /**
     * app注册
     *
     * @param user 用户信息
     * @param yzm  验证码
     * @return String
     */
    String regapp(User user, String yzm);
    /**
     * 关联用户和token
     * @param username 用户名
     * @return
     */
    Token createToken(String username);

    /**
     * 检测token有效性
     * @param token
     * @return
     */
    boolean checkToken(Token token);

    /**
     * 删除token
     * @param username
     */
    void deleteToken (String username);

    /**
     * token登陆
     * @param user
     * @return
     */
    Token login(User user);

    /**
     * 忘记密码通过邮箱修改
     * @param user
     * @param yzm
     * @return
     */
    String forgetPass(User user,String yzm);
}
