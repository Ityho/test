angular.module('ngApp', []).controller('initController',function($scope,$http){
	$scope.result = {obj:[],code:0}
	
	$scope.initData = function(){
		$scope.GotoPage(1);
	}
	$scope.GotoPage = function(page){
		var data = {
			reviewCode:$("#reviewCode").val(),
			page:page,
			pagesize:10
		}
		
		var $navLi = $(".nav ul li.active");
		
		if($navLi.length)
			data.tendencyCondition = $navLi.data("tendency");
		
		$http.post(actionBase+"/warningCenter/getMoreWarningDetail",data)
		.success(function(result){
			if($("#pageAll").val() == "1"){
				$(".spinner1").hide();
				if(result.maxpage == 0){
					$("#Pagination").parent().hide();
					$("#result_null").show();
				}else{
					$("#Pagination").parent().show();
					$("#Pagination").twbsPagination({totalPages: result.maxpage,
						startPage: result.page,
						visiblePages: result.pagesize,
						hrefVariable: result.page,
						 first: '首页',
				         prev: '上一页',
				         next: '下一页',
						onPageClick: function (event, page) {
							$scope.GotoPage(page);
						}
					});
				}
				
			}
			$scope.result.obj = result.icontentCommonNetList;
			$scope.result.code = result.code;
			if(result.code == "0000"){
				if(result.icontentCommonNetList.length == 10){
					$("#pageAll").val(parseInt($("#pageAll").val())+1);
				}
			}
		})
	}
	$scope.changeTendency = function(tendency,e){
		console.log($(e.target))
		console.log($(e.target).parent().siblings().find("a"))
		//$(e.target).addClass("active");
		//$(e.target).parent().siblings().find("a").removeClass("active");
		$(e.target).parent().addClass("active").siblings().removeClass("active");
		$("#pageAll").val(1);
		$(".spinner1").show();
		$("#result_null").hide();
		$(".pagination").remove();
		$("#Pagination").data("twbs-pagination","");
		$("#Pagination").unbind();
		$scope.result = {obj:[],code:0}
		$scope.GotoPage(1);
	
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
})
//过滤html标签绑定
.filter('removeHtml',function(){
	return function(html){
		var reg = new RegExp("<a[^>]*>(.*?)</a>");
		if(!reg.exec(html)){
			return "微博 weibo.com";
		}
		return reg.exec(html)[1];
	}
})
//加强请求
.service('httpSerive',function($http,$q){
	this.query = function(param) {
		if(!param){
			throw new Error("param is empty");
		}
        var deferred=$q.defer();
        var promise=deferred.promise;
        $http({
        	url:param.url,
        	method:param.method,
        	data:param.data
        }).success(function(data,status,headers,config){
            deferred.resolve(data);//执行成功
        }).error(function(data,status,headers,config){
            deferred.reject();//执行失败
        })
        return promise;
    }
});

//angularjs自启动
angular.bootstrap(document.documentElement,["ngApp"]);
