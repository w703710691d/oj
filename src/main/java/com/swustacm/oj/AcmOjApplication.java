package com.swustacm.oj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author lixiaobo
 */
@MapperScan(basePackages = "com.swustacm.oj.mapper.**")
@SpringBootApplication
public class AcmOjApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AcmOjApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(AcmOjApplication.class, args);
    }

}
