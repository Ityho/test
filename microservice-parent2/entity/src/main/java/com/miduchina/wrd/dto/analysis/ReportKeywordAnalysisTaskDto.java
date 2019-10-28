package com.miduchina.wrd.dto.analysis;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:15.
 *
 * @author shitao
 */
public class ReportKeywordAnalysisTaskDto {

    @ApiModelProperty(value = "任务id")
    private int taskId;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "任务唯一标识")
    private String taskTicket;
    @ApiModelProperty(value = "状态")
    private int status = 1;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "关键词")
    private String keyword;
    @ApiModelProperty(value = "排除关键词")
    private String filterKeyword;
    @ApiModelProperty(value = "进度")
    private Double schedule = 0.00;
    @ApiModelProperty(value = "进度说明")
    private String scheduleTips;
    @ApiModelProperty(value = "平台")
    private String platform;
    @ApiModelProperty(value = "任务状态")
    private int analysisStatus = 1;
    @ApiModelProperty(value = "标题")
    private String incidentTitle;

    //数据库无此字段
    private int analysisTotalConsumeExpect;
    private int analysisSolrFirstCountExpect;

}
