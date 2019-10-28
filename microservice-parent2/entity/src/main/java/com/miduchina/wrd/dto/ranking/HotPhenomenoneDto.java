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
package com.miduchina.wrd.dto.ranking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** 
 * @ClassName: HotPhenomenoneDto
 * @Description: 热门现象
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33  
 */
@Data
public class HotPhenomenoneDto {
	@ApiModelProperty(value = "编号")
	private Integer id;
	@ApiModelProperty(value = "名称")
	private String name;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "一级标签名称")
	private String labelName;
	@ApiModelProperty(value = "二级标签名称")
	private String label1Name;
	@ApiModelProperty(value = "热门事件个数")
	private String eventNum;
	@ApiModelProperty(value = "全网信息量")
	private Long numberDay;
	@ApiModelProperty(value = "各事件标题")
	private String incidentTitles;
	@ApiModelProperty(value = "各事件编号")
	private String hotIncidentIds;
	@ApiModelProperty(value = "标签集合")
	private String labels;
	@ApiModelProperty(value = "标签名称集合")
	private String labelNames;
	@ApiModelProperty(value = "关键词")
	private String keyword;
	@ApiModelProperty(value = "关键词1")
	private String keyword1;
	@ApiModelProperty(value = "关键词2")
	private String keyword2;
	@ApiModelProperty(value = "关键词3")
	private String keyword3;
	@ApiModelProperty(value = "关键词4")
	private String keyword4;
	@ApiModelProperty(value = "过滤词")
	private String filterKeyword;
	@ApiModelProperty(value = "概述")
	private String summary;
	@ApiModelProperty(value = "1-显示,0-隐藏")
	private Integer showTag;
}
