package com.ycl.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.ycl.job
 * @author:袁成龙
 * @createTime:2020/9/29 8:29
 * @version:1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ycl"})
public class ApplicationJob {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationJob.class);
    }
}