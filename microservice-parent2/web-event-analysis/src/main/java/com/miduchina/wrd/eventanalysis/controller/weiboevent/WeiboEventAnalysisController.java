package com.miduchina.wrd.eventanalysis.controller.weiboevent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.eventanalysis.TasksView;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.WyqDataConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.log.model.EventReportRes;
import com.miduchina.wrd.eventanalysis.service.EventService;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.BaseRes;
import com.miduchina.wrd.po.eventanalysis.EventTasksView;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import com.miduchina.wrd.util.DateUtils;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.view.industry.eventAnalysis.EventTaskView;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/weibo")
public class WeiboEventAnalysisController extends BaseController{
    @Autowired
    private EventService eventService;
    private static String updateTime = "2016-09-02 21:15:00";// 微博分析改版系统上线时间
    @RequestMapping(value = "/weiboCompleteList")
    public ModelAndView weiboCompleteList(HttpServletRequest request,ModelAndView modelAndView) throws Exception {
        fetchRightNumber(request,modelAndView);
        resetPagesize();
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_WEIBO_ANALYSIS);
        if (admin == null) {
            modelAndView.setViewName("redirect:/userLogin");
            return modelAndView;
        }
        // 获取报告列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("contentType", 2);
        String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                "eventReportList", params);
        EventTasksView report = JSONObject.parseObject(jsonStr, EventTasksView.class);
        List<IncidentAnalysisReport> reportList=null;
        if (report.getReports() != null
                && CodeConstant.SUCCESS_CODE.equals(report.getCode())) {
            reportList = report.getReports();
        }
        modelAndView.addObject("reportList",reportList);
        modelAndView.addObject("admin",admin);
        modelAndView.addObject("userPlatform",userPlatform);
        modelAndView.setViewName("/view/weiboEventAnalysis/completeList");
        return modelAndView;
    }
    /**
     * 任务列表删除、更新状态
     * @throws Exception
     */
    @RequestMapping(value = "/delTaskStatusC")
    public void delTaskStatusC(String tickets,HttpServletRequest request,HttpServletResponse response) {
        fetchSessionAdmin(request);
        resetPagesize();


        try {
            response.sendRedirect("http://site.11j.net.cn/");
        }catch (Exception e){

        }
        PrintWriter printWriter = null;
        boolean result = false;
        try {
            printWriter = response.getWriter();
            if (tickets != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                params.put("ticket", tickets);
                String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                        "eventDeleteReport", params);
                System.out.println("-----jsonStr---" + jsonStr);
                BaseRes json = JSONObject.parseObject(jsonStr.trim(), BaseRes.class);
                if (json != null && CodeConstant.SUCCESS_CODE.equals(json.getCode())) {
                    result = true;
                    printWriter.print(result);

                } else {
                    printWriter.print(result);
                }
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
    @RequestMapping(value = "/analysisPreview",method = RequestMethod.GET)
    public ModelAndView analysisPreview(ModelAndView modelAndView,String tickets,HttpServletRequest request) throws Exception {
        fetchSessionAdmin(request);
        if (admin != null){
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null,BusinessConstant.PLATFORM_H5,objectDto,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,null);
        }

        if (tickets != null) {
            Map<String, Object> params = new HashMap<String, Object>();
            if (admin != null) {
                params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
            }
            params.put("ticket", tickets);
            String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                    "eventGetReport", params);
            EventReportRes report = JSONObject.parseObject(jsonStr.trim(), EventReportRes.class);
            if (report != null && CodeConstant.SUCCESS_CODE.equals(report.getCode())) {
                String reportName = report.getReport().getIncidentTitle();
                String reportTime = DateUtils.format(report.getReport().getCreateTime(), DateUtils.FORMAT_SHORT_TIME);
                Date dc = DateUtils.parse(updateTime);
                Date dot = report.getReport().getCreateTime();
                log.info("dc.getTime():" + dc.getTime() + "---dc.getTime():" + dot.getTime());
                String reportDate="";
                if (dc.getTime() > dot.getTime()) {
                    reportDate = "b";// 新版
                } else if (dc.getTime() < dot.getTime()) {
                    reportDate = "a";// 旧版
                }
                modelAndView.addObject("reportDate",reportDate);
                log.info("--------------ap tickets " + reportName + "---" + reportTime + "===============");
                Map<String,Object> map = new HashMap<>();
                map.put("UserDto",admin);
                OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
                CommonUtils.opreateLog(request,null,
                        BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,objectDto, BusinessConstant.OPERATE_LOG_OPERATE_TYPE_R, null, null);
                modelAndView.addObject("tickets",tickets);
                modelAndView.addObject("reportName",reportName);
                modelAndView.addObject("reportTime",reportTime);
                modelAndView.setViewName("/view/weiboEventAnalysis/analysisPreview");
                return modelAndView;
            }
        }
        modelAndView.setViewName(Flags.error_view);
        return modelAndView;
    }
    @InitBinder("analysisTask")
    public void initBinderCreate(WebDataBinder binder){
        binder.setFieldDefaultPrefix("analysisTask.");
    }
    @RequestMapping(value = "/createWeiBoAnalysis")
    public ModelAndView createWeiBoAnalysis(ModelAndView modelAndView,String tickets,HttpServletRequest request,IncidentAnalysisTask analysisTask,Integer createType) throws Exception {
        fetchSessionAdmin(request);
        fetchRightNumber(request,modelAndView);
        resetPagesize();
        fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_WEIBO_ANALYSIS);
        if (analysisTask != null && analysisTask.getTaskId() != 0) {
            analysisTask = eventService.getEventById(request,CommonUtils.getUserEncodeNew(admin.getUserId()),analysisTask.getTaskId(),"","wyq");
        } else {
            // 创建微博分析
            analysisTask = new IncidentAnalysisTask();
            Calendar c = Calendar.getInstance();
            analysisTask.setEndTime(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, -3);
            analysisTask.setStartTime(c.getTime());
        }
        // 获取任务列表
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("contentType", 2);
        String jsonStr = Utils.sendIntraBusinessAPIPOST(request,
                "taskList", params);
        EventTasksView task = JSONObject.parseObject(jsonStr, EventTasksView.class);
        if (task.getTasks() != null && CodeConstant.SUCCESS_CODE.equals(task.getCode())) {
            List<IncidentAnalysisTask> taskList = task.getTasks();
            if (CollectionUtils.isNotEmpty(taskList)) {
                tickets = "";
                for (IncidentAnalysisTask tasks : taskList) {
                    if (tasks.getAnalysisStatus() != 5 && tasks.getAnalysisStatus() != 0) {
                        tickets += tasks.getTaskTicket() + ",";
                    }
                }
                modelAndView.addObject("taskList",taskList);
            }
        }
        modelAndView.addObject("admin",admin);
        modelAndView.addObject("tickets",tickets);
        modelAndView.addObject("userPlatform",userPlatform);
        modelAndView.setViewName("/view/weiboEventAnalysis/createWeiboAnalysis");
        return modelAndView;
    }
    /**
     * 任务列表删除、更新状态
     * @throws Exception
     */
    @RequestMapping(value = "/delTaskStatus")
    public void delTaskStatus(String tickets,HttpServletRequest request,HttpServletResponse response) {
        fetchSessionAdmin(request);
        admin=new UserDto();
//        resetPagesize();
        response.setContentType("application/json;charset=GBK;");
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
                BaseRes json = JSONObject.parseObject(jsonStr.trim(), BaseRes.class);
                if (json != null && CodeConstant.SUCCESS_CODE.equals(json.getCode())) {
                    result = true;
                    printWriter.print(result);
                } else {
                    printWriter.print(result);
                }
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

    @RequestMapping(value = "analysisPreviewDemo")
    @ResponseBody
    public ModelAndView analysisPreviewDemo(HttpServletRequest request,HttpServletResponse response,String tickets,ModelAndView modelAndView) throws Exception {
        admin = fetchSessionAdmin(request);
        Map<String, Object> params = new HashMap<String, Object>();
        if (admin != null) {
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",admin);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request, null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_WEIBOEVENT,
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
            Date dc = DateUtils.parse(updateTime);
            Date dot = report.getReport().getCreateTime();
            log.info("dc.getTime():" + dc.getTime() + "---dc.getTime():" + dot.getTime());
            if (dc.getTime() > dot.getTime()) {
                String reportDate = "b";// 新版
            } else if (dc.getTime() < dot.getTime()) {
                String reportDate = "a";// 旧版
            }
            log.info("--------------ap tickets " + reportName + "---" + reportTime + "===============");


            modelAndView.addObject("admin",admin);
            modelAndView.addObject("tickets",tickets);

            modelAndView.addObject("tickets",tickets);
            modelAndView.addObject("reportName",reportName);
            modelAndView.addObject("reportTime",reportTime);
            modelAndView.addObject("reportTime1",reportTime1);
            modelAndView.setViewName("/view/weiboEventAnalysis/analysisPreview");

        }
        return modelAndView;
    }


    @InitBinder("analysisTask")
    public void initBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("analysisTask.");
    }
    /**
     * 创建或更新微博分析、重新分析
     */
    @RequestMapping(value = "/addWeiboTask")
    @ResponseBody
    public ModelDto addWeiboTask(String setTime, @ModelAttribute("analysisTask") IncidentAnalysisTask analysisTask, int createType, String tickets, HttpServletRequest request, HttpServletResponse response) {

        ModelDto dto = new ModelDto();
        dto.setStatus(0);
        dto.setMsg("分析失败，请稍后重新分析");
        fetchSessionAdmin(request);
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        log.info("wb task insert start ------------------------------------" + tickets);
        int getSetTime = analysisTask.getSetTime();
        log.info("createType = " + createType + analysisTask.getTaskId());
        if (createType == 2) {
            getSetTime = Integer.parseInt(setTime);
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
            if (analysisTask != null) {
                analysisTask.setStartTime(DateUtils.parse(setStartTime));
                analysisTask.setEndTime(DateUtils.parse(setEndTime));
                if (createType == 2 || createType == 3) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
                    if (createType == 2) {
                        params.put("createType", 2);
                        params.put("startTime",setStartTime);
                        params.put("endTime",setEndTime);
                    } else if (createType == 3) {
                        params.put("createType", 3);
                    }

                    params.put("taskId", analysisTask.getTaskId());
                    params.put("contentType", 2);
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
                    params.put("title", analysisTask.getIncidentTitle());
                    String keyword = analysisTask.getKeyword().replaceAll(" ", "+");
                    params.put("keyword", keyword);
                    params.put("startTime",setStartTime);
                    params.put("endTime",setEndTime);
                    params.put("contentType", 2);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dto;
    }
    /**
     * 查询分析状态
     * @throws Exception
     */
    @RequestMapping(value = "/queryTaskInfo")
    @ResponseBody
    public TasksView queryTaskInfo(HttpServletRequest request,String tickets) throws Exception {
        if (StringUtils.isNotBlank(tickets)) {
            Map<String, Object> param = WyqDataConfig.getDataInitMap(request);
            param.put("analysisTaskTickets", tickets);
            String jsonStr = Utils.sendGet(SysConfig.cfgMap.get("API_WEIBO_ANALYSIS_TASK_URL") + "QueryTask", param);
            if (StringUtils.isNotBlank(jsonStr)) {
                TasksView tv = JSON.parseObject(jsonStr, TasksView.class);
                log.info("queryTask=" + jsonStr);
                return  tv;
            }
        }
        return null;


    }


    /**
     * 预查询创建所需时间、数据量
     * @throws Exception
     */


    @RequestMapping(value = "/expectTask")
    public void expectTask(String setTime,@ModelAttribute("analysisTask") IncidentAnalysisTask analysisTask, int createType,String tickets,HttpServletRequest request,HttpServletResponse response) {
        fetchSessionAdmin(request);
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        log.info("wb task insert start ------------------------------------" + tickets);
        int getSetTime = analysisTask.getSetTime();
        log.info("createType = " + createType + analysisTask.getTaskId());
        if (createType == 2) {
            getSetTime = Integer.parseInt(setTime);
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
            if (org.apache.commons.lang3.StringUtils.isNotBlank(analysisTask.getKeyword())) {

                IncidentAnalysisTask task = new IncidentAnalysisTask();
                analysisTask.setUserId(analysisTask.getUserId()); 			//用户id
                analysisTask.setContentType(0);			//分析范围
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
