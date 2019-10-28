package com.miduchina.wrd.eventanalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.bigdata.BigReportVo;
import com.miduchina.wrd.dto.bigdata.OperationAdminWbDto;
import com.miduchina.wrd.dto.home.HeatShareDto;
import com.miduchina.wrd.dto.log.OperateLogObjectDto;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.stock.StockTypeKeywords;
import com.miduchina.wrd.dto.stock.StockTypeKeywordsView;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.config.WyqDataConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.geetest.GeetestConfig;
import com.miduchina.wrd.eventanalysis.geetest.GeetestLib;
import com.miduchina.wrd.eventanalysis.geetest.GeetestValid;
import com.miduchina.wrd.eventanalysis.service.EventService;
import com.miduchina.wrd.eventanalysis.service.HomeService;
import com.miduchina.wrd.eventanalysis.service.ProductsAnalysisService;
import com.miduchina.wrd.eventanalysis.service.SingleWeiboAnalysisService;
import com.miduchina.wrd.eventanalysis.utils.*;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.analysis.weiboanalysis.WeiBoAnalysisTask;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisReport;
import com.miduchina.wrd.po.ranking.OperationAdminHot;
import com.miduchina.wrd.po.ranking.OperationAdminHotContent;
import com.miduchina.wrd.po.user.User;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import com.xd.tools.method.wyq.web.WyqUnBlockV1;
import com.xd.tools.method.wyq.web.log.OperatLogMethodV1;
import com.xd.tools.ownutils.GetCommon;
import com.xd.tools.pojo.HotIncident;
import com.xd.tools.pojo.Statistics;
import com.xd.tools.pojo.YwztParams;
import com.xd.tools.pojo.db.mysql.wyq.weiyuqing.H5SearchShare;
import com.xd.tools.view.BaseView;
import com.xd.tools.view.HotIncidentView;
import com.xd.tools.view.StatisticsView;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Controller
public class HomeController extends BaseController{


    @Autowired
    HomeService homeService;
    @Autowired
    ProductsAnalysisService productsAnalysisService;

    @Autowired
    EventService eventService;

    @Autowired
    SingleWeiboAnalysisService singleWeiboAnalysisService;



    @RequestMapping(value = "/")
    public String index(){
        return "myviews/index";
    }


    @RequestMapping(value = "/home")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("view/home/home");
        log.info("loginuser:{}","sucess");
        OperatLogMethodV1.opreateLog("home");
        return modelAndView;
    }





    @RequestMapping(value = "/readPage")
    public ModelAndView readPage(ModelAndView modelAndView){
        modelAndView.setViewName("readPage");
        return modelAndView;
    }


    @RequestMapping(value = "/infoData")
    public String infoData(){
        return "view/home/infoData";
    }

    @RequestMapping(value = "analysis")
    public ModelAndView analysis(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response){
        admin=fetchSessionAdmin(request);
        if (admin == null){
            modelAndView.setViewName("userLogin");
            modelAndView.addObject("urlType","analysis");
            return modelAndView;
        }
        String basePath = Utils.getBaseUrl(request);
//        BaseDto<Integer> baseDto=apiInfoClient.queryProductCount(admin.getUserId());

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userEncode", CommonUtils.buildUserEncode(String.valueOf(admin.getUserId())));
        params.put("platformTag", "wyq");
        params.put("page", page);
        params.put("pageSize", 20);
        params.put("userIds",""+admin.getUserId());
        String rtnStr = Utils.sendIntraBusinessAPIPOST(request, "productAnalysisTotal", params);
        PageDto baseDto = new PageDto();
        if (StringUtils.isNotBlank(rtnStr)){
            baseDto = JSONObject.parseObject(rtnStr, PageDto.class);
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                Long productCount= baseDto.getTotalCount();
                modelAndView.addObject("productCount",productCount);
            }else {
                modelAndView.addObject("productCount",0);
            }
        }else {
            modelAndView.addObject("productCount",0);
        }
        //
        List<IncidentAnalysisReport> reportList=eventService.getEventReportList(request,Integer.valueOf(admin.getUserId()),1);
        if(reportList!=null && reportList.size()>0){
            modelAndView.addObject("eventCount",reportList.size());
        }
        //
        int weiboEventCount=eventService.queryCount(request,Integer.valueOf(admin.getUserId()),0);
        modelAndView.addObject("weiboEventCount",weiboEventCount);
        //微博传播效果分析
        int singleWeiboCount=0;
        BaseDto baseDto1=apiInfoClient.queryWeiboAnalysisListByUserId(String.valueOf(admin.getUserId()));
        if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null){
            List<WeiBoAnalysisTask> taskList= (List<WeiBoAnalysisTask>) baseDto1.getData();
            if(taskList!=null && taskList.size()>0){
                singleWeiboCount = taskList.size();
            }
        }
        modelAndView.addObject("singleWeiboCount",singleWeiboCount);
        modelAndView.addObject("basePath",basePath);
        modelAndView.setViewName("view/home/analysis");
        return modelAndView;
    }

    @RequestMapping(value = "/refreshUser")
    public void refreshUser(ModelAndView modelAndView,HttpServletRequest request){
        admin = refreshSesssionUserInfo(request);
    }



    @RequestMapping(value = "/user")
    public ModelAndView user(ModelAndView modelAndView,HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        if (admin == null){
            modelAndView.setViewName("userLogin");
            modelAndView.addObject("urlType","user");
            return modelAndView;
        }
        modelAndView.addObject("admin",admin);

        if (admin.getLastSignInDate() != null){
            int day = DateUtils.countDays(DateUtils.format(admin.getLastSignInDate()),DateUtils.FORMAT_SHORT);
            if (day == 0){
                modelAndView.addObject("goSign",0);
            }else {
                modelAndView.addObject("goSign",1);
            }
        }else {
            modelAndView.addObject("goSign",1);
        }
        modelAndView.addObject("baseUrl",Utils.getBaseUrl(request));
        modelAndView.setViewName("view/home/user");
        return modelAndView;
    }

    @RequestMapping(value ="center")
    public String  center(ModelMap map){
        map.put("users",parseUsers());
        map.put("title","用户列表");
        return "myviews/center";
    }

    @RequestMapping(value = "/weiboHome")
    public String weiboHome(){
        return "view/home/home";
    }

    /**
     * 获取明星榜信息
     * @return
     */
    @RequestMapping("/getStarList")
    public ModelAndView getStarList(ModelAndView modelAndView,HttpServletRequest request) throws Exception {
        fetchSessionAdmin(request);
        Long token = System.currentTimeMillis();// new Date().getTime();
        modelAndView.addObject("token",token);
        String user = null;
        String ip = CommonUtils.getIp(request);
        if (admin != null){
            user = admin.getUserId().toString();
        }
        String url = SysConfig.cfgMap.get("API_DB_URL") + "/v2/statistics/others";
        String paramsAll = "";
        if (admin != null) {
            paramsAll = "page=1&pagesize=10&type=3&sort=3&order=1&platform=2&channel=1&userTag=" + user;
        } else {
            paramsAll = "page=1&pagesize=10&type=3&sort=3&order=1&platform=2&channel=1&userTag=" + ip;
        }
        String resultAll = HttpRequestUtils.sendGet(url, paramsAll);
        StockTypeKeywordsView stockTypeKeywordsViewAll = JSON.parseObject(resultAll, StockTypeKeywordsView.class);
        List<StockTypeKeywords> starListAll = stockTypeKeywordsViewAll.getStatisticsList();
        modelAndView.addObject("starListAll",starListAll);
        for (int i = 0; i < starListAll.size(); i++) {
            starListAll.get(i).setRatioHotDay(starListAll.get(i).getRatioHotDay());
        }
        String viewUri = DNSUtils.getDNSUrl(request.getRequestURL().toString());
        modelAndView.addObject("viewUri",viewUri);
        modelAndView.setViewName("view/hotEventRanking/stockRanking");
        return modelAndView;
    }

    @RequestMapping(value = "/getHotWeibo")
    @ResponseBody
    public Map<String,Object> getHotWeibo(int pagesize, int page, int categoryId){
        Map<String,Object> resMap=homeService.getHotWeibo(pagesize,page,categoryId);
        return resMap;
    }


    /**
     * 热门事件标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHotLabelList")
    @ResponseBody
    public String getHotLabelList(HttpServletRequest request){
        String restul=homeService.getHotLabel(request);
        return restul;
    }

    /**
     * 热门事件count
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHotEventCount")
    @ResponseBody
    public String getHotEventCount(HttpServletRequest request){
        String restul=homeService.getHotEventCount(request);
        return restul;
    }


    /**
     * 热门事件
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHotEventList")
    @ResponseBody
    public String getHotEventList(HttpServletRequest request){
        String restul=homeService.getHotEvent(request);
        return restul;
    }

    /**
     * 重大事件列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBigEventList")
    @ResponseBody
    public String getBigEventList(HttpServletRequest request){
        String restul=homeService.getBigEventList(request);
        if(!TextUtils.isEmpty(restul)){
            return restul;
        }
        return "";
    }

    /**
     * 重大事件详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/getBigEventDetail")
    public ModelAndView getBigEventDetail(ModelAndView modelAndView,HttpServletRequest request,int id){


        Date endDate = new Date();
        Date startDate = DateUtils.addDay(endDate,-7);


        String startTime=  DateUtils.getStringDate(startDate,"yyyy-MM-dd HH:mm:ss");
        String endTime = DateUtils.getStringDate(endDate,"yyyy-MM-dd HH:mm:ss");

        HotIncidentDto hotIncident=homeService.getBigEventDetail(request,id,startTime,endTime);
        if(hotIncident!=null){
            modelAndView.addObject("data",hotIncident);
        }

        startTime=  DateUtils.getStringDate(startDate,"MM月dd日");
        endTime = DateUtils.getStringDate(endDate,"MM月dd日");

        modelAndView.addObject("sTime",startTime);
        modelAndView.addObject("eTime",endTime);

        modelAndView.setViewName("view/home/listDetails");
        return modelAndView ;
    }

    @RequestMapping(value = "getBigDataDetail")
    public ModelAndView getBigDataDetail(HttpServletRequest request,ModelAndView modelAndView,int id){
        admin=fetchSessionAdmin(request);
        BaseDto<OperationAdminWbDto> baseDto= apiInfoClient.queryBigDataDetail(id);
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            OperationAdminWbDto data = baseDto.getData();
            Integer packagePrice = data.getPackagePrice();
            boolean bl=false;
            if(packagePrice!=0&&packagePrice!=null){
                bl=true;
            }
            BaseDto baseDto1 = apiInfoClient.queryBuyedBigData(Integer.parseInt(admin.getUserId()));
            int count=0;
            if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null) {
                String string = JSONObject.toJSONString(baseDto1.getData());
                List<BigReportVo> bigReportVo = JSONObject.parseArray(string, BigReportVo.class);
//                List<BigReportVo> bigReportVo = (List<BigReportVo>) baseDto1.getData();
//                List<BigReportVo> bigReportVo = (List<BigReportVo>) baseDto1.getData();
                for (BigReportVo datum : bigReportVo) {
                    Integer bigReportId = datum.getBigReportId();
                    if(bigReportId==id){
                        count++;
                    }
                }
            }
            if(count==0){
                if(data.getPackagePrice()!=0){
                    data.setContent(data.getAbstracts());
                }
            }
            modelAndView.addObject("adminWb",baseDto.getData());
            modelAndView.addObject("priceshow",bl);
            modelAndView.addObject("credit",admin.getCreditAmount());
            modelAndView.addObject("code","0000");
            modelAndView.addObject("msg","请求成功");
        }else{
            modelAndView.addObject("code","9999");
        }
        modelAndView.setViewName("view/home/infoDataDetails");
        return modelAndView;
    }
    @RequestMapping(value = "infoDataReport")
    public ModelAndView infoDataReport(HttpServletRequest request,ModelAndView modelAndView){
        admin=fetchSessionAdmin(request);
//        BaseDto<OperationAdminWbDto> baseDto= apiInfoClient.queryBigDataDetail(id);
//        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
//            modelAndView.addObject("adminWb",baseDto.getData());
//            OperationAdminWbDto data = baseDto.getData();
//            Integer packagePrice = data.getPackagePrice();
//            boolean bl=false;
//            if(packagePrice!=0&&packagePrice!=null){
//                bl=true;
//            }
//            modelAndView.addObject("priceshow",bl);
//            modelAndView.addObject("credit",admin.getCreditAmount());
//            modelAndView.addObject("code","0000");
//            modelAndView.addObject("msg","请求成功");
//        }else{
//            modelAndView.addObject("code","9999");
//        }
        modelAndView.setViewName("view/home/infoDataReport");
        return modelAndView;
    }


    @RequestMapping(value = "getBigDataShareDetail")
    public ModelAndView getBigDataShareDetail(HttpServletRequest request,ModelAndView modelAndView,int id){
        BaseDto<OperationAdminWbDto> baseDto= apiInfoClient.queryBigDataDetail(id);
//        admin=fetchSessionAdmin(request);
//        BaseDto baseDto1 = apiInfoClient.queryBuyedBigData(Integer.parseInt(admin.getUserId()));
//        int count=0;
//        if(baseDto1!=null && baseDto1.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto1.getData()!=null) {
//            List<BigReportVo> data = (List<BigReportVo>) baseDto1.getData();
//            for (BigReportVo datum : data) {
//                Integer bigReportId = datum.getBigReportId();
//                if(bigReportId==id){
//                    count++;
//                }
//            }
//        }
//        OperationAdminWbDto data = baseDto.getData();
//        if(count==0){
//            if(data.getPackagePrice()!=0){
//                data.setContent(data.getAbstracts());
//            }
//        }
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            modelAndView.addObject("adminWb",baseDto.getData());
            modelAndView.addObject("code","0000");
            modelAndView.addObject("msg","请求成功");
        }else{
            modelAndView.addObject("code","9999");
        }
        modelAndView.setViewName("view/home/infoDataDetails");
        return modelAndView;
    }

    @RequestMapping(value = "/getBigReport")
    @ResponseBody
    public Map<String,Object> getBigReport(int page,int pagesize,Integer userId) {
        Map<String, Object> resHashMap = new HashMap<>();
        if (userId == null){
            return null;
        }
        return null;
    }


    @RequestMapping(value = "/setBigReport")
    @ResponseBody
    public Map<String,Object> getBigReport() {
        return null;
    }



    /**
     * 大数据解读
     * @param type
     * @return
     */
    @RequestMapping(value = "/getBigDataList")
    @ResponseBody
    public Map<String,Object> getBigDataList(int page,int pagesize,int type,int isPackagePrice){
        Map<String,Object> resHashMap=new HashMap<>();
        if(page == 0){
            page = 1;
        }
        if(pagesize==0){
            pagesize = 15;
        }
        Integer packagePrice=null;
        if(isPackagePrice==0){
            packagePrice=isPackagePrice;
        }
        PageDto<OperationAdminWbDto> pageDto = apiInfoClient.findBigDataByNameTypePage(page, pagesize,type,packagePrice);
        if(pageDto!=null && pageDto.getCode().equals(CodeConstant.SUCCESS_CODE) && pageDto.getList()!=null){
            List<OperationAdminWbDto> oAdminWbs=pageDto.getList();
            for(OperationAdminWbDto oAdminWb : oAdminWbs){
                if(packagePrice==null){
                    oAdminWb.setContent(oAdminWb.getAbstracts());
                }
                oAdminWb.setArticleUrl(SysConfig.cfgMap.get("SYSTEM_WEB_URL") + "share/hotSearch/hotEventPreview.shtml?hotEventPreview.id=" + oAdminWb.getId());
            }
            resHashMap.put("message", "请求成功");
            resHashMap.put("code", "0000");
            resHashMap.put("data", oAdminWbs);
            resHashMap.put("totalCount", pageDto.getTotalCount());
        }else {
            if(pageDto!=null && pageDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                resHashMap.put("code", "9999");
                resHashMap.put("message", "暂无数据");
            }else {
                resHashMap.put("code", "1111");
                resHashMap.put("message", "访问错误");
            }
        }
        return resHashMap;
    }

    /**
     * 大数据解读
     * @return
     */
    @RequestMapping(value = "/readBuy")
    @ResponseBody
    public Map<String,Object> readBuy(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        Map<String,Object> resHashMap=new HashMap<>();
        BaseDto baseDto = apiInfoClient.queryBuyedBigData(Integer.parseInt(admin.getUserId()));
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getData()!=null){
            List<BigReportVo> data = (List<BigReportVo>) baseDto.getData();
            resHashMap.put("message", "请求成功");
            resHashMap.put("code", "0000");
            resHashMap.put("data", data);
        }else {
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.FAILURE_CODE)){
                resHashMap.put("code", "9999");
                resHashMap.put("message", "暂无数据");
            }else {
                resHashMap.put("code", "1111");
                resHashMap.put("message", "访问错误");
            }
        }
        return resHashMap;
    }
    //分页查询购买大数据报告
    @RequestMapping(value = "/queryBuyedBigDataByUserId")
    @ResponseBody
    public Map<String,Object> queryBuyedBigDataByUserId(HttpServletRequest request,Integer page,Integer pageSize){
        admin=fetchSessionAdmin(request);
        Map<String,Object> resHashMap=new HashMap<>();
        Map<String,Object> pararm=new HashMap<>();
        if(page!=null){
            pararm.put("page",page);
        }
        if(pageSize!=null){
            pararm.put("pageSize",pageSize);
        }
        pararm.put("userId",admin.getUserId());
        PageDto baseDto = new PageDto();
        baseDto = apiInfoClient.queryBuyedBigDataByUserId(pararm);
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE) && baseDto.getList()!=null){
            List<BigReportVo> data = (List<BigReportVo>) baseDto.getList();
            resHashMap.put("message", "请求成功");
            resHashMap.put("code", "0000");
            resHashMap.put("data", data);
            resHashMap.put("maxPage", baseDto.getMaxPage());
        }else {
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.FAILURE_CODE)){
                resHashMap.put("code", "9999");
                resHashMap.put("message", "暂无数据");
            }else {
                resHashMap.put("code", "1111");
                resHashMap.put("message", "访问错误");
            }
        }
        return resHashMap;
    }
    @RequestMapping(value = "/deletReport")
    @ResponseBody
    public Map<String,Object> deletReport(HttpServletRequest request,Integer reportId){
        admin=fetchSessionAdmin(request);
        Map<String,Object> resHashMap=new HashMap<>();
        Map<String,Object> pararm=new HashMap<>();
        pararm.put("id",reportId);
        pararm.put("userId",admin.getUserId());
        BaseDto baseDto = apiInfoClient.deletReport(pararm);
        if(baseDto!=null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            resHashMap.put("message", "请求成功");
            resHashMap.put("code", "0000");
        }else {
            if(baseDto!=null && baseDto.getCode().equals(CodeConstant.FAILURE_CODE)){
                resHashMap.put("code", "9999");
                resHashMap.put("message", "暂无数据");
            }else {
                resHashMap.put("code", "1111");
                resHashMap.put("message", "访问错误");
            }
        }
        return resHashMap;
    }


    @RequestMapping(value = "/searchDetails")
    public ModelAndView searchDetails( HttpServletRequest request, ModelAndView modelAndView,String keyword,String title,String filterKeyword){
        fetchSessionAdmin(request);

        String userStr = "0"+String.valueOf(new Random().nextInt(10000));
        if(admin != null && admin.getUserId() != null){
            userStr = admin.getUserId().toString();
        }
        Object code = SinaWeiboMid2Id.id2Mid("5005"+userStr+System.currentTimeMillis());
        String  shareCode = String.valueOf(code);
        if (modelAndView == null){
            modelAndView = new ModelAndView();
        }
        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        }
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        modelAndView.addObject("searchKeyword",keyword);
        modelAndView.addObject("shareCode",shareCode);
        modelAndView.addObject("searchTitle",title);
        if (StringUtils.isEmpty(filterKeyword)){
            filterKeyword = "";
        }
        modelAndView.addObject("searchFilterKeyword",filterKeyword);
        modelAndView.addObject("platform", platform);
        modelAndView.setViewName("view/home/searchDetails");

        return modelAndView;

    }

    @RequestMapping(value = "/checkSensitive")
    @ResponseBody
    public ModelDto checkSensitive(Integer index,String keyword){
        ModelDto dto= new ModelDto();
        try {
            List<String> commonList= GetCommon.analyzerSensitiveWords(keyword);
            if (index == 1){
                List<String> emotionSensitiveWords = GetCommon.analyzerSensitiveWordsEmotion(keyword);
                if ((emotionSensitiveWords!=null && emotionSensitiveWords.size()>0) || (commonList!=null && commonList.size()>0)) {
                    dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
                    return dto;
                }
            }else {
                List<String> sensitiveWordList= GetCommon.analyzerSensitiveWordsHot(keyword);
                if ((sensitiveWordList != null && sensitiveWordList.size() > 0) || (commonList!=null && commonList.size()>0)) {
                    dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
                    return dto;
                }
            }
        }catch (Exception e){
            dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
            return dto;
        }
        return  dto;
    }

    @RequestMapping(value = "/doSearchShareCode")
    @ResponseBody
    public ModelDto doSearchShareCode(HttpServletRequest request,String keyword,String title,String filterKeyword){
        fetchSessionAdmin(request);
        String userStr = "0"+String.valueOf(new Random().nextInt(10000));

        ModelDto dto= new ModelDto(1);

        if(admin != null && admin.getUserId() != null){
            userStr = admin.getUserId().toString();
        }

        Object code = SinaWeiboMid2Id.id2Mid(BusinessConstant.OPERATE_LOG_PRODUCT_SECTION_SEARCH_ACCURATE+userStr+System.currentTimeMillis());


        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        }
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        if (StringUtils.isEmpty(filterKeyword)){
            filterKeyword = "";
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("keyword1",keyword);
        jsonObject.put("title1",title);
        jsonObject.put("filterKeyword1",filterKeyword);
        H5SearchShare share = new H5SearchShare();
        share.setJsonData(JSONObject.toJSONString(jsonObject));
        share.setStatus(1);
        share.setTabIndex("#1");
        share.setShareCode(String.valueOf(code));
        apiInfoClient.saveH5SearchShare(share);
        log.info("title1:{},keyword1:{},filterKeyword1:{}",title,keyword,filterKeyword);
        dto.setObj(code);
        return dto;
    }


    @RequestMapping(value = "/searchDetailsShare")
    public ModelAndView searchDetailsShare( HttpServletRequest request, ModelAndView modelAndView,String shareCode){
        fetchSessionAdmin(request);

        String keyword = "";
        String title = "";
        String filterKeyword = "";
        if (StringUtils.isNotBlank(shareCode)){
            BaseDto<H5SearchShare> baseDto = apiInfoClient.findH5SearchShare(shareCode);
            String str = baseDto.getData().getJsonData();
            if (StringUtils.isNotBlank(str)){
                JSONObject jsonObject = JSONObject.parseObject(str);
                keyword = jsonObject.getString("keyword1");
                title = jsonObject.getString("title1");
                filterKeyword = jsonObject.getString("filterKeyword1");
            }
        }else {
            keyword = request.getParameter("keyword");
            title = request.getParameter("title");
            filterKeyword = request.getParameter("filterKeyword");
        }
        if (modelAndView == null){
            modelAndView = new ModelAndView();
        }
        if (StringUtils.isEmpty(keyword)) {
            keyword = "";
        }
        if (StringUtils.isEmpty(title)) {
            title = "";
        }
        if (StringUtils.isEmpty(filterKeyword)){
            filterKeyword = "";
        }
        modelAndView.addObject("searchKeyword",keyword);
        modelAndView.addObject("shareCode",shareCode);
        modelAndView.addObject("searchTitle",title);
        modelAndView.addObject("searchFilterKeyword",filterKeyword);
        modelAndView.addObject("platform", platform);

        log.info("title1:{},keyword1:{},filterKeyword1:{}",title,keyword,filterKeyword);

        modelAndView.setViewName("view/home/searchDetails");

        return modelAndView;

    }




    /**
     * 搜索走势 热度
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHotline")
    @ResponseBody
    public String getHotline(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        String restul=homeService.getHotline(request,admin);
        return restul;
    }



    /**
     * 搜索词云
     * @param request
     * @return
     */
    @RequestMapping(value = "/getWordcloud")
    @ResponseBody
    public String getWordcloud(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        String restul=homeService.getWordcloud(request,admin);
        return restul;
    }

    private List<User> parseUsers(){
        List<User> list= new ArrayList<>();
        for(int i=0;i<10;i++){
            User map= new User();
            map.setNickname("name"+i);
            map.setUsername("username"+i);
            list.add(map);
        }
        return list;
    }

    /**
     * 情绪占比
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionProportion")
    @ResponseBody
    public ModelDto getEmotionProportion(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionProportion(request,admin);
        return dto;
    }




    /**
     * 情绪地图
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionMap")
    @ResponseBody
    public ModelDto getEmotionMap(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionMap(request,admin);
        return dto;
    }


    /**
     * 情绪走势
     * @param request
     * @return
     */
    @RequestMapping(value = "/getContentTrends")
    @ResponseBody
    public ModelDto getContentTrends(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getContentTrends(request,admin);
        return dto;
    }


    /**
     * 六元情绪洞察-性别情绪分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionSex")
    @ResponseBody
    public ModelDto getEmotionSex(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionSex(request,admin);
        return dto;
    }



    /**
     * 六元情绪洞察-用户认证类型情绪分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionType")
    @ResponseBody
    public ModelDto getEmotionType(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionType(request,admin);
        return dto;
    }


    /**
     * 二元情绪洞察-性别情绪分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionSex2")
    @ResponseBody
    public ModelDto getEmotionSex2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionSex2(request,admin);
        return dto;
    }


    /**
     * 二元情绪洞察-用户认证类型情绪分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionType2")
    @ResponseBody
    public ModelDto getEmotionType2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionType2(request,admin);
        return dto;
    }


    /**
     * 二元情绪洞察-粉丝数量分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionFans")
    @ResponseBody
    public ModelDto getEmotionFans(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionFans(request,admin);
        return dto;
    }



    /**
     * 二元情绪洞察-转发层级
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionLevel")
    @ResponseBody
    public ModelDto getEmotionLevel(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionLevel(request,admin);
        return dto;
    }


    /**
     * 二元情绪洞察-兴趣标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEmotionHobby")
    @ResponseBody
    public ModelDto getEmotionHobby(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionHobby(request,admin);
        return dto;
    }




    /**
     * 二元情绪洞察-正面热词
     * @param request
     * @return
     */
    @RequestMapping(value = "/getZMHotWord")
    @ResponseBody
    public ModelDto getZMHotWord(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getZMHotWord(request,admin);
        return dto;
    }


    /**
     * 二元情绪洞察-负面热词
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFMHotWord")
    @ResponseBody
    public ModelDto getFMHotWord(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getFMHotWord(request,admin);
        return dto;
    }




    @RequestMapping(value = "/operatePageLog")
    @ResponseBody
    public void operatePageLog(HttpServletRequest request,String pageCode,String pageName){
        admin=fetchSessionAdmin(request);
        OperateLogObjectDto operateLogObjectDto = new OperateLogObjectDto();
        try {
            CommonUtils.opreateLog(request,pageName,Integer.valueOf(pageCode),null,1,null,null);
        }catch (Exception e){

        }
    }


    @RequestMapping(value = "/verifyLoginServlet")
    @ResponseBody
    public String verifyLoginServlet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        String str = "";

        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
        String type = request.getParameter("type");//1-登录,2-短信登录,3-注册,4-热度防刷
        String mobile = request.getParameter("mobile");
        String userId = "";
        if (admin != null){
            userId = admin.getUserId().toString();
        }else{
            userId = CommonUtils.getIp(request);
        }
        if(type != null) {
            // 从redis中获取userid状态
            String gt = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GEETEST_USERID+userId));
            log.info("geetest : gtStatusObj = [" + gt + "]");
            if(StringUtils.isNotBlank(gt)) {
                GeetestValid uv = JSON.parseObject(gt,GeetestValid.class);
                int gt_server_status_code = uv.getGtServerStatus();

                //自定义参数,可选择添加
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("user_id", userId); //网站用户id
                param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
                param.put("ip_address", userId); //传输用户请求验证时所携带的IP

                int gtResult = 0;

                if (gt_server_status_code == 1) {
                    // gt-server正常，向gt-server进行二次验证
                    gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
                    log.info(gtResult+"");
                } else {
                    // gt-server非正常情况下，进行failback模式验证
                    log.info("failback:use your own server captcha validate");
                    gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
                    log.info(gtResult+"");
                }

                if (gtResult == 1) {
                    // 验证成功
                    org.json.JSONObject data = new org.json.JSONObject();
                    try {
                        if(("2".equals(type) || "3".equals(type))&& StringUtils.isNotBlank(mobile)) {
                            RedisUtils.setAttribute(RedisUtils.generateJedisKey(new StringBuilder(SystemConstants.JEDIS_KEYS_SMS_CHECK_SUCCESS).append(mobile).toString()), "1", 10 * 60);
                            log.info("VerifyLoginServlet.doPost [" + mobile + "] check geetest success!");
                        }
                        uv.setVerifyingSuccessTimes(uv.getVerifyingSuccessTimes()+1);
                        uv.setUpdateTime(new Date());
                        uv.setGtServerStatus(0);
                        RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GEETEST_USERID+userId),JSON.toJSONString(uv));
                        data.put("status", "success");
                        data.put("version", gtSdk.getVersionInfo());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    str = data.toString();
                } else {
                    // 验证失败
                    org.json.JSONObject data = new org.json.JSONObject();
                    try {
                        uv.setVerifyingFailTimes(uv.getVerifyingFailTimes()+1);
                        uv.setUpdateTime(new Date());
                        uv.setGtServerStatus(0);
                        RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GEETEST_USERID+userId),JSON.toJSONString(uv));
                        data.put("status", "fail");
                        data.put("version", gtSdk.getVersionInfo());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    str = data.toString();
                }
            } else {
                // 验证失败
                org.json.JSONObject data = new org.json.JSONObject();
                try {
                    data.put("status", "fail");
                    data.put("version", gtSdk.getVersionInfo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                str = data.toString();
            }
        }
        return  str;
    }


    /**
     * 热度 情绪解封
     * @param request
     * @return
     */
    @RequestMapping(value = "/doUnseal")
    @ResponseBody
    public ModelDto doUnseal(HttpServletRequest request){


        YwztParams params = new YwztParams();
        Map<String, Object> result=new HashMap<String, Object>();
        if(admin == null){
            fetchSessionAdmin(request);
        }
        String userTag = "";
        if (admin != null){
            userTag = admin.getUserId().toString();
        }else{
            userTag = CommonUtils.getIp(request);
        }
        if (userTag.equals("0:0:0:0:0:0:0:1")){
            userTag = "0-0-0-0-0-0-0-1";
        }
        try {

            String co = "0000";
            List<String> ll = new ArrayList<>();
            String searchChar = GetCommon.getSystemConfigMap().get("SSJK_METHOD_API_V1_SEARCH_CHART");
            String hotLine = GetCommon.getSystemConfigMap().get("RD_METHOD_API_ALL_HOT_LINE");
            String wordCloud = GetCommon.getSystemConfigMap().get("GGFX_METHOD_API_ANLYZER_WORDCLOUD");
            String searchAPI= GetCommon.getSystemConfigMap().get("SEARCH_API_PREFIX");
            String hotLineAPI = GetCommon.getSystemConfigMap().get("AB_API_PREFIX");
            String wordCloudAPI = GetCommon.getSystemConfigMap().get("GGFX_API_PREFIX");
            if (StringUtils.isNoneBlank(searchChar)){
                searchChar = searchChar.replace("?","");
            }else {
                searchChar = "api/v1/search/chart";
            }
            if (StringUtils.isNoneBlank(hotLine)){
                hotLine = hotLine.replace("?","");
            }else {
                hotLine = "api/all/hot/line";
            }
            if (StringUtils.isNoneBlank(wordCloud)){
                wordCloud = wordCloud.replace("?","");
            }else {
                wordCloud = "api/anlyzer/wordCloud";
            }
            String timeStr = "EXPIRED_TIME";//RedisUtil.EXPIREDTIME;//"EXPIRED_TIME";
            ll.add("wyq-h5-"+userTag+"-"+searchAPI+searchChar+"-"+timeStr);
            ll.add("wyq-h5-"+userTag+"-"+hotLineAPI+hotLine+"-"+timeStr);
            ll.add("wyq-h5-"+userTag+"-"+wordCloudAPI+wordCloud+"-"+timeStr);
            params.setIntegerFaceKeyList(ll);

            if (ll!=null && ll.size() >0){
                BaseView view1 =  WyqUnBlockV1.userUnBlock(userTag,params);
                log.info(new Date() + "--doUnseal.view1 =" + JSONObject.toJSONString(view1));
            } else {
                co = "9999";
            }
            result.put("code", co);
            if("0000".equals(co)){
                return  new ModelDto(1,result);

            }else{
                return  new ModelDto(0,result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "9999");
        }
        return  new ModelDto(0,result);
    }



    @RequestMapping(value = "/startCaptchaServlet")
    public void startCaptchaServlet(HttpServletRequest request, HttpServletResponse response,String pageCode,String pageName){
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        // 自定义userid

        String userId = "";
        if (admin != null){
            userId = admin.getUserId().toString();
        }else{
            userId = CommonUtils.getIp(request);
        }

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userId); //网站用户id
        param.put("client_type", "h5"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", userId); //传输用户请求验证时所携带的IP

        // 进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        String countStr = RedisUtils.getAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GEETEST_USERID+userId));
        GeetestValid uv = new GeetestValid();
        if(StringUtils.isBlank(countStr)) {
            uv.setIp(userId);
            uv.setUserId(userId);
            uv.setVerifyingTimes(1);
            uv.setGtServerStatus(gtServerStatus);
            uv.setCreateTime(new Date());
        }else {
            uv = JSON.parseObject(countStr, GeetestValid.class);
            uv.setIp(userId);
            uv.setGtServerStatus(gtServerStatus);
            uv.setVerifyingTimes(uv.getVerifyingTimes()+1);
            uv.setUpdateTime(new Date());
        }
        // 将userid调用验证码的次数保存到redis中
        RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_GEETEST_USERID+userId),JSON.toJSONString(uv));

        resStr = gtSdk.getResponseStr();

        try {
            PrintWriter out = response.getWriter();
            out.println(resStr);
        }catch (Exception e){

        }
    }


    /**
     * 客户端竞品分析页面
     * @param request
     * @param response
     */
    @RequestMapping(value = "/lookShareCodeReport.action")
    public void lookShareCodeReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"compet/lookShareCodeReport");
        }catch (Exception e){

        }
    }


    //兼容
    /**
     * 竞品分析 web分享 查看
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/productsAnalysisDemo")
    public void productsAnalysisDemo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"compet/lookShareCodeReport");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/weiboEventAnalysis1/analysisPreviewDemo")
    public void weiboAnalysisPreviewDemo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"weibo/analysisPreviewDemo");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/eventAnalysis1/analysisPreviewDemo")
    public void analysisPreviewDemo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"analysisPreviewDemo");
        }catch (Exception e){

        }
    }

    /**
     * 1-自定义词对比；2-监测方案对比
     * @param request
     * @param response
     */
    @RequestMapping(value = "/appProductsAnalysisChat.action")
    public void appProductsAnalysisChat(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"compet/appProductsAnalysisChat");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/eventAnalysis1/analysisPreview.action")
    public void eventAnalysisPreview(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"analysisPreview");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/weiboEventAnalysis1/analysisPreview.action")
    public void weiboAnalysisPreview(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"weibo/analysisPreview");
        }catch (Exception e){

        }
    }


    /**
     * 热门搜索报告
     * @param request
     * @param response
     */
    @RequestMapping(value = "/searchResult.shtml")
    public void searchResult(HttpServletRequest request, HttpServletResponse response) {
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"compet/lookShareCodeReport");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/hotSearch/searchHeatResultApp.shtml")
    public void searchHeatResultApp(HttpServletRequest request, HttpServletResponse response,String filterKeyword,String searchKeyword,String keyword1,ModelAndView modelAndView) {
        try {
//            Utils.sendRedirect(request,response,"http://127.0.0.1:1178/","searchAPPHotDetails");
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"searchAPPHotDetails");
        }catch (Exception e){

        }
    }

    @RequestMapping(value = "/view/weiboMood/goWeiboMoodApp.shtml")
    public void goWeiboMoodApp(HttpServletRequest request, HttpServletResponse response) {
        try {
//            Utils.sendRedirect(request,response,"http://127.0.0.1:1178/","searchAPPEmotionDetails");
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"searchAPPEmotionDetails");
        }catch (Exception e){
        }
    }

    @RequestMapping(value = "/searchAPPHotDetails")
    public ModelAndView searchAPPHotDetails(HttpServletRequest request, HttpServletResponse response,String filterKeyword,String searchKeyword,String keyword1,ModelAndView modelAndView) {
        admin = fetchSessionAdmin(request);
        Integer isSensitive = 0;
        try {
            List<String> commonList= GetCommon.analyzerSensitiveWords(keyword1);
            List<String> sensitiveWordList= GetCommon.analyzerSensitiveWordsHot(keyword1);
            if ((sensitiveWordList != null && sensitiveWordList.size() > 0) || (commonList!=null && commonList.size()>0)) {
//                dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
                isSensitive =1;
            }
        }catch (Exception e){
//            dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
        }
        String userStr = "0"+String.valueOf(new Random().nextInt(10000));
        if(admin != null && admin.getUserId() != null){
            userStr = admin.getUserId().toString();
        }
        Object code = SinaWeiboMid2Id.id2Mid("5005"+userStr+System.currentTimeMillis());
        String  shareCode = String.valueOf(code);
        if (modelAndView == null){
            modelAndView = new ModelAndView();
        }
        if (StringUtils.isEmpty(searchKeyword)) {
            searchKeyword = "";
        }
        if (StringUtils.isEmpty(keyword1)) {
            keyword1 = "";
        }
        modelAndView.addObject("searchKeyword",keyword1);
        modelAndView.addObject("shareCode",shareCode);
        modelAndView.addObject("searchTitle",searchKeyword);
        if (StringUtils.isEmpty(filterKeyword)){
            filterKeyword = "";
        }
        modelAndView.addObject("searchFilterKeyword",filterKeyword);
        modelAndView.addObject("platform", platform);
        modelAndView.addObject("isApp", 1);
        modelAndView.addObject("isSensitive", isSensitive);
        modelAndView.setViewName("view/home/searchAPPDetails");

        return modelAndView;
    }

    @RequestMapping(value = "/searchAPPEmotionDetails")
    public ModelAndView searchAPPEmotionDetails(HttpServletRequest request, HttpServletResponse response,String filterKeyword,String searchKeyword,String keyword1,ModelAndView modelAndView) {
        admin = fetchSessionAdmin(request);
        Integer isSensitive = 0;
        try {
            List<String> commonList= GetCommon.analyzerSensitiveWords(keyword1);
            List<String> emotionSensitiveWords = GetCommon.analyzerSensitiveWordsEmotion(keyword1);
            if ((emotionSensitiveWords!=null && emotionSensitiveWords.size()>0) || (commonList!=null && commonList.size()>0)) {
//                dto = new ModelDto(0,"对不起，您的关键词包含敏感词语，请检查后再试！");
                isSensitive = 1;
            }
        }catch (Exception e){
        }
        String userStr = "0"+String.valueOf(new Random().nextInt(10000));
        if(admin != null && admin.getUserId() != null){
            userStr = admin.getUserId().toString();
        }
        Object code = SinaWeiboMid2Id.id2Mid("5005"+userStr+System.currentTimeMillis());
        String  shareCode = String.valueOf(code);
        if (modelAndView == null){
            modelAndView = new ModelAndView();
        }
        if (StringUtils.isEmpty(searchKeyword)) {
            searchKeyword = "";
        }
        if (StringUtils.isEmpty(keyword1)) {
            keyword1 = "";
        }
        modelAndView.addObject("searchKeyword",keyword1);
        modelAndView.addObject("shareCode",shareCode);
        modelAndView.addObject("searchTitle",searchKeyword);
        if (StringUtils.isEmpty(filterKeyword)){
            filterKeyword = "";
        }
        modelAndView.addObject("searchFilterKeyword",filterKeyword);
        modelAndView.addObject("platform", platform);
        modelAndView.addObject("isApp", 2);
        modelAndView.addObject("isSensitive", isSensitive);
        modelAndView.setViewName("view/home/searchAPPDetails");
        return modelAndView;
    }

    @RequestMapping(value = "/i/singleWeiboAnalysis/index.shtml")
    public void singleWeiboAnalysis(HttpServletRequest request, HttpServletResponse response){
        try {
            Utils.sendRedirect(request,response,SysConfig.cfgMap.get("SYSTEM_H5_URL"),"weiboAnalysis/weiboAnalysisIndex");
        }catch (Exception e){

        }
    }

    /**
     *  热度报告
     * @param request
     * @param modelAndView
     * @param shareCode
     * @return
     */
    @RequestMapping(value = "/view/search/goSearchShare")
    public ModelAndView goSearchShare(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {

        admin = fetchSessionAdmin(request);

        if (StringUtils.isNoneBlank(shareCode)){
            if (admin == null){
                modelAndView.setViewName("/userLogin");
                modelAndView.addObject("urlType","home");
                return modelAndView;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("shareCode",shareCode);
            BaseDto<HeatShareDto> baseDto = apiInfoClient.findHs(map);
            if (baseDto!= null && baseDto.getData() != null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                modelAndView.addObject("searchKeyword",baseDto.getData().getTitle());
                modelAndView.addObject("keyword1",baseDto.getData().getKeyword());
                modelAndView.addObject("searchKeyword1",baseDto.getData().getTitle());
                if (StringUtils.isNoneBlank(baseDto.getData().getFilterKeyword())){
                    modelAndView.addObject("filterKeyword1",baseDto.getData().getFilterKeyword());
                }else {
                    modelAndView.addObject("filterKeyword1","");
                }
                modelAndView.addObject("shareCode",shareCode);
                modelAndView.addObject("heatShareCode",shareCode);
                if (StringUtils.isNoneBlank(baseDto.getData().getCategoryId())){
                    modelAndView.addObject("categoryId",baseDto.getData().getCategoryId());
                }else {
                    modelAndView.addObject("categoryId","");
                }
                if (StringUtils.isNoneBlank(baseDto.getData().getType())){
                    modelAndView.addObject("type",baseDto.getData().getType());
                }else {
                    modelAndView.addObject("type","");
                }
                modelAndView.addObject("admin",admin);
                modelAndView.addObject("platform",platform);

                modelAndView.addObject("message","");
                modelAndView.addObject("date",baseDto.getData().getDate());
                modelAndView.addObject("startTime",baseDto.getData().getStartTime());
                modelAndView.addObject("endTime",baseDto.getData().getEndTime());
                modelAndView.addObject("message","");
                modelAndView.addObject("searchShareCode",shareCode);
                modelAndView.setViewName("view/home/searchShare");
            }else {
//                modelAndView.addObject("message","暂无数据");
                modelAndView.setViewName(Flags.login_view);
            }
        }else {
            modelAndView.setViewName(Flags.login_view);
        }
        return modelAndView;
    }

    /**
     * 热度报告 - 热度指数
     * @param request
     * @param modelAndView
     * @param shareCode
     * @return
     */
    @RequestMapping(value = "/view/hotSearch/goHotSearchShare")
    public ModelAndView goHotSearchShare(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {

        admin = fetchSessionAdmin(request);
        if (StringUtils.isNoneBlank(shareCode)){

            if (admin == null){
                modelAndView.setViewName("/userLogin");
                modelAndView.addObject("urlType","/view/hotSearch/goHotSearchShare");
                return modelAndView;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("shareCode",shareCode);
            BaseDto<HeatShareDto> baseDto = apiInfoClient.findHs(map);
            if (baseDto!= null && baseDto.getData() != null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                modelAndView.addObject("searchKeyword",baseDto.getData().getTitle());
                modelAndView.addObject("keyword1",baseDto.getData().getKeyword());
                modelAndView.addObject("searchKeyword1",baseDto.getData().getTitle());
                if (StringUtils.isNoneBlank(baseDto.getData().getFilterKeyword())){
                    modelAndView.addObject("filterKeyword1",baseDto.getData().getFilterKeyword());
                }else {
                    modelAndView.addObject("filterKeyword1","");
                }
                modelAndView.addObject("shareCode",shareCode);
                modelAndView.addObject("heatShareCode",shareCode);
                if (StringUtils.isNoneBlank(baseDto.getData().getCategoryId())){
                    modelAndView.addObject("categoryId",baseDto.getData().getCategoryId());
                }else {
                    modelAndView.addObject("categoryId","");
                }
                if (StringUtils.isNoneBlank(baseDto.getData().getType())){
                    modelAndView.addObject("type",baseDto.getData().getType());
                }else {
                    modelAndView.addObject("type","");
                }
                modelAndView.addObject("admin",admin);
                modelAndView.addObject("platform",platform);

                modelAndView.addObject("message","");
                modelAndView.addObject("date",baseDto.getData().getDate());
                modelAndView.addObject("startTime",baseDto.getData().getStartTime());
                modelAndView.addObject("endTime",baseDto.getData().getEndTime());
                modelAndView.setViewName("view/home/hot_search_share");
            }else {
                modelAndView.setViewName(Flags.login_view);
            }
        }else {
            modelAndView.setViewName(Flags.login_view);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view/moodMap/goMoodMap")
    public ModelAndView goMoodMap(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        return modelAndView;
    }

    @RequestMapping(value = "/view/search/goKeywordSearch")
    public ModelAndView goKeywordSearch(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        return modelAndView;
    }


    @RequestMapping(value = "/view/search/goProductsAnalysis")
    public ModelAndView goProductsAnalysis(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        return modelAndView;
    }

    @RequestMapping(value = "/view/search/goNetworkDaily")
    public ModelAndView goNetworkDaily(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        return modelAndView;
    }
    @RequestMapping(value = "/view/spreadAnalysis/goSpreadAnalysis")
    public ModelAndView goSpreadAnalysis(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        admin = fetchSessionAdmin(request);
        if (StringUtils.isNoneBlank(shareCode)){
            if (admin == null){
                modelAndView.setViewName("/userLogin");
                modelAndView.addObject("urlType","/view/spreadAnalysis/goSpreadAnalysis");
                return modelAndView;
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("shareCode",shareCode);
            BaseDto<HeatShareDto> baseDto = apiInfoClient.findHs(map);
            if (baseDto!= null && baseDto.getData() != null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                modelAndView.addObject("searchKeyword",baseDto.getData().getTitle());
                modelAndView.addObject("keyword1",baseDto.getData().getKeyword());
                modelAndView.addObject("searchKeyword1",baseDto.getData().getTitle());
                if (StringUtils.isNoneBlank(baseDto.getData().getFilterKeyword())){
                    modelAndView.addObject("filterKeyword1",baseDto.getData().getFilterKeyword());
                }else {
                    modelAndView.addObject("filterKeyword1","");
                }
                modelAndView.addObject("shareCode",shareCode);
                modelAndView.addObject("heatShareCode",shareCode);
                modelAndView.addObject("searchShareCode",shareCode);

                if (StringUtils.isNoneBlank(baseDto.getData().getCategoryId())){
                    modelAndView.addObject("categoryId",baseDto.getData().getCategoryId());
                }else {
                    modelAndView.addObject("categoryId","");
                }
                if (StringUtils.isNoneBlank(baseDto.getData().getType())){
                    modelAndView.addObject("type",baseDto.getData().getType());
                }else {
                    modelAndView.addObject("type","");
                }
                modelAndView.addObject("admin",admin);
                modelAndView.addObject("platform",platform);

                modelAndView.addObject("message","");
                modelAndView.addObject("date",baseDto.getData().getDate());
                modelAndView.addObject("startTime",baseDto.getData().getStartTime());
                modelAndView.addObject("endTime",baseDto.getData().getEndTime());
                modelAndView.setViewName("view/home/spread_analysis");
            }else {
                modelAndView.setViewName(Flags.login_view);
            }
        }else {
            modelAndView.setViewName(Flags.login_view);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view/opinionsAnalysis/opinionsAnalysisResult")
    public ModelAndView opinionsAnalysisResult(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        admin = fetchSessionAdmin(request);
        if (StringUtils.isNoneBlank(shareCode)){
            if (admin == null){
                modelAndView.setViewName("/userLogin");
                modelAndView.addObject("urlType","/view/opinionsAnalysis/opinionsAnalysisResult");
                return modelAndView;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("shareCode",shareCode);
            BaseDto<HeatShareDto> baseDto = apiInfoClient.findHs(map);
            if (baseDto!= null && baseDto.getData() != null && baseDto.getCode().equals(CodeConstant.SUCCESS_CODE)){
                modelAndView.addObject("searchKeyword",baseDto.getData().getTitle());
                modelAndView.addObject("keyword1",baseDto.getData().getKeyword());
                modelAndView.addObject("searchKeyword1",baseDto.getData().getTitle());
                if (StringUtils.isNoneBlank(baseDto.getData().getFilterKeyword())){
                    modelAndView.addObject("filterKeyword1",baseDto.getData().getFilterKeyword());
                }else {
                    modelAndView.addObject("filterKeyword1","");
                }
                modelAndView.addObject("shareCode",shareCode);
                modelAndView.addObject("heatShareCode",shareCode);
                modelAndView.addObject("searchShareCode",shareCode);
                if (StringUtils.isNoneBlank(baseDto.getData().getCategoryId())){
                    modelAndView.addObject("categoryId",baseDto.getData().getCategoryId());
                }else {
                    modelAndView.addObject("categoryId","");
                }
                if (StringUtils.isNoneBlank(baseDto.getData().getType())){
                    modelAndView.addObject("type",baseDto.getData().getType());
                }else {
                    modelAndView.addObject("type","");
                }
                modelAndView.addObject("admin",admin);
                modelAndView.addObject("platform",platform);
                modelAndView.addObject("message","");
                modelAndView.addObject("date",baseDto.getData().getDate());
                modelAndView.addObject("startTime",baseDto.getData().getStartTime());
                modelAndView.addObject("endTime",baseDto.getData().getEndTime());
                modelAndView.setViewName("view/home/opinions_analysis");
            }else {
                modelAndView.setViewName(Flags.login_view);
            }
        }else {
            modelAndView.setViewName(Flags.login_view);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view/moodMap/goWeiboMoodShare")
    public ModelAndView goWeiboMoodShare(HttpServletRequest request,ModelAndView modelAndView,String shareCode) {
        return modelAndView;
    }

    @RequestMapping(value = "/report/getWeixinConfig")
    @ResponseBody
    public  Map<String, String> getWeixinConfig(HttpServletRequest request,String url) throws Exception{
        String ticket = WeixinJsPayUtils.getJsapiTicket();
        Map<String, String> ret = WeixinJsPayUtils.sign(ticket, url);
        return  ret;
    }

    /**
     * 热度报告 - 信息来源
     * @param request
     * @return
     */
    @RequestMapping(value = "/doInfoFromV2")
    @ResponseBody
    public ModelDto doInfoFromV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getInfoFromV2(request,admin);
        return dto;
    }

    /**
     * 热度报告 - 活跃媒体
     * @param request
     * @return
     */
    @RequestMapping(value = "/doActiveMediaV2")
    @ResponseBody
    public ModelDto doActiveMediaV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getActiveMediaV2(request,admin);
        return dto;
    }


    /**
     * 热度报告 - 声量走势
     * @param request
     * @return
     */
    @RequestMapping(value = "/doVolumeMapV3")
    @ResponseBody
    public ModelDto doVolumeMapV3(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getVolumeMapV3(request,admin);
        return dto;
    }


    /**
     * 热度报告 - 地域分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/doVirtualSolutionV3")
    @ResponseBody
    public ModelDto doVirtualSolutionV3(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getVirtualSolutionV3(request,admin);
        return dto;
    }

    /**
     * 热度报告 - 热点网民
     * @param request
     * @return
     */
    @RequestMapping(value = "/doHotPeopleV2")
    @ResponseBody
    public ModelDto doHotPeopleV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getHotPeopleV2(request,admin);
        return dto;
    }

    /**
     * 热度报告 -媒体友好度
     * @param request
     * @return
     */
    @RequestMapping(value = "/doMediaFriendV3")
    @ResponseBody
    public ModelDto doMediaFriendV3(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getMediaFriendV3(request,admin);
        return dto;
    }

    /**
     * 热度报告 -用户情感洞察-性别
     * @param request
     * @return
     */
    @RequestMapping(value = "/doEmotionSexV2")
    @ResponseBody
    public ModelDto doEmotionSexV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionSexV2(request,admin);
        return dto;
    }


    /**
     * 热度报告 -用户情感洞察-用户认证类型
     * @param request
     * @return
     */
    @RequestMapping(value = "/doEmotionTypeV2")
    @ResponseBody
    public ModelDto doEmotionTypeV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionTypeV2(request,admin);
        return dto;
    }


    /**
     * 热度报告 -用户情感洞察-粉丝数量分布
     * @param request
     * @return
     */
    @RequestMapping(value = "/doEmotionFansV2")
    @ResponseBody
    public ModelDto doEmotionFansV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionFansV2(request,admin);
        return dto;
    }

    /**
     * 热度报告 -用户情感洞察-转发层级
     * @param request
     * @return
     */
    @RequestMapping(value = "/doEmotionLevelV2")
    @ResponseBody
    public ModelDto doEmotionLevelV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionLevelV2(request,admin);
        return dto;
    }


    /**
     * 热度报告 -用户情感洞察-兴趣标签
     * @param request
     * @return
     */
    @RequestMapping(value = "/doEmotionHobbyV2")
    @ResponseBody
    public ModelDto doEmotionHobbyV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getEmotionHobbyV2(request,admin);
        return dto;
    }


    /**
     * 热度报告 -口碑热词-非敏感
     * @param request
     * @return
     */
    @RequestMapping(value = "/doZMHotWordV2")
    @ResponseBody
    public ModelDto doZMHotWordV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getZMHotWordV2(request,admin);
        return dto;
    }

    /**
     * 热度报告 -口碑热词-敏感
     * @param request
     * @return
     */
    @RequestMapping(value = "/doFMHotWordV2")
    @ResponseBody
    public ModelDto doFMHotWordV2(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getFMHotWordV2(request,admin);
        return dto;
    }



    /**
     * 热度报告 -热度指数
     * @param request
     * @return
     */
    @RequestMapping(value = "/doStatAndLineNewV4")
    @ResponseBody
    public ModelDto doStatAndLineNewV4(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getStatAndLineNewV4(request,admin);
        return dto;
    }

    /**
     * 热度报告 -全网声量
     * @param request
     * @return
     */
    @RequestMapping(value = "/doRelatedTermsV3")
    @ResponseBody
    public ModelDto doRelatedTermsV3(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getRelatedTermsV3(request,admin);
        return dto;
    }


    /**
     * 热度报告 -热门信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/doHotMessage")
    @ResponseBody
    public ModelDto doHotMessage(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getHotMessage(request,admin);
        return dto;
    }

    /**
     * 热度报告 -关键词云
     * @param request
     * @return
     */
    @RequestMapping(value = "/doWordcloudV3")
    @ResponseBody
    public ModelDto doWordcloudV3(HttpServletRequest request){
        admin=fetchSessionAdmin(request);
        ModelDto dto=homeService.getWordcloudV3(request,admin);
        return dto;
    }



//    @RequestMapping(value = "view/hotEvent/hotList")
//    public ModelAndView getHotListView(HttpServletRequest request,ModelAndView modelAndView) {
//
//        admin=fetchSessionAdmin(request);
//        if (admin == null){
//            modelAndView.setViewName(Flags.error_view);
//            return modelAndView;
//        }
//        int rankType = 17;
//        String shareTitle = "24小时热点榜";
//        String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php";
//        String ip = CommonUtils.getIp(request);
//        String params = "format=json&ip=" + ip;
//        String jsonStr = HttpRequestUtils.sendGet(url, params);
//        String province = null;
//        try {
//            JSONObject jsonObject = JSON.parseObject(jsonStr);
//            System.out.println("getHotListView() province == " + jsonStr);
//            province = jsonObject.getString("province");
//        } catch (Exception e) {
//            province = null;
//        }
//        Map<String,Object> map = new HashMap<>();
//        PageDto<OperationAdminHot> baseDto= apiInfoClient.getHotEventList(map);
//
//        if (baseDto == null && baseDto.getList().size() == 0){
//            modelAndView.setViewName(Flags.error_view);
//            return modelAndView;
//        }
//        modelAndView.addObject("hotList",baseDto.getList());
//        modelAndView.addObject("maxpage",50);
//        modelAndView.addObject("page",1);
//        modelAndView.addObject("keywordId","");
//        modelAndView.addObject("admin",admin);
//
//
//        modelAndView.setViewName("view/hotEventRanking/hotlist");
//        return modelAndView;
//    }


    /**
     * 24小时热点榜
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "view/hotEvent/hotEvent")
    public ModelAndView hotEvent(ModelAndView modelAndView){

        Map<String,Object> params = new HashMap<>();
        PageDto<OperationAdminHot> baseDto= apiInfoClient.getHotEventList(params);
        if (baseDto == null || baseDto.getList().size() == 0){
            modelAndView.setViewName(Flags.error_view);
            return modelAndView;
        }
        modelAndView.addObject("hotEventList",baseDto.getList());
        modelAndView.addObject("viewUri","");

        modelAndView.setViewName("view/hotEventRanking/hotEvent");
        return modelAndView;
    }

    @RequestMapping(value = "share/view/dailyHot")
    public ModelAndView dailyHot(ModelAndView modelAndView,String sharecode){

        Map<String,Object> params = new HashMap<>();
        params.put("shareCode",sharecode);
        PageDto<OperationAdminHot> baseDto= apiInfoClient.getHotEventList(params);
        if (baseDto == null || baseDto.getList().size() == 0){
            modelAndView.setViewName(Flags.error_view);
            return modelAndView;
        }
        OperationAdminHot hot = baseDto.getList().get(0);
        modelAndView.addObject("hotTitle",hot.getTitle());
        modelAndView.addObject("shareCode",hot.getShareCode());
        modelAndView.addObject("viewUri","");
        modelAndView.addObject("id",hot.getId());
        modelAndView.addObject("isChina",0);
        modelAndView.addObject("isApp",0);
        modelAndView.addObject("startTime",DateUtils.format(hot.getStartTime()));
        modelAndView.addObject("endTime",DateUtils.format(hot.getEndTime()));
        modelAndView.addObject("sentiment",hot.getSentiment());
        modelAndView.addObject("sentimentend",hot.getSentimentend());

//        List<OperationAdminHotContent> opalist=operationAdminHotServier.OperationAdminHotContentSelect(id);
        params = new HashMap<>();
        params.put("hId",hot.getId());
        PageDto<OperationAdminHotContent> pageDto= apiInfoClient.getHotEventContentList(params);
        if (pageDto!= null){
            modelAndView.addObject("dailyHotList",pageDto.getList());
        }
        modelAndView.setViewName("view/hotEventRanking/dailyHot");
        return modelAndView;
    }

    /**
     * 自定义分享摘要
     * @param modelAndView
     * @param id
     * @return
     */
    @RequestMapping(value = "share/view/getDailyHotBycode")
    public ModelAndView getDailyHotBycode(ModelAndView modelAndView,int id){


        return modelAndView;
    }


    @RequestMapping(value = "/view/hotEvent/getNewsByArea")
    @ResponseBody
    public List<com.xd.tools.pojo.HotIncident> getNewsByArea(HttpServletRequest request, String province){
        fetchSessionAdmin(request);
        String user = null;
        String url = SysConfig.cfgMap.get("API_DB_URL") + "/v2/statistics/hotKeywords";
        String params = "";
        String ip = CommonUtils.getIp(request);
        if (admin != null) {
            params = "sort=2&page=1&pagesize=5&platform=2&channel=1&userTag=" + user + "&province=" + province;
        } else {
            params = "sort=2&page=1&pagesize=5&platform=2&channel=1&userTag=" + ip + "&province=" + province;
        }
        String result = HttpRequestUtils.sendGet(url, params);
        List<HotIncident> hotIncidentList = new ArrayList<>();
        if (result != null && !"".equals(result)) {
            HotIncidentView hotIncidentView = JSON.parseObject(result, HotIncidentView.class);
            if ("0000".equals(hotIncidentView.getCode())) {
                hotIncidentList = hotIncidentView.getHotIncidentList();
                for (int i = 0; i < hotIncidentList.size(); i++) {
                    hotIncidentList.get(i).setRatioHotDay(hotIncidentList.get(i).getRatioHotDay());
                    hotIncidentList.get(i).setProvince(province);
                }
            } else {
                hotIncidentList = null;
            }
        } else {
            hotIncidentList = null;
        }
        return  hotIncidentList;
    }

    /**
     * 区域List
     * @param request
     * @return
     */
    @RequestMapping(value = "/view/hotEvent/getAreaList")
    @ResponseBody
    public List<MapList> getAreaList(HttpServletRequest request,ModelAndView modelAndView){

        List<Statistics> ctkMore = new ArrayList<>();// 更多

        int flag = 0;// 是否为更多榜单(0-不是 1-是)
        int listType = 0;
        int pageMore = 0;

        Map<String,Object> params = WyqDataConfig.getDataInitMap(request);
        params.put("rankTypeId1", 269);
        params.put("rankTypeId2", 271);
        params.put("page", 1);
        params.put("pagesize", 10);
        String url = SysConfig.cfgMap.get("API_DB_URL") + "/v3/statistics/others";
        if (flag == 1) {
            params.put("page", pageMore);
            if (listType != 0) {
                params.put("rankTypeId2", listType);
            }
        } else if (flag == 0) {
            params.put("rankTypeId2", listType);
        }

        String resultAll =HttpRequestUtils.sendGet(url, params);
        StatisticsView parseObject = JSON.parseObject(resultAll, StatisticsView.class);
        if (parseObject!= null){
            ctkMore = parseObject.getStatisticsList();
        }
        List<Statistics> ctkAll;// 股票全部
        fetchSessionAdmin(request);
        try {
            fetchProductlist(request,modelAndView,BusinessConstant.PRODUCT_PACKAGE_HOTDAILYMORE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long token = new Date().getTime();
        String user = null;
        String ip = CommonUtils.getIp(request);
        if (admin != null)
            user = admin.getUserId().toString();
        url = SysConfig.cfgMap.get("API_DB_URL") + "/v2/statistics/others";
        String paramsAll = "";
        if (admin != null) {
            paramsAll = "sort=3&order=1&type=1&platform=2&channel=1&userTag=" + user;
        } else {
            paramsAll = "sort=3&order=1&type=1&platform=2&channel=1&userTag=" + ip;
        }
        resultAll = HttpRequestUtils.sendGet(url, paramsAll);
        List<MapList> list = new ArrayList<>();
        for (int i = 0; i < ctkMore.size(); i++) {
            JSONArray arrC = JSONArray.fromObject(ctkMore.get(i));
            String str = arrC.getString(0);
            net.sf.json.JSONObject js = net.sf.json.JSONObject.fromObject(str);
            Iterator iterator = js.keys();
            String key = null;
            String value = null;
            MapList eeC = null;
            eeC = new MapList();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                value = js.getString(key);
                if (key.equals("name")) {
                    eeC.setName(value.replace("省", ""));
                } else if (key.equals("numberDay")) {
                    eeC.setValue(Integer.parseInt(value));
                }
            }
            list.add(eeC);
        }
        return list;
    }





}
