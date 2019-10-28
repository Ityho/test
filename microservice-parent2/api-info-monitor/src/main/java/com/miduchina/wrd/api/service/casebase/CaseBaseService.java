/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: CaseBaseService.java 
 * @Prject: wyq-enterprise-api
 * @Package: com.midu.wyq.enterprise.wrd.service 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年12月18日 上午9:52:04 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.service.casebase;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.casebase.WyqCase;

import java.util.Map;

/** 
 * @ClassName: CaseBaseService 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年12月18日 上午9:52:04  
 */
public interface CaseBaseService {
	
	/**
	 * 案例库
	 * */
	PageInfo<WyqCase> findAll(Map<String, Object> param);

	/**
	 * 单条案例库
	 * */
	WyqCase findById(Integer caseId);

	/**
	 * 同类事件
	 * */
	PageInfo<WyqCase> findByTopLabel(Integer label);
}
