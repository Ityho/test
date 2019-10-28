package com.miduchina.wrd.api.service.user.impl;/**
 * Created by sty on 2019/7/18.
 */

import com.miduchina.wrd.api.mapper.user.UserInviteMapper;
import com.miduchina.wrd.api.service.user.UserInviteService;
import com.miduchina.wrd.po.user.UserInviteRelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @version v1.0.0
 * @ClassName: UserInviteServiceImpl
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 10:19 AM
 */
@Service
public class UserInviteServiceImpl implements UserInviteService{
    @Resource
    private UserInviteMapper userInviteMapper;

    @Override
    public boolean doSaveUserInviteRelation(UserInviteRelation userInviteRelation) {
        return userInviteMapper.insertUserInviteRelation(userInviteRelation);
    }
}
