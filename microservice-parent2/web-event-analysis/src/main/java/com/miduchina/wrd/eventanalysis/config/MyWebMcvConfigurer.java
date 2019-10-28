package com.miduchina.wrd.eventanalysis.config;

import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.CommonUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
public class MyWebMcvConfigurer implements WebMvcConfigurer {
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //开启路径后缀匹配
        configurer.setUseRegisteredSuffixPatternMatch(true);
    }

}
