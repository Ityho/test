package com.miduchina.wrd.dto.home;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
public class HeatReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private int userId;
    private String reportName;
    private String keyword;
    private String filterKeyword;
    private int reportSwitch;
    private int emailSwitch;
    private int appSwitch;
    private int weiboSwitch;
    private String loadFlag;
    private Date lastProcTime;
    private int status;
    private Date createTime;
    private Date updateTime;
    private Date validEndDate;
    private String jsonData;

    private Set reportContacts = new LinkedHashSet(0);
}

