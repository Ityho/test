package com.miduchina.wrd.po.user;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserInviteRelation
 * @Description: 邀请用户关联
 * @author: sty
 * @date: 2019/7/18 10:12 AM
 */
@Data
public class UserInviteRelation {
    private Integer id;
    private Integer userId;
    private Integer inviteUserId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
