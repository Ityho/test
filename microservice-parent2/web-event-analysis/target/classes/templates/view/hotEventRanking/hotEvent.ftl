<#include "../../top.ftl" >

<!--主要内容 start-->

<script type="text/javascript">document.title = "微热点";saveOperateLog('热点榜','1023');</script>
<div style="overflow:hidden;background:#fff;">
	<div style="background: url(${staticResourcePathH5}/images/weyuqing-logo.png?v=${SYSTEM_INIT_TIME}) no-repeat 1px;height: 55px;background-size:70%;width:45%;text-align:center;float:left;margin-left:10px;"></div>
	<div style="background: url(${staticResourcePathH5}/images/weiyuqing-24time.png?v=${SYSTEM_INIT_TIME}) no-repeat right;height: 55px;background-size:70%;width:45%;text-align:center;float:right;margin-right:10px;"></div>
</div>
<!--头部导航栏 -->
<!--头部导航栏 -->
<!--主要内容 start-->
<input type="hidden" id="viewUri" name="viewUri" value="${viewUri}"/> 

<#if  hotEventList?? >
	<#list hotEventList! as item>
		<div style="overflow:hidden;" onclick = "location.href='${njxBasePath}/share/view/dailyHot.action?sharecode=${item.shareCode}'">
			<div style="float: left;width: 15%;height: 100%;text-align: center;">
				<h1 style="line-height: 30px;margin-top: 30px;font-size: 14px;color:#333;">
					${(item.endTime?string('MM-dd'))!}</h1>
				<i style="width: 11px;height: 11px;background: #fd8c25;border-radius: 50%;display: block;float: right;margin-right: -6px;margin-top: -20px;"></i>
			</div>
			<div style="float: left;width: 76%;height: 100%;padding:0px 4%;border-left: solid 1px #fd8c25;overflow: hidden;">
				<div style="float: left;width: 100%;margin-top: 30px;overflow: hidden;">
					<h1 style="line-height: 30px;font-size: 15px;color:#333;overflow: hidden;">
						<#if item.title?contains('24小时热点：') >
							${item.title?substring(7)}
						<#else>
							${item.title}
						</#if>
					</h1>
					<img src="${item.picPath}" width="100%">
				</div>
			</div>

		</div>
	</#list>
<#else>
	<div class="chartBox">
		<p class="loading_pic">此时间段暂无推送记录</p>
	</div>
</#if>

<!--分享弹出框 -->
<div id="shareNewsPOP" class="footPOP" style="display: none;">
	<ul class="footList fenxiang bdsharebuttonbox" data-tag="share_1">
		<li><a class="icon icon_2" data-cmd="tsina"></a>新浪微博</li>
		<li><a class="icon icon_1" data-cmd="weixin"></a>微信</li>
		<li><a class="icon icon_4" data-cmd="sqq"></a>QQ好友</li>
		<li><a class="icon icon_9" data-cmd="tqq"></a>腾讯微博</li>
		<li><a class="icon icon_5" data-cmd="qzone"></a>QQ空间</li>
	</ul>
</div>
<div class="zhezhao" style="display: none;"></div>

<!--主要内容 end--> >
<#include "../../buttom.ftl">
<script>
	$(function(){
		$(".menuListRank").css('transform','translate(-572.5px, 0px) scale(1) translateZ(0px)');
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
			var shareCode = $(this).parent().find("input").val();
			var title = $(this).parent().find("input:last").val();
			var month = $(this).parent().find("input[name=time]").val();
			var day = $(this).parent().find("input[name=time2]").val();
			var date = "("+month+"月"+day+"日"+")";
			shareHotlistCallBack(shareCode,title,date);
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
	function shareHotlistCallBack(data,title,date){
		var url = "";
		url = $("#viewUri").val()+'/share/view/getDailyHotBycode.action?sharecode='+data;
		bdShareTitle =title+date;
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
