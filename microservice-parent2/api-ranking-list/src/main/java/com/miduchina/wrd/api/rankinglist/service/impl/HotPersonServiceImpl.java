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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Sets;
import com.miduchina.wrd.api.rankinglist.mapper.HotIncidentMapper;
import com.miduchina.wrd.api.rankinglist.mapper.HotPersonMapper;
import com.miduchina.wrd.api.rankinglist.service.HotPersonService;
import com.miduchina.wrd.api.rankinglist.util.CommonUtils;
import com.miduchina.wrd.api.rankinglist.util.JedisUtil;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPerson;
import com.miduchina.wrd.po.ranking.RankingListWeb;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: HotIncidentServiceImpl 
 * @Description: 热门现象业务接口实现类
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Service
//@CacheConfig(cacheNames = "hotPerson")
public class HotPersonServiceImpl implements HotPersonService {

	@Resource
	private HotPersonMapper hotPersonMapper;

	@Resource
	private HotIncidentMapper hotIncidentMapper;

	@Override
	public PageInfo<HotPerson> list(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPersonMapper.findAll(params));
	}

//	@Cacheable(key = "#root.methodName+'['+#params['labels']+']'+'_'+'['+#params['t']+']'", condition = "#params['webShow'] == '1'")
	@Override
	public PageInfo<HotPerson> webList(JSONObject params) {
        String labels1 = params.getString("labels");
        Integer t = params.getInteger("t");
        String redisKey = "webList"+labels1+t;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);

        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
        PageInfo<HotPerson> pageInfo=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
		if (jsonStr!=null){
            pageInfo = JSONObject.parseObject(jsonStr,new TypeReference<PageInfo<HotPerson>>(){});
        }else {
            pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPersonMapper.findAllUnionHotIncident(params));
            List<Integer> hotEventPersonIds = new ArrayList<>();
            List<Integer> hotNewsPersonIds = new ArrayList<>();
            for (HotPerson hp:pageInfo.getList()) {
                if(hp.getLabelName().equals("事件人物")){
                    hotEventPersonIds.add(hp.getId());
                }else{
                    hotNewsPersonIds.add(hp.getId());
                }
            }
            JSONObject hiParams = new JSONObject();
            hiParams.put("hotEventPersonIds",hotEventPersonIds);
            hiParams.put("hotNewsPersonIds",hotNewsPersonIds);
            hiParams.put("startTime",params.get("startTime"));
            hiParams.put("endTime",params.get("endTime"));
            hiParams.put("showTag",params.get("showTag"));
            hiParams.put("labelShowTag",params.get("labelShowTag"));
            List<HotIncident> hotIncidentList = hotIncidentMapper.findAllV2(hiParams);
            for (HotPerson hp:pageInfo.getList()) {
                if(hp.getHotIncidentList() == null){
                    hp.setHotIncidentList(new ArrayList<>());
                }
                for (HotIncident hi:hotIncidentList) {
                    if(hp.getLabelName().equals("事件人物")){
                        if(StringUtils.isNotBlank(hi.getHotEventPersonIds())){
                            List<String> temp = Arrays.asList(hi.getHotEventPersonIds().split(","));
                            if(temp.contains(String.valueOf(hp.getId()))){
                                hp.getHotIncidentList().add(hi);
                            }
                        }
                    }else{
                        if(StringUtils.isNotBlank(hi.getHotNewsPersonIds())){
                            List<String> temp = Arrays.asList(hi.getHotNewsPersonIds().split(","));
                            if(temp.contains(String.valueOf(hp.getId()))){
                                hp.getHotIncidentList().add(hi);
                            }
                        }
                    }
                }
            }
            JedisUtil.setAttribute(redisKey, JSON.toJSONString(pageInfo),30*60,ip);
        }
		return pageInfo;
	}

	@Override
	public HotPerson get(Integer id) {
		return hotPersonMapper.findOne(id);
	}

	@Override
	public Boolean save(JSONObject params) {
		Integer flag = hotPersonMapper.insert(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			List<HotIncident> hotIncidentList = hotPersonMapper.findHotIncidentByIds(hotIncidentIdsIn);
			for (HotIncident hotIncident:hotIncidentList) {
				List<Integer> personIds = new ArrayList<>();
				if(hotIncident.getHotEventPersonIds() != null){
					personIds = Arrays.asList(hotIncident.getHotEventPersonIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
				}
				personIds.add(params.getInteger("id"));
				hotIncident.setHotEventPersonIds(StringUtils.join(personIds,","));
			}
			hotPersonMapper.updateHotIncidentRelation(hotIncidentList);
		}
		return flag > 0;
	}

//	@Override
//	public Boolean save(JSONObject params) {
//		Integer flag = hotPersonMapper.insert(params);
//		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
//			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonId",params.getInteger("id"));
//			hotPersonRelation.put("hotIncidentIds",hotIncidentIdsIn);
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_2);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
//		return flag > 0;
//	}

	@Override
	public Boolean modify(JSONObject params) {
		Integer flag = hotPersonMapper.update(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			List<HotIncident> hotIncidentList = hotPersonMapper.findHotIncidentByIds(hotIncidentIdsIn);
			for (HotIncident hotIncident:hotIncidentList) {
				Set<Integer> personIds = new HashSet<>();
				if(hotIncident.getHotEventPersonIds() != null){
					personIds = Arrays.asList(hotIncident.getHotEventPersonIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet());
				}
				personIds.add(params.getInteger("id"));
				hotIncident.setHotEventPersonIds(StringUtils.join(personIds,","));
			}
			hotPersonMapper.updateHotIncidentRelation(hotIncidentList);
		}
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsNotIn"))){
			List<Integer> hotIncidentIdsNotIn = Arrays.asList(params.getString("hotIncidentIdsNotIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			List<HotIncident> hotIncidentList = hotPersonMapper.findHotIncidentByIds(hotIncidentIdsNotIn);
			for (HotIncident hotIncident:hotIncidentList) {
				Set<Integer> personIds = new HashSet<>();
				if(hotIncident.getHotEventPersonIds() != null){
					personIds = Arrays.asList(hotIncident.getHotEventPersonIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toSet());
				}
				personIds.remove(params.getInteger("id"));
				hotIncident.setHotEventPersonIds(StringUtils.join(personIds,","));
			}
			hotPersonMapper.updateHotIncidentRelation(hotIncidentList);
		}
		return flag > 0;
	}

//	@Override
//	public Boolean modify(JSONObject params) {
//		Integer flag = hotPersonMapper.update(params);
//		JSONObject deleteHotIncident = new JSONObject();
//		deleteHotIncident.put("hotPersonId", params.getInteger("id"));
//		deleteHotIncident.put("status", BusinessConstant.STATUS_INVALID);
//		deleteHotIncident.put("type", BusinessConstant.STATUS_VALUE_2);
//		hotPersonMapper.deleteHotIncidentRelation(deleteHotIncident);
//		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
//			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonId",params.getInteger("id"));
//			hotPersonRelation.put("hotIncidentIds",hotIncidentIdsIn);
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_2);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
//		return flag > 0;
//	}

	@Override
	public Boolean remove(Integer id) {
		Integer flag = hotPersonMapper.delete(id);
		return flag > 0;
	}

	@Override
	public Boolean removeHotIncidentRelationId(Integer hotIncidentId, Integer personId, Integer type) {
		HotIncident hotIncident = hotIncidentMapper.findOneV2(hotIncidentId);
		List<Integer> personIds = new ArrayList<>();
		if(type == BusinessConstant.STATUS_VALUE_1){
			personIds = Arrays.asList(hotIncident.getHotNewsPersonIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		}
		if(type == BusinessConstant.STATUS_VALUE_2){
			personIds = Arrays.asList(hotIncident.getHotEventPersonIds().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		}
		personIds.remove(personId);
		return hotPersonMapper.deleteHotIncidentRelation(StringUtils.join(personIds,","), hotIncidentId, type) > 0;
	}

	@Override
	public List<RankingListWeb> findAllByShowTag(Integer showTag) {
		return hotPersonMapper.findAllByShowTag(showTag);
	}

	@Override
	public PageInfo<HotPerson> listUnion(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPersonMapper.findAllUnion(params));
	}

	@Override
	public PageInfo<HotPerson> listUnionHotIncident(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPersonMapper.findAllUnionHotIncident(params));
	}
}