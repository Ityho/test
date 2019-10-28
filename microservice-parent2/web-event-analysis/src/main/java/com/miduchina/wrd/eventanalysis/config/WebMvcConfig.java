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
package com.miduchina.wrd.eventanalysis.config;

import com.miduchina.wrd.eventanalysis.filter.AutoAuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName: WebMvcConfig
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年3月19日 下午7:12:32
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new AutoAuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**").excludePathPatterns("/toWxAuth");
        registry.addInterceptor(getMyInterceptor()).excludePathPatterns("/toWxAuth").excludePathPatterns("/view/user/wxAuth.action").excludePathPatterns("/token").excludePathPatterns("/pay/*").excludePathPatterns("/error");
    }


}
