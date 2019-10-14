package com.danbai.ys.service.impl;

import com.danbai.ys.service.Statistical;
import com.danbai.ys.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author danbai
 * @date 2019-10-14 15:30
 */
@Service
public class StatisticalImpl implements Statistical {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void addAccess() {
        redisTemplate.opsForValue().increment("TotalAccess");
    }

    @Override
    public boolean isIpInTheDatabase(String ip) {
        return redisTemplate.opsForSet().isMember(IpUtils.getDay(), ip);
    }
    @Override
    public void addIp(String ip) {
        redisTemplate.opsForSet().add(IpUtils.getDay(), ip);
        redisTemplate.opsForValue().increment(IpUtils.getDay() + "-Access");
        redisTemplate.expire(IpUtils.getDay(),1,TimeUnit.DAYS);
        redisTemplate.expire(IpUtils.getDay() + "-Access",1,TimeUnit.DAYS);
    }

    @Override
    public int getAccess() {
        return (int) redisTemplate.opsForValue().get("TotalAccess");
    }

    @Override
    public int getDayAccess() {
        return (int) redisTemplate.opsForValue().get(IpUtils.getDay() + "-Access");
    }
}
