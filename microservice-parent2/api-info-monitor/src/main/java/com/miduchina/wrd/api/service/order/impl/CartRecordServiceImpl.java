package com.miduchina.wrd.api.service.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.order.CartRecordMapper;
import com.miduchina.wrd.api.service.order.CartRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CartRecordServiceImpl implements CartRecordService {

    @Resource
    private CartRecordMapper cartRecordMapper;

    @Override
    public Integer queryCount(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        JSONObject parma=new JSONObject(map);
        return cartRecordMapper.selectAllCount(parma);
    }
}
