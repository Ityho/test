package com.miduchina.wrd.eventanalysis.utils;

import com.miduchina.wrd.common.redis.util.SysConfig;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class DNSUtils {
//	/**
//     * 获取访问路径的DNS地址
//     * @param url
//     * @return
//     */
//    public static String getDNSUrl(String url){
//    	if(url!=null&&!"".equals(url)){
//    		if(url.indexOf("localhost:8080")!=-1){
//    			url = "http://localhost:8080/wyq_h5";
//    		}else if(url.indexOf("h1.wyq.cn")!=-1){
//    			url = "http://h1.wyq.cn";
//    		}else if(url.indexOf("h4.wyq.cn")!=-1){
//    			url = "http://h4.wyq.cn";
//    		}else{
//    			url = "http://h5.51wyq.cn";
//    		}
//    	}
//    	return url;
//    }

	/**
	 * 获取访问路径的DNS地址
	 * @param url
	 * @return
	 */
	public static String getDNSUrl(String url){
		if(StringUtils.isNotBlank(url)){
			if(url.contains("localhost:8080")){
				url = "http://localhost:8080/wyqh5";
			}
			url = SysConfig.cfgMap.get("SYSTEM_H5_URL");
		}
		return url;
	}

	/**
	 * 获取访问路径的DNS地址
	 * @param url
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request){
		String url = request.getRequestURL().toString();
		if(StringUtils.isNotBlank(url)){
			if(url.contains("localhost")){
				url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			}else {
				url = request.getScheme()+"://"+ request.getServerName()+request.getContextPath()+"/";
			}
		}
		return url;
	}
	/**
	 * 获取访问路径的DNS地址
	 * @param url
	 * @return
	 */
	public static String getWebDNSUrl(String url){
		if(StringUtils.isNotBlank(url)){
			url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
		}
		return url;
	}

	/**
	 * 获取访问路径的DNS地址
	 * @param url
	 * @return
	 */
	public static String getH5DNSUrl(String url){
		if(StringUtils.isNotBlank(url)){
			url = SysConfig.cfgMap.get("SYSTEM_WEB_URL");
		}
		return url;
	}

	/**
	 * 获取访问路径的DNS地址
	 * @param url
	 * @return
	 */
	public static String getDNSWarning(String str){
		if (str.indexOf("h5-beta4.51wyq.cn") >= 0){
			str = "http://warning-beta4.51wyq.cn/a";
		}
		if (str.indexOf("h5-beta.51wyq.cn") >= 0)
		{
			str = "http://warning-beta.51wyq.cn/a";
		}
		if (str.indexOf("h5.51wyq.cn") >= 0)
		{
			str = "http://warning.51wyq.cn/a";
		}
		if (str.indexOf("localhost:8080/wyq_h5") >= 0)
		{
			str = "warningb4.wyq.cn/a";
		}

		return str;
	}

}