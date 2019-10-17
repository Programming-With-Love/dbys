package com.danbai.ys;

import com.danbai.ys.scheduler.Scheduler;
import com.danbai.ys.service.impl.YsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author danbai
 * @date 2019/10/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YsApplication.class)
public class YsApplicationTests {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    YsServiceImpl ysService;
    @Test
    public void redis() {
        //这里相当于redis对String类型的set操作
        redisTemplate.opsForValue().set("test","哈喽!");
        //这里相当于redis对String类型的get操作
        String test = (String)redisTemplate.opsForValue().get("test");
        System.out.println(test);
    }
}