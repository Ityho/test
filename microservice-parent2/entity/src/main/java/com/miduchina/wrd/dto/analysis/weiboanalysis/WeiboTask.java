package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTask {

    private int weiboId;
    private Integer userId;
    private String weiboUrl;
    private String weiboTaskTicket;
    private String weiboContent;
    private Long weiboForwardNumber;
    private int weiboCommentsNumber;
    private int weiboPraiseNumber;
    private long weiboPublishedTime;
    private String weiboUid;
    private String weiboNickname;
    private String weiboUserhead;
    private String weiboVerifiedType;
    private int payStatus;
    private String platform;
    private String percent;
    private String schedule;
    private int analysisStatus;
    private int status;
    private long createTime;
    private String updateTime;
    private String module;
    private String weiboImages;

}
