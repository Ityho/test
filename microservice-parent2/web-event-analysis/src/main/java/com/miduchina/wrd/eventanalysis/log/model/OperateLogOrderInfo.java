package com.miduchina.wrd.eventanalysis.log.model;

import java.util.List;

import lombok.Data;

@Data
public class OperateLogOrderInfo {
	private Double totalFee;
	private Integer orderType;
	private String orderNo;
	private Integer virtual;
	private String innerTradeNo;
	private String outsideTradeNo;
	private String payChannel;
	private List<OperateLogProductPackageInfo> packages;
}
