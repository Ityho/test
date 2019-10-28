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
import com.miduchina.wrd.api.rankinglist.mapper.HotIncidentMapper;
import com.miduchina.wrd.api.rankinglist.mapper.HotLabelMapper;
import com.miduchina.wrd.api.rankinglist.mapper.ImportantEventMapper;
import com.miduchina.wrd.api.rankinglist.service.ImportantEventService;
import com.miduchina.wrd.api.rankinglist.util.CommonUtils;
import com.miduchina.wrd.api.rankinglist.util.JedisUtil;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: ImportantEventServiceImpl
 * @Description: 重大事件业务接口实现类
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Service
//@CacheConfig(cacheNames = "importantEvent")
public class ImportantEventServiceImpl implements ImportantEventService {

	@Resource
	private HotIncidentMapper hotIncidentMapper;

	@Resource
	private ImportantEventMapper importantEventMapper;

	@Resource
	private HotLabelMapper hotLabelMapper;

	@Override
	public PageInfo<ImportantEvent> list(JSONObject params) {
		Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
		Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
		if(StringUtils.isNotBlank(params.getString("labels"))){
			List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			params.put("labels",labels);
		}
		return PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> importantEventMapper.findAll(params));
	}

//    @Cacheable(key = "#root.methodName+'['+#params['webShow']+']'", condition = "#params['webShow'] == '1'")
    @Override
    public PageInfo<ImportantEvent> webList(JSONObject params) {
        Integer webShow = params.getInteger("webShow");
        String redisKey = "webList"+webShow;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIp(request);

        Integer page = params.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:params.getInteger("page");
        Integer pageSize = params.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:params.getInteger("pageSize");
        if(StringUtils.isNotBlank(params.getString("labels"))){
            List<Integer> labels = Arrays.asList(params.getString("labels").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
            params.put("labels",labels);
        }
        PageInfo<ImportantEvent> pageInfo=null;
        String jsonStr = JedisUtil.getAttribute(redisKey, ip);
        if (jsonStr!=null){
            pageInfo =  JSONObject.parseObject(jsonStr,new TypeReference<PageInfo<ImportantEvent>>(){});
        }else {
            pageInfo = PageHelper.startPage(page, pageSize).doSelectPageInfo(() -> importantEventMapper.findAll(params));
            List<Integer> ieIds = pageInfo.getList().stream().map(s -> s.getId()).collect(Collectors.toList());
            JSONObject hiParams = new JSONObject();
            hiParams.put("importantEventIds",ieIds);
            hiParams.put("startTime",params.get("startTime"));
            hiParams.put("endTime",params.get("endTime"));
            hiParams.put("showTag",params.get("showTag"));
            hiParams.put("labelShowTag",params.get("labelShowTag"));
            List<HotIncident> hotIncidentList = hotIncidentMapper.findAllV2(hiParams);
            for (ImportantEvent ie:pageInfo.getList()) {
                if(ie.getHotIncidentList() == null){
                    ie.setHotIncidentList(new ArrayList<>());
                }
                for (HotIncident hi:hotIncidentList) {
                    if(ie.getId().equals(hi.getImportantEventId())){
                        ie.getHotIncidentList().add(hi);
                    }
                }
            }
            JedisUtil.setAttribute(redisKey, JSON.toJSONString(pageInfo),30*60,ip);
        }
        return pageInfo;
    }

	@Override
	public ImportantEvent get(Integer id) {
		return importantEventMapper.findOne(id);
	}

	@Override
	public ImportantEvent webGet(JSONObject params) {
		ImportantEvent ie = importantEventMapper.findOneV2(params);
		if(ie == null){
			return ie;
		}
		JSONObject hiParams = new JSONObject();
		hiParams.put("importantEventIds",Arrays.asList(ie.getId()));
		hiParams.put("startTime",params.get("startTime"));
		hiParams.put("endTime",params.get("endTime"));
		hiParams.put("showTag",params.get("showTag"));
		hiParams.put("labelShowTag",params.get("labelShowTag"));
		List<HotIncident> hotIncidentList = hotIncidentMapper.findAllV2(hiParams);
		ie.setHotIncidentList(hotIncidentList);
		return ie;
	}

	@Override
	public Boolean save(JSONObject params) {
		Integer flag = importantEventMapper.insert(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			importantEventMapper.updateHotIncidentRelation(hotIncidentIdsIn,params.getInteger("id"));
		}
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsNotIn"))){
			List<Integer> hotIncidentIdsNotIn = Arrays.asList(params.getString("hotIncidentIdsNotIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			importantEventMapper.updateHotIncidentRelation(hotIncidentIdsNotIn,null);
		}
		return flag > 0;
	}

	@Override
	public Boolean modify(JSONObject params) {
		Integer flag = importantEventMapper.update(params);
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsIn"))){
			List<Integer> hotIncidentIdsIn = Arrays.asList(params.getString("hotIncidentIdsIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			importantEventMapper.updateHotIncidentRelation(hotIncidentIdsIn,params.getInteger("id"));
		}
		if(StringUtils.isNotBlank(params.getString("hotIncidentIdsNotIn"))){
			List<Integer> hotIncidentIdsNotIn = Arrays.asList(params.getString("hotIncidentIdsNotIn").split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
			importantEventMapper.updateHotIncidentRelation(hotIncidentIdsNotIn,null);
		}
		return flag > 0;
	}

	@Override
	public Boolean remove(Integer id) {
		Integer flag = importantEventMapper.delete(id);
		return flag > 0;
	}
}