package com.miduchina.wrd.eventanalysis.log.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Warning implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private int userId;
	private String warningName;// 方案名称
	private int warningContentType;//全部0；负面2，疑似负面1；负面和疑似负面3
	private int type;// 预警类型,1-为单个关键词预警, 2 - 多个关键词预警

	private Set warningKeywords = new LinkedHashSet(0);
	private Set warningContacts = new LinkedHashSet(0);

	private String startTime;
	private String endTime;
	private int intervalTime;

	private int smsSwitch;
	private int appSwitch;
	private int webSwitch;
	private int emailSwitch;
	private int weiboSwitch;

	private int sameContentMergeSwitch;//合并
	private int weekendSendSwitch;

	private Date createTime;
	private Date updateTime;
	private Date validEndDate;
	private int status;
	private int isValid;
	private int warningIntervalTime;//预警时间间隔(分钟)',(为0时表示实时预警)
	private String keyword;//预警关键词
	private String sourceCondition;//来源类型，多选
	private String province;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getWarningName() {
		return warningName;
	}
	public void setWarningName(String warningName) {
		this.warningName = warningName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Set getWarningKeywords() {
		return warningKeywords;
	}
	public void setWarningKeywords(Set warningKeywords) {
		this.warningKeywords = warningKeywords;
	}
	public Set getWarningContacts() {
		return warningContacts;
	}
	public void setWarningContacts(Set warningContacts) {
		this.warningContacts = warningContacts;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}
	public int getWarningContentType() {
		return warningContentType;
	}

	public void setWarningContentType(int warningContentType) {
		this.warningContentType = warningContentType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSmsSwitch() {
		return smsSwitch;
	}
	public void setSmsSwitch(int smsSwitch) {
		this.smsSwitch = smsSwitch;
	}
	/**
	 * @return the warningIntervalTime
	 */
	public int getWarningIntervalTime() {
		return warningIntervalTime;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @return the sourceCondition
	 */
	public String getSourceCondition() {
		return sourceCondition;
	}
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param warningIntervalTime the warningIntervalTime to set
	 */
	public void setWarningIntervalTime(int warningIntervalTime) {
		this.warningIntervalTime = warningIntervalTime;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	/**
	 * @param sourceCondition the sourceCondition to set
	 */
	public void setSourceCondition(String sourceCondition) {
		this.sourceCondition = sourceCondition;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	public int getSameContentMergeSwitch() {
		return sameContentMergeSwitch;
	}
	public void setSameContentMergeSwitch(int sameContentMergeSwitch) {
		this.sameContentMergeSwitch = sameContentMergeSwitch;
	}
	public int getWeekendSendSwitch() {
		return weekendSendSwitch;
	}
	public void setWeekendSendSwitch(int weekendSendSwitch) {
		this.weekendSendSwitch = weekendSendSwitch;
	}
	public int getAppSwitch() {
		return appSwitch;
	}
	public void setAppSwitch(int appSwitch) {
		this.appSwitch = appSwitch;
	}
	public int getWebSwitch() {
		return webSwitch;
	}
	public void setWebSwitch(int webSwitch) {
		this.webSwitch = webSwitch;
	}
	public int getEmailSwitch() {
		return emailSwitch;
	}
	public void setEmailSwitch(int emailSwitch) {
		this.emailSwitch = emailSwitch;
	}
	public int getIsValid() {
		return isValid;
	}
	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}
	public int getWeiboSwitch() {
		return weiboSwitch;
	}
	public void setWeiboSwitch(int weiboSwitch) {
		this.weiboSwitch = weiboSwitch;
	}

	public String toPrintString(){
		return "预警id:"+this.id+";预警名称:"+this.warningName+";开始时间:"+this.startTime+";结束时间:"
				+this.endTime+";间隔时间:"+this.intervalTime+";周末是否推送:"+(this.weekendSendSwitch==0?"不推送":"推送")
				+";预警状态："+(this.emailSwitch==1?"开":"关")
				+";预警内容属性："+(this.warningContentType==3?"敏感":"全部");
	}

	public Date getValidEndDate() {
		return validEndDate;
	}
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}
}