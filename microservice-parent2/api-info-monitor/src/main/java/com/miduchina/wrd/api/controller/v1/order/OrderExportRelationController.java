package com.miduchina.wrd.api.controller.v1.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.order.OrderExportRelationService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.order.OrderExportRelation;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 15:38
 */
@RestController
@RequestMapping("api/v1/orderExportRelation")
public class OrderExportRelationController {

    @Autowired
    private OrderExportRelationService orderExportRelationService;

    @ApiOperation(value = "查询未创建任务的已支付订单", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "findOrderExportStatus")
    public BaseDto findOrderExportStatus(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("userId");
        if(StringUtils.isNotBlank(str)){
            List<OrderExportRelation> b = orderExportRelationService.findOrderExportStatus(str);
            if (CollectionUtils.isEmpty(b)){
                baseDto.setCode(CodeConstant.SUCCESS_CODE).setData(b);
            }else{
                baseDto.setCode(CodeConstant.FAILURE_CODE);
            }

        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少用户id");
        }
        return baseDto;
    }

    @ApiOperation(value = "保存未支付订单的记录以及条件", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveOrUpdateOrderExport")
    public BaseDto saveOrUpdateOrderExport(@RequestBody String params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        JSONObject jsonObject = JSON.parseObject(params);
        String str = jsonObject.getString("userId");
        if(StringUtils.isNotBlank(str)){
            OrderExportRelation oer = JSON.parseObject(str, OrderExportRelation.class);
            Boolean b = orderExportRelationService.saveOrUpdateOrderExport(oer);
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
