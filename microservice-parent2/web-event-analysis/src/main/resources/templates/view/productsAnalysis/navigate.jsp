<%@page import="com.xd.iis.sp.weidingyue.SystemConfig"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>
<%
	String njxBasePath = request.getContextPath();

	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
	SystemConfig systemConfig = (SystemConfig) wac.getBean("systemConfig");
	String staticResourcePath = systemConfig.getFileviewPath() + "web";
	String webPath = systemConfig.getSystemWeb();
%>
<link href="<%=staticResourcePath%>/css/openWeiboImg.css" rel="stylesheet">
<link href="<%=staticResourcePath%>/css/accordion.css" rel="stylesheet"/>
<script src='<%=staticResourcePath%>/js/prettify.js'></script>
<script src='<%=staticResourcePath%>/js/jquery.slimscroll.js'></script>
 <%
	((OgnlValueStack) request.getAttribute("struts.valueStack")).set("currentPage", request.getParameter("currentPage")); 
	((OgnlValueStack) request.getAttribute("struts.valueStack")).set("noNeedForm", request.getParameter("noNeedForm")); 
 %>
 
	<div id="head" class="rel" <s:if test = "#attr.isApp == 1">style = 'display:none;'</s:if>>
     <span class="logo abs"></span>
      <div class="nav abs">
         <a href="<%=webPath%>login.shtml" <s:if test='currentPage=="shouye"'> class="active"</s:if>>首页</a>
         <a href="<%=webPath%>product.shtml" <s:if test='currentPage=="jieshao"'> class="active"</s:if> >产品介绍</a>
         <a href="<%=webPath%>novice.shtml" <s:if test='currentPage=="novice"'> class="active"</s:if> >陪你入门</a>
         <a href="<%=webPath%>help.shtml" <s:if test='currentPage=="help"'> class="active"</s:if> >帮助中心</a>
         <a href="<%=webPath%>downLoad.shtml" <s:if test='currentPage=="download"'> class="active"</s:if> >客户端下载</a>
      </div>
      <span class="r_btn abs"><a class="loginBtn">登录</a><a href="javascript:void(0);" class="RegistBtn">注册</a></span>
   </div>

	<script type="text/javascript">
	  /*
	* 智能机浏览器版本信息:
	*
	*/
   window.bs={
	   versions:function(){
		   var u = navigator.userAgent, app = navigator.appVersion;
		   return {//移动终端浏览器版本信息
		   trident: u.indexOf('Trident') > -1, //IE内核
		   presto: u.indexOf('Presto') > -1, //opera内核
		   webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		   //mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
		   mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
		   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
		   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
		   iPad: u.indexOf('iPad') > -1, //是否iPad
		   webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		   };
	   }(),
	   language:(navigator.browserLanguage || navigator.language).toLowerCase()
   }
		var shareCode = "${shareCode}";
		var url = location.href;
		var isApp = document.getElementById("isApp").value;
		var Uid = "${admin}";
		if((Uid || url.indexOf("productsAnalysisChatLook") != -1) && bs.versions.mobile){
			var text = document.querySelector("#style").innerText.replace("margin-top: 70px;","margin-top: 0px;")
			document.querySelector("#style").innerText = text;
			document.querySelector("#head").style.display="none";
		}else if(bs.versions.mobile){
		    var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
			if (ua.match(/WeiBo/i) == "weibo") {
				 //在新浪微博客户端打开
				document.querySelector(".RegistBtn").style.display="none";
				console.log("在新浪微博客户端打开");
				$(function(){
					$(".loginBtn").on("click",function(){
						location.href="http://apps.weibo.com/3960037780/8rXM111J";
					});
				});
		    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
		    	 //在微信中打开
		    	document.querySelector(".RegistBtn").style.display="none";
		    	console.log("在微信中打开");
		    	$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/weiboHome.action";
					});
				});
		    }else{
		    	//手机浏览器打开
		    	console.log("手机浏览器打开");
		    	$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/indexLocal.action";
					});
					$(".RegistBtn").on("click",function(){
						location.href="<%=njxBasePath%>/register.action";
					});
				});
		    }
		}else{
			//pc中打开
			$(function(){
				$(".loginBtn").on("click",function(){
					location.href="<%=webPath%>logon.shtml";
				});
				$(".RegistBtn").on("click",function(){
					location.href="<%=webPath%>user/goRegister.shtml";
				});
			})
		}

	
	</script>
