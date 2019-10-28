/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: WarningSearchService.java 
 * @Prject: wyq-warning-view
 * @Package: com.xd.warning.service 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年5月15日 下午2:32:09 
 * @version: V1.0   
 */
package com.miduchina.wrd.monitor.warning.service;

import com.miduchina.wrd.po.keyword.WarningTimerSearchResult;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.po.keyword.Warning;

/** 
 * @ClassName: WarningSearchService 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年5月15日 下午2:32:09  
 */
public interface WarningService {

	/**
	 * 查询预警下发记录
	 * @see #getWTSRByCode(String)
	 * @param reviewCode 预警code
	 * @return WarningTimerSearchResult
	 * */
	WarningTimerSearchResult getWTSRByCode(String reviewCode);

	/**
	 * 查询单个预警
	 * @see #findWarningById(Integer)
	 * @param warningId 条件
	 * @return Warning
	 * */
	Warning findWarningById(Integer warningId);

	/**
	 * 修改预警是否读过
	 * @see #updateWarningTimerSearchResult(String)
	 * @param reviewCode 条件
	 * */
	void updateWarningTimerSearchResult(String reviewCode);

	/**
	 * 查询单个用户
	 * @see #findUserById(Integer)
	 * @param userId 条件
	 * @return User
	 * */
	User findUserById(Integer userId);
}
