/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: WebMvcConfig.java 
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.com.miduchina.wrd.com.miduchina.wrd.com.miduchina.wrd.api.rankinglist.config
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年3月19日 下午7:12:32 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfig
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年3月19日 下午7:12:32
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new FormatInterceptor()).addPathPatterns("/**/api/**");
//	}
	
}
