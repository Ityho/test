package com.miduchina.wrd.api.mapper.ranking;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 10:26
 */
@Mapper
public interface RankingMapper {
    Boolean insert(JSONObject params);
    Boolean update(JSONObject params);
    List<OperationAdminHotContent> list(JSONObject params);


    List<OperationAdminHot> hotList(JSONObject params);
}
