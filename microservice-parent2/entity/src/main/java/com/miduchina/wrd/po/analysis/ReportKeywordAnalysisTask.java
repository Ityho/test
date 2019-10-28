package com.miduchina.wrd.po.analysis;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:15.
 *
 * @author shitao
 */
public class ReportKeywordAnalysisTask {

    /**
     * 任务id
     */
    private int taskId;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 任务唯一标识
     */
    private String taskTicket;
    /**
     * 状态
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
     * 关键词
     */
    private String keyword;
    /**
     * 排除关键词
     */
    private String filterKeyword;
    /**
     * 进度
     */
    private Double schedule = 0.00;
    /**
     * 进度说明
     */
    private String scheduleTips;
    /**
     * 平台
     */
    private String platform;
    /**
     * 任务状态
     */
    private int analysisStatus = 1;
    /**
     * 标题
     */
    private String incidentTitle;
    /**
     *  预估
     */
    private int analysisTotalConsumeExpect;
    private int analysisSolrFirstCountExpect;

}
