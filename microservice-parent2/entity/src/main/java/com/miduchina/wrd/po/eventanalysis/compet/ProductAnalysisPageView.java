package com.miduchina.wrd.po.eventanalysis.compet;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
public class ProductAnalysisPageView{
    @ApiModelProperty(value = "统计数", required = true)
    private long dbCount;
    @ApiModelProperty(value = "分类关键词列表", required = true)
    private List<?> list;
    @ApiModelProperty(value = "最大页数", required = true)
    private int maxPage;
    @ApiModelProperty(value = "当前页数", required = true)
    private int page;
    @ApiModelProperty(value = "总数", required = true)
    private long totalCount;

    @ApiModelProperty(value = "返回码", required = true)
    protected String code  ;

    @ApiModelProperty(value = "返回信息", required = true)
    protected String message;


    public ProductAnalysisPageView(String code, long totalCount, int page, int maxPage, List<?> list) {
        super();
        this.code = code;
        this.message =message;
        this.maxPage = maxPage;
        this.page = page;
        this.totalCount = totalCount;
        this.list = list;
    }

    private List<ProductAnalysis> productAnalysisList;

    public ProductAnalysisPageView() {

    }

    public ProductAnalysisPageView(String code, int page, int pageSize, int maxPage, long totalCount,
                                   List<ProductAnalysis> productAnalysisList) {
        this.productAnalysisList = productAnalysisList;
    }

    public List<ProductAnalysis> getProductAnalysisList() {
        return productAnalysisList;
    }

    public void setProductAnalysisList(List<ProductAnalysis> productAnalysisList) {
        this.productAnalysisList = productAnalysisList;
    }
}
