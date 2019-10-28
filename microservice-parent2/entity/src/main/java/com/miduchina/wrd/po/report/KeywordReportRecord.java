package com.miduchina.wrd.po.report;

import lombok.Data;

import java.util.Date;

@Data
public class KeywordReportRecord {
    /**
     * id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 监测方案id
     */
    private Integer keywordId;
    /**
     * 监测方案内容
     */
    private String keywordContent;
    /**
     * 日报名称
     */
    private String reportName;
    /**
     * 日报邮件发送内容
     */
    private String reportEmailContent;
    /**
     * 发送状态
     */
    private Integer sendStatus;
    /**
     * 日报状态
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
     * Y:开 L：关
     */
    private String loadFlag;
    /**
     * 分享code
     */
    private String shareCode;
    /**
     * 是否已读 0-未读；1-已读
     */
    private Integer isRead;
    /**
     * 监测日报版式(1,2,3,4,5)共五种
     */
    private Integer dailyReportModel;
}
