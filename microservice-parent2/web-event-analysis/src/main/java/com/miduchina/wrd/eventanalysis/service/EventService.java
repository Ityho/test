package com.miduchina.wrd.eventanalysis.service;

import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    /**
     * 获取全网事件分析总数
     * @return
     */
    Integer queryCount(HttpServletRequest request,int userId,int contentType);

    IncidentAnalysisTask getEventById(HttpServletRequest request,String userEncode, Integer taskId, String ticket, String platformTag);

    /**
     * 获取全网事件列表
     * @param request
     * @param userId
     * @param contentType
     * @return
     */
    List<IncidentAnalysisReport> getEventReportList(HttpServletRequest request, int userId, int contentType);
}
