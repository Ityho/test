package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.UserExtendInfoMapper;
import com.miduchina.wrd.api.service.user.UserExtendInfoService;
import com.miduchina.wrd.po.user.UserExtendInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: UserExtendInfoServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:48 PM
 */
@Service
public class UserExtendInfoServiceImpl implements UserExtendInfoService{

    @Resource
    private UserExtendInfoMapper userExtendInfoMapper;

    @Override
    public UserExtendInfo queryUserExtendInfo(Integer userId){
        return userExtendInfoMapper.selectUserExtendInfo(userId);
    }
}
