package com.miduchina.wrd.po.keyword;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 10:24.
 *
 * @author shitao
 */
@Data
public class ExportKeywordAnalysisTask {

    /**
     * 分析任务ID
     */
    private int taskId;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 分析唯一标识
     */
    private String taskTicket;
    /**
     * 分析状态(1:有效，2:无效)
     */
    private int status = 1;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 事件匹配关键词
     */
    private String keyword;
    /**
     * 事件排除关键词
     */
    private String filterKeyword;
    /**
     * 任务进度（百分比）
     */
    private Double schedule = 0.00;
    /**
     * 任务进度说明
     */
    private String scheduleTips;
    /**
     * 平台标识
     */
    private String platform;
    /**
     * 处理状态(1:待处理，2:正在处理，3:已处理，4:处理完成)
     */
    private int analysisStatus = 1;
    /**
     * 事件标题
     */
    private String incidentTitle;
    /**
     * 事件开始时间
     */
    private Date exportStartTime;
    /**
     * 事件结束时间
     */
    private Date exportEndTime;
    private int type;
    /**
     * 事件文件路径
     */
    private String filePath;
    private int count;
    private Integer apiType;
    /**
     * 新旧接口标记-1.新接口  null或不为1.旧接口
     */
    private Integer isNew;


}
