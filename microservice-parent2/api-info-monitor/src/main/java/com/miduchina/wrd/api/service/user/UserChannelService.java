package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;


public interface UserChannelService {
    /**
     * 获取用户渠道
     * */
    UserExclusiveChannel queryOneUserChannel(String code);


    boolean saveUserExclusiveChannelRelation(UserExclusiveChannelRelation relation);



}
