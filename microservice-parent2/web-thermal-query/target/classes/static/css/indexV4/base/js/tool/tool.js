$(function(){
	var sucaiTotal = $("#sucaiTotal").val();
	var collectTotal = $("#collectTotal").val();
	collectTotal = collectTotal==0?"":collectTotal;
	var cartTotal = $("#cartTotal").val();
	var htmlTool ="<div class=\"tool-menu\">\n" +
		"        <a href=\"javascript:void(0);\">\n" +
		"            <div class=\"btn tool-home\">\n" +
		"                <i class=\"des_icon icon icon-caidan\"></i>\n" +
		"                <span class=\"des_txt_1\"></span>\n" +
		"            </div>\n" +
		"        </a>\n" +
		"        <div class=\"tool_hover\">\n" +
		"            <div style='display:none;'  id=\"commonShare\" class=\"btn btn-fx\">\n" +
		"                <div class=\"pic\">\n" +
		"                    <div class=\"fenxiang\">\n" +
		"                        <div class=\"arrow\"></div>\n" +
		"                        <a href=\"javascript:;\" class=\"icon icon_wb\" title=\"分享到微博\"></a>\n" +
		"                        <a href=\"javascript:;\" class=\"icon icon_wx\" title=\"分享到微信\"></a>\n" +
		"                        <a href=\"javascript:;\" class=\"icon icon_pyq\" title=\"分享到朋友圈\"></a>\n" +
		"                        <a href=\"javascript:;\" class=\"icon icon_qq\" title=\"分享到QQ\"></a>\n" +
		"                        <a href=\"javascript:;\" class=\"icon icon_qqkj\" title=\"分享到QQ空间\"></a>\n" +
		"                    </div>\n" +
		"                </div>\n" +
		"            </div>\n" +
		"            <a href=\'" + actionBase +"/collect.shtml'\">\n" +
		"                <div class=\"btn btn-collector\" >\n" +
		"                    <span id='collect_num' class=\"numBox abs\">"+collectTotal+"</span>\n" +
		"                </div>\n" +
		"            </a>\n" +
		"            <a href=\'"+actionBase+"/exportInfortmation.shtml'\>\n" +
		"                <div  class=\"btn btn-export\"></div>\n" +
		"            </a>\n" +
		"            <a href=\'"+actionBase+"/downLoad.shtml'\>\n" +
		"                <div class=\"btn btn-download\">\n" +
		"                    <img class=\"pic\" src=\"http://app.51wyq.cn/QRCode/wyq_d_entry.png\" style=\"display: none;\" onclick=\"window.location.href=\'javascript:;'\"/>\n" +
		"                </div>\n" +
		"            </a>\n" +
		"            <a href=\'"+actionBase+"/help.shtml'\>\n" +
		"                <div class=\"btn btn-question\"></div>\n" +
		"            </a>\n" +
		"            <div class=\"btn btn-qq\">\n" +
		"                <div class=\"pic\">\n" +
		"                    <a href=\"http://wpa.qq.com/msgrd?v=3&amp;uin=3002436852&amp;site=qq&amp;menu=yes\" class=\"qq_btn\"target=\"\blank\" title=\"客服小舆\">客服小舆</a>\n" +
		"                    <a href=\"http://wpa.qq.com/msgrd?v=3&amp;uin=3002432217&amp;site=qq&amp;menu=yes\" class=\"qq_btn\"target=\"\blank\" title=\"客服小微\">客服小微</a>\n" +
		"                </div>\n" +
		"            </div>\n" +
		"            <a href=\'" + actionBase + "/QAndA/list.shtml'\>\n" +
		"                <div class=\"btn btn-sug\"></div>\n" +
		"            </a>\n" +
		"        </div>\n" +
		"        <a href=\"javascript:void(0);\">\n" +
		"            <div class=\"btn btn-top\"></div>\n" +
		"        </a>\n" +
		"    </div>"
	$("#toolCase").html(htmlTool);
	$(".tool-menu").each(function(){
		$(this).find(".btn-fx").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-fx").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		$(this).find(".btn-qq").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-qq").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		$(this).find(".btn-download").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-download").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		$(this).find(".btn-phone").mouseenter(function(){
			$(this).find(".phone").fadeIn("fast");
		});
		$(this).find(".btn-phone").mouseleave(function(){
			$(this).find(".phone").fadeOut("fast");
		});
		$(this).find(".btn-top").click(function(){
			$("html, body").animate({
				"scroll-top":0
			},"fast");
		});
	});
	var lastRmenuStatus=false;
	$(window).scroll(function(){//bug
		var _top=$(window).scrollTop();
		if(_top>200){
			$(".tool-menu").data("expanded",true);
		}else{
			$(".tool-menu").data("expanded",false);
		}
		if($(".tool-menu").data("expanded")!=lastRmenuStatus){
			lastRmenuStatus=$(".tool-menu").data("expanded");
			if(lastRmenuStatus){
				$(".tool-menu .btn-top").slideDown();
			}else{
				$(".tool-menu .btn-top").slideUp();
			}
		}
	});
	$('.tool-home').on('mouseenter', function () {
		//鼠标移入
		$(".tool_hover").stop().slideDown(400);
	});
	$('#toolCase').on('mouseleave', function () {
		//鼠标移入
		$(".tool_hover").stop().slideUp(400);

	});
});
