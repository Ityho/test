package com.miduchina.wrd.po.user;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserExtraInfo
 * @Description: 用户扩展信息
 * @author: sty
 * @date: 2019/7/18 10:12 AM
 */
@Data
public class UserExtraInfo {
    private Integer extraId;
    private Integer extraAccountType;
    private String extraRegIp;
    private String extraRegIpProvince;
    private String extraRegIpCity;
    private String extraMobile;
    private String extraMobileOperator;
    private String extraMobileAreaCode;
    private String extraMobileProvince;
    private String extraMobileCity;
    private String extraMobileCounty;
    private String extraUid;
    private String extraInfo;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public UserExtraInfo() {
    }

    public UserExtraInfo(Integer extraAccountType, String extraMobile, Integer status) {
        super();
        this.extraAccountType = extraAccountType;
        this.extraMobile = extraMobile;
        this.status = status;
    }

    public UserExtraInfo(Integer extraAccountType, String extraUid, String extraInfo, Integer status) {
        super();
        this.extraAccountType = extraAccountType;
        this.extraUid = extraUid;
        this.extraInfo = extraInfo;
        this.status = status;
    }


}
