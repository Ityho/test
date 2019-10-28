<%@ page  pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <%
        String njxBasePath = request.getContextPath();
        String staticResourcePath = njxBasePath;
    %>
    <!-- saved from url=(0014)index:internet -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="UTF-8">
    <title>${reportName}-微热点(微舆情)-社会化大数据应用平台</title>
    <META name="keywords" content="微热点(微舆情)_网络舆情监测">
    <META name="description" content="微热点(微舆情)_网络舆情监测">
    <link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/font-icon.css"/>
    <link href="<%=njxBasePath%>/css/style.css" rel="stylesheet" type="text/css">
    <link href="<%=njxBasePath%>/css/common.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/dayReport.css"/>
        <link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/iconfont.css"/>
    <link href="<%=njxBasePath%>/css/tips.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="<%=njxBasePath%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/index.js"></script>
    <script src="<%=njxBasePath%>/js/jquery.JPlaceholder.js"></script>

    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/echarts.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/line.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/bar.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/map.js" charset="UTF-8"></script>
    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#uuid=ad6ca8fe-7189-423a-b7b6-ed44605dd77a&style=-1"></script>
</head>
<style>
/* .map{width:100%;margin-top:5px;height:350px;}
.mapArea{margin-top:25px;width:50%;float:left;height: 300px;}
.maptit{background-color:#949494 !important;border-bottom:  solid 1px #DBDBDB;color:#FFF;}
.mapDiv{margin-top:25px;float:right;width:35%;}
.mapTable{margin: 10px 0; display: inline-block;width: 100%;}
.mapTable > li{width: 30%; display: inline-block; margin: auto; margin-right: 4%; }
.mapTable > li:last-child{border: none; margin-right: 0;}
.mapTable > li tr{ background-color: #fff;}

.mapTable > li td{line-height: 22px; padding:0; text-align: center ;}
.mapTable > li td{border-bottom:  solid 1px #DBDBDB;border-right:solid 1px #DBDBDB;border-left:solid 1px #DBDBDB;}
.mapTable > li tr td:first-child{width: 50px;}
.mapTable > li tr td:last-child{width: 100px; }
.mapTable > li> .tit{ height: 30px; line-height: 30px; font-size: 12px; background-color: #F1AE74; text-align: center; color: #fff;}
.mapTable2 > li{width: 95%; display: inline-block; margin: auto; margin-right: 4%; }

@media only screen and (max-width:550px ){
	.mapArea{margin-top:25px;width:100%;float:left;height: 300px;}
	.mapDiv{margin-top:25px;float:left;margin-left:30px;width:35%;}
} */
/*    .background3{
	background-image: url("../reportView/images/bgV3.jpg");
	background-repeat: no-repeat;
	background-position: center center;
	background-size:100% 100%;
	height:130px;
}
.logo2{
	background-image: url("../reportView/images/logo2.png");
	background-repeat: no-repeat;
	background-position: center center; 
	background-size:50% 50%;
	height:120px;
}
.logo3{
	background-image: url("../reportView/images/logo2.png");
	background-repeat: no-repeat;
	background-position: top left; 
	background-size:30% 25%;
	height:120px;
}
.logoText{
	width:80%;position:absolute;top:45%;font-size:20px;color:#fff;margin-left:10%;text-align:center;
}
@media only screen and (min-width:750px ){
	.background3{
		background-image: url("../reportView/images/bgV3.jpg");
		background-repeat: no-repeat;
		background-position: center center;
		background-size:100% 100%;
		height:200px;
	}
	.logo2{
		height:180px;
	} 
	.logo3{
		background-size:20% 25%;
		height:180px;
	}
	.logoText{
		font-size:32px !important;color:#fff;
	}
}
.sourceTitle{
	margin:5px 15px 0px 16px;height:60px;float:left;width:30px;text-align:center;font-size:14px;color:#949494;
}
.t1{
	width:45px;
}
.i1{
	width:100%;color:#949494;font-size:16px;
}   
@media only screen and (max-width:550px){
	.sourceTitle{
	margin:5px 5px;
		width:14%;
	}
	.t1{
		width:14%;
	}
	.sourceTitle span{
		display:block;
	}
}
@media only screen and (max-width:350px){
	.sourceTitle{
	margin:5px 5px;
		width:13%;
	}
	.t1{
		width:14%;
	}
	.sourceTitle span{
		display:block;
	}
} */
/* .bsFrameDiv {
	background: #000 !important;	
	margin-top: -5px;
	margin-left: -6px;
	margin:0px !important;
} */

</style>
<body class="mobileStyle dayReport">

<input type="hidden" id="userId" value="${keywordReportRecord.userId}">
<input type="hidden" id="keywordId" value="${keywordReportRecord.keywordId}">
<input type="hidden" id="code" value="${keywordReportRecord.shareCode}">
<input type="hidden" value="${filterNum}">
<input type="hidden" id="wbTop" value="${wbTop}">
<input type="hidden" id="fwbTop"value="${fwbTop}">
<!--head代码 start-->
<div id="head" class="rel h_line">
    <div class="head-top">
        <span class="logo logoJC float_l" onclick="javascript:location.href='http://www.wrd.cn'"></span>
        <span class="r_btn abs" style="top: 14px;">
			<a class="loginBtn loginBtnReport" href="javascript:void(0);"title="微信" onclick="sharePage(event,'weixin');return false;"><img src="<%=njxBasePath %>/images/mobile.png" style="margin-right:5px;width:12px">在手机上查看</a>
        </span>
    </div>
</div>
<!--head代码 end-->
<script>
function sharePage(event,type){
	$("#cancleBtn").show();
	var url = location.href;
    var title = $(document).attr("title");
    bShare.addEntry({
        title:title,
        url: url
    });
  //清除自定义分享内容的方法，在设置前清空，重新自定义内容
    bShare.entries = [];
    bShare.init();
    bShare.share(event,type,0);
   
}
$(function(){
    var bs = {
        versions: function () {
            var u = navigator.userAgent, app = navigator.appVersion;
            return {
                windowsPhone: u.indexOf('IEMobile') > -1,
                trident: u.indexOf('Trident') > -1,
                presto: u.indexOf('Presto') > -1,
                webKit: u.indexOf('AppleWebKit') > -1,
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
                mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/) || !!u.match(/IEMobile/),
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
                android: u.indexOf('Android') > -1 || u.indexOf('UCBrowser') > -1,
                iPhone: u.indexOf('iPhone') > -1,
                iPad: u.indexOf('iPad') > -1,
                webApp: u.indexOf('Safari') === -1
            };
        } (),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    };
    if (bs.versions.mobile) {
        if (bs.versions.android || bs.versions.iPhone || bs.versions.iPad || bs.versions.windowsPhone) {
           $("#head").hide();
           $(".page-container2").css("margin-top","0px");

           $("#reportTime").hide();
        }
    }
   
});
</script>
<div class="page-container2">
    <!--日报 start-->
    <div class="content mt15">
        <!--报告头部 start-->
        <div class="row-fluid">
            <div class="con Report-header background3 ">
             
            	<div class="logo3"></div>
            	<div class="logoText">${reportName}&nbsp;监测日报</div>
            </div>
			 <!-- <i class="iconfont icon-bangdan2" style="color:#666"></i> -->
                <div class="info">
                    <div class="float_l align_l" style="margin:5px;color:#000;font-weight:bold;">
                    	<div style="width:10%;float:left;height:30px;width:30px;border-radius:50%; background: url(${userHead }) no-repeat center; background-size: cover;"></div>
                    	<div style="margin:5px;font-size:12px;float:left;">${nickname}</div>
                        	  <div style="margin:5px; float:left;weight:1px;">|</div>
                        	<div style="margin:5px;font-size:12px;float:left;"><span id="reporTime">监测时间段&nbsp;：&nbsp;</span>${reportTime}&nbsp;00:00~23:59</div>
                    </div>
                    <div class="clear"></div>
                </div>
        </div>
		<div class="empty" style="display:${showFlag}">
		
		</div>	
        <!--报告头部 end-->
		<div class="row-fluid" style="background-color:#f3f3f3;margin-top:-10px;"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con" style="text-align:center;">
                    <c:if test="${not empty sources}">
                    	<div class="sourceTitle">
                    		<i class="iconfont icon-total i1"></i>
                 			<span style="width:100%;">全部</span>
                 			<span>${totalNum}</span>
                 		</div>
                       	<c:forEach var="source" items="${sources}">
                    		<c:if test="${source.name == '网站'}">
                    			<div class="sourceTitle">
                    				<i class="iconfont icon-website i1"></i>
                    				<span style="width:100%;">${source.name}</span>
                    				<span>${source.num}</span>
                    			</div>
                    		</c:if>
                            <c:if test="${source.name == '微博'}">
                      			<div class="sourceTitle">
                      				<i class="iconfont icon-weibo2 i1"></i>
                      				<span style="width:100%;">${source.name}</span>
                      				<span>${source.num}</span>
                      			</div>
                       		</c:if>
                      		<c:if test="${source.name == '新闻'}">
                      			<div class="sourceTitle">
                   					<i class="iconfont icon-news2 i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                      			</div>
                      		</c:if>
                   			<c:if test="${source.name == '论坛'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-bbs2 i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			
                   			<c:if test="${source.name == '客户端'}">
                   				<div class="sourceTitle t1">
                   					<i class="iconfont icon-app i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			
                   			<c:if test="${source.name == '微信'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-weixin2 i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			
                   			<c:if test="${source.name == '报刊'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-baokan i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			<c:if test="${source.name == '政务'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-zhengwu i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			<c:if test="${source.name == '博客'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-boke i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			<c:if test="${source.name == '视频'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-video i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                   			<c:if test="${source.name == '外媒'}">
                   				<div class="sourceTitle">
                   					<i class="iconfont icon-waimei i1"></i>
                   					<span style="width:100%;">${source.name}</span>
                   					<span>${source.num}</span>
                   				</div>
                   			</c:if>
                      	 </c:forEach>
                    </c:if>
                   </div>
                <div class="clear"></div>
            </div>
            
		<div class="row-fluid" style="margin-top:5px;"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con">
                <div>
                	<div style="float:left;margin-left:10px;">
                   		<span style="color:#6B6B6B">近三天整体信息走势图</span>
               	  	</div>
              	</div>
                <div class="mwbBorder chart" style="width: 100%;height: 300px;" id="hotLine2">

                </div>
                <div class="clear"></div>
            </div>
        </div>
        
        <div class="row-fluid" style="margin-top:5px;height:350px;"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con">
                <div>
                	<div style="float:left;margin-left:10px;">
                   		<span style="color:#6B6B6B">媒体活跃度</span>
               	  	</div>
              	</div>
                <div class="mwbBorder chart" style="width: 100%;height: 300px;" id="sourceBar">

                </div>
                <div class="clear"></div>
            </div>
        </div>

		<div class="row-fluid" style="margin-top:5px;"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con">
                <div>
                  <div style="float:left;margin-left:10px;">
                   <span style="color:#6B6B6B">敏感、非敏感近三天走势</span>
               </div>
              <div style="float:right;margin-right:25px;color:#6B6B6B;font-size:12px;">
                 <span style="color:#3454a1">非敏感${fmNum}条</span>
                 <span style="color:#c1222a">敏感${mgNum}条</span>
               </div>
               </div>
                <div class="mwbBorder chart" style="width: 100%;height: 300px;" id="hotLine">

                </div>
                <div class="clear"></div>
            </div>
        </div>
        
        <div class="row-fluid" class="map"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con">
                <div>
                	<div style="float:left;margin-left:10px;">
                   		<span style="color:#6B6B6B">地域分布图</span>
               	  	</div>
              	</div>
                <div class="mwbBorder chart mapArea"  id="areaMap">

                </div>
                <div class="mapDiv float_r" id="mapTop10" ></div>
                <div class="clear"></div>
            </div>
        </div>
        <!--非微主要观点Top20 start-->
        <div class="row-fluid" style="<c:if test="${empty fwbList}">display: none</c:if>">
            <div class="con">
                <div class="title">
                    <div class="titleBlue" style="width:15%;">重点内容</div>
                </div>
                <div class="main_list">
                    <c:if test="${empty fwbList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>此时间段暂无信息</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty fwbList}">
                        <ul>
                            <c:forEach var="mg" items="${fwbList}" begin="0" end="9" varStatus="status">
                            	<li style="padding:30px 0px 0px 10px;min-height:initial">
                            		<div style="float:left;color:#333333;font-size:14px;">重点内容${status.index+1}</div>
                            	</li>                     
                            	<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                                    <li style="padding:0 10px;min-height:initial">
                                     	<div class="m_l" style="margin-left:-55px;margin-top:20px;width:44px;">
                                            <c:if test="${icn.customFlag1 != 4}">
                                            	<span style="width:100%;float:left;font-size:14px;color:#ff2121;">敏感</span>
                                            </c:if>
                                        </div>     
                                        <div class="m_r" style="margin-left:70px;">
                                            <p class="quickLink">
                                                <a target="_blank" href="${icn.webpageUrl}" style="margin-left:-5px;font-size:14px;color:#333333;">
                                                	【${icn.title}】${icn.summary}
                                                </a>
                                            </p>

                                            <c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
                                                <div class="zfCon">${icn.forwarderContent}</div>
                                            </c:if>

                                            <div class="infor" style="margin-top:0px;">
                                                <div class="float_l">
                                                    <div class="m_l">
                                                	 <c:if test="${not empty icn.profileImageUrl}">
		                                                <img src='${icn.profileImageUrl}'/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
		                                                <c:if test='${icn.captureWebsiteName=="腾讯微博"}'>
		                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg"/>
		                                                </c:if>
		                                                <c:if test='${icn.captureWebsiteName=="新浪微博"}'>
		                                                    <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg"/>
		                                                </c:if>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg"/>
		                                            </c:if>
		                                            <c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
		                                                <img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
		                                            </c:if>
		                                            </div>
                                        			<c:forEach items="${mg.iContentCommonNetList}" varStatus="status" var="icn" begin="0" end="1">
                                        				<c:choose>
                                        					 <c:when test="${!empty icn.captureWebsiteName}">
                                        					 
                                         			 			${icn.captureWebsiteName}
                                        		 			</c:when>
                                        					 <c:otherwise>
                                        					 	网站媒体
                                        					 </c:otherwise>
                                        				</c:choose>	
                                        			</c:forEach>
                                                    <span>&nbsp;等${mg.num}条相似文章</span>
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </c:if>
                    <c:if test="${not empty wbList}">
                        <ul>
                            <c:forEach var="mg" items="${wbList}" begin="0" end="9" varStatus="status">
                            	<li style="padding:30px 0px 0px 10px;min-height:initial">
                            		<div style="float:left;color:#333333;font-size:14px;">重点内容${status.index+3}</div>
                            	</li>
                                <c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                                    <li style="padding:0 10px;min-height:initial"">
                                       <div class="m_l" style="margin-left:-55px;margin-top:20px;width:44px;">
                                            <c:if test="${icn.customFlag1 != 4}">
                                            	<span style="width:100%;float:left;font-size:14px;color:#ff2121;">敏感</span>
                                            </c:if>
                                        </div>
                                        
                                        <div class="m_r" style="margin-left:70px;">
                                            <p class="quickLink">
                                                <a target="_blank" href="${icn.webpageUrl}" style="font-size:14px;color:#333333;">
                                                    ${icn.summary}
                                                </a>
                                            </p>

                                            <c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
                                                <div class="zfCon">${icn.forwarderContent}</div>
                                            </c:if>

                                            <div class="infor" style="margin-top:0px;">
                                                <div class="float_l">
                                                 <div class="m_l" style="width:100% !important ;text-align:left;">
                                                 	<c:forEach items="${mg.iContentCommonNetList}" varStatus="status" var="icn" begin="0" end="0">
                                        		       ${icn.author}
                                        			</c:forEach>
                                        			  <span>&nbsp;等${mg.num}条近似微博</span>
		                                            </div>
                                        		
                                                  
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </c:if>

                </div>
            </div>
        </div>
        <!--微博主要观点 end-->
        <c:if test="${empty loginType}">
            <!--分享组件部分 start-->
            <div class="row-fluid" style="text-align: center;margin:10px 0 0;">
                <a class="visible-xs" href="http://d.51wyq.cn"><img width="100%" src="<%=njxBasePath%>/images/bottomPic.jpg" /></a>
                <a class="visible-lg" href="http://d.51wyq.cn"><img width="100%" src="<%=njxBasePath%>/images/bottomPic2.jpg" /></a>
            </div>
            <!--分享组件部分 end-->
        </c:if>




    </div>
</div>
<!--日报 end-->
</div>

<div id = "cancleBtn" class="cancleBtn"></div>

<!--底部部分代码 start-->
<div class="h35 clear"></div>

<div id="footer">
    <p><a href="http://www.wrd.cn/aboutUs.shtml">关于我们</a>  |  <a href="http://www.wrd.cn/contectUs.shtml">联系我们</a>  |  <a href="http://www.wrd.cn/help.shtml">帮助中心</a></p>
    <p>&copy; Copyright wrd.cn 2002-2017 All Rights Reserved 沪ICP备05035762号-9｜增值电信业务经营许可证B2-20090366</p></div>
<!--底部部分代码 end-->

<script>
    $(function() {
        $(".julei .main_list").each(function(i){
            $("#cluster"+(i)+">ul>li:gt(0)").hide();
        });
        $(".bottomMore").on("click",function(){
            if(!$(this).find("img").hasClass("rotate180")){
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").show(300);
                $(this).html("<img src='images/expand3-32.png' class='rotate180'/>");
            }else{
                $(this).parents(".julei .main_list").find(">ul>li:gt(0)").hide(300);
                $(this).html("<img src='images/expand3-32.png' class='rotate0'/>");
            }
        });
        $("#cancleBtn").on("click",function(){
        	$("#bsWXBox").hide();
        	 $("#cancleBtn").hide();
        });
    });

    LineChart('${lineChat}',"hotLine");
    LineChart2('${lineChat2}',"hotLine2");
    BarChart('${barChat}',"sourceBar");
    MapCallBack('${mapChat}');
    function LineChart(data,dom){
        if (data==null||data==""){
            //document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data1 = eval(data)[1][0];
        data = eval(data)[0][0];
        console.log(data);
        console.log(data1);
        var splitNum = 0;
        if(data.datetime.length>12){
            splitNum = 2;
        }
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart1 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter:function(params){
                                var v = new Date(parseInt(params[0].name)).format("MM-dd");

                                for (var i = 0, l = params.length; i < l; i++) {
                                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                                }

                                return v;
                            }
                        },
                        legend: {
                            data:["全部"],
                            show:false
                        },
                        grid:{
                            x:40,
                            x2:30,
                            y:10
                        },
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            data : data.datetime,

                             splitLine:{
                             show:false
                             },
                            splitNumber:splitNum,
                            axisLabel : {
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    v = new Date(parseInt(v)).format("MM-dd hh:00");
                                    return v;
                                }
                            },
                        }
                        ],
                        yAxis : [{
                            type : 'value',
                            axisLabel:{
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    if(v>=1000){
                                        return (v/1000)+"k";
                                    }else{
                                        return v;
                                    }
                                }
                            },
                        }],


                        calculable : false,
                        series : [
                                  {
		                            name:"非敏感",
		                            type:"line",
		                            smooth:true,
		                            itemStyle : {  
		                                normal : {  
		                                	color:'#3454a1',
		                                    lineStyle:{  
		                                        color:'#3454a1'  
		                                    }  
		                                }  
		                            },  
		                            data:data.data
		                            
		                          },
		                          {
		                            name:"敏感",
		                            type:"line",
		                            smooth:true,
		                            itemStyle : {  
		                                normal : { 
		                                	color:'#c1222a',
		                                    lineStyle:{  
		                                        color:'#c1222a'  
		                                    }  
		                                }  
		                            },  
		                            data:data1.data
		                            
		                          }
                        		]
                    		}
                    chart1.setOption(option);
                    chart1.setTheme('infographic');
                    var enConfig = require('echarts/config');

                }
        );
    }
    function LineChart2(data,dom){
        if (data==null||data==""){
            //document.getElementById(dom).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=njxBasePath %>/images/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            $("#"+dom).parents(".row-fluid").hide();
            return false;
        }
        data = eval(data)[0][0];
        console.log(data);

        var splitNum = 0;
        if(data.datetime.length>12){
            splitNum = 2;
        }
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart1 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter:function(params){
                                var v = new Date(parseInt(params[0].name)).format("MM-dd");

                                for (var i = 0, l = params.length; i < l; i++) {
                                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                                }

                                return v;
                            }
                        },

                        legend: {
                            data:["全部"],
                            show:false
                        },
                        grid:{
                            x:40,
                            x2:30,
                            y:10
                        },
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            data : data.datetime,

                             splitLine:{
                             show:false
                             },
                            splitNumber:splitNum,
                            axisLabel : {
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    v = new Date(parseInt(v)).format("MM-dd hh:00");
                                    return v;
                                }
                            },
                        }
                        ],
                        yAxis : [{
                            type : 'value',
                            axisLabel:{
                                textStyle : {
                                    decoration: 'none',
                                    fontFamily: 'Microsoft YaHei',
                                    fontSize: 12,
                                    color:"#000"
                                },
                                formatter:function(v){
                                    if(v>=1000){
                                        return (v/1000)+"k";
                                    }else{
                                        return v;
                                    }
                                }
                            },
                        }],


                        calculable : false,
                        series : [
                                  {
		                            name:"全部",
		                            type:"line",
		                            smooth:true,
		                            itemStyle : {  
		                                normal : {  
		                                	color:'#27727B',
		                                    lineStyle:{  
		                                        color:'#27727B'  
		                                    }  
		                                }  
		                            },  
		                            data:data.data
		                            
		                          }

                        		]
                    		}
                    chart1.setOption(option);
                    chart1.setTheme('infographic');
                    var enConfig = require('echarts/config');

                }
        );
    }
    function BarChart(data,dom){
		data =  eval(data)[0][0];
		console.log(data);
		var config = require(
		           [  
		            'echarts',
		            'echarts/chart/bar',
		           ],
		           function (ec) {
		        	   	var chart4 = ec.init(document.getElementById(dom));
		        		 option = {

		        				    tooltip : {
		        				        trigger: 'axis'
		        				    },

		        				    grid:{
			                	        x:40,
			                	        y:10,
			                	        x2:40,
			                	        y2:40
			                	      },
		        				    calculable : false,
		        				    xAxis : [
		        				        {
		        				            type : 'category',
		        				            data : data.datetime,
		        				            axisLine: {
		   		       	                	 lineStyle:{
		   		       	                		    color: ['#eee'],
		   		       	                		    width: 0.1,
		   		       	                		    type: 'solid'
		   		       	                		    }
		   		       	               		 },
		   		       	               		 axisTick:{
		   			       	               		show:true,
		   			       	               		lineStyle:{
		   				                		    color: '#eee',
		   				                		    width: 1,
		   				                		    type: 'solid'
		   				                		    }
		   			       	               		},
		   			       	              axisLabel: {
			   			       	            rotate: 28,
			   			       	        },
		        				        }
		        				    ],
		        				    yAxis : [
		        				        {
		        				        	 axisLabel:{
			                     	             formatter:function(v){
			                     	            	 if(v>=1000){
			                     	            		 return (v/1000)+"k";
			                     	            	 }else{
			                     	            		 return v;
			                     	            	 }
			                     	             }
			        	               	 	},
		        				            type : 'value',
		        				            splitArea : {
	        	                                 show: true,
	        	                                 areaStyle:{
	        	                                     color:['#FFF','#F7F7F7']
	        	                                 }
	        	                             },
        	                             	axisLine: {
        		       	                	 lineStyle:{
        		       	                		    color: ['#eee'],
        		       	                		    width: 0.1,
        		       	                		    type: 'solid'
        		       	                		    }
        		       	               		 },
	        	                             splitLine:{lineStyle:{width:0.1}}
		        				        }
		        				    ],
		        				    series :  [{
		        			            name:'相关信息',
		        			            type:'bar',
		        			            data:data.data
		        			        }]
		        				};
		        		
		        		 chart4.setOption(option);

		                var enConfig = require('echarts/config');
		                
		           }
		       );
		
	}
	function MapCallBack(data){
		var res = eval(data);
		console.log(res[0][0]);
		var mapTop10 = document.getElementById("mapTop10");
		var topData = res[0][0].topData;
		console.log("topData--"+topData);
		var ulHtml = "";
		if(topData==null || topData.length==0|| topData[0]==null){
			mapTop10.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
		}else{
			ulHtml += "<ul class='mapTable mapTable2' >";
			var liHtml = "<li><table border='0' cellspacing='0' cellpadding='0' style='height:250px;background-color:#fff'><tbody>";
			liHtml +="<tr class='maptit'><td style='border-right;solid 1px #fff';>地域</td><td>信息数</td></tr>";
			for(var i=0;i<topData.length;i++){
				var subData = eval(topData[i]);
				var topName = "";
				var topValue= "";
				topName = subData.name;
				topValue = subData.value;	
				var trHtml = "<tr><td>"+topName+"</td><td>"+topValue+"</td></tr>";
				liHtml +=trHtml;
			}
			liHtml += "</tbody></table></li>";
			ulHtml += liHtml;
			ulHtml += "</ul>";
		}
		mapTop10.innerHTML = ulHtml;
		MapChart(res[0][0],"areaMap");
	}
	function MapChart(data,dom){
		var min = 0;
		var max = data.data[0]['value'];
	 	$.each(eval(data).data, function(i, n) {
	 		$.each(n,function(j,m){
	 			delete m.itemStyle;
				if (m.value > max)
					max = m.value;
				if (m.value < min)
					min = m.value;
	 		});
		}); 
		var config = require(
		           [  
		            'echarts',
		            'echarts/chart/map',
		           ],
		           function (ec) {
		        	   	var chart2 = ec.init(document.getElementById(dom));
		        	   	data =  eval(data); 
		        		var option = {
		        				title : {
		        					text : '',
		        					subtext : '',
		        					x : 'center',
		        					y : 'top',
		        				},
		        				animation : true,
		        				tooltip : {
		        					trigger : 'item',
		        					enterable:true,
		        					textStyle : {
		        						fontSize : 12
		        					}
		        				},
		                	    dataRange: {
		                	    	show:false,
							        min: min,
							        max: max,
							        calculable : false,
							        text:['高','低'],
							        color: ['#d44e24','#f29300','#f3d647']
							    },
		        				series :[{
	        			            name:'相关信息',
	        			            type:'map',
	        			            itemStyle:{
	        			                normal:{
	        			
			        			            color:'#f3d647'//地图背景颜色
			        			 
			        			 		}
		        				},
	        			            data:data.data
	        			        }]
		        			};
		                chart2.setOption(option);
		                var enConfig = require('echarts/config');
		           }
		       );
	}
    function recordSign(){
        $.ajax({
            url : "<%=njxBasePath %>/recordSign.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }

    function recordDown(){
        $.ajax({
            url : "<%=njxBasePath %>/recordDown.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }


</script>
</body>
</html>