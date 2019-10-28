var timer;
var clickFlag = 0;
var keysetArray= new Array();
function init() {
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	var offsetWidth = document.body.offsetWidth;
	var offsetHeight = document.body.offsetHeight;
	var scrollWidth = document.body.scrollWidth;
	var scrollHeight = document.body.scrollHeight;
	var scrillTop = document.body.scrollTop;
	var scrollLeft = document.body.scrollLeft;
	var screenTop = window.screenTop;
	var screenLeft = window.screenLeft;
	var screenHeight = window.screen.height;
	var screenWidth = window.screen.width;
	var screenAvailHeight = window.screen.availHeight;
	var screenAvailWidth = window.screen.availWidth;
}
window.onresize = function() {
	if ($("#SystemSetDiv").css("display") == "block") {
		showSystemSet();
	}
	if ($("#InfoDiv").css("display") == "block") {
		var msg = $("#msg").text();
		var style = $("#style").val();
		showInfoDiv(msg, style, null, 0);
	}
	if ($("#SmsDiv").css("display") == "block") {
		// hideSmsDiv();
	}
}
function showBgDiv() {
	var scrollWidth = document.body.scrollWidth;
	var scrollHeight = document.body.scrollHeight;
	$("#BgDiv").css({
		height : scrollHeight,
		width : scrollWidth,
		display : "block",
		zIndex : 50
	});
}
function hideBgDiv() {
	$("#BgDiv").css({
		display : "none"
	});
}

function showSystemSet() {
	window.search_set.src = "searchset.action?actionInfo='search'";
	window.search_set.location = "searchset.action?actionInfo='search'";
	showBgDiv();
	var screenWidth = window.screen.width;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $("#SystemSetDiv").height();
	var reWidth = (screenWidth - divH) / 2;
	$("#SystemSetDiv").css({
		display : "block",
		top : "200px",
		left : reWidth,
		zIndex : 50,
		position : "absolute",
		backgroundColor : "#FFF",
		width : 700
	});
}
function hideSystemSet() {
	hideBgDiv();
	$("#SystemSetDiv").css({
		display : "none"
	});
	window.search_set.src = "";
}

function jump(count, url) {
	timer = window.setTimeout(function() {count--;
		if (count > 0) {
			$("#submitBtn").html(
					"<span style='font-size:12px;'> " + count
							+ " </span>秒后跳转");
			jump(count, url);
		} else {
			if (url != null) {
				window.location.href = url;
			} else {
				$("#submitBtn").html("确定");
				hideInfoDiv();
			}
		}
	}, 1000);
}
function showInfoDiv(msg, style, src, type) {
	showBgDiv();
	var screenWidth = window.screen.width;
	var clientWidth = document.body.clientWidth;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $("#InfoDiv").height();
	var divW = $("#InfoDiv").width();
	var reWidth = (clientWidth - divW) / 2;
	var clientHeight = document.body.clientHeight;
	var topH = (clientHeight - divH) / 2
	$(".cancelBtn").css({
		display : "",
		margin : "0",
		marginLeft : "5px"
	});
	$(".submitBtn").css({
		display : "",
		margin : "0",
		marginRight : "5px"
	});
	$('.submitBtn').unbind("click"); // 移除click
	if (style == 0) {
		// 只显示确定按钮
		$(".cancelBtn").css({
			display : "none"
		});
		$(".submitBtn").css({
			margin : "5px 75px"
		});
		$(".submitBtn").one('click', function() {
			hideInfoDiv();
		});
	} else if (style == 2) {
		// 继续或者跳转
		$(".cancelBtn").css({
			display : "",
			margin : "0",
			marginLeft : "5px"
		});
		$(".submitBtn").css({
			display : "",
			margin : "0",
			marginRight : "5px"
		});
		$('.submitBtn').html("<span style='font-size:12px;'>3</span>秒后跳转");
		if (type == 0) {
			$('.cancelBtn').html("继续");

			$('.cancelBtn').click(function() {
				$('.submitBtn').html("确定");
				clearTimeout(timer);
				hideInfoDiv();
			});

		} else if (type == 1) {
			$('.cancelBtn').html("去收藏夹");
			$('.cancelBtn').click(function() {
				window.location.href = "collectNewsList.action";
			});

		} else if (type == 2) {
			$('.cancelBtn').html("去素材库");
			$('.cancelBtn').click(function() {
				window.location.href = "briefListSearch.action?flag=3";
			});
		}

		jump(3, src);
		$(".submitBtn").one('click', function() {
			hideInfoDiv();
		});
	} else if (style == 1) {
		// 继续或者跳转
		$(".cancelBtn").css({
			display : "",
			margin : "0",
			marginLeft : "5px"
		});
		$(".submitBtn").css({
			display : "",
			margin : "0",
			marginRight : "5px"
		});
		if (type == 1) {
			$('.submitBtn').html("去购买");
			$(".submitBtn").one('click', function() {
				window.location.href = src;
			});
		} else if (type == 2) {
			$(".submitBtn").one('click', function() {
				window.location.href = src;
			});
		}
	}
	$('.info_close,.cancelBtn').click(function() {
		hideInfoDiv();
	});
	$("#InfoDiv").css({
		display : "block",
		top : topH,
		left : reWidth
	});
	$("#msg").html(msg);
	$("#style").val(style);
}
// 隐藏消息弹出层
function hideInfoDiv() {
	hideBgDiv();
	$("#InfoDiv").css({
		display : "none"
	});
}

// 跳转到某一个action
function showMsgInfoToAction(msg_type, msg, style, src) {
	if (msg_type == 0) {
		showInfoDiv(msg, style, src, 0);
	}
}

// 跳转简报素材或者收藏夹列表 type为1时跳到收藏夹列表 2时跳到简报素材列表
function showMsgInfoByType(msg_type, msg, style, src, type) {
	if (msg_type == 0) {
		showInfoDiv(msg, style, src, type);
	}
}

// 新提示弹出层
/*
 * msg_type = 0 普通消息
 * 
 * stype = 1 ;确定、取消
 * 
 * stype = 0 ;确定
 * 
 * importType =1 素材库 importType =2 收藏夹
 */
function showMsgInfo(msg_type, msg, style) {
	if (msg_type == 0) {
		showInfoDiv(msg, style, null, 0);
	}
}
// 标记成功状态
function CallBackSenior(result) {
	var res;
	clickFlag = 0;
	if (null == result || "" == result) {
		showMsgInfo(0, "请不要重复添加！", 0);
		return;
	} else {
		res = eval('(' + result + ')');
	}
	var count = res.count;
	//var type = res.type;
	//var flag = res.flag;
	var ids = res.ids;
	var collectNums = res.collectNum;
	//ids = ids.substring(0, ids.length - 1);
	var idstr = ids.split(",");
	var silderRightDiv = $("#izl_rmenu", parent.window.document);
	if (parent.document.getElementById("izl_rmenu") == null) {
		silderRightDiv = $("#izl_rmenu", parent.parent.window.document);
	}
	var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find(
			"span"); // 获取右侧素材 对象
	var countCollectObj = $(silderRightDiv)
			.find("div[class='btn btn-scj rel']").find("span"); // 获取右侧收藏 对象
	//if (null != type && type == 1) {
		// showMsgInfoByType(0,"已成功加入收藏夹!",2,null,1);
		// var ids = res.ids;
		// var idstr = ids.split(",");
		$(countCollectObj).css({
			'visibility' : 'visible'
		});
		var collectNum = $(countCollectObj).text() == "" ? 0 : $(
				countCollectObj).text();
		$(countCollectObj).html(collectNums);

		$('#addC').css('display', 'none');
		$('#clearC').css('display', 'inline-block');

		if (document.getElementById("addC") == null) {
			for (var i = 0; i < idstr.length; i++) {
				var id = idstr[i];
				var cl = "iclt_" + id;
				var ce = "icltok_" + id;
				// var topF = window.frames["top_ifrmview"];
				// if(!topF){
				// topF = window.frames["ifrmview"];
				// }else{
				// topF = topF.frames["iframe"];
				// topF = topF.frames["ifrmview"];
				// }
				var iclt;
				var icltok;
				// if(topF && topF!='undifend'){
				// iclt = topF.document.getElementById(cl);
				// icltok = topF.document.getElementById(ce);
				// }else{
				// iclt = document.getElementById(cl);
				// icltok = document.getElementById(ce);
				// }
				iclt = document.getElementById(cl);
				if (iclt == null) {
					iclt = window.frames["ifrmview"].document
							.getElementById(cl);
				}
				icltok = document.getElementById(ce);
				if (icltok == null) {
					icltok = window.frames["ifrmview"].document
							.getElementById(ce);
				}
				// var iclt =
				// window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(cl);
				// var icltok =
				// window.frames["top_ifrmview"].frames["ifrmview"].document.getElementById(ce);
				if (iclt && icltok) {
					if (iclt.style.display == "") {
						iclt.style.display = "none";
						icltok.style.display = "";
					}
				}
			}
		}

		$(".addCollect").bind('click', addCollect);

//	} else if (null != type && type == 2) {
//		// showMsgInfoByType(0,"已成功加入简报素材，请进入舆情简报栏目中创建您的简报！",2,null,2);
//		var sucaiNum = $(".sucaiNum").html();
//		if (flag == 0) {
//			$(".sucaiNum").html(parseInt(sucaiNum) + parseInt(count));
//		} else if (flag == 1) {
//			$(".sucaiNum").html(parseInt(sucaiNum) - parseInt(count));
//		}
//		if ($(".tipfloat").height() == 0) {
//			$(".tipfloat").css("height", "48px");
//			$(".tipfloat").animate({
//				height : "show"
//			}, 400);
//		}
//		if ($(".sucaiNum").html() < 0) {
//			$(".sucaiNum").html(0);
//		}
//		for (var i = 0; i < idstr.length; i++) {
//			var id = idstr[i];
//			var cl = "sc_" + id;
//			var ce = "scok_" + id;
//
//			var iclt = document.getElementById(cl);
//			var icltok = document.getElementById(ce);
//
//			if (iclt && icltok) {
//				if (iclt.style.display == "") {
//					iclt.style.display = "none";
//					icltok.style.display = "";
//
//				} else if (iclt.style.display == "none") {
//					iclt.style.display = "";
//					icltok.style.display = "none";
//				}
//			}
//		}
//	}

}

function operateNews(checkedIds) {
	// 高级版
	if (clickFlag == 0) {
		clickFlag = 1;
		NewsOperate.operateNewsSenior(checkedIds, CallBackSenior);
	}
}

// 内容多条导入收藏夹或素材库（新）
function importNewsSenior(importNewType) {
	if (importNewType == 2) {
		importToName = "简报素材";
		importType = 2;

	} else if (importNewType == 1) {
		importToName = "收藏夹";
		importType = 1;
	}
	var elements = document.getElementsByTagName("input");
	var checkedIds = "";
	for (var i = 0; i < elements.length; i++) {
		var e = elements[i];
		if (e.id == "menuCheckId") {
			if (e.checked == true) {
				checkedIds = checkedIds + e.value + ",";
			}
		}
	}
//	var silderRightDiv = $("#izl_rmenu", parent.window.document);
//	if (parent.document.getElementById("izl_rmenu") == null) {
//		silderRightDiv = $("#izl_rmenu", parent.parent.window.document);
//	}
//	var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span"); // 获取右侧收藏 对象
//	var collectNum = $(countCollectObj).text() == "" ? 0 : $(countCollectObj).text();
//	var checkNum = checkedIds.split(",").length;
//	if (importNewType == 1 && (parseInt(collectNum) + checkNum) > 1001) {
//		$("#cancelBtn").html("确认");
//		$("#submitBtn").html("去收藏夹");
//
//		showMsgInfo(0, "失败啦，收藏夹只提供1000条信息加入哦！", 1);
//		$("#submitBtn").one('click', function() {
//			location.href = njxBasePath + "/collect.shtml";
//		});
//		return;
//	}
	var checkId;
	var customFlag1 = '';
	checkId = checkedIds.split(",");
	for (var i = 0; i < checkId.length; i++) {
		if (document.getElementById("flag_" + checkId[i].split("_")[0])) {
			customFlag1 += document.getElementById("flag_"
					+ checkId[i].split("_")[0]).value
					+ ',';
		}
	}
	operateNews(checkedIds);
}

// 内容单条导入收藏夹或素材库(新)2
function importNewsSingleSenior(importNewType) {
	if (importNewType == 2) {
		importToName = "简报素材";
		importType = 2;
	} else if (importNewType == 1) {
		importToName = "收藏夹";
		importType = 1;
	}
	var checkedId = document.getElementById("selectedId").value;
//
//	var silderRightDiv = $("#izl_rmenu", parent.window.document);
//	if (parent.document.getElementById("izl_rmenu") == null) {
//		silderRightDiv = $("#izl_rmenu", parent.parent.window.document);
//	}
//	var countCollectObj = $(silderRightDiv)
//			.find("div[class='btn btn-scj rel']").find("span"); // 获取右侧收藏 对象
//	var collectNum = $(countCollectObj).text() == "" ? 0 : $(countCollectObj)
//			.text();
//	var checkNum = checkedId.split(",").length;
//	if (importNewType == 1 && (parseInt(collectNum) + checkNum) > 1001) {
//		$("#cancelBtn").html("确认");
//		$("#submitBtn").html("去收藏夹");
//		showMsgInfo(0, "失败啦，收藏夹只提供1000条信息加入哦！", 1);
//		$("#submitBtn").one('click', function() {
//			location.href = njxBasePath + "/collect.shtml";
//		});
//		return;
//	}
//	var checkId;
	operateNews(checkedId);
}

// 短信发送
function showSmsDiv(src) {
	document.getElementById("send_sms_frame").contentWindow.document.body.innerText = "";
	window.send_sms_frame.src = src;
	window.send_sms_frame.location = src;
	showBgDiv();
	var screenWidth = window.screen.width;
	var clientWidth = document.body.clientWidth;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $("#SmsDiv").height();
	var divW = $("#SmsDiv").width();
	var reWidth = (clientWidth - divW) / 2;
	var clientHeight = document.body.clientHeight;
	var topH = (clientHeight - divH) / 2
	$("#SmsDiv").css({
		display : "block",
		left : reWidth,
		top : topH,
		zIndex : 100,
		position : "fixed"
	});
}

function hideSmsDiv() {
	hideBgDiv();
	$("#SmsDiv").css({
		display : "none"
	});
}

function hideMailFrame() {
	hideBgDiv();
	$("#InfoDiv2").fadeOut("slow");
	$("body").css({
		overflow : "auto"
	}); // 启用滚动条
}

function showEditBSDiv(src) {
	window.edit_bs_frame.src = src;
	window.edit_bs_frame.location = src;
	showBgDiv();
	var screenWidth = window.screen.width;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $("#EditBSDiv").height();
	var divW = $("#EditBSDiv").width();
	var reWidth = (screenWidth - divW) / 2;
	var clientHeight = document.body.clientHeight;
	var topH = (clientHeight - divH) / 2
	$("#EditBSDiv").css({
		display : "block",
		left : reWidth,
		top : topH,
		zIndex : 100,
		position : "fixed",
		backgroundColor : "#FFF"
	});
}

function hideEditBSDiv() {
	hideBgDiv();
	$("#EditBSDiv").css({
		display : "none"
	});
}

// 多层弹出框 style 为第几层弹出框
function showInfoDiv2(msg, style) {
	showBgDiv();
	var screenWidth = window.screen.width;
	var screenAvailWidth = window.screen.availWidth;
	var divH = $("#InfoDiv").height();
	var divW = $("#InfoDiv").width();
	var reWidth = (screenWidth - divW) / 3;
	var clientHeight = document.body.clientHeight;
	var topH = (clientHeight - divH) / 3;
	$("#InfoDiv").css({
		display : "block",
		left : reWidth,
		top : topH,
		zIndex : 100,
		position : "fixed",
		backgroundColor : "#FFF"
	});

	if (style == 2) {
		//只显示确定按钮
		$("#InfoDiv .submitBtn").css({
			margin : "5px 75px"
		});
		$("#InfoDiv .cancelBtn").css({
			display : 'none'
		})
		$('.submitBtn,.info_close').click(function() {
			hideInfoDiv();
		});
	}
	$("#msg").html(msg);
}