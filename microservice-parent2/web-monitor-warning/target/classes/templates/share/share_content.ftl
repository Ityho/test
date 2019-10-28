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
            
            //һ�ι���һ��
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
    
    <!--ͷ������ start-->
      <div class="mail_heard">
      <img src="images/icon-60.png"  class="logo" style="width:50px;height:50px;line-height:60px;margin-top:-5px;"/> 
      <%-- <a class="more" href="#"></a> --%>
      <div class="topRight">
	      <a class="btn btn-home" href="${dnsStringh5}" id="shouyeHref"></a>
	      <a class="btn btn-download" href="http://d.51wyq.cn"></a>
	      <a class="btn btn-help" href="${helpUrl}"></a>
   	  </div>
      <ul class="top_nav">
        <li><a href="${dnsString}">��ҳ</a></li>
        <li><a href="${dnsString}/help.shtml">��������</a></li>          
      </ul>
     
      </div>
     <!--ͷ������ end-->
     <div class="h60"></div>
     <!--��ϸ���� start-->
     <div class="info">
       <div class="info_tit">
       		<a href="<s:property value='#attr.con.webpageUrl'/>">
       			<s:if test="#attr.con.customFlag1!=4">
       				<img src="<%=base%>/images/yq_icon1.jpg" width="15" height="15"> 
       			</s:if>
       			
       			<s:if test='con.captureWebsiteName=="����΢��"'>
					<img src='images/sinawb.png' width="25">
				</s:if>
				<s:if test='con.captureWebsiteName=="΢��"'>
					<img src='images/wx.png' width="25">
				</s:if>
				<s:if test='#attr.con.captureWebsiteName=="��Ѷ΢��"'>
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
           <li>�ɼ��ԣ�<s:property value="#attr.con.captureWebsiteName"/> </li>
           <s:if test="#attr.con.originType!='wb'" > 
           		<li>�����Դ�� <s:property value="#attr.con.originWebsiteName"/> </li>
           </s:if>
           <li>����ʱ�䣺<s:date name="#attr.con.published" format="yyyy-MM-dd HH:mm" /></li>
           <li>�������ԣ� 
           		<s:if test="#attr.con.customFlag1!=4">
           			<font style=" color:red;">����</font>   
           		</s:if>
           		<s:else>
           			������
           		</s:else>
           		<span style="margin-left:10%;">��ͬ���£�
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
           <li><a href="<s:property value='#attr.con.webpageUrl'/>" style="color:#008fe6;">�鿴ԭ������</a></li>
         </ul>
       </div>
       
       <s:if test="#attr.con.originType=='wb'">
       		<!-- ΢��content��չʾ start -->
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
				<!-- ΢��content��չʾ end -->
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
    
    <!--��ϸ���� end-->
 
  <div class="h60"></div>
  <style type="text/css">
  	/* marquee5 */
	#marquee5{height:20px;overflow:hidden;}
	#marquee5 ul li{height:20px;padding:0;line-height:20px;overflow:hidden;}
  </style>

    <!--�ײ� start-->
    <div class="footer">
     <div class="text"><i class="icon_logo"></i>΢�ȵ�<br>
     	<div id="marquee5">
		<ul>
			<li>���ע�ᣬ���ͼ��á�</li>
			<li>���˶�������ר��</li>
			<li>ֻ��΢�ȵ㣬һ�׾͹��ˡ�</li>
			<li>���ͼ۸�����ӵ�С�</li>
		</ul>
	</div>
	</div>
      <a class="btn-download" href="http://d.51wyq.cn">���ؿͻ���</a>

    </div>
    <!--�ײ� end-->
 </div>
  
 
 <%--  <div class="zhezhao"></div>
    <div class="content">
        <nav class="rightNav">
            <ul>
                <li class="menu menu1"><a href="${dnsString}">��ҳ</a></li>
                <li class="menu menu4"><a href="${dnsString}/help.shtml">��������</a></li>
            </ul>
        </nav>
    </div> --%>
</body>
</html>
