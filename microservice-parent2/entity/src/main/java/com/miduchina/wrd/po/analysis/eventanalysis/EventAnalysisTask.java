package com.miduchina.wrd.po.analysis.eventanalysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-08 15:25.
 *
 * @author shitao
 */
@Data
public class EventAnalysisTask {

    /**
     * ID
     */
    private Integer taskId;
    /**
     * 平台标识
     */
    private String platformTag;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 分析范围(1:全网，2:微博，3:非微博，default:1)
     */
    private Integer contentType;
    /**
     * 事件标题
     */
    private String incidentTitle;
    /**
     * 事件匹配关键词
     */
    private String keyword;
    /**
     * 事件补充词语
     */
    private String supplementKeyword;
    /**
     * 事件排除关键词
     */
    private String filterKeyword;
    /**
     * 分析类型(1:单次，2:持续)
     */
    private Integer analysisType;
    /**
     * 分析参数详情，JSON格式，配合“分析参数类型”进行扩展
     */
    private String analysisParams;
    /**
     * 处理状态(0:失败，1:待处理，2:正在处理，3:已处理，4:处理完成)
     */
    private Integer analysisStatus;
    /**
     * 处理开始时间(default:1970-01-01 00:00:00)
     */
    private Date startTime;
    /**
     * 处理结束时间(default:当前时间)
     */
    private Date endTime;
    /**
     * 处理截止时间
     */
    private Date analysisUpToTime;
    /**
     * 任务进度（百分比）
     */
    private Double analysisSchedule;
    /**
     * 任务进度说明
     */
    private String analysisScheduleTips;
    /**
     * 处理批次号，用于跟处理结果关联
     */
    private String taskTicket;
    /**
     * 状态(1:有效，2:无效)
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 微博分析内容类型：0-全部微博，1-原创微博
     */
    private Integer analysisWbContentType;
    /**
     * 更新次数
     */
    private Integer updateNum;
    /**
     * 创建类型 1：创建 2：更新 3：重新分析
     */
    private Integer createType;
    /**
     * 信息属性
     */
    private Integer options;
    /**
     * 匹配方式
     */
    private Integer matchType;
    /**
     * 内容来源
     */
    private String origin;

}
