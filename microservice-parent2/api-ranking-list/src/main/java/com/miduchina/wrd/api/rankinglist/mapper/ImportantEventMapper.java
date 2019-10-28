/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: RankingListMapper.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.mapper
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月16日 上午10:00:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import org.apache.ibatis.annotations.Param;

/** 
 * @ClassName: ImportantEventMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface ImportantEventMapper {

	/**
	 * 查询重大事件列表
	 * @see #findAll(JSONObject)
	 * @param params 重大事件
	 * @return PageInfo<ImportantEvent>
	 * */
	List<ImportantEvent> findAll(JSONObject params);

	/**
	 * 查询单个重大事件
	 * @see #findOne(Integer)
	 * @param id 主键
	 * @return ImportantEvent
	 * */
	ImportantEvent findOne(Integer id);

	/**
	 * 查询单个重大事件（前台）
	 * @see #findOne(Integer)
	 * @param params 重大事件
	 * @return ImportantEvent
	 * */
	ImportantEvent findOneV2(JSONObject params);

	/**
	 * 保存单个重大事件
	 * @see #insert(JSONObject)
	 * @param params 重大事件
	 * @return Integer
	 * */
	Integer insert(JSONObject params);

	/**
	 * 更新单个重大事件
	 * @see #update(JSONObject)
	 * @param params 重大事件
	 * @return Integer
	 * */
	Integer update(JSONObject params);

	/**
	 * 删除单个重大事件
	 * @see #delete(Integer)
	 * @param id 重大事件
	 * @return Integer
	 * */
	Integer delete(Integer id);

	/**
	 * 查询关联热门事件id
	 * @see #findAllHotIncidentRelationId(JSONObject)
	 * @param params 重大事件
	 * @return List<Integer>
	 * */
	List<Integer> findAllHotIncidentRelationId(JSONObject params);

	/**
	 * 修改关联热门事件
	 * @see #updateHotIncidentRelation(List, Integer)
	 * @param hotIncidentIds 热门事件id集合
	 * @param id 重大事件id
	 * @return Integer
	 * */
	Integer updateHotIncidentRelation(@Param("hotIncidentIds")List<Integer> hotIncidentIds, @Param("id")Integer id);

	/**
	 * 删除关联热门事件
	 * @see #deleteHotIncidentRelation(Integer)
	 * @param id 重大事件id
	 * @return Integer
	 * */
	Integer deleteHotIncidentRelation(@Param("id")Integer id);
}
