$(function() {
	$('.empty').click(function() {
		$('.inputBox').val("");
		document.getElementById('empty').style.display = "none";// 隐藏
	});
});
function dis() {
	var t = document.getElementById('weiboURL').value;
	if (t == "") {
		document.getElementById('empty').style.display = "none";// 隐藏
	} else {
		document.getElementById('empty').style.display = "block";// 显示
	}
}

$(function(){
	//转发层级下拉更多
	$(".table_list > li:gt(3)").hide();
	var num=1;
	$(".more").on("click",function(){
		if(num){
			num--;
			$(".table_list > li:gt(3)").show(300);
			$(this).html("<img src='" + staticResourcePath + "/images/swa/arrow-down.png' class='rotate180'/>");
		}else{
			num++;
			$(".table_list > li:gt(3)").hide(300);
			$(this).html("<img src='" + staticResourcePath + "/images/swa/arrow-down.png' class='rotate0'/>");
		}
	});



	//转发层级更多
	$(".m_hide").hide();
	//var num=1;
	$(".arrow").on("click",function(){
		if(!$(this).hasClass("rotate90")){
			//num--;
			$(this).prev().find(".m_hide").show(300);
			$(this).addClass('rotate90');
		}else{
			//num++;
			$(this).prev().find(".m_hide").hide(300);
			$(this).removeClass('rotate90');
		}
	});



	//转发量TOP10博文更多
	$(".mwblist2 > li:gt(2)").hide();
	var num=1;
	$(".more2").on("click",function(){
		if(num){
			num--;
			$(".mwblist2 > li:gt(2)").show(300);
			$(this).html("隐藏更多 <img src='" + staticResourcePath + "/images/swa/arrow-down2.png' class='rotate180'/>");
		}else{
			num++;
			$(".mwblist2 > li:gt(2)").hide(300);
			$(this).html("展开更多 <img src='" + staticResourcePath + "/images/swa/arrow-down2.png' class='rotate0'/>");
		}
	});

	$("#play_btn").on("click",function(){
		$(".demoPlay").fadeIn(300);
	});
	$("#closePlay").on("click",function(){
		$(".demoPlay").fadeOut(300);
	});
	//敏感度分析热词选择
	$(".tdOneColor").on("click",".wd",function(){
		$(".tdOneColor .wd").removeClass("active");
		$(this).addClass("active");
		weiboTaskResultHotWordsDetail($(this).text());
	});
	//切换图表/词云
	$("#switchChart").on("click",function(){
		$(".wordTable").toggle();
		$(".wordCloud").toggle();
	});

});
