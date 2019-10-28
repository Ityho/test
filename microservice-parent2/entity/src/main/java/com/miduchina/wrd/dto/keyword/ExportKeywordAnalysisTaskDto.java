package com.miduchina.wrd.dto.keyword;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 18:15.
 *
 * @author shitao
 */
@Data
public class ExportKeywordAnalysisTaskDto {

    @ApiModelProperty(value = "分析任务ID")
    private int taskId;
    @ApiModelProperty(value = "用户ID")
    private int userId;
    @ApiModelProperty(value = "分析唯一标识")
    private String taskTicket;
    @ApiModelProperty(value = "分析状态(1:有效，2:无效)")
    private int status = 1;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "事件匹配关键词")
    private String keyword;
    @ApiModelProperty(value = "事件排除关键词")
    private String filterKeyword;
    @ApiModelProperty(value = "任务进度（百分比）")
    private Double schedule = 0.00;
    @ApiModelProperty(value = "任务进度说明")
    private String scheduleTips;
    @ApiModelProperty(value = "平台标识")
    private String platform;
    @ApiModelProperty(value = "处理状态(1:待处理，2:正在处理，3:已处理，4:处理完成)")
    private int analysisStatus = 1;
    @ApiModelProperty(value = "事件标题")
    private String incidentTitle;
    @ApiModelProperty(value = "事件开始时间")
    private Date exportStartTime;
    @ApiModelProperty(value = "事件结束时间")
    private Date exportEndTime;
    private int type;
    @ApiModelProperty(value = "事件文件路径")
    private String filePath;
    private int count;
    private Integer apiType;
    @ApiModelProperty(value = "新旧接口标记-1.新接口  null或不为1.旧接口")
    private Integer isNew;
}
