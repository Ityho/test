/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotPersonMapper.java
 * @Prject: ranking-list-api
 * @Package: com.miduchina.wrd.api.rankinglist.mapper
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月16日 上午10:00:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.mapper;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPerson;
import com.miduchina.wrd.po.ranking.RankingListWeb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: HotPersonMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HotPersonMapper {

	/**
	 * 查询热门事件人物列表
	 * @see #findAll(JSONObject)
	 * @param params 热门事件人物
	 * @return List<HotPerson>
	 * */
	List<HotPerson> findAll(JSONObject params);

	/**
	 * 联合查询热门事件人物、热门新闻人物列表
	 * @see #findAllUnion(JSONObject)
	 * @param params 热门事件人物，明星，人物
	 * @return List<HotPerson>
	 * */
	List<HotPerson> findAllUnion(JSONObject params);

	/**
	 * 联合查询热门事件人物、热门新闻人物、热门事件列表
	 * @see #findAllUnionHotIncident(JSONObject)
	 * @param params 热门事件人物，明星，人物
	 * @return List<HotPerson>
	 * */
	List<HotPerson> findAllUnionHotIncident(JSONObject params);

	/**
	 * 查询单个热门人物
	 * @see #findOne(Integer)
	 * @param id 主键
	 * @return HotPerson
	 * */
	HotPerson findOne(Integer id);

	/**
	 * 保存单个热门人物
	 * @see #insert(JSONObject)
	 * @param params 热门人物
	 * @return Integer
	 * */
	Integer insert(JSONObject params);

	/**
	 * 保存单个热门人物关联对象
	 * @see #insertHotIncidentRelation(JSONObject)
	 * @param params 热门人物
	 * @return Integer
	 * */
	Integer insertHotIncidentRelation(JSONObject params);

	/**
	 * 保存单个热门人物关联对象
	 * @see #insertHotIncidentRelation(JSONObject)
	 * @param params 热门人物
	 * @return Integer
	 * */
	Integer insertBatchHotIncidentRelation(JSONObject params);

	/**
	 * 更新单个热门人物
	 * @see #update(JSONObject)
	 * @param params 热门人物
	 * @return Integer
	 * */
	Integer update(JSONObject params);

	/**
	 * 删除单个热门人物
	 * @see #delete(Integer)
	 * @param id 热门人物
	 * @return Integer
	 * */
	Integer delete(Integer id);

	/**
	 * 查询关联热门事件id
	 * @see #findAllHotIncidentRelationId(Integer)
	 * @param id 热门人物编号
	 * @return List<Integer>
	 * */
	Integer findAllHotIncidentRelationId(@Param("id")Integer id);

	/**
	 * 修改关联热门事件
	 * @see #updateHotIncidentRelation(List)
	 * @param hotIncidentList 热门事件集合
	 * @return Integer
	 * */
	Integer updateHotIncidentRelation(@Param("hotIncidentList") List<HotIncident> hotIncidentList);

	/**
	 * 删除关联热门事件
	 * @see #deleteHotIncidentRelation(String,Integer,Integer)
	 * @param hotIncidentId 热门事件id
	 * @param personIds 关联id
	 * @param type 关联类型
	 * @return Integer
	 * */
	Integer deleteHotIncidentRelation(@Param("personIds") String personIds,@Param("hotIncidentId") Integer hotIncidentId,@Param("type") Integer type);

	/**
	 * 查询热门标签
	 * @see #findAllByShowTag(Integer)
	 * @param showTag 显示/隐藏
	 * @return List<HotLabel>
	 * */
	List<RankingListWeb> findAllByShowTag(@Param("showTag") Integer showTag);

	/**
	 * 查询热门事件
	 * @see #findHotIncidentByIds(List)
	 * @param hotIncidentIds 热门事件id集合
	 * @return List<HotIncident>
	 * */
	List<HotIncident> findHotIncidentByIds(@Param("hotIncidentIds")List<Integer> hotIncidentIds);
}
