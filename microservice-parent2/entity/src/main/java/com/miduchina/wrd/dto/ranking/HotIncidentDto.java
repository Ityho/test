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
package com.miduchina.wrd.dto.ranking;

import com.miduchina.wrd.po.ranking.HotIncidentNews;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: HotIncidentDto
 * @Description: 热门事件
 * @author: 许双龙
 * @date: 2018年11月7日 下午9:00:17  
 */
@Data
public class HotIncidentDto {
	private Integer id;
	private String name;
	private Integer eventNum;

	@ApiModelProperty(value = "编号")
	private Integer hotIncidentId;
	@ApiModelProperty(value = "标题")
	private String incidentTitle;
	@ApiModelProperty(value = "长标题")
	private String longTitle;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "情绪")
	private String emotion;
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

	private Set<HotIncidentNews> hotIncidentNews = new HashSet<>();

	private List<HotIncidentDto> hotIncidentList;

	@ApiModelProperty(value = "排序编号")
	private int incidentSeq;

	@ApiModelProperty(value = "标签集合")
	private String labels;
	@ApiModelProperty(value = "标签名称集合")
	private String labelNames;
	@ApiModelProperty(value = "一级标签")
	private Integer label;
	@ApiModelProperty(value = "一级标签名称")
	private String labelName;
	@ApiModelProperty(value = "二级标签")
	private Integer label1;
	@ApiModelProperty(value = "二级标签名称")
	private String label1Name;

	@ApiModelProperty(value = "全网信息量")
	private long numberDay;
	@ApiModelProperty(value = "微博信息量")
	private long wbNumberDay;
	@ApiModelProperty(value = "微信信息量")
	private long wxNumberDay;
	@ApiModelProperty(value = "政务信息量")
	private long zwNumberDay;
	@ApiModelProperty(value = "报刊信息量")
	private long bkNumberDay;
	@ApiModelProperty(value = "敏感信息量")
	private long mgNumberDay;
	@ApiModelProperty(value = "非敏感信息量")
	private long fmgNumberDay;
	@ApiModelProperty(value = "媒体信息量")
	private long mediaNumberDay;
	@ApiModelProperty(value = "客户端信息量")
	private long clientNumberDay;
	@ApiModelProperty(value = "（本日与上日）提及数据量增量")
	private double differenceDay;
	@ApiModelProperty(value = "来源")
	private String origin;
	@ApiModelProperty(value = "来源链接")
	private String originalUrl;
	@ApiModelProperty(value = "热度详情链接")
	private String hotDetailsUrl;
	@ApiModelProperty(value = "1-国内,2-国外")
	private Integer areaType;
	@ApiModelProperty(value = "省份")
	private String province;
	@ApiModelProperty(value = "城市")
	private String city;
	@ApiModelProperty(value = "（本日）热词排名")
	private int rank;
	@ApiModelProperty(value = "（上日与本日）热词排名变化")
	private int rankDifference;
	@ApiModelProperty(value = "（上日）排名")
	private int rankLast;
	@ApiModelProperty(value = "（本日与上日）提及数据量同比")
	private double ratioDay;
	@ApiModelProperty(value = "（本日）热度值")
	private double ratioHotDay;
	@ApiModelProperty(value = "（上日）热度值")
	private double ratioHotLastDay;
	@ApiModelProperty(value = "（本次自定义时间）热度高峰值")
	private double ratioHotTopCustom;
	@ApiModelProperty(value = "1-显示,0-隐藏")
	private Integer showTag;
	@ApiModelProperty(value = "热门现象编号")
	private Integer hotPhenomenoneId;
	@ApiModelProperty(value = "热门现象标题")
	private String hotPhenomenoneName;
	@ApiModelProperty(value = "热词编号")
	private Integer hotWordId;
	@ApiModelProperty(value = "热词名称")
	private String hotWordName;
	@ApiModelProperty(value = "重大事件编号")
	private Integer importantEventId;
	@ApiModelProperty(value = "重大事件名称")
	private String importantEventName;
	@ApiModelProperty(value = "热门新闻人物编号")
	private String hotNewsPersonIds;
	@ApiModelProperty(value = "热门事件人物编号")
	private String hotEventPersonIds;
	@ApiModelProperty(value = "热度走势")
	private String lineData;
	@ApiModelProperty(value = "纬度")
	private String lat;
	@ApiModelProperty(value = "经度")
	private String lng;
	@ApiModelProperty(value = "敏感走势")
	private String mgLineData;
	@ApiModelProperty(value = "非敏感走势")
	private String fmgLineData;
	@ApiModelProperty(value = "详情链接")
	private String HotDetailsUrl;
	@ApiModelProperty(value = "一小时热度")
	private double ratioHotDayHour;
	@ApiModelProperty(value = "一小时环比")
	private double ratioHotDifferenceDayHour;
	@ApiModelProperty(value = "前一天24小时热度走势")
	private String lastLineData;
}
