package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserChannelRelationMapper {
    /**
     * 插入用户渠道关系
     * @param params
     * @return Boolean
     */
    Boolean insertChannelRelation(JSONObject params);
}
