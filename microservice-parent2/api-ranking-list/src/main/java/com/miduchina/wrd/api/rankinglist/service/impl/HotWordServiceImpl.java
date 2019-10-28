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
package com.miduchina.wrd.api.rankinglist.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.rankinglist.mapper.HotIncidentMapper;
import com.miduchina.wrd.api.rankinglist.mapper.HotLabelMapper;
import com.miduchina.wrd.api.rankinglist.mapper.HotWordMapper;
import com.miduchina.wrd.api.rankinglist.service.HotWordService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.HotWord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: HotIncidentServiceImpl 
 * @Description: 热门现象业务接口实现类
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Service
public class HotWordServiceImpl implements HotWordService {

	@Resource
	private HotIncidentMapper hotIncidentMapper;

	@Resource
	private HotWordMapper hotWordMapper;
	
	@Resource
	private HotLabelMapper hotLabelMapper;

	@Override
	public PageInfo<HotWord> list(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotWordMapper.findAll(params));
	}

	@Override
	public HotWord get(Integer id) {
		return hotWordMapper.findOne(id);
	}

	@Override
	public Boolean remove(Integer id) {
		Integer flag = hotWordMapper.delete(id);
		return flag > 0;
	}
}