package com.miduchina.wrd.api.controller.v3.infomonitor;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.infomonitor.BriefService;
import com.miduchina.wrd.dto.BaseDto;
import io.swagger.annotations.*;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version v1.0.0
 * @ClassName: BriefController
 * @Description: 简报
 * @author: sty
 * @date: 2019/4/23 12:17 PM
 */
@Api(tags = {"简报"})
@RestController
@RequestMapping("api/v3/brief")
public class BriefController {

    @Autowired
    private BriefService briefService;

    @ApiOperation(value = "查询用户素材总量", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "查询用户素材总量") })
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getSourceMaterialCount", headers = "Accept=application/json")
    public BaseDto getSourceMaterialCount(@ApiParam(value = "筛选条件(userId:用户id directoryTag:素材库 type:1-当前素材库数量，2-用户24小时内添加的素材总量 )") @RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000)
                    .setMessage(CodeConstant.MSG_MAP.get(CodeConstant.ERROR_CODE_1000));
        }
        if (params.get("userId") == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_1000)
                    .setMessage("userId必填");
        }

//        if (params.get("type") == null) {
//            return baseDto.setCode(CodeConstant.ERROR_CODE_1000)
//                    .setMessage("type必填");
//        }

        JSONObject jsonParams = new JSONObject(params);
        Integer sourceMaterialCount = briefService.getSourceMaterialCount(jsonParams);
        if(sourceMaterialCount == null) {
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        baseDto.setData(sourceMaterialCount);
        baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        return baseDto;
    }
}
