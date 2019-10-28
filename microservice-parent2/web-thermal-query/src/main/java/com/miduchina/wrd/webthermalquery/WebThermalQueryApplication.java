package com.miduchina.wrd.webthermalquery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@RefreshScope //动态更新配置
@EnableEurekaClient
@EnableFeignClients
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan   //Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
public class WebThermalQueryApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebThermalQueryApplication.class, args);
	}


}
