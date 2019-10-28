/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncidentServiceImpl.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.service.impl 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月17日 下午1:13:23 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.mediasel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.miduchina.wrd.api.rankinglist.mapper.NationalMediaMapper;
import com.miduchina.wrd.api.rankinglist.mediasel.service.NationalMediaService;
import com.miduchina.wrd.po.ranking.HistoryRanking;
import com.miduchina.wrd.po.ranking.StatisData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: NationalMediaServiceImpl
 * @Description: 全国媒体排行榜
 * @author: 许双龙
 * @date: 2018年9月10日 下午8:04:08
 */
@Service
public class NationalMediaServiceImpl implements NationalMediaService {

	@Resource
	private NationalMediaMapper nationalMediaMapper;

	@Override
	public List<HistoryRanking> queryHistoryRankingList() {
		return nationalMediaMapper.findAll();
	}

	@Override
	public PageInfo<StatisData> findStatisDatas(Map<String, Object> map,
												Integer page, Integer pagesize) {
		PageInfo<StatisData> pi = new PageInfo<StatisData>();
		PageInfo<String> sList= PageHelper.startPage(page, pagesize).doSelectPageInfo(() -> nationalMediaMapper.findBaseList(map));
		List<String> str = sList.getList();
		
		pi.setTotal(sList.getTotal());
		pi.setPages(sList.getPages());
		List<String> serviceUnits = new ArrayList<>();
		for(int i=0;i<str.size();i++) {
			serviceUnits.add(str.get(i));
		}

		if(CollectionUtils.isNotEmpty(serviceUnits)){
			map.put("serviceUnits", serviceUnits);
			List<StatisData> statisDatas = nationalMediaMapper.findStatisDataList(map);
			List<Integer> lis = new ArrayList<>();
			List<String> sLs = new ArrayList<>();
			for(StatisData ss : statisDatas){
				lis.add(ss.getId());
				sLs.add(ss.getServiceUnit());
			}
			map.put("lis", lis);
			map.put("sLs", sLs);
			List<StatisData> statisDatas2 = nationalMediaMapper.findStatisDataList2(map);
			for(StatisData ad : statisDatas2){
				statisDatas.add(ad);
			}
			pi.setList(statisDatas);
		}
		
		return pi;
	}

	@Override
	public List<Integer> findMonthList() {
		return nationalMediaMapper.findMonthList();
	}
	
	
}
