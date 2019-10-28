package com.miduchina.wrd.api.service.log.impl;

import com.miduchina.wrd.api.mapper.log.SignInLogMapper;
import com.miduchina.wrd.api.service.log.SignInLogService;
import com.miduchina.wrd.po.log.SignInLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: SignInLogServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:45 PM
 */
@Service
public class SignInLogServiceImpl implements SignInLogService{

    @Resource
    private SignInLogMapper signInLogMapper;

    @Override
    public SignInLog queryLastSignInLog(Integer userId) {
        return signInLogMapper.selectLastSignInLog(userId);
    }

}
