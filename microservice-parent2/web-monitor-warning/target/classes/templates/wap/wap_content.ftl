<?xml version="1.0" encoding="utf-8" ?> 
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<!-- 默认版本 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
 <meta charset="gbk">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title><s:property value="#attr.con.title"/></title>
<link href="css/YJmail.css" rel="stylesheet" type="text/css" />
</head>
 <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
     <script type="text/javascript" src="js/zepto.js"></script>
<script type="text/javascript" src="js/scrolltopcontrol.js"></script>
  <script>
  var isWeiboClient = /weibo/i.test(navigator.userAgent);
        $(function(){
        	/* if(isWeiboClient){
				$("#shouyeHref").attr("href","http://apps.weibo.com/3960037780/QoXyelm");
			} */
            var num2= 1;
            $(".more").on("touchstart",function(e){
                   if(num2) {
                        num2--;
                        $(".zhezhao").show();
                        $(".content").show();
                    }else{
                        num2++;
                        $(".zhezhao").hide();
                        $(".content").hide();
                    }
			});
        });
        
        //列表
        function list(){
        	frmPopWin.action="more.action";
    		frmPopWin.submit();
        }
    </script>
<body>

 <div class="YJmail"  id="container">
     <FORM method=post name=frmPopWin action="">
    	<input type="hidden" name="listFlag" id="listFlag" value="1"/>
    	<input type="hidden" name="reviewCode" id="reviewCode" value="${reviewCode}"/>
    	<input type="hidden" name="highLightKeywords" id="highLightKeywords" value="${highLightKeywords}"/>
    	<input type="hidden" name="platform" id="platform" value="${platform }"/>
    	<input type="hidden" name="contentId" id="contentId" value="${contentId}"/>
    	<input type="hidden" name="defaultRepeatNum" id="defaultRepeatNum" value="${defaultRepeatNum}"/>
    <!--头部代码 start-->
      <div class="mail_heard">
      <img src="images/icon-60.png"  class="logo" style="width:50px;height:50px;line-height:60px;margin-top:-5px;"/> 
      <div class="topRight">
      	  <a class="btn btn-list" href="javascript:list();"></a>
	      <a class="btn btn-home" href="${dnsStringh5}" id="shouyeHref"></a>
	      <a class="btn btn-download" href="http://d.51wyq.cn"></a>
	      <a class="btn btn-help" href="${helpUrl}"></a>
   	  </div>
      <ul class="top_nav">
        <li><a href="${dnsString}">首页</a></li>
        <li><a href="javascript:list();">列表</a></li>
        <li><a href="${dnsString}/help.shtml">帮助中心</a></li>          
      </ul>
     
      </div>
     <!--头部代码 end-->
     <div class="h60"></div>
     <!--详细内容 start-->
     <div class="info">
       <div class="info_tit">
       	<a href="${con.webpageUrl}">
       		<s:if test="con.customFlag1!=4">
       			<img src="images/yq_icon1.jpg" width="15" height="15">
       		</s:if>
       		<s:if test='con.captureWebsiteName=="新浪微博"'>
				<img src='images/sinawb.png' width="25">
			</s:if>
			<s:if test='con.captureWebsiteName=="微信"'>
				<img src='images/wx.png' width="25">
			</s:if>
			<s:if test='#attr.con.captureWebsiteName=="腾讯微博"'>
				<img src='images/qqwb.png' width="25">
			</s:if>
       		${con.title}
       	</a>
       </div>
       <div class="attr">
         <ul>
           <li>采集自：
           	<s:if test='con.captureWebsiteName!=null'><s:property value="con.captureWebsiteName" /></s:if>
			<s:else>未知</s:else>
           </li>
           <li>稿件来源： ${con.originWebsiteName}</li>
           <li>发布时间：
           		<s:set name="pdate"	value="#con.published" />
				<fmt:formatDate value='${con.published}' pattern='yyyy年MM月dd日 HH:mm'/>
           </li>
           <li>内容属性： 
           	   <s:if test="con.customFlag1==4">
           	   		<font style=" color:blue;">非敏感</font>    
           	   </s:if>
           	   <s:else>
           	   		<font style=" color:red;">敏感</font>    
           	   </s:else>
	           <span style="margin-left:10%;">
	           	相同文章： <font style="color:red;">${con.repeatNum}</font>
	           </span>
           </li>
           <li><a href="${con.webpageUrl}" style="color:#008fe6;">查看原文链接</a></li>
         </ul>
       </div>
       <div class="conBox">
       <p>${con.content}</p>
       </div>
     </div>
    <!--详细内容 end-->
 
  <div class="h60"></div>
  
  <!--返回列表 start-->
    <a href="javascript:history.go(-1);" title="返回列表"><img src="images/back-64.png"  class="back"></a>

    <!--返回列表 end-->
    </FORM>
 </div>
  
 
  <%-- <div class="zhezhao"></div>
    <div class="content">
        <nav class="rightNav">
            <ul>
            	<li class="menu menu1"><a href="${dnsString}">首页</a></li>
        		<li class="menu menu4"><a href="${dnsString}/help.shtml">帮助中心</a></li>          
            </ul>
        </nav>
    </div> --%>
</body>
</html>
