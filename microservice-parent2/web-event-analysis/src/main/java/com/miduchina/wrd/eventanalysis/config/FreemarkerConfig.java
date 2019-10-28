package com.miduchina.wrd.eventanalysis.config;

import com.miduchina.wrd.common.redis.util.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;

    @Value("${staticResourcePathH5}")
    protected String staticResourcePathH5;

    // Spring 初始化的时候加载配置
    @PostConstruct
    public void setConfigure() throws Exception {

        // 加载html的资源路径
        Long currentTimeMillis=System.currentTimeMillis();
        configuration.setSharedVariable("SYSTEM_INIT_TIME", currentTimeMillis);
        configuration.setSharedVariable("SYSTEMINITTIME", currentTimeMillis);

        configuration.setSharedVariable("njxBasePath","");
        configuration.setSharedVariable("staticResourcePath", "");
        configuration.setSharedVariable("staticResourcePathH5", staticResourcePathH5);
        configuration.setSharedVariable("webPath", SysConfig.cfgMap.get("SYSTEM_WEB_URL"));

    }

}
