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
import com.miduchina.wrd.po.ranking.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HotPhenomenoneService
 * @Description: 热门现象业务接口
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
public interface HotPhenomenoneService {

	/**
	 * 查询热门现象列表
	 * @see #list(JSONObject)
	 * @param params 热门现象
	 * @return PageInfo<HotPhenomenone>
	 * */
	PageInfo<HotPhenomenone> list(JSONObject params);

	/**
	 * 查询单个热门现象
	 * @see #get(Integer)
	 * @param id 热门现象id
	 * @return HotPhenomenone
	 * */
	HotPhenomenone get(Integer id);

	/**
	 * 保存单个热门现象
	 * @see #save(JSONObject)
	 * @param params 热门现象
	 * @return Boolean
	 * */
	Boolean save(JSONObject params);

	/**
	 * 更新单个热门现象
	 * @see #modify(JSONObject)
	 * @param params 热门现象
	 * @return Boolean
	 * */
	Boolean modify(JSONObject params);

	/**
	 * 删除单个热门现象
	 * @see #remove(Integer)
	 * @param id 热门现象id
	 * @return Boolean
	 * */
	Boolean remove(Integer id);
}
