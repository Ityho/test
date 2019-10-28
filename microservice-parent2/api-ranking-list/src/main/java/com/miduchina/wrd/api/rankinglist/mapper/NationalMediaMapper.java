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

import com.miduchina.wrd.po.ranking.HistoryRanking;
import com.miduchina.wrd.po.ranking.StatisData;

import java.util.List;
import java.util.Map;


public interface NationalMediaMapper {
	
	List<HistoryRanking> findAll();

	Integer findCount(Map<String, Object> map);

	List<String> findBaseList(Map<String, Object> map);

	List<StatisData> findStatisDataList(Map<String, Object> map);

	List<StatisData> findStatisDataList2(Map<String, Object> map);

	List<Integer> findMonthList();

}
