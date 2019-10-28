package com.miduchina.wrd.po.eventanalysis.compet;

import java.util.Date;

/**
 * 
 */
public class ProductAnalysis {
	private int analysisStatus;
	private String areaDesc;
	private String code;
	private Date createTime;
	private Date endTime;
	private Integer id;
	private String keywords;
	private String monitoringDesc;//filepath
	private String negativeDesc;
	private Double percent;
	private String platform;
	private String schedule;
	private String sensitiveDesc;
	private String sourceMediaDesc;
	private String sourceTypeDesc;
	private Date startTime;
	private int status;
	private String tendDesc;
	private String ticket;
	private String title;
	private Date updateTime;
	private int userId;
	private String wordDesc;
	private int productAnalysisId;
	
	private String hrefUrl;
	private String filePath;
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

	public int getProductAnalysisId() {
		return productAnalysisId;
	}

	public void setProductAnalysisId(int productAnalysisId) {
		this.productAnalysisId = productAnalysisId;
	}

	public int getAnalysisStatus() {
		return analysisStatus;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public String getCode() {
		return code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Integer getId() {
		return id;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getMonitoringDesc() {
		return monitoringDesc;
	}

	public String getNegativeDesc() {
		return negativeDesc;
	}

	public Double getPercent() {
		return percent;
	}

	public String getPlatform() {
		return platform;
	}

	public String getSchedule() {
		return schedule;
	}

	public String getSensitiveDesc() {
		return sensitiveDesc;
	}

	public String getSourceMediaDesc() {
		return sourceMediaDesc;
	}

	public String getSourceTypeDesc() {
		return sourceTypeDesc;
	}

	public Date getStartTime() {
		return startTime;
	}

	public int getStatus() {
		return status;
	}

	public String getTendDesc() {
		return tendDesc;
	}

	public String getTicket() {
		return ticket;
	}

	public String getTitle() {
		return title;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public int getUserId() {
		return userId;
	}

	public String getWordDesc() {
		return wordDesc;
	}

	public void setAnalysisStatus(int analysisStatus) {
		this.analysisStatus = analysisStatus;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setMonitoringDesc(String monitoringDesc) {
		this.monitoringDesc = monitoringDesc;
	}

	public void setNegativeDesc(String negativeDesc) {
		this.negativeDesc = negativeDesc;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public void setSensitiveDesc(String sensitiveDesc) {
		this.sensitiveDesc = sensitiveDesc;
	}

	public void setSourceMediaDesc(String sourceMediaDesc) {
		this.sourceMediaDesc = sourceMediaDesc;
	}

	public void setSourceTypeDesc(String sourceTypeDesc) {
		this.sourceTypeDesc = sourceTypeDesc;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTendDesc(String tendDesc) {
		this.tendDesc = tendDesc;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setWordDesc(String wordDesc) {
		this.wordDesc = wordDesc;
	}
}
