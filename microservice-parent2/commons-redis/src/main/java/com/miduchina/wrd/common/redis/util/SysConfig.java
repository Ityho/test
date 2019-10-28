package com.miduchina.wrd.common.redis.util;


import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


public class SysConfig {

	// 所有配置文件对应数据
	public static Map<String, String> cfgMap = new HashMap<>();
	// 微博表情
	public static Map<String, String> expressions = new HashMap<>();

	// 加载数据
	public static void load(RedisTemplate redisTemplate) {
		RedisUtils.setRedisTemplate(redisTemplate);
		cfgMap = redisTemplate.opsForHash().entries("sysconfig");

		IntraBusinessAPIConfig.loadData();

		Executors.newFixedThreadPool(1).execute(new Runnable() {
			@Override
			public void run() {
				while (true){
					try {
						Thread.sleep(60*10000*10);
						cfgMap = redisTemplate.opsForHash().entries("sysconfig");
					}catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		});
	}

}
