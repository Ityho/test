package com.miduchina.wrd.monitor.report.pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlRootElement(name = "eventProfile")
public class ExtendStats {

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
	private List<Stats> StatsList = new ArrayList<Stats>();

	public Date getAnalysisEndTime() {
		return analysisEndTime;
	}

	public void setAnalysisEndTime(Date analysisEndTime) {
		this.analysisEndTime = analysisEndTime;
	}

	public Date getAnalysisStartTime() {
		return analysisStartTime;
	}

	public void setAnalysisStartTime(Date analysisStartTime) {
		this.analysisStartTime = analysisStartTime;
	}

	public String getFilterKeyword() {
		return filterKeyword;
	}

	public void setFilterKeyword(String filterKeyword) {
		this.filterKeyword = filterKeyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getTopTime() {
		return topTime;
	}

	public void setTopTime(Date topTime) {
		this.topTime = topTime;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

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

	@XmlElementWrapper(name = "iContentCommonNetsList")
	@XmlElement(name = "iContentCommonNets")
	public List<IContentCommonNets> getiContentCommonNetLists() {
		return iContentCommonNetLists;
	}

	public void setiContentCommonNetLists(List<IContentCommonNets> iContentCommonNetLists) {
		this.iContentCommonNetLists = iContentCommonNetLists;
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
