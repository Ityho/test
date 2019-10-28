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
package com.miduchina.wrd.dto.ranking;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/** 
 * @ClassName: HotWordDto
 * @Description: 热词
 * @author: 许双龙
 * @date: 2018年11月8日 上午10:29:10  
 */
@Data
public class HotWordDto {
	@ApiModelProperty(value = "编号")
	private Integer id;
	@ApiModelProperty(value = "名称")
	private String name;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "标签集合")
	private String labels;
	@ApiModelProperty(value = "标签名称集合")
	private String labelNames;
	@ApiModelProperty(value = "一级标签名称")
	private String labelName;
	@ApiModelProperty(value = "二级标签名称")
	private String label1Name;
	@ApiModelProperty(value = "热度值")
	private Double ratioHotDay;
	@ApiModelProperty(value = "热门事件个数")
	private Integer eventNum;
	@ApiModelProperty(value = "全网信息量")
	private Long numberDay;
	@ApiModelProperty(value = "各事件标题")
	private String incidentTitle;
	@ApiModelProperty(value = "各事件编号")
	private List<Integer> hotIncidentIds;
}
