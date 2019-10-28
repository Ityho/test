package com.miduchina.wrd.api.controller.v1.heatanalysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.heatanalysis.HeatReportService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.home.HeatShareDto;
import com.miduchina.wrd.po.home.HeatShare;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version v1.0.0
 * @ClassName: HeatAnalysisController
 * @Description: 热度分析
 * @author: sty
 * @date: 2019/7/30 12:10 PM
 */
@Slf4j
@Api(tags = {"热度分析"})
@RestController
@RequestMapping("api/v1/heatAnalysis")
public class HeatAnalysisController {

    @Autowired
    private HeatReportService heatReportService;

    @RequestMapping(value = "/queryCountHeatReportByUserId")
    public BaseDto queryCountHeatReportByUserId(String userId){
        BaseDto baseDto=new BaseDto();
        if(!TextUtils.isEmpty(userId)){
            Integer count=heatReportService.getCountHeatReportByUserId(Integer.parseInt(userId));
            if(count!=null){
                baseDto.setData(count).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }
        return baseDto;
    }

    @ApiOperation(value = "根据条件，查出记录", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findShareCode")
    BaseDto findShareCode(@RequestParam Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonParams = new JSONObject(params);
        String str = jsonParams.getString("shareCode");
        if(StringUtils.isNotBlank(str)){
            HeatShare heatShare = heatReportService.findShareCode(str);
            if (heatShare != null){
                HeatShareDto dto = BeanUtils.copyProperties(heatShare, HeatShareDto.class);
                if (dto!=null){
                    baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(dto);
                }else{
                    baseDto.setCode(CodeConstant.FAILURE_CODE);
                }
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少shareCode");
        }
        return baseDto;
    }

}
