package com.miduchina.wrd.api.mapper.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.user.UserRegRewardRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by shitao on 2019-05-09 17:08.
 *
 * @author shitao
 */
@Mapper
public interface UserRegRewardRecordMapper {
    /**
     * 获取用户注册赠送的微积分
     * @param params
     * @return
     */
    List<UserRegRewardRecord> selectRegisterCreditByMobile(JSONObject params);

    Boolean updateUserRegRewardRecord(JSONObject params);

}
