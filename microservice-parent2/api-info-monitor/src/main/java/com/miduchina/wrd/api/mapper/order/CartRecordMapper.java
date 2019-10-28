package com.miduchina.wrd.api.mapper.order;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by shitao on 2019-05-09 17:08.
 *
 * @author shitao
 */
@Mapper
public interface CartRecordMapper {


    /**
     * 查询购物车总数
     * */
    Integer selectAllCount(JSONObject params);

}
