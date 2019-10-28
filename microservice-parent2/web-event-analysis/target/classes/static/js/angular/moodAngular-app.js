angular.module('ngApp', []).controller('initController',function($scope,$http){
	var prompt = "<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>";
	$scope.doMoodAddNumber = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doMoodAddNumber.action",data)
		.success(function(result){
			if(result.status){
				$scope.totalCount = result.obj;
			}else{
				$scope.totalCount = 0;
			}
		})
	}
	
	$scope.doAreaStatistics = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doMoodAreaStatistics.action",data)
		.success(function(result){
			if(result.status){
				$scope.areaStatistics = result.obj;
			}
		})
	}
	
	$scope.doChangeAreaStatistics = function(index,e){
		if(e){
			$(e.target).addClass("active");
			$(e.target).siblings().removeClass("active");
			var text = $(e.target).text();
			if(text == "����"){
				var areaTopStatistics = $scope.areaTopStatistics;
			}else{
				var areaStatistics = $scope.areaStatistics[index-1];
			}
		}else{
			var text = "����";
			var areaTopStatistics = $scope.areaTopStatistics;
		}
		var opt = $.extend(true,{},echartsOpts["map"]);
		opt.series[0].itemStyle.normal.label.show = false;
		opt.series[0].itemStyle.emphasis.label.show = false;
		opt.series[0].itemStyle.normal.borderColor = "#fff";
		opt.series[0].itemStyle.emphasis.borderColor = "#fff";
		opt.dataRange.orient = "horizontal";
		if(index == 0){
			opt.dataRange = null;
			opt.color = ["#E0E0E0",'#cf421e','#333','#0e7dc0','#3fa579','#f18d00','#b6becc'];
			opt.legend = {data:["��ŭ","�־�","����","����","ϲ��","����"],x:'left',y:'bottom',itemWidth:10,itemGap:5};
			opt.series[0].hoverable = false;
			opt.series[0].showLegendSymbol = false;
			opt.series[0].roam = false;
			opt.series[0].itemStyle.normal.color = opt.color[0];
		}else if(index == 1){
			opt.dataRange.color = ["#cf421e","#d3582d","#d86b3d","#dd7f52","#e19167"];
		}else if(index == 2){
			opt.dataRange.color = ["#313131","#423e3d","#544d4b","#665e5b","#79716d"];
		}else if(index == 3){
			opt.dataRange.color = ["#0e7dc0","#4381ba","#5e8dc1","#759ac8","#8ba8d0"];
		}else if(index == 4){
			opt.dataRange.color = ["#3fa579","#5da782","#74b08e","#8aba9c","#9dc4aa"];
		}else if(index == 5){
			opt.dataRange.color = ["#f18d00","#ea9630","#eca147","#efae5e","#f2ba76"];
		}else if(index == 6){
			opt.dataRange.color = ["#9098a5","#9ba0ab","#a6aab4","#b2b4bd","#bdbec6"];
		}
		
		if(text == "����"){
			for(var i = 1;i<=opt.legend.data.length;i++){
				opt.series[i] = $.extend(true,{},echartsOpts["map"]).series[0];
				opt.series[i].name = opt.legend.data[i-1];
				opt.series[i].itemStyle.normal.label.show = false;
				opt.series[i].itemStyle.emphasis.label.show = false;
				opt.series[i].itemStyle.normal.borderColor = "#fff";
				opt.series[i].itemStyle.emphasis.borderColor = "#fff";
				opt.series[i].hoverable = false;
				opt.series[i].showLegendSymbol = false;
				opt.series[i].roam = false;
				opt.series[i].itemStyle.normal.color = opt.color[i];
				var g = 0;
				for(var j = 0;j<areaTopStatistics.length;j++){
					if(opt.series[i].name == areaTopStatistics[j].statList[0].name){
						opt.series[i].data[g] = {name:areaTopStatistics[j].name,value:areaTopStatistics[j].statList[0].num};
						g++;
					}
		    	}
			}
			setEchartsOpion({$id:$("#areaStatistics"),opt:opt});
			$("#xzMoodTop10Desc").text("΢������TOP10��������������");
			$scope.tableStatus = 1;
		}else{
			for(var i = 0;i<areaStatistics.statList.length;i++){
	    	   opt.series[0].data[i] = {name:areaStatistics.statList[i].name,value:areaStatistics.statList[i].num};
	    	}
			setEchartsOpion({$id:$("#areaStatistics"),opt:opt});
			
			$("#xzMoodTop10Desc").text(text+"TOP10��������������");
			$scope.tableStatus = 0;
			var areaTopArray = $scope.areaStatistics;
			for(var i = 0;i<areaTopArray.length;i++){
				if(text == areaTopArray[i].name){
					$scope.areaTop10Statistics = areaTopArray[i];
				}
			}
		}
	}
	
//	$scope.doAreaTopStatistics = function(){
//		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
//		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
//		$http.post(actionBase+"/view/moodMap/doMoodAreaTopStatistics.action",data)
//		.success(function(result){
//			if(result.status){
//				$scope.areaTopStatistics = result.obj;
//				$scope.doChangeAreaStatistics(0,null);
//			}else{
//				$("#areaStatistics").html(prompt);
//			}
//		})
//	}
	
	$scope.doAreaTopStatistics = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doMoodAreaTopStatistics.action",data)
		.success(function(result){
			if(result.status){
				$scope.areaTopStatistics = result.obj[0].statsList;
				$scope.areaStatistics = result.obj[1].statsList;
				$scope.doChangeAreaStatistics(0,null);
			}else{
				$("#areaStatistics").html(prompt);
			}
		})
	}
	
	$scope.doEmotionStatistics = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doMoodEmotionStatistics.action",data)
		.success(function(result){
			if(result.status){
				var emotionStatistics = result.obj;
				if(emotionStatistics && emotionStatistics.length>0){
					$scope.xzMood = emotionStatistics[0].name;
					if($scope.xzMood == "��ŭ"){
						$(".mood-desc li:eq(0)").css("background","#ec4444");
					}else if($scope.xzMood == "�־�"){
						$(".mood-desc li:eq(0)").css("background","#333");
					}else if($scope.xzMood == "����"){
						$(".mood-desc li:eq(0)").css("background","#24AEEB");
					}else if($scope.xzMood == "����"){
						$(".mood-desc li:eq(0)").css("background","#80ab10");
					}else if($scope.xzMood == "ϲ��"){
						$(".mood-desc li:eq(0)").css("background","#ff8500");
					}else if($scope.xzMood == "����"){
						$(".mood-desc li:eq(0)").css("background","#d6d6d6");
					}
				}
				var opt = $.extend(true,{},echartsOpts["pie"]);
				opt.color = ["#d6d6d6",""]
				for(var i = 0;i<emotionStatistics.length;i++){
		    	   opt.series[0].data[i] = {name:emotionStatistics[i].name,value:emotionStatistics[i].num};
		    	   if(emotionStatistics[i].name == "��ŭ"){
		    		   opt.color[i] = "#ec4444";
		    	   }else if(emotionStatistics[i].name == "�־�"){
		    		   opt.color[i] = "#333";
		    	   }else if(emotionStatistics[i].name == "����"){
		    		   opt.color[i] = "#24AEEB";
		    	   }else if(emotionStatistics[i].name == "����"){
		    		   opt.color[i] = "#80ab10";
		    	   }else if(emotionStatistics[i].name == "ϲ��"){
		    		   opt.color[i] = "#ff8500";
		    	   }else if(emotionStatistics[i].name == "����"){
		    		   opt.color[i] = "#d6d6d6";
		    	   }
		    	}
				setEchartsOpion({$id:$("#emotionStatistics"),opt:opt});
			}else{
				$("#emotionStatistics").html(prompt);
				$scope.xzMood = "��";
			}
		})
	}
	
	$scope.doContentTrends1 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doMoodContentTrends1.action",data)
		.success(function(result){
			if(result.status){
				var contentTrends1 = result.obj;
				var opt = $.extend(true,{},echartsOpts["line2"]);
				 opt.color = [];
				for(var i = 0;i<contentTrends1.length;i++){
					opt.legend.data[i] = contentTrends1[i].name;
					opt.series[i] = {name:contentTrends1[i].name,type:'line',symbol: 'emptyCircle',symbolSize: 10,itemStyle: {
        				normal: {
        					color: '#F08C00',
        					borderColor: '#F08C00',
        					borderWidth: 2
        				}
        			},lineStyle: {
        				normal: {
        					color: '#F08C00',
        					width: 2,
        					type: 'solid'
        				}
        			},areaStyle: {
        				normal: {
        					color: 'rgba(0,0,0,0.5)',
        					opacity: 0
        				}
        			},smooth: true,data:[]};
					for(var j = 0;j<contentTrends1[i].statList.length;j++){
						opt.xAxis[0].data[j] = contentTrends1[i].statList[j].name;
						opt.series[i].data[j] = contentTrends1[i].statList[j].num;
					}
					if(contentTrends1[i].name == "��ŭ"){
		    		   opt.series[i].itemStyle.normal.color = "#ec4444";
		    		   opt.series[i].itemStyle.normal.borderColor = "#ec4444";
		    		   opt.series[i].lineStyle.normal.color = "#ec4444";
					}else if(contentTrends1[i].name == "�־�"){
						opt.series[i].itemStyle.normal.color = "#333";
			    		opt.series[i].itemStyle.normal.borderColor = "#333";
			    		opt.series[i].lineStyle.normal.color = "#333";
					}else if(contentTrends1[i].name == "����"){
						opt.series[i].itemStyle.normal.color = "#24AEEB";
			    		opt.series[i].itemStyle.normal.borderColor = "#24AEEB";
			    		opt.series[i].lineStyle.normal.color = "#24AEEB";
					}else if(contentTrends1[i].name == "����"){
						opt.series[i].itemStyle.normal.color = "#80ab10";
			    		opt.series[i].itemStyle.normal.borderColor = "#80ab10";
			    		opt.series[i].lineStyle.normal.color = "#80ab10";
					}else if(contentTrends1[i].name == "ϲ��"){
						opt.series[i].itemStyle.normal.color = "#ff8500";
			    		opt.series[i].itemStyle.normal.borderColor = "#ff8500";
			    		opt.series[i].lineStyle.normal.color = "#ff8500";
					}else if(contentTrends1[i].name == "����"){
						opt.series[i].itemStyle.normal.color = "#d6d6d6";
			    		opt.series[i].itemStyle.normal.borderColor = "#d6d6d6";
			    		opt.series[i].lineStyle.normal.color = "#d6d6d6";
					}
		    	}
				setEchartsOpion({$id:$("#contentTrends1"),opt:opt});
			}else{
				$("#contentTrends1").html(prompt);
			}
		})
	}
	
	$scope.doEmotionProportion = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doEmotionProportion.action",data)
		.success(function(data){
			if (data.status) {
				if (data.obj.data.length==0) {
					$("#emotionpieEchart").html(prompt);
				}else{
					var opt = $.extend(true,{},echartsOpts["emotionProportion"]);
					var opt2 = $.extend(true,{},echartsOpts["emotionProportion2"]);
					var colors = data.obj.color;
					var data = data.obj.data;
					var datas = new Array();
					var legend = new Array();
					
					for(n in data){
						var color = "";
						if(data[n].name == "ϲ��"){
							color = colors.xy;
						}else if(data[n].name == "��ŭ"){
							color = colors.fn;
						}else if(data[n].name == "����"){
							color = colors.bs;
						}else if(data[n].name == "����"){
							color = colors.jq;
						}else if(data[n].name == "�־�"){
							color = colors.kj;
						}else if(data[n].name == "����"){
							color = colors.zx;
						}
						legend.push({
							name : data[n].name,
							textStyle: {
								color: color,
							}
						});
						datas.push({
							value: data[n].count,
							name: data[n].name,
							itemStyle: {
								normal: {
									color: color,
									borderColor: '#fff',
									borderWidth: 2
								}
							},
							label: {
								normal: {
								textStyle: {
									fontSize: 12
									}
								}
							}
						});
					}
					opt.series[0].data=datas;
					setEchartsOpion({$id:$("#emotionpieEchart"),opt:opt});
				}
			}else{
				$("#emotionpieEchart").html(prompt);
			}
		})
	}
	
	$scope.doEmotionProportion2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doEmotionProportion.action",data)
		.success(function(data){
			if (data.status) {
				if (data.obj.data.length==0) {
					$("#emotionpieEchart2").html(prompt);
				}else{
					var opt2 = $.extend(true,{},echartsOpts["emotionProportion2"]);
					var colors = data.obj.color;
					var data = data.obj.data;
					
					var yAxis = new Array();
					var datas2 = new Array();
					data.reverse();
					for(n in data){
						var color = "";
						if(data[n].name == "ϲ��"){
							color = colors.xy;
						}else if(data[n].name == "��ŭ"){
							color = colors.fn;
						}else if(data[n].name == "����"){
							color = colors.bs;
						}else if(data[n].name == "����"){
							color = colors.jq;
						}else if(data[n].name == "�־�"){
							color = colors.kj;
						}else if(data[n].name == "����"){
							color = colors.zx;
						}
						
						yAxis.push(data[n].name);
						datas2.push({
							name: data[n].name,
							value: data[n].count,
							itemStyle: {
								normal: {
									color: color,
									barBorderRadius: 5
								}
							}
						});
					}
					opt2.yAxis[0].data=yAxis;
					opt2.series[0].data=datas2;
					setEchartsOpion({$id:$("#emotionpieEchart2"),opt:opt2});
				}
			}else{
				$("#emotionpieEchart2").html(prompt);
			}
		})
	}
	
	$scope.doEmotionSex = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doEmotionSex.action",data)
		.success(function(data){
			if (data.status) {
				var opt = $.extend(true,{},echartsOpts["emotionSex"]);
				var datas= [data.obj.max,
					data.obj.max,
					data.obj.max,
					data.obj.max,
					data.obj.max,
					data.obj.max];
				
				opt.series[0].data[0].value=data.obj.m.xy;
				opt.series[0].data[1].value=data.obj.m.zx;
				opt.series[0].data[2].value=data.obj.m.fn;
				opt.series[0].data[3].value=data.obj.m.bs;
				opt.series[0].data[4].value=data.obj.m.jq;
				opt.series[0].data[5].value=data.obj.m.kj;
				
				opt.series[2].data[0].value=data.obj.f.xy;
				opt.series[2].data[1].value=data.obj.f.zx;
				opt.series[2].data[2].value=data.obj.f.fn;
				opt.series[2].data[3].value=data.obj.f.bs;
				opt.series[2].data[4].value=data.obj.f.jq;
				opt.series[2].data[5].value=data.obj.f.kj;
				
				opt.series[1].data=datas;
				opt.series[3].data=datas;
				
				opt.xAxis[0].max=data.obj.max;
				opt.xAxis[2].max=data.obj.max;
				setEchartsOpion({$id:$("#emotionBar5"),opt:opt});
			}else{
				$("#emotionBar5").html(prompt);
			}
		})
	}
	
	
	$scope.doEmotionType = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),
		            keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val()}
		$http.post(actionBase+"/view/moodMap/doEmotionType.action",data)
		.success(function(data){
			if (data.status) {
				var opt = $.extend(true,{},echartsOpts["emotionType"]);
				
				/*var indicator = new Array();
				for(n in data.obj.emotion){
					indicator.push({
						name: data.obj.emotion[n],
						max: 100
					});
				}*/
				
				opt.series[0].data=data.obj.xy[0];
				opt.series[1].data=data.obj.zx[0];
				opt.series[2].data=data.obj.fn[0];
				opt.series[3].data=data.obj.bs[0];
				opt.series[4].data=data.obj.jq[0];
				opt.series[5].data=data.obj.kj[0];
				
				setEchartsOpion({$id:$("#emotionBar6"),opt:opt});
			}else{
				$("#emotionBar6").html(prompt);
			}
		})
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
});

//angularjs������
angular.bootstrap(document.documentElement,["ngApp"]);
