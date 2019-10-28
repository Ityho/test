package com.miduchina.wrd;

import com.netflix.discovery.EurekaClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class ApiServerCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiServerCenterApplication.class, args);
	}

//	@Bean
//	@Profile("default")
//	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
//		EurekaInstanceConfigBean b = new EurekaInstanceConfigBean(inetUtils);
//		log.info("ipAddress : {}",b.getIpAddress());
//		System.out.println("ipAddress : {}"+b.getIpAddress());
//		return b;
//	}
//
//	@Bean
//	@Profile("default")
//	public EurekaClientConfigBean eurekaClientConfigBean(ConfigurableEnvironment env) {
//		EurekaClientConfigBean client = new EurekaClientConfigBean();
//		if ("bootstrap".equals(env.getProperty("spring.config.name"))) {
//			// We don't register during bootstrap by default, but there will be another
//			// chance later.
//			client.setRegisterWithEureka(false);
//		}
//		return client;
//	}
}

