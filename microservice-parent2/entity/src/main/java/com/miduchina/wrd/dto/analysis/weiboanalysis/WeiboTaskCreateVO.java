package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskCreateVO {
    private int weiboForwardNumber;
    private String weiboAnalysisTaskId;
    private String platformTag;
    private String userTag;
    private String weiboUrl;
    private String weiboId;
    private String analysisType;
    private String analysisTimes;
    private String totalTier;
    private String finishedTier;
    private Integer analysisStatus;
    private String analysisTaskTicket;
    private String totalRepostsCount;
    private String totalCommentsCount;
    private String status;
    private String createTime;
    private String updateTime;
    private String statusRepostsCount;
    private String statusCommentsCount;
    private String statusAttitudesCount;
    private String statusReadsCount;
    private Weibo statusInfo;
}
