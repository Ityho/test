package com.miduchina.wrd.monitor.report.service;

import com.miduchina.wrd.po.report.KeywordReportRecord;
import com.miduchina.wrd.po.user.UserOperateLog;

/**
 * @auther Administrator
 * @vreate 2019-06 10:40
 */
public interface ReportService {
    void updateIsRead(Integer id);

    UserOperateLog queryUserInfo(Integer userId);

    KeywordReportRecord findKeywordReportByCode(String code);
}
