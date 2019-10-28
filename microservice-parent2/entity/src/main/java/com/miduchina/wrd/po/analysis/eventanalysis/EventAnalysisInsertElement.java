package com.miduchina.wrd.po.analysis.eventanalysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 15:41.
 *
 * @author shitao
 */
@Data
public class EventAnalysisInsertElement {

    /**
     * ID
     */
    private Integer id;
    /**
     * 模块ID
     */
    private Integer elementId;
    /**
     * 元素ID
     */
    private Integer elementStyleId;
    /**
     * 报告ID
     */
    private Integer analysisReportId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 任务状态
     */
    private int status;
    /**
     * 元素顺序
     */
    private int seq;
    /**
     * 标题名称
     */
    private String name;
    /**
     * 文本内容
     */
    private String content;
    /**
     * 标题列
     */
    private String titleColumn;
    /**
     * 任务唯一标识
     */
    private String analysisTaskTicket;
    /**
     * 模版ID
     */
    private int modelId;
    /**
     * 事件走势时间
     */
    private String treeTime;
    /**
     * 事件走势说明文本
     */
    private String treeText;
    /**
     * 热门信息说明文本
     */
    private String hotText;
    /**
     * 事件标题
     */
    private String analysisTitle;
    /**
     * 微博链接
     */
    private String weiboUrl;
    /**
     * 1列表样式 2圆环样式
     */
    private int picStyle;



}
