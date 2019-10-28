package com.miduchina.wrd.api.rankinglist.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.HotPhenomenoneService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotPhenomenoneDto;
import com.miduchina.wrd.po.ranking.HotPhenomenone;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName: HotPhenomenoneV1Controller
 * @Description: 热门现象
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Api(tags = {"热门现象"})
@RestController
@RequestMapping("api/v1/hotPhenomenone")
public class HotPhenomenoneV1Controller {

    @Autowired
    private HotPhenomenoneService hotPhenomenoneService;

    @ApiOperation(value = "查询热门现象", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门现象") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list", headers = "Accept=application/json")
    public PageDto<HotPhenomenoneDto> listV1(@RequestParam(required = false) Map<String,Object> params) {
        PageDto<HotPhenomenoneDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotPhenomenone> pageInfo = hotPhenomenoneService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
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
        pageDto.setCode(CodeConstant.SUCCESS_CODE);
        pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        pageDto.setMaxPage(pageInfo.getPages());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        return pageDto;
    }

    @ApiOperation(value = "查询单个热门现象", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "单个热门现象") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "get", headers = "Accept=application/json")
    public BaseDto<HotPhenomenoneDto> getV1(@ApiParam(value = "热门现象id") @RequestParam Integer id) {
        BaseDto<HotPhenomenoneDto> baseDto = new BaseDto<>();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        HotPhenomenone hotPhenomenone = hotPhenomenoneService.get(id);
        if(hotPhenomenone == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        HotPhenomenoneDto hpDto = BeanUtils.copyProperties(hotPhenomenone,HotPhenomenoneDto.class);
        if(StringUtils.isNotBlank(hpDto.getLabels())){
            String[] labels = hpDto.getLabels().split(",");
            List<String> labelNameList = new ArrayList<>();
            for (String labelId : labels) {
                labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
            }
            hpDto.setLabelNames(StringUtils.join(labelNameList,","));
        }

        baseDto.setData(hpDto);
        baseDto.setCode(CodeConstant.SUCCESS_CODE);
        baseDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        return baseDto;
    }

    @ApiOperation(value = "添加热门现象", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "添加热门现象") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "add", headers = "Accept=application/json")
    public BaseDto addV1(@RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = hotPhenomenoneService.save(jsonParams);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "更新热门现象", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "更新热门现象") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "modify", headers = "Accept=application/json")
    public BaseDto modifyV1(@RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = hotPhenomenoneService.modify(jsonParams);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }

    @ApiOperation(value = "删除热门现象", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除热词") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove", headers = "Accept=application/json")
    public BaseDto removeV1(
            @ApiParam(value = "热门现象id") @RequestParam Integer id) {
        BaseDto baseDto = new BaseDto();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        Boolean flag = hotPhenomenoneService.remove(id);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }
}
