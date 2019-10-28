/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: Swagger2.java 
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年3月19日 下午7:14:04 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.hot.all.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: Swagger2
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年3月19日 下午7:14:04
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.miduchina.wrd.api.hot.all.controller")).paths(PathSelectors.any()).build();
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("全年热度 RESTful APIs").description("全年热度api接口文档").version("1.0").build();
	}
}
