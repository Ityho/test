package com.miduchina.wrd.api.research;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@RefreshScope //动态更新配置
@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {"com.miduchina.wrd"})
@ServletComponentScan
public class ApiResearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiResearchApplication.class, args);
	}

}
