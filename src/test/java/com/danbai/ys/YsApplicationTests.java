package com.danbai.ys;

import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.HtmlUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;



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
    @Test
    public void test(){
        System.out.println(HtmlUtils.getHtmlContent("http://whatismyip.akamai.com/"));
        System.out.println(ysService.getYsDanMu("空降利刃",1));
    }
    @Test
    public void get(){
        System.out.println();
    }
}