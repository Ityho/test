package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.user.SubUserInfo;

/**
 * @version v1.0.0
 * @ClassName: SubUserInfoService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:42 AM
 */
public interface SubUserInfoService {
    /**
     * 根据用户名和密码获取子账号信息
     *
     * @param username
     * @param password
     * @return
     */
    SubUserInfo querySubUserInfoByUsernameAndPassword(String username, String password);
}
