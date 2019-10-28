package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;

public interface UserChannelRelationService {
    /**
     * 获取用户渠道
     * */
    Boolean addUserChannelRelation(UserExclusiveChannelRelation channelRelation);


}
