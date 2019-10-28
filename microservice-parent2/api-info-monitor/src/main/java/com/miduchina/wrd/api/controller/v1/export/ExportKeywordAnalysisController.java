package com.miduchina.wrd.api.controller.v1.export;

/**
 * @auther Administrator
 * @vreate 2019-05 16:39
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.export.ExportKeywordAnalysisService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.keyword.ExportKeywordAnalysisCondition;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/exportKeywordAnalysis")
public class ExportKeywordAnalysisController {
    @Autowired
    private ExportKeywordAnalysisService exportKeywordAnalysisService;

    @ApiOperation(value = "根据条件ID，创建数据导出记录", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findConditionId")
    BaseDto findConditionId(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("conditionId");
        if(StringUtils.isNotBlank(str)){
            ExportKeywordAnalysisCondition b = exportKeywordAnalysisService.findConditionId(str);
            if (b!=null){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少conditionId");
        }
        return baseDto;
    }
    @ApiOperation(value = "插入任务", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "insertExportKeywordAnalysisCondition")
    public BaseDto insertExportKeywordAnalysisCondition(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("ExportKeywordAnalysisCondition");
        if(StringUtils.isNotBlank(str)){
            ExportKeywordAnalysisCondition oer = JSON.parseObject(str, ExportKeywordAnalysisCondition.class);
            Boolean b = exportKeywordAnalysisService.insertExportKeywordAnalysisCondition(oer);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少用户id");
        }
        return baseDto;
    }
}
