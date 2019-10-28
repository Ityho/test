package com.miduchina.wrd.api.rankinglist.controller;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.rankinglist.service.HotLabelService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.ranking.HotLabelDto;
import com.miduchina.wrd.po.ranking.HotLabel;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: HotLabelV1Controller
 * @Description: 热门事件标签
 * @author: 许双龙
 * @date: 2018年9月10日 下午8:04:08
 */
@Api(tags = {"热门事件标签"})
@RestController
@RequestMapping("api/v1/hotLabel")
public class HotLabelV1Controller {

    @Autowired
    private HotLabelService hotLabelService;

    @ApiOperation(value = "热度标签", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "热度标签") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "list", headers = "Accept=application/json")
    public PageDto<HotLabelDto> list(@ApiParam(value = "是否显示：不传显示部分标签,传显示所有标签") @RequestParam(required = false) Integer showTag) {
        PageDto<HotLabelDto> pageDto = new PageDto<>();

        List<HotLabel> hlList = hotLabelService.findAllHotLabel(showTag);
        if(CollectionUtils.isEmpty(hlList)) {
            return pageDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);

        }
        return pageDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setList(BeanUtils.copyTo(hlList,HotLabelDto.class));
    }

    @ApiOperation(value = "热度标签分组", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "热度标签分组") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "get/groupByLevel", headers = "Accept=application/json")
    public BaseDto<HotLabelDto> hotLabelGroupByLevel(@ApiParam(value = "是否显示：不传显示部分标签,传显示所有标签") @RequestParam(required = false) Integer showTag) {
        BaseDto<HotLabelDto> baseDto = new BaseDto<>();

        HotLabel hotLabel = hotLabelService.findAllHotLabelGroupByLevel(showTag);
        if(hotLabel == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_2000);
        }
        return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE)
                .setData(BeanUtils.copyProperties(hotLabel,HotLabelDto.class));
    }
}
