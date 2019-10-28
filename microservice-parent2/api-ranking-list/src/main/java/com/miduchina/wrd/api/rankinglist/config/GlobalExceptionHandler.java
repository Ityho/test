package com.miduchina.wrd.api.rankinglist.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e) {
		log.error(ExceptionUtils.getStackTrace(e)); // 记录错误信息
		String msg = e.getMessage();
		if (msg == null || msg.equals("")) {
			msg = "服务器出错";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", msg);
		return jsonObject;
	}
}
