
<#include "../../top.ftl" >
</head>
<body>
<section id ="searchTap" style="border-bottom:solid 1px #D8D8D8;">
	<div class="search-list" style="display:none">
	<ul >
		<li>
			<form id = "searchForm" method="post" target="submitIframe">
				<input id="searchKeyword" type="text" name="searchKeyword" value="${searchKeyword}" placeholder="人名、机构、品牌或事件" size="30" maxlength="62" style="color:#999;">
				<input id = "keyword1" name="keyword1" type="hidden" value="${keyword1}">
				<input id = "filterKeyword1" name="filterKeyword1" type="hidden" value="${filterKeyword1}">
				<input id = "categoryId1" name="categoryId1" type="hidden" value="${categoryId}">
				<input id = "type1" name="type1" type="hidden" value="${type}">
				<input id ="adminId" value="${admin.userId }" type="hidden">
				<input id= "platform" name="platform" value="${platform }" type="hidden"/>
				<input id= "searchShareCode" name="searchShareCode" value="${searchShareCode }" type="hidden"/>
			</form>
		</li>
		<li>
			<button id = "goSearchBtn">搜索</button>
		</li>
	</ul>

	</div>
	<nav class="nav-list">
		<ul>
			<li><a href="#1" data-index = "0">热度指数</a></li>
			<li><a href="#7" data-index = "7">传播分析</a></li>
			<li><a href="#8" data-index = "8">口碑分析</a></li>
		</ul>
	</nav>
</section>

<img class="goHomecontrol" src="${staticResourcePathH5}/images/goHome.png"
	 onclick="javascript:location.href ='${njxBasePath}/weiboHome.shtml';">
<iframe id = "iframe" name="submitIframe" style="width:100%;border:0;"></iframe>

<script type="text/javascript">
	saveOperateLog('搜索-${searchKeyword}','2014');
	var common_keyword_filter = /^[/0-9a-zA-Z\u4e00-\u9fa5\s\.\_\-\[\]@#_《》']+$/;
	var common_keyword_char_filter = /[\s\.\-\[\]@#_《》']/g;
	$(function(){
		$(".nav-list li").on("click",function(){
			var searchKeyword = $("#searchKeyword").val();
			if(!searchKeyword){
				swal("请输入内容");
				return false;
			}
			if(searchKeyword.length == 1){
				swal("内容至少输入2个字");
				return false;
			}
			if(!checkKeywordFilter(searchKeyword)){
				swal("关键词只能包含中文、英文和数字");
				return false;
			}
			$(this).find("a").addClass("cur");
			$(this).siblings().find("a").removeClass("cur");
			changeSearch();
		});

		$("#goSearchBtn").on("click",function(){
			var searchKeyword = $("#searchKeyword").val();
			if(!searchKeyword){
				swal("请输入内容");
				return false;
			}

			if(searchKeyword.length == 1){
				swal("内容至少输入2个字");
				return false;
			}
			if(!checkKeywordFilter(searchKeyword)){
				swal("关键词只能包含中文、英文和数字");
				return false;
			}
			$.ajax({url:njxBasePath+"/view/search/doSearchShareCode.shtml",
				cache:false,
				async:false,
				success:function(result){
					if(result.status){
						$("#searchForm").prop("target","");
						$("#searchForm").prop("action",njxBasePath+"/view/search/goSearchShare.shtml?searchShareCode="+result.obj+ window.location.hash);
						$("#searchForm").submit();
					}
				}});
		});

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
				$.ajax({url:njxBasePath+"/doSearchShareCode",
					cache:false,
					async:false,
					success:function(result){
						if(result.status){
							$("#searchForm").prop("target","");
							$("#searchForm").prop("action",njxBasePath+"/view/search/goSearchShare.shtml?searchShareCode="+result.obj+ window.location.hash);
							$("#searchForm").submit();
						}
					}});
			}
		});

		$("#searchKeyword").on("input",function(){
			$("#keyword1").val("");
			$("#filterKeyword1").val("");
			$("#categoryId1").val("");
			$("#type1").val("");
		});

		console.log($($('#iframe')[0].contentWindow));


		changeSearch1();
		//设置iframe高度
		$("#iframe").height($("body").height()-$("#searchTap").height());
	});

	function checkKeywordFilter(keyword){

		if(keyword == null || keyword == ''){
			return false;
		}
		var newKeywords = $.trim(keyword);
		newKeywords = newKeywords.split(' ').join('');
		newKeywords = newKeywords.split('+').join('');
		newKeywords = newKeywords.split('|').join('');
		newKeywords = newKeywords.split('(').join('');
		newKeywords = newKeywords.split(')').join('');
		if(newKeywords == ''){
			return false;
		}
		if(!common_keyword_filter.test(newKeywords)){
			return false;
		}
		newKeywords = newKeywords.replace(common_keyword_char_filter, '');
		if(newKeywords.length == 0){
			return false;
		}
		return true;
	}

	function changeSearch(){
		if($(".nav-list ul li a.cur").data("index") == 0){
			$("#searchForm").prop("action",njxBasePath+"/view/hotSearch/goHotSearchShare.shtml?heatShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 1){

			$("#searchForm").prop("action",njxBasePath+"/view/moodMap/goMoodMap.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 2){
			$("#searchForm").prop("action",njxBasePath+"/view/search/goKeywordSearch.shtml?searchShareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 3){
			$("#searchForm").prop("action",njxBasePath+"/view/search/goProductsAnalysis.shtml?searchShareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 4){
			$("#searchForm").prop("action",njxBasePath+"/view/search/goNetworkDaily.shtml?searchShareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 7){
			$("#searchForm").prop("action",njxBasePath+"/view/spreadAnalysis/goSpreadAnalysis.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 8){
			$("#searchForm").prop("action",njxBasePath+"/view/opinionsAnalysis/opinionsAnalysisResult.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if($(".nav-list ul li a.cur").data("index") == 9){
			$("#searchForm").prop("action",njxBasePath+"/view/moodMap/goWeiboMoodShare.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}
		$("#searchForm").prop("target","submitIframe");
		$("#searchForm").submit();
	}
	/**
	 初始化
	 **/
	function changeSearch1(){
		var hash = window.location.hash;
		if(hash == "#1"){
			$(".nav-list li a").eq(0).addClass("cur");//热度指数
			$("#searchForm").prop("action",njxBasePath+"/view/hotSearch/goHotSearchShare.shtml?heatShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if(hash == "#7"){
			$(".nav-list li a").eq(1).addClass("cur");
			$("#searchForm").prop("action",njxBasePath+"/view/spreadAnalysis/goSpreadAnalysis.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if(hash == "#8"){
			$(".nav-list li a").eq(2).addClass("cur");
			$("#searchForm").prop("action",njxBasePath+"/view/opinionsAnalysis/opinionsAnalysisResult.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else if(hash == "#9") {
			$(".nav-list li a").eq(3).addClass("cur");//微博情绪
			$("#searchForm").prop("action", njxBasePath + "/view/moodMap/goWeiboMoodShare.shtml?searchShareCode=${searchShareCode}&shareCode=${shareCode}");
		}else{
				$(".nav-list li a").eq(0).addClass("cur");
				$("#searchForm").prop("action",njxBasePath+"/view/hotSearch/goHotSearchShare.shtml?heatShareCode=${shareCode}&shareCode=${shareCode}");
			}
			$("#searchForm").prop("target","submitIframe");
			$("#searchForm").submit();
		}

</script>
<script type='text/javascript' src='http://res.wx.qq.com/open/js/jweixin-1.0.0.js'></script>
<script type='text/javascript' src='${staticResourcePathH5}/js/weixinNewConfig.js?v=<%=SYSTEM_INIT_TIME %>'></script>
<script>
	$(function(){
		var hash = window.location.hash;
		if(hash == "#1"){
			var title = "${searchKeyword}-微热点-热度查询";
		}else if(hash == "#2"){
			var title = "${searchKeyword}-微热点-传播分析";
		}else if(hash == "#3"){
			var title = "${searchKeyword}-微热点-信息列表";
		}else if(hash == "#4"){
			var title = "${searchKeyword}-微热点-竞品分析";
		}else if(hash == "#5"){
			var title = "${searchKeyword}-微热点-网络日报";
		}
		weixinLinkShare({
			wxAuthUrl:njxBasePath+'/report/getWeixinConfig.action',
			title:title,
			desc:"微热点，社会化大数据应用平台",
			imgUrl:"${staticResourcePathH5}/images/fenxiang/weibowyq-icon300.jpg",
			link:location.href
		});
	})
</script>
</body>
</html>