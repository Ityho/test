package com.miduchina.wrd.dto.eventanalysis;

import lombok.Data;

@Data
public class Task{
    private Integer incidentAnalysisTaskId;
    private String platformTag;
    private String channelTag;
    private String userTag;
    private String incidentTitle;
    private Double analysisSchedulePercent;
    private Integer analysisStatus;
    private String analysisTaskTicket;
}