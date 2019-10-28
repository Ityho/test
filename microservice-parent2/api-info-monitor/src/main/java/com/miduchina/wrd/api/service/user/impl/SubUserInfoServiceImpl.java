package com.miduchina.wrd.api.service.user.impl;

import com.miduchina.wrd.api.mapper.user.SubUserInfoMapper;
import com.miduchina.wrd.api.service.user.SubUserInfoService;
import com.miduchina.wrd.po.user.SubUserInfo;
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
public class SubUserInfoServiceImpl implements SubUserInfoService{

    @Resource
    private SubUserInfoMapper subUserInfoMapper;
    @Override
    public SubUserInfo querySubUserInfoByUsernameAndPassword(String username, String password) {
        return subUserInfoMapper.selectSubUserInfoByUsernameAndPassword(username, password);
    }

}
