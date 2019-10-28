/**
 * weixinInitShare  微信分享初始化参数和方法
 * authOption 微信授权验证参数
 * shareOption 自定义分享参数
 * */
function weixinInitShare(authOption,shareOption){
		//微信授权
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: authOption.appId, // 必填，公众号的唯一标识
		    timestamp: authOption.timestamp, // 必填，生成签名的时间戳
		    nonceStr: authOption.nonceStr, // 必填，生成签名的随机串
		    signature: authOption.signature,// 必填，签名，见附录1
		    jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage',  
		                  'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone' ]  
		});
		
		//分享对象
		var obj = {  
			title : shareOption.title,  
		    desc : shareOption.desc,
		    link : shareOption.link,  
		    imgUrl : shareOption.imgUrl, 
		};  
		
		//微信分享
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
		wx.error(function(res){});
}
/**
 * weixinLinkShare  自定义微信分享参数方法
 * option 自定义分享参数对象
 * option.wxAuthUrl 微信授权接口
 * option.title 分享标题
 * option.link 分享链接
 * option.imgUrl 分享图片
 * */
function weixinLinkShare(option){
	var curUrl = location.href.split('#')[0];
	console.log(curUrl);
	$.ajax({
		url :option.basePath + "/sys/taskMessage/getWeixinConfig.action",
		type : 'POST',
		data :{"url":curUrl},
	   	success: function(authOption){
			weixinInitShare(authOption,option); 
	    }
    });
}