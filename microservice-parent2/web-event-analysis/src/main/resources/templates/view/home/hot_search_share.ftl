
<#include "../../top.ftl" >
<link rel="stylesheet" href="${staticResourcePathH5}/css/report2.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/popModal.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/common.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hotsearch.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hot.css?v=${SYSTEM_INIT_TIME}" />
<script type="text/javascript" src="${staticResourcePathH5}/js/utils/utils.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/gauge.js?v=${SYSTEM_INIT_TIME}"></script>
</head>
<body>
<div class="sperate-line"></div>
<div data-ng-controller="initController">
	<form action="${njxBasePath}/view/hotSearch/goHotSearch.shtml?heatShareCode=${heatShareCode}"
		  name="searchForm" method="post">
		<section class="heard heard-yellow padding8 rel search-section">
			<div class="searchH searchH1 rel">
				<input id="searchKeyword" name="searchKeyword" value="${searchKeyword}">
				<input id="searchKeyword1" class="comWord" maxlength="20"value="${searchKeyword1}">
				<input id="keyword1" name="keyword1" type="hidden" value="${keyword1}">
				<input id="filterKeyword1" name="filterKeyword1" type="hidden" value="${filterKeyword1}">
				<input id="categoryId" name="categoryId" type="hidden" value="${categoryId}">
				<input id="type" name="type" type="hidden" value="${type}">
				<input id="adminId" value="${admin.userId }" type="hidden">
				<input id= "shareCode" value="${shareCode }" type="hidden"/>
				<input id= "platform" name="platform" value="${platform }" type="hidden"/>
				<input id= "message" name="message" value="${message }" type="hidden"/>
				<input id="date" value="${date}" type="hidden" value="${date}">
				<input id="startTime" value="${startTime}" type="hidden" value="${startTime}">
				<input id="endTime" value="${endTime}" type="hidden" value="${endTime}">
			</div> gb
		</section>
		<input type="hidden" name="date" value="24" id="hotsearchdate" />

		<#if message == "" >
			<section id="timetools" class="section timetools">
				<div class="hothead1">
					<#if shareCode == "" >
						<div>
							<ul style="border-radius: 3px;width:100%;overflow: hidden;color: #fff;border:solid 1px #fff;">
								<li style="<s:if test='date == 24'>background: rgba(73, 177, 133, 1);color:#fff;</s:if><s:else>background: rgba(228, 228, 228, 1);color:#333333;</s:else> font-size:14px;float: left;width: 50%;line-height: 28px;text-align:center;"
									ng-click="goSearch($event,24)">24小时</li>
								<li style="<s:if test='date == 72'>background: rgba(73, 177, 133, 1);color:#fff;</s:if><s:else>background: rgba(228, 228, 228, 1);color:#333333;</s:else>font-size:14px;float: right;width: 50%;line-height: 28px;text-align:center;"
									ng-click="goSearch($event,72)">72小时</li>
							</ul>
						</div>
					</#if>
					<p style="font-size: 12px; text-align: right;">
						数据时间 ${startTime}～${endTime}
					</p>
				</div>
			</section>
		</#if>

		<section class="section second-section">
			<#if message == "">
				<header class="hothead">
					<i class="xi"></i>
					<div style="float: left;">热度指数</div>
					<div class="tit titlBar">
						<i class="iconfont icon-help"></i>
					</div>
					<div class="tipinfo showing">
						<div class="tiparro">
							<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
									class="icon-remove close"></i>
						</div>
						<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词的全网热度指数值。</div>
					</div>
				</header>
				<article class="article">
					<div id="circle1" class="echarts" data-type="gauge" style="height: 300px;">
						<div class="spinner1">
							<div class="spinner-container container1">
								<div class="circle1 circle-item"></div>
								<div class="circle2 circle-item"></div>
								<div class="circle3 circle-item"></div>
								<div class="circle4 circle-item"></div>
							</div>
							<div class="spinner-container container2">
								<div class="circle1 circle-item"></div>
								<div class="circle2 circle-item"></div>
								<div class="circle3 circle-item"></div>
								<div class="circle4 circle-item"></div>
							</div>
							<div class="spinner-container container3">
								<div class="circle1 circle-item"></div>
								<div class="circle2 circle-item"></div>
								<div class="circle3 circle-item"></div>
								<div class="circle4 circle-item"></div>
							</div>
						</div>
					</div>
					<div class="website" style="position: absolute;top:270px;right:5px;">@微热点（wrd.cn）</div>
				</article>
			</#if>
		</section>
	</form>

	<#if message != "" && message != null >
		<div class="show-msg">
			<img
					src="${staticResourcePathH5}/images/group.png?v=${SYSTEM_INIT_TIME}">
			<p>
				${message}
			</p>
		</div>
	<#else>
		<section class="section">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">全网声量</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
					</div>
					<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词的全网信息数量。</div>
				</div>
			</header>
			<article class="article">
				<!--全网声量-->
				<div class="whole-body" style="height: 300px;">
					<div class="counter counter-lg">
							<span class="counter-number fc_dark_grey-300"><li data-ng-if="$index ==0" data-ng-repeat="hot in hots" style="width: 100%;">
									<ul>
										<li data-ng-bind="hot.numberCustom"></li>
									</ul>
								</li>
								</span>
						<p class="mt10"><img src="${staticResourcePathH5}/images/home/tongji_grey.svg?v=${SYSTEM_INIT_TIME}" width="100%" /></p>
					</div>
				</div>
				<div class="website" style="position: absolute;top:270px;right:5px;">@微热点（wrd.cn）</div>

			</article>
		</section>

		<section class="section trend-module">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">热度指数趋势</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
								class="icon-remove close"></i>
					</div>
					<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词分时段的热度值变化走势。</div>
				</div>
			</header>
			<article class="article">
				<div id="hotTrends" class="echarts" data-type="line">
					<div class="spinner1">
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
				</div>
				<div class="website" style="position: absolute;top:320px;right:5px;">@微热点（wrd.cn）</div>
			</article>
		</section>

		<section class="section" id="hotMessage">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">热门信息</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
					</div>
					<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词的热门信息（相似声量最多）TOP5，点击标题，即可跳转到原文，查看详情。注：对于微博数据，系统只展示原微博内容（不含转发内容），因此可能会有展示内容中不包含关键字的情况，如果是非微博数据，则会出现标题不包含关键词、但正文包含关键词的情形。</div>
				</div>
			</header>
			<article class="article">
				<div  style="padding-bottom: 25px;">
					<table id="hotMessageDivId" border="0" cellspacing="0" cellpadding="0" class="table table2 table3 table-striped" width="100%" style="table-layout: fixed;">
					</table>
				</div>
				<div class="website" style="position: absolute;bottom: 5px;right:5px;">@微热点（wrd.cn）</div>
			</article>
		</section>

		<section class="section">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">关键词云</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
								class="icon-remove close"></i>
					</div>
					<div class="tipcont">该数据为您显示：<br>利用自然语义分析法，对事件、人物、品牌、地域中所提及的关键词进行分词聚合，呈现出被提及次数最多的关键词；字号越大的词组，被提及次数越多。</div>
				</div>
			</header>
			<article class="article">
				<div id="weiBoWordClouds" class="weiBoWordClouds">
					<div id = "weiBoWordCloud" class="weiBoWordCloud echarts" data-type="wordCloud">
						<div class="spinner1">
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
					</div>
				</div>
				<div class="website" style="position: absolute;top:320px;right:5px;">@微热点（wrd.cn）</div>
				<div class="span5 borderBoxLine chartBox">
					<div class="descriptionCon">
						<p id="weiBoWordCloud_msg" class="hot-result-msg"></p>
					</div>
				</div>
			</article>
		</section>

		<section class="section">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">关联词</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
								class="icon-remove close"></i>
					</div>
					<div class="tipcont">该数据为您显示：<br>通过系统自动运算找出事件核心词，并计算出与核心词同时出现频次最高的关联词，图中心位置为核心词，其余为关联词，圈层颜色的由深入浅代表关联程度由大到小，词汇圆圈的大小代表声量的多少。</div>
				</div>
			</header>
			<article class="article">
				<div id="relatedTerms" class="echarts" data-type="scatter">
					<div class="spinner1">
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
				</div>
				<div class="website">@微热点（wrd.cn）</div>
				<div class="span5 borderBoxLine chartBox">
					<div class="descriptionCon">
						<p id="relatedTerms_msg" class="hot-result-msg"></p>
					</div>
				</div>
			</article>
		</section>
		<section style="margin-top: -10px;width: 100%; background-color: #ffffff;display: flex;justify-content: space-between;align-items: center;">
			<img src="${staticResourcePathH5}/images/home/page_footer.png" width="100%" />
		</section>
	</#if>
</div>
<span id="back-to-top" style="display: none;">返回顶部</span>
<script type="text/javascript">heatNUm=0,document.title = "${searchKeyword1}-微热点-热度查询"</script>
<script src="${staticResourcePathH5}/js/searchjs.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/angular/angular-bigpipe.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/angular/angular.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/echarts/echarts3.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/echarts/dist/wordcloud.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/echarts/dist/map/china.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/echarts/echarts3-liquidfill.min.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/echarts/echarts3-component-new-share.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/angular/searchAngular-new.js?v=${SYSTEM_INIT_TIME}"></script>

<#include "../../buttom.ftl" >

<script>
	$(function(){

		var searchKeyword1 = $("#searchKeyword").val();
		var keyword1 = $("#keyword1").val();
		var filterKeyword1 = $("#filterKeyword1").val();
		var shareCode = $("#shareCode").val();
		var date = $("#date").val();
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();

		$.post(njxBasePath+'/doStatAndLineNewV4',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("ratioCustom",data.obj.ratioCustom);
			bigpipe.set("doHeatValue",data.obj.doHeatValue);
			bigpipe.set("doEventTrends2",data.obj.doEventTrend2);
			bigpipe.set("date",data.obj.date);
		});
		$.post(njxBasePath+'/doWordcloudV3',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doWordcloud",data);
		});

		$.post(njxBasePath+'/doRelatedTermsV3',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doRelatedTerms",data);
		});

		$.post(njxBasePath+'/doHotMessage',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doHotMessage",data);
		});
	})
</script>


<script type="text/javascript">saveOperateLog('热度搜索','2003');
	$(function(){
		var searchKeyword1Val = $("#searchKeyword1").val();
		var url = location.href;
		if(url.split("wb.wyq.cn").length > 1){
			url = url.replace("wb.wyq.cn","m.wrd.cn");
		}
		jiathis_config={
			title:"一起来比比热度指数吧~${searchKeyword1}的热度指数在"+heatNUm+",快戳",
			summary:"",
			url:url,
			pic:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg"
		};
		$(".jiathis_button_tsina").on("mousedown",function(){
			jiathis_config.title = "[来自@微热点]#热度比拼#，"+searchKeyword1Val+"的热度指数在"+heatNUm+"之间,快戳";
			jiathis_config.summary = " ";
		});
		$(".jiathis_button_cqq").on("mousedown",function(){
			jiathis_config.title = "一起来比比热度指数吧~";
			jiathis_config.summary = searchKeyword1Val+"的热度指数在"+heatNUm+"之间,快戳";
		});
		$(".jiathis_button_qzone").on("mousedown",function(){
			jiathis_config.title = "一起来比比热度指数吧~"+searchKeyword1Val+"的热度指数在"+heatNUm+"之间,快戳";
			jiathis_config.summary = "";
		});

		//说明文字显示隐藏
		$(".titlBar .icon-help").on("click",function(){
			$(this).parents(".titlBar").next(".tipinfo").toggle(300)
		});
		$(".tipinfo .close").on("click",function(){
			$(this).parents(".tipinfo").hide(300)
		});
	});
</script>
<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
<script type='text/javascript' src='${staticResourcePathH5}/js/weixinNewConfig.js?v=${SYSTEM_INIT_TIME}'></script>
<script>
	$(function(){
		weixinLinkShare({wxAuthUrl:njxBasePath+'/report/getWeixinConfig.action',
			title:"${searchKeyword}-微热点-热度查询",desc:"微热点，社会化大数据应用平台",
			imgUrl:staticResourcePathH5+"/images/fenxiang/weibowyq-icon300.jpg",
			link : location.href.split('#')[0]
		});
	})
</script>

<script>
	function hotMessageClick(href){
		if(href!="" || href.length>0){
			window.open(href,"_blank");
		}
	}
	function getParams(){
		var params = {
			'userTag' : $("#adminId").val(),
			'keyword' : $("#keyword1").val(),
			'filterKeyword' : $("#filterKeyword1").val(),
			'categoryId' : $("#categoryId").val(),
			'categoryType' : $("#categoryType").val(),
			'date' : "${date}",
			'shareCode' : $("#shareCode").val(),
			'platform' :  $("#platform").val()
		};
		return params;
	}

</script>
</body>
</html>