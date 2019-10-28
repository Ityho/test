package com.miduchina.wrd.api.service.infomonitor.impl;

import com.miduchina.wrd.api.mapper.infomonitor.WarningMapper;
import com.miduchina.wrd.api.service.infomonitor.WarningService;
import com.miduchina.wrd.po.ranking.Notice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-05 11:15
 */
@Service
public class WarningServiceImpl implements WarningService {
    @Resource
    private WarningMapper warningMapper;
    @Override
    public List<Notice> getNotice() {
        return warningMapper.getNotice();
    }
}
