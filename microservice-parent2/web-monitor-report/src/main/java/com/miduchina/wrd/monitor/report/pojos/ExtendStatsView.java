package com.miduchina.wrd.monitor.report.pojos;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class ExtendStatsView implements Serializable {
	
	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private ExtendStats extendStats;

	public ExtendStatsView() {
		super();
	}

	public ExtendStatsView(String code, ExtendStats extendStats) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.extendStats = extendStats;
	}

	public ExtendStatsView(String code, String message, ExtendStats extendStats) {
		super();
		this.code = code;
		this.message = message;
		this.extendStats = extendStats;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ExtendStats getExtendStats() {
		return extendStats;
	}

	public void setExtendStats(ExtendStats extendStats) {
		this.extendStats = extendStats;
	}

}
