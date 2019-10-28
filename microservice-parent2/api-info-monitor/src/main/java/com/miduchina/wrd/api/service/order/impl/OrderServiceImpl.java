package com.miduchina.wrd.api.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.order.OrderMapper;
import com.miduchina.wrd.api.service.order.OrderService;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.po.order.CartRecord;
import com.miduchina.wrd.po.order.H5Activity;
import com.miduchina.wrd.po.order.Order;
import com.miduchina.wrd.po.order.PayRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:47
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public boolean saveOrder(Order order) {
        return orderMapper.saveOrder(order) ;
    }

    @Override
    public boolean updateUserType(UserDto userDto) {
        return orderMapper.updateUserType(userDto);
    }

    @Override
    public int findKeywordSeq(UserDto userDto) {
        return 0;
    }

    @Override
    public boolean saveCartRecord(CartRecord cartRecord) {
        return orderMapper.saveCartRecord(cartRecord);
    }

    @Override
    public boolean updateCartStatus(CartRecord cartRecord) {
        return orderMapper.updateCartStatus(cartRecord);
    }

    @Override
    public int queryProPackageId(Integer userId) {
        return orderMapper.selectProPackageId(userId);
    }

    @Override
    public double queryAllOrderFeeByPackageId(int userId, List<Integer> packageIds) {
        return orderMapper.selectAllOrderFeeByPackageId(userId, packageIds);
    }

    @Override
    public List<Integer> findPayRecordByUserIdAndPackageId(JSONObject jsonObject) {
        return orderMapper.findPayRecordByUserIdAndPackageId(jsonObject);
    }

    @Override
    public Integer findOrderCountByProductPackageIds(JSONObject jsonObject) {
        List<Integer>  orderCountList=orderMapper.findOrderCountByProductPackageIds(jsonObject);
        if(orderCountList!=null && orderCountList.size()>0){
            return orderCountList.get(0);
        }
        return null;
    }


    @Override
    public PayRecord findPayRecordById(Integer id){
        return orderMapper.findPayRecordById(id);
    }


    @Override
    public PayRecord findPayRecordByInnerTradeNo(String innerTradeNo){

        List<PayRecord> list = orderMapper.findPayRecordByInnerTradeNo(innerTradeNo);
        PayRecord record = new PayRecord();
        if (list != null && list.size() >0){
            record = list.get(0);
        }
        return record;
    }


    @Override
    public H5Activity find5ActivityByPackageId(Integer packageId) {

        List<H5Activity> list = orderMapper.find5ActivityByPackageId(packageId);
        H5Activity h5Activity = new H5Activity();
        if (list != null && list.size() >0){
            h5Activity = list.get(0);
        }
        return h5Activity;
    }



}
