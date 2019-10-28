package com.miduchina.wrd.dto.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户渠道关系表
 *
 * @author liym
 */
@Data
public class UserExclusiveChannelRelation implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer uecrId;
    private Integer userId;
    private Integer uecId;
    private int status;
    private Date createTime;
    private Date updateTime;
}

