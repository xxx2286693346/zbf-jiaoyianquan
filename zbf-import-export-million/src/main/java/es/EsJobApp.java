package es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 作者: LCG
 * 日期: 2020/7/28 09:02
 * 描述:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EsJobApp {
    public static void main(String[] args) {
        SpringApplication.run(EsJobApp.class);
    }
}
