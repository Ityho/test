package com.miduchina.wrd.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户权益变动记录表
 *
 * @author liym
 */
@Data
public class UserRightsRechargeRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer userId;
    private Integer relationId;
    private Integer type; // 1-增加 2-减少
    private Integer count;
    private Integer currentCount;
    private Integer item; // 1-事件分析 2-简报制作 3-竞品分析 4-自动报表 5-单条微博分析
    private String recordDesc;
    private Integer platform;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
