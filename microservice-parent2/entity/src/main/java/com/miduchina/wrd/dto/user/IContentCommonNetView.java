package com.miduchina.wrd.dto.user;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
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

    @XmlElementWrapper(name = "iContentCommonNetList")
    @XmlElement(name = "iContentCommonNet")
    public List<IContentCommonNet> getiContentCommonNetList() {
        return iContentCommonNetList;
    }

    public void setiContentCommonNetList(List<IContentCommonNet> iContentCommonNetList) {
        this.iContentCommonNetList = iContentCommonNetList;
    }


}

