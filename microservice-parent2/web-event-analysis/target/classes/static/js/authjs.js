   /*
	* ���ܻ�������汾��Ϣ:
	*
	*/
   window.bs={
	   versions:function(){
		   var u = navigator.userAgent, app = navigator.appVersion;
		   return {//�ƶ��ն�������汾��Ϣ
		   trident: u.indexOf('Trident') > -1, //IE�ں�
		   presto: u.indexOf('Presto') > -1, //opera�ں�
		   webKit: u.indexOf('AppleWebKit') > -1, //ƻ�����ȸ��ں�
		   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //����ں�
		   //mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //�Ƿ�Ϊ�ƶ��ն�
		   mobile: !!u.match(/AppleWebKit.*Mobile.*/), //�Ƿ�Ϊ�ƶ��ն�
		   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios�ն�
		   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android�ն˻���uc�����
		   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //�Ƿ�ΪiPhone����QQHD�����
		   iPad: u.indexOf('iPad') > -1, //�Ƿ�iPad
		   webApp: u.indexOf('Safari') == -1 //�Ƿ�webӦ�ó���û��ͷ����ײ�
		   };
	   }(),
	   language:(navigator.browserLanguage || navigator.language).toLowerCase()
   }
	var url = location.href;
	if(bs.versions.mobile){
	    var ua = navigator.userAgent.toLowerCase();//��ȡ�ж��õĶ���
		if (ua.match(/WeiBo/i) == "weibo") {
			//������΢���ͻ��˴�
			console.log("������΢���ͻ��˴�");
			location.href="http://apps.weibo.com/3960037780/8rXM111J";
	    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
	    	//��΢���д�
	    	console.log("��΢���д�");
			location.href="<%=njxBasePath%>/weiboHome.action";
	    }else{
	    	//�ֻ��������
	    	console.log("�ֻ��������");
	    	location.href="<%=njxBasePath%>/weiboHome.action";
	    }
	}