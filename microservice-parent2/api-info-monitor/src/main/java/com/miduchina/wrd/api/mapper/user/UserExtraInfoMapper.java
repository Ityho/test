package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.po.user.UserExtraInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: UserExtraInfoMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:29 PM
 */
@Mapper
public interface UserExtraInfoMapper {

    UserExtraInfo selectUserExtraInfo(UserExtraInfo userExtraInfo);

    boolean insertUserExtraInfo(UserExtraInfo userExtraInfo);
}
