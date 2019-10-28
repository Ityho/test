package com.miduchina.wrd.monitor.report.service.impl;

import com.miduchina.wrd.monitor.report.mapper.ReportSampleMapper;
import com.miduchina.wrd.monitor.report.service.ReportService;
import com.miduchina.wrd.po.report.KeywordReportRecord;
import com.miduchina.wrd.po.user.UserOperateLog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther Administrator
 * @vreate 2019-06 10:41
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    private ReportSampleMapper reportSampleMapper;

    @Override
    public void updateIsRead(Integer id) {
        reportSampleMapper.updateIsRead(id);
    }

    @Override
    public UserOperateLog queryUserInfo(Integer userId) {
        return reportSampleMapper.findUserById(userId);
    }

    @Override
    public KeywordReportRecord findKeywordReportByCode(String code) {
        return reportSampleMapper.findKeywordReportByCode(code);
    }
}
