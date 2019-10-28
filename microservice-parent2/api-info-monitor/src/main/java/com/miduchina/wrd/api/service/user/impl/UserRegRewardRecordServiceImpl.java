package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.user.UserRegRewardRecordMapper;
import com.miduchina.wrd.api.service.user.UserRegRewardRecordService;
import com.miduchina.wrd.po.user.UserRegRewardRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRegRewardRecordServiceImpl implements UserRegRewardRecordService {

    @Resource
    UserRegRewardRecordMapper recordMapper;
    @Override
    public List<UserRegRewardRecord> queryRegisterCreditByMobile(String mobile) {
        Map<String,Object> map=new HashMap<>();
        map.put("mobile",mobile);
        JSONObject params=new JSONObject(map);
        return recordMapper.selectRegisterCreditByMobile(params);
    }

    @Override
    public Boolean updateRewardRecord(String mobile) {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("username",mobile);
        JSONObject jsonObject=new JSONObject(objectMap);
        return recordMapper.updateUserRegRewardRecord(jsonObject);
    }
}
