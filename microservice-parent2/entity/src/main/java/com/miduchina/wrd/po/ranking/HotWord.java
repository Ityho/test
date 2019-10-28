/**   
 * Copyright © 2018 公司名. All rights reserved.
 * 
 * @Title: HotWord.java 
 * @Prject: ranking-list-api
 * @Package: com.xd.rankinglist.api.model 
 * @Description: TODO
 * @author: 许双龙   
 * @date: 2018年11月8日 上午10:29:10 
 * @version: V1.0   
 */
package com.miduchina.wrd.po.ranking;

import java.util.Date;
import java.util.List;

import lombok.Data;

/** 
 * @ClassName: HotWord 
 * @Description: TODO
 * @author: 许双龙
 * @date: 2018年11月8日 上午10:29:10  
 */
@Data
public class HotWord {
	private Integer id;
	private String name;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private String labels;
	private String labelNames;
	private String labelName;
	private String label1Name;
	private Double ratioHotDay;
	private Long numberDay;
	private Integer eventNum;
	private String incidentTitle;
	private List<Integer> hotIncidentIds;
}
