package com.miduchina.wrd.controller;



import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.pojo.AvgValueResult;
import com.miduchina.wrd.pojo.ResultClassList;
import com.miduchina.wrd.pojo.ResultRank;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.HttpRequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Author ZhuFangTao
 * @Description
 * @Date  2019/10/14 15:34
 * @Param
 * @return
 */
@Controller
@RequestMapping("/api/hot/largeScreen")
public class HotEventController {

    private final String ONE = "1";
    private final String TWO = "2";
    private final String NATIONAL = "全国";

    @RequestMapping("showScreen")
    public String goLargeScreen(){
        return "largeScreen/firstView";
    }

    @RequestMapping("labels")
    @ResponseBody
    public ResultClassList getHotLabels(){
        String url = "http://localhost:1118/api/v1/hotLabel/get/groupByLevel";
        String s = HttpRequestUtils.sendGet(url);
        ResultClassList resultClassList = JSONObject.parseObject(s, ResultClassList.class);
        return resultClassList;
    }

    @RequestMapping("hotAverageValue")
    @ResponseBody
    public AvgValueResult hotAverageValue(@RequestParam("labels") String labels,
                                          @RequestParam("province") String province,
                                          @RequestParam("city") String city){
        String url = "http://localhost:1118/api/v3/hotIncident/count";
        Map<String, Object> paramsMap = new HashMap<String, Object>(16);
        //即24小时时差
            //获取前1天的当前时间
            paramsMap.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
            //获取当天当前时间
            paramsMap.put("endTime", DateUtils.getNow());

        if (StringUtils.isNotBlank(labels)) {
            paramsMap.put("labels", labels);
        }

        if (StringUtils.isNotBlank(province)) {
            paramsMap.put("province", province);
        }

        if (StringUtils.isNotBlank(city)){
            paramsMap.put("city",city);
        }
        String rankData = HttpRequestUtils.sendPost(url, paramsMap);
        AvgValueResult avgValueResult = JSONObject.parseObject(rankData, AvgValueResult.class);
        return avgValueResult;
    }

    @RequestMapping("rankListData")
    @ResponseBody
    public ResultRank rankData(@RequestParam("labels") String labels,
                               @RequestParam("province") String province,
                               @RequestParam("city") String city,
                               @RequestParam("page") Integer page,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam("sort") String sort,
                               @RequestParam("areaType") String areaType){
        String url = "http://localhost:1118/api/v3/hotIncident/web/list";
        HashMap<String, Object> params = new HashMap<>(16);
            //获取前1天的当前时间
            params.put("startTime", DateUtils.format(DateUtils.addDay(new Date(), -1)));
            //获取当天当前时间
            params.put("endTime", DateUtils.getNow());

        if (StringUtils.isNotBlank(labels)) {
            params.put("labels", labels);
        }
        if (StringUtils.isNotBlank(province) && !NATIONAL.equals(province)) {
            params.put("province", province);
        }
        if (StringUtils.isNotBlank(city)) {
            params.put("city", city);
        }
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("showTag", 1);
        params.put("sort", sort);
        params.put("areaType", areaType);
        params.put("labelShowTag", 0);
        String result = HttpRequestUtils.sendPost(url, params);
        ResultRank resultRank = JSONObject.parseObject(result, ResultRank.class);
        return resultRank;
    }

}
