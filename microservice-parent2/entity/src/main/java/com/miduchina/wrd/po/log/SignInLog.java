package com.miduchina.wrd.po.log;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: SignInLog
 * @Description: 签到日志
 * @author: sty
 * @date: 2019/7/18 1:52 PM
 */
@Data
public class SignInLog {
    private Integer id;
    private Integer userId;
    //连续签到天数
    private Integer seriesDays;
    //连续签到奖励微豆数
    private Integer seriesRewardWdNum;
    //领取奖励id
    private Integer signInRewardId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
