package com.miduchina.wrd.po.analysis.eventanalysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-08 15:25.
 *
 * @author shitao
 */
@Data
public class EventAnalysisReport {


    /**
     * ID
     */
    private Integer reportId;
    /**
     * 任务ID
     */
    private Integer taskId;
    /**
     * 处理批次号，用于跟处理结果关联
     */
    private String ticket;
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
     * 用户ID
     */
    private Integer userId;
    /**
     * 事件标题
     */
    private String incidentTitle;
    /**
     * 微博分析内容类型：1-全网，2-微博
     */
    private Integer contentType;
    /**
     * 平台标识(2:wyq，3:yqt)
     */
    private String platform;
    /**
     * 模板ID
     */
    private Integer modelId;



}
