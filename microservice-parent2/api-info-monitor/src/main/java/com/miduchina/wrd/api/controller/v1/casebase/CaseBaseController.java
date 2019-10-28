package com.miduchina.wrd.api.controller.v1.casebase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.api.service.casebase.CaseBaseService;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.casebase.WyqCaseDto;
import com.miduchina.wrd.po.casebase.WyqCase;
import com.miduchina.wrd.util.BeanUtils;
import com.miduchina.wrd.util.CommonUtils;
import com.miduchina.wrd.util.FileUtils;
import com.xd.tools.pojo.Stat;
import com.xd.tools.pojo.Stats;
import com.xd.tools.view.StatView;
import com.xd.tools.view.StatsView;
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
import java.util.*;

/**
 * Created by sty on 2018/12/25.
 * @author sty
 */
@RestController
@RequestMapping("api/v1/casebase")
public class CaseBaseController {

    @Autowired
    private CaseBaseService caseBaseService;

    @ApiOperation(value = "案例库列表", produces = "application/json")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "案例库列表")})
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST}, value = "list")
    public PageDto<WyqCaseDto> list(@ApiParam(value = "参数集合"
            + "1.label:事件类型"
            + "2.title:事件名称"
            + "3.page:当前页"
            + "4.pageSize:页大小"
            + "5.order:排序字段选项 1-热度(默认),2-时间"
            + "6.sort:升降序选项 1-降序(默认),2-升序") @RequestParam Map<String, Object> param){
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
            if (i < 20) {
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
