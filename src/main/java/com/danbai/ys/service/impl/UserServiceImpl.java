package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.User;
import com.danbai.ys.mapper.UserMapper;
import com.danbai.ys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegisterValidateServiceImpl registerValidateService;

    @Override
    public User getUser(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public boolean addUser(User user) {
        if (userMapper.insert(user) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public boolean upheadimg(String username, String url) {
        User user = new User();
        user.setUsername(username);
        User user1 = userMapper.selectOne(user);
        user1.setHeadurl(url);
        if (userMapper.updateByPrimaryKey(user1) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public int contUser() {
        Example example = new Example(User.class);
        return userMapper.selectCountByExample(example);
    }

    @Override
    public boolean yzUser(User user, HttpServletRequest request, HttpServletResponse response) {
        if (user != null) {
            User user1 = new User();
            user1.setUsername(user.getUsername());
            User user2 = getUser(user1);
            if (user2 != null) {
                if (DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(user2.getPassword())) {
                    HttpSession sessoin = request.getSession();
                    sessoin.setMaxInactiveInterval(60 * 60 * 24);
                    sessoin.setAttribute("user", user2);
                    Cookie cookie = new Cookie("JSESSIONID", sessoin.getId());
                    cookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void reg(User user, Model model, String yzm) {
        if (user != null) {
            if (yzm == null) {
                model.addAttribute("message", "验证码有误");
                return;
            }
            String str = registerValidateService.getVerificationCode(user.getEmail());
            if (str != null & str.equals(yzm)) {
                registerValidateService.deleteVerificationCode(user.getEmail());
                User user2 = new User();
                user2.setUsername(user.getUsername());
                User user1 = getUser(user2);
                User user3 = getUserByEmail(user.getEmail());
                if (user3 != null) {
                    model.addAttribute("message", "邮箱已存在");
                    return;
                }
                if (user1 == null) {
                    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                    user.setUserType(1);
                    user.setHeadurl("http://gravatar.com/avatar/" + user.getUsername() + "?s=256&d=identicon");
                    if (addUser(user)) {
                        model.addAttribute("message", "注册成功");
                        return;
                    }
                } else {
                    model.addAttribute("message", "用户名已存在");
                }
            } else {
                model.addAttribute("message", "验证码有误");
            }
        }
        return;
    }

    @Override
    public String regapp(User user, String yzm) {
        Map<String, String> map = new HashMap<>(3);
        if (yzm == null) {
            map.put("message", "验证码有误");
            return JSON.toJSONString(map);
        }
        if (user != null) {
            String str = registerValidateService.getVerificationCode(user.getEmail());
            if (str != null & str.equals(yzm)) {
                registerValidateService.deleteVerificationCode(user.getEmail());
                User user2 = new User();
                user2.setUsername(user.getUsername());
                User user1 = getUser(user2);
                User user3 = getUserByEmail(user.getEmail());
                if (user3 != null) {
                    map.put("message", "邮箱已存在");
                    return JSON.toJSONString(map);
                }
                if (user1 == null) {
                    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                    user.setUserType(1);
                    user.setHeadurl("http://gravatar.com/avatar/" + user.getUsername() + "?s=256&d=identicon");
                    if (addUser(user)) {
                        map.put("message", "注册成功");
                        map.put("zt", "ok");
                        return JSON.toJSONString(map);
                    }
                } else {
                    map.put("message", "用户名已存在");
                }
            } else {
                map.put("message", "验证码有误");
            }
        }
        return JSON.toJSONString(map);
    }

}
