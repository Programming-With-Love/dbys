package com.danbai.ys.controller;

import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.Ji;
import com.danbai.ys.entity.User;
import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.impl.StatisticalImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.IpUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Controller
public class YsController {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    StatisticalImpl statistical;

    @ModelAttribute
    void count(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        if (!statistical.isIpInTheDatabase(ip)) {
            statistical.addIp(ip);
            statistical.addAccess();
        }
    }

    @RequestMapping(value = "/ys", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String ys(int id, Model model, HttpServletRequest request) {
        Ysb ysb = ysService.selectYsById(id);
        model.addAttribute("ys", ysb);
        List<Ji> list;
        String kong = "[]";
        if (kong.equals(ysb.getGkdz())) {
            list = JSONObject.parseArray(ysb.getXzdz(), Ji.class);
        } else {
            list = JSONObject.parseArray(ysb.getGkdz(), Ji.class);
        }
        model.addAttribute("xs", list);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            HashMap ysLs = ysService.getYsLs(user.getUsername(), id);
            if (ysLs != null) {
                model.addAttribute("ysid", id + (String) ysLs.get("jiname"));
                model.addAttribute("url", ysLs.get("url"));
                model.addAttribute("jiname", ysLs.get("jiname"));
                return "ys/index";
            }
        }
        model.addAttribute("ysid", id + list.get(0).getName());
        model.addAttribute("url", list.get(0).getUrl());
        model.addAttribute("jiname", list.get(0).getName());
        return "ys/index";
    }

    @RequestMapping(value = "/type/dy", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String dy(int page, Model model) {
        PageInfo page1 = ysService.getYs("电影", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dy";
    }

    @RequestMapping(value = "/type/dsj", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String dsj(int page, Model model) {
        PageInfo page1 = ysService.getYs("电视剧", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dsj";
    }

    @RequestMapping(value = "/type/dm", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String dm(int page, Model model) {
        PageInfo page1 = ysService.getYs("动漫", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dm";
    }

    @RequestMapping(value = "/type/zy", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String zy(int page, Model model) {
        PageInfo page1 = ysService.getYs("综艺", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/zy";
    }

    @RequestMapping(value = "/search", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String search(String gjc, Model model) {
        if ("".equals(gjc)) {
            return "/index";
        }
        model.addAttribute("ysb", ysService.selectYsByPm(gjc));
        model.addAttribute("gjc", gjc);
        return "search";
    }

    @RequestMapping(value = "/ys/time", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    String ysTimeApi(VideoTime videoTime) {
        String user = "user";
        if (user.equals(videoTime.getUsername())) {
            return "ok";
        }
        ysService.addYsTime(videoTime);
        return "ok";
    }

    @RequestMapping(value = "/ys/gettime", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    String ysGetTimeApi(VideoTime videoTime) {
        if (videoTime.getUsername().equals(User.DEFAULT_USER)) {
            return "0";
        }
        return String.valueOf(ysService.getYsTime(videoTime));
    }
}
