package com.miduchina.wrd.dto.analysis;

import lombok.Data;

import java.util.Date;

/**
 * Created by shitao on 2019-05-30 17:14.
 *
 * @author shitao
 */
@Data
public class ProductsAnalysisShareDto {

    private Integer productsAnalysisShareId;
    private String shareCode;
    private int productsAnalysisBriefId;
    private int status;
    private Date createTime;
    private Date updateTime;

}
