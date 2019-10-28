package com.miduchina.wrd.po.eventanalysis.weiboevent;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
public class IContentCommonNetListView implements Serializable {

	private static final long serialVersionUID = 1;
	private String code;
	private String message;
	private IContentCommonNetList iContentCommonNetList;

	public IContentCommonNetListView() {
		super();
	}

	public IContentCommonNetListView(String code, IContentCommonNetList iContentCommonNetList) {
		super();
		this.code = code;
		this.message = MyErrorCodeConstant.getMsg(code);
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public IContentCommonNetListView(String code, String message, IContentCommonNetList iContentCommonNetList) {
		super();
		this.code = code;
		this.message = message;
		this.iContentCommonNetList = iContentCommonNetList;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IContentCommonNetList getiContentCommonNetList() {
		return iContentCommonNetList;
	}

	public void setiContentCommonNetList(IContentCommonNetList iContentCommonNetList) {
		this.iContentCommonNetList = iContentCommonNetList;
	}

}
