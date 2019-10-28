package com.miduchina.wrd.api.controller.v3.eventanalysis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.controller.v3.productsanalysis.ProductsAnalysisController;
import com.miduchina.wrd.api.service.analysis.ClientAnalysisSampleService;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.analysis.AnalysisDto;
import com.miduchina.wrd.dto.analysis.AnalysisSampleDto;
import com.miduchina.wrd.dto.analysis.eventanalysis.EventAnalysisReportDto;
import com.miduchina.wrd.dto.analysis.eventanalysis.EventAnalysisTaskDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.other.util.StringUtil;
import com.miduchina.wrd.po.analysis.AnalysisSample;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.xd.tools.method.wyq.app.db.WyqAppDbMethodV1;
import com.xd.tools.pojo.AnalysisTask;
import com.xd.tools.pojo.DbParams;
import com.xd.tools.view.AnalysisTaskView;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by shitao on 2019-05-08 15:25.
 *
 * @author shitao
 */
@Slf4j
@Api(tags = {"事件分析"})
@RestController
@RequestMapping("api/v3/eventAnalysis")
public class EventAnalysisController extends BaseController {

    @Autowired
    private ClientAnalysisSampleService sampleService;

    @ApiOperation(value = "事件分析验证接口", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析验证接口") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "validate", headers = "Accept=application/json")
    public BaseDto validate( @ApiParam(value = "筛选条件(userEncode:用户Encode,contentType:分析类型(1:全网，2:微博),keyword:关键词语,filterKeyword:排除词语,startTime:开始时间,endTime:结束时间,sensitiveCheck:是否开启敏感词校验(0:高级 1:低级 default:1))") @RequestParam Map<String,Object> params) {


        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        if(params.get("userEncode") == null|| params.get("contentType") == null ||
                params.get("keyword") == null  || params.get("startTime") == null ||
                params.get("endTime") == null ){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Integer contentType = jsonParams.getInteger("contentType") == null ? 1:jsonParams.getInteger("contentType");
        String keyword = jsonParams.getString("keyword") == null ? "":jsonParams.getString("keyword");
        String filterKeyword = jsonParams.getString("filtaerKeyword") == null ? "":jsonParams.getString("filterKeyword");
        String startTime = jsonParams.getString("startTime") == null ? "":jsonParams.getString("startTime");
        String endTime = jsonParams.getString("endTime") == null ? "":jsonParams.getString("endTime");
        String sensitiveCheck = jsonParams.getString("sensitiveCheck") == null ? "":jsonParams.getString("sensitiveCheck");
        /**
         * 事件分析关键词长度要两个以上
         */
        if(keyword.length()<=1){
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_KEYWORD_SIZEMIN_CODE_4022);
        }

        String key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_EVENT_ANALYSIS_CREATE + userEncode + "_" + MD5Utils.MD5Encode(keyword));
        if (!RedisUtils.lockUserOperate(key, SystemConstants.JEDIS_SESSION_TIME)) {
            return baseDto.setCodeMessage(CodeConstant.E_REQUEST_HANDLE_IGNORE_CODE_0001);
        }

        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(Integer.valueOf(userDto.getUserId()));
        buParams.put("keyword",keyword);
        if (StringUtils.isNotBlank(filterKeyword)) {
            buParams.put("filterKeyword",filterKeyword);
        }
        buParams.put("startTime",startTime);
        buParams.put("endTime", endTime);
        /**
         * 1全网事件分析 2微博事件分析
         */
        buParams.put("contentType",contentType);

        buParams.put("sensitiveCheck",sensitiveCheck);


        log.info("businessAPI_s===>>> {}+{}","eventValidate",userDto.getUserId());
        String jsonStr =  BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventExpectAnalysis"),buParams);
        baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
        JSONObject object = JSONObject.parseObject(jsonStr);
        EventAnalysisTaskDto dto = JSONObject.parseObject(object.getJSONObject("tasks").toJSONString(),EventAnalysisTaskDto.class);

        log.info("businessAPI_e===>>> {}+{}","eventValidate",userDto.getUserId());

        if (dto==null){
            RedisUtils.removeAttribute(key);
            return baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("系统开小差了，请稍后再试！~");
        }
        if(dto.isSensitive()){
            RedisUtils.removeAttribute(key);
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_KEYWORD_CODE_4032);
        }
        baseDto.setData(dto);
        /**
         *  记录用户操作日志:操作类型：1-增 2-删 3-改 4-查
         */
        try {
            int operateType=BusinessConstant.STATUS_VALUE_4;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            map.put("EventAnalysisTaskDto",dto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            com.miduchina.wrd.other.util.CommonUtils.opreateLog(request, null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto,operateType,null,dto);
            baseDto.setMessage(CodeConstant.SUCCESS_CODE).setData(generateTaskInfo(dto));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            RedisUtils.removeAttribute(key);
            if (dto==null) {
                return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2000);
            }
        }
        return baseDto;
    }


    @ApiOperation(value = "创建事件分析", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "创建事件分析") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "create", headers = "Accept=application/json")
    public BaseDto create( @ApiParam(value = "筛选条件(userEncode:用户Encode,title:任务名称,contentType:分析类型(1:全网，2:微博),keyword:关键词语,filterKeyword:排除词语,startTime:开始时间,endTime:结束时间)") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null|| params.get("contentType") == null ||
                params.get("keyword") == null || params.get("filtaerKeyword") == null || params.get("startTime") == null ||
                params.get("endTime") == null ){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        String title = jsonParams.getString("title") == null ? "":jsonParams.getString("title");
        Integer contentType = jsonParams.getInteger("contentType") == null ? 1:jsonParams.getInteger("contentType");
        String keyword = jsonParams.getString("keyword") == null ? "":jsonParams.getString("keyword");
        String filterKeyword = jsonParams.getString("filterKeyword") == null ? "":jsonParams.getString("filterKeyword");
        String startTime = jsonParams.getString("startTime") == null ? "":jsonParams.getString("startTime");
        String endTime = jsonParams.getString("endTime") == null ? "":jsonParams.getString("endTime");

        if(keyword.length()<=1){
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_KEYWORD_SIZEMIN_CODE_4022);
        }

        Boolean isCreate = true;
        /**
         * 判断事件分析有效次数
         */
        if (contentType == BusinessConstant.EVENT_CONTENTTYPE_ALL){
            if(userDto.getUserAnalysisValidCount() <= 0){
                isCreate = false;
            }
        }else if (contentType== BusinessConstant.EVENT_CONTENTTYPE_WB){
            if(userDto.getUserWeiboAnalysisValidCount() <= 0){
                isCreate = false;
            }
        }
        if (!isCreate){
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_VALID_COUNT_CODE_4021);
        }
        String key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_EVENT_ANALYSIS_CREATE + userEncode + "_" + MD5Utils.MD5Encode(keyword));
        if (!RedisUtils.lockUserOperate(key, SystemConstants.JEDIS_SESSION_TIME)) {
            return baseDto.setCodeMessage(CodeConstant.E_REQUEST_HANDLE_IGNORE_CODE_0001);
        }

        Map<String, Object> bParams= new HashMap<String, Object>();
        bParams.put("date",BusinessConstant.STATUS_VALUE);
        bParams.put("startTime",startTime);
        bParams.put("endTime",endTime);
        bParams.put("keyword",keyword);
        if(StringUtils.isNotEmpty(filterKeyword)) {
            bParams.put("filterKeyword",filterKeyword);
        }

        DbParams dbParams = new DbParams();
        if (contentType == BusinessConstant.EVENT_CONTENTTYPE_WB) {
            dbParams.setAnalysisSource(72);
            dbParams.setOrigin("2");
        } else {
            dbParams.setAnalysisSource(74);
        }
        dbParams.setTitle(title);
        dbParams.setStepType(BusinessConstant.STATUS_VALUE_5);
        dbParams.setParamsJson(JSON.toJSONString(bParams));

        AnalysisTaskView taskView = null;

        log.info("WyqAppDbMethodV1.analysisTaskInsert_s===>>> {}+{}","eventCreate",userDto.getUserId());
        try {
            taskView = WyqAppDbMethodV1.analysisTaskInsert(String.valueOf(userDto.getUserId()), dbParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("WyqAppDbMethodV1.analysisTaskInsert_e===>>> {}+{}","eventCreate",userDto.getUserId());

        if (taskView != null &&
                !(StringUtils.equalsIgnoreCase(taskView.getCode(), CodeConstant.SUCCESS_CODE) ||
                        StringUtils.equalsIgnoreCase(taskView.getCode(), CodeConstant.DATA_API_SUCCESS_CODE))) {
            return baseDto.setCode(taskView.getCode()).setMessage(taskView.getMessage());
        }

        AnalysisTask task = taskView.getAnalysisTask();
        Map<String, Object> pms = CommonUtils.getIntraBusinessParams(Integer.valueOf(userDto.getUserId()));
        pms.put("createType", String.valueOf(BusinessConstant.EVENT_ANALYSIS_CREATE));
        pms.put("title", title);
        pms.put("keyword", keyword);
        if (StringUtils.isNotEmpty(filterKeyword)) {
            pms.put("filterKeyword", filterKeyword);
        }
        pms.put("startTime", startTime);
        pms.put("endTime", endTime);
        pms.put("contentType", String.valueOf(contentType));
        pms.put("solrCount", String.valueOf(task.getAnalysisSolrFirstCountExpect()));
        pms.put("totalConsume", String.valueOf(task.getAnalysisTotalConsumeExpect()));

        log.info("businessAPI_s===>>> {}+{}","eventCreate",userDto.getUserId());
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventAddTask"),pms);
        log.info("businessAPI_e===>>> {}+{}","eventCreate",userDto.getUserId());

        baseDto =  JSONObject.parseObject(jsonStr,BaseDto.class);
        /**
         * 记录用户操作日志
         */
        try {

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            int operateType = BusinessConstant.STATUS_VALUE_1;
            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, baseDto);



        } catch (Exception re) {
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage(re.getMessage());
        } finally {
            RedisUtils.removeAttribute(key);
        }

        return baseDto;
    }


    @ApiOperation(value = "更新事件分析", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "更新事件分析") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "update", headers = "Accept=application/json")
    public BaseDto update( @ApiParam(value = "筛选条件(userEncode:用户Encode,taskId:任务ID,type:(2:更新 3:重新分析),startTime:开始时间,endTime:结束时间),keyword:关键词,filterKeyword:排除词语") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null|| params.get("type") == null ||
                params.get("taskId") == null || params.get("startTime") == null ||
                params.get("endTime") == null ){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        String title = jsonParams.getString("title") == null ? "":jsonParams.getString("title");
        Integer type = jsonParams.getInteger("type") == null ? 1:jsonParams.getInteger("type");
        Integer taskId = jsonParams.getInteger("taskId") == null ? -1:jsonParams.getInteger("taskId");
        String startTime = jsonParams.getString("startTime") == null ? "":jsonParams.getString("startTime");
        String endTime = jsonParams.getString("endTime") == null ? "":jsonParams.getString("endTime");
        String keyword = jsonParams.getString("keyword") == null ? "":jsonParams.getString("keyword");
        String filterKeyword = jsonParams.getString("filterKeyword") == null ? "":jsonParams.getString("filterKeyword");

        String key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_EVENT_ANALYSIS_UPDATE + userEncode + "_" + taskId);
        if (!RedisUtils.lockUserOperate(key, SystemConstants.JEDIS_SESSION_TIME)) {
            return baseDto.setCodeMessage(CodeConstant.E_REQUEST_HANDLE_IGNORE_CODE_0001);
        }


        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(Integer.valueOf(userDto.getUserId()));
        buParams.put("taskId", String.valueOf(taskId));
        buParams.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
        String jsonStr =  BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventGetTask"),buParams);

        baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
        JSONObject object = JSONObject.parseObject(jsonStr);
        EventAnalysisTaskDto dto = JSONObject.parseObject(object.getJSONObject("task").toJSONString(),EventAnalysisTaskDto.class);

        if(dto==null || (dto !=null && dto.getStatus()== BusinessConstant.STATUS_VALUE_2)){
            return  baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_TASK_NOT_EXIST_CODE_4023);
        }

        if(dto.getUpdateNum() >= BusinessConstant.EVENT_ANALYSIS_MAX_UPDATE_COUNT
                && type == BusinessConstant.EVENT_ANALYSIS_UPDATE){
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_TASK_UPDATED_MAX_COUNT_CODE_4024);
        }

        if (type == BusinessConstant.EVENT_ANALYSIS_UPDATE && StringUtils.isEmpty(startTime)
                && StringUtils.isEmpty(endTime)) {
            return baseDto.setCodeMessage(CodeConstant.E_LACK_NECESSARY_SYSTEM_PARAM_CODE_101);
        }

        if (dto.getAnalysisStatus() != BusinessConstant.EVENT_ANALYSIS_STATUS_FINISHED
                && dto.getAnalysisStatus() != BusinessConstant.EVENT_ANALYSIS_STATUS_FAILED) {
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_ONLY_UPDATE_FINISHED_TASK_CODE_4030);
        }


        Map<String, Object> pms = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        pms.put("taskId", String.valueOf(dto.getTaskId()));
        if (type == BusinessConstant.STATUS_VALUE_2) {
            pms.put("startTime",startTime);
            pms.put("endTime", endTime);
        }
        if (StringUtils.isNotEmpty(keyword)){
            pms.put("keyword", keyword);
        }
        if (StringUtils.isNotEmpty(keyword)){
            pms.put("filterKeyword", filterKeyword);
        }
        pms.put("createType", String.valueOf(type));
        pms.put("title", title);

        jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventAddTask"),pms);
        baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
        if (baseDto != null ) {
            String messageStr = baseDto.getMessage();
            if( messageStr.indexOf("transactionId") != -1) {
                messageStr = messageStr.substring(0,messageStr.indexOf("transactionId"));
                baseDto.setMessage(messageStr);
            }
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            int operateType = BusinessConstant.STATUS_VALUE_3;
            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, baseDto);

        } catch (Exception re) {
            baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage(re.getMessage());
        } finally {
            RedisUtils.removeAttribute(key);
        }
        return baseDto;
    }


    @ApiOperation(value = "事件分析列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "taskList", headers = "Accept=application/json")
    public PageDto taskList(@ApiParam(value = "筛选条件(userEncode:用户Encode,contentType:(1:全网，2:微博),page:页码,pageSize:页面大小)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null|| params.get("contentType") == null ||
                params.get("page") == null || params.get("pageSize") == null  ){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Integer contentType = jsonParams.getInteger("contentType") == null ? 1:jsonParams.getInteger("contentType");
        Integer page = jsonParams.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:jsonParams.getInteger("page");
        Integer pageSize = jsonParams.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:jsonParams.getInteger("pageSize");

        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        buParams.put("contentType", String.valueOf(contentType));
        buParams.put("page", String.valueOf(page));
        buParams.put("pageSize", String.valueOf(pageSize));

        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("taskList"),buParams);

        baseDto = JSONObject.parseObject(jsonStr,PageDto.class);
        JSONObject object = JSONObject.parseObject(jsonStr);
        List<EventAnalysisTaskDto> taskList = object.getJSONArray("tasks").toJavaList(EventAnalysisTaskDto.class);

        //记录用户操作日志
        try {
            int operateType = BusinessConstant.STATUS_VALUE_4;
            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, taskList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto.setData(taskList);
    }


    @ApiOperation(value = "事件分析进度", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析进度") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "schedule", headers = "Accept=application/json")
    public BaseDto schedule(@ApiParam(value = "筛选条件(userEncode:用户Encode,contentType:(1:全网，2:微博),taskIds:任务ids,tickets:任务tickets)") @RequestParam Map<String,Object> params) {


        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null|| params.get("contentType") == null){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Integer contentType = jsonParams.getInteger("contentType") == null ? 1:jsonParams.getInteger("contentType");
        String taskIds = jsonParams.getString("taskIds") == null ? "":jsonParams.getString("taskIds");
        String tickets = jsonParams.getString("tickets") == null ? "":jsonParams.getString("tickets");

        Integer type = BusinessConstant.STATUS_VALUE_0;
        ArrayList<String> list = null;
        if (StringUtils.isNotEmpty(taskIds)){
            list = new ArrayList<>();
            String[] strIds = taskIds.split(",");
            for (String str:strIds){
                list.add(str);
            }
        }
        if (StringUtils.isNotEmpty(tickets) && list == null){
            list = new ArrayList<>();
            String[] strTickets = tickets.split(",");
            for (String str:strTickets){
                list.add(str);
            }
            type = BusinessConstant.STATUS_VALUE_1;
        }

        List<EventAnalysisTaskDto> listDto = new ArrayList<>();
        for (String str:list){
            Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            if (type == BusinessConstant.STATUS_VALUE_1){
                buParams.put("ticket", str);
            }else {
                buParams.put("taskId", str);
            }
            buParams.put("contentType", contentType);
            String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventGetTask"),buParams);
            baseDto = JSONObject.parseObject(jsonStr,BaseDto.class);
            JSONObject object = JSONObject.parseObject(jsonStr);
            EventAnalysisTaskDto dto = JSONObject.parseObject(object.getJSONObject("task").toJSONString(),EventAnalysisTaskDto.class);
            if (dto != null){
                dto = generateTaskInfo(dto);
            }
            listDto.add(dto);
        }
        return baseDto.setData(listDto);
    }

    @ApiOperation(value = "事件分析报告列表", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析报告列表") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "reportList", headers = "Accept=application/json")
    public PageDto reportList(@ApiParam(value = "筛选条件(userEncode:用户Encode,contentType:(1:全网，2:微博),page:页码,pageSize:页面大小)") @RequestParam Map<String,Object> params) {

        PageDto baseDto = new PageDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null|| params.get("contentType") == null ||
                params.get("page") == null || params.get("pageSize") == null  ){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Integer contentType = jsonParams.getInteger("contentType") == null ? 1:jsonParams.getInteger("contentType");
        Integer page = jsonParams.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE:jsonParams.getInteger("page");
        Integer pageSize = jsonParams.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE:jsonParams.getInteger("pageSize");

        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        buParams.put("contentType", String.valueOf(contentType));
        buParams.put("page", String.valueOf(page));
        buParams.put("pageSize", String.valueOf(pageSize));

        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventReportList"),buParams);
        baseDto = JSONObject.parseObject(jsonStr,PageDto.class);

        JSONObject object = JSONObject.parseObject(jsonStr);
        List<EventAnalysisReportDto> reportList = object.getJSONArray("reports").toJavaList(EventAnalysisReportDto.class);

        if(CollectionUtils.isNotEmpty(reportList)){
            reportList = generateReportInfoList(reportList);
        }else {
            reportList = new ArrayList<>();
            int sampleType = BusinessConstant.STATUS_VALUE_2;
            if (contentType == BusinessConstant.EVENT_CONTENTTYPE_WB) {
                sampleType = BusinessConstant.STATUS_VALUE_3;
            }
            buParams = new HashMap<String, Object>();
            buParams.put("sampleType",sampleType);
            jsonParams = new JSONObject(buParams);
            AnalysisSample sample = sampleService.getSample(jsonParams);
            AnalysisSampleDto serialDto = BeanUtils.copyProperties(sample, AnalysisSampleDto.class);

            if (serialDto != null) {
                EventAnalysisReportDto ear = new EventAnalysisReportDto();
                ear.setIsSample(BusinessConstant.STATUS_VALUE_1);
                ear.setIncidentTitle(sample.getTitle());
                ear.setReportURL(sample.getUrl());
                ear.setShareUrl(sample.getShareUrl());
                ear.setCaseType(sampleType);
                ear.setCreateTime(sample.getCreateTime());
                reportList.add(ear);
            } else {
                String isapp = "&isApp=1";
                EventAnalysisReportDto ear = new EventAnalysisReportDto();
                ear.setIsSample(BusinessConstant.STATUS_VALUE_1);
                if (contentType == BusinessConstant.EVENT_CONTENTTYPE_ALL) {
                    ear.setIncidentTitle("王宝强离婚案开庭");
                    ear.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL + "oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ" + isapp);
                    ear.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL + "oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ");
                    ear.setCaseType(BusinessConstant.STATUS_VALUE_2);
                    ear.setCreateTime(new Date());
                } else {
                    ear.setIncidentTitle("范冰冰路演遭劝离");
                    ear.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_WB_VISIT_URL + "oh7r8mJGeftshjc0L5XY3pugtrx7gR6a" + isapp);
                    ear.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_WB_VISIT_URL + "oh7r8mJGeftshjc0L5XY3pugtrx7gR6a");
                    ear.setCaseType(BusinessConstant.STATUS_VALUE_3);
                    ear.setCreateTime(new Date());
                }
                reportList.add(ear);
            }
            baseDto.setMaxPage(BusinessConstant.STATUS_VALUE_1);
            baseDto.setTotalCount(Long.valueOf(BusinessConstant.STATUS_VALUE_1));
        }

        //记录用户操作日志
        try {

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            int operateType = BusinessConstant.STATUS_VALUE_4;
            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, reportList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return baseDto.setData(reportList);
    }


    @ApiOperation(value = "事件分析任务删除", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析任务删除") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "eventDelete", headers = "Accept=application/json")
    public BaseDto eventDelete(@ApiParam(value = "筛选条件(userEncode:用户Encode,taskId:任务ID,ticket:任务标识") @RequestParam Map<String,Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");
        String ticket = jsonParams.getString("ticket") == null ? "":jsonParams.getString("ticket");
        Integer taskId = jsonParams.getInteger("taskId") == null ? BusinessConstant.STATUS_VALUE:jsonParams.getInteger("taskId");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        params = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        if (taskId != BusinessConstant.STATUS_VALUE){
            params.put("taskId", String.valueOf(taskId));
        }
        params.put("ticket", ticket);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventDeleteTask"),params);
        baseDto = JSONObject.parseObject(jsonStr,PageDto.class);


        try {
            int operateType= BusinessConstant.STATUS_VALUE_2;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            CommonUtils.opreateLog(request,null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return baseDto;
    }


    @ApiOperation(value = "事件分析报告删除", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析报告删除") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "reportDelete", headers = "Accept=application/json")
    public BaseDto reportDelete(@ApiParam(value = "筛选条件(userEncode:用户Encode,ticket:rm,reportId:报告ID") @RequestParam Map<String,Object> params) {
        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");
        String ticket = jsonParams.getString("ticket") == null ? "":jsonParams.getString("ticket");
        Integer reportId = jsonParams.getInteger("reportId") == null ? BusinessConstant.STATUS_VALUE:jsonParams.getInteger("reportId");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        params = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        if (reportId != BusinessConstant.STATUS_VALUE){
            params.put("reportId", String.valueOf(reportId));
        }
        params.put("ticket", ticket);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventDeleteReport"),params);
        baseDto = JSONObject.parseObject(jsonStr,PageDto.class);

        try {
            int operateType= BusinessConstant.STATUS_VALUE_2;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            CommonUtils.opreateLog(request,null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT ,objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto;
    }

    @ApiOperation(value = "事件分析经典案例", produces = "application/json")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "事件分析经典案例") })
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "banner", headers = "Accept=application/json")
    public BaseDto banner(@ApiParam(value = "筛选条件(userEncode:用户Encode,eventBannerType:(分析案例，1：全网，2：微博，3：全部（竞品，全网，微博，传播）)") @RequestParam Map<String,Object> params) {


        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if(params.get("userEncode") == null){
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "":jsonParams.getString("userEncode");
        Integer eventBannerType = jsonParams.getInteger("eventBannerType") == 1 ? BusinessConstant.ANALYSIS_SAMPLE_TYPE_FULL:jsonParams.getInteger("eventBannerType");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request,userEncode);
        if(!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            return  baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();


        //图片路径前缀
        String realPath= request.getSession().getServletContext().getRealPath("");
        realPath =realPath.substring(realPath.lastIndexOf("/")+1, realPath.length());

        List<EventAnalysisReportDto> eventBannerList = new ArrayList<EventAnalysisReportDto>();
        EventAnalysisReportDto ear1 = new EventAnalysisReportDto();
        EventAnalysisReportDto ear2 = new EventAnalysisReportDto();
        EventAnalysisReportDto ear3 = new EventAnalysisReportDto();

        String isapp = "&isApp=1";
        if(eventBannerType == BusinessConstant.ANALYSIS_SAMPLE_TYPE_FULL){
            String url = SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL;

            ear1.setIncidentTitle("王宝强离婚案开庭");
            ear1.setReportURL(url+"oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ"+isapp);
            ear1.setShareUrl(url + "oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ");

            ear1.setEventBannerImgURL("http://"+realPath+"/template/eventImg/qw20161026_1.png");

            ear2.setIncidentTitle("泰国国王逝世");
            ear2.setReportURL(url+"oh7r8mJGefv5zqs%2BejDj9%2FkuqPAY%2BtwW"+isapp);
            ear2.setShareUrl(url + "oh7r8mJGefv5zqs%2BejDj9%2FkuqPAY%2BtwW");
            ear2.setEventBannerImgURL("http://"+realPath+"/template/eventImg/qw20161026_2.png");

            ear3.setIncidentTitle("易建联被裁");
            ear3.setReportURL(url+"oh7r8mJGefsBbbs71xLhePC5G0xPnNfC"+isapp);
            ear3.setShareUrl(url + "oh7r8mJGefsBbbs71xLhePC5G0xPnNfC");
            ear3.setEventBannerImgURL("http://"+realPath+"/template/eventImg/qw20161026_3.png");

        }else if(eventBannerType== BusinessConstant.ANALYSIS_SAMPLE_TYPE_WEIBO){
            String url = SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")  + BusinessConstant.EVENT_REPORT_WB_VISIT_URL;


            ear1.setIncidentTitle("范冰冰路演遭劝离");
            ear1.setReportURL(url+"oh7r8mJGeftshjc0L5XY3pugtrx7gR6a"+isapp);
            ear1.setShareUrl(url+"oh7r8mJGeftshjc0L5XY3pugtrx7gR6a");
            ear1.setEventBannerImgURL("http://"+realPath+"/template/eventImg/wb20161026_1.png");

            ear2.setIncidentTitle("Ella宣布怀孕");
            ear2.setReportURL(url+"oh7r8mJGeftM8k8m3FGFqDW3GWanziel"+isapp);
            ear2.setShareUrl(url+"oh7r8mJGeftM8k8m3FGFqDW3GWanziel");
            ear2.setEventBannerImgURL("http://"+realPath+"/template/eventImg/wb20161026_2.png");

            ear3.setIncidentTitle("V影响力峰会召开");
            ear3.setReportURL(url+"oh7r8mJGefvlq7QeD9yCaSepEXCm2ZFr"+isapp);
            ear3.setShareUrl(url+"oh7r8mJGefvlq7QeD9yCaSepEXCm2ZFr");
            ear3.setEventBannerImgURL("http://"+realPath+"/template/eventImg/wb20161026_3.png");
        }else if(eventBannerType==BusinessConstant.ANALYSIS_SAMPLE_TYPE_ALL) {
            //竞品报告

            Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            buParams.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
            buParams.put("page",BusinessConstant.STATUS_VALUE_1 );
            buParams.put("pageSize", BusinessConstant.STATUS_VALUE_5);
            buParams.put("userEncode", userEncode);
            String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("productTaskList"), params);
            AnalysisDto dto = null;
            if (StringUtils.isNotEmpty(jsonStr)) {
                baseDto = JSONObject.parseObject(jsonStr, PageDto.class);
                JSONObject object = JSONObject.parseObject(jsonStr);
                List<AnalysisDto> list = object.getJSONArray("productAnalysisList").toJavaList(AnalysisDto.class);
                if (list.size()>0){
                     dto =  ProductsAnalysisController.INSTANCE.generateCompetitiveInfo(list.get(0));
                }
            }
            if (dto != null){
                ear3.setIncidentTitle(dto.getTitle());
                ear3.setReportURL(dto.getFilePath());
                ear3.setShareUrl(dto.getShareUrl());
                ear3.setCaseType(BusinessConstant.STATUS_VALUE_1);
            }else {
                ear3.setIncidentTitle("万科、中金黄金的对比分析");
                ear3.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + "/lookShareCodeReport.action?isApp=1&shareCode=1AeOSISybmVn3Rc");
                ear3.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + "/lookShareCodeReport.action?shareCode=1AeOSISybmVn3Rc");
                ear3.setCaseType(BusinessConstant.STATUS_VALUE_1);
            }

            /**
             * 全网事件分析
             */
            buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            buParams.put("contentType", String.valueOf(BusinessConstant.EVENT_CONTENTTYPE_ALL));
            buParams.put("page", String.valueOf(BusinessConstant.STATUS_VALUE_1));
            buParams.put("pageSize", String.valueOf(BusinessConstant.STATUS_VALUE_5));
            jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventReportList"),buParams);
            baseDto = JSONObject.parseObject(jsonStr,PageDto.class);
            JSONObject object = JSONObject.parseObject(jsonStr);
            List<EventAnalysisReportDto> reportList = object.getJSONArray("reports").toJavaList(EventAnalysisReportDto.class);
            EventAnalysisReportDto reportDto = null;
            if (reportList.size()>0){
                reportDto = reportList.get(0);
            }
            if (reportDto != null) {
                EventAnalysisReportDto reportInfoV3 = generateReportInfo(reportDto);
                ear1.setIncidentTitle(reportInfoV3.getIncidentTitle());
                ear1.setReportURL(reportInfoV3.getReportURL());
                ear1.setShareUrl(reportInfoV3.getShareUrl());
                ear1.setCaseType(BusinessConstant.STATUS_VALUE_2);
            } else {
                ear1.setIncidentTitle("王宝强离婚案开庭");
                ear1.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")  + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL
                        + "oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ" + isapp);
                ear1.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")  + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL
                        + "oh7r8mJGefsdLo3bosEVqZm84f3yJNXJ");
                ear1.setCaseType(BusinessConstant.STATUS_VALUE_2);
            }

            /**
             * 微博事件分析
             */
            buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
            buParams.put("contentType", String.valueOf(BusinessConstant.EVENT_CONTENTTYPE_WB));
            buParams.put("page", String.valueOf(BusinessConstant.STATUS_VALUE_1));
            buParams.put("pageSize", String.valueOf(BusinessConstant.STATUS_VALUE_5));
            jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request,IntraBusinessAPIConfig.getValue("eventReportList"),buParams);
            baseDto = JSONObject.parseObject(jsonStr,PageDto.class);
            object = JSONObject.parseObject(jsonStr);
            reportList = object.getJSONArray("reports").toJavaList(EventAnalysisReportDto.class);

            if (reportList.size()>0){
                reportDto = reportList.get(0);
            }
            if (reportDto != null) {
                reportDto = generateReportInfo(reportDto);
                ear2.setIncidentTitle(reportDto.getIncidentTitle());
                ear2.setReportURL(reportDto.getReportURL());
                ear2.setShareUrl(reportDto.getShareUrl());
                ear2.setCaseType(BusinessConstant.STATUS_VALUE_3);
            } else {
                ear2.setIncidentTitle("范冰冰路演遭劝离");
                ear2.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_WB_VISIT_URL
                        + "oh7r8mJGeftshjc0L5XY3pugtrx7gR6a" + isapp);
                ear2.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + BusinessConstant.EVENT_REPORT_WB_VISIT_URL
                        + "oh7r8mJGeftshjc0L5XY3pugtrx7gR6a");
                ear2.setCaseType(BusinessConstant.STATUS_VALUE_3);
            }

            /**
             *  微博传播效果分析报告
             */
//            WeiboAnalysisTask weiboAnalysisTask = weiboAnalysisServiceV3.lastFinishTask(request, userViewElement.getUserId());
//            if (weiboAnalysisTask != null) {
//                WeiboAnalysisTaskInfoV3 info = WeiboAnalysisTaskInfoV3Generate.getInstance().createAnalysisTaskInfo(weiboAnalysisTask);
//
//                EventAnalysisReportInfoV3 ear4 = new EventAnalysisReportInfoV3();
//                ear4.setIncidentTitle("微博传播效果分析报告");
//                ear4.setReportURL(info.getShareUrl());
//                ear4.setShareUrl(info.getShareUrl());
//                ear4.setCaseType(BusinessConstant.ANALYSIS_TYPE_SPREAD);
//                eventBannerList.add(ear4);
//            } else {
//
//                EventAnalysisReportInfoV3 ear4 = new EventAnalysisReportInfoV3();
//                ear4.setIncidentTitle("世卫组织疫苗微博分析报告");
//                ear4.setReportURL(SystemConfig.weiboAnalysisDemo);
//                ear4.setShareUrl(SystemConfig.weiboAnalysisDemo);
//                ear4.setCaseType(BusinessConstant.ANALYSIS_TYPE_SPREAD);
//                eventBannerList.add(ear4);
//            }
//
        }

        eventBannerList.add(ear3);
        eventBannerList.add(ear1);
        eventBannerList.add(ear2);
        baseDto.setData(eventBannerList);
        /**
         * 记录用户操作日志
         */
        try {
            /**
             * 操作类型：1-增 2-删 3-改 4-查
             */
            int operateType = BusinessConstant.STATUS_VALUE_4;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);
            CommonUtils.opreateLog(request, null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_EVENT,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return baseDto;
    }



    private List<EventAnalysisTaskDto> generateTaskInfoList(List<EventAnalysisTaskDto> taskDtoList) {

        List<EventAnalysisTaskDto> resultList = new ArrayList<EventAnalysisTaskDto>();
        for (EventAnalysisTaskDto task: taskDtoList) {
            EventAnalysisTaskDto infoV3 = generateTaskInfo(task);
            resultList.add(infoV3);
        }
        return resultList;
    }

    public EventAnalysisTaskDto generateTaskInfo(EventAnalysisTaskDto task) {
        //秒转换成分钟
        double second = task.getAnalysisTotalConsumeExpect();
        int minute =Integer.parseInt(new java.text.DecimalFormat("0").format(second/60));
        task.setEstimateMinute(minute<1 ? 1 :minute);

        int analysisStatus = task.getAnalysisStatus();
        //事件状态优化处理
        //处理状态(0:失败，1:待处理，2:正在处理，3:已处理，4:处理完成(实际还未处理完)，5：处理完成
        if(analysisStatus==BusinessConstant.STATUS_VALUE_1 ||analysisStatus==BusinessConstant.STATUS_VALUE_2 ||analysisStatus==BusinessConstant.STATUS_VALUE_3 || analysisStatus==BusinessConstant.STATUS_VALUE_4){
            analysisStatus=1;
        }else if(analysisStatus==BusinessConstant.STATUS_VALUE_5){
            analysisStatus=2;
        }else{
            analysisStatus=0;
        }
        task.setAnalysisStatus(analysisStatus);
        int surplusCount=3-task.getUpdateNum();
        if (surplusCount>BusinessConstant.STATUS_VALUE_3||surplusCount<BusinessConstant.STATUS_VALUE_0) {
            task.setUpdateNum(0);
        } else {
            task.setUpdateNum(surplusCount);
        }
        return task;
    }


    public List<EventAnalysisReportDto> generateReportInfoList(List<EventAnalysisReportDto> reportList) {
        List<EventAnalysisReportDto> resultList = new ArrayList<EventAnalysisReportDto>();
        for (EventAnalysisReportDto task: reportList) {
            EventAnalysisReportDto infoV3 = generateReportInfo(task);
            resultList.add(infoV3);
        }
        return resultList;
    }


    public EventAnalysisReportDto generateReportInfo(EventAnalysisReportDto report) {
        EventAnalysisReportDto result = new EventAnalysisReportDto();

        result.setIsSample(0);
        result.setReportId(report.getReportId());
        result.setTaskId(report.getTaskId());
        result.setIncidentTitle(report.getIncidentTitle());
        result.setCreateTime(report.getCreateTime());
        result.setTaskTicket(report.getTaskTicket());

        String isapp = "&isApp=1";
        if (report.getContentType() == 1) {
            result.setReportURL( SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")
                    + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL
                    + StringUtil.urlEncode(report.getTaskTicket()) + isapp);
            result.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")
                    + BusinessConstant.EVENT_REPORT_ALL_VISIT_URL
                    + StringUtil.urlEncode(report.getTaskTicket()));
        } else if (report.getContentType() == 2) {
            result.setReportURL(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")
                    + BusinessConstant.EVENT_REPORT_WB_VISIT_URL
                    + StringUtil.urlEncode(report.getTaskTicket()) + isapp);
            result.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL")
                    + BusinessConstant.EVENT_REPORT_WB_VISIT_URL
                    + StringUtil.urlEncode(report.getTaskTicket()));
        }
        return result;
    }



    public static final EventAnalysisController INSTANCE = new EventAnalysisController();

    public static EventAnalysisController getInstance(){
        return INSTANCE;
    }

}
