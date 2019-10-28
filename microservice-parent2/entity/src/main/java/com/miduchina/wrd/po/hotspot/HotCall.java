package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.util.Date;
@Data
public class HotCall implements java.io.Serializable {
    private Integer id;
    private Integer type;
    private String userIp;
    private Integer userId;
    private Integer date;
    private String startTime;
    private String endTime;
    private String keyword;
    private String filterKeyword;
    private Date createTime;
    private Integer status;
    private Integer plat;
    

    
}
