
<#include "../../top.ftl" >
<link rel="stylesheet" href="${staticResourcePathH5}/css/report2.css?v=<${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/popModal.css?v=<${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/common.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hotsearch.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/hot.css?v=${SYSTEM_INIT_TIME}" />
<link rel="stylesheet" href="${njxBasePath}/css/weiboMood/style.css?v=${SYSTEM_INIT_TIME}" />
<script src="${staticResourcePathH5}/js/searchjs.js?v=${SYSTEM_INIT_TIME}"></script>
<script type="text/javascript" src="${staticResourcePathH5}/js/gauge.js?v=${SYSTEM_INIT_TIME}"></script>
</head>
<body>
<div class="sperate-line"></div>
<div data-ng-controller="initController">
	<form id = "searchForm"
		  action="${njxBasePath}/view/opinionsAnalysis/opinionsAnalysisResult.shtml?searchShareCode=${searchShareCode}"
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
			</div>
		</section>
		<input type="hidden" name="date" value="24" id="hotsearchdate" />
		<#if message == "">
			<section id="timetools" class="section timetools">
				<div class="hothead1">
					<#if shareCode == "">
						<div>
							<ul id = "timtes" style="border-radius: 3px;width:100%;overflow: hidden;color: #fff;border:solid 1px #fff;">
								<li style="<s:if test='date == 24'>background: rgba(73, 177, 133, 1);color:#fff;</s:if><s:else>background: rgba(228, 228, 228, 1);color:#333333;</s:else> font-size:14px;float: left;width: 50%;line-height: 28px;text-align:center;" onclick = "goSearch1(this,24)">24小时</li>
								<li style="<s:if test='date == 72'>background: rgba(73, 177, 133, 1);color:#fff;</s:if><s:else>background: rgba(228, 228, 228, 1);color:#333333;</s:else>font-size:14px;float: right;width: 50%;line-height: 28px;text-align:center;" onclick = "goSearch1(this,72)">72小时</li>
							</ul>
						</div>
					</#if>
					<p style="font-size: 12px; text-align: right;">
						数据时间 ${startTime}～${endTime}
					</p>
				</div>
			</section>
		</#if>
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
				<div style="float: left;">媒体友好度</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i
								class="icon-remove close"></i>
					</div>
					<div class="tipcont">在指定时间范围内，该关键词在全网的非敏感信息的总占比情况。</div>
				</div>
			</header>
			<article class="article">
				<div class="mediaFriendliness align_c">
					<div class="mediaSource yellow">
						<ul>
							<li><p></p><p></p></li>
							<li><p></p><p></p></li>
							<li><p></p><p></p></li>
							<li><p></p><p></p></li>
							<li><p></p><p></p></li>
						</ul>
					</div>
					<div class="mediaCircle yellow">
						<div class="echarts mediaFriendlyChart" data-type="liquidFill" id="mediaFriendlyChart">
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
				</div>
				<div class="website" style="position: absolute;top:290px;right:5px;">@微热点（wrd.cn）</div>
			</article>
		</section>
		<section class="section">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">用户情感洞察</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
					</div>
					<div class="tipcont">提及该关键词时，在微博数据中，用户属性（性别、认证类型、粉丝数量分布、转发层级和兴趣标签）和情感维度（敏感和非敏感）的关联统计分析。</div>
				</div>
			</header>
			<article class="article">
				<div style="text-align: center;">
					<span class="ml20 f_c31 fz14"><i class="icon-square fc-orange fz14"></i> 非敏感</span>
					<span class="ml20 f_c32 fz14"><i class="icon-square fc_blue fz14"></i> 敏感</span>
				</div>
				<div style="position: relative;">
					<p class="echartTitle"><em></em>性别</p>
					<div class="echartSex">
						<div id="emotionBar1" style="height: 300px;">

						</div>
						<div class="echartSex-line">
							<div class="echartSex-lf">
								<img src="${staticResourcePathH5}/images/newTools/icon-nan.png" />
								<p>男</p>
							</div>
							<div class="echartSex-rg">
								<img src="${staticResourcePathH5}/images/newTools/icon-nv.png" />
								<p>女</p>
							</div>
						</div>
					</div>
					<div class="website" style="position: absolute;bottom: 10px;right:5px;">@微热点（wrd.cn）</div>
				</div>
				<div style="position: relative;">
					<p class="echartTitle"><em></em>用户认证类型</p>
					<div id="emotionBar2" style="height: 300px;padding:10px">
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
					<div class="website" style="position: absolute;bottom: 10px;right:5px;">@微热点（wrd.cn）</div>
				</div>
				<div style="position: relative;">
					<p class="echartTitle"><em></em>粉丝数量分布</p>
					<div id="emotionBar3" style="height: 300px;padding:10px">
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
					<div class="website" style="position: absolute;right:5px;">@微热点（wrd.cn）</div>
				</div>
				<div style="position: relative;">
					<p class="echartTitle"><em></em>转发层级</p>
					<div class="pieChar">
						<div id="emotionBar4" style="height: 600px;">
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
						<ul id="ulemotionBar4">
						</ul>
					</div>
					<div class="website" style="position: absolute;bottom: 10px;right:5px;">@微热点（wrd.cn）</div>
				</div>
				<div style="position: relative;">
					<p class="echartTitle"><em></em>兴趣标签</p>
					<div>
						<div class="positive-lf">
							<p style="font-size: 14px;">非敏感言论用户兴趣标签</p>
							<div class="positive-con" id="interestWordCloud-zm">

							</div>
						</div>
						<div class="positive-right">
							<p style="font-size: 14px;">敏感言论用户兴趣标签</p>
							<div class="positive-con" id="interestWordCloud-fm">

							</div>
						</div>
					</div>
					<div class="website" style="position: absolute;bottom: 10px;right:5px;">@微热点（wrd.cn）</div>
				</div>
			</article>
		</section>
		<section class="section">
			<header class="hothead">
				<i class="xi"></i>
				<div style="float: left;">口碑热词</div>
				<div class="tit titlBar">
					<i class="iconfont icon-help"></i>
				</div>
				<div class="tipinfo showing">
					<div class="tiparro">
						<i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i> <i class="icon-remove close"></i>
					</div>
					<div class="tipcont">在指定时间范围内，通过对全网提及该关键词的全部非敏感信息和敏感信息进行分词统计，列出其中提及量最高的词，最多展示9个高频词。</div>
				</div>
			</header>
			<article class="article" style="padding-bottom: 20px;">
				<div id="mapChart" style="text-align: center;margin-top: 10px;">
					<p class="terms-bg">非敏感高频词</p>
					<div id="zmHotWord">
						<ul class="terms square  clearfix" id="zmHotWord-ul">
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
						</ul>
					</div>
				</div>
				<div id="mapDisChart" style="text-align: center;">
					<p class="terms-bg terms-bg2">敏感高频词</p>
					<div id="fmHotWord">
						<ul class="terms conside clearfix" id="fmHotWord-ul">
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
						</ul>
					</div>
				</div>
				<div class="website" style="position: absolute;bottom: 5px;right:5px;">@微热点（wrd.cn）</div>

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
<script src="${staticResourcePathH5}/js/echarts/echarts3-component-new.js?v=${SYSTEM_INIT_TIME}" charset="utf-8"></script>
<script src="${staticResourcePathH5}/js/angular/opinionsAngular-newshare.js?v=${SYSTEM_INIT_TIME}"></script>
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

		$.post(njxBasePath+'/doMediaFriendV3',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doMediaFriend",data);
		});

		$.post(njxBasePath+'/doEmotionSexV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doEmotionSex",data);
		});

		$.post(njxBasePath+'/doEmotionTypeV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doEmotionType",data);
		});


		$.post(njxBasePath+'/doEmotionFansV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doEmotionFans",data);
		});

		$.post(njxBasePath+'/doEmotionLevelV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doEmotionLevel",data);
		});
		$.post(njxBasePath+'/doEmotionHobbyV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doEmotionHobby",data);
		});

		$.post(njxBasePath+'/doZMHotWordV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doZMHotWord",data);
		});

		$.post(njxBasePath+'/doFMHotWordV2',{
			date: date,
			searchKeyword1: searchKeyword1,
			keyword1: keyword1,
			filterKeyword1: filterKeyword1,
			shareCode:shareCode,
			startTime:startTime,
			endTime:endTime
		},function(data) {
			bigpipe.set("doFMHotWord",data);
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
			jiathis_config.title = "一起来比比热度指数吧~"+searchKeyword1Val+"的热度指数在"+heatNUm+"之间,快戳";images/shouye/warn.png
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
			'categoryId' : $("#categoryId").val(),
			'categoryType' : $("#categoryType").val(),
			'date' : "${date}",
			'shareCode':$("#shareCode").val()
		};
		return params;
	}
</script>
</body>
</html>