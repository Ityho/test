package com.miduchina.wrd.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @ClassName: RedisConfig
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年1月2日 下午2:13:37
 */
@EnableCaching
@Configuration
public class RedisConfig {
    /**
     * 注入 RedisConnectionFactory
     */
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        //缓存配置对象
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();

        //如果是空值，不缓存
        //设置缓存的默认超时时间：30分钟
        redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofMinutes(30L))
                .disableCachingNullValues()
                //设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(getJsonRedisTemplate().getStringSerializer()))
                //设置value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(getJsonRedisTemplate().getValueSerializer()));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    // @Bean(name = "jsonRedisTemplate")
    // public RedisTemplate<String, Object> getJsonRedisTemplate() {
    // //StringRedisTemplate template = new StringRedisTemplate(factory);
    // RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    // redisTemplate.setKeySerializer(new StringRedisSerializer());
    // redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //
    // Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new
    // Jackson2JsonRedisSerializer<Object>(Object.class);
    // ObjectMapper om = new ObjectMapper();
    // om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    // jackson2JsonRedisSerializer.setObjectMapper(om);
    //
    // redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    // redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    // redisTemplate.setConnectionFactory(redisConnectionFactory);
    // return redisTemplate;
    // }
    /**
     * 实例化 json RedisTemplate 对象
     * @return
     */
    @Bean(name = "jsonRedisTemplate")
    public RedisTemplate<String, String> getJsonRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
                Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
    /**
     * 实例化 jdk RedisTemplate 对象
     * @return
     */
    @Bean(name = "jdkRedisTemplate")
    public RedisTemplate<String, Object> getJdkRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }
    /**
     * 设置数据存入 redis 的序列化方式
     * @param redisTemplate
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }
    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
    /**
     * 实例化 ValueOperations 对象,可以使用 String 操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }
    /**
     * 实例化 ListOperations 对象,可以使用 List 操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForList();
    }
    /**
     * 实例化 SetOperations 对象,可以使用 Set 操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForSet();
    }
    /**
     * 实例化 ZSetOperations 对象,可以使用 ZSet 操作
     * @param redisTemplate
     * @return
     */
    @Bean
    public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForZSet();
    }

}