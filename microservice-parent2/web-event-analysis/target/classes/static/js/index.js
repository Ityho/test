//Object.prototype.clone = function(){  
//    var objClone;  
//    if ( this.constructor == Object ) objClone = new this.constructor();  
//    else objClone = new this.constructor(this.valueOf());  
//    for ( var key in this )  
//     {  
//        if ( objClone[key] != this[key] )  
//         {  
//            if ( typeof(this[key]) == 'object' )  
//             {  
//                 objClone[key] = this[key].clone();  
//             }  
//            else  
//             {  
//                 objClone[key] = this[key];  
//             }  
//         }  
//     }  
//     objClone.toString = this.toString;  
//     objClone.valueOf = this.valueOf;  
//    return objClone;  
//}  
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
//点击遮罩层隐藏
$(function(){
	$(".zhezhao").on("click",function(){
		$(".zhezhao").fadeOut(300);
		$(".footPOP").fadeOut(300);
		$(".prompPOP").fadeOut(300);
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};
		$(".footPOP").removeClass('downShow');
		$(".footPOP").addClass('downOut');
		//$(".footPOP").show();
		$("#fromPOP").removeClass('downShow');
		//$(".footPOP").out();
		$("#fromPOP").addClass('downOut');
		$(".prompPOP").removeClass('scaleShow');
		$(".prompPOP").addClass('scaleOut');
		$(".zz_content").hide();
		$(".dialog").hide();
		$(".dialog").removeClass("openBuyPrompt");
	});
	$(".icon-remove").on("click",function(){
		$(".dialog").fadeOut(300);
		$(".dialog").removeClass("openBuyPrompt");
		$(".zhezhao").fadeOut(300);
	});
	$(".info_close").on("click",function(){
		$(".zz_content").fadeOut(300);
		$(".zhezhao").fadeOut(300);
	});
	$(".cancel").on("click",function(){
		$(".zhezhao").fadeOut(300);
		$(".prompPOP").fadeOut(300);
		$(".mark").fadeOut(300);
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};

		$(".prompPOP").removeClass('scaleShow');
		$(".prompPOP").addClass('scaleOut');
	});

	$(".footPOP a").on("click",function(){
		if ('shareNews' == $(this).attr('name'))return;
		//$(".zhezhao").fadeOut(300);
		$(".footPOP").fadeOut(300);
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};
		$(".footPOP").removeClass('downShow');
		$(".footPOP").addClass('downOut');
		//$(".footPOP").show();
		$("#fromPOP").removeClass('downShow');
		//$(".footPOP").out();
		$("#fromPOP").addClass('downOut');
		return false;
	});

	$(".cancelFot").on("click",function(){
		$(".zhezhao").fadeOut(300);
		$(".prompPOP").fadeOut(300);
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};
		$(".prompPOP").removeClass('scaleShow');
		$(".prompPOP").addClass('scaleOut');
	});



	$(".submitBtn").on("click",function(){
		$(".zhezhao").hide();
		$(".footPOP").hide();
		$(".prompPOP").hide();
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};
	});

	$(".oper").on("click",function(){
		$(".zhezhao").show();
		$("#operationPOP").show();
//         window.ontouchmove=function(e){
//            e.preventDefault && e.preventDefault();
//            e.returnValue=false;
//            e.stopPropagation && e.stopPropagation();
//            return false;
//      } ;
		//$(".footPOP").show();
		$(".footPOP").addClass('downShow');
		//$(".footPOP").out();
		$(".footPOP").removeClass('downOut');
	})

//提示
	$("#refer").on("click",function(){
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=false;
			e.stopPropagation && e.stopPropagation();
			return false;
		} ;
		$(".zhezhao").fadeIn(300);
		$("#referPOP").fadeIn(300);
		$(".prompPOP").addClass('scaleShow');
		$(".prompPOP").removeClass('scaleOut');

		setTimeout(function(){
			$(".zhezhao").fadeOut(300);
			$("#referPOP").fadeOut(300);
			$(".prompPOP").addClass('scaleOut');
			$(".prompPOP").removeClass('scaleShow');
		},2000);
	});


	//间隔时间打开提示层
	$(function(){
		$("#appTimeInter").on("click",function(){
//    	window.ontouchmove=function(e){
//            e.preventDefault && e.preventDefault();
//            e.returnValue=false;
//            e.stopPropagation && e.stopPropagation();
//            return false;
//      } ;
			$(".zhezhao").fadeIn(300);
			$("#JGtimePOP").fadeIn(300);
			$(".prompPOP").addClass('scaleShow');
			$(".prompPOP").removeClass('scaleOut');
		});

		$("#JGtimePOP").find('li').click(function(){
			var text = $(this).find('p').text();
			$("#appTimeInter").val(text);
			$(".zhezhao").fadeOut(300);
			$(".prompPOP").fadeOut(300);
			$(".prompPOP").removeClass('scaleShow');
			$(".prompPOP").addClass('scaleOut');
			window.ontouchmove=function(e){
				e.preventDefault && e.preventDefault();
				e.returnValue=true;
				e.stopPropagation && e.stopPropagation();
				return true;
			};
		});

		$("#JGtimePOP ul li").on("click",function(){
			$("#JGtimePOP ul li").removeClass("cur");
			$(this).addClass("cur");
		});
	});

	$("#from_menu").on("click",function(){
		$(".zhezhao").fadeIn(300);
		$("#fromPOP").fadeIn(300);
//          window.ontouchmove=function(e){
//            e.preventDefault && e.preventDefault();
//            e.returnValue=false;
//            e.stopPropagation && e.stopPropagation();
//            return false;
//      } ;
		//$(".footPOP").show();
		$("#fromPOP").addClass('downShow');
		//$(".footPOP").out();
		$("#fromPOP").removeClass('downOut');
	})

	//帮助弹出效果
	$("#helpLink").on("click",function(){
		$("#helpPop").fadeIn(300);
		//禁用滚动条
//         window.ontouchmove=function(e){
//            e.preventDefault && e.preventDefault();
//            e.returnValue=false;
//            e.stopPropagation && e.stopPropagation();
//            return false;
//      } ;

	})
	$(".close_h").on("click",function(){
		$("#helpPop").fadeOut(300);
		//启用滚动条
		window.ontouchmove=function(e){
			e.preventDefault && e.preventDefault();
			e.returnValue=true;
			e.stopPropagation && e.stopPropagation();
			return true;
		};

	});


	//tabs
	$("#m_titles1 ul li").on("click",function(){
		$("#m_titles1 ul li").removeClass("m_titles_click");
		$(this).addClass("m_titles_click");
	});


	//关键词设置 高级设置切换
	/*
     $('#senior_set').click(function(){
     $('#set_1').stop().hide();
         $('#set_2').stop().show();   //显示
     });
     $('#ord_set').click(function(){
    $('#set_1').stop().show();
         $('#set_2').stop().hide();   //隐藏
     });*/


});




//我的tabs标签

$(function(){
	$(".list_nav3 ul li").on("click",function(){
		$("section.item").hide();
		$("section.item").eq($(this).attr("data-index")).show();
		$(".list_nav3 ul li").removeClass("click");
		$(this).addClass("click");
		if($(this).index() == 0){
			$(".foot").show();
		}else{
			$(".foot").hide();
		}
	});
});


//关键词设置
$(function () {
	$(".addMto").on("tap",function(){
		var num;
		$("article[data-index]").each(function(i,item){
			num = $(item).css("display")=="none"? $(item).attr("data-index"):-1;
			if(num!=-1){return false;}
		})
		if(num==-1){
			alert("只能添加3个");
		}
		$("article[data-index='"+num+"']").show();
	})
	$(".delMto").on("tap",function(){
		$(this).parents("article[data-index]").find("textarea").val("");
		$(this).parents("article[data-index]").hide();
	})
})



//支付选择
$(function(){
	//点击选中效果
	$(".pay>li").on("click",function(){
		$(".pay>li").removeClass("li_click");
		$(this).addClass("li_click");

	});

	//只显示一行，点击显示全部支付
	$(".pay li:gt(0)").show();
	var num=1;
	$(".more").on("click",function(){
		if(num){
			num--;
			$(".pay li:gt(0)").show();
		}else{
			num++;
			$(".pay li:gt(0)").hide();
		}
	});

	$(".icon-circle-choose").on("click",function(){
		$(this).toggleClass("icon-circle-seleted");
	});
	$(".icon-circle-chooseAll").on("click",function(){
		//$(".icon-circle-chooseAll").toggleClass("icon-circle-seleted");
		if($(".icon-circle-chooseAll").toggleClass("icon-circle-seleted").hasClass("icon-circle-seleted")){
			$(".icon-circle-choose").addClass("icon-circle-seleted");
		}else{
			$(".icon-circle-choose").removeClass("icon-circle-seleted");
		}
	});
});


//监测成功提示
function orderSuccess(){
	//提示
//            	window.ontouchmove=function(e){
//                    e.preventDefault && e.preventDefault();
//                    e.returnValue=false;
//                    e.stopPropagation && e.stopPropagation();
//                    return false;
//              } ;
	$(".zhezhao").fadeIn(300);
	$("#subPOP").fadeIn(300);
	$(".prompPOP").addClass('scaleShow');
	$(".prompPOP").removeClass('scaleOut');
	//2s后出引导修改
	setTimeout(function(){
		$("#subPOP").fadeOut(300);
		//$("#markPOP").fadeIn(300);
		//$("#markPOP").addClass('scaleShow');
		$(".prompPOP").addClass('scaleOut');
		$(".prompPOP").removeClass('scaleShow');
		$(".zhezhao").fadeOut(300);
	},2000);
	//2s后自动消失
//                   setTimeout(function(){
//                	    $(".zhezhao").fadeOut(300);
//                      	$("#markPOP").removeClass('scaleShow');
//                      	$("#markPOP").fadeOut(300);
//	              },4000);
}


//监测成功提示3次后
function orderSuccessAfter(){
	//提示
//            	window.ontouchmove=function(e){
//                    e.preventDefault && e.preventDefault();
//                    e.returnValue=false;
//                    e.stopPropagation && e.stopPropagation();
//                    return false;
//              } ;
	$(".zhezhao").fadeIn(300);
	$("#subPOP").fadeIn(300);
	$(".prompPOP").addClass('scaleShow');
	$(".prompPOP").removeClass('scaleOut');

	setTimeout(function(){
		$(".zhezhao").fadeOut(300);
		$("#subPOP").removeClass('scaleShow');
		$("#subPOP").fadeOut(300);
	},2000);
}



//取消弹出的遮罩层
function cancelZheZhao(){
	$(".zhezhao").fadeOut(300);
	$(".footPOP").fadeOut(300);
	$(".prompPOP").fadeOut(300);
	//启用滚动条
	window.ontouchmove=function(e){
		e.preventDefault && e.preventDefault();
		e.returnValue=true;
		e.stopPropagation && e.stopPropagation();
		return true;
	};
	$(".footPOP").removeClass('downShow');
	$(".footPOP").addClass('downOut');
	$("#fromPOP").removeClass('downShow');
	$("#fromPOP").addClass('downOut');
	$(".prompPOP").removeClass('scaleShow');
	$(".prompPOP").addClass('scaleOut');
}

function cancelShare(){
	$(".zhezhao").hide();
	$(".footPOP").fadeOut(300);
	window.ontouchmove=function(e){
		e.preventDefault && e.preventDefault();
		e.returnValue=true;
		e.stopPropagation && e.stopPropagation();
		return true;
	};
	$(".footPOP").removeClass('downShow');
	$(".footPOP").addClass('downOut');
	$("#shareNewsPOP").removeClass('downShow');
	$("#shareNewsPOP").addClass('downOut');
}

function getHeatShareCode(){
	$.ajax({url:njxBasePath+"/doHeatShareCode.shtml",cache:false,success:function(result){
			if(result.status){
				//alert(result.obj);
				$("form[name='searchForm']").attr("action",njxBasePath+"/view/heatsearch/searchHeatResult.shtml?heatShareCode="+result.obj);
			}
		}});
//	$.ajax(,function(result){
//		if(result.status){
//			$("form[name='searchForm']").attr("action","searchHeatResult.shtml?heatShareCode="+result.obj);
//		}
//	})
}

function setSelectIdVal(ev,t,repeatNum) {
	$("#wapUrl").val($(t).data("url"));
	$("#selectedMenuId").val(ev);
	$("#repeatNum").val(repeatNum);
}
function setSelectIdVal1(ev,t,repeatNum) {
	$("#wapUrl").val($(t).data("url"));
	$("#selectedMenuId").val(ev);
	$("#repeatNum").val(repeatNum);
	/*var favorite = $(t).parent().find("input[name='favorite']").val();
	if(favorite === "true"){
		$("#operationPOP a:eq(0)").text("取消收藏");
		$("#operationPOP a:eq(0)").attr("onclick","
j,()");
	}else{
		$("#operationPOP a:eq(0)").text("收藏");
		$("#operationPOP a:eq(0)").attr("onclick","importNewsSingleSenior()");
	}*/
	var customFlag = $(t).parent().find("input[name='customFlag']").val();
	if(customFlag == 4){
		$("#operationPOP a:eq(0)").text("屏蔽该条信息");
		$("#operationPOP a:eq(0)").attr("onclick","goNewsDelete()");
	}else{
		$("#operationPOP a:eq(0)").text("屏蔽该条信息");
		$("#operationPOP a:eq(0)").attr("onclick","goNewsDelete()");
	}
}
function stopScroll(){
	$(window).off("scroll resize");
	var $body = $("body"),
		scrollTop = $body.scrollTop();//body设置为fixed之后会飘到顶部，所以要动态计算当前用户所在高度
	$body.css({
		'overflow':'hidden',
		'position': 'fixed',
		'top': scrollTop*-1
	});
}

function startScroll(){
	var $body = $("body");
	/*取消后设置回来*/
	$body.css({
		'overflow':'auto',
		'position': 'static',
		'top': 'auto'
	});
}
$(function(){
	$("#appdownload").width($("body").width());
	$("#appdownloadDiv").height($("#appdownload").height()-1);
})
function openApp(){
	saveOperateLog('首页下载app','1052');
	var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
	if(ua.match(/WeiBo/i) == "weibo"){
		location.href = 'http://d.51wyq.cn';
	}else{
		location.href = 'http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq';
	}

}
function appHide(){
	saveOperateLog('首页关闭app下载','1051');
	$("#appdownload").hide();
	$("#appdownloadDiv").hide();
}
//随机数...
function GetRandom(n) {
	GetRandomn = Math.floor(Math.random() * n + 1);
	return GetRandomn;
}
//加载中...
function chartloading(id){
	var stat = document.getElementById(id);
	stat.innerHTML = '<div class="spinner"><div class="bounce1"></div><div class="bounce2"></div><div class="bounce3"></div></div>';
}
//history.pushState({href:location.href}, location.href,location.href);
//window.onpopstate = function(event){
//	if(document.referrer){
//		if(document.referrer.indexOf("?returning=1&") != -1){
//			location.href = document.referrer.replace("?returning=1&","?");
//		}else if(document.referrer.indexOf("?returning=1") != -1){
//			location.href = document.referrer.replace("?returning=1","");
//		}else if(document.referrer.indexOf("&returning=1") != -1){
//			location.href = document.referrer.replace("&returning=1","");
//		}
//	}
//}

