package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.po.user.UserWdRightsRechargeRecord;
import com.miduchina.wrd.po.user.UserWdRightsRecord;

import javax.servlet.http.HttpServletRequest;

/**
 * @version v1.0.0
 * @ClassName: UserWdRightsRecordService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 2:17 PM
 */
public interface UserWdRightsRecordService {

    /**
     * 保存用户微豆权益变动记录
     *
     * @param userWdRightsRechargeRecord
     * @return
     */
    boolean doSaveUserWdRightsRechargeRecord(UserWdRightsRechargeRecord userWdRightsRechargeRecord);

    /**
     * 增加用户微豆权益
     *
     * @param request
     * @param user
     * @param userWdRightsRecord
     * @param relationId
     * @return
     */
    UserBO doAddUserWdRightsRecord(HttpServletRequest request, UserBO user, UserWdRightsRecord userWdRightsRecord, Integer relationId);

    /**
     * 获取相关微豆添加次数
     *
     * @param userId
     * @param userWdRightsItem
     * @param relationId
     * @param isToday
     * @return
     */
    Integer queryAddWdTimes(Integer userId, Integer userWdRightsItem, Integer relationId, boolean isToday);
}
