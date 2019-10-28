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
 * @ClassName: HotPersonDto
 * @Description: 热门人物
 * @author: 许双龙
 * @date: 2018年11月6日 下午1:57:33  
 */
@Data
public class HotPersonDto {
	@ApiModelProperty(value = "编号")
	private Integer id;
	@ApiModelProperty(value = "名称")
	private String name;
	@ApiModelProperty(value = "概述")
	private String summary;
	@ApiModelProperty(value = "一级标签名称")
	private String labelName;
	@ApiModelProperty(value = "二级标签名称")
	private String label1Name;
	@ApiModelProperty(value = "热门事件个数")
	private String eventNum;
	@ApiModelProperty(value = "（本日与上日）提及数据量同比")
	private double ratioDay;
	@ApiModelProperty(value = "（本日）热度值")
	private double ratioHotDay;
	@ApiModelProperty(value = "（上日）热度值")
	private double ratioHotLastDay;
	@ApiModelProperty(value = "（本次自定义时间）热度高峰值")
	private double ratioHotTopCustom;
	@ApiModelProperty(value = "（本日与上日）提及数据量增量")
	private double differenceDay;
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
	@ApiModelProperty(value = "1-显示,0-隐藏")
	private Integer showTag;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "热门事件集合")
	private List<HotIncidentDto> hotIncidentList;
	@ApiModelProperty(value = "热度走势")
	private String lineData;
	@ApiModelProperty(value = "敏感信息走势")
	private String mgLineData;
	@ApiModelProperty(value = "非敏感信息走势")
	private String fmgLineData;
	@ApiModelProperty(value = "显著情绪")
	private String emotion;
	@ApiModelProperty(value = "用户头像")
	private String logoUrl;
}
