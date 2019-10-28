<%@ page language="java" contentType="text/html; charset=gbk"
	pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- 默认版本 -->
<html>
<head lang="en">
 <meta charset="gbk">
 <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<title>预警列表</title>

<link href="css/YJmail.css" rel="stylesheet" type="text/css" />
</head>
 <script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
 <script type="text/javascript" src="js/zepto.js"></script>
	
  <script type="text/javascript">
  var isWeiboClient = /weibo/i.test(navigator.userAgent);
        var shouyeHref = "http://www.wrd.cn";
        var appHref = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
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
			})
			
			var paramHtml =  $(".mail_Parameter").html();
            if(paramHtml!='相同文章列表'){
            	$("#titleHs").val('');
            }


            if (/weibo/i.test(navigator.userAgent.toLowerCase())) {
                shouyeHref = "http://apps.weibo.com/3960037780/8rXM111J";
                appHref = "d.51wyq.cn";
            }
            if (/micromessenger/i.test(navigator.userAgent.toLowerCase())) {
                shouyeHref = "http://h5.wyq.cn";
            }
			
        });
        
        
        
        
        

    	function goView(cid,num){
    		var platform ;
    		if('${paltform}'!=null&&'${paltform}'!=''&&'${paltform}'!=undefined){
    			platform = '${platform}';
    		}
    		var highLightKeywords = $("#highLightKeywords").val();
    		var arr = new Array();
    		var newStr ="";
    		if(highLightKeywords.indexOf(",")>-1){
    			highLightKeywords = highLightKeywords.substring(0,highLightKeywords.length-1);
    			arr = highLightKeywords.split(",");
    			if(arr.length>0){
    				if(arr.length>1){
    					for(var i=0;i<=arr.length-2;i++){
       						newStr +=arr[i]+"###";
    					}
    				}
    				newStr += arr[arr.length-1];
    			}
    		}
    		newStr = encodeURIComponent(encodeURIComponent(newStr));
    		var url = "conView.action?con.id="+cid+"&paltform="+platform+"&reviewCode=${reviewCode}&repeatNum="+num+"&defaultRepeatNum=${defaultRepeatNum}&highLightKeywords="+newStr;
    		window.open(url,"_self"); 
    	}
    	
    	
    	//翻页
    	function GotoPage(i)
    	{	
    		if (document.frmPopWin.page)
    		{
    			document.frmPopWin.page.value = i;
    			subForm();
    		}
    		else{
    			subForm(); 
    		}
    	} 
    	
    	function goZZNewsList(titleHs){
    		$("#titleHs").val(titleHs);
    		subForm();
    	}

    	
    	function subForm(){
    		frmPopWin.action="more.action";
    		frmPopWin.submit();
    	}

          function recordSign(){
              $.ajax({
                  url : "recordSign.action?userId="+$("#userId").val(),
                  type : "get",
                  success : function(result){
                  },
                  complete:function(){
                      location.href=shouyeHref;
                  }
              })

          }

          function recordDown(){
              $.ajax({
                  url : "recordDownApp.action?userId="+$("#userId").val(),
                  type : "get",
                  success : function(result){
                  },
                  complete:function(){
                      location.href=appHref;
                  }
              })
          }

          function jumpLogin(){
              var data = {"userId":$("#userId").val(),"keywordId":$("#keywordId").val()};

              $.getJSON("http://h5.wyq.cn/api/doAddAccessRecords.action?callback=?",
                      data,
                      function(data){
                          if(data.status){
                              console.log(data.status);
                              location.href= shouyeHref;
                          }else{
                              console.log(data.status);
                              console.log(data.msg);
                          }
                      });

          }

    </script>
<body>

 <div class="YJmail"  id="container">

     <!--头部代码 start-->
     <div class="mail_heard">
         <img src="images/logo.png"  class="logo"/>
         <div class="topRight" >
             <a class="btn" href="javascript:recordSign()" >首页</a>
         </div>
     </div>
     <!--头部代码 end-->
     <!--banner代码 start-->
     <section class="section" onclick="jumpLogin()" style="cursor: pointer;">
         <div class="mail_Banner rel">
             <div class="picBg"></div>
             <div>调整预警条件，让监测结果更满意~
                 去修改 ></div>
         </div>
     </section>
     <!--banner代码 end-->

     <FORM method=post name=frmPopWin action="">
     <input type="hidden" name="reviewCode" id="reviewCode" value="${reviewCode}"/>
     <input type="hidden" name="highLightKeywords" id="highLightKeywords" value="${highLightKeywords}"/>
     <input type="hidden" name="titleHs" id="titleHs" value="${titleHs}"/>
     <input type="hidden" name="paltform" id="paltform" value="${paltform }"/>
     <input type="hidden" name="contentId" id="contentId" value="${contentId }"/>
     <input type="hidden" name="listFlag" id="listFlag" value="${listFlag}"/>
     <input type="hidden" name="repeatNum" id="repeatNum" value="${repeatNum}"/>
     <input type="hidden" name="defaultRepeatNum" id="defaultRepeatNum" value="${defaultRepeatNum}"/>
     <input type="hidden" id="userId" value="${userId}">
     <input type="hidden" id="keywordId" value="${wr.keywordId}">
     <s:if test="#attr.titleHs!=null&&#attr.titleHs!=''">
     	<div class="mail_Parameter">相同文章列表</div>
     </s:if>
     <s:else>
         <!--预警信息 start-->
         <section class="section line">
             <div class="mail_Parameter">
                 <p>亲爱的：${userName}</p>
                 <p>&nbsp;&nbsp;&nbsp;&nbsp;截止 <span style="font-weight: bold"><s:date name="#attr.wr.createTime" format="MM月dd日 HH:mm"/> </span>，您的预警方案<em style="color: #72ab00">［${wr.keywordName}］</em>
                     新增${total}条预警,<s:if test="#attr.mgNum==0">未发现敏感信息</s:if><s:else>其中<em>${mgNum}</em>条敏感信息</s:else> 。现
                     <s:if test="#attr.similarCondition==1">合并</s:if><s:else>不合并</s:else>显示如下：</p>
             </div>
         </section>
         <!--预警信息 end-->
     </s:else>
     
     
     <!--列表内容 start-->
     <section class="section">
     <div class="mwbBorder listMinH">
       <ul class="mwblist">
       	 <s:set name="nowTime" value="new java.util.Date()"></s:set>
       	 <s:iterator value="list" id="con" status="st">
	         <li>

                 <a href="${con.webpageUrl}"  target="_blank">
                     <s:if test="#con.customFlag1 == 1||#con.customFlag1 == 2 || #con.customFlag1 == 3">
                         <span class="label label_mg">敏感</span>
                     </s:if>

                     <div class="mwbHead">
                         <div class="m_l">
                             <s:if test="#con.profileImageUrl!=null&&#con.profileImageUrl!=''">
                                 <img src="${con.profileImageUrl}">
                             </s:if>
                             <s:elseif test="#con.originType=='wb'">
                                 <img src="images/userlogo/app-more-icon-weibo.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='sp'">
                                 <img src="images/userlogo/app-more-icon-video.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='wx'">
                                 <img src="images/userlogo/app-more-icon-weixin.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='xw'">
                                 <img src="images/userlogo/app-more-icon-news.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='lt'">
                                 <img src="images/userlogo/app-more-icon-bbs.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='bk'">
                                 <img src="images/userlogo/app-more-icon-blog.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='app'">
                                 <img src="images/userlogo/app-more-icon-app.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='zw'">
                                 <img src="images/userlogo/app-more-icon-affairs.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='baokan'">
                                 <img src="images/userlogo/app-more-icon-press.jpg">
                             </s:elseif>
                             <s:elseif test="#con.originType=='jw'">
                                 <img src="images/userlogo/app-more-icon-media.jpg">
                             </s:elseif>
                             <s:else>
                                 <img src="images/userlogo/app-more-icon-web.jpg">
                             </s:else>
                         </div>
                         <div class="m_r">
                             <h1>
                                 <s:if test='#con.captureWebsiteName=="新浪微博"'>
                                     <s:if test='#con.author!=null&&#con.author!=""'>
                                         ${con.author}
                                     </s:if>
                                     <s:else>
                                         ${con.captureWebsiteName }
                                     </s:else>
                                 </s:if>
                                 <s:else>
                                     ${con.title}
                                 </s:else>
                             </h1>
                             <div class="mr-buttom">
                                 <span id="pdate_<s:property value='#st.index+1'/>">

                                      <script>
                                          <s:set name="pdate"	value="#con.published" />
                                          if ('<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>' =='<s:date name="#pdate" format="yyyy-MM-dd" />'){
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend("今天 ");
                                          }else if ('<%=new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %>' =='<s:date name="#pdate" format="yyyy" />'){
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="M月d日" /> ');
                                          }else {
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="HH:mm" />');
                                              $("#pdate_<s:property value='#st.index+1'/>").prepend('<s:date name="#pdate" format="yyyy年M月d日" /> ');
                                          }

                                      </script>
                                 </span>
                                 <span>相同文章：<s:if test="#con.repeatNum==0">1</s:if><s:else><s:property value="#con.repeatNum"/></s:else></span>
                                 <span>来自：${con.captureWebsiteName }</span>
                             </div>
                         </div>
                     </div>
                     <div class="mwbCon">
                         <s:if test="#con.originType=='wb'">
                            <div class="txt">
                                 <!-- 转发或者原微博 -->
                                 <s:property value="#con.content" escape="false"/>
                            </div>
                             <!-- 原微博 -->
                             <s:if test="#con.forwarderContent!=''&&#con.forwarderContent!=null">
                                 <div class="txt zftxt"><s:property value="#con.forwarderContent" escape="false"/></div>
                             </s:if>

                         </s:if>
                         <s:else>
                             <div class="txt">
                                 <!-- 转发或者原微博 -->
                                 <s:property value="#con.summary" escape="false"/>
                             </div>
                         </s:else>
                     </div>
                 </a>
		         
	         </li>
         </s:iterator>
       </ul>
     </div>
     </section>
    <!--列表内容 end-->
     <section>
         <div class="footer">
             <s:if test="list!=null">
                 <s:include value="../common/paginated_list_bottom.jsp">
                 </s:include>
             </s:if>
         </div>
     </section>
     <section>
         <div class="footer">
             <a href="javascript:recordDown()">
                 <img class="visible-xs" src="images/bottomPic.jpg"/>
                 <img class="visible-lg" src="images/bottomPic2.jpg"/>
             </a>
         </div>
     </section>
	<%--<!--返回列表 start-->
		<s:if test="#attr.titleHs!=null">
    	<a href="javascript:history.go(-1);" title="返回列表"><img src="images/back-64.png"  class="back"></a>
    	</s:if>
    <!--返回列表 end-->
    <!--底部 start-->
    <div class="footer">
    <s:if test="list!=null">

		<s:include value="../paginated_list_bottom.jsp">
			 
		</s:include>
	</s:if>
		
    </div>--%>

    </FORM>
    <!--底部 end-->
 </div>

 <%--cnzz统计代码start  --%>
 <div style="display:none;">
     <%--<script type="text/javascript" src="js/cnzz.js"></script>--%>
 </div>
 <%--cnzz统计代码end  --%>
</body>
</html>
