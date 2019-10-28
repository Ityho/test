package com.miduchina.wrd.api.rankinglist.util;

import com.alibaba.fastjson.JSONObject;
import com.xd.tools.ownutils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
        "W", "X", "Y", "Z" };
	public static String regx = "^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$";//科学计数法正则表达式
	public static Pattern pattern = Pattern.compile(regx);
	/**
	 * 生成短码
	 *
	 * @return
	 */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();
	}

	public static List<String> getLocalIPNew() {
		List<String> list = new ArrayList<String>();
		Collection<InetAddress> colInetAddress = getAllHostAddress();
		for (InetAddress address : colInetAddress) {
			if (!address.isLoopbackAddress()) {
				if (!address.getHostAddress().equals("127.0.0.1")) {
					list.add(address.getHostAddress());
				}
			}
		}
		if (list.size() > 0) {
			return list;
		}
		return null;
	}
	private static Collection<InetAddress> getAllHostAddress() {
		try {
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			Collection<InetAddress> addresses = new ArrayList<InetAddress>();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = networkInterfaces.nextElement();
				Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}

			return addresses;
		} catch (SocketException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	/**
	 * 发送GET请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String sendGet(String url, Map<String, Object> params) {
		CloseableHttpClient httpClient = null;
		HttpGet httpGet = null;
		String urlWithParams = url;
		String rtnStr = null;

		try {
			long s = System.currentTimeMillis();
			if (params != null && !params.isEmpty()) {
				List<String> paramList = new ArrayList<String>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					paramList.add(entry.getKey() + "=" + entry.getValue());
				}
				urlWithParams += "?" + StringUtils.join(paramList.toArray(), "&");
			}

			httpClient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60 * 30).setConnectTimeout(1000 * 60 * 30).build();//设置请求和传输超时时间
			httpGet = new HttpGet(urlWithParams);
			httpGet.setConfig(requestConfig);
			httpGet.setHeader("Connection", "Close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resultEntity, "GBK");
			}
			System.out.println("CommonUtil.sendGet() : cost = [" + (System.currentTimeMillis() - s) + " ms.] URL = [" + urlWithParams + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}
		return rtnStr;
	}
	/**
	 * 发送POST请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String sendPost(String url, Map<String, Object> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			long s = System.currentTimeMillis();
			httpClient = HttpClients.createDefault();
			httpPost = new HttpPost(urlWithParams);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000 * 60 * 30).setConnectTimeout(1000 * 60 * 30).build();//设置请求和传输超时时间
			httpPost.setConfig(requestConfig);
			httpPost.setHeader("Connection", "Close");

			if (params != null && !params.isEmpty()) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					postParams.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
				}

				urlWithParams += "?" + StringUtils.join(postParams.toArray(), "&");

				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "UTF-8");
				httpPost.setEntity(entity);
			}

			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resEntity, "GBK");
			}
			System.out.println("CommonUtil.sendPost() : cost = [" + (System.currentTimeMillis() - s) + " ms.] URL = [" + urlWithParams + "]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return rtnStr;
	}
	/**
	 * 编码 ios-8859-1 转 utf-8
	 * @param arrAll
	 * @return utf-8
	 */
	public static String utf8(String arrAll){
		String utf8 = null;
		try {
			utf8= new String(arrAll.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utf8;
	}
	/**
	 * 格式化double类型
	 * @param str
	 * @return string
	 */
	public static String doubleFormat(double str){
		NumberFormat formatter = new DecimalFormat("0.000");
		String dbstr = formatter.format(str);
		return dbstr;
	}
	/**
	 * 格式化double类型 5位有效
	 * @param str
	 * @return string
	 */
	public static String doubleFormatFive(double str){
		NumberFormat formatter = new DecimalFormat("0.00000");
		String dbstr = formatter.format(str);
		return dbstr;
	}
	/**
	 * 获取ip
	 * @param request
	 * @return String
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
	 * 获取物理ip
	 * @return
	 */
	public static String getLocalIP() {
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "127.0.0.1";
	}

	/**
	 * 判断是否是科学记数法
	 */
	public static boolean isENum(String input){//判断输入字符串是否为科学计数法
		return pattern.matcher(input).matches();
	}
	/**日志信息
	 * 请求action名字--action方法--用户--时间---日志信息
	 * @param uid
	 * @param nowTime
	 * @param requestActionName
	 * @param logContent
	 */
	public static void logCommUtil(String uid,String nowTime,String requestActionName,String requestActionMethod,String logContent){
		StringBuffer sb = new StringBuffer();
		sb.append(" outlog : { ");
		if (StringUtils.isNotBlank(requestActionName)){
			sb.append("requestActionName : [ "+requestActionName+" ] - ");
		}
		if (StringUtils.isNotBlank(uid)){
			sb.append("User : [ " +uid+" ] - ");
		}
		if (StringUtils.isNotBlank(nowTime)){
			sb.append("logTime : [ "+nowTime+" ]  - ");
		}
		if (StringUtils.isNotBlank(logContent)){
			sb.append("logContent : [ "+logContent+" ]");
		}
		sb.append(" } ");
		System.out.println(sb.toString());
	}

	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b, "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static Integer getNewYearForWeek(){
		Calendar c = Calendar.getInstance();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(Calendar.MONDAY);
		return Integer.valueOf(c.get(Calendar.YEAR)+""+(c.get(Calendar.WEEK_OF_YEAR)<=9?"0"+c.get(Calendar.WEEK_OF_YEAR):c.get(Calendar.WEEK_OF_YEAR)));
	}

	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static void printLog(String module,String info){
        System.out.println(DateUtils.format(new Date()) + " [" + module + "] " + "[info] - " + info);
    }
	/**
	 * 解析时间字符串
	 */
	public static Date parseDateStr(String str){
		Date date = null;
		if(StringUtils.isNotBlank(str)){
			if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}:\\d{2}$", str)){
				date = DateUtils.parse(str, "yyyy-MM-dd HH:mm:ss");
			}else if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}:\\d{2}$", str)){
				date = DateUtils.parse(str, "yyyy-MM-dd HH:mm");
			}else if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}\\s{1}\\d{2}$", str)){
				date = DateUtils.parse(str, "yyyy-MM-dd HH");
			}else if(CommonUtils.match("^\\d{4}-\\d{2}-\\d{2}$", str)){
				date = DateUtils.parse(str, "yyyy-MM-dd");
			}else if(CommonUtils.match("^\\d{4}-\\d{2}", str)){
				date = DateUtils.parse(str, "yyyy-MM");
			}
		}
		return date;
	}

}
