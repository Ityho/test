/**   
 * Copyright © 2017 公司名. All rights reserved.
 * 
 * @Title: WarningSearchServiceImpl.java 
 * @Prject: wyq-warning-view
 * @Package: com.xd.warning.service.impl 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2017年5月15日 下午2:39:51 
 * @version: V1.0   
 */
package com.miduchina.wrd.monitor.warning.service.impl;

import javax.annotation.Resource;

import com.miduchina.wrd.monitor.warning.mapper.UserMapper;
import com.miduchina.wrd.monitor.warning.mapper.WarningMapper;
import com.miduchina.wrd.monitor.warning.mapper.WarningTimerSearchMapper;
import com.miduchina.wrd.po.keyword.Warning;
import com.miduchina.wrd.po.keyword.WarningTimerSearchResult;
import com.miduchina.wrd.po.user.User;
import org.springframework.stereotype.Service;

import com.miduchina.wrd.monitor.warning.service.WarningService;
import org.springframework.util.CollectionUtils;

import java.util.List;

/** 
 * @ClassName: WarningSearchServiceImpl 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年5月15日 下午2:39:51  
 */
@Service
public class WarningServiceImpl implements WarningService {
	
	@Resource
	WarningTimerSearchMapper warningTimerSearchMapper;
	
	@Resource
	WarningMapper warningMapper;
	
	@Resource
	UserMapper userMapper;
	
	
	@Override
	public WarningTimerSearchResult getWTSRByCode(String reviewCode) {
		List<WarningTimerSearchResult> list = warningTimerSearchMapper.findWTSRByCode(reviewCode);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Warning findWarningById(Integer warningId) {
		return warningMapper.findById(warningId);
	}

	@Override
	public void updateWarningTimerSearchResult(String reviewCode) {
		warningTimerSearchMapper.updateWarningTimerSearchResult(reviewCode);
	}

	@Override
	public User findUserById(Integer userId) {
		return userMapper.findById(userId);
	}
}
