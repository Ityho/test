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
@XmlRootElement(name = "stats")
public class Stats {

	private boolean isUndulate = false;// 是否起伏
	private String name;// 统计名称
	private long num = 0;// 统计数量
	private List<Stat> StatList = new ArrayList<Stat>();// 统计列表
	private int type = 1;// 统计类型
	private int maxUndulatePosition = 0;// 最陡位置
	private int maxPosition = 0;// 最大位置

	public boolean getIsUndulate() {
		return isUndulate;
	}

	public void setIsUndulate(boolean isUndulate) {
		this.isUndulate = isUndulate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	@XmlElementWrapper(name = "statList")
	@XmlElement(name = "stat")
	public List<Stat> getStatList() {
		return StatList;
	}

	public void setStatList(List<Stat> statList) {
		StatList = statList;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMaxUndulatePosition() {
		return maxUndulatePosition;
	}

	public void setMaxUndulatePosition(int maxUndulatePosition) {
		this.maxUndulatePosition = maxUndulatePosition;
	}

	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

}
