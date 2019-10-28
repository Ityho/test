package com.miduchina.wrd.api.service.log;

import com.miduchina.wrd.po.log.SignInLog;

/**
 * @version v1.0.0
 * @ClassName: SignInLogService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:44 PM
 */
public interface SignInLogService {

    /**
     * 根据用户id查看最后一次签到
     *
     * @param userId
     * @return SignInLog
     */
    SignInLog queryLastSignInLog(Integer userId);
}
