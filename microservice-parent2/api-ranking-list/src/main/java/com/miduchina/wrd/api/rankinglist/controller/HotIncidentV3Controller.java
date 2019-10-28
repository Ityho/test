/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncidentController.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.com.miduchina.wrd.api.rankinglist.controller
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年9月10日 下午8:04:08 
 * @version: V1.0   
 */
package com.miduchina.wrd.api.rankinglist.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.HotIncidentService;
import com.miduchina.wrd.api.rankinglist.service.HotPhenomenoneService;
import com.miduchina.wrd.api.rankinglist.service.HotWordService;
import com.miduchina.wrd.api.rankinglist.service.ImportantEventService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.*;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import com.miduchina.wrd.po.ranking.HotWord;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.DateUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.*;

/** 
 * @ClassName: HotIncidentV2Controller
 * @Description: 热门事件
 * @author: 许双龙
 * @date: 2018年9月10日 下午8:04:08  
 */
@Api(tags = {"热门事件"})
@RestController
@RequestMapping("api/v3/hotIncident")
public class HotIncidentV3Controller {

	@Autowired
	private HotIncidentService hotIncidentService;

	@Autowired
	private HotPhenomenoneService hotPhenomenoneService;

	@Autowired
	private ImportantEventService importantEventService;

	@Autowired
	private HotWordService hotWordService;

	@Value("classpath:city.json")
	private Resource areaRes;

	@Value("${wrdurl}")
	private String wrdurl;

	@ApiOperation(value = "查询热门事件", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门事件") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list", headers = "Accept=application/json")
	public PageDto<HotIncidentDto> list(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotIncidentDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotIncident> pageInfo = hotIncidentService.listV2(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
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

		return pageDto.setData(hiDtoList)
				.setList(hiDtoList)
				.setCodeMessage(CodeConstant.SUCCESS_CODE)
				.setMaxPage(pageInfo.getPages())
				.setTotalCount(pageInfo.getTotal());
	}

	@ApiOperation(value = "查询热门事件(前台)", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门事件(前台)") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "web/list", headers = "Accept=application/json")
	public PageDto<HotIncidentDto> webList(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotIncidentDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		params.put("showTag","1");
		params.put("labelShowTag","0");
        if (StringUtils.isNotBlank((String)params.get("startTime"))&&StringUtils.isNotBlank((String)params.get("endTime"))){
            int startTime = DateUtils.countDays((String) params.get("startTime"));
            int endTime = DateUtils.countDays((String) params.get("endTime"));
            int date = endTime - startTime;
            params.put("date",date);
        }
		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotIncident> pageInfo = hotIncidentService.webListV2(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
		}

		List<HotIncidentDto> hiDtoList = new ArrayList<>();
		for (HotIncident hi : pageInfo.getList()) {
			HotIncidentDto hiDto = BeanUtils.copyProperties(hi,HotIncidentDto.class);
			if(StringUtils.isNotBlank(hiDto.getLabels())){
				String[] labels = hiDto.getLabels().split(",");
				Set<String> labelNameList = new HashSet<>();
				for (String labelId : labels) {
					labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
				}
				hiDto.setLabelNames(StringUtils.join(labelNameList,","));
			}

			try {
				String areaData =  IOUtils.toString(areaRes.getInputStream(), Charset.forName("UTF-8"));
				List<AreaDto> areaDtoList = JSON.parseArray(areaData,AreaDto.class);
				if(StringUtils.isNotBlank(hi.getProvince())){
					AreaDto areaDto = areaDtoList.stream().filter(a -> hi.getProvince().equals(a.getProvince())).findFirst().get();
					hiDto.setLat(areaDto.getLat());
					hiDto.setLng(areaDto.getLng());
				}
				if(StringUtils.isNotBlank(hi.getCity())){
					AreaDto areaDto = areaDtoList.stream().filter(a -> hi.getCity().equals(a.getCity())).findFirst().get();
					hiDto.setLat(areaDto.getLat());
					hiDto.setLng(areaDto.getLng());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			hiDto.setHotDetailsUrl(wrdurl+hi.getHotIncidentId());
			hiDtoList.add(hiDto);
		}

		return pageDto.setList(hiDtoList)
				.setCodeMessage(CodeConstant.SUCCESS_CODE)
				.setMaxPage(pageInfo.getPages())
				.setTotalCount(pageInfo.getTotal());
	}

	@ApiOperation(value = "查询热门事件(前台)", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门事件(前台)") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "test/list", headers = "Accept=application/json")
	public PageDto<HotIncidentDto> web2List(@RequestParam(required = false) Map<String,Object> params) {
		PageDto<HotIncidentDto> pageDto = new PageDto<>();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		params.put("showTag","1");
		params.put("labelShowTag","0");
		params.put("startTime",DateUtils.format(DateUtils.addMonth(new Date(),-1)));
		params.put("endTime",DateUtils.getNow());
		if (StringUtils.isNotBlank((String)params.get("startTime"))&&StringUtils.isNotBlank((String)params.get("endTime"))){
			int startTime = DateUtils.countDays((String) params.get("startTime"));
			int endTime = DateUtils.countDays((String) params.get("endTime"));
			int date = endTime - startTime;
			params.put("date",date);
		}
		JSONObject jsonParams = new JSONObject(params);
		PageInfo<HotIncident> pageInfo = hotIncidentService.webListV2(jsonParams);
		if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
			return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
		}

		List<HotIncidentDto> hiDtoList = new ArrayList<>();
		for (HotIncident hi : pageInfo.getList()) {
			HotIncidentDto hiDto = new HotIncidentDto();
			hiDto.setIncidentTitle(hi.getIncidentTitle());
			hiDto.setOriginalUrl(hi.getOriginalUrl());
			hiDto.setProvince(hi.getProvince());
			hiDto.setCity(hi.getCity());
			hiDto.setRatioHotDay(hi.getRatioHotDay());
			hiDto.setDifferenceDay(hi.getDifferenceDay());
			hiDtoList.add(hiDto);
		}

		return pageDto.setList(hiDtoList)
				.setCodeMessage(CodeConstant.SUCCESS_CODE)
				.setMaxPage(pageInfo.getPages())
				.setTotalCount(pageInfo.getTotal());
	}

	@ApiOperation(value = "查询单个热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "查询单个热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "get", headers = "Accept=application/json")
	public BaseDto<HotIncidentDto> get(@ApiParam(value = "热词id") @RequestParam Integer id) {
		BaseDto<HotIncidentDto> baseDto = new BaseDto<>();
		if (id == null) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		HotIncident hotIncident = hotIncidentService.findOneV2(id);
		if(hotIncident == null) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
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

		return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(hiDto);
	}

	@ApiOperation(value = "添加热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "添加热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "add", headers = "Accept=application/json")
	public BaseDto add(@RequestParam(required = false) Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		Boolean flag = hotIncidentService.addV2(jsonParams);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "更新热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "更新热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "modify", headers = "Accept=application/json")
	public BaseDto modify(
			@ApiParam(value = "修改对象(1:热门事件 2:热门现象 3:重大事件 4:热词)") @RequestParam Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		Boolean flag = hotIncidentService.modifyV2(jsonParams);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "删除热词", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "删除热词") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove", headers = "Accept=application/json")
	public BaseDto remove(
			@ApiParam(value = "热词") @RequestParam Integer id,
			@ApiParam(value = "类型(1:热门事件 2:热门现象 3:重大事件 6:热词)") @RequestParam Integer type) {
		BaseDto baseDto = new BaseDto();
		if (id == null || type == null) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		Boolean flag = hotIncidentService.removeV2(id,type);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "添加热点关联", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "添加热点关联") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "relation", headers = "Accept=application/json")
	public BaseDto addRelationHotIncident(
			@ApiParam(value = "热点事件id") @RequestParam(required = false) String ids,
			@ApiParam(value = "关联id") @RequestParam(required = false) Integer relationId,
			@ApiParam(value = "类型(1:热门事件 2:热门现象 3:重大事件 4:热词)") @RequestParam Integer type) {
		BaseDto baseDto = new BaseDto();
		if (StringUtils.isBlank(ids) || ArrayUtils.isEmpty(ids.split(",")) || relationId == null || type == null) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}
		Boolean flag = hotIncidentService.modifyHotIncidentRelationId(ids,relationId,type);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "删除热点关联", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "删除热点关联") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "relation/remove", headers = "Accept=application/json")
	public BaseDto deleteRelationHotIncident(
			@ApiParam(value = "热点事件id") @RequestParam(required = false) String ids,
			@ApiParam(value = "关联id") @RequestParam(required = false) Integer relationId,
			@ApiParam(value = "类型(1:热门事件 2:热门现象 3:重大事件 4:热词)") @RequestParam Integer type) {
		BaseDto baseDto = new BaseDto();
		if (StringUtils.isBlank(ids) || ArrayUtils.isEmpty(ids.split(",")) || relationId == null || type == null) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}
		Boolean flag = hotIncidentService.removeHotIncidentRelationId(ids,relationId,type);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "下载榜单", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "热点排行") })
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "download", headers = "Accept=application/json")
	public PageDto download(@RequestParam(required = false) Map<String,Object> params) {
		PageDto pageDto = new PageDto();
		if (MapUtils.isEmpty(params)) {
			return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
		}

		JSONObject jsonParams = new JSONObject(params);
		if("1".equals(jsonParams.getString("type"))) {
			PageInfo<HotIncident> pageInfo = hotIncidentService.listV2(jsonParams);
			if(CollectionUtils.isEmpty(pageInfo.getList())) {
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
			pageDto.setMaxPage(pageInfo.getPages());
			pageDto.setTotalCount(pageInfo.getTotal());
		}else if("2".equals(jsonParams.getString("type"))) {
			PageInfo<HotPhenomenone> pageInfo = hotPhenomenoneService.list(jsonParams);
			if(CollectionUtils.isEmpty(pageInfo.getList())) {
				return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
			}
			List<HotPhenomenoneDto> hpDtoList = new ArrayList<>();
			for (HotPhenomenone hp : pageInfo.getList()) {
				HotPhenomenoneDto hpDto = BeanUtils.copyProperties(hp,HotPhenomenoneDto.class);
				if(StringUtils.isNotBlank(hpDto.getLabels())){
					String[] labels = hpDto.getLabels().split(",");
					List<String> labelNameList = new ArrayList<>();
					for (String labelId : labels) {
						labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
					}
					hpDto.setLabelNames(StringUtils.join(labelNameList,","));
				}
				hpDtoList.add(hpDto);
			}
			pageDto.setData(hpDtoList);
			pageDto.setMaxPage(pageInfo.getPages());
			pageDto.setTotalCount(pageInfo.getTotal());
		}else if("3".equals(jsonParams.getString("type"))) {
			PageInfo<ImportantEvent> pageInfo = importantEventService.list(jsonParams);
			if(CollectionUtils.isEmpty(pageInfo.getList())) {
				return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
			}
			List<ImportantEventDto> ieDtoList = new ArrayList<>();
			for (ImportantEvent ie : pageInfo.getList()) {
				ImportantEventDto ieDto = BeanUtils.copyProperties(ie,ImportantEventDto.class);
				if(StringUtils.isNotBlank(ieDto.getLabels())){
					String[] labels = ieDto.getLabels().split(",");
					List<String> labelNameList = new ArrayList<>();
					for (String labelId : labels) {
						labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
					}
					ieDto.setLabelNames(StringUtils.join(labelNameList,","));
				}
				ieDtoList.add(ieDto);
			}
			pageDto.setData(ieDtoList);
			pageDto.setMaxPage(pageInfo.getPages());
			pageDto.setTotalCount(pageInfo.getTotal());
		}else if("6".equals(jsonParams.getString("type"))) {
			PageInfo<HotWord> pageInfo = hotWordService.list(jsonParams);
			if(CollectionUtils.isEmpty(pageInfo.getList())) {
				return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
			}
			List<HotWordDto> hwDtoList = new ArrayList<>();
			for (HotWord hw : pageInfo.getList()) {
				HotWordDto hwDto = BeanUtils.copyProperties(hw,HotWordDto.class);
				if(StringUtils.isNotBlank(hwDto.getLabels())){
					Set<String> labels = new HashSet<>(Arrays.asList(hwDto.getLabels().split(",")));
					List<String> labelNameList = new ArrayList<>();
					for (String labelId : labels) {
						labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
					}
					hwDto.setLabelNames(StringUtils.join(labelNameList,","));
				}
				hwDtoList.add(hwDto);
			}
			pageDto.setData(hwDtoList);
			pageDto.setMaxPage(pageInfo.getPages());
			pageDto.setTotalCount(pageInfo.getTotal());
		}
		return pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
	}

	@ApiOperation(value = "热词置顶", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "热词置顶") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "top", headers = "Accept=application/json,application/xml")
	public BaseDto top( @ApiParam(value = "热词id") @RequestParam(required = true) Integer id,
								   @ApiParam(value = "是否置顶(1:是置顶 2:取消置顶)") @RequestParam(required = true) Integer whether) {
		BaseDto baseDto = new BaseDto();
		if (id == null) {
			baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}

		Boolean flag = hotIncidentService.modifyTop(id,whether);
		if(flag) {
			return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
		}
		return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
	}

	@ApiOperation(value = "统计", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "统计") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "count", headers = "Accept=application/json,application/xml")
	public BaseDto count(@RequestParam(required = false) Map<String,Object> params) throws ParseException {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}
		if (StringUtils.isNotBlank((String)params.get("startTime"))&&StringUtils.isNotBlank((String)params.get("endTime"))){
            int startTime = DateUtils.countDays((String) params.get("startTime"));
            int endTime = DateUtils.countDays((String) params.get("endTime"));
			int date = endTime - startTime;
            params.put("date",date);
        }
        JSONObject jsonParams = new JSONObject(params);
		Map<String,Object> map = hotIncidentService.findCount(jsonParams);
		if(MapUtils.isEmpty(map)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
		}
		return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(map);
	}

	@ApiOperation(value = "类型统计", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "统计") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "typeCount", headers = "Accept=application/json,application/xml")
	public BaseDto typeCount(@RequestParam(required = false) Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
		}
        if (StringUtils.isNotBlank((String)params.get("startTime"))&&StringUtils.isNotBlank((String)params.get("endTime"))){
            int startTime = DateUtils.countDays((String) params.get("startTime"));
            int endTime = DateUtils.countDays((String) params.get("endTime"));
            int date = endTime - startTime;
            params.put("date",date);
        }
		JSONObject jsonParams = new JSONObject(params);
		Map<String,Integer> map = hotIncidentService.findTypeCount(jsonParams);
		if(MapUtils.isEmpty(map)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
		}
		return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(map);
	}

	@ApiOperation(value = "省份分组找出最高热度值事件", produces = "application/json")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "省份分组找出最高热度值事件") })
	@RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "provinceGroupBy", headers = "Accept=application/json,application/xml")
	public BaseDto provinceGroupBy(@RequestParam(required = false) Map<String,Object> params) {
		BaseDto baseDto = new BaseDto();
		if (MapUtils.isEmpty(params)) {
			return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
		}
		JSONObject jsonParams = new JSONObject(params);
		List<HotIncident> hotIncidentList = hotIncidentService.findProvinceGroupBy(jsonParams);
		if(CollectionUtils.isEmpty(hotIncidentList)) {
			return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
		}
		return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(hotIncidentList);
	}

}