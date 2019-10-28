package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.user.UserExtendInfo;

/**
 * @version v1.0.0
 * @ClassName: UserExtendInfoService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 2:47 PM
 */
public interface UserExtendInfoService {

    /**
     * 获取用户扩展信息
     *
     * @return
     */
    UserExtendInfo queryUserExtendInfo(Integer userId);

}
