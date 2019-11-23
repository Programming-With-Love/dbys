package com.danbai.ys.service.impl;

import com.alibaba.fastjson.JSON;
import com.danbai.ys.entity.Config;
import com.danbai.ys.entity.User;
import com.danbai.ys.entity.Ylink;
import com.danbai.ys.mapper.ConfigMapper;
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
    static String YES="yes";
    static String NO="no";
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(User.DEFAULT_USER);
        if (user != null && user.getUserType() == User.ADMIN) {
            return true;
        }
        return false;
    }

    @Override
    public List<Ylink> getYlink() {
        return JSON.parseArray(configMapper.selectOne(new Config(Config.YLINK)).getValue(), Ylink.class);
    }

    @Override
    public void updataYlink(String json) {
        Config ylink = new Config("ylink", json);
        configMapper.updateByPrimaryKey(ylink);
    }

    @Override
    public List<Config> getConfig() {
        return configMapper.selectAll();
    }

    @Override
    public void updataAllConfig(List<Config> list) {
        configMapper.deleteAll();
        for (Config c : list) {
            configMapper.insert(c);
        }
    }

    @Override
    public String getConfig(String name) {
        return configMapper.selectOne(new Config(name)).getValue();
    }

    @Override
    public void updataConfig(String name, String value) {
        configMapper.updateByPrimaryKey(new Config(name, value));
    }
}
