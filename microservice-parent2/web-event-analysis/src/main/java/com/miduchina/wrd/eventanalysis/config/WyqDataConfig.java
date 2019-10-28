package com.miduchina.wrd.eventanalysis.config;

import com.miduchina.wrd.dto.user.UserDto;
import com.miduchina.wrd.other.util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;



/**
 * 微热点数据接口
 *
 * */
public class WyqDataConfig {
	//渠道标识(1:h5 2:web 3:app)
	public static String channel = "1";
	//平台标识(1:test 2:wyq 3:yqt)
	public static String platform = "2";
	//用户标示，必填：默认：0
	public static String userTag = "0";
	//返回结果格式，选填：JSON（默认）XML
	//public static String format = "json";

	/**
	 * 微热点业务接口，初始化参数
	 *
	 * @throws Exception
	 */
	public static Map<String, Object> getDataInitMap(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		map.put("channel", WyqDataConfig.channel);
		map.put("platform", WyqDataConfig.platform);

		UserDto user = null;
		if(request.getAttribute("admin") != null){
			user = (UserDto) request.getAttribute("admin");
			map.put("userTag", user.getUserId().toString());
		}else{
			map.put("userTag", CommonUtils.getIp(request));
		}
		//map.put("format", WyqDataConfig.format);
		return map;
	}

	public static Map<String, Object> getDataInitMap(HttpServletRequest request,UserDto admin){
		Map<String, Object> map = new HashMap<>();
		map.put("channel", WyqDataConfig.channel);
		map.put("platform", WyqDataConfig.platform);

		if(admin != null){
			map.put("userTag", admin.getUserId().toString());
		}else{
			map.put("userTag", CommonUtils.getIp(request));
		}
		//map.put("format", WyqDataConfig.format);
		return map;
	}

	public static String getUserTag(HttpServletRequest request,UserDto admin){
		String userTag = "";
		if(admin != null){
			userTag = admin.getUserId().toString();
		}else{
			userTag = CommonUtils.getIp(request);
		}
		System.out.println("WyqDataConfig.getUserTag, userTag=" + userTag);
		return userTag;
	}

	public static Map<String, Object> getDataInitMap(Integer userId){
		Map<String, Object> map = new HashMap<>();
		map.put("channel", WyqDataConfig.channel);
		map.put("platform", WyqDataConfig.platform);
		map.put("userTag", userId.toString());
		//map.put("format", WyqDataConfig.format);
		return map;
	}

	/**
	 * 将map 转为 string
	 *
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, String> map,
										   boolean isSort) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<String>(map.keySet());
		if (isSort) {
			Collections.sort(keys);
		}
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key).toString();
			sb.append(key + "=" + value);
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.lastIndexOf("&"));
		}
	      /*
	       * for (Map.Entry<String, Object> entry : map.entrySet()) {
	       * sb.append(entry.getKey() + "=" + entry.getValue()); sb.append("&"); }
	       * String s = sb.toString(); if (s.endsWith("&")) { //s =
	       * StringUtils.substringBeforeLast(s, "&"); s = s.substring(0,
	       * s.lastIndexOf("&")); }
	       */
		return s;
	}

}
