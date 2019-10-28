package com.miduchina.wrd.po.eventanalysis.weiboevent;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "iContentCommonNets")
public class IContentCommonNetList {

	private IContentCommonNet iContentCommonNet = new IContentCommonNet();
	private List<IContentCommonNetList> iContentCommonNetList = new ArrayList<IContentCommonNetList>();

	public IContentCommonNet getiContentCommonNet() {
		return iContentCommonNet;
	}

	public void setiContentCommonNet(IContentCommonNet iContentCommonNet) {
		this.iContentCommonNet = iContentCommonNet;
	}

	@XmlElementWrapper(name = "iContentCommonNetLists")
	@XmlElement(name = "iContentCommonNetList")
	public List<IContentCommonNetList> getiContentCommonNetList() {
		return iContentCommonNetList;
	}

	public void setiContentCommonNetList(List<IContentCommonNetList> iContentCommonNetList) {
		this.iContentCommonNetList = iContentCommonNetList;
	}

}
