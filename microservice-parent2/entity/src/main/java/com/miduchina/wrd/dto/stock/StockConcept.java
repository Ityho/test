package com.miduchina.wrd.dto.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 股票概念表
 * @author yff
 *
 */
@Data
public class StockConcept implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer stockConceptId;	//主键
    private String stockConcept;	//股票概念
    private Date createTime;		//入库时间
    private Date updateTime;		//更新时间
    private String stockCode;		//股票代码
}