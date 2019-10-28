package com.miduchina.wrd.bo.user;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserBO
 * @Description: TODO
 * @author: sty
 * @date: 2019/7/17 2:27 PM
 */
@Data
public class UserBO {

    private Integer userId;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private String password;
    private Integer passwordStrength;
    private String thirdpartyAccount;
    private Integer thirdpartyType;
    private Date lastLoginTime;
    private String remark;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String userHead;
    private Integer appUserStatus;
    private Integer userChannel;
    private Integer userType;
    private Date proUserValidEndTime;
    private Integer userAnalysisValidCount;
    private Integer userWeiboAnalysisValidCount;
    private Integer userBriefValidCount;
    private Integer userReportValidCount;
    private Integer userProductAnalysisValidCount;
    private Integer userSingleWeiboAnalysisValidCount;
    private Integer exportDataCount;
    private Integer creditAmount;
    private String platform;
    private Integer heatReportCount;
    private Double sharePlanAmount;
    private Integer commentsCount;
    private Integer allKeywordCount;
    private Integer noUseKeywordCount;

    private Boolean subUser;
    private Integer subUserId;

    private VipInfoBO vipInfo;
    private Integer validWdSum;
    private Integer willOverdueWdSum;
    private String lastSignInDate;
    private Integer seriesSignInDays;
    private UserExtendInfoBO userExtendInfo;
    private Integer proLevel;
    private Integer wrdNewsMonitorCount;

    private Date stopEndTime;
    private PackgeManagentBO packgeManagent;
}
