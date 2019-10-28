   /*
	* 智能机浏览器版本信息:
	*
	*/
   window.bs={
	   versions:function(){
		   var u = navigator.userAgent, app = navigator.appVersion;
		   return {//移动终端浏览器版本信息
		   trident: u.indexOf('Trident') > -1, //IE内核
		   presto: u.indexOf('Presto') > -1, //opera内核
		   webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		   gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		   //mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
		   mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
		   ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		   android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
		   iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
		   iPad: u.indexOf('iPad') > -1, //是否iPad
		   webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		   };
	   }(),
	   language:(navigator.browserLanguage || navigator.language).toLowerCase()
   }
	var url = location.href;
	if(bs.versions.mobile){
	    var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
		if (ua.match(/WeiBo/i) == "weibo") {
			//在新浪微博客户端打开
			console.log("在新浪微博客户端打开");
			location.href="http://apps.weibo.com/3960037780/8rXM111J";
	    }else if(ua.match(/MicroMessenger/i) == "micromessenger"){
	    	//在微信中打开
	    	console.log("在微信中打开");
			location.href="<%=njxBasePath%>/weiboHome.action";
	    }else{
	    	//手机浏览器打开
	    	console.log("手机浏览器打开");
	    	location.href="<%=njxBasePath%>/weiboHome.action";
	    }
	}