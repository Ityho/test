/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotPhenomenoneMapper.java
 * @Prject: ranking-list-api
 * @Package: com.miduchina.wrd.api.rankinglist.mapper
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月16日 上午10:00:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.mapper;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName: HotPhenomenoneMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HotPhenomenoneMapper {

	/**
	 * 查询热门现象列表
	 * @see #findAll(JSONObject)
	 * @param params 热门现象
	 * @return PageInfo<HotPhenomenone>
	 * */
	List<HotPhenomenone> findAll(JSONObject params);

	/**
	 * 查询单个热门现象
	 * @see #findOne(Integer)
	 * @param id 主键
	 * @return HotPhenomenone
	 * */
	HotPhenomenone findOne(Integer id);

	/**
	 * 保存单个热门现象
	 * @see #insert(JSONObject)
	 * @param params 热门现象
	 * @return Integer
	 * */
	Integer insert(JSONObject params);

	/**
	 * 更新单个热门现象
	 * @see #update(JSONObject)
	 * @param params 热门现象
	 * @return Integer
	 * */
	Integer update(JSONObject params);

	/**
	 * 删除单个热门现象
	 * @see #delete(Integer)
	 * @param id 热门现象
	 * @return Integer
	 * */
	Integer delete(Integer id);

	/**
	 * 查询关联热门事件id
	 * @see #findAllHotIncidentRelationId(JSONObject)
	 * @param params 热门现象
	 * @return List<Integer>
	 * */
	List<Integer> findAllHotIncidentRelationId(JSONObject params);

	/**
	 * 修改关联热门事件
	 * @see #updateHotIncidentRelation(List, Integer)
	 * @param hotIncidentIds 热门事件id集合
	 * @param id 热门现象id
	 * @return Integer
	 * */
	Integer updateHotIncidentRelation(@Param("hotIncidentIds")List<Integer> hotIncidentIds, @Param("id")Integer id);

	/**
	 * 删除关联热门事件
	 * @see #deleteHotIncidentRelation(Integer)
	 * @param id 热门现象id
	 * @return Integer
	 * */
	Integer deleteHotIncidentRelation(@Param("id")Integer id);
}
