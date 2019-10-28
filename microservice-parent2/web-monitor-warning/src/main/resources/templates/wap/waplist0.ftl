<#--<%@ page isELIgnored="false"%>-->
<#--<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>-->
<#--<%-->
	<#--String base = request.getContextPath();-->
<#--%>-->
<#assign base = request.contextPath />
<#--<script type="text/javascript">-->
    <#--var base='${base}';-->
<#--</script>-->
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<!-- 默认版本 -->
<html>
<head> 
<title>预警列表</title>
<meta forua="true" http-equiv="Cache-Control" content="max-age=0"/>
<meta http-equiv="Cache-Control" content="no-cache"/>
<link rel="shortcut icon" sizes="16x16" href="${base}/images/addhomescreen/icon-16x16.png">
<link rel="apple-touch-icon-precomposed" href="${base}/images/addhomescreen/icon-152x152.png">
<link rel="shortcut icon" sizes="196x196" href="${base}/images/addhomescreen/icon-196x196.png">
<link href="${base}/css/YJmail.css" rel="stylesheet" type="text/css"/>
<link href="${base}/css/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var actionBase = "${base}";
	</script>
<script type="text/javascript" src="${base}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${base}/js/zepto.js"></script>
</head>
<#--<%# include file="top.ftl"%>-->
<#include "top.ftl"/>
	<body>
		 <div class="YJmail"  id="container">
		    <!--banner代码 start-->
		    <section class="section" style="cursor: pointer;">
		     	<a href = "http://www.wrd.cn" style="display: block;height: 100%;width: 20%;position: absolute;top: 0px;left:0px;z-index: 4;"></a>
		        <div class="mail_Banner rel">
		             <div class="picBg"></div>
		             <div></div>
		        </div>
		    </section>
		    <!--banner代码 end-->
		     <div class="h60"></div>
		     
		     
		     <!--列表内容 start-->
		     <div class="prompt">
		        <img src="${base}/images/yj_bg.png"/>
		        <p class="text">SORRY ~ 超过三天的预警信息已送入垃圾库</p>
		     </div>
		    <!--列表内容 end-->
		 
		  <div class="h60"></div>
		    
         <div class="footer">
             <a href="javascript:recordDown()">
                 <img class="visible-xs" src="${base}/images/bottomPic2.jpg"/>
                 <img class="visible-lg" src="${base}/images/bottomPic2.jpg"/>
             </a>
         </div>
		 </div>
		 <script>
		  var isWeiboClient = /weibo/i.test(navigator.userAgent);
		  var shouyeHref = "http://www.wrd.cn";
		  var appHref = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
		  $(function(){
			  if (/weibo/i.test(navigator.userAgent.toLowerCase())) {
	                shouyeHref = "http://apps.weibo.com/3960037780/8rXM111J";
	                appHref = "http://d.51wyq.cn";
	            }
	            if (/micromessenger/i.test(navigator.userAgent.toLowerCase())) {
	                shouyeHref = "http://h5.51wyq.cn";
	            }
		  });
		  function recordDown(){
			  location.href=appHref;
	      }
		 </script>
	</body>
</html>