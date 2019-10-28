package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.util.Date;

/**
 *
 * 【全局消息实体类】
 *
 * @version 1.0
 * @since 2017年2月28日 下午5:43:58
 * @author virgo
 */
@Data
public class Message {
    private int messageId;
    private int userId;
    private int messageType;
    private String messageUrl;
    private String messageDesc;
    private int status;
    private Date createTime;
    private Date updateTime;



}
