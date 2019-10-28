package com.miduchina.wrd.eventanalysis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.service.EventService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.po.eventanalysis.EventTasksView;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Service
public class EventServiceImpl extends BaseService implements EventService {

    @Override
    public Integer queryCount(HttpServletRequest request,int userId,int contentType) {
        Map<String,Object> objectMap=new HashMap<>();
        if(userId!=0){
            objectMap.put("userEncode",Utils.getUserEncode(userId));
        }
        objectMap.put("userIds",userId);
        objectMap.put("contentType",contentType);
        String jsonStr=Utils.sendIntraBusinessAPIPOST(request,"eventAnalysisTotal",objectMap);
        log.info("jsonStr"+jsonStr);
        PageDto pageDto = null;
        if (StringUtils.isNotBlank(jsonStr)){
            pageDto = JSONObject.parseObject(jsonStr, PageDto.class);
        }
        Long count = Long.valueOf(0);
        if(pageDto!=null && pageDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
             count= pageDto.getTotalCount();
        }

        return count.intValue();
    }

    @Override
    public IncidentAnalysisTask getEventById(HttpServletRequest request,String userEncode, Integer taskId, String ticket, String platformTag) {
        Map<String,Object> objectMap=new HashMap<>();
        if(!TextUtils.isEmpty(userEncode)){
            objectMap.put("userEncode",userEncode);
        }
        if(taskId!=null && taskId!=0){
            objectMap.put("taskId",taskId);
        }
        if(!TextUtils.isEmpty(ticket)){
            objectMap.put("ticket",ticket);
        }
        if(!TextUtils.isEmpty(platformTag)){
            objectMap.put("platformTag",platformTag);
        }
        String jsonStr=Utils.sendIntraBusinessAPIPOST(request,"eventGetTask",objectMap);
        EventTasksView eventTasksView = JSONObject.parseObject(jsonStr, EventTasksView.class);
        if(eventTasksView!= null && eventTasksView.getCode().equals(CodeConstant.SUCCESS_CODE)){
            IncidentAnalysisTask incidentAnalysisTask=eventTasksView.getTask();
            return incidentAnalysisTask;
        }
        return null;
    }

    @Override
    public List<IncidentAnalysisReport> getEventReportList(HttpServletRequest request, int userId, int contentType) {
        Map<String,Object> objectMap=new HashMap<>();
        if(userId!=0){
            objectMap.put("userEncode",Utils.getUserEncode(userId));
        }
        objectMap.put("contentType",contentType);
        String jsonStr=Utils.sendIntraBusinessAPIPOST(request,"eventReportList",objectMap);
        log.info("jsonStr"+jsonStr);
        EventTasksView report = JSONObject.parseObject(jsonStr, EventTasksView.class);
        if (report!= null && report.getReports() != null && report.getReports().size()>0) {
            List<IncidentAnalysisReport> reportList = report.getReports();
            return reportList;
        }
        return null;
    }
}
