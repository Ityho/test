/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HeatStatisticsService.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.service 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月17日 上午9:37:17 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.service;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.ranking.HeatStatistics;

/**
 * @ClassName: HeatStatisticsService 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月17日 上午9:37:17  
 */
public interface HeatStatisticsService {
	
	PageInfo<HeatStatistics> findHeatStatisticsAll(Integer type, Integer page, Integer pagesize, Integer sort, Integer order);
	
}
