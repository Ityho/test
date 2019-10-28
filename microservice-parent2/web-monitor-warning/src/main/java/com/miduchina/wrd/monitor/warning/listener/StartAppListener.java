/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: StartAppListener.java 
 * @Prject: wyq-product-operation
 * @Package: com.wyq.operation.listener 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年3月13日 下午7:48:27 
 * @version: V1.0   
 */
package com.miduchina.wrd.monitor.warning.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.miduchina.wrd.common.redis.util.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
 * @ClassName: StartAppListener 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年3月13日 下午7:48:27  
 */
@Slf4j
@WebListener
public class StartAppListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		SysConfig.load((RedisTemplate)(ctx.getBean("jsonRedisTemplate")));
		log.info("创建时执行");
	}
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {}
}
