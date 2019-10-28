package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.po.user.UserExtendInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: UserExtendInfoMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:32 AM
 */
@Mapper
public interface UserExtendInfoMapper {

    boolean insertUserExtendInfo(UserExtendInfo userExtendInfo);

    UserExtendInfo selectUserExtendInfo(Integer userId);
}
