package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 罗朝州 on 2016/8/31.
 */
@Data
public class NotLoginOperateRecord implements Serializable {

    private int id;
    private String ip;
    private String userAgent;
    private int operateType;
    private String name;
    private String keyword;
    private String filterKeyword;
    private String jsonData;
    private int status=1;
    private Date createTime;
    private Date updateTime;

    private String categoryId;
    private String categoryType;
    private Integer num=0;
    private Date starttime;
    private Date endtime;

    private Integer options;
    private Integer matchType;
    private String origin;
    private String shareCode;


}
