package com.miduchina.wrd.api.service.order;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.order.CartRecord;
import com.miduchina.wrd.po.order.H5Activity;
import com.miduchina.wrd.po.order.Order;
import com.miduchina.wrd.po.order.PayRecord;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:45
 */
public interface OrderService {
    /**
     * 保存订单
     *
     * @param order
     * @return
     */
    boolean saveOrder(Order order);

    /**
     * 确认订单
     *
     * @param request
     * @param user
     * @param confirmOrderVO
     * @return
     */
    //Map<String, Object> doConfirmOrder(HttpServletRequest request, UserBO user, ConfirmOrderDto confirmOrderVO);
    /**
     * 修改用户属性
     *
     * @param userDto
     * @return
     */
    boolean updateUserType(UserDto userDto);
    /**
     * 获取用户关键词最大顺序
     *
     * @param userDto
     * @return
     */
    int findKeywordSeq(UserDto userDto);
    /**
     * 保存购物车记录
     *
     * @param cartRecord
     * @return
     */
    boolean saveCartRecord(CartRecord cartRecord);
    /**
     * 修改购物车状态
     *
     * @param cartRecord
     * @return
     */
    boolean updateCartStatus(CartRecord cartRecord);

    /**
     * 获取专业版产品包ID
     *
     * @param userId
     * @return
     */
    int queryProPackageId(Integer userId);

    /**
     * 根据产品IDS获取总订单金额
     *
     * @param userId
     * @param packageIds
     * @return
     */
    double queryAllOrderFeeByPackageId(int userId, List<Integer> packageIds);


    List<Integer> findPayRecordByUserIdAndPackageId(JSONObject jsonObject);

    Integer findOrderCountByProductPackageIds(JSONObject jsonObject);



    PayRecord findPayRecordById(Integer id);

    PayRecord findPayRecordByInnerTradeNo(String innerTradeNo);


    H5Activity find5ActivityByPackageId(Integer packageId);


}
