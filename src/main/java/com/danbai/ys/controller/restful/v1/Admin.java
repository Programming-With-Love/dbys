package com.danbai.ys.controller.restful.v1;

import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.service.impl.AdminServiceImpl;
import com.danbai.ys.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author danbai
 * @date 2019-10-31 10:14
 */
@RestController
@RequestMapping("/api/v1")
public class Admin {
    @Autowired
    AdminServiceImpl adminService;
    @PostMapping("/admin/config")
    public BaseResult updataConfig(String gg,String ylink, HttpServletRequest request){
        if (adminService.isAdmin(request)){
            adminService.updatagg(gg);
            adminService.updataYlink(ylink);
            return ResultUtil.successOk();
        }
        return ResultUtil.error("权限不足");
    }
}
