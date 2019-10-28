package com.miduchina.wrd.po.keyword;

import lombok.Data;

import java.util.Date;
@Data
public class KeyWordLog {

	private Integer id;
	private int userId;
	private Date createTime;
	private Date updateTime;
	private String keyWord;
	private Integer total;
	private int status;
	private int type;
	

}
