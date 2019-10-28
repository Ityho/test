package com.miduchina.wrd.api.rankinglist.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.ImportantEventService;

import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.ranking.ImportantEventDto;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.ImportantEvent;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ImportantEventV1Controller
 * @Description: 重大事件
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Slf4j
@Api(tags = {"重大事件"})
@RestController
@RequestMapping("api/v1/importantEvent")
public class ImportantEventV1Controller {

    @Autowired
    private ImportantEventService importantEventService;

    @ApiOperation(value = "查询重大事件", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询重大事件") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list", headers = "Accept=application/json")
    public PageDto<ImportantEventDto> listV1(@RequestParam(required = false) Map<String,Object> params) {
        log.info("listV1 params = [{}]",params);
        PageDto<ImportantEventDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<ImportantEvent> pageInfo = importantEventService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
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
        pageDto.setCode(CodeConstant.SUCCESS_CODE);
        pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        pageDto.setMaxPage(pageInfo.getPages());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        return pageDto;
    }

    @ApiOperation(value = "查询重大事件(前台)", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询重大事件(前台)") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "web/list", headers = "Accept=application/json")
    public PageDto<ImportantEventDto> webListV1(@RequestParam(required = false) Map<String,Object> params) {
        PageDto<ImportantEventDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<ImportantEvent> pageInfo = importantEventService.webList(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        List<ImportantEventDto> ieDtoList = new ArrayList<>();
        for (ImportantEvent ie : pageInfo.getList()) {
            ImportantEventDto ieDto = BeanUtils.copyProperties(ie,ImportantEventDto.class);
            List<HotIncidentDto> hiDtoList = new ArrayList<>();
            for (HotIncident hi:ie.getHotIncidentList()) {
                HotIncidentDto hiDto = BeanUtils.copyProperties(hi,HotIncidentDto.class);
                if(StringUtils.isNotBlank(hiDto.getLabels())){
                    String[] labels = hiDto.getLabels().split(",");
                    List<String> labelNameList = new ArrayList<>();
                    for (String labelId : labels) {
                        labelNameList.add((Constants.HOT_LABEL_MAP.get(Constants.HOT_PARENT_ID_MAP.get(Integer.valueOf(labelId)))));
                    }
                    hiDto.setLabelNames(StringUtils.join(labelNameList,","));
                }
                hiDtoList.add(hiDto);
            }
            if(StringUtils.isNotBlank(ieDto.getLabels())){
                String[] labels = ieDto.getLabels().split(",");
                List<String> labelNameList = new ArrayList<>();
                for (String labelId : labels) {
                    labelNameList.add((Constants.HOT_LABEL_MAP.get(Constants.HOT_PARENT_ID_MAP.get(Integer.valueOf(labelId)))));
                }
                ieDto.setLabelNames(StringUtils.join(labelNameList,","));
            }
            ieDto.setHotIncidentList(hiDtoList);
            ieDtoList.add(ieDto);
        }
        pageDto.setData(ieDtoList);
        pageDto.setCode(CodeConstant.SUCCESS_CODE);
        pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        pageDto.setMaxPage(pageInfo.getPages());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        return pageDto;
    }

    @ApiOperation(value = "查询单个重大事件", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "单个热门现象") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "get", headers = "Accept=application/json")
    public BaseDto<ImportantEventDto> getV1(@ApiParam(value = "重大事件id") @RequestParam Integer id) {
        BaseDto<ImportantEventDto> baseDto = new BaseDto<>();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        ImportantEvent importantEvent = importantEventService.get(id);
        if(importantEvent == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        ImportantEventDto ieDto = BeanUtils.copyProperties(importantEvent,ImportantEventDto.class);
        if(StringUtils.isNotBlank(ieDto.getLabels())){
            String[] labels = ieDto.getLabels().split(",");
            List<String> labelNameList = new ArrayList<>();
            for (String labelId : labels) {
                labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
            }
            ieDto.setLabelNames(StringUtils.join(labelNameList,","));
        }

        baseDto.setData(ieDto);
        baseDto.setCode(CodeConstant.SUCCESS_CODE);
        baseDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        return baseDto;
    }

    @ApiOperation(value = "查询单个重大事件(前台)", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "单个热门现象(前台)") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "web/get", headers = "Accept=application/json")
    public BaseDto<ImportantEventDto> webGetV1(@RequestParam(required = false) Map<String,Object> params) {
        BaseDto<ImportantEventDto> baseDto = new BaseDto<>();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = new JSONObject(params);

        ImportantEvent importantEvent = importantEventService.webGet(jsonParams);
        if(importantEvent == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        ImportantEventDto ieDto = BeanUtils.copyProperties(importantEvent,ImportantEventDto.class);
        if(StringUtils.isNotBlank(ieDto.getLabels())){
            String[] labels = ieDto.getLabels().split(",");
            List<String> labelNameList = new ArrayList<>();
            for (String labelId : labels) {
                labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
            }
            ieDto.setLabelNames(StringUtils.join(labelNameList,","));
        }

        baseDto.setData(ieDto);
        baseDto.setCode(CodeConstant.SUCCESS_CODE);
        baseDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        return baseDto;
    }

    @ApiOperation(value = "添加重大事件", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "添加重大事件") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "add", headers = "Accept=application/json")
    public BaseDto addV1(@RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = importantEventService.save(jsonParams);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "更新重大事件", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "更新热门现象") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "modify", headers = "Accept=application/json")
    public BaseDto modifyV1(@RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = importantEventService.modify(jsonParams);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "删除重大事件", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除热词") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove", headers = "Accept=application/json")
    public BaseDto removeV1(
            @ApiParam(value = "热门现象id") @RequestParam Integer id) {
        BaseDto baseDto = new BaseDto();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        Boolean flag = importantEventService.remove(id);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }
}
