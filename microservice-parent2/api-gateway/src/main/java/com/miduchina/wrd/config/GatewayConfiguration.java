package com.miduchina.wrd.config;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 配置限流key
 */
@Slf4j
@Configuration
public class GatewayConfiguration {
    /**
     * 自定义限流标志的key
     * exchange对象中获取服务ID、请求信息，用户信息等
     * IP限流
     */
    @Bean
    KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

    /**
     * 自定义限流标志的key
     * exchange对象中获取请求地址的uri作为限流key
     */
    @Bean
    KeyResolver uriKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

    /**
     * 自定义限流标志的key
     * exchange对象中获取请求地址的uri作为限流key
     * 用户限流：使用这种方式限流，请求路径中必须携带userId参数
     */
    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
    }

    /**
     * 断路器
     * */
    @HystrixCommand(commandKey = "authHystrixCommand")
    public void authHystrixCommand(){
        log.info("路由认证");
    }
}
