package com.miduchina.wrd.eventanalysis.geetest;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.08.08
 */
@Data
public class GeetestValid {

    /**
     * 验证ip
     */
    private String ip;
    /**
     * 验证用户
     */
    private String userId;
    /**
     * 验证次数
     */
    private int verifyingTimes;
    /**
     * 验证通过次数
     */
    private int verifyingSuccessTimes;
    /**
     * 验证通过次数
     */
    private int verifyingFailTimes;
    /**
     * 极验处理状态
     */
    private Integer gtServerStatus;
    private Date createTime;
    private Date updateTime;
}
