package com.miduchina.wrd.api.controller.v1.infomonitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.infomonitor.ReportService;
import com.miduchina.wrd.dto.BaseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Administrator
 * @vreate 2019-05 16:39
 */
@RestController
@RequestMapping("api/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "关闭周报日报月报", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "closeReport")
    BaseDto closeReport(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        Integer userId = jsonObject.getInteger("userId");
        if(userId!=null){
            boolean b = reportService.closeReport(userId);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("关闭参数无效");
        }
        return baseDto;
    }
}
