package com.miduchina.wrd.api.service.productsanalysis.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.productsanalysis.ProductsAnalysisMapper;
import com.miduchina.wrd.api.service.productsanalysis.ProductsAnalysisService;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductsAnalysisServiceImpl implements ProductsAnalysisService {
    @Resource
    ProductsAnalysisMapper mapper;

    @Override
    public Integer queryCount(JSONObject params) {
        return mapper.selectCount(params);
    }

    @Override
    public List<ProductsAnalysisBrief> getPABList(JSONObject params) {
        return mapper.getPABList(params);
    }

    @Override
    public List<ProductsAnalysis> getListTask(JSONObject params) {
        return mapper.getListTask(params);
    }

    @Override
    public List<ProductsAnalysisShare> findisShareCodePabInId(JSONObject jsonObject) {
        return mapper.getListShare(jsonObject);
    }
}
