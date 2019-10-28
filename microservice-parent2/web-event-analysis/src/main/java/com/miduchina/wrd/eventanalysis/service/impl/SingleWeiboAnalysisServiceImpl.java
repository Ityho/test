package com.miduchina.wrd.eventanalysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.eventanalysis.products.PackageListRes;
import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.service.SingleWeiboAnalysisService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@Service("singleWeiboAnalysisService")
public class SingleWeiboAnalysisServiceImpl extends BaseService implements SingleWeiboAnalysisService {
    @Override
    public Integer queryCount(HttpServletRequest request,int userId, int confirmType) {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("userEncode", Utils.getUserEncode(userId));
        objectMap.put("confirmType", 1);
        objectMap.put("platformTag","wyq");
        objectMap.put("pageSize","100000");
        String result=Utils.sendIntraBusinessAPIPOST(request,"wfxTaskList",objectMap);
        PageDto dto = JSONObject.parseObject(result, PageDto.class);
        return dto.getTotalCount().intValue();
    }
}
