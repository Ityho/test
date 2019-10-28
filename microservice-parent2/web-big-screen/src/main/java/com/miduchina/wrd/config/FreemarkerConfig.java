package com.miduchina.wrd.config;

import com.miduchina.wrd.common.redis.util.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {
    @Autowired
    private freemarker.template.Configuration configuration;


    @Value("${staticResourcePath}")
    protected String staticResourcePath;


    // Spring 初始化的时候加载配置
    @PostConstruct
    public void setConfigure() throws Exception {

        // 加载html的资源路径
        Long currentTimeMillis=System.currentTimeMillis();
        configuration.setSharedVariable("SYSTEM_INIT_TIME", currentTimeMillis);
        configuration.setSharedVariable("SYSTEMINITTIME", currentTimeMillis);

        configuration.setSharedVariable("njxBasePath","");
        configuration.setSharedVariable("staticResourcePath", staticResourcePath);
//        configuration.setSharedVariable("staticResourcePathH5", "http://files-m-beta.wrd.cn");
//        configuration.setSharedVariable("staticResourcePathH5", "http://cdn-files-m.wrd.cn");
        configuration.setSharedVariable("webPath", SysConfig.cfgMap.get("SYSTEM_WEB_URL"));
    }

}
