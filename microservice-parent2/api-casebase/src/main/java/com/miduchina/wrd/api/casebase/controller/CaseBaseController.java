package com.miduchina.wrd.api.casebase.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.casebase.commons.Dwr;
import com.miduchina.wrd.api.casebase.service.CaseBaseService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.casebase.WyqCaseDto;
import com.miduchina.wrd.po.casebase.WyqCase;
import com.miduchina.wrd.po.eventanalysis.weiboevent.IContentWeiboCommonNetView;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.CommonUtils;
import com.miduchina.wrd.api.casebase.commons.EventType;
import com.miduchina.wrd.util.FileUtils;
import com.xd.tools.pojo.*;
import com.xd.tools.view.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.xd.tools.method.base.BaseMethodV1.getUserTag;

/**
 * Created by sty on 2018/12/25.
 * @author sty
 */
@RestController
@RequestMapping("api/v1")
public class CaseBaseController {

    @Autowired
    private CaseBaseService caseBaseService;

/**
 * @Author ZhuFangTao
 * @Description 
 * @Date  2019/5/22 17:01
 * @Param [param]
 * @return com.miduchina.wrd.dto.BaseDto
 */
    @ApiOperation(value = "热点事件走势图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "热点事件走势图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hotEventLineData")
    public BaseDto go30HotWorth(@ApiParam(value = "参数集合"
            +"eventLabel:事件标签ID(0:全部 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治)"
    ) @RequestParam Map<String, Object> param){
        BaseDto baseDto = new BaseDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> result = new HashMap<String, Object>();
        List<String> stamp = getHotWorthStamp();
        List<String> type = new ArrayList<String>();
        List<WyqCase> caseList = caseBaseService.select30HotWorths(param);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(caseList)){
            for(WyqCase op : caseList){
                op.setEventLabelStr(EventType.getDesc(op.getByteEventLabel()+""));
                op.setMaxTime(sdf.format(op.getMaxHotValueTime()));
                if(!type.contains(EventType.getDesc(op.getByteEventLabel()+""))){
                    type.add(EventType.getDesc(op.getByteEventLabel()+""));
                }
            }
            result.put("type", type);
            result.put("stamp", stamp);
            result.put("caseList", caseList);
            baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
            baseDto.setData(result);
        }else{
          return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
        }
       return baseDto;

    }
    public List<String> getHotWorthStamp(){
        List<String> stamp = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date time1 = new Date();
        Calendar calendar = Calendar.getInstance();
        stamp.add(sdf.format(time1));
        for(int i=1;i<10000;i++){
            calendar.setTime(time1);
            calendar.add(Calendar.DATE,-i);
            Date time2 = calendar.getTime();
            stamp.add(sdf.format(time2));
            if(sdf.format(time2).equals("2018-01-10")){
                return stamp;
            }
        }
        return null;
    }

    @ApiOperation(value = "热点事件区间占比", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "热点事件区间占比")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hotInterval")
    public BaseDto goHotEventsIntervalChart(@ApiParam(value = "参数集合"
    +"eventLabel:事件标签ID(0:全部 1.时事 2.社会 3.体育 4.科技 5.自然灾害 6.娱乐 7.人物 8.财经 9.房产 10.金融 11.医疗 12.教育 13.文化 14.汽车 15.旅游 16.政务 17.法治)"
    ) @RequestParam Map<String, Object> param){
        BaseDto baseDto = new BaseDto();
        List<Double> hotValues = caseBaseService.selectHotEventsIntervalChart(param);
        if(CollectionUtils.isNotEmpty(hotValues)){
         baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
         baseDto.setData(hotValues);
         return baseDto;
        }
        return baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);

    }

    @ApiOperation(value = "热门信息", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "热门信息")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "hotMessage")
    public BaseDto goHotMessage(@ApiParam(value = "案例ID") @RequestParam Integer caseID) throws Exception {
        String userTag=null;
        BaseDto baseDto = new BaseDto();
        Map<String, Object> result = new HashMap<>(16);
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(userTag)){
            userTag = getUserTag();
        }
        String[] jsons = null;
        //微博
        IContentCommonNetView contenView = null;
        //非微博
        IContentCommonNetView contenView2 = null;
        String[] jsons2 = null;
        String json = null;
        try {
            contenView = ShareData("yesWeiBoHotPeople", IContentCommonNetView.class,caseID);
            contenView2 = ShareData("noWeiBoHotPeople", IContentCommonNetView.class,caseID);
            if (StringUtils.isEmpty(json)) {
                if (null != contenView) {
                    if (null!=contenView && "0000".equals(contenView.getCode())) {
                        List<com.xd.tools.pojo.IContentCommonNet> contentList = contenView
                                .getIContentCommonNetList();
                        if (null != contentList && contentList.size() > 0) {
                           jsons = new String[contentList.size()];
                            for (int i = 0; i < contentList.size(); i++) {
                                com.xd.tools.pojo.IContentCommonNet conten = contentList.get(i);
                                System.out.println(conten.getTitle());
                                String tit = conten.getTitle();
                                if (tit.indexOf("【原微博】") >= 0) {
                                    tit = conten.getTitle().substring(conten.getTitle().indexOf("【原微博】") + 5,
                                            conten.getTitle().length());
                                }

                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("{");
                                System.out.println(tit + "======");
                                tit = tit.replaceAll("\"", "\'");
                                tit = tit.replaceAll("[\\u00A0]+", "").trim();
                                if (tit.indexOf("\"") >= 0) {
                                    tit = tit.replaceAll("\"", "“");
                                }
                                tit = tit.replaceAll("[\\t\\n\\r]", " ");
                                sb2.append("\"title\":\"" + tit + "\",");
                                String content = conten.getContent();
                                content = content.replaceAll("\"", "\'");
                                content = content.replaceAll("[\\u00A0]+", "").trim();
                                if (content.indexOf("\"") >= 0) {
                                    content = content.replaceAll("\"", "“");
                                }
                                content = content.replaceAll("[\\t\\n\\r]", " ");
                                String s = conten.getForwarderContent();
                                s = s.replaceAll("\"", "\'");
                                s = s.replaceAll("[\\u00A0]+", "").trim();
                                if (s.indexOf("\"") >= 0) {
                                    s = s.replaceAll("\"", "“");
                                }
                                s = s.replaceAll("[\\t\\n\\r]", " ");
                                sb2.append("\"content\":\"" + tit + "\",");
                                sb2.append("\"sourceWebsite\":\"" + conten.getCaptureWebsiteName() + "\",");
                                sb2.append("\"publishTime\":\"" + formatDate.format(conten.getPublished())
                                        + "\",");
                                sb2.append("\"url\":\"" + conten.getWebpageUrl() + "\",");
                                sb2.append("\"profileImageUrl\":\"" + conten.getProfileImageUrl() + "\",");
                                sb2.append("\"author\":\"" + conten.getAuthor() + "\",");
                                sb2.append("\"captureWebsiteName\":\"" + conten.getCaptureWebsiteName() + "\",");
                                sb2.append("\"originType\":\"" + conten.getOriginType() + "\",");
                                sb2.append("\"similarCount\":\"" + conten.getRepeatNum() + "\",");
                                sb2.append("\"forwarderContent\":\"" + s + "\"}");
                                jsons[i] = sb2.toString();
                            }
                            result.put("jsons", jsons);
                        } else {
                            result.put("jsons", null);
                        }
                    } else {
                        result.put("jsons", null);
                    }
                } else {
                    result.put("jsons", null);
                }

                if (null != contenView2) {
                    if ("0000".equals(contenView2.getCode())) {
                        List<com.xd.tools.pojo.IContentCommonNet> contentList = contenView2
                                .getIContentCommonNetList();
                        if (null != contentList && contentList.size() > 0) {
                            // doFilterRepeat(contentList);
                            jsons2 = new String[contentList.size()];
                            for (int i = 0; i < contentList.size(); i++) {
                                com.xd.tools.pojo.IContentCommonNet conten = contentList.get(i);
                                System.out.println(conten.getTitle());
                                String tit = conten.getTitle();
                                if (StringUtils.indexOf(tit,"【原微博】") >= 0) {
                                    tit = conten.getTitle().substring(conten.getTitle().indexOf("【原微博】") + 5,
                                            conten.getTitle().length());
                                }

                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("{");
                                System.out.println(tit + "======");
                                tit = tit.replaceAll("\"", "\'");
                                tit = tit.trim();
                                if (tit.indexOf("\"") >= 0) {
                                    tit = tit.replaceAll("\"", "“");
                                }
                                tit = tit.replaceAll("[\\t\\n\\r]", " ");
                                sb2.append("\"title\":\"" + tit + "\",");
                                String content = conten.getContent();
                                content = content.replaceAll("\"", "\'");
                                content = content.replaceAll("[\\u00A0]+", "").trim();
                                if (content.indexOf("\"") >= 0) {
                                    content = content.replaceAll("\"", "“");
                                }
                                content = content.replaceAll("[\\t\\n\\r]", " ");
                                sb2.append("\"content\":\"" + tit + "\",");
                                sb2.append("\"sourceWebsite\":\"" + conten.getCaptureWebsiteName() + "\",");
                                sb2.append("\"publishTime\":\"" + formatDate.format(conten.getPublished())
                                        + "\",");
                                sb2.append("\"url\":\"" + conten.getWebpageUrl() + "\",");
                                sb2.append("\"profileImageUrl\":\"" + conten.getProfileImageUrl() + "\",");
                                sb2.append("\"author\":\"" + conten.getAuthor() + "\",");
                                sb2.append("\"captureWebsiteName\":\"" + conten.getCaptureWebsiteName() + "\",");
                                sb2.append("\"originType\":\"" + conten.getOriginType() + "\",");
                                sb2.append("\"similarCount\":\"" + conten.getRepeatNum() + "\"}");
                                jsons2[i] = sb2.toString();
                            }
                            result.put("jsons2", jsons2);
                            json = JSONObject.toJSONString(result);
                            baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
                            baseDto.setData(json);
                        } else {
                            result.put("jsons2", null);
                            json = JSONObject.toJSONString(result);
                            baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
                            baseDto.setData(json);
                        }
                    } else {
                        result.put("jsons2", null);
                        json = JSONObject.toJSONString(result);
                        baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
                        baseDto.setData(json);
                    }
                } else {
                    result.put("jsons2", null);
                    json = JSONObject.toJSONString(result);
                    baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
                    baseDto.setData(json);
                }
            }
        } catch (Exception e) {
            result.put("jsons", null);
            e.printStackTrace();
        }
        return baseDto;

    }
    private <T> T ShareData(String fileName, Class<T> className,Integer caseID){
        T view = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       WyqCase oaw = caseBaseService.checkCaseAnalysis(caseID);
        if(oaw == null){
            return null;
        }
       String startTime = dateFormat.format(oaw.getStartTime());
       String endTime = dateFormat.format(oaw.getEndTime());
        String filePath = oaw.getFilePath();
        //走任务 7天以外的
        view = CommonUtils.getFileDataToObject(fileName, filePath, className);
        return view;
    }

    @ApiOperation(value = "案例库列表", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "案例库列表")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "list/casebase")
    public PageDto<WyqCaseDto> list(@ApiParam(value = "参数集合"
            + "1.label:事件类型"
            + "2.title:事件名称"
            + "3.page:当前页"
            + "4.pageSize:页大小"
            + "5.order:排序字段选项 1-热度(默认),2-时间"
            + "6.sort:升降序选项 1-降序(默认),2-升序"
            + "7.startTime:开始时间"
            + "8.endTime:结束时间") @RequestParam Map<String, Object> param){
        PageDto<WyqCaseDto> pageDto = new PageDto<>();
        PageInfo<WyqCase> pageInfo = caseBaseService.findAll(param);
        if(CollectionUtils.isEmpty(pageInfo.getList())) {
            return pageDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        pageDto.setCode(CodeConstant.SUCCESS_CODE).setData(BeanUtils.copyTo(pageInfo.getList(),WyqCaseDto.class));
        pageDto.setPageSize(pageInfo.getPageSize());
        pageDto.setPage(pageInfo.getPageNum());
        pageDto.setTotalCount(pageInfo.getTotal());
        pageDto.setMaxPage(pageInfo.getPages());
        return pageDto;
    }

    @ApiOperation(value = "案例库查看详情", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "案例库查看详情")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "see/casebase")
    public BaseDto seeCaseAnalysis(@ApiParam(value = "案例ID") @RequestParam Integer caseId) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        WyqCase oaw = caseBaseService.checkCaseAnalysis(caseId);
        BaseDto baseDto = new BaseDto();
        if(null== oaw){
            baseDto.setCodeMessage(CodeConstant.ERROR_CODE_2001);
            return baseDto;
        }
        //从文件中取出首发网站和发布时间
        IContentWeiboCommonNetView icnv = getShareData("initialWebsite", IContentWeiboCommonNetView.class,caseId);
        if(null!= icnv && "0000".equals(icnv.getCode())){
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(icnv.getiContentCommonNetList())){
                oaw.setInitialWebsite(icnv.getiContentCommonNetList().get(0).getCaptureWebsiteName());
                oaw.setStartCreateTime(dateFormat.format(icnv.getiContentCommonNetList().get(0).getPublished()));
            }
        }
        baseDto.setCodeMessage(CodeConstant.SUCCESS_CODE);
        baseDto.setData(oaw);
        return baseDto;
    }

    @ApiOperation(value = "事件趋势图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "事件趋势图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goVolume")
    public BaseDto goVolume(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatView view = getShareData("yearHotVolumeDay", StatView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        Map<String, Object> result = new HashMap<>();
        List<Stat> statList = view.getStatList();
        List<String> legend = new ArrayList<>();
        String timeNode = statList.get(0).getKey();
        Integer timeNodeType = CommonUtils.getTimeNodeType(timeNode);
        List<String> dates = CommonUtils.getTimeAxis(wc.getStartTime(), wc.getEndTime(), timeNodeType);
        List<Long> emotionData = new ArrayList<>();

        statList.sort((Stat o1, Stat o2) -> o1.getName().compareTo(o2.getName()));
        int j = 0;
        for (int i = 0; i < dates.size(); i++) {
            String d = dates.get(i);
            if (j < statList.size()) {
                long num = statList.get(j).getNum();
                if (statList.get(j).getName().indexOf(d) >= 0) {
                    j++;
                } else { // 缺少日期补0
                    num = 0;
                }
                emotionData.add(num);
            } else {
                emotionData.add(0L);
            }
        }
        result.put("legend", legend);
        result.put("dates", dates);
        result.put("jsonLine", emotionData);
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "媒体友好度图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "媒体友好度图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goMedaiFriend")
    public BaseDto goMedaiFriend(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatsView view = getShareData("yearHotMediafriend", StatsView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatsList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        int hh=0;
        long sumMG=0;
        long unSumMG=0;
        long qwSumMG=0;
        long qwUnSumMG=0;
        Map<String, Object> result =new HashMap<>();
        DecimalFormat df = new DecimalFormat("#0.00");
        List<Stats> statsList = view.getStatsList();
        for (Stats stats:statsList) {
            if(stats == null || CollectionUtils.isEmpty(stats.getStatList())){
                return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
            }
            List<Stat> statList = stats.getStatList();
            for (Stat stat:statList) {
                if("微博".equals(stats.getName()) || "客户端".equals(stats.getName()) ||
                        "微信".equals(stats.getName()) || "论坛".equals(stats.getName())){

                    if ("敏感".equals(stat.getName())) {
                        sumMG += stat.getNum();
                    } else {
                        unSumMG += stat.getNum();
                        double percent = stat.getPercent();
                        if (percent == 0.0) {
                            hh += 1;
                        } else {
                            if ("微博".equals(stats.getName())) {
                                result.put("wb", df.format(percent));
                            }else if("客户端".equals(stats.getName())){
                                result.put("app", df.format(percent));
                            }else if("微信".equals(stats.getName())){
                                result.put("wx", df.format(percent));
                            }else if("论坛".equals(stats.getName())){
                                result.put("lt", df.format(percent));
                            }
                        }
                    }
                }else{
                    if ("敏感".equals(stat.getName())) {
                        sumMG += stat.getNum();
                        qwSumMG += stat.getNum();
                    } else {
                        unSumMG += stat.getNum();
                        qwUnSumMG += stat.getNum();
                    }
                }
            }
        }
        if(result.get("wb") == null){
            result.put("wb",0.00);
        }if(result.get("app") == null){
            result.put("app",0.00);
        }if(result.get("wx") == null){
            result.put("wx",0.00);
        }if(result.get("lt") == null){
            result.put("lt",0.00);
        }
        result.put("unSumMG",unSumMG);
        result.put("sumMG",sumMG);
        Double aaa=(double)qwUnSumMG;
        Double bbb=(double)qwSumMG;
        Double a=(double)unSumMG;
        Double b=(double)sumMG;
        if(aaa==0.0 && bbb==0.0){
            result.put("wz",0.00);
        }else{
            result.put("wz",df.format(aaa*100/(aaa+bbb)));
        }
        if(a==0.0 && b==0.0){
            hh+=1;
            result.put("youhaodu",0);
        }else{
            df.applyLocalizedPattern("#0.0000");
            result.put("youhaodu",df.format(a/(a+b)));
        }
        if(hh == 5){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "来源类型图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "来源类型图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goMedaiFrom")
    public BaseDto goMedaiFrom(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatView view = getShareData("yearHotOrigin", StatView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        long sum = 0;
        Map<String, Object> result =new HashMap<>();
        List<Stat> statList = view.getStatList();
        for (Stat stat:statList) {
            if(stat == null || StringUtils.isEmpty(stat.getName()) || stat.getNum() == 0){
                return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
            }
            result.put(stat.getName(),stat.getNum());
            sum += stat.getNum();
        }

        result.put("sum",sum);
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "热点词云图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "热点词云图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goMoreWords")
    public BaseDto goMoreWords(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatView view = getShareData("yearHotWordCloud", StatView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        Map<String, Object> result = new HashMap<>();
        List<Stat> statList = view.getStatList();
        List<JSONObject> li = new ArrayList<>();
        for (int i = 0; i < statList.size(); i++) {
            if (i < 60) {
                StringBuilder sb = new StringBuilder();
                String[] colors = { "#383838", "#f29300", "#a05623", "#277bc0" };
                Stat stat = statList.get(i);
                if (null != stat) {
                    int j = (int) (Math.random() * 4);
                    sb.append("{name:'" + stat.getName() + "',");
                    sb.append("value:" + stat.getNum() + ",");
                    sb.append("itemStyle: {normal: {color: '");
                    sb.append(colors[j]);
                    sb.append("'}}}");
                    JSONObject data = JSON.parseObject(sb.toString());
                    li.add(data);
                } else {
                    return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
                }
            }
        }
        result.put("li",li);
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "情绪占比图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "情绪占比图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goEmotionStatAnalysis")
    public BaseDto goEmotionStatAnalysis(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatsView view = getShareData("yearEmotionProportion", StatsView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatsList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        Map<String, Object> result = new HashMap<>();
        List<Stats> statsList = view.getStatsList();
        List<JSONObject> statValueVoList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(statsList)){
            for (Stats stats : statsList) {
                JSONObject data = new JSONObject();
                data.put("name",stats.getName());
                data.put("value",stats.getNum());
                statValueVoList.add(data);
            }
        }
        statValueVoList.sort((JSONObject o1,JSONObject o2) -> o2.getLong("value").compareTo(o1.getLong("value")));
        result.put("data", statValueVoList);
        result.put("color", EMOTION_COLOR_MAP);
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "地域分析图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "地域分析图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goMap")
    public BaseDto goMap(Integer caseId) {
        BaseDto baseDto = new BaseDto();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        StatView view = getShareData("yearHotProvince", StatView.class, wc.getId());
        if(view == null || !CodeConstant.SUCCESS_CODE.equals(view.getCode()) || CollectionUtils.isEmpty(view.getStatList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        Map<String, Object> result = new LinkedHashMap<>();
        List<Stat> statList = view.getStatList();
        for (Stat stat:statList) {
            result.put(stat.getName(),stat.getNum());
        }
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "同类事件图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "地域分析图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/goSimilarIncidents")
    public PageDto<WyqCaseDto> goSimilarIncidents(Integer caseId) {
        PageDto<WyqCaseDto> baseDto = new PageDto<>();

        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }

        PageInfo<WyqCase> pageInfo = caseBaseService.findByTopLabel(wc.getEventLabel());
        if(pageInfo == null || CollectionUtils.isEmpty(pageInfo.getList())){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }
        return baseDto.setData(BeanUtils.copyTo(pageInfo.getList(),WyqCaseDto.class)).setCode(CodeConstant.SUCCESS_CODE);
    }

    @ApiOperation(value = "案例折线图", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "案例折线图")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "chart/HotWorth")
    public BaseDto goHotWorth(Integer caseId) throws IOException {
        BaseDto baseDto = new BaseDto();
        WyqCase wc = caseBaseService.findById(caseId);
        if(wc == null){
            return baseDto.setCode(CodeConstant.ERROR_CODE_2001);
        }
        String json = null;
        Map<String, Object> result = new HashMap<String, Object>();
        String jsonLine = null;
        DecimalFormat df = new DecimalFormat("######0.00");
        try {
            CtksStatStatListsView yearLine = getShareData("yearHotStatAndLine", CtksStatStatListsView.class,caseId);
            if (null!=yearLine && "0000".equals(yearLine.getCode())) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                initTime(formatter.format(wc.getStartTime()), formatter.format(wc.getEndTime()));
                CtksStatStatLists ctksStatStatLists = yearLine.getCtksStatStatLists();
                if (null != ctksStatStatLists) {
                    List<StatStatLists> statsList = ctksStatStatLists.getStatStatListsList();
                    if (org.apache.commons.collections.CollectionUtils.isNotEmpty(statsList)) {
                        String timeNode = statsList.get(0).getStatStatListList().get(0).getName();
                        Integer timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                        List<String> dates = CommonUtils.getTimeAxis(wc.getStartTime(), wc.getEndTime(), timeNodeType);
                        List<Float> emotionData = new ArrayList<Float>();
                        for (StatStatLists stats : statsList) {
                            List<StatStatList> stats2 = stats.getStatStatListList();
                            if (org.apache.commons.collections.CollectionUtils.isEmpty(stats2)) {
                                result.put("dates", null);
                                result.put("jsonLine", null);
                                System.out.println(new Date()
                                        + "--getHotTableAndLine return jsons size : ,clusterCount size :" + " ,jsonLine :" + jsonLine);
                                return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
                            }
                            Collections.sort(stats2, new Comparator<StatStatList>() {
                                @Override
                                public int compare(StatStatList o1, StatStatList o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                            int j = 0;
                            for (int i = 0; i < dates.size(); i++) {
                                String d = dates.get(i);
                                if (j < stats2.size()) {
                                    double total = stats2.get(j).getTotal();
                                    String ftot = df.format(total);
                                    float num = Float.parseFloat(ftot);
                                    if (stats2.get(j).getName().indexOf(d) >= 0) {
                                        j++;
                                    } else { // 缺少日期补0
                                        num = 0;
                                    }
                                    emotionData.add(num);
                                } else {
                                    emotionData.add(0f);
                                }
                            }
                        }
                        result.put("dates", dates);
                        result.put("jsonLine", emotionData);
                        System.out.println(new Date() + "--getHotTableAndLine return jsons size : ,clusterCount size :" + " ,jsonLine :" + jsonLine);
                    } else {
                        result.put("dates", null);
                        result.put("jsonLine", null);
                        System.out.println(new Date() + "--getHotTableAndLine return jsons size ,clusterCount size :" + " ,jsonLine :" + jsonLine);
                    }
                }
            } else {
                result.put("jsons", null);
                result.put("jsonLine", null);
            }
        } catch (Exception e) {
            System.out.println(new Date() + "--getHotTableAndLine Exception is :" + e);
            e.printStackTrace();
        }
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }


    @ApiOperation(value = "关联词", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "关联词")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "go/Assion")
    public BaseDto goAssion(Integer caseId) throws IOException {
        BaseDto baseDto = new BaseDto();
        Map<String, Object> result = new HashMap<String, Object>();
        RelatedWordView statV1_001 = null;
        String json = null;
        WyqCase wc = caseBaseService.findById(caseId);
        statV1_001 = getShareData("yearHotAnlyzer", RelatedWordView.class,caseId);
        try {
            if (StringUtils.isEmpty(json)) {
                StringBuilder sb = new StringBuilder();
                CommAnalysisRelateWord c = new CommAnalysisRelateWord();

                c.setNum(10);

                sb.append("{");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                initTime(formatter.format(wc.getStartTime()), formatter.format(wc.getEndTime()));
                List<String> li = new ArrayList<String>();
                if (null != statV1_001) {
                    System.out.println(new Date() + "--goAssociation  user id=[],code=["
                            + statV1_001.getCode() + "],relatedWordList =[" + statV1_001.getRelatedWordList()
                            + "]");
                }
                String[] str = new String[10];
                if (null!=statV1_001 && "0000".equals(statV1_001.getCode())) {
                    List<RelatedWord> relatedWordList = statV1_001.getRelatedWordList();
                    if (null != relatedWordList && relatedWordList.size() > 0) {
                        RelatedWord relatedWord = relatedWordList.get(0);
                        sb.append("\"na\":\"" + relatedWord.getName() + "\"}");
                        long num = relatedWord.getNum();
                        List<RelatedWord> list = relatedWord.getRelatedWordList();
                        if (null != list && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                if (i < 10) {
                                    if (null != list.get(i)) {
                                        double percent = list.get(i).getPercent();
                                        long n = new Dwr().getYuanSize(list.get(i).getNum(), num);
                                        String u = "1";
                                        str[i] = percent + "," + n + "," + u + "," + list.get(i).getName();
                                    }
                                }
                            }
                        } else {
                            if (StringUtils.isNotBlank(num + "")) {
                                result.put("num", num);
                            } else {
                                result.put("num", null);
                            }
                            result.put("sb", null);
                            return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
                        }
                    } else {
                        result.put("sb", null);
                        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
                    }
                    li = new Dwr().getAssociation(str);
                    result.put("sb", sb.toString());
                    result.put("li", li.toString());
                    json = JSONObject.toJSONString(result);
                } else {
                    result.put("sb", null);
                    json = JSONObject.toJSONString(result);
                }
            }

        } catch (Exception e) {
            System.out.println(new Date() + "--getHotSeniority Exception is :" + e);
            e.printStackTrace();
        }
        return baseDto.setData(result).setCode(CodeConstant.SUCCESS_CODE);
    }
    /**
     * 时间参数处理
     * @param startTime
     * @param endTime
     */
    private void initTime(String startTime, String endTime) {
        Date startDate;
        Date endDate;
        Integer timeNodeType;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();

        try {
            startDate = dateFormat.parse(startTime);
            endDate = dateFormat.parse(endTime);
        } catch (Exception e) {
            endDate = time.getTime();
            time.set(Calendar.HOUR_OF_DAY, 0);
            startDate = time.getTime();
            startTime = dateFormat.format(startDate);
            endTime = dateFormat.format(endDate);
        }
        // 时间取整十分
        time.setTime(startDate);
        int minute = time.get(Calendar.MINUTE);
        minute = minute - minute % 10;
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, 0);
        startDate = time.getTime();
        startTime = dateFormat.format(startDate);

        time.setTime(endDate);
        minute = time.get(Calendar.MINUTE);
        minute = minute - minute % 10;
        time.set(Calendar.MINUTE, minute);
        time.set(Calendar.SECOND, 0);
        endDate = time.getTime();
        endTime = dateFormat.format(endDate);

        if (endDate.getTime() - startDate.getTime() >= 7 * 24 * 60 * 60 * 1000L) {
            timeNodeType = 2;
        } else {
            timeNodeType = 1;
        }
    }
    public <T> T getShareData(String fileName, Class<T> className, Integer caseId){
        WyqCase wc = caseBaseService.findById(caseId);
        T view = null;
        try {
            String data = FileUtils.readFile(wc.getFilePath()+fileName+BusinessConstant.FILE_SUFFIX_TXT, BusinessConstant.ENCODE_GBK);
            view = JSON.parseObject(data,className);
        }catch (IOException e){
            e.getStackTrace();
        }
        return view;
    }

    public static final Map<String, String> EMOTION_COLOR_MAP = new HashMap<>();

    static {
        EMOTION_COLOR_MAP.put("xy", "#F18D00");
        EMOTION_COLOR_MAP.put("fn", "#CF421E");
        EMOTION_COLOR_MAP.put("bs", "#0C7DC0");
        EMOTION_COLOR_MAP.put("jq", "#45B485");
        EMOTION_COLOR_MAP.put("kj", "#2F3237");
        EMOTION_COLOR_MAP.put("zx", "#9DA7B5");
    }

}
