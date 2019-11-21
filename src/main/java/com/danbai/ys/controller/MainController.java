package com.danbai.ys.controller;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.Config;
import com.danbai.ys.entity.Gkls;
import com.danbai.ys.entity.User;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.impl.*;
import com.danbai.ys.utils.SiteMapUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    AdminServiceImpl adminService;
    @Autowired
    CommImpl comm;
    @Autowired
    SiteMapUtils siteMapUtils;
    @ModelAttribute
    public void bif(Model model) {
        model.addAllAttributes(comm.getAllComm());
    }

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
    @RequestMapping(value = {"/sy"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    String indexApi(HttpServletRequest request) {
        PageInfo ysbs = ysService.getYs("电影", 1, 12);
        PageInfo ysbs1 = ysService.getYs("电视剧", 1, 12);
        PageInfo ysbs2 = ysService.getYs("综艺", 1, 12);
        PageInfo ysbs3 = ysService.getYs("动漫", 1, 12);
        Map<String, Object> map = new HashMap<>(10);
        map.put(Ysb.DY, ysService.qcsy(ysbs.getList()));
        map.put(Ysb.DSJ, ysService.qcsy(ysbs1.getList()));
        map.put(Ysb.ZY, ysService.qcsy(ysbs2.getList()));
        map.put(Ysb.DM, ysService.qcsy(ysbs3.getList()));
        map.put(Ysb.TJ, ysService.qcsy(ysService.tuijian()));
        map.put(Config.GG, adminService.getConfig(Config.GG));
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = {"/iflogin"}, produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    String ifLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute(User.DEFAULT_USER) != null) {
            return "yes";
        } else {
            return "no";
        }
    }

    @RequestMapping(value = "/adminlogin", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String adminLogi() {
        return "adminlogin";
    }

    @RequestMapping(value = "/adminlogin", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String adminLogiApi(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (userService.yzUser(user, request, response)) {
            return "redirect:admin";
        }
        model.addAttribute("message", "账号或密码错误");
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
        userService.reg(user, model, yzm);
        return "reg";
    }

    @RequestMapping(value = "/regapp", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    String regAppApi(User user, Model model, String yzm) {
        return userService.regapp(user, yzm);
    }

    @RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    String loginApi(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
        if (userService.yzUser(user, request, response)) {
            return "redirect:/";
        }
        model.addAttribute("message", "账号或密码错误");
        return "login";
    }

    @RequestMapping(value = "/loginapp", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    String loginAppApi(User user, HttpServletRequest request, HttpServletResponse response) {

        if (userService.yzUser(user, request, response)) {
            user.setPassword(null);
            return JSON.toJSONString(userService.getUser(user));
        }
        return "err";
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
        model.addAttribute("gks", gkls.size());
        if (gkls.size() > Gkls.MAX) {
            gkls = gkls.subList(0, 30);
        }
        model.addAttribute("gkls", gkls);
        return "person";
    }
    @GetMapping(value = "/sitemap.xml",produces = {"application/xml"})
    @ResponseBody
    public String getSiteMap() {
       return siteMapUtils.getSiteMap();
    }
}
