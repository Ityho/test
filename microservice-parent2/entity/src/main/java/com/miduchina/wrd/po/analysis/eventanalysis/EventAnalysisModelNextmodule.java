package com.miduchina.wrd.po.analysis.eventanalysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-29 15:48.
 *
 * @author shitao
 */
@Data
public class EventAnalysisModelNextmodule {

    /**
     * id
     */
    private Integer id;
    /**
     * 模块ID
     */
    private int elementId;
    /**
     * 模块名称
     */
    private String modelNextName;
    /**
     * 模块说明
     */
    private String modelNextText;
    /**
     * 模块状态 1-显示  2-删除
     */
    private int status;
    /**
     * 元素顺序
     */
    private int seq;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 元素id
     */
    private Integer elementStyleId;
    /**
     * 事件分析报告ID
     */
    private Integer analysisReportId;
    /**
     *
     */
    private String containerId;
    private String viewpointText;
    private String viewpoint;
    /**
     * 微博URL
     */
    private String weiboUrl;


}
