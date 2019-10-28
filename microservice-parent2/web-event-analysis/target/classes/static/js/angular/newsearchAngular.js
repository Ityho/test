angular.module('ngApp', []).controller('initController',function($scope,$http){
	$scope.result = {obj:[],code:0}
	var prompt = "<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><br/>此时间段暂无信息</p></div>";
	
	if(window.top.bs.versions.ios){
		$(window.top).scroll(function(){
		　　var scrollTop = $(this).scrollTop();
		　　var scrollHeight = $(document).height();
		　　var windowHeight = $(this).height();
		　　if(scrollTop + windowHeight >= scrollHeight){
				if($("#pageAll").data("flag") == true)
					$scope.GotoPage($("#pageAll").val());
		　　}
		});
	}else{
		$(window).scroll(function(){
		　　var scrollTop = $(this).scrollTop();
		　　var scrollHeight = $(document).height();
		　　var windowHeight = $(this).height();
		　　if(scrollTop + windowHeight >= scrollHeight){
				if($("#pageAll").data("flag") == true)
					$scope.GotoPage($("#pageAll").val());
		　　}
		});
	}
		

	$scope.initData = function(){
		$scope.GotoPage(1);
	}
	
	$scope.goSingleContent = function(webpageUrl){
		location.href = webpageUrl;
	}
	
	$scope.goZZNews = function(titleHs,origin,repeatNum){
//		var data = {
//			keyword:$("#searchKeyword").val(),
//			titleHs:titleHs,
//			origin:origin,
//			page: 1,
//			pagesize:10
//		}

		$("#titleHs").val(titleHs);
		$("#origin").val(origin);
		$("#repeatNum").val(repeatNum);
		$("#page").val(1);
		$("#searchForm1").prop("action",njxBasePath+"/view/search/goKeywordSearch.shtml");
		$("#searchForm1").submit();
		//console.log(JSON.stringify(data));
		//location.href = actionBase+"/search/goKeywordSearch.shtml?searchKeyword="+encodeURIComponent($("#searchKeyword").val())+"&titleHs="+titleHs+"&origin="+type+"&page=1&pagesize=10";
	}

	$scope.GotoPage = function(page){
		$("#pageAll").data("flag",false);
		var data = {
			searchKeyword:$("#searchKeyword").val(),
			keyword1:$("#keyword1").val(),
			filterKeyword1:$("#filterKeyword1").val(),
			titleHs:$("#titleHs").val(),
			origin:$("#origin").val(),
			page:page,
			pagesize:10
		}
		
		$http.post(actionBase+"/view/search/doKeywordSearch.shtml",data)
		.success(function(result){
			if($("#pageAll").val() == "1"){
				$(".spinner1").hide();
				$("#slideDown2").show();
				if(result.maxpage == 0){
					$("#result_null").show();
					$("#slideDown2").hide();
				}
				$scope.result.obj = result.iContentCommonNetList;
			}else{
				$scope.result.obj = $scope.result.obj.concat(result.iContentCommonNetList);
			}
			
			$scope.result.code = result.code;
			if(result.code == "0000"){
				if(result.iContentCommonNetList.length == 10){
					$("#pageAll").val(parseInt($("#pageAll").val())+1);
					$("#pageAll").data("flag",true);
				}else{
					if($("#pageAll").val() != "1"){
						$("#no_more_msg").show();
					}
					if($("#pageAll").val() == "1" && result.iContentCommonNetList.length>0){
						$("#no_more_msg").show();
					}
				}
				
				if(result.maxpage == page || result.iContentCommonNetList.length != 10){
					$("#slideDown2").hide();
				}
			}
		})
	}
})
//配置ajax请求参数 
.config(function ($httpProvider) {
    $httpProvider.defaults.transformRequest = function(data){
        if (data === undefined) {
            return data;
        }
        return $.param(data);
    }
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
//允许html标签绑定
.filter('trustAsHtml',function($sce){
	return function(html){
		return $sce.trustAsHtml(html);
	}
});

//angularjs自启动
angular.bootstrap(document.documentElement,["ngApp"]);
