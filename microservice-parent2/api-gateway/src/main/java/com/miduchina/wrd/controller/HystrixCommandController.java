package com.miduchina.wrd.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sty on 2019/1/3.
 * @author sty
 */
@Slf4j
@RestController
public class HystrixCommandController {

    /**
     * 全局路由短路
     * */
    @RequestMapping("hystrixTimeout")
    public String hystrixTimeout(){
        log.error("触发了断路由");
        return "api error";
    }
}
