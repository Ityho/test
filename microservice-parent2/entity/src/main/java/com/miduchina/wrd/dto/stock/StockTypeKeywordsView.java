package com.miduchina.wrd.dto.stock;

import com.miduchina.wrd.po.eventanalysis.MyErrorCodeConstant;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "data")
@XmlAccessorOrder(XmlAccessOrder.UNDEFINED)
@Data
public class StockTypeKeywordsView implements Serializable {
    private static final long serialVersionUID = 1;
    private String code;
    private String message;
    private long totalCount;
    private List<StockTypeKeywords> statisticsList;
    private List<StockTypeKeywords> ctkList;
    private List<StockTypeKeywords> brandsList;
    private List<StockTypeKeywords> stkList;

    public StockTypeKeywordsView() {
        super();
    }

    public StockTypeKeywordsView(String code, long totalCount, List<StockTypeKeywords> statisticsList,List<StockTypeKeywords> stkList,List<StockTypeKeywords> ctkList,List<StockTypeKeywords> brandsList) {
        super();
        this.code = code;
        this.message = MyErrorCodeConstant.getMsg(code);
        this.totalCount = totalCount;
        this.statisticsList = statisticsList;
        this.stkList = stkList;
        this.ctkList = ctkList;
        this.brandsList = brandsList;
    }

    public StockTypeKeywordsView(String code, String message, long totalCount, List<StockTypeKeywords> statisticsList,List<StockTypeKeywords> stkList,List<StockTypeKeywords> ctkList,List<StockTypeKeywords> brandsList) {
        super();
        this.code = code;
        this.message = message;
        this.totalCount = totalCount;
        this.statisticsList = statisticsList;
        this.stkList = stkList;
        this.ctkList = ctkList;
        this.brandsList = brandsList;
    }
}

