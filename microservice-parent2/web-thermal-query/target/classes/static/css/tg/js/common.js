var flag=false,winH,winW,len,tt;
//window.addEventListener( "load", init, false );

function init( event ) {
	setTimeout(function(){
		$("#loading").animate({top:-winH},600);
		//addImg();
		setTimeout(function(){
			arrow();
		},600);
	},600);
};


window.onload=function(){
	winH=$(window).height();
	winW=$(window).width();
	$(".page").height(winH);
	$(".page").width(winW);
	len=$(".page").length;
	init();
}



window.addEventListener("orientationchange", function(){
	if (window.orientation == 90 || window.orientation == -90) {
		alert("请使用竖屏浏览，以获得更好的操作体验！");
		return false;
	}else if(window.orientation == 0 || window.orientation == 180){
		window.location.reload();
	}
}, false);

$(function(){
	winH=$(window).height();
	winW=$(window).width();
	len=$(".page").length;
	//滑动
	$(".page").touchwipe({
		wipeUp:function(){
			goPrev();
		},wipeDown:function(){
			goNext();
		},
		preventDefaultEvents: true
	});
	flag=true;
	
	//判断打开设备是手机还是电脑
	var urlhash = window.location.hash;  
	if (!urlhash.match("fromapp")){  
		if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i))) {  
			var conH=winW/640*1100;
			var scale=winH/conH;
			var top=(winH-1100)/2;
			if(winW<conH){
				//alert(winH+"====="+conH+"====="+scale+"====="+top);
				/*$(".page .con").css({transform:"scale("+scale+")","-webkit-transform":"scale("+scale+")",top:top,height:winH-top});*/
				/*$(".page .con .case").css({transform:"scale("+1/scale+")","-webkit-transform":"scale("+1/scale+")",bottom:top});*/
			}
		}/*else{
			$("body").hide();
			alert("请使用手机浏览！");
		}*/
	}  	
});

function goNext(){
	$("img.arrow").attr("class","stop");
	var index=$(".page.show").index();
	var cur=$(".page.show");
	var next=$(".page").eq(index+1);
	if(index < len-1 && flag){
		flag=false;
		next.find(".con span").not('.case').css({top:winH,opacity:0});
		next.removeClass("hide").addClass("active").css({top:winH});
		$(".page.active").animate({top:0},600);
		setTimeout(function(){
			setTimeout(function(){next.find("span.p1").animate({top:0,opacity:1},700);},200);
			setTimeout(function(){
				next.find("span.p2").animate({top:0,opacity:1},700,function(){arrow();});
			},300);
			setTimeout(function(){
				next.find("span.p3").animate({top:0,opacity:1},700,function(){arrow();});
			},500);
			setTimeout(function(){
				next.find("span.p4").not(".cloudb").animate({top:0,opacity:1},700,function(){arrow();});
			},700);
			setTimeout(function(){
				next.find("span.p5").animate({top:0,opacity:1},700,function(){arrow();});
			},900);
			setTimeout(function(){
				next.find("span.p6").animate({top:0,opacity:1},700,function(){arrow();});
			},1100);
			setTimeout(function(){
				next.find("span.p7").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				next.find("span.p8").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				next.find("span.p9").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				next.find("span.p10").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			cur.removeClass("show").addClass("hide");
			next.removeClass("active").addClass("show");
			flag=true;
			//cloud词云
			setTimeout(function(){
				next.find("span span").animate({top:0,opacity:1},700,function(){arrow();});
			}, 1300);
			setTimeout(function(){
				//$(".cutl").stop().animate({"opacity":"1","top":"100px"},700,function(){$(".cloudb").stop().animate({"opacity":"1","top":"170px","left":"190px"},700)});
			}, 1300);
		},600);
	}else if(index >= len-1 && flag){
		flag=false;
		$(".page").eq(0).find(".con span").not('.case').css({top:winH,opacity:0});
		$(".page").eq(0).removeClass("hide").addClass("active").css({top:winH});
		$(".page.active").animate({top:0},600);
		setTimeout(function(){
			setTimeout(function(){$(".page").eq(0).find("span.p1").animate({top:0,opacity:1},700);},200);
			setTimeout(function(){
				$(".page").eq(0).find("span.p2").animate({top:0,opacity:1},700,function(){arrow();});
			},300);
			setTimeout(function(){
				$(".page").eq(0).find("span.p3").animate({top:0,opacity:1},700,function(){arrow();});
			},500);
			setTimeout(function(){
				$(".page").eq(0).find("span.p4").not(".cloudb").animate({top:0,opacity:1},700,function(){arrow();});
			},700);
			setTimeout(function(){
				$(".page").eq(0).find("span.p5").animate({top:0,opacity:1},700,function(){arrow();});
			},900);
			setTimeout(function(){
				$(".page").eq(0).find("span.p6").animate({top:0,opacity:1},700,function(){arrow();});
			},1100);
			setTimeout(function(){
				$(".page").eq(0).find("span.p7").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(0).find("span.p8").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(0).find("span.p9").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(0).find("span.p10").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			$(".page").eq(index).removeClass("show").addClass("hide");
			$(".page").eq(0).removeClass("active").addClass("show");
			flag=true;
			//cloud词云
			setTimeout(function(){
				$(".page").eq(0).find("span span").animate({top:0,opacity:1},700,function(){arrow();});
			}, 1300);
		},600);
	}
}
function goPrev(){
	$("img.arrow").attr("class","stop");
	var index=$(".page.show").index();
	var cur=$(".page.show");
	var prev=$(".page").eq(index-1);
	$(".arrow").addClass("cur");
	setTimeout(function(){$(".arrow").addClass("stop").removeClass("cur");},3000);
	setTimeout(function(){$(".arrow").attr("class","arrow");},3200);
	if(index>0 && flag){
		flag=false;
		prev.find(".con span").not('.case').css({top:winH,opacity:0});
		prev.removeClass("hide").addClass("active").css({top:-winH});
		$(".page.active").animate({top:0},600);
		setTimeout(function(){
			setTimeout(function(){prev.find("span.p1").animate({top:0,opacity:1},700);},200);
			setTimeout(function(){
				prev.find("span.p2").animate({top:0,opacity:1},700,function(){arrow();});
			},300);
			setTimeout(function(){
				prev.find("span.p3").animate({top:0,opacity:1},700,function(){arrow();});
			},500);
			setTimeout(function(){
				prev.find("span.p4").not(".cloudb").animate({top:0,opacity:1},700,function(){arrow();});
			},700);
			setTimeout(function(){
				prev.find("span.p5").animate({top:0,opacity:1},700,function(){arrow();});
			},900);
			setTimeout(function(){
				prev.find("span.p6").animate({top:0,opacity:1},700,function(){arrow();});
			},1100);
			setTimeout(function(){
				prev.find("span.p7").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				prev.find("span.p8").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				prev.find("span.p9").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				prev.find("span.p10").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			cur.removeClass("show").addClass("hide");
			prev.removeClass("active").addClass("show");
			flag=true;
			//cloud词云
			setTimeout(function(){
				prev.find("span span").animate({top:0,opacity:1},700,function(){arrow();});
			}, 1300);
		},600);
	}else if(index<=0 && flag){
		flag=false;
		$(".page").eq(len-1).find(".con span").not('.case').css({top:winH,opacity:0});
		$(".page").eq(len-1).removeClass("hide").addClass("active").css({top:-winH});
		$(".page.active").animate({top:0},600);
		setTimeout(function(){
			setTimeout(function(){$(".page").eq(len-1).find("span.p1").animate({top:0,opacity:1},700);},200);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p2").animate({top:0,opacity:1},700,function(){arrow();});
			},300);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p3").animate({top:0,opacity:1},700,function(){arrow();});
			},500);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p4").not(".cloudb").animate({top:0,opacity:1},700,function(){arrow();});
			},700);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p5").animate({top:0,opacity:1},700,function(){arrow();});
			},900);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p6").animate({top:0,opacity:1},700,function(){arrow();});
			},1100);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p7").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p8").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p9").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			setTimeout(function(){
				$(".page").eq(len-1).find("span.p10").animate({top:0,opacity:1},700,function(){arrow();});
			},1300);
			$(".page").eq(index).removeClass("show").addClass("hide");
			$(".page").eq(len-1).removeClass("active").addClass("show");
			flag=true;
			//cloud词云
			setTimeout(function(){
				$(".page").eq(len-1).find("span span").animate({top:0,opacity:1},700,function(){arrow();});
			}, 1300);
		},600);
	}
}
function arrow(){
	$("img.stop").attr("class","arrow");
	$("img.arrow").addClass("cur").show();
}
function left(){
	$("img.stop").attr("class","left");
	$("img.left").addClass("cur").show();
}