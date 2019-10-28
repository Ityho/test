package com.miduchina.wrd.dto.analysis.weiboanalysis;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@Data
public class StatListView implements Serializable {

	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private StatList StatList;

	public StatListView() {
		super();
	}

	public StatListView(String code, StatList StatList) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.StatList = StatList;
	}

	public StatListView(String code, String message, StatList StatList) {
		super();
		this.code = code;
		this.message = message;
		this.StatList = StatList;
	}

}
