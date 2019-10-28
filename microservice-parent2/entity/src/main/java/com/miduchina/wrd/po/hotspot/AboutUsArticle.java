package com.miduchina.wrd.po.hotspot;

import lombok.Data;

import java.util.Date;

@Data
public class AboutUsArticle implements java.io.Serializable {

	private Integer id;
	private Integer type;
	private String title;
	private String subTitle;
	private String abstracts;
	private String summary;
	private String content;
	private String articleUrl;
	private String webName;
	private String webLogoUrl;
	private String topImage;
	private String imgUrl;
	private String imgUrlTwo;
	private Integer status;
	private Date publishTime;
	private String publishTimeStr;
	private Date createTime;
	private Date updateTime;
	private Integer label;
	private Integer category;
	private Integer totalCount;
	private String numberPeriods;
	private String link;
	private String readNumber;
	// 事件类型
	private String eventLabel;
	//发布时间
	private String pubTime;
	private String platform;


}
