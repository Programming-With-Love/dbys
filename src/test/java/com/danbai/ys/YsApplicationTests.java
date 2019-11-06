package com.danbai.ys;

import com.danbai.ys.entity.Ysb;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.HtmlUtils;
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
@SpringBootTest(classes = YsApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
    @Test
    public void sou(){
        List<Ysb> ysbs = ysService.selectYsByGjc("胡歌");
        for (Ysb ysb:ysbs) {
            System.out.println(ysb.getPm());
        }
    }
    @Test
    public void get(){

        System.out.println(ysService.getAllTv().get(5).getId());
    }
}