package com.miduchina.wrd.monitor.warning.operatelog.model;

import lombok.Data;

@Data
public class OperateLogUserInfo {
	private Integer userId;
	private String username;
	private String nickname;
	private String email;
	private String mobile;
	private String password;
	private String userHead;
	private String lastLoginTime;
	private Integer appUserStatus;
	private Integer userChannel;
	private Integer usreType;
	private String proUserValidEndTime;
	private String platform;
	private Integer userAnalysisValidCount;
	private Integer userBriefValidCount;
	private Integer userReportValidCount;
	private Integer userProductAnalysisValidCount;
	private Integer userSingleWeiboAnalysisValidCount;
	private Integer status;
	private String createTime;
	private String updateTime;
}
