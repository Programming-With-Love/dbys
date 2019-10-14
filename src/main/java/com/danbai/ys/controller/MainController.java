package com.danbai.ys.controller;

import com.danbai.ys.entity.Gkls;
import com.danbai.ys.entity.User;
import com.danbai.ys.service.impl.RegisterValidateServiceImpl;
import com.danbai.ys.service.impl.UserServiceImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Controller
public class MainController {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RegisterValidateServiceImpl registerValidateService;
    @RequestMapping(value = {"/", "index"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String index(Model model) {
        PageInfo ysbs = ysService.getYs("电影", 1, 12);
        PageInfo ysbs1 = ysService.getYs("电视剧", 1, 12);
        PageInfo ysbs2 = ysService.getYs("综艺", 1, 12);
        PageInfo ysbs3 = ysService.getYs("动漫", 1, 12);
        model.addAttribute("dy", ysbs.getList());
        model.addAttribute("dsj", ysbs1.getList());
        model.addAttribute("zy", ysbs2.getList());
        model.addAttribute("dm", ysbs3.getList());
        return "index";
    }

    @RequestMapping(value = "/adminlogin", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String adminLogi() {
        return "adminlogin";
    }

    @RequestMapping(value = "/adminlogin", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String adminLogiApi(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (user != null) {
            User user1 = new User();
            user1.setUsername(user.getUsername());
            User user2 = userService.getUser(user1);
            if (user2 != null) {
                if (DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(user2.getPassword())) {
                    if (user2.getUserType() == User.GENERAL) {
                        HttpSession sessoin = request.getSession();
                        sessoin.setMaxInactiveInterval(60 * 60 * 24);
                        sessoin.setAttribute("user", user2);
                        Cookie cookie = new Cookie("JSESSIONID", sessoin.getId());
                        cookie.setMaxAge(60 * 60 * 24);
                        response.addCookie(cookie);
                        return "redirect:admin";
                    }
                    model.addAttribute("message", "账号不是管理员账号");
                    return "adminlogin";
                }
            }
            model.addAttribute("message", "账号或密码错误");
        }
        return "adminlogin";
    }

    @RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String login() {
        return "login";
    }

    @RequestMapping(value = "/reg", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String reg() {
        return "reg";
    }

    @RequestMapping(value = "/reg", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String regApi(User user, Model model, String yzm) {
        if (user != null) {
            if (yzm == null) {
                model.addAttribute("message", "验证码有误");
                return "reg";
            }
            String str = registerValidateService.getVerificationCode(user.getEmail());
            if (str.equals(yzm)) {
                registerValidateService.deleteVerificationCode(user.getEmail());
                User user2 = new User();
                user2.setUsername(user.getUsername());
                User user1 = userService.getUser(user2);
                User user3 = userService.getUserByEmail(user.getEmail());
                if (user3 != null) {
                    model.addAttribute("message", "邮箱已存在");
                    return "reg";
                }
                if (user1 == null) {
                    user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
                    user.setUserType(1);
                    user.setHeadurl("http://gravatar.com/avatar/" + user.getUsername() + "?s=256&d=identicon");
                    if (userService.addUser(user)) {
                        model.addAttribute("message", "注册成功");
                        return "reg";
                    }
                } else {
                    model.addAttribute("message", "用户名已存在");
                }
            } else {
                model.addAttribute("message", "验证码有误");
            }
        }
        return "reg";
    }

    @RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String loginApi(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (user != null) {
            User user1 = new User();
            user1.setUsername(user.getUsername());
            User user2 = userService.getUser(user1);
            if (user2 != null) {
                if (DigestUtils.md5DigestAsHex(user.getPassword().getBytes()).equals(user2.getPassword())) {
                    HttpSession sessoin = request.getSession();
                    sessoin.setMaxInactiveInterval(60 * 60 * 24);
                    sessoin.setAttribute("user", user2);
                    Cookie cookie = new Cookie("JSESSIONID", sessoin.getId());
                    cookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(cookie);
                    return "redirect:/index";
                }
            }
            model.addAttribute("message", "账号或密码错误");
        }
        return "login";
    }

    @RequestMapping(value = "/logout", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "login";
    }

    @RequestMapping(value = "/hedimgupdate", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String hedimgupdate() {
        return "hedimgupdate";
    }

    @RequestMapping(value = "/hedimgupdate", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    String hedimgupdateApi(String url, HttpServletRequest servletRequest) {
        User user = (User) servletRequest.getSession().getAttribute("user");
        if (user == null) {
            return "err";
        }
        if (userService.upheadimg(user.getUsername(), url)) {
            user.setHeadurl(url);
            servletRequest.getSession().setAttribute("user", user);
            return "ok";
        }
        return "err";
    }

    @RequestMapping(value = "/person", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String person(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "login";
        }
        List<Gkls> gkls = ysService.getGkls(user.getUsername());
        if (gkls.size() > Gkls.MAX) {
            gkls = gkls.subList(0, 30);
        }
        model.addAttribute("gkls", gkls);
        model.addAttribute("gks", gkls.size());
        return "person";
    }
}
