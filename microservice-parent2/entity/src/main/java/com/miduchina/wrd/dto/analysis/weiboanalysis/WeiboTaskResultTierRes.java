package com.miduchina.wrd.dto.analysis.weiboanalysis;

import java.util.List;

/**
 * 转发层级响应消息
 * 
 * @author liym
 */
public class WeiboTaskResultTierRes {
	private String code;
	private String message;
	private List<WeiboTaskResultTierVO> transTierList;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<WeiboTaskResultTierVO> getTransTierList() {
		return transTierList;
	}

	public void setTransTierList(List<WeiboTaskResultTierVO> transTierList) {
		this.transTierList = transTierList;
	}
}
