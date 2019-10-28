package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.po.user.UserRightsRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version v1.0.0
 * @ClassName: UserRightsRecordMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:07 PM
 */
@Mapper
public interface UserRightsRecordMapper {

    List<UserRightsRecord> selectUserRightsRecords(UserRightsRecord userRightsRecord);
}
