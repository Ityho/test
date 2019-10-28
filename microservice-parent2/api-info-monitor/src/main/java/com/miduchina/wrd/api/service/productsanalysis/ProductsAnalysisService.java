package com.miduchina.wrd.api.service.productsanalysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;

import java.util.List;

public interface ProductsAnalysisService {

    Integer queryCount(JSONObject params);

    List<ProductsAnalysisBrief> getPABList(JSONObject params);

    List<ProductsAnalysisShare> findisShareCodePabInId(JSONObject jsonObject);


    List<ProductsAnalysis> getListTask(JSONObject params);

}
