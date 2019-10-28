/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncidentService.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.service
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月17日 下午1:12:54 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.ranking.HotPerson;
import com.miduchina.wrd.po.ranking.RankingListWeb;

import java.util.List;

/**
 * @ClassName: HotPersonService
 * @Description: 热门人物业务接口
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
public interface HotPersonService {

	/**
	 * 查询热门事件人物列表
	 * @see #list(JSONObject)
	 * @param params 热门事件人物
	 * @return PageInfo<HotPerson>
	 * */
	PageInfo<HotPerson> list(JSONObject params);

	/**
	 * 查询热门事件人物列表(前台)
	 * @see #webList(JSONObject)
	 * @param params 热门事件人物
	 * @return PageInfo<HotPerson>
	 * */
	PageInfo<HotPerson> webList(JSONObject params);

	/**
	 * 查询单个热门人物
	 * @see #get(Integer)
	 * @param id 热门人物id
	 * @return HotPerson
	 * */
	HotPerson get(Integer id);

	/**
	 * 保存单个热门人物
	 * @see #save(JSONObject)
	 * @param params 热门人物
	 * @return Boolean
	 * */
	Boolean save(JSONObject params);

	/**
	 * 更新单个热门人物
	 * @see #modify(JSONObject)
	 * @param params 热门人物
	 * @return Boolean
	 * */
	Boolean modify(JSONObject params);

	/**
	 * 删除单个热门人物
	 * @see #remove(Integer)
	 * @param id 热门人物id
	 * @return Boolean
	 * */
	Boolean remove(Integer id);

	/**
	 * 删除热门事件关联id
	 * @see #removeHotIncidentRelationId(Integer, Integer, Integer)
	 * @param hotIncidentId 热门事件id
	 * @param personId 关联id
	 * @param type 关联类型
	 * @return Boolean
	 * */
	Boolean removeHotIncidentRelationId(Integer hotIncidentId, Integer personId, Integer type);

	/**
	 * 查询标签列表
	 * @see #findAllByShowTag(Integer)
	 * @param showTag 显示
	 * @return List<HotLabel>
	 * */
	List<RankingListWeb> findAllByShowTag(Integer showTag);

	/**
	 * 联合查询热门事件人物、热门新闻人物列表
	 * @see #listUnion(JSONObject)
	 * @param params 热门事件人物，明星，人物
	 * @return PageInfo<HotPerson>
	 * */
	PageInfo<HotPerson> listUnion(JSONObject params);

	/**
	 * 联合查询热门事件人物、热门新闻人物、热门事件列表
	 * @see #listUnionHotIncident(JSONObject)
	 * @param params 热门事件人物，明星，人物
	 * @return PageInfo<HotPerson>
	 * */
	PageInfo<HotPerson> listUnionHotIncident(JSONObject params);
}
