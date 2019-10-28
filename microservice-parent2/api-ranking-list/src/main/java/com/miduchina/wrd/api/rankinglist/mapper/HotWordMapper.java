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
import com.miduchina.wrd.po.ranking.HotWord;
/** 
 * @ClassName: HotWordMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HotWordMapper {

	/**
	 * 查询热词列表
	 * @see #findAll(JSONObject)
	 * @param params 热词
	 * @return PageInfo<HotWord>
	 * */
	List<HotWord> findAll(JSONObject params);

	/**
	 * 查询单个热词
	 * @see #findOne(Integer)
	 * @param id 主键
	 * @return HotWord
	 * */
	HotWord findOne(Integer id);

	/**
	 * 保存单个热词
	 * @see #insert(HotWord)
	 * @param hotWord 热词
	 * @return Boolean
	 * */
	Integer insert(HotWord hotWord);

	/**
	 * 更新单个热词
	 * @see #update(HotWord)
	 * @param hotWord 热词
	 * @return Integer
	 * */

	Integer update(HotWord hotWord);

	/**
	 * 删除单个热词
	 * @see #delete(Integer)
	 * @param id 热词
	 * @return Integer
	 * */
	Integer delete(Integer id);
	
	Integer insertHotWordRelation(HotWord hotWord);

	/**
	 * 查询热词名称是否存在
	 * @see #findHotWordName(String)
	 * @param name 名称
	 * @return Integer
	 * */
	Integer findHotWordName(String name);

}
