package com.danbai.ys.controller;

import com.danbai.ys.service.impl.RegisterValidateServiceImpl;
import com.danbai.ys.utils.SiteMapUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Controller
@Api(tags = "工具请求")
public class UtilsController {
    @Autowired
    RegisterValidateServiceImpl registerValidateService;
    @Autowired
    SiteMapUtils siteMapUtils;

    @RequestMapping(value = "/getvalidate", produces = "text/plain;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "请求验证码", notes = "用户注册时的邮箱验证码")
    public String getValidate(String username, String email) {
        if (username == null) {
            return "err";
        }
        registerValidateService.senValidate(email);
        return "ok";
    }

    @GetMapping(value = "/sitemap.xml", produces = {"application/xml"})
    @ResponseBody
    @ApiOperation(value = "站点地图")
    public String getSiteMap() {
        return siteMapUtils.getSiteMap();
    }
}
