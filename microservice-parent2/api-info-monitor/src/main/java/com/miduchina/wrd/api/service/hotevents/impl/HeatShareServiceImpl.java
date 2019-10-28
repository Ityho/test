package com.miduchina.wrd.api.service.hotevents.impl;

import com.miduchina.wrd.api.mapper.hotevents.HeatShareMapper;
import com.miduchina.wrd.api.service.hotevents.HeatShareService;
import com.miduchina.wrd.po.hotspot.HeatShare;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther Administrator
 * @vreate 2019-06 11:42
 */
@Service
public class HeatShareServiceImpl implements HeatShareService {
    @Resource
    private HeatShareMapper heatShareMapper;
    @Override
    public HeatShare findHs(String shareCode) {
        return heatShareMapper.findHs(shareCode);
    }
}
