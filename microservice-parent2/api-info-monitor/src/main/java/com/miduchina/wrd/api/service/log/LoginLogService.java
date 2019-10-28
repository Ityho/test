package com.miduchina.wrd.api.service.log;

import com.miduchina.wrd.po.log.LoginLog;

/**
 * @version v1.0.0
 * @ClassName: LoginLogService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:34 PM
 */
public interface LoginLogService {

    /**
     * 保存记录日志
     *
     * @param loginLog
     * @return
     */
    boolean doSaveLoginLog(LoginLog loginLog);
}
