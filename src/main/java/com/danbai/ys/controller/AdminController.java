package com.danbai.ys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.*;
import com.danbai.ys.service.impl.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Controller
@Api(tags = "管理api")
public class AdminController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    StatisticalImpl statistical;
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    DmServiceImpl dmService;
    @Autowired
    CommImpl comm;

    @RequestMapping(value = "/admin", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "管理视图框架首页")
    String admin() {
        return "admin/index";
    }

    @RequestMapping(value = "/admin/index_v1", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "管理视图首页")
    String adminIndex(Model model) {
        model.addAttribute("yssize", ysService.contYs());
        model.addAttribute("usersize", userService.contUser());
        model.addAttribute("totalAccess", statistical.getAccess());
        model.addAttribute("dayAccess", statistical.getDayAccess());
        List<Acces> dayAccess = statistical.get30DayAccess();
        List<String> times = new ArrayList<>();
        List<Integer> is = new ArrayList<>();
        for (Acces a : dayAccess) {
            times.add(a.getName());
            is.add(a.getCount());
        }
        model.addAttribute("times", JSON.toJSONString(times));
        model.addAttribute("is", JSON.toJSONString(is));
        return "admin/index_v1";
    }

    @RequestMapping(value = "/admin/ystable", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "影视表视图")
    String adminYstable() {
        return "admin/ystable";
    }

    @RequestMapping(value = "/admin/dmtable", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "弹幕表视图")
    String adminDmtable() {
        return "admin/dmtable";
    }

    @RequestMapping(value = "/admin/feedback", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "反馈处理视图")
    String adminFeedback(Model model) {
        List<Feedback> allFeedback = comm.getAllFeedback();
        List tList = new ArrayList();
        List fList = new ArrayList();
        allFeedback.forEach(b -> {
            if (b.getDispose()) {
                tList.add(b);
            } else {
                fList.add(b);
            }
            model.addAttribute("tlist", tList);
            model.addAttribute("flist", fList);
        });
        return "admin/fb";
    }

    @RequestMapping(value = "/admin/getysb", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取影视表api")
    String adminGetysb(@RequestParam("rows") Integer rows, @RequestParam("page") Integer page, String searchString,
                       String searchField) {
        JSONObject re = new JSONObject();
        if (searchString == null) {
            searchString = "";
        }
        if (searchField == null) {
            searchField = "";
        }
        if ("".equals(searchString) && "".equals(searchField)) {
            PageInfo p = ysService.getYs("全部", page, rows);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(p.getList()));
            re.put("rows", array);
            re.put("page", p.getPageNum());
            re.put("total", p.getPages());
            re.put("records", ysService.contYs());

        } else {
            List<Ysb> yss;
            String id = "id";
            if (id.equals(searchField)) {
                yss = ysService.selectYsByIdlist(Integer.parseInt(searchString));

            } else {
                yss = ysService.selectYsByPm(searchString);
            }
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(yss));
            re.put("rows", array);
            re.put("page", 1);
            re.put("total", 1);
            re.put("records", yss.size());
        }
        return re.toJSONString();
    }

    @RequestMapping(value = "/admin/ysedit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "影视编辑api")
    String adminYsedit(Ysb ysb, @RequestParam(value = "oper", required = false) String oper) {
        JSONObject re = new JSONObject();
        //noinspection AlibabaSwitchStatement,AlibabaSwitchStatement
        switch (oper) {
            case "add":
                ysService.addYs(ysb);
                break;
            case "edit":
                ysService.update(ysb);
                break;
            case "del":
                ysService.delYs(ysb);
                break;
            default:
        }
        return re.toJSONString();
    }

    @RequestMapping(value = "/admin/config", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "配置视图")
    String adminConfig(Model model) {
        List<Config> config = adminService.getConfig();
        for (Config c : config) {
            model.addAttribute(c.getItem(), c.getValue());
        }
        return "admin/config";
    }

    @RequestMapping(value = "/admin/getdmb", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取弹幕表api")
    String adminGetDmb(@RequestParam("rows") Integer rows, @RequestParam("page") Integer page, String searchString,
                       String searchField) {
        JSONObject re = new JSONObject();
        if (searchString == null) {
            searchString = "";
        }
        if (searchField == null) {
            searchField = "";
        }
        PageResult<Dan> dmList = null;
        if ("".equals(searchString) && "".equals(searchField)) {
            dmList = dmService.getDmList(rows, page);
        } else {
            switch (searchField) {
                case "id":
                    dmList = dmService.getDmListById(searchString, rows, page);
                    break;
                case "author":
                    dmList = dmService.getDmListByYsUsername(searchString, rows, page);
                    break;
                case "player":
                    dmList = dmService.getDmListByYsJi(searchString, rows, page);
                    break;
                default:
                    dmList = dmService.getDmList(rows, page);
            }
        }
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(dmList.getList()));
        re.put("rows", array);
        re.put("page", page);
        re.put("total", dmList.getPages());
        re.put("records", dmList.getTotal());
        return re.toJSONString();
    }

    @RequestMapping(value = "/admin/dmedit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "弹幕编辑api")
    String adminDmedit(Dan dan, @RequestParam(value = "oper", required = false) String oper) {
        JSONObject re = new JSONObject();
        switch (oper) {
            case "add":
                dmService.addDm(dan);
                break;
            case "edit":
                dmService.updateDm(dan);
                break;
            case "del":
                dmService.delDm(dan);
                break;
            default:
        }
        return re.toJSONString();
    }

    @RequestMapping(value = "/admin/okfb", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "完成反馈处理")
    void adminGetDmb(Integer id) {
        comm.okFeedback(id);
    }
}