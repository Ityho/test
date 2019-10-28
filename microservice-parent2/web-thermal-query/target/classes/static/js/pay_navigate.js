<!--
//导航条的搜索功能
	function ajaxClickEvl(ev) {
		$("#searchKeyword").val(ev.innerHTML);
		$("#keyWordLogs_div").css( {
			"display" : "none"
		});
		subSearchForm();
	}

	function ajaxOverBG(ev) {
		ev.style.backgroundColor = "#e4e4e4";
		ev.style.color = "#2680b4";
	}

	function ajaxOutBG(ev) {
		ev.style.backgroundColor = "#ffffff";
		ev.style.color = "#2680b4";
	}
	//点击的不是显示操作时将他隐藏
	document.onclick = function(event) {
		var e = event || window.event;
		var elem = e.srcElement || e.target;
		while (elem) {
			var reg = /^search/g;
			if (reg.test(elem.id)) {
				return;
			}
			elem = elem.parentNode;
		}
		//隐藏div的方法   
		$("#keyWordLogs_div").hide();

		$("#searchKeyword").css( {
				
				"background": "url(../../images/search.png) 180px 2px no-repeat"
			});
	};


	function deleteUserSearch() {
		$.ajax( {
			url : "keyWordLogs_deleteAll.action?" + new Date(),
			type : "get",
			success : function(data) {
				 
			},
			error : function(data) {
				alert("删除失败!");
			}
		});
	}
	function goNext(obj) {
		 
		var objName = "";
		var objId = "";
		if (event.keyCode == 13) {
		 
			subSearchForm();
			return false;
		}

	}

	function subSearchForm(){
			var kv = document.getElementById("searchKeyword").value;

		
		if($.trim(kv)==""){
			document.getElementById("searchKeyword").value = "";
			document.getElementById("searchKeyword").focus();
			//alert("请输入搜索关键字!");
			return ;
		}

		 
		var keyTitleV = kv;
		kv =  kv.replace(/\%/g, "%25");

		searchForm.action = "../../ksearch.action";
	    searchForm.target = "_self";
	    searchForm.submit();

	}

	$(function(){
 
 		
				$("#searchKeyword")
				.mousedown(
						function() {
							//alert("11");
							$.ajax({
								url : "../../keyWordLogs.action?"+new Date(),
								type : "get",
								dataType : "json",
								success : function(data){
									var len = data.size;
									var keyWordLogs ="<table width='100%'><tr><td style='color:#8a8a8a;padding-left: 5px;font-size:12px;'>最近搜过&nbsp;&nbsp;<a href='javascript:deleteUserSearch();' style='color:#2A8BDB;'>删除搜索记录</a></td></tr>";
									
									for(var i= 0; i<len ; i++){
										var vl = data.logs["keyWord"+i];
										keyWordLogs += "<tr height='20px;' style='cursor: default;'  onmouseover='ajaxOverBG(this);' onmouseout='ajaxOutBG(this);'>";
										keyWordLogs += "<td style='padding-left: 5px;color:#2680b4;font-size:12px;' onclick='ajaxClickEvl(this)' >"+vl+"</td></tr>";
									}
									keyWordLogs += "</table>";
									$("#keyWordLogs_div").css({"display":"block"}).html(keyWordLogs);
									if(len != 0){
										$("#history_search").css({"display":"block"});
									}
								},error:function(data){
									
								}
							});
						});
		$("#searchKeyword").click(function() {
			$(this).css( {
				"color" : "#434343",
				"background": "url(../../images/search_over.png) 180px 2px no-repeat"
			});
		});
		 
		 
		

	});

function goShouye(init){
	if(init==1){
		window.location.href="../../shouye.action";
	}
}
function goYQList(init){
	if(init==1){
		window.location.href="../../keywordSearch.action";
	}
}
function goCollectNewsList(init){
	if(init==1){
		window.location.href="../../collectNewsList.action";
	}
}	
function goWarning(init){
	window.location.href="../../warning.action";
}
function goUserCenter(init){

	window.location.href="../../view/usercenter/userCenter.action";
}
function goJianbao(init){
	window.location.href="../../briefListSearch.action";
}
function logout(){
	window.location.href="../../logout.action";
}
function goDev(){
	window.location.href="../../goDev.action";
}