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
    <link href="<%=njxBasePath%>/css/dayReport.css" rel="stylesheet" type="text/css" />
    <link href="<%=njxBasePath%>/css/tips.css" rel="stylesheet" type="text/css">


    <script type="text/javascript" src="<%=njxBasePath%>/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/index.js"></script>
    <script src="<%=njxBasePath%>/js/jquery.JPlaceholder.js"></script>


    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/echarts.js" charset="UTF-8"></script>
    <script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/line.js" charset="UTF-8"></script>
    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#uuid=ad6ca8fe-7189-423a-b7b6-ed44605dd77a&style=-1"></script>
</head>
<style>
.progress2 {
    height: 2px;
    margin-bottom: 10px;
    overflow: hidden;
    background-color: #f5f5f5;
    border-radius: 8px;
    -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
    box-shadow: inset 0 1px 2px rgba(0, 0, 0, 0.1);
}
.progress2-bar {
  float: left;
  width: 0;
  height: 2px;
  font-size: 12px;
  line-height: 20px;
  color: #fff;
  text-align: center;
  background-color: #428bca;
  -webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,0.15);
  box-shadow: inset 0 -1px 0 rgba(0,0,0,0.15);

}
</style>
<body class="mobileStyle dayReport">

<input type="hidden" id="userId" value="${keywordReportRecord.userId}">
<input type="hidden" id="keywordId" value="${keywordReportRecord.keywordId}">
<input type="hidden" id="code" value="${keywordReportRecord.shareCode}">
<input type="hidden" value="${filterNum}">
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

           
        }
    }
   
});
</script>
<div class="page-container2">
    <!--日报 start-->
    <div class="content mt15">
        <!--报告头部 start-->
        <div class="row-fluid">
            <div class="con Report-header backNew blue">
                <div class="header abs">
                    <div class="pading10">
                        <a href="www.wrd.cn" class="icon-shouye float_l"></a>
                        <%--<a href="#" class="icon-fenxiang float_r"></a>--%>
                    </div>

                </div>

                <h1>“${reportName}”监测日报</h1>
                <div class="info">
                    <div class="float_l align_l">
                        <p>微热点(微舆情)-${nickname}</p>                        
                    </div>
                    <div class="float_r align_r">
                        <p>${reportTime}</p>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>

        </div>
		<div class="empty" style="display:${showFlag}">
		
		</div>	
        <!--报告头部 end-->

        <!--基本信息 start-->
        <!--  
        <div class="row-fluid" <c:if test="${empty fmwords&&empty mgwords}">style="display: none" </c:if>>
            <div class="con">
                <div class="hwBox">
                    <%--<c:if test="${empty fmwords}">
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>此时间段暂无信息</p>
                        </div>
                    </c:if>--%>
                    <c:if test="${not empty fmwords}">
                        <div class="hwfm lineBottom">
                            <div class="fl float_l">
                                <p>非敏信息  ${fmNum} </p>
                                <p>占         比   ${fmPercent}</p>
                                <p>主要高频词有：</p>
                            </div>
                            <div class="fr float_r">
                                <ul>
                                    <c:forEach var="word" items="${fmwords}">
                                        <li>${word.name}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </c:if>

                    <%--<c:if test="${empty mgwords}">
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>此时间段暂无信息</p>
                        </div>
                    </c:if>--%>
                    <c:if test="${not empty mgwords}">
                        <div class="hwmg">
                            <div class="fl float_l">
                                <p>敏感信息  ${mgNum} </p>
                                <p>占         比  ${mgPercent}</p>
                                <p>主要高频词有：</p>
                            </div>
                            <div class="fr float_r">
                                <ul>
                                    <c:forEach var="word" items="${mgwords}">
                                        <li>${word.name}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>

                    </c:if>

                </div>
                <div class="clear"></div>
            </div>
        </div>-->
        <!--基本信息 end-->

		<div class="row-fluid" style="margin-top:-10px;"<c:if test="${empty sources}">style="display: none" </c:if>>
            <div class="con">
            	<div class="title" style="margin:10px;">
                    <h1>日报概况:</h1>
                </div>
                <div class="hwBox" >
                	<div style="word-break:break-all;text-indent:2em;width:100%;line-height:200%;letter-spacing:1px;">
                		${reportTime}00:01-${reportTime}23:59,针对“${reportNameAll}”监测方案进行统计,网络上该事件共新增信息${totalNum}条。
                    <c:if test="${not empty sources}">
                    	其中,
                       	<c:forEach var="source" items="${sources}">
                       		<c:if test="${source.num != 0}">
                       			${source.name}${source.num}条,
                       		</c:if>
                          		
                      	 	</c:forEach>
                      	 	<c:forEach begin="0" end="0" var="source" items="${sources}">
                      	 		<span>信息主要来源于${source.name}。</span>
                      	 	</c:forEach>
                      	 	<span>对本次信息新增信息进行情感分析,非敏感信息${fmNum}条,占比${fmPercent}。敏感信息${mgNum}条，占比${mgPercent}，近三天</span>
                      	 	<span style="color:#3454a1">信息总量</span>和<span style="color:#c1222a">敏感信息量</span>的走势对比如下:
                    	</c:if>
                    </div>
                </div>
                <div class="mwbBorder chart" style="width: 100%;height: 400px;" id="hotLine">

                </div>
                <div class="clear"></div>
            </div>
        </div>

        <!--近7天信息走势对比 start-->
        <!-- 
        <div class="row-fluid">
            <div class="con">
                <div class="title">
                    <h1 class="dot">近三天信息走势对比</h1>
                </div>
                <div class="mwbBorder chart" style="width: 100%;height: 400px;" id="hotLine">

                </div>
            </div>
        </div> -->
        <!--近7天信息走势对比 end-->



        <!--敏感信息text start-->
        <!--  
        <div class="row-fluid" <c:if test="${empty wbList}">style="display: none" </c:if>>
            <div class="con">
                <div class="text dot red"><c:if test="${mgNum == 0}">今日信息总量${totalNum}条，经微热点(微舆情)大数据系统对其进行垃圾信息过滤和相同文章合并后，建议重点关注以下信息：</c:if>
                <c:if test="${mgNum > 0}">今日敏感信息${mgNum}条，占今日全部信息的${mgPercent}，经微热点(微舆情)大数据系统对其进行多个维度的综合分析，建议重点关注以下信息：</c:if>
                </div>
            </div>
        </div>-->
        <!--敏感信息text end-->

		<!--敏感信息Top20 start-->
        <div class="row-fluid" <c:if test="${empty mgList}">style="display: none" </c:if>>
         <div class="hwBox" >
                	<div style="word-break:break-all;text-indent:2em;width:100%;line-height:200%;letter-spacing:1px;">
                		${reportTime}00:01-${reportTime}23:59,针对“${reportNameAll}”监测方案进行统计,网络上该事件共新增信息${totalNum}条,建议重点关注以下信息:
                    
                    </div>
                </div>
            <div class="con">
                <div class="main_list">
                    <c:if test="${empty mgList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>此时间段暂无信息</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty mgList}">
                        <ul>
                            <c:forEach var="mg" items="${mgList}" >
                                <c:forEach items="${mg.iContentCommonNetList}" var="icn">
                                    <li>
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
                                        <div class="m_r">
                                            <p class="quickLink">
                                                <a href="${icn.webpageUrl}" >
                                                    <c:if test="${icn.originType=='wb'||icn.originType=='txwb'}">
                                                        <c:if test="${empty icn.author}">
                                                            ${icn.captureWebsiteName}
                                                        </c:if>
                                                        <c:if test="${not empty icn.author}">
                                                            ${icn.author}
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${icn.originType!='wb'&&icn.originType!='txwb'}">
                                                        ${icn.title}
                                                    </c:if>
                                                </a>
                                            </p>
                                            <p class="conText">
                                                <c:if test="${icn.originType=='wb'||icn.originType=='txwb'}">
                                                    ${icn.summary}
                                                </c:if>
                                                <c:if test="${icn.originType!='wb'&&icn.originType!='txwb'&&not empty icn.summary}">
                                                    ${icn.summary}
                                                </c:if>
                                            </p>
                                            <c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
                                                <div class="zfCon">${icn.forwarderContent}</div>
                                            </c:if>

                                            <div class="infor">
                                                <div class="float_l">
                                                    <span>${icn.captureWebsiteName}</span> |
                                                    <span><fmt:formatDate value="${icn.published}" pattern="MM月dd日 HH:mm"/></span>
                                                    <span>相似文章数:${mg.num}</span>
                                                </div>
                                                <%--<div class="float_r">
                                                    <a href="#" class="btn btn-warning btn-sm">深度分析</a>
                                                </div>--%>
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
        <!--敏感信息Top20 end-->
       
		

        <!--非敏感信息text start-->
        <%-- 
        <div class="row-fluid" <c:if test="${empty fwbList}">style="display: none" </c:if>>
            <div class="con">
                <div class="text dot blue">今日非敏感信息${fmNum}条，占今日全部信息的${fmPercent}，经微热点(微舆情)大数据系统对其进行多个维度的综合分析，建议重点关注以下信息：</div>
            </div>
        </div>--%>
        <!--非敏感信息text end-->


        <!--非微主要观点Top20 start-->
        <div class="row-fluid" style="<c:if test="${empty fwbList}">display: none</c:if>">
            <div class="con">
                <div class="title">
                    <div class="titleBlue" style="width:120px;">非微博重点内容Top${fwbTop}<!--<span style="font-size:12px;<c:if test="${filterNum == 0}">display:none;</c:if>">(已排除垃圾数据${filterfmNum}条)</span>--></div>
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
                            	<li style="margin-top:25px;padding:10px;min-height:initial">
                            		<div style="float:left;width:100%;color:#333333;font-size:14px;">重点内容${status.index+1}<!--  <span style="float:right">${mg.percentStr}</span>-->
                            		<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                            			<c:if test="${icn.customFlag1 != 4}">
                            				<span style="font-size:14px;color:#ff2121;">敏感</span>
                            			</c:if>
                            		</c:forEach></div>
                            	</li>
                            	<li style="margin-top:15px;padding:0 10px;min-height:initial"></li>
                            	<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                                    <li style="padding:0 10px;min-height:initial"">
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
                                        
                                        <div class="m_r">
	                                        <div class="progress2  progress-striped active">
											      <div id="setSchedule2"class="progress2-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:${mg.percentStr};">
											      		
											       </div>
											</div>
                                            <p class="quickLink">
                                                <a href="${icn.webpageUrl}" style="margin-left:-5px;font-size:14px;color:#333333;">
                                                	【${icn.title}】${icn.summary}
                                                </a>
                                            </p>

                                            <c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
                                                <div class="zfCon">${icn.forwarderContent}</div>
                                            </c:if>

                                            <div class="infor">
                                                <div class="float_l">
                                                	来自&nbsp;
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
                                                    <!--<div class="float_r">
                                                        <a href="#" class="btn btn-warning btn-sm">深度分析</a>
                                                    </div>-->
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
        <!--非微博主要观点 end-->
		 <!--微博主要观点 start-->
        <div class="row-fluid" <c:if test="${empty wbList}">style="display: none" </c:if>>
            <div class="con">
                <div class="title">
                     <div class="titleBlue" style="width:120px;">${mgTitle}Top${wbTop}<!-- <span style="font-size:14px; <c:if test="${filterNum == 0}">display:none;</c:if>">(已排除垃圾数据${filterNum}条)</span>--></div>
                </div>
                <div class="main_list">
                    <c:if test="${empty wbList}">
                        <br>
                        <div align="center" style="padding-top:50px"><p style="display:inline;font-size: 14px">
                            <img src="<%=njxBasePath %>/images/warn.png" style="width:60px"><br/>此时间段暂无信息</p>
                        </div>
                    </c:if>
                    <c:if test="${not empty wbList}">
                        <ul>
                            <c:forEach var="mg" items="${wbList}" begin="0" end="9" varStatus="status">
                            	<li style="margin-top:25px;padding:10px;min-height:initial">
                            		<div style="float:left;width:100%;color:#333333;font-size:14px;">重点内容${status.index+1}<!--  <span style="float:right">${mg.percentStr}</span>-->
                            		<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                            			<c:if test="${icn.customFlag1 != 4}">
                            				<span style="font-size:14px;color:#ff2121;">敏感</span>
                            			</c:if>
                            		</c:forEach></div>
                            	</li>
                            	<li style="margin-top:15px;padding:0 10px;min-height:initial"></li>
                                <c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
                                    <li style="padding:0 10px;min-height:initial"">
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
                                        
                                        <div class="m_r">
	                                        <div class="progress2  progress-striped active">
										      <div id="setSchedule2"class="progress2-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:${mg.percentStr};">
										       </div>
											</div>
                                            <p class="quickLink">
                                                <a href="${icn.webpageUrl}" style="font-size:14px;color:#333333;">
                                                    ${icn.summary}
                                                </a>
                                            </p>

                                            <c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
                                                <div class="zfCon">${icn.forwarderContent}</div>
                                            </c:if>

                                            <div class="infor">
                                                <div class="float_l">来自&nbsp;
                                        			<c:forEach items="${mg.iContentCommonNetList}" varStatus="status" var="icn" begin="0" end="0">
                                        		          <c:choose>
                                        					 <c:when test="${empty icn.author && !empty icn.userScreenName}">
                                        					 	${icn.userScreenName}
                                        		 		 	</c:when>
                                        					 <c:when test="${empty icn.userScreenName && empty icn.author}">
                                        					 	微博网友
                                        					 </c:when>
                                        					  <c:otherwise>
                                        					 	${icn.author}
                                        					 </c:otherwise>
                                        				</c:choose>	
                                        			</c:forEach>
                                                    <span>&nbsp;等${mg.num}条近似微博</span>
                                                </div>
                                                    <!--<div class="float_r">
                                                        <a href="#" class="btn btn-warning btn-sm">深度分析</a>
                                                    </div>-->
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
            <div class="row-fluid" style="text-align: center;">
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
        /*$.each(data.data,function(){
         this.symbolSize = 6;
         this.itemStyle={'normal':{'lineStyle':{'width':2.8}}};
         });*/
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
                        toolbox: {
                            show : true,
                            orient:'horizontal',
                            y:10,
                            x:'right',

                            feature : {
                                mark : {show: false},
                                dataView : {
                                    show: false,
                                    readOnly: false,
                                    lang: ['数据视图', '关闭', '刷新']
                                },
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.title
                                },
                            }
                        },
                        legend: {
                            data:["全部"],
                            show:false
                        },
                        grid:{
                            x:50,
                            x2:30
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
                            /*axisLine: {
                             onZero: false,
                             show:false
                             },
                             splitLine:{
                             show:false
                             },
                            splitArea:{
                                show:true,
                                areaStyle:{
                                    color:['#08172c','#08172c']
                                }
                            },*/
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