package com.zbf.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 袁成龙
 * @Description //TODO 配置本地的一些图片上传的访问路径
 * @Date 19:05 2020/9/23
 * @Param 
 * @return 
 **/
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    /**
     *资源配置
     * @param registry
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler("/pic/**")  访问的地址
        //addResourceLocations("file:D:/pic/image/");  实际指向的盘符
        registry.addResourceHandler("/pic/**").addResourceLocations("file:D:/pic/1801Dimage/","file:D:/pic/jarimage/");
    }


}
