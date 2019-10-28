package com.miduchina.wrd.api.service.heatanalysis;

import com.miduchina.wrd.po.home.HeatShare;

public interface HeatReportService {
    /**
     * 获取单个关键词
     * @see #getCountHeatReportByUserId(Integer)
     * @param userId 查询参数
     * */
    Integer getCountHeatReportByUserId(Integer userId);

    HeatShare findShareCode(String shareCode);
}
