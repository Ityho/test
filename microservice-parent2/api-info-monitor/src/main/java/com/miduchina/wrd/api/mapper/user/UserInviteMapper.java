package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.po.user.UserInviteRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: UserInviteMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:21 AM
 */
@Mapper
public interface UserInviteMapper {

    boolean insertUserInviteRelation(UserInviteRelation userInviteRelation);
}
