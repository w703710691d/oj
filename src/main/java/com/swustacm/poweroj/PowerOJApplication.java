package com.swustacm.poweroj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author lixiaobo
 */
@MapperScan(basePackages = "com.swustacm.poweroj.mapper.**")
@SpringBootApplication
public class PowerOJApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PowerOJApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(PowerOJApplication.class, args);
    }

}
