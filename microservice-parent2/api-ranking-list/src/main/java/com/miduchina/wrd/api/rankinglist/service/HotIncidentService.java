/**
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncidentService.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.service
 * @Description: 热门现象业务接口
 * @author: 许双龙   
 * @date: 2018年8月17日 下午1:12:54 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import com.miduchina.wrd.po.ranking.HotWord;
import com.miduchina.wrd.po.ranking.ImportantEvent;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HotIncidentService 
 * @Description: 热门现象业务接口
 * @author: 许双龙
 * @date: 2018年8月17日 下午1:12:54  
 */
public interface HotIncidentService {

	/**
	 * 查询热门事件列表
	 * @see #listV2(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo<HotIncident>
	 * */
	PageInfo<HotIncident> listV2(JSONObject params);

	/**
	 * 查询热门事件列表(前台)
	 * @see #listV2(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo<HotIncident>
	 * */
	PageInfo<HotIncident> webListV2(JSONObject params);

	/**
	 * 查询单个热门事件
	 * @see #findOneV2(Integer)
	 * @param id 热门事件id
	 * @return HotIncident
	 * */
	HotIncident findOneV2(Integer id);

	/**
	 * 保存单个热门事件
	 * @see #addV2(JSONObject)
	 * @param params 热门事件
	 * @return Boolean
	 * */
	Boolean addV2(JSONObject params);

	/**
	 * 更新单个热门事件
	 * @see #modifyV2(JSONObject)
	 * @param params 查询参数
	 * @return Boolean
	 * */
	Boolean modifyV2(JSONObject params);

	/**
	 * 删除单个热门事件
	 * @see #removeV2(Integer, Integer)
	 * @param id 热门事件id
	 * @param type 类型
	 * @return Boolean
	 * */
	Boolean removeV2(Integer id, Integer type);

	/**
	 * 查询热门现象列表
	 * @see #findHotPhenomenoneList(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo<HotPhenomenone>
	 * */
	PageInfo<HotPhenomenone> findHotPhenomenoneList(JSONObject params);

	/**
	 * 查询重大事件列表
	 * @see #findHotPhenomenoneList(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo<ImportantEvent>
	 * */
	PageInfo<ImportantEvent> findImportantEventList(JSONObject params);

	/**
	 * 查询热词列表
	 * @see #findHotPhenomenoneList(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo<HotWord>
	 * */
	PageInfo<HotWord> findHotWordList(JSONObject params);

	/**
	 * 查询热门事件、重大事件、热门现象、热词列表
	 * @see #findHotPhenomenoneList(JSONObject)
	 * @param params 查询参数
	 * @return PageInfo
	 * */
	PageInfo<?> findHotListV2(JSONObject params);

	/**
	 * 修改热门事件关联id
	 * @see #modifyHotIncidentRelationId(String, Integer, Integer)
	 * @param ids 热门事件id
	 * @param relationId 关联id
	 * @param type 关联类型
	 * @return Boolean
	 * */
	Boolean modifyHotIncidentRelationId(String ids, Integer relationId, Integer type);

	/**
	 * 删除热门事件关联id
	 * @see #removeHotIncidentRelationId(String, Integer, Integer)
	 * @param ids 热门事件id
	 * @param relationId 关联id
	 * @param type 关联类型
	 * @return Boolean
	 * */
	Boolean removeHotIncidentRelationId(String ids, Integer relationId, Integer type);

	/**
	 * 置顶
	 * @see #modifyTop(Integer, Integer)
	 * @param id 热门事件id
	 * @param whether 是否置顶
	 * @return Boolean
	 * */
	Boolean modifyTop(Integer id,Integer whether);

	/**
	 * 统计
	 * @see #findCount(JSONObject)
	 * @param params 查询参数
	 * @return Map
	 * */
	Map<String,Object> findCount(JSONObject params);

	/**
	 * 类型统计
	 * @see #findTypeCount(JSONObject)
	 * @param params 查询参数
	 * @return Map
	 * */
	Map<String,Integer> findTypeCount(JSONObject params);

	/**
	 * 查询单个热门事件
	 * @see #findProvinceGroupBy(JSONObject)
	 * @param params 查询参数
	 * @return List<HotIncident>
	 * */
	List<HotIncident> findProvinceGroupBy(JSONObject params);
}
