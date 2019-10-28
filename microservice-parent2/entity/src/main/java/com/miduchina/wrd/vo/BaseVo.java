/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: ResultVo.java 
 * @Prject: entity
 * @Package: com.midu.wyq.vo 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年11月1日 下午6:37:24 
 * @version: V1.0   
 */
package com.miduchina.wrd.vo;

import java.io.Serializable;


import lombok.Data;

/** 
 * @ClassName: BaseVo
 * @Description: 基础视图对象
 * @author: 许双龙
 * @date: 2017年11月1日 下午6:37:24  
 */
@Data
public class BaseVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String code;
	private String message;
	private Object data;
	
	public BaseVo setCode(String code) {
		this.code = code;
		return this;
	}
	
	public BaseVo setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public BaseVo setData(Object data) {
		this.data = data;
		return this;
	}
	
}
