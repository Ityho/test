package com.miduchina.wrd.po.analysis.weiboanalysis;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shitao on 2019-05-23 15:24.
 *
 * @author shitao
 */
@Data
public class WeiBoAnalysisTask  {

    /**
     * 任务ID
     */
    private int taskId;
    /**
     * 用户ID
     */
    private int userId;
    /**
     * 微博URL
     */
    private String weiboUrl;
    /**
     * 任务唯一标识
     */
    private String taskTicket;
    /**
     * 微博内容
     */
    private String weiboContent;
    /**
     * 微博转发数
     */
    private Integer forwardCount;
    /**
     * 评论数
     */
    private Integer commentCount;
    /**
     * 点赞数
     */
    private Integer praiseCount;
    /**
     * 微博发布时间
     */
    private Date publishedTime;
    /**
     * 微博发布时间
     */
    private String publishedTimeStr;
    /**
     * 微博用户ID
     */
    private String weiboUid;
    /**
     * 微博用户昵称
     */
    private String weiboNickname;
    /**
     * 微博用户头像url
     */
    private String weiboUserhead;
    /**
     * 分享code
     */
    private String shareCode;
    /**
     * 任务进度(状态:0失败，1等待分析，2正在拉取数据，3完成，4等待确认，5正在分析)
     */
    private Integer analysisStatus;
    /**
     * 分享链接
     */
    private String shareUrl;
    /**
     * 任务创建时间
     */
    private Date createTime;

    /**
     * 任务创建时间
     */
    private String createTimeStr;

    /**
     * 任务更新时间
     */
    private Date updateTime;
    /**
     * 转发内容
     */
    private String forwardContent;
    /**
     * 是否案例
     */
    private Integer isSample;

    /**
     * 旧版新版（0旧 新1）
     */
    private Integer markType;

    /**
     * 分享阅读数
     */
    private  int shareRedCount;


    /**
     * 博主认证类型
     */
    private int verifiedType;

    /**
     * 是否支付
     */
    private Integer payment;

    /**
     * 分享平台（分享平台  1-微博  2-微信  3-微信朋友圈  4-QQ  5-QQ空间）
     */
    private int sharePlatform;


    /**
     * status 有效状态
     */
    private Integer status;

    /**
     *  (支付超过15分钟 不能再次支付)
     */
    private Integer isExceed;


}
