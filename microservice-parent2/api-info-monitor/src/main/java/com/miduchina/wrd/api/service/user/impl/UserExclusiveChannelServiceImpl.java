package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.UserExclusiveMapper;
import com.miduchina.wrd.api.service.user.UserExclusiveChannelService;
import com.miduchina.wrd.dto.user.UserExclusiveChannelRelation;
import com.miduchina.wrd.po.user.UserExclusiveChannel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: SubUserInfoServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:42 AM
 */
@Service
public class UserExclusiveChannelServiceImpl implements UserExclusiveChannelService{

    @Resource
    private UserExclusiveMapper userExclusiveMapper;

    @Override
    public UserExclusiveChannel queryExclusiveChannelByCode(String exclusiveChannelCode) {
        return userExclusiveMapper.selectExclusiveChannelByCode(exclusiveChannelCode);
    }

    @Override
    public boolean doSaveUserExclusiveChannelRelation(UserExclusiveChannelRelation userExclusiveChannelRelation) {
        return userExclusiveMapper.insertUserExclusiveChannelRelation(userExclusiveChannelRelation);
    }

}
