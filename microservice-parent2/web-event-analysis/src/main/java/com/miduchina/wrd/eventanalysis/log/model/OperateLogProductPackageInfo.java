package com.miduchina.wrd.eventanalysis.log.model;

public class OperateLogProductPackageInfo {
	private Integer productPackageId;
	private String packageName;
	private Float packagePrice;
	private Integer packageCount;

	public Integer getProductPackageId() {
		return productPackageId;
	}

	public void setProductPackageId(Integer productPackageId) {
		this.productPackageId = productPackageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Float getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(Float packagePrice) {
		this.packagePrice = packagePrice;
	}

	public Integer getPackageCount() {
		return packageCount;
	}

	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}
}
