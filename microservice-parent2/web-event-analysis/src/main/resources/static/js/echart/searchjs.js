// JavaScript Document
//搜索框显示隐藏清除按钮
$(function(){
	$("#searchKeyword").on("input",function(){
		var t=$(this).val();
		if(t==""){
			$("#hotwords").show();
			$("#searchHelp").hide();
		}else{
			searchHelp(t);
		}
		$("#emCancel").show();
	});

	$(".comWord").on("input",function(){
		var t=$(this).val();
		if(t==""){
			$(".compUl").next().hide();
			$("#emCancel1").hide();
			$("#goSearch").show();
		}else{
			searchHelp1($(this));
		}
	});

	$("#goSearch").on("click",function(){
		var searchKeyword1 = $.trim($("#searchKeyword1").val());
		var searchKeyword2 = $.trim($("#searchKeyword2").val());
		var searchKeyword3 = $.trim($("#searchKeyword3").val());

		if(!searchKeyword1 && !searchKeyword2 && !searchKeyword3){
			swal("请输入内容");
			return false;
		}
		if(searchKeyword1.length == 1 || searchKeyword2.length == 1 ||
			searchKeyword3 == 1){
			swal("对比词至少输入2个字");
			return false;
		}
		if((searchKeyword1 && searchKeyword1 == searchKeyword2) ||
			(searchKeyword1 && searchKeyword1 == searchKeyword3) ||
			(searchKeyword2 && searchKeyword2 == searchKeyword3)){
			swal("对不起,重复的内容对比会浪费您的使用次数,请修改关键词后重新提交!");
			return false;
		}
		var i = 0,keywordstr1 = "",keywordstr2 = "",keywordstr3 = "",keywordsJson={date:$("#hotsearchdate").val()};
		if($.trim($("#searchKeyword1").val())){
			keywordstr1 = searchKeyword1;
			keywordsJson.title1 = searchKeyword1;
			keywordsJson.categoryId1 = $.trim($("#categoryId1").val());
			keywordsJson.type1 = $.trim($("#type1").val());
			if($.trim($("#keyword1").val())){
				keywordstr1 = $.trim($("#keyword1").val())+"_"+searchKeyword1;
				keywordsJson.keyword1 = $.trim($("#keyword1").val());
			}
			if($.trim($("#filterKeyword1").val())){
				keywordstr1 = $.trim($("#keyword1").val())+"_"+searchKeyword1+"_"+$.trim($("#filterKeyword1").val());
				keywordsJson.filterKeyword1 = $.trim($("#filterKeyword1").val());
			}
			//$("#keywords").val(keywordstr1);
			i++;
		}
		if($.trim($("#searchKeyword2").val())){
			if(i>0){
				keywordstr2 = searchKeyword2;
				keywordsJson.title2 = searchKeyword2;
				keywordsJson.categoryId2 = $.trim($("#categoryId2").val());
				keywordsJson.type2 = $.trim($("#type2").val());
				if($.trim($("#keyword2").val())){
					keywordstr2 = $.trim($("#keyword2").val())+"_"+searchKeyword2;
					keywordsJson.keyword2 = $.trim($("#keyword2").val());
				}
				if($.trim($("#filterKeyword2").val())){
					keywordstr2 = $.trim($("#keyword2").val())+"_"+searchKeyword2+"_"+$.trim($("#filterKeyword2").val());
					keywordsJson.filterKeyword2 = $.trim($("#filterKeyword2").val());
				}
				//$("#keywords").val( $("#keywords").val()+","+keywordstr2);
			}else{
				//$("#keywords").val(keywordstr2);
				keywordsJson.title1 = searchKeyword2;
				keywordsJson.keyword1 = $.trim($("#keyword2").val());
				keywordsJson.filterKeyword1 = $.trim($("#filterKeyword2").val());
				keywordsJson.categoryId1 = $.trim($("#categoryId2").val());
				keywordsJson.type1 = $.trim($("#type2").val());
			}
			i++;
		}
		if($.trim($("#searchKeyword3").val())){
			if(i>0){
				keywordstr3 = searchKeyword3;
				keywordsJson.title3 = searchKeyword3;
				keywordsJson.categoryId3 = $.trim($("#categoryId3").val());
				keywordsJson.type3 = $.trim($("#type3").val());
				if($.trim($("#keyword3").val())){
					keywordstr3 = $.trim($("#keyword3").val())+"_"+searchKeyword3;
					keywordsJson.keyword3 = $.trim($("#keyword3").val());
				}
				if($.trim($("#filterKeyword3").val())){
					keywordstr3 = $.trim($("#keyword3").val())+"_"+searchKeyword3+"_"+$.trim($("#filterKeyword3").val());
					keywordsJson.filterKeyword3 = $.trim($("#filterKeyword3").val());
				}
				//$("#keywords").val( $("#keywords").val()+","+keywordstr3);
			}else{
				//$("#keywords").val(keywordstr3);
				keywordsJson.title1 = searchKeyword3;
				keywordsJson.keyword1 = $.trim($("#keyword3").val());
				keywordsJson.filterKeyword1 = $.trim($("#filterKeyword3").val());
				keywordsJson.categoryId1 = $.trim($("#categoryId3").val());
				keywordsJson.type1 = $.trim($("#type3").val());
			}
			i++;
		}
		if(i>=2){
			if(!$("#adminId").val() && !$("#platform").val()){
				location.href = actionBase+"/indexLocal.shtml";
				return false;
			}
			$("#keywords").val(JSON.stringify(keywordsJson));
			$("#buyPrompt_jp13 input[name='packageCount']").val(i);
			showBuy({type:13});
			return false;
		}else{
			goSearchLoad();
		}
	});

});
function goSearch(that,time){
	console.log("---goSearch");
	if(time==24){
		var hot_time=document.getElementById("hot_time")
		hot_time.innerText="24小时"
	}else if(time==72){
		hot_time.innerText="72小时"
	}
	$(that).css({"background":"#fff","color":"#fd8c25"});
	$(that).siblings().css({"color":"#fff","background":"none"});
	$("#hotsearchdate").val(time);
	$("#searchKeyword1").val($("#searchKeyword1")[0].defaultValue);
	$("#searchKeyword2").val($("#searchKeyword2")[0].defaultValue);
	$("#searchKeyword3").val($("#searchKeyword3")[0].defaultValue);
	goSearchLoad();
}

function goSearch1(that,time){
	$(that).css({"background":"#fff","color":"#fd8c25"});
	$(that).siblings().css({"color":"#fff","background":"none"});
	$("#hotsearchdate").val(time);
	$("#searchKeyword1").val($("#searchKeyword1")[0].defaultValue);
	goSearchLoad();
}

var timeout;
//搜索助词
function searchHelp1($this){
	$this.next().val("");
	$this.next().next().val("");
	$this.next().next().next().val("");
	$this.next().next().next().next().val("");
	if(timeout){
		window.clearTimeout(timeout);
	}
	timeout = setTimeout(function(){
		$.ajax({
			type:  "post",
			url: njxBasePath + '/searchHelp.action',
			data: {historyKeyword:$this.val()},
			cache:false,
			success:function(data){
				if(data.status){
					var divHtml = "<section class='section' style = 'margin-bottom:0px;'><ul class='list'>";
					for(var i=0;i<data.obj.length;i++){
						var type = data.obj[i]["type"];
						var category = data.obj[i]["category"];//1:地域关键词,2:股票关键词,3:明星关键词,4:企业关键词,5:热门事件
						var titleType = "";
						if(type || category){
							titleType = "<i>";
						}
						if(type==1){
							titleType += "来自地域";
						}else if(type==2){
							titleType += "来自股票";
						}else if(type==3){
							titleType += "来自明星";
						}else if(type==4){
							titleType += "来自企业";
						}else if(type==5){
							titleType += "来自品牌";
						}else if(type==6){
							titleType += "来自人物";
						}else if(type==7){
							titleType += "来自景区";
						}else if(type==9){
							titleType += "来自汽车";
						}else if(type==10){
							titleType += "来自手机";
						}else if(type==11){
							titleType += "来自美妆";
						}else if(type==13){
							titleType += "来自电脑";
						}
						if(category){
							titleType += "("+category+")";
						}
						if(type){
							titleType += "</i>"
						}
						var liHtml = "<li onclick=\"searchHelpWord1('"+$this.attr("id")+"','"+data.obj[i]["title"]+"','"+data.obj[i]["keyword"]+"','"+data.obj[i]["filterKeyword"]+"','"+data.obj[i]["categoryId"]+"','"+data.obj[i]["type"]+"')\"><h1><span>"+data.obj[i]["title"]+titleType+"</span></h1></li>";
						divHtml +=liHtml;
					}
					divHtml = divHtml+"</ul><div id='emCancel1' style = 'background:#f2f2f2;line-height:35px;text-align:center;'>关闭</div></section>";
					$(".compUl").next().html(divHtml);
					$('#emCancel1').click(function(){
						$("#searchHelp1").hide();
						$(this).hide();
						$this.focus();
						$("#goSearch").show();
						return false;
					});
					$(".compUl").next().show();
					//$("#emCancel1").show();
					//$("#goSearch").hide();
				}else{
					$(".compUl").next().html("");
					$(".compUl").next().hide();
					//$("#emCancel1").hide();
					//$("#goSearch").show();
				}
			}
		});
		$("#hotwords").css('display','none');
	},800);
}


function searchHelp(keyword){
	if(timeout){
		window.clearTimeout(timeout);
	}
	timeout = setTimeout(function(){
		$.ajax({
			type:  "post",
			url: njxBasePath + '/searchHelp.action',
			data: {historyKeyword:keyword},
			cache:false,
			success:function(data){
				if(data.status){
					var divHtml = "<section class='section' style = 'margin-bottom:0px;'><ul class='list'>";
					for(var i=0;i<data.obj.length;i++){
						var type = data.obj[i]["type"];
						var category = data.obj[i]["category"];//1:地域关键词,2:股票关键词,3:明星关键词,4:企业关键词,5:热门事件
						var titleType = "";
						if(type || category){
							titleType = "<i>";
						}
						if(type==1){
							titleType += "来自地域";
						}else if(type==2){
							titleType += "来自股票";
						}else if(type==3){
							titleType += "来自明星";
						}else if(type==4){
							titleType += "来自企业";
						}else if(type==5){
							titleType += "来自品牌";
						}else if(type==6){
							titleType += "来自人物";
						}else if(type==7){
							titleType += "来自景区";
						}else if(type==9){
							titleType += "来自汽车";
						}else if(type==10){
							titleType += "来自手机";
						}else if(type==11){
							titleType += "来自美妆";
						}else if(type==13){
							titleType += "来自电脑";
						}
						if(category){
							titleType += "("+category+")";
						}
						if(type){
							titleType += "</i>"
						}
						var title = data.obj[i]["title"];
						title = title.replace(keyword,"<label class = 'f_c3'>"+keyword+"</label>");
						var liHtml = "<li onclick=\"searchHelpWord('"+data.obj[i]["title"]+"','"+data.obj[i]["keyword"]+"','"+data.obj[i]["filterKeyword"]+"','"+data.obj[i]["categoryId"]+"','"+data.obj[i]["type"]+"')\"><h1><span>"+title+titleType+"</span></h1></li>";
						divHtml +=liHtml;
					}
					divHtml = divHtml+"</ul><div class='emCancel' style = 'background:#f2f2f2;line-height:35px;text-align:center;'>关闭</div></section>";
					$("#searchHelp").html(divHtml);
					$('.emCancel').click(function(){
						$("#searchHelp").hide();
						$("#searchKeyword").focus();
					});
					$("#searchHelp").show();
				}else{
					$("#searchHelp").html("");
					$("#searchHelp").hide();
				}
			}
		});
		$("#hotwords").css('display','none');
	},800);
}

function searchHelpWord(title,keyword,filterKeyword,categoryId,type){
	if($.trim(title)){
		//$("#flag1").val(true);
		$("#searchKeyword").val($.trim(title));
		$("#keyword1").val($.trim(keyword));
		if($.trim(filterKeyword) && $.trim(filterKeyword) != "undefined" && $.trim(filterKeyword) != "null")
			$("#filterKeyword1").val($.trim(filterKeyword));
		$("#categoryId1").val($.trim(categoryId));
		$("#type1").val($.trim(type));
		$.ajax({url:njxBasePath+"/view/search/doSearchShareCode.shtml",cache:false,async:false,success:function(result){
				if(result.status){
					$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#1");
					goSearchLoad();
				}
			}});
	}else{
		swal("请输入内容");
	}
}
function searchHelpWord1(id,title,keyword,filterKeyword,categoryId,type){
	if($.trim(title)){
		$("#"+id).val($.trim(title));
		$("#"+id).next().val($.trim(keyword));
		$("#"+id).next().next().val($.trim(filterKeyword));
		$("#"+id).next().next().next().val($.trim(categoryId));
		$("#"+id).next().next().next().next().val($.trim(type));
//		   $("#"+id).next().val(true);
		$('#emCancel1').click();
		//goSearchLoad();
	}
}

$(function () {
	$(".dele").click(function () {
		$(this).parent().remove();
	})
})


//搜索热门关键字代码
function fbbox(fbboxID,ObjID,ObjID2,ObjID3,ObjID4,ObjID5){
	$(fbboxID).click(function(){
//		$('.searchH3').addClass('searchH2');
//		$('.searchH4').addClass('searchH5');
//		$('.searchH3').removeClass('searchH3');
		if($.trim($(fbboxID).val())){
			$("#searchHelp").show();
		}else{
			$(ObjID).show();
		}
		$(ObjID2).show();
		$(ObjID3).show();
//		$(ObjID4).hide();
		$(ObjID5).hide();
//		  window.ontouchmove=function(e){
//          e.preventDefault && e.preventDefault();
//          e.returnValue=false;
//          e.stopPropagation && e.stopPropagation();
//          return false;
//     };
	});
	$(ObjID).hover('',function(){
		$(ObjID).hide();
		$(ObjID2).hide();
		$(ObjID3).hide();
//		$(ObjID4).show();
		$(ObjID5).show();
//		 window.ontouchmove=function(e){
//          e.preventDefault && e.preventDefault();
//          e.returnValue=true;
//          e.stopPropagation && e.stopPropagation();
//          return true;
//     };
	});
	$(ObjID3).click('',function(){
		$('.searchH4').addClass('searchH3');
		$('.searchH4').removeClass('searchH2');
		$(ObjID).hide();
		$(ObjID2).hide();
		$(ObjID3).hide();
//		$(ObjID4).show();
		$(ObjID5).show();
//		 window.ontouchmove=function(e){
//          e.preventDefault && e.preventDefault();
//          e.returnValue=true;
//          e.stopPropagation && e.stopPropagation();
//          return true;
//     };
	});
//	$(fbboxID).keydown(function(){
//		$(ObjID).hide();
//		$(ObjID2).hide();
//
//	});
	$(ObjID).find('h1').click(function(){
		var text = $(this).find('span').text();
		$(fbboxID).val(text);
//		$('.searchH4').addClass('searchH3');
//		$('.searchH4').removeClass('searchH2');
		$(ObjID).hide();
		$(ObjID2).hide();
		$(ObjID3).hide();
//		$(ObjID4).show();
		$(ObjID5).show();
		goSearchLoad();
//		 window.ontouchmove=function(e){
//          e.preventDefault && e.preventDefault();
//          e.returnValue=true;
//          e.stopPropagation && e.stopPropagation();
//          return true;
//     };
	});
}
fbbox('#searchKeyword','#hotwords','#empty','#emCancel','#searchLink','#userlink');


//	$("#searchKeyword").on("click",function(){
//		  $("#searchHelp").css('display','none');
//		  $(".yapiskan").removeClass('gizle');
//		  $(".yapiskan").removeClass('sabit');
//	});

$("#searchKeyword").on("keydown",function(e){
	if(!$.trim($(this).val()) && e.keyCode ==13){
		setTimeout(function(){
			swal("请输入内容");
		},1);
		return false;
	}
	if($.trim($(this).val()).length<2 && e.keyCode ==13){
		setTimeout(function(){
			swal("请至少输入2个字");
		},1);
		return false;
	}
	if(e.keyCode ==13){
		var searchKeyword = $.trim($(this).val());
		var keywords = searchKeyword.split(",");
		var i = 0,keywordsJson = {date:$("#hotsearchdate").val()};
		$("#searchKeyword1").val("");
		$("#keyword1").val("");
		$("#filterKeyword1").val("");
		$("#categoryId1").val("");
		$("#type1").val("");
		$("#searchKeyword2").val("");
		$("#keyword2").val("");
		$("#filterKeyword2").val("");
		$("#categoryId2").val("");
		$("#type2").val("");
		$("#searchKeyword3").val("");
		$("#keyword3").val("");
		$("#filterKeyword3").val("");
		$("#categoryId3").val("");
		$("#type3").val("");
		if(keywords.length>0){
			$("#searchKeyword1").val(keywords[0]);
			$("#keyword1").val(keywords[0]);
			//$("#keywords").val(keywords[0]);
			keywordsJson.keyword1 = keywords[0];
			keywordsJson.title1 = keywords[0];
			i++;
		}
		if(keywords.length>1){
			$("#searchKeyword2").val(keywords[1]);
			$("#keyword2").val(keywords[1]);
			if(i>0){
				//$("#keywords").val($("#keywords").val()+","+keywords[1]);
				keywordsJson.title2 = keywords[1];
				keywordsJson.keyword2 = keywords[1];
			}else{
				//$("#keywords").val(+keywords[1]);
				keywordsJson.keyword1 = keywords[1];
				keywordsJson.title1 = keywords[1];
			}
			i++;
		}
		if(keywords.length>2){
			$("#searchKeyword3").val(keywords[2]);
			$("#keyword3").val(keywords[2]);
			if(i>0){
				//$("#keywords").val($("#keywords").val()+","+keywords[2]);
				keywordsJson.title3 = keywords[2];
				keywordsJson.keyword3 = keywords[2];
			}else{
				//$("#keywords").val(+keywords[2]);
				keywordsJson.keyword1 = keywords[2];
				keywordsJson.title1 = keywords[2];
			}
			i++;
		}
		if(i>1){
			if(!$("#adminId").val() && !$("#platform").val()){
				location.href = actionBase+"/indexLocal.shtml";
				return false;
			}
			$("#keywords").val(JSON.stringify(keywordsJson));
			$("#buyPrompt_jp13 input[name='packageCount']").val(i);
			showBuy({type:13});
			return false;
		}
		if(($.trim($("#searchKeyword1").val()) && $.trim($("#searchKeyword1").val()) == $.trim($("#searchKeyword2").val())) ||
			($.trim($("#searchKeyword1").val()) && $.trim($("#searchKeyword1").val()) == $.trim($("#searchKeyword3").val())) ||
			($.trim($("#searchKeyword2").val()) && $.trim($("#searchKeyword2").val()) == $.trim($("#searchKeyword3").val()))){
			swal("对不起,重复的内容对比会浪费您的使用次数,请修改关键词后重新提交!");
			return false;
		}
		$.ajax({url:njxBasePath+"/view/search/doSearchShareCode.shtml",cache:false,async:false,success:function(result){
				if(result.status){
					$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#1");
					$("form[name='searchForm']").submit();
				}
			}});
	}
});

$("#goHomeSearch").on("click",function(){
	var $searchKeyword = $("#searchKeyword");
	if(!$.trim($searchKeyword.val())){
		setTimeout(function(){
			swal("请输入内容");
		},1);
		return false;
	}
	if($.trim($searchKeyword.val()).length<2){
		setTimeout(function(){
			swal("请至少输入2个字");
		},1);
		return false;
	}
	$.ajax({url:njxBasePath+"/view/search/doSearchShareCode.shtml",cache:false,async:false,success:function(result){
			if(result.status){
				$("form[name='searchForm']").attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#1");
				$("form[name='searchForm']").submit();
			}
		}});

	//$("form[name='searchForm']").submit();
//		 var searchKeyword = $.trim($searchKeyword.val());
//		 var keywords = searchKeyword.split(",");
//		 var i = 0,keywordsJson = {date:$("#hotsearchdate").val()};
//		 $("#searchKeyword1").val("");
//		 $("#keyword1").val("");
//		 $("#filterKeyword1").val("");
//		 $("#categoryId1").val("");
//		 $("#type1").val("");
//		 $("#searchKeyword2").val("");
//		 $("#keyword2").val("");
//	     $("#filterKeyword2").val("");
//	     $("#categoryId2").val("");
//		 $("#type2").val("");
//		 $("#searchKeyword3").val("");
//		 $("#keyword3").val("");
//		 $("#filterKeyword3").val("");
//		 $("#categoryId3").val("");
//		 $("#type3").val("");
//
//		 if(keywords.length>0){
//			  $("#searchKeyword1").val(keywords[0]);
//			  $("#keyword1").val(keywords[0]);
//			  //$("#keywords").val(keywords[0]);
//			  keywordsJson.keyword1 = keywords[0];
//			  keywordsJson.title1 = keywords[0];
//			  i++;
//		  }
//		  if(keywords.length>1){
//			  $("#searchKeyword2").val(keywords[1]);
//			  $("#keyword2").val(keywords[1]);
//			  if(i>0){
//				   //$("#keywords").val($("#keywords").val()+","+keywords[1]);
//				   keywordsJson.title2 = keywords[1];
//				   keywordsJson.keyword2 = keywords[1];
//			  }else{
//				   //$("#keywords").val(+keywords[1]);
//				   keywordsJson.keyword1 = keywords[1];
//				   keywordsJson.title1 = keywords[1];
//			  }
//			  i++;
//		  }
//		  if(keywords.length>2){
//			  $("#searchKeyword3").val(keywords[2]);
//			  $("#keyword3").val(keywords[2]);
//			  if(i>0){
//				  //$("#keywords").val($("#keywords").val()+","+keywords[2]);
//				  keywordsJson.title3 = keywords[2];
//				  keywordsJson.keyword3 = keywords[2];
//			  }else{
//				  //$("#keywords").val(+keywords[2]);
//				  keywordsJson.keyword1 = keywords[2];
//				  keywordsJson.title1 = keywords[2];
//			  }
//			  i++;
//		  }
//		  if(i>1){
//			  if(!$("#adminId").val() && !$("#platform").val()){
//				  location.href = actionBase+"/indexLocal.shtml";
//				  return false;
//			  }
//			  $("#keywords").val(JSON.stringify(keywordsJson));
//			  $("#buyPrompt_jp13 input[name='packageCount']").val(i);
//			  showBuy({type:13});
//			  return false;
//		  }
//		  if(($.trim($("#searchKeyword1").val()) && $.trim($("#searchKeyword1").val()) == $.trim($("#searchKeyword2").val())) ||
//			 ($.trim($("#searchKeyword1").val()) && $.trim($("#searchKeyword1").val()) == $.trim($("#searchKeyword3").val())) ||
//			  ($.trim($("#searchKeyword2").val()) && $.trim($("#searchKeyword2").val()) == $.trim($("#searchKeyword3").val()))){
//			  swal("对不起,重复的内容对比会浪费您的使用次数,请修改关键词后重新提交!");
//			  return false;
//		  }
//		  $.ajax({url:njxBasePath+"/doHeatShareCode.shtml",cache:false,async:false,success:function(result){
//				if(result.status){
//					$("form[name='searchForm']").attr("action",njxBasePath+"/searchHeatResult.shtml?heatShareCode="+result.obj);
//					$("form[name='searchForm']").submit();
//				}
//		  }});
});


//搜索框检测代码
function checkinput(){
	var search = $('#searchKeyword');
	var isNull = /^[\s' ']*$/;
	if(search.val() == '输入关键词，回车搜索全国设计素材资源' || search.val().length <= 0 || isNull.test(search.val())){
		search.focus();
		window.alert("请输入关键词，搜索关键词不能为空");
		return false;
	}
}

//导航代码
$('#navMenu dl dt').hover(function(){
	$(this).find('.dropMenu').show();
	if($(this).attr("class") != 'clear-btn'){
		$(this).addClass('hover');
	}
},function(){
	$(this).find('.dropMenu').hide();
	$(this).removeClass('hover');
});

//查看更多搜索历史
$("#moreHot").on("click",function(){
	if($(this).find("a").text() === "清除所有记录"){
		//deleteUserSearchLog();
		//$("#hotwords .section .list").children().remove();
		//$(this).find("a").text("更多搜索记录");
		//$('#emCancel').click();
	}else{
		//$(this).find("a").text("清除所有记录");
		$(this).find("a").hide();
		$(".moreHot").show();
	}
});

//时间差计算
function dayBetween()
{
	$('.daybetween').each(function(){
		var timestamp = Date.parse(new Date())/1000;
		var publishedTime = getDate($(this).text());
		var publishedstamp = Date.parse(publishedTime)/1000;
		timestamp = timestamp - publishedstamp;
		//timestamp = timestamp+'/'+$(this).text();
		var daytime = timestamp/86400;
		var htime = timestamp/3600;
		var mtmie = timestamp/60;
		var stime = timestamp/1;
		if(daytime>=1)
		{
			/*daytime = Math.floor(daytime);
			var t = daytime+'天前';*/
			var t  = publishedTime.format("yyyy-MM-dd hh:mm");
			$(this).text(t);
		}
		else if(htime>=1&&htime<=8)
		{
			daytime = Math.floor(htime);
			var t = daytime+'小时前';
			$(this).text(t);
		}else if(htime>8&&htime<24){
			var t = '今天 '+publishedTime.getHours()+":"+publishedTime.getMinutes();
			$(this).text(t);
		}
		else
		{
			if(mtmie<=0){
				mtmie = 1;
			}
			daytime = Math.floor(mtmie);
			var t = daytime+'分钟前';
			$(this).text(t);
		}
		//成功修改时间显示后 将触发的class去掉
		$(this).removeClass('daybetween');
	});
}
function getDate(strDate) {
	var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
		function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
	return date;
}
function goSearchLoad(){
	if($("#searchKeyword1").length>0 || $("#searchKeyword2").length>0 || $("#searchKeyword3").length>0){
		var searchKeyword = "";
		if($.trim($("#searchKeyword1").val())){
			searchKeyword += $.trim($("#searchKeyword1").val());
		}
		if($.trim($("#searchKeyword2").val())){
			if(searchKeyword){
				searchKeyword += ",";
			}
			searchKeyword += $.trim($("#searchKeyword2").val())
		}
		if($.trim($("#searchKeyword3").val())){
			if(searchKeyword){
				searchKeyword += ",";
			}
			searchKeyword += $.trim($("#searchKeyword3").val())
		}
		$("#searchKeyword").val(searchKeyword);
	}
	$("form[name='searchForm']").submit();
}