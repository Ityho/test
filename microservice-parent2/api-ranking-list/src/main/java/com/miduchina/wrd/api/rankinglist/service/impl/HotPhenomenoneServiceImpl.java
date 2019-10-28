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
import com.miduchina.wrd.api.rankinglist.mapper.*;
import com.miduchina.wrd.api.rankinglist.service.HotPhenomenoneService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class HotPhenomenoneServiceImpl implements HotPhenomenoneService {

	@Resource
	private HotIncidentMapper hotIncidentMapper;

	@Resource
	private HotPhenomenoneMapper hotPhenomenoneMapper;
	
	@Resource
	private HotLabelMapper hotLabelMapper;

	@Override
	public PageInfo<HotPhenomenone> list(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPhenomenoneMapper.findAll(params));
	}

	@Override
	public HotPhenomenone get(Integer id) {
		return hotPhenomenoneMapper.findOne(id);
	}

	@Override
	public Boolean save(JSONObject params) {
		Integer flag = hotPhenomenoneMapper.insert(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			hotPhenomenoneMapper.updateHotIncidentRelation(hotIncidentIdsIn,params.getInteger("id"));
		}
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsNotIn"))){
			List<Integer> hotIncidentIdsNotIn = Arrays.asList(params.getString("hotIncidentIdsNotIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			hotPhenomenoneMapper.updateHotIncidentRelation(hotIncidentIdsNotIn,null);
		}
		return flag > 0;
	}

	@Override
	public Boolean modify(JSONObject params) {
		Integer flag = hotPhenomenoneMapper.update(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			hotPhenomenoneMapper.updateHotIncidentRelation(hotIncidentIdsIn,params.getInteger("id"));
		}
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsNotIn"))){
			List<Integer> hotIncidentIdsNotIn = Arrays.asList(params.getString("hotIncidentIdsNotIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			hotPhenomenoneMapper.updateHotIncidentRelation(hotIncidentIdsNotIn,null);
		}
		return flag > 0;
	}

	@Override
	public Boolean remove(Integer id) {
		Integer flag = hotPhenomenoneMapper.delete(id);
		return flag > 0;
	}
}