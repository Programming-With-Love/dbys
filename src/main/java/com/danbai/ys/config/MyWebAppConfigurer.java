package com.danbai.ys.config;

import com.danbai.ys.interceptor.CountInterceptor;
import com.danbai.ys.interceptor.LoginInterceptor;
import com.danbai.ys.utils.SpringUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author danbai
 * @date 2019/10/13
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(SpringUtil.getBean(CountInterceptor.class)).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //添加映射路径
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                //是否发送Cookie信息
                .allowCredentials(true)
                //放行哪些原始域(请求方式)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                //放行哪些原始域(头部信息)
                .allowedHeaders("*");
    }
}
