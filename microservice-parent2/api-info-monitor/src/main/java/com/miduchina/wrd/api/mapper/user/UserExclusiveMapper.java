package com.miduchina.wrd.api.mapper.user;

import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @version v1.0.0
 * @ClassName: UserExclusiveMapper
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:26 AM
 */
@Mapper
public interface UserExclusiveMapper {

    UserExclusiveChannel selectExclusiveChannelByCode(String exclusiveChannelCode);

    boolean insertUserExclusiveChannelRelation(UserExclusiveChannelRelation userExclusiveChannelRelation);
}
