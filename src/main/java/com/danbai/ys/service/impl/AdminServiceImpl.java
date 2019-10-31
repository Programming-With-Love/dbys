package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.User;
import com.danbai.ys.entity.Ylink;
import com.danbai.ys.mapper.YlinkMapper;
import com.danbai.ys.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author danbai
 * @date 2019-10-31 10:17
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    YlinkMapper ylinkMapper;
    @Override
    public void updatagg(String gg) {
        redisTemplate.opsForValue().set("gg",gg);
    }
    @Override
    public boolean isAdmin(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if(user!=null&user.getUserType()==User.ADMIN){
            return true;
        }
        return false;
    }

    @Override
    public List<Ylink> getYlink() {
        return ylinkMapper.selectAll();
    }

    @Override
    public void updataYlink(String json) {
        List<Ylink> ylinks = JSON.parseArray(json, Ylink.class);
        ylinkMapper.deleteAll();
        if(ylinks.size()>0){
            ylinkMapper.insertList(ylinks);
        }
    }
}
