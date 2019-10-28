package com.miduchina.wrd.dto.log;

import lombok.Data;


import java.util.Date;

@Data
public class LoginLog {

    private static final long serialVersionUID = 1L;
    private Integer loginId;
    private Integer loginUserId;
    private Date loginTime;
    private Integer loginType; // 1-手机号码登录 2-新浪微博登录 3-微信登录 4-QQ登录
    private Integer loginPlatform; // 1-微热点WEB 2-微热点客户端  3-微博微热点WEB 4-微博微热点H5
    private String loginIp;
    @Override
    public String toString() {
        return "LoginLog [loginId=" + loginId + ", loginUserId=" + loginUserId
                + ", loginTime=" + loginTime + ", loginType=" + loginType
                + ", loginPlatform=" + loginPlatform + ", loginIp=" + loginIp
                + "]";
    }

}
