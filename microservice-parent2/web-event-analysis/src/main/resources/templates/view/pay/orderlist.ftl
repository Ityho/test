<#include "../../init_top.ftl" >
<link href="${staticResourcePathH5}/css/mobiscroll.custom-2.5.0.min.css?v=${SYSTEM_INIT_TIME}" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/productBuy/font-icon.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/style.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" href="${staticResourcePathH5}/css/layout20160825.css?v=${SYSTEMINITTIME}" />
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/loading.css" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/tips.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/animation.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/fsgallery.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/fonts-icomoon.css?v=${SYSTEM_INIT_TIME }"/>
<link rel="stylesheet" href="${staticResourcePathH5 }/css/iconfont.css?v=${SYSTEM_INIT_TIME }"/>
<link rel="stylesheet" href="${staticResourcePathH5 }/css/loading.css?v=${SYSTEM_INIT_TIME }" />
<link rel="stylesheet" href="${staticResourcePathH5 }/css/sweetalert.css?v=${SYSTEM_INIT_TIME }">
<link rel="stylesheet" href="http://at.alicdn.com/t/font_189126_38986hdj15ib2o6r.css?v=${SYSTEM_INIT_TIME }">

<script type="text/javascript" charset="utf-8" src="${staticResourcePathH5}/js/vue/moment.js?v=${SYSTEM_INIT_TIME}"></script>
<style>

	.triangle-topleft {
		width: 0;
		height: 0;

		border-top: 23px solid #fc8625;
		border-right: 23px solid transparent;
	}
	.tip-bar{
		font-size: 13px;
		padding: 10px 20px;
		background: #FFEAE8;
		color: #E93A2C;
		position: relative;
		padding-left: 45px;
	}
	.tip-bar i{
		font-size: 14px;
		position: absolute;
		top: 13px;
		left: 20px;
	}

</style>
</head>
<body data-ng-controller = "initController">
<section class="section" id="header-section" style="border-left:solid 6px #fca832;">
	<h2 class="float_l">我的订单</h2>
	<div style="margin-top: 10px;margin-right:20px;" class="float_r">
		<a href="javascript:location.href = '${njxBasePath}/user';" class="">返回</a></div>
</section>
<input id = "loginPlatform" type="hidden" value="${loginPlatform}">
<section class="section" id="category-section" style="height:40px;line-height:40px;position:relative;">
	<div style="width:33%;float:left;text-align:center;" onClick="openCondition(1);">
		<span id="orderStatus">全部</span>
		<img  style="margin-left:15px;height:6px;width:6px;" src="${staticResourcePathH5}/images/pushDown.png">
		<input type="hidden" id="payStatus" value="-1">
	</div>
	<div style="width:33%;float:left;text-align:center;" onClick="openCondition(2);">
		<span id="orderTypes">类别</span>
		<img  style="margin-left:15px;height:6px;width:6px;" src="${staticResourcePathH5}/images/pushDown.png">
		<input type="hidden" id="orderType" value="-1">
	</div>
	<div style="width:33%;float:left;text-align:center;" onClick="openCondition(3);">
		<span>时间</span>
		<img  style="margin-left:15px;height:6px;width:6px;" src="${staticResourcePathH5}/images/pushDown.png">
	</div>
</section>

<section id="showStatus" class="section" style="margin-top:-10px;height:80px;line-height:80px;display:none;">
	<div class="chooseType">
		<div id="pay1" class="inside cur" data-ng-click = "goOrderSearch(1,1)">全部</div>
	</div>
	<div class="chooseType">
		<div id="pay2" class="inside" data-ng-click = "goOrderSearch(1,2)">未支付</div>
	</div>
	<div class="chooseType">
		<div id="pay3" class="inside" data-ng-click = "goOrderSearch(1,3)">已支付</div>
	</div>
	<div class="chooseType">
		<div id="pay4" class="inside" data-ng-click = "goOrderSearch(1,4)">支付失败</div>
	</div>
</section>
<section id="showType"class="section" style="margin-top:-10px;height:80px;line-height:80px;display:none;">
	<div class="chooseType">
		<div id="type1"class="inside cur" data-ng-click = "goOrderSearch(2,1)">全部</div>
	</div>
	<div class="chooseType">
		<div id="type2"class="inside" data-ng-click = "goOrderSearch(2,2)">赠送</div>
	</div>
	<div class="chooseType">
		<div id="type3"class="inside" data-ng-click = "goOrderSearch(2,3)">购买</div>
	</div>
	<div class="chooseType">
		<div id="type4"class="inside" data-ng-click = "goOrderSearch(2,4)">续费</div>
	</div>
</section>
<section id="showTime"class=" orderui section" style="margin-top:-10px;height:80px;line-height:80px;display:none;">
	<ul class="ordertime clearfix" style="border:solid 0px;line-height:80px;">
		<li style="width:75%;"><input id="startDate" class="timeinput" readonly="readonly">&nbsp;~&nbsp;<input id="endDate" class="timeinput" readonly="readonly"></li>
		<li style="width:25%;"><button style="background: #fc8625;width:60px;" data-ng-click = "goOrderSearch()">查询</button></li>
	</ul>
</section>
<section class="orderinfoui section" data-ng-init = "goOrderSearch()" style="width:95%;">
	<div class="orderinfoitem" data-ng-if = "orderlists&&orderlists.length>0" data-ng-repeat = "order in orderlists">
		<div style="position:absolute;right:0px;">
			<div data-ng-bind = "order.packagesInfo[0].packageName+' * ' +order.packagesInfo[0].packageCount" data-ng-if="order.packagesInfo[0].packageCount<10"style="background-image: url(${staticResourcePathH5}/images/side.png?v=${SYSTEM_INIT_TIME});height:240px;background-size:115%;padding-top:5px;background-repeat:no-repeat;word-break:break-all;position:absolute;right:0px;text-align:center;width:20px;color:#FFF;">
			</div>
			<div data-ng-bind = "order.packagesInfo[0].packageName+' * ' +order.packagesInfo[0].packageCount" data-ng-if="order.packagesInfo[0].packageCount>10"style="background-image: url(${staticResourcePathH5}/images/side.png?v=${SYSTEM_INIT_TIME});height:240px;background-size:140%;background-repeat:no-repeat;word-break:break-all;position:absolute;right:0px;text-align:center;width:20px;color:#FFF;">
			</div>
			<div data-ng-bind = "order.packagesInfo[0].packageName+' * ' +order.packagesInfo[0].packagePrice" data-ng-if="order.packagesInfo[0].productPackageId==61"style="background-image: url(${staticResourcePathH5}/images/side.png?v=${SYSTEM_INIT_TIME});height:240px;background-size:140%;background-repeat:no-repeat;word-break:break-all;position:absolute;right:0px;text-align:center;width:20px;color:#FFF;">
			</div>
		</div>
		<h1 style="height:10px;"></h1>
		<ul>
			<li>订单号</li>
			<li data-ng-bind = "order.orderInfo.orderNo"></li>
		</ul>
		<ul>
			<li>下单时间</li>
			<li data-ng-bind = "order.orderInfo.createTimeStr"></li>
<#--			<li data-ng-bind = "moment(order.orderInfo.createTime).format('YYYY-MM-DD HH:mm:ss')"></li>-->

		</ul>
		<ul>
			<li>金额</li>
			<li><span class="f_c6">￥<label data-ng-bind = "order.orderInfo.totalFee + '(微积分'+order.useCreditAmount+')'"></label></span>
				<!--  <span data-ng-if = "order.payInfo.payChannel == '00'">(未知)</span>
                 <span data-ng-if = "order.payInfo.payChannel == '01'">(支付宝)</span>
                 <span data-ng-if = "order.payInfo.payChannel == '02'">(微信)</span>
                 <span data-ng-if = "order.payInfo.payChannel == '03'">(微博)</span> -->
			</li>
		</ul>
		<ul>
			<li>类别</li>
			<li data-ng-if = "order.orderInfo.orderType == '0'">赠送</li>
			<li data-ng-if = "order.orderInfo.orderType == '1'">购买</li>
			<li data-ng-if = "order.orderInfo.orderType == '2'">续费</li>
		</ul>
		<ul>
			<li>订单状态</li>
			<li>
				<span data-ng-if = "order.orderInfo.payStatus == '0'" class="f_c2">未支付</span>
				<span data-ng-if = "order.orderInfo.payStatus == '1'" class="f_c9">已支付</span>
				<span data-ng-if = "order.orderInfo.payStatus == '2'" class="f_c2">支付失败</span>
			</li>
			<li >
				<div  data-ng-if = "order.orderInfo.payStatus  == '0'" >
					<div style="width:50%;text-align:center;float:left;">
						<button class="addBtn" style="width:130px;border-radius:4px;color:#333333;background-color: #fff;border: solid 1px #b7b7b7;" data-ng-if = "order.orderInfo.payStatus == '0'"
								data-ng-click = "cancelOrder(order.orderInfo.orderRecordId)">取消</button>
					</div>
					<div style="width:50%;text-align:center;float:left;">
						<button class="addBtn" style="background-color:#fca832;border-radius:4px;width:130px;border: solid 1px #b7b7b7;" data-ng-if = "order.orderInfo.payStatus  == '0'"
								data-ng-click = "goPay(order.payInfo.payRecordId)">支付</button>
					</div>
				</div>
			</li>
		</ul>
		<h1 style="height:10px;"></h1>
		<div class="line"></div>
	</div>

	<div class="spinner" id="loading_gif">
		<div class="bounce1"></div>
		<div class="bounce2"></div>
		<div class="bounce3"></div>
	</div>
</section>
<div id="modal" onclick="hideModal()" style="background: #000;filter: alpha(opacity=50); /* IE的透明度 */opacity: 0.5;  /* 透明度 */ display: none;position: fixed;top: 45px;left: 0px;width: 100%;height: 100%;overflow:hidden;z-index: 1; /* 此处的图层要大于页面 */display:none;">

</div>
<div id="noresult" style="display:none;text-align:center">
	<img src="${staticResourcePathH5}/images/noresult.jpg">
</div>
<script>
	var njxBasePath="${njxBasePath}";
</script>
<script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/angular/angular.min.js?v=${SYSTEM_INIT_TIME}"></script>
<script src="${staticResourcePathH5}/js/angular/orderAngular.js?v=${SYSTEM_INIT_TIME}" type="text/javascript"></script>
<script src="${staticResourcePathH5}/js/mobiscroll.custom-2.5.0.min.js?v=${SYSTEM_INIT_TIME}" type="text/javascript"></script>
<script src="${staticResourcePathH5}/js/navigate.js?v=${SYSTEM_INIT_TIME}"></script>


<script type="text/javascript">
	$(function(){
		var currYear = (new Date()).getFullYear();
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.defaults = {
			theme: "android-ics",
			mode: "scroller",
			display: "modal",
			lang: "zh",
			monthText: "月",
			dayText: "日",
			yearText: "年",
			hourText: "时",
			minuteText: "分",
			ampmText:"上午/下午",
			setText: '确定',
			cancelText: '取消',
			dateFormat: 'yy-mm-dd',
			startYear:currYear-10,
			endYear:currYear+10
		};
		var optDateTime = $.extend(opt['datetime'], opt['defaults']);
		/* 必须先输入第一个时间 */
		optDateTime = $.extend(optDateTime,{
			onSelect:function(value,inst){
				optDateTime = $.extend(optDateTime,{minDate:new Date(parseInt(value.substring(0,4)),parseInt(value.substring(5,7))-1,parseInt(value.substring(8,10)),parseInt(value.substring(11,13)),parseInt(value.substring(14,16)))});
				$("#endDate").mobiscroll(optDateTime).datetime(optDateTime);
			}
		})
		$("#startDate").mobiscroll(optDateTime).datetime(optDateTime);
	});
	//切换条件(展示)
	function openCondition(type){
		$('body').css("position","fixed");
		var top = $("#header-section").height()+11+$("#category-section").height()+$("#showStatus").height();
		$("#modal").css("top",top);
		$("#modal").css("display","block");

		if(type == 1){
			$("#showStatus").css("display","block");
			$("#showType").css("display","none");
			$("#showTime").css("display","none");
		}else if(type == 2){
			$("#showStatus").css("display","none");
			$("#showType").css("display","block");
			$("#showTime").css("display","none");
		}else if(type == 3){
			$("#showStatus").css("display","none");
			$("#showType").css("display","none");
			$("#showTime").css("display","block");
		}
	}
	//隐藏遮罩层
	function hideModal(){
		$("body").css("position","");//去除body上的fixed定位
		$("#showStatus").fadeOut(300);
		$("#showType").fadeOut(300);
		$("#showTime").fadeOut(300);
		$("#modal").fadeOut(300);//关闭弹出层
	}

</script>
<#include "../../buttom.ftl">
</body>
</html>