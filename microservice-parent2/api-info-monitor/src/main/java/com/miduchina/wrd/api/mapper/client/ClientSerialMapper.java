package com.miduchina.wrd.api.mapper.client;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.user.ClientSerial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-10 15:13.
 *
 * @author shitao
 */
@Mapper
public interface ClientSerialMapper {


    /**
     * 查询序列列表
     * @see #getListSerial(JSONObject)
     * @param params 热门现象
     * @return List<SerialService>
     * */
    List<ClientSerial> getListSerial(JSONObject params);

    /**
     * 插入序列号
     * @param params
     * @return Boolean
     */
    Boolean insertSerial(JSONObject params);

    /**
     * 更新序列号
     * @param params
     * @return Boolean
     */
    Boolean updateSerial(JSONObject params);

}
