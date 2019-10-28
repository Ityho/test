package com.miduchina.wrd.po.order;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class H5Activity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer adminId;
	private String title;
	private String link;
	private String image;
	private String bootImage;
	private Date createTime;
	private Date updateTime;
	private int status;
	private Date startTime;
	private Date endTime;
	
	private Integer activityPackageId;
	private Integer activityOnce;
}