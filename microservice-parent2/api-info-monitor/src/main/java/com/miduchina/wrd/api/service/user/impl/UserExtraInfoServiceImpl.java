package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.UserExtraInfoMapper;
import com.miduchina.wrd.api.service.user.UserExtraInfoService;
import com.miduchina.wrd.po.user.UserExtraInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: UserExtraInfoServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:30 PM
 */
@Service
public class UserExtraInfoServiceImpl implements UserExtraInfoService {

    @Resource
    private UserExtraInfoMapper userExtraInfoMapper;

    @Override
    public UserExtraInfo queryUserExtraInfo(UserExtraInfo userExtraInfo) {
        return userExtraInfoMapper.selectUserExtraInfo(userExtraInfo);
    }

    @Override
    public boolean doSaveUserExtraInfo(UserExtraInfo userExtraInfo) {
        return userExtraInfoMapper.insertUserExtraInfo(userExtraInfo);
    }
}
