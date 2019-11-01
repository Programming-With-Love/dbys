package com.danbai.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author danbai
 * @date 2019/10/13
 */
@MapperScan(basePackages = "com.danbai.ys.mapper")
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class YsApplication {
    public static void main(String[] args) {
        SpringApplication.run(YsApplication.class, args);
    }
}
