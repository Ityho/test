package com.miduchina.wrd.api.service.hotevents.impl;

import com.miduchina.wrd.api.mapper.hotevents.HotCallMapper;
import com.miduchina.wrd.api.service.hotevents.HotCallService;
import com.miduchina.wrd.po.hotspot.HotCall;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther Administrator
 * @vreate 2019-06 13:49
 */
@Service
public class HotCallServiceImpl implements HotCallService {

    @Resource
    private HotCallMapper hotCallMapper;

    @Override
    public boolean saveHotCall(HotCall hotCall) {
        return hotCallMapper.saveHotCall(hotCall);
    }
}
