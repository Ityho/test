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

import org.apache.ibatis.annotations.Param;

import com.miduchina.wrd.po.ranking.HotLabel;

/**
 * @ClassName: HotLabelMapper
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HotLabelMapper {

	/**
	 * 查询热门标签
	 * @see #findAllByShowTag(Integer)
	 * @param showTag 显示/隐藏
	 * @return List<HotLabel>
	 * */
	List<HotLabel> findAllByShowTag(@Param("showTag") Integer showTag);
}
