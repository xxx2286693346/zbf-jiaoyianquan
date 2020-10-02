package com.ycl.job.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 每次记得修改mapper自己的扫描包
 */
@Configuration  //相当于写了个spring的xml配置
@MapperScan("com.ycl.job.mapper")
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor PaginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //设置拦截器分页参数
        return  paginationInterceptor;
    }


  /*  public static void main(String[] args) {
        String str = "2286693346@qq.com";
        String s = str.split("@")[1];
        System.out.println(s);
    }*/
}
