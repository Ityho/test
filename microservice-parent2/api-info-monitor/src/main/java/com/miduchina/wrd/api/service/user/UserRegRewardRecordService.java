package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.user.UserRegRewardRecord;

import java.util.List;

public interface UserRegRewardRecordService {
    /**
     * 注册赠送积分给用户
     * @param mobile
     * @return
     */
    List<UserRegRewardRecord> queryRegisterCreditByMobile(String mobile);

    /**
     * 修改赠送积分用户的状态，被领取
     * @param mobile
     * @return
     */
    Boolean updateRewardRecord(String mobile);
}
