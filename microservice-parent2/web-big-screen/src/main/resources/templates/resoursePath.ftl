<%@page import="com.xd.iis.sp.weiyuqing.SystemConfig"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%
	String njxBasePath = request.getContextPath();
 	String base = request.getContextPath();
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");
//	String staticResourcePath = systemConfig.getCdnResourcesPath() + "web";
	String staticResourcePath = request.getContextPath();
	String qrCodeImg = systemConfig.getQrCodeImg();
 %>