package com.miduchina.wrd.po.eventanalysis.compet;

import com.miduchina.wrd.po.eventanalysis.BaseView;

import javax.xml.bind.annotation.XmlRootElement;


public class ProductAnalysisView extends BaseView {
    private ProductAnalysis productAnalysis;

    public ProductAnalysisView() {

    }

    public ProductAnalysisView(String code) {
        super(code);
    }

    public ProductAnalysisView(String code, ProductAnalysis pa) {
        super(code);
        this.productAnalysis = pa;
    }

    public ProductAnalysisView(String code, String message) {
        super(code, message);
    }

    public ProductAnalysisView(String code, String message, ProductAnalysis pa) {
        super(code, message);
        this.productAnalysis = pa;
    }

    public ProductAnalysis getProductAnalysis() {
        return productAnalysis;
    }

    public void setProductAnalysis(ProductAnalysis productAnalysis) {
        this.productAnalysis = productAnalysis;
    }
}
