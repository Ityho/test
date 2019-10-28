package com.miduchina.wrd.dto.analysis.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by st on 2019-05-08 15:25.
 *
 * @author st
 */

@Data
public class EventAnalysisReportDto {

    @ApiModelProperty(value = "状态(1:有效，2:无效)")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "事件标题")
    private String incidentTitle;
    @ApiModelProperty(value = "微博分析内容类型：1-全网，2-微博")
    private Integer contentType;
    @ApiModelProperty(value = "平台标识(2:wyq，3:yqt)")
    private String platformTag;
    @ApiModelProperty(value = "模板ID")
    private Integer modelId;

    @ApiModelProperty(value = "是否是案例")
    private Integer isSample;
    @ApiModelProperty(value = "事件通栏图片")
    private String eventBannerImgURL;
    @ApiModelProperty(value = "报告ID")
    private int reportId;
    @ApiModelProperty(value = "任务ID")
    private int taskId;
    @ApiModelProperty(value = "案例类型 1-竞品 2-全网事件 3-微博事件分析")
    private int caseType;
    @ApiModelProperty(value = "报告链接")
    private String reportURL;
    @ApiModelProperty(value = "分享url")
    private String shareUrl;
    @ApiModelProperty(value = "处理批次号，用于跟处理结果关联")
    private String taskTicket;

    private List<EventAnalysisReportDto> reports;

}
