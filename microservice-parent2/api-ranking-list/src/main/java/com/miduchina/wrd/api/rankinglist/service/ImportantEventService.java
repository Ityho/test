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
import com.miduchina.wrd.po.ranking.ImportantEvent;

/**
 * @ClassName: ImportantEventService
 * @Description: 重大事件业务接口
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
public interface ImportantEventService {

	/**
	 * 查询重大事件列表
	 * @see #list(JSONObject)
	 * @param params 重大事件
	 * @return PageInfo<ImportantEvent>
	 * */
	PageInfo<ImportantEvent> list(JSONObject params);

	/**
	 * 查询重大事件列表(前台)
	 * @see #webList(JSONObject)
	 * @param params 重大事件
	 * @return PageInfo<ImportantEvent>
	 * */
	PageInfo<ImportantEvent> webList(JSONObject params);

	/**
	 * 查询单个重大事件
	 * @see #get(Integer)
	 * @param id 重大事件id
	 * @return ImportantEvent
	 * */
	ImportantEvent get(Integer id);

	/**
	 * 查询单个重大事件(前台)
	 * @see #get(Integer)
	 * @param params 重大事件id
	 * @return ImportantEvent
	 * */
	ImportantEvent webGet(JSONObject params);

	/**
	 * 保存单个重大事件
	 * @see #save(JSONObject)
	 * @param params 重大事件
	 * @return Boolean
	 * */
	Boolean save(JSONObject params);

	/**
	 * 更新单个重大事件
	 * @see #modify(JSONObject)
	 * @param params 重大事件
	 * @return Boolean
	 * */
	Boolean modify(JSONObject params);

	/**
	 * 删除单个重大事件
	 * @see #remove(Integer)
	 * @param id 重大事件id
	 * @return Boolean
	 * */
	Boolean remove(Integer id);
}
