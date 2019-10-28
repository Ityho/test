/**
 * http://usejsdoc.org/
 */
function weixinShare(){


	var shareMessage = document.getElementById("shareMessage").value;

	var text = document.title;


	wx.config({
		debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId: $("#appId").val(), // 必填，公众号的唯一标识
		timestamp: $("#timestamp").val(), // 必填，生成签名的时间戳
		nonceStr: $("#noncestr").val(), // 必填，生成签名的随机串
		signature:  $("#signature").val(),// 必填，签名，见附录1
		jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage',
			'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone' ]
	});
	var obj = {
		title : text,
		desc : shareMessage,
		link : 'http://gonganxs.wyq.cn/report/MartyrsMemorial.shtml',
		imgUrl : 'http://files.wyq.cn/h5/images/policeH5/share.jpg',
	};
	wx.ready(function(){
		wx.onMenuShareAppMessage(obj);

		// 2.2 监听“分享到朋友圈”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareTimeline(obj);

		// 2.3 监听“分享到QQ”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareQQ(obj);

		// 2.4 监听“分享到微博”按钮点击、自定义分享内容及分享结果接口
		wx.onMenuShareWeibo(obj);

		// config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	});
	wx.error(function(res){

	});
}


