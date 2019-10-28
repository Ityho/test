package com.miduchina.wrd.eventanalysis.config;

import com.alibaba.fastjson.JSONObject;
import com.miduchina.wrd.common.IntraBusinessAPIConfig;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.eventanalysis.utils.Utils;
import com.miduchina.wrd.other.util.CommonUtils;
import com.miduchina.wrd.util.MD5Utils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 微热点业务接口
 *
 * */
public class WyqBusinessConfig {
	//产品标识，必填：微热点：1001，微分析：1002，
	public static String product = "1001";
	//平台标识，必填：WEB：1，APP：2，H5：3
	public static String platform = "3";
	//软件类型，必填：默认：1
	public static String softType = "1";
	//软件版本，必填：默认：1.0.0
	public static String version = "1.0.0";
	//返回结果格式，选填：JSON（默认）XML
	//public static String timeStamp =  String.valueOf(new Date().getTime());
	//用户原始请求信息，必填：JSON（默认）XML
	//public static String ua = ServletActionContext.getRequest().getHeader("user-agent");
	//public static String ip = CommonUtils.getIp(ServletActionContext.getRequest());
	//public static String referer = StringUtils.isBlank(ServletActionContext.getRequest().getHeader("referer"))? "-":ServletActionContext.getRequest().getHeader("referer");
	//返回结果格式，选填：JSON（默认）XML
	public static String format = "json";

	/**
	 * 微热点业务接口，初始化参数
	 *
	 * @throws Exception
	 */
	public static Map<String, Object> getDataInitMap(){
		HttpServletRequest req = Utils.getRequest();
		if(req!=null){
			Map<String, Object> map = new HashMap<>();
			map.put("key", SysConfig.cfgMap.get("API_INTRA_BUSINESS_KEY"));
			map.put("product", WyqBusinessConfig.product);
			map.put("platform", WyqBusinessConfig.platform);
			map.put("softType", WyqBusinessConfig.softType);
			map.put("version", WyqBusinessConfig.version);
			map.put("channel", WyqDataConfig.channel);
			map.put("platformTag", IntraBusinessAPIConfig.getValue("platformTag"));
			map.put("timeStamp", new Date().getTime()+"");
			map.put("originalRequest", "{ua:'"+req.getHeader("user-agent")+"',ip:'"+ CommonUtils.getIp(req)+"',referer:'"+(StringUtils.isBlank(req.getHeader("referer"))? "-":req.getHeader("referer"))+"'}");
			map.put("format", WyqBusinessConfig.format);






			return map;
		}
		return null;
	}

	/**
	 * 微热点业务接口，初始化参数
	 *
	 * @throws Exception
	 */
	public static Map<String, Object> getDataInitMap(HttpServletRequest req){
		Map<String, Object> map = new HashMap<>();
		map.put("key", SysConfig.cfgMap.get("API_INTRA_BUSINESS_KEY"));
		map.put("product", WyqBusinessConfig.product);
		map.put("platform", WyqBusinessConfig.platform);
		map.put("softType", WyqBusinessConfig.softType);
		map.put("version", WyqBusinessConfig.version);
		map.put("timeStamp", new Date().getTime()+"");
		map.put("originalRequest", "{ua:'"+req.getHeader("user-agent")+"',ip:'"+CommonUtils.getIp(req)+"',referer:'"+(StringUtils.isBlank(req.getHeader("referer"))? "-":req.getHeader("referer"))+"'}");
		map.put("format", WyqBusinessConfig.format);
		return map;
	}

	/**
	 * 用户标示加密
	 *
	 * @param metedata
	 * @return
	 */
	public static String encryptString(String metedata) {
		if (StringUtils.isBlank(metedata)) {
			return null;
		}
		metedata += "@" + MD5Utils.MD5Encode(metedata + "$$$$").substring(0, 8);
		return metedata;
	}
	/**
	 * 请求接口参数加密
	 *
	 * @param params
	 * @return
	 */
	public static String encryptCode(Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			String salt = "wyq.intra.business.api@2016";

			// 排序
			List<String> paramKeyList = new ArrayList(params.keySet());
			Collections.sort(paramKeyList);

			// 拼接
			StringBuilder sb = new StringBuilder();
			for (String k : paramKeyList) {
				sb.append("&").append(k).append("=").append(params.get(k));
			}
			String jointParams = sb.toString().replaceFirst("&", "");
			System.out.println("encryptCode = [" + salt + jointParams + "]");

			// 加密
			String encryptCode = MD5Utils.MD5Encode(salt + jointParams);
			return encryptCode;
		}
		return null;
	}

}
