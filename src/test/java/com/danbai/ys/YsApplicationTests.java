package com.danbai.ys;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Test
    public void test() {
        System.out.println(redisTemplate.opsForValue().get("gg"));
    }
}