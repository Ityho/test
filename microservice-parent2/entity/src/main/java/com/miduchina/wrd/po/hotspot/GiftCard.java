package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.util.Date;
@Data
public class GiftCard {
	private Integer id;
	private Integer userId;
	private String cardNum;
	private Integer creditCount;
	private Integer creditValidDays;
	private Date validEndDate;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	
	private String validEndDateStr;
	private String count;
	

    
}
