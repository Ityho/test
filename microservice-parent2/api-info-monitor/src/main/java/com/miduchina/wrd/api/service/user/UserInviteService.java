package com.miduchina.wrd.api.service.user;

import com.miduchina.wrd.po.user.UserInviteRelation;

/**
 * @version v1.0.0
 * @ClassName: UserInviteService
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:18 AM
 */
public interface UserInviteService {
    /**
     * 保存邀请用户关联表
     *
     * @param userInviteRelation
     * @return
     */
    boolean doSaveUserInviteRelation(UserInviteRelation userInviteRelation);
}
