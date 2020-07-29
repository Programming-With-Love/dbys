package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.Token;
import com.danbai.ys.entity.User;
import com.danbai.ys.mapper.UserMapper;
import com.danbai.ys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Service
public class UserServiceImpl implements UserService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserMapper userMapper;
    @Autowired
    RegisterValidateServiceImpl registerValidateService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public User getUser(User user) {
        return userMapper.selectOne(user);
    }

    @Override
    public boolean addUser(User user) {
        if (userMapper.insert(user) > 0) {
            logger.info("有新的用户啦!--" + user.getUsername());
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
                if (DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()).equals(user2.getPassword())) {
                    HttpSession sessoin = request.getSession();
                    sessoin.setMaxInactiveInterval(60 * 60 * 24);
                    user2.setPassword("password");
                    sessoin.setAttribute("user", user2);
                    Cookie cookie = new Cookie("JSESSIONID", sessoin.getId());
                    cookie.setMaxAge(60 * 60 * 24 * 7);
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

            if (!Pattern.matches("^[\u4e00-\u9fa5_a-zA-Z0-9]+$", user.getUsername())) {
                model.addAttribute("message", "用户名不能有符号");
                return;
            }
            String str = registerValidateService.getVerificationCode(user.getEmail());
            if (str != null && str.equals(yzm)) {
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
                    user.setPassword(DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()));
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
            if (str != null && str.equals(yzm)) {
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
                    user.setPassword(DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()));
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

    @Override
    public Token createToken(String username) {
        String tokenid = UUID.randomUUID().toString().replace("-", "");
        Token token = new Token(username, tokenid);
        redisTemplate.opsForValue().set(Token.TOKEN + username, token, 7, TimeUnit.DAYS);
        return token;
    }

    @Override
    public boolean checkToken(Token token) {
        if (token == null || token.getUsername() == null || token.getToken() == null || token.getUsername().length() < 1 | token.getToken().length() < 1) {
            return false;
        }
        Token rtoken = (Token) redisTemplate.opsForValue().get(Token.TOKEN + token.getUsername());
        if (rtoken != null && rtoken.getToken().equals(token.getToken())) {
            // 如果验证成功，说明此用户进行了一次有效操作，延长 token 的过期时间
            redisTemplate.expire(Token.TOKEN + token.getUsername(), 7, TimeUnit.DAYS);
            return true;
        }
        return false;
    }

    @Override
    public void deleteToken(String username) {
        redisTemplate.delete(Token.TOKEN + username);
    }

    @Override
    public Token login(User user) {
        if (user != null) {
            User user1 = new User();
            user1.setUsername(user.getUsername());
            User user2 = getUser(user1);
            if (user2 != null) {
                if (DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()).equals(user2.getPassword())) {
                    return createToken(user.getUsername());
                }
            }
        }
        return null;
    }

    @Override
    public String forgetPass(User user, String yzm) {
        if (StringUtils.isEmpty(yzm)) {
            return "验证码不能为空";
        }
        if (user != null) {
            String str = registerValidateService.getVerificationCode(user.getEmail());
            if (str != null && str.equals(yzm)) {
                registerValidateService.deleteVerificationCode(user.getEmail());
                User user2 = new User();
                user2.setUsername(user.getUsername());
                User user1 = getUser(user2);
                if (!user1.getEmail().equals(user.getEmail())) {
                    return "用户名和邮箱不匹配";
                }
                user1.setPassword(DigestUtils.md5DigestAsHex((user.getUsername() + user.getPassword()).getBytes()));
                if (userMapper.updateByPrimaryKey(user1) == 1) {
                    //退出登录
                    deleteToken(user1.getUsername());
                    return "修改成功";
                }

            } else {
                return "验证码有误";
            }
        }
        return "请求数据有误，请检查";
    }
}
