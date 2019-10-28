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

import lombok.Data;

import java.util.Date;

/** 
 * @ClassName: ImportantEventRelation
 * @Description: 重大事件标签关联表
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33  
 */
@Data
public class ImportantEventRelation {
	private Long id;
	private Long importantEventId;
	private Long hotIncidentId;
	private Integer status;
	private Date createTime;
	private Date updateTime;
}
