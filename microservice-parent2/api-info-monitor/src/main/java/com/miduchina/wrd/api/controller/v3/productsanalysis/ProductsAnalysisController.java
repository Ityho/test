package com.miduchina.wrd.api.controller.v3.productsanalysis;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.controller.v3.BaseController;
import com.miduchina.wrd.api.service.productsanalysis.ProductsAnalysisService;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.analysis.AnalysisDto;
import com.miduchina.wrd.dto.analysis.AnalysisSolidifyDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.BusinessReuqestUtils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.other.util.StringUtil;
import com.miduchina.wrd.po.analysis.ProductsAnalysis;
import com.miduchina.wrd.po.analysis.ProductsAnalysisBrief;
import com.miduchina.wrd.po.analysis.ProductsAnalysisShare;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.MD5Utils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
 * Created by shitao on 2019-05-17 15:58.
 *
 * @author shitao
 */

@Slf4j
@Api(tags = {"竞品分析"})
@RestController
@RequestMapping("api/v3/competitive")
public class ProductsAnalysisController extends BaseController {

    @Autowired
    ProductsAnalysisService productsAnalysisService;

    @ApiOperation(value = "竞品分析创建接口", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "竞品分析创建接口")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "create", headers = "Accept=application/json")
    public BaseDto create(@ApiParam(value = "筛选条件(userEncode:用户Encode,productAnalysisId:(任务id重新分析必填),createType:(1创建,2重新分析),json:( 任务名称关键词的json对象,新建必填)timeType:(24 - 24小时 ; 3- 3天;  7 - 7天 ; 10 - 10天 ; 30 - 30天))") @RequestParam Map<String, Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("contentType") == null ||
                params.get("json") == null || params.get("startTime") == null ||
                params.get("endTime") == null) {
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

        /**
         * 购买有效次数验证(高级用户不限使用竞品、事件分析次数)
         */
        if (userDto.getUserProductAnalysisValidCount() <= 0) {
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_VALID_COUNT_CODE_4021);
        }

        Integer createType = jsonParams.getInteger("createType") == null ? 1 : jsonParams.getInteger("createType");
        String json = jsonParams.getString("json") == null ? "" : jsonParams.getString("json");
        Integer productAnalysisId = jsonParams.getInteger("productAnalysisId") == null ? -1 : jsonParams.getInteger("productAnalysisId");
        Integer timeType = jsonParams.getInteger("timeType") == null ? 1 : jsonParams.getInteger("timeType");

        String key = RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_COMPETITIVE_DATA_ANALYSIS + userEncode + "_" + MD5Utils.MD5Encode(json));
        if (!RedisUtils.lockUserOperate(key, SystemConstants.JEDIS_SESSION_TIME)) {
            return baseDto.setCodeMessage(CodeConstant.E_REQUEST_HANDLE_IGNORE_CODE_0001);
        }

        if (timeType == 24) {
            timeType = 1;
        }
        String endtime = DateUtils.getNow();
        String starttime = DateUtils.format(DateUtils.addDay(new Date(), -timeType));
        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        params.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
        params.put("createType", createType);
        if (createType == BusinessConstant.STATUS_VALUE_2) {
            params.put("productAnalysisId", productAnalysisId);
        }
        params.put("userEncode", userEncode);
        if (createType == BusinessConstant.STATUS_VALUE_1) {
            params.put("json", json);
            params.put("startTime", starttime);
            params.put("endTime", endtime);
        }
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("productAddTask"), buParams);
        if (StringUtils.isEmpty(jsonStr)) {
            return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_CREATE_CODE_4031);
        }
        baseDto = JSONObject.parseObject(jsonStr, BaseDto.class);
        if (baseDto == null || !baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            if (baseDto == null) {
                return baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_CREATE_CODE_4031);
            } else {
                if ("2205".equals(baseDto.getCode())) {
                    baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_KEYWORD_CODE_4032);
                }
                if ("4026".equals(baseDto.getCode())) {
                    baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_TASK_MORE_THAN_MAX_DATA_CODE_4026);
                }
                if ("4025".equals(baseDto.getCode())) {
                    baseDto.setCodeMessage(CodeConstant.E_EVENT_ANALYSIS_TASK_NO_DATA_CODE_4025);
                }
            }
            return baseDto;
        }
        try {
            int operateType = 1; // 操作类型：1-增 2-删 3-改 4-查

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        RedisUtils.removeAttribute(key);
        return baseDto;
    }


    @ApiOperation(value = "竞品分析进度", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "竞品分析进度")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "solidifySchedule", headers = "Accept=application/json")
    public PageDto solidifySchedule(@ApiParam(value = "筛选条件(userEncode:用户Encode,tickets:(任务标识 不同任务用/分隔)") @RequestParam Map<String, Object> params) {


        PageDto baseDto = new PageDto();
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
        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        params.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
        params.put("ticket", tickets);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("productQuerySolidifySchedule"), buParams);
        if (StringUtils.isNotEmpty(jsonStr)) {
            baseDto = JSONObject.parseObject(jsonStr, PageDto.class);
            JSONObject object = JSONObject.parseObject(jsonStr);
            List<AnalysisSolidifyDto> solidifyList = object.getJSONArray("solidifyList").toJavaList(AnalysisSolidifyDto.class);
            baseDto.setData(solidifyList);
        } else {
            baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
        }
        return baseDto;
    }

    @ApiOperation(value = "竞品分析列表", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "竞品分析列表")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "list", headers = "Accept=application/json")
    public PageDto list(@ApiParam(value = "筛选条件(userEncode:用户Encode,page:页码,pageSize:每页条数") @RequestParam Map<String, Object> params) {

        PageDto baseDto = new PageDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        Integer page = jsonParams.getInteger("page") == null ? BusinessConstant.DEFAULT_PAGE : jsonParams.getInteger("page");
        Integer pageSize = jsonParams.getInteger("pageSize") == null ? BusinessConstant.DEFAULT_PAGE_SIZE : jsonParams.getInteger("pageSize");
        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();

        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        buParams.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
        buParams.put("page", page);
        buParams.put("pageSize", pageSize);
        buParams.put("userEncode", userEncode);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("productTaskList"), params);
        if (StringUtils.isNotEmpty(jsonStr)) {
            baseDto = JSONObject.parseObject(jsonStr, PageDto.class);
            JSONObject object = JSONObject.parseObject(jsonStr);
            List<AnalysisDto> list = object.getJSONArray("productAnalysisList").toJavaList(AnalysisDto.class);
            list = generateCompetitiveInfoList(list);
            baseDto.setData(list);
        } else {
            baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
        }

        //记录用户操作日志
        try {
            int operateType = BusinessConstant.STATUS_VALUE_4;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);


            CommonUtils.opreateLog(request, null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto;
    }


    @ApiOperation(value = "竞品分析删除", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "竞品分析删除")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "delete", headers = "Accept=application/json")
    public BaseDto delete(@ApiParam(value = "筛选条件(userEncode:用户Encode,productAnalysisId:任务ID") @RequestParam Map<String, Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null || params.get("pabId") == null) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        JSONObject jsonParams = new JSONObject(params);
        String userEncode = jsonParams.getString("userEncode") == null ? "" : jsonParams.getString("userEncode");
        Integer pabId = jsonParams.getInteger("productAnalysisId") == null ? BusinessConstant.DEFAULT_PAGE : jsonParams.getInteger("productAnalysisId");

        BaseDto<UserDto> userBaseDto = checkUserEncode(request, userEncode);
        if (!userBaseDto.getCode().equals(CodeConstant.SUCCESS_CODE)) {
            return baseDto.setCode(userBaseDto.getCode()).setMessage(userBaseDto.getMessage());
        }
        UserDto userDto = userBaseDto.getData();


        Map<String, Object> buParams = CommonUtils.getIntraBusinessParams(userDto.getUserId());
        buParams.put("platformTag", BusinessConstant.PARTNER_KEY_WYQ);
        buParams.put("productId", pabId);
        buParams.put("userEncode", userEncode);
        String jsonStr = BusinessReuqestUtils.sendIntraBusinessAPIPOST(request, IntraBusinessAPIConfig.getValue("productDeleteTask"), params);
        if (StringUtils.isNotEmpty(jsonStr)) {
            baseDto = JSONObject.parseObject(jsonStr, BaseDto.class);
        } else {
            baseDto.setCodeMessage(CodeConstant.FAILURE_CODE);
        }

        //记录用户操作日志
        try {
            int operateType = BusinessConstant.STATUS_VALUE_2;

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            CommonUtils.opreateLog(request,null, BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto;

    }


    @ApiOperation(value = "竞品分析案例", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "竞品分析案例")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "banner", headers = "Accept=application/json")
    public BaseDto banner(@ApiParam(value = "筛选条件(userEncode:用户Encode") @RequestParam Map<String, Object> params) {

        BaseDto baseDto = new BaseDto();
        if (MapUtils.isEmpty(params)) {
            return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_1000);
        }
        if (params.get("userEncode") == null) {
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


        String realPath= request.getSession().getServletContext().getRealPath("");
        realPath =realPath.substring(realPath.lastIndexOf("/")+1, realPath.length());


        List<AnalysisDto> paList = new ArrayList<AnalysisDto>();
        AnalysisDto pad1 = new AnalysisDto();
        pad1.setTitle("万科、中金黄金的对比分析");
        pad1.setFilePath("http://h5.wyq.cn/lookShareCodeReport.action?isApp=1&shareCode=1AeOSISybmVn3Rc");
        pad1.setShareUrl("http://h5.wyq.cn/lookShareCodeReport.action?shareCode=1AeOSISybmVn3Rc");
        pad1.setBarImg("http://"+realPath+"/template/CaseAnalysisImg/20160726152846.jpg");

        AnalysisDto pad2 = new AnalysisDto();
        pad2.setTitle("老九门、余罪的对比分析");
        pad2.setFilePath("http://h5.wyq.cn/lookShareCodeReport.action?isApp=1&shareCode=1AeOSIStZdTn3Rg");
        pad2.setShareUrl("http://h5.wyq.cn/lookShareCodeReport.action?shareCode=1AeOSIStZdTn3Rg");
        pad2.setBarImg("http://"+realPath+"/template/CaseAnalysisImg/20160726152832.jpg");

        AnalysisDto pad3 = new AnalysisDto();
        pad3.setTitle("C罗、佩佩的对比分析");
        pad3.setFilePath("http://h5.wyq.cn/lookShareCodeReport.action?isApp=1&shareCode=1AeOSISlAVPn42q");
        pad3.setShareUrl("http://h5.wyq.cn/lookShareCodeReport.action?shareCode=1AeOSISlAVPn42q");
        pad3.setBarImg("http://"+realPath+"/template/CaseAnalysisImg/20160726152839.jpg");

        paList.add(pad1);
        paList.add(pad2);
        paList.add(pad3);


        baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE).setData(paList);

        //记录用户操作日志
        try {
            int operateType=BusinessConstant.STATUS_VALUE_4; // 操作类型：1-增 2-删 3-改 4-查

            Map<String,Object> map = new HashMap<>();
            map.put("UserDto",userDto);
            OperateLogObjectDto objectDto = CommonUtils.generateOperateLogObject(null,map);

            CommonUtils.opreateLog(request, null,BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_PRODUCT_ANALYSIS,
                    objectDto, operateType, null, baseDto);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseDto;
    }

    @ApiOperation(value = "竞品分析总数", produces = "application/json")
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "queryProductCount")
    public BaseDto<Integer> queryProductCount(int userId) {
        BaseDto<Integer> baseDto=new BaseDto();
        Map<String,Object> params=new HashMap<>();
        JSONObject jsonObject=new JSONObject(params);
        params.put("userId",userId);
        if(userId!=0){
            Integer count=productsAnalysisService.queryCount(jsonObject);
            if(count!=null){
                baseDto.setData(count).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求失败");
            }
        }else {
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }
        return baseDto;
    }

    @RequestMapping(value = "/getPABList")
    public BaseDto getPABList(Integer userId){
        BaseDto<List<ProductsAnalysisBrief>> baseDto=new BaseDto();
        Map<String,Object> objectMap= new HashMap<>();
        objectMap.put("userId",userId);
        JSONObject jsonObject=new JSONObject(objectMap);
        if(userId==null){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            List<ProductsAnalysisBrief> briefList=productsAnalysisService.getPABList(jsonObject);
            if(briefList!=null && briefList.size()>0){
                baseDto.setData(briefList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }


    @RequestMapping(value = "/getPABId")
    public BaseDto getPABId(Integer id){
        BaseDto<List<ProductsAnalysis>> baseDto=new BaseDto();
        Map<String,Object> objectMap= new HashMap<>();
        objectMap.put("id",id);
        objectMap.put("status",1);
        JSONObject jsonObject=new JSONObject(objectMap);
        if(id==null){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            List<ProductsAnalysis> listTask=productsAnalysisService.getListTask(jsonObject);
            if(listTask!=null && listTask.size()>0){
                baseDto.setData(listTask).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }


    @RequestMapping(value = "/findisShareCodePabInId")
    public BaseDto findisShareCodePabInId(String pabIds){
        BaseDto<List<ProductsAnalysisShare>> baseDto=new BaseDto();
        Map<String,Object> objectMap= new HashMap<>();
        objectMap.put("productsAnalysisBriefId",pabIds);
        JSONObject jsonObject=new JSONObject(objectMap);
        if(TextUtils.isEmpty(pabIds)){
            baseDto.setCode(CodeConstant.ERROR_CODE_1000).setMessage("请求参数不能为空");
        }else {
            List<ProductsAnalysisShare> briefList=productsAnalysisService.findisShareCodePabInId(jsonObject);
            if(briefList!=null && briefList.size()>0){
                baseDto.setData(briefList).setCode(CodeConstant.SUCCESS_CODE).setMessage("请求成功！");
            }else {
                baseDto.setCode(CodeConstant.FAILURE_CODE).setMessage("请求数据为空");
            }
        }
        return baseDto;
    }

    public List<AnalysisDto> generateCompetitiveInfoList(List<AnalysisDto> dtoList){
        List<AnalysisDto> competitiveInfoV3List = new ArrayList<AnalysisDto>();
        if(CollectionUtils.isNotEmpty(dtoList)){
            for (AnalysisDto dto : dtoList) {
                AnalysisDto competitiveInfoV3 = generateCompetitiveInfo(dto);
                competitiveInfoV3List.add(competitiveInfoV3);
            }
        }
        return competitiveInfoV3List;
    }

    public AnalysisDto generateCompetitiveInfo(AnalysisDto dto) {

        AnalysisDto products = dto;
        products.setIsSample(0);

        Integer analysisStatus = dto.getAnalysisStatus();
        Integer schedulesStatus = null;
        if (analysisStatus == 0){
            schedulesStatus = 0;
        }else if (analysisStatus > 0 && analysisStatus <= 4){
            schedulesStatus = 2;
        }else if (analysisStatus >4 ){
            schedulesStatus = 1;
        }
        products.setAnalysisStatus(schedulesStatus);

        if (schedulesStatus == 1){
            //兼容以前老的竞品分析（访问路径不一样）
            final Date createTime = DateUtils.parse("2016-06-30 18:00:00"); 	//六月三十号之前的为老版本创建的竞品分析
            if(StringUtils.isNotBlank(dto.getFilePath()) && createTime.after(dto.getCreateTime()) ){
                final String visitSuffix=".html";
                String filePath = SysConfig.cfgMap.get("FILE_HTTP_BASE_PATH")+dto.getFilePath()+visitSuffix;
                products.setFilePath(filePath + "?isApp=1");
                products.setShareUrl(filePath);
            } else {
                products.setFilePath(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + SysConfig.cfgMap.get("H5_COMPETITIVE_REPORT_ACTION") + StringUtil.urlEncode(dto.getCode()));
                products.setShareUrl(SysConfig.cfgMap.get("H5_PROJECT_VISIT_URL") + SysConfig.cfgMap.get("H5_COMPETITIVE_REPORT_ACTION") + StringUtil.urlEncode(dto.getCode()));
            }
        }
        return products;
    }



    public static final ProductsAnalysisController INSTANCE = new ProductsAnalysisController();

    public static ProductsAnalysisController getInstance(){
        return INSTANCE;
    }



}