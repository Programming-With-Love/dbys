package com.danbai.ys;

import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.impl.YsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


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
    public void test() {
        System.out.println(redisTemplate.opsForValue().get("gg"));
    }
    @Test
    public void tuijian(){
        List<Ysb> tuijian = ysService.tuijian();
        System.out.println(tuijian.size());
    }
}