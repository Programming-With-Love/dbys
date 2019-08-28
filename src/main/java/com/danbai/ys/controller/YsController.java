package com.danbai.ys.controller;

import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.Impl.YsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class YsController {
    @Autowired
    YsServiceImpl ysService;
    @RequestMapping(value = "/ys",produces = "text/plain;charset=UTF-8",method= RequestMethod.GET)
    String Ys(int id , Model model){
        Ysb ysb = ysService.selectYsById(id);
        model.addAttribute("ys",ysb);
        return "ys/index";
    }
}
