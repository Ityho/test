<%@ page pageEncoding="utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/font-icon.css" />
<link href="<%=njxBasePath%>/css/style.css" rel="stylesheet" type="text/css">
<link href="<%=njxBasePath%>/css/common.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/dayReport.css" />
<link rel="stylesheet" type="text/css" href="<%=njxBasePath%>/css/iconfont.css" />
<link href="<%=njxBasePath%>/css/tips.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=njxBasePath%>/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=njxBasePath%>/js/index.js"></script>
<script src="<%=njxBasePath%>/js/jquery.JPlaceholder.js"></script>

<script type="text/javascript" src="<%=njxBasePath%>/js/echarts/echarts.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=njxBasePath%>/js/echarts/chart/line.js" charset="UTF-8"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#uuid=ad6ca8fe-7189-423a-b7b6-ed44605dd77a&style=-1"></script>
</head>
<style>
.main_list li .m_l {
	float: left;
	text-align: center;
}

.main_list li .m_l>img {
	width: 20px;
	height: 20px;
	border-radius: 50%
}

.scale {
	transform: scale(.9, .9);
	-ms-transform: scale(.9, .9);
	-webkit-transform: scale(.9, .9);
	-o-transform: scale(.9, .9);
	-moz-transform: scale(.9, .9);
}
/*   .background3{
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
@media only screen and (min-width:750px ){
	.background3{
		background-image: url("../reportView/images/bgV3.jpg");
		background-repeat: no-repeat;
		background-position: center center;
		background-size:100% 100%;
		height:200px;
	}
	.logo2{
		background-image: url("../reportView/images/logo2.png");
		background-repeat: no-repeat;
		background-position: center center;
		background-size:50% 50%;
		height:180px;
	} 

}
.sourceTitle{
	margin:5px 15px 0px 16px;height:60px;float:left;width:35px;text-align:center;font-size:14px;color:#949494;
}
.t1{
	width:45px;
}
.i1{
	width:100%;color:#949494;font-size:16px;
}   */
</style>
<body class="mobileStyle dayReport">
	<input type="hidden" id="userId" value="${keywordReportRecord.userId}">
	<input type="hidden" id="keywordId" value="${keywordReportRecord.keywordId}">
	<input type="hidden" id="code" value="${keywordReportRecord.shareCode}">
	<input type="hidden" value="${filterNum}">
	<input type="hidden" id="wbTop" value="${wbTop}">
	<input type="hidden" id="fwbTop" value="${fwbTop}">
	<!--head代码 start-->
	<div id="head" class="rel h_line">
		<div class="head-top">
			<span class="logo logoJC float_l" onclick="javascript:location.href='http://www.wrd.cn'"></span>
			<span class="r_btn abs" style="top: 14px;"> 
				<a class="loginBtn loginBtnReport" href="javascript:void(0);" title="微信" onclick="sharePage(event,'weixin');return false;">
					<img src="<%=njxBasePath %>/images/mobile.png" style="margin-right: 5px; width: 12px">在手机上查看</a>
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
				<div class="info" style="height: 44px;background-color:#007bdd;">
					<%-- <img src="<%=njxBasePath %>/reportView/images/logo-w.png" style="width: 85px; padding: 8px 0px 0px 10px;"> --%>
					<div class="float_l align_l" style="margin: 10px 0px 0px 12px; color: #000; font-weight: bold;">
						<div style="width:10%;float:left;height:24px;width:24px;border-radius:50%; background: url(${userHead }) no-repeat center; background-size: cover;"></div>
						<div style="height: 24px;line-height: 24px;margin: 0px 0px 0px 30px;font-size: 12px;color:#ffffff;font-weight: normal;">${nickname }</div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="con Report-header" style="background-color:#007bdd;padding: 0px 0px 20px 0px;">
					<div class="" style="font-size: 18px;color:#ffffff;text-align: center;padding-right: 8px;">【${reportName}】&nbsp;微热点(微舆情)监测日报</div>
					<div style="font-size: 14px;color:#ffffff;">${reportTime}&nbsp;00:00~23:59</div>
				</div>
			</div>
			<div class="empty" style="display:${showFlag}"></div>
			<!--报告头部 end-->
			<div class="row-fluid" style="background-color: #f3f3f3; margin-top: -10px;" <c:if test="${empty sources}">style="display: none" </c:if>>
				<div class="con" style="text-align: center;">
					<c:if test="${not empty sources}">
						<div class="sourceTitle">
							<i class="iconfont icon-total i1"></i> 
							<span style="width: 100%;">全部</span>
							<span>${totalNum}</span>
						</div>
						<c:forEach var="source" items="${sources}">
							<c:if test="${source.name == '网站'}">
								<div class="sourceTitle">
									<i class="iconfont icon-website i1"></i> 
									<span style="width: 100%;">${source.name}</span>
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '微博'}">
								<div class="sourceTitle">
									<i class="iconfont icon-weibo2 i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '新闻'}">
								<div class="sourceTitle">
									<i class="iconfont icon-news2 i1"></i> 
									<span style="width: 100%;">${source.name}</span>
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '论坛'}">
								<div class="sourceTitle">
									<i class="iconfont icon-bbs2 i1"></i>
									<span style="width: 100%;">${source.name}</span>
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '客户端'}">
								<div class="sourceTitle t1">
									<i class="iconfont icon-app i1"></i> 
									<span style="width: 100%;">${source.name}</span>
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '微信'}">
								<div class="sourceTitle">
									<i class="iconfont icon-weixin2 i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '报刊'}">
								<div class="sourceTitle">
									<i class="iconfont icon-baokan i1"></i> 
									<span style="width: 100%;">${source.name}</span>
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '政务'}">
								<div class="sourceTitle">
									<i class="iconfont icon-zhengwu i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '博客'}">
								<div class="sourceTitle">
									<i class="iconfont icon-boke i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '视频'}">
								<div class="sourceTitle">
									<i class="iconfont icon-video i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
							<c:if test="${source.name == '外媒'}">
								<div class="sourceTitle">
									<i class="iconfont icon-waimei i1"></i> 
									<span style="width: 100%;">${source.name}</span> 
									<span>${source.num}</span>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
				</div>
				<div class="clear"></div>
			</div>
			
			<%-- <div class="row-fluid" style="margin-top: 5px;" <c:if test="${empty sources}">style="display: none" </c:if>> --%>
			<div class="row-fluid" style="margin-top: 5px;display: none;">
				<div class="con">
					<!--             	<div class="title" style="margin:10px;">
                    <h1>日报概况:</h1>
                </div> -->
					<%--                 <div class="hwBox" >
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
                      	 		<span>舆情信息主要来源于${source.name}。</span>
                      	 	</c:forEach>
                      	 	<span>对本次信息新增信息进行情感分析,非敏感信息${fmNum}条,占比${fmPercent}。敏感信息${mgNum}条，占比${mgPercent}，近三天</span>
                      	 	<span style="color:#3454a1">信息总量</span>和<span style="color:#c1222a">敏感信息量</span>的走势对比如下:
                    	</c:if>
                    </div>
                </div> --%>
					<div>
						<div style="float: left; margin-left: 10px;">
							<span style="color: #6B6B6B">敏感、非敏感近三天走势</span>
						</div>
						<div style="float: right; margin-right: 25px; color: #6B6B6B; font-size: 12px;">
							<span style="color: #3454a1">非敏感${fmNum}条&nbsp;&nbsp;&nbsp;</span>
							<span style="color: #c1222a">敏感${mgNum}条</span>
						</div>
					</div>
					<div class="mwbBorder chart" style="width: 100%; height: 300px;" id="hotLine"></div>
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
				<div class="hwBox">
					<div style="word-break: break-all; text-indent: 2em; width: 100%; line-height: 200%; letter-spacing: 1px;">
						${reportTime}00:01-${reportTime}23:59,针对“${reportNameAll}”监测方案进行统计,网络上该事件共新增信息${totalNum}条,建议重点关注以下信息:
					</div>
				</div>
				<div class="con">
					<div class="main_list">
						<c:if test="${empty mgList}">
							<br>
							<div align="center" style="padding-top: 50px">
								<p style="display: inline; font-size: 14px">
									<img src="<%=njxBasePath %>/images/warn.png" style="width: 60px"><br />此时间段暂无信息
								</p>
							</div>
						</c:if>
						<c:if test="${not empty mgList}">
							<ul>
								<c:forEach var="mg" items="${mgList}">
									<c:forEach items="${mg.iContentCommonNetList}" var="icn">
										<li>
											<div class="m_l">
												<c:if test="${not empty icn.profileImageUrl}">
													<img src='${icn.profileImageUrl}' />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
													<c:if test='${icn.captureWebsiteName=="腾讯微博"}'>
														<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg" />
													</c:if>
													<c:if test='${icn.captureWebsiteName=="新浪微博"}'>
														<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg" />
													</c:if>
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg" />
												</c:if>
												<c:if test="${empty icn.profileImageUrl&&icn.originType=='wz'}">
													<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-web.jpg" />
												</c:if>
											</div>
											<div class="m_r">
												<p class="quickLink">
													<a target="_blank" href="${icn.webpageUrl}"> 
														<c:if test="${icn.originType=='wb'||icn.originType=='txwb'}">
															<c:if test="${empty icn.author}">
                                                            ${icn.captureWebsiteName}
                                                        	</c:if>
															<c:if test="${not empty icn.author}">
                                                            ${icn.author}
                                                        	</c:if>
														</c:if> 
														<c:if
															test="${icn.originType!='wb'&&icn.originType!='txwb'}">
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
														<span>${icn.captureWebsiteName}</span> | <span><fmt:formatDate value="${icn.published}" pattern="MM月dd日 HH:mm" /></span> <span>相似文章数:${mg.num}</span>
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
						<div class="titleBlue" style="background-color: #007bdd;">非微博重点内容</div>
					</div>
					<div class="main_list">
						<c:if test="${empty fwbList}">
							<br>
							<div align="center" style="padding-top: 50px">
								<p style="display: inline; font-size: 14px">
									<img src="<%=njxBasePath %>/images/warn.png" style="width: 60px"><br />此时间段暂无信息
								</p>
							</div>
						</c:if>
						<c:if test="${not empty fwbList}">
							<ul>
								<c:forEach var="mg" items="${fwbList}" begin="0" end="9" varStatus="status">
									<li style="padding: 30px 0px 0px 10px; min-height: initial">
										<div style="float: left; color: #333333; font-size: 14px;">重点内容${status.index+1}</div>
									</li>
									<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
										<li style="padding: 0 10px; min-height: initial">
											<%-- <div class="m_l" style="<c:if test="${ status.index==9}">margin-left:-63px;</c:if><c:if test="${ status.index!=9}">margin-left:-55px;</c:if>margin-top:20px;width:44px;">
                                    		<div id="fmg_${status.index}" class="scale"style="line-height: 44px;text-align: center;background: url(<%=njxBasePath%>/images/investjd.png);">
                                            	<span style="width:100%;float:left;">${mg.percentStr}</span>
                                            	<input type="hidden"  name="fmPercent" value = "${mg.percentStr}">
                                            </div>
                                            <c:if test="${icn.customFlag1 != 4}">
                                            	<span style="width:100%;float:left;font-size:14px;color:#ff2121;">敏感</span>
                                            </c:if>
                                        </div>  --%>
											<div class="" style="margin-left: 30px; margin-top: -1px; width: 125px; height: 20px;">
												<span style="width: 35%; float: left; font-size: 14px; padding-left: 10px;">(${mg.percentStr})</span>
												<input type="hidden" name="mgPercent" value="${mg.percentStr}">
												<c:if test="${icn.customFlag1 != 4}">
													<span style="float: left; font-size: 14px; color: #ff2121;">敏感</span>
												</c:if>
											</div>
											<div class="">
												<p class="quickLink">
													<a target="_blank" href="${icn.webpageUrl}" style="margin-left: -5px; font-size: 14px; color: #333333;">
														【${icn.title}】${icn.summary} </a>
												</p>

												<c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
													<div class="zfCon">${icn.forwarderContent}</div>
												</c:if>

												<div class="infor" style="margin-top: 0px;">
													<div class="float_l">
														<div class="m_l">
															<c:if test="${not empty icn.profileImageUrl}">
																<img src='${icn.profileImageUrl}' />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='wb'}">
																<c:if test='${icn.captureWebsiteName=="腾讯微博"}'>
																	<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-txwb.jpg" />
																</c:if>
																<c:if test='${icn.captureWebsiteName=="新浪微博"}'>
																	<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weibo.jpg" />
																</c:if>
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='sp'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-video.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='wx'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-weixin.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='xw'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-news.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='lt'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-bbs.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='bk'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-blog.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='app'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-app.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='zw'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-affairs.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='baokan'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-press.jpg" />
															</c:if>
															<c:if test="${empty icn.profileImageUrl&&icn.originType=='jw'}">
																<img src="<%=njxBasePath%>/images/userlogo/app-more-icon-media.jpg" />
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
					</div>
				</div>
			</div>
			<!--非微博主要观点 end-->
			<!--微博主要观点 start-->
			<div class="row-fluid"
				<c:if test="${empty wbList}">style="display: none" </c:if>>
				<div class="con">
					<div class="title">
						<div class="titleBlue">${mgTitle}</div>
					</div>
					<div class="main_list">
						<c:if test="${empty wbList}">
							<br>
							<div align="center" style="padding-top: 50px">
								<p style="display: inline; font-size: 14px">
									<img src="<%=njxBasePath %>/images/warn.png" style="width: 60px"><br />此时间段暂无信息
								</p>
							</div>
						</c:if>
						<c:if test="${not empty wbList}">
							<ul>
								<c:forEach var="mg" items="${wbList}" begin="0" end="9" varStatus="status">
									<li style="padding: 30px 0px 0px 10px; min-height: initial">
										<div style="float: left; color: #333333; font-size: 14px;">重点内容${status.index+1}</div>
									</li>
									<c:forEach items="${mg.iContentCommonNetList}" var="icn" begin="0" end="0">
										<li style="padding: 0 10px; min-height: initial"">
											<%-- <div class="m_l" style="<c:if test="${ status.index==9}">margin-left:-63px;</c:if><c:if test="${ status.index!=9}">margin-left:-55px;</c:if>margin-top:20px;width:44px;">
                                    		<div id="mg_${status.index}" class="scale"style="line-height: 44px;text-align: center;background: url(<%=njxBasePath%>/images/investjd.png);">
                                            	<span style="width:100%;float:left;">${mg.percentStr}</span>
                                            	<input type="hidden"  name="mgPercent" value = "${mg.percentStr}">
                                            </div>
                                            <c:if test="${icn.customFlag1 != 4}">
                                            	<span style="width:100%;float:left;font-size:14px;color:#ff2121;">敏感</span>
                                            </c:if>
                                        </div> --%>
											<div class="" style="margin-left: 30px; margin-top: -1px; width: 125px; height: 20px;">
												<span style="width: 35%; float: left; font-size: 14px; padding-left: 10px;">(${mg.percentStr})</span>
												<input type="hidden" name="mgPercent" value="${mg.percentStr}">
												<c:if test="${icn.customFlag1 != 4}">
													<span style="float: left; font-size: 14px; color: #ff2121;">敏感</span>
												</c:if>
											</div>
											<div class="">
												<p class="quickLink">
													<a target="_blank" href="${icn.webpageUrl}" style="font-size: 14px; color: #333333;">
														${icn.summary} </a>
												</p>
												<c:if test="${(icn.originType=='wb'||icn.originType=='txwb')&&not empty icn.forwarderContent}">
													<div class="zfCon">${icn.forwarderContent}</div>
												</c:if>

												<div class="infor" style="margin-top: 0px;">
													<div class="float_l">
														<div class="m_l" style="width: 100% !important; text-align: left;">
															<c:if test="${not empty icn.profileImageUrl}">
																<img src='${icn.profileImageUrl}' />
															</c:if>
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
				<div class="row-fluid" style="text-align: center; margin: 10px 0 0;">
					<a class="visible-xs" href="http://d.51wyq.cn">
						<img width="100%" src="<%=njxBasePath%>/images/bottomPic.jpg" /></a> 
					<a class="visible-lg" href="http://d.51wyq.cn">
						<img width="100%" src="<%=njxBasePath%>/images/bottomPic2.jpg" /></a>
				</div>
				<!--分享组件部分 end-->
			</c:if>
		</div>
	</div>
	<!--日报 end-->
	</div>

	<div id="cancleBtn" class="cancleBtn"></div>

	<!--底部部分代码 start-->
	<div class="h35 clear"></div>
	<div id="footer">
		<p>
			<a href="http://www.wrd.cn/aboutUs.shtml">关于我们</a> | <a href="http://www.wrd.cn/contectUs.shtml">联系我们</a> | <a href="http://www.wrd.cn/help.shtml">帮助中心</a>
		</p>
		<p>&copy; Copyright wrd.cn 2002-2017 All Rights Reserved 沪ICP备05035762号-9｜增值电信业务经营许可证B2-20090366</p>
	</div>
	<!--底部部分代码 end-->
	<script type="text/javascript">
		window.onload = function(){
			var countWb = $("#wbTop").val();
			var countFwb = $("#fwbTop").val();
			if(countWb > 0){
				var fm = "#mg_";
		        for(var i = 0;i< countWb;i++){
		        	 var fmId = "";
		        	 fmId = fm+i;
		        	 console.log(fmId);
		        	 console.log($(fmId).find('input[name=mgPercent]').val().replace("%",""));
		        	 var object = document.getElementById(fmId.replace("#",""));
		        	
		        	 loadImg($(fmId).find('input[name=mgPercent]').val().replace("%",""),object);
		        	 }
		        }
			if(countFwb > 0){
				var fm = "#fmg_";
		        for(var i = 0;i< countFwb;i++){
		        	 var fmId = "";
		        	 fmId = fm+i;
		        	 console.log(fmId);
		        	 console.log($(fmId).find('input[name=fmPercent]').val().replace("%",""));
		        	 var object = document.getElementById(fmId.replace("#",""));
		        	 loadImg($(fmId).find('input[name=fmPercent]').val().replace("%",""),object);
		        	 }
		        }
			}
		function loadImg (data,object){
		
			var i = 100;
			if(i>data){
				i=data
			}
			object.innerHTML = i+'%';
			if(i < 1){
				i=1;
			}
			var imgLeft = -(i*44+(i*10))+'px';
			object.style.backgroundPosition = imgLeft+'\t'+'0px';
			
			
	}
	</script>
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

    //LineChart('${lineChat}',"hotLine");

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
                      /*   toolbox: {
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
                        }, */
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

    function recordSign(){
        $.ajax({
            url : "<%=njxBasePath%>/recordSign.shtml?userId="+$("#userId").val(),
            type : "get",
            success : function(result){
            }
        })
    }

    function recordDown(){
        $.ajax({
            url : "<%=njxBasePath%>/recordDown.shtml?userId=" + $("#userId").val(),
			type : "get",
			success : function(result) {
				}
			})
		}
	</script>
</body>
</html>