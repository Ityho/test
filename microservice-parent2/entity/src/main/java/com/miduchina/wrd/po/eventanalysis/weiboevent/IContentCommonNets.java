package com.miduchina.wrd.po.eventanalysis.weiboevent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "iContentCommonNets")
public class IContentCommonNets {

	private String name;// 统计名称
	private int type = 1;// 统计类型
	private List<IContentCommonNet> iContentCommonNetList = new ArrayList<IContentCommonNet>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@XmlElementWrapper(name = "iContentCommonNetList")
	@XmlElement(name = "iContentCommonNet")
	public List<IContentCommonNet> getiContentCommonNetList() {
		return iContentCommonNetList;
	}

	public void setiContentCommonNetList(List<IContentCommonNet> iContentCommonNetList) {
		this.iContentCommonNetList = iContentCommonNetList;
	}

}
