package com.danbai.ys.service.impl;

import com.danbai.ys.entity.Acces;
import com.danbai.ys.service.Statistical;
import com.danbai.ys.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        return redisTemplate.opsForSet().isMember(DateUtils.getDay(), ip);
    }

    @Override
    public void addIp(String ip) {
        redisTemplate.opsForSet().add(DateUtils.getDay(), ip);
        redisTemplate.opsForValue().increment(DateUtils.getDay() + "-Access");
        redisTemplate.expire(DateUtils.getDay(), 30, TimeUnit.DAYS);
        redisTemplate.expire(DateUtils.getDay() + "-Access", 30, TimeUnit.DAYS);
    }

    @Override
    public int getAccess() {
        return (int) redisTemplate.opsForValue().get("TotalAccess");
    }

    @Override
    public int getDayAccess() {
        return (int) redisTemplate.opsForValue().get(DateUtils.getDay() + "-Access");
    }

    @Override
    public List get30DayAccess() {
        List<Acces> list = new ArrayList<>();
        Date date = new Date();
        int yue = 29;
        for (int i = yue; i >= 0; i--) {
            try {
                Date rdate = DateUtils.dateAdd(date, -i, false);
                String s = DateUtils.dateFormat(rdate, DateUtils.DATE_PATTERN);
                Integer a = (Integer) redisTemplate.opsForValue().get(s + "-Access");
                Acces acces;
                if (a != null) {
                    acces = new Acces(s, a);
                } else {
                    acces = new Acces(s, 0);
                }
                list.add(acces);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
