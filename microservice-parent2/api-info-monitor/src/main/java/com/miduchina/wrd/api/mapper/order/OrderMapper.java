package com.miduchina.wrd.api.mapper.order;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.order.CartRecord;
import com.miduchina.wrd.po.order.H5Activity;
import com.miduchina.wrd.po.order.Order;
import com.miduchina.wrd.po.order.PayRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:53
 */
@Mapper
public interface OrderMapper {

    boolean saveOrder(Order order);

    boolean updateUserType(UserDto userDto);

    boolean saveCartRecord(CartRecord cartRecord);

    boolean updateCartStatus(CartRecord cartRecord);

    int selectProPackageId(Integer userId);

    double selectAllOrderFeeByPackageId(int userId, List<Integer> packageIds);

    List<Integer> findPayRecordByUserIdAndPackageId(JSONObject jsonObject);

    List<Integer> findOrderCountByProductPackageIds(JSONObject jsonObject);

    /**
     * 根据ID查询支付记录
     * @param payRecordId
     * @return
     */
    PayRecord findPayRecordById(Integer payRecordId);

    List<PayRecord> findPayRecordByInnerTradeNo(String innerTradeNo);


    List<H5Activity> find5ActivityByPackageId(Integer packageId);

}
