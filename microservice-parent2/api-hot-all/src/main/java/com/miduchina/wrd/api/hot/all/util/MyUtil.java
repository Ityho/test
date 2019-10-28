package com.miduchina.wrd.api.hot.all.util;

import com.alibaba.fastjson.JSON;
import com.miduchina.wrd.api.hot.all.constant.BusinessConstant;
import com.xd.tools.method.sjfx.abilityseal.SjfxAbilitysealMethodV1;
import com.xd.tools.method.sjfx.db.SjfxDbMethodV1;
import com.xd.tools.ownutils.MathUtils;
import com.xd.tools.ownutils.MeUtils;
import com.xd.tools.pojo.*;
import com.xd.tools.view.AnalysisTasksView;
import com.xd.tools.view.IContentCommonNetView;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.junit.Test;

public class MyUtil {

	public static LinkedHashMap<Long, Long> fansNumlinkedHashMap = new LinkedHashMap<Long, Long>();
	public static LinkedHashMap<String, String> optionsMap = new LinkedHashMap<String, String>();
	public static final Map<String, String> SystemConfigMap = new HashMap<String, String>();
	static {
		fansNumlinkedHashMap.put(0L, 49L);
		fansNumlinkedHashMap.put(50L, 199L);
		fansNumlinkedHashMap.put(200L, 499L);
		fansNumlinkedHashMap.put(500L, 799L);
		fansNumlinkedHashMap.put(800L, 999L);
		fansNumlinkedHashMap.put(1000L, 1599L);
		fansNumlinkedHashMap.put(1600L, 2000L);

		optionsMap.put("2", "敏感");
		optionsMap.put("3", "非敏感");
	}

	/**
	 * 全年热度 格式化为天
	 *
	 * @param list
	 * @Title: formatToDay
	 * @author lkc
	 */
	public static List<StatStatList> formatToDay(List<StatStatList> list) {

		// 日期
		List<StatStatList> dayList = new LinkedList<>();

		if (CollectionUtils.isNotEmpty(list)) {

			// 最大的分
			Double maxTotal = 0D;

			// 当前日分
			Double prevDayTotal = 0D;

			// 最大分值日期
			String maxDay = "";

			// 当前日期
			String prevDay = "";

			int num = 0;

			// 添加一个空的，保证最后一个能够被运算

			StatStatList st = new StatStatList();
			st.setName("123456789011");
			list.add(st);

			for (StatStatList stat : list) {

				if (stat == null || StringUtils.isEmpty(stat.getName())) {
					continue;
				}

				// 获取天
				String dayStr = stat.getName().substring(0, 10);

				// 如果不是当前日期
				if (!prevDay.equals(dayStr) && num > 0) {

					// 日期为单位的统计
					StatStatList dayStat = new StatStatList();

					dayStat.setName(prevDay);

					// 取平均值
					prevDayTotal = prevDayTotal / num;
					dayStat.setTotal(prevDayTotal);
					dayList.add(dayStat);

					// 取出最大值
					prevDayTotal = Math.max(maxTotal, prevDayTotal);

					// 设置最大日期
					maxDay = prevDayTotal == maxTotal ? prevDay : maxDay;
					// 清空当前分
					prevDayTotal = 0D;

					// 清空数量
					num = 0;

				}

				// 当前分值相加
				prevDayTotal = prevDayTotal + stat.getTotal();

				// 设置当前日期
				prevDay = dayStr;

				num++;

			}

		}

		return dayList;

	}

	/**
	 * 通过analysisTaskTicket获取AnalysisTask
	 * 
	 * @param analysisTaskTicket
	 * @return
	 * @throws Exception
	 */
	public static AnalysisTask getAnalysisTask(String userTag, String analysisTaskTicket, String function,
                                               LinkedHashMap<String, Object> logMap) throws Exception {
		AnalysisTask analysisTask = null;
		DbParams dbPar = new DbParams();
		dbPar.setAnalysisTaskTicket(analysisTaskTicket);
		AnalysisTasksView analysisTasksView = SjfxDbMethodV1.analysisTaskSelect(userTag, dbPar);
		logMap.put("process", "SjfxDbMethodV1.analysisTaskSelect");
		logMap.put("ticket", analysisTaskTicket);
		// logMap.put("view", JSON.toJSONString(analysisTasksView));
		// MeUtils.info(function, logMap);
		// 打印code和message
		printLog(function, logMap, "view", JSON.toJSONString(analysisTasksView), true);
		List<AnalysisTask> analysisTaskList = analysisTasksView.getAnalysisTaskList();
		if (CollectionUtils.isNotEmpty(analysisTaskList)) {
			analysisTask = analysisTaskList.get(0);
		}
		return analysisTask;
	}

	/**
	 * json转对象
	 */
	public static <T> T getAnalysisTaskTagRdfx(String perParamsJson, Class<T> class1, String function,
			LinkedHashMap<String, Object> logMap) throws InstantiationException, IllegalAccessException {
		T t = null;
		if (StringUtils.isNotEmpty(perParamsJson)) {
			t = JSON.parseObject(perParamsJson, class1);
		}
		if (null != t) {
			return t;
		}
		return class1.newInstance();
	}

	/**
	 * 获取个性化参数Json
	 */
	public static String getAnalysisTaskTagRdfxJson(AnalysisTask analysisTask, String function,
                                                    LinkedHashMap<String, Object> logMap) {
		String perParamsJson = StringUtils.EMPTY;
		if (analysisTask != null) {
			perParamsJson = analysisTask.getPerParams();
			printLog(function, logMap, "paramsJson", perParamsJson, true);
		} else {
			printErrLog(function, logMap, "process", "getParamsJson() analysisTask is null", false);
		}
		return perParamsJson;
	}

	// 正则匹配图片路径
	public static List<String> getImageSrc(String htmlCode) {
		List<String> imageSrcList = new ArrayList<String>();
		Pattern p = Pattern.compile(
				"<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\">]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)?(.*)\\b)[^>]*>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(htmlCode);
		String quote = null;
		String src = null;
		while (m.find()) {
			quote = m.group(1);

			// src=https://sms.reyo.cn:443/temp/screenshot/zY9Ur-KcyY6-2fVB1-1FSH4.png
			src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
			imageSrcList.add(src);

		}
		return imageSrcList;
	}

	// 获取字符串的长度
	public static double getLength(String s) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		// 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < s.length(); i++) {
			// 获取一个字符
			String temp = s.substring(i, i + 1);
			// 判断是否为中文字符
			if (temp.matches(chinese)) {
				// 中文字符长度为1
				valueLength += 1;
			} else {
				// 其他字符长度为0.5
				valueLength += 0.5;
			}
		}
		// 进位取整
		return Math.ceil(valueLength);
	}

	/**
	 * 全年热度 找出最大的日期并且格式化时间格式
	 *
	 * @return
	 * @Title: getMaxDayAadFormatDay
	 * @author lkc
	 */
	public static String getMaxDayAadFormatDay(List<StatStatList> list) {

		if (CollectionUtils.isNotEmpty(list)) {

			// 最大的分
			Double maxTotal = 0D;

			// 最大分值日期
			String maxDay = "";

			for (StatStatList stat : list) {

				if (stat == null || StringUtils.isEmpty(stat.getName())) {
					continue;
				}

				if (maxTotal < stat.getTotal()) {
					maxTotal = stat.getTotal();
					maxDay = stat.getName();
					if (StringUtils.isNotEmpty(maxDay) && maxDay.length() > 10) {
						maxDay = maxDay.substring(0, 10);
					}
				}

			}

			return maxDay;
		}

		return null;
	}

	/**
	 * 通过paramsJson获取Params
	 * 
	 * @param paramsJson
	 * @param function
	 * @param logMap
	 * @return
	 */
	public static Params getParams(String paramsJson, String function, LinkedHashMap<String, Object> logMap) {
		Params params = new Params();
		if (StringUtils.isNotEmpty(paramsJson)) {
			params = JSON.parseObject(paramsJson, Params.class);
		}
		return params;
	}

	/**
	 * 通过AnalysisTask获取paramsJson
	 * 
	 * @param analysisTask
	 * @return
	 * @throws Exception
	 */
	public static String getParamsJson(AnalysisTask analysisTask, String function, LinkedHashMap<String, Object> logMap)
			throws Exception {
		String paramsJson = StringUtils.EMPTY;
		if (analysisTask != null) {
			paramsJson = analysisTask.getParamsJson();
			printLog(function, logMap, "paramsJson", paramsJson, true);
		} else {
			printErrLog(function, logMap, "process", "getParamsJson() analysisTask is null", false);
		}
		return paramsJson;
	}

	// 正则匹配视频路径
	public static List<String> getVideoSrc(String htmlCode) {
		List<String> videoSrcList = new ArrayList<String>();
		Pattern p = Pattern.compile("<video\\b[^>]*>(.*)?</video>", Pattern.CASE_INSENSITIVE);
		Pattern v = Pattern.compile(
				"<source\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.mp4|\\.rmvb|\\.rm|\\.mpeg|\\.mov|\\.flv|\\.avi|\\.mpg|\\.wmv|\\.wma|\\.asf|\\.mid|\\.mp3)\\b)[^>]*>?</source>",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(htmlCode);
		String quote = null;
		String src = null;
		while (m.find()) {
			quote = m.group(1);
			if (StringUtils.isNotEmpty(quote)) {
				Matcher mv = v.matcher(quote);
				while (mv.find()) {
					quote = mv.group(1);
					mv.group(2);
					mv.group(3);
					mv.groupCount();
					src = (quote == null || quote.trim().length() == 0) ? mv.group(2).split("\\s+")[0] : mv.group(2);
					videoSrcList.add(src);
				}
			}
		}
		return videoSrcList;
	}

	public static void main(String[] args) throws ParseException, InstantiationException, IllegalAccessException {
		// System.out.println(getLength(
		// "#每日车事# 【东风风行景逸X6将8月21日上市
		// 预售8.49万起】日前，我们从东风风行官方获悉，曾在2017上海车展发布的景逸X6已经正式开启预售，预售价格区间为8.49—11.29万元。据悉，新车将于8月21日正式上市，动力方面搭载1.5T和2.0L两款发动机，除此之外，我们从国家工信部获悉，景逸X6还将推出1.3T车型，以丰富购买选项。请看：http://t.cn/R9vxcNH"));
		// String json =
		// "{\"isUndulate\":false,\"maxPosition\":0,\"maxUndulatePosition\":0,\"name\":\"愤怒\",\"num\":46831,\"percent\":1.96335,\"statList\":[{\"increment\":0,\"key\":\"2018-01-03\",\"name\":\"2018-01-03
		// 00:00:00\",\"num\":4670,\"originLst\":[],\"percent\":9.97203,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"10%\",\"tfidf\":0.0,\"total\":0.0,\"type\":1},{\"increment\":0,\"key\":\"2018-01-08\",\"name\":\"2018-01-08
		// 00:00:00\",\"num\":3533,\"originLst\":[],\"percent\":7.54415,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"8%\",\"tfidf\":0.0,\"total\":0.0,\"type\":2},{\"increment\":0,\"key\":\"2018-01-07\",\"name\":\"2018-01-07
		// 00:00:00\",\"num\":3225,\"originLst\":[],\"percent\":6.88646,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"7%\",\"tfidf\":0.0,\"total\":0.0,\"type\":3},{\"increment\":0,\"key\":\"2018-01-10\",\"name\":\"2018-01-10
		// 00:00:00\",\"num\":3203,\"originLst\":[],\"percent\":6.83949,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"7%\",\"tfidf\":0.0,\"total\":0.0,\"type\":4},{\"increment\":0,\"key\":\"2018-01-05\",\"name\":\"2018-01-05
		// 00:00:00\",\"num\":3065,\"originLst\":[],\"percent\":6.54481,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"7%\",\"tfidf\":0.0,\"total\":0.0,\"type\":5},{\"increment\":0,\"key\":\"2018-01-12\",\"name\":\"2018-01-12
		// 00:00:00\",\"num\":2995,\"originLst\":[],\"percent\":6.39534,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"6%\",\"tfidf\":0.0,\"total\":0.0,\"type\":6},{\"increment\":0,\"key\":\"2018-01-01\",\"name\":\"2018-01-01
		// 00:00:00\",\"num\":2956,\"originLst\":[],\"percent\":6.31206,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"6%\",\"tfidf\":0.0,\"total\":0.0,\"type\":7},{\"increment\":0,\"key\":\"2018-01-06\",\"name\":\"2018-01-06
		// 00:00:00\",\"num\":2777,\"originLst\":[],\"percent\":5.92983,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"6%\",\"tfidf\":0.0,\"total\":0.0,\"type\":8},{\"increment\":0,\"key\":\"2018-01-02\",\"name\":\"2018-01-02
		// 00:00:00\",\"num\":2770,\"originLst\":[],\"percent\":5.91489,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"6%\",\"tfidf\":0.0,\"total\":0.0,\"type\":9},{\"increment\":0,\"key\":\"2018-01-09\",\"name\":\"2018-01-09
		// 00:00:00\",\"num\":2386,\"originLst\":[],\"percent\":5.09492,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":10},{\"increment\":0,\"key\":\"2018-01-13\",\"name\":\"2018-01-13
		// 00:00:00\",\"num\":2348,\"originLst\":[],\"percent\":5.01377,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":11},{\"increment\":0,\"key\":\"2018-01-04\",\"name\":\"2018-01-04
		// 00:00:00\",\"num\":2245,\"originLst\":[],\"percent\":4.79383,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":12},{\"increment\":0,\"key\":\"2018-01-16\",\"name\":\"2018-01-16
		// 00:00:00\",\"num\":2236,\"originLst\":[],\"percent\":4.77462,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":13},{\"increment\":0,\"key\":\"2018-01-11\",\"name\":\"2018-01-11
		// 00:00:00\",\"num\":2235,\"originLst\":[],\"percent\":4.77248,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":14},{\"increment\":0,\"key\":\"2018-01-17\",\"name\":\"2018-01-17
		// 00:00:00\",\"num\":2151,\"originLst\":[],\"percent\":4.59311,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"5%\",\"tfidf\":0.0,\"total\":0.0,\"type\":15},{\"increment\":0,\"key\":\"2018-01-15\",\"name\":\"2018-01-15
		// 00:00:00\",\"num\":2024,\"originLst\":[],\"percent\":4.32192,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"4%\",\"tfidf\":0.0,\"total\":0.0,\"type\":16},{\"increment\":0,\"key\":\"2018-01-14\",\"name\":\"2018-01-14
		// 00:00:00\",\"num\":1461,\"originLst\":[],\"percent\":3.11973,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"3%\",\"tfidf\":0.0,\"total\":0.0,\"type\":17},{\"increment\":0,\"key\":\"2018-01-18\",\"name\":\"2018-01-18
		// 00:00:00\",\"num\":551,\"originLst\":[],\"percent\":1.17657,\"percentAverage\":0.0,\"percentAverageMinus\":0.0,\"percentStr\":\"1%\",\"tfidf\":0.0,\"total\":0.0,\"type\":18}],\"type\":1}";
		// Stats stats = JSON.parseObject(json, Stats.class);
		// List<Stats> statsList = new ArrayList<Stats>();
		// statsList.add(stats);
		// sortStatsListAsc(statsList);

		Params params = getAnalysisTaskTagRdfx(
				"{\"date\":-1,\"endTime\":\"2017-11-11 23:59:59\",\"keyword\":\"东方明珠\",\"startTime\":\"2017-11-10 00:00:00\"}",
				Params.class, "", new LinkedHashMap<String, Object>());
		System.out.println(params.getKeyword());
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		String quote = null;
		String src = null;
		while (matcher.find()) {
			quote = matcher.group(1);

			// src=https://sms.reyo.cn:443/temp/screenshot/zY9Ur-KcyY6-2fVB1-1FSH4.png
			src = (quote == null || quote.trim().length() == 0) ? matcher.group(2).split("\\s+")[0] : matcher.group(2);
			System.out.println(src);

		}
		return matcher.find();
	}

	/**
	 * 打印自定义错误日志
	 * 
	 * @param function
	 * @param logMap
	 * @param key
	 * @param value
	 */
	public static void printErrLog(String function, LinkedHashMap<String, Object> logMap, String key, Object value,
			boolean flag) {
		if (MeUtils.isNotEmpty(logMap)) {
			logMap.put(key, value);
			MeUtils.error(function, logMap);
			if (flag && logMap.containsKey(key)) {
				logMap.remove(key);
			}
		}
	}

	/**
	 * 打印自定义正常日志
	 * 
	 * @param function
	 * @param logMap
	 * @param key
	 * @param value
	 * @param flag     打印后是否删除
	 */
	public static void printLog(String function, LinkedHashMap<String, Object> logMap, String key, Object value,
			boolean flag) {
		if (MeUtils.isNotEmpty(logMap)) {
			logMap.put(key, value);
			MeUtils.info(function, logMap);
			if (flag && logMap.containsKey(key)) {
				logMap.remove(key);
			}
		}
	}

	/**
	 * 非微博获取富文本
	 *
	 * @param platform
	 * @param channel
	 * @param userTag
	 * @param iccNetList
	 * @return
	 */
	public static List<IContentCommonNet> processRichText(int platform, int channel, String userTag,
                                                          List<IContentCommonNet> iccNetList) throws Exception {
		if (CollectionUtils.isNotEmpty(iccNetList)) {
			IContentCommonNetView iccNetView = SjfxAbilitysealMethodV1.getCarSearchListRichTextBinary("system",
					iccNetList);
			if (iccNetView != null && CollectionUtils.isNotEmpty(iccNetView.getIContentCommonNetList())) {
				for (IContentCommonNet iccNet : iccNetList) {
					String content = iccNet.getContent();
					String captureWebsiteName = iccNet.getCaptureWebsiteName();
					if (StringUtils.isNotEmpty(content)
							&& !StringUtils.equals(BusinessConstant.CAPTURE_WEBSITE_NAME, captureWebsiteName)
							&& !StringUtils.equals(BusinessConstant.CAPTURE_WEBSITE_NAME_WX, captureWebsiteName)) {
						// contentTemp统一返回content
						List<String> imageList = getImageSrc(content);
						List<String> videoList = getVideoSrc(content);
						if (CollectionUtils.isNotEmpty(imageList)) {
							iccNet.setPictureList(imageList);
						}
						if (CollectionUtils.isNotEmpty(videoList)) {
							iccNet.setVideoList(videoList);
						}
					}
				}
			}
		}
		return iccNetList;
	}

	public static StatStatLists setStatStatLists(StatStatLists statStatLists) {
		double[] data;
		double[] dataAverage;
		double[] dataAverageMinus;
		if (statStatLists != null) {
			List<StatStatList> statStatListList = statStatLists.getStatStatListList();
			List<Stat> statList = statStatListListToStatList(statStatListList);
			if (CollectionUtils.isNotEmpty(statList)) {
				data = MathUtils.statListTotalToDouble(statList);
				statStatLists.setUndulate(MathUtils.isUndulate(data));
				statStatLists.setMaxPosition(MathUtils.getMaxPosition(data));
				dataAverage = MathUtils.getDataAverage(data);
				dataAverageMinus = MathUtils.getDataAverageMinus(dataAverage);
				if (dataAverage != null && dataAverage.length > 0) {
					if (CollectionUtils.isNotEmpty(statStatListList)) {
						StatStatList statStatList;
						for (int i = 0; i < statStatListList.size(); i++) {
							statStatList = statStatListList.get(i);
							if (i < dataAverage.length) {
								statStatList.setPercentAverage(dataAverage[i]);
								statStatList.setPercentAverageMinus(dataAverageMinus[i]);
							}
						}
					}
				}
				statStatLists.setMaxUndulatePosition(MathUtils.getAbsMaxPosition(dataAverageMinus));
			}
		}
		return statStatLists;
	}

	public static Map<String, Integer> sortByName(Map<String, Integer> oriMap) {
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Entry<String, Integer>> entryList = new ArrayList<Entry<String, Integer>>(oriMap.entrySet());
			Collections.sort(entryList, new Comparator<Entry<String, Integer>>() {
				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					if (o1 != null && o2 != null) {
						String i1 = (o1).getValue() + (o1).getKey();
						String i2 = (o2).getValue() + (o2).getKey();
						String n1 = "";
						String n2 = "";
						if (i1 != null) {
							n1 = i1;
						}
						if (i2 != null) {
							n2 = i2;
						}
						if (n1.compareTo(n2) <= 0){
							return -1;
						}
						if (n1.compareTo(n2) > 0) {
							return 1;
						}
					}
					return 0;
				}
			});
			Iterator<Entry<String, Integer>> iter = entryList.iterator();
			Entry<String, Integer> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}

	public static List<Stat> sortByNameAndNum(List<Stat> IContentCommonList) {
		if (CollectionUtils.isNotEmpty(IContentCommonList)) {
			Collections.sort(IContentCommonList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					if (o1 != null && o2 != null) {
						String i1 = ((Stat) o1).getNum() + ((Stat) o1).getName();
						String i2 = ((Stat) o2).getNum() + ((Stat) o2).getName();
						String n1 = "";
						String n2 = "";
						if (i1 != null) {
							n1 = i1;
						}
						if (i2 != null) {
							n2 = i2;
						}
						if (n1.compareTo(n2) <= 0)
							return -1;
						if (n1.compareTo(n2) > 0) {
							return 1;
						}
					}
					return 0;
				}
			});
		}
		return IContentCommonList;
	}

	public static List<Stat> sortByTotal(List<Stat> statList) {
		if (CollectionUtils.isNotEmpty(statList)) {
			Collections.sort(statList, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					if (o1 != null && o2 != null) {
						Long i1 = ((Stat) o1).getNum();
						Long i2 = ((Stat) o2).getNum();
						long n1 = 0;
						long n2 = 0;
						if (i1 != null) {
							n1 = i1;
						}
						if (i2 != null) {
							n2 = i2;
						}
						if (n1 > n2)
							return -1;
						if (n1 < n2) {
							return 1;
						}
					}
					return 0;
				}
			});
		}
		return statList;
	}

	public static List<Stats> sortStatsListAsc(List<Stats> statsList) throws ParseException {
		if (CollectionUtils.isNotEmpty(statsList)) {
			for (Stats stats : statsList) {
				List<Stat> statList = stats.getStatList();
				if (CollectionUtils.isNotEmpty(statList)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Map<Long, Stat> map = new TreeMap<Long, Stat>();
					for (int i = 0; i < statList.size(); i++) {
						Stat stat = statList.get(i);
						map.put(sdf.parse(stat.getKey()).getTime(), stat);
					}
					Collection<Stat> coll = map.values();
					statList.clear();
					statList.addAll(coll);
				}
			}
		}
		return statsList;
	}

//	// 返回结果分类
//	public static Map<String, List<IContentCommonNet>> splitCarResultBytypes(List<IContentCommonNet> iccNetList) {
//		Map<String, List<IContentCommonNet>> resultMap = new HashMap<String, List<IContentCommonNet>>();
//		List<IContentCommonNet> imageIccNetList = new ArrayList<IContentCommonNet>();
//		List<IContentCommonNet> imageGroupIccNetList = new ArrayList<IContentCommonNet>();
//		List<IContentCommonNet> videoIccNetList = new ArrayList<IContentCommonNet>();
//		List<IContentCommonNet> articleIccNetList = new ArrayList<IContentCommonNet>();
//		if (CollectionUtils.isNotEmpty(iccNetList)) {
//			for (Iterator it = iccNetList.iterator(); it.hasNext();) {
//				IContentCommonNet iccNet = (IContentCommonNet) it.next();
//				if (iccNet != null) {
//					if (CollectionUtils.isNotEmpty(iccNet.getVideoList())) {
//						videoIccNetList.add(iccNet);
//						it.remove();
//					} else if (CollectionUtils.isNotEmpty(iccNet.getPictureList())) {
//						String captureWebsiteName = iccNet.getCaptureWebsiteName();
//						if (StringUtils.equals(BusinessConstant.CAPTURE_WEBSITE_NAME, captureWebsiteName)
//								&& iccNet.getPictureList().size() > 1 && !iccNet.isWeiboHeadline()) {
//							imageGroupIccNetList.add(iccNet);
//							it.remove();
//						} else {
//							imageIccNetList.add(iccNet);
//							it.remove();
//						}
//					} else {
//						articleIccNetList.add(iccNet);
//						it.remove();
//					}
//				}
//			}
//		}
//		resultMap.put("video", videoIccNetList);
//		resultMap.put("image", imageIccNetList);
//		resultMap.put("imageGroup", imageGroupIccNetList);
//		resultMap.put("article", articleIccNetList);
//		return resultMap;
//	}

	public static List<StatStatList> statListToStatStatListList(List<Stat> statList) {
		if (CollectionUtils.isNotEmpty(statList)) {
			List<StatStatList> statStatListList = new ArrayList<StatStatList>();
			for (Stat stat : statList) {
				try {
					StatStatList statStatList = new StatStatList();
					BeanUtils.copyProperties(statStatList, stat);
					statStatListList.add(statStatList);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			return statStatListList;
		}
		return null;
	}

	public static List<Stat> statStatListListToStatList(List<StatStatList> statStatListList) {
		if (CollectionUtils.isNotEmpty(statStatListList)) {
			List<Stat> statList = new ArrayList<Stat>();
			for (StatStatList statStatList : statStatListList) {
				try {
					Stat stat = new Stat();
					BeanUtils.copyProperties(stat, statStatList);
					statList.add(stat);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return statList;
		}
		return null;
	}

	@Test
	public void matchUrls() {
		String str = "<img alt=\"E3 2017：《刺客信条：起源》最新预告 感受美丽埃及\" src=\"http://mmbiz.qpic.cn/mmbiz_jpg/ZcnhmdyHcok4icT2OZw6W1qgTwiayXSL3cuLDzPsjwb3o7jwzUcWicaPhahhfRrvfDq9zyqfJ4MEhaxNTLG1kUsRw/0?wx_fmt=jpeg\">";
		String str2 = "http://pic.anhuinews.com/0/01/55/08/1550824_341488.jpg";
		String IMAGE_TAG = "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\">]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)?(.*)\\b)[^>]*>";
		String IMAGE_SRC = "http:(\\\")?(.*?)(|>|\\\\s+)";
		String videoStr = "<a href=\"http://www.tudou.com/albumplay/t1tzlUNNYo4/-iH73Qo6xTc.html\" target=\"new\" class=\"inner\"><img width=\"132\" height=\"99\" alt=\"http://i3.tdimg.com/b/20120806/c224.jpg\" class=\"pack_clipImg lazyImg\" src=\"http://css.tudouui.com/skin/__g/img/sprite.gif\" ><span class=\"vpbg\"></span><a class=\"vinf\" target=\"new\" href=\"http://www.tudou.com/albumplay/t1tzlUNNYo4/-iH73Qo6xTc.html\">2012-08-05</a></a>";

		String te = "http://mmbiz.qpic.cn/mmbiz/HxHJicXTwTnadlnYgzRpDPTf46ow5hbEFCiciaAkQwqbsGCOGMtlEKL6v3kBH5ejlcXzribraic7M0RPtjYZ6xRCCmw/640?wx_fmt=jpeg";

		// http:\"?(.*?)(\"|>|\\s+)
		// Pattern r = Pattern.compile(pattern);
		// Matcher m = r.matcher(str);
		// System.out.println(match(IMAGE_SRC, te));
		System.out.println(match(IMAGE_TAG, str));
		// System.out.println(match(IMAGE_SRC, str2));
	}

}
