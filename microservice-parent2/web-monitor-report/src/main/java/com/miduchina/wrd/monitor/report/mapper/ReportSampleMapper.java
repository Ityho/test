package com.miduchina.wrd.monitor.report.mapper;
import com.miduchina.wrd.po.report.KeywordReportRecord;
import com.miduchina.wrd.po.user.UserOperateLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportSampleMapper {

    UserOperateLog findUserById(@Param("userId") Integer userId);

    KeywordReportRecord findKeywordReportByCode(@Param("shareCode")String shareCode);

    void updateIsRead(@Param("userId")Integer userId);

}
