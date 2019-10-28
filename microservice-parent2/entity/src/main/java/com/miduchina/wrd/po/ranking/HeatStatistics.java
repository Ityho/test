package com.miduchina.wrd.po.ranking;

import lombok.Data;

import java.util.Date;

/**
 * @version v1.0.0
 * @ClassName: HeatStatistics
 * @Description: TODO
 * @author: sty
 * @date: 2019/8/2 1:27 PM
 */
@Data
public class HeatStatistics {
    private String analysisTaskTicket;
    private Integer categoryId;
    private String channelTag;
    private Date createTime = new Date();
    private Integer dataAmount;
    private Double heatIndexChange;
    private Double heatValue;
    private int id;
    private String name;
    private String platformTag;
    private Integer rank;
    private Integer rankDifference;
    private int status;
    private Date updateTime;
    private String userTag;
}
