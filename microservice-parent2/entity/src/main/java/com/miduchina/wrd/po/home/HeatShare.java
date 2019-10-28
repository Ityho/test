package com.miduchina.wrd.po.home;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: shitao
 * @date: 2019.07.18
 */

@Data
public class HeatShare  {

    private Integer id;
    private String shareCode;
    private int status;
    private Date createTime;
    private Date updateTime;
    private int userId;
    private String title;
    private String categoryId;
    private String type;
    private String shareDate;
    private String platTag;
    private String keyword;
    private String filterKeyword;
    private Integer solidifyStatus;
    private int conditionId;
    private String analysisTaskTicket;
    private String startTime;
    private String endTime;
    private Integer num;
    private String no;

}

