package com.miduchina.wrd.eventanalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.EventReportRes;
import com.miduchina.wrd.eventanalysis.log.model.EventTaskRes;
import com.miduchina.wrd.eventanalysis.service.EventService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.BaseRes;
import com.miduchina.wrd.po.eventanalysis.EventTasksView;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import com.miduchina.wrd.util.DateUtils;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.view.industry.eventAnalysis.EventTaskView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;
@Slf4j
@Controller
public class EventAnalysisController extends BaseController {
    @Autowired
    EventService eventService;

    private List<IncidentAnalysisReport> reportList = new ArrayList<>();
    @RequestMapping(value = "/completeList")
    public ModelAndView  completeList(HttpServletRequest request,ModelAndView modelAndView) throws Exception {
        fetchRightNumber(request,modelAndView);
        resetPagesize();
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_ANALYSIS);
        if (admin == null) {
            modelAndView.setViewName(Flags.login_view);
            return modelAndView;
        }
        // 获取报告列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("contentType", 1);
        params.put("platform", BusinessConstant.PLATFORM_H5);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,
                IntraBusinessAPIConfig.getValue("eventReportList"), params);
        EventTasksView report = JSONObject.parseObject(jsonStr, EventTasksView.class);
        if (report.getReports() != null
                && CodeConstant.SUCCESS_CODE.equals(report.getCode())) {
            for (int i = 0; i < report.getReports().size(); i++) {
                reportList = report.getReports();
            }
        }
        modelAndView.addObject("reportList",reportList);
        modelAndView.addObject("admin",admin);
        modelAndView.setViewName("view/eventAnalysis/completeList");
        return modelAndView;
    }
    /**
     * 任务列表删除、更新状态
     * @throws Exception
     */
    @RequestMapping(value = "/delTaskStatusC")
    @ResponseBody
    public boolean delTaskStatusC(HttpServletRequest request,String tickets,HttpServletResponse response) {
        fetchSessionAdmin(request);
        /*resetPagesize();*/
        response.setContentType("application/json;charset=GBK;");
        try {
            if (tickets != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                params.put("ticket", tickets);
                params.put("platform", BusinessConstant.PLATFORM_H5);
                String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,
                        IntraBusinessAPIConfig.getValue("eventDeleteReport"), params);
                System.out.println("-----jsonStr---" + jsonStr);
                BaseRes json = JSONObject.parseObject(jsonStr.trim(), BaseRes.class);
                if (json != null && CodeConstant.SUCCESS_CODE.equals(json.getCode())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @RequestMapping(value = "/analysisPreview",method = RequestMethod.GET)
    public ModelAndView analysisPreview(HttpServletRequest request,String tickets,ModelAndView modelAndView) throws Exception {
        fetchSessionAdmin(request);
        if (admin != null) {
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,objectDto,BusinessConstant.OPERATE_LOG_OPERATE_TYPE_R,null,null);
        }
        if (tickets != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            if (admin != null) {
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            }
            params.put("ticket", tickets);
            params.put("platform", BusinessConstant.PLATFORM_H5);
            String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,
                    IntraBusinessAPIConfig.getValue("eventGetReport"), params);
            EventReportRes report = JSONObject.parseObject(jsonStr.trim(), EventReportRes.class);
            if (report != null && CodeConstant.SUCCESS_CODE.equals(report.getCode())) {
                String reportName = report.getReport().getIncidentTitle();
                String reportTime = DateUtils.format(report.getReport().getCreateTime(), DateUtils.FORMAT_SHORT_TIME);
                modelAndView.addObject("reportName",reportName);
                modelAndView.addObject("reportTime",reportTime);
                modelAndView.addObject("tickets",tickets);
                modelAndView.addObject("admin",admin);
                modelAndView.setViewName("/view/eventAnalysis/analysisPreview");
                return modelAndView;
            }
        }
         modelAndView.setViewName(Flags.error_view);
        return modelAndView;
    }

    @InitBinder("analysisTask")
    public void initBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("analysisTask.");
    }
    /**
     * 创建分析
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "createAnalysis")
    public ModelAndView createAnalysis(@ModelAttribute("analysisTask") IncidentAnalysisTask analysisTask, Integer createType, HttpServletRequest request, ModelAndView modelAndView) throws Exception {
        admin = fetchSessionAdmin(request);
        resetPagesize();
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_ANALYSIS);
        if (analysisTask != null && analysisTask.getTaskId() != 0) {
            analysisTask = eventService.getEventById(request,"",analysisTask.getTaskId(),"","");
            // return "analysisDetail";
        } else {
            analysisTask = new IncidentAnalysisTask();
            Calendar c = Calendar.getInstance();
            analysisTask.setEndTime(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -3);
            analysisTask.setStartTime(c.getTime());
        }
        if (analysisTask != null && analysisTask.getTaskId() != 0) {
            analysisTask = eventService.getEventById(request,"",analysisTask.getTaskId(),"","");
//            return "analysisDetail";
        } else {
            analysisTask = new IncidentAnalysisTask();
            Calendar c = Calendar.getInstance();
            analysisTask.setEndTime(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -3);
            analysisTask.setStartTime(c.getTime());
        }
        if (admin == null) {
            modelAndView.setViewName("redirect:/userLogin");
            return modelAndView;
        }
        System.out.println(admin.getUserId());
        // 获取任务列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("contentType", 1);
        params.put("platform", BusinessConstant.PLATFORM_H5);
        String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                "taskList", params);
        List<IncidentAnalysisTask> taskList=null;
        EventTasksView task = JSONObject.parseObject(jsonStr, EventTasksView.class);
        if (task.getTasks() != null && CodeConstant.SUCCESS_CODE.equals(task.getCode())) {
            for (int i = 0; i < task.getTasks().size(); i++) {
                taskList = task.getTasks();
            }
            if (taskList != null) {
                String tickets = "";
                for (IncidentAnalysisTask tasks : taskList) {
                    if (tasks.getAnalysisStatus() != 5 && tasks.getAnalysisStatus() != 0) {
                        tickets += tasks.getTaskTicket() + ",";
                    }
                }
                System.out.println("tickets = " + tickets);
            }
        }
        modelAndView.setViewName("/view/eventAnalysis/createAnalysis");
        modelAndView.addObject("admin",admin);
        modelAndView.addObject("createType",createType);
        modelAndView.addObject("analysisTask",analysisTask);
        modelAndView.addObject("taskList",taskList);
        modelAndView.addObject("userPlatform",userPlatform);
        return modelAndView;
    }
    /**
     * 任务列表删除、更新状态
     * @throws Exception
     */
    @RequestMapping(value = "/delTaskStatus")
    public void delTaskStatus(HttpServletRequest request,HttpServletResponse response,String tickets) {
        fetchSessionAdmin(request);
        resetPagesize();
        PrintWriter printWriter = null;
        boolean result = false;
        try {
            printWriter = response.getWriter();
            if (tickets != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                params.put("ticket", tickets);
                String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                        "eventDeleteTask", params);
                System.out.println("-----jsonStr---" + jsonStr);
                BaseRes json = JSONObject.parseObject(jsonStr.trim(), BaseRes.class);
                if (json != null && CodeConstant.SUCCESS_CODE.equals(json.getCode())) {
                    result = true;
                    printWriter.print(result);
                } else {
                    printWriter.print(result);
                }
                // CommonUtils.opreateLog(ServletActionContext.getRequest(), admin,
                // Constants.OPERATE_LOG_PRODUCT_SECTION_EVENT_D,
                // Constants.OPERATE_LOG_OPERATE_TYPE_D, null, null,null);
            } else {
                printWriter.print(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            printWriter.print(result);
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }
    /**
     * 检测专业功能剩余次数
     *
     * @throws Exception
     */
    @RequestMapping(value = "checkProValidCount")
    @ResponseBody
    public Map<String,Object> checkProValidCount(HttpServletRequest request) throws Exception {
        admin = fetchSessionAdmin(request);
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        if (admin != null) {
            if (admin.getUserType() == BusinessConstant.USER_TYPE_PRO && admin.getProUserValidEndTime().getTime() - new Date().getTime() > 0) { // 是否专业版用户
                rtnMap.put("isPro", true);
            } else {
                rtnMap.put("isPro", false);
                rtnMap.put("analysisCount", admin.getUserAnalysisValidCount());
                rtnMap.put("wbAnalysisCount", admin.getUserWeiboAnalysisValidCount());
                rtnMap.put("briefCount", admin.getUserBriefValidCount());
                rtnMap.put("productAnalysisCount", admin.getUserProductAnalysisValidCount());
                rtnMap.put("productAnalysisCount1",new Date());

            }
        }
        return rtnMap;
    }



    // 创建/重新分析/更新
    @RequestMapping(value = "addTask")
    @ResponseBody
    public ModelDto addTask(HttpServletRequest request,HttpServletResponse response,Integer createType) throws Exception {

        ModelDto dto = new ModelDto();
        dto.setStatus(0);
        dto.setMsg("分析失败，请稍后重新分析");
       admin =  fetchSessionAdmin(request);
        String taskId = request.getParameter("analysisTask.taskId");
        String setTime = request.getParameter("analysisTask.setTime");
        String keyword = request.getParameter("analysisTask.keyword");
        String title = request.getParameter("analysisTask.incidentTitle");

        IncidentAnalysisTask analysisTask = new IncidentAnalysisTask();
        if (StringUtils.isNotBlank(taskId)){
            Map<String, Object> params1 = new HashMap<String, Object>();
            params1.put("taskId",taskId);
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request,"eventGetTask",params1);
            if (StringUtils.isNotBlank(rtnStr)) {
                EventTaskRes taskRes = JSON.parseObject(rtnStr, EventTaskRes.class);
                if (taskRes != null && CodeConstant.SUCCESS_CODE.equals(taskRes.getCode())) {
                    analysisTask = taskRes.getTask();
                }
            }
        }
        PrintWriter printWriter = null;
        int getSetTime = 3;
        if (createType != null){
            if (createType == 3) {
                getSetTime = Integer.parseInt(setTime);
            }else if (createType == 2){
                getSetTime = analysisTask.getSetTime();
            }else if (createType == 1){
                getSetTime = Integer.parseInt(setTime);
            }
        }else {
            createType = 1;
        }
        Date today = new Date();
        Date yesterday = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        if (getSetTime == 24) {
            calendar.setTimeInMillis(new Date().getTime() - 24 * 3600 * 1000);
        } else if (getSetTime == 3) {
            calendar.add(Calendar.DAY_OF_MONTH, -3);
        } else if (getSetTime == 7) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        } else if (getSetTime == 10) {
            calendar.add(Calendar.DAY_OF_MONTH, -10);
        } else if (getSetTime == 30) {
            calendar.add(Calendar.DAY_OF_MONTH, -30);
        }
        yesterday = calendar.getTime();
        String setStartTime = DateUtils.format(yesterday);
        String setEndTime = DateUtils.format(today);
        try {
            if (analysisTask != null) {
                analysisTask.setStartTime(DateUtils.parse(setStartTime));
                analysisTask.setEndTime(DateUtils.parse(setEndTime));
                log.info("getTaskId"+analysisTask.getTaskId());
                if (createType == 2 || createType == 3) {// 2--更新 3--重新分析
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                    if (createType == 2) {
                        params.put("createType", 2);
                        params.put("startTime",DateUtils.format(analysisTask.getStartTime()));
                        params.put("endTime",DateUtils.format(analysisTask.getEndTime()));
                    } else if (createType == 3) {
                        params.put("createType", 3);
                    }
                    params.put("taskId", analysisTask.getTaskId());
                    params.put("contentType", 1);
                    String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                            "eventAddTask", params);
                    BaseRes json = JSONObject.parseObject(jsonStr, BaseRes.class);
                    if (json != null && CodeConstant.DATA_API_SUCCESS_CODE.equals(json.getCode())) {
                        dto.setStatus(1);
                    } else {
                        if (json != null) {
                            if ( CodeConstant.E_EVENT_ANALYSIS_TASK_MORE_THAN_MAX_DATA_CODE_4026.equals(json.getCode())) {
                                dto.setStatus(0);
                                dto.setMsg("该事件数据量过大,请修改关键字或时间范围！");
                            }else if (com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SENSITIVEWORDSHOT.equals(json.getCode())
                                    || com.xd.tools.common.ErrorCodeConstant.F_LACK_SEARCH_SENSITIVE_WORDS.equals(json.getCode())
                                    || com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(json.getCode())){
                                dto.setStatus(0);
                                dto.setMsg("您的关键词包含敏感词，请检查后再试！~");
                            } else if ( CodeConstant.E_EVENT_ANALYSIS_TASK_NO_DATA_CODE_4025.equals(json.getCode())) {
                                dto.setStatus(0);
                                dto.setMsg("该事件数据量过小,请修改关键字或时间范围！");
                            }
                        }else {
                            dto.setStatus(0);
                        }
                    }
                } else {// 新建
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                    params.put("createType", 1);
                    params.put("title", title);

                    keyword = keyword.replaceAll(" ", "+");

                    params.put("keyword", keyword);
                    params.put("startTime",DateUtils.format(analysisTask.getStartTime()));
                    params.put("endTime",DateUtils.format(analysisTask.getEndTime()));
                    params.put("contentType", 1);
                    String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                            "eventAddTask", params);
                    BaseRes json = JSONObject.parseObject(jsonStr.trim(), BaseRes.class);
                    if (json != null && CodeConstant.DATA_API_SUCCESS_CODE.equals(json.getCode())) {
                        dto.setStatus(1);
                    } else {

                        if (json != null) {
                            if ( CodeConstant.E_EVENT_ANALYSIS_TASK_MORE_THAN_MAX_DATA_CODE_4026.equals(json.getCode())) {
                                dto.setStatus(0);
                                dto.setMsg("该事件数据量过大,请修改关键字或时间范围！");
                            }else if (com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SENSITIVEWORDSHOT.equals(json.getCode())
                                    || com.xd.tools.common.ErrorCodeConstant.F_LACK_SEARCH_SENSITIVE_WORDS.equals(json.getCode())
                                    || com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(json.getCode())){
                                dto.setStatus(0);
                                dto.setMsg("您的关键词包含敏感词，请检查后再试！~");
                            } else if ( CodeConstant.E_EVENT_ANALYSIS_TASK_NO_DATA_CODE_4025.equals(json.getCode())) {
                                dto.setStatus(0);
                                dto.setMsg("该事件数据量过小,请修改关键字或时间范围！");
                            }
                        }else {
                            dto.setStatus(0);
                        }
                    }
                }
            } else {
                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return dto;
        } finally {
        }
        return dto;
    }

    @RequestMapping(value = "analysisPreviewDemo")
    @ResponseBody
    public ModelAndView analysisPreviewDemo(HttpServletRequest request,HttpServletResponse response,String tickets,ModelAndView modelAndView) throws Exception {
        fetchSessionAdmin(request);
        Map<String, Object> params = new HashMap<String, Object>();
        if (admin != null) {
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request, null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, 1, null, null);
            params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        }
        params.put("ticket", tickets);
        String jsonStr = Utils.sendIntraBusinessAPIPOST(request,"eventGetReport", params);
        EventReportRes report = JSONObject.parseObject(jsonStr.trim(), EventReportRes.class);
        if (report != null && CodeConstant.SUCCESS_CODE.equals(report.getCode())) {
            String reportName = report.getReport().getIncidentTitle();
            String reportTime = DateUtils.format(report.getReport().getCreateTime(), DateUtils.FORMAT_SHORT_TIME);
            String reportTime1 = DateUtils.format(report.getReport().getCreateTime(), DateUtils.FORMAT_LONG);

            modelAndView.addObject("reportTime1",reportTime1);
            modelAndView.addObject("reportName",reportName);
            modelAndView.addObject("reportTime",reportTime);
            modelAndView.addObject("tickets",tickets);
            modelAndView.addObject("admin",admin);
            modelAndView.setViewName("/view/eventAnalysis/analysisPreview");


        }
        return modelAndView;
    }

    @RequestMapping(value = "/expectTask")
    @ResponseBody
    public void expectTask(HttpServletRequest request,HttpServletResponse response) {
        fetchSessionAdmin(request);
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;

        String setTime =  request.getParameter("analysisTask.setTime");
        String keyword = request.getParameter("analysisTask.keyword");
        String contentType =  request.getParameter("analysisTask.contentType");
        Integer getSetTime = 1;
        if (StringUtils.isNotBlank(setTime)){
            getSetTime = Integer.valueOf(setTime);
        }

        Date today = new Date();
        Date yesterday = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        if (getSetTime == 3) {
            calendar.add(Calendar.DAY_OF_MONTH, -3);
        } else if (getSetTime == 7) {
            calendar.add(Calendar.DAY_OF_MONTH, -7);
        } else if (getSetTime == 10) {
            calendar.add(Calendar.DAY_OF_MONTH, -10);
        } else if (getSetTime == 30) {
            calendar.add(Calendar.DAY_OF_MONTH, -30);
        }
        yesterday = calendar.getTime();

        String setStartTime = DateUtils.format(yesterday);
        String setEndTime = DateUtils.format(today);
        try {
            printWriter = response.getWriter();
            if (org.apache.commons.lang3.StringUtils.isNotBlank(keyword)) {

                IncidentAnalysisTask analysisTask = new IncidentAnalysisTask();
                analysisTask.setUserId(Integer.valueOf(admin.getUserId())); 			//用户id
                analysisTask.setContentType(1);			//分析范围
                analysisTask.setIncidentTitle(analysisTask.getIncidentTitle());		//事件名称
                analysisTask.setKeyword(analysisTask.getKeyword());					//关键词
                analysisTask.setFilterKeyword(analysisTask.getFilterKeyword());		//排除关键词
                analysisTask.setAnalysisType(0);					//分析类型(1:单次，2:持续)
                analysisTask.setAnalysisStatus(1);					//处理状态(0:失败，1:待处理，2:正在处理，3:已处理，4:处理完成)

                analysisTask.setCreateTime(new Date());
                analysisTask.setStartTime(today);				//处理开始时间(default:1970-01-01 00:00:00)
                analysisTask.setEndTime(yesterday);					//处理结束时间(default:当前时间)
                analysisTask.setPlatform(BusinessConstant.PARTNER_KEY_WYQ);					//平台标识
                analysisTask.setCreateType(1);


                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(analysisTask.getUserId())));
                params.put("title", analysisTask.getIncidentTitle());
                params.put("keyword", analysisTask.getKeyword());
                params.put("filterKeyword", analysisTask.getFilterKeyword());
                params.put("startTime", DateUtils.format(analysisTask.getStartTime()));
                params.put("endTime", DateUtils.format(analysisTask.getEndTime()));
                params.put("contentType", analysisTask.getContentType());
                String jsonStr = Utils.sendIntraBusinessAPIPOST(request,"expectTask", params);
                EventTaskView view = null;
                if(org.apache.commons.lang3.StringUtils.isNotBlank(jsonStr)) {
                    view = JSON.parseObject(jsonStr, EventTaskView.class);
                }
                if(view == null) {
                    printWriter.print("系统开小差了，请稍后再试！~");
                }else if(ErrorCodeConstant.F_OPERATE_SUCCESS.equals(view.getCode())) {
                    printWriter.print(JSON.toJSON(view.getTasks()));
                }else if(com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SENSITIVEWORDSHOT.equals(view.getCode())
                        || com.xd.tools.common.ErrorCodeConstant.F_LACK_SEARCH_SENSITIVE_WORDS.equals(view.getCode())
                        || com.xd.tools.common.ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
                    printWriter.print("您的关键词包含敏感词，请检查后再试！~");
                } else if(com.xd.tools.common.ErrorCodeConstant.F_LACK_SOLRFIRSTCOUNTEXPECT_OUT_MORE.equals(view.getCode())) {
                    printWriter.print("该事件数据量超过限制，请优化您的事件关键词配置！~");

                }else if(com.xd.tools.common.ErrorCodeConstant.F_LACK_SOLRFIRSTCOUNTEXPECT_NOT_ZERO.equals(view.getCode())) {
                    printWriter.print("该事件数据量为0,数据过少,请优化您的事件关键词配置,再来分析吧！~");
                } else {
                    printWriter.print("系统开小差了，请稍后再试！~");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }





}
