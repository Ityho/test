package com.miduchina.wrd.po.keyword;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class WarningTimerSearchResult implements Serializable{
	
	private static final long serialVersionUID = -2667598739184584818L;
	
	private int warningTimerSearchResultId;	//主键
	private int userId;//用户ID
	private int warningId; //预警ID
	private int keywordId; //关键词
	private String warningName;	//预警名称
	private String keywordName;  //关键词名称
	private int increaseCount;  //新增条数
	private int sensitiveCount; //敏感数
	private String warningSearchConditionJson;  //预警条件
	private String paramsJson;  //搜索条件
	private String hbaseIds;  //预警条件
	private Date indexStartTime;  //开始时间
	private Date indexEndTime;  //结束时间
	private String warningReviewCode;  //查看码
	private Date createTime;  //新建时间
	private Integer isRead;	//是否已读 0-未读，1-已读
}