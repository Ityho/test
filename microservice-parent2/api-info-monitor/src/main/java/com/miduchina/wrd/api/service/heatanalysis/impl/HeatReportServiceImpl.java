package com.miduchina.wrd.api.service.heatanalysis.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.api.mapper.heatanalysis.HeatMapper;
import com.miduchina.wrd.api.mapper.heatanalysis.HeatReportMapper;
import com.miduchina.wrd.api.service.heatanalysis.HeatReportService;
import com.miduchina.wrd.po.home.HeatShare;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class HeatReportServiceImpl implements HeatReportService {

    @Resource
    private HeatReportMapper heatReportMapper;

    @Resource
    private HeatMapper heatMapper;

    @Override
    public Integer getCountHeatReportByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        JSONObject parma=new JSONObject(map);
        return heatReportMapper.selectCountHeatReportByUserId(parma);
    }

    @Override
    public HeatShare findShareCode(String shareCode) {
        return heatMapper.findShareCode(shareCode);
    }

}
