package com.danbai.ys.interceptor;


import com.danbai.ys.service.impl.StatisticalImpl;
import com.danbai.ys.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author danbai
 * @date 2019-10-28 17:24
 */
@Component
public class CountInterceptor implements HandlerInterceptor {
    @Autowired
    StatisticalImpl statistical;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = IpUtils.getIpAddr(request);
        if (!statistical.isIpInTheDatabase(ip)) {
            logger.info("访问统计:欢迎ip为"+ip+"的用户访问");
            statistical.addIp(ip);
            statistical.addAccess();
        }
        return true;
    }
}

