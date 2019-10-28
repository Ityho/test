/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HeatStatisticsController.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.controller 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年8月17日 上午9:36:49 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.controller;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.service.HeatStatisticsService;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HeatStatisticsDto;
import com.miduchina.wrd.po.ranking.HeatStatistics;
import com.miduchina.wrd.util.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/** 
 * @ClassName: HeatStatisticsController 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年8月17日 上午9:36:49  
 */
@Api(tags = {"明星排行榜"})
@RestController
@RequestMapping("api/v3/statistics")
public class HeatStatisticsV3Controller {
	
	@Autowired
	private HeatStatisticsService heatStatisticsService;
	
	@ApiOperation(value = "获取明星排名榜列表", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "明星排名列表") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "starRankList", headers = "Accept=application/json")
	public PageDto<HeatStatisticsDto> dayRankList(
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "返回格式") @RequestParam(required = false) String format,
			@ApiParam(value = "当前页") @RequestParam(required = true) Integer page,
			@ApiParam(value = "页大小") @RequestParam(required = true) Integer pagesize,
			@ApiParam(value = "排序字段选项(1:时榜 2:日榜 3:周榜 4:月榜 default:1)") @RequestParam(required = false) Integer type,
			@ApiParam(value = "升降序选项(1:名称/代码/字母 2:(本日)热度值 3:(本日与上日)热度变化 4:(本日)声量 5:(本日)排行 6:(本日与上日)排名变化  default:3)") @RequestParam(required = false) Integer sort,
			@ApiParam(value = "排序字段选项(1:降序 2:升序 default:1)") @RequestParam(required = false) Integer order) {
		PageDto<HeatStatisticsDto> pageDto = new PageDto<>();
		PageInfo<HeatStatistics> pageInfo = heatStatisticsService.findHeatStatisticsAll(type, page, pagesize, sort, order);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
		}
		
		List<HeatStatisticsDto> heatStatisticsList = BeanUtils.copyTo(pageInfo.getList(), HeatStatisticsDto.class);
		pageDto.setList(heatStatisticsList);
		pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		return pageDto;
	}
	
}
