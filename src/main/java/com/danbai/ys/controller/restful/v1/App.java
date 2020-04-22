package com.danbai.ys.controller.restful.v1;

import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.BaseResult;
import com.danbai.ys.entity.UpdateInfo;
import com.danbai.ys.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author danbai
 * @date 2019-11-14 17:53
 */
@RestController
@RequestMapping("/api/v1")
public class App {
    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/update")
    public BaseResult update() {
        return ResultUtil.success(JSONObject.parseObject((String) redisTemplate.opsForValue().get("appupdate"), UpdateInfo.class));
    }
    @GetMapping("/update-flutter")
    public JSONObject flutter() {
        return JSONObject.parseObject((String) redisTemplate.opsForValue().get("flutterAPPUpdate"));
    }
}
