package com.miduchina.wrd.po.order;

import lombok.Data;

import java.util.Date;
@Data
public class Order implements java.io.Serializable {
	private static final long serialVersionUID = 1062929513116951473L;

	private String sellerEmail;
	private String outTradeNo;
	private String subject;
	private Float totalFee;
	private String showUrl;
	private String body;
	private Integer orderRecordId;
	private Integer userId;
	private Integer productPackageId;
	private Date cartTime;
	private Date orderTime;
	private Date payTime;
	private Integer payStatus;
	private String payDetail;
	private Integer createReceiptStatus;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String orderName;
	private String orderDescription;
	private Integer packageCount;
	private Integer orderType = 1;
	private String productName;
	private String keyword;
	private Date endTime;
	private Integer receiptRecordId;
	private String orderNo;
	private Integer payRecordId;
	private String payChannel;
	private Integer virtual;
	
	private boolean supportGoPay;


}
