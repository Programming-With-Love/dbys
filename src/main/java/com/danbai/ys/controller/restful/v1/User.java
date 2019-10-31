package com.danbai.ys.controller.restful.v1;

import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.service.UserService;
import com.danbai.ys.service.YsService;
import com.danbai.ys.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author danbai
 * @date 2019-10-29 20:01
 */
@RestController
@RequestMapping("/api/v1")
public class User {
    @Autowired
    UserService userService;
    @Autowired
    YsService ysService;
    /**
     * 获取当前用户信息
     * @param request 请求
     * @return BaseResult
     */
    @GetMapping("/user")
    public BaseResult thisUser(HttpServletRequest request){
        return ResultUtil.success(request.getSession().getAttribute("user"));
    }
    /**
     * 获取当前用户信息观看历史
     * @param request 请求
     * @return BaseResult
     */
    @GetMapping("/user/gkls")
    public BaseResult thisUserGkls(HttpServletRequest request){
        com.danbai.ys.entity.User user = (com.danbai.ys.entity.User)request.getSession().getAttribute("user");
        if(user!=null){
            return ResultUtil.success(ysService.getGkls(user.getUsername()));
        }
        return ResultUtil.error("未登陆");
    }
}
