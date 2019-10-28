/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncidentController.java 
 * @Prject: ranking-list-api
 * @Package: com.miduchina.wrd.api.rankinglist.controller
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年9月10日 下午8:04:08 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.ranking.HotPhenomenoneDto;
import com.miduchina.wrd.dto.ranking.HotWordDto;
import com.miduchina.wrd.dto.ranking.ImportantEventDto;
import com.miduchina.wrd.util.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import com.miduchina.wrd.po.ranking.HotWord;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import com.miduchina.wrd.api.rankinglist.service.HotIncidentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/** 
 * @ClassName: HotIncidentV2Controller
 * @Description: 热门事件
 * @author: 许双龙
 * @date: 2018年9月10日 下午8:04:08  
 */
@Api(tags = {"热门事件"})
@RestController
@RequestMapping("api/v2")
public class HotIncidentV2Controller {
	
	@Autowired
	private HotIncidentService hotIncidentService;
	
	@ApiOperation(value = "查询热门事件", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门事件") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/hotIncident", headers = "Accept=application/json")
	public PageDto<HotIncidentDto> listHotIncident(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotIncidentDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotIncident> pageInfo = hotIncidentService.listV2(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		List<HotIncidentDto> hiDtoList = new ArrayList<>();
		for (HotIncident hi : pageInfo.getList()) {
			HotIncidentDto hiDto = BeanUtils.copyProperties(hi,HotIncidentDto.class);
			if(StringUtils.isNotBlank(hiDto.getLabels())){
				String[] labels = hiDto.getLabels().split(",");
				List<String> labelNameList = new ArrayList<>();
				for (String labelId : labels) {
					labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
				}
				hiDto.setLabelNames(StringUtils.join(labelNameList,","));
			}
			hiDtoList.add(hiDto);
		}
		pageDto.setData(hiDtoList);
		pageDto.setCode(CodeConstant.SUCCESS_CODE);
		pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		return pageDto;
	}
	
	@ApiOperation(value = "查询热门现象", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门现象") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/hotPhenomenone", headers = "Accept=application/json")
	public PageDto<HotPhenomenoneDto> listHotPhenomenone(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotPhenomenoneDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotPhenomenone> pageInfo = hotIncidentService.findHotPhenomenoneList(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		pageDto.setData(BeanUtils.copyTo(pageInfo.getList(),HotPhenomenoneDto.class));
		pageDto.setCode(CodeConstant.SUCCESS_CODE);
		pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		return pageDto;
	}
	
	@ApiOperation(value = "查询重大事件", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询重大事件") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/importantEvent", headers = "Accept=application/json")
	public PageDto<ImportantEventDto> listImportantEvent(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<ImportantEventDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		PageInfo<ImportantEvent> pageInfo = hotIncidentService.findImportantEventList(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		pageDto.setData(BeanUtils.copyTo(pageInfo.getList(),ImportantEventDto.class));
		pageDto.setCode(CodeConstant.SUCCESS_CODE);
		pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		return pageDto;
	}
	
	@ApiOperation(value = "查询热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热词") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/hotWord", headers = "Accept=application/json")
	public PageDto<HotWordDto> listHotWord(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotWordDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}
		
		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotWord> pageInfo = hotIncidentService.findHotWordList(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		pageDto.setData(BeanUtils.copyTo(pageInfo.getList(),HotWordDto.class));
		pageDto.setCode(CodeConstant.SUCCESS_CODE);
		pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		return pageDto;
	}
	
	@ApiOperation(value = "热点排行", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "热点排行") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/hot", headers = "Accept=application/json")
	public PageDto<?> hotListV2(@ApiParam(value = "筛选条件(page:页码数 pageSize:每页数量 type:1-热门事件,2-热门现象,3-重大事件,6-热词 incidentTitle:标题 origin:来源 label:一级标签 label1:二级标签 areaType:1-国内,2-国外 province:省份 city:城市 startTime:开始时间 endTime:结束时间 sort:1-序列,2-热度,默认序列 order:1-降序,2-升序,默认降序)")
			@RequestParam(required = false) Map<String,Object> params) {
		PageDto<?> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("type必填");
		}

		JSONObject jsonParams = new JSONObject(params);
		PageInfo<?> pageInfo = hotIncidentService.findHotListV2(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		pageDto.setData(pageInfo.getList());
		pageDto.setCode(CodeConstant.SUCCESS_CODE);
		pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		pageDto.setMaxPage(pageInfo.getPages());
		pageDto.setTotalCount(pageInfo.getTotal());
		return pageDto;
	}
	
	@ApiOperation(value = "添加热点关联", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "添加热点关联") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "relation/hotIncident", headers = "Accept=application/json")
	public BaseDto addRelationHotIncident(
			@ApiParam(value = "热点事件id") @RequestParam(required = false) String ids,
			@ApiParam(value = "关联id") @RequestParam(required = false) Integer relationId,
			@ApiParam(value = "类型(1:热门事件 2:热门现象 3:重大事件 4:热词)") @RequestParam Integer type) {
		BaseDto baseDto = new BaseDto();
		if (StringUtils.isBlank(ids) || ArrayUtils.isEmpty(ids.split(",")) || relationId == null || type == null) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}
		Boolean flag = hotIncidentService.modifyHotIncidentRelationId(ids,relationId,type);
		if(flag) {
			return baseDto.setCode(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
	}
	
	@ApiOperation(value = "查询单个热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询单个热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "get/hotIncident", headers = "Accept=application/json")
	public BaseDto<HotIncidentDto> getHotIncidentV2(
			@ApiParam(value = "热词id") @RequestParam Integer id) {
		BaseDto<HotIncidentDto> baseDto = new BaseDto<>();
		if (id == null) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}
		
		HotIncident hotIncident = hotIncidentService.findOneV2(id);
		if(hotIncident == null) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		HotIncidentDto hiDto = BeanUtils.copyProperties(hotIncident,HotIncidentDto.class);
		if(StringUtils.isNotBlank(hiDto.getLabels())){
			String[] labels = hiDto.getLabels().split(",");
			List<String> labelNameList = new ArrayList<>();
			for (String labelId : labels) {
				labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
			}
			hiDto.setLabelNames(StringUtils.join(labelNameList,","));
		}

		baseDto.setData(hiDto);
		baseDto.setCode(CodeConstant.SUCCESS_CODE);
		baseDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
		return baseDto;
	}
	
	@ApiOperation(value = "添加热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "添加热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "add/hotIncident", headers = "Accept=application/json")
	public BaseDto addHotIncidentV2(@RequestParam(required = false) Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		Boolean flag = hotIncidentService.addV2(jsonParams);
		if(flag) {
			return baseDto.setCode(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
	}
	
	@ApiOperation(value = "更新热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "更新热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "modify/hotIncident", headers = "Accept=application/json")
	public BaseDto modifyHotIncidentV2(
			@ApiParam(value = "修改对象(1:热门事件 2:热门现象 3:重大事件 4:热词)") @RequestParam Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		Boolean flag = hotIncidentService.modifyV2(jsonParams);
		if(flag) {
			return baseDto.setCode(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
	}
	
	@ApiOperation(value = "删除热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "删除热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove/hotIncident", headers = "Accept=application/json")
	public BaseDto removeHotIncident(
			@ApiParam(value = "热词") @RequestParam Integer id,
			@ApiParam(value = "类型(1:热门事件 2:热门现象 3:重大事件 6:热词)") @RequestParam Integer type) {
		BaseDto baseDto = new BaseDto();
		if (id == null || type == null) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}
		
		Boolean flag = hotIncidentService.removeV2(id,type);
		if(flag) {
			return baseDto.setCode(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
	}
}