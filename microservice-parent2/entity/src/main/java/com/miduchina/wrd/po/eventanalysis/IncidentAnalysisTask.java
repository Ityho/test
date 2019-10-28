package com.miduchina.wrd.po.eventanalysis;

import lombok.Data;

import java.util.Date;

@Data
public class IncidentAnalysisTask implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int taskId;
    private String platform;
    private int userId;
    private int contentType = 1;
    private String incidentTitle;
    private String keyword;
    private String supplementKeyword;
    private String filterKeyword;
    private int analysisType;
    private String analysisParams;
    private int analysisStatus = 1;
    private Date startTime;
    private Date endTime;
    private Date upToTime;
    private Double schedule = 0.00;
    private String scheduleTips;
    private String taskTicket;
    private int status = 1;
    private Date createTime;
    private Date updateTime;
    private int wbContentType = 0;
    private int setTime ;
    private int updateNum = 0;
    private int createType = 1;

    //数据库无此字段
    private Integer analysisTotalConsumeExpect;
    private Integer analysisSolrFirstCountExpect;
    private boolean sensitive;
}



