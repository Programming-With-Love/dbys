package com.danbai.ys.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.Impl.UserServiceImpl;
import com.danbai.ys.service.Impl.YsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    YsServiceImpl ysService;
    @Autowired
    UserServiceImpl userService;
    @RequestMapping(value = "/admin",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String admin(Model model){
        return "/admin/index";
    }
    @RequestMapping(value = "/admin/index_v1",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String adminIndex(Model model){
        return "/admin/index_v1";
    }
    @RequestMapping(value = "/admin/ystable",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String adminYstable(Model model){
        return "/admin/ystable";
    }
    @RequestMapping(value = "/admin/getysb",produces = "application/json;charset=UTF-8",method= RequestMethod.GET)
    @ResponseBody
    String adminGetysb(@RequestParam("rows")Integer rows,@RequestParam("page")Integer page,String searchString,String searchField){
        JSONObject re = new JSONObject();
        if(searchString==null)
            searchString="";
        if (searchField==null)
            searchField="";
        if(searchString.equals("")&&searchField.equals("")){
            List<Ysb> yss = ysService.page(page, rows);
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(yss));
            int cons = ysService.contYs();
            int total;
            if (cons % page == 0) {
                total = cons / rows;
            } else {
                total = cons / rows + 1;
            }
            re.put("rows", array);
            re.put("page", page);
            re.put("total", total);
            re.put("records", cons);

        }else {
            List<Ysb> yss=new ArrayList<>();
            if (searchField.equals("id")){
                 yss=ysService.selectYsByIdlist(Integer.parseInt(searchString));

            }else {
                yss=ysService.selectYsByPm(searchString);
            }
            JSONArray array = JSONArray.parseArray(JSON.toJSONString(yss));
            re.put("rows",array);
            re.put("page", 1);
            re.put("total", 1);
            re.put("records", yss.size());
        }
        return re.toJSONString();
    }
    @RequestMapping(value = "/admin/ysedit",produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    @ResponseBody
    String adminYSedit(Ysb ysb,@RequestParam(value="oper", required=false)String oper){
        JSONObject re = new JSONObject();
        switch (oper){
            case "add":
                ysService.addYs(ysb);
                break;
            case"edit":
                ysService.update(ysb);
                break;
            case "del":
                ysService.delYs(ysb);
        }
        return re.toJSONString();
    }
}