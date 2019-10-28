package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentAnalysisReport implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int reportId;
    private int taskId;
    private int userId;
    private String taskTicket;
    private int status = 1;
    private Date createTime;
    private Date updateTime;
    private String incidentTitle;
    private int contentType;
    private String platform;
}

