<#include "../../top.ftl" >

<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
<script type='text/javascript' src='${staticResourcePathH5}/js/weixinNewConfig.js?v=${SYSTEM_INIT_TIME}'></script>
<script>
	weixinLinkShare({wxAuthUrl:njxBasePath+'/report/getWeixinConfig.action',
		title:"${hotTitle}",desc:"微热点，社会化大数据应用平台",imgUrl:"${staticResourcePathH5}/images/fenxiang/weibowyq-icon300.jpg",
		link:location.href.split('#')[0]});
</script>
<#include "../../echarts.ftl" >
<!--主要内容 start-->
</head>
<body>

<!--头部导航栏 start-->
<#include "../../navigate_style.ftl" >

<script type="text/javascript">saveOperateLog('热榜查看页','1039');</script>
<input type="hidden" id="setCity" value="china">
<input type="hidden" id="shareCode" value="${shareCode}">
<input type="hidden" id="viewUri" value="${viewUri}">
<input type="hidden" id="reportId" value="${id}">
<input type="hidden" id="isChina" value="${isChina}">
<input type="hidden" id="isApp" value="${isApp}">
<input type="hidden" id="startTime" value="${startTime}">
<input type="hidden" id="endTime" value="${endTime}">

<!--头部导航栏 -->
<section class="section2" style="margin-top:-18px">
	<article class="context">
		<!--每日热点排行报告 start-->
		<div class="dailyHot">
			<!--报告头部 start -->
			<div class="heard">
				<div class="logo" >
<#--					<p style="font-size:20px;float:left;position:absolute;left:50px;font-weight:bold">微热点</p>-->
<#--					<p style="font-size:20px;position:absolute;left:190px;float:left">微热点只用数据说话!</p>-->
					<p>微热点只用数据说话!</p>
				</div>
				<h2 style="font-size:16px;margin-top:15px">${hotTitle}</h2>
				<h3>
					${startTime}～${endTime}
				</h3>
			</div>
			<!--报告头部 end -->
			<!--情绪指数 start -->
			<div class="emotionBox">
				<div class="emotion">
					<div class="thermometer">
						&nbsp;&nbsp; ${sentiment}
					</div>
					<div class="cText"></div>
					<div class="thermometer thermometer2">
						${sentimentend}&nbsp;&nbsp;
					</div>
				</div>
			</div>
			<!--情绪指数 end -->
			<!--排行列表 start -->
			<#list dailyHotList! as item>
				<#if dailyHotList?size <= 3>
					<div class="HotList">
						<ul>
							<li>
								<em>${item_index}</em>
								<h1 style="border-bottom:dashed 0px #eee;">
									<#if item.eventLink?? >
										<a href='${item.eventLink!}'>
											<div style="font-size:15px;width:70%;line-height:18px;float:left;white-space:normal">
												${item.title!''}
											</div>
											<div style="font-size:15px;width:30%;float:left;white-space:normal">
												<#if item.subtitle??>
													<span style="margin-left:15px;color:red;font-size:12px;">${item.subtitle!""}</span>
												</#if>
											</div>
										</a>
									<#else>
										<#if item.originallink??>
											<a href="${item.originallink}">
												<div style="font-size:15px;width:70%;line-height:18px;float:left;	white-space:normal">
													${item.title!""}
												</div>
												<div style="font-size:15px;width:30%;float:left;	white-space:normal">
													${item.subtitle!""}
												</div>
											</a>
										</#if>
									</#if>
								</h1>
								<div class="con" style="font-size:15px;border-top:dashed 1px #eee;">
									<div>${item.content!""}</div>
								</div>
								<#if item.eventLink??>
									<div class="img" onclick="javascript:location.href =${item.eventLink!}">
										<img src="${item.picpath!""}"/>
									</div>
								<#else>
									<#if item.originallink??>
										<div class="img" onclick="javascript:location.href = ${dh.originallink!""}">
											<img src="${item.picpath!""}"/>
										</div>
									</#if>
								</#if>
								<div class="HotBottom">
									<dl>
										<dd>热度指数<br>${item.heatvalue!0}</dd>
										<dd>舆论高峰值<br>${item.peakvalue!0}</dd>
										<dd>信息总量<br>${item.infocount!0}</dd>
										<dd>媒体报道量<br>${item.reportcount!0}</dd>
									</dl>
								</div>
							</li>
						</ul>
					</div>
				<#else>
					<div class="HotList">
						<ul>
							<li class="xs">
								<em>${item_index}</em>
								<h1>
									<#if item.eventLink! == ''>
										<a href="${item.eventLink!}">
											<div style="width:100%;float:left;	over-flow:hidden;white-space:nowrap;text-overflow:ellipsis;">
												${item.title!""}
												<#if item.subtitle??>
													<span style="margin-left:15px;color:red;font-size:12px;">${item.subtitle}</span>
												</#if>
											</div>
										</a>
									<#else>
										<#if item.originallink??>
											<a href="${item.originallink!}">
												<div style="width:100%;float:left;	over-flow:hidden;white-space:nowrap;text-overflow:ellipsis;">
													${item.title!""}
													<#if item.subtitle??>
														<span style="margin-left:15px;color:red;font-size:12px;">${item.subtitle}</span>
													</#if>
												</div>
											</a>
										</#if>
									</#if>
								</h1>
								<div class="HotBottom2">
									<dl>
										<dd>热度指数 ${item.heatvalue} </dd>
									</dl>
								</div>
							</li>
						</ul>
					</div>
				</#if>
			</#list>
			<!--每日热点排行报告 end-->
	</article>
</section>

<#--<section class="section">-->
<#--	<article class="context">-->
<#--		<ul>-->
<#--			<li class="g_12">-->
<#--				<div class="con con_1">-->
<#--					<span class="tit tit5 float_l"><strong>区域热点</strong>-->
<#--						<p class="sm">微热点提供大数据支持</p>-->
<#--					</span>-->
<#--					<span class="tit tit5 " style="margin-left:50%" onClick="changeMap()"><strong>全国</strong>-->
<#--					</span>-->
<#--				</div>-->
<#--			</li>-->
<#--		</ul>-->
<#--		<div class="chartBox" id="chart_1_1"  style="width: 100%;height: 350px;">-->
<#--			<div class="spinner">-->
<#--				<div class="bounce1"></div>-->
<#--				<div class="bounce2"></div>-->
<#--				<div class="bounce3"></div>-->
<#--			</div>-->
<#--		</div>-->
<#--	</article>-->
<#--</section>-->

<section class="section2" style="margin-top:20px">
	<article class="context">
		<!--每日热点排行报告 start-->
		<div id="news"class="dailyHot">

		</div>		 <!--每日热点排行报告 end-->
	</article>
</section>
<section  class="section2"style="height:50px;line-height:50px;bottom:50px;">
	<div style="text-align: center;">
		<a class="icon-share">分享</a>
	</div>
</section>

<#if isApp == 0 >
<#--	<c:set var = "type" value="3"></c:set>-->
</#if>


<div id="shareNewsPOP" class="footPOP" style="display: none;">
	<ul class="footList fenxiang bdsharebuttonbox" data-tag="share_1">
		<li><a class="icon icon_2" data-cmd="tsina"></a>新浪微博</li>
		<li><a class="icon icon_1" data-cmd="weixin"></a>微信</li>
		<li><a class="icon icon_4" data-cmd="sqq"></a>QQ好友</li>
		<li><a class="icon icon_9" data-cmd="tqq"></a>腾讯微博</li>
		<li><a class="icon icon_5" data-cmd="qzone"></a>QQ空间</li>
	</ul>
</div>
<div class="zhezhao" ></div>
</body>
<script>
	$(function(){
		$(".footer .icon-cross").on("touchend",function(){
			$(".footer").addClass(downShow);
			$(".floatHeight").css("display","none");
		});

		$(".icon-share").on("click",function(){
			$(".zhezhao").show(300);
			$("#shareNewsPOP").fadeIn(300);
			$(".footPOP").addClass('downShow');
			$(".footPOP").removeClass('downOut');
			$(".prompPOP").removeClass('scaleShow');
			$(".prompPOP").addClass('scaleOut');
			var shareCode = document.getElementById("shareCode").value;

			shareHotlistCallBack(shareCode);
		});
	});
	//分享的beforeclick事件
	function bdShareBeforeClick(cmd,config) {
		config.bdText = bdShareTitle;
		config.bdDesc = bdShareDesc;
		config.bdUrl = bdShareUrl;
		config.bdSign = "off";
		return config;
	}
	function shareHotlistCallBack(data){
		var url = "";
		url = $("#viewUri").val()+'/share/view/getDailyHotBycode.action?sharecode='+data;
		bdShareTitle ="";
		bdShareDesc = '自定义分享摘要';
		bdShareUrl = url;

		window._bd_share_config = {
			common : {
				onBeforeClick : bdShareBeforeClick
			},
			share : []
		}
		$(".footPOP a").off("click");
		with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];

	}





</script>
<script type="text/javascript">

	$(function () {
		getChartData({province:""});
		var getReportId = document.getElementById("reportId").value;
		getHotEvent({province:"",id:getReportId});
	});

	function changeMap(){
		var getReportId = document.getElementById("reportId").value;
		getHotEvent({province:"",id:getReportId});
	}

	function getHotEvent(obj){
		if(obj.province!=""){
			document.getElementById("isChina").value =1;
		}else{
			document.getElementById("isChina").value =0;
		}
		$.ajax({
			type:  "post",
			url: '${njxBasePath}/view/hotEvent/getNewsByArea.shtml',
			data: obj,
			cache:false,
			success:function(data){
				var chartDiv = $("#news");
				if(data&&data!=""){
					if(data==null || data.length==0){
						chartDiv.empty();
						return;
					}else{
						//chartMapCallback(data);
						chartDiv.empty();
						//console.log(data);
						var length =data.length;
						var isChina = document.getElementById("isChina").value;

						if(length > 10 && isChina <1){
							length = 10;
						}else if(length >3 && isChina >0){
							length = 3;
						}else{
							length = data.length;
						}

						for(var i =0;i < length;i++){
							if(data[i].originallink==null){
								chartDiv.append(
										"<div class='HotList'>"
										+"<ul>"
										+"<li class='xs'>"
										+"<div style='width:20%;height:50px;color:#2874b6;text-align:center;line-height:50px;float:left;white-space:normal'>"
										+"["+data[i].keywordsname+"]"
										+"</div>"
										+"<div style='width:50%;line-height:30px;margin-top:10px;float:left;white-space:normal'>"

										+"<a style='color:#000;'href='"+data[i].eventLink+"'>"
										+data[i].title
										+"</a>"

										+"</div>"
										+"<div style='font-size:14px;width:25%;height:50px;line-height:50px;float:left;white-space:normal;color:#F02B15;'class='HotBottom2'>"
										+"|热度指数"+data[i].heatvalue
										+"</div>"
										+"</li>"
										+"</ul>"
										+"</div>"
								);
							}else{
								chartDiv.append(
										"<div class='HotList'>"
										+"<ul>"
										+"<li class='xs'>"
										+"<div style='width:20%;height:50px;color:#2874b6;text-align:center;line-height:50px;float:left;white-space:normal'>"
										+"["+data[i].keywordsname+"]"
										+"</div>"
										+"<div style='width:50%;margin-top:10px;line-height:30px;float:left;white-space:normal'>"

										+"<a style='color:#000;'href='"+data[i].originallink+"'>"
										+data[i].title
										+"</a>"

										+"</div>"
										+"<div style='font-size:14px;width:25%;height:50px;line-height:50px;float:left;white-space:normal;color:#F02B15;'class='HotBottom2'>"
										+"|热度指数"+data[i].heatvalue
										+"</div>"
										+"</li>"
										+"</ul>"
										+"</div>"
								);
							}

						}

					}
				}else{
					chartDiv.empty();
				}
			}
		});
	}
</script>
<script type="text/javascript">

	function getChartData(obj){

		$.ajax({
			type:  "post",
			url: '${njxBasePath}/view/hotEvent/getAreaList.action',
			data: obj,
			cache:false,
			success:function(data){
				var chartDiv = $("#chart_1_1");
				if(data&&data!=""){
					if(data==null || data.length==0){
						chartDiv.html('<p class="loading_pic">此时间段暂无信息</p>');
						return;
					}else{
						chartMapCallback(data);
					}
				}else{
					chartDiv.html('<p class="loading_pic">此时间段暂无信息</p>');
				}
			}
		});
	}
	function chartMapCallback(data){

		var min = 0;
		var max = 50000;
		var getCity = document.getElementById("setCity").value;
		$.each(eval(data), function(i, n) {
			delete n.itemStyle;
			if (n.value > max)
				max = n.value;
			if (n.value < min)
				min = n.value;
		});
		var config = require(
				[
					'echarts',
					'echarts/chart/map',
				],
				function (ec) {
					var chart1 = ec.init(document.getElementById("chart_1_1"));

					option = {
						title : {

						},
						tooltip : {
							trigger: 'item'
						},
						legend: {
							orient: 'vertical',
							x:'left',
							data:['地域热度'],
							selectedMode:false
						},
						dataRange: {
							min: min,
							max: max,
							x: 'left',
							y: 'bottom',
							itemHeight:10,
							itemWidth:20,
							text:['高','低'],           // 文本，默认为数值文本
							calculable : false,
							color: ['#d44e24','#f29300','#f3d647']
						},
						toolbox: {
							show: false,
							orient : 'vertical',
							x: 'right',
							y: 'center',
							feature : {
								mark : {show: true},
								dataView : {show: true, readOnly: false},
								restore : {show: true},
								saveAsImage : {show: true}
							}
						},

						series : [
							{
								name: '地域热度',
								type: 'map',
								mapType: getCity,
								selectedMode : 'single',
								roam: false,
								itemStyle:{
									normal:{label:{show:true}},
									emphasis:{label:{show:true}}
								},
								data:data
							},

						]
					};
					chart1.setOption(option);

					var enConfig = require('echarts/config');

					var mapType = [
						'china',
						// 23个省
						'广东', '青海', '四川', '海南', '陕西',
						'甘肃', '云南', '湖南', '湖北', '黑龙江',
						'贵州', '山东', '江西', '河南', '河北',
						'山西', '安徽', '福建', '浙江', '江苏',
						'吉林', '辽宁', '台湾',
						// 5个自治区
						'新疆', '广西', '宁夏', '内蒙古', '西藏',
						// 4个直辖市
						'北京', '天津', '上海', '重庆',
						// 2个特别行政区
						'香港', '澳门'
					];
					var getReportId = document.getElementById("reportId").value;
					chart1.on(enConfig.EVENT.MAP_SELECTED, function (param){

						var selected = param.selected;
						var str = '';
						for (var p in selected) {
							if (selected[p]) {
								str += p;
							}
						}
						getHotEvent({province:str,id:getReportId});
					});
				}
		)
	}
</script>
<#include "../../buttom.ftl">