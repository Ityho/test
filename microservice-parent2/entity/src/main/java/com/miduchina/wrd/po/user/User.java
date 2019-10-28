package com.miduchina.wrd.po.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shitao on 2019-05-08 19:04.
 *
 * @author shitao
 */
@Data
public class User implements Serializable{
    private Integer userId;
    private String username;
    private String nickname;
    private String email;
    private String mobile;
    private String password;
    private int passwordStrength;
    private String thirdpartyAccount;
    private Integer thirdpartyType;
    private int isVerified;
    private Date verifiedTime;
    private Integer validSmsCount;
    private Date lastLoginTime;
    private String remark;
    private int status;
    private Date createTime;
    private Date updateTime;
    private Integer smsTotalCount;
    /**
     * 0-未激活 1-已激活
     */
    private Integer mailStatus;
    /**
     * 用户头像图片地址
     */
    private String userHead;
    /**
     * 0-WEB用户，1-客户端用户，2-H5用户
     */
    private int appUserStatus;
    /**
     * 用户类型 1：基础用户 2：专业版用户 3：专业版ABC用户
     */
    private int userType;
    /**
     * 专业版用户过期时间
     */
    private Date proUserValidEndTime;
    /**
     * 用户渠道 1：微舆情用户 2：新浪微博用户 3:微信用户 4:微博权益包用户 5:代理商用户 6:买火车票用户 7:微博内嵌 8:微博内部开通 9:京东万象 10: 推广渠道 12: 邀请用户（12以下保留）
     */
    private int userChannel;
    /**
     * 全网事件分析有效次数
     */
    private int userAnalysisValidCount;
    /**
     * 微博事件分析有效次数
     */
    private int userWeiboAnalysisValidCount;
    /**
     * 简报制作有效次数
     */
    private int userBriefValidCount;
    /**
     * 自动报表有效次数
     */
    private int userReportValidCount;
    /**
     * 竞品分析有效次数
     */
    private int userProductAnalysisValidCount;
    /**
     * 客户端用户的注册平台：Android， iOS等
     */
    private String platform;
    /**
     * 单条微博分析剩余次数
     */
    private int userSingleWeiboAnalysisValidCount;
    /**
     * 导出数据剩余条数
     */
    private int exportDataCount;
    /**
     * 微积分余额
     */
    private int creditAmount;
    /**
     * 热度对比剩余次数
     */
    private Integer heatReportCount;
    /**
     * 互动基金
     */
    private Double sharePlanAmount;
    /**
     * 帐号封停时间
     */
    private Date stopEndTime;
    private Integer wbAccountFansAnalysisCount;
    /**
     * 新闻稿监测次数
     */
    private Integer wrdNewsMonitorCount;

}
