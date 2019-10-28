
<#include "../../top.ftl" >
<link rel="stylesheet" href="${staticResourcePathH5}/css/report2.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/popModal.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/common.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hotsearch.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hot.css?v=${SYSTEM_INIT_TIME}" />
<script type="text/javascript" src="${staticResourcePathH5}/js/gauge.js?v=${SYSTEM_INIT_TIME}"></script>
</head>
<body>
<div class="sperate-line"></div>
<div data-ng-controller="initController">
	<form id = "searchForm"
		  action="${njxBasePath}/view/spreadAnalysis/goSpreadAnalysis.shtml?heatShareCode=${heatShareCode}"
		  name="searchForm" method="post">
		<section class="heard heard-yellow padding8 rel search-section">
			<div class="searchH searchH1 rel">
				<!-- <i class="icon-search"></i> -->
				<input id="searchKeyword" name="searchKeyword" value="${searchKeyword}">
				<input id="searchKeyword1" class="comWord" maxlength="20"value="${searchKeyword1}">
				<input id="keyword1" name="keyword1" type="hidden" value="${keyword1}">
				<input id="filterKeyword1" name="filterKeyword1" type="hidden" value="${filterKeyword1}">
				<input id="categoryId1" name="categoryId1" type="hidden" value="${categoryId}">
				<input id="type1" name="type1" type="hidden" value="${type}">
				<input id="adminId" value="${admin.userId}" type="hidden">
				<input id="shareCode" value="${shareCode}" type="hidden"/>

				<input id="date" value="${date}" type="hidden" value="${date}">
				<input id="startTime" value="${startTime}" type="hidden" value="${startTime}">
				<input id="endTime" value="${endTime}" type="hidden" value="${endTime}">


			</div>
		</section>
		<input type="hidden" name="date" value="24" id="hotsearchdate" />
		<section id="timetools" class="section timetools">
			<div class="hothead1">
				<p style="font-size: 12px; text-align: right;">
					 数据时间 ${startTime}～${endTime}
				</p>
			</div>
		</section>
	</form>
	<section class="section second-section">
		<header class="hothead">
			<i class="xi"></i>
			<div style="float: left;">信息来源</div>
			<div class="tit titlBar">
				<i class="iconfont icon-help"></i>
			</div>
			<div class="tipinfo showing">
				<div class="tiparro">
					<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
				</div>
				<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词的相关信息在原发平台，即微博、客户端、微信、论坛以及网站（新闻、政务、报刊、网站、博客、外媒、视频）上的声量分布情况。</div>
			</div>
		</header>
		<article class="article">

			<div id="mediaActivity" style="height: 300px;">
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
			<div class="website" style="position: absolute;bottom:0px;right:5px;">@微热点（wrd.cn）</div>

		</article>
	</section>

	<section class="section">
		<header class="hothead">
			<i class="xi"></i>
			<div style="float: left;">活跃媒体</div>
			<div class="tit titlBar">
				<i class="iconfont icon-help"></i>
			</div>
			<div class="tipinfo showing">
				<div class="tiparro">
					<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
				</div>
				<div class="tipcont">该数据为您显示：<br>在指定时间范围内，对该关键词提及量最多的媒体TOP8（默认隐去“新浪微博”，点击图例即可恢复显示）。</div>
			</div>
		</header>
		<article class="article">

			<div id="sourceTypeChart2" style="height: 320px; padding-bottom: 10px;">
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

			<div class="website" style="position: absolute;bottom: 6px;right:5px;">@微热点（wrd.cn）</div>

		</article>
	</section>
	<section class="section">
		<header class="hothead">
			<i class="xi"></i>
			<div style="float: left;">声量走势</div>
			<div class="tit titlBar">
				<i class="iconfont icon-help"></i>
			</div>
			<div class="tipinfo showing">
				<div class="tiparro">
					<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
				</div>
				<div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词在各渠道分时段的声量变化走势。</div>
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
	<section class="section">
		<header class="hothead">
			<i class="xi"></i>
			<div style="float: left;">地域分布</div>
			<div class="tit titlBar">
				<i class="iconfont icon-help"></i>
			</div>
			<div class="tipinfo showing">
				<div class="tiparro">
					<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
							class="icon-remove close"></i>
				</div>
				<div class="tipcont">该数据为您显示：<br>在指定时间范围内，按照信息发布者所在地域进行声量统计，展示中国大陆及港澳台地区的声量TOP10。在地图上，仅展示我国34个省级行政区域（包括23个省，5个自治区，4个直辖市，以及香港，澳门2个特别行政区）的声量TOP10。</div>
			</div>
		</header>
		<article class="article">
			<div class="echarts virtualSolution" data-type="map" style="height: 300px;">
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
			<div class="website" style="position: absolute;top:300px;right:5px;">@微热点（wrd.cn）</div>
			<div class="echarts virtualSolutions" data-type="bar">
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
			<div class="website" style="position: absolute;top:615px;right:5px;">@微热点（wrd.cn）</div>
			<!-- <div class="span5 borderBoxLine chartBox">
                <div class="descriptionCon">
                    <p id="virtualSolutions_msg" class="hot-result-msg"></p>
                </div>
            </div> -->
		</article>
	</section>

	<section class="section">
		<header class="hothead">
			<i class="xi"></i>
			<div style="float: left;">热点网民</div>
			<div class="tit titlBar">
				<i class="iconfont icon-help"></i>
			</div>
			<div class="tipinfo showing">
				<div class="tiparro">
					<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
				</div>
				<div class="tipcont">该数据为您显示：<br>在指定时间范围内，对该关键词相关微博转发量最多的TOP8微博用户，点击对应用户，即可跳转到其微博主页，查看详情。</div>
			</div>
		</header>
		<article class="article">
			<div class="networkList" id="hotPeopleDivId">
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
			<div class="website" style="position: absolute;top:312px;right:5px;">@微热点（wrd.cn）</div>
		</article>
	</section>
	<section style="margin-top: -10px;width: 100%; background-color: #ffffff;display: flex;justify-content: space-between;align-items: center;">
		<img src="${staticResourcePathH5}/images/home/page_footer.png" width="100%" />
	</section>
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
<script src="${staticResourcePathH5}/js/angular/spreadAngular-new.js?v=${SYSTEM_INIT_TIME}"></script>
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

		$.post(njxBasePath+'/doInfoFromV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doInfoFrom",data);
		});


		$.post(njxBasePath+'/doActiveMediaV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doActiveMedia",data);
		});

		$.post(njxBasePath+'/doVolumeMapV3',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doVolumeMap",data);
		});

		$.post(njxBasePath+'/doVirtualSolutionV3',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doVirtualSolution",data);
		});

		$.post(njxBasePath+'/doHotPeopleV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doHotPeople",data);
		});
	})
</script>




<script type="text/javascript">saveOperateLog('热度搜索','2003');
	$(function(){
		var searchKeyword1Val = $("#searchKeyword1").val();
		var url = location.href;
		if(url.split("wb.wyq.cn").length > 1){
			url = url.replace("wb.wyq.cn","h5.51wyq.cn");
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
	function getParams(){
		var params = {
			'userTag' : $("#adminId").val(),
			'keyword' : $("#keyword1").val(),
			'filterKeyword' : $("#filterKeyword1").val(),
			'categoryId' : $("#categoryId1").val(),
			'categoryType' : $("#categoryType").val(),
			'date' : "${date}",
			'shareCode':$("#shareCode").val()
		};
		return params;
	}
	function goSearch1(t,date){
		$("#hotsearchdate").val(date);
		$("#searchForm").submit();
	}
</script>
</body>
</html>