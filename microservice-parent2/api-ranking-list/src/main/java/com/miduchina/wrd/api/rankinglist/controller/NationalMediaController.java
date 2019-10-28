package com.miduchina.wrd.api.rankinglist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.mediasel.service.NationalMediaService;
import com.miduchina.wrd.api.rankinglist.util.NationalUtils;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.AbilityDto;
import com.miduchina.wrd.po.ranking.StatisData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @ClassName: NationalMediaController
 * @Description: 全国媒体排行榜
 * @author: 许双龙
 * @date: 2018年9月10日 下午8:04:08
 */
@Api(tags = {"全国媒体排行榜"})
@RestController
@RequestMapping("api/v3/media")
public class NationalMediaController {
	
	@Autowired
	private NationalMediaService nationalMediaService;
	
	@ApiOperation(value = "获取全国媒体排行榜列表", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "明星排名列表") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "starRankList", headers = "Accept=application/json,application/xml")
	public PageDto<AbilityDto> dayRankList(
			@ApiParam(value = "平台标识(0:unknown 1:sjzx 2:wyq 3:yqt 4:sjb 5:open)") @RequestParam(required = true) Integer platform,
			@ApiParam(value = "渠道标识(0:unknown 1:h5 2:web 3:app)") @RequestParam(required = true) Integer channel,
			@ApiParam(value = "用户标识") @RequestParam(required = true) String userTag,
			@ApiParam(value = "媒体类型") @RequestParam(required = true) String rankingType,
			@ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
			@ApiParam(value = "时间") @RequestParam(required = true) Integer time,
			@ApiParam(value = "当前页") @RequestParam(required = true) Integer page,
			@ApiParam(value = "页大小") @RequestParam(required = true) Integer pagesize
			) {
		PageDto<AbilityDto> pageDto = new PageDto<>();
		String area = "全国";
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.isBlank(rankingType)){
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		List<AbilityDto> abilityVOList = new ArrayList<>();
		map.put("rankingType", rankingType);
		map.put("area", area);
		map.put("time", time);
		map.put("keyword", keyword);
		PageInfo<StatisData> pageInfo = nationalMediaService.findStatisDatas(map, page, pagesize);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		List<StatisData> items = pageInfo.getList();
		abilityVOList = NationalUtils.getRankingList(items);
		List<AbilityDto> abilityVOList2 = null;
		String lastTime = NationalUtils.getLastMonth(time+"");
		if(StringUtils.isNotBlank(lastTime)){
			map = new HashMap<>();
			map.put("rankingType", rankingType);
			map.put("area", area);
			map.put("time", lastTime);
			map.put("keyword", keyword);
			PageInfo<StatisData> findStatisDatas2 = nationalMediaService.findStatisDatas(map, page, pagesize);
			abilityVOList2 = NationalUtils.getRankingList(findStatisDatas2.getList());
			abilityVOList = NationalUtils.getLastList(abilityVOList,abilityVOList2);
			pageDto.setData(abilityVOList);
			pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
			return pageDto;
		}
		pageDto.setData(abilityVOList);
		pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		return pageDto;
	}
	
	@ApiOperation(value = "获取日期下拉列表", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "日期列表") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "monthList", headers = "Accept=application/json")
	public PageDto<Integer> monthList() {
		PageDto<Integer> pageDto = new PageDto<>();
		List<Integer> list = nationalMediaService.findMonthList();
		if(CollectionUtils.isEmpty(list)){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}
		pageDto.setData(list);
		pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		return pageDto;
	}

}
