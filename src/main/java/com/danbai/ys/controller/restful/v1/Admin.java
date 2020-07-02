package com.danbai.ys.controller.restful.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.entity.Config;
import com.danbai.ys.service.impl.AdminServiceImpl;
import com.danbai.ys.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author danbai
 * @date 2019-10-31 10:14
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = "后台管理api")
public class Admin {
    @Autowired
    AdminServiceImpl adminService;

    @PostMapping("/admin/config")
    @ApiOperation(value = "配置修改api")
    public BaseResult updataConfig(String data, HttpServletRequest request) {
        if (adminService.isAdmin(request)) {
            JSONObject jsonObject = JSON.parseObject(data);
            List<Config> cs = new ArrayList<>();
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                cs.add(new Config(entry.getKey(), (String) entry.getValue()));
            }
            adminService.updataAllConfig(cs);
            return ResultUtil.successOk();
        }
        return ResultUtil.error("权限不足");
    }
}
