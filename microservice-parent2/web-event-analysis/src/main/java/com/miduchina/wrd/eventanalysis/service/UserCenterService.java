package com.miduchina.wrd.eventanalysis.service;

import com.miduchina.wrd.dto.user.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserCenterService {
    /**
     * 用户手机号注册
     *
     * @param user
     * @return
     */
    public UserDto doRegByMobile(HttpServletRequest req, HttpServletResponse resp, UserDto user, String uecChannel, Integer inviteUserId);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public int doRegister(UserDto user,HttpServletRequest request);
}
