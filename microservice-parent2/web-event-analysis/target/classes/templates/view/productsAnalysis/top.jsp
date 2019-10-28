<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.xd.iis.sp.weidingyue.SystemConfig"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<html>
<head>
<%
	String njxBasePath = request.getContextPath();
	String base = request.getContextPath();
	
	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");
	String staticResourcePath = systemConfig.getFileviewPath() + "web";
	String staticResourcePathH5 = systemConfig.getFileviewPathH5();
	String qrCodeImg = systemConfig.getQrCodeImg();
	Long SYSTEM_INIT_TIME = SystemConfig.SYSTEMINITTIME;
%>
 
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="renderer" content="webkit">

<title>新浪微热点(微舆情)官方网站（wrd.cn）-社会化大数据搜索|热度指数|传播分析|口碑分析|微博情绪</title>
<meta name="author" content="" />
<meta name="keywords" content="微信舆情监测,微博舆情监控,舆情监测, 舆情APP ,舆情客户端，舆情软件,互联网舆情,视频舆情,海外媒体，舆情APP ,舆情客户端，舆情预警" />
<meta name="description" content="新浪微热点是中国最大的微博热点舆论服务平台，提供网页、微博、微信等全媒体热点信息网以及网络信息,负面事件分析,信息管理,军犬舆情, 热度事件分析报告,信息监控, 信息监测软件,海外媒体监测,竞争情报服务等" />		
<meta name="searchtitle" content="微信热点监测,微博热点监控,信息监测, 热点APP ,热点客户端，热点软件,互联网热点,视频热点,海外媒体，热点APP ,热点客户端，信息预警" />

<!-- 
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?bda531e3d034b85211d0555ecaa6517b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script> -->

</head>
  

<!-- 
// 轻应用
<script type='text/javascript' src='http://tjs.sjs.sinajs.cn/open/thirdpart/js/frame/appclient.js' charset='utf-8'></script>
<script>
	$(function() {
		setInterval(function() {
			if (App) {
				document.body.style.height = '0px';
				document.body.style.height = 'auto';
				App.trigger('setPageHeight', $('body').css('height'));
			}
		}, 1000);
	});
</script>

// 微博内部应用
<script src="http://js.t.sinajs.cn/t5/page/js/lib/open/iframeconnect.js"></script>
<script>
	$(function() {
		setInterval(function() {
			document.body.style.height = '0px';
			document.body.style.height = 'auto';
			iconnect.trigger('setHeight', $('body').css('height').replace('px', ''));
		}, 1000);
	});
</script>
 -->