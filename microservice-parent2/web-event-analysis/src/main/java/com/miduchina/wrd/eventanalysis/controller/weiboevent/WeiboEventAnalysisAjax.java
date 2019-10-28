package com.miduchina.wrd.eventanalysis.controller.weiboevent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.dto.analysis.weiboanalysis.ExtendStatsView;
import com.miduchina.wrd.dto.analysis.weiboanalysis.StatView;
import com.miduchina.wrd.dto.eventanalysis.products.IGroupResult;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.echart.EChartsAdapter;
import com.miduchina.wrd.eventanalysis.log.model.*;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import com.miduchina.wrd.po.eventanalysis.weiboevent.*;
import com.miduchina.wrd.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/weibo")
public class WeiboEventAnalysisAjax extends BaseController {
    private static String[] mapColors = { "#8acff9", "#fe7f57", "#d974d5", "#3ccc3e", "#cc5d5e", "#b95bd1", "#6698eb",
            "#fe6cb4" };
    private static final long serialVersionUID = 1L;



    /**
     * 简介
     */
    @RequestMapping(value = "/eventProfile")
    public void eventProfile(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = null;
        Object[] json = new Object[3];
        try {
            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            // 简介
            StringBuilder introduceResult = new StringBuilder();
            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/eventProfile.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/eventProfile.txt";
            }

            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                introduceResult.append(line);
            }

            ExtendStatsView extendStatsView = JSON.parseObject(introduceResult.toString(), ExtendStatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(extendStatsView.getCode())) {
                ExtendStats extendStats = extendStatsView.getExtendStats();
                List<Stats> statsList = extendStats.getStatsList();
                List<IContentCommonNets> iContentCommonNets = extendStats.getiContentCommonNetLists();
                if (iContentCommonNets != null && iContentCommonNets.size() > 0) {
                    json[0] = iContentCommonNets.get(0).getiContentCommonNetList();
                }
                json[1] = DateUtils.format(extendStats.getAnalysisStartTime(), DateUtils.FORMAT_SHORT_DAY);
                Stat maxPosition = statsList.get(0).getStatList().get(statsList.get(0).getMaxPosition());
                // String[] maxArr = maxPosition.getName().split("-");
                // String maxTime = maxArr[0]+"年"+maxArr[1]+"月"+maxArr[2]+"日";
                // 高峰时间（分为两种，一种是年月日 一种是带小时）
                String maxTime = null;
                if (maxPosition.getName() != null && !"".equals(maxPosition.getName())) {
                    if (maxPosition.getName().length() > 10) {
                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH");
                        maxTime = DateUtils.format(simple.parse(maxPosition.getName()), DateUtils.FORMAT_SHORT_CN_HOUR);
                    } else {
                        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
                        maxTime = DateUtils.format(simple.parse(maxPosition.getName()), DateUtils.FORMAT_SHORT_CN_DAY);
                    }
                }
                List<Stat> sitesList = statsList.get(0).getStatList();
                String sites = "";
                for (int i = 0; i < sitesList.size(); i++) {
                    if (i > 4)
                        break;
                    sites += sitesList.get(i).getName() + "、";
                }
                sites = sites.substring(0, sites.length() - 1);
                String undulate = statsList.get(0).isUndulate() ? "突出" : "平缓";
                IContentCommonNet firstNews = extendStats.getiContentCommonNetLists().get(0).getiContentCommonNetList()
                        .get(0);
                String title = firstNews.getContent().length() > 20 ? firstNews.getContent().substring(0, 20) + "..."
                        : firstNews.getContent();
                String introduce = "<div style='float:left;text-indent:2em;width:100%;'>本报告围绕关键词“"
                        + extendStats.getKeyword() + "”，对"
                        + DateUtils.format(extendStats.getAnalysisStartTime(), "yyyy/MM/dd  HH:mm") + "~"
                        + DateUtils.format(extendStats.getAnalysisEndTime(), "yyyy/MM/dd日  HH:mm") + "期间，从微博上采集到的"
                        + extendStats.getTotalCount() + "条信息进行了深入的分析。</div>"
                        + "<div style='float:left;text-indent:2em;width:100%;'>微博信息量最高峰出现在" + maxTime + "，当天共有"
                        + maxPosition.getNum()
                        + "篇相关微博言论。</div><div style='float:left;text-indent:2em;width:100%;'>疑似源头的微博讯息于"
                        + DateUtils.format(firstNews.getPublished(), "yyyy/MM/dd  HH:mm") + "发布在"
                        + firstNews.getCaptureWebsiteName() + "标题为：『" + title
                        + "』。</div><div style='float:left;text-indent:2em;width:100%;'>总体来说，整个微博事件的发展趋势较为" + undulate
                        + "，详细报告如下。</div>";
                // 后续舆论主要集中在"+originName+","+originStat.getName()+"类型的相关舆论最多，主要来源于"
//                String fullStr = new String(introduce.getBytes("GBK"), "UTF-8");
                json[2] = introduce;
                printWriter.print(JSON.toJSON(json));
                return;
            }
        } catch (Exception e) {
            log.info("-----------------wb eventProfile Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
        }
        String nullStr = null;
        printWriter.print(nullStr);
    }
    /**
     * 事件趋势
     */
    @RequestMapping(value = "/sitesStatistics")
    public void sitesStatistics(String reportTime,String tickets,HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[2];
        try {
            printWriter = response.getWriter();
            IncidentAnalysisTask task = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ticket", tickets);
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request,
                    "eventGetTask", params);
            if (StringUtils.isNotBlank(rtnStr)) {
                EventTaskRes taskRes = JSON.parseObject(rtnStr, EventTaskRes.class);
                if (taskRes != null && CodeConstant.SUCCESS_CODE.equals(taskRes.getCode())) {
                    task = taskRes.getTask();
                }
            }
            if (task == null) {
                return;
            }
            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            // 网站统计
            StringBuilder siteResult = new StringBuilder();
            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/sitesStatistics.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/sitesStatistics.txt";
            }

            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                siteResult.append(line);
            }

            ExtendStatsView siteView = JSON.parseObject(siteResult.toString(), ExtendStatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(siteView.getCode())) {
                List<Stats> siteStatsList = siteView.getExtendStats().getStatsList();
                if (CollectionUtils.isEmpty(siteStatsList)) {
                    printWriter.print(json);
                    return;
                }
                long total = siteStatsList.get(0).getNum();
                if (total <= 0) {
                    printWriter.print(json);
                    return;
                }
                int maxPosition = siteStatsList.get(0).getMaxPosition();
                String maxName = siteStatsList.get(0).getStatList().get(maxPosition).getKey();
                if (StringUtils.isBlank(maxName)) {
                    maxName = siteStatsList.get(0).getStatList().get(maxPosition).getName();
                }
                Date endTime = task.getEndTime();
                Date startTime = task.getStartTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
                int timeNodeType = CommonUtils.getTimeNodeType(maxName);
                if (timeNodeType == 1) { // 小时
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH");
                    sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH点");
                } else if (timeNodeType == 2) { // 天
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
                } else if (timeNodeType == 3) { // 月
                    sdf = new SimpleDateFormat("yyyy-MM");
                    sdf2 = new SimpleDateFormat("yyyy年MM月");
                }
                TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
                List<IGroupResult> list = new ArrayList<IGroupResult>();
                List<String> timeNodes = CommonUtils.getTimeAxis(startTime, endTime, timeNodeType);
                for (Stats stats : siteStatsList) {
                    IGroupResult iGroupResult = new IGroupResult();
                    List<IGroupResult> subList = new ArrayList<IGroupResult>();
                    iGroupResult.setGroupValue(stats.getName());
                    iGroupResult.setTotal((int) stats.getNum());
                    List<Stat> statList = stats.getStatList();
                    if (CollectionUtils.isNotEmpty(statList)) {
                        Collections.sort(statList, new Comparator<Stat>() {
                            @Override
                            public int compare(Stat o1, Stat o2) {
                                if (StringUtils.isNotBlank(o1.getKey())) {
                                    return o1.getKey().compareTo(o2.getKey());
                                } else {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            }
                        });
                    }
                    int j = 0;
                    for (int i = 0; i < timeNodes.size(); i++) {
                        String timeNode = timeNodes.get(i);
                        Integer num = 0;
                        if (j < statList.size()) {
                            if (statList.get(j).getName().indexOf(timeNode) >= 0) {
                                num = statList.get(j).getNum().intValue();
                                j++;
                            } else { // 缺少日期补0
                                num = 0;
                            }
                        }
                        IGroupResult igr = new IGroupResult();
                        igr.setTotal(num);
                        igr.setGroupValue(timeNode);
                        subList.add(igr);
                    }
                    iGroupResult.setSubGroups(subList);
                    list.add(iGroupResult);
                }
                for (IGroupResult iGroupResult : list) {
                    if (!"全部".equals(iGroupResult.getGroupValue())) {
                        treeMap.put(iGroupResult.getSubGroups().get(maxPosition).getTotal(),
                                iGroupResult.getGroupValue());
                    } else {
                        for (int i = 0; i < iGroupResult.getSubGroups().size(); i++) {
                            IGroupResult iGroupResult1 = iGroupResult.getSubGroups().get(i);
                            if (maxName.equals(iGroupResult1.getGroupValue())) {
                                maxPosition = i;
                            }
                        }
                    }
                }
                EChartsAdapter adapter = new EChartsAdapter(list, "line", "热点走势图");
                json[0] = JSONObject.toJSONString(adapter.getChart());
                Object[] valArr = treeMap.values().toArray();
                String text = "";
                String time = sdf2.format(sdf.parse(maxName));
                if (valArr.length > 2) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出，加上"
                            + valArr[valArr.length - 2] + "和" + valArr[valArr.length - 3] + "的关注，将事态发展推向高点。";
                } else if (valArr.length == 2) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出，加上"
                            + valArr[valArr.length - 2] + "的关注，将事态发展推向高点。";
                } else if (valArr.length == 1) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出。";
                }
                json[1] = text;
                printWriter.print(JSON.toJSON(json));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("sitesStatistics"+JSONObject.toJSONString(e));
        }
        String nullStr = null;
        printWriter.print(nullStr);
    }
    /**
     * 事件趋势
     */
    @RequestMapping(value = "/sitesStatisticsV2")
    public void sitesStatisticsV2(String reportTime,String tickets,HttpServletRequest request,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[2];
        try {
            printWriter = response.getWriter();
            IncidentAnalysisTask task = null;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("ticket", tickets);
            String rtnStr = Utils.sendIntraBusinessAPIPOST(request,
                    "eventGetTask", params);
            if (StringUtils.isNotBlank(rtnStr)) {
                EventTaskRes taskRes = JSON.parseObject(rtnStr, EventTaskRes.class);
                if (taskRes != null && CodeConstant.SUCCESS_CODE.equals(taskRes.getCode())) {
                    task = taskRes.getTask();
                }
            }
            if (task == null) {
                return;
            }
            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            // 网站统计
            StringBuilder siteResult = new StringBuilder();
            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/sitesStatistics.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/sitesStatistics.txt";
            }


            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                siteResult.append(line);
            }
            ExtendStatsView siteView = JSON.parseObject(siteResult.toString(), ExtendStatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(siteView.getCode())) {
                List<Stats> siteStatsList = siteView.getExtendStats().getStatsList();
                if (CollectionUtils.isEmpty(siteStatsList)) {
                    printWriter.print(json);
                    return;
                }
                long total = siteStatsList.get(0).getNum();
                if (total <= 0) {
                    printWriter.print(json);
                    return;
                }
                int maxPosition = siteStatsList.get(0).getMaxPosition();
                String maxName = siteStatsList.get(0).getStatList().get(maxPosition).getKey();
                if (StringUtils.isBlank(maxName)) {
                    maxName = siteStatsList.get(0).getStatList().get(maxPosition).getName();
                }
                Date endTime = task.getEndTime();
                Date startTime = task.getStartTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
                int timeNodeType = CommonUtils.getTimeNodeType(maxName);
                if (timeNodeType == 1) { // 小时
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH");
                    sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH点");
                } else if (timeNodeType == 2) { // 天
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
                } else if (timeNodeType == 3) { // 月
                    sdf = new SimpleDateFormat("yyyy-MM");
                    sdf2 = new SimpleDateFormat("yyyy年MM月");
                }
                TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
                List<IGroupResult> list = new ArrayList<IGroupResult>();
                List<String> timeNodes = CommonUtils.getTimeAxis(startTime, endTime, timeNodeType);
                for (Stats stats : siteStatsList) {
                    IGroupResult iGroupResult = new IGroupResult();
                    List<IGroupResult> subList = new ArrayList<IGroupResult>();
                    iGroupResult.setGroupValue(stats.getName());
                    iGroupResult.setTotal((int) stats.getNum());
                    List<Stat> statList = stats.getStatList();
                    if (CollectionUtils.isNotEmpty(statList)) {
                        Collections.sort(statList, new Comparator<Stat>() {
                            @Override
                            public int compare(Stat o1, Stat o2) {
                                if (StringUtils.isNotBlank(o1.getKey())) {
                                    return o1.getKey().compareTo(o2.getKey());
                                } else {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            }
                        });
                    }
                    int j = 0;
                    for (int i = 0; i < timeNodes.size(); i++) {
                        String timeNode = timeNodes.get(i);
                        Integer num = 0;
                        if (j < statList.size()) {
                            if (statList.get(j).getName().indexOf(timeNode) >= 0) {
                                num = statList.get(j).getNum().intValue();
                                j++;
                            } else { // 缺少日期补0
                                num = 0;
                            }
                        }
                        IGroupResult igr = new IGroupResult();
                        igr.setTotal(num);
                        igr.setGroupValue(timeNode);
                        subList.add(igr);
                    }
                    iGroupResult.setSubGroups(subList);
                    list.add(iGroupResult);
                }
                for (IGroupResult iGroupResult : list) {
                    if (!"全部".equals(iGroupResult.getGroupValue())) {
                        if (maxPosition < iGroupResult.getSubGroups().size()){
                            treeMap.put(iGroupResult.getSubGroups().get(maxPosition).getTotal(),
                                    iGroupResult.getGroupValue());
                        }
                    } else {
                        for (int i = 0; i < iGroupResult.getSubGroups().size(); i++) {
                            IGroupResult iGroupResult1 = iGroupResult.getSubGroups().get(i);
                            if (maxName.equals(iGroupResult1.getGroupValue())) {
                                maxPosition = i;
                            }
                        }
                    }
                }
                EChartsAdapter adapter = new EChartsAdapter(list, "line", "热点走势图");
                json[0] = JSONObject.toJSONString(adapter.getChart());
                Object[] valArr = treeMap.values().toArray();
                String text = "";
                String time = sdf2.format(sdf.parse(maxName));
                if (valArr.length > 2) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出，加上"
                            + valArr[valArr.length - 2] + "和" + valArr[valArr.length - 3] + "的关注，将事态发展推向高点。";
                } else if (valArr.length == 2) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出，加上"
                            + valArr[valArr.length - 2] + "的关注，将事态发展推向高点。";
                } else if (valArr.length == 1) {
                    text = "从上图可以看出，整个事件的爆发点是" + time + "，" + valArr[valArr.length - 1] + "类型的数据较为突出。";
                }
                json[1] = text;
                printWriter.print(JSON.toJSON(json));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("sitesStatisticsV2"+JSONObject.toJSONString(e));
        }
        String nullStr = null;
        printWriter.print(nullStr);
    }
    /**
     * 数据类型
     */
    @RequestMapping(value = "/dataType")
    public void dataType(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        String[] json = new String[6];
        try {
            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder dataResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/dataType.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/dataType.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                dataResult.append(line);
            }
            String srcStr=CommonUtils.getUtfFromGbk(dataResult.toString());
            StatsView dataView = JSON.parseObject(srcStr, StatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(dataView.getCode())) {
                List<Stats> statsList = dataView.getStatsList();
                if (CollectionUtils.isNotEmpty(statsList)) {
                    // 粉丝统计
                    if (statsList.size() > 0 && statsList.get(0) != null && statsList.get(0).getType() == 1) {
                        List<Stat> fanList = statsList.get(0).getStatList(); // 粉丝统计
                        if (CollectionUtils.isNotEmpty(fanList)) {
                            json[0] = com.miduchina.wrd.eventanalysis.utils.CommonUtils .convertStat(fanList, new String("粉丝分布".getBytes("GBK"),"UTF-8"), "wbBar");
                        } else {
                            json[0] = "";
                        }
                    }
                    // 敏感统计
                    if (statsList.size() > 1 && statsList.get(1) != null && statsList.get(1).getType() == 2) {
                        List<Stat> minList = statsList.get(1).getStatList(); // 敏感统计
                        if (CollectionUtils.isNotEmpty(minList)) {
                            json[1] = com.miduchina.wrd.eventanalysis.utils.CommonUtils.convertStat(minList, new String("敏感占比".getBytes("GBK"),"UTF-8"), "gauge");
                        } else {
                            json[1] = "";
                        }
                    }
                    // 根微博统计
                    if (statsList.size() > 2 && statsList.get(2) != null && statsList.get(2).getType() == 3) {
                        List<Stat> rootList = statsList.get(2).getStatList(); // 根微博统计
                        if (CollectionUtils.isNotEmpty(rootList)) {
                            json[2] = com.miduchina.wrd.eventanalysis.utils.CommonUtils.convertStat(rootList, new String("原微博转发微博占比".getBytes("GBK"),"UTF-8"), "wbPie");
                        } else {
                            json[2] = "";
                        }
                    }
                    // 直接转发统计
                    if (statsList.size() > 3 && statsList.get(3) != null && statsList.get(3).getType() == 4) {
                        List<Stat> forwardList = statsList.get(3).getStatList(); // 直接转发统计
                        if (CollectionUtils.isNotEmpty(forwardList)) {
                            json[3] = com.miduchina.wrd.eventanalysis.utils.CommonUtils.convertStat(forwardList, new String("转发层级占比".getBytes("GBK"),"UTF-8"), "wbPie");
                        } else {
                            json[3] = "";
                        }
                    }
                    // 地域统计
                    if (statsList.size() > 4 && statsList.get(4) != null && statsList.get(4).getType() == 5) {
                        List<Stat> dyList = statsList.get(4).getStatList();
                        List<Stat> dyList2 = new ArrayList<Stat>();
                        if (CollectionUtils.isNotEmpty(dyList)) {
                            if (dyList.size() > 10) {
                                for (int i = 0; i < 10; i++) {
                                    dyList2.add(dyList.get(i));
                                }
                            }
                            json[4] = com.miduchina.wrd.eventanalysis.utils.CommonUtils.convertStat(dyList2, new String("转发地域分布".getBytes("GBK"),"UTF-8"), "bar");// 地域统计
                        } else {
                            json[4] = "";
                        }
                    }
                    // 发布设备统计
                    if (statsList.size() > 5 && statsList.get(5) != null && statsList.get(5).getType() == 6) {
                        List<Stat> devList = statsList.get(5).getStatList(); // 发布设备统计
                        json[5] = com.miduchina.wrd.eventanalysis.utils.CommonUtils.convertStat(devList, new String("发布设备分布".getBytes("GBK"),"UTF-8"), "wbPie");
                    }
                }
                printWriter.print(JSON.toJSON(json));
                return;
            }
        } catch (Exception e) {
            log.info("-----------------wb dataType Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("dataType"+JSONObject.toJSONString(e));
        }
        String nullStr = null;
        printWriter.print(nullStr);
    }
    /**
     * 微博相关词云
     */
    @RequestMapping(value = "/wordCloud")
    public void wordCloud(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        String[] json = new String[1];
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder cloudResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/wordCloud.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/wordCloud.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                cloudResult.append(line);
            }

            StatView cloudView = JSON.parseObject(cloudResult.toString(), StatView.class);
            if (CodeConstant.SUCCESS_CODE.equals(cloudView.getCode())) {
                List<Stat> list = cloudView.getStatList();
                if (CollectionUtils.isNotEmpty(list)) {
                    String[] colors = { "#72c1be", "#f29300", "#a05623", "#277bc0" };
                    StringBuilder sb = new StringBuilder("[");
                    for (Stat stat : list) {
                        int i = (int) (Math.random() * 4);
                        sb.append("{name:'" + stat.getName() + "',");
                        sb.append("value:" + (stat.getNum() * 10));
                        sb.append(",itemStyle: {normal: {color: '");
                        sb.append(colors[i]);
                        sb.append("'}}},");
                    }
                    json[0] = sb.substring(0, sb.length() - 1) + "]";
                    printWriter.print(JSON.toJSON(json));
                    return;
                }
            }
        } catch (Exception e) {
            log.info("-----------------wb keyword1 Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("wordCloud"+JSONObject.toJSONString(e));
        }
        String nullStr = null;
        printWriter.print(nullStr);
    }
    /**
     * 热门信息
     */
    @RequestMapping(value = "/hotNews")
    public void hotNews(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        String json = null;
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder hotResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath =Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/hotNews.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/hotNews.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                hotResult.append(line);
            }
            ExtendStatsView hotView = JSON.parseObject(hotResult.toString(), ExtendStatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(hotView.getCode())) {
                List<IContentCommonNets> list = hotView.getExtendStats().getiContentCommonNetLists();
                if (CollectionUtils.isNotEmpty(list)) {
                    printWriter.print(JSON.toJSON(list));
                    return;
                }
            }
        } catch (Exception e) {
            log.info("-----------------wb hotNews Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("hotNews"+JSONObject.toJSONString(e));
        }
        printWriter.print(json);
    }
    /**
     * 意见领袖
     */
    @RequestMapping(value = "/hotPeople")
    public void hotPeople(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        String json = null;
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();

            StringBuilder peopleResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/hotPeople.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/hotPeople.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                peopleResult.append(line);
            }
            IContentWeiboCommonNetView peopleView = JSON.parseObject(peopleResult.toString(),
                    IContentWeiboCommonNetView.class);
            if (CodeConstant.SUCCESS_CODE.equals(peopleView.getCode())) {
                List<com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet> list = peopleView.getiContentCommonNetList();
                if (CollectionUtils.isNotEmpty(list)) {
                    printWriter.print(JSON.toJSON(list));
                    return;
                }
            }
        } catch (Exception e) {
            log.info("-----------------wb hotPeople Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("hotPeople"+JSONObject.toJSONString(e));
        }
        printWriter.print(json);
    }
    /**
     * 传播途径
     */
    @RequestMapping(value = "/propagationPath")
    public void propagationPath(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        String json = null;
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;


            printWriter = response.getWriter();
            StringBuilder pathResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/propagationPath.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/propagationPath.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                pathResult.append(line);
            }

            IContentWeiboCommonNetView pathView = JSON.parseObject(pathResult.toString(),
                    IContentWeiboCommonNetView.class);
            if (CodeConstant.SUCCESS_CODE.equals(pathView.getCode())) {
                List<com.miduchina.wrd.po.eventanalysis.weiboevent.IContentCommonNet> iContentCommonNetList = pathView.getiContentCommonNetList();
                if (iContentCommonNetList != null) {
                    printWriter.print(JSON.toJSON(iContentCommonNetList));
                    return;
                }
            }
        } catch (Exception e) {
            log.info("-----------------wb propagationPath Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("propagationPath"+JSONObject.toJSONString(e));
        }
        printWriter.print(json);
    }
    /**
     * 博主分析
     */
    @RequestMapping(value = "/blogAnalysis")
    public void blogAnalysis(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[6];
        try {
            printWriter = response.getWriter();
            StringBuilder bloggerResult = new StringBuilder();

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/bloggerAnalysis.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/bloggerAnalysis.txt";
            }
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                bloggerResult.append(line);
            }

            ExtendStatsView extendStatsView = JSON.parseObject(bloggerResult.toString(), ExtendStatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(extendStatsView.getCode())) {
                ExtendStats extendStats = extendStatsView.getExtendStats();
                if (extendStats != null) {
                    // 转发数前十博主列表
                    List<IContentCommonNets> iContentCommonNetsList = extendStats.getiContentCommonNetLists();
                    if (CollectionUtils.isNotEmpty(iContentCommonNetsList)) {
                        json[0] = JSON.toJSON(iContentCommonNetsList);
                    }
                    // 统计列表
                    List<Stats> statsList = extendStats.getStatsList();
                    if (CollectionUtils.isNotEmpty(statsList)) {
                        if (statsList.size() > 0 && statsList.get(0) != null && statsList.get(0).getType() == 1) {
                            if (CollectionUtils.isNotEmpty(statsList.get(0).getStatList())) {
                                json[1] = getMapJSON(statsList.get(0).getStatList(), 2);// 地域统计
                            } else {
                                json[1] = "";
                            }
                        }
                        if (statsList.size() > 1 && statsList.get(1) != null && statsList.get(1).getType() == 2) {
                            List<Stat> sexList = statsList.get(1).getStatList(); // 性别统计
                            json[2] = convertStat(sexList, "男女比例", "wbPie");
                        }
                        if (statsList.size() > 2 && statsList.get(2) != null && statsList.get(2).getType() == 3) {
                            List<Stat> userList = statsList.get(2).getStatList(); // 用户类型统计
                            json[3] = convertStat(userList, "用户认证", "wbPie");
                        }
                        if (statsList.size() > 3 && statsList.get(3) != null && statsList.get(3).getType() == 4) {
                            List<Stat> jwList = statsList.get(3).getStatList(); // 境内外统计
                            json[4] = convertStat(jwList, "海内外统计", "wbPie");
                        }
                        if (statsList.size() > 4 && statsList.get(4) != null && statsList.get(4).getType() == 5) {
                            List<Stat> sjList = statsList.get(4).getStatList(); // 水军统计
                            json[5] = convertStat(sjList, "水军分析", "wbPie");
                        }
                    }
                    printWriter.print(JSON.toJSON(json));
                    return;
                }
            }
        } catch (Exception e) {
            log.info("-----------------wb bloggerAnalysis Json convert error" + tickets + "--------------------------");
            e.printStackTrace();
            log.info("blogAnalysis"+JSONObject.toJSONString(e));
        }
        printWriter.print(json);
    }
    /**
     * 从数字统计转换为type类型的json数据
     * @return
     */
    public static String convertStat(List<Stat> statList, String name, String type) {
        String result = "";
        if (CollectionUtils.isNotEmpty(statList)) {
            List<IGroupResult> list = new ArrayList<IGroupResult>();
            for (Stat stat : statList) {
                IGroupResult igr = new IGroupResult();
                igr.setGroupValue(stat.getName());
                igr.setTotal(stat.getNum().intValue());
                igr.setCalculatedValue(stat.getPercent());
                list.add(igr);
            }
            EChartsAdapter adapter = new EChartsAdapter(list, type, name);
            result = JSONObject.toJSONString(adapter.getChart());
        }
        return result;
    }
    public static String getMapJSON(List<Stat> list, int type) {
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        String[] colors = new String[list.size()];
        colors = pickColor1(list, type);
        String result = "";
        StringBuilder sb = new StringBuilder();
        sb.append("[{");
        sb.append("data:[");
        long total = 0;
        long max = 0;
        long min = 0;
        for (int i = 0; i < list.size(); i++) {
            Stat stat = list.get(i);
            if (i == 0) {
                max = stat.getNum();
            }
            if (i == (list.size() - 1)) {
                min = stat.getNum();
            }
            if (stat.getName() == null || "".equals(stat.getName()) || "外媒".equals(stat.getName())
                    || "全国".equals(stat.getName())) {
                continue;
            }
            sb.append("{name:");
            sb.append("'");
            sb.append(stat.getName());
            sb.append("'");
            sb.append(",");
            sb.append("value:");
            sb.append(stat.getNum());
			/*
			 * sb.append(","); sb.append(" itemStyle: {");
			 * sb.append("normal: {borderColor:'#FFF',color: '"+colors[i]+"'},");
			 * sb.append("emphasis: {borderColor:'yellow'}"); sb.append("}");
			 */
            sb.append("}");
            if (i < list.size() - 1) {
                sb.append(",");
            }
            total += stat.getNum();
        }
        sb.append("]");
        sb.append(",total:");
        sb.append(total);
        sb.append(",max:");
        sb.append(max);
        sb.append(",min:");
        sb.append(min);
        sb.append("}]");
        result = sb.toString();
        return result;
    }
    public static String[] pickColor1(List<Stat> gr, int type) {
        if (gr == null || gr.size() == 0) {
            return null;
        }
        String[] colors = new String[gr.size()];
        for (int i = 0; i < gr.size(); i++) {
            long total = gr.get(i).getNum();
            if (type == 1) {
                if (total == 1) {
                    colors[i] = mapColors[0];
                } else if (total == 2) {
                    colors[i] = mapColors[1];
                } else if (total >= 3 && total < 6) {
                    colors[i] = mapColors[2];
                } else if (total >= 6 && total < 11) {
                    colors[i] = mapColors[3];
                } else if (total >= 11 && total < 16) {
                    colors[i] = mapColors[4];
                } else if (total >= 16 && total < 21) {
                    colors[i] = mapColors[5];
                } else if (total >= 21 && total < 101) {
                    colors[i] = mapColors[6];
                } else if (total >= 101) {
                    colors[i] = mapColors[7];
                }
            } else if (type == 2) {
                if (total >= 1 && total < 11) {
                    colors[i] = mapColors[0];
                } else if (total >= 11 && total < 21) {
                    colors[i] = mapColors[1];
                } else if (total >= 21 && total < 51) {
                    colors[i] = mapColors[2];
                } else if (total >= 51 && total < 101) {
                    colors[i] = mapColors[3];
                } else if (total >= 101 && total < 151) {
                    colors[i] = mapColors[4];
                } else if (total >= 151 && total < 201) {
                    colors[i] = mapColors[5];
                } else if (total >= 201 && total < 501) {
                    colors[i] = mapColors[6];
                } else if (total >= 501) {
                    colors[i] = mapColors[7];
                }
            }
        }
        return colors;
    }
    /**
     * 情绪分析
     */
    @RequestMapping(value = "/emoteAnalysis")
    public void emoteAnalysis(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[4];
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder emoteResult = new StringBuilder();
            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/emotionAnalysis.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/emotionAnalysis.txt";
            }

            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                emoteResult.append(line);
            }

            StatsView statsView = JSON.parseObject(emoteResult.toString(), StatsView.class);
            if (CodeConstant.SUCCESS_CODE.equals(statsView.getCode())) {
                List<Stats> statsList = statsView.getStatsList();
                if (statsList != null) {
                    // 情绪分析
                    if (CollectionUtils.isNotEmpty(statsList)) {
                        if (statsList.size() > 0 && statsList.get(0) != null && statsList.get(0).getType() == 1) {
                            if (CollectionUtils.isNotEmpty(statsList.get(0).getStatList())) {
                                json[0] = getMapJSON(statsList.get(0).getStatList(), 2);// 地域统计
                            } else {
                                json[0] = "";
                            }
                        }
                        if (statsList.size() > 1 && statsList.get(1) != null && statsList.get(1).getType() == 2) {
                            List<Stat> qxList = statsList.get(1).getStatList(); // 情绪统计
                            json[1] = convertStat(qxList, "情绪统计", "gauge");
                        }
                        if (statsList.size() > 2 && statsList.get(2) != null && statsList.get(2).getType() == 3) {
                            List<Stat> forwardList = statsList.get(2).getStatList(); // 转发统计
                            List<String> filterEmotions = getFilterEmotions();
                            if (CollectionUtils.isNotEmpty(filterEmotions)) {
                                for (String filterEmotion : filterEmotions) {
                                    for (int i = 0; i < forwardList.size(); i++) {
                                        if (filterEmotion.equals(forwardList.get(i).getName())) {
                                            forwardList.remove(i);
                                            break;
                                        }
                                    }
                                }
                            }
                            int maxLength = forwardList.size() > 12 ? 12 : forwardList.size();
                            forwardList = forwardList.subList(0, maxLength);
                            List<Stat> plList = statsList.get(3).getStatList(); // 评论统计
                            List<Stat> newPlList = new ArrayList<Stat>();
                            List<IGroupResult> barList = new ArrayList<IGroupResult>();
                            IGroupResult igr1 = new IGroupResult();
                            igr1.setGroupValue("转发表情分析");
                            igr1.setTotal(0);
                            IGroupResult igr2 = new IGroupResult();
                            igr2.setGroupValue("评论表情分析");
                            igr2.setTotal(0);
                            barList.add(igr2);
                            barList.add(igr1);
                            List<IGroupResult> tempList = new ArrayList<IGroupResult>();
                            List<IGroupResult> temp2List = new ArrayList<IGroupResult>();
                            for (int i = forwardList.size() - 1; i >= 0; i--) {
                                Stat stat = forwardList.get(i);
                                Stat stat1 = new Stat();
                                stat1.setName(stat.getName());
                                stat1.setNum(0l);
                                for (Stat stat2 : plList) {
                                    if (stat.getName().equals(stat2.getName())) {
                                        stat1.setNum(stat2.getNum());
                                        break;
                                    }
                                }
                                newPlList.add(stat1);
                                IGroupResult igr3 = new IGroupResult();
                                IGroupResult igr4 = new IGroupResult();
                                igr3.setGroupValue(stat.getName());
                                igr3.setTotal(stat.getNum().intValue());
                                tempList.add(igr3);
                                igr4.setGroupValue(stat1.getName());
                                igr4.setTotal(stat1.getNum().intValue());
                                temp2List.add(igr4);
                                stat.setName(CommonUtils.replaceEmoji(stat.getName()));
                            }
                            Collections.reverse(temp2List);
                            igr1.setSubGroups(tempList);
                            igr2.setSubGroups(temp2List);
                            EChartsAdapter adapter = new EChartsAdapter(barList, "bar2", "情绪分析");
                            json[1] = JSONObject.toJSONString(adapter.getChart());
                            json[2] = JSON.toJSON(forwardList);
                            json[3] = JSON.toJSON(newPlList);
                        }
                    }
                    printWriter.print(JSON.toJSON(json));
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printWriter.print(json);
    }
    /**
     * 组装核心传播人信息
     * @param
     * @since 2016-8-30 下午3:42:15
     * @author mabenyong
     * @return
     */
    @RequestMapping(value = "/coreTranInfo")
    public void coreTranInfo(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[1];
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder coreTranResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/coreTran.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/coreTran.txt";
            }
            File f = new File(filePath);
            if (f.exists()) {
                log.info("【coreTran filePath of document for txt:" + filePath + "】");
                @SuppressWarnings("resource")
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    coreTranResult.append(line);
                }
                ExtendStatsView extendStatsView = JSON.parseObject(coreTranResult.toString(), ExtendStatsView.class);
                if (CodeConstant.SUCCESS_CODE.equals(extendStatsView.getCode())) {
                    ExtendStats extendStats = extendStatsView.getExtendStats();
                    if (extendStats != null) {
                        List<IContentCommonNets> iContentCommonNetsList = extendStats.getiContentCommonNetLists();
                        if (CollectionUtils.isNotEmpty(iContentCommonNetsList)) {
                            json[0] = JSON.toJSON(iContentCommonNetsList);
                        }
                        printWriter.print(JSON.toJSON(json));
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 热门评论
     * @param
     * @since 2016-8-30 下午8:51:50
     * @return
     */
    @RequestMapping(value = "/typicalViews")
    public void typicalViews(String reportTime,String tickets,HttpServletResponse response) {
        response.setContentType("application/json;charset=GBK;");
        PrintWriter printWriter = null;
        Object[] json = new Object[7];
        try {

            Flags.local_flag = local_flag;
            Flags.filePath = filePath;

            printWriter = response.getWriter();
            StringBuilder typicalResult = new StringBuilder();

            String filePath="";
            if (Flags.local_flag) {
                filePath = Flags.filePath + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/typicalViews.txt";
            }else{
                filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "weiboEventAnalysis/" + reportTime + "/" + tickets
                        + "/typicalViews.txt";
            }
            File f = new File(filePath);
            if (f.exists()) {
                @SuppressWarnings("resource")
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    typicalResult.append(line);
                }
                StatsView typicalView = JSON.parseObject(typicalResult.toString(), StatsView.class);
                if (CodeConstant.SUCCESS_CODE.equals(typicalView.getCode())) {
                    List<Stats> statsList = typicalView.getStatsList();
                    if (CollectionUtils.isNotEmpty(statsList)) {
                        List<Stat> weiboList = null;
                        for (Stats stats : statsList) {
                            if (stats.getType() == 3) {
                                weiboList = stats.getStatList();
                                List<IGroupResult> list = new ArrayList<IGroupResult>();
                                for (int i = 0; i <= weiboList.size() - 1; i++) {
                                    Stat stat = weiboList.get(i);
									/*
									 * IdvalueBean idvalueBean = stat.getIdbeanlst().get(0); String
									 * name = idvalueBean.getContent();
									 */
                                    String name = stat.getName();
                                    name = name.length() > 30 ? name.substring(0, 30) : name;
                                    IGroupResult igr1 = new IGroupResult();
                                    igr1.setGroupValue(name);
                                    // igr1.setTotal((int)stat.getNum());
                                    igr1.setCalculatedValue(stat.getTotal());
                                    list.add(igr1);
                                }
                                EChartsAdapter adapter = new EChartsAdapter(list, "eventpie2", "微博观点分析");
                                json[2] = JSONObject.toJSONString(adapter.getChart());
                                json[6] = JSON.toJSON(weiboList);
                            }
                            printWriter.print(JSON.toJSON(json));
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printWriter.print(json);
    }

    public List<String> getFilterEmotions() {
        return IntraBusinessAPIConfig.getFilterEmotions();
    }
}