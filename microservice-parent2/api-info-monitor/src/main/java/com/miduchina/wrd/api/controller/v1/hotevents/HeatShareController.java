package com.miduchina.wrd.api.controller.v1.hotevents;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.hotevents.HeatShareService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.home.HeatShareDto;
import com.miduchina.wrd.po.hotspot.HeatShare;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Administrator
 * @vreate 2019-06 11:40
 */
@RestController
@RequestMapping("api/v1/heatshare")
public class HeatShareController {
    @Autowired
    private HeatShareService heatShareService;


    @ApiOperation(value = "根据shareCode查找heatshare", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findHs")
    public BaseDto findHs(@RequestBody String  params) {
        BaseDto baseDto = new BaseDto();
        JSONObject jsonObject = JSON.parseObject(params);
        String shareCode = jsonObject.getString("shareCode");
        if(StringUtils.isNotBlank(shareCode)){
            HeatShare b = heatShareService.findHs(shareCode);
            if (b!=null){
                HeatShareDto dto = BeanUtils.copyProperties(b, HeatShareDto.class);
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(dto);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请填写必要的参数");
        }
        return baseDto;
    }
}
