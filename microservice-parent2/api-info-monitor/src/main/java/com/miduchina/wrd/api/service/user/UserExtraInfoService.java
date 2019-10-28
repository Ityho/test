package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.user.UserExtraInfo;

/**
 * @version v1.0.0
 * @ClassName: UserExtraInfoService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:30 PM
 */
public interface UserExtraInfoService {

    /**
     * 获取用户扩展信息
     *
     * @param userExtraInfo
     * @return
     */
    UserExtraInfo queryUserExtraInfo(UserExtraInfo userExtraInfo);

    /**
     * 保存用户扩展信息
     *
     * @param userExtraInfo
     * @return
     */
    boolean doSaveUserExtraInfo(UserExtraInfo userExtraInfo);
}
