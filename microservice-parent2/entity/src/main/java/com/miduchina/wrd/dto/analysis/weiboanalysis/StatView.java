package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@Data
public class StatView implements Serializable {
	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private long totalCount;
	private List<Stat> statList;

	public StatView() {
		super();
	}

	public StatView(String code, long totalCount, List<Stat> statList) {
		super();
		this.code = code;
		this.message = ErrorCodeConstant.getMsg(code);
		this.totalCount = totalCount;
		this.statList = statList;
	}

	public StatView(String code, String message, long totalCount, List<Stat> statList) {
		super();
		this.code = code;
		this.message = message;
		this.totalCount = totalCount;
		this.statList = statList;
	}
}
