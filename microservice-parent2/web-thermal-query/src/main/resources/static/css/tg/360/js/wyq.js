
//nav
//function navLine(){
//var endX=$(window).width();
//var startX=0;
//var centerX=endX/2;
//var circleR=85;
//var lineY=60;
//var line= Snap("#nav #svg");
//var path="M"+startX+" "+lineY+" L"+(centerX-circleR)+" "+lineY+"A"+circleR*1.1+" "+(circleR)*1.1+" "+"1 0 0 "+(centerX+circleR)+" "+lineY+" L"+endX+" "+lineY;
//	$("#svg #line ").remove();
//	line.path(path).attr({
//		strokeWidth:2,
//		stroke:"#1885e8",
//		fill:"#3a3a45",
//		id:"line"
//	});
//
//}
//navLine();
//$(window).resize(function(){
//navLine();
//});
//
//;

//case
/*casethree点击长图*/
$("#case .casethree .car img").click(function(){
	$("#case").removeClass("active");
	$("#casemask").show();

});

$("#casemask").mouseover(function(){
	$(this).css({cursor:"url(src/img/close.png),auto"});	
}).click(function(){
	$("#case").addClass("active");
	$(this).hide();
});

/*casethree点击长图 end*/


/* casetwo*/
$(".tabName span").click(function(){
	$(this).addClass("active").siblings("span").removeClass("active");
	var target=$(this).attr("data-target");
	$(".container .media").hide();
	$(".container .video").hide();
	$(".container ."+target).addClass("active").show();

});




$("#qqchat").mouseover(function(){
	
	$(this).find("img").attr("src","src/img/qqjiaotan2.png");
}).mouseleave(function(){
	$(this).find("img").attr("src","src/img/qqjiaotan.png");
});







;
//caseround
	$("#case .case").click(function(){

			var on=$("#case .focuson"),
				left=$("#case .left"),
				right=$("#case .right");

			if($(this).hasClass("focuson")){
					console.log("focuson");
			}

			if($(this).hasClass("left")){	
					$(this).removeClass("left").addClass("focuson");
					on.removeClass("focuson").addClass("right");
					right.removeClass("right").addClass("left");

			}


			if($(this).hasClass("right")){
					$(this).removeClass("right").addClass("focuson");
					on.removeClass("focuson").addClass("left");
					left.removeClass("left").addClass("right");
			}



	});

;
$(function(){

//灰彩切换
$(".mainWrap .spread").mouseover(function(){
	$(this).find(".assay").addClass("active");
	$(this).find(".bg").css({
		background:"url(src/img/productld.png) right center no-repeat"
	});
	var events=$(".mainWrap .event");
		events.find(".assay").removeClass("active");
		events.find(".bg").css({
			background:"url(src/img/productrd.png) left center no-repeat"
		});

});

$(".mainWrap .event").mouseover(function(){
	$(this).find(".assay").addClass("active");
	$(this).find(".bg").css({
		background:"url(src/img/productr.png) left center no-repeat"
	});
	var spread=$(".mainWrap .spread");
		spread.find(".assay").removeClass("active");
		spread.find(".bg").css({
			background:"url(src/img/productl.png) right center no-repeat"
		});

});
//灰彩切换end



/*$(".mainWrap .zhiweiDs .img").click(function(){
	var mask=$("#mask");
	    mask.find(".longImage img").attr("src","src/img/eventfull.png");//替换图片
		mask.show();
	$("#product").removeClass("active");
});


*/


/*

$("#mask").mouseover(function(){
	$(this).css({cursor:"url(src/img/close.png),auto"});
}).click(function(){
	$(this).hide();
	$("#product").addClass("active");
});
*/

});;
//aboutus

$("#yuxiao").mouseover(function(){
	$("#ceo").show(function(){
		$(this).css({opacity:1});
	});
}).mouseleave(function(){
	$("#ceo").hide(function(){
		$(this).css({opacity:0});
	});
});


$("#shenhao").mouseover(function(){
	$("#manager").show(function(){
		$(this).css({opacity:1});
	});
}).mouseleave(function(){
	$("#manager").hide(function(){
		$(this).css({opacity:0});
	});
});


$("#duanyanfeng").mouseover(function(){
	$("#assistant").show(function(){
		$(this).css({opacity:1});
	});
}).mouseleave(function(){
	$("#assistant").hide(function(){
		$(this).css({opacity:0});
	});
});


$("#research").mouseover(function(){
	$("#support").show(function(){
		$(this).css({opacity:1});
	});
}).mouseleave(function(){
	$("#support").hide(function(){
		$(this).css({opacity:0});
	});
});



;
$('#fullpage').fullpage({
			anchors: ["a","g","b","c","d","e","f","h"],
			menu:"#nav",
			slidesNavigation:true,
			navigation: true,
			navigationPosition: 'right',
			onLeave: function(index, nextIndex, direction){
				var leavingSection = $(this);
				if(index && nextIndex==1){
				    $("#nav").removeClass("active");
				    $(".turnPage .up").hide();
				    $(".turnPage .down").show();
				}else if(index && nextIndex==5){
					$(".turnPage .up").show();
				    $(".turnPage .down").hide();
				}else{
				     $("#nav").addClass("active"); 
				     $(".turnPage .up").show();
				     $(".turnPage .down").show(); 
				}

				}
			});

			//漂浮物
			$('#logo').parallax();
			$('#aboutUs').parallax();
			$('#product .decorations').parallax();
			$('#poster2 .decorations').parallax();