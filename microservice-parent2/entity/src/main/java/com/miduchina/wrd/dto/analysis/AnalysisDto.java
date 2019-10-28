package com.miduchina.wrd.dto.analysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-17 17:34.
 *
 * @author shitao
 */
@Data
public class AnalysisDto {

    @ApiModelProperty(value = "分析状态")
    private int analysisStatus;
    @ApiModelProperty(value = "")
    private String areaDesc;
    @ApiModelProperty(value = "")
    private String code;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "")
    private Integer id;
    @ApiModelProperty(value = "关键词")
    private String keywords;
    @ApiModelProperty(value = "")
    private String monitoringDesc;
    @ApiModelProperty(value = "")
    private String negativeDesc;
    @ApiModelProperty(value = "百分比")
    private Double percent;
    @ApiModelProperty(value = "平台")
    private String platform;
    @ApiModelProperty(value = "分析ID")
    private int productAnalysisId;
    @ApiModelProperty(value = "进度")
    private String schedule;
    @ApiModelProperty(value = "")
    private String sensitiveDesc;
    @ApiModelProperty(value = "")
    private String sourceMediaDesc;
    @ApiModelProperty(value = "")
    private String sourceTypeDesc;
    @ApiModelProperty(value = "任务开始时间")
    private Date startTime;
    @ApiModelProperty(value = "状态")
    private int status;
    @ApiModelProperty(value = "")
    private String tendDesc;
    @ApiModelProperty(value = "任务唯一标识")
    private String ticket;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "")
    private String wordDesc;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "分享链接")
    private String shareUrl;

    @ApiModelProperty(value = "bar图")
    private String barImg;

    @ApiModelProperty(value = "是否是案例")
    private Integer isSample;

    private Double schedulePercent;

}
