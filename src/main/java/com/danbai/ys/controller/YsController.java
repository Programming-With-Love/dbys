package com.danbai.ys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.*;
import com.danbai.ys.service.impl.AdminServiceImpl;
import com.danbai.ys.service.impl.CommImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.DateUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Controller
@Api(tags = "影视请求")
public class YsController {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    CommImpl comm;

    @ModelAttribute
    public void bif(Model model) {
        model.addAllAttributes(comm.getAllComm());
    }

    @ApiOperation(value = "影视详情页", notes = "根据 id 展示影视")
    @RequestMapping(value = "/ys", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String ys(Integer id, Model model, HttpServletRequest request) {
        Ysb ysb = ysService.selectYsById(id);
        model.addAttribute("ys", ysb);
        String tagpm = ysb.getPm() + ysb.getDy() + ysb.getLx();
        model.addAttribute("tagpm", tagpm);
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

    @RequestMapping(value = "/getys", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "影视详情Json", notes = "根据 id 返回影视Json")
    String getYsApi(Integer id, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(5);
        Ysb ys = ysService.selectYsById(id);
        map.put("ys", ys);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            HashMap ysLs = ysService.getYsLs(user.getUsername(), id);
            if (ysLs != null) {
                map.put("gkls", ysLs);
                VideoTime videoTime = new VideoTime();
                videoTime.setUsername(user.getUsername());
                videoTime.setYsid(id);
                videoTime.setYsjiname((String) ysLs.get("jiname"));
                map.put("time", ysService.getYsTime(videoTime));
                return JSON.toJSONString(map);
            }
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/gettypeys", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "类型影视分页", notes = "根据类型获取影视Json")
    String getTypeYsApi(String type, Integer page) {
        PageInfo page1 = ysService.getYs(type, page, 24);
        Map<String, Object> map = new HashMap<>(10);
        map.put("list", ysService.qcsy(page1.getList()));
        map.put("zys", page1.getPages());
        map.put("page", page);
        map.put("total", page1.getTotal());
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/type/dy", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "电影视图")
    String dy(Integer page, Model model) {
        PageInfo page1 = ysService.getYs("电影", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dy";
    }

    @RequestMapping(value = "/type/dsj", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "电视剧视图")
    String dsj(Integer page, Model model) {
        PageInfo page1 = ysService.getYs("电视剧", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dsj";
    }

    @RequestMapping(value = "/type/tv", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "Tv视图")
    String tv(Model model) {
        model.addAttribute("tvs", ysService.getAllTv());
        return "type/tv";
    }

    @RequestMapping(value = "/type/dm", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "动漫视图")
    String dm(Integer page, Model model) {
        PageInfo page1 = ysService.getYs("动漫", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/dm";
    }

    @RequestMapping(value = "/type/zy", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "综艺视图")
    String zy(Integer page, Model model) {
        PageInfo page1 = ysService.getYs("综艺", page, 24);
        model.addAttribute("ysb", page1.getList());
        model.addAttribute("zys", page1.getPages());
        model.addAttribute("page", page);
        return "type/zy";
    }

    @RequestMapping(value = "/search", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "搜索视图")
    String search(String gjc, Model model) {
        if (gjc.length() == 0) {
            return "redirect:/";
        }
        if (gjc.length() > 1) {
            model.addAttribute("ysb", ysService.selectYsByGjc(gjc));
        } else {
            model.addAttribute("ysb", ysService.selectYsByPm(gjc));
        }
        model.addAttribute("gjc", gjc);
        return "search";
    }

    @RequestMapping(value = "/ys/time", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "观看时间api", notes = "更新观看时间")
    void ysTimeApi(VideoTime videoTime, HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute(User.DEFAULT_USER);
        if (u == null) {
            return;
        }
        videoTime.setUsername(u.getUsername());
        if (User.DEFAULT_USER.equals(videoTime.getUsername())) {
            return;
        }
        ysService.addYsTime(videoTime);
    }

    @RequestMapping(value = "/ys/gettime", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取影视观看时间", notes = "获取用户某个影视的观看进度")
    String ysGetTimeApi(VideoTime videoTime) {
        if (videoTime.getUsername().equals(User.DEFAULT_USER)) {
            return "0";
        }
        return String.valueOf(ysService.getYsTime(videoTime));
    }

    @RequestMapping(value = "/ys/gettagid", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取影视弹幕tagid")
    String ysGetTagIdApi(@RequestParam(value = "pm", required = true) String pm, @RequestParam(value = "ysid", required = true) String ysid) {
        return ysService.getYsDanMu(pm, ysid);
    }
}
