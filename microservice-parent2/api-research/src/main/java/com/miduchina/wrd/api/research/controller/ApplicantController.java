package com.miduchina.wrd.api.research.controller;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.api.research.service.ApplicantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther yho
 * @vreate 2019-08 14:39
 */
@Slf4j
@Api(tags = {"研究院相关接口"})
@RestController
@RequestMapping("api/v1")
public class ApplicantController {

    @Autowired
    protected ApplicantService applicantService;

    @ApiOperation(value = "保存申请人信息", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "添加参数") })
    @PostMapping(value = "saveApplicants",headers = "Accept=application/json")
    BaseDto saveApplicants(@RequestBody(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        JSONObject jsonParams = new JSONObject(params);
        Boolean flag = applicantService.doSaveApplicant(jsonParams);
        if (flag){
                return baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        }
        return baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
    }
}
