package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.user.UserChannelMapper;
import com.miduchina.wrd.api.service.user.UserChannelService;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserChannelServiceImpl implements UserChannelService {
    @Resource
    private UserChannelMapper userChannelMapper;
    @Override
    public UserExclusiveChannel queryOneUserChannel(String code) {
        Map<String,Object> map=new HashMap<>();
        map.put("uec_code",code);
        JSONObject parma=new JSONObject(map);
        List<UserExclusiveChannel> userChannelList=userChannelMapper.selectOneChannel(parma);
        if(userChannelList!=null && userChannelList.size()>0){
            return userChannelList.get(0);
        }
        return null;
    }

    @Override
    public boolean saveUserExclusiveChannelRelation(UserExclusiveChannelRelation relation){

        return  userChannelMapper.saveUserExclusiveChannelRelation(relation);

    }



}
