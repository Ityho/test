package com.miduchina.wrd.api.controller.v3.weiboanalysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.service.analysis.ClientAnalysisSampleService;
import com.miduchina.wrd.api.service.weiboanalysis.WeiboAnalysisService;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.analysis.AnalysisSampleDto;
import com.miduchina.wrd.dto.analysis.AnalysisSolidifyDto;
import com.miduchina.wrd.dto.analysis.weiboanalysis.WeiboAnalysisTaskDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.analysis.AnalysisSample;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.xd.tools.ownutils.DateUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by shitao on 2019-05-23 14:28.
 *
 * @author shitao
 */
@Slf4j
@Api(tags = {"微博传播效果分析"})
@RestController
@RequestMapping("api/v3/weiboAnalysis")
public class WeiboAnalysisController extends BaseController {


    @Autowired
    private ClientAnalysisSampleService sampleService;

    @Autowired
    private WeiboAnalysisService weiboAnalysisService;


    @ApiOperation(value = "微博传播效果列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "微博传播效果列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "taskList", headers = "Accept=application/json")
    public PageDto taskList(@ApiParam(value = "筛选条件(userEncode:用户Encode,page:页码,pageSize:页面大小)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("contentType") == null ||
                params.get("page") == null || params.get("pageSize") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();


        Integer page = jsonParams.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE : jsonParams.getInteger("page");
        Integer pageSize = jsonParams.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE : jsonParams.getInteger("pageSize");

        Map<String, Object> buParams = new HashMap<>();
        buParams.put("page", String.valueOf(page));
        buParams.put("pageSize", String.valueOf(pageSize));
        buParams.put("userId", userDto.getUserId());

        List<WeiboAnalysisTaskDto> list = new ArrayList<>();
        jsonParams = new JSONObject(buParams);
        PageInfo pageInfo = weiboAnalysisService.getTasks(jsonParams);
        if (pageInfo != null && CollectionUtils.isNotEmpty(pageInfo.getList())) {
            baseDto.setMaxPage(Integer.valueOf(pageInfo.getPageNum()));
            baseDto.setTotalCount(pageInfo.getTotal());
            baseDto.setPage(pageInfo.getPages());
            baseDto.setPageSize(pageInfo.getPageSize());
            list = createAnalysisTaskDtoList(pageInfo.getList());
        }
        if (list.size() == 0){
            buParams = new HashMap<String, Object>();
            buParams.put("sampleType",BusinessConstant.STATUS_VALUE_4);
            jsonParams = new JSONObject(buParams);
            AnalysisSample sample = sampleService.getSample(jsonParams);
            AnalysisSampleDto serialDto = BeanUtils.copyProperties(sample, AnalysisSampleDto.class);
            if (serialDto != null) {

                WeiboAnalysisTaskDto dto = new WeiboAnalysisTaskDto();
                dto.setIsSample(BusinessConstant.STATUS_VALUE_1);
                dto.setForwardCount(25511);
                dto.setCommentCount(15911);
                dto.setPraiseCount(11743);
                dto.setWeiboContent(sample.getTitle());
                dto.setShareUrl(sample.getShareUrl());
                dto.setWeiboNickname("微舆情");
                dto.setPublishedTime(sample.getCreateTime());
                list.add(dto);
            }else {
                WeiboAnalysisTaskDto ear4 = new WeiboAnalysisTaskDto();
                ear4.setIsSample(1);
                ear4.setWeiboContent("世卫组织疫苗微博分析报告");
                ear4.setShareUrl(SysConfig.cfgMap.get("WEIBO_ANALYSIS_DEMO"));
                ear4.setForwardCount(25511);
                ear4.setCommentCount(15911);
                ear4.setPraiseCount(11743);
                ear4.setWeiboNickname("世界卫生组织");
                ear4.setPublishedTime(new Date());
                list.add(ear4);
            }
        }
        baseDto.setData(list);
        //记录用户操作日志
        try {
            int operateType = BusinessConstant.STATUS_VALUE_4;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SWA,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto;
    }



    @ApiOperation(value = "微博传播效果创建", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "微博传播效果列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "createTask", headers = "Accept=application/json")
    public BaseDto createTask(@ApiParam(value = "筛选条件(userEncode:用户Encode,weiboUrl:微博链接)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("weiboUrl") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        String weiboUrl = jsonParams.getString("weiboUrl") == null ? "" : jsonParams.getString("weiboUrl");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        if (StringUtils.isNotBlank(weiboUrl)) {
            if (weiboUrl.indexOf("?") != -1)
                weiboUrl = weiboUrl.substring(0, weiboUrl.indexOf("?"));
            weiboUrl = weiboUrl.replace("www.", "");
        }
        Map<String, Object> params1 = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        params1.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
        params1.put("weiboUrl", weiboUrl);
        params1.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userDto.getUserId())));
        String jsonStr =  BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("wfxAddTask"), params1);

        baseDto = JSONObject.parseObject(jsonStr,PageDto.class);
        JSONObject object = JSONObject.parseObject(jsonStr);
        WeiBoAnalysisTask task1 = JSONObject.parseObject(object.getJSONObject("task").toJSONString(), WeiBoAnalysisTask.class);

        if(task1 != null){
            params1 = new HashMap<>();
            params1.put("userId",userDto.getUserId());
            params1.put("taskTicket",task1.getTaskTicket());
            jsonParams = new JSONObject(params1);
            WeiBoAnalysisTask task = weiboAnalysisService.getTask(jsonParams);
            if (task == null) {
                params1 = CommonUtils.entityToMap(task1);
                jsonParams = new JSONObject(params1);
                weiboAnalysisService.insertTask(jsonParams);
            }else {
                params1 = CommonUtils.entityToMap(task);
                params1.put("wUserId",userDto.getUserId());
                params1.put("wTaskTicket",task.getTaskTicket());
                jsonParams = new JSONObject(params1);
                weiboAnalysisService.updateTask(jsonParams);
            }
            WeiboAnalysisTaskDto taskDto = createAnalysisTaskDto(task1);
            baseDto.setData(taskDto);
        }
        return baseDto;
    }


    @ApiOperation(value = "确认任务", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "确认任务") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "confirmTask", headers = "Accept=application/json")
    public BaseDto confirmTask(@ApiParam(value = "筛选条件(userEncode:用户Encode,ticket:任务标识)") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("ticket") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        String ticket = jsonParams.getString("ticket") == null ? "" : jsonParams.getString("ticket");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Map<String, Object> map = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        map.put("userId",userDto.getUserId());
        map.put("taskTicket",ticket);
        jsonParams = new JSONObject(params);
        WeiBoAnalysisTask task = weiboAnalysisService.getTask(jsonParams);
        if (task != null) {

//            UserViewVo userViewVo = wjfService.rightModify(request, response, user, 5, 2,
//                        task.getForwardCount(), task.getTaskId() + "", "app-减少微博传播分析");
//                if (userViewVo == null) {
//                    return null;
//                }


            if(task.getMarkType() == 0){
                String url = SysConfig.cfgMap.get("WEIBO_ANALYSIS_BASE_URL");
                String action = SysConfig.cfgMap.get("WEIBO_ANALYSIS_TASK_CONFIRM");
                if ( !url.startsWith("http://") && !url.startsWith("https://") ) {
                    return baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
                }
                if (url.endsWith("/")) {
                    url = url.substring(-1);
                }
                if (!action.startsWith("/")) {
                    action = "/" + action;
                }
                double totalFee = task.getForwardCount() * 0.01;
                //TODO：支付情况

                url = url + action;
                StringBuilder builder = new StringBuilder();
                builder.append("?userTag=" + userDto.getUserId());
                builder.append("&format=json");
                builder.append("&platformTag=" + BusinessConstant.PARTNER_ID_WYQ);
                builder.append("&taskTicket=" + ticket);
                builder.append("&totalFee=0.00");
                String sendResponse = HttpRequestUtils.sendGet(url,builder.toString());
                baseDto = JSONObject.parseObject(sendResponse,BaseDto.class);

                if (baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                    JSONObject object = JSONObject.parseObject(sendResponse);
                    WeiboAnalysisTaskDto taskDto = JSONObject.parseObject(object.getJSONObject("task").toJSONString(), WeiboAnalysisTaskDto.class);
                    taskDto.setPayment(1);
                    map = CommonUtils.entityToMap(taskDto);
                    map.put("taskId",taskDto.getTaskId());
                    jsonParams = new JSONObject(map);
                    weiboAnalysisService.updateTask(jsonParams);
                }else {
                    //app-微博传播分析失败，返还扣除的微积分
//                    userViewVo = wjfService.rightModify(request, response, user, 5, 1,
//                            task.getForwardCount(), task.getTaskId() + "", "app-微博传播分析失败，返还扣除的微积分");
                }
            }else {

                Map<String, Object> params1 = CommonUtils.getIntraBusinessParams(userDto.getUserId());
                params1.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
                params1.put("ticket", ticket);
                params1.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userDto.getUserId())));
                String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("wfxConfirmTask"), params1);
                baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
                if (baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                    jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("wfxGetTaskByTicket"), params1);
                }
                baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
                JSONObject object = JSONObject.parseObject(jsonStr);
                WeiBoAnalysisTask task1 = JSONObject.parseObject(object.getJSONObject("task").toJSONString(), WeiBoAnalysisTask.class);

                if (task1 != null) {
                    if (task1.getPayment() == 1){

                        params1 = CommonUtils.entityToMap(task1);
                        params1.put("taskId",task1.getTaskId());
                        jsonParams = new JSONObject(params1);
                        weiboAnalysisService.updateTask(jsonParams);
                    }else {
//                        userViewVo = wjfService.rightModify(request, response, user, 5, 1,
//                                task.getForwardCount(), task.getTaskId() + "", "app-微博传播分析失败，返还扣除的微积分");
//                        String message = view.getMessage();
//                        throw new Exception(message);
                    }
                }
            }
        }else {
            baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_CONFIRM_CODE_4033);
        }
        return baseDto;
    }

    @ApiOperation(value = "任务进度", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "任务进度") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "progress", headers = "Accept=application/json")
    public BaseDto progress(@ApiParam(value = "筛选条件(userEncode:用户Encode,tickets:任务标识)") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("tickets") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        String tickets = jsonParams.getString("tickets") == null ? "" : jsonParams.getString("tickets");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        List<WeiBoAnalysisTask> list = new ArrayList<WeiBoAnalysisTask>();

        String[] strList = tickets.split(",");
        String str = null;
        for(String taskTicket : strList) {

            Map<String, Object> map = new HashMap<>();
            map.put("userId",userDto.getUserId());
            map.put("taskTicket",taskTicket);
            jsonParams = new JSONObject(map);
            WeiBoAnalysisTask task = weiboAnalysisService.getTask(jsonParams);

            if (task.getMarkType() == 1){
                if (StringUtils.isEmpty(str)){
                    str = task.getTaskTicket();
                }else {
                    str += "/"+task.getTaskTicket();
                }
            }else {

                String url = SysConfig.cfgMap.get("WEIBO_ANALYSIS_BASE_URL");
                String action = SysConfig.cfgMap.get("WEIBO_ANALYSIS_TASK_STATUS");
                if ( !url.startsWith("http://") && !url.startsWith("https://") ) {
                    return baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
                }
                if (url.endsWith("/")) {
                    url = url.substring(-1);
                }
                if (!action.startsWith("/")) {
                    action = "/" + action;
                }

                url = url + action;
                StringBuilder builder = new StringBuilder();
                builder.append("?userTag=" + userDto.getUserId());
                builder.append("&format=json");
                builder.append("&platformTag=" + BusinessConstant.PARTNER_ID_WYQ);
                builder.append("&taskTicket=" + taskTicket);

                String sendResponse = HttpRequestUtils.sendGet(url,builder.toString());
                baseDto = JSONObject.parseObject(sendResponse,BaseDto.class);
                if (baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                    JSONObject object = JSONObject.parseObject(sendResponse);
                    WeiBoAnalysisTask task1 = JSONObject.parseObject(object.getJSONObject("task").toJSONString(), WeiBoAnalysisTask.class);
                    task1.setPayment(1);
                    map = CommonUtils.entityToMap(task1);
                    map.put("taskId",task1.getTaskId());
                    jsonParams = new JSONObject(map);
                    weiboAnalysisService.updateTask(jsonParams);
                    list.add(task1);
                }
            }
        }
        if (StringUtils.isNotEmpty(str)){

            Map<String, Object> params1 = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            params1.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
            params1.put("tickets", str);
            params1.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userDto.getUserId())));
            String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("wfxQuerySolidifySchedule"), params1);

            baseDto = JSONObject.parseObject(jsonStr,PageDto.class);

            if (baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                JSONObject object = JSONObject.parseObject(jsonStr);
                List<AnalysisSolidifyDto> taskList = object.getJSONArray("solidifyList").toJavaList(AnalysisSolidifyDto.class);
                for (AnalysisSolidifyDto solidify : taskList) {

                    Map<String, Object> map = new HashMap<>();
                    map.put("userId",userDto.getUserId());
                    map.put("taskTicket",solidify.getTicket());
                    jsonParams = new JSONObject(map);
                    WeiBoAnalysisTask task = weiboAnalysisService.getTask(jsonParams);
                    task.setAnalysisStatus(solidify.getAnalysisStatus());

                    list.add(task);
                    params1 = CommonUtils.entityToMap(task);
                    params1.put("wUserId",userDto.getUserId());
                    params1.put("wTaskTicket",task.getTaskTicket());
                    jsonParams = new JSONObject(params1);
                    weiboAnalysisService.updateTask(jsonParams);
                }
            }
        }
        List<WeiboAnalysisTaskDto> dtoList = createAnalysisTaskDtoList(list);
        return baseDto.setData(dtoList);
    }



    @ApiOperation(value = "删除任务", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "删除任务") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "deleteTask", headers = "Accept=application/json")
    public BaseDto deleteTask(@ApiParam(value = "筛选条件(userEncode:用户Encode,ticket:任务标识)") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("ticket") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        String ticket = jsonParams.getString("ticket") == null ? "" : jsonParams.getString("ticket");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Map<String,Object> params1 = new HashMap<>();
        params1.put("userId",userDto.getUserId());
        params1.put("taskTicket",ticket);
        jsonParams = new JSONObject(params1);
        WeiBoAnalysisTask task = weiboAnalysisService.getTask(jsonParams);

        if (task == null){
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_DELETE_CODE_4034);
        }
        if (task.getMarkType() == 1){
            params1 = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            params1.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
            params1.put("taskId", task.getTaskId());
            params1.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(userDto.getUserId())));
            String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("wfxDeleteTask"), params1);
            baseDto = JSON.parseObject(jsonStr, BaseDto.class);
            if (baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                task.setStatus(0);
                task.setUpdateTime(new Date());
                params1 = CommonUtils.entityToMap(task);
                params1.put("wUserId",task.getUserId());
                params1.put("wTaskTicket",task.getTaskTicket());
                jsonParams = new JSONObject(params1);
                weiboAnalysisService.updateTask(jsonParams);
            }else {

            }
        } else {
            params1 = CommonUtils.entityToMap(task);
            params1.put("wUserId",task.getUserId());
            params1.put("wTaskTicket",task.getTaskTicket());
            jsonParams = new JSONObject(params1);
            weiboAnalysisService.updateTask(jsonParams);
        }
        return  baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
    }





    private final static WeiboAnalysisController INSTANCE  = new WeiboAnalysisController();
    private WeiboAnalysisController(){}
    public static WeiboAnalysisController getInstance(){
        return INSTANCE;
    }

    public List<WeiboAnalysisTaskDto> createAnalysisTaskDtoList(List<WeiBoAnalysisTask> taskList) {
        List<WeiboAnalysisTaskDto> result = new ArrayList<WeiboAnalysisTaskDto>();

        if (taskList != null) {
            for (WeiBoAnalysisTask task : taskList) {
                WeiboAnalysisTaskDto taskInfo = createAnalysisTaskDto(task);
                result.add(taskInfo);
            }
        }

        return result;
    }

    @RequestMapping(value = "/queryLatestWeibo")
    public BaseDto<WeiBoAnalysisTask> queryLatestWeibo(Integer userId){
        BaseDto<WeiBoAnalysisTask> baseDto=new BaseDto<WeiBoAnalysisTask>();
        Map<String,Object> objectMap=new HashMap<>();
        if(userId!=null && userId!=0){
            objectMap.put("userId",userId);
        }
        JSONObject jsonObject=new JSONObject(objectMap);
        if(userId==null || userId==0){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            List<WeiBoAnalysisTask> weiBoAnalysisTaskList=weiboAnalysisService.getLatestWeibo(jsonObject);
            if(weiBoAnalysisTaskList!=null && weiBoAnalysisTaskList.size()>0){
                baseDto.setData(weiBoAnalysisTaskList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping(value = "/queryWeiboAnalysisList")
    public BaseDto<WeiBoAnalysisTask> queryWeiboAnalysisList(Integer weiboId ,String weiboURL,int userId){
        BaseDto<WeiBoAnalysisTask> baseDto=new BaseDto<WeiBoAnalysisTask>();

        if(TextUtils.isEmpty(weiboURL)){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Map<String,Object> objectMap=new HashMap<>();
            if(weiboId !=null && weiboId>0){
                objectMap.put("taskId",weiboId);
            }
            objectMap.put("weiboUrl",weiboURL);
            objectMap.put("userId",userId);

            JSONObject jsonObject=new JSONObject(objectMap);
            List<WeiBoAnalysisTask> weiBoAnalysisTaskList=weiboAnalysisService.getH5TaskList(jsonObject);
            if(weiBoAnalysisTaskList!=null && weiBoAnalysisTaskList.size()>0){
                baseDto.setData(weiBoAnalysisTaskList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping(value = "/queryWeiboExistsByUrl")
    public BaseDto<WeiBoAnalysisTask> queryWeiboExistsByUrl(@RequestBody WeiBoAnalysisTask tempWeibo){
        BaseDto<WeiBoAnalysisTask> baseDto=new BaseDto<WeiBoAnalysisTask>();
        if(tempWeibo==null){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Map<String,Object> objectMap=new HashMap<>();
            objectMap.put("taskId",tempWeibo.getTaskId());
            objectMap.put("weiboUrl",tempWeibo.getWeiboUrl());
            objectMap.put("userId",tempWeibo.getUserId());
            objectMap.put("payment",tempWeibo.getPayment());
            objectMap.put("status",tempWeibo.getStatus());
            JSONObject jsonObject=new JSONObject(objectMap);
            List<WeiBoAnalysisTask> weiBoAnalysisTaskList=weiboAnalysisService.queryWeiboExistsByUrl(jsonObject);
            if(weiBoAnalysisTaskList!=null && weiBoAnalysisTaskList.size()>0){
                baseDto.setData(weiBoAnalysisTaskList.get(0)).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }

    @RequestMapping(value = "/queryWeiboAnalysisListByUserId")
    public BaseDto queryWeiboAnalysisListByUserId(String userId){
        BaseDto baseDto=new BaseDto();
        Map<String,Object> objectMap=new HashMap<>();
        if(!TextUtils.isEmpty(userId)){
            objectMap.put("userId",userId);
        }
        JSONObject jsonObject=new JSONObject(objectMap);
        if(TextUtils.isEmpty(userId)){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            List<WeiBoAnalysisTask> weiBoAnalysisTaskList=weiboAnalysisService.getH5TaskList(jsonObject);
            if(weiBoAnalysisTaskList!=null && weiBoAnalysisTaskList.size()>0){
                for (WeiBoAnalysisTask task : weiBoAnalysisTaskList) {
                    if(((System.currentTimeMillis()-task.getCreateTime().getTime())>(15*60*1000)) && task.getPayment()!=1){
                        task.setIsExceed(1);
                    }else{
                        task.setIsExceed(0);
                    }
                    if (task.getForwardCount() == null){
                        task.setForwardCount(Integer.valueOf(0));
                    }
                    task.setCreateTimeStr(DateUtils.format(task.getCreateTime(),DateUtils.FORMAT_SHORT));
                    task.setPublishedTimeStr(DateUtils.format(task.getPublishedTime(),DateUtils.FORMAT_SHORT));
                }
                baseDto.setData(weiBoAnalysisTaskList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }

    @RequestMapping(value = "/delWeiboAnalysis")
    public BaseDto delWeiboAnalysis(String weiboId){
        BaseDto baseDto=new BaseDto();
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("taskId",weiboId);
        objectMap.put("status",0);
        JSONObject jsonObject=new JSONObject(objectMap);
        if(TextUtils.isEmpty(weiboId)){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Boolean flag=weiboAnalysisService.updateTask(jsonObject);
            if(flag!=null && flag){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping(value = "/doSaveWeibo")
    @ResponseBody
    public BaseDto doSaveWeibo(@RequestBody WeiBoAnalysisTask analysisTask){
        BaseDto baseDto=new BaseDto();
//        Map<String,Object> objectMap= CommonUtils.entityToMap(analysisTask);
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("userId",analysisTask.getUserId());
        objectMap.put("weiboUrl",analysisTask.getWeiboUrl());
        objectMap.put("weiboContent",analysisTask.getWeiboContent());
        objectMap.put("forwardCount",analysisTask.getForwardCount());
        objectMap.put("commentCount",analysisTask.getCommentCount());
        objectMap.put("praiseCount",analysisTask.getPraiseCount());
        objectMap.put("publishedTime",analysisTask.getPublishedTime());
        objectMap.put("weiboUid",analysisTask.getWeiboUid());
        objectMap.put("weiboNickname",analysisTask.getWeiboNickname());
        objectMap.put("weiboUserhead",analysisTask.getWeiboUserhead());
        objectMap.put("verifiedType",analysisTask.getWeiboUserhead());
        objectMap.put("payment",analysisTask.getPayment());
        objectMap.put("shareCode",analysisTask.getShareCode());
        objectMap.put("sharePlatform",analysisTask.getSharePlatform());
        objectMap.put("shareReadCount",analysisTask.getShareRedCount());
        objectMap.put("analysisStatus",analysisTask.getAnalysisStatus());
        objectMap.put("createTime",analysisTask.getCreateTime());
        objectMap.put("markType",analysisTask.getMarkType());
        objectMap.put("taskTicket",analysisTask.getTaskTicket());
        JSONObject jsonObject=new JSONObject(objectMap);
        if(analysisTask==null){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Boolean flag=weiboAnalysisService.insertTask(jsonObject);
            if(flag!=null && flag){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }
    @RequestMapping(value = "/doModifyWeiboPayment")
    @ResponseBody
    public BaseDto<Boolean> doModifyWeiboPayment(@RequestBody WeiBoAnalysisTask analysisTask){
        BaseDto<Boolean> baseDto=new BaseDto<Boolean>();
        Map<String,Object> objectMap= com.miduchina.wrd.util.CommonUtils.entityToMap(analysisTask);
        objectMap.put("weiboContent", analysisTask.getWeiboContent());
            JSONObject jsonObject=new JSONObject(objectMap);
            if(analysisTask==null){
                baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            Boolean flag=weiboAnalysisService.updateTask(jsonObject);
            if(flag!=null && flag){
                baseDto.setData(flag).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }



    public WeiboAnalysisTaskDto createAnalysisTaskDto(WeiBoAnalysisTask task) {

        WeiboAnalysisTaskDto result = BeanUtils.copyProperties(task, WeiboAnalysisTaskDto.class);
        result.setIsSample(0);

        if (result.getAnalysisStatus() == 5){
            result.setAnalysisStatus(3);
        }else  if (result.getPayment() == 0 && result.getAnalysisStatus() == 1){
            result.setAnalysisStatus(4);
        }else {
            result.setAnalysisStatus(result.getAnalysisStatus());
        }
        if (result.getPayment() == 1 && result.getAnalysisStatus() == 4){
            result.setAnalysisStatus(2);
        }

        String shareUrl = SysConfig.cfgMap.get("WEIBO_ANALYSIS_SHARE_URL") + task.getShareCode() + "&isApp=1";
        result.setShareUrl(shareUrl);
        if (result.getAnalysisStatus() == 4 && result.getCreateTime() != null){
            long min = DateUtils.getDistMins(result.getCreateTime(),new Date());
            if (min > 15){
                result.setIsExceed(1);
            }
        }
        return  result;
    }




}
