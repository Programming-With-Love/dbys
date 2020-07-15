package com.danbai.ys;

import com.alibaba.fastjson.JSONObject;
import com.danbai.ys.entity.*;
import com.danbai.ys.mapper.VideoTimeMapper;
import com.danbai.ys.service.impl.DmServiceImpl;
import com.danbai.ys.service.impl.UserServiceImpl;
import com.danbai.ys.service.impl.YsServiceImpl;
import com.danbai.ys.utils.Md5;
import com.danbai.ys.utils.SiteMapUtils;
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
    @Autowired
    SiteMapUtils siteMapUtils;
    @Autowired
    VideoTimeMapper videoTimeMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    DmServiceImpl dmService;
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
    @Test
    public void getsite(){

        System.out.println(siteMapUtils.getSiteMap());
    }
    @Test
    public void appgx(){
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.hasUpdate=false;
        redisTemplate.opsForValue().set("appupdate", JSONObject.toJSONString(updateInfo));
    }
    @Test
    public void gkls(){
        ysService.getGkls("danbai");
    }
    @Test
    public void user(){
        System.out.println(Md5.getMD5LowerCase("是撒1"));
    }
    @Test
    public void getYsByType(){
        List<Ysb> byType = ysService.getByType("电影", "全部", "国产", "全部","评分",1);
        byType.forEach(ysb -> {
            System.out.println(ysb.getDy());
        });
    }
    @Test
    public void dmList(){
        PageResult<Dan> danPageResult = dmService.getDmListByYsUsername("danbai", 10, 1);
        danPageResult.getList().forEach(d->{
            System.out.println(d.getText());
        });
    }


}