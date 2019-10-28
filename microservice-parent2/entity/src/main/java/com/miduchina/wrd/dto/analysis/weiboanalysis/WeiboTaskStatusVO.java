package com.miduchina.wrd.dto.analysis.weiboanalysis;

import lombok.Data;

@Data
public class WeiboTaskStatusVO {
    private Integer schedulePercent;
    private String weiboAnalysisTaskId;
    private String platformTag;
    private String userTag;
    private String weiboUrl;
    private String weiboId;
    private String analysisType;
    private String analysisTimes;
    private String totalTier;
    private String finishedTier;
    private String analysisStatus;
    private String analysisTaskTicket;
    private String analysisStartTime;
    private String analysisEndTime;
    private String totalRepostsCount;
    private String totalCommentsCount;
    private String status;
    private String createTime;
    private String updateTime;
}
