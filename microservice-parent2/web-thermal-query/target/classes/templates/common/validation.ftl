<!DOCTYPE html PUBLIC "-//W3C//Dtd Xhtml 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/Dtd/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.xd.iis.sp.weiyuqing.pay.util.JedisUtil"%>
<%@page import="com.xd.iis.sp.weiyuqing.pay.util.Constants"%>
<%@ page import="java.util.Date"%>
<%@ page contentType="image/jpeg"%>
<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.io.OutputStream"%>
<jsp:useBean id="image" scope="page" class="com.xd.iis.sp.weiyuqing.pay.util.Validation" />
<%
	//no-cache指示请求或响应消息不能缓存
	response.setHeader("Cache-Control", "no-cache");
	response.setContentType("image/png");
	OutputStream os = response.getOutputStream();
	String str = image.getCertPic(110, 45, os);
	String c = request.getParameter("c");
	if (c == null || (c = c.trim()).length() == 0) {
		c = "reg";
	}
	String vcodeKey = Constants.generateJedisKey(new StringBuilder(Constants.JEDIS_KEYS_LOGIN_VCODE).append(c).toString());
	System.out.println("validation jsp c = [" + c + "] str = [" + str + "] vcodeKey = [" + vcodeKey + "]");
	System.out.println("validation jsp c = [" + c + "] obj1 = [" + JedisUtil.getAttribute(vcodeKey) + "]");
	
	if (StringUtils.isBlank(JedisUtil.getAttribute(vcodeKey)))
		JedisUtil.setAttribute(vcodeKey, str, 120);
	System.out.println("validation jsp c = [" + c + "] obj2 = [" + JedisUtil.getAttribute(vcodeKey) + "]");
	out.clear();
	out = pageContext.pushBody();
	System.out.println("validation jsp c = [" + c + "] obj3 = [" + JedisUtil.getAttribute(vcodeKey) + "]");
%>