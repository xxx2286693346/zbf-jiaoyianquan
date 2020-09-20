package com.ycl.deupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @projectName:zbf-jiaoyianquan
 * @see:com.ycl.deupload
 * @author:袁成龙
 * @createTime:2020/9/20 9:51
 * @version:1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.ycl"})
public class ApplicationDeUpload {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDeUpload.class);
    }

}