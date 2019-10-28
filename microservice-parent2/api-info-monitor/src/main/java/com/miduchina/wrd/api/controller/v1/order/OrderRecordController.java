package com.miduchina.wrd.api.controller.v1.order;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.api.service.user.UserService;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.po.order.CartRecord;
import com.miduchina.wrd.po.order.Order;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @auther Administrator
 * @vreate 2019-05 9:49
 */
@RestController
@RequestMapping("api/v1/orderRecord")
public class OrderRecordController {

    @Autowired
    private OrderService orderService ;
    @Autowired
    private UserService userService ;

    @ApiOperation(value = "订单操作", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "operationOrder", headers = "Accept=application/json")
    public BaseDto operationOrder(@ApiParam(value = "筛选条件(order:订单 type:1-save，2-update ,3-select)") @RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        int type=0;
        type = (int) params.get("type");
        Order order = new Order();
        String str = params.get("order").toString();
        order = JSONObject.parseObject(str, Order.class);
        if(type!=0){
            if(type==1){

            }
        }else{
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("缺少操作类型type参数(type:1-save，2-update ,3-select)");
        }
        return baseDto;
    }
    @ApiOperation(value = "修改用户属性", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "doModifyUserType", headers = "Accept=application/json")
    public BaseDto doModifyUserType(@ApiParam(value = "筛选条件(UserDto:用户对象 )") @RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
//        UserDto userDto = new UserDto();
        String str = params.get("UserDto").toString();
        JSONObject jsonObject = JSONObject.parseObject(str);
//        UserDto userDto = JSONObject.parseObject(str, UserDto.class);
        if(StringUtils.isNotBlank(str)){
            boolean b = userService.updateUser(jsonObject);
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
    @ApiOperation(value = "购物车创建", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "saveCartRecord", headers = "Accept=application/json")
    public BaseDto saveCartRecord(@ApiParam(value = "筛选条件(cartRecord:购物车 )") @RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        String str = params.get("cartRecord").toString();
        CartRecord cartRecord = JSONObject.parseObject(str, CartRecord.class);
        if(StringUtils.isNotBlank(str)){
            boolean b = orderService.saveCartRecord(cartRecord);
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
    @ApiOperation(value = "购物车创建", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET,RequestMethod.POST }, value = "updateCartStatus", headers = "Accept=application/json")
    public BaseDto updateCartStatus(@ApiParam(value = "筛选条件(cartRecord:购物车 )") @RequestParam(required = false) Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
//        CartRecord cartRecord = new CartRecord();
        String str = params.get("cartRecord").toString();
        CartRecord cartRecord = JSONObject.parseObject(str, CartRecord.class);
        if(StringUtils.isNotBlank(str)){
            boolean b = orderService.updateCartStatus(cartRecord);
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
