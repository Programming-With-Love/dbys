package com.danbai.ys.interceptor;


import com.danbai.ys.service.impl.StatisticalImpl;
import com.danbai.ys.utils.IpUtils;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String ip = IpUtils.getIpAddr(request);
        if (!statistical.isIpInTheDatabase(ip)) {
            statistical.addIp(ip);
            statistical.addAccess();
        }
        return true;
    }
}

