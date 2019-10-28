//查询用户已经监测的明星
function hadDY(type,userId){
	StatDwr.getKeywordByReference(userId,type, GetRandom(30), DYCallBack);
}

function DYCallBack(result) {
	if(result!=null&&result.length>0){
		for(var i=0;i<result.length;i++){
			var id = result[i]['id'];
			var keywordId = result[i]['type'];
			$(".dyadd").each(function(){
				var categoryId = $(this).attr('id');
				if(id!=''&&categoryId==id){
					$(this).html(cancelOrderMark);
					$("#keywordId").val(keywordId);
					$(this).attr('onclick','cancelKeyword('+keywordId+')');
				}
			});
		}
	}

}


function existKeyWord(title,id) {
	$("#searchKeyword").val(title);
	$("#categoryId").val(id);
	var searchKeyword = $("#searchKeyword").val();
	$.ajax({
		type : "post",
		url : staticResourcePath + '/view/pay/existKeyWord.action',
		data : {
			searchKeyword : searchKeyword
		},
		cache : false,
		success : function(msg) {
			if (msg == "1") {
				console.log("keywordExistAlert()");
				keywordExistAlert(); //警告框弹出
			} else {
				console.log("pOrdering(id)");
				pOrdering(id); //直接监测
			}
		}
	});
}

//监测
function pOrdering(id) {
	var searchKeyword = $("#searchKeyword").val();
	tempSearchKeyword = searchKeyword;
	$.ajax({
		type : "post",
		url : staticResourcePath + '/view/pay/pOrdering.action',
		data : {
			searchKeyword : searchKeyword,'ctk.categoryId':id
		},
		cache : false,
		success : function(msg) {
			if (msg[1] == "0") {
				var userId = $("#userId").val();
				orderKeywordList(userId);	//监测成功的预警方案
//					$("#replacePOP").fadeIn(300);
//					$(".preplaceframe").addClass('scaleShow');
//					$(".preplaceframe").removeClass('scaleOut');
				doOrder();
			} else {
				dingyueSuccess(id,searchKeyword,msg[1]);

				//前三次引导
				if (parseInt(msg[0]) <= 3) {
					orderSuccess();
				}else{
					orderSuccessAfter();
				}
				$("#keywordId").val(msg[1]);
			}
		}
	});
}

//获取用户的已监测列表
function orderKeywordList(userId){
	if(userId!=null&&userId!=''){
		StatDwr.getOrderKeyWordList(userId, GetRandom(30), OKCallBack);
	}
}

function OKCallBack(result){
	if(result!=null&&result.length>0){
		var ulHtml = "<ul class='dyList'>";
		for(var i=0;i<result.length;i++){
			var liHtml ="";
			if(i==0){
				liHtml = "<li id='"+result[i]["keywordId"]+"' class='li_click' >";
			}else{
				liHtml = "<li id='"+result[i]["keywordId"]+"' >";
			}
			liHtml += "<span>"+result[i]["keywordName"]+"</span><i></i></li>";
			ulHtml += liHtml;
		}
		ulHtml += "</ul>";

		$("section[class=col2]").html(ulHtml);

		orderlist();
	}

}

//动态加载已监测列表后  的 默认选中事件和选中事件
function orderlist(){
	//默认选中的id存起来，待确认调用
	$(".dyList>li").each(function(){
		if($(this).attr('class')=='li_click'){
			var id = $(this).attr('id');
			$("#replaceKeyWordId").val(id);
		}
	});

	//点击选中效果
	$(".dyList>li").on("click",function(){
		$(".dyList>li").removeClass("li_click");
		$(this).addClass("li_click");
		var id = $(this).attr('id');
		$("#replaceKeyWordId").val(id);
	});
}



//监测成功后将监测按钮换成取消监测
function dingyueSuccess(id,searchKeyword,keywordId) {
	if(id>0){	//分类（股票、明星）监测成功
		$(".dyadd").each(function(){
			var dyId = $(this).attr('id');
			if(dyId==id){
				$(this).html(cancelOrderMark);
				$(this).attr("onclick", "cancelKeyword("+keywordId+")");
				$(this).attr("id", "dyCancel");
			}
		});
	}else{	//热门事件监测成功
		$(".dyadd").each(function(){
			var event = $(this).prev('input').val();
			if(event==searchKeyword){
				$(this).html(cancelOrderMark);
				$(this).attr("onclick", "cancelKeyword("+keywordId+")");
				$(this).attr("id", "dyCancel");
			}
		});
	}

}

//替换监测内容成功
function replaceSuccess() {
	//关注列表打开提示层
	var keyword = $("#searchKeyword").val();
	$("#dingyue_keyword").html(keyword);
	$(".zhezhao").fadeIn(300);
	$("#subPOP").fadeIn(300);
//		$("body").css({
//			overflow : "hidden"
//		}); //禁用滚动条
	$(".prompPOP").addClass('scaleShow');
	$(".prompPOP").removeClass('scaleOut');
}

//替换监测
function replaceOrdering() {
	var id = $("#replaceKeyWordId").val();
	var searchKeyword = $("#searchKeyword").val();
	var categoryId = $("#categoryId").val();
	if (id > 0) {
		$.ajax({
			type : "post",
			url : staticResourcePath + '/view/pay/replaceOrdering.action',
			data : {
				keywordId : id,
				searchKeyword : searchKeyword,
				'ctk.categoryId' : categoryId
			},
			cache : false,
			success : function(msg) {
				replaceSuccess();
				$("#keywordId").val(msg);
				$("#type").val(2);
				setTimeout(goSearchById(msg), 1000);
			}
		});
	}
}

//取消监测替换的那个弹窗
function cancelOrder() {
	$("#replacePOP").css('display', 'none');
}

//随机数
function GetRandom(n) {
	GetRandomn = Math.floor(Math.random() * n + 1);
	return GetRandomn;
}

//取消监测
function cancelKeyword(keywordId) {
	$("#keywordId").val(keywordId);
	$(".zhezhao").fadeIn(300);
	$("#cancelKeywordPOP").fadeIn(300);
//		$("body").css({
//			overflow : "hidden"
//		}); //禁用滚动条
	$(".prompPOP").addClass('scaleShow');
	$(".prompPOP").removeClass('scaleOut');
}

function doCancelKeyword() {
	if ($('#keywordId').val() != '')
		location.href = staticResourcePath
			+ '/manage/keyword/cancelKeyword.action?keywordId='
			+ $('#keywordId').val() + "&cancelMark=1";
}

//已存在关键词警告框
function keywordExistAlert(keywordId) {
	$(".zhezhao").fadeIn(300);
	$("#existsKeyWordPOP").fadeIn(300);
	$(".prompPOP").addClass('scaleShow');
	$(".prompPOP").removeClass('scaleOut');
}


function goSearchByCategoryId(id,type){
	$('#timeDomain').val(7);	//默认一周
	$("#filterOrigina").val(0);	//默认类别为全部
	$("#type").val(type);
	location.href = staticResourcePath+"/goResultList.shtml?ctk.categoryId="+id+"&type="+type;
}

