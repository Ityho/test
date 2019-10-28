package com.miduchina.wrd.po.log;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: LoginLog
 * @Description: 登录日志
 * @author: sty
 * @date: 2019/7/18 11:08 AM
 */
@Data
public class LoginLog {
    private Integer loginLogId;
    private Integer loginLogUserId;
    private Date loginLogTime;
    private Integer loginLogType;
    private Integer loginLogPlatform;
    private String loginLogIp;
    private Long loginLogNumIp;
    private String loginProvince;
    private String loginCity;
    private String loginIsp;
}
