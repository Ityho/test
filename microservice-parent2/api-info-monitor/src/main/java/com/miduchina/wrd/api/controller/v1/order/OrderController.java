package com.miduchina.wrd.api.controller.v1.order;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.pay.H5ActivityDto;
import com.miduchina.wrd.dto.pay.PayRecordDto;
import com.miduchina.wrd.po.order.H5Activity;
import com.miduchina.wrd.po.order.PayRecord;
import com.miduchina.wrd.util.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther Administrator
 * @vreate 2019-05 9:49
 */
@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService ;

    @ApiOperation(value = "订单操作", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "/findOrderCountByProductPackageIds", headers = "Accept=application/json")
    public BaseDto findOrderCountByProductPackageIds(Integer userId,String package_type_report_ids) {
        BaseDto baseDto=new BaseDto();
        Map<String,Object> objectMap= new HashMap<>();
        objectMap.put("userId",userId);
        objectMap.put("package_type_report_ids",package_type_report_ids);
        JSONObject jsonObject=new JSONObject(objectMap);
        if(userId==null && TextUtils.isEmpty(package_type_report_ids)){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Integer orderCount=orderService.findOrderCountByProductPackageIds(jsonObject);
            if(orderCount!=null){
                baseDto.setData(orderCount).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }



    @RequestMapping(value = "/findPayRecordById")
    public BaseDto findPayRecordById(Integer id){
        BaseDto baseDto=new BaseDto();

        if (id != null){
            PayRecord record = orderService.findPayRecordById(id);
            if (record != null){
                PayRecordDto dto = BeanUtils.copyProperties(record, PayRecordDto.class);
                baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(dto);
            }else {
                baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
            }
        }else {
            baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_ERROR);
        }
        return baseDto;

    }


    @RequestMapping(value = "/findPayRecordByInnerTradeNo")
    public BaseDto findPayRecordByInnerTradeNo(String innerTradeNo){
        BaseDto baseDto=new BaseDto();

        if (StringUtils.isNotBlank(innerTradeNo)){
            PayRecord record = orderService.findPayRecordByInnerTradeNo(innerTradeNo);
            if (record != null){
                PayRecordDto dto = BeanUtils.copyProperties(record, PayRecordDto.class);
                baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(dto);
            }else {
                baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
            }
        }else {
            baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_ERROR);
        }
        return baseDto;

    }


    @RequestMapping(value = "/find5ActivityByPackageId")
    public BaseDto find5ActivityByPackageId(Integer packageId){
        BaseDto baseDto=new BaseDto();

        if (packageId != null){
            H5Activity h5Activity = orderService.find5ActivityByPackageId(packageId);
            if (h5Activity != null){
                H5ActivityDto dto = BeanUtils.copyProperties(h5Activity, H5ActivityDto.class);
                baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(dto);
            }else {
                baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
            }
        }else {
            baseDto.setCodeMessage(CodeConstant.F_LACK_NECESSARY_ERROR);
        }
        return baseDto;

    }


}
