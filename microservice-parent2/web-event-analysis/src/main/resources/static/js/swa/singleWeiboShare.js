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
								$('.bd_weixin_popup_foot').html('��΢�ţ�����ײ���"����"��<br />ʹ��"ɨһɨ"���ɽ���ҳ����������Ȧ��');
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
								$('.bd_weixin_popup_foot').html('��΢�ţ�����ײ���"����"��<br />ʹ��"ɨһɨ"���ɽ���ҳ����������Ȧ��');
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
       '���Ľ�ôק�������΢����' + shareReportsCount + '��ת����' + shareCommentCount + '�����ۣ�' + sharePriseCount + '�˵��ޡ�',
       '��ѽ��ѽ�������΢������Ȼ����@' + shareTopUser1 + '��@' + shareTopUser2 + '��@' + shareTopUser3 + '��ת���ˣ����� ��С��������ֱ����',
       '�ʹ�����̸��������ʲô���飿��������΢��һ��������һ˿���ң�',
       '�����϶�����㲻֪�����£�����΢����' + shareReportsCount + '��ת������Ȼû���ң��Ҳ�����',
       '�����࣡' + shareReadsCount + '�˹�ע������΢���������Ʒλ���ң���Ȼ���ڲſ�����',
       '��˵90%�����Ѷ���㿪������Ϣ������������������㿴��~',
       '@' + shareOriginalUser + ' ������΢��������ȴը���ˣ�' + shareReportsCount + '��ת����' + shareCommentCount + '�����ۣ��ٲ������out�ˣ�',
       '����û�뵽��һ��' + shareReadsCount + '�˹�ע��΢���������Ȼ��������˴������~',
       '��~~����΢����������ô�������ģ������һ���о�һ�¡�',
       '��ұ𳳣�����ר�Ŀ�������'
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