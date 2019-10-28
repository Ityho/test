/**
 * Copyright © 2018 公司名. All rights reserved.
 *
 * @Title: HotIncidentServiceImpl.java
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.service.impl
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:13:23
 * @version: V1.0
 */
package com.miduchina.wrd.api.service.infomonitor.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.infomonitor.BriefMapper;
import com.miduchina.wrd.api.service.infomonitor.BriefService;
import com.miduchina.wrd.util.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName: BriefServiceImpl
 * @Description: 简报业务接口实现
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:13:23
 * @version v1.0.0
 */
@Service
public class BriefServiceImpl implements BriefService {

	@Resource
	private BriefMapper briefMapper;

	@Override
	public Integer getSourceMaterialCount(JSONObject parmas) {
		Date now = new Date();
		parmas.put("endTime", DateUtils.format(now));
		parmas.put("startTime", DateUtils.format(DateUtils.addDay(now,-1)));
		Integer sourceMaterialCount = briefMapper.getSourceMaterialCount(parmas);
		return sourceMaterialCount == null? 0:sourceMaterialCount;
	}
}