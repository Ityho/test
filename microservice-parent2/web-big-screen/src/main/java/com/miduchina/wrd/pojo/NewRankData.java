package com.miduchina.wrd.pojo;


import lombok.Data;

import java.util.Date;

/**
 * @Classname NewRankData
 * @Description TODO
 * @Date 2019/7/9 11:53
 * @Author ZhuFangTao
 */
@Data
public class NewRankData {
    private String labelNames;
    private double ratioHotDay;
    private double ratioHotLastDay;
    private double ratioHotTopCustom;
    private Date createTime;
    private String province;
    private String incidentTitle;
    private String keyword;
    private String filterKeyword;
    private long fmgNumberDay;
    private long mediaNumberDay;
    private long zwNumberDay;
    private long bkNumberDay;
    private String mgLineData;
    private String fmgLineData;
    private String lineData;
    private Long mgNumberDay;
    private Integer areaType;
    private String originalUrl;
    private String lastLineData;
    private double ratioHotDayHour;
    private double ratioHotDifferenceDayHour;

}
