package com.danbai.ys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.Acces;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.Statistical;
import com.danbai.ys.service.impl.StatisticalImpl;
import com.danbai.ys.service.impl.UserServiceImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminController {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    StatisticalImpl statistical;

    @RequestMapping(value = "/admin", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    String admin(Model model) {
        return "admin/index";
    }

    @RequestMapping(value = "/admin/index_v1", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
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
    String adminYstable(Model model) {
        return "admin/ystable";
    }

    @RequestMapping(value = "/admin/getysb", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
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
            re.put("total", p.getPageSize());
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
}