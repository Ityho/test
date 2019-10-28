package com.miduchina.wrd.api.mapper.user;


import com.miduchina.wrd.po.user.UserWdRightsRechargeRecord;
import com.miduchina.wrd.po.user.UserWdRightsRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: UserWdRightsRecordMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:52 PM
 */
@Mapper
public interface UserWdRightsRecordMapper {

    int selectUserValidWdSum(Integer userId, String validEndTime);

    boolean insetUserWdRightsRechargeRecord(UserWdRightsRechargeRecord userWdRightsRechargeRecord);

    boolean insertUserWdRightsRecord(UserWdRightsRecord userWdRightsRecord);

    Integer selectAddWdTimes(Integer userId, Integer userWdRightsItem, Integer relationId, boolean isToday);
}
