/**   
 * Copyright 2017 ��˾��. All rights reserved.
 * 
 * @Title: HttpRequestUtils.java 
 * @Prject: wyq-wx-app
 * @Package: com.wyq.wx.util 
 * @Description: TODO
 * @author: ��˫��   
 * @date: 2017��1��11�� ����9:35:00 
 * @version: V1.0   
 */
package com.miduchina.wrd.eventanalysis.utils;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HttpRequestUtils
 * @Description: TODO
 * @author: 许双龙
 * @date: 2017年1月11日 下午9:35:00
 */
@SuppressWarnings("deprecation")
public class HttpRequestUtils {
	/**
	 * 发送GET请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url, String params) {
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			if (StringUtils.isNotBlank(params)) {
				String paramArray[] = params.split("&");
				List<String> paramList = new ArrayList<>();
				for (String string : paramArray) {
					String singleparamArray[] = string.split("=");
					if (singleparamArray.length == 2) {
						paramList.add(singleparamArray[0] + "=" + URLEncoder.encode(singleparamArray[1], "UTF-8"));
					}
				}
				urlWithParams = urlWithParams + "?" + StringUtils.join(paramList.toArray(), "&");
			}
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			httpGet = new HttpGet(urlWithParams);
			httpGet.setHeader("Connection", "Close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resultEntity, "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null)
				httpGet.releaseConnection();
		}
		System.out.println("GET URL = [" + urlWithParams + "]  请求时长：" + (System.currentTimeMillis() - start) + "ms");
		return rtnStr;
	}
	/**
	 * 发送GET请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendGet(String url, Map<String, Object> params) {
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			if (MapUtils.isNotEmpty(params)) {
				List<String> paramList = new ArrayList<String>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (entry.getValue() != null)
						paramList.add(
								entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8"));
				}
				urlWithParams += "?" + StringUtils.join(paramList.toArray(), "&");
			}
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			httpGet = new HttpGet(urlWithParams);
			httpGet.setHeader("Connection", "Close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resultEntity, "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null)
				httpGet.releaseConnection();
		}
		System.out.println("GET URL = [" + urlWithParams + "]  请求时长：" + (System.currentTimeMillis() - start) + "ms");
		return rtnStr;
	}
	/**
	 * 发送GET请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String sendGet(String url, String params, String encode) {
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			if (StringUtils.isNotBlank(params)) {
				String paramArray[] = params.split("&");
				List<String> paramList = new ArrayList<>();
				for (String string : paramArray) {
					String singleparamArray[] = string.split("=");
					if (singleparamArray.length == 2) {
						paramList.add(singleparamArray[0] + "=" + URLEncoder.encode(singleparamArray[1], encode));
					}
				}
				urlWithParams = urlWithParams + "?" + StringUtils.join(paramList.toArray(), "&");
			}
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			httpGet = new HttpGet(urlWithParams);
			httpGet.setHeader("Connection", "Close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resultEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resultEntity, encode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null)
				httpGet.releaseConnection();
		}
		System.out.println("GET URL = [" + urlWithParams + "]  请求时长：" + (System.currentTimeMillis() - start) + "ms");
		return rtnStr;
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 * @param url 发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String params) {
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			httpPost = new HttpPost(urlWithParams);
			httpPost.setHeader("Connection", "Close");
			if (StringUtils.isNotBlank(params)) {
				String paramArray[] = params.split("&");
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				for (String string : paramArray) {
					String singleparamArray[] = string.split("=");
					if (singleparamArray.length == 2) {
						postParams
								.add(new BasicNameValuePair(singleparamArray[0], String.valueOf(singleparamArray[1])));
					}
				}
				urlWithParams = urlWithParams + "?" + StringUtils.join(postParams.toArray(), "&");
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, "UTF-8");
				httpPost.setEntity(entity);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity resEntity = httpResponse.getEntity();
				rtnStr = EntityUtils.toString(resEntity, "GBK");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null)
				httpPost.releaseConnection();
		}
		System.out.println("Post [" + urlWithParams + "] 请求时长：" + (System.currentTimeMillis() - start) + "ms");
		return rtnStr;
	}
	/**
	 * 发送POST请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPost(String url, Map<String, Object> params) {
		long start = System.currentTimeMillis();
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String urlWithParams = url;
		String rtnStr = null;
		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			httpPost = new HttpPost(urlWithParams);
			httpPost.setHeader("Connection", "Close");
			if (MapUtils.isNotEmpty(params)) {
				List<NameValuePair> postParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (entry.getValue() != null)
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null)
				httpPost.releaseConnection();
		}
		System.out.println("Post [" + urlWithParams + "] 请求时长：" + (System.currentTimeMillis() - start) + "ms");
		return rtnStr;
	}
	public static String sendGet(String url) {
		return sendGet(url, "");
	}
	public static String sendPost(String url) {
		return sendPost(url, "");
	}
	public static String sendGetRequest(String url) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000 * 60 * 5);
			httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60 * 5);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Connection", "Close");
			HttpResponse httpResponse = httpClient.execute(httpGet);
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = bufferedReader.readLine()) != null)
				sb.append(line);
			bufferedReader.close();
			httpGet.releaseConnection();
			return sb.toString();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	// post请求
	public static String sendWXPost(String param, String url) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			// out = new PrintWriter(conn.getOutputStream());
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "utf-8"));
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
