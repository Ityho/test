package com.miduchina.wrd.monitor.warning.model;

import lombok.Data;

/**
 * 预警查询条件
 * @author yff
 *
 */
@Data
public class WarningSearchCondition {
	private int warningId;	//预警ID
	private String warningName; //预警名称
	private int userId;	//用户ID
	private int keywordId;	//关键词ID
	private String keywordName;	//关键词名称
	private String keyword;		//关键词
	private String filterKeyword;	//过滤关键词
	private String rangWebsiteCodes;	//
	private String filterRangWebsiteCodes;
	private String publishStartTime;	//发布开始时间
	private String publishEndTime;	//发布结束时间
	private String indexStartTime;
	private String indexEndTime;
	private int sameContentMergeSwitch; // 1-相同文章-合并，2-相同文章-不合并
	private int warningContentTendency; // 0-内容属性-全部内容，3-内容属性-敏感，4-内容属性-非敏感
	private String contentId;		//默认内容ID
	private String newsTitle;		//标题
	private String highLightWords;	//高亮词
	private Integer repeatNum;	//
    private String province;//省份
    private String sourceCondition;//来源
}
