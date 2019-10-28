package com.miduchina.wrd.po.order;

import lombok.Data;

import java.util.Date;

@SuppressWarnings("serial")
@Data
public class CartRecord implements java.io.Serializable {

	private Integer cartRecordId;
	private Integer userId;
	private Integer productPackageId;
	private Integer productPackageCount;
	private Integer showSwitch;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Float price;
	private String productPackageName;
	
	

	
	
	
}
