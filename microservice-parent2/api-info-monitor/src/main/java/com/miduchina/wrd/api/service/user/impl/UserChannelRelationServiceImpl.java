package com.miduchina.wrd.api.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.user.UserChannelRelationMapper;
import com.miduchina.wrd.api.service.user.UserChannelRelationService;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserChannelRelationServiceImpl implements UserChannelRelationService {
    @Resource
    private UserChannelRelationMapper channelRelationMapper;

    @Override
    public Boolean addUserChannelRelation(UserExclusiveChannelRelation channelRelation) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",channelRelation.getUserId());
        map.put("uecId",channelRelation.getUecId());
        map.put("status",channelRelation.getStatus());
        JSONObject parma=new JSONObject(map);
        return channelRelationMapper.insertChannelRelation(parma);
    }
}
