
<#include "../../top.ftl" >
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<!-- 微博 -->
	<meta property="qc:admins" content="2404321175711636" />
	<!-- QQ -->
	<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/app/invitation/Basics.css" />
	<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/app/invitation/activity.css"/>
	<script src="${staticResourcePathH5}/js/app/wyrem.js"></script>
	<title></title>
</head>

<body>
<div class="bg"></div>
<div class="content activity" style="margin-top: -1.875rem;">
	<input type="hidden" id="userEncode" value="${userEncode}">
	<section class="text-center" style="margin-top: 20px;">
		<h3 class="fz15"><i class="icon-act_jewel v-a-sub"></i> 本月已邀请<span class="fz30 padding-h-3 orange400 v-a-sub">${userInviteInfo.currentMonthInviteUserNum}</span>位好友，快去领取奖励吧~</h3>
		<div class="act-item mt20">
			<p class="fz12">邀请<span class="fz15 orange400 padding-h-5 fw600"><i class="icon-act_user"></i>×1</span>可获得200微豆+5天信息监测服务抵用券</p>
			<a onclick="javascript:getReward(1);" id="activeReward1" class="act-btn">马上领取</a>
		</div>
		<div class="act-item mt20">
			<p class="fz12">邀请<span class="fz15 orange400 padding-h-5 fw600"><i class="icon-act_user"></i>×3</span>可获得800微豆+10天信息监测服务抵用券</p>
			<a onclick="javascript:getReward(3);" id="activeReward5" class="act-btn">马上领取</a>
		</div>
		<div class="act-item mt20">
			<p class="fz12">邀请<span class="fz15 orange400 padding-h-5 fw600"><i class="icon-act_user"></i>×5</span>可获得1500微豆+15天信息监测服务抵用券</p>
			<a onclick="javascript:getReward(5);" id="activeReward10" class="act-btn">马上领取</a>
		</div>
		<p class="act-p">*以上奖励，按月刷新</p>
		<div class="act-copy">
			<label for="">
				<p class="fz11 fc-gray6">您的专属链接:</p>
				<input type="text" name="" id="myLink" value="${userInviteInfo.inviteUrl}" style="text-align:center"/>
			</label>
			<div>
				<%-- data-clipboard-text="${userInviteInfo.inviteUrl}" --%>
				<a class="btn-copy" href="javascript:;" id="shareApp">立即邀请</a>
			</div>

		</div>
	</section>
	<section>
		<!--我已获得的奖励-->
		<div class="act-title margin-v-25">
			<h3>您已获得的奖励</h3>
		</div>
		<div class="award clearfix">
			<div class="award-left">
				<img src="${staticResourcePathH5}/images/app/invitation/act_douzi.png"/>
				<p class="fz11">累计获得<span class="fz18 orange400 padding-h-5">${userInviteInfo.rewardWdToal}</span>微豆</p>
			</div>
			<div class="award-right">
				<img src="${staticResourcePathH5}/images/app/invitation/act_jiance.png"/>
				<p class="fz11">累计获信息监测服务 <span class="fz18 orange400 padding-h-5">${userInviteInfo.rewardKeywordDayTotal}</span>天</p>
			</div>
		</div>


	</section>
	<section>
		<!--申请流程-->
		<div class="act-title margin-v-25">
			<h3>申请流程</h3>
		</div>
		<div class="step-wrap">
			<ul class="clearfix">
				<li>
					<em>1</em>
					<p class="fz12">复制自己的专属链接</p>
				</li>
				<li>
					<em>2</em>
					<p class="fz12">将链接发送给自己的好友</p>
				</li>
				<li>
					<em>3</em>
					<p class="fz12">好友通过链接注册成功</p>
				</li>
			</ul>
			<div class="actdown">
				<img src="${staticResourcePathH5}/images/app/invitation/act_down.png" />
			</div>
			<h3><i class="icon-right"></i>邀请成功~可以领取奖励去使用啦！</h3>
			<p class="fz12 fc-gray6">可以在会员中心--资产中心中查看并使用哦~</p>
		</div>
	</section>
	<section>
		<div class="act-title margin-v-25">
			<h3>活动规则</h3>
		</div>
		<div class="act-txt">
			<dl class="">
				<dt><i class="icon-txt"></i></dt>
				<dd>
					<p>本活动所有微热点用户均可参加；</p>
				</dd>

			</dl>
			<dl class="clearfix">
				<dt><i class="icon-txt"></i></dt>
				<dd>
					<p>活动奖励每月刷新一次，已获得领取奖励资格的用户请及时领取，未能领取的奖励将会于次月失效；</p>
				</dd>
			</dl>
			<dl class="clearfix">
				<dt><i class="icon-txt"></i></dt>
				<dd>
					<p>奖励有效期为领取后15天，请领取后及时使用哦。ps:微豆可以用来兑换各种产品和服务，服务抵用券可以用来兑换相应的产品，其中监测方案抵用券可以为现有方案进行续期哦；</p>
				</dd>

			</dl>
			<dl class="clearfix">
				<dt><i class="icon-txt"></i></dt>
				<dd>
					<p>本活动最终解释权归上海蜜度信息技术有限公司所有。</p>
				</dd>
			</dl>
		</div>
	</section>

</div>
<div class="actfooter"></div>
<!--弹框-->
<div class="modal-sucess modal1 padding-v-25">
	<div class="modal-content text-center">
		<img src="${staticResourcePathH5}/images/app/invitation/act-sucess.png" style="width: 2.125rem;">
		<p class="mt10 fz18 orange400">领取成功</p>
		<p class="fz13 padding-v-10">请至会员中心-资产中心查收哦</p>
	</div>
	<div class="modal-footer text-center">
		<a href="javascript:;" class="pay-close mr10 close">稍后查看</a>
		<a href="javascript:;" class="btn-recharge btn-orange">马上去使用</a>
	</div>
	<div>
		<a href="javascript:;" class="btn-close close">
			<img src="${staticResourcePathH5}/images/app/invitation/act_close.png"/>
		</a>
	</div>

</div>
<!--复制成功-->
<div class="modal-sucess modal2 padding-v-25">
	<div class="modal-content text-center">
		<img src="${staticResourcePathH5}/images/app/invitation/act-sucess.png" style="width: 2.125rem;">
		<p class="mt10 fz18 orange400">复制成功</p>
		<p class="fz13 padding-v-10">快去分享给你的小伙伴们吧~！</p>
	</div>
	<div class="modal-footer text-center">
		<a href="javascript:;" class="btn-recharge btn-orange close">我知道了</a>
	</div>
	<div>
		<a href="javascript:;" class="btn-close close">
			<img src="${staticResourcePathH5}/images/app/invitation/act_close.png"/>
		</a>
	</div>
</div>
<!--复制失败-->
<div class="modal-sucess modal3 padding-v-25">
	<div class="modal-content text-center mb30">
		<p class="mt10 fz18 orange400">复制失败</p>
		<p class="fz13 padding-v-10">请手动粘贴复制哦~！</p>
	</div>
	<div class="modal-footer text-center">
		<a href="javascript:;" class="btn-recharge btn-orange close">我知道了</a>
	</div>
	<div>
		<a href="javascript:;" class="btn-close close">
			<img src="${staticResourcePathH5}/images/app/invitation/act_close.png"/>
		</a>
	</div>
</div>
<div class="modal-mask"></div>
<script src="${staticResourcePathH5}/js/app/jquery.min.js"></script>
<script src="${staticResourcePathH5}/js/app/clipboard.min.js"></script>
<script type="text/javascript">

	$('.close').click(function() {
		$(this).parent().parent().hide();
		$('html').removeClass('noscroll');
		$('.modal-mask').hide();
	})

</script>
<script type="text/javascript">
	function getReward(n){
		$.ajax({
			url : actionBase+'/app/getReward.shtml',
			type : 'POST',
			data : {
				userEncode:$("#userEncode").val(),
				rewardNum : n
			},
			success : function(result){
				if(result.status==1 && result.obj.code=="0000"){
					$("#activeReward"+n).attr("class","act-btn bg-grey400");
					$("#activeReward"+n).attr('onclick','');
					$("#activeReward"+n).text("已领取");
					$("#activeReward"+n).unbind();

					var ua = navigator.userAgent.toLowerCase();
					if (/iphone|ipad|ipod/.test(ua)) {
						window.webkit.messageHandlers.IsGet.postMessage({isGet:1});
					} else if (/android/.test(ua)) {
						wyqApp.show("{'isGet':1}");
					}
				}else{
					var ua = navigator.userAgent.toLowerCase();
					if (/iphone|ipad|ipod/.test(ua)) {
						window.webkit.messageHandlers.IsGet.postMessage({isGet:0});
					} else if (/android/.test(ua)) {
						wyqApp.show("{'isGet':0}");
					}
				}
			}
		});
	}
</script>
<script type="text/javascript">
	$(function() {// 初始化内容
		var ua = navigator.userAgent.toLowerCase();
		if (/iphone|ipad|ipod/.test(ua)) {
			$("#shareApp").attr("onclick","iosShare()");
		} else if (/android/.test(ua)) {
			$("#shareApp").attr("onclick","androidShare()");
		}
	});
	function iosShare(){
		var shareUrl = $("#myLink").val();
		window.webkit.messageHandlers.Share.postMessage({title:'HI，微热点邀请您免费使用信息监测啦~',content:'新用户可享30,000微积分好礼，社会化大数据搜索，网络信息尽在掌握~',url:shareUrl});
	}
	function androidShare(){
		var shareUrl = $("#myLink").val();
		wyqApp.show("{'title':'HI，微热点邀请您免费使用信息监测啦~','content':'新用户可享30,000微积分好礼，社会化大数据搜索，网络信息尽在掌握~','url':'"+shareUrl+"'}");
	}
</script>
<script type="text/javascript">
	$(function() {// 初始化内容
		var num = ${userInviteInfo.currentMonthInviteUserNum};
		var get1 = ${fn:indexOf(userInviteInfo.isGet, "1")};
		if(num < 1){
			console.log("111");
			$("#activeReward1").attr("class","act-btn bg-grey400");
			$("#activeReward1").attr('onclick','');
			$("#activeReward1").text("未达成");
			$("#activeReward1").unbind();
		}
		if(${fn:indexOf(userInviteInfo.isGet, "1")}>=0){
			$("#activeReward1").attr("class","act-btn bg-grey400");
			$("#activeReward1").attr('onclick','');
			$("#activeReward1").text("已领取");
			$("#activeReward1").unbind();
		}
		if(num < 3){
			console.log("111");
			$("#activeReward5").attr("class","act-btn bg-grey400");
			$("#activeReward5").attr('onclick','');
			$("#activeReward5").text("未达成");
			$("#activeReward5").unbind();
		}
		if(${fn:indexOf(userInviteInfo.isGet, "2")} >= 0){
			console.log("111");
			$("#activeReward5").attr("class","act-btn bg-grey400");
			$("#activeReward5").attr('onclick','');
			$("#activeReward5").text("已领取");
			$("#activeReward5").unbind();
		}
		if(num < 5){
			console.log("111");
			$("#activeReward10").attr("class","act-btn bg-grey400");
			$("#activeReward10").attr('onclick','');
			$("#activeReward10").text("未达成");
			$("#activeReward10").unbind();
		}
		if(${fn:indexOf(userInviteInfo.isGet, "3")} >= 0){
			console.log("111");
			$("#activeReward10").attr("class","act-btn bg-grey400");
			$("#activeReward10").attr('onclick','');
			$("#activeReward10").text("已领取");
			$("#activeReward10").unbind();
		}
	});
</script>
<%@ include file="../../buttom.jsp" %>
</body>
</html>