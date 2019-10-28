package com.miduchina.wrd.monitor.report.pojos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 统计类
 *
 * 作者： wangjun 日期：2016-02-20
 *
 */
@XmlRootElement(name = "stat")
public class Stat {

	private String name;// 统计名称(时间)
	private int num = 0;// 统计数量
	private double percent = 0;// 统计百分比
	private String percentStr;// 统计百分比
	private int type = 1;// 统计类型
	private double percentAverage = 0;// 均差比
	private double percentAverageMinus = 0;// 均差比差
	private IContentCommonNet iContentCommonNet = new IContentCommonNet();// 内容对象
	private List<IContentCommonNet> iContentCommonNetList = new ArrayList<IContentCommonNet>();// 内容列表

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public String getPercentStr() {
		return percentStr;
	}

	public void setPercentStr(String percentStr) {
		this.percentStr = percentStr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPercentAverage() {
		return percentAverage;
	}

	public void setPercentAverage(double percentAverage) {
		this.percentAverage = percentAverage;
	}

	public double getPercentAverageMinus() {
		return percentAverageMinus;
	}

	public void setPercentAverageMinus(double percentAverageMinus) {
		this.percentAverageMinus = percentAverageMinus;
	}

	public IContentCommonNet getiContentCommonNet() {
		return iContentCommonNet;
	}

	public void setiContentCommonNet(IContentCommonNet iContentCommonNet) {
		this.iContentCommonNet = iContentCommonNet;
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
