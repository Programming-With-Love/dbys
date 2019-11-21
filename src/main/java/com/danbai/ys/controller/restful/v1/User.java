package com.danbai.ys.controller.restful.v1;

import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.entity.Token;
import com.danbai.ys.service.UserService;
import com.danbai.ys.service.YsService;
import com.danbai.ys.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     *
     * @param request 请求
     * @return BaseResult
     */
    @GetMapping("/user")
    public BaseResult thisUser(HttpServletRequest request,Token token) {
        if(userService.checkToken(token)){
            com.danbai.ys.entity.User user = new com.danbai.ys.entity.User();
            user.setUsername(token.getUsername());
            return ResultUtil.success(userService.getUser(user));
        }
        return ResultUtil.success(request.getSession().getAttribute("user"));
    }

    /**
     * 获取当前用户信息观看历史
     *
     * @param request 请求
     * @return BaseResult
     */
    @GetMapping("/user/gkls")
    public BaseResult thisUserGkls(HttpServletRequest request,Token token) {
        if (userService.checkToken(token)){
            return ResultUtil.success(ysService.getGkls(token.getUsername()));
        }
        com.danbai.ys.entity.User user = (com.danbai.ys.entity.User) request.getSession().getAttribute("user");
        if (user != null) {
            return ResultUtil.success(ysService.getGkls(user.getUsername()));
        }
        return ResultUtil.error("未登陆");
    }
    @PostMapping("/token")
    public BaseResult token(com.danbai.ys.entity.User user){
        return ResultUtil.success(userService.login(user));
    }
    @DeleteMapping("/token")
    public BaseResult login(Token token){
        userService.deleteToken(token.getUsername());
        return ResultUtil.successOk();
    }
}
