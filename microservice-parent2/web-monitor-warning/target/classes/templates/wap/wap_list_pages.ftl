<#--<%@ page isELIgnored="false"%>-->
<#--<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>-->
<#--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>-->
<#assign base = request.contextPath />
<!-- 默认版本 -->
<html>
<head lang="en">
 	<meta charset="utf-8">
 	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<title>预警列表</title>
	<link rel="shortcut icon" sizes="16x16" href="${base}/images/addhomescreen/icon-16x16.png">
	<link rel="apple-touch-icon-precomposed" href="${base}/images/addhomescreen/icon-152x152.png">
	<link rel="shortcut icon" sizes="196x196" href="${base}/images/addhomescreen/icon-196x196.png">
	<link rel="stylesheet" type="text/css" href="${base}/css/YJmail.css"/>
	<script type="text/javascript">
		var actionBase = "${base}";
	</script>
</head>
<body data-ng-controller = "initController">
 	<div class="YJmail"  id="container" data-ng-init = "initData()">
    <!--banner代码 start-->
    <section class="section" style="cursor: pointer;">
     	<a href = "http://www.wrd.cn" style="display: block;height: 100%;width: 20%;position: absolute;top: 0px;left:0px;z-index: 4;"></a>
        <div class="mail_Banner rel">
             <div class="picBg"><a onclick="jumpLogin()" style="color:#fff;padding-right: 10px;">修改监测方案，让预警更精准~
                 去修改 ></a></div>
             <div></div>
        </div>
    </section>
    <!--banner代码 end-->

     <form method=post name=frmPopWin action="">
	     <input type="hidden" name="reviewCode" id="reviewCode" value="${reviewCode}"/>
	     <#--<input type="hidden" name="highLightKeywords" id="highLightKeywords" value="${highLightKeywords}"/>-->
	     <input type="hidden" name="titleHs" id="titleHs" value="${titleHs}"/>
	     <input type="hidden" name="paltform" id="paltform" value="${paltform}"/>
	     <#--<input type="hidden" name="contentId" id="contentId" value="${contentId }"/>-->
	     <#--<input type="hidden" name="listFlag" id="listFlag" value="${listFlag}"/>-->
	     <#--<input type="hidden" name="repeatNum" id="repeatNum" value="${repeatNum}"/>-->
	     <#--<input type="hidden" name="defaultRepeatNum" id="defaultRepeatNum" value="${defaultRepeatNum}"/>-->
	     <input type="hidden" id="userId" value="${userId}">
	     <input type="hidden" id="keywordId" value="${wr.keywordId}">
	     <input type="hidden" id="pageAll" value="${page}">
     <#if titleHs!=""&&titleHs!=null>
     	<div class="mail_Parameter">相同文章列表</div>
     <#else>
         <!--预警信息 start-->
         <section class="section line" style="background: #fff;">
             <div class="mail_Parameter">
                 <p>亲爱的：${userName}</p>
                 <p>&nbsp;&nbsp;&nbsp;&nbsp;截止 <span style="font-weight: bold">${wr.createTime?string('MM月dd日 HH:mm')}</span>，您的预警方案<em style="color: #217dc3">【${wr.keywordName}】</em>
                     新增${totalStr}条预警,<#if mgNum==0>未发现敏感信息</#if><#if mgNum!=0>其中<em>${mgNumStr}</em>条敏感信息</#if> 。现<#if similarCondition==1><#if (total > 100)>合并</#if></#if><#if (similarCondition!=1)>不合并</#if>显示如下：</p>
             </div>
         </section>
         <!--预警信息 end-->
     </#if>
     
     <nav class="nav">
     	<ul>
     		<li class="active" data-tendency = "0" ng-click = "changeTendency(0,$event)"><a>全部（${totalStr}）</a></li>
     		<li data-tendency = "3"  ng-click = "changeTendency(3,$event)"><a>敏感（${mgNumStr}）</a></li>
     		<li data-tendency = "4" ng-click = "changeTendency(4,$event)"><a>非敏感（${fmgNumStr}）</a></li>
     	</ul>
     </nav>
     
     <!--列表内容 start-->
     <section class="section" style="border-top: solid 1px rgba(0,0,0,.1);">
     <div class="mwbBorder listMinH">
       <ul class="mwblist" >
          <li data-ng-repeat = "con in result.obj">
                 <a ng-href="{{con.webpageUrl}}"  target="_blank">
                    <!--  <span class="label label_mg" ng-if = "con.customFlag1 == 1 || 
                     con.customFlag1 == 2 || 
                     con.customFlag1 == 3">敏感</span> -->

                     <div class="mwbHead">
                         <div class="m_l" ng-switch='con.originType'>
                             <img ng-if = "con.profileImageUrl != null && con.profileImageUrl != ''"  ng-src="{{con.profileImageUrl}}">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='wb' ng-src="${base}/images/userlogo/app-more-icon-weibo.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='sp' ng-src="${base}/images/userlogo/app-more-icon-video.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='wx' ng-src="${base}/images/userlogo/app-more-icon-weixin.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='xw' ng-src="${base}/images/userlogo/app-more-icon-news.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='lt' ng-src="${base}/images/userlogo/app-more-icon-bbs.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='bk' ng-src="${base}/images/userlogo/app-more-icon-blog.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='app' ng-src="${base}/images/userlogo/app-more-icon-app.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='zw' ng-src="${base}/images/userlogo/app-more-icon-affairs.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='baokan' ng-src="${base}/images/userlogo/app-more-icon-press.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-when='jw' ng-src="${base}/images/userlogo/app-more-icon-media.jpg">
                             <img ng-if = "con.profileImageUrl == null || con.profileImageUrl == ''" ng-switch-default ng-src="${base}/images/userlogo/app-more-icon-web.jpg">
                         </div>
                         <div class="m_r">
                             <h1 ng-bind ="con.author | trustAsHtml" ng-if = "con.captureWebsiteName=='新浪微博' && con.author!=null && con.author!=''"></h1>
                             <h1 ng-bind ="con.captureWebsiteName | trustAsHtml" ng-if = "con.captureWebsiteName=='新浪微博' && (con.author==null || con.author=='')"></h1>
                             <h1 ng-bind-html ="con.title | trustAsHtml" ng-if = "con.captureWebsiteName!='新浪微博'"></h1>
                             <div class="mr-buttom">
                                 <span id="pdate_{{$index+1}}" ng-bind = "con.published | date:'MM月dd日 HH:mm'"></span>
                                 <span>相同文章：<label ng-bind = "con.repeatNum==0 ? 1:con.repeatNum"></label></span>
                                 <span>来自：<label ng-bind = "con.captureWebsiteName"></label></span>
                             </div>
                         </div>
                     </div>
                     <div class="mwbCon">
                     	<div class="txt" ng-bind-html = "con.content | trustAsHtml" ng-if = "con.originType=='wb'"></div>
                        <!-- 原微博 -->
                        <div class="txt zftxt" ng-bind-html = "con.forwarderContent | trustAsHtml" ng-if = "con.originType=='wb'&&con.forwarderContent!=''&&con.forwarderContent!=null" ></div>
                        <div class="txt" ng-bind-html = "con.summary | trustAsHtml" ng-if = "con.originType!='wb'"></div>
                     </div>
                 </a>
	         </li>
       </ul>
     </div>
     <div id = "result_null" style="padding:20% 20%;width: 60%;display: none;"><img alt="" src="${base}/images/default.png" width="100%"></div>
     <div class="spinner1" style="margin:50% auto 50%;">
		    <div class="spinner-container container1">
		        <div class="circle1"></div>
		        <div class="circle2"></div>
		        <div class="circle3"></div>
		        <div class="circle4"></div>
		    </div>
		    <div class="spinner-container container2">
		        <div class="circle1"></div>
		        <div class="circle2"></div>
		        <div class="circle3"></div>
		        <div class="circle4"></div>
		    </div>
		    <div class="spinner-container container3">
		        <div class="circle1"></div>
		        <div class="circle2"></div>
		        <div class="circle3"></div>
		        <div class="circle4"></div>
		    </div>
	 </div>
     </section>
    <!--列表内容 end-->
     <section>
         <div class="footer">
             <#include "../common/paginated_list_bottom.ftl"/>
             <#--<jsp:include page="../common/paginated_list_bottom.ftl"></jsp:include>-->
         </div>
     </section>
     <section>
         <div class="footer">
             <a href="javascript:recordDown()">
                 <img class="visible-xs" src="${base}/images/bottomPic2.jpg"/>
                 <img class="visible-lg" src="${base}/images/bottomPic2.jpg"/>
             </a>
         </div>
     </section>
    </form>
    <!--底部 end-->
 </div>
 
<script type="text/javascript" src="${base}/js/jquery-1.10.2.min.js"></script>
// <%-- <script type="text/javascript" src="${base}/js/zepto.js"></script> --%>
<script type="text/javascript" src="${base}/js/jquery.paginate.js"></script>
<script type="text/javascript" src="${base}/js/angular.min.js"></script>
<script type="text/javascript" src="${base}/js/warningAngular.js"></script>
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
                appHref = "http://d.51wyq.cn";
            }
            if (/micromessenger/i.test(navigator.userAgent.toLowerCase())) {
                shouyeHref = "http://h5.51wyq.cn";
            }
            
        });

    	function goView(cid,num){
    		var platform ;
    		if('${paltform}'!=null&&'${paltform}'!=''&&'${paltform}'!=undefined){
    			platform = '${platform!""}';
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
    		var url = "conView.action?con.id="+cid+"&paltform="+platform+"&reviewCode=${reviewCode!''}&repeatNum="+num+"&defaultRepeatNum=${defaultRepeatNum!''}&highLightKeywords="+newStr;
    		window.open(url,"_self"); 
    	}
    	
    	
    	//翻页
    	function GotoPage(i){	
    		if (document.frmPopWin.page){
    			document.frmPopWin.page.value = i;
    			subForm();
    		}else{
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
               url : actionBase+"/warningCenter/recordSign.action?userId="+$("#userId").val(),
               type : "get",
               success : function(result){
               },
               complete:function(){
                   location.href=shouyeHref;
               }
           });
        }

        function recordDown(){
	        $.ajax({
	            url : actionBase+"/warningCenter/recordDownApp.action?userId="+$("#userId").val(),
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

            $.getJSON("http://h5.51wyq.cn/api/doAddAccessRecords.action?callback=?",
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
</body>
</html>
