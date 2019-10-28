package com.miduchina.wrd.dto.ip;

import java.io.Serializable;

public class OriginalRequestDto implements Serializable {
	private static final long serialVersionUID = 1044708264027817231L;
	private String ip; // 请求IP
	private String ua; // 请求UA
	private String referer; // 请求referer

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}
