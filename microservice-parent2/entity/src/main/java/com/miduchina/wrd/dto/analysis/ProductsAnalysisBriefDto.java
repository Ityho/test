package com.miduchina.wrd.dto.analysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:14.
 *
 * @author shitao
 */
@Data
public class ProductsAnalysisBriefDto {

    private Integer id;
    private int userId;
    private String title;
    private int status;
    private Date createTime;
    private Date updateTime;
    private String lineImg;
    private String gagueImg;
    private String mapImg;
    private String barImg;
    private String tendDesc;
    private String qgDesc;
    private String dyDesc;
    private String lyDesc;
    private String negativeDesc;
    private String negativeImg;
    private Date startTime;
    private Date endTime;
    private String filePath;
    private int productsAnalysisId;
    private String wcImg;
    private String hnImg;
    private String hnDescription;
    private String tackets;
    private String percentage;

    //附加字段
    private String ticket;
    private String createStr;
    private String startStr;
    private String endStr;
    private String sensitiveDesc;
    private String wordDesc;
    private String areaDesc;
    private String sourceTypeDesc;
    private String sourceMediaDesc;
    private String monitoringDesc;

}
