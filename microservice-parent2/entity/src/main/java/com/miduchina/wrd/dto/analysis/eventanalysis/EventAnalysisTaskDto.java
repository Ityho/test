package com.miduchina.wrd.dto.analysis.eventanalysis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by shitao on 2019-05-08 15:25.
 *
 * @author shitao
 */
@Data
public class EventAnalysisTaskDto {

    @ApiModelProperty(value = "编号")
    private Integer taskId;
    @ApiModelProperty(value = "平台标识")
    private String platformTag;
    @ApiModelProperty(value = "分析范围(1:全网，2:微博，3:非微博，default:1)")
    private Integer contentType;
    @ApiModelProperty(value = "事件标题")
    private String incidentTitle;
    @ApiModelProperty(value = "事件匹配关键词")
    private String keyword;
    @ApiModelProperty(value = "事件补充词语")
    private String supplementKeyword;
    @ApiModelProperty(value = "事件排除关键词")
    private String filterKeyword;
    @ApiModelProperty(value = "分析类型(1:单次，2:持续)")
    private Integer analysisType;
    @ApiModelProperty(value = "分析参数详情，JSON格式，配合“分析参数类型”进行扩展")
    private String analysisParams;
    @ApiModelProperty(value = "处理状态(0:失败，1:待处理，2:正在处理，3:已处理，4:处理完成)")
    private Integer analysisStatus;
    @ApiModelProperty(value = "处理开始时间(default:1970-01-01 00:00:00)")
    private Date startTime;
    @ApiModelProperty(value = "处理结束时间(default:当前时间)")
    private Date endTime;
    @ApiModelProperty(value = "处理截止时间")
    private Date analysisUpToTime;
    @ApiModelProperty(value = "任务进度（百分比）")
    private Double analysisSchedule;
    @ApiModelProperty(value = "任务进度说明")
    private String analysisScheduleTips;
    @ApiModelProperty(value = "处理批次号，用于跟处理结果关联")
    private String taskTicket;
    @ApiModelProperty(value = "状态(1:有效，2:无效)")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "微博分析内容类型：0-全部微博，1-原创微博")
    private Integer analysisWbContentType;
    @ApiModelProperty(value = "更新次数")
    private Integer updateNum;
    @ApiModelProperty(value = "创建类型 1：创建 2：更新 3：重新分析")
    private Integer createType;
    @ApiModelProperty(value = "信息属性")
    private Integer options;
    @ApiModelProperty(value = "匹配方式")
    private Integer matchType;
    @ApiModelProperty(value = "内容来源")
    private String origin;

    @ApiModelProperty(value = "预计时间 毫秒")
    private Integer analysisTotalConsumeExpect=0;
    @ApiModelProperty(value = "预计数量")
    private Integer analysisSolrFirstCountExpect=0;
    @ApiModelProperty(value = "含有敏感词")
    private boolean sensitive;
    @ApiModelProperty(value = "预计时间（分钟）")
    private int estimateMinute=0;


    private List<EventAnalysisTaskDto> tasks;


}
