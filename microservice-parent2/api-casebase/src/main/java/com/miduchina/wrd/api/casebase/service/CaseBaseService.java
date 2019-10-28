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
package com.miduchina.wrd.api.casebase.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.casebase.WyqCase;

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
	/**
	 * @Author ZhuFangTao
	 * @Description 热点事件区间
	 * @Date  2019/5/22 12:00
	 * @Param [param]
	 * @return java.util.List<java.lang.Double>
	 */
	List<Double> selectHotEventsIntervalChart(Map<String, Object> param);
	/**
	 * @Author ZhuFangTao
	 * @Description
	 * @Date  2019/5/22 17:02
	 * @Param [param]
	 * @return java.util.ArrayList<com.miduchina.wrd.po.casebase.WyqCase>
	 */
    List<WyqCase> select30HotWorths(Map<String, Object> param);
	/**
	 * @Author ZhuFangTao
	 * @Description 
	 * @Date  2019/5/23 11:48
	 * @Param [caseID]
	 * @return com.miduchina.wrd.po.casebase.WyqCase
	 */
    WyqCase checkCaseAnalysis(Integer caseID);

}
