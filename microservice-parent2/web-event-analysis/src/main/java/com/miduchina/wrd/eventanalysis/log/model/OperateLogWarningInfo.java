package com.miduchina.wrd.eventanalysis.log.model;

public class OperateLogWarningInfo {
	private Integer warningId; // 预警ID
	private Integer userId; // 用户ID
	private String warningName; // 预警名称
	private String keyword; // 预警关键词
	private String warningStartTime; // 预警开始时间
	private String warningEndTime; // 预警结束时间
	private Integer warningIntervalTime; // 预警时间间隔(分钟)
	private Integer smsSwitch; // 短信开关
	private Integer appSwitch; // APP开关
	private Integer webSwitch; // WEB开关
	private Integer emailSwitch; // 邮箱开关
	private Integer weiboSwitch; // 私信开关
	private Integer weekendSendSwitch; // 周末是否推送
	private Integer sameContentMergeSwitch; // 相同内容合并
	private String sourceCondition; // 来源类型
	private Integer warningContentType; // 内容属性
	private String province; // 省份
	private Integer status; // 状态

	public Integer getWarningId() {
		return warningId;
	}

	public void setWarningId(Integer warningId) {
		this.warningId = warningId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWarningName() {
		return warningName;
	}

	public void setWarningName(String warningName) {
		this.warningName = warningName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getWarningStartTime() {
		return warningStartTime;
	}

	public void setWarningStartTime(String warningStartTime) {
		this.warningStartTime = warningStartTime;
	}

	public String getWarningEndTime() {
		return warningEndTime;
	}

	public void setWarningEndTime(String warningEndTime) {
		this.warningEndTime = warningEndTime;
	}

	public Integer getWarningIntervalTime() {
		return warningIntervalTime;
	}

	public void setWarningIntervalTime(Integer warningIntervalTime) {
		this.warningIntervalTime = warningIntervalTime;
	}

	public Integer getSmsSwitch() {
		return smsSwitch;
	}

	public void setSmsSwitch(Integer smsSwitch) {
		this.smsSwitch = smsSwitch;
	}

	public Integer getAppSwitch() {
		return appSwitch;
	}

	public void setAppSwitch(Integer appSwitch) {
		this.appSwitch = appSwitch;
	}

	public Integer getWebSwitch() {
		return webSwitch;
	}

	public void setWebSwitch(Integer webSwitch) {
		this.webSwitch = webSwitch;
	}

	public Integer getEmailSwitch() {
		return emailSwitch;
	}

	public void setEmailSwitch(Integer emailSwitch) {
		this.emailSwitch = emailSwitch;
	}

	public Integer getWeiboSwitch() {
		return weiboSwitch;
	}

	public void setWeiboSwitch(Integer weiboSwitch) {
		this.weiboSwitch = weiboSwitch;
	}

	public Integer getWeekendSendSwitch() {
		return weekendSendSwitch;
	}

	public void setWeekendSendSwitch(Integer weekendSendSwitch) {
		this.weekendSendSwitch = weekendSendSwitch;
	}

	public Integer getSameContentMergeSwitch() {
		return sameContentMergeSwitch;
	}

	public void setSameContentMergeSwitch(Integer sameContentMergeSwitch) {
		this.sameContentMergeSwitch = sameContentMergeSwitch;
	}

	public String getSourceCondition() {
		return sourceCondition;
	}

	public void setSourceCondition(String sourceCondition) {
		this.sourceCondition = sourceCondition;
	}

	public Integer getWarningContentType() {
		return warningContentType;
	}

	public void setWarningContentType(Integer warningContentType) {
		this.warningContentType = warningContentType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}