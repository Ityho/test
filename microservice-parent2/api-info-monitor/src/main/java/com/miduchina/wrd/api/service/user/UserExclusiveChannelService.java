package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;

/**
 * @version v1.0.0
 * @ClassName: UserExclusiveChannelService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:23 AM
 */
public interface UserExclusiveChannelService {

    /**
     * 根据分享码获取渠道信息
     *
     * @param exclusiveChannelCode
     * @return
     */
    UserExclusiveChannel queryExclusiveChannelByCode(String exclusiveChannelCode);

    /**
     * 保存用户和渠道关系
     *
     * @param userExclusiveChannelRelation
     * @return
     */
    boolean doSaveUserExclusiveChannelRelation(UserExclusiveChannelRelation userExclusiveChannelRelation);
}
