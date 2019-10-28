<#include "../../init_top.ftl" >
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/share/style.css"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePathH5}/css/iconfont.css"/>
</head>
<body class="back" >
<a href="javascript:history.go(-1)" class="goback">
	<i class="iconfont icon-fanhui1"></i>
</a>
<input type="hidden" id="uecCode" value="${userExclusiveChannelResElement.uecCode }" />
<input type="hidden" id="uecQrcodeLogoPath" value="${userExclusiveChannelResElement.uecQrcodeLogoPath }" />
<input type="hidden" id="uecUrl" value="${url}" />
<div class="row body_t_l">
	<div class="content">
		<div class="title">
			<div class="tit_top_l"></div>
			<h1><i class="s_l icon-wujiaoxing"></i>邀请二维码<i class="s_r icon-wujiaoxing"></i></h1>
		</div>
		<div class="code">
			<img id="sharePlanQRCodeImg" />
			<p>用大数据说话</p>
		</div>
		<div class="mode" style="display: block;">
			<div class="tit">
				<h2>邀请方式</h2>
			</div>
			<div class="mode_list">
				<ul>
					<li onclick="shareBtnClick('weixin')">
						<i class="icon-weixin"></i>
						<p>微信</p>
					</li>
					<li onclick="shareBtnClick('tsina')">
						<i class="icon-weibo"></i>
						<p>微博</p>
					</li>
					<li onclick="shareBtnClick('sqq')">
						<i class="icon-qq"></i>
						<p>QQ</p>
					</li>
					<!-- <li>
                        <i class="icon-SMS"></i>
                        <p>短信</p>
                    </li> -->
				</ul>
			</div>
			<div class="bottom align_c" style="display: none;">
				<a href="#" class="btn btn-info btn-block">更多推荐方式</a>
				<a href="#" class="btn btn-link btn-block">已成功推荐2人 >></a>
			</div>
			<div class="bdsharebuttonbox" style="display: none;">
				<a href="javascript:;" data-cmd="weixin"  title="微信">微信</a>
				<a href="javascript:;" data-cmd="tsina" title="微博">微博</a>
				<a href="javascript:;" data-cmd="sqq" title="QQ">QQ</a>
			</div>
		</div>
	</div>
</div>
<script src="${staticResourcePathH5}/js/newHome/jquery-1.10.2.min.js?v=${SYSTEM_INIT_TIME}"></script>
<#--<script type="text/javascript">saveOperateLog('分享计划','1047');</script>-->
<script type="text/javascript">
	$(function() {
		var imgURL = "${baseUrl}" + '/userCenter/sharePlanQRCode?userExclusiveChannelResElement.uecCode=' + $('#uecCode').val() + '&userExclusiveChannelResElement.uecQrcodeLogoPath=' + $('#uecQrcodeLogoPath').val();
		$('#sharePlanQRCodeImg').attr('src', imgURL);
	});

	function shareBtnClick(cmd) {
		if (cmd && $('a[data-cmd="' + cmd + '"]')[0]) {
			$('div.bdsharebuttonbox > a[data-cmd="' + cmd + '"]')[0].click();
		}
	}

	window._bd_share_config = {
		common : {
			onBeforeClick : function(cmd, config) {
				var shareURL = $('#shareURL').val();
				var shareContent = '分享计划';
				config.bdText = shareContent;
				config.bdDesc = shareContent;
				config.bdUrl = $('#uecUrl').val();

				config.bdSign = 'off';
				return config;
			}
		},
		share : {}
	};

	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
</script>
<#include "../../buttom.ftl" >
</body>
</html>
