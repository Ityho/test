package com.miduchina.wrd.dto.user;/**
 * Created by sty on 2019/7/18.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @version v1.0.0
 * @ClassName: UserCreateThirdpartyDto
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/18 9:54 AM
 */
@Data
public class UserCreateThirdpartyDto implements Serializable {

    private String uid;
    private String accessToken;
    private String refreshToken;
    private Long expireIn;
    private Integer platformType;
    private Integer appUserStatus;
    private Integer userChannel;
    private Integer giftPackageId;
    private String verifiedType;
    private String nickname;
    private String userHead;
    private String email;
    private String mobile;
    private String password;
    private String appPlatform;
    private String exclusiveChannelCode;
    private Boolean isLogin;
    private String extraInfo;
    private Boolean isGiftPackage;

    public boolean isGiftPackage() {
        return isGiftPackage == null ? false : isGiftPackage;
    }


}
