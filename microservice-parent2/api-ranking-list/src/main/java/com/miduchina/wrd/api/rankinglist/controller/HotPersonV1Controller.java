package com.miduchina.wrd.api.rankinglist.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.HotPersonService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.ranking.HotPersonDto;
import com.miduchina.wrd.dto.ranking.RankingListWebDto;
import com.miduchina.wrd.po.ranking.HotIncident;
import com.miduchina.wrd.po.ranking.HotPerson;
import com.miduchina.wrd.po.ranking.RankingListWeb;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.DateUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
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
 * @ClassName: HotPersonV1Controller
 * @Description: 热门人物
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Slf4j
@Api(tags = {"热门人物"})
@RestController
@RequestMapping("api/v1/hotPerson")
public class HotPersonV1Controller {

    @Autowired
    private HotPersonService hotPersonService;

    @ApiOperation(value = "查询热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门人物") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list", headers = "Accept=application/json")
    public PageDto<HotPersonDto> list(@RequestParam(required = false) Map<String,Object> params) {
        log.info("HotPersonV1Controller.list params = {}",params);
        PageDto<HotPersonDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotPerson> pageInfo = hotPersonService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),HotPersonDto.class))
            .setCodeMessage(CodeConstant.SUCCESS_CODE)
            .setMaxPage(pageInfo.getPages())
            .setTotalCount(pageInfo.getTotal())
            .setPage(pageInfo.getPageNum())
            .setPageSize(pageInfo.getPageSize());
    }

    @ApiOperation(value = "联合查询热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门人物") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/union", headers = "Accept=application/json")
    public PageDto<HotPersonDto> listUnion(@RequestParam(required = false) Map<String,Object> params) {
        log.info("HotPersonV1Controller.listUnion params = {}",params);
        PageDto<HotPersonDto> pageDto = new PageDto<>();
//        if (MapUtils.isEmpty(params)) {
//            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
//        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotPerson> pageInfo = hotPersonService.listUnion(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),HotPersonDto.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal())
                .setPage(pageInfo.getPageNum())
                .setPageSize(pageInfo.getPageSize());
    }

    @ApiOperation(value = "联合查询热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门人物") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list/union/hotIncident", headers = "Accept=application/json")
    public PageDto<HotPersonDto> listUnionHotIncident(@RequestParam(required = false) Map<String,Object> params) {
        log.info("HotPersonV1Controller.listUnionHotIncident params = {}",params);
        PageDto<HotPersonDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotPerson> pageInfo = hotPersonService.listUnionHotIncident(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return pageDto.setList(BeanUtils.copyTo(pageInfo.getList(),HotPersonDto.class))
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal())
                .setPage(pageInfo.getPageNum())
                .setPageSize(pageInfo.getPageSize());
    }

    @ApiOperation(value = "查询热门人物(前台)", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门人物(前台)") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "web/list", headers = "Accept=application/json")
    public PageDto<HotPersonDto> webListV1(@RequestParam(required = false) Map<String,Object> params) {
        PageDto<HotPersonDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
        }
        if (StringUtils.isNotBlank((String)params.get("startTime"))&&StringUtils.isNotBlank((String)params.get("endTime"))){
            int startTime = DateUtils.countDays((String)params.get("startTime"));
            int endTime = DateUtils.countDays((String)params.get("endTime"));
            int date = endTime - startTime;
            params.put("t",date);
        }
        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotPerson> pageInfo = hotPersonService.webList(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        List<HotPersonDto> hpDtoList = new ArrayList<>();
        for (HotPerson hp : pageInfo.getList()) {
            HotPersonDto hpDto = BeanUtils.copyProperties(hp,HotPersonDto.class);
            List<HotIncidentDto> hiDtoList = new ArrayList<>();
            for (HotIncident hi:hp.getHotIncidentList()) {
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
            hpDto.setHotIncidentList(hiDtoList);
            hpDtoList.add(hpDto);
        }
        return pageDto.setList(hpDtoList)
                .setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setMaxPage(pageInfo.getPages())
                .setTotalCount(pageInfo.getTotal())
                .setPage(pageInfo.getPageNum())
                .setPageSize(pageInfo.getPageSize());
    }

    @ApiOperation(value = "查询单个热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "单个热门人物") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "get", headers = "Accept=application/json")
    public BaseDto<HotPersonDto> get(@ApiParam(value = "热门人物id") @RequestParam Integer id) {
        log.info("HotPersonV1Controller.get id = {}",id);
        BaseDto<HotPersonDto> baseDto = new BaseDto<>();
        if (id == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        HotPerson hotPerson = hotPersonService.get(id);
        if(hotPerson == null){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }

        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setData(BeanUtils.copyProperties(hotPerson,HotPersonDto.class));
    }

    @ApiOperation(value = "添加热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "添加热门人物") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "add", headers = "Accept=application/json")
    public BaseDto add(@RequestParam(required = false) Map<String,Object> params) {
        log.info("HotPersonV1Controller.add params = {}",params);
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = hotPersonService.save(jsonParams);
        if(flag) {
            return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "更新热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "更新热门人物") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "modify", headers = "Accept=application/json")
    public BaseDto modify(@RequestParam(required = false) Map<String,Object> params) {
        log.info("HotPersonV1Controller.modify params = {}",params);
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = hotPersonService.modify(jsonParams);
        if(flag) {
            return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "删除热门人物", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除热门人物") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove", headers = "Accept=application/json")
    public BaseDto remove(
            @ApiParam(value = "热门人物id") @RequestParam Integer id) {
        log.info("HotPersonV1Controller.remove id = {}",id);
        BaseDto baseDto = new BaseDto();
        if (id == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        Boolean flag = hotPersonService.remove(id);
        if(flag) {
            return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "热门人物标签", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "热门人物标签") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "list/label", headers = "Accept=application/json")
    public PageDto<RankingListWebDto> listLabel(@ApiParam(value = "是否显示：不传显示部分标签,传显示所有标签") @RequestParam(required = false) Integer showTag) {
        log.info("HotPersonV1Controller.listLabel showTag = {}",showTag);
        PageDto<RankingListWebDto> pageDto = new PageDto<>();
        if (showTag == null) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        List<RankingListWeb> rlwList = hotPersonService.findAllByShowTag(showTag);
        RankingListWeb rlw = new RankingListWeb();
        rlw.setId(-1);
        rlw.setTypeName("事件人物");
        rlwList.add(0,rlw);
        if(CollectionUtils.isEmpty(rlwList)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);

        }
        return pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setList(BeanUtils.copyTo(rlwList,RankingListWebDto.class));
    }

    @ApiOperation(value = "删除热点关联", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除热点关联") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "relation/remove", headers = "Accept=application/json")
    public BaseDto deleteRelationHotIncident(
            @ApiParam(value = "热点事件id") @RequestParam(required = false) Integer hotIncidentId,
            @ApiParam(value = "关联id") @RequestParam(required = false) Integer personId,
            @ApiParam(value = "类型(1:热门新闻人物 2:热门事件人物)") @RequestParam Integer type) {
        BaseDto baseDto = new BaseDto();
        if (hotIncidentId == null || personId == null || type == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        Boolean flag = hotPersonService.removeHotIncidentRelationId(hotIncidentId,personId,type);
        if(flag) {
            return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
    }
}
