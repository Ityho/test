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
 * @ClassName: ImportantEvent 
 * @Description: 重大事件
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33  
 */
@Data
public class ImportantEvent {
	private Integer id;
	private String name;
	private String hotIncidentIds;
	private String incidentTitles;
	private String labels;
	private String keyword;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String filterKeyword;
	private String summary;
	private Integer showTag;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String labelName;
	private String label1Name;
	private String areaType;
	private String province;
	private String city;
	private String eventNum;
	private Long numberDay;
	private String incidentTitle;
	private List<HotIncident> hotIncidentList;
}
