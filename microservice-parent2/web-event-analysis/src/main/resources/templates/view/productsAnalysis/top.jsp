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

<title>����΢�ȵ�(΢����)�ٷ���վ��wrd.cn��-��ữ����������|�ȶ�ָ��|��������|�ڱ�����|΢������</title>
<meta name="author" content="" />
<meta name="keywords" content="΢��������,΢��������,������, ����APP ,����ͻ��ˣ��������,����������,��Ƶ����,����ý�壬����APP ,����ͻ��ˣ�����Ԥ��" />
<meta name="description" content="����΢�ȵ����й�����΢���ȵ����۷���ƽ̨���ṩ��ҳ��΢����΢�ŵ�ȫý���ȵ���Ϣ���Լ�������Ϣ,�����¼�����,��Ϣ����,��Ȯ����, �ȶ��¼���������,��Ϣ���, ��Ϣ������,����ý����,�����鱨�����" />		
<meta name="searchtitle" content="΢���ȵ���,΢���ȵ���,��Ϣ���, �ȵ�APP ,�ȵ�ͻ��ˣ��ȵ����,�������ȵ�,��Ƶ�ȵ�,����ý�壬�ȵ�APP ,�ȵ�ͻ��ˣ���ϢԤ��" />

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
// ��Ӧ��
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

// ΢���ڲ�Ӧ��
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