package com.miduchina.wrd.eventanalysis.utils;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.dto.eventanalysis.products.IGroupResult;
import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.eventanalysis.config.WyqBusinessConfig;
import com.miduchina.wrd.eventanalysis.echart.EChartsAdapter;
import com.miduchina.wrd.eventanalysis.feign.APIInfoClient;
import com.miduchina.wrd.eventanalysis.log.model.OperateLogKeywordInfo;
import com.miduchina.wrd.eventanalysis.log.model.OperateLogWarningInfo;
import com.miduchina.wrd.eventanalysis.log.model.Warning;
import com.miduchina.wrd.po.eventanalysis.weiboevent.Stat;
import com.miduchina.wrd.po.keyword.KeyWord;
import com.miduchina.wrd.util.DateUtils;
import com.miduchina.wrd.util.MD5Utils;
import com.xd.tools.pojo.Params;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author liym
 */
public class CommonUtils {



	@Autowired
	APIInfoClient apiInfoClient;

	/**
	 * 根据用户ID获得userEncode
	 *
	 * @param
	 * @return
	 */
	public static String buildUserEncode(String userId) {
		String userEncode = null;
		try {
			userEncode = WyqBusinessConfig.encryptString(userId);
		} catch (Exception e) {
		}
		return userEncode;
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
	/**
	 * 满减
	 *
	 * @param totalFee
	 * @return
	 */
	public static float fullCut(float totalFee) {
		if (totalFee > 0) {
			if (totalFee >= 5000){
				totalFee = totalFee * 0.6F;
			}
			else if (totalFee >= 4000){
				totalFee = totalFee - 1500;
			}
			else if (totalFee >= 3000){
				totalFee = totalFee - 1000;
			}
			else if (totalFee >= 2000){
				totalFee = totalFee - 500;
			}
			else if (totalFee >= 1000){
				totalFee = totalFee - 100;
			}
			totalFee = Float.parseFloat(new DecimalFormat("0.00").format(totalFee));
			return totalFee;
		}
		return 0;
	}

	public static String getRequestPlatform(HttpServletRequest request){
		String platform = "";
		String userAgent = request.getHeader("user-agent");
		System.out.println("getRequestPlatform userAgent = " + userAgent);
		if (StringUtils.isNotBlank(userAgent)) {
			if (userAgent.toLowerCase().contains("micromessenger")) { // 微信客户端
				platform = "weixin";
			}else if(userAgent.toLowerCase().contains("weibo")){
				platform = "weibo";
			}
		}
		System.out.println("getRequestPlatform platform = " + platform);
		return platform;
	}

	/**
	 * 获取IP
	 *
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			//ip="127.0.0.1";
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0){
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 获取一定长度的随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomStringByLength(int length) {
		String base = "abcdefghijkmnpqrstuvwxyz23456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString().toLowerCase();
	}
	/**
	 * 发送GET请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendGet(String url, Map<String, Object> params) {
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String urlWithParams = url;
		String rtnStr = null;

		try {
			if (params != null && !params.isEmpty()) {
				List<String> paramList = new ArrayList<String>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					paramList.add(entry.getKey() + "=" + entry.getValue());
				}
				urlWithParams += "?" + StringUtils.join(paramList.toArray(), "&");
			}

			System.out.println("CommonUtil.sendGet() : url = " + urlWithParams);

			httpClient = new DefaultHttpClient();
			httpGet = new HttpGet(urlWithParams);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resultEntity, "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null){
				httpGet.releaseConnection();
			}
		}
		return rtnStr;
	}

	/**
	 * 记录用户操作行为日志
	 *
	 * @param request
	 *            请求
	 * @param userId
	 *            用户ID
	 * @param productSection
	 *            产品模块
	 * @param operateType
	 *            操作类型
	 * @param beforeObject
	 *            操作前对象
	 * @param afterObject
	 *            操作后对象
	 * @throws Exception
	 */
//	public static void opreateLog(HttpServletRequest request, Object user, int productSection, int operateType, Object beforeObject, Object afterObject, String extraInfo) throws Exception {
//		OperateLogObject operateLogObject = new OperateLogObject();
//		operateLogObject.setLogType(1); // 操作
//		operateLogObject.setProduct(1); // 微热点
//		operateLogObject.setReqTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 请求时间
//		operateLogObject.setProductChannel(3); // 产品入口
//
//		operateLogObject.setUser(generateOperateLogObject(1, user)); // 用户ID
//		operateLogObject.setReqIp(CommonUtils.getIp(request)); // 请求IP
//		operateLogObject.setProductSection(productSection); // 功能模块
//		operateLogObject.setOperateType(operateType); // 操作类型
//		if (beforeObject != null)
//			operateLogObject.setOperateBeforeObj(beforeObject); // 操作前对象
//		if (afterObject != null)
//			operateLogObject.setOperateAfterObj(afterObject); // 操作后对象
//		operateLogObject.setReqUA(request.getHeader("user-agent")); // 请求UA
//		if (extraInfo != null && !"".equals(extraInfo.trim()))
//			operateLogObject.setLogExtraInfo(extraInfo); // 扩展信息
//		String jsonString = JSONObject.toJSONString(operateLogObject);
//		SystemOperateLog.log(StringUtil.convertDB2LogString(jsonString));
//	}
	/**
	 * 生成日志对象
	 *
	 * @param type
	 *            类型：1-user 2-keyword 3-warning
	 * @param obj
	 *            对象
	 * @return
	 */
	public static Object generateOperateLogObject(int type, Object obj) {
		if (obj != null) {
			if (type == 1) {
				UserDto operateUserObject = (UserDto) obj;
				return operateUserObject;
			} else if (type == 2) {
				KeyWord operateKeywordObject = (KeyWord) obj;

				OperateLogKeywordInfo operateLogKeywordInfo = new OperateLogKeywordInfo();
				operateLogKeywordInfo.setKeywordId(operateKeywordObject.getKeywordId());
				operateLogKeywordInfo.setUserId(operateKeywordObject.getUserId());
				operateLogKeywordInfo.setKeywordName(operateKeywordObject.getKeywordName());
				operateLogKeywordInfo.setKeyword(operateKeywordObject.getKeyword());
				operateLogKeywordInfo.setInputKeyword(operateKeywordObject.getInputKeyword());
				operateLogKeywordInfo.setKeyword1(operateKeywordObject.getKeyword1());
				operateLogKeywordInfo.setKeyword2(operateKeywordObject.getKeyword2());
				operateLogKeywordInfo.setKeyword3(operateKeywordObject.getKeyword3());
				operateLogKeywordInfo.setKeyword4(operateKeywordObject.getKeyword4());
				operateLogKeywordInfo.setInputFilterKeyword(operateKeywordObject.getInputFilterKeyword());
				operateLogKeywordInfo.setFilterKeyword(operateKeywordObject.getFilterKeyword());
				operateLogKeywordInfo.setKeywordType(operateKeywordObject.getKeywordType());
				operateLogKeywordInfo.setValidEndDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(operateKeywordObject.getValidEndDate()));
				operateKeywordObject.setMonitorType(operateKeywordObject.getMonitorType());
				operateLogKeywordInfo.setStatus(operateKeywordObject.getStatus());

				return operateLogKeywordInfo;
			} else if (type == 3) {
				Warning operateWarningObject = (Warning) obj;

				OperateLogWarningInfo operateLogWarningInfo = new OperateLogWarningInfo();
				operateLogWarningInfo.setWarningId(operateWarningObject.getId());
				operateLogWarningInfo.setUserId(operateWarningObject.getUserId());
				operateLogWarningInfo.setWarningName(operateWarningObject.getWarningName());
				operateLogWarningInfo.setKeyword("");
				operateLogWarningInfo.setWarningStartTime(operateWarningObject.getStartTime());
				operateLogWarningInfo.setWarningEndTime(operateWarningObject.getEndTime());
				operateLogWarningInfo.setWarningIntervalTime(operateWarningObject.getIntervalTime());
				operateLogWarningInfo.setSmsSwitch(operateWarningObject.getSmsSwitch());
				operateLogWarningInfo.setAppSwitch(operateWarningObject.getAppSwitch());
				operateLogWarningInfo.setWebSwitch(operateWarningObject.getWebSwitch());
				operateLogWarningInfo.setEmailSwitch(operateWarningObject.getEmailSwitch());
				operateLogWarningInfo.setWeiboSwitch(operateWarningObject.getWeiboSwitch());
				operateLogWarningInfo.setWeekendSendSwitch(operateWarningObject.getWeekendSendSwitch());
				operateLogWarningInfo.setSameContentMergeSwitch(operateWarningObject.getSameContentMergeSwitch());
				operateLogWarningInfo.setSourceCondition(operateWarningObject.getSourceCondition());
				operateLogWarningInfo.setWarningContentType(operateWarningObject.getWarningContentType());
				operateLogWarningInfo.setProvince(operateWarningObject.getProvince());
				operateLogWarningInfo.setStatus(operateWarningObject.getStatus());

				return operateLogWarningInfo;
			}
		}
		return null;
	}


	/**
	 * 获取sessionName
	 *
	 * @param name
	 * @param isPrecise
	 *            是否获取精确的缓存名称（对于数据的起止时间不需要精确到分、秒的传 false）
	 */
	public static String getSessionName(String name, boolean isPrecise ,Map map) {
		StringBuilder sessionName = new StringBuilder(
				SystemConstants.JEDIS_KEYS_OPEN_TOOLS);
		sessionName.append(name);

		String shareCode = (String) map.get("shareCode");
		Integer date =  Integer.valueOf((String)map.get("date"));
		String startTime = (String) map.get("startTime");
		String endTime = (String) map.get("endTime");
		Date startDate = (Date) map.get("startDate");
		Date endDate = (Date) map.get("endDate");
		String searchKeyword1 = (String) map.get("searchKeyword1");


		if (net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotBlank(shareCode)) {
			sessionName.append("_" + shareCode);
		} else {
			sessionName.append("_" + MD5Utils.md5(searchKeyword1));
		}
		sessionName.append("_" + date);
		if (date == Params.TIME_OTHER) {
			if (isPrecise) {
				if (net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotEmpty(startTime)){
					sessionName.append("_"
							+ startTime.replace("-", "").replace(":", "")
							.replace(" ", ""));
				}
				if (net.logstash.logback.encoder.org.apache.commons.lang.StringUtils.isNotEmpty(endTime)){
					sessionName.append("_"
							+ endTime.replace("-", "").replace(":", "")
							.replace(" ", ""));
				}
			} else {
				if (startDate == null || endDate == null){
					initTime(date,startTime,endTime,endDate,startDate);
				}
				sessionName.append("_"
						+ DateUtils.format(startDate, "yyyyMMddHH"));
				sessionName.append("_"
						+ DateUtils.format(endDate, "yyyyMMddHH"));
			}
		}
		return generateJedisKey(sessionName.toString());

	}



	// 生成jedis key
	public static String generateJedisKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			return new StringBuilder(SystemConstants.JEDIS_KEYS_PREFIX).append(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")).append(key).toString();
		}
		return null;
	}



	/**
	 * 时间参数处理 private Boolean isHour;
	 */
	public static Boolean initTime(Integer date,String startTime,String endTime,Date endDate, Date startDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Calendar time = Calendar.getInstance();

		Boolean isHour = false;
		if (date == Params.TIME_OTHER
				&& (StringUtils.isBlank(startTime) || StringUtils
				.isBlank(endTime))){
			date = Params.TIME_24HOURS;
		}
		if (date != Params.TIME_OTHER) {
			endDate = time.getTime();
			if (date == Params.TIME_24HOURS) {
				time.add(Calendar.DATE, -1);
				startDate = time.getTime();
			} else if (date == Params.TIME_72HOURS) {
				time.add(Calendar.DATE, -3);
				startDate = time.getTime();
			} else if (date == Params.TIME_ONEDAY) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				startDate = time.getTime();
			} else if (date == Params.TIME_THREEDAY) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				time.add(Calendar.DATE, -2);
				startDate = time.getTime();
			} else if (date == Params.TIME_FIVEDAY) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				time.add(Calendar.DATE, -4);
				startDate = time.getTime();
			} else if (date == Params.TIME_WEEKDAY) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				time.add(Calendar.DATE, -6);
				startDate = time.getTime();
			} else if (date == Params.TIME_ONMONTH) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				time.add(Calendar.DATE, -29);
				startDate = time.getTime();
			} else if (date == Params.TIME_ONEYEAR) {
				time.set(Calendar.HOUR_OF_DAY, 0);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				time.add(Calendar.DATE, -364);
				startDate = time.getTime();
			}
			if (startDate != null && endDate != null) {
				startTime = dateFormat.format(startDate);
				endTime = dateFormat.format(endDate);
			}

			if (date != Params.TIME_24HOURS && date != Params.TIME_72HOURS
					&& date != Params.TIME_WEEKDAY && date > 7) {
//				isHour = false;
			} else {
				isHour = true;
			}
		}

		if (date == Params.TIME_OTHER) {
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

			if (endDate.getTime() - startDate.getTime() > 7 * 24 * 60 * 60
					* 1000L) {
				isHour = false;
			} else {
				isHour = true;
			}
		}
		return  isHour;
	}




	/**
	 * 获取时间节点类型
	 */
	public static Integer getTimeNodeType(String timeNode){
		Integer timeNodeType = 0;
		if(StringUtils.isNotBlank(timeNode)){
			if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}.*$", timeNode)){
				timeNodeType = 1;	//按小时
			}else if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}$", timeNode)){
				timeNodeType = 2;	//按天
			}else if(CommonUtils.match("^\\d{4}-\\d{2}$", timeNode)){
				timeNodeType = 3;	//按月
			}
		}
		return timeNodeType;
	}

	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}


	public static List<String> getTimeAxis(Date startTime, Date endTime, int timeNodeType){
		List<String> timeAxis = new ArrayList<String>();
		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if(timeNodeType == 1){	//按小时 结尾时间向前取整
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			end.add(Calendar.HOUR_OF_DAY, -1);
			for(; DateUtils.format(begin.getTime(), "yyMMddHH").compareTo(DateUtils.format(end.getTime(), "yyMMddHH")) <= 0; begin.add(Calendar.HOUR_OF_DAY, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}else if(timeNodeType == 2){	//按天
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for(; DateUtils.format(begin.getTime(), "yyMMdd").compareTo(DateUtils.format(end.getTime(), "yyMMdd")) <= 0; begin.add(Calendar.DAY_OF_MONTH, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}else if(timeNodeType == 3){	//按月
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			for(; DateUtils.format(begin.getTime(), "yyMM").compareTo(DateUtils.format(end.getTime(), "yyMM")) <= 0; begin.add(Calendar.MONTH, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}
		return timeAxis;
	}


	public static List<String> getTimeAxis(Date startTime, Date endTime){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");

		long time = startTime.getTime();
		long add = 60*60*1000;

		List<String> timeAxis = new ArrayList<String>();
		for(; time <= endTime.getTime(); time += add){

			timeAxis.add(dateFormat.format(new Date(time)));

		}
		return timeAxis;
	}

	public static List<String> getNewTimeAxis(Date startTime, Date endTime, int timeNodeType){
		List<String> timeAxis = new ArrayList<String>();
		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		if(timeNodeType == 1){	//按小时 结尾时间向前取整
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
//    		end.add(Calendar.HOUR_OF_DAY, -1);
			for(; DateUtils.format(begin.getTime(), "yyMMddHH").compareTo(DateUtils.format(end.getTime(), "yyMMddHH")) <= 0; begin.add(Calendar.HOUR_OF_DAY, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}else if(timeNodeType == 2){	//按天
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			for(; DateUtils.format(begin.getTime(), "yyMMdd").compareTo(DateUtils.format(end.getTime(), "yyMMdd")) <= 0; begin.add(Calendar.DAY_OF_MONTH, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}else if(timeNodeType == 3){	//按月
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			for(; DateUtils.format(begin.getTime(), "yyMM").compareTo(DateUtils.format(end.getTime(), "yyMM")) <= 0; begin.add(Calendar.MONTH, 1)){
				timeAxis.add(dateFormat.format(begin.getTime()));
			}
		}
		return timeAxis;
	}


	public static void jsonToMap(HashMap<Integer, Object> r, HashMap<String, Object> result, String json) {
		JSONObject object = JSONObject.parseObject(json);
		Set<Map.Entry<String, Object>> entrySet = object.entrySet();
		Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> next = iterator.next();
			String key = next.getKey();
			Object value = next.getValue();
			result.put(key, value);
		}
		r.put(0, result);
	}

	public static void jsonToMap(Map<String, Object> result, Map<Integer, Object> echartmap, String json) {
		JSONObject object = JSONObject.parseObject(json);
		Set<Map.Entry<String, Object>> entrySet = object.entrySet();
		Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> next = iterator.next();
			String key = next.getKey();
			Object value = next.getValue();
			result.put(key, value);
		}
		echartmap.put(0, result);
	}


	public static void jsonToMap(Map<String, Object> result, String json) {
		if (StringUtils.isNoneBlank(json)){
			JSONObject object = JSONObject.parseObject(json);
			Set<Map.Entry<String, Object>> entrySet = object.entrySet();
			Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> next = iterator.next();
				String key = next.getKey();
				Object value = next.getValue();
				result.put(key, value);
			}
		}

	}

}

