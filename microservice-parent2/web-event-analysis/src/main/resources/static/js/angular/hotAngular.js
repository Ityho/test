angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive){
	$scope.result = {obj:[],status:0}
	
	$scope.changeRankType = function(rankType,type){
		$(".spinner1").show();
		$scope.result = {obj:[],status:0}
		$("#pageAll").val(1);
		if(type){
			$("#s1").addClass('active');
			$("#s0").removeClass('active');
		}else{
			$("#s0").addClass('active');
			$("#s1").removeClass('active');
		}
		$scope.initData(rankType,0);
	}
	
	
	
	$scope.initData = function(rankType,isMore){ 
		if(isMore){
			$("#bAll").hide();
			$("#slideDown2").show();
		}
		var data = {pageMore:$("#pageAll").val()};
		if($("#s1").hasClass("active")){
			data.province = $("#s1").text();
		}
		$http.post(actionBase+"/view/hotEvent/getMoreHotList.action",data)
		.success(function(result){
			if($("#pageAll").val() == "1"){
				$(".spinner1").hide();
				$scope.result.obj = result.obj;
			}else{
				$("#slideDown2").hide();
				if(result.status){
					$("#bAll").show();
					$scope.result.obj = $scope.result.obj.concat(result.obj);
				}
			}
			$scope.result.status = result.status;
			if(result.status){
				if(result.obj.length == 10){
					$("#pageAll").val(parseInt($("#pageAll").val())+1);
				}else{
					$scope.result.status = 0;
				}
			}
		})
	}

	$scope.changeRankType2 = function(rankType,type){
		$(".spinner1").show();
		$scope.result = {obj:[],status:0}
		$("#pageAll").val(1);
		if(type !== ''){
			$(".subNavItem").removeClass('active');
			$("#s"+type).addClass('active');
		}
		if(rankType == 9){//���԰���Ϣ
			$scope.rankingTagName = "computerTag";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 1;
			}
		}else if(rankType == 10){//�ҵ����Ϣ
			$scope.rankingTagName = "appliancesTag";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 1;
			}
		}else if(rankType == 14){//���Ӿ����Ϣ
			$scope.rankingTagName = "amusementTag";
			$scope.rankingTagType = 2;
		}else if(rankType == 15){//��Ӱ����Ϣ
			$scope.rankingTagName = "amusementTag";
			$scope.rankingTagType = 1;
		}else if(rankType == 16){//���հ���Ϣ
			$scope.rankingTagName = "amusementTag";
			$scope.rankingTagType = 3;
		}else if(rankType == 13){//�����˰���Ϣ
			$scope.rankingTagName = "menTag";
			$scope.rankingTagType = 1;
		}else if(rankType == 12){//�˶�Ա����Ϣ
			$scope.rankingTagName = "menTag";
			$scope.rankingTagType = 3;
		}else if(rankType == 11){//������Ϣ
			$scope.rankingTagName = "menTag";
			$scope.rankingTagType = 2;
		}else if(rankType == 3){//��������Ϣ
			$scope.rankingTagName = "carTag";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 1;
			}
		}else if(rankType == 2){//���ǰ���Ϣ
			$scope.rankingTagName = "celebrityTag";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 0;
			}
		}else if(rankType == 1){//��Ʊ����Ϣ
			$scope.rankingTagName = "stockType";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 0;
			}
		}else if(rankType == 18){//��Ʊ����Ϣ
			$scope.rankingTagName = "";
			if(type){
				$scope.rankingTagType = type;
			}else{
				$scope.rankingTagType = 0;
			}
		}
		$scope.initData2(rankType,0);
	}
	
	
	
	$scope.initData2 = function(rankType,isMore){
		if(isMore){
			$("#bAll").hide();
			$("#slideDown2").show();
		}
		var data = {pageMore:$("#pageAll").val()};
		if($("#subnav .active").length){
			data.listType = $("#subnav .active").data("listtype");
		}
		var url = actionBase;
		if(rankType == 18){
			url += "/view/hotEvent/getMoreEducationList.action?flag=1";
		}else if(rankType == 17){
			url += "/view/hotEvent/getMoreHotList.action";
		}else if(rankType == 16){
			url += "/view/hotEvent/getMoreTeleplayList.action?flag=1&listType=3";
		}else if(rankType == 15){
			url += "/view/hotEvent/getMoreTeleplayList.action?flag=1&listType=1";
		}else if(rankType == 14){
			url += "/view/hotEvent/getMoreTeleplayList.action?flag=1&listType=2";
		}else if(rankType == 13){
			url += "/view/hotEvent/getMoreCharacterList.action?flag=1&listType=1";
		}else if(rankType == 12){
			url += "/view/hotEvent/getMoreCharacterList.action?flag=1&listType=3";
		}else if(rankType == 11){
			url += "/view/hotEvent/getMoreCharacterList.action?flag=1&listType=2";
		}else if(rankType == 10){
			url += "/view/hotEvent/getMoreHouseholdList.action?flag=1";
		}else if(rankType == 9){
			url += "/view/hotEvent/getMoreComputerList.action?flag=1";
		}else if(rankType == 7){
			url += "/view/hotEvent/getMoreScenicList.action?flag=1&listType=0";
		}else if(rankType == 4){
			url += "/view/hotEvent/getMoreMobileList.action?flag=1&listType=0";
		}else if(rankType == 3){
			url += "/view/hotEvent/getMoreCarList.action?flag=1";
		}else if(rankType == 2){
			url += "/view/hotEvent/getMoreStarList.action?flag=1";
		}else if(rankType == 1){
			url += "/view/hotEvent/getMoreStockList.action?flag=1";
		}
		$http.post(url,data)
		.success(function(result){
			if($("#pageAll").val() == "1"){
				$(".spinner1").hide();
				$scope.result.obj = result.obj;
			}else{
				$("#slideDown2").hide();
				if(result.status){
					$("#bAll").show();
					$scope.result.obj = $scope.result.obj.concat(result.obj);
				}
			}
			$scope.result.status = result.status;
			if(result.status){
				if(result.obj.length == 10){
					$("#pageAll").val(parseInt($("#pageAll").val())+1);
				}else{
					$scope.result.status = 0;
				}
			}
		});
	}
	
	$scope.goHeatSearch = function(index){
		$http.get(actionBase+"/doHeatShareCode.shtml").
			success(function(result){
			if(result.status){
				$("#frmGohot"+index).attr("action",njxBasePath+"/view/search/goSearch.shtml?searchShareCode="+result.obj+"#1");
				$("#frmGohot"+index).submit();
			}
		});
	}
})
//����ajax������� 
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
//����html��ǩ��
.filter('trustAsHtml',function($sce){
	return function(html){
		return $sce.trustAsHtml(html);
	}
})
//����html��ǩ��
.filter('removeHtml',function(){
	return function(html){
		var reg = new RegExp("<a[^>]*>(.*?)</a>");
		if(!reg.exec(html)){
			return "΢�� weibo.com";
		}
		return reg.exec(html)[1];
	}
})
//��ǿ����
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
            deferred.resolve(data);//ִ�гɹ�
        }).error(function(data,status,headers,config){
            deferred.reject();//ִ��ʧ��
        })
        return promise;
    }
});

//angularjs������
angular.bootstrap(document.documentElement,["ngApp"]);
