/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: RankingListMapper.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.mapper 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月16日 上午10:00:13 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.mapper;

import com.miduchina.wrd.po.ranking.HeatStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName: RankingListMapper 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月16日 上午10:00:13  
 */
public interface HeatStatisticsMapper {
	
	List<HeatStatistics> findAll(@Param("table") String fTable, @Param("sort") Integer sort, @Param("order") Integer order);

}
