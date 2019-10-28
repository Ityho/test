package com.miduchina.wrd.eventanalysis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.BaseDto;
import com.miduchina.wrd.dto.ModelDto;
import com.miduchina.wrd.dto.PageDto;
import com.miduchina.wrd.dto.home.HeatShareDto;
import com.miduchina.wrd.dto.hotsportview.HotWeiBoView;
import com.miduchina.wrd.dto.hotsportview.Statuses;
import com.miduchina.wrd.dto.keyword.Keywords;
import com.miduchina.wrd.dto.ranking.HotIncidentDto;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.base.BaseService;
import com.miduchina.wrd.eventanalysis.config.WyqDataConfig;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.echart.ECharts;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.service.HomeService;
import com.miduchina.wrd.eventanalysis.utils.CommonUtils;
import com.miduchina.wrd.eventanalysis.utils.Range;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.util.DateUtils;
import com.tuke.gospel.core.util.PaginationUtils;
import com.vdurmont.emoji.EmojiParser;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.method.wyq.h5.WyqH5MethodV1;
import com.xd.tools.method.wyq.h5.abilityseal.WyqH5AbilitysealMethodV1;
import com.xd.tools.pojo.*;
import com.xd.tools.view.*;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
@Service("homeService")
public class HomeServiceImpl extends BaseService implements HomeService {

    @Autowired
    APIInfoClient apiInfoClient;

    public static final String[] emotionArray = { "喜悦", "中性", "愤怒", "悲伤", "惊奇", "恐惧" };

    public static final Map<String, String> emotionColorMap = new HashMap<String, String>();
    static {
        emotionColorMap.put("xy", "#F18D00");
        emotionColorMap.put("fn", "#CF421E");
        emotionColorMap.put("bs", "#0C7DC0");
        emotionColorMap.put("jq", "#45B485");
        emotionColorMap.put("kj", "#2F3237");
        emotionColorMap.put("zx", "#9DA7B5");
    }

    public static final Map<String, String> emotionFlagMap = new HashMap<String, String>();
    static {
        emotionFlagMap.put("愤怒", "fn");
        emotionFlagMap.put("恐惧", "kj");
        emotionFlagMap.put("喜悦", "xy");
        emotionFlagMap.put("惊奇", "jq");
        emotionFlagMap.put("悲伤", "bs");
        emotionFlagMap.put("中性", "zx");
    }

    public static final Map<String, Integer> emotionTypeMap = new HashMap<String, Integer>();
    static {
        emotionTypeMap.put("喜悦", 0);
        emotionTypeMap.put("中性", 1);
        emotionTypeMap.put("愤怒", 2);
        emotionTypeMap.put("悲伤", 3);
        emotionTypeMap.put("惊奇", 4);
        emotionTypeMap.put("恐惧", 5);
    }

    public static final List<com.miduchina.wrd.eventanalysis.utils.Range> sortList = new ArrayList<com.miduchina.wrd.eventanalysis.utils.Range>();
    static {
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(0L, 49L, "0-49"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(50L, 199L, "50-199"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(200L, 499L, "200-499"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(500L, 799L, "500-799"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(800L, 999L, "800-999"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(1000L, 1599L, "1000-1599"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(1600L, 2000L, "1600-2000"));
        sortList.add(new com.miduchina.wrd.eventanalysis.utils.Range(2001L, 50000000L, "2000以上"));
    }


    @Override
    public Map<String, Object> getHotWeibo(int pagesize, int page, int categoryId) {
        Map<String, Object> mapData = new HashMap<>();
        List<Statuses> statuses = new ArrayList<>();
        String hotWBURL = "http://api.u-mei.com/sh/weibo/get_hot_weibo";
        String sessionName = getSessionName(SystemConstants.JEDIS_KEYS_HOT_WEIBO + page + categoryId);
        String resWB = "";
        String json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isBlank(json)) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("count", pagesize);
            params.put("page", page);
            params.put("category", categoryId);
            resWB = Utils.sendPost(hotWBURL, params);
            if (!TextUtils.isEmpty(resWB)) {
                HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
                if (null != hotWeiBoView) {
                    RedisUtils.setAttribute(sessionName, resWB, SystemConstants.HOT_WEIBO_TIME);
                }
            }
        } else {
            resWB = json;
        }
        HotWeiBoView hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
        if (null != hotWeiBoView) {
            page = PaginationUtils.getRightPage(pagesize, page);
            if (hotWeiBoView.getData() != null && hotWeiBoView.getStatuses() != null && hotWeiBoView.getStatuses().size() > 0) {
                mapData.put("message", "请求成功");
                mapData.put("code", "0000");
                statuses = hotWeiBoView.getStatuses();
            } else {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("count", pagesize);
                params.put("page", page);
                params.put("category", categoryId);
                resWB = Utils.sendPost(hotWBURL, params);
                if (!TextUtils.isEmpty(resWB)) {
                    hotWeiBoView = JSON.parseObject(resWB, HotWeiBoView.class);
                    if (null != hotWeiBoView) {
                        RedisUtils.setAttribute(sessionName, resWB, SystemConstants.HOT_WEIBO_TIME);
                        if (hotWeiBoView.getData() != null && hotWeiBoView.getData().getStatuses() != null && hotWeiBoView.getData().getStatuses().size() > 0) {
                            mapData.put("message", "请求成功");
                            mapData.put("code", "0000");
                            statuses = hotWeiBoView.getData().getStatuses();
                        }
                    }
                }
            }

            System.out.println("resWB=" + resWB);
            if (CollectionUtils.isNotEmpty(statuses)) {
                for (Statuses s : statuses) {
                    s.setUrl("https://weibo.com/" + s.getUser().getUserId() + "/"
                            + s.getTrueMid());
                    s.setOrig_text(s.getOrig_text().trim());
                    s.setText(s.getText().replaceAll("\u200b", ""));
                    s.setText(s.getText().replaceAll("\ufe0f", ""));
                    String ss = EmojiParser.parseToHtmlDecimal(s.getText());
                    s.setText(ss);
                }
            }
            mapData.put("data", statuses);
        }
        return mapData;
    }

    @Override
    public String getHotEventCount(HttpServletRequest request) {

        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String url = baseUrl + "/v3/hotIncident/count";

        Map<String, Object> params = new HashMap<>();
        String varStr = request.getParameter("page");
        Integer varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("page", varInt);

        varStr = request.getParameter("pageSize");
        varInt = 20;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("pageSize", varInt);


        varStr = request.getParameter("sort");
        varInt = 2;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("sort", varInt);

        varStr = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("startTime", varStr);
        }

        varStr = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("endTime", varStr);
        }

        varStr = request.getParameter("webShow");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("webShow", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("showTag");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("showTag", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("province");
        if (StringUtils.isNotBlank(varStr) && !varStr.equals("全部")){
            params.put("province", varStr);
        }

        varStr = request.getParameter("city");
        if (StringUtils.isNotBlank(varStr)){
            params.put("city", varStr);
        }

        varStr = request.getParameter("areaType");
        if (StringUtils.isNotBlank(varStr)){
            params.put("areaType", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("labels");
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
            if (varInt != 0) {
                if (varInt == -1){
                    params.put("areaType", 2);
                }else {
                    params.put("labels", varInt);
                }
            }
        }
        varStr = request.getParameter("labelShowTag");
        varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("labelShowTag", varInt);

        log.info("getHotEvent=" + params.toString());
        String result = Utils.sendGet(url, params);
        return result;
    }


    @Override
    public String getHotEvent(HttpServletRequest request) {
        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String url = baseUrl + "/v3/hotIncident/web/list";


        Map<String, Object> params = new HashMap<>();
        String varStr = request.getParameter("page");
        Integer varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("page", varInt);

        varStr = request.getParameter("pageSize");
        varInt = 20;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("pageSize", varInt);


        varStr = request.getParameter("sort");
        varInt = 2;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("sort", varInt);

        varStr = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("startTime", varStr);
        }

        varStr = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("endTime", varStr);
        }

        varStr = request.getParameter("webShow");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("webShow", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("showTag");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("showTag", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("province");
        if (StringUtils.isNotBlank(varStr) && !varStr.equals("全部")){
            params.put("province", varStr);
        }

        varStr = request.getParameter("city");
        if (StringUtils.isNotBlank(varStr)){
            params.put("city", varStr);
        }

        varStr = request.getParameter("areaType");
        if (StringUtils.isNotBlank(varStr)){
            params.put("areaType", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("labels");
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
            if (varInt != 0) {
                if (varInt == -1){
                    params.put("areaType", 2);
                }else {
                    params.put("labels", varInt);
                }
            }
        }
        varStr = request.getParameter("labelShowTag");
        varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("labelShowTag", varInt);

        log.info("getHotEvent=" + params.toString());
        String result = Utils.sendGet(url, params);
        return result;
    }

    @Override
    public String getBigEventList(HttpServletRequest request) {

        Map<String, Object> params = new HashMap<>();
        String varStr = request.getParameter("page");
        Integer varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("page", varInt);

        varStr = request.getParameter("pageSize");
        varInt = 20;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("pageSize", varInt);


        varStr = request.getParameter("sort");
        varInt = 2;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("sort", varInt);

        varStr = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("startTime", varStr);
        }

        varStr = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("endTime", varStr);
        }

        varStr = request.getParameter("webShow");
        if (StringUtils.isNotEmpty(varStr)) {

            params.put("webShow", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("showTag");
        if (StringUtils.isNotEmpty(varStr)) {
            params.put("showTag", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("province");
        if (StringUtils.isNotBlank(varStr) && !varStr.equals("全部") ){
            params.put("province", varStr);
        }

        varStr = request.getParameter("areaType");
        if (StringUtils.isNotBlank(varStr)){
            params.put("areaType", Integer.parseInt(varStr));
        }

        varStr = request.getParameter("city");
        if (StringUtils.isNotBlank(varStr)){
            params.put("city", varStr);
        }

        varStr = request.getParameter("labels");
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
            if (varInt != 0) {
                if (varInt == -1){
                    params.put("areaType", 2);
                }else {
                    params.put("labels", varInt);
                }
            }
        }

        varStr = request.getParameter("labelShowTag");
        varInt = 1;
        if (StringUtils.isNotEmpty(varStr)) {
            varInt = Integer.parseInt(varStr);
        }
        params.put("labelShowTag", varInt);

        log.info("getBigEventList=" + params.toString());
        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String jsonStr = Utils.sendPost(baseUrl + "/v1/importantEvent/web/list", params);
        return jsonStr;
    }

    @Override
    public List<HotIncidentDto> getHotEventListByBigEventId(HttpServletRequest request, int id) {
        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String url = baseUrl + "/v3/hotIncident/web/list";
        HashMap<String, Object> params = new HashMap<>(16);
        params.put("showTag", 1);
        params.put("labelShowTag", 0);
        params.put("importantEventId", id);
        String result = Utils.sendGet(url, params);
        if (!TextUtils.isEmpty(result)) {
            PageDto<HotIncidentDto> pageDto = JSON.parseObject(result, new TypeReference<PageDto<HotIncidentDto>>() {
            });
            return pageDto.getList();
        }
        return null;
    }



    @Override
    public String getHotLabel(HttpServletRequest request) {
        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String url = baseUrl + "/v1/hotLabel/get/groupByLevel";
        HashMap<String, Object> params = new HashMap<>(16);
//        params.put("showTag", 1);
        log.info("getHotLabel=" + params.toString());
        String result = Utils.sendGet(url, params);
        return result;
    }

    @Override
    public HotIncidentDto getBigEventDetail(HttpServletRequest request, int id,String startTime,String endTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("webShow", 1);
        params.put("hotShowTag", 1);
        params.put("showTag", 1);
        params.put("sort", 1);
        params.put("areaType",1);
        params.put("labelShowTag",1);
        String baseUrl = SysConfig.cfgMap.get("API_MICROSERVICE_RANKLIST_URL");
        String jsonStr = Utils.sendPost(baseUrl + "/v1/importantEvent/web/get", params);
        if (!TextUtils.isEmpty(jsonStr)) {
            BaseDto<HotIncidentDto> pageDto = JSON.parseObject(jsonStr, new TypeReference<BaseDto<HotIncidentDto>>() {
            });
            return pageDto.getData();
        }
        return new HotIncidentDto();
    }

    @Override
    public String getHotline(HttpServletRequest request, UserDto user) {

        boolean cacheFlag = false;
        DecimalFormat df = new DecimalFormat("######0.00");
        String ratioCustom = "0";
        int timeNodeType;    //1按小时 2按天 3按月
        String timeInterval;

        String code = "9999";

        List<CategoryTypeKeywords> lists = new ArrayList<>();
        Map<String, Object> echartmap = new HashMap<>();
        HashMap<Integer, StatStatLists> echartMap2 = new HashMap<Integer, StatStatLists>();
        String json = null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(request.getParameter("searchKeyword1"))) {
            Keywords k1 = new Keywords();
            k1.setTitle1(request.getParameter("searchKeyword1"));
            k1.setKeyword1(request.getParameter("keyword1"));
            k1.setFilterKeyword1(request.getParameter("filterKeyword1"));
            keywordList.add(k1);
        }
        if (StringUtils.isNotBlank(request.getParameter("searchKeyword2"))) {
            Keywords k2 = new Keywords();
            k2.setTitle1(request.getParameter("searchKeyword2"));
            k2.setKeyword1(request.getParameter("keyword2"));
            k2.setFilterKeyword1(request.getParameter("filterKeyword2"));
            keywordList.add(k2);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        Integer date = Integer.parseInt(request.getParameter("date"));
        String shareCode = request.getParameter("shareCode");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String searchKeyword1 = request.getParameter("searchKeyword1");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        if (CollectionUtils.isNotEmpty(keywordList)) {
            for (int i = 0; i < keywordList.size(); i++) {
                Keywords k = keywordList.get(i);
                String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_HOTTABLE, false, getSessionNameMap(request));
                json = RedisUtils.getAttribute(sessionName);
                CtksStatsView csv = null;
                CtksStatStatListsView csView = null;
                /**
                 * 分享
                 */
                if (StringUtils.isBlank(json) && StringUtils.isNotBlank(request.getParameter("shareCode"))) {
                    cacheFlag = true;
                    csView = getShareData("yearHotStatAndLine", CtksStatStatListsView.class, request.getParameter("shareCode"));
                    cacheFlag = CommonUtils.initTime(date, startTime, endTime, endDate, startDate);
                    if (csView != null) {

                        code = csView.getCode();

                        List<CategoryTypeKeywords> ctkList = csView.getCtksStatStatLists().getCtkList();
                        List<StatStatLists> statsList = csView.getCtksStatStatLists().getStatStatListsList();
                        List<StatStatList> lineData = csView.getCtksStatStatLists().getStatStatListsList().get(0).getStatStatListList();
                        if (CollectionUtils.isNotEmpty(ctkList)) {
                            for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                StringBuilder sb = new StringBuilder();
                                CategoryTypeKeywords ctk = ctkList.get(i1);
                                String f1 = df.format(ctk.getRatioHotCustom());
                                double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                f2 = f2 * 100;
                                int f4 = (int) f2;
                                String f3 = df.format(ctk.getDifferenceCustom());
                                sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                ratioCustom = f3;
                                sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
                            }
                        }
                        if (CollectionUtils.isNotEmpty(statsList)) {
                            for (StatStatLists stats : statsList) {
                                List<StatStatList> stats2 = stats.getStatStatListList();
                                String timeNode = lineData.get(0).getName();
                                timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                                List<String> dates = CommonUtils.getTimeAxis(startDate, endDate, timeNodeType);
                                int j = 0;
                                for (int t = 0; t < dates.size(); t++) {
                                    String d = dates.get(t);
                                    if (j < stats2.size()) {
                                        if (stats2.get(j).getName().indexOf(d) >= 0) {//存在日期
                                            j++;
                                        } else { // 缺少日期补0
                                            StatStatList statStatList = new StatStatList();
                                            statStatList.setName(d);
                                            statStatList.setTotal(0);
                                            lineData.add(statStatList);
                                        }
                                    } else {
                                        StatStatList statStatList = new StatStatList();
                                        statStatList.setName(d);
                                        statStatList.setTotal(0);
                                        lineData.add(statStatList);
                                    }
                                }
                                Collections.sort(lineData, new Comparator<StatStatList>() {
                                    @Override
                                    public int compare(StatStatList o1, StatStatList o2) {
                                        return o1.getName().compareTo(o2.getName());
                                    }
                                });
                            }
                        }
                        if (csView.getCtksStatStatLists() != null && CollectionUtils.isNotEmpty(csView.getCtksStatStatLists().getCtkList())) {
                            csView.getCtksStatStatLists().getCtkList().get(0).setTitle(k.getTitle1());
                            lists.add(csView.getCtksStatStatLists().getCtkList().get(0));
                        }
                        if (csView.getCtksStatStatLists() != null && CollectionUtils.isNotEmpty(csView.getCtksStatStatLists().getStatStatListsList())) {
                            csView.getCtksStatStatLists().getStatStatListsList().get(0).setName(k.getTitle1());
                            echartMap2.put(i, csView.getCtksStatStatLists().getStatStatListsList().get(0));

                        }
                    }
                }
                if (csView == null){
                    cacheFlag = CommonUtils.initTime(date, startTime, endTime, endDate, startDate);
                    timeInterval = Utils.getStringFromDate(startDate, "") + " ~ " + Utils.getStringFromDate(endDate, "");
                    /**
                     * 不是分享
                     */
                    if (StringUtils.isNotEmpty(json)) {//使用缓存
                        cacheFlag = true;
                        csv = JSON.parseObject(json, CtksStatsView.class);
                    } else {//json为空
                        cacheFlag = false;
                        Params p = new Params();
                        p.setIsDelay(0);
                        p.setKeyword(k.getKeyword1());
                        p.setFilterKeyword(k.getFilterKeyword1());
                        p.setDate(date);
                        p.setOrder(Params.ORDER_KEY);
                        p.setSort(Params.SORT_ASC + "");
//                        p.setStatField(Params.STATFIELD_ORIGINTYPE);

                        try {
                            csv = WyqH5AbilitysealMethodV1.hotLine(WyqDataConfig.getUserTag(request, user), p, 0);
                        } catch (Exception e) {
                            log.info(JSONObject.toJSONString(e));
                        }
                        Utils.setResultCtksStats(csv, echartmap);

                        log.info("doHotLineV4 result = " + JSON.toJSONString(csv));
                        if (csv != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(csv.getCode())) {
                            String transactionId = csv.getTransactionId();
                            System.out.println("transactionId=" + transactionId);
                            CtksStats ctksStatStatLists = csv.getCtksStats();
                            if (null != ctksStatStatLists) {
                                List<CategoryTypeKeywords> ctkList = ctksStatStatLists.getCtkList();
                                if (CollectionUtils.isNotEmpty(ctkList)) {
                                    String[] jsons = new String[ctkList.size()];
                                    for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                        StringBuilder sb = new StringBuilder();
                                        CategoryTypeKeywords ctk = ctkList.get(i1);
                                        String f1 = df.format(ctk.getRatioHotCustom());
                                        double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                        f2 = f2 * 100;
                                        int f4 = (int) f2;
                                        String f3 = df.format(ctk.getDifferenceCustom());
                                        sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                        sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                        ratioCustom = f3;
                                        sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                        sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                        sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                        sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
                                        jsons[i1] = sb.toString();
                                    }
                                }

                                List<Stat> lineData = csv.getCtksStats().getStatsList().get(0).getStatList();
                                if (CollectionUtils.isNotEmpty(lineData)) {
                                    String timeNode = lineData.get(0).getName();
                                    timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                                    List<String> dates = CommonUtils.getNewTimeAxis(startDate, endDate, timeNodeType);

                                    for (int q = 0; q < dates.size(); q++) {
                                        String d = dates.get(q);
                                        boolean exist = false;
                                        for (int t = 0; t < lineData.size(); t++) {
                                            Stat stat = lineData.get(t);
                                            if (stat.getName().equals(d)) {
                                                exist = true;
                                                break;
                                            }
                                        }
                                        if (!exist) {
                                            Stat statStatList = new Stat();
                                            statStatList.setName(d);
                                            statStatList.setTotal(0);
                                            lineData.add(statStatList);
                                        }
                                    }

                                    Collections.sort(lineData, new Comparator<Stat>() {
                                        @Override
                                        public int compare(Stat o1, Stat o2) {
                                            return o1.getName().compareTo(o2.getName());
                                        }
                                    });

                                    if (CollectionUtils.isNotEmpty(lineData)) {
                                        if (timeNodeType == 1) {//按小时
                                            lineData.remove(lineData.size() - 1);
                                        }
                                    }
                                    log.info("doHotLineV4 lineData.size() = " + lineData.size());
                                    log.info("doHotLineV4 dates = " + Utils.getJsonFromObject(dates));
                                }
                                String jsonStr = JSON.toJSONString(csv);
                                RedisUtils.setAttribute(sessionName, jsonStr,
                                        SystemConstants.DEFAULT_SESSION_TIME);
                            }

                        }
                    }

                    if (csv != null) {
                        code = csv.getCode();
                        CtksStats ctksStatStatLists = csv.getCtksStats();
                        if (null != ctksStatStatLists) {
                            List<CategoryTypeKeywords> ctkList = ctksStatStatLists.getCtkList();
                            if (CollectionUtils.isNotEmpty(ctkList)) {
                                String[] jsons = new String[ctkList.size()];
                                for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                    StringBuilder sb = new StringBuilder();
                                    CategoryTypeKeywords ctk = ctkList.get(i1);
                                    String f1 = df.format(ctk.getRatioHotCustom());
                                    double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                    f2 = f2 * 100;
                                    int f4 = (int) f2;
                                    String f3 = df.format(ctk.getDifferenceCustom());
                                    sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                    sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                    ratioCustom = f3;
                                    sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                    sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                    sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                    sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
                                    jsons[i1] = sb.toString();
                                }
                            }
                            if (csv.getCtksStats().getStatsList().get(0).getStatList().size() >0){
                                double total1 = csv.getCtksStats().getStatsList().get(0).getStatList().get(csv.getCtksStats().getStatsList().get(0).getStatList().size()-1).getTotal();
                                double total2 = csv.getCtksStats().getStatsList().get(0).getStatList().get(csv.getCtksStats().getStatsList().get(0).getStatList().size()-2).getTotal();
                                ratioCustom = String.valueOf(total1 -  total2);
                            }
                        }
                        if (csv.getCtksStats() != null && CollectionUtils.isNotEmpty(csv.getCtksStats().getCtkList())) {
                            csv.getCtksStats().getCtkList().get(0).setTitle(k.getTitle1());
                            lists.add(csv.getCtksStats().getCtkList().get(0));
                        }
                        if (csv.getCtksStats() != null && CollectionUtils.isNotEmpty(csv.getCtksStats().getStatsList())) {
                            csv.getCtksStats().getStatsList().get(0).setName(k.getTitle1());
                            echartmap.put(String.valueOf(i), csv.getCtksStats().getStatsList().get(0));

                        }
                    }
                }
            }
        }

        Map map = new HashMap();
        map.put("ratioCustom",ratioCustom);
        map.put("doHeatValue",lists);
        map.put("doEventTrend",echartmap);
        map.put("date", request.getParameter("date"));
        map.put("code",code);

        return JSONObject.toJSONString(map);
    }
    @Override
    public String getWordcloud(HttpServletRequest request, UserDto user) {

        Map<String,Object> echartsmapWordcloud = new HashMap<>();
        List<Keywords> keywordList = new ArrayList<Keywords>();
        String json=null;
        if(StringUtils.isNotBlank(request.getParameter("searchKeyword1"))){
            Keywords k1 = new Keywords();
            k1.setTitle1(request.getParameter("searchKeyword1"));
            k1.setKeyword1(request.getParameter("keyword1"));
            k1.setFilterKeyword1(request.getParameter("filterKeyword1"));
            keywordList.add(k1);
        }

        if(CollectionUtils.isNotEmpty(keywordList)){
            for (int i = 0; i < keywordList.size(); i++) {
                Keywords k = keywordList.get(i);
                Params p = new Params();
                p.setIsDelay(0);
                p.setDate(Integer.valueOf(request.getParameter("date")));
                p.setKeyword(k.getKeyword1());
                p.setFilterKeyword(k.getFilterKeyword1());
                p.setComblineflg(Params.COMBLINEFLG_MERGE);
                p.setOrder(Params.ORDER_SIMILAR);
                p.setPagesize(60);
                String jsonStr = "";
                com.xd.tools.view.StatView sv = null;
                String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_MOREWORDS,true,getSessionNameMap(request));
                json = RedisUtils.getAttribute(sessionName);

                String shareCode = request.getParameter("shareCode");

                if (StringUtils.isNotEmpty(json)) {
                    sv = JSON.parseObject(json, StatView.class);
                }else if(StringUtils.isNotBlank(shareCode)){
                    sv = getShareData("yearHotWordCloud", StatView.class,shareCode);
                    jsonStr = JSON.toJSONString(sv);
                }
                if (sv == null){
                    CommAnalysisWordCloud commAnalysisWordCloud = new CommAnalysisWordCloud();
                    commAnalysisWordCloud.setNum(60);
                    try{
                        sv = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(WyqDataConfig.getUserTag(request,user), p, commAnalysisWordCloud);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    Utils.setResultMsgStatView(sv, echartsmapWordcloud);
                    jsonStr = JSON.toJSONString(sv);
                    log.info("doWordcloudV4 result = "+jsonStr);
                }
                if(sv != null){
                    if(CollectionUtils.isNotEmpty(sv.getStatList()) && sv.getStatList().size()>0){
                        echartsmapWordcloud.put(String.valueOf(i), sv.getStatList());
                    }
                    RedisUtils.setAttribute(sessionName, jsonStr,SystemConstants.DEFAULT_SESSION_TIME);
                    echartsmapWordcloud.put("code",sv.getCode());
                }else {
                    echartsmapWordcloud.put("code","9999");
                }
            }
        }else {
            echartsmapWordcloud.put("code","9999");
        }
        return JSONObject.toJSONString(echartsmapWordcloud);
    }

    @Override
    public ModelDto getEmotionProportion(HttpServletRequest request, UserDto user) {





        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        Map<String, Object> map = WyqDataConfig.getDataInitMap(request, user);
        if (StringUtils.isBlank(keyword1)) {
            keyword1 = searchKeyword;
        } else {
            map.put("sensitiveCheck", 0);
        }
        map.put("keyword", keyword1);
        map.put("date", date);
        if (StringUtils.isNotBlank(filterKeyword1)) {
            map.put("filterKeyword", filterKeyword1);
        }
        String userTag = (String) map.get("userTag");
        if (StringUtils.isNotBlank(keyword1)) {
            Map<String, Object> result = new HashMap<String, Object>();
            String sessionName =  CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_WEIBO_EMOTION + SystemConstants.JEDIS_KEYS_EMOTION_RATIO,true,getSessionNameMap(request));
            String json = RedisUtils.getAttribute(sessionName);
            if (StringUtils.isEmpty(json)) {
                Params params = new Params();
                params.setIsDelay(0);
                params.setOrigin("2");// 微博情绪取微博数据
                params.setStatField(Params.STATFIELD_EMOTION);
                params.setDate(date);
                params.setKeyword(keyword1);
                List<ECharts> statValueVoList = new ArrayList<ECharts>();
                try {
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(shareCode)) {
                        com.xd.tools.view.StatsView statsView = getShareData("yearEmotionProportion",
                                com.xd.tools.view.StatsView.class,shareCode);
                        if (statsView != null) {
                            List<Stats> statsList = statsView.getStatsList();
                            if (CollectionUtils.isNotEmpty(statsList)) {
                                for (Stats stats : statsList) {
                                    ECharts data = new ECharts();
                                    data.setName(stats.getName());
                                    data.setValue(Long.valueOf(stats.getNum()).intValue());
                                    statValueVoList.add(data);
                                }
                                Collections.sort(statValueVoList, new Comparator<ECharts>() {
                                    @Override
                                    public int compare(ECharts o1, ECharts o2) {
                                        return (Integer)o2.getValue() - (Integer)o1.getValue();
                                    }
                                });
                                result.put("data", statValueVoList);
                                result.put("color", emotionColorMap);
                                json = JSONObject.toJSONString(result);
                                RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                            }
                        }
                    }

                    if (statValueVoList.size() == 0){
                        log.info("doEmotionProportionV2 params = [" + JSON.toJSONString(params) + "]");
                        com.xd.tools.view.StatView statView = WyqH5MethodV1.statV1_001(userTag, params);
                        Utils.setResultMsgStatView(statView, result);
                        log.info("doEmotionProportionV2 result = [" + JSON.toJSONString(statView) + "]");
                        if (statView != null) {
                            List<Stat> statList = statView.getStatList();
                            if (CollectionUtils.isNotEmpty(statList)) {
                                for (Stat stat : statList) {
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(stat.getNum());
                                    statValueVoList.add(data);
                                }
                                Collections.sort(statValueVoList, new Comparator<ECharts>() {
                                    @Override
                                    public int compare(ECharts o1, ECharts o2) {
                                        return ((Long) o2.getValue()).intValue() - ((Long) o1.getValue()).intValue();
                                    }
                                });
                                result.put("data", statValueVoList);
                                result.put("color", emotionColorMap);
                                json = JSONObject.toJSONString(result);
                                if(result.get("code").equals("0000")){
                                    RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.jsonToMap(result, json);
            }
            if (MapUtils.isNotEmpty(result)) {
                return dto = new ModelDto(1, result);
            }
        }
        return dto = new ModelDto(0);
    }

    @Override
    public ModelDto getEmotionMap(HttpServletRequest request, UserDto user) {

        ModelDto dto = new ModelDto();
        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        Map<String, Object> map = WyqDataConfig.getDataInitMap(request,user);
        if(StringUtils.isBlank(keyword1)){
            keyword1 = searchKeyword;
        }else{
            map.put("sensitiveCheck",0);
        }
        map.put("keyword",keyword1);
        map.put("date",date);
        if(StringUtils.isNotBlank(filterKeyword1)){
            map.put("filterKeyword",filterKeyword1);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        String userTag = (String) map.get("userTag");
        if (StringUtils.isNotBlank(keyword1)) {
            String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_WEIBO_EMOTION + SystemConstants.JEDIS_KEYS_EMOTION_MAP,true,getSessionNameMap(request));
            String json =  RedisUtils.getAttribute(sessionName);
            if (StringUtils.isEmpty(json)) {
                com.xd.tools.view.StatsView view = null;
                Params params = new Params();
                params.setIsDelay(0);
                params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PROVINCE);
                params.setOrder(Params.ORDER_VALUE + "," + Params.ORDER_VALUE);
                params.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
                params.setDate(date);
                params.setKeyword(keyword1);
                params.setOrigin("2");// 微博情绪取微博数据
                try {
                    if (StringUtils.isNotBlank(shareCode)) {
                        view = getShareData("yearEmotionMap", com.xd.tools.view.StatsView.class,shareCode);
                    }
                    if (view == null) {
                        log.info("doEmotionMap params = [" + JSON.toJSONString(params) + "]");
                        view = WyqH5MethodV1.statStackedV1_001(userTag, params);
                        Utils.setResultMsg(view, result);
                        log.info("doEmotionMap result = [" + JSON.toJSONString(view) + "]");
                    }
                    if (view != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
                        List<Stats> statsList = view.getStatsList();
                        if (CollectionUtils.isNotEmpty(statsList)) {
                            // 各情绪
                            Map<String, List<ECharts>> dataMap = new HashMap<String, List<ECharts>>();
                            // 显著情绪
                            String[] lendth = emotionArray;
                            List<Object> dataList = new ArrayList<Object>();
                            List<ECharts> data = new ArrayList<ECharts>();
                            List<String[]> emotionRankings = new ArrayList<String[]>();
                            Map<String, List<ECharts>> provinceEmotions = new HashMap<String, List<ECharts>>();
                            // 各省情绪总声量
                            Map<String, Long> provinceEmotionTotal = new HashMap<String, Long>();

                            List<ECharts> emotionOne = new ArrayList<ECharts>();
                            List<String> emotions = new ArrayList<String>();
                            for (Stats stats : statsList) {
                                String emotion = stats.getName();
                                if("显著情绪".equals(emotion)){
                                    continue;
                                }
                                emotions.add(emotion);
                                // 各情绪
                                emotionOne = dataMap.get(emotion);
                                if (emotionOne == null)
                                    emotionOne = new ArrayList<ECharts>();
                                if (CollectionUtils.isNotEmpty(stats.getStatList())) {
                                    for (Stat stat : stats.getStatList()) {
                                        // 各情绪
                                        String province = stat.getName();
                                        long count = stat.getNum();
                                        if (StringUtils.isEmpty(province) || province.equals("全国")
                                                || province.equals("海外"))
                                            continue;
                                        if (count <0)
                                            count = 0;
                                        ECharts pEmotion = new ECharts();
                                        pEmotion.setName(province);
                                        pEmotion.setValue(count);
                                        emotionOne.add(pEmotion);

                                        // 显著情绪
                                        List<ECharts> provinceEmotion = provinceEmotions.get(province);
                                        if (provinceEmotion == null) {
                                            provinceEmotion = new ArrayList<ECharts>();
                                        }
                                        pEmotion = new ECharts();
                                        pEmotion.setName(emotion);
                                        pEmotion.setValue(count);
                                        provinceEmotion.add(pEmotion);
                                        provinceEmotions.put(province, provinceEmotion);

                                        // 各省情绪总声量
                                        Long pet = provinceEmotionTotal.get(province);
                                        if (pet == null)
                                            pet = 0l;
                                        pet += count;
                                        provinceEmotionTotal.put(province, pet);
                                    }
                                }
                                dataMap.put(emotion, emotionOne);
                            }
                            // 各情绪按省份排序
                            List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>(6);
                            for (String emotion : emotionArray) {
                                Map<String, Object> data2 = new HashMap<String, Object>();
                                emotionOne = dataMap.get(emotion);
                                if (emotionOne != null) {
                                    Collections.sort(emotionOne, new Comparator<ECharts>() {
                                        @Override
                                        public int compare(ECharts o1, ECharts o2) {
                                            Long f = (Long)o2.getValue() - (Long)o1.getValue();
                                            return f.intValue();
                                        }
                                    });
                                }
                                data2.put("name", emotion);
                                data2.put("data", emotionOne);
                                dataList2.add(data2);
                            }
                            // 各省显著情绪排序、各省情绪总声量排序
                            for (String province : provinceEmotions.keySet()) {
                                List<ECharts> provinceEmotion = provinceEmotions.get(province);
                                if (CollectionUtils.isNotEmpty(provinceEmotion)) {
                                    Collections.sort(provinceEmotion, new Comparator<ECharts>() {
                                        @Override
                                        public int compare(ECharts o1, ECharts o2) {
                                            Long f = (Long)o2.getValue() - (Long)o1.getValue();
                                            return f.intValue();
                                        }
                                    });
                                    ECharts eCharts = new ECharts();
                                    eCharts.setName(province);
                                    eCharts.setValue(emotionTypeMap.get(provinceEmotion.get(0).getName())); // 设置情绪标记值
                                    eCharts.setEmotion(provinceEmotion);
                                    data.add(eCharts);
                                }
                                String[] emotionRanking = { province,
                                        String.valueOf(provinceEmotionTotal.get(province)),
                                        String.valueOf(provinceEmotion.get(0).getName()) };
                                emotionRankings.add(emotionRanking);
                            }
                            Collections.sort(emotionRankings, new Comparator<String[]>() {
                                @Override
                                public int compare(String[] o1, String[] o2) {
                                    long count1 = 0L;
                                    long count2 = 0L;
                                    if (o1 != null && o1.length == 3){
                                        count1 = Long.valueOf(o1[1]);
                                    }
                                    if (o2 != null && o2.length == 3){
                                        count2 = Long.valueOf(o2[1]);
                                    }
                                    if (count1 > count2){
                                        return -1;
                                    }else if (count1 == count2){
                                        return 0;
                                    }else{
                                        return 1;
                                    }
                                }
                            });
                            dataList.add(emotionRankings);
                            dataList.add(lendth);
                            dataList.add(data);
                            dataList.add(emotionColorMap);
                            result.put("map1", dataList);
                            result.put("map2", dataList2);
                            Utils.setResultMsg(view, result);
                            String jsonStr = JSONObject.toJSONString(result);
                            RedisUtils.setAttribute(sessionName, jsonStr, SystemConstants.DEFAULT_SESSION_TIME);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.jsonToMap(result, json);
            }
            if (MapUtils.isNotEmpty(result) && result.get("map1")!=null) {
                return dto =new ModelDto(1,result);
            }
        }
        return dto =new ModelDto(0,result);
    }

    @Override
    public ModelDto getContentTrends(HttpServletRequest request, UserDto user) {
        ModelDto dto = new ModelDto();
        Integer timeNodeType;
        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }

        Map<String, Object> map = WyqDataConfig.getDataInitMap(request,user);
        if(StringUtils.isBlank(keyword1)){
            keyword1 = searchKeyword;
        }else{
            map.put("sensitiveCheck",0);
        }
        map.put("keyword",keyword1);
        map.put("date", date);
        if(StringUtils.isNotBlank(filterKeyword1)){
            map.put("filterKeyword",filterKeyword1);
        }

        if(StringUtils.isBlank(keyword1)) {

            return dto = new ModelDto(0);
        }
        Map<String, Object> result=new HashMap<String, Object>();
        String userTag = (String) map.get("userTag");
        String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_WEIBO_EMOTION + SystemConstants.JEDIS_KEYS_EMOTION_LINE,true,getSessionNameMap(request));
        String json = RedisUtils.getAttribute(sessionName);
        com.xd.tools.view.StatsView sv = null;
        if(StringUtils.isNotBlank(json)) {
            sv = JSON.parseObject(json, com.xd.tools.view.StatsView.class);
            result.put("code", sv.getCode());
            result.put("message", sv.getMessage());
            result.put("data", sv.getStatsList());
            return  dto = new ModelDto(1,result);
        }
        if(StringUtils.isNotBlank(shareCode)) {
            sv = getShareData("yearEmotionalMovements", com.xd.tools.view.StatsView.class,shareCode);
            if (sv != null){
                result.put("code", sv.getCode());
                result.put("message", sv.getMessage());
                result.put("data", sv.getStatsList());
                return   dto = new ModelDto(1,result);
            }
        }
        CommonUtils.initTime(date,startTime,endTime,startDate,endDate);
        Params params = new Params();
        params.setIsDelay(0);
        params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_PUBLISHEDHOUR);
        params.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
        params.setSort(Params.SORT_DESC + "," + Params.SORT_ASC);
        params.setDate(date);
        params.setStartTime(startTime);
        params.setEndTime(endTime);
        params.setKeyword(keyword1);
        log.info("doMoodContentTrends2 params = [" + JSON.toJSONString(params) + "]");
        try {
            sv = WyqH5MethodV1.statStackedV1_001(userTag, params);
        }catch (Exception e){
            log.info("doMoodContentTrends2 Error"+e);
        }
        Utils.setResultMsg(sv, result);
        json = JSONObject.toJSONString(sv);
        log.info("doMoodContentTrends2 result = [" + json + "]");
        if (sv != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(sv.getCode())) {
            List<Stats> statsList = sv.getStatsList();//喜悦、愤怒等值
            if (CollectionUtils.isNotEmpty(statsList)) {
                for(Stats stats : statsList){
                    List<Stat> statList = stats.getStatList();
                    if(CollectionUtils.isNotEmpty(statList)){//喜悦、愤怒等值
                        Collections.sort(statList, new Comparator<Stat>() {
                            @Override
                            public int compare(Stat o1, Stat o2) {
                                return o1.getName().compareTo(o2.getName());
                            }
                        });
                        String timeNode = statList.get(0).getKey();
                        timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                        List<String> dates = CommonUtils.getTimeAxis(startDate, endDate, timeNodeType);
                        int j = 0;
                        for (int t = 0; t < dates.size(); t++) {
                            String d = dates.get(t);
                            if (j < statList.size()) {
                                if (statList.get(j).getName().indexOf(d) >= 0) {//存在日期
                                    j++;
                                } else { // 缺少日期补0
                                    Stat statStatList=new Stat();
                                    statStatList.setName(d);
                                    statStatList.setNum(0);
                                    statList.add(statStatList);
                                }
                            } else {
                                Stat statStatList=new Stat();
                                statStatList.setName(d);
                                statStatList.setNum(0);
                                statList.add(statStatList);
                            }
                        }
                    }
                }
                result.put("code", "0000");
                result.put("data", sv.getStatsList());
                RedisUtils.setAttribute(sessionName, JSON.toJSONString(sv), SystemConstants.DEFAULT_SESSION_TIME);
                return dto = new ModelDto(1,result);
            }
        }else{
            return dto = new ModelDto(0,result);
        }
        return dto = new ModelDto(0);
    }

    @Override
    public ModelDto getEmotionSex(HttpServletRequest request, UserDto user) {

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        Map<String, Object> map = WyqDataConfig.getDataInitMap(request,user);
        if(StringUtils.isBlank(keyword1)){
            keyword1 = searchKeyword;
        }else{
            map.put("sensitiveCheck",0);
        }
        map.put("keyword",keyword1);
        map.put("date", date);
        if(StringUtils.isNotBlank(filterKeyword1)){
            map.put("filterKeyword",filterKeyword1);
        }

        String userTag = (String) map.get("userTag");
        if (StringUtils.isNotBlank(keyword1)) {
            Map<String, Object> result = new HashMap<String, Object>();
            String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_WEIBO_EMOTION + SystemConstants
                            .JEDIS_KEYS_SEX_EMOTION,
                    true,getSessionNameMap(request));
            String json = RedisUtils.getAttribute(sessionName);
            if (StringUtils.isEmpty(json)) {
                com.xd.tools.view.StatsView view = null;
                Params params = new Params();
                params.setIsDelay(0);
                params.setStatField(Params.STATFIELD_USERGENDER + "," + Params.STATFIELD_EMOTION);
                params.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
                params.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
                params.setDate(date);
                params.setKeyword(keyword1);
                params.setOrigin("2");// 微博情绪取微博数据
                try {
                    boolean isShareCode = true;
                    if (StringUtils.isNotBlank(shareCode)) {
                        view = getShareData("yearPeopleMood", com.xd.tools.view.StatsView.class,shareCode);
                    }
                    if (view == null){
                        isShareCode = false;
                        log.info("doEmotionSex2 params = [" + JSON.toJSONString(params) + "]");
                        view = WyqH5MethodV1.statStackedV1_001(userTag, params);
                        Utils.setResultMsg(view, result);
                        log.info("doEmotionSex2 result = [" + JSON.toJSONString(view) + "]");
                    }
                    if (view != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
                        long max = 0l;
                        List<Stats> statsList = view.getStatsList();
                        if (CollectionUtils.isNotEmpty(statsList)) {
                            Map<String, Long> m = new HashMap<String, Long>();
                            Map<String, Long> f = new HashMap<String, Long>();
                            for (String key : emotionFlagMap.keySet()) {
                                m.put(emotionFlagMap.get(key), 0l);
                                f.put(emotionFlagMap.get(key), 0l);
                            }
                            if(StringUtils.isBlank(shareCode) && isShareCode) {
                                for (Stats stats : statsList) {
                                    String sex = stats.getName();
                                    for (Stat stat : stats.getStatList()) {
                                        long num = stat.getNum();
                                        if (num > max)
                                            max = num;
                                        String emotion = stat.getName();
                                        if ("m".equals(sex)) {
                                            m.put(emotionFlagMap.get(emotion), num);
                                        } else {
                                            f.put(emotionFlagMap.get(emotion), num);
                                        }
                                    }
                                }
                            }else {
                                for (Stats stats : statsList) {
                                    String emotion = stats.getName();
                                    for (Stat stat : stats.getStatList()) {
                                        long num = stat.getNum();
                                        if (num > max)
                                            max = num;
                                        String sex = stat.getName();
                                        if ("m".equals(emotion)) {
                                            m.put(emotionFlagMap.get(sex), num);
                                        } else {
                                            f.put(emotionFlagMap.get(sex), num);
                                        }
                                    }
                                }
                            }
                            if (max > 0) {
                                long maxHeight = max;
                                max = 1;
                                for (; maxHeight > 10; maxHeight = maxHeight / 10)
                                    max *= 10;
                                max = max * (maxHeight + 1);
                            }
                            result.put("m", m);
                            result.put("f", f);
                            result.put("max", max);
                            result.put("color", emotionColorMap);
                            String jsonStr = JSONObject.toJSONString(result);
                            if (StringUtils.isNotBlank(jsonStr)){
                                RedisUtils.setAttribute(sessionName, jsonStr, SystemConstants.DEFAULT_SESSION_TIME);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                 CommonUtils.jsonToMap(result, json);
            }
            if (MapUtils.isNotEmpty(result)) {
                return   new ModelDto(1, result);
            }
        }
        return   new ModelDto(0);
    }

    @Override
    public ModelDto getEmotionType(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        Map<String, Object> map = WyqDataConfig.getDataInitMap(request,user);
        if(StringUtils.isBlank(keyword1)){
            keyword1 = searchKeyword;
        }else{
            map.put("sensitiveCheck",0);
        }
        map.put("keyword",keyword1);
        map.put("date", date);
        if(StringUtils.isNotBlank(filterKeyword1)){
            map.put("filterKeyword",filterKeyword1);
        }

        String userTag = WyqDataConfig.getUserTag(request, user);
        if (StringUtils.isNotBlank(keyword1)) {
            Map<String, Object> result = new HashMap<String, Object>();
            String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_WEIBO_EMOTION + SystemConstants.JEDIS_KEYS_USER_TYPE_EMOTION, true,getSessionNameMap(request));
            String json = RedisUtils.getAttribute(sessionName);
            if (StringUtils.isEmpty(json)) {
                com.xd.tools.view.StatsView view = null;
                Params params = new Params();
                params.setIsDelay(0);
                params.setStatField(Params.STATFIELD_EMOTION + "," + Params.STATFIELD_USERVERIFIED);
                params.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
                params.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
                params.setDate(date);
                params.setKeyword(keyword1);
                params.setOrigin("2");// 微博情绪取微博数据
                try {
                    if (StringUtils.isNotBlank(shareCode)) {
                        view = getShareData("yearCertificationEmotions", com.xd.tools.view.StatsView.class,shareCode);
                    }
                    if (view == null){
                        log.info("doEmotionType2 params = [" + JSON.toJSONString(params) + "]");
                        view = WyqH5MethodV1.statStackedV1_001(userTag, params);
                        Utils.setResultMsg(view, result);
                        log.info("doEmotionType2 result = [" + JSON.toJSONString(view) + "]");
                    }
                    if (view != null) {
                        if (CodeConstant.SUCCESS_CODE.equals(view.getCode()) && CollectionUtils.isNotEmpty(view.getStatsList())) {
                            List<Stats> statsList = view.getStatsList();
                            if (CollectionUtils.isNotEmpty(statsList)) {
                                long max = 0;
                                result.put("xy", new double[2][4]);
                                result.put("zx", new double[2][4]);
                                result.put("fn", new double[2][4]);
                                result.put("bs", new double[2][4]);
                                result.put("jq", new double[2][4]);
                                result.put("kj", new double[2][4]);
                                long[][] userTypeMap = new long[4][7];
                                String[] userType = { "普通用户", "橙V用户", "蓝V用户", "达人用户" };
                                for (Stats stats : statsList) {
                                    String emotion = stats.getName();
                                    int index = emotionTypeMap.get(emotion);
                                    for (Stat stat : stats.getStatList()) {
                                        String key = stat.getKey();
                                        long num = stat.getNum();
                                        if (StringUtils.isNotEmpty(key)) {
                                            long[] data = null;
                                            if (Params.VERIFIED_NORMAL.equals(key)) { // 普通用户
                                                data = userTypeMap[0]; // 获取用户情绪
                                                data[index] += num; // 对应情绪数量累加
                                                data[6] += num; // 用户的情绪总值累加
                                            } else if (Params.VERIFIED_ORANGE_V.equals(key)) { // 橙V用户
                                                data = userTypeMap[1];
                                                data[index] += num;
                                                data[6] += num;
                                            } else if (Params.VERIFIED_MASTER.contains(key)) { // 达人用户
                                                data = userTypeMap[3];
                                                data[index] += num;
                                                data[6] += num;
                                            } else if (Params.VERIFIED_BLUE_V.contains(key)) { // 蓝V用户
                                                data = userTypeMap[2];
                                                data[6] += num;
                                                data[index] += num;
                                            }
                                        }
                                        if (num > max)
                                            max = num;
                                    }
                                }

                                double[][] pre = null;
                                for (int i = 0; i < 4; i++) {
                                    long[] data = userTypeMap[i];
                                    for (String emotion : emotionArray) {
                                        pre = (double[][]) result.get(emotionFlagMap.get(emotion));
                                        pre[1][i] = data[emotionTypeMap.get(emotion)];
                                        pre[0][i] = 100 * (double) pre[1][i] / (double) data[6];
                                        BigDecimal b = new BigDecimal(pre[0][i]);
                                        pre[0][i] = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                    }
                                }
                                String[] emotion = emotionArray;
                                result.put("color", emotionColorMap);
                                result.put("userType", userType);
                                result.put("emotion", emotion);
                                result.put("max", max);
                                String jsonStr = JSONObject.toJSONString(result);
                                RedisUtils.setAttribute(sessionName, jsonStr, SystemConstants.DEFAULT_SESSION_TIME);
                            }
                        } else {
                            result.put("code", CodeConstant.FAILURE_CODE);
                            json = JSONObject.toJSONString(result);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                CommonUtils.jsonToMap(result, json);
            }
            if (MapUtils.isNotEmpty(result)) {
                return dto=new ModelDto(1, result);
            }
        }
        return dto=new ModelDto(0);
    }

    @Override
    public ModelDto getEmotionSex2(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_SEX_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);

        String userTag = WyqDataConfig.getUserTag(request, user);

        if (StringUtils.isEmpty(json)) {
            com.xd.tools.view.StatsView statView=null;
            Params p = new Params();
            p.setIsDelay(0);
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_USERGENDER + "," + Params.STATFIELD_CUSTOMFLAG1);
            p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
            p.setSort(Params.SORT_DESC + "," + Params.SORT_ASC);
            if (StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearMoodSix", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(userTag, p);
                }catch (Exception e){
                    log.info("doEmotionSexV2"+e);
                }

                log.info("doEmotionSexV2"+" params = [" + JSON.toJSONString(p) + "]");
                log.info(new Date() +"--"+"doEmotionSexV2"+" = " + JSON.toJSONString(statView));

                Utils.setResultMsg(statView, result);
            }
            try {
                if (statView != null) {
                    if (CollectionUtils.isNotEmpty(statView.getStatsList())) {
                        double[] zm = new double[2];
                        double[] fm = new double[2];
                        double[] total = new double[2];
                        for (Stats stats : statView.getStatsList()) {
                            String sex = stats.getName(); // m:男 f:女
                            int index = 0;
                            if ("f".equals(sex)) {
                                index = 1;
                            }
                            for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                if ("敏感".equals(stat.getName()))
                                    fm[index] = stat.getNum();
                                else
                                    zm[index] = stat.getNum();
                                total[index]  += stat.getNum();
                            }
                        }
                        for(int j=0; j<2; j++){
                            if(total[0] != 0){
                                zm[j] = 100*zm[j]/total[j];
                                fm[j] = 100*fm[j]/total[j];
                            }else{
                                zm[j] = 0;
                                fm[j] = 0;
                            }
                        }
                        result.put("zm", zm);
                        result.put("fm", fm);
                        json = JSONObject.toJSONString(result);
                        if(result.get("code").equals("0000")){
                            RedisUtils.setAttribute(sessionName, json,
                                    SystemConstants.DEFAULT_SESSION_TIME);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return  dto = new ModelDto(1,r);
        }else{
            return  dto = new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionType2(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        log.info("doEmotionTypeV2 ");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        com.xd.tools.view.StatsView statView = null;
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        Params p = new Params();
        p.setIsDelay(0);
        p.setDate( date);
        p.setKeyword(keyword1);
        p.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_USERVERIFIED);
        p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
        p.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
        p.setStatCount(20);
        if(StringUtils.isNotBlank(filterKeyword1)){
            p.setFilterKeyword(filterKeyword1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_USER_TYPE_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        String userTag = WyqDataConfig.getUserTag(request, user);

        if (StringUtils.isEmpty(json)) {
            if (StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearUserAuthenType", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(userTag, p);
                }catch (Exception e){
                    log.info("statStackedV1_001"+e);
                }

                log.info("getEmotionTypeV2"+" params = [" + JSON.toJSONString(p) + "]");
                log.info(new Date() +"--"+"getEmotionTypeV2"+" = " + JSON.toJSONString(statView));


                Utils.setResultMsg(statView, result);
            }
            try {
                if (statView != null) {
                    if (CollectionUtils.isNotEmpty(statView.getStatsList())) {
                        List<Stats> statsList = statView.getStatsList();
                        String[] legend = {"普通用户", "橙V用户", "蓝V用户", "达人"};
                        double[] zm = { 0d, 0d, 0d, 0d }; // '普通用户', '橙V用户', '蓝V用户', '达人'
                        double[] fm = { 0d, 0d, 0d, 0d };
                        double[] total = { 0d, 0d, 0d, 0d };

                        if (CollectionUtils.isNotEmpty(statsList)) {
                            for (Stats stats : statsList) {
                                if ("敏感".equals(stats.getName())) {
                                    for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                        String key = stat.getKey();
                                        Integer num = (int) stat.getNum();
                                        if (num == null)
                                            num = 0;
                                        System.out.println(key);
                                        if (StringUtils.isNotEmpty(key)) {
                                            if (Params.VERIFIED_NORMAL.equals(key)) { // 普通用户
                                                fm[0] += num;
                                                total[0] += num;
                                            } else if (Params.VERIFIED_ORANGE_V.equals(key)) { // 橙V用户
                                                fm[1] += num;
                                                total[1] += num;
                                            } else if (Params.VERIFIED_MASTER.contains(key)) { // 达人用户
                                                fm[3] += num;
                                                total[3] += num;
                                            } else if (Params.VERIFIED_BLUE_V.contains(key)) { // 蓝V用户
                                                fm[2] += num;
                                                total[2] += num;
                                            }
                                        }
                                    }
                                } else if ("非敏感".equals(stats.getName())) {
                                    for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                        String key = stat.getKey();
                                        Integer num = (int) stat.getNum();
                                        if (num == null)
                                            num = 0;
                                        if (StringUtils.isNotEmpty(key)) {
                                            if (Params.VERIFIED_NORMAL.equals(key)) { // 普通用户
                                                zm[0] += num;
                                                total[0] += num;
                                            } else if (Params.VERIFIED_ORANGE_V.equals(key)) { // 橙V用户
                                                zm[1] += num;
                                                total[1] += num;
                                            } else if (Params.VERIFIED_MASTER.contains(key)) { // 达人用户
                                                zm[3] += num;
                                                total[3] += num;
                                            } else if (Params.VERIFIED_BLUE_V.contains(key)) { // 蓝V用户
                                                zm[2] += num;
                                                total[2] += num;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for(int j=0; j<4; j++){
                            if(total[j] != 0d){
                                zm[j] = 100*zm[j]/total[j];
                                fm[j] = 100*fm[j]/total[j];
                            }else{
                                zm[j] = 0;
                                fm[j] = 0;
                            }
                        }
                        result.put("legend", legend);
                        result.put("zm", zm);
                        result.put("fm", fm);
                        json = JSONObject.toJSONString(result);
                        if(statView != null && "0000".equals(statView.getCode())){
                            RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }

        if(MapUtils.isNotEmpty(result) && result.get("code").equals("0000")){
            return  new ModelDto(1,r);
        }else{
            return  new ModelDto(0,r);
        }
    }

    @Override
    public ModelDto getEmotionFans(HttpServletRequest request, UserDto user){


        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FANS_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(0);
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_CUSTOMFLAG1);
            try {
                Integer[] zm = { 0, 0, 0, 0, 0, 0, 0, 0 };
                Integer[] fm = { 0, 0, 0, 0, 0, 0, 0, 0 };
                String[] xAxis = new String[8];
                int j = 0;
                boolean isLoad = false;
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView view = getShareData("yearUserFuns", com.xd.tools.view.StatsView.class,shareCode);
                    Map<String, Integer> indeMap = new HashMap<String, Integer>();
                    for (Range range : sortList) {
                        xAxis[j] = range.getName();
                        indeMap.put(xAxis[j], j);
                        j++;
                    }
                    if(view != null){
                        isLoad = true;
                        List<Stats> statsList = view.getStatsList();
                        if(null !=statsList && statsList.size()>0){
                            for(Stats stats : statsList){
                                if ("敏感".equals(stats.getName())) {
                                    for(com.xd.tools.pojo.Stat stat : stats.getStatList()){
                                        fm[indeMap.get(stat.getName())] = stat.getCount().intValue();
                                    }
                                } else if ("非敏感".equals(stats.getName())) {
                                    for(com.xd.tools.pojo.Stat stat : stats.getStatList()){
                                        zm[indeMap.get(stat.getName())] = stat.getCount().intValue();
                                    }
                                }
                            }
                        }
                    }
                }
                if (!isLoad){
                    j = 0;
                    String userTag = WyqDataConfig.getUserTag(request, user);
                    for (com.miduchina.wrd.eventanalysis.utils.Range range : sortList) {
                        xAxis[j] = range.getName();
                        p.setFollowersCountStart(range.getFrom());
                        p.setFollowersCountEnd(range.getTo());

                        StatView statView = WyqH5MethodV1.statV1_001(userTag, p);
                        log.info("doEmotionFansV2"+" params = [" + JSON.toJSONString(p) + "]");
                        log.info(new Date() +"--"+"doEmotionFansV2"+" = " + JSON.toJSONString(statView));
                        Utils.setResultMsgStatView(statView, result);

                        if (statView != null) {
                            if (CollectionUtils.isNotEmpty(statView.getStatList())) {
                                for (com.xd.tools.pojo.Stat stat : statView.getStatList()) {
                                    if ("敏感".equals(stat.getName())) {
                                        fm[j] = (int) stat.getNum();
                                    } else {
                                        zm[j] = (int) stat.getNum();
                                    }
                                }
                            }
                        }
                        j++;
                    }
                }
                Integer[] zm2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
                Integer[] fm2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
                boolean equals = Arrays.equals(zm, zm2);
                boolean equals2 = Arrays.equals(fm,fm2);
                if (!(equals&&equals2) && result.get("code").equals("0000")) {
                    result.put("zm", zm);
                    result.put("fm", fm);
                    result.put("xAxis", xAxis);
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result) && result.get("code").equals("0000")){
            return dto =  new ModelDto(1,r);
        }else{
            return dto =  new ModelDto(0,r);
        }
    }

    @Override
    public ModelDto getEmotionLevel(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;

        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }


        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_RELAY_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            com.xd.tools.view.StatsView statView = null;
            Params p = new Params();
            p.setIsDelay(0);
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_LEVEL);
            p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
            p.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
            p.setStatCount(1000);
            String userTag = WyqDataConfig.getUserTag(request, user);
            if (StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearForwardHierarchy", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(userTag, p);
                }catch(Exception e){
                    log.info("statStackedV1_001 ERROR"+e);
                }
                log.info("doEmotionLevelV2"+" params = [" + JSON.toJSONString(p) + "]");
                log.info(new Date() +"--"+"doEmotionLevelV2"+" = " + JSON.toJSONString(statView));
                Utils.setResultMsg(statView, result);
            }
            try {
                List<Stats> statsList = statView.getStatsList();
                long[] zm = { 0l, 0l, 0l, 0l };
                long[] fm = { 0l, 0l, 0l, 0l };
                if (CollectionUtils.isNotEmpty(statsList)) {
                    for (Stats stats : statsList) {
                        if(CollectionUtils.isNotEmpty(stats.getStatList())){
                            if ("敏感".equals(stats.getName())) {
                                for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                    Integer level = Integer.valueOf(stat.getKey());
                                    int num = (int) stat.getNum();
                                    if (level == 1) {
                                        fm[0] = num;
                                    } else if (level == 2) {
                                        fm[1] = num;
                                    } else if (level == 3) {
                                        fm[2] = num;
                                    } else if (level >= 4) {
                                        fm[3] += num;
                                    }
                                }
                            } else if ("非敏感".equals(stats.getName())) {
                                for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                    Integer level = Integer.valueOf(stat.getKey());
                                    int num = (int) stat.getNum();
                                    if (level == 1) {
                                        zm[0] = num;
                                    } else if (level == 2) {
                                        zm[1] = num;
                                    } else if (level == 3) {
                                        zm[2] = num;
                                    } else if (level >= 4) {
                                        zm[3] += num;
                                    }
                                }
                            }
                        }
                    }
                }
                String[] name = {"一次转发", "二次转发", "三次转发", "四次+"};
                result.put("name", name);
                result.put("zm", zm);
                result.put("fm", fm);
                json = JSONObject.toJSONString(result);
                if(statView != null && "0000".equals(statView.getCode())){
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result) && "0000".equals(result.get("code"))){
            return dto = new ModelDto(1,r);
        }else{
            return dto = new ModelDto(0,result);
        }
    }

    @Override
    public ModelDto getEmotionHobby(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;

        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_INTEREST_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);

        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(0);
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_USERTAG);
            p.setPage(1);
            p.setPagesize(10);
            int j = 0;
            List<ECharts> zm = new ArrayList<ECharts>();
            long zmMax = 0l;
            List<ECharts> fm = new ArrayList<ECharts>();
            long fmMax = 0l;
            StatView view=null;
            try {
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearInterestLabel", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                        for(Stats stats : statsView.getStatsList()){
                            j = 0;
                            List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                            Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                @Override
                                public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                    return (int) (o2.getNum() - o1.getNum());
                                }
                            });
                            if("敏感".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (j >= 9){
                                        break;
                                    }
                                    long num = stat.getNum();
                                    if (num > fmMax)
                                        fmMax = num;
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    fm.add(data);
                                    j++;
                                }
                            }else if("非敏感".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (j >= 9){
                                        break;
                                    }
                                    long num = stat.getNum();
                                    if (num > zmMax)
                                        zmMax = num;
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    zm.add(data);
                                    j++;
                                }
                            }
                        }
                    }
                }
                if (fm.size()==0 && zm.size() == 0){
                    String userTag = WyqDataConfig.getUserTag(request, user);

                    log.info("doEmotionHobbyV2 one params = [" + JSON.toJSONString(p) + "]");

                    view = WyqH5MethodV1.statV1_001(userTag, p);
                    log.info("doEmotionHobbyV2 one result = [" + JSON.toJSONString(view) + "]");
                    Utils.setResultMsgStatView(view, result);
                    if (view != null && view.getCode() != null
                            && view.getCode().equals(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS)) {
                        List<com.xd.tools.pojo.Stat> statList = view.getStatList();
                        for (com.xd.tools.pojo.Stat stat : statList) {
                            long num = stat.getNum();
                            if (num > zmMax)
                                zmMax = num;
                            ECharts data = new ECharts();
                            data.setName(stat.getName());
                            data.setValue(num);
                            zm.add(data);
                            j++;
                        }
                        if (j > 9)
                            zm = zm.subList(0, 9);
                    }
                    // 负面
                    j = 0;
                    p.setOptions(Params.OPTIONS_SENSITIVE);
                    log.info("doEmotionHobbyV2 two params = [" + JSON.toJSONString(p) + "]");
                    view= WyqH5MethodV1.statV1_001(userTag, p);
                    log.info("doEmotionHobbyV2 two result = [" + JSON.toJSONString(view) + "]");
                    if (view != null && view.getCode() != null
                            && view.getCode().equals(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS)) {
                        List<com.xd.tools.pojo.Stat> statList = view.getStatList();
                        for (com.xd.tools.pojo.Stat stat : statList) {
                            long num = stat.getNum();
                            if (num > fmMax)
                                fmMax = num;
                            ECharts data = new ECharts();
                            data.setName(stat.getName());
                            data.setValue(num);
                            fm.add(data);
                            j++;
                        }
                        if (j > 9)
                            fm = fm.subList(0, 9);
                    }
                }

                result.put("zm", zm);
                result.put("fm", fm);
                result.put("zmMax", zmMax);
                result.put("fmMax", fmMax);
                json = JSONObject.toJSONString(result);
//                 JedisUtil.setAttribute(sessionName, json,
//							Constants.DEFAULT_SESSION_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result) && result.get("code").equals("0000")) {
                RedisUtils.setAttribute(sessionName, json,
                        SystemConstants.DEFAULT_SESSION_TIME);
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return  new ModelDto(1,r);
        }else{
            return  new ModelDto(0);
        }
    }

    @Override
    public ModelDto getZMHotWord(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_ZM_WORD, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(0);
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setOrder(Params.ORDER_RECOMMEND);
            p.setSort(String.valueOf(Params.SORT_DESC));
            p.setPage(1);
            p.setPagesize(200);

            CommAnalysisWordCloud cloudWord = new CommAnalysisWordCloud();
            cloudWord.setNum(9);
            List<ECharts> nonsensitiveValueList = new ArrayList<ECharts>();
            try {
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearMouthWords", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null){
                        if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                            for(Stats stats : statsView.getStatsList()){
                                int i = 0;
                                List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                                Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                    @Override
                                    public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                        return (int) (o2.getNum() - o1.getNum());
                                    }
                                });
                                if("正面口碑热词".equals(stats.getName())){
                                    for(com.xd.tools.pojo.Stat stat : statList){
                                        if (i >= 9)
                                            break;
                                        long num = stat.getNum();
                                        ECharts data = new ECharts();
                                        data.setName(stat.getName());
                                        data.setValue(num);
                                        nonsensitiveValueList.add(data);
                                        i++;
                                    }
                                }
                            }
                        }
                    }
                }
                if (nonsensitiveValueList.size() == 0){
                    StatView statView = null;
                    p.setOptions(Params.OPTIONS_NONSENSITIVE);
                    cloudWord.setType("2");
                    String userTag = WyqDataConfig.getUserTag(request, user);
                    statView = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(userTag, p, cloudWord);
                    log.info("doZMHotWordV2"+" params = [" + JSON.toJSONString(p) + "]");
                    log.info(new Date() +"--"+"doZMHotWordV2"+" = " + JSON.toJSONString(statView));

                    Utils.setResultMsgStatView(statView, result);
                    if (statView!=null) {
                        List<com.xd.tools.pojo.Stat> statList = statView.getStatList();
                        Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                            @Override
                            public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                return (int) (o2.getNum() - o1.getNum());
                            }
                        });
                        if(CollectionUtils.isNotEmpty(statList)){
                            for (com.xd.tools.pojo.Stat stat : statList) {
                                ECharts data = new ECharts();
                                data.setName(stat.getName());
                                data.setValue(stat.getNum());
                                nonsensitiveValueList.add(data);
                            }
                        }
                    }
                    /*nonsensitiveValueList = StatValueVoGenerator.generateStatValueVoListWithStatView(statView);*/
                    if (nonsensitiveValueList != null && nonsensitiveValueList.size() > 9) {
                        nonsensitiveValueList = nonsensitiveValueList.subList(0, 9);
                    }
                }
                result.put("zmWords", nonsensitiveValueList);
                if(null !=nonsensitiveValueList && nonsensitiveValueList.size()>0 && result.get("code").equals("0000")){
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME * 6);
                }
                json = JSONObject.toJSONString(result);
//                    JedisUtil.setAttribute(sessionName, json,
//							Constants.DEFAULT_SESSION_TIME * 6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getFMHotWord(HttpServletRequest request, UserDto user){
        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FM_WORD, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setDate( date);
            p.setKeyword(keyword1);
            p.setOrder(Params.ORDER_RECOMMEND);
            p.setSort(String.valueOf(Params.SORT_DESC));
            p.setPage(1);
            p.setPagesize(200);

            CommAnalysisWordCloud cloudWord = new CommAnalysisWordCloud();
            cloudWord.setNum(9);
            List<ECharts> sensitiveValueList = new ArrayList<ECharts>();
            try {
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearMouthWords", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null){
                        if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                            for(Stats stats : statsView.getStatsList()){
                                int i = 0;
                                List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                                Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                    @Override
                                    public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                        return (int) (o2.getNum() - o1.getNum());
                                    }
                                });
                                if("负面口碑热词".equals(stats.getName())){
                                    for(com.xd.tools.pojo.Stat stat : statList){
                                        if (i >= 9)
                                            break;
                                        long num = stat.getNum();
                                        ECharts data = new ECharts();
                                        data.setName(stat.getName());
                                        data.setValue(num);
                                        sensitiveValueList.add(data);
                                        i++;
                                    }
                                }
                            }
                        }
                    }
                }
                if (sensitiveValueList.size() ==0){
                    StatView statView = null;
                    p.setOptions(Params.OPTIONS_SENSITIVE);
                    cloudWord.setType("1");
                    String userTag = WyqDataConfig.getUserTag(request, user);

                    statView = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(userTag, p, cloudWord);
                    log.info("setResponseLog"+" params = [" + JSON.toJSONString(p) + "]");
                    log.info(new Date() +"--"+"setResponseLog"+" = " + JSON.toJSONString(statView));

                    Utils.setResultMsgStatView(statView, result);
                    if (statView!=null) {
                        List<com.xd.tools.pojo.Stat> statList = statView.getStatList();
                        Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                            @Override
                            public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                return (int) (o2.getNum() - o1.getNum());
                            }
                        });
                        if(CollectionUtils.isNotEmpty(statList)){
                            for (com.xd.tools.pojo.Stat stat : statList) {
                                ECharts data = new ECharts();
                                data.setName(stat.getName());
                                data.setValue(stat.getNum());
                                sensitiveValueList.add(data);
                            }
                        }
                    }
                    /*sensitiveValueList = StatValueVoGenerator.generateStatValueVoListWithStatView(statView);*/
                    if (sensitiveValueList != null && sensitiveValueList.size() > 9) {
                        sensitiveValueList = sensitiveValueList.subList(0, 9);
                    }
                }

                if(null !=sensitiveValueList && sensitiveValueList.size()>0 && result.get("code").equals("0000")){
                    result.put("fmWords", sensitiveValueList);
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME * 6);
                }
                json = JSONObject.toJSONString(result);
//                	JedisUtil.setAttribute(sessionName, json,
//							Constants.DEFAULT_SESSION_TIME * 6);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return  new ModelDto(1,r);
        }else{
            return  new ModelDto(0);
        }
    }

    @Override
    public ModelDto getMediaFriend(HttpServletRequest request, UserDto user){

        ModelDto dto = new ModelDto();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        Map<Integer,Stats> echartMediaFriend = new HashMap<>();
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        Params p = new Params();
        p.setIsDelay(0);
        p.setDate( date);
        p.setOrder(Params.ORDER_KEY);
        p.setSort(Params.SORT_ASC+"");
        p.setStatField(Params.STATFIELD_ORIGINTYPE + "," + Params.STATFIELD_CUSTOMFLAG1);
        p.setKeyword(keyword1);
        p.setFilterKeyword(filterKeyword1);

        String jsonStr = "";
        com.xd.tools.view.StatsView sv = null;
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FRIENDLY, true,getSessionNameMap(request));
        String json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isNotEmpty(json)) {
            sv = JSON.parseObject(json, com.xd.tools.view.StatsView.class);
        }else{
            String userTag = WyqDataConfig.getUserTag(request, user);

            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearHotMediafriend", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (sv == null){
                try {
                    sv = WyqH5MethodV1.statStackedV1_001(userTag, p);
                }catch (Exception e){

                }
            }
            jsonStr = JSON.toJSONString(sv);

            if(StringUtils.isBlank(jsonStr)){
                return dto = new ModelDto(0);
            }
        }
        if(sv != null) {
            if(CollectionUtils.isNotEmpty(sv.getStatsList())) {
                if(sv != null && "0000".equals(sv.getCode())){
                    RedisUtils.setAttribute(sessionName, jsonStr,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
                double mgSum = 0;
                double fmgSum = 0;
                double wzMgSum = 0;
                double wzFmgSum = 0;
                Stats yhd = new Stats();
                yhd.setKey("yhd");
                yhd.setName("友好度");
                List<com.xd.tools.pojo.Stat> list = new ArrayList<>();
                com.xd.tools.pojo.Stat newStat = null;
                com.xd.tools.pojo.Stat wzStat = new com.xd.tools.pojo.Stat();
                wzStat.setName("网站");
                wzStat.setPercent(0d);
                com.xd.tools.pojo.Stat wbStat = new com.xd.tools.pojo.Stat();
                wbStat.setName("微博");
                wbStat.setPercent(0d);
                com.xd.tools.pojo.Stat appStat = new com.xd.tools.pojo.Stat();
                appStat.setName("客户端");
                appStat.setPercent(0d);
                com.xd.tools.pojo.Stat ltStat = new com.xd.tools.pojo.Stat();
                ltStat.setName("论坛");
                ltStat.setPercent(0d);
                com.xd.tools.pojo.Stat wxStat = new com.xd.tools.pojo.Stat();
                wxStat.setName("微信");
                wxStat.setPercent(0d);
                for (int j = 0; j < sv.getStatsList().size(); j++) {
                    Stats ss = sv.getStatsList().get(j);
                    List<com.xd.tools.pojo.Stat> statList = ss.getStatList();
                    if(wzStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wzStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(wbStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wbStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(appStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                appStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(ltStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                ltStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(wxStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wxStat.setPercent(s.getPercent());
                            }
                        }
                    }else{
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                                wzMgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wzFmgSum+=s.getNum();
                            }
                        }
                    }
                }
                double wzPercent = 0;
                double percent = 0;
                if(wzFmgSum+wzMgSum != 0) {
                    wzPercent = wzFmgSum/(wzFmgSum+wzMgSum)*100;
                    wzStat.setPercent(wzPercent);
                }
                list.add(wzStat);
                list.add(wbStat);
                list.add(appStat);
                list.add(ltStat);
                list.add(wxStat);
                percent = fmgSum/(fmgSum+mgSum)*100;
                yhd.setPercent(percent);

                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        com.xd.tools.pojo.Stat s1 = (com.xd.tools.pojo.Stat) o1;
                        com.xd.tools.pojo.Stat s2 = (com.xd.tools.pojo.Stat) o2;
                        // TODO Auto-generated method stub
                        return s2.getPercent()>s1.getPercent() ? 1:-1;
                    }

                });
                yhd.setStatList(list);
                echartMediaFriend.put(0,yhd);
            }
        }
        if(MapUtils.isNotEmpty(echartMediaFriend)){
            return dto = new ModelDto(1,echartMediaFriend);
        }else{
            return dto = new ModelDto(0);
        }

    }

    public Map getSessionNameMap(HttpServletRequest request) {
        Map map = new HashMap();
        map.put("date",request.getParameter("date"));
        map.put("shareCode",request.getParameter("shareCode"));
        map.put("searchKeyword1",request.getParameter("searchKeyword1"));
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("userTag",request.getParameter("userTag"));

        try{
            Date startDate = DateUtils.parse(startTime);
            Date endDate = DateUtils.parse(endTime);
            map.put("startDate",startDate);
            map.put("endDate",endDate);

        }catch (Exception e){

        }
        return map;
    }

    public  <T> T getShareData(String fileName, Class<T> className,String shareCode){

        T view = null;
        Map<String,Object> params = new HashMap<>();
        params.put("shareCode",shareCode);
        BaseDto<HeatShareDto> dto = apiInfoClient.findHs(params);
        log.info("getShareData dto:{}",JSONObject.toJSONString(dto));
        if (dto != null && dto.getCode().equals(CodeConstant.SUCCESS_CODE)){
            HeatShareDto hs = dto.getData();
            String filePath="";
            if(Flags.local_flag){
                filePath = Flags.filePath + "heatAnalysis/" +
                        DateUtils.format(hs.getCreateTime(), DateUtils.FORMAT_SHORT_TIME) + "/" +
                        hs.getUserId() + "/" + hs.getShareCode();
            }else{
                filePath =  SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "heatAnalysis/" +
                        DateUtils.format(hs.getCreateTime(), DateUtils.FORMAT_SHORT_TIME) + "/" +
                        hs.getUserId() + "/" + hs.getShareCode();
            }
            log.info("getShareData {}:{}",shareCode,filePath);
            if( hs != null &&  net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotBlank(hs.getAnalysisTaskTicket())){
                //走任务 7天以外的
                view = com.miduchina.wrd.util.CommonUtils.getFileDataToObject(fileName, filePath, className);
            }
        }
        return view;
    }

    @Override
    public ModelDto getInfoFromV2(HttpServletRequest request, UserDto user) {

        ModelDto dto = new ModelDto(0);

        Map<String, Object> result = new HashMap<String, Object>();
        Map<Integer, Object> echartmap = new HashMap<>();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        String json = null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_PROPAGATION_ANALYSIS + SystemConstants.JEDIS_KEYS_MEDIAFROM,
                true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setOrder(Params.ORDER_KEY);
            p.setSort(Params.SORT_ASC + "");
            p.setStatField(Params.STATFIELD_ORIGINTYPE);

            StatView sv = null;
            if (org.apache.commons.lang3.StringUtils.isNotBlank(shareCode)) {
                sv= getShareData("yearHotOrigin", StatView.class,shareCode);
            }
            if (sv == null){
                try {
                    sv = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request, user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Map map = new HashMap<String, String>();
            double d = 0d;
            Integer sum = 0;
            Integer wzSum = 0;
            int hh = 0;
            int kk = 0;
            if (sv!= null && "0000".equals(sv.getCode())) {
                List<com.xd.tools.pojo.Stat> statList = sv.getStatList();
                if (CollectionUtils.isNotEmpty(statList)) {
                    for (int j = 0; j < statList.size(); j++) {
                        com.xd.tools.pojo.Stat stat = statList.get(j);
                        if ("微博".equals(stat.getName()) || "客户端".equals(stat.getName()) || "微信".equals(stat.getName())
                                || "论坛".equals(stat.getName())) {
                            map.put(stat.getName(), stat.getNum() + "");
                            sum += (int)stat.getNum();
                        } else {
                            sum += (int)stat.getNum();
                            wzSum += (int)stat.getNum();
                        }
                    }
                    RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                } else {
                    result.put("sb", null);
                }
                StringBuilder sb = new StringBuilder();
                if (sum == 0 || wzSum == 0) {
                    hh += 1;
                    sb.append("{\"wangzhan\":\"0\",");
                } else {
                    sb.append("{\"wangzhan\":\"" + wzSum + "\",");
                }
                sb.append("\"title\":\"" + searchKeyword + "\",");
                for (Object object : map.keySet()) {
                    Object str = map.get(object);
                    if ("微博".equals(object)) {
                        sb.append("\"weibo\":\"" + str + "\",");
                    }
                    if ("客户端".equals(object)) {
                        sb.append("\"kehuduan\":\"" + str + "\",");
                    }
                    if ("微信".equals(object)) {
                        sb.append("\"weixin\":\"" + str + "\",");
                    }
                    if ("论坛".equals(object)) {
                        sb.append("\"luntan\":\"" + str + "\",");
                    }
                    if (str.equals("0")) {
                        kk+=1;
                    }
                }
                System.out.println(kk);

                if (sb.toString().indexOf("weibo") < 0) {
                    sb.append("\"weibo\":\"0\",");
                    hh += 1;
                }
                if (sb.toString().indexOf("kehuduan") < 0) {
                    sb.append("\"kehuduan\":\"0\",");
                    hh += 1;
                }
                if (sb.toString().indexOf("weixin") < 0) {
                    sb.append("\"weixin\":\"0\",");
                    hh += 1;
                }
                if (sb.toString().indexOf("luntan") < 0) {
                    sb.append("\"luntan\":\"0\",");
                    hh += 1;
                }
                if (hh == 5 || kk ==4) {
                    /*result.put("sb", null);*/
                    json = JSONObject.toJSONString(result);
                } else {
                    result.put("sb", sb.toString().substring(0, sb.length() - 1) + "}");
                    json = JSONObject.toJSONString(result);
                }
                //JedisUtil.setAttribute(sessionName, json, Constants.OPEN_TOOLS_SESSION_TIME);
            }
            if (sv != null) {
                if (CollectionUtils.isNotEmpty(sv.getStatList())) {
                    echartmap.put(0, result);
                }
            }
        } else {
            CommonUtils.jsonToMap(result, json);
        }
        if (MapUtils.isNotEmpty(result)) {
            return new ModelDto(1, echartmap);
        } else {
            return dto;
        }
    }

    @Override
    public ModelDto getActiveMediaV2(HttpServletRequest request, UserDto user) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<Integer, Object> echartmap = new HashMap<>();
        String json = null;
        String[] jsons = null;

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }


        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(searchKeyword)) {
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_PROPAGATION_ANALYSIS + SystemConstants.JEDIS_KEYS_LIVEMEDIA,
                true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_CAPTUREWEBSITENAME);
            p.setStatCount(8);

            StatView sv = null;
            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearLiveMedia", StatView.class,shareCode);
            }
            if (sv == null){
                try {
                    sv = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request, user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Map map = new HashMap<String, String>();
            if (sv!= null && "0000".equals(sv.getCode())) {
                List<com.xd.tools.pojo.Stat> statList = sv.getStatList();
                if (CollectionUtils.isNotEmpty(statList)) {
                    jsons = new String[statList.size()];
                    for (int j = 0; j < statList.size(); j++) {
                        if(j<8){
                            com.xd.tools.pojo.Stat stat = statList.get(j);
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("{");
                            sb2.append("\"name\":\"" + stat.getName() + "\",");
                            sb2.append("\"count\":\"" + stat.getNum() + "\"}");
                            jsons[j] = sb2.toString();
                        }
                    }
                    result.put("sb", jsons);
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                } else {
                    result.put("sb", null);
                    json = JSONObject.toJSONString(result);
                }
            } else {
                result.put("sb", null);
                json = JSONObject.toJSONString(result);
            }
            if (sv != null) {
                if (CollectionUtils.isNotEmpty(sv.getStatList())) {
                    echartmap.put(0, result);
                }
            }
        } else {
            CommonUtils.jsonToMap(result, json);
        }
        if (MapUtils.isNotEmpty(echartmap)) {
            return new ModelDto(1, echartmap);
        } else {
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getVolumeMapV3(HttpServletRequest request, UserDto user) {

        Map<Integer, List<com.xd.tools.pojo.Stat>> echartmap = new HashMap<>();
        String json = null;

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");


        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(searchKeyword)) {
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        Params p = new Params();
        p.setIsDelay(-1);
        p.setDate(date);
        p.setOrder(Params.ORDER_KEY);
        p.setSort(Params.SORT_ASC + "");
        p.setStatField(Params.STATFIELD_PUBLISHEDHOUR);
        p.setKeyword(keyword1);

        String jsonStr = "";
        StatView sv = null;
        String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_PROPAGATION_ANALYSIS + SystemConstants.JEDIS_KEYS_VOLUMEPIG,
                true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isNotEmpty(json)) {
            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearHotVolumeDay", StatView.class,shareCode);
            }else{
                sv = JSON.parseObject(json, StatView.class);
            }
        }
        if (sv == null){
            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearHotVolumeDay", StatView.class,shareCode);
            }else{
                try {
                    sv = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request, user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            jsonStr = JSON.toJSONString(sv);
            if (StringUtils.isBlank(jsonStr)) {
               return new ModelDto(0);
            }
        }
        CommonUtils.initTime(date, startTime, endTime, endDate, startDate);
        if (sv != null) {
            if (CollectionUtils.isNotEmpty(sv.getStatList())) {
                if (CollectionUtils.isNotEmpty(sv.getStatList())){
                    String timeNode = sv.getStatList().get(0).getKey();
                    timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                    List<String> dates = CommonUtils.getTimeAxis(startDate, endDate, timeNodeType);
                    int j = 0;
                    for (int t = 0; t < dates.size(); t++) {
                        String d = dates.get(t);
                        if (j < sv.getStatList().size()) {
                            double total = sv.getStatList().get(j).getTotal();
                            String ftot = df.format(total);
                            float num = Float.parseFloat(ftot);
                            if (sv.getStatList().get(j).getName().indexOf(d) >= 0) {//存在日期
                                j++;
                            } else { // 缺少日期补0
                                num = 0;
                                com.xd.tools.pojo.Stat statStatList=new com.xd.tools.pojo.Stat();
                                statStatList.setKey(d);
                                statStatList.setNum(0);
                                sv.getStatList().add(statStatList);
                            }
                        } else {
                            com.xd.tools.pojo.Stat statStatList=new com.xd.tools.pojo.Stat();
                            statStatList.setKey(d);
                            statStatList.setNum(0);
                            sv.getStatList().add(statStatList);
                        }
                    }
                    Collections.sort(sv.getStatList(), new Comparator<com.xd.tools.pojo.Stat>() {
                        @Override
                        public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                            return o1.getKey().compareTo(o2.getKey());
                        }
                    });
                }


                echartmap.put(0, sv.getStatList());
                jsonStr = JSON.toJSONString(sv);
                if(sv != null && "0000".equals(sv.getCode())){
                    RedisUtils.setAttribute(sessionName, jsonStr, SystemConstants.DEFAULT_SESSION_TIME);
                }
            }
        }
        if (MapUtils.isNotEmpty(echartmap)) {
            return new ModelDto(1, echartmap);
        } else {
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getVirtualSolutionV3(HttpServletRequest request, UserDto user) {
        Map<Integer, List<com.xd.tools.pojo.Stat>> echartmap = new HashMap<>();
        String json = null;

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(searchKeyword)) {
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        Params p = new Params();
        p.setIsDelay(-1);
        p.setDate(date);
        p.setStatField(Params.STATFIELD_PROVINCE);
        p.setKeyword(keyword1);

        String jsonStr = "";
        StatView sv = null;
        String sessionName =CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_PROPAGATION_ANALYSIS + SystemConstants.JEDIS_KEYS_GOCHINA,
                true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isNotEmpty(json)) {
            sv = JSON.parseObject(json, StatView.class);
        } else {
            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearHotProvince", StatView.class,shareCode);
            }
            if (sv == null){
                try {
                    sv = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request, user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(JSONObject.toJSONString(e));
                }
            }
            jsonStr = JSON.toJSONString(sv);
            if (StringUtils.isBlank(jsonStr)) {
                return new ModelDto(0);
            }
        }
        if (sv != null) {
            if (CollectionUtils.isNotEmpty(sv.getStatList())) {
                echartmap.put(0, sv.getStatList());
                if(sv != null && "0000".equals(sv.getCode())){
                    RedisUtils.setAttribute(sessionName, jsonStr, SystemConstants.DEFAULT_SESSION_TIME);
                }
            }
        }
        if (MapUtils.isNotEmpty(echartmap)) {
           return new ModelDto(1, echartmap);
        } else {
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getHotPeopleV2(HttpServletRequest request, UserDto user) {
        Map<String, Object> result = new HashMap<String, Object>();
        Map<Integer, Object> echartmap = new HashMap<>();

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }

        String[] jsons = null;
        String json = null;

        List<Keywords> keywordList = new ArrayList<Keywords>();
        if (StringUtils.isNotBlank(searchKeyword)) {
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_HOTPEOPLE, false,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params pms = new Params();
            pms.setIsDelay(-1);
            pms.setPage(1);
            pms.setPagesize(8);
            pms.setComblineField(Params.COMBLINE_FIELD_AUTHOR);
            pms.setComblineflg(Params.COMBLINEFLG_MERGE);
            pms.setOrder(Params.ORDER_SIMILAR);
            pms.setSort(Params.SORT_DESC + "");
            pms.setStatField(Params.STATFIELD_AUTHOR);
            pms.setDate(date);
            pms.setKeyword(keyword1);
            pms.setOrigin(String.valueOf(Params.ORIGIN_WB));
            pms.setIsRoot(Params.ISROOT_FORWARD);


            com.xd.tools.view.IContentCommonNetView view = null;
            if (StringUtils.isNotBlank(shareCode)) {
                view = getShareData("yearHotPeople", com.xd.tools.view.IContentCommonNetView.class,shareCode);
            }
            if (view == null){
                try {
                    view = WyqH5MethodV1.searchListV1_001(WyqDataConfig.getUserTag(request, user), pms);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(JSONObject.toJSONString(e));
                }
            }
            if (view != null && CollectionUtils.isNotEmpty(view.getIContentCommonNetList())) {
                if (view.getIContentCommonNetList().size() >= 8) {
                    jsons = new String[8];
                } else {
                    jsons = new String[view.getIContentCommonNetList().size()];
                }
                for (int j = 0; j < view.getIContentCommonNetList().size(); j++) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("{");
                    IContentCommonNet content = view.getIContentCommonNetList().get(j);
                    sb2.append("\"headImg\":\"" + content.getProfileImageUrl() + "\",");
                    sb2.append("\"verifiedType\":\"" + content.getVerifiedType() + "\",");
                    sb2.append("\"fansNumber\":\"" + content.getFansNumber() + "\",");
                    sb2.append("\"originAuthorId\":\"https://weibo.com/u/" + content.getOriginAuthorId() + "\",");
                    sb2.append("\"name\":\"" + content.getAuthor() + "\",");
                    sb2.append("\"count\":\"" + content.getRepeatNum() + "\"}");
                    jsons[j] = sb2.toString();
                }
                result.put("jsons", jsons);
                json = JSONObject.toJSONString(result);


            } else {
                result.put("jsons", null);
                json = JSONObject.toJSONString(result);
            }
            if (view != null) {
                if (CollectionUtils.isNotEmpty(view.getIContentCommonNetList())) {
                    echartmap.put(0, result);
                    if(view != null && "0000".equals(view.getCode())){
                        RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                    }
                }
            }
        } else {
            CommonUtils.jsonToMap(result, echartmap, json);
        }
        if (MapUtils.isNotEmpty(echartmap)) {
            return new ModelDto(1, echartmap);
        } else {
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getMediaFriendV3(HttpServletRequest request, UserDto user) {

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        Map<Integer,Stats> echartMediaFriend = new HashMap<>();
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        Params p = new Params();
        p.setIsDelay(-1);
        p.setDate(date);
        p.setOrder(Params.ORDER_KEY);
        p.setSort(Params.SORT_ASC+"");
        p.setStatField(Params.STATFIELD_ORIGINTYPE + "," + Params.STATFIELD_CUSTOMFLAG1);
        p.setKeyword(keyword1);
        p.setFilterKeyword(filterKeyword1);

        String jsonStr = "";
        com.xd.tools.view.StatsView sv = null;
        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FRIENDLY, true,getSessionNameMap(request));
        String json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isNotEmpty(json)) {
            sv = JSON.parseObject(json, com.xd.tools.view.StatsView.class);
        }
        if (sv == null){
            if (StringUtils.isNotBlank(shareCode)) {
                sv = getShareData("yearHotMediafriend", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (sv == null) {
                try {
                    sv = WyqH5MethodV1.statStackedV1_001(WyqDataConfig.getUserTag(request, user), p);
                }catch (Exception e){
                    log.info(JSONObject.toJSONString(e));
                }
            }
        }

        jsonStr = JSON.toJSONString(sv);
        if(StringUtils.isBlank(jsonStr)){
           return new ModelDto(0);
        }

        if(sv != null) {
            if(CollectionUtils.isNotEmpty(sv.getStatsList())) {
                if(sv != null && "0000".equals(sv.getCode())){
                    RedisUtils.setAttribute(sessionName, jsonStr,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
                double mgSum = 0;
                double fmgSum = 0;
                double wzMgSum = 0;
                double wzFmgSum = 0;
                Stats yhd = new Stats();
                yhd.setKey("yhd");
                yhd.setName("友好度");
                List<com.xd.tools.pojo.Stat> list = new ArrayList<>();
                com.xd.tools.pojo.Stat newStat = null;
                com.xd.tools.pojo.Stat wzStat = new com.xd.tools.pojo.Stat();
                wzStat.setName("网站");
                wzStat.setPercent(0d);
                com.xd.tools.pojo.Stat wbStat = new com.xd.tools.pojo.Stat();
                wbStat.setName("微博");
                wbStat.setPercent(0d);
                com.xd.tools.pojo.Stat appStat = new com.xd.tools.pojo.Stat();
                appStat.setName("客户端");
                appStat.setPercent(0d);
                com.xd.tools.pojo.Stat ltStat = new com.xd.tools.pojo.Stat();
                ltStat.setName("论坛");
                ltStat.setPercent(0d);
                com.xd.tools.pojo.Stat wxStat = new com.xd.tools.pojo.Stat();
                wxStat.setName("微信");
                wxStat.setPercent(0d);
                for (int j = 0; j < sv.getStatsList().size(); j++) {
                    Stats ss = sv.getStatsList().get(j);
                    List<com.xd.tools.pojo.Stat> statList = ss.getStatList();
                    if(wzStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wzStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(wbStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wbStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(appStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                appStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(ltStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                ltStat.setPercent(s.getPercent());
                            }
                        }
                    }else if(wxStat.getName().equals(ss.getName())) {
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wxStat.setPercent(s.getPercent());
                            }
                        }
                    }else{
                        for (int l = 0; l < statList.size(); l++) {
                            com.xd.tools.pojo.Stat s = statList.get(l);
                            if("敏感".equals(s.getName())) {
                                mgSum+=s.getNum();
                                wzMgSum+=s.getNum();
                            }else if("非敏感".equals(s.getName())) {
                                fmgSum+=s.getNum();
                                wzFmgSum+=s.getNum();
                            }
                        }
                    }
                }
                double wzPercent = 0;
                double percent = 0;
                if(wzFmgSum+wzMgSum != 0) {
                    wzPercent = wzFmgSum/(wzFmgSum+wzMgSum)*100;
                    wzStat.setPercent(wzPercent);
                }
                list.add(wzStat);
                list.add(wbStat);
                list.add(appStat);
                list.add(ltStat);
                list.add(wxStat);
                percent = fmgSum/(fmgSum+mgSum)*100;
                yhd.setPercent(percent);

                Collections.sort(list, new Comparator() {

                    @Override
                    public int compare(Object o1, Object o2) {
                        com.xd.tools.pojo.Stat s1 = (com.xd.tools.pojo.Stat) o1;
                        com.xd.tools.pojo.Stat s2 = (com.xd.tools.pojo.Stat) o2;
                        // TODO Auto-generated method stub
                        return s2.getPercent()>s1.getPercent() ? 1:-1;
                    }

                });
                yhd.setStatList(list);
                echartMediaFriend.put(0,yhd);
            }
        }
        if(MapUtils.isNotEmpty(echartMediaFriend)){
            return new ModelDto(1,echartMediaFriend);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionSexV2(HttpServletRequest request, UserDto user) {

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");


        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_SEX_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            com.xd.tools.view.StatsView statView=null;
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_USERGENDER + "," + Params.STATFIELD_CUSTOMFLAG1);
            p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
            p.setSort(Params.SORT_DESC + "," + Params.SORT_ASC);
            if (StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearMoodSix", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(WyqDataConfig.getUserTag(request,user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(JSONObject.toJSONString(e));
                }
            }
            try {
                if (statView != null) {
                    if (CollectionUtils.isNotEmpty(statView.getStatsList())) {
                        double[] zm = new double[2];
                        double[] fm = new double[2];
                        double[] total = new double[2];
                        for (Stats stats : statView.getStatsList()) {
                            String sex = stats.getName(); // m:男 f:女
                            int index = 0;
                            if ("f".equals(sex)) {
                                index = 1;
                            }
                            for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                if ("敏感".equals(stat.getName()))
                                    fm[index] = stat.getNum();
                                else
                                    zm[index] = stat.getNum();
                                total[index]  += stat.getNum();
                            }
                        }
                        for(int j=0; j<2; j++){
                            if(total[0] != 0){
                                zm[j] = 100*zm[j]/total[j];
                                fm[j] = 100*fm[j]/total[j];
                            }else{
                                zm[j] = 0;
                                fm[j] = 0;
                            }
                        }
                        result.put("zm", zm);
                        result.put("fm", fm);
                        json = JSONObject.toJSONString(result);
                        RedisUtils.setAttribute(sessionName, json,
                                SystemConstants.DEFAULT_SESSION_TIME);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionTypeV2(HttpServletRequest request, UserDto user) {

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        com.xd.tools.view.StatsView statView = null;
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        Params p = new Params();
        p.setIsDelay(-1);
        p.setDate(date);
        p.setKeyword(keyword1);
        p.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_USERVERIFIED);
        p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
        p.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);

        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_USER_TYPE_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            if (StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearUserAuthenType", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(WyqDataConfig.getUserTag(request,user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(JSONObject.toJSONString(e));
                }
            }
            try {
                if (statView != null) {
                    if (CollectionUtils.isNotEmpty(statView.getStatsList())) {
                        List<Stats> statsList = statView.getStatsList();
                        String[] legend = {"普通用户", "橙V用户", "蓝V用户", "达人"};
                        double[] zm = { 0d, 0d, 0d, 0d }; // '普通用户', '橙V用户', '蓝V用户', '达人'
                        double[] fm = { 0d, 0d, 0d, 0d };
                        double[] total = { 0d, 0d, 0d, 0d };
                        if (CollectionUtils.isNotEmpty(statsList)) {
                            for (Stats stats : statsList) {
                                if ("敏感".equals(stats.getName())) {
                                    for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                        String key = stat.getKey();
                                        Integer num = (int) stat.getNum();
                                        if (num == null)
                                            num = 0;
                                        System.out.println(key);
                                        if (StringUtils.isNotEmpty(key)) {
                                            if (Params.VERIFIED_NORMAL.equals(key)) { // 普通用户
                                                fm[0] += num;
                                                total[0] += num;
                                            } else if (Params.VERIFIED_ORANGE_V.equals(key)) { // 橙V用户
                                                fm[1] += num;
                                                total[1] += num;
                                            } else if (Params.VERIFIED_MASTER.contains(key)) { // 达人用户
                                                fm[3] += num;
                                                total[3] += num;
                                            } else if (Params.VERIFIED_BLUE_V.contains(key)) { // 蓝V用户
                                                fm[2] += num;
                                                total[2] += num;
                                            }
                                        }
                                    }
                                } else if ("非敏感".equals(stats.getName())) {
                                    for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                        String key = stat.getKey();
                                        Integer num = (int) stat.getNum();
                                        if (num == null)
                                            num = 0;
                                        if (StringUtils.isNotEmpty(key)) {
                                            if (Params.VERIFIED_NORMAL.equals(key)) { // 普通用户
                                                zm[0] += num;
                                                total[0] += num;
                                            } else if (Params.VERIFIED_ORANGE_V.equals(key)) { // 橙V用户
                                                zm[1] += num;
                                                total[1] += num;
                                            } else if (Params.VERIFIED_MASTER.contains(key)) { // 达人用户
                                                zm[3] += num;
                                                total[3] += num;
                                            } else if (Params.VERIFIED_BLUE_V.contains(key)) { // 蓝V用户
                                                zm[2] += num;
                                                total[2] += num;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for(int j=0; j<4; j++){
                            if(total[j] != 0d){
                                zm[j] = 100*zm[j]/total[j];
                                fm[j] = 100*fm[j]/total[j];
                            }else{
                                zm[j] = 0;
                                fm[j] = 0;
                            }
                        }
                        result.put("legend", legend);
                        result.put("zm", zm);
                        result.put("fm", fm);
                        json = JSONObject.toJSONString(result);
                        if(statView != null && "0000".equals(statView.getCode())){
                            RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
           return  new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionFansV2(HttpServletRequest request, UserDto user) {
        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }

        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FANS_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_CUSTOMFLAG1);
            try {
                Integer[] zm = { 0, 0, 0, 0, 0, 0, 0, 0 };
                Integer[] fm = { 0, 0, 0, 0, 0, 0, 0, 0 };
                String[] xAxis = new String[8];
                int j = 0;
                boolean isLoading =  false;
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView view = getShareData("yearUserFuns", com.xd.tools.view.StatsView.class,shareCode);
                    Map<String, Integer> indeMap = new HashMap<String, Integer>();
                    for (Range range : sortList) {
                        xAxis[j] = range.getName();
                        indeMap.put(xAxis[j], j);
                        j++;
                    }
                    if(view != null){
                        List<Stats> statsList = view.getStatsList();
                        if(null !=statsList && statsList.size()>0){
                            for(Stats stats : statsList){
                                if ("敏感".equals(stats.getName())) {
                                    for(com.xd.tools.pojo.Stat stat : stats.getStatList()){
                                        fm[indeMap.get(stat.getName())] = stat.getCount().intValue();
                                    }
                                } else if ("非敏感".equals(stats.getName())) {
                                    for(com.xd.tools.pojo.Stat stat : stats.getStatList()){
                                        zm[indeMap.get(stat.getName())] = stat.getCount().intValue();
                                    }
                                }
                            }
                        }
                    }else {
                        isLoading = true;
                    }
                }
                if (isLoading){
                    j = 0;
                    for (Range range : sortList) {
                        xAxis[j] = range.getName();
                        p.setFollowersCountStart(range.getFrom());
                        p.setFollowersCountEnd(range.getTo());

                        StatView statView = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request,user), p);

                        if (statView != null) {
                            if (CollectionUtils.isNotEmpty(statView.getStatList())) {
                                for (com.xd.tools.pojo.Stat stat : statView.getStatList()) {
                                    if ("敏感".equals(stat.getName())) {
                                        fm[j] = (int) stat.getNum();
                                    } else {
                                        zm[j] = (int) stat.getNum();
                                    }
                                }
                            }
                        }
                        j++;
                    }
                }
                Integer[] zm2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
                Integer[] fm2 = { 0, 0, 0, 0, 0, 0, 0, 0 };
                boolean equals = Arrays.equals(zm, zm2);
                boolean equals2 = Arrays.equals(fm,fm2);
                if (!(equals&&equals2)) {
                    result.put("zm", zm);
                    result.put("fm", fm);
                    result.put("xAxis", xAxis);
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionLevelV2(HttpServletRequest request, UserDto user) {

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_RELAY_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
            com.xd.tools.view.StatsView statView = null;
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_CUSTOMFLAG1 + "," + Params.STATFIELD_LEVEL);
            p.setOrder(Params.ORDER_KEY + "," + Params.ORDER_KEY);
            p.setSort(Params.SORT_DESC + "," + Params.SORT_DESC);
            p.setStatCount(1000);
            if (org.apache.commons.lang3.StringUtils.isNotBlank(shareCode)) {
                statView = getShareData("yearForwardHierarchy", com.xd.tools.view.StatsView.class,shareCode);
            }
            if (statView == null){
                try {
                    statView = WyqH5MethodV1.statStackedV1_001(WyqDataConfig.getUserTag(request,user), p);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(JSONObject.toJSONString(e));
                }
            }
            try {
                List<Stats> statsList = statView.getStatsList();
                long[] zm = { 0l, 0l, 0l, 0l };
                long[] fm = { 0l, 0l, 0l, 0l };
                if (CollectionUtils.isNotEmpty(statsList)) {
                    for (Stats stats : statsList) {
                        if(CollectionUtils.isNotEmpty(stats.getStatList())){
                            if ("敏感".equals(stats.getName())) {
                                for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                    Integer level = Integer.valueOf(stat.getKey());
                                    int num = (int) stat.getNum();
                                    if (level == 1) {
                                        fm[0] = num;
                                    } else if (level == 2) {
                                        fm[1] = num;
                                    } else if (level == 3) {
                                        fm[2] = num;
                                    } else if (level >= 4) {
                                        fm[3] += num;
                                    }
                                }
                            } else if ("非敏感".equals(stats.getName())) {
                                for (com.xd.tools.pojo.Stat stat : stats.getStatList()) {
                                    Integer level = Integer.valueOf(stat.getKey());
                                    int num = (int) stat.getNum();
                                    if (level == 1) {
                                        zm[0] = num;
                                    } else if (level == 2) {
                                        zm[1] = num;
                                    } else if (level == 3) {
                                        zm[2] = num;
                                    } else if (level >= 4) {
                                        zm[3] += num;
                                    }
                                }
                            }
                        }
                    }
                }
                String[] name = {"一次转发", "二次转发", "三次转发", "四次+"};
                result.put("name", name);
                result.put("zm", zm);
                result.put("fm", fm);
                json = JSONObject.toJSONString(result);
                if(statView != null && "0000".equals(statView.getCode())){
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getEmotionHobbyV2(HttpServletRequest request, UserDto user) {
        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_INTEREST_OPTIONS, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setStatField(Params.STATFIELD_USERTAG);
            p.setPage(1);
            p.setPagesize(10);
            int j = 0;
            List<ECharts> zm = new ArrayList<ECharts>();
            long zmMax = 0l;
            List<ECharts> fm = new ArrayList<ECharts>();
            long fmMax = 0l;
            StatView view=null;
            try {
                boolean isLoding = false;
                if (org.apache.commons.lang3.StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearInterestLabel", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                        for(Stats stats : statsView.getStatsList()){
                            j = 0;
                            List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                            Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                @Override
                                public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                    return (int) (o2.getNum() - o1.getNum());
                                }
                            });
                            if("敏感".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (j >= 9){
                                        break;
                                    }
                                    long num = stat.getNum();
                                    if (num > fmMax)
                                        fmMax = num;
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    fm.add(data);
                                    j++;
                                }
                            }else if("非敏感".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (j >= 9){
                                        break;
                                    }
                                    long num = stat.getNum();
                                    if (num > zmMax)
                                        zmMax = num;
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    zm.add(data);
                                    j++;
                                }
                            }
                        }
                    }else {
                        isLoding = true;
                    }
                }
                if (isLoding){
                    p.setOptions(Params.OPTIONS_NONSENSITIVE);
                    view = WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request,user), p);
                    if (view != null && view.getCode() != null
                            && view.getCode().equals(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS)) {
                        List<com.xd.tools.pojo.Stat> statList = view.getStatList();
                        for (com.xd.tools.pojo.Stat stat : statList) {
                            long num = stat.getNum();
                            if (num > zmMax)
                                zmMax = num;
                            ECharts data = new ECharts();
                            data.setName(stat.getName());
                            data.setValue(num);
                            zm.add(data);
                            j++;
                        }
                        if (j > 9)
                            zm = zm.subList(0, 9);
                    }
                    // 负面
                    j = 0;
                    p.setOptions(Params.OPTIONS_SENSITIVE);
                    view= WyqH5MethodV1.statV1_001(WyqDataConfig.getUserTag(request,user), p);
                    if (view != null && view.getCode() != null
                            && view.getCode().equals(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS)) {
                        List<com.xd.tools.pojo.Stat> statList = view.getStatList();
                        for (com.xd.tools.pojo.Stat stat : statList) {
                            long num = stat.getNum();
                            if (num > fmMax)
                                fmMax = num;
                            ECharts data = new ECharts();
                            data.setName(stat.getName());
                            data.setValue(num);
                            fm.add(data);
                            j++;
                        }
                        if (j > 9)
                            fm = fm.subList(0, 9);
                    }
                }

                result.put("zm", zm);
                result.put("fm", fm);
                result.put("zmMax", zmMax);
                result.put("fmMax", fmMax);
                json = JSONObject.toJSONString(result);
//                 JedisUtil.setAttribute(sessionName, json,
//							Constants.OPEN_TOOLS_SESSION_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                RedisUtils.setAttribute(sessionName, json,
                        SystemConstants.DEFAULT_SESSION_TIME);
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getZMHotWordV2(HttpServletRequest request, UserDto user) {
        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName =CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_ZM_WORD, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setOrder(Params.ORDER_RECOMMEND);
            p.setSort(String.valueOf(Params.SORT_DESC));
            p.setPage(1);
            p.setPagesize(200);

            CommAnalysisWordCloud cloudWord = new CommAnalysisWordCloud();
            cloudWord.setNum(9);
            List<ECharts> nonsensitiveValueList = new ArrayList<ECharts>();
            try {
                boolean isLoding =false;
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearMouthWords", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                        for(Stats stats : statsView.getStatsList()){
                            int i = 0;
                            List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                            Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                @Override
                                public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                    return (int) (o2.getNum() - o1.getNum());
                                }
                            });
                            if("正面口碑热词".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (i >= 9)
                                        break;
                                    long num = stat.getNum();
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    nonsensitiveValueList.add(data);
                                    i++;
                                }
                            }
                        }
                    }else {
                        isLoding = true;
                    }
                }
                if (isLoding){
                    StatView statView = null;
                    p.setOptions(Params.OPTIONS_NONSENSITIVE);
                    cloudWord.setType("2");
                    statView = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(WyqDataConfig.getUserTag(request,user), p, cloudWord);
                    if (statView!=null) {
                        List<com.xd.tools.pojo.Stat> statList = statView.getStatList();
                        Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                            @Override
                            public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                return (int) (o2.getNum() - o1.getNum());
                            }
                        });
                        if(CollectionUtils.isNotEmpty(statList)){
                            for (com.xd.tools.pojo.Stat stat : statList) {
                                ECharts data = new ECharts();
                                data.setName(stat.getName());
                                data.setValue(stat.getNum());
                                nonsensitiveValueList.add(data);
                            }
                        }
                    }
                    if (nonsensitiveValueList != null && nonsensitiveValueList.size() > 9) {
                        nonsensitiveValueList = nonsensitiveValueList.subList(0, 9);
                    }
                }
                result.put("zmWords", nonsensitiveValueList);
                if(null !=nonsensitiveValueList && nonsensitiveValueList.size()>0){
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
                json = JSONObject.toJSONString(result);
//                    JedisUtil.setAttribute(sessionName, json,
//							Constants.OPEN_TOOLS_SESSION_TIME * 6);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return  new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getFMHotWordV2(HttpServletRequest request, UserDto user) {

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        HashMap<Integer,Object> r = new HashMap<>();
        HashMap<String,Object> result = new HashMap<>();
        String json=null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        String sessionName = CommonUtils.getSessionName(
                SystemConstants.JEDIS_KEYS_PRAISE_ANALYSIS
                        + SystemConstants.JEDIS_KEYS_FM_WORD, true,getSessionNameMap(request));
        json = RedisUtils.getAttribute(sessionName);
        if (StringUtils.isEmpty(json)) {
            Params p = new Params();
            p.setIsDelay(-1);
            p.setDate(date);
            p.setKeyword(keyword1);
            p.setOrder(Params.ORDER_RECOMMEND);
            p.setSort(String.valueOf(Params.SORT_DESC));
            p.setPage(1);
            p.setPagesize(200);

            CommAnalysisWordCloud cloudWord = new CommAnalysisWordCloud();
            cloudWord.setNum(9);
            List<ECharts> sensitiveValueList = new ArrayList<ECharts>();
            try {
                boolean isLoading = false;
                if (StringUtils.isNotBlank(shareCode)) {
                    com.xd.tools.view.StatsView statsView = getShareData("yearMouthWords", com.xd.tools.view.StatsView.class,shareCode);
                    if(statsView != null && CollectionUtils.isNotEmpty(statsView.getStatsList())){
                        for(Stats stats : statsView.getStatsList()){
                            int i = 0;
                            List<com.xd.tools.pojo.Stat> statList = stats.getStatList();
                            Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                                @Override
                                public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                    return (int) (o2.getNum() - o1.getNum());
                                }
                            });
                            if("负面口碑热词".equals(stats.getName())){
                                for(com.xd.tools.pojo.Stat stat : statList){
                                    if (i >= 9)
                                        break;
                                    long num = stat.getNum();
                                    ECharts data = new ECharts();
                                    data.setName(stat.getName());
                                    data.setValue(num);
                                    sensitiveValueList.add(data);
                                    i++;
                                }
                            }
                        }
                    }else {
                        isLoading = true;
                    }
                }
                if (isLoading){
                    StatView statView = null;
                    p.setOptions(Params.OPTIONS_SENSITIVE);
                    cloudWord.setType("1");
                    statView = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(WyqDataConfig.getUserTag(request,user), p, cloudWord);
                    if (statView!=null) {
                        List<com.xd.tools.pojo.Stat> statList = statView.getStatList();
                        Collections.sort(statList, new Comparator<com.xd.tools.pojo.Stat>() {
                            @Override
                            public int compare(com.xd.tools.pojo.Stat o1, com.xd.tools.pojo.Stat o2) {
                                return (int) (o2.getNum() - o1.getNum());
                            }
                        });
                        if(CollectionUtils.isNotEmpty(statList)){
                            for (com.xd.tools.pojo.Stat stat : statList) {
                                ECharts data = new ECharts();
                                data.setName(stat.getName());
                                data.setValue(stat.getNum());
                                sensitiveValueList.add(data);
                            }
                        }
                    }
                    if (sensitiveValueList != null && sensitiveValueList.size() > 9) {
                        sensitiveValueList = sensitiveValueList.subList(0, 9);
                    }
                }

                if(null !=sensitiveValueList && sensitiveValueList.size()>0){
                    result.put("fmWords", sensitiveValueList);
                    json = JSONObject.toJSONString(result);
                    RedisUtils.setAttribute(sessionName, json,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
                json = JSONObject.toJSONString(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(MapUtils.isNotEmpty(result)) {
                r.put(0,result);
            }
        }else {
            CommonUtils.jsonToMap(r, result, json);
        }
        if(MapUtils.isNotEmpty(result)){
            return  new ModelDto(1,r);
        }else{
            return new ModelDto(0);
        }
    }


    @Override
    public ModelDto getStatAndLineNewV4(HttpServletRequest request, UserDto user){

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");

        String keyword2 = request.getParameter("keyword2");
        String searchKeyword2 = request.getParameter("searchKeyword2");
        String filterKeyword2 = request.getParameter("filterKeyword2");



        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        List<CategoryTypeKeywords> lists = new ArrayList<>();
        Map<String,Object> echartmap = new HashMap<>();
        HashMap<Integer, StatStatLists> echartMap2 = new HashMap<Integer, StatStatLists>();
        String json =null;
        List<Keywords> keywordList = new ArrayList<Keywords>();
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        if(StringUtils.isNotBlank(searchKeyword2)){
            Keywords k2 = new Keywords();
            k2.setTitle1(searchKeyword2);
            k2.setKeyword1(keyword2);
            k2.setFilterKeyword1(filterKeyword2);
            keywordList.add(k2);
        }

        String ratioCustom="0";
        if(CollectionUtils.isNotEmpty(keywordList)) {
            for (int i = 0; i < keywordList.size(); i++) {
                Keywords k = keywordList.get(i);
                String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH
                        + SystemConstants.JEDIS_KEYS_HOTTABLE, false,getSessionNameMap(request));
                json = RedisUtils.getAttribute(sessionName);
                CtksStatsView csv = null;
                CtksStatStatListsView csView = null;
                /**
                 * 分享
                 */
                if(StringUtils.isBlank(json)&&StringUtils.isNotBlank(shareCode)){
                    csView=getShareData("yearHotStatAndLine", CtksStatStatListsView.class,shareCode);
                    CommonUtils.initTime(date, startTime, endTime, endDate, startDate);
                    if(csView != null) {
                        List<CategoryTypeKeywords> ctkList = csView.getCtksStatStatLists().getCtkList();
                        List<StatStatLists> statsList = csView.getCtksStatStatLists().getStatStatListsList();
                        List<StatStatList> lineData=csView.getCtksStatStatLists().getStatStatListsList().get(0).getStatStatListList();
                        if (CollectionUtils.isNotEmpty(ctkList)) {
                            for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                StringBuilder sb = new StringBuilder();
                                CategoryTypeKeywords ctk = ctkList.get(i1);
                                String f1 = df.format(ctk.getRatioHotCustom());
                                double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                f2 = f2 * 100;
                                int f4 = (int) f2;
                                String f3 = df.format(ctk.getDifferenceCustom());
                                sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                ratioCustom=f3;
                                sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
//                                writeJson("ratioCustom",new Model(1,ratioCustom));
                            }
                        }
                        if (CollectionUtils.isNotEmpty(statsList)){
                            for (StatStatLists stats : statsList){
                                List<StatStatList> stats2 = stats.getStatStatListList();
                                String timeNode = lineData.get(0).getName();
                                timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                                List<String> dates = CommonUtils.getTimeAxis(startDate, endDate, timeNodeType);
                                int j = 0;
                                for (int t = 0; t < dates.size(); t++) {
                                    String d = dates.get(t);
                                    if (j < stats2.size()) {
                                        if (stats2.get(j).getName().indexOf(d) >= 0) {//存在日期
                                            j++;
                                        } else { // 缺少日期补0
                                            StatStatList statStatList=new StatStatList();
                                            statStatList.setName(d);
                                            statStatList.setTotal(0);
                                            lineData.add(statStatList);
                                        }
                                    } else {
                                        StatStatList statStatList=new StatStatList();
                                        statStatList.setName(d);
                                        statStatList.setTotal(0);
                                        lineData.add(statStatList);
                                    }
                                }
                                Collections.sort(lineData, new Comparator<StatStatList>() {
                                    @Override
                                    public int compare(StatStatList o1, StatStatList o2) {
                                        return o1.getName().compareTo(o2.getName());
                                    }
                                });
                            }
                        }
                        if (csView.getCtksStatStatLists()!=null && CollectionUtils.isNotEmpty(csView.getCtksStatStatLists().getCtkList())) {
                            csView.getCtksStatStatLists().getCtkList().get(0).setTitle(k.getTitle1());
                            lists.add(csView.getCtksStatStatLists().getCtkList().get(0));
                        }
                        if (csView.getCtksStatStatLists()!=null && CollectionUtils.isNotEmpty(csView.getCtksStatStatLists().getStatStatListsList())) {
                            csView.getCtksStatStatLists().getStatStatListsList().get(0).setName(k.getTitle1());
                            echartMap2.put(i, csView.getCtksStatStatLists().getStatStatListsList().get(0));

                        }
                    }
                }
                if (lists.size()==0){
                    CommonUtils.initTime(date, startTime, endTime, endDate, startDate);
                    String str = Utils.getStringFromDate(startDate, "")+" ~ "+Utils.getStringFromDate(endDate, "");
                    /**
                     * 不是分享
                     */
                    if(StringUtils.isNotEmpty(json)){//使用缓存
                        csv = JSON.parseObject(json, CtksStatsView.class);
                    }else{//json为空
                        Params p = new Params();
                        p.setIsDelay(-1);
                        p.setKeyword(StringUtils.trim(k.getKeyword1()));
                        p.setFilterKeyword(StringUtils.trim(k.getFilterKeyword1()));
                        p.setDate(date);
                        p.setOrder(Params.ORDER_KEY);
                        p.setSort(Params.SORT_ASC + "");
                        p.setStatField(Params.STATFIELD_ORIGINTYPE);

                        try {
                            csv = WyqH5AbilitysealMethodV1.hotLine(WyqDataConfig.getUserTag(request, user), p, 0);
                        } catch (Exception e) {
                            log.info(JSONObject.toJSONString(e));
                        }
                        Utils.setResultCtksStats(csv, echartmap);
                        log.info("doHotLineV4 result = "+JSON.toJSONString(csv));
                        if(csv != null && ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(csv.getCode())){
                            String transactionId=csv.getTransactionId();
                            System.out.println("transactionId="+transactionId);
                            CtksStats ctksStatStatLists = csv.getCtksStats();
                            if (null != ctksStatStatLists) {
                                List<CategoryTypeKeywords> ctkList = ctksStatStatLists.getCtkList();
                                if (CollectionUtils.isNotEmpty(ctkList)) {
                                    String[] jsons = new String[ctkList.size()];
                                    for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                        StringBuilder sb = new StringBuilder();
                                        CategoryTypeKeywords ctk = ctkList.get(i1);
                                        String f1 = df.format(ctk.getRatioHotCustom());
                                        double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                        f2 = f2 * 100;
                                        int f4 = (int) f2;
                                        String f3 = df.format(ctk.getDifferenceCustom());
                                        sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                        sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                        ratioCustom=f3;
                                        sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                        sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                        sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                        sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
                                        jsons[i1] = sb.toString();
                                    }
                                }
                                List<Stat> lineData=csv.getCtksStats().getStatsList().get(0).getStatList();
                                if(CollectionUtils.isNotEmpty(lineData)){
                                    String timeNode = lineData.get(0).getName();
                                    timeNodeType = CommonUtils.getTimeNodeType(timeNode);
                                    List<String> dates = CommonUtils.getNewTimeAxis(startDate, endDate, timeNodeType);
                                    for(int q=0;q<dates.size();q++){
                                        String d=dates.get(q);
                                        boolean exist=false;
                                        for(int t=0;t<lineData.size();t++){
                                            Stat stat = lineData.get(t);
                                            if(stat.getName().equals(d)){
                                                exist=true;
                                                break;
                                            }
                                        }
                                        if(!exist){
                                            Stat statStatList=new Stat();
                                            statStatList.setName(d);
                                            statStatList.setTotal(0);
                                            lineData.add(statStatList);
                                        }
                                    }

                                    Collections.sort(lineData, new Comparator<Stat>() {
                                        @Override
                                        public int compare(Stat o1, Stat o2) {
                                            return o1.getName().compareTo(o2.getName());
                                        }
                                    });

                                    if(CollectionUtils.isNotEmpty(lineData)){
                                        if(timeNodeType==1){//按小时
                                            lineData.remove(lineData.size()-1);
                                        }
                                    }
                                    log.info("doHotLineV4 lineData.size() = "+lineData.size());
                                    log.info("doHotLineV4 dates = "+Utils.getJsonFromObject(dates));
                                }
                                String jsonStr = JSON.toJSONString(csv);
                                RedisUtils.setAttribute(sessionName, jsonStr,
                                        SystemConstants.DEFAULT_SESSION_TIME);
                            }

                        }
                    }
                    if(csv != null) {
                        CtksStats ctksStatStatLists = csv.getCtksStats();
                        if (null != ctksStatStatLists) {
                            List<CategoryTypeKeywords> ctkList = ctksStatStatLists.getCtkList();
                            if (CollectionUtils.isNotEmpty(ctkList)) {
                                String[] jsons = new String[ctkList.size()];
                                for (int i1 = 0; i1 < ctkList.size(); i1++) {
                                    StringBuilder sb = new StringBuilder();
                                    CategoryTypeKeywords ctk = ctkList.get(i1);
                                    String f1 = df.format(ctk.getRatioHotCustom());
                                    double f2 = Double.parseDouble(df.format(ctk.getRatioCustom()));
                                    f2 = f2 * 100;
                                    int f4 = (int) f2;
                                    String f3 = df.format(ctk.getDifferenceCustom());
                                    sb.append("{ratioHotCustom:" + f1 + ",");// 热度值
                                    sb.append("ratioCustom:" + f4 + ",");// 热度同比
                                    ratioCustom=f3;
                                    sb.append("differenceCustom:" + f3 + ",");// 热度变化
                                    sb.append("weiboNum :" + ctk.getWeibo() + ",");// 微博声量
                                    sb.append("weixinNum  :" + ctk.getWeixin() + ",");// 微信声量
                                    sb.append("numberCustom:" + ctk.getNumberCustom() + "}");// 全网信息声量
                                    jsons[i1] = sb.toString();
                                }
                            }
                        }
                        if (csv.getCtksStats().getStatsList().get(0).getStatList().size() >0){
                            double total1 = csv.getCtksStats().getStatsList().get(0).getStatList().get(csv.getCtksStats().getStatsList().get(0).getStatList().size()-1).getTotal();
                            double total2 = csv.getCtksStats().getStatsList().get(0).getStatList().get(csv.getCtksStats().getStatsList().get(0).getStatList().size()-2).getTotal();
                            ratioCustom = String.valueOf(total1 -  total2);
                        }
                        if(csv.getCtksStats() != null && CollectionUtils.isNotEmpty(csv.getCtksStats().getCtkList())) {
                            csv.getCtksStats().getCtkList().get(0).setTitle(k.getTitle1());
                            lists.add(csv.getCtksStats().getCtkList().get(0));
                        }
                        if(csv.getCtksStats() != null && CollectionUtils.isNotEmpty(csv.getCtksStats().getStatsList())) {
                            csv.getCtksStats().getStatsList().get(0).setName(k.getTitle1());
                            echartmap.put(String.valueOf(i), csv.getCtksStats().getStatsList().get(0));

                        }
                    }
                }
            }
        }
        Map map = new HashMap();
        map.put("ratioCustom",new ModelDto(1,ratioCustom));
        if (lists.size() >0){
            map.put("doHeatValue",new ModelDto(1,lists));
        }else {
            map.put("doHeatValue",new ModelDto(0,lists));
        }
        if (echartmap.size() > 0){
            map.put("doEventTrend",new ModelDto(1,echartmap));
        }else {
            map.put("doEventTrend",new ModelDto(0,echartmap));
        }
        if (echartMap2.size() > 0){
            map.put("doEventTrend2",new ModelDto(1,echartMap2));
        }else {
            map.put("doEventTrend2",new ModelDto(0,echartMap2));
        }
        map.put("date", request.getParameter("date"));
        return new ModelDto(1,map);
    }

    @Override
    public ModelDto getRelatedTermsV3(HttpServletRequest request, UserDto user){

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");

        String keyword2 = request.getParameter("keyword2");
        String searchKeyword2 = request.getParameter("searchKeyword2");
        String filterKeyword2 = request.getParameter("filterKeyword2");

        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        Map<Integer,List<RelatedWord>> echartsmapRelatedTerm = new HashMap<>();
        List<Keywords> keywordList = new ArrayList<Keywords>();
        String json =null;
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        if(StringUtils.isNotBlank(searchKeyword2)){
            Keywords k2 = new Keywords();
            k2.setTitle1(searchKeyword2);
            k2.setKeyword1(keyword2);
            k2.setFilterKeyword1(filterKeyword2);
            keywordList.add(k2);
        }

        if(CollectionUtils.isNotEmpty(keywordList)){
            for (int i = 0; i < keywordList.size(); i++) {
                Keywords k = keywordList.get(i);
                Params p = new Params();
                p.setIsDelay(-1);
                p.setDate(date);
                p.setKeyword(k.getKeyword1());
                p.setFilterKeyword(k.getFilterKeyword1());
                p.setPage(1);
                p.setPagesize(60);
                p.setComblineflg(Params.COMBLINEFLG_MERGE);
                p.setOrder(Params.ORDER_SIMILAR);
                String jsonStr = "";
                RelatedWordView rwv = null;
                String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_ASSOCIATION,
                        true,getSessionNameMap(request));
                json = RedisUtils.getAttribute(sessionName);
                if (StringUtils.isNotEmpty(json)) {
                    rwv = JSON.parseObject(json, RelatedWordView.class);
                }
                if (StringUtils.isNotBlank(shareCode)) {
                    rwv = getShareData("yearHotAnlyzer", RelatedWordView.class,shareCode);
                    jsonStr = JSONObject.toJSONString(rwv);
                }
                if (StringUtils.isBlank(jsonStr)){
                    CommAnalysisRelateWord crw = new CommAnalysisRelateWord();
                    crw.setNum(10);
                    try {
                        rwv = WyqH5MethodV1.anlyzerRelatedWordInsideV1_001(WyqDataConfig.getUserTag(request,user), p, crw);
                    }catch (Exception e){
                        log.info(JSONObject.toJSONString(e));
                    }
                    jsonStr = JSON.toJSONString(rwv);
                }
                if(rwv != null){
                    if(CollectionUtils.isNotEmpty(rwv.getRelatedWordList())){
                        echartsmapRelatedTerm.put(i, rwv.getRelatedWordList());
                        RedisUtils.setAttribute(sessionName, jsonStr,
                                SystemConstants.DEFAULT_SESSION_TIME);
                    }
                }
            }
        }
        if(MapUtils.isNotEmpty(echartsmapRelatedTerm)){
             return new ModelDto(1,echartsmapRelatedTerm);
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getHotMessage(HttpServletRequest request, UserDto user){

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");

        String keyword2 = request.getParameter("keyword2");
        String searchKeyword2 = request.getParameter("searchKeyword2");
        String filterKeyword2 = request.getParameter("filterKeyword2");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        if(StringUtils.isNotBlank(shareCode)){//shareCode是web付费分享

            HashMap<Integer,Object> r = new HashMap<>();
            HashMap<String,Object> result = new HashMap<>();
            IContentCommonNetView contenView = new IContentCommonNetView();
            IContentCommonNetView contenView2 = null;//非微博
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String[] jsons  = null;
            String[] jsons2 = null;
            String json = null;
            String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH + SystemConstants.JEDIS_KEYS_HOTMESSAGE,
                    true,getSessionNameMap(request));
            String title = searchKeyword;
            List<Keywords> keywordList = new ArrayList<Keywords>();
            if(StringUtils.isNotBlank(searchKeyword)){
                Keywords k1 = new Keywords();
                k1.setTitle1(searchKeyword);
                k1.setKeyword1(keyword1);
                k1.setFilterKeyword1(filterKeyword1);
                keywordList.add(k1);
            }
            if(CollectionUtils.isNotEmpty(keywordList)) {
                for (int i = 0; i < keywordList.size(); i++) {
                    Keywords k = keywordList.get(i);
                    Params p = new Params();
                    p.setIsDelay(-1);
                    p.setDate(date);
                    p.setKeyword(k.getKeyword1());
                    p.setFilterKeyword(k.getFilterKeyword1());
                    p.setComblineflg(Params.COMBLINEFLG_MERGE);
                    p.setOrder(Params.ORDER_SIMILAR);
                    p.setPage(1);
                    p.setPagesize(5);
                    json = RedisUtils.getAttribute(sessionName);

                    if (StringUtils.isEmpty(json)) {
                        p.setOrigin("2");
                        if (StringUtils.isNotBlank(shareCode)) {
                            contenView = getShareData("yesWeiBoHotPeople", IContentCommonNetView.class,shareCode);
                        }
                        if (contenView == null){
                            try {
                                contenView = WyqH5MethodV1.searchHotListV1_001(WyqDataConfig.getUserTag(request, user), p);
                            }catch (Exception e){
                                log.info(JSONObject.toJSONString(e));
                            }
                        }
                        if (contenView != null) {
                            if (CodeConstant.SUCCESS_CODE.equals(contenView.getCode())) {
                                List<com.xd.tools.pojo.IContentCommonNet> contentList = contenView
                                        .getIContentCommonNetList();
                                if (null != contentList && contentList.size() > 0) {
                                    jsons = new String[contentList.size()];
                                    for (int j = 0; j < contentList.size(); j++) {
                                        com.xd.tools.pojo.IContentCommonNet conten = contentList.get(j);
                                        System.out.println(conten.getTitle());
                                        String tit = conten.getTitle();
                                        if (tit.indexOf("【原微博】") >= 0) {
                                            tit = conten.getTitle().substring(conten.getTitle().indexOf("【原微博】") + 5,
                                                    conten.getTitle().length());
                                        }
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append("{");
                                        System.out.println(tit + "======");
                                        tit = tit.replaceAll("<font color=\"red\">", "").replaceAll("</font>", "");
                                        tit = tit.replaceAll("[\\u00A0]+", "").trim();
                                        if (tit.indexOf(title) >= 0) {
                                            tit = tit.replaceAll(title, "<font color='red'>" + title + "</font>");
                                        }
                                        if (tit.indexOf("\"") >= 0) {
                                            tit = tit.replaceAll("\"", "“");
                                        }
                                        tit = tit.replaceAll("[\\t\\n\\r]", " ");
                                        sb2.append("\"title\":\"" + tit + "\",");
                                        sb2.append("\"sourceWebsite\":\"" + conten.getCaptureWebsiteName() + "\",");
                                        sb2.append("\"publishTime\":\"" + formatDate.format(conten.getPublished())
                                                + "\",");
                                        sb2.append("\"url\":\"" + conten.getWebpageUrl() + "\",");
                                        sb2.append("\"similarCount\":\"" + conten.getRepeatNum() + "\"}");
                                        jsons[j] = sb2.toString();
                                    }
                                    result.put("jsons", jsons);
                                } else {
                                    result.put("jsons", null);
                                }
                            } else {
                                result.put("jsons", null);
                            }
                        }else {
                            result.put("jsons", null);
                        }
                        p.setOrigin("3,4,5,6,7,8,9,10,11,12");
                        if (StringUtils.isNotBlank(shareCode)) {
                            contenView2 = getShareData("noWeiBoHotPeople", IContentCommonNetView.class,shareCode);
                        }
                        if (contenView2 == null){
                            try {
                                contenView2 = WyqH5MethodV1.searchHotListV1_001(WyqDataConfig.getUserTag(request,user), p);
                            }catch (Exception e){
                                log.info(JSONObject.toJSONString(e));
                            }
                        }
                        if (null != contenView2) {
                            if (CodeConstant.SUCCESS_CODE.equals(contenView2.getCode())) {
                                List<com.xd.tools.pojo.IContentCommonNet> contentList = contenView2
                                        .getIContentCommonNetList();
                                if (null != contentList && contentList.size() > 0) {
                                    // doFilterRepeat(contentList);
                                    jsons2 = new String[contentList.size()];
                                    for (int j = 0; j < contentList.size(); j++) {
                                        com.xd.tools.pojo.IContentCommonNet conten = contentList.get(j);
                                        System.out.println(conten.getTitle());
                                        String tit = conten.getTitle();
                                        if (tit.indexOf("【原微博】") >= 0) {
                                            tit = conten.getTitle().substring(conten.getTitle().indexOf("【原微博】") + 5,
                                                    conten.getTitle().length());
                                        }
                                        StringBuilder sb2 = new StringBuilder();
                                        sb2.append("{");
                                        System.out.println(tit + "======");
                                        tit = tit.replaceAll("<font color=\"red\">", "").replaceAll("</font>", "");
                                        tit = tit.trim();
                                        if (tit.indexOf(title) >= 0) {
                                            tit = tit.replaceAll(title, "<font color='red'>" + title + "</font>");
                                        }
                                        if (tit.indexOf("\"") >= 0) {
                                            tit = tit.replaceAll("\"", "“");
                                        }
                                        tit = tit.replaceAll("[\\t\\n\\r]", " ");
                                        sb2.append("\"title\":\"" + tit + "\",");
                                        sb2.append("\"sourceWebsite\":\"" + conten.getCaptureWebsiteName() + "\",");
                                        sb2.append("\"publishTime\":\"" + formatDate.format(conten.getPublished())
                                                + "\",");
                                        sb2.append("\"url\":\"" + conten.getWebpageUrl() + "\",");
                                        sb2.append("\"similarCount\":\"" + conten.getRepeatNum() + "\"}");
                                        jsons2[j] = sb2.toString();
                                    }
                                    result.put("jsons2", jsons2);
                                    json = JSONObject.toJSONString(result);
                                    RedisUtils.setAttribute(sessionName, json, SystemConstants.DEFAULT_SESSION_TIME);
                                } else {
                                    result.put("jsons2", null);
                                    json = JSONObject.toJSONString(result);
                                }
                            } else {
                                result.put("jsons2", null);
                                json = JSONObject.toJSONString(result);
                            }
                        } else {
                            result.put("jsons2", null);
                            json = JSONObject.toJSONString(result);
                        }
                        if(MapUtils.isNotEmpty(result)) {
                            r.put(i,result);
                        }
                    }
                    CommonUtils.jsonToMap(r, result, json);
                }
            }
            if(MapUtils.isNotEmpty(r)){
                return new ModelDto(1,r);
            }else{
                return new ModelDto(0);
            }
        }else{
            return new ModelDto(0);
        }
    }

    @Override
    public ModelDto getWordcloudV3(HttpServletRequest request, UserDto user){

        String keyword1 = request.getParameter("keyword1");
        String searchKeyword = request.getParameter("searchKeyword1");
        String dateStr = request.getParameter("date");
        String filterKeyword1 = request.getParameter("filterKeyword1");

        String keyword2 = request.getParameter("keyword2");
        String searchKeyword2 = request.getParameter("searchKeyword2");
        String filterKeyword2 = request.getParameter("filterKeyword2");
        String shareCode = request.getParameter("shareCode");
        Integer date = 24;
        if (StringUtils.isNotBlank(dateStr)){
            date = Integer.valueOf(dateStr);
        }
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtils.parse(startTime);
            endDate =  DateUtils.parse(endTime);
        } catch (Exception e) {

        }
        int timeNodeType;
        DecimalFormat df = new DecimalFormat("######0.00");

        Map<Integer,List<Stat>> echartsmapWordcloud = new HashMap<>();
        List<Keywords> keywordList = new ArrayList<Keywords>();
        String json=null;
        if(StringUtils.isNotBlank(searchKeyword)){
            Keywords k1 = new Keywords();
            k1.setTitle1(searchKeyword);
            k1.setKeyword1(keyword1);
            k1.setFilterKeyword1(filterKeyword1);
            keywordList.add(k1);
        }
        if(StringUtils.isNotBlank(searchKeyword2)){
            Keywords k2 = new Keywords();
            k2.setTitle1(searchKeyword2);
            k2.setKeyword1(keyword2);
            k2.setFilterKeyword1(filterKeyword2);
            keywordList.add(k2);
        }

        if(CollectionUtils.isNotEmpty(keywordList)){
            for (int i = 0; i < keywordList.size(); i++) {
                Keywords k = keywordList.get(i);
                Params p = new Params();
                p.setIsDelay(-1);
                p.setDate(date);
                p.setKeyword(k.getKeyword1());
                p.setFilterKeyword(k.getFilterKeyword1());
                p.setComblineflg(Params.COMBLINEFLG_MERGE);
                p.setOrder(Params.ORDER_SIMILAR);
                p.setPagesize(60);
                String jsonStr = "";
                StatView sv = null;
                String sessionName = CommonUtils.getSessionName(SystemConstants.JEDIS_KEYS_HOT_SEARCH
                        + SystemConstants.JEDIS_KEYS_MOREWORDS, true,getSessionNameMap(request));
                json = RedisUtils.getAttribute(sessionName);
                sv = JSON.parseObject(json, StatView.class);
                if (StringUtils.isNotEmpty(json) && sv.getStatList()!=null && sv.getStatList().size()>0) {
                    sv = JSON.parseObject(json, StatView.class);
                }else if(StringUtils.isNotBlank(shareCode)){
                    sv = getShareData("yearHotWordCloud", StatView.class,shareCode);
                    jsonStr = JSON.toJSONString(sv);
                }
                if (StringUtils.isBlank(jsonStr)){
                    CommAnalysisWordCloud commAnalysisWordCloud = new CommAnalysisWordCloud();
                    commAnalysisWordCloud.setNum(60);
                   try {
                       sv = WyqH5MethodV1.anlyzerWordCloudInSideV1_001(WyqDataConfig.getUserTag(request,user), p, commAnalysisWordCloud);
                   }catch (Exception e){
                       log.info(JSONObject.toJSONString(e));
                   }
                    jsonStr = JSON.toJSONString(sv);
                }
                if(sv != null){
                    if(CollectionUtils.isNotEmpty(sv.getStatList()))
                        echartsmapWordcloud.put(i, sv.getStatList());
                    RedisUtils.setAttribute(sessionName, jsonStr,
                            SystemConstants.DEFAULT_SESSION_TIME);
                }
            }
        }
        if(MapUtils.isNotEmpty(echartsmapWordcloud)){
            return  new ModelDto(1,echartsmapWordcloud);
        }else{
            return new ModelDto(0);
        }
    }

}
