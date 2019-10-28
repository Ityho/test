package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.util.Date;

/**
 * Created by xujing on 2016/11/8 0008.
 */
@Data
public class HeatShare implements java.io.Serializable {

    private Integer id;
    private String shareCode;
    private int status;
    private Date createTime;
    private Date updateTime;
    private int userId;
    private Integer date;
    private String title;
    private String categoryId;

    private String keyword;
    private String filterKeyword;
    private String shareDate;

    private int solidifyStatus;
    private int conditionId;
    private String type;
    private String analysisTaskTicket;
    private String analysisScheduleTips;
    private Integer analysisStatus;
    private String platTag;
    private String startTime;
    private String endTime;

    private String filePath;
    private Integer version;
    private String analysisSchedulePercent;


}
