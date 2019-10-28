package com.miduchina.wrd.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCreateDto implements Serializable {
    private static final long serialVersionUID = 2248719219668392622L;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String userHead;
    private Integer appUserStatus;
    private Integer userChannel;
    private String appPlatform;
    private String exclusiveChannelCode;
    private Integer giftPackageId;
    private boolean isLogin;
    private Integer inviteUserId;
    private Boolean isGiftPackage;

    public void setGiftPackage(Boolean isGiftPackage) {
        isGiftPackage = isGiftPackage == null ? false : isGiftPackage;
        this.isGiftPackage = isGiftPackage;
    }

    public boolean isGiftPackage() {
        return isGiftPackage == null ? false : isGiftPackage;
    }

}

