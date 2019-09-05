package com.danbai.ys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.Ji;
import com.danbai.ys.entity.User;
import com.danbai.ys.entity.VideoTime;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.Impl.YsServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class YsController {
    @Autowired
    YsServiceImpl ysService;
    @RequestMapping(value = "/ys",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Ys(int id , Model model, HttpServletRequest request){
        Ysb ysb = ysService.selectYsById(id);
        model.addAttribute("ys",ysb);
        List<Ji> list;
        if(ysb.getXzdz().equals("[]")){
            list = JSONObject.parseArray(ysb.getGkdz(),Ji.class);
            model.addAttribute("gs",list);
            return "ys/index";
        }
        list = JSONObject.parseArray(ysb.getXzdz(),Ji.class);
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("xs",list);
        if(user!=null) {
            HashMap ysLs = ysService.getYsLs(user.getUsername(), id);
            if (ysLs != null) {
                model.addAttribute("ysid", id + (String) ysLs.get("jiname"));
                model.addAttribute("url", ysLs.get("url"));
                model.addAttribute("jiname",ysLs.get("jiname"));
                return "ys/index";
            }
        }
        model.addAttribute("ysid", id+list.get(0).getName());
        model.addAttribute("url",list.get(0).getUrl());
        model.addAttribute("jiname",list.get(0).getName());
        return "ys/index";
    }
    @RequestMapping(value = "/type/dy",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Dy(int page,Model model){
        PageInfo page1 = ysService.getYs("电影",page,20);
        model.addAttribute("ysb",page1.getList());
        model.addAttribute("zys",page1.getPages());
        model.addAttribute("page",page);
        return "type/dy";
    }
    @RequestMapping(value = "/type/dsj",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Dsj(int page,Model model){
        PageInfo page1 = ysService.getYs("电视剧",page,20);
        model.addAttribute("ysb",page1.getList());
        model.addAttribute("zys",page1.getPages());
        model.addAttribute("page",page);
        return "type/dsj";
    }
    @RequestMapping(value = "/type/dm",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Dm(int page,Model model){
        PageInfo page1 = ysService.getYs("动漫",page,20);
        model.addAttribute("ysb",page1.getList());
        model.addAttribute("zys",page1.getPages());
        model.addAttribute("page",page);
        return "type/dm";
    }
    @RequestMapping(value = "/type/zy",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Zy(int page,Model model){
        PageInfo page1 = ysService.getYs("综艺",page,20);
        model.addAttribute("ysb",page1.getList());
        model.addAttribute("zys",page1.getPages());
        model.addAttribute("page",page);
        return "type/zy";
    }
    @RequestMapping(value = "/search",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Search(String gjc,Model model){
        if (gjc.equals(""))
            return "/index";
        model.addAttribute("ysb",ysService.selectYsByPm(gjc));
        model.addAttribute("gjc",gjc);
        return "search";
    }
    @RequestMapping(value = "/ys/time",produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    @ResponseBody
    String ysTimeApi(VideoTime videoTime){
        if (videoTime.getUsername().equals("user")){
            return "ok";
        }
        ysService.addYsTime(videoTime);
        return "ok";
    }
    @RequestMapping(value = "/ys/gettime",produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    @ResponseBody
    String ysGetTimeApi(VideoTime videoTime){
        if (videoTime.getUsername().equals("user"))
            return "0";
        return String.valueOf(ysService.getYsTime(videoTime));
    }
}
