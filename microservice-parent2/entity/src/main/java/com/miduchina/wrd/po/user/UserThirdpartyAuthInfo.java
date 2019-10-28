package com.miduchina.wrd.po.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方账号授权表
 *
 * @author liym
 */
@Data
@NoArgsConstructor
public class UserThirdpartyAuthInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String thirdpartyUserId;
    private int userId;
    private String accessToken;
    private String refreshToken;
    private Date authTime; // 授权时间
    private Date expireTime; // 过期时间
    private int platformType; // 第三方平台类型1-微博，2-微信，3-QQ
    private String verifiedType;
    private int applicationType; // 应用类型1-WEB，2-移动
    private int status;
    private long expireIn;

    public UserThirdpartyAuthInfo(String thirdpartyUserId, Integer platformType, Integer status) {
        super();
        this.thirdpartyUserId = thirdpartyUserId;
        this.platformType = platformType;
        this.status = status;
    }
}
