package com.miduchina.wrd.po.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: UserExclusiveChannel
 * @Description: 用户渠道信息
 * @author: sty
 * @date: 2019/7/18 10:41 AM
 */
@Data
public class UserExclusiveChannel implements Serializable {

    //主键
    private Integer uecId;
    //'账号类型：1-新浪微博  2-微信  3-QQ  4-手机号码
    private Integer userId;
    private String uecCode;
    private String uecDesc;
    private String uecQrcodeLogoPath;
    private Integer uecPersonal;
    private Integer uecChannel;
    private int status;
    private Date createTime;
    private Date updateTime;
}
