package com.danbai.ys.scheduler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.async.Dmas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author danbai
 * @date 2019-10-16 18:33
 */
@Component
@Configurable
public class Scheduler {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private Dmas as;
    @Async
    @Scheduled(fixedDelay = 60000)
    public void cronJobScheduleDm() {
        Set tagids = redisTemplate.opsForSet().members("tagids");
        redisTemplate.delete("tagids");
        Object[] das = tagids.toArray();
        for (Object s : das) {
            JSONObject jsonObject = JSON.parseObject(String.valueOf(s));
            String tagid = jsonObject.getString("tagid");
            String player = (jsonObject.getString("player"));
            int timestamp = 0;
            boolean flg = true;
            while (flg) {
                String url = "http://mfm.video.qq.com/danmu?otype=json&target_id=" + tagid + "&timestamp=" + timestamp;
                timestamp += 30;
                as.xzbcdm(url, player);
                if (timestamp > 60 * 120) {
                    flg = false;
                }
            }
            redisTemplate.opsForSet().add("oktagids", tagid);
            redisTemplate.delete("danmaku" + player);
        }
    }
}