package com.miduchina.wrd.api.hot.all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Classname ApiHotAllApplication
 * @Description TODO
 * @Date 2019/8/13 15:35
 * @Author ZhuFangTao
 */
@RefreshScope
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan
public class ApiHotAllApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiHotAllApplication.class,args);
    }
}
