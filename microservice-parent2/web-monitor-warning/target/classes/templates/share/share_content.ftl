<%@ page language="java" contentType="text/html; charset=gbk" 	pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String base = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title><s:property value="#attr.con.title"/></title>
<link href="<%=base%>/css/fxmail.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=base %>/css/fsgallery.css" /> 
</head>
 <script type="text/javascript" src="<%=base%>/js/jquery-1.10.2.min.js"></script>
 <script type="text/javascript" src="<%=base%>/js/zepto.js"></script>
 <script type="text/javascript" src="<%=base%>/js/scrolltopcontrol.js"></script>
 <script type="text/javascript" src="<%=base%>/js/Marquee.js"></script>
 <script type='text/javascript' src='<%=base %>/js/fs_forse.js'></script>
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
            
            //一次滚动一屏
        	$('#marquee5').kxbdSuperMarquee({
        		isEqual:false,
        		distance:100,
        		time:4,
        		direction:'up'
        	});
			
        });
        
        $(document).ready(function() {
        	$("p[id^='gallery_']").each(function(){
        		$(this).find('img').fsgallery();
        	});
        });
    </script>
<body>

 <div class="fxmail"  id="container">
    
    <!--头部代码 start-->
      <div class="mail_heard">
      <img src="images/icon-60.png"  class="logo" style="width:50px;height:50px;line-height:60px;margin-top:-5px;"/> 
      <%-- <a class="more" href="#"></a> --%>
      <div class="topRight">
	      <a class="btn btn-home" href="${dnsStringh5}" id="shouyeHref"></a>
	      <a class="btn btn-download" href="http://d.51wyq.cn"></a>
	      <a class="btn btn-help" href="${helpUrl}"></a>
   	  </div>
      <ul class="top_nav">
        <li><a href="${dnsString}">首页</a></li>
        <li><a href="${dnsString}/help.shtml">帮助中心</a></li>          
      </ul>
     
      </div>
     <!--头部代码 end-->
     <div class="h60"></div>
     <!--详细内容 start-->
     <div class="info">
       <div class="info_tit">
       		<a href="<s:property value='#attr.con.webpageUrl'/>">
       			<s:if test="#attr.con.customFlag1!=4">
       				<img src="<%=base%>/images/yq_icon1.jpg" width="15" height="15"> 
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
				
	       		<s:if test="#attr.con.originType=='wb'">
       				<s:if test="#attr.con.author!=null&&#attr.con.author!=''">
       					<s:property value="#attr.con.author"/>
       				</s:if>
       				<s:else>
       					<s:property value="#attr.con.captureWebsiteName"/>
       				</s:else>
       			</s:if>
	       		<s:else>
	       			<s:property value="#attr.con.title"/> 
	       		</s:else>
       		</a>
       </div>
       <div class="attr">
         <ul>
           <li>采集自：<s:property value="#attr.con.captureWebsiteName"/> </li>
           <s:if test="#attr.con.originType!='wb'" > 
           		<li>稿件来源： <s:property value="#attr.con.originWebsiteName"/> </li>
           </s:if>
           <li>发布时间：<s:date name="#attr.con.published" format="yyyy-MM-dd HH:mm" /></li>
           <li>内容属性： 
           		<s:if test="#attr.con.customFlag1!=4">
           			<font style=" color:red;">敏感</font>   
           		</s:if>
           		<s:else>
           			非敏感
           		</s:else>
           		<span style="margin-left:10%;">相同文章：
					<font style="color:red;">
						<s:if test="#attr.con.repeatNum!=0">
							<s:property value="#attr.con.repeatNum"/> 
						</s:if>
						<s:else>
							1
						</s:else>
					</font>
				</span>
           </li>
           <li><a href="<s:property value='#attr.con.webpageUrl'/>" style="color:#008fe6;">查看原文链接</a></li>
         </ul>
       </div>
       
       <s:if test="#attr.con.originType=='wb'">
       		<!-- 微博content的展示 start -->
				<s:if test="#attr.con.forwarderContent==''||#attr.con.forwarderContent==null">
						<div class="conBox">
							<p>
								<s:property value="#attr.con.content" escape="false"/>
							</p>
							<s:if test="#attr.con.images!=null">
								<p class="photo photo2 thumbnails" id="gallery_<s:property value="#attr.con.id" />">
									<s:iterator value="#attr.con.images" id="img">
										<img src='<s:property value='#img.bmiddlePic'/>'/>
									</s:iterator>
								</p>
							</s:if>
						</div>
				<!-- 微博content的展示 end -->
			</s:if>
			<s:else>
				 <div class="conBox">
					<p>
						<s:property value="#attr.con.content" escape="false"/>
					</p>
			     </div>
			</s:else>
		</s:if>
		<s:else>
			 <div class="conBox">
				<p>
					<s:property value="#attr.con.content" escape="false"/>
				</p>
		     </div>
			 
		</s:else>
      
    </div>
    
    <s:if test="#attr.con.forwarderContent!=''&&#attr.con.forwarderContent!=null">
    	<div class="zfconBox">
				<p>
					<s:property value="#attr.con.forwarderContent" escape="false"/>
				</p>
				<s:if test="#attr.con.images!=null">
					<p class="photo photo2 thumbnails" id="gallery_<s:property value="#attr.con.id" />">
						<s:iterator value="#attr.con.images" id="img">
							<img src='<s:property value='#img.bmiddlePic'/>'/>
						</s:iterator>
					</p>
				</s:if>
		</div>
    </s:if>
    
    <!--详细内容 end-->
 
  <div class="h60"></div>
  <style type="text/css">
  	/* marquee5 */
	#marquee5{height:20px;overflow:hidden;}
	#marquee5 ul li{height:20px;padding:0;line-height:20px;overflow:hidden;}
  </style>

    <!--底部 start-->
    <div class="footer">
     <div class="text"><i class="icon_logo"></i>微热点<br>
     	<div id="marquee5">
		<ul>
			<li>免费注册，即送即用。</li>
			<li>人人都是数据专家</li>
			<li>只用微热点，一套就够了。</li>
			<li>超低价格，轻松拥有。</li>
		</ul>
	</div>
	</div>
      <a class="btn-download" href="http://d.51wyq.cn">下载客户端</a>

    </div>
    <!--底部 end-->
 </div>
  
 
 <%--  <div class="zhezhao"></div>
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
