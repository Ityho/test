package com.miduchina.wrd.api.service.log.impl;

import com.miduchina.wrd.api.mapper.log.LoginLogMapper;
import com.miduchina.wrd.api.service.log.LoginLogService;
import com.miduchina.wrd.po.log.LoginLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: LoginLogServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:34 PM
 */
@Service
public class LoginLogServiceImpl implements LoginLogService{

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public boolean doSaveLoginLog(LoginLog loginLog) {
        return loginLogMapper.insertLoginLog(loginLog);
    }
}
