package com.miduchina.wrd.api.rankinglist.service.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.rankinglist.mapper.HeatStatisticsMapper;
import com.miduchina.wrd.api.rankinglist.service.HeatStatisticsService;
import com.miduchina.wrd.po.ranking.HeatStatistics;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
/**
 * @ClassName: HeatStatisticsServiceImpl 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月17日 上午9:37:41  
 */
@Service
public class HeatStatisticsServiceImpl implements HeatStatisticsService {
	
	@Resource
	private HeatStatisticsMapper heatStatisticsMapper;

	@Override
	public PageInfo<HeatStatistics> findHeatStatisticsAll(Integer type, Integer page, Integer pagesize, Integer sort, Integer order) {
		String table = "hour";
		if(type != null) {
			if(type == 2) {
				table = "day";
			}else if(type == 3) {
				table = "week";
			}else if(type == 4) {
				table = "month";
			}
		}
		final String fTable = table;
		PageInfo<HeatStatistics> pageInfo = PageHelper.startPage(page, pagesize).doSelectPageInfo(() -> heatStatisticsMapper.findAll(fTable,sort,order));
		return pageInfo;
	}
}
