package com.miduchina.wrd.eventanalysis.service;

import javax.servlet.http.HttpServletRequest;

public interface ProductsAnalysisService {
    String  getProductListTotal(HttpServletRequest request,Integer userId, String platformTag);
}
