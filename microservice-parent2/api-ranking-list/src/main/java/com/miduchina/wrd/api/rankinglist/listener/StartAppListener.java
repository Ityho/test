package com.miduchina.wrd.api.rankinglist.listener;

import com.miduchina.wrd.api.rankinglist.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @ClassName: StartAppListener
 * @Description: 监听上下文启动
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:12:54
 */
@Slf4j
@WebListener
public class StartAppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
        SpringUtils.setApplicationContext(ctx);
        LoadConfigThread loadConfigThread = new LoadConfigThread();
        loadConfigThread.start();
        log.info("创建时执行");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("销毁时执行");
    }
}
