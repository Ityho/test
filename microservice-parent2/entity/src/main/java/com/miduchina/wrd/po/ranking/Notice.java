package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/26 0026.
 */
@Data
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer noticeId;//
    private int noticeAdminId;//管理员ID
    private String noticeTitle;//公告标题
    private String noticeContent;//公告内容
    private int noticeType;//公告类型  1-普通公告  2-紧急公告  默认1
    private int noticeIsTiming;//是否定时  0-否  1-是  默认0
    private Date noticeTiming;//定时时间
    private Date noticeSendTime;//发送时间
    private int status;//状态  0-已删除  1-已发送  2-未发送  3-已撤回  默认状态1
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private String isRead;//用户是否读过


}
