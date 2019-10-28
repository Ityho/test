package com.miduchina.wrd.api.hot.all.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.xd.intersys.filter.TimeInteceptor;
import com.xd.intersys.support.CommonInitDataListener;

/**
 * @Author: Xieyc
 * @Date: 2019/5/21 14:14
 */
@Configuration
public class WebInterceptorConfigurer implements WebMvcConfigurer {

	// 增加拦截器
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new TimeInteceptor()).addPathPatterns("/api/all/**");
//	}
//
//	@Bean
//	public CommonInitDataListener createCommonInitDataListener() {
//		return new CommonInitDataListener();
//	}


}
