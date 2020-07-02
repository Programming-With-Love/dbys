package com.danbai.ys.controller.restful.v1;

import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.entity.Feedback;
import com.danbai.ys.entity.UpdateInfo;

import com.danbai.ys.service.impl.AdminServiceImpl;
import com.danbai.ys.service.impl.CommImpl;
import com.danbai.ys.utils.ResultUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author danbai
 * @date 2019-11-14 17:53
 */
@RestController
@RequestMapping("/api/v1")
@ApiOperation(value ="app相关API", notes = "移动app")
public class App {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AdminServiceImpl adminService;
    @Autowired
    CommImpl comm;
    @GetMapping("/update")
    @ApiOperation(value ="app更新查询", notes = "安卓apk")
    public BaseResult update() {
        return ResultUtil.success(JSONObject.parseObject((String) redisTemplate.opsForValue().get("appupdate"), UpdateInfo.class));
    }
    @GetMapping("/update-flutter")
    @ApiOperation(value ="app更新查询", notes = "flutterApp")
    public JSONObject flutter() {
        return JSONObject.parseObject((String) redisTemplate.opsForValue().get("flutterAPPUpdate"));
    }
    @GetMapping("/gg")
    @ApiOperation(value ="app公告获取")
    public BaseResult gg() {
        return ResultUtil.success(adminService.getConfig("appGG"));
    }
    @PostMapping("/feedback")
    @ApiOperation(value ="反馈接口")
    public BaseResult feedback(Feedback feedback){
        feedback.setDispose(false);
        comm.addFeedback(feedback);
        return ResultUtil.successOk();
    }
}
