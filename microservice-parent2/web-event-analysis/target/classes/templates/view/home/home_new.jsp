<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>
<%@ include file="/top.jsp" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		
	<script src="<%=staticResourcePathH5 %>/js/swa/util.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/productBuy/jquery.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/home/wyrem.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/productBuy/icheck.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/productBuy/iscroll.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/navigate.js?v=<%=SYSTEM_INIT_TIME %>"></script>
	<script src="<%=staticResourcePathH5%>/js/common-order.js?v=<%=systemConfig.SYSTEMINITTIME %>"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5%>/css/home/wyui.css?v=<%=systemConfig.SYSTEMINITTIME %>" />	
	<link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5%>/css/home/font-icon.css?v=<%=systemConfig.SYSTEMINITTIME %>" />	
	<link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5%>/css/home/Basics.css?v=<%=systemConfig.SYSTEMINITTIME %>" />	
	<link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5%>/css/home/module.css?v=<%=systemConfig.SYSTEMINITTIME %>" />
</head>
<style>
input::-webkit-input-placeholder {
	color: #b0b0b0;
}

input::-moz-placeholder { /* Mozilla Firefox 19+ */
	color: #b0b0b0;
}

input:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
	color: #b0b0b0;
}

input:-ms-input-placeholder { /* Internet Explorer 10-11 */
	color: #b0b0b0;
}
</style>

<style type="text/css">
.wy-main-tab {
	position: relative;
	margin: 0 10px;
}

.wy-main-tab li {
	float: left;
	position: relative;
}

.wy-main-tab li a {
	font-size: 15px;
	font-weight: 400;
	color: rgba(255, 255, 255, 1);
	opacity: 0.5;
	padding: 0 10px;
}

.wy-main-tab li.active a {
	opacity: 1;
}

.wy-main-tab li.active a:after {
	content: "";
	width: 0;
	height: 0;
	border-left: 7px solid transparent;
	border-right: 7px solid transparent;
	border-bottom: 7px solid #fff;
	display: inline-block;
	position: absolute;
	bottom: -11px;
	left: 0;
	right: 0;
	margin: auto;
}
</style>
<body>
	<div class="wyui-content rel">
		<!-- 用户登录 start -->
		<div class="heard_userInfor white fz14">
			<s:if test="#attr.admin.nickname != null && #attr.admin.nickname != ''">
			  <a href="javascript:location.href = njxBasePath + '/lookMore.shtml';"><img src="${admin.userHead }" class="img-circle icon_60 v-a-middle"/></a>
			  ${admin.nickname }
			</s:if>
			<s:if test="admin == null">
			<a href="http://d.51wyq.cn"><img src="<%=staticResourcePathH5 %>/images/defaultHead.jpg?v=<%=SYSTEM_INIT_TIME %>" class="img-circle icon_60 v-a-middle"/></a>
			  未登录
			</s:if>
		</div>
		<!-- 用户登录 end -->
		
		<!-- banner start -->
		<div class="wyui_bg wyui-text-center" style="height: 14.5625rem; background-image: url(<%=staticResourcePathH5%>/images/home/home_banner.jpg);">
			<img src="<%=staticResourcePathH5%>/images/home/logo-home.png" class="mb35" width="50%" style="margin-top: 22%;"/>
			<%-- <img src="<%=staticResourcePathH5%>/images/home/home_banner_fourText.png" width="80%"/> --%>
		</div>
		<form action = "<%= njxBasePath %>/view/search/goSearch.shtml#1" name = "searchForm" method="post" style="position: relative;">	
		<div style="margin-top: -18%;">
			<div class="clearfix wy-main-tab">
				<ul>
					<li class="active" index="1">
						<a href="javascript:void(0)">热度指数</a>
					</li>
					<li index="2">
						<a href="javascript:void(0)">微博情绪</a>
					</li>
				</ul>
			</div>
			<!-- banner end -->
			<!-- search start -->
			
				<section class="wyui-card border_r5 mb15">
					<div class="wyui-input-row">
						<input style="cursor:pointer;" id="searchKeyword" name="searchKeyword" value="${searchKeyword}" type="search" class="wyui-input-right wyui-input-lg mb0 border0 fz15" placeholder="搜索人名、企业名、品牌名或事件关键词"><span class="wyui-icon iconfont orange400" style="cursor:pointer;" onclick="goHomeSearchTwo()" id = "goHomeSearch">&#xe6ce;</span>
						<input id = "keyword1" name="keyword1" type="hidden">
						<input id = "filterKeyword1" name="filterKeyword1" type="hidden">
					    <input id = "categoryId1" name="categoryId1" type="hidden" value="">
						<input id = "type1" name="type1" type="hidden" value="">
						<input id ="adminId" value="${admin.userId }" type="hidden">
					</div>
				</section>
			
		</div>
		</form>
		<!-- search end -->	
		<!-- advert start -->
			<section class="wyui-card border_r5 wyui-advert" style="display:none;">
				 <img src="<%=staticResourcePathH5%>/images/wyq-gg-1.png"/>
			</section>
		<!-- advert end -->	
		<!-- 热点推荐 start -->
			<section class="wyui-card border_r5">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz14">热点推荐</h3>
				 </div>
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding0">
						<ul id = "keysChange" class="wyui-table-view wyui-grid-view wyui-grid-9">
							<s:iterator var="item" value="hotIncidents" status="vs">
								<li class="wyui-table-view-cell wyui-media wyui-col-xs-12" onclick="searchHelpWord('<s:property value="#item.incidentTitle"/>','<s:property value="#item.keyword"/>','<s:property value="#item.filterKeyword"/>')">
									<img src="<%=staticResourcePathH5%>/images/home/icon-hot.svg" class="icon_40 v-a-middle"/>
									<label style="font-size:14px;">
										<s:if test="#item.incidentTitle.length()>7">
											<s:property value="#item.incidentTitle"/>
										</s:if>
										<s:else>
											<s:property value="#item.incidentTitle"/>
										</s:else>
									</label>
								</li>
							</s:iterator>
						</ul>
					</div>
				</div>
			</section>
		<!-- 热点推荐 end -->
		<!-- 排行榜单 start -->
			<section class="wyui-card border_r5" style="display:none;">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz14">排行榜单</h3>
				 	<div class="wyui-card-actions">
				 		<a class="orange500 fz14" href="<%=njxBasePath%>/goRankingList.shtml">查看更多<i class="iconfont fz14">&#xe659;</i></a>
				 	</div>
				 </div>
				 
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding0 padding-v-10">
						 <ul class="wyui-table-view wyui-grid-view wyui-grid-9 rankings-group line_bottom padding-v-10">
						 <c:forEach var="item" items="${ctkMore}" begin="1" end="1" varStatus="status">
						 		<li class="wyui-table-view-cell wyui-media wyui-col-xs-4 border0 text-center">
									<div class="head-icon mt15 mb5" onclick="goToHotSearch(${status.index })">
										<div class="icon_rank">
											<img src="<%=staticResourcePathH5%>/images/home/icon_rank2.png"/>
										</div>
										
										<img src="${item.logoUrl}" class="img-circle border-red" style="width: 4rem;"/>
										<form id = "fmGoHot${status.index }" method="post">
											<input type="hidden" name="keyword1" value = "${item.keyword}">
											<input type="hidden" name="searchKeyword" value = "${item.shortName}">
											<input type="hidden" name="filterKeyword1" value = "${item.filterKeyword}">
											<input type="hidden" name="categoryId1" value = "${item.categoryId}">
											<input type="hidden" name="type1" value = "${item.type}">
											<input type="hidden" name="category" value = "${item.category}">
										</form>
									</div>
									<p class="fz13">${item.shortName}</p>
									<p class="orange500 fz13"><fmt:formatNumber value="${item.ratioHotDay}" pattern="0.00"/></p>
								</li>
						 </c:forEach>
						 <c:forEach var="item" items="${ctkMore}" begin="0" end="0" varStatus="status">
							 	<li class="wyui-table-view-cell wyui-media wyui-col-xs-4 border0 text-center fz17">
									<div class="head-icon mb15" onclick="goToHotSearch(${status.index })">
										<img src="<%=staticResourcePathH5%>/images/rangking/icon_crown.png" class="icon_crown"/>
										<div class="icon_rank">
											<img src="<%=staticResourcePathH5%>/images/home/icon_rank1.png"/>
										</div>
										<img src="${item.logoUrl}" class="img-circle border-red" style="width: 5.5rem;"/>
										<form id = "fmGoHot${status.index }" method="post">
											<input type="hidden" name="keyword1" value = "${item.keyword}">
											<input type="hidden" name="searchKeyword" value = "${item.shortName}">
											<input type="hidden" name="filterKeyword1" value = "${item.filterKeyword}">
											<input type="hidden" name="categoryId1" value = "${item.categoryId}">
											<input type="hidden" name="type1" value = "${item.type}">
											<input type="hidden" name="category" value = "${item.category}">
										</form>
									</div>
									<p class="fz17">${item.shortName}</p>
									<p class="orange500 fz17 mt5"><fmt:formatNumber value="${item.ratioHotDay}" pattern="0.00"/></p>
								</li>
						</c:forEach>
						 <c:forEach var="item" items="${ctkMore}" begin="2" end="2" varStatus="status">
							 	<li class="wyui-table-view-cell wyui-media wyui-col-xs-4 border0 text-center">
									<div class="head-icon mt15 mb5" onclick="goToHotSearch(${status.index })">
										<div class="icon_rank">
											<img src="<%=staticResourcePathH5%>/images/home/icon_rank3.png"/>
										</div>
										<img src="${item.logoUrl}" class="img-circle border-red" style="width: 4rem;"/>
										<form id = "fmGoHot${status.index }" method="post">
											<input type="hidden" name="keyword1" value = "${item.keyword}">
											<input type="hidden" name="searchKeyword" value = "${item.shortName}">
											<input type="hidden" name="filterKeyword1" value = "${item.filterKeyword}">
											<input type="hidden" name="categoryId1" value = "${item.categoryId}">
											<input type="hidden" name="type1" value = "${item.type}">
											<input type="hidden" name="category" value = "${item.category}">
										</form>
									</div>
									<p class="fz13">${item.shortName}</p>
									<p class="orange500 fz13"><fmt:formatNumber value="${item.ratioHotDay}" pattern="0.00"/></p>
								</li>
						 </c:forEach>
						</ul>
						<div class="wyui-tipGroup">
					     	<ul class="wyui-table-view wyui-space-between fz14">
						     	<li class="wyui-table-view-cell fc-gray9 fz10 padding-v-0">
						     		<span class="ml10 w50">排名</span>
						     		<span class="text-center" style="width: 10rem">名称</span>
						     		<span>热度指数 <i class="iconfont fc-p-orange wyui-tip-showBtn">&#xe72a;</i></span>
						     	</li>
					     	</ul>
						     <div class="wyui-tipinfo">
								<div class="wyui-tip-actions"><i class="iconfont close">&#xe833;</i></div>
								<div class="wyui-tip-cont">热度指数是指通过对全网信息量进行加权计算后得出的能反 映其在网络上受关注程度的指数，数值为0~100，数值越大， 表明其网络受关注度越高。</div>
							</div>
					     </div>
					     <ul class="wyui-table-view wyui-space-between fz14">
					     <c:forEach var="item" items="${ctkMore}" begin="3"  varStatus="status">
					     
					     	<li class="wyui-table-view-cell">
					     		<span class="ml10 w50">${status.index+1 }</span>
					     		<span style="width: 10rem" onclick="goToHotSearch(${status.index })">
					     				<form id = "fmGoHot${status.index }" method="post">
											<input type="hidden" name="keyword1" value = "${item.keyword}">
											<input type="hidden" name="searchKeyword" value = "${item.shortName}">
											<input type="hidden" name="filterKeyword1" value = "${item.filterKeyword}">
											<input type="hidden" name="categoryId1" value = "${item.categoryId}">
											<input type="hidden" name="type1" value = "${item.type}">
											<input type="hidden" name="category" value = "${item.category}">
										</form>
					     			<img src=" ${item.logoUrl}" class="img-circle icon_80 v-a-middle mr5"/>
					     			${item.shortName}
					     			
					     		</span>
					     		<span><font class="orange500 fz17"><fmt:formatNumber value="${item.ratioHotDay}" pattern="0.00"/></font></span>
					     	</li>
					     </c:forEach>
					     </ul>
					</div>
				</div>
			</section>
		<!-- 排行榜单 end -->
		<!-- 大数据解读 start -->
			<section class="wyui-card border_r5">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz14">大数据解读</h3>
				 </div>
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding-h-15 padding-v-10">
					
						 <c:forEach var="item" items="${hotEvents}" begin="0" end="2">
						 	<a href="<%=njxBasePath %>/view/hotPreview/doHotEventPreview.shtml?hotEventPreview.link=${item.link}">
						 	<div class="wyui-card-pic wyui-mask-card white" style="background-image:url(${item.imageUrl})">
							 	<div class="wyui-card-header wyui-card-media" style="height:30vw;">
							 		<h3 class="wyui-card-title fz15" style="line-height:30px;">${item.title}</h3>
							 		<div class="wyui-card-footer white fz13 space-between">
							 		<span><fmt:formatDate pattern="yyyy-MM-dd" value="${item.createTime}" /></span>
							 		<span style="display:none;"><i class="iconfont mr5">&#xe6d0;</i>来自微热点</span>
							 	</div>
							 	</div>
							</div>
							</a>
						</c:forEach>
					</div>
				</div>
			</section>
		<!-- 大数据解读 end -->
		<s:if test="admin != null">
		<!-- 信息监测 start -->
			<section class="wyui-card border_r5">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz12">信息监测<span class="fc-gray9">（品牌监测、预警、日报）</span></h3>
				 	<div class="wyui-card-actions">
				 		<a class="orange500 fz13" href="<%=njxBasePath %>/keywordInfo.shtml">查看更多<i class="iconfont fz14">&#xe659;</i></a>
				 	</div>
				 </div>
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding-h-15 padding-v-10">	
						<s:if test="kwList != null && kwList.size() > 0">	
							<c:forEach var="item" items="${kwList}" begin="0" end="1">
								<ul class="wyui-table-view wyui-space-between">
								  <li class="wyui-table-view-cell padding-h-0">
								  	<div style="margin:0px">
								  		<h3 class="fz20 mb10 fw500" onclick="javascript:location.href ='<%=njxBasePath %>/view/keyword/goList.shtml?kw.keywordId=${item.keywordId }';">${item.keywordName}</h3>
								  		<p class="fz13">还有<font class="orange500">${item.programdaynum}天</font>到期 <a class="orange500" href="<%=njxBasePath %>/view/keyword/goList.shtml?kw.keywordId=${item.keywordId}">去查看></a></p>
								  	</div>
								  	<div style="margin:0px">
								  		<p class="mb10">
								  			<a href="<%=njxBasePath %>/view/keyword/goList.shtml?warningFlag=2&kw.keywordId=${item.keywordId }" class="mb5 fz17 orange500">预警 <i class="iconfont">&#xe659;</i></a>
								  		</p>
								  		<p>
								  			<a href="<%=njxBasePath %>/view/keyword/goList.shtml?reportFlag=2&kw.keywordId=${item.keywordId }" class="mb5 fz17 orange500">日报 <i class="iconfont">&#xe659;</i></a>
								  		</p>
						  			</div>
								  </li>	
							    </ul>  
							   </c:forEach>
					    </s:if>
					    <s:else>
					    	<div class="text-center">
							  <p><img src="<%=staticResourcePathH5%>/images/home/icon-nodata.png" width="35%"/></p>
							  <p class="fz14">您还没有设置监测方案</p>
							  <p class="fz14 mt20 mb20">
								  <a class="orange500 mr15" onclick="javascript:location.href = '<%=njxBasePath %>/goDemoList.shtml?kw.keywordId=6341570';">查看案例</a>
								  <a class="orange500" onclick="<s:if test = 'noKeywordCount==0'>javascript:location.href ='<%=njxBasePath %>/keywordInfo.shtml';</s:if>
												<s:else>javascript:location.href ='<%=njxBasePath %>/view/usercenter/indexFirst.shtml';</s:else>">新建监测</a>
							  </p>
						    </div>	
					   </s:else>		 
					</div>
				</div>
			</section>
		<!-- 信息监测 end -->
		</s:if>
		<s:if test="admin != null">
		<!-- 传播分析 start -->
			<section class="wyui-card border_r5 mb30">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz12">传播分析<span class="fc-gray9">（热点事件、营销活动）</span></h3>				 	
				 </div>
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding-h-15 padding-v-10">
						 <div class="wyui-card-pic white">
						 	<div class="wyui-card-header wyui-card-media padding20" style="height:28vw;background-image:url(<%=staticResourcePathH5%>/images/home/yqfx_bg1.jpg)">
						 		<h3 class="wyui-card-title space-between fz20">
						 			<span>竞品分析</span>
<%-- 						 			<a class="white fz12" href="javascript:location.href ='<%=njxBasePath%>/productsAnalysisDemo.shtml?shareCode=fQodqoXdeqGixtI&isApp=1';">查看案例></a> --%>
										<a class="white fz12" href="javascript:location.href ='http://m.wrd.cn/lookShareCodeReport.action?shareCode=fQodqoXdeqGixtI&isApp=1';">查看案例></a>
						 		</h3>
						 		<div class="white fz13 mt15">
						 		<span class="wyui-btn wyui-btn-o mr5" onclick="javascript:location.href ='<%=njxBasePath%>/productsReport.shtml';">报告（${productCount}）</span>
						 		<span class="wyui-btn wyui-btn-o" onclick="javascript:location.href ='<%=njxBasePath%>/productsAnalysis.shtml';">我想分析</span>
						 	</div>
						 	</div>
						 </div>
						 <div class="wyui-card-pic white">
						 	<div class="wyui-card-header wyui-card-media padding20" style="height:28vw;background-image:url(<%=staticResourcePathH5%>/images/home/yqfx_bg2.jpg)">
						 		<h3 class="wyui-card-title space-between fz20">
						 			<span>全网事件分析</span>
						 			<a class="white fz12" onclick="javascript:location.href ='http://h5.51wyq.cn/view/eventAnalysis1/analysisPreviewDemo.shtml?tickets=CTjV50Dct6qV0EAn_pZvrGWVFEDDISYY';">查看案例></a>
						 		</h3>
						 		<div class="white fz13 mt15">
						 		<span class="wyui-btn wyui-btn-o mr5" onclick="javascript:location.href ='<%=njxBasePath %>/view/eventAnalysis1/completeList.shtml';">报告（${eventCount}）</span>
						 		<span class="wyui-btn wyui-btn-o" onclick="javascript:location.href ='<%=njxBasePath%>/view/eventAnalysis/createAnalysis.shtml?createType=1';">我想分析</span>
						 	</div>
						 	</div>
						 </div>
						 <div class="wyui-card-pic white">
						 	<div class="wyui-card-header wyui-card-media padding20" style="height:28vw;background-image:url(<%=staticResourcePathH5%>/images/home/yqfx_bg3.jpg)">
						 		<h3 class="wyui-card-title space-between fz20">
						 			<span>微博事件分析</span>
						 			<a class="white fz12" onclick="javascript:location.href ='http://h5.51wyq.cn/view/weiboEventAnalysis1/analysisPreviewDemo.shtml?tickets=CTjV50Dct6pwRwmgkB_2JtOFHMDTQIlK';">查看案例></a>
						 		</h3>
						 		<div class="white fz13 mt15">
						 		<span class="wyui-btn wyui-btn-o mr5" onclick="javascript:location.href ='<%=njxBasePath %>/view/weiboEventAnalysis1/weiboCompleteList.action'">报告（${weiboEventCount}）</span>
						 		<span class="wyui-btn wyui-btn-o" onclick="javascript:location.href ='<%=njxBasePath%>/view/weiboEventAnalysis/createWeiBoAnalysis.shtml?createType=1';">我想分析</span>
						 		</div>
						 	</div>
						 </div>
						 <div class="wyui-card-pic white">
						 	<div class="wyui-card-header wyui-card-media padding20" style="height:28vw;background-image:url(<%=staticResourcePathH5%>/images/home/yqfx_bg4.jpg)">
						 		<h3 class="wyui-card-title space-between fz20">
						 			<span>微博传播效果分析</span>
						 			<a class="white fz12" onclick="demo()">查看案例></a>
						 		</h3>
						 		<div class="white fz13 mt15">
						 			<!-- onclick="location.href= njxBasePath + '/i/singleWeiboAnalysis/history.shtml'" -->
								 	<span class="wyui-btn wyui-btn-o mr5" onclick="location.href= njxBasePath + '/i/singleWeiboAnalysis/history'">报告（${singleCount}）</span>
							 		<span class="wyui-btn wyui-btn-o" onclick="location.href= njxBasePath + '/weiboAnalysis/weiboAnalysisIndex'">我想分析</span>
						 		</div>
						 	</div>
						 </div>
					</div>
				</div>
			</section>
		<!-- 传播分析 end -->
		</s:if>
		<s:else>
			<!-- 其他功能 start -->
			<section class="wyui-card border_r5 mb30">
				 <div class="wyui-card-header">
				 	<h3 class="wyui-card-title line_right fz14">其他功能</h3>
				 </div>
				 <div class="wyui-card-content">
					<div class="wyui-card-content-inner padding-h-15 padding-v-10">						  						
					<ul class="wyui-table-view wyui-space-between">
						  <li class="wyui-table-view-cell">
						  	<div style="margin:0px">
						  		<h3 class="fz16 mb10 fw500">信息监测</h3>
						  		<p class="fz13">品牌监测、预警、日报</p>
						  	</div>
						  	<img src="<%=staticResourcePathH5%>/images/home/icon-other1.png" class="icon_100"/>
						  </li>
						  <li class="wyui-table-view-cell">
						  	<div style="margin:0px">
						  		<h3 class="fz16 mb10 fw500">传播分析</h3>
						  		<p class="fz13">热点事件、营销活动</p> 
						  	</div>
						  	<img src="<%=staticResourcePathH5%>/images/home/icon-other2.png" class="icon_100"/>
						  </li>
					    </ul>  						 
					</div>
				</div>
			</section>
		<!-- 其他功能 end -->
		</s:else>
		<!-- 客户端下载 start -->
			<section class="bg-white padding-v-25">				  
				 <ul class="wyui-table-view wyui-grid-view wyui-grid-9">
					<li class="wyui-table-view-cell wyui-media wyui-col-xs-6 text-center border_b0">
						<div class="mb10"><i class="iconfont fz30 orange500">&#xe6d3;</i></div>
						<h3 class="fw500 fz15">微热点电脑版</h3>
						<p class="fz12 fc-gray9">功能更多 操作更方便 更灵活</p>
						<p class="fz14 orange500"><a class="orange500" href="">wrd.cn</a></p>
					</li>
					<li class="wyui-table-view-cell wyui-media wyui-col-xs-6 text-center border_b0">
						<div onclick="javascript:location.href ='<%=njxBasePath%>/indexLocal.shtml';" class="mb10"><i class="iconfont fz30 orange500">&#xe663;</i></div>
						<h3 class="fw500 fz15">微热点客户端</h3>
						<p class="fz12 fc-gray9">随时随地 掌握热点信息</p>
						<p class="fz14"><a class="orange500" href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq">立即下载</a></p>
					</li>
				</ul>
			</section>
		<!-- 客户端下载 end -->	
		
		<section>
			<img src="<%=staticResourcePathH5%>/images/home/page_footer.png" width="100%"/>
		</section>
			
	</div>
	<div class="zhezhao" style="display: none;"></div>
	<!-- <div id="searchNotice"class="dcon" onClick="noticeClick();">
		<p>情绪地图、热度指数、一搜便知</p>
		<p style="text-align:right;text-decoration:underline;margin-top:-30px;margin-right:10px;">我知道了</p>
	</div> -->
<%-- <%@ include file="../pay/productPackage.jsp" %> --%>
<%@ include file="../../buttom.jsp" %>
<script src="<%=staticResourcePathH5%>/js/home/clipboard.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=staticResourcePathH5%>/js/home/switchery.min.css"/>
<script src="<%=staticResourcePathH5%>/js/home/switchery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=staticResourcePathH5 %>/js/searchjs.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<%-- <script src="<%=staticResourcePathH5 %>/js/addtohomescreen.min.js?v=<%=SYSTEM_INIT_TIME %>" charset="utf-8"></script> --%>
<script type="text/javascript">
			  var switchery = Array.prototype.slice.call(document.querySelectorAll('[data-plugin="switchery"]'));
                 switchery.forEach(function(html) {
                   var switchery = new Switchery(html, { color: '#53d769' });
                });
</script>
<script>/* addToHomescreen(); */saveOperateLog('首页','1004');
function message(){
	swal({
		title:"<div class='lxkfdiv'><ul><li><i class='iconfont icon-youxiang'></i></li><li><p>邮箱</p><p><a href='mailto:cs@mail.wyq.sina.cn'>cs@mail.wyq.sina.cn</a></p></li></ul><ul><li><i class='iconfont icon-qq'></i></li><li><p>企业QQ</p><p><a>3002432217</a></p></li></ul><ul><li><i class='iconfont icon-lianxidianhua'></i></li><li><p>电话</p><p><a href='tel:400-600-7505'>400-600-7505</a></p></li></ul></div>",
		confirmButtonText: "关闭"		
	});
	$(".showSweetAlert").css("marginTop","-200px");
	var height = $("#imgid").height(),
	width = $("#imgid").width();
	console.log($("#imgid").height());
	console.log($("#imgid").width());
	$("area").prop("coords","0,"+parseInt(height*0.66)+","+width+","+height);
}
function copyText(type){
	if(type ==1){
		  var contat = "cs@mail.wyq.sina.com";
		    window.clipboardData.setData('Text', contat); 
		    if(window.clipboardData.getData('text')==''){ 
		    	 alert("内容没有复制到剪贴板！"); 
		    }else{ 
		       alert("内容已经复制到剪贴板！"); 
		    } 
	}
}
function demo() {
	createForm(actionBase + '/ui/singleWeiboAnalysis/demo.shtml', 'POST', '_self', null);
}

 /*  setTimeout(function(){//定时器 
	$("#searchNotice").fadeOut(3000);//将图片的display属性设置为none
	},
	5000);//设置三千毫秒即3秒  */
$(function(){
	$("#keywordAll").on("click",function(){
		$(".home-list1 ul").slideDown();
	});
	
	var myDate=new Date();
	var startDate=new Date();
	startDate.setDate(8);
	startDate.setHours(18,40,0,0);
	var endDate=new Date();
	endDate.setDate(9);
	endDate.setHours(23,59,0,0);
	/* if(myDate > startDate && myDate < endDate){
		swal({
			title:"《来自微热点的一封公告信》",
			text:"<p style = 'text-align:left;font-size:13px;'>亲爱的微友们：</p><p style = 'text-align:left;text-indent:26px;font-size:13px;'>热度指数与情绪地图升级版上线！</p><p style = 'text-align:left;text-indent:26px;font-size:13px;'> 感谢大家对微热点的大力支持！</p>",
			//confirmButtonText: "关闭"		
		});
		$(".showSweetAlert").css("marginTop","-180px");
	} */

})
/* $('.dcon').addClass('animated bounceInDown'); */
/* window.onload =function(){
var res = document.cookie.indexOf("nameNotice");

if(res<0){
var oDate =new Date();
document.cookie ="nameNotice=zheng;";
if(window.screen.width<=320){
	 $("#searchNotice").animate({top:'185px'},1000);
}else{
	 $("#searchNotice").animate({top:'200px'},1000);
}
}
}
 	function noticeClick(){
	$("#searchNotice").fadeOut(1500);
	}  */
</script>
<script type="text/javascript">
function keyup_submit(e){
	 var evt = window.event || e; 
	    if (evt.keyCode == 13){
	      //回车事件
	    	$.ajax({url:njxBasePath+"/view/search/doSearchShareCode.shtml",
				 cache:false,
				 async:false,
				 success:function(result){
					 if(result.status){
						 $(".wy-main-tab li").each(function(index, element) {
						    if($(this).hasClass("active")){
						    	if($(this).attr("index") ==1){
						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#1");
									$("form[name='searchForm']").submit();
						    	}else if($(this).attr("index") ==2){
						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#9");
// 						    		window.location.hash="#9"; 
									 $("form[name='searchForm']").submit();
						    	}
							}
						});
					 }
			 }});
	   }
	 }
	document.body.addEventListener('touchstart',function(){},false);
		
	//标题栏说明文字显示隐藏
	$(".wyui-tipGroup .wyui-tip-showBtn").on("click", function() {
		$(this).parents(".wyui-tipGroup").find(".wyui-tipinfo").toggle(300)
	});
	$(".wyui-tipinfo .close").on("click", function() {
		$(this).parents(".wyui-tipinfo").hide(300)
	});
	
	//搜索框分类搜索
	$(".wy-main-tab li").on("click",function() {
		$(this).addClass("active").siblings().removeClass("active");
		
		if($(this).attr("index") ==1){
			$('#searchKeyword').attr("placeholder",'搜索人名、企业名、品牌名或事件关键词');
			
		}else{
			$('#searchKeyword').attr("placeholder",'搜索人名、企业名、品牌名或事件关键词');
		}
	})
	var searchFlag=false;
	var originalHeight=document.documentElement.clientHeight ||document.body.clientHeight;
		window.onresize=function(){
		    //键盘弹起与隐藏都会引起窗口的高度发生变化
		       var resizeHeight=document.documentElement.clientHeight || document.body.clientHeight;
		        if(resizeHeight-0<originalHeight-0){
		         //当软键盘弹起，在此处操作
		         }else{
		         //当软键盘收起，在此处操作
		         	setTimeout(() => {
		         		if(searchFlag){
		         			$(".wy-main-tab li").each(function(index, element) {
  	  						    if($(this).hasClass("active")){
  	  						    	if($(this).attr("index") ==1){
  	  						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchKeyword_value="+searchKeyword_value+"&searchShareCode="+result.obj+"#1");
  	  									$("form[name='searchForm']").submit();
  	  						    	}else if($(this).attr("index") ==2){
  	  						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchKeyword_value="+searchKeyword_value+"&searchShareCode="+result.obj+"#9");
//  	   						    		window.location.hash="#9"; 
  	  									 $("form[name='searchForm']").submit();
  	  						    	}
  	  							}
  	  						});
		         			searchFlag=false;
		         		}else{
		         			window.scroll(0, 0);//失焦后强制让页面归位
		         		}
			      	//当焦点在弹出层的输入框之间切换时先不归位
			          
			      	}, 300);
		         }
		}
		var u = navigator.userAgent;
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		
	    document.body.addEventListener('focusout', () => {
	      //软键盘收起的事件处理
	      if(isiOS){
	    	  setTimeout(() => {
		      	//当焦点在弹出层的输入框之间切换时先不归位
	    		  if(searchFlag){
	         			$(".wy-main-tab li").each(function(index, element) {
  						    if($(this).hasClass("active")){
  						    	if($(this).attr("index") ==1){
  						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchKeyword_value="+searchKeyword_value+"&searchShareCode="+result.obj+"#1");
  									$("form[name='searchForm']").submit();
  						    	}else if($(this).attr("index") ==2){
  						    		$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchKeyword_value="+searchKeyword_value+"&searchShareCode="+result.obj+"#9");
//   						    		window.location.hash="#9"; 
  									 $("form[name='searchForm']").submit();
  						    	}
  							}
  						});
	         			searchFlag=false;
	         		}else{
	         			window.scroll(0, 0);//失焦后强制让页面归位
	         		}
		          
		      }, 300);
	      }
	      
	    });
</script>
</body>
</html>