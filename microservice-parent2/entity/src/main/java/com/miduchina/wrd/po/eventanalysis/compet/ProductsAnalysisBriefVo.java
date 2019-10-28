package com.miduchina.wrd.po.eventanalysis.compet;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProductsAnalysisBriefVo  {

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
    private String shareCode;
    private String hrefUrl;
    private String ticket;
}
