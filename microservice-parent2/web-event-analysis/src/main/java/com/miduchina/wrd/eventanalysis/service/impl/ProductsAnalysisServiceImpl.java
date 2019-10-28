package com.miduchina.wrd.eventanalysis.service.impl;

import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.service.ProductsAnalysisService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductsAnalysisServiceImpl extends BaseService implements ProductsAnalysisService{
    @Override
    public String getProductListTotal(HttpServletRequest request,Integer userId, String platformTag) {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("userEncode",Utils.getUserEncode(userId));
        objectMap.put("platformTag","wyq");
        String result=Utils.sendIntraBusinessAPIPOST(request,"productAnalysisTotal",objectMap);
        return result;
    }
}
