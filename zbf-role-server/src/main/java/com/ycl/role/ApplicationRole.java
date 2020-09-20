package com.ycl.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:PACKAGE_NAME
 * @author:袁成龙
 * @createTime:2020/9/16 8:56
 * @version:1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ycl"})
class ApplicationRole {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRole.class);
    }
}