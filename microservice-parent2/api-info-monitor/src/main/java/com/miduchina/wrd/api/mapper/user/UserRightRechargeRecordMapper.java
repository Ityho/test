package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRightRechargeRecordMapper {
    /**
     * 添加用户权益变动记录
     * @param params
     * @return
     */
    Boolean insertUserRightsRechargeRecordMapper(JSONObject params);


    List<UserRightsRechargeRecord> selectRightsRechargeRecordList(JSONObject jsonObject);
}
