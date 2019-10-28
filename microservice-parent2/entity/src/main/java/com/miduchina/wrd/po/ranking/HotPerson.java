/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: ImportantEvent.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.model 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年11月6日 下午1:57:33 
 * @version: V1.0   
 */
package com.miduchina.wrd.po.ranking;

import java.util.Date;
import java.util.List;

import lombok.Data;

/** 
 * @ClassName: HotPersonDto
 * @Description: 热门人物
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33  
 */
@Data
public class HotPerson {
	private Integer id;
	private String name;
	private String summary;
	private String labelName;
	private String hotIncidentIds;
	private String incidentTitles;
	private String keyword;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String filterKeyword;
	private Long numberDay;
	private double ratioDay;
	private double ratioHotDay;
	private double ratioHotLastDay;
	private double ratioHotTopCustom;
	private double differenceDay;
	private Double eventNum;
	private Integer showTag;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private List<HotIncident> hotIncidentList;
	private String mgLineData;
	private String fmgLineData;
	private String lineData;
	private String emotion;
	private String logoUrl;
}
