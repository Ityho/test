package com.miduchina.wrd.monitor.report.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.miduchina.wrd.dto.user.UserOperateLogDto;
import com.miduchina.wrd.monitor.report.common.CommonUtil;
import com.miduchina.wrd.monitor.report.common.EChartsAdapter;
import com.miduchina.wrd.monitor.report.common.OperateLogUtil;
import com.miduchina.wrd.monitor.report.pojos.*;
import com.miduchina.wrd.monitor.report.service.ReportService;
import com.miduchina.wrd.po.report.KeywordReportRecord;
import com.miduchina.wrd.po.user.UserOperateLog;
import com.miduchina.wrd.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 罗朝州 on 2016/7/25.
 */
@Slf4j
@Controller
public class ReportViewController {

	@Autowired
	private ReportService reportService;
	
	@Value("${uploadPath}")
	private String uploadPath;

	@RequestMapping(value = "/")
	@ResponseBody
	public String report(){
		return "0000";
	}

	@RequestMapping(value = "/dayReportView.shtml")
	public String reportView(String code, Integer channelTag, Integer shareType, Integer loginType,
							 HttpServletRequest request, Map<String, Object> map, Integer reportModel) {
		long startTime = System.currentTimeMillis();
		try {
			if (code == null || "".equals(code)) {
				return " ";
			}
			/*
			 * if(request.getHeader("user-agent").indexOf("Jakarta Commons-HttpClient")!=-1){
			 * log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new
			 * Date())+"------weiboReportView return code:"+code); return " "; }
			 */
			StringBuilder result = new StringBuilder();
			KeywordReportRecord keywordReportRecord = reportService.findKeywordReportByCode(code);
			long loadRecordTime = System.currentTimeMillis();
			System.out
					.println("------code:" + code + " load record cost time " + (loadRecordTime - startTime) + "-----");
			if (keywordReportRecord == null) {
             log.info("keywordReportRecord为空!");
				return "";
			} else if (keywordReportRecord.getIsRead() == 0) {
				keywordReportRecord.setIsRead(1);
				reportService.updateIsRead(keywordReportRecord.getId());
			}
			reportModel = keywordReportRecord.getDailyReportModel();
			if (reportModel == null) {
				reportModel = 1;
			}

			UserOperateLog userOperateLog = reportService.queryUserInfo(keywordReportRecord.getUserId());
			UserOperateLogDto operateLogUserInfo = BeanUtils.copyProperties(userOperateLog, UserOperateLogDto.class);

			if (channelTag != null && uploadPath.indexOf("files.wyq.cn") != -1
					|| uploadPath.indexOf("files.51wyq.cn") != -1) {
				OperateLogDayReportInfo dayReportInfo = new OperateLogDayReportInfo();
				dayReportInfo.setUserId(operateLogUserInfo.getUserId());
				dayReportInfo.setReportId(keywordReportRecord.getId());
				dayReportInfo.setShareCode(code);
				dayReportInfo.setSendTime(
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(keywordReportRecord.getCreateTime()));
				OperateLogUtil.opreateLog(request, operateLogUserInfo, 10001, 1, dayReportInfo, null, channelTag);
			}
			if (loginType != null && loginType == 1) {
				map.put("userHead", operateLogUserInfo.getUserHead());
				map.put("loginType", loginType);
			}
			map.put("userHead", operateLogUserInfo.getUserHead());

			if (shareType != null && shareType == 1) {
				map.put("shareType", shareType);
			}
			map.put("keywordReportRecord", keywordReportRecord);
			String nickname = operateLogUserInfo.getNickname();
			if (nickname == null || "".equals(nickname)) {
				String mobile = operateLogUserInfo.getMobile();
				if (mobile == null || "".equals(mobile)) {
					nickname = "";
				} else if (mobile.length() > 10) {
					nickname = mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length());
				}
			}
			map.put("nickname", nickname);
			long loadNickNameTime = System.currentTimeMillis();
			log.info( "------code:" + code + " load nickname cost time " + (loadNickNameTime - loadRecordTime) + "-----");
			Date createDate = keywordReportRecord.getCreateTime();
			String reportName = keywordReportRecord.getReportName().split("标准化")[0];
			map.put("reportNameAll", reportName);
			reportName = reportName.length() > 8 ? reportName.substring(0, 8) + "..." : reportName;
			map.put("reportName", reportName);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(keywordReportRecord.getCreateTime());
			calendar.add(Calendar.DATE, -1);
			String reportTime = new SimpleDateFormat("yyyy年MM月dd日").format(calendar.getTime());
			map.put("reportTime", reportTime);
			calendar.set(2018, 0, 11, 16, 00);// 2016 0809 17:00 变改文件路径 by lcz
			// Properties pro = getProperties("/day_report_view.properties");
			String filePath = uploadPath + "keywordDayReport/" + new SimpleDateFormat("yyyyMM").format(createDate)
					+ "/" + new SimpleDateFormat("dd").format(createDate) + "/"
					+ new SimpleDateFormat("HH").format(createDate) + "/"
					+ new SimpleDateFormat("mm").format(createDate) + "/" + keywordReportRecord.getKeywordId() + "/";
			if (calendar.getTime().getTime() > createDate.getTime()) {
				filePath = filePath + "reportData.txt";
				log.info("当前时间>创建时间" + filePath);
			} else {
				log.info("路径啊" + filePath);
//				Map<String, Object> reportData = CommonUtil.getData("F:\\cccccc\\");
				Map<String, Object> reportData = CommonUtil.getData(filePath);
				List<Stat> sourceTypeNum = (List<Stat>) reportData.get("sourceTypeNum");
				log.info("sourceTypeNum:"+JSON.toJSONString(sourceTypeNum));
				int totalNum = 0;
				if (sourceTypeNum != null && sourceTypeNum.size() > 0) {
					for (Stat stat : sourceTypeNum) {
						totalNum += stat.getNum();
					}
				}
				map.put("totalNum", totalNum);
				map.put("sources", sourceTypeNum);
				map.put("fwbList", reportData.get("fwbList"));
				map.put("wbList", reportData.get("wbList"));
				// 微博list
				DecimalFormat df = new DecimalFormat("0.00");
				List<IContentCommonNet> wbList = (List<IContentCommonNet>) reportData.get("wbList");
				map.put("mgTitle", "微博重点内容");
				log.info("微博list:"+JSON.toJSONString(wbList));
				int mgsum = 0;
				int count = 0;
				if (!CollectionUtils.isEmpty(wbList)) {
					if (wbList.size() > 10) {
						count = 10;
					} else {
						count = wbList.size();
					}
					for (int i = 0; i < wbList.size(); i++) {
						mgsum += wbList.get(i).getRepeatNum();
					}
					for (int i = 0; i < wbList.size(); i++) {
						if ((float) wbList.get(i).getRepeatNum() / mgsum * 100 < 1) {
							wbList.get(i).setPercentStr(
									new BigDecimal(df.format((float) wbList.get(i).getRepeatNum() / mgsum * 100))
											.setScale(1, BigDecimal.ROUND_HALF_UP) + "%");
						} else {
							wbList.get(i).setPercentStr(
									new BigDecimal(df.format((float) wbList.get(i).getRepeatNum() / mgsum * 100))
											.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
						}
					}
				}
				map.put("wbTop", count);
				map.put("showFlag", "none");
				map.put("wbList", wbList);
				// 非微博list
				List<IContentCommonNet> fwbList = (List<IContentCommonNet>) reportData.get("fwbList");
                log.info("非微博list:"+JSON.toJSONString(wbList));
				int fmsum = 0;
				int count2 = 0;
				if (!CollectionUtils.isEmpty(fwbList)) {
					if (fwbList.size() > 10) {
						count2 = 10;
					} else {
						count2 = fwbList.size();
					}
					for (int i = 0; i < fwbList.size(); i++) {
						fmsum += fwbList.get(i).getRepeatNum();
					}
					for (int i = 0; i < fwbList.size(); i++) {
						if ((float) fwbList.get(i).getRepeatNum() / fmsum * 100 < 1) {
							fwbList.get(i).setPercentStr(
									new BigDecimal(df.format((float) fwbList.get(i).getRepeatNum() / fmsum * 100))
											.setScale(1, BigDecimal.ROUND_HALF_UP) + "%");
						} else {
							fwbList.get(i).setPercentStr(
									new BigDecimal(df.format((float) fwbList.get(i).getRepeatNum() / fmsum * 100))
											.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
						}
					}
					log.info("执行了");
				}
				map.put("fwbTop", count2);
				map.put("fwbList", fwbList);
				if(CollectionUtils.isEmpty(wbList)&&CollectionUtils.isEmpty(fwbList)){
					map.put("showFlag", "block");
				}else{
					map.put("showFlag", "none");
				}
//				map.put("showFlag", "none");
				map.put("filterNum", 0);
				return "view/dayReportView_v3";
			}

			// log.info("----time1----"+calendar.getTime().getTime());
			// filePath="e:/reportData.txt";
			calendar.set(2017, 1, 15, 2, 00);
			String line = null;
			// log.info("----time2----"+calendar.getTime().getTime());
			// log.info("----createDate----"+createDate.getTime());
			// String reportTime2 = new SimpleDateFormat("yyyy年MM月dd日").format(createDate);
			// log.info("---calendar.getTime()--"+new
			// SimpleDateFormat("yyyy年MM月dd日").format(calendar.getTime()));
			// log.info("---reportTime2--"+reportTime2);
			//String str = "/home/web/webfiles/files.wyq.cn/keywordDayReport/201801/11/01/15/5748302/reportData.txt";
			if (calendar.getTime().compareTo(createDate) > 0) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "GBK"));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
				//CommonFile.readTxt(filePath, "GBK");
			} else {
				log.info("------------to UTF-8--------------");
				log.info(filePath);
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "utf-8"));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
				//CommonFile.readTxt(str, "utf-8");
				log.info("------------to UTF-8 result--------------" + result.toString());
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			// 这里时间可以自己定
			Date oldDate1 = format.parse("2016-05-10 15:16:00");
			// 这里时间可以自己定
			Date oldDate2 = format.parse(format.format(createDate));
			// 判断,如果时间在这时间后,就执行后面操作
			log.info(String.valueOf(oldDate1.compareTo(oldDate2)));
			if (oldDate1.compareTo(oldDate2) > 0) {
				reportModel = 1;
			}
			log.info(result.toString());
			ExtendStatsView extendStatsView = JSON.parseObject(result.toString(), ExtendStatsView.class);
			if ("0000".equals(extendStatsView.getCode())) {
				List<Stats> statsList = extendStatsView.getExtendStats().getStatsList();
				List<Stat> mgNumList;
				if (reportModel == 5) {
					mgNumList = statsList.get(5).getStatList();
				} else if (reportModel == 4) {
					mgNumList = statsList.get(0).getStatList();
				} else {
					mgNumList = statsList.get(3).getStatList();
				}
				int mgNum = 0;
				int fmNum = 0;
				String showFlag = "none";
				if (!CollectionUtils.isEmpty(mgNumList)) {
					int totalNum = 0;
					if (reportModel == 4) {
						totalNum = mgNumList.get(11).getNum();
						map.put("totalNum", totalNum);
					} else {
						mgNum = mgNumList.get(0).getNum();
						fmNum = mgNumList.get(1).getNum();
						totalNum = mgNum + fmNum;
						map.put("totalNum", totalNum);
						map.put("mgNum", mgNum);
						map.put("fmNum", fmNum);
					}
					if (totalNum != 0) {
						if (reportModel != 4) {
							map.put("mgPercent", new BigDecimal(mgNumList.get(0).getPercent())
									.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
							map.put("fmPercent", new BigDecimal(mgNumList.get(1).getPercent())
									.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
						}
					}
					if (totalNum < 30 && totalNum > 0) {
						List<Stat> mgList;
						if (reportModel == 5) {
							mgList = statsList.get(7).getStatList();
						} else {
							mgList = statsList.get(6).getStatList();
						}
						int jlsum = 0;
						map.put("mgList", mgList);
						map.put("mgTitle", "信息列表");
						for (int i = 0; i < mgList.size(); i++) {
							jlsum += mgList.get(i).getNum();
						}
						int filterNum = totalNum - jlsum;
						map.put("filterNum", filterNum);
						if (mgList.size() == 0) {
							showFlag = "block";
						}
						map.put("showFlag", showFlag);
						return "dayReportView_1";
					} else if (totalNum == 0) {
						showFlag = "block";
						map.put("showFlag", showFlag);
						if (reportModel == 3) {
							return "view/dayReportView3";
						} else {
							return "view/dayReportView";
						}
					}
				}
				map.put("showFlag", showFlag);
				// 来源类型
				List<Stat> sourceList = statsList.get(0).getStatList();
				log.info("--sourceList--" + sourceList);
				map.put("sources", sourceList);
				// 走势
				if (reportModel == 5) {
					List<Stat> lineStat1 = statsList.get(3).getStatList();
					List<Stat> lineStat2 = statsList.get(4).getStatList();
					if (!CollectionUtils.isEmpty(lineStat2)) {
						List<IGroupResult> list1 = new ArrayList<>();
						for (Stat stat : lineStat1) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list1.add(igr);
						}
						List<IGroupResult> list2 = new ArrayList<>();
						for (Stat stat : lineStat2) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list2.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list1, "line", "信息走势");
						EChartsAdapter adapter2 = new EChartsAdapter(list2, "line", "信息走势");
						// log.info("------json-----"+JSON.toJSONString(adapter.getChart()));
						// log.info("------json2-----"+JSON.toJSONString(adapter2.getChart()));
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						array.add(adapter2.getChart());
						log.info("--array--" + array.toJSONString());
						map.put("lineChat", array.toJSONString());
					}
					List<Stat> mgwordCloud = statsList.get(2).getStatList();
					map.put("mgwordCloud", mgwordCloud);
					List<Stat> fmgwordCloud = statsList.get(1).getStatList();
					map.put("fmgwordCloud", fmgwordCloud);
					// 敏感list
					DecimalFormat df = new DecimalFormat("0.00");
					List<Stat> wbList = statsList.get(7).getStatList();
					map.put("mgTitle", "重点敏感信息");
					int mgsum = 0;
					int count = 0;
					if (wbList != null) {
						if (wbList.size() > 10) {
							count = 10;
						} else {
							count = wbList.size();
						}
						map.put("wbTop", count);
					}
					map.put("wbList", wbList);
					// 非敏感list
					List<Stat> fwbList = statsList.get(6).getStatList();
					int fmsum = 0;
					int count2 = 0;
					if (fwbList != null) {
						if (fwbList.size() > 10) {
							count2 = 10;
						} else {
							count2 = fwbList.size();
						}
						map.put("fwbTop", count2);
						map.put("fwbList", fwbList);
					}
					int filterfmNum = fmNum - fmsum; // 计算排除垃圾数据条数
					int filterNum = mgNum - mgsum; // 计算排除垃圾数据条数
					map.put("filterfmNum", filterfmNum);
					map.put("filterNum", filterNum);
				} else if (reportModel == 4) {
					// 近三天
					List<Stat> lineStat3 = statsList.get(1).getStatList();
					if (!CollectionUtils.isEmpty(lineStat3)) {
						List<IGroupResult> list3 = new ArrayList<>();
						for (Stat stat : lineStat3) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list3.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list3, "line", "近三天信息走势");
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						// log.info("--array--"+array.toJSONString());
						map.put("lineChat2", array.toJSONString());
					}
					// 媒体活跃度
					List<Stat> lineStat4 = statsList.get(5).getStatList();
					if (!CollectionUtils.isEmpty(lineStat4)) {
						List<IGroupResult> list4 = new ArrayList<>();
						for (Stat stat : lineStat4) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName());
							igr.setTotal(stat.getNum());
							list4.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list4, "bar2", "媒体活跃度");
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						log.info("--arrayBar--" + array.toJSONString());
						map.put("barChat", array.toJSONString());
					}
					// 敏感非敏感趋势
					List<Stat> lineStat1 = statsList.get(2).getStatList();
					List<Stat> lineStat2 = statsList.get(3).getStatList();
					if (!CollectionUtils.isEmpty(lineStat2)) {
						List<IGroupResult> list1 = new ArrayList<IGroupResult>();
						for (Stat stat : lineStat1) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list1.add(igr);
						}
						List<IGroupResult> list2 = new ArrayList<IGroupResult>();
						for (Stat stat : lineStat2) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list2.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list1, "line", "信息走势");
						EChartsAdapter adapter2 = new EChartsAdapter(list2, "line", "信息走势");
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						array.add(adapter2.getChart());
						log.info("--array--" + array.toJSONString());
						map.put("lineChat", array.toJSONString());
					}
					for (int i = 0; i < lineStat2.size(); i++) {
						mgNum += lineStat2.get(i).getNum();
					}
					for (int i = 0; i < lineStat1.size(); i++) {
						fmNum += lineStat1.get(i).getNum();
					}
					map.put("mgNum", mgNum);
					map.put("fmNum", fmNum);
					// 地域分布图
					List<Stat> lineStat5 = statsList.get(4).getStatList();
					if (!CollectionUtils.isEmpty(lineStat5)) {
						List<IGroupResult> list5 = new ArrayList<>();
						for (Stat stat : lineStat5) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName());
							igr.setTotal(stat.getNum());
							list5.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list5, "map2", "地域分布图");
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						log.info("--arrayMap--" + array.toJSONString());
						map.put("mapChat", array.toJSONString());
					}
					// 敏感list
					List<Stat> wbList = statsList.get(7).getStatList();
					map.put("mgTitle", "重点敏感信息");
					map.put("wbList", wbList);
					// 非敏感list
					List<Stat> fwbList = statsList.get(6).getStatList();
					if (fwbList != null) {
						map.put("fwbList", fwbList);
					}
				} else {
					List<Stat> lineStat1 = statsList.get(1).getStatList();
					List<Stat> lineStat2 = statsList.get(2).getStatList();
					if (!CollectionUtils.isEmpty(lineStat2)) {
						List<IGroupResult> list1 = new ArrayList<>();
						for (Stat stat : lineStat1) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list1.add(igr);
						}
						List<IGroupResult> list2 = new ArrayList<>();
						for (Stat stat : lineStat2) {
							IGroupResult igr = new IGroupResult();
							igr.setGroupValue(stat.getName() + ":00:00");
							igr.setTotal(stat.getNum());
							list2.add(igr);
						}
						EChartsAdapter adapter = new EChartsAdapter(list1, "line", "信息走势");
						EChartsAdapter adapter2 = new EChartsAdapter(list2, "line", "信息走势");
						JSONArray array = new JSONArray();
						array.add(adapter.getChart());
						array.add(adapter2.getChart());
						log.info("--array--" + array.toJSONString());
						map.put("lineChat", array.toJSONString());
					}
					// 微博list
					DecimalFormat df = new DecimalFormat("0.00");
					List<Stat> wbList = statsList.get(5).getStatList();
					map.put("mgTitle", "微博重点内容");
					int mgsum = 0;
					int count = 0;
					if (wbList != null) {
						if (wbList.size() > 10) {
							count = 10;
						} else {
							count = wbList.size();
						}
						for (int i = 0; i < wbList.size(); i++) {
							mgsum += wbList.get(i).getNum();
						}
						for (int i = 0; i < wbList.size(); i++) {
							if ((float) wbList.get(i).getNum() / mgsum * 100 < 1) {
								wbList.get(i).setPercentStr(
										new BigDecimal(df.format((float) wbList.get(i).getNum() / mgsum * 100))
												.setScale(1, BigDecimal.ROUND_HALF_UP) + "%");
							} else {
								wbList.get(i).setPercentStr(
										new BigDecimal(df.format((float) wbList.get(i).getNum() / mgsum * 100))
												.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
							}
						}
						map.put("wbTop", count);
					}
					map.put("wbList", wbList);
					// 非微博list
					List<Stat> fwbList = statsList.get(4).getStatList();
					int fmsum = 0;
					int count2 = 0;
					if (fwbList != null) {
						if (fwbList.size() > 10) {
							count2 = 10;
						} else {
							count2 = fwbList.size();
						}
						for (int i = 0; i < fwbList.size(); i++) {
							fmsum += fwbList.get(i).getNum();
						}
						for (int i = 0; i < fwbList.size(); i++) {
							if ((float) fwbList.get(i).getNum() / fmsum * 100 < 1) {
								fwbList.get(i).setPercentStr(
										new BigDecimal(df.format((float) fwbList.get(i).getNum() / fmsum * 100))
												.setScale(1, BigDecimal.ROUND_HALF_UP) + "%");
							} else {
								fwbList.get(i).setPercentStr(
										new BigDecimal(df.format((float) fwbList.get(i).getNum() / fmsum * 100))
												.setScale(0, BigDecimal.ROUND_HALF_UP) + "%");
							}
						}
						map.put("fwbTop", count2);
						map.put("fwbList", fwbList);
					}
					int filterfmNum = fmNum - fmsum; // 计算排除垃圾数据条数
					int filterNum = mgNum - mgsum; // 计算排除垃圾数据条数
					map.put("filterfmNum", filterfmNum);
					map.put("filterNum", filterNum);
				}
			}
			long endTime = System.currentTimeMillis();
			log.info("------code:" + code + " process cost time " + (endTime - loadNickNameTime) + "-----");
			log.info("------code:" + code + " total cost time " + (endTime - startTime) + "-----");
			if (reportModel == 1) {
				return "view/dayReportView";
			} else if (reportModel == 2) {
				return "view/dayReportView2";
			} else if (reportModel == 3) {
				return "view/dayReportView3";
			} else if (reportModel == 4) {
				return "view/dayReportView4";
			} else if (reportModel == 5){
				return "view/dayReportView5";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("错误执行了");

		return "error";
	}

	public static void main(String args[]) {
		int mgNum = 46565;
		int total = 541144;
		String j = new BigDecimal(((float) mgNum / (float) total * 100)).setScale(0, BigDecimal.ROUND_HALF_UP) + "%";
		log.info(j);
	}

	@RequestMapping(value = "/weiboReportView")
	public String weiboReportView(String code, Integer channelTag, Integer shareType, Integer loginType,
								  RedirectAttributes attributes) {
		log.info(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "------weiboReportView code:" + code);
		attributes.addAttribute("code", code);
		attributes.addAttribute("channelTag", channelTag);
		attributes.addAttribute("shareType", shareType);
		attributes.addAttribute("loginType", loginType);
		return "redirect:/dayReportView.shtml";
	}

	@RequestMapping(value = "/recordSign")
	public void recordSign(HttpServletRequest request, Integer userId) {
		try {
			if (uploadPath.indexOf("files.wyq.cn") != -1) {
				UserOperateLog userOperateLog = reportService.queryUserInfo(userId);

				UserOperateLogDto operateLogUserInfo = BeanUtils.copyProperties(userOperateLog, UserOperateLogDto.class);

				OperateLogUtil.operatePageLog(request, operateLogUserInfo, 10002, "日报签到", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/recordDown")
	public void recordDownApp(HttpServletRequest request, Integer userId) {
		try {
			if (uploadPath.indexOf("files.wyq.cn") != -1 || uploadPath.indexOf("files.51wyq.cn") != -1) {
				UserOperateLog userOperateLog = reportService.queryUserInfo(userId);
				UserOperateLogDto operateLogUserInfo = BeanUtils.copyProperties(userOperateLog, UserOperateLogDto.class);
				OperateLogUtil.operatePageLog(request, operateLogUserInfo, 10003, "日报下载APP", null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String removeForwardContent(String sor) {
		String aims = "";
		aims = sor;
		if (sor == null || sor.equals("")) {
			return aims;
		} else if (aims.indexOf("【原微博】") != -1) {
			int pos = aims.indexOf("【原微博】");
			aims = aims.substring(0, pos);
		}
		return aims;
	}
}

