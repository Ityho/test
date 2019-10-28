package com.miduchina.wrd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Classname LargeSreenApplication
 * @Description TODO
 * @Date 2019/10/9 11:32
 * @Author ZhuFangTao
 */
@RefreshScope
@EnableEurekaClient
@EnableFeignClients
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
public class WebBigScreenApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBigScreenApplication.class,args);
    }
}
