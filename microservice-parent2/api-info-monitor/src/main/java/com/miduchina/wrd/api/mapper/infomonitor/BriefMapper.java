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
package com.miduchina.wrd.api.mapper.infomonitor;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.ranking.HotIncident;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: BriefMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
@Mapper
public interface BriefMapper {

	/**
	 * 查询用户素材总量
	 * @see #getSourceMaterialCount(JSONObject)
	 * @param parmas 查询参数
	 * @return Integer
	 * */
	Integer getSourceMaterialCount(JSONObject parmas);
}
