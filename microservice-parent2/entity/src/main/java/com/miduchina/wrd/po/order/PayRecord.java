package com.miduchina.wrd.po.order;

import lombok.Data;

import java.util.Date;

@Data
public class PayRecord implements java.io.Serializable {
    private static final long serialVersionUID = 7537182682029802197L;

    private Integer payRecordId;
    private Integer userId;
    private String payName;
    private Double totalFee;
    private String payDescription;
    private Date createTime;
    private Date updateTime;
    private Integer payStatus;
    private String innerTradeNo;
    private String outerTradeNo;
}
