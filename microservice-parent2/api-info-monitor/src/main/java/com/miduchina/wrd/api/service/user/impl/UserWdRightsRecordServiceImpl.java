package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.UserWdRightsRecordMapper;
import com.miduchina.wrd.api.service.user.UserWdRightsRecordService;
import com.miduchina.wrd.bo.user.UserBO;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.po.user.UserWdRightsRechargeRecord;
import com.miduchina.wrd.po.user.UserWdRightsRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserWdRightsRecordServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/19 2:17 PM
 */
@Service
public class UserWdRightsRecordServiceImpl implements UserWdRightsRecordService {

    @Resource
    private UserWdRightsRecordMapper userWdRightsRecordMapper;

    @Override
    public boolean doSaveUserWdRightsRechargeRecord(UserWdRightsRechargeRecord userWdRightsRechargeRecord) {
        return userWdRightsRecordMapper.insetUserWdRightsRechargeRecord(userWdRightsRechargeRecord);
    }

    @Override
    public UserBO doAddUserWdRightsRecord(HttpServletRequest request, UserBO user, UserWdRightsRecord userWdRightsRecord, Integer relationId) {
        // 平台
        int platform = Integer.parseInt(request.getParameter("platform"));
        user.setValidWdSum(user.getValidWdSum() + userWdRightsRecord.getUserWdRightsRecordInitCount());
        doSaveUserWdRightsRechargeRecord(new UserWdRightsRechargeRecord(user.getUserId(), relationId, BusinessConstant.USER_RIGHTS_RECHARGE_TYPE_ADD, userWdRightsRecord.getUserWdRightsRecordInitCount(), user.getValidWdSum(), userWdRightsRecord.getUserWdRightsRecordOrigin(), userWdRightsRecord.getUserWdRightsRecordDesc(), platform, BusinessConstant.STATUS_VALID, new Date()));

        userWdRightsRecord.setUserWdRightsRecordCurrCount(userWdRightsRecord.getUserWdRightsRecordInitCount());
        userWdRightsRecord.setStatus(BusinessConstant.STATUS_VALID);
        userWdRightsRecord.setCreateTime(new Date());
        userWdRightsRecord.setUserWdRightsRecordValidEndTime(BusinessConstant.generateDefaultUserWdRightsRecordValidEndTime());
        userWdRightsRecordMapper.insertUserWdRightsRecord(userWdRightsRecord);

        return user;
    }

    @Override
    public Integer queryAddWdTimes(Integer userId, Integer userWdRightsItem, Integer relationId, boolean isToday){
        return userWdRightsRecordMapper.selectAddWdTimes(userId, userWdRightsItem, relationId, isToday);
    }
}
