package com.miduchina.wrd.api.mapper.infomonitor;

import org.apache.ibatis.annotations.Mapper;

/**
 * @auther Administrator
 * @vreate 2019-05 16:40
 */
@Mapper
public interface ReportMapper {

    Boolean closeDailyReport(Integer userId);

    Boolean closeWeeklyReport(Integer userId);

    Boolean closeMonthlyReport(Integer userId);
}
