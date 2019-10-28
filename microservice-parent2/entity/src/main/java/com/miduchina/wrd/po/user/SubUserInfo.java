package com.miduchina.wrd.po.user;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: SubUserInfo
 * @Description: 子账号
 * @author: sty
 * @date: 2019/7/18 10:41 AM
 */
@Data
public class SubUserInfo {
    private Integer subUserId;
    private Integer parentUserId;
    private String username;
    private String password;
    private Date validEndDate;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
