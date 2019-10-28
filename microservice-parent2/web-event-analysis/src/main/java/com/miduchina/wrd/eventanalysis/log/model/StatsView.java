package com.miduchina.wrd.eventanalysis.log.model;

import com.miduchina.wrd.eventanalysis.utils.ErrorCodeConstant;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stats;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@Data
public class StatsView implements Serializable {
	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private long totalCount;
	private List<Stats> statsList;

	public StatsView() {
		super();
	}

	public StatsView(String code, long totalCount, List<Stats> statsList) {
		super();
		this.code = code;
		this.message = ErrorCodeConstant.getMsg(code);
		this.totalCount = totalCount;
		this.statsList = statsList;
	}

	public StatsView(String code, String message, long totalCount, List<Stats> statsList) {
		super();
		this.code = code;
		this.message = message;
		this.totalCount = totalCount;
		this.statsList = statsList;
	}
}
