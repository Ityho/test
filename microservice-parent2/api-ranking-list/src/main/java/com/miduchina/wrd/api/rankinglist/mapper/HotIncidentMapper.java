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
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import com.miduchina.wrd.po.ranking.HotIncident;
/** 
 * @ClassName: HotIncidentMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HotIncidentMapper {

	/**
	 * 保存单个热门事件
	 * @see #insertV2(JSONObject)
	 * @param params 热门事件
	 * @return Integer
	 * */
	Integer insertV2(JSONObject params);

	/**
	 * 修改单个热门事件
	 * @see #updateV2(JSONObject)
	 * @param params 热门事件
	 * @return Integer
	 * */
	Integer updateV2(JSONObject params);

	/**
	 * 删除热门事件
	 * @see #delete(Integer)
	 * @param id 条件
	 * @return Integer
	 * */
	Integer delete(@Param("id") Integer id);

	/**
	 * 查询热门事件列表
	 * @see #findAllV2(JSONObject)
	 * @param params 条件
	 * @return List<HotIncident>
	 * */
	List<HotIncident> findAllV2(JSONObject params);

	/**
	 * 查询单个热门事件
	 * @see #findOneV2(Integer)
	 * @param id 条件
	 * @return HotIncident
	 * */
	HotIncident findOneV2(Integer id);

	/**
	 * 修改热门事件关联对象
	 * @see #updateHotIncidentRelationId(String[], Integer, Integer)
	 * @param ids 条件
	 * @param relationId 条件
	 * @param type 条件
	 * @return Integer
	 * */
	Integer updateHotIncidentRelationId(@Param("ids") String[] ids, @Param("relationId") Integer relationId, @Param("type") Integer type);

	/**
	 * 删除热门事件关联对象
	 * @see #deleteHotIncidentRelationId(String[], Integer, Integer)
	 * @param ids 条件
	 * @param relationId 条件
	 * @param type 条件
	 * @return Integer
	 * */
	Integer deleteHotIncidentRelationId(@Param("ids") String[] ids, @Param("relationId") Integer relationId, @Param("type") Integer type);

	/**
	 * 置顶
	 * @see #updateTop(Integer, Integer)
	 * @param id 热门事件id
	 * @param whether 是否置顶
	 * @return Integer
	 * */
	Integer updateTop(@Param("id")Integer id,@Param("whether")Integer whether);

	/**
	 * 统计
	 * @see #findCount(JSONObject)
	 * @param params 查询参数
	 * @return Map<String,Object>
	 * */
	Map<String,Object> findCount(JSONObject params);

	/**
	 * 类型统计
	 * @see #findTypeCount(JSONObject)
	 * @param params 查询参数
	 * @return List<String>
	 * */
	List<String> findTypeCount(JSONObject params);

	/**
	 * 省份分组找出最高热度值事件
	 * @see #findProvinceGroupBy(JSONObject)
	 * @param params 查询参数
	 * @return List<HotIncident>
	 * */
	List<HotIncident> findProvinceGroupBy(JSONObject params);
}
