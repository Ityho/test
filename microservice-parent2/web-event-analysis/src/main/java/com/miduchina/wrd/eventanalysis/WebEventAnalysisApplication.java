package com.miduchina.wrd.eventanalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@RefreshScope //动态更新配置
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan   //Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
@ImportResource("classpath*:spring/spring.xml")
public class WebEventAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebEventAnalysisApplication.class, args);
	}

}

