package com.sxbang.friday;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan(basePackages = "com.sxbang.friday.dao")
public class FridayApplication {
    public static void main(String[] args) {
        SpringApplication.run(FridayApplication.class, args);
    }
}
