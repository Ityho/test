angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive){
	var not_result = "<br> <div class = 'not-result' align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><br/>此时间段暂无信息</p></div>";
	//订单查询
	$scope.goOrderSearch = function (type,condition){
		if(type == 1){
			if(condition == 1){
				$("#orderStatus").text("全部");
				$("#pay1").addClass("cur");
				$("#pay2").removeClass("cur");
				$("#pay3").removeClass("cur");
				$("#pay4").removeClass("cur");
				document.getElementById("payStatus").value = -1;
			}else if(condition == 2){
				$("#orderStatus").text("未支付");
				$("#pay2").addClass("cur");
				$("#pay1").removeClass("cur");
				$("#pay3").removeClass("cur");
				$("#pay4").removeClass("cur");
				document.getElementById("payStatus").value = 0;
			}else if(condition == 3){
				$("#orderStatus").text("已支付");
				$("#pay3").addClass("cur");
				$("#pay1").removeClass("cur");
				$("#pay2").removeClass("cur");
				$("#pay4").removeClass("cur");
				document.getElementById("payStatus").value = 1;
			}else if(condition == 4){
				$("#orderStatus").text("支付失败");
				$("#pay4").addClass("cur");
				$("#pay2").removeClass("cur");
				$("#pay3").removeClass("cur");
				$("#pay1").removeClass("cur");
				document.getElementById("payStatus").value = 2;
			}

			$(".orderinfoui").find(".not_result").remove();
			//所加载数据未出来时显示加载动图
			$(".orderinfoui").children().css("display","none");//清空orderinfoui content
			$("#loading_gif").css("display","block");//显示加载动图
			var data = {orderType:$("#orderType").val(),payStatus:$("#payStatus").val(),
				startDate:$("#startDate").val(),endDate:$("#endDate").val()};
			httpSerive.query({method:'post',url:actionBase+'/userCenter/getOrderList',data:data}).then(function(data){
				if(data.status){
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","none");
				}else{
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","block");
				}
			});
			$("#showStatus").fadeOut(300);//关闭支付状态栏
			$("#modal").fadeOut(300);//关闭弹出层
			$('body').css("position","");//去除body上的fixed定位
		}else if(type == 2){
			if(condition == 1){
				$("#orderTypes").text("全部");
				$("#type1").addClass("cur");
				$("#type2").removeClass("cur");
				$("#type3").removeClass("cur");
				$("#type4").removeClass("cur");
				document.getElementById("orderType").value = -1;

			}else if(condition == 2){
				$("#orderTypes").text("赠送");
				$("#type2").addClass("cur");
				$("#type1").removeClass("cur");
				$("#type3").removeClass("cur");
				$("#type4").removeClass("cur");
				document.getElementById("orderType").value = 0;
			}else if(condition == 3){
				$("#orderTypes").text("购买");
				$("#type3").addClass("cur");
				$("#type1").removeClass("cur");
				$("#type2").removeClass("cur");
				$("#type4").removeClass("cur");
				document.getElementById("orderType").value = 1;
			}else if(condition == 4){
				$("#orderTypes").text("续费");
				$("#type4").addClass("cur");
				$("#type2").removeClass("cur");
				$("#type3").removeClass("cur");
				$("#type1").removeClass("cur");
				document.getElementById("orderType").value = 2;
			}
			//所加载数据未出来时显示加载动图
			$(".orderinfoui").children().css("display","none");//清空orderinfoui content
			$("#loading_gif").css("display","block");//显示加载动图
			$(".orderinfoui").find(".not_result").remove();
			var data = {orderType:$("#orderType").val(),payStatus:$("#payStatus").val(),
				startDate:$("#startDate").val(),endDate:$("#endDate").val()};
			httpSerive.query({method:'post',url:actionBase+'/userCenter/getOrderList',data:data}).then(function(data){
				if(data.status){
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","none");
				}else{
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","block");
				}
			});
			$("#showType").fadeOut(300);//关闭类型选择栏
			$("#modal").fadeOut(300);//关闭遮罩层
			$('body').css("position","");//去除body上的fixed定位
		}else{//选择时间和初始化
			console.log("初始化");
			$("#showTime").fadeOut(300);//关闭时间选择
			$("#modal").fadeOut(300);//关闭遮罩层
			$('body').css("position","");//去除body上的fixed定位
			//所加载数据未出来时显示加载动图
			$(".orderinfoui").children().css("display","none");//清空orderinfoui content
			$("#loading_gif").css("display","block");//显示加载动图
			$(".orderinfoui").find(".not_result").remove();
			var data = {orderType:$("#orderType").val(),payStatus:$("#payStatus").val(),
				startDate:$("#startDate").val(),endDate:$("#endDate").val()};
			httpSerive.query({method:'post',url:actionBase+'/userCenter/getOrderList',data:data}).then(function(data){
				if(data.status == 1){
					console.log("1");
					console.log(data.obj);
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","none");
				}else{
					console.log("2");
					$scope.orderlists = data.obj;
					$("#loading_gif").hide();
					$("#noresult").css("display","block");
				}
			});
		}

	}
	//取消订单
	$scope.cancelOrder = function(orderRecordId){
		swal({   title: "确定删除此订单？",   type: "info",   showCancelButton: true }, function(){
			var data = {"order.orderRecordId":orderRecordId};
			httpSerive.query({method:'post',url:actionBase+'/pay/cancelOrder',data:data}).then(function(data){
				if(data.status){
					location.reload(true);//当前页面刷新


				}
			});
		});

	}
	$scope.goPay = function(payRecordId){
		goPay(payRecordId);
	}

	//互动基金查询
	$scope.goMutualFundSearch = function (){
		$(".orderinfoui").find(".not_result").remove();
		var data = {startTime:$("#startDate").val(),endTime:$("#endDate").val()};
		httpSerive.query({method:'post',url:actionBase+'/userCenter/sharePlanAmount.shtml',data:data}).then(function(data){
			if(data.status){
				$scope.mutualFundlists = data.obj;
				$("#loading_gif").hide();
			}else{
				$(".orderinfoui").append(not_result);
			}
		});
	}
	//查询续费监测方案
	$scope.doFindAllKeyword = function(init,$event){
		if($event){
			$(".icon-circle-chooseAll").removeClass("icon-circle-seleted");
			$($event.target).siblings().removeClass("cur");
			$($event.target).addClass("cur");
		}
		var data = {"init":init};
		httpSerive.query({method:'post',url:actionBase+'/userCenter/getContinuePayManage',data:data}).then(function(data){
			if(data.status){
				$scope.keywords = data.obj;
				$(".icon-circle-choose").on("click",function(){
					$(this).toggleClass("icon-circle-seleted");
				});
				$("#loading_gif").hide();
			}
		});
	}
	$scope.iconChoose = function($event){
		$($event.target).toggleClass("icon-circle-seleted");
		var flag = true;
		$(".icon-circle-choose").each(function(i,item){
			if(!$(item).hasClass("icon-circle-seleted")){
				flag = false;
				return;
			}
		})
		if(flag){
			$(".icon-circle-chooseAll").addClass("icon-circle-seleted");
		}else{
			$(".icon-circle-chooseAll").removeClass("icon-circle-seleted");
		}
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
	////过滤html标签绑定
	//.filter('removeHtml',function(){
	//	return function(html){
	//		var reg = new RegExp("<a[^>]*>(.*?)</a>");
	//		if(!reg.exec(html)){
	//			return "微博 weibo.com";
	//		}
	//		return reg.exec(html)[1];
	//	}
	//})
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
