package com.miduchina.wrd.eventanalysis.utils;

import com.miduchina.wrd.CodeConstant;
import com.miduchina.wrd.common.redis.util.RedisUtils;
import com.miduchina.wrd.common.redis.util.SysConfig;
import com.miduchina.wrd.constant.SystemConstants;
import com.miduchina.wrd.eventanalysis.log.model.UserRes;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class LoginUtils {

	public final static String COOKIE_PATH = "/";
	public final static String SESSION_WEB_FLAG_OF_COOKIE = "wyq";

	/**
	 * 设置cookie
	 * return sid
	 */
	public static void setSidToCookie(HttpServletRequest request, HttpServletResponse response, String sid) {
		String domain = getBaseUrl(getUrlPrefix(request));
		Cookie cookie = new Cookie(getCookieName(), sid);
		cookie.setMaxAge(SystemConstants.SYS_ADMIN_CACHE_TIME);
		if ((domain != null) && (domain.indexOf('.') != -1) && !domain.equals("0.0.1")) {
			cookie.setDomain(domain);
		}
		cookie.setPath(COOKIE_PATH);
		response.addCookie(cookie);
	}

	/**
	 * 从cookie获取sid
	 */
	public static String getSidFromCookie(HttpServletRequest request) {
		String sid = null;
		Cookie cookie = getCookie(request, getCookieName());
		if ((cookie != null) && (!cookie.getValue().isEmpty())) {
			sid = cookie.getValue();
			if(StringUtils.isNotBlank(sid)){
				String[] utype = sid.split("_");
				if ((utype == null) || ("".equals(utype)) || (utype.length < 5)) {
					System.out.println("utype is null or utype.length < 5, sid=" + sid);
					sid = null;
				}
			}
		} else {
			sid = request.getParameter("sid");
			System.out.println("cookie is null ");
		}
  		return sid;
	}

	/**
	 * 从cookie获取sid
	 */
	public static Integer getUserIdFromSid(String sid) {
		Integer userId = null;
		Pattern pattern = Pattern.compile("^[\\d]*$");
		if(StringUtils.isNotBlank(sid)){
			String[] strs = sid.split("_");
			if(strs.length == 5 && pattern.matcher(strs[3]).matches()){
				userId = Integer.valueOf(strs[3]);
			}
		}
		return userId;
	}

	/**
	 * sid存入cookie，添加子账号表示或单点登录标识
	 */
	public static void saveLoginFlag(HttpServletRequest request, HttpServletResponse response, UserRes userRes){
		if(userRes != null && CodeConstant.SUCCESS_CODE.equals(userRes.getCode())){
			String sid = userRes.getSid();
			Boolean subUser = userRes.getUserInfo().getSubUser();
			Integer userId = userRes.getUserInfo().getUserId();
			//sid存入cookie
			LoginUtils.setSidToCookie(request, response, sid);
			//非子账号登录记录单点登录标识
			if (userRes.getUserInfo().getSubUser() == null || !userRes.getUserInfo().getSubUser()){
				RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SSO_FLAG + userId), sid, SystemConstants.SYS_ADMIN_CACHE_TIME);
			}else{
				RedisUtils.setAttribute(RedisUtils.generateJedisKey(SystemConstants.JEDIS_KEYS_SUB_USER + sid), subUser.toString(), SystemConstants.SYS_ADMIN_CACHE_TIME);
			}
		}
	}

	/**
	 * 清除cookie中的sid
	 */
	public static void clearCookieSid(HttpServletRequest request, HttpServletResponse response){
		Cookie cookie = getCookie(request, getCookieName());
		if ((cookie != null) && (!cookie.getValue().isEmpty())) {
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		} else {
			System.out.println("cookie is null ");
		}
	}

	public static String getCookieName(){
		return new StringBuilder(SESSION_WEB_FLAG_OF_COOKIE).append("_").append(SysConfig.cfgMap.get("WEBID_COOKIE_NAME")).toString();
	}

	public static Cookie getCookie(HttpServletRequest request, String cookieName){
		if(request == null || StringUtils.isBlank(cookieName)){
			return null;
		}
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies == null){
			return null;
		}
		for (int i = 0; i < cookies.length; i++) {
			if (cookieName.equals(cookies[i].getName())) {
				cookie = cookies[i];
			}
		}
		return cookie;
	}

	public static String getBaseUrl(String url) {
		try {
			URL burl = new URL(url);
			String host = burl.getHost();
			if (host.endsWith(".")) {
				host = host.substring(0, host.length() - 1);
			}
			int index = 0;
			String candidate = host;
			if (index >= 0) {
				index = candidate.indexOf('.');
				return candidate.substring(index + 1);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getUrlPrefix(HttpServletRequest request){
		StringBuffer url = new StringBuffer(request.getScheme());
		url.append("://");
		url.append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80) {
			url.append(":");
			url.append(port);
		}
		return url.toString();
	}
}