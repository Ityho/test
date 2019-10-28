package com.miduchina.wrd.api.service.user;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.dto.user.UserRightsRechargeRecord;
import com.miduchina.wrd.po.user.UserRightsRecord;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserRightsRecordService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 1:31 PM
 */
public interface UserRightsRecordService {
    /**
     * 初始化用户权益
     *
     * @return
     */
    UserBO initUserRightsRecord(UserBO user);

    /**
     * 刷新缓存对象
     *
     * @param userId
     * @param key
     */
    UserBO refreshSessionUser(int userId, String key);

    /**
     * 根据条件查询用户权益列表
     *
     * @param userRightsRecord
     * @return
     */
    List<UserRightsRecord> queryUserRightsRecords(UserRightsRecord userRightsRecord);

    /**
     * 添加用户权益记录
     * @param userRightsRechargeRecord
     * @return
     */
    Boolean addRightsRechargeRecord(UserRightsRechargeRecord userRightsRechargeRecord);

    List<UserRightsRechargeRecord> queryUserRightsRechargeRecord(JSONObject jsonObject);
}
