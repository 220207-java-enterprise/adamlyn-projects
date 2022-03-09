package com.revature.spring;


import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class SpringDriver {

    public static void main(String[] args) {
        SpringApplication.run(SpringDriver.class);
    }
}
