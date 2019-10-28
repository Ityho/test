package com.miduchina.wrd.api.controller.v1.tuiguang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.tuiguang.TuiguangService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.tuiguang.Tuiguang;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther Administrator
 * @vreate 2019-05 9:49
 */
@RestController
@RequestMapping("api/v1/tuiguang")
public class TuiguangController {

    @Autowired
    private TuiguangService tuiguangService ;

    @ApiOperation(value = "推广创建", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveTuiguang")
    BaseDto saveTuiguang(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("tuiguang");
        if(StringUtils.isNotBlank(str)){
            Tuiguang tuiguang = JSON.parseObject(str, Tuiguang.class);
            boolean b = tuiguangService.doSaveRecord(tuiguang);
            if (b){
                baseDto.setCode(CodeConstant.SUCCESS_CODE);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }

}
