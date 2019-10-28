package com.miduchina.wrd.po.analysis;

import lombok.Data;

import java.util.Date;

@Data
public class ProductsAnalysisShare {

    private Integer productsAnalysisShareId;
    private String shareCode;
    private int productsAnalysisBriefId;
    private int status;
    private Date createTime;
    private Date updateTime;
}
