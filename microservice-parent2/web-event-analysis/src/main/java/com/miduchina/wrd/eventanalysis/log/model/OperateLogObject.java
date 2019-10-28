package com.miduchina.wrd.eventanalysis.log.model;

public class OperateLogObject {
	private Integer logType;
	private Integer product;
	private Object user;
	private String reqTime;
	private String reqIp;
	private Integer productChannel;
	private Integer productSection;
	private Integer productPageCode;
	private String productPageDesc;
	private Integer operateType;
	private Object operateBeforeObj;
	private Object operateAfterObj;
	private String reqUA;
	private String mobilePlatform;
	private String mobileVersion;
	private String logExtraInfo;
	private Integer channelTag;

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public Integer getProduct() {
		return product;
	}

	public void setProduct(Integer product) {
		this.product = product;
	}

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public Integer getProductChannel() {
		return productChannel;
	}

	public void setProductChannel(Integer productChannel) {
		this.productChannel = productChannel;
	}

	public Integer getProductSection() {
		return productSection;
	}

	public void setProductSection(Integer productSection) {
		this.productSection = productSection;
	}

	public Integer getProductPageCode() {
		return productPageCode;
	}

	public void setProductPageCode(Integer productPageCode) {
		this.productPageCode = productPageCode;
	}

	public String getProductPageDesc() {
		return productPageDesc;
	}

	public void setProductPageDesc(String productPageDesc) {
		this.productPageDesc = productPageDesc;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Object getOperateBeforeObj() {
		return operateBeforeObj;
	}

	public void setOperateBeforeObj(Object operateBeforeObj) {
		this.operateBeforeObj = operateBeforeObj;
	}

	public Object getOperateAfterObj() {
		return operateAfterObj;
	}

	public void setOperateAfterObj(Object operateAfterObj) {
		this.operateAfterObj = operateAfterObj;
	}

	public String getReqUA() {
		return reqUA;
	}

	public void setReqUA(String reqUA) {
		this.reqUA = reqUA;
	}

	public String getMobilePlatform() {
		return mobilePlatform;
	}

	public void setMobilePlatform(String mobilePlatform) {
		this.mobilePlatform = mobilePlatform;
	}

	public String getMobileVersion() {
		return mobileVersion;
	}

	public void setMobileVersion(String mobileVersion) {
		this.mobileVersion = mobileVersion;
	}

	public String getLogExtraInfo() {
		return logExtraInfo;
	}

	public void setLogExtraInfo(String logExtraInfo) {
		this.logExtraInfo = logExtraInfo;
	}

	public Integer getChannelTag() {
		return channelTag;
	}

	public void setChannelTag(Integer channelTag) {
		this.channelTag = channelTag;
	}
}
