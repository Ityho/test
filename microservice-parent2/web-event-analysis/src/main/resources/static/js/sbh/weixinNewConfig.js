/**
 * weixinInitShare  ΢�ŷ����ʼ�������ͷ���
 * authOption ΢����Ȩ��֤����
 * shareOption �Զ���������
 * */
function weixinInitShare(authOption,shareOption){
		//΢����Ȩ
		wx.config({
		    debug: false, // ��������ģʽ,���õ�����api�ķ���ֵ���ڿͻ���alert��������Ҫ�鿴����Ĳ�����������pc�˴򿪣�������Ϣ��ͨ��log���������pc��ʱ�Ż��ӡ��
		    appId: authOption.appId, // ������ںŵ�Ψһ��ʶ
		    timestamp: authOption.timestamp, // �������ǩ����ʱ���
		    nonceStr: authOption.nonceStr, // �������ǩ���������
		    signature: authOption.signature,// ���ǩ��������¼1
		    jsApiList : [ 'onMenuShareTimeline', 'onMenuShareAppMessage',  
		                  'onMenuShareQQ', 'onMenuShareWeibo', 'onMenuShareQZone' ]  
		});
		
		//�������
		var obj = {  
			title : shareOption.title,  
		    desc : shareOption.desc,
		    link : shareOption.link,  
		    imgUrl : shareOption.imgUrl, 
		};  
		
		//΢�ŷ���
		wx.ready(function(){
			  wx.onMenuShareAppMessage(obj);  
		        // 2.2 ��������������Ȧ����ť������Զ���������ݼ��������ӿ�  
		        wx.onMenuShareTimeline(obj);  
		        // 2.3 ����������QQ����ť������Զ���������ݼ��������ӿ�  
		        wx.onMenuShareQQ(obj);  
		        // 2.4 ����������΢������ť������Զ���������ݼ��������ӿ�  
		        wx.onMenuShareWeibo(obj);  
		        // config��Ϣ��֤���ִ��ready���������нӿڵ��ö�������config�ӿڻ�ý��֮��config��һ���ͻ��˵��첽���������������Ҫ��ҳ�����ʱ�͵�����ؽӿڣ��������ؽӿڷ���ready�����е�����ȷ����ȷִ�С������û�����ʱ�ŵ��õĽӿڣ������ֱ�ӵ��ã�����Ҫ����ready�����С�
		});
		wx.error(function(res){
			console.log(res);
		});
}
/**
 * weixinLinkShare  �Զ���΢�ŷ����������
 * option �Զ�������������
 * option.wxAuthUrl ΢����Ȩ�ӿ�
 * option.title �������
 * option.desc ����ժҪ
 * option.link ��������
 * option.imgUrl ����ͼƬ
 * */
function weixinLinkShare(option){
	var curUrl = location.href.split('#')[0];
	$.ajax({
		url :option.wxAuthUrl,
		type : 'POST',
		data :{"url":curUrl},
	   	success: function(authOption){
			weixinInitShare(authOption,option); 
	    }
    });
}


/**
 * ʹ�÷�ʽ
 * <script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
 * <script type='text/javascript' src='<%=staticResourcePathH5 %>/js/weixinNewConfig.js?v=<%=SYSTEM_INIT_TIME %>'></script>
 * <script>
 *	$(function(){
 *		weixinLinkShare({wxAuthUrl:njxBasePath+'/report/getWeixinConfig.action',
 *		title:"${hotTitle}",desc:"${hotTitle}",imgUrl:"<%=staticResourcePathH5 %>/images/fenxiang/weibowyq-icon300.jpg",
 *		link:location.href.split('#')[0]});
 *	})
 *	</script>
 * */

