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
         <a href="<%=webPath%>login.shtml" <s:if test='currentPage=="shouye"'> class="active"</s:if>>��ҳ</a>
         <a href="<%=webPath%>product.shtml" <s:if test='currentPage=="jieshao"'> class="active"</s:if> >��Ʒ����</a>
         <a href="<%=webPath%>novice.shtml" <s:if test='currentPage=="novice"'> class="active"</s:if> >��������</a>
         <a href="<%=webPath%>help.shtml" <s:if test='currentPage=="help"'> class="active"</s:if> >��������</a>
         <a href="<%=webPath%>downLoad.shtml" <s:if test='currentPage=="download"'> class="active"</s:if> >�ͻ�������</a>
      </div>
      <span class="r_btn abs"><a class="loginBtn">��¼</a><a href="javascript:void(0);" class="RegistBtn">ע��</a></span>
   </div>

	<script type="text/javascript">
	  /*
	* ���ܻ�������汾��Ϣ:
	*
	*/
   window.bs={
	   versions:function(){
		   var u = navigator.userAgent, app = navigator.appVersion;
		   return {//�ƶ��ն�������汾��Ϣ
		   trident: u.indexOf('Trident') > -1, //IE�ں�
		   presto: u.indexOf('Presto') > -1, //opera�ں�
		   webKit: u.indexOf('AppleWebKit') > -1, //ƻ�����ȸ��ں�
		   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //����ں�
		   //mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //�Ƿ�Ϊ�ƶ��ն�
		   mobile: !!u.match(/AppleWebKit.*Mobile.*/), //�Ƿ�Ϊ�ƶ��ն�
		   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios�ն�
		   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android�ն˻���uc�����
		   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //�Ƿ�ΪiPhone����QQHD�����
		   iPad: u.indexOf('iPad') > -1, //�Ƿ�iPad
		   webApp: u.indexOf('Safari') == -1 //�Ƿ�webӦ�ó���û��ͷ����ײ�
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
		    var ua = navigator.userAgent.toLowerCase();//��ȡ�ж��õĶ���
			if (ua.match(/WeiBo/i) == "weibo") {
				 //������΢���ͻ��˴�
				document.querySelector(".RegistBtn").style.display="none";
				console.log("������΢���ͻ��˴�");
				$(function(){
					$(".loginBtn").on("click",function(){
						location.href="http://apps.weibo.com/3960037780/8rXM111J";
					});
				});
		    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
		    	 //��΢���д�
		    	document.querySelector(".RegistBtn").style.display="none";
		    	console.log("��΢���д�");
		    	$(function(){
					$(".loginBtn").on("click",function(){
						location.href="<%=njxBasePath%>/weiboHome.action";
					});
				});
		    }else{
		    	//�ֻ��������
		    	console.log("�ֻ��������");
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
			//pc�д�
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
