package com.miduchina.wrd.monitor.warning.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.xd.tools.pojo.IContentCommonNet;

@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class IContentCommonNetView implements Serializable {

	private static final long serialVersionUID = 1;
	private String code;
	private List<IContentCommonNet> iContentCommonNetList = new ArrayList<IContentCommonNet>();
	private long maxpage;
	private String message;
	private int page;
	private int pagesize;
	private long totalCount;

	public IContentCommonNetView() {
		super();
	}

	public IContentCommonNetView(String code, int page, int pagesize, long maxpage, long totalCount,
			List<IContentCommonNet> iContentCommonNetList) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.page = page;
		this.pagesize = pagesize;
		this.maxpage = maxpage;
		this.totalCount = totalCount;
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public IContentCommonNetView(String code, long totalCount, List<IContentCommonNet> iContentCommonNetList) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.totalCount = totalCount;
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public IContentCommonNetView(String code, String message, int page, int pagesize, long maxpage, long totalCount,
			List<IContentCommonNet> iContentCommonNetList) {
		super();
		this.code = code;
		this.message = message;
		this.page = page;
		this.pagesize = pagesize;
		this.maxpage = maxpage;
		this.totalCount = totalCount;
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public IContentCommonNetView(String code, String message, long totalCount,
			List<IContentCommonNet> iContentCommonNetList) {
		super();
		this.code = code;
		this.message = message;
		this.totalCount = totalCount;
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public String getCode() {
		return code;
	}

	@XmlElementWrapper(name = "iContentCommonNetList")
	@XmlElement(name = "iContentCommonNet")
	public List<IContentCommonNet> getiContentCommonNetList() {
		return iContentCommonNetList;
	}

	public long getMaxpage() {
		return maxpage;
	}

	public String getMessage() {
		return message;
	}

	public int getPage() {
		return page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setiContentCommonNetList(List<IContentCommonNet> iContentCommonNetList) {
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public void setMaxpage(long maxpage) {
		this.maxpage = maxpage;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
