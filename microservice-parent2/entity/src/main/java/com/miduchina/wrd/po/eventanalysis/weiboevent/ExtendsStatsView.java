package com.miduchina.wrd.po.eventanalysis.weiboevent;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExtendsStatsView implements Serializable {

	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private ExtendsStats extendsStats;

	public ExtendsStatsView() {
		super();
	}

	public ExtendsStatsView(String code, ExtendsStats extendsStats) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.extendsStats = extendsStats;
	}

	public ExtendsStatsView(String code, String message, ExtendsStats extendsStats) {
		super();
		this.code = code;
		this.message = message;
		this.extendsStats = extendsStats;
	}
}
