package com.miduchina.wrd.api.controller.v1.hotevents;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.hotevents.HotCallService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.hotspot.HotCall;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther Administrator
 * @vreate 2019-06 13:48
 */
@RestController
@RequestMapping("api/v1/hotcall")
public class HotCallController {

    @Autowired
    private HotCallService hotCallService;

    @ApiOperation(value = "hotcall创建", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveHotCall")
    public BaseDto saveHotCall( @RequestBody String  params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("hotCall");
        if(StringUtils.isNotBlank(str)){
            HotCall hotCall = JSONObject.parseObject(str, HotCall.class);
            boolean b = hotCallService.saveHotCall(hotCall);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作对象hotCall");
        }
        return baseDto;
    }
}
