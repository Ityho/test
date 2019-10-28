package com.miduchina.wrd.api.service.infomonitor.impl;

import com.miduchina.wrd.api.mapper.infomonitor.ReportMapper;
import com.miduchina.wrd.api.service.infomonitor.ReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther Administrator
 * @vreate 2019-05 16:41
 */
@Service
public class ReportServiceImpl implements ReportService{
   @Resource
   private ReportMapper reportMapper;
    @Override
    public boolean closeReport(Integer userId) {
        Boolean bl = true;
        Boolean bl1 = reportMapper.closeDailyReport(userId);
        Boolean bl2 = reportMapper.closeWeeklyReport(userId);
        Boolean bl3 = reportMapper.closeMonthlyReport(userId);
        if (bl1 && bl2 && bl3) {
            bl = true;
        }else{
            bl = false;
        }
        return bl;
    }
}
