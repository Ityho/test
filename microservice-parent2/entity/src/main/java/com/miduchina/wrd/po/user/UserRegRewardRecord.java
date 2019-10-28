package com.miduchina.wrd.po.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserRegRewardRecord
 * @Description: 用户注册奖励记录
 * @author: sty
 * @date: 2019/7/18 10:12 AM
 */
@Data
public class UserRegRewardRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键
    private Integer id;
    private String username;
    private Integer creditAmount;
    private int status;
    private Date createTime;
    private Date updateTime;
}
