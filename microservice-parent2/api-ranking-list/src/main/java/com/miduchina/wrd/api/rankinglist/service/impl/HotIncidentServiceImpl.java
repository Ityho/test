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

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.mapper.*;
import com.miduchina.wrd.api.rankinglist.util.CommonUtils;
import com.miduchina.wrd.api.rankinglist.util.JedisUtil;
import com.xd.tools.pojo.Params;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import com.miduchina.wrd.po.ranking.HotWord;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import com.miduchina.wrd.api.rankinglist.service.HotIncidentService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @ClassName: HotIncidentServiceImpl
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:13:23
 * @version v1.0.0
 */
@Service
//@CacheConfig(cacheNames = "hotIncident")
public class HotIncidentServiceImpl implements HotIncidentService {

	@Resource
	private HotIncidentMapper hotIncidentMapper;

	@Resource
	private HotPhenomenoneMapper hotPhenomenoneMapper;

	@Resource
	private ImportantEventMapper importantEventMapper;

	@Resource
	private HotWordMapper hotWordMapper;

	@Resource
	private HotLabelMapper hotLabelMapper;

	@Resource
	private HotPersonMapper hotPersonMapper;

	@Override
	public PageInfo<HotIncident> listV2(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotIncidentMapper.findAllV2(params));
	}

//	@Cacheable(key = "#root.methodName+'['+#params['date']+']'+'_'+'['+#params['sort']+']'+'_'+'['+#params['pageSize']+']'+'_'+'['+#params['page']+']'+'_'+'['+#params['city']+']'+'_'+'['+#params['areaType']+']'",condition = "#params['labels'] == null and #params['province'] == null and #params['city'] == null")
	@Override
	public PageInfo<HotIncident> webListV2(JSONObject params) {
        Integer data = params.getInteger("data");
        Integer sort = params.getInteger("sort");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        String city = params.getString("city");
        String labels1 = params.getString("labels");
        Integer areaType = params.getInteger("areaType");
        String province = params.getString("province");
        String redisKey = "webListV2"+data+sort+pageSize+page+city+areaType+labels1+province;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);
        if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			List<Integer> chidrenLabels = new ArrayList<>();
			for (Integer label :labels) {
				boolean flag = true;
				for (Map.Entry<Integer, Integer> m :Constants.HOT_PARENT_ID_MAP.entrySet())  {
					if(m.getValue().equals(label)){
						chidrenLabels.add(m.getKey());
						flag = false;
					}
				}
				if(flag){
					chidrenLabels.add(label);
				}
			}
			params.put("labels",chidrenLabels);
		}
        PageInfo<HotIncident> result=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
        if(jsonStr!=null){
            result = JSONObject.parseObject(jsonStr, new TypeReference<PageInfo<HotIncident>>(){});
        }else {
            result = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotIncidentMapper.findAllV2(params));
            JedisUtil.setAttribute(redisKey,JSON.toJSONString(result),30*60,ip);
            System.out.println("result:"+ result.toString());
        }
		return result;
	}

	@Override
	public PageInfo<HotPhenomenone> findHotPhenomenoneList(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPhenomenoneMapper.findAll(params));
	}

	@Override
	public PageInfo<ImportantEvent> findImportantEventList(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> importantEventMapper.findAll(params));
	}

	@Override
	public PageInfo<HotWord> findHotWordList(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotWordMapper.findAll(params));
	}

	@Override
	public HotIncident findOneV2(Integer id) {
		return hotIncidentMapper.findOneV2(id);
	}

	@Override
	public Boolean addV2(JSONObject params) {
		Integer id = null;
		String hotWordName = params.getString("hotWordName");
		if(StringUtils.isNotBlank(hotWordName)) {
			id = hotWordMapper.findHotWordName(hotWordName);
			if(id == null) {
				HotWord hotWord = new HotWord();
				hotWord.setStatus(BusinessConstant.STATUS_VALID);
				hotWord.setName(hotWordName);
				hotWordMapper.insert(hotWord);
				id = hotWord.getId();
			}
		}
		params.put("hotWordId", id);
		if(params.get("showTag") == null) {
			params.put("showTag", 0);
		}
		Integer flag = hotIncidentMapper.insertV2(params);
//		if(params.get("hotNewsPersonIds") != null && StringUtils.isNotBlank(params.getString("hotNewsPersonIds"))) {
//			List<Integer> hotNewsPersonIds = Arrays.asList(params.getString("hotNewsPersonIds").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonIds",hotNewsPersonIds);
//			hotPersonRelation.put("hotIncidentId",params.get("hotIncidentId"));
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_1);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
//		if(params.get("hotEventPersonIds") != null && StringUtils.isNotBlank(params.getString("hotEventPersonIds"))) {
//			List<Integer> hotEventPersonIds = Arrays.asList(params.getString("hotEventPersonIds").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonIds",hotEventPersonIds);
//			hotPersonRelation.put("hotIncidentId",params.get("hotIncidentId"));
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_2);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
		return flag > 0;
	}

	@Override
	public Boolean modifyV2(JSONObject params) {
		Integer id = null;
		String hotWordName = params.getString("hotWordName");
		if(StringUtils.isNotBlank(hotWordName)) {
			id = hotWordMapper.findHotWordName(hotWordName);
			if(id == null) {
				HotWord hotWord = new HotWord();
				hotWord.setStatus(BusinessConstant.STATUS_VALID);
				hotWord.setName(hotWordName);
				hotWordMapper.insert(hotWord);
				id = hotWord.getId();
			}
		}
		params.put("hotWordId", id);
		if(params.get("showTag") == null) {
			params.put("showTag", 0);
		}
//		JSONObject deleteHotIncident = new JSONObject();
//		deleteHotIncident.put("hotIncidentId", params.getInteger("hotIncidentId"));
//		deleteHotIncident.put("status", BusinessConstant.STATUS_INVALID);
//		hotPersonMapper.deleteHotIncidentRelation(deleteHotIncident);
//		if(params.get("hotNewsPersonIds") != null && StringUtils.isNotBlank(params.getString("hotNewsPersonIds"))) {
//			List<Integer> hotNewsPersonIds = Arrays.asList(params.getString("hotNewsPersonIds").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonIds",hotNewsPersonIds);
//			hotPersonRelation.put("hotIncidentId",params.get("hotIncidentId"));
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_1);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
//		if(params.get("hotEventPersonIds") != null && StringUtils.isNotBlank(params.getString("hotEventPersonIds"))) {
//			List<Integer> hotEventPersonIds = Arrays.asList(params.getString("hotEventPersonIds").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
//			JSONObject hotPersonRelation = new JSONObject();
//			hotPersonRelation.put("hotPersonIds",hotEventPersonIds);
//			hotPersonRelation.put("hotIncidentId",params.get("hotIncidentId"));
//			hotPersonRelation.put("status",BusinessConstant.STATUS_VALUE_1);
//			hotPersonRelation.put("type",BusinessConstant.STATUS_VALUE_2);
//			hotPersonMapper.insertBatchHotIncidentRelation(hotPersonRelation);
//		}
		Integer flag = hotIncidentMapper.updateV2(params);
		return flag > 0;
	}

	@Override
	public Boolean removeV2(Integer id, Integer type) {
		Integer flag = 0;
		if(type == 1) {
			flag = hotIncidentMapper.delete(id);
		}else if(type == 2) {
			flag = hotPhenomenoneMapper.delete(id);
		}else if(type == 3) {
			flag = importantEventMapper.delete(id);
		}else if(type == 6) {
			flag = hotWordMapper.delete(id);
		}
		return flag > 0;
	}

	@Override
	public PageInfo<?> findHotListV2(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
		PageInfo<?> pageInfo = new PageInfo<>();
		if("1".equals(params.getString("type"))) {
			pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotIncidentMapper.findAllV2(params));
		}else if("2".equals(params.getString("type"))) {
			pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotPhenomenoneMapper.findAll(params));
		}else if("3".equals(params.getString("type"))) {
			pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> importantEventMapper.findAll(params));
			return pageInfo;
		}else if("6".equals(params.getString("type"))) {
			pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> hotWordMapper.findAll(params));
		}
		return pageInfo;
	}

	@Override
	public Boolean modifyHotIncidentRelationId(String ids, Integer relationId, Integer type) {
		return hotIncidentMapper.updateHotIncidentRelationId(ids.split(","), relationId, type) > 0;
	}

	@Override
	public Boolean removeHotIncidentRelationId(String ids, Integer relationId, Integer type) {
		return hotIncidentMapper.deleteHotIncidentRelationId(ids.split(","), relationId, type) > 0;
	}

	@Override
	public Boolean modifyTop(Integer id,Integer whether) {
		return hotIncidentMapper.updateTop(id,whether) > 0;
	}

//	@Cacheable(key = "#root.methodName+'['+#params['date']+']'+'_'+'['+#params['city']+']'",condition = "#params['labels'] == null and #params['province'] == null and #params['city'] == null")
	@Override
	public Map<String, Object> findCount(JSONObject params) {
        Integer data = params.getInteger("data");
        String labels1 = params.getString("labels");
        String city = params.getString("city");
        String province = params.getString("province");
        String redisKey="findCount"+data+city+labels1+province;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);

        if(StringUtils.isNotBlank(params.getString("labels"))){
            List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            List<Integer> childrenLabels = new ArrayList<>();
            for (Integer label:labels) {
                for(Map.Entry<Integer,Integer> mapEntry : Constants.HOT_PARENT_ID_MAP.entrySet()){
                    if(mapEntry.getValue().equals(label)){
                        childrenLabels.add(mapEntry.getKey());
                    }
                }
            }
            params.put("labels",childrenLabels);
        }
        Map<String, Object> count=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
        if (jsonStr!=null){
             count = JSONObject.parseObject(jsonStr, new TypeReference<Map<String, Object>>() {});
        }else {
            count = hotIncidentMapper.findCount(params);
            JedisUtil.setAttribute(redisKey,JSON.toJSONString(count),30*60,ip);
        }
        return  count;
	}

//	@Cacheable(key = "#root.methodName+'['+#params['date']+']'+'['+#params['province']+']'+'_'+'['+#params['city']+']'",condition = "#params['city'] == null")
	@Override
	public Map<String,Integer> findTypeCount(JSONObject params) {
        Integer data = params.getInteger("data");
        String province = params.getString("province");
        String city = params.getString("city");
        String redisKey = "findTypeCount"+data+province+city;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);
        List<String> labelList=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
        if (jsonStr!=null){
            labelList = JSONObject.parseObject(jsonStr,new TypeReference<List<String>>(){});
        }else {
            labelList = hotIncidentMapper.findTypeCount(params);
            JedisUtil.setAttribute(redisKey,JSON.toJSONString(labelList),30*60,ip);
        }
		Map<String,Integer> typeCount = new HashMap<>();
		for (String labels:labelList) {
			String[] labelsArr = labels.split(",");
			for (String label:labelsArr) {
				String parentName = Constants.HOT_LABEL_MAP.get(Constants.HOT_PARENT_ID_MAP.get(Integer.valueOf(label)));
				if(typeCount.containsKey(parentName)){
					typeCount.put(parentName,typeCount.get(parentName)+1);
				}else{
					typeCount.put(parentName,1);
				}
			}
		}
		return typeCount;
	}

//	@Cacheable(key = "#root.methodName")
	@Override
	public List<HotIncident> findProvinceGroupBy(JSONObject params) {
	    String redisKey="findProvinceGroupBy";
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);
        List<HotIncident> provinceGroupBy=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
        if (jsonStr!=null){
            provinceGroupBy = JSONObject.parseObject(jsonStr,new TypeReference<List<HotIncident>>(){});
        }else {
            provinceGroupBy = hotIncidentMapper.findProvinceGroupBy(params);
            JedisUtil.setAttribute(redisKey,JSON.toJSONString(provinceGroupBy),30*60,ip);
        }

        return provinceGroupBy;
	}
}