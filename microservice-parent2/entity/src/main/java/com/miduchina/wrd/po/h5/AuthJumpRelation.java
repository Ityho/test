package com.miduchina.wrd.po.h5;

import lombok.Data;

import java.util.Date;

@Data
public class AuthJumpRelation implements java.io.Serializable {

	private static final long serialVersionUID = -3286564461647015367L;
	private Integer jumpId;
	private Integer keywordId;
	private Integer userId;
	private Integer type;
	private int status;
	private Date createTime;
	private Date updateTime;
}
