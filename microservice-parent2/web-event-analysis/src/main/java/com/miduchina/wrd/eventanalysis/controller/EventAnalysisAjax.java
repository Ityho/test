package com.miduchina.wrd.eventanalysis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.BusinessConstant;
import com.miduchina.wrd.dto.analysis.weiboanalysis.ExtendStatsView;
import com.miduchina.wrd.dto.analysis.weiboanalysis.StatList;
import com.miduchina.wrd.dto.analysis.weiboanalysis.StatListView;
import com.miduchina.wrd.dto.eventanalysis.products.IGroupResult;
import com.miduchina.wrd.eventanalysis.base.BaseController;
import com.miduchina.wrd.eventanalysis.constant.Flags;
import com.miduchina.wrd.eventanalysis.echart.EChartsAdapter;
import com.miduchina.wrd.eventanalysis.log.model.EventTaskRes;
import com.miduchina.wrd.eventanalysis.log.model.StatsView;
import com.miduchina.wrd.eventanalysis.utils.TaskJob;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.po.eventanalysis.IncidentAnalysisTask;
import com.miduchina.wrd.po.eventanalysis.weiboevent.*;
import com.miduchina.wrd.util.DateUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Data
@RestController
public class EventAnalysisAjax extends BaseController  {
	private static String[] mapColors = { "#8acff9", "#fe7f57", "#d974d5", "#3ccc3e", "#cc5d5e", "#b95bd1", "#6698eb",
			"#fe6cb4" };
	@RequestMapping(value = "/eventProfile")
	public void eventProfile(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		Object[] json = new Object[3];
		try {
			printWriter = response.getWriter();
			// 简介
			StringBuilder introduceResult = new StringBuilder();

			Flags.local_flag = local_flag;
			Flags.filePath = filePath;

			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/eventProfile.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/eventProfile.txt";
			}
			log.info("eventProfile"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				introduceResult.append(line);
			}
			ExtendStatsView extendStatsView = JSON.parseObject(introduceResult.toString(), ExtendStatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(extendStatsView.getCode())) {
				ExtendStats extendStats = extendStatsView.getExtendStats();
				List<Stats> statsList = extendStats.getStatsList();
				System.out.println("-----" + extendStats.getiContentCommonNetLists().size() + "------");
				int checksize = extendStats.getiContentCommonNetLists().size();
				if (checksize != 0) {
					json[0] = extendStats.getiContentCommonNetLists().get(1).getiContentCommonNetList();
					json[1] = DateUtils.format(extendStats.getAnalysisStartTime(), DateUtils.FORMAT_SHORT_DAY);
					Stat maxPosition = statsList.get(0).getStatList().get(statsList.get(0).getMaxPosition());
					Pattern pattern = Pattern.compile("-| ");
					String[] maxArr = pattern.split(maxPosition.getName());
					String maxTime = maxArr[0] + "年" + maxArr[1] + "月" + maxArr[2] + "日";
					/*
					 * Stat originStat = statsList.get(3).getStatList().get(0); String originName =
					 * "境外".equals(originStat.getName())?"境外":"境内";
					 */
					List<Stat> sitesList = statsList.get(1).getStatList();
					String sites = "";
					for (int i = 0; i < sitesList.size(); i++) {
						if (i > 4)
							break;
						sites += sitesList.get(i).getName() + "、";
					}
					sites = sites.substring(0, sites.length() - 1);
					String undulate = statsList.get(0).isUndulate() ? "突出" : "平缓";
					IContentCommonNet firstNews = extendStats.getiContentCommonNetLists().get(0)
							.getiContentCommonNetList().get(0);
					String title = firstNews.getTitle().length() > 20 ? firstNews.getTitle().substring(0, 20) + "..."
							: firstNews.getTitle();
					String introduce = "<div style='float:left;text-indent:2em;width:100%;'>本报告围绕关键词"
							+ extendStats.getKeyword() + "，对"
							+ DateUtils.format(extendStats.getAnalysisStartTime(), "yyyy年MM月dd日  HH时mm分") + "到"
							+ DateUtils.format(extendStats.getAnalysisEndTime(), "yyyy年MM月dd日  HH时mm分") + "以来，互联网上采集到的"
							+ extendStats.getTotalCount() + "条信息进行了深入的分析。</div>"
							+ "<div style='float:left;text-indent:2em;width:100%;'>舆论最高峰出现在" + maxTime + "，当天共有"
							+ maxPosition.getNum()
							+ "篇相关舆论。</div><div style='float:left;text-indent:2em;width:100%;'>最早的舆论在"
							+ DateUtils.format(firstNews.getPublished(), "yyyy年MM月dd日  HH时mm分") + "发布在"
							+ firstNews.getCaptureWebsiteName() + ",标题为：" + title + "。</div>";
					if (StringUtils.isNotEmpty(sites)) {
						introduce += "<div style='float:left;text-indent:2em;width:100%;'>后续舆论主要来源于" + sites
								+ "等几大站点。</div>";
					}
					introduce += "<div style='float:left;text-indent:2em;width:100%;'>总体来说，整个事件发展趋势较为" + undulate
							+ "，详细报告如下。</div>";
					json[2] = introduce;
					printWriter.print(JSON.toJSON(json));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("eventProfile"+JSONObject.toJSONString(e));
		}
		String nullStr = null;
		printWriter.print(nullStr);
	}

	public void sitesStatistics(String reportTime,String tickets,HttpServletRequest request , HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		Object[] json = new Object[2];
		try {
			printWriter = response.getWriter();
			IncidentAnalysisTask task = null;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ticket", tickets);
			params.put("platform", BusinessConstant.PLATFORM_H5);
			String rtnStr = Utils.sendIntraBusinessAPIPOST(request,"eventGetTask", params);
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
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/sitesStatistics.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/sitesStatistics.txt";
			}
			log.info("sitesStatistics"+filePath);
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
				List<String> timeNodes = CommonUtils.getTimeAxis(startTime, endTime, timeNodeType);
				TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
				List<IGroupResult> list = new ArrayList<IGroupResult>();
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
			params.put("platform", BusinessConstant.PLATFORM_H5);
			String rtnStr = Utils.sendIntraBusinessAPIPOST(request, "eventGetTask", params);
			if (StringUtils.isNotBlank(rtnStr)) {
				EventTaskRes taskRes = JSON.parseObject(rtnStr, EventTaskRes.class);
				if (taskRes != null && CodeConstant.SUCCESS_CODE.equals(taskRes.getCode())) {
					task = taskRes.getTask();
				}
			}
			if (task == null) {
				printWriter.print("");
				return;
			}
			Flags.local_flag = local_flag;
			Flags.filePath = filePath;
			// new 网站统计
			StringBuilder siteResult = new StringBuilder();
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/sitesStatistics.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/sitesStatistics.txt";
			}
			log.info("sitesStatisticsV2"+filePath);
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
				int timeNodeType = CommonUtils.getTimeNodeType(maxName);
				List<String> timeNodes = CommonUtils.getTimeAxis(startTime, endTime, timeNodeType);
				TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
				List<IGroupResult> list = new ArrayList<IGroupResult>();
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
						if (maxPosition <= iGroupResult.getSubGroups().size()) {
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
				String time = maxName;
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
	@RequestMapping(value = "/dataType")
	public void dataType(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		String[] json = new String[3];
		try {

			Flags.local_flag = local_flag;
			Flags.filePath = filePath;

			printWriter = response.getWriter();
			StringBuilder dataResult = new StringBuilder();
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/dataType.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/dataType.txt";
			}
			log.info("dataType"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				dataResult.append(line);
			}
			StatsView dataView = JSON.parseObject(dataResult.toString(), StatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(dataView.getCode())) {
				List<Stat> mgList = dataView.getStatsList().get(0).getStatList();
				if (CollectionUtils.isNotEmpty(mgList)) {
					List<IGroupResult> list = new ArrayList<IGroupResult>();
					IGroupResult igr1 = new IGroupResult();
					igr1.setGroupValue(mgList.get(0).getName());
					igr1.setTotal(mgList.get(0).getNum().intValue());
					list.add(igr1);
					IGroupResult igr2 = new IGroupResult();
					igr2.setGroupValue(mgList.get(1).getName());
					igr2.setTotal(mgList.get(1).getNum().intValue());
					list.add(igr2);
					EChartsAdapter adapter = new EChartsAdapter(list, "pie", "情感分析");
					json[0] = JSONObject.toJSONString(adapter.getChart());
				} else {
					json[0] = "";
				}
				List<Stat> jwList = dataView.getStatsList().get(1).getStatList();
				if (CollectionUtils.isNotEmpty(jwList)) {
					List<IGroupResult> list = new ArrayList<IGroupResult>();
					IGroupResult igr1 = new IGroupResult();
					igr1.setGroupValue(jwList.get(0).getName());
					igr1.setTotal(jwList.get(0).getNum().intValue());
					list.add(igr1);
					IGroupResult igr2 = new IGroupResult();
					igr2.setGroupValue(jwList.get(1).getName());
					igr2.setTotal(jwList.get(1).getNum().intValue());
					list.add(igr2);
					EChartsAdapter adapter = new EChartsAdapter(list, "pie", "境内外分布");
					json[1] = JSONObject.toJSONString(adapter.getChart());
				} else {
					json[1] = "";
				}
				List<Stat> dyList = dataView.getStatsList().get(2).getStatList();
				if (CollectionUtils.isNotEmpty(dyList)) {
					json[2] = getMapJSON(dyList, 2);
				} else {
					json[2] = "";
				}
				printWriter.print(JSON.toJSON(json));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("dataType"+JSONObject.toJSONString(e));
		}
		String nullStr = null;
		printWriter.print(nullStr);
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
	public static String getMapJSON(List<Stat> list, int type) {
		if (list == null || list.size() == 0) {
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
	@RequestMapping(value = "/mediaMap")
	public void mediaMap(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		Object[] json = new Object[2];
		try {
			Flags.local_flag = local_flag;
			Flags.filePath = filePath;

			printWriter = response.getWriter();
			StringBuilder mediaResult = new StringBuilder();
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/mediaMap.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/mediaMap.txt";
			}
			log.info("mediaMap"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				mediaResult.append(line);
			}
			ExtendStatsView mediaView = JSON.parseObject(mediaResult.toString(), ExtendStatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(mediaView.getCode())) {
				List<Stats> statsList = mediaView.getExtendStats().getStatsList();
				List<Stat> list1 = null;
				for (Stats stats : statsList) {
					if (stats.getType() == 1) {
						list1 = stats.getStatList();
						break;
					}
				}
				if (CollectionUtils.isNotEmpty(list1)) {
					json[0] = list1;
				}
				List<Stat> list2 = null;
				for (Stats stats : statsList) {
					if (stats.getType() == 2) {
						list2 = stats.getStatList();
						break;
					}
				}
				List<IGroupResult> list3 = new ArrayList<IGroupResult>();
				if (CollectionUtils.isNotEmpty(list2)) {
					for (Stat stat : list2) {
						IGroupResult igr = new IGroupResult();
						igr.setTotal( stat.getNum().intValue());
						igr.setGroupValue(stat.getName());
						list3.add(igr);
					}
				}
				EChartsAdapter adapter = new EChartsAdapter(list3, "eventbar", "媒体活跃度");
				json[1] = JSONObject.toJSONString(adapter.getChart());
				printWriter.print(JSON.toJSON(json));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("mediaMap"+JSONObject.toJSONString(e));
		}
		String nullStr = null;
		printWriter.print(nullStr);
	}
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
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/wordCloud.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/wordCloud.txt";
			}
			log.info("wordCloud"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				cloudResult.append(line);
			}
			ExtendsStatsView cloudView = JSON.parseObject(cloudResult.toString(), ExtendsStatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(cloudView.getCode())) {
				List<Stat> list = cloudView.getExtendsStats().getExtendStatsList().get(0).getStatsList().get(0)
						.getStatList();
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
			e.printStackTrace();
			log.info("wordCloud"+JSONObject.toJSONString(e));
		}
		String nullStr = null;
		printWriter.print(nullStr);
	}
	@RequestMapping(value = "/hotNews")
	public void hotNews(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		String json = null;
		try {
			printWriter = response.getWriter();
			StringBuilder hotResult = new StringBuilder();
			String filePath = "";
			Flags.local_flag = local_flag;
			Flags.filePath = filePath;

			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/hotNews.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/hotNews.txt";
			}
			log.info("hotNews"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				hotResult.append(line);
			}
			ExtendStatsView hotView = JSON.parseObject(hotResult.toString(), ExtendStatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(hotView.getCode())) {
				List<IContentCommonNet> list = hotView.getExtendStats().getiContentCommonNetLists().get(0)
						.getiContentCommonNetList();
				if (CollectionUtils.isNotEmpty(list)) {
					printWriter.print(JSON.toJSON(list));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("hotNews"+JSONObject.toJSONString(e));
		}
		printWriter.print(json);
	}
	@RequestMapping(value = "/hotPeople")
	public void hotPeople(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		String json = null;
		try {
			printWriter = response.getWriter();
			StringBuilder peopleResult = new StringBuilder();

			Flags.local_flag = local_flag;
			Flags.filePath = filePath;


			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/hotPeople.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/hotPeople.txt";
			}
			log.info("hotPeople"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				peopleResult.append(line);
			}
			ExtendStatsView peopleView = JSON.parseObject(peopleResult.toString(), ExtendStatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(peopleView.getCode())) {
				List<IContentCommonNets> list = peopleView.getExtendStats().getiContentCommonNetLists();
				if (CollectionUtils.isNotEmpty(list)) {
					List<IContentCommonNet> inl = list.get(0).getiContentCommonNetList();
					String txt;
					if (inl.size() == 0) {
						txt = "";
					} else if (inl.size() == 1) {
						txt = "<div style='text-indent:2em;width:100%;float:left;'>热点网民统计中，最活跃的网民是"
								+ inl.get(0).getAuthor() + "，可作为重点监控对象。" + inl.get(0).getAuthor() + "发布文章"
								+ inl.get(0).getNum() + "篇。</div>";
					} else if (inl.size() == 2) {
						txt = "<div style='text-indent:2em;width:100%;float:left;'>热点网民统计中，最活跃的网民排行分别是"
								+ inl.get(0).getAuthor() + "、" + inl.get(1).getAuthor() + "，可作为重点监控对象。其中"
								+ inl.get(0).getAuthor() + "发布文章" + inl.get(0).getNum() + "篇，" + inl.get(1).getAuthor()
								+ "发布文章" + inl.get(1).getNum() + "篇。</div>";
					} else {
						txt = "<div style='text-indent:2em;width:100%;float:left;'>热点网民统计中，最活跃的网民排行分别是"
								+ inl.get(0).getAuthor() + "、" + inl.get(1).getAuthor() + "、" + inl.get(2).getAuthor()
								+ "，可作为重点监控对象。其中" + inl.get(0).getAuthor() + "发布文章" + inl.get(0).getNum() + "篇，"
								+ inl.get(1).getAuthor() + "发布文章" + inl.get(1).getNum() + "篇，" + inl.get(2).getAuthor()
								+ "发布文章" + inl.get(2).getNum() + "篇。</div>";
					}
					Object[] objects = new Object[2];
					objects[0] = txt;
					objects[1] = list;
					printWriter.print(JSON.toJSON(objects));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("hotPeople"+JSONObject.toJSONString(e));
		}
		printWriter.print(json);
	}
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
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/propagationPath.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/propagationPath.txt";
			}
			log.info("propagationPath"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				pathResult.append(line);
			}
			StatListView pathView = JSON.parseObject(pathResult.toString(), StatListView.class);
			if (CodeConstant.SUCCESS_CODE.equals(pathView.getCode())) {
				StatList statList = pathView.getStatList();
				if (statList != null) {
					String[] arr = new String[2];
					arr[0] = "{data:[" + TaskJob.genaretPathData(statList) + "]}";
					List<StatList> statList1 = statList.getStatListList();
					IContentCommonNet first = statList1.get(0).getStat().getIContentCommonNet();
					String shortTitle = "";
					if (first.getTitle().length() > 300) {
						shortTitle = first.getTitle().substring(0, 300) + "...";
						first.setTitle(shortTitle);
					}
					String txt = "<div style='width:100%;float:left;text-indent:2em;'>从上图可看出，该事件的首篇相关报道于"
							+ DateUtils.format(first.getPublished(), DateUtils.FORMAT_LONG_CN) + "在"
							+ first.getCaptureWebsiteName() + "发布，报道标题为:" + first.getTitle() + "。";
					if (statList1.size() > 1) {
						Stat burst = statList1.get(1).getStat();
						txt += "而后，更多相关报道出现，并通过" + burst.getName() + "进行传播，经过这些网的传播，将事件扩散化，可将" + burst.getName()
								+ "这一批网站进行重点监控。</div>";
					}
					arr[1] = txt;
					printWriter.print(JSON.toJSON(arr));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("propagationPath"+JSONObject.toJSONString(e));
		}
		printWriter.print(json);
	}
	@RequestMapping(value = "/relatedWord")
	public void relatedWord(String reportTime,String tickets,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		String json = null;
		try {
			Flags.local_flag = local_flag;
			Flags.filePath = filePath;
			printWriter = response.getWriter();
			StringBuilder wordResult = new StringBuilder();
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/relatedWord.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/relatedWord.txt";
			}
			log.info("relatedWord"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				wordResult.append(line);
			}
			StatsView wordView = JSON.parseObject(wordResult.toString(), StatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(wordView.getCode())) {
				List<Stat> list = wordView.getStatsList().get(0).getStatList();
				if (CollectionUtils.isNotEmpty(list)) {
					printWriter.print(JSON.toJSON(list));
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("relatedWord"+JSONObject.toJSONString(e));
		}
		printWriter.print(json);
	}
	@RequestMapping(value = "/typicalViews")
	public void typicalViews(String reportTime,String tickets,HttpServletRequest request,HttpServletResponse response) {
		response.setContentType("application/json;charset=GBK;");
		PrintWriter printWriter = null;
		String[] json = new String[7];
		String newsTitle = "";
		String weiboTitle = "";
		IncidentAnalysisTask task = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ticket", tickets);
		params.put("platform", BusinessConstant.PLATFORM_H5);
		String rtnStr = Utils.sendIntraBusinessAPIPOST(request,"eventGetTask", params);
		if (StringUtils.isNotBlank(rtnStr)) {
			EventTaskRes taskRes = JSON.parseObject(rtnStr, EventTaskRes.class);
			if (taskRes != null && CodeConstant.SUCCESS_CODE.equals(taskRes.getCode())) {
				task = taskRes.getTask();
			}
		}
		if (task == null) {
			return;
		}
		try {
			Flags.local_flag = local_flag;
			Flags.filePath = filePath;
			printWriter = response.getWriter();
			StringBuilder typicalResult = new StringBuilder();
			String filePath = "";
			if (Flags.local_flag) {
				filePath = Flags.filePath + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/typicalViews.txt";
			} else {
				filePath = SysConfig.cfgMap.get("FILE_UPLOAD_PATH") + "eventAnalysis/" + reportTime + "/" + tickets
						+ "/typicalViews.txt";
			}
			log.info("typicalViews"+filePath);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				typicalResult.append(line);
			}
			StatsView typicalView = JSON.parseObject(typicalResult.toString(), StatsView.class);
			if (CodeConstant.SUCCESS_CODE.equals(typicalView.getCode())) {
				List<Stats> statsList = typicalView.getStatsList();
				if (CollectionUtils.isNotEmpty(statsList)) {
					List<Stat> newsList = null;
					List<Stat> forumList = null;
					List<Stat> weiboList = null;
					for (Stats stats : statsList) {
						if (stats.getType() == 1) {
							newsList = stats.getStatList();
							Integer size = newsList.size();
							if (newsList.size()>=10){
								size = 10;
							}
							List<IGroupResult> list = new ArrayList<IGroupResult>();
							for (int i = 0; i <= size-1; i++) {
								Stat stat = newsList.get(i);
								/*
								 * IdvalueBean idvalueBean = stat.getIdbeanlst().get(0); String name
								 * = idvalueBean.getContent();
								 */
								String name = stat.getName();
								name = name.length() > 30 ? name.substring(0, 30) : name;
								if (i == 0) {
									newsTitle = name;
								}
								IGroupResult igr1 = new IGroupResult();
								igr1.setGroupValue(name + " " + stat.getPercentStr());
								igr1.setTotal( stat.getNum().intValue());
								list.add(igr1);
							}
							EChartsAdapter adapter = new EChartsAdapter(list, "eventbar3", "新闻观点分析");
							json[0] = JSONObject.toJSONString(adapter.getChart());
							json[4] = JSONObject.toJSONString(newsList);
						}
						if (stats.getType() == 2) {
							forumList = stats.getStatList();
							Integer size = forumList.size();
							if (forumList.size()>=10){
								size = 10;
							}
							List<IGroupResult> list = new ArrayList<IGroupResult>();
							for (int i = 0; i <= size - 1; i++) {
								/*
								 * IdvalueBean idvalueBean = stat.getIdbeanlst().get(0); String name
								 * = idvalueBean.getContent();
								 */
								Stat stat = forumList.get(i);
								String name = stat.getName();
								name = name.length() > 30 ? name.substring(0, 30) : name;
								IGroupResult igr1 = new IGroupResult();
								igr1.setGroupValue(name + " " + stat.getPercentStr());
								igr1.setTotal( stat.getNum().intValue());
								list.add(igr1);
							}
							EChartsAdapter adapter = new EChartsAdapter(list, "eventbar3", "论坛观点分析");
							json[1] = JSONObject.toJSONString(adapter.getChart());
							json[5] = JSONObject.toJSONString(forumList);
						}
						if (stats.getType() == 3) {
							weiboList = stats.getStatList();
							Integer size = weiboList.size();
							if (weiboList.size()>=10){
								size = 10;
							}
							List<IGroupResult> list = new ArrayList<IGroupResult>();
							for (int i = 0; i <= size - 1; i++) {
								Stat stat = weiboList.get(i);
								/*
								 * IdvalueBean idvalueBean = stat.getIdbeanlst().get(0); String name
								 * = idvalueBean.getContent();
								 */
								String name = stat.getName();
								name = name.length() > 30 ? name.substring(0, 30) : name;
								if (i == 0) {
									weiboTitle = name;
								}
								IGroupResult igr1 = new IGroupResult();
								igr1.setGroupValue(name);
								// igr1.setTotal((int)stat.getNum());
								igr1.setCalculatedValue(stat.getTotal());
								list.add(igr1);
							}
							EChartsAdapter adapter = new EChartsAdapter(list, "eventpie2", "微博观点分析");
							json[2] = JSONObject.toJSONString(adapter.getChart());
							json[6] = JSONObject.toJSONString(weiboList);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("typicalViews"+JSONObject.toJSONString(e));
		}
		String summary = "综上所述，在『" + task.getIncidentTitle() + "』事件/话题中，媒体主流报道为『" + newsTitle + "』，网民主流意见为『"
				+ weiboTitle + "』；应深入挖掘网民意见和情感倾向，识别事件传播过程中的意见领袖和主要信息来源，预测或追踪舆论走向，以便对不良舆论进行疏导。";
		json[3] = summary;
		printWriter.print(JSON.toJSON(json));
	}
}