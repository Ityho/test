package com.miduchina.wrd.api.service.hotEventRanking;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;

/**
 * @auther Administrator
 * @vreate 2019-05 16:39
 */
public interface HotEventRankingService {

    PageInfo<OperationAdminHotContent> getList(JSONObject params);
    Boolean insert(JSONObject params);
    Boolean update(JSONObject params);


    PageInfo<OperationAdminHot> getHotList(JSONObject params);

}
