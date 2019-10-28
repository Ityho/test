package com.miduchina.wrd.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@RefreshScope //动态更新配置
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan   //Servlet、Filter、Listener 可以直接通过 @WebServlet、@WebFilter、@WebListener 注解自动注册，无需其他代码
public class ApiInfoMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiInfoMonitorApplication.class, args);
	}

}
