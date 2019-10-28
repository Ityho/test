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
import com.miduchina.wrd.po.ranking.HotWord;

/**
 * @ClassName: HotWordService
 * @Description: 热词业务接口
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
public interface HotWordService {

	/**
	 * 查询热词列表
	 * @see #list(JSONObject)
	 * @param params 热词
	 * @return PageInfo<HotWord>
	 * */
	PageInfo<HotWord> list(JSONObject params);

	/**
	 * 查询单个热词
	 * @see #get(Integer)
	 * @param id 热词id
	 * @return HotWord
	 * */
	HotWord get(Integer id);

	/**
	 * 删除单个热词
	 * @see #remove(Integer)
	 * @param id 热词id
	 * @return Boolean
	 * */
	Boolean remove(Integer id);
}
