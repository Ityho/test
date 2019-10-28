/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotIncident.java 
 * @Prject: weiyuqing_operation_admin
 * @Package: com.xd.iis.woa.ranklist.pojo 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年11月7日 下午9:00:17 
 * @version: V1.0   
 */
package com.miduchina.wrd.po.ranking;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;
/** 
 * @ClassName: HotIncident 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年11月7日 下午9:00:17  
 */
@Data
public class HotIncident {
	
	private Integer hotIncidentId;
	private String incidentTitle;
	private String longTitle;
	private String origin;
	private String originalUrl;
	private Integer areaType;
	private String province;
	private String city;
	private int incidentSeq;

	private String keyword;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String filterKeyword;

	private String labels;
	private Integer label;
	private String labelName;
	private Integer label1;
	private String label1Name;

	private long mediaNumberDay;
	private long clientNumberDay;
	private long wbNumberDay;
	private long wxNumberDay;
	private long zwNumberDay;
	private long bkNumberDay;
	private long mgNumberDay;
	private long fmgNumberDay;
	private long numberCustom;
	private long numberDay;

	private int rank;
	private int rankDifference;
	private int rankLast;
	private double ratioCustom;
	private double ratioDay;
	private double ratioHotCustom;
	private double ratioHotDay;
	private double ratioHotLastCustom;
	private double ratioHotTopCustom;
	private double ratioHotLastDay;
	private double differenceCustom;
	private double differenceDay;

	private Integer showTag;
	private int statStatus;
	private int status;

	private Date createTime;
	private Date updateTime;
	private String emotion;

	private Integer hotPhenomenoneId;
	private String hotPhenomenoneName;
	private Integer hotWordId;
	private String hotWordName;
	private Integer importantEventId;
	private String importantEventName;
	private String hotNewsPersonIds;
	private String hotEventPersonIds;
	private String lineData;
	private String mgLineData;
	private String fmgLineData;
	private String lastLineData;
	private double ratioHotDayHour;
	private double ratioHotDifferenceDayHour;
	private Set<HotIncidentNews> hotIncidentNews = new HashSet<>();
}
