package com.miduchina.wrd.api.controller.v1.infomonitor;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.infomonitor.WarningService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.ranking.Notice;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:13
 */
@RestController
@RequestMapping("api/v1/warning")
public class WarningController {
    @Autowired
    private WarningService warningService;
    @ApiOperation(value = "预警查询", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "getNotice")
    BaseDto getNotice(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
//        JSONObject jsonObject = JSON.parseObject(params);
//        String str = jsonObject.getString("tuiguang");
//        if(StringUtils.isNotBlank(str)){
            List<Notice> b = warningService.getNotice();
            if (CollectionUtils.isNotEmpty(b)){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }
        return baseDto;
    }
}
