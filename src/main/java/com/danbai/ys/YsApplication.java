package com.danbai.ys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.danbai.ys.mapper")
@SpringBootApplication
public class YsApplication {

	public static void main(String[] args) {
		SpringApplication.run(YsApplication.class, args);
	}
}
