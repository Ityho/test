package com.miduchina.wrd.api.controller.v3.order;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.service.order.CartRecordService;
import com.miduchina.wrd.dto.BaseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "api/v3/cartRecord")
public class CartRecordController extends BaseController {
    @Autowired
    private CartRecordService cartRecordService;

    @RequestMapping(value = "/queryCount")
    public BaseDto queryCount(String userId){
        BaseDto baseDto=new BaseDto();
        if(!TextUtils.isEmpty(userId)){
            Integer count=cartRecordService.queryCount(Integer.parseInt(userId));
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
}
