package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserChannelMapper {
    List<UserExclusiveChannel> selectOneChannel(JSONObject params);

    boolean saveUserExclusiveChannelRelation(UserExclusiveChannelRelation relation);



}
