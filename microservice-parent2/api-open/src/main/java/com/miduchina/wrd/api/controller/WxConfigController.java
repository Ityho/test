package com.miduchina.wrd.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @auther yho
 * @vreate 2019-08 14:18
 */
@Controller
@RequestMapping
public class WxConfigController {
//    @RequestMapping({"MP_verify_3ZXenPjFdNmICUrz.txt"})
    @RequestMapping("/MP_verify_3ZXenPjFdNmICUrz.txt")
    @ResponseBody
    public  String returnConfigFile(HttpServletResponse response) {
        //把MP_verify_xxxxxx.txt中的内容返回
        return "3ZXenPjFdNmICUrz";
    }
}
