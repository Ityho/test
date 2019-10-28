package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import com.miduchina.wrd.po.eventanalysis.weiboevent.ExtendStats;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExtendStatsView implements Serializable {
	
	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private ExtendStats extendStats;
	private List<IContentCommonNet> iContentCommonNetList;

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

}
