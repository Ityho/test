$(function() {
	if ($('#shareCode').val()) {
		if ($('#pageType').val() == 'web') {
			window._bd_share_config = {
				common : {
					bdMini : "1",
					bdMiniList : [ "tsina", "weixin", "sqq" ],
					onBeforeClick : shareOnBeforeClick,
					onAfterClick : function(cmd) {
						if ('weixin' == cmd) {
							setTimeout(function() {
								$('.bd_weixin_popup_foot').css('text-align', 'center');
								$('.bd_weixin_popup_foot').html('打开微信，点击底部的"发现"，<br />使用"扫一扫"即可将网页分享至朋友圈。');
							}, 100);
						}
						
						var sharePlatform = 0;
						if ('tsina' == cmd)
							sharePlatform = 1;
						else if ('weixin' == cmd)
							sharePlatform = 2;
						else if ('sqq' == cmd)
							sharePlatform = 4;
						
						$.ajax({
							url : actionBase + '/ui/singleWeiboAnalysis/recordSharePlatform.action',
							type : 'POST',
							data : {
								'fenxiWeibo.weiboShareCode' : $('#shareCode').val(),
								'fenxiWeibo.weiboSharePlatform' : sharePlatform
							},
							success : function() {}
						});
					}
				},
				slide : {
					bdImg : 0,
					bdPos : "right",
					bdTop : 80
				}
			};
		} else if ($('#pageType').val() == 'share') {
			window._bd_share_config = {
				common : {
					onBeforeClick : shareOnBeforeClick,
					onAfterClick : function(cmd) {
						if ('weixin' == cmd) {
							setTimeout(function() {
								$('.bd_weixin_popup_foot').css('text-align', 'center');
								$('.bd_weixin_popup_foot').html('打开微信，点击底部的"发现"，<br />使用"扫一扫"即可将网页分享至朋友圈。');
							}, 100);
						}
						
						var sharePlatform = 0;
						if ('tsina' == cmd)
							sharePlatform = 1;
						else if ('weixin' == cmd)
							sharePlatform = 2;
						else if ('sqq' == cmd)
							sharePlatform = 4;
						
						$.ajax({
							url : actionBase + '/ui/singleWeiboAnalysis/recordSharePlatform.action',
							type : 'POST',
							data : {
								'fenxiWeibo.weiboShareCode' : $('#shareCode').val(),
								'fenxiWeibo.weiboSharePlatform' : sharePlatform
							},
							success : function() {}
						});
					}
				},
				share : {}
			};
		}
	}
});

var sharePic;
var shareOriginalUser;
var shareReportsCount = 0;
var shareCommentCount = 0;
var sharePriseCount = 0;
var shareReadsCount = 0;
var shareTopUser1, shareTopUser2, shareTopUser3;
function getShareContent() {
	var shareContentArr = [
       '就四介么拽，看介个微博，' + shareReportsCount + '人转发，' + shareCommentCount + '人评论，' + sharePriseCount + '人点赞。',
       '哎呀妈呀，看介个微博，竟然被“@' + shareTopUser1 + '、@' + shareTopUser2 + '、@' + shareTopUser3 + '”转发了，鸡冻 的小心脏怦怦直跳。',
       '和大数据谈恋爱是种什么体验？就像这条微博一样被剥得一丝不挂！',
       '这世上多的是你不知道的事，这条微博被' + shareReportsCount + '人转发，居然没有我，我不服！',
       '天了噜！' + shareReadsCount + '人关注了这条微博，如此有品位的我，居然现在才看到！',
       '据说90%的网友都会点开这条信息，这是真的吗？我先替你看看~',
       '@' + shareOriginalUser + ' 发了条微博，网友却炸锅了，' + shareReportsCount + '人转发，' + shareCommentCount + '人评论，再不看你就out了！',
       '万万没想到，一条' + shareReadsCount + '人关注的微博，背后居然暗藏着如此大的玄机~',
       '咦~~这条微博究竟是怎么火起来的？快跟我一起研究一下。',
       '大家别吵，我正专心看。。。'
    ];
	
	var shareContent = shareContentArr[Math.round(Math.random() * 9)];
	return shareContent;
}

function shareOnBeforeClick(cmd, config) {
	var shareContent = getShareContent();
	config.bdText = shareContent;
	config.bdDesc = shareContent;
	config.bdUrl = $('#shareURL').val() + '/ui/singleWeiboAnalysis/shareView.action?shareCode=' + $('#shareCode').val(); 
	if ('tsina' != cmd)
		config.bdPic = sharePic;
	config.bdSign = 'off';
	return config;
}

with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];