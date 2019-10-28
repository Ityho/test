package com.miduchina.wrd.eventanalysis.service;

import com.miduchina.wrd.dto.eventanalysis.ProductPackage;
import com.miduchina.wrd.dto.pay.ConfirmOrderDto;
import com.miduchina.wrd.dto.pay.ConfirmOrderViewDto;
import com.miduchina.wrd.dto.pay.OrderFeeViewDto;
import com.miduchina.wrd.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface OrderClientService {

    List<ProductPackage> findProductPackageByType(HttpServletRequest request,Integer type);

    OrderFeeViewDto findOrderFeeByUserId(HttpServletRequest request, ConfirmOrderDto orderDto, UserDto admin);

    /**
     * 确认订单
     * @param orderDto
     * @param admin
     * @return
     */
    Map<String,Object> doComfirmOrder(HttpServletRequest request,ConfirmOrderDto orderDto, UserDto admin);

    ConfirmOrderViewDto doPayEnd(HttpServletRequest request,String innerTradeNo, UserDto admin, String trade_no);

    Integer findPayRecordByUserIdAndPackageId(Integer userId, int productPackageId);

    Integer findOrderCountByProductPackageIds(UserDto admin, String package_type_report_ids);


    ConfirmOrderViewDto findOrderByInnerTradeNo(HttpServletRequest request,Integer userId,String innerTradeNo);


    void doPayRecordFail(String out_trade_no,UserDto userDto ,String trade_status);

}
