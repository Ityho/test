package com.miduchina.wrd.api.hot.all.service.impl;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.api.hot.all.service.HotService;
import com.xd.tools.common.ErrorCodeConstant;
import com.xd.tools.method.base.BaseMethodV1;
import com.xd.tools.method.sjfx.abilityseal.SjfxAbilitysealMethodV1;
import com.xd.tools.method.sjfx.es.time.SjfxEsTimeMethodV1;
import com.xd.tools.ownutils.GetCommon;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.ownutils.ParamsUtils;
import com.xd.tools.ownutils.StringUtil;
import com.xd.tools.pojo.*;
import com.xd.tools.view.CtksStatsView;
import com.xd.tools.view.IContentCommonNetView;
import com.xd.tools.view.StatView;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 热度 service 实现类
 * 
 * @author lkc
 * @date 2017年4月12日 上午11:33:28
 * @version V1.0
 *
 */
@Service("hotService")
public class HotServiceImpl implements HotService {

	public static final int INTERVAL_DAYS = Integer
			.parseInt(GetCommon.getBusinessConfigMapValue("INTERVAL_DAYS", "100"));

	//////////////////////////////////////////////////////////////////////////
	/**
	 * 统计切分时间段
	 *
	 * @param params
	 * @return List<Map<String, String>> resultList
	 */
	public static List<Map<String, String>> devisionTime(Params params) {
		return devisionTime(params, null);
	}

	/**
	 * 统计切分时间段
	 *
	 * @param params
	 * @param intervalDays
	 * @return List<Map<String, String>> resultList
	 */
	public static List<Map<String, String>> devisionTime(Params params, Integer intervalDays) {
		double nums = 0;
		String fromStr = StringUtils.EMPTY;
		String toStr = StringUtils.EMPTY;
		String startTime = params.getStartTime();
		String endTime = params.getEndTime();
		Integer date = -1;
		Date fromDate = null;
		Date toDate = null;
		String statField = params.getStatField();
		Map<String, String> map = new HashMap<String, String>();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		if (StringUtils.isNotEmpty(statField)) {
			if (date == -1 && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
				fromDate = com.xd.tools.ownutils.DateUtils.parse(startTime);
				toDate = com.xd.tools.ownutils.DateUtils.parse(endTime);
				map = getTimeRange(params);
				if (MapUtils.isNotEmpty(map)) {
					nums = Double.parseDouble(map.get("nums"));
				}
			} else {
				return null;
			}
			intervalDays = (intervalDays == null ? INTERVAL_DAYS : intervalDays);
			if (nums >= intervalDays) {
				int n = (int) Math.ceil(nums / (double) intervalDays);
				Calendar startDateCa = Calendar.getInstance();
				Calendar endDateCa = Calendar.getInstance();
				startDateCa.setTime(fromDate);
				endDateCa.setTime(toDate);
				if (n > 0) {
					for (int i = 0; i < n; i++) {
						if (endDateCa.after(startDateCa)) {
							Map<String, String> timeRangeMap = new HashMap<String, String>();
							fromStr = com.xd.tools.ownutils.DateUtils.format(startDateCa.getTime());
							int dateType = Integer.parseInt(map.get("dateType"));
							startDateCa.add(dateType, +intervalDays);
							toStr = com.xd.tools.ownutils.DateUtils.format(startDateCa.getTime());
							double timeRange = 0;
							if (MapUtils.isNotEmpty(map)) {
								Params par1 = new Params();
								par1.setStartTime(toStr);
								par1.setEndTime(com.xd.tools.ownutils.DateUtils.format(endDateCa.getTime()));
								Map<String, String> rangeMap = getTimeRange(params);
								timeRange = Double.parseDouble(rangeMap.get("nums"));
							}
							if ((timeRange < intervalDays) && (i == n - 1) || startDateCa.after(endDateCa)) {
								toStr = com.xd.tools.ownutils.DateUtils.format(endDateCa.getTime());
							}
							timeRangeMap.put("startTime", fromStr);
							timeRangeMap.put("endTime", toStr);
							resultList.add(timeRangeMap);
						}
					}
				}
			} else {
				Map<String, String> timeRangeMap = new HashMap<String, String>();
				timeRangeMap.put("startTime", startTime);
				timeRangeMap.put("endTime", endTime);
				resultList.add(timeRangeMap);
			}
		}
		return resultList;
	}

	/**
	 * 填充关联词数量和同比 百分比
	 * 
	 * @param analysisTaskTicket
	 * @param list
	 * @param coreNums
	 * @param params
	 * @param percentKeyWord
	 * @param isNeetRatio
	 * @param isSolr
	 * @throws Exception
	 */
	private static void doFillRelatedWordNumAndRatio(String userTag, String analysisTaskTicket, List<RelatedWord> list,
                                                     long coreNums, Params params, String percentKeyWord, boolean isNeetRatio, boolean isSolr) throws Exception {
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		if (list == null || list.size() == 0) {
			return;
		}
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		ParamsUtils.initParamsTime(params);
		long rationStartTimes = formart.parse(params.getStartTime()).getTime();
		long rationEndTimes = formart.parse(params.getEndTime()).getTime();
		rationStartTimes = rationStartTimes - (rationEndTimes - rationStartTimes);
		// 同比开始时间
		String ratioStartTime = formart.format(new Date(rationStartTimes));
		String ratioEndTime = params.getStartTime();
		String keyword = params.getKeyword();
		String filterKeyword = params.getFilterKeyword();
		// 对关键字进行过滤
		if (!StringUtils.isEmpty(keyword)) {
			keyword = StringUtil.conversionString(keyword);
		}
		if (!StringUtils.isEmpty(filterKeyword)) {
			filterKeyword = StringUtil.conversionString(filterKeyword);
		}
		for (RelatedWord word : list) {
			// 敏感词问题
			Integer sensitiveCheck = params.getSensitiveCheck();
			logMap.put("process", "敏感词库获取");
			logMap.put("sensitiveCheck", sensitiveCheck);
			MeUtils.info("doFillRelatedWordNumAndRatio", logMap);
			String sensitiveWord = "";
			// sensitiveCheck是null或者1,排除wyq的敏感词
			if (null == sensitiveCheck || 1 == sensitiveCheck) {
				if (GetCommon.checkSensitiveWordsHot(word.getName()) || GetCommon.checkSensitiveWords(word.getName())) {
					sensitiveWord += "," + word.getName();
					continue;
				}
				// sensitiveCheck==0,排除yqt的敏感词
			} else if (0 == sensitiveCheck) {
				if (GetCommon.checkSensitiveWordsHot(word.getName())
						|| GetCommon.checkSensitiveWordsAclass(word.getName())) {
					sensitiveWord += "," + word.getName();
					continue;
				}
			}
			if (StringUtils.isNotEmpty(sensitiveWord)) {
				sensitiveWord = sensitiveWord.replaceFirst(",", "");
			}
			logMap.put("process", "敏感词校验完毕");
			logMap.put("sensitiveWord", sensitiveWord);
			MeUtils.info("doFillRelatedWordNumAndRatio", logMap);
			Params par1 = (Params) params.clone();
			par1.setKeyword("(" + keyword + ")" + "+" + word.getName());
			// 查询外围词数量
			IContentCommonNetView iccNetView = SjfxEsTimeMethodV1.searchTotalV1_001(userTag, par1);
			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(iccNetView.getCode())) {
				throw new RuntimeException(iccNetView.getMessage());
			}
			long total = iccNetView.getTotalCount();
			word.setNum(total);
			// 获取同比
			if (isNeetRatio) {
				Params par2 = (Params) params.clone();
				par2.setKeyword("(" + keyword + ")" + "+" + word.getName());
				par2.setDate(Params.TIME_OTHER);
				par2.setStartTime(ratioStartTime);
				par2.setEndTime(ratioEndTime);
				IContentCommonNetView ratioIccNetView = SjfxEsTimeMethodV1.searchTotalV1_001(userTag, par2);
				// 如果请求失败
				if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(ratioIccNetView.getCode())) {
					throw new RuntimeException(ratioIccNetView.getMessage());
				}
				long ratioTotal = ratioIccNetView.getTotalCount();
				// 日志map
				logMap.put("analysisTaskTicket", analysisTaskTicket);
				logMap.put("keyword", par2.getKeyword());
				logMap.put("doFillRelatedWordNumAndRatio_total", total);
				logMap.put("doFillRelatedWordNumAndRatio_ratioTotal", ratioTotal);
				MeUtils.info("doFillRelatedWordNumAndRatio", logMap);
				// 增量
				word.setIncrement(total - ratioTotal);
				// 计算同比mul
				double ratio = 0;
				if (ratioTotal > 0) {
					ratio = new BigDecimal(total - ratioTotal)
							.divide(new BigDecimal(ratioTotal), 5, RoundingMode.HALF_DOWN).doubleValue();
				}
				word.setRatio(ratio);
			}
			// 如果是第一次循环，拼接上keyword
			if (percentKeyWord == null || percentKeyWord.length() == 0) {
				// 如果有括号不拼接括号
				if (keyword.startsWith("(") && keyword.endsWith(")")) {
					percentKeyWord = params.getKeyword();
				} else {
					percentKeyWord = "(" + keyword + ")";
				}
			}
			// 当前算百分比keyword
			Params par3 = (Params) params.clone();
			String currentpercentKeyWord = percentKeyWord + "+" + "(" + word.getName() + ")";
			par3.setKeyword(currentpercentKeyWord);
			IContentCommonNetView percentIccNetView = SjfxEsTimeMethodV1.searchTotalV1_001(userTag, par3);
			// 如果请求失败
			if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(percentIccNetView.getCode())) {
				throw new RuntimeException(percentIccNetView.getMessage());
			}
			long percentTotal = percentIccNetView.getTotalCount();
			// 日志map
			LinkedHashMap<String, Object> logMap1 = new LinkedHashMap<String, Object>();
			logMap1.put("analysisTaskTicket", analysisTaskTicket);
			logMap1.put("doFillRelatedWordNumAndRatio1_total", total);
			logMap1.put("doFillRelatedWordNumAndRatio1_coreNums", coreNums);
			logMap1.put("doFillRelatedWordNumAndRatio1_percentTotal", percentTotal);
			logMap1.put("doFillRelatedWordNumAndRatio1_percentKeyWord", currentpercentKeyWord);
			MeUtils.info("doFillRelatedWordNumAndRatio", logMap1);
			// 计算统计百分比 外围词数量/核心词数量
			if (coreNums <= 0 || percentTotal == 0) {
				word.setPercent(1);
			} else {
				double percent = new BigDecimal(percentTotal)
						.divide(new BigDecimal(coreNums), 5, RoundingMode.HALF_DOWN).doubleValue();
				// 如果关联度大于1 设置关联度为1
				percent = percent > 1 ? 1L : percent;
				word.setPercent(percent);
			}
			word.setPercentStr(word.getPercent() * 100 + "");
			// 填充外围词信息
			doFillRelatedWordNumAndRatio(userTag, analysisTaskTicket, word.getRelatedWordList(), percentTotal, params,
					currentpercentKeyWord, isNeetRatio, isSolr);
		}
		// 排序
		Collections.sort(list, new Comparator<RelatedWord>() {

			@Override
			public int compare(RelatedWord o1, RelatedWord o2) {
				if (o1 == null) {
					return -1;
				}
				if (o2 == null) {
					return 1;
				}
				return new Double(o2.getPercent()).compareTo(new Double(o1.getPercent()));
			}
		});
	}

	public static Map<String, String> getTimeRange(Params params) {
		String statField = params.getStatField();
		Map<String, String> map = new HashMap<String, String>();
		double nums = 0;
		int dateType = 0;
		Date fromDate = com.xd.tools.ownutils.DateUtils.parse(params.getStartTime());
		Date toDate = com.xd.tools.ownutils.DateUtils.parse(params.getEndTime());
		if (StringUtils.equals(Params.STATFIELD_PUBLISHEDHOUR, statField)) {
			nums = com.xd.tools.ownutils.DateUtils.deltaTHour(fromDate, toDate);
			dateType = Calendar.HOUR;
		} else if (StringUtils.equals(Params.STATFIELD_PUBLISHEDDAY, statField)) {
			nums = com.xd.tools.ownutils.DateUtils.deltaTDay(fromDate, toDate);
			dateType = Calendar.DATE;
		} else if (StringUtils.equals(Params.STATFIELD_PUBLISHEDMONTH, statField)) {
			nums = com.xd.tools.ownutils.DateUtils.deltaTMonth(fromDate, toDate);
			dateType = Calendar.MONTH;
		}
		map.put("nums", String.valueOf(nums));
		map.put("dateType", String.valueOf(dateType));
		return map;
	}

	/**
	 * 填充聚类分析
	 * 
	 * @Title: doFillAnlyzerComputerInSide
	 * @param list
	 * @throws Exception
	 * @author lkc
	 */
	@Override
	public void doFillAnlyzerComputerInSide(String userTag, List<StatStatList> list, Params params) throws Exception {
		if (list == null || list.isEmpty() || params == null) {
			return;
		}
		params = (Params) params.clone();
		// 创建一个有序的map
		Map<String, StatStatList> statmap = new TreeMap<>(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}

		});
		// 格式化数量，避免出错
		DecimalFormat numformat = new DecimalFormat("00000000000000");
		for (StatStatList stat : list) {
			if (stat == null) {
				continue;
			}
			statmap.put(numformat.format(stat.getTotal() * 1000000) + "_" + stat.hashCode(), stat);
		}
		// 查询聚类
		int maxNum = 5;
		int num = 0;
		for (String key : statmap.keySet()) {
			if (num == maxNum) {
				break;
			}
			StatStatList stat = statmap.get(key);
			String startTime = stat.getName() + " 00:00:00";
			String endTime = stat.getName() + " 23:59:59";
			// 设置数量
			params.setPage(1);
			params.setPagesize(100);
			// 获取聚类
			List<Stat> statList = getAnlyzerComputerInSideV1List(userTag, startTime, endTime, params);
			stat.setStatList(statList);
			num++;
		}
	}

	/**
	 * 填充数量
	 * 
	 * @Title: doFillRelatedWordNum
	 * @param analysisTaskTicket
	 * @param list
	 * @param params
	 * @throws Exception
	 * @author lkc
	 */
	@Override
	public void doFillRelatedWordNum(String userTag, String analysisTaskTicket, List<RelatedWord> list, Params params,
                                     boolean isSolr) throws Exception {
		// 填充
		doFillRelatedWordNumAndRatio(userTag, analysisTaskTicket, list, -1, params, "", false, isSolr);
	}

	/**
	 * 填充关联词数量和同比 百分比
	 * 
	 * @param analysisTaskTicket
	 * @param list
	 * @param params
	 * @param isSolr
	 * @throws Exception
	 */
	@Override
	public void doFillRelatedWordNumAndRatio(String userTag, String analysisTaskTicket, List<RelatedWord> list,
                                             Params params, boolean isSolr) throws Exception {
		// 填充
		doFillRelatedWordNumAndRatio(userTag, analysisTaskTicket, list, -1, params, "", true, isSolr);
	}

	/**
	 * 查询聚类
	 * 
	 * @param startTime
	 * @param endTime
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private List<Stat> getAnlyzerComputerInSideV1List(String userTag, String startTime, String endTime, Params params)
			throws Exception {
		// 克隆
		params = (Params) params.clone();
		String keyword = params.getKeyword();
		String filterKeyword = params.getFilterKeyword();
		// 对关键字进行过滤
		if (!StringUtils.isEmpty(keyword)) {
			keyword = StringUtil.conversionString(keyword);
			params.setKeyword(keyword);
		}
		if (!StringUtils.isEmpty(filterKeyword)) {
			filterKeyword = StringUtil.conversionString(filterKeyword);
			params.setFilterKeyword(filterKeyword);
		}
		// 设置时间
		params.setStartTime(startTime);
		params.setEndTime(endTime);
		params.setComblineflg(Params.COMBLINEFLG_MERGE);
		params.setOrder(Params.ORDER_SIMILAR);
		params.setSort(Params.SORT_DESC + "");
		CommAnalysisComputer commAnalysisComputer = new CommAnalysisComputer();
		commAnalysisComputer.setFimalyNum(1);
		commAnalysisComputer.setNum(1);
		StatView view = SjfxEsTimeMethodV1.anlyzerComputerInSideV1_001(userTag, params, commAnalysisComputer);
		// 如果请求失败
		if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(view.getCode())) {
			throw new RuntimeException(view.getMessage());
		}
		return view.getStatList();
	}

	/**
	 * 获取聚类列表
	 */
	@Override
	public List<Stat> getClustering(String userTag, Params params, Integer samplingNum, Integer fimalyNum, Integer num)
			throws Exception {
		if (samplingNum == null || samplingNum <= 0) {
			samplingNum = 100;
		}
		params.setPage(1);
		params.setPagesize(samplingNum);
		CommAnalysisComputer commAnalysisComputer = new CommAnalysisComputer();
		commAnalysisComputer.setFimalyNum(fimalyNum);
		commAnalysisComputer.setNum(num);
		StatView statView = SjfxEsTimeMethodV1.anlyzerComputerInSideV1_001(userTag, params, commAnalysisComputer);
		// 如果请求失败
		if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(statView.getCode())) {
			throw new RuntimeException(statView.getMessage());
		}
		return statView.getStatList();
	}

	@Override
	public CtksStatsView getHot(String userTag, Params params) throws Exception {
		String function = "getHot()";
		LinkedHashMap<String, Object> logMap = new LinkedHashMap<String, Object>();
		logMap.put("keyword", params.getKeyword());
		params = (Params) params.clone();
		// 初始化时间
		ParamsUtils.initParamsTime(params);
		params.setStatField(Params.STATFIELD_PUBLISHEDDAY);
		// 统计list
		List<Stats> statsList = null;
		long currentTimeMillis = System.currentTimeMillis();
		params.setStatField(Params.STATFIELD_PUBLISHEDHOUR);
		CtksStatsView ctkView = SjfxAbilitysealMethodV1.hotLine(userTag, params, 1);
		logMap.put("step", "AbilitySealMethodV1.hotLine finished");
		logMap.put("startTime", params.getStartTime());
		logMap.put("endTime", params.getEndTime());
		logMap.put("subview", MeUtils.subStrByEnd(JSON.toJSONString(ctkView), 2000));
		MeUtils.showUsedTime(function, logMap, currentTimeMillis, System.currentTimeMillis());
		logMap.remove("subview");
		CategoryTypeKeywords ctk = new CategoryTypeKeywords();
		// 如果请求失败
		if (!ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS.equals(ctkView.getCode())) {
			throw new RuntimeException(ctkView.getMessage());
		}
		// 设置统计数量
		if (statsList == null) {
			statsList = ctkView.getCtksStats().getStatsList();
		} else {
			statsList.get(0).getStatList().addAll(ctkView.getCtksStats().getStatsList().get(0).getStatList());
		}
		// 热度高峰值
		double ratioHotTopCustom = 0;
		// 本次热度值
		double ratioHotSum = 0;
		for (CategoryTypeKeywords c : ctkView.getCtksStats().getCtkList()) {
			// 微博数量
			ctk.setWeibo(ctk.getWeibo() + c.getWeibo());
			// 论坛
			ctk.setLuntan(ctk.getLuntan() + c.getLuntan());
			// web
			ctk.setWeb(ctk.getWeb() + c.getWeb());
			// 微信
			ctk.setWeixin(ctk.getWeixin() + c.getWeixin());
			// 媒体声量
			ctk.setNewsCustom(ctk.getNewsCustom() + c.getNewsCustom());
			// app
			ctk.setApp(ctk.getApp() + c.getApp());
			// 新闻
			ctk.setXinwen(ctk.getXinwen() + c.getXinwen());
			// 博客
			ctk.setBoke(ctk.getBoke() + c.getBoke());
			// 政务
			ctk.setZhengwu(ctk.getZhengwu() + c.getZhengwu());
			// 报刊
			ctk.setBaokan(ctk.getBaokan() + c.getBaokan());
			// 境外
			ctk.setJingwai(ctk.getJingwai() + c.getJingwai());
			// 境外
			ctk.setVideo(ctk.getVideo() + c.getVideo());
			// 数量
			ctk.setNumberCustom(ctk.getNumberCustom() + c.getNumberCustom());
			// 热度峰值
			ratioHotTopCustom = c.getRatioHotTopCustom() < ratioHotTopCustom ? ratioHotTopCustom
					: c.getRatioHotTopCustom();
			// 热度值
			ratioHotSum += c.getRatioHotCustom();
		}
		// 设置热度峰值
		ctk.setRatioHotTopCustom(ratioHotTopCustom);
		// 设置热度值
		ctk.setRatioHotCustom(ratioHotSum / ctkView.getCtksStats().getCtkList().size());
		CtksStatsView ctkViewNew = new CtksStatsView();
		ctkViewNew.setCode(ErrorCodeConstant.T_REQUEST_HANDLE_SUCCESS);
		CtksStats cs = new CtksStats();
		cs.setCtkList(Arrays.asList(ctk));
		cs.setStatsList(statsList);
		ctkViewNew.setCtksStats(cs);
		return ctkViewNew;
	}

	@Test
	public void testGetHot() throws Exception {
		BaseMethodV1.setChannel(2);
		BaseMethodV1.setPlatform(2);
		BaseMethodV1.setUserTag("aaa6");
		String paramJson = "{\"date\":-1,\"endTime\":\"2018-07-03 10:00:00\",\"keyword\":\"(清华大学|清华)\",\"startTime\":\"2018-01-02 10:00:00\"}";
		// paramJson = "{\"date\":-1,\"endTime\":\"2018-01-04
		// 13:00:00\",\"keyword\":\"(清华大学|清华)\",\"startTime\":\"2018-01-03
		// 12:00:00\"}";
		Params params = JSON.parseObject(paramJson, Params.class);
		CtksStatsView hot = getHot("", params);
		System.out.println(JSON.toJSONString(hot));
	}

}