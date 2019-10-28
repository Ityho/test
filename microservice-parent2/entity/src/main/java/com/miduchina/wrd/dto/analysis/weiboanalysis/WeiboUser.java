package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboUser {
    private String userId;
    private String userScreenName;
    private String userLogoUrl;
    private Integer userFriendsCount;
    private Integer userFollowersCount;
    private Integer userStatusCount;
    private Integer userVerifiedType;
    private String statusId;
    private String statusText;
    private Integer statusRepostsCount;
    private Integer statusCommentsCount;
    private Integer statusAttitudesCount;
    private Integer statusReadsCount;
    private String statusCreatedAt;

    private String pageUrl;
    private String weiboUrl;
}
