package com.miduchina.wrd.monitor.warning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther Administrator
 * @vreate 2019-07 13:59
 */
@Controller
public class IndexController {
	@RequestMapping(value = "/")
	@ResponseBody
	public String report(){
		return "0000";
	}
}
