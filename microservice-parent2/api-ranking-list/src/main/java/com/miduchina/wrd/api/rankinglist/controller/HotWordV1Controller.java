package com.miduchina.wrd.api.rankinglist.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.api.rankinglist.constant.Constants;
import com.miduchina.wrd.api.rankinglist.service.HotWordService;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotWordDto;
import com.miduchina.wrd.po.ranking.HotWord;
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
 * @ClassName: HotWordV1Controller
 * @Description: 热词
 * @author: 许双龙
 * @date: 2019年2月19日 下午2:04:08
 */
@Api(tags = {"热词"})
@RestController
@RequestMapping("api/v1/hotWord")
public class HotWordV1Controller {

    @Autowired
    private HotWordService hotWordService;

    @ApiOperation(value = "查询热词", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询热门现象") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "list", headers = "Accept=application/json")
    public PageDto<HotWordDto> list(@RequestParam(required = false) Map<String,Object> params) {
        PageDto<HotWordDto> pageDto = new PageDto<>();
        if (MapUtils.isEmpty(params)) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        JSONObject jsonParams = new JSONObject(params);
        PageInfo<HotWord> pageInfo = hotWordService.list(jsonParams);
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
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
        pageDto.setCode(CodeConstant.SUCCESS_CODE);
        pageDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        pageDto.setMaxPage(pageInfo.getPages());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setPageSize(pageInfo.getPageSize());
        return pageDto;
    }

    @ApiOperation(value = "查询单个热词", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "单个热词") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "get", headers = "Accept=application/json")
    public BaseDto<HotWordDto> getV1(@ApiParam(value = "热词id") @RequestParam Integer id) {
        BaseDto<HotWordDto> baseDto = new BaseDto<>();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        HotWord hotWord = hotWordService.get(id);
        if(hotWord == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        HotWordDto hwDto = BeanUtils.copyProperties(hotWord,HotWordDto.class);
        if(StringUtils.isNotBlank(hwDto.getLabels())){
            String[] labels = hwDto.getLabels().split(",");
            List<String> labelNameList = new ArrayList<>();
            for (String labelId : labels) {
                labelNameList.add(Constants.HOT_LABEL_MAP.get(Integer.valueOf(labelId)));
            }
            hwDto.setLabelNames(StringUtils.join(labelNameList,","));
        }

        baseDto.setData(hwDto);
        baseDto.setCode(CodeConstant.SUCCESS_CODE);
        baseDto.setMessage(CodeConstant.MSG_MAP.get(CodeConstant.SUCCESS_CODE));
        return baseDto;
    }

    @ApiOperation(value = "删除热词", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除热词") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "remove", headers = "Accept=application/json")
    public BaseDto removeV1(
            @ApiParam(value = "热词id") @RequestParam Integer id) {
        BaseDto baseDto = new BaseDto();
        if (id == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000);
        }

        Boolean flag = hotWordService.remove(id);
        if(flag) {
            return baseDto.setCode(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
    }
}
