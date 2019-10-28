package com.miduchina.wrd.po.analysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:16.
 *
 * @author shitao
 */
@Data
public class ReviewAnalysis {

    /**
     * 评论分析id
     */
    private Integer reviewAnalysisId;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 评论标题
     */
    private String reviewAnalysisTitle;
    /**
     * 评论内容
     */
    private String reviewAnalysisContent;
    /**
     * 标题链接
     */
    private String reviewAnalysisLink;
    /**
     * 发布时间
     */
    private Date releaseTime;
    /**
     * 发布网站
     */
    private String releaseWeb;
    /**
     * 来源网站
     */
    private String fromWeb;
    /**
     * 分析时间
     */
    private Date analysisTime;
    /**
     * 评论数
     */
    private Integer reviewNum;
    /**
     * 参与人数
     */
    private Integer participateInNum;
    /**
     * 评论生成的ticket
     */
    private String ticket;
    /**
     * 支付状态
     */
    private Integer payStatus;
    /**
     * 状态 --0 删除  --1有效
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
     * 分享码
     */
    private String reviewAnalysisShareCode;
    /**
     * 固话状态
     */
    private Integer fixed_status;

}
