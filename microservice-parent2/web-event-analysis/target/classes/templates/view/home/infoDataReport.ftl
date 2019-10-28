<#include "../../init_top.ftl" >
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/style.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/font-icon.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/mui/mui.min.css?v=${SYSTEM_INIT_TIME}"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/less/wui.css" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/newHome/modal.css"/>
<script src="${staticResourcePathH5}/js/newHome/wyrem.js?v=${SYSTEM_INIT_TIME}"></script>
<title>微热点——用大数据洞察传播</title>
<style>
	<style type="text/css">
						  .article-body span{
							  background: transparent !important;
							  color: #FFFFFF !important;
						  }
	.article-body img{
		width: 100% !important;
	}
</style>
</head>

<body class="mainBackground" >
<div class="wui-bar wui-tag" style="background-color: #1B172C;">
	<a href="javascript:history.go(-1)" class="wui-back mui-action-back wui-icon-nav wui-pull-left iconfont icon-fanhui">
	</a>
</div>
<div class="rel wui-content" id="app">
	<!--列表-->
	<section>
		<div class="infoReport">
			<ul>
				<li v-for="(item,index) in bList">
					<a :href="'getBigDataDetail?id='+item.bigReportId">
						<div class="nowrap infoTop-txt" v-text="item.title">陈情令火了 争议也来了陈情令火了 争议也来了</div>
						<p class="font-size-12 color_1">{{item.createTime|formatDate()}}</p>

					</a>
					<i class="iconfont icon-shanchu infoDel" @click="deletReport(item.id,index)"></i>
				</li>
			</ul>
		</div>
	</section>

</div>
<div id="d_loading" class="text-center margin-vertical-10" style="color: #8F9DBA">
	<div class="inline-block w-ball-clip-rotate la-sm v_a_m margin-right-5"><div></div></div><span class="v_a_m">加载中</span>
</div>
<div id="getButtom" class="endline padding-vertical-20">
	<span>我们是有底线的</span>
</div>
<!--弹框删除报告提示-->
<div id="wui-modal-tootips" class="wui-modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="wui-modal-body text-center">
				<a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
				<h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
				<p class="font-size-12 color_1">确定要删除这份报告吗？</p>
			</div>

			<div class="modal-footer clearfix">
				<a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
				<a href="javascript:;" id="dodel" class="btn fontColor_1">确定</a>
			</div>
		</div>
	</div>
</div>
<!--弹框删除报告提示-->
<div id="wui-modal-tootips1" class="wui-modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="wui-modal-body text-center">
				<a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
				<h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
				<p class="font-size-12 color_1" id="mesg">确定要删除这份报告吗？</p>
			</div>
		</div>
	</div>
</div>
<!--弹框微积分不够了-->
<div id="pay-wjf" class="wui-modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="wui-modal-body text-center">
				<a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
				<h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">微积分不够啦</h3>
				<p class="font-size-12 color_1">当前可用微积分为 <span class="fontColor_1">0</span></p>
				<p class="font-size-12 color_1">还需要 <span class="fontColor_1">9999</span>才能购买</p>
			</div>

			<div class="modal-footer clearfix">
				<a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
				<a href="javascript:;" class="btn fontColor_1">去充值</a>
			</div>
		</div>
	</div>
</div>
<!--弹框微积分支付提示-->
<div id="wjf-tootips" class="wui-modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="wui-modal-body text-center">
				<a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
				<h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">温馨提示</h3>
				<p class="font-size-12 color_1">当前可用微积分为 <span class="fontColor_1">28300</span></p>
				<p class="font-size-12 color_1">本次购买需花费  <span class="fontColor_1">9999</span></p>
			</div>

			<div class="modal-footer clearfix">
				<a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
				<a href="javascript:;" class="btn fontColor_1">确认支付</a>
			</div>
		</div>
	</div>
</div>
<!--弹框支付成功-->
<div id="pay-success" class="wui-modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="wui-modal-body text-center">
				<a href="javascript:;" class="close" data-dismiss="modal" aria-label="Close">&times;</a>
				<h3 class="font-size-16 margin-bottom-10" style="color: #333333;font-weight: 600;">支付成功啦</h3>
				<p class="font-size-12 color_1">您的深度报告已经制作成功</p>
				<p class="font-size-12 color_1">可前往“<span class="fontColor_1">我-大数据深度报告</span>”查看</p>
			</div>

			<div class="modal-footer clearfix">
				<a href="javascript:;" class="btn" data-dismiss="modal">取消</a>
				<a href="javascript:;" class="btn fontColor_1">立即查看</a>
			</div>
		</div>
	</div>
</div>

<script src="${staticResourcePathH5}/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/bootstrap.min.js"></script>
<script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/vue/vue.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/jquery/jquery.endless-scroll-1.3.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/goToTop.js"></script>
<script src="${staticResourcePathH5}/js/vue/home/infoDataReport.js"></script>
<script>
	$(function() {
		var goToTopButton = "<div id=\"wui-back-top\">\n" +
				"        <i class=\"iconfont icon-fanhuidingbu1\"></i>\n" +
				"    </div>";
		$("body").append(goToTopButton); //插入按钮的html标签
		if($(window).scrollTop() < 1) {
			$("#wui-back-top").hide(); //滚动条距离顶端的距离小于showDistance是隐藏goToTop按钮
		}
		$("#wui-back-top").goToTop({
			min: 1,
			fadeSpeed: 500
		});
		$("#wui-back-top").on('click', function(e) {
			e.preventDefault();
			$("html,body").animate({
				scrollTop: 0
			}, "slow");

		});
		$(".infoDel").on('click', function(e) {
			$('#wui-modal-tootips').modal('show')
		});

	});
</script>
</body>

</html>