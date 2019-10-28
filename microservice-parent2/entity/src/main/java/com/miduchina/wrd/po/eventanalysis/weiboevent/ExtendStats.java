package com.miduchina.wrd.po.eventanalysis.weiboevent;


import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ExtendStats implements Serializable{

	private static final long serialVersionUID = 1L;
	private Date analysisEndTime;
	private Date analysisStartTime;
	private String filterKeyword;
	private String keyword;
	private Date topTime;
	private long totalCount;
	private int maxPosition = 0;// 最大位置
	private String name;// 统计名称
	private int type = 1;// 统计类型
	private List<IContentCommonNets> iContentCommonNetLists = new ArrayList<IContentCommonNets>();
	private List<IContentCommonNet> iContentCommonNetList = new ArrayList<IContentCommonNet>();
	private List<Stats> StatsList = new ArrayList<Stats>();

	@XmlElementWrapper(name = "iContentCommonNetsList")
	@XmlElement(name = "iContentCommonNets")
	public List<IContentCommonNets> getiContentCommonNetLists() {
		return iContentCommonNetLists;
	}

	public void setiContentCommonNetLists(List<IContentCommonNets> iContentCommonNetLists) {
		this.iContentCommonNetLists = iContentCommonNetLists;
	}

	public List<IContentCommonNet> getiContentCommonNetList() {
		return iContentCommonNetList;
	}

	public void setiContentCommonNetList(List<IContentCommonNet> iContentCommonNetList) {
		this.iContentCommonNetList = iContentCommonNetList;
	}

	@XmlElementWrapper(name = "statsList")
	@XmlElement(name = "stats")
	public List<Stats> getStatsList() {
		return StatsList;
	}

	public void setStatsList(List<Stats> statsList) {
		StatsList = statsList;
	}

}

