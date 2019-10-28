angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive,utils){
	var prompt = "<div align='center' style='padding-top:50px'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p><p>热度君暂时计算不出您的热度</p><p>换个词试试呢~</p></div></div>";
	// 热度指数
	bigpipe.ready('doHeatValue',function(result){
		if(result.status){
			if(result.obj[0]){
				var opt = $.extend(true,{},echartsOpts["liquidFill"]);
				opt.series[0].name = "";
				opt.series[0].data[0] = (result.obj[0].ratioHotCustom / 100).toFixed(4);
				opt.series[0].label.normal.formatter = function(v){
					return (v.data * 100).toFixed(2);
				}
				setEchartsOpion({$id:$(".heatValueChart:eq(0)"),opt:opt});
			}else{
				$(".heatValueChart:eq(0)").html(prompt);
			}

			if(result.obj[1]){
				var opt = $.extend(true,{},echartsOpts["liquidFill"]);
				opt.series[0].name = "";
				opt.series[0].data[0] = (result.obj[1].ratioHotCustom / 100).toFixed(4);
				opt.series[0].itemStyle.normal.color = "#577bc6";
				opt.series[0].backgroundStyle.borderColor = "#577bc6";
				opt.series[0].label.normal.textStyle.color = "#577bc6";
				opt.series[0].label.normal.formatter = function(v){
					return (v.data * 100).toFixed(2);
				}
				setEchartsOpion({$id:$(".heatValueChart:eq(1)"),opt:opt});
			}else{
				$(".heatValueChart:eq(1)").html(prompt);
			}

			var str = "";

			if(result.obj[0].ratioHotCustom > 80){
				str = "80~100";
			}else if(result.obj[0].ratioHotCustom > 60){
				str = "60~80";
			}else if(result.obj[0].ratioHotCustom > 30){
				str = "30~60";
			}else{
				str = "0~30";
			}
			heatNUm = str;

			$scope.$apply(function(){
				$scope.hots= result.obj;
				$scope.hotratioHotCustom = "<0.01";
			});
		}else{
			$(".heatValueChart").html(prompt);
		}
	});

	// 热度走势
	bigpipe.ready('doEventTrends',function(result){
		if(result.status){
			$scope.eventTrendsObj = result.obj;
		}
		$scope.doEventTrends($scope.eventTrendsObj);
	});

	// 热度走势
	$scope.doEventTrends = function(obj){
		$("#legend-list2 li:eq(0)").addClass("active");
		$("#legend-list2 li:eq(1)").removeClass("active");

		if(obj){
			var opt = $.extend(true,{},echartsOpts["line"]);
			var alertMsg = "从热度指数变化趋势来看,";
			if(obj[0]){
				opt.series[0] = $.extend(true,{},echartsOpts["line"]).series[0];
				opt.series[0].name = obj[0].name;
				opt.legend.data[0] = obj[0].name;

				var topValue = obj[0].statList[0].total.toFixed(2),topTime,subMsg;
				var topTime = obj[0].statList[0].name;
				if(obj[0].statList.length){
					for(var i = 0;i < obj[0].statList.length;i++){
						opt.series[0].showAllSymbol = false;
						opt.series[0].data[i] = obj[0].statList[i].total.toFixed(2);
						opt.xAxis[0].data[i] = obj[0].statList[i].name+":00";
						if(parseFloat(obj[0].statList[i].total) > parseFloat(topValue)){
							topValue = obj[0].statList[i].total.toFixed(2);
							topTime = obj[0].statList[i].name;
						}
					}
				}
				subMsg = $("#searchKeyword1").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
			}
			if(obj[1]){
				opt.series[1] = $.extend(true,{},echartsOpts["line"]).series[0];
				opt.series[1].name = obj[1].name;
				opt.legend.data[1] = obj[1].name;
				opt.series[1].itemStyle.normal.color = "#456dc0";
				opt.series[1].lineStyle.normal.color = "#456dc0";
				opt.series[1].areaStyle.normal.color = new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
					offset: 0,
					color: 'rgba(69, 109, 192, 0.5)'
				}, {
					offset: 1,
					color: 'rgba(69, 109, 192, 0)'
				}], false);

				var topValue = obj[1].statList[0].total.toFixed(2),topTime,subMsg;
				var topTime = obj[1].statList[0].name;
				if(obj[1].statList.length){
					for(var i = 0;i < obj[1].statList.length;i++){
						opt.series[1].showAllSymbol = false;
						opt.series[1].data[i] = obj[1].statList[i].total.toFixed(2);
						if(parseFloat(obj[1].statList[i].total) > parseFloat(topValue)){
							topValue = obj[1].statList[i].total.toFixed(2);
							topTime = obj[1].statList[i].name;
						}
					}
				}
				subMsg = $("#searchKeyword2").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
			}
			$("#hotTrends_msg").html(alertMsg)
			setEchartsOpion({$id:$("#hotTrends"),opt:opt});
		}else{
			$("#hotTrends").html(prompt);
		}
	};

	// 热度聚类
	bigpipe.ready('doHotCluster',function(result){
		if(result.status){
			$scope.$apply(function(){
				$scope.hotclusters= result.obj;
			});
		}
	});

	// 声量图
	bigpipe.ready('doVolumeMap',function(result){
		if(result.status){
			$scope.volumeMapObj = result.obj;
		}
	});

	// 声量图
	$scope.doVolumeMap = function(obj){
		$("#legend-list2 li:eq(0)").removeClass("active");
		$("#legend-list2 li:eq(1)").addClass("active");

		if(obj){
			var opt = $.extend(true,{},echartsOpts["line"]);
			var alertMsg = "从声量变化趋势来看,";
			if(obj[0]){
				opt.series[0] = $.extend(true,{},echartsOpts["line"]).series[0];
				opt.series[0].name = $("#searchKeyword1").val();
				opt.legend.data[0] = $("#searchKeyword1").val();

				var topValue = obj[0][0].num,
					topTime = obj[0][0].key,subMsg;
				if(obj[0].length){
					for(var i = 0;i < obj[0].length;i++){
						opt.series[0].showAllSymbol = false;
						opt.series[0].data[i] = obj[0][i].num;
						opt.xAxis[0].data[i] = obj[0][i].key+":00";
						if(obj[0][i].num > topValue){
							topValue = obj[0][i].num;
							topTime = obj[0][i].key;
						}
					}
				}
				subMsg = $("#searchKeyword1").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>的声量在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
			}
			if(obj[1]){
				opt.series[1] = $.extend(true,{},echartsOpts["line"]).series[0];
				opt.series[1].name = $("#searchKeyword2").val();
				opt.legend.data[1] = $("#searchKeyword2").val();
				opt.series[1].itemStyle.normal.color = "#456dc0";
				opt.series[1].lineStyle.normal.color = "#456dc0";
				opt.series[1].areaStyle.normal.color = new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
					offset: 0,
					color: 'rgba(69, 109, 192, 0.5)'
				}, {
					offset: 1,
					color: 'rgba(69, 109, 192, 0)'
				}], false);

				var topValue = obj[1][0].num,
					topTime = obj[1][0].key,subMsg;
				if(obj[1].length){
					for(var i = 0;i < obj[1].length;i++){
						opt.series[1].showAllSymbol = false;
						opt.series[1].data[i] = obj[1][i].num;
						if(obj[1][i].num > topValue){
							topValue = obj[1][i].num;
							topTime = obj[1][i].key;
						}
					}
				}
				subMsg = $("#searchKeyword2").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>的声量在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
			}

			$("#hotTrends_msg").html(alertMsg)
			setEchartsOpion({$id:$("#hotTrends"),opt:opt});
		}else{
			$("#hotTrends").html(prompt);
		}
	};

	// 相关排行
	bigpipe.ready('doHotRelatedList',function(result){
		$scope.hotRlelatedActive = [$("#searchKeyword1").val(),$("#searchKeyword2").val(),$("#searchKeyword3").val()];
		if(result.status){
			$scope.$apply(function(){
				$scope.hotRelatedIndex = 0;
				$scope.hotRelatedLists = result.obj;

			});
			if(!result.obj["0"]){
				$("#paihang .table1").hide();
				$("#paihang .relatedTerms_null").height(400);
				$("#paihang .relatedTerms_null").show();
				$("#paihang .relatedTerms_null").html(prompt);
			}
		}else{
			$("#paihang").hide();
		}
	});

	// 模块二：印象词云
	bigpipe.ready('doWordcloud',function(result){
		if(result.status){
			var alertMsg = "";
			if(result.obj[0]){
				var opt1 = $.extend(true,{},echartsOpts["wordCloud"]),maskImage = new Image();
				var topValue="",topName="",subMsg;

				var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色
				opt1.maskImage = maskImage;
				maskImage.onload = function(){
					opt1.series[0].maskImage;
					setEchartsOpion({$id:$(".weiBoWordCloud:eq(0)"),opt:opt1});
				}
				for(var i = 0;i<result.obj[0].length;i++){
					opt1.series[0].data[i] = {name:result.obj[0][i].name,value:result.obj[0][i].num*100, textStyle:{normal:{color:colors[i%3]}}};
					if(i<3){
						topName += "<font class='f_c22'>"+result.obj[0][i].name + "</font>、";
					}
				}

				if(window.screen.width>=320 && window.screen.width<375){
					opt1.series[0] = $.extend(true,opt1.series[0],{ width:'120%',height:"100%",top:'-6%'});
					$("#weiBoWordClouds1").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
					maskImage.src = njxBasePath+'/images/cloud-320.png';
				}
				if(window.screen.width>=375 && window.screen.width<412){
					opt1.series[0] = $.extend(true,opt1.series[0],{ width:'109%',height:"100%",top:'-2%'});
					$("#weiBoWordClouds1").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
					maskImage.src = njxBasePath+'/images/cloud-375.png';
				}
				if(window.screen.width>=412){
					opt1.series[0] = $.extend(true,opt1.series[0],{ width:'100%',height:"102%",top:'-5%'});
					$("#weiBoWordClouds1").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
					maskImage.src = njxBasePath+'/images/cloud.png';
				}

				subMsg = "<font class='f_c17'>"+ $("#searchKeyword1").val()+"</font>";
				topName = topName.substring(0,topName.length-1);
				alertMsg += "在与"+subMsg+"相关的全部信息中,被提及频次最高的词语分别为" + topName+";";
			}else{
				$(".weiBoWordCloud:eq(0)").html(prompt);
			}

			if(result.obj[1]){
				var opt2 = $.extend(true,{},echartsOpts["wordCloud"]),maskImage = new Image();
				var topValue="",topName="",subMsg;

				var colors = ["#97d2f6","#3193cf","#1979db"];//词云颜色
				opt2.maskImage = maskImage;
				maskImage.onload = function(){
					opt2.series[0].maskImage;
					setEchartsOpion({$id:$(".weiBoWordCloud:eq(1)"),opt:opt2});
				}
				for(var i = 0;i<result.obj[1].length;i++){
					opt2.series[0].data[i] = {name:result.obj[1][i].name,value:result.obj[1][i].num*100, textStyle:{normal:{color:colors[i%3]}}};
					if(i<3){
						topName += "<font class='f_c22'>"+result.obj[1][i].name + "</font>、";
					}
				}

				if(window.screen.width>=320 && window.screen.width<375){
					opt2.series[0] = $.extend(true,opt2.series[0],{ width:'120%',height:"100%",top:'-6%'});
					$("#weiBoWordClouds2").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
					maskImage.src = njxBasePath+'/images/cloud-320.png';
				}
				if(window.screen.width>=375 && window.screen.width<412){
					opt2.series[0] = $.extend(true,opt2.series[0],{ width:'109%',height:"100%",top:'-2%'});
					$("#weiBoWordClouds2").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
					maskImage.src = njxBasePath+'/images/cloud-375.png';
				}
				if(window.screen.width>=412){
					opt2.series[0] = $.extend(true,opt2.series[0],{ width:'100%',height:"102%",top:'-5%'});
					$("#weiBoWordClouds2").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
					maskImage.src = njxBasePath+'/images/cloud.png';
				}

				subMsg = "<font class='f_c17'>"+ $("#searchKeyword2").val()+"</font>";
				topName = topName.substring(0,topName.length-1);
				alertMsg += "在与"+subMsg+"相关的全部信息中,被提及频次最高的词语分别为" + topName+";";
			}else{
				$(".weiBoWordCloud:eq(1)").html(prompt);
			}

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#weiBoWordCloud_msg").html(alertMsg);
			}
		}else{
			$(".weiBoWordCloud").html(prompt);
		}
	});

	// 相关排行切换
	$scope.relatedTermsChange = function(index){
		var relatedTerms = $scope.hotRelatedLists[index];
		$(".rank-list ul li").removeClass("active");
		$(".rank-list ul li:eq("+index+")").addClass("active");
		$("#paihang .table1").show();
		$("#paihang .relatedTerms_null").hide();
		if(relatedTerms){
			//$scope.$apply(function(){
			$scope.hotRelatedIndex = index;
			//});
		}else{
			$("#paihang .table1").hide();
			$("#paihang .relatedTerms_null").height(400);
			$("#paihang .relatedTerms_null").show();
			$("#paihang .relatedTerms_null").html(prompt);
			//$("#paihang").html(prompt);
		}
	}

	// 媒体来源
	bigpipe.ready('doSourceStatistics',function(result){
		if(result.status){
			var alertMsg = "";
			if(result.obj[0]){
				var opt = $.extend(true,{},echartsOpts["funnel"]);
				opt.series[0].name = $("#searchKeyword1").val();
				var topValue="",subMsg;
				for(var i = 0;i < result.obj[0].length;i++){
					opt.series[0].data[i].name = result.obj[0][i].name;
					opt.series[1].data[i] = {value:result.obj[0][i].percent.toFixed(2),name:result.obj[0][i].name};
					opt.legend.data[i].name = result.obj[0][i].name;
					if(i<3){
						topValue += "<font class='f_c22'>"+(result.obj[0][i].name+"("+result.obj[0][i].percent.toFixed(2)) +"%)</font>、";
					}
				}
				topValue = topValue.substring(0,topValue.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
				alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+";";
				setEchartsOpion({$id:$(".sourceStatistics:eq(0)"),opt:opt});
			}else{
				$(".sourceStatistics:eq(0)").html(prompt);
			}

			if(result.obj[1]){
				var opt = $.extend(true,{},echartsOpts["funnel"]);
				opt.series[0].name = $("#searchKeyword2").val();
				opt.color = ['#456dc0', '#5aa9d9', '#95cced', '#b7d1f1', '#bdc6cc'];
				var topValue="",subMsg;
				for(var i = 0;i < result.obj[1].length;i++){
					opt.series[0].data[i].name = result.obj[1][i].name;
					opt.series[1].data[i] = {value:result.obj[1][i].percent.toFixed(2),name:result.obj[1][i].name};
					opt.legend.data[i].name = result.obj[1][i].name;
					if(i<3){
						topValue += "<font class='f_c22'>"+(result.obj[1][i].name+"("+result.obj[1][i].percent.toFixed(2)) +"%)</font>、";
					}
				}
				topValue = topValue.substring(0,topValue.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword2").val()+"</font>";
				alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+";";
				setEchartsOpion({$id:$(".sourceStatistics:eq(1)"),opt:opt});
			}else{
				$(".sourceStatistics:eq(1)").html(prompt);
			}

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#sourceStatistics_msg").html(alertMsg);
			}
		}else{
			$(".sourceStatistics").html(prompt);
		}
	});

	// 媒体友好度
	bigpipe.ready('doMediaFriend',function(result){
		if(result.status){
			var alertMsg = "";
			if(window.screen.width>=412){
				$(".mediaCircle").css({width:"250px",height:"250px"})
			}else if(window.screen.width>=375){
				$(".mediaCircle").css({width:"220px",height:"220px"})
			}else{
				$(".mediaCircle").css({width:"180px",height:"180px"})
			}
			if(result.obj[0]){
				var opt = $.extend(true,{},echartsOpts["liquidFill"]);
				var position = parseInt(100-result.obj[0].percent/2);
				opt.series[0].data[0] = (parseInt(result.obj[0].percent) / 100).toFixed(2);
				opt.series[0].label.normal.position = ['50%', position+'%'];

				var topValue="",subMsg;
				for(var i = 0;i < result.obj[0].statList.length;i++){
					var $mediaFriend = $(".mediaSource:eq(0) ul li:eq("+i+")");
					$mediaFriend.find("p:eq(0)").text(result.obj[0].statList[i].name);
					$mediaFriend.find("p:eq(1)").text(result.obj[0].statList[i].percent.toFixed(2)+"%");
					if(i<3){
						topValue += "<font class='f_c22'>"+(result.obj[0].statList[i].name+"("+result.obj[0].statList[i].percent.toFixed(2)) +"%)</font>、";
					}
				}
				topValue = topValue.substring(0,topValue.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
				alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+";";
				setEchartsOpion({$id:$(".mediaFriendlyChart:eq(0)"),opt:opt});
			}else{
				$(".mediaFriendlyChart:eq(0)").html(prompt);
			}

			if(result.obj[1]){
				var opt = $.extend(true,{},echartsOpts["liquidFill"]);
				var position = parseInt(100-result.obj[1].percent/2);
				opt.series[0].data[0] = (parseInt(result.obj[1].percent) / 100).toFixed(2);
				opt.series[0].label.normal.position = ['50%', position+'%'];
				opt.series[0].itemStyle.normal.color = "#577bc6";
				opt.series[0].backgroundStyle.borderColor = "#577bc6";
				opt.series[0].label.normal.textStyle.color = "#577bc6";

				var topValue="",subMsg;
				for(var i = 0;i < result.obj[1].statList.length;i++){
					var $mediaFriend = $(".mediaSource:eq(1) ul li:eq("+i+")");
					$mediaFriend.find("p:eq(0)").text(result.obj[1].statList[i].name);
					$mediaFriend.find("p:eq(1)").text(result.obj[1].statList[i].percent.toFixed(2)+"%");
					if(i<3){
						topValue += "<font class='f_c22'>"+(result.obj[1].statList[i].name+"("+result.obj[1].statList[i].percent.toFixed(2)) +"%)</font>、";
					}
				}
				topValue = topValue.substring(0,topValue.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword2").val()+"</font>";
				alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+";";
				setEchartsOpion({$id:$(".mediaFriendlyChart:eq(1)"),opt:opt});
			}else{
				$(".mediaFriendlyChart:eq(1)").html(prompt);
			}

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#mediaFriendlyChart_msg").html(alertMsg);
			}
		}else{
			$(".mediaFriendlyChart").html(prompt);
		}
	});

	// 地域分布
	bigpipe.ready('doVirtualSolution',function(result){
		if(result.status){
			var alertMsg = "从地域分布来看,";
			if(result.obj[0]){
				var subMsg = "",topName = "";

				var opt = $.extend(true,{},echartsOpts["map"]);
				opt.series[0].name = $("#searchKeyword1").val();
				var max = result.obj[0][0].num;
				for(var i = 0;i<result.obj[0].length;i++){
					opt.series[0].data[i] = {value:result.obj[0][i].num,name:result.obj[0][i].name};
					if(result.obj[0][i].num > max){
						max = result.obj[0][i].num;
					}
				}
				opt.visualMap.max = max;
				opt.series[0].label.normal={show:false,textStyle:{color:'#595959'}};
				opt.visualMap.inRange.color = ['#fab85e','#fa7d43', '#e34743'];
				opt.series[0].itemStyle.emphasis={show:true,areaColor:"#bdd3f5"};
				setEchartsOpion({$id:$(".virtualSolution:eq(0)"),opt:opt});

				var opt = $.extend(true,{},echartsOpts["bar"]);
				opt.series[0].name = $("#searchKeyword1").val();
				opt.series[0].barWidth = 12;
				result.obj[0].reverse();
				for(var j = result.obj[0].length-10,i=0;j<result.obj[0].length;j++,i++){
					if(j<0){
						j=0;
					}
					opt.series[0].data[i] = result.obj[0][j].num;
					opt.yAxis[0].data[i] = result.obj[0][j].name;
					if(j>result.obj[0].length-4){
						topName = "<font class='f_c22'>"+result.obj[0][j].name +"("+result.obj[0][j].num +")条</font>、" +topName;
					}
					subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
				}
				setEchartsOpion({$id:$(".virtualSolutions:eq(0)"),opt:opt});
				topName = topName.substring(0,topName.length-1);
				alertMsg += "与"+subMsg+"相关的信息主要来源"+ topName + ";";
			}else{
				$(".virtualSolution:eq(0)").html(prompt);
				$(".virtualSolutions:eq(0)").html(prompt);
			}

			if(result.obj[1]){
				var subMsg = "",topName = "";

				var opt = $.extend(true,{},echartsOpts["map"]);
				opt.series[0].name = $("#searchKeyword2").val();
				var max = result.obj[1][0].num;
				for(var i = 0;i<result.obj[1].length;i++){
					opt.series[0].data[i] = {value:result.obj[1][i].num,name:result.obj[1][i].name};
					if(result.obj[1][i].num > max){
						max = result.obj[1][i].num;
					}
				}
				opt.visualMap.max = max;
				opt.series[0].label.normal={show:false,textStyle:{color:'#595959'}};
				opt.visualMap.inRange.color = ['#c1d4f7','#98b4e7', '#6e94d9'];
				opt.series[0].itemStyle.emphasis={show:true,areaColor:"#bdd3f5"};
				setEchartsOpion({$id:$(".virtualSolution:eq(1)"),opt:opt});

				var opt = $.extend(true,{},echartsOpts["bar"]);
				opt.series[0].name = $("#searchKeyword2").val();
				opt.series[0].barWidth = 12;
				opt.series[0].itemStyle.normal.color = function(d){//颜色判断  这里用到两种  你可以设置多种
					if(d.dataIndex%2===0){
						return '#96bae6';
					}else{
						return '#5378c5';
					}
				}
				result.obj[1].reverse();
				for(var j = result.obj[1].length-10,i=0;j<result.obj[1].length;j++,i++){
					if(j<0){
						j=0;
					}
					opt.series[0].data[i] = result.obj[1][j].num;
					opt.yAxis[0].data[i] = result.obj[1][j].name;
					if(j>result.obj[1].length-4){
						topName = "<font class='f_c22'>"+result.obj[1][j].name +"("+result.obj[1][j].num +")条</font>、" +topName;
					}
					subMsg = "<font class='f_c17'>"+$("#searchKeyword2").val()+"</font>";
				}
				setEchartsOpion({$id:$(".virtualSolutions:eq(1)"),opt:opt});
				topName = topName.substring(0,topName.length-1);
				alertMsg += "与"+subMsg+"相关的信息主要来源"+ topName + ";";
			}else{
				$(".virtualSolution:eq(1)").html(prompt);
				$(".virtualSolutions:eq(1)").html(prompt);
			}

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#virtualSolutions_msg").html(alertMsg);
			}
		}else{
			$(".virtualSolution").html(prompt);
			$(".virtualSolutions").html(prompt);
		}
	});

	// 相关词
	bigpipe.ready('doRelatedTerms',function(result){
		if(result.status){
			var alertMsg = "";
			if(result.obj[0][0]){
				var opt = $.extend(true,{},echartsOpts["scatter"]);
				var dataList = utils.getAssociation(result.obj[0][0].relatedWordList.slice(0,10),result.obj[0][0]);
				var obj = {data:[50, 50, result.obj[0][0].name, '#eaa269','100%','100%'],dataList:dataList};
				var dataBody = echartsFunc["scatter1"](obj);
				opt.series = dataBody;
				var topName="",subMsg,code = result.obj[0][0].name;
				for(j = 0;j < result.obj[0][0].relatedWordList.length;j++){
					if(j<3){
						topName += "<font class='f_c22'>"+result.obj[0][0].relatedWordList[j].name+"</font>、";
					}
				}
				topName = topName.substring(0,topName.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
				code = "<strong style = 'font-weight: bold;'>"+code+"</strong>";
				alertMsg += "通过对与"+subMsg+"相关的信息进行分析后可看出，与其核心词"+ code + "关联度最高的词语为" + topName + ";";
				setEchartsOpion({$id:$(".relatedTerms:eq(0)"),opt:opt});
			}else{
				$(".relatedTerms:eq(0)").html(prompt);
			}

			if(result.obj[1][0]){
				var opt = $.extend(true,{},echartsOpts["scatter"]);
				var dataList = utils.getAssociation(result.obj[1][0].relatedWordList.slice(0,10),result.obj[1][0]);
				var obj = {data:[50, 50, result.obj[1][0].name, '#eaa269','100%','100%'],dataList:dataList};
				var dataBody = echartsFunc["scatter2"](obj);
				opt.series = dataBody;
				var topName="",subMsg,code = result.obj[1][0].name;
				for(j = 0;j < result.obj[1][0].relatedWordList.length;j++){
					if(j<3){
						topName += "<font class='f_c22'>"+result.obj[1][0].relatedWordList[j].name+"</font>、";
					}
				}
				topName = topName.substring(0,topName.length-1);
				subMsg = "<font class='f_c17'>"+$("#searchKeyword2").val()+"</font>";
				code = "<strong style = 'font-weight: bold;'>"+code+"</strong>";
				alertMsg += "通过对与"+subMsg+"相关的信息进行分析后可看出，与其核心词"+ code + "关联度最高的词语为" + topName + ";";
				setEchartsOpion({$id:$(".relatedTerms:eq(1)"),opt:opt});
			}else{
				$(".relatedTerms:eq(1)").html(prompt);
			}

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#relatedTerms_msg").html(alertMsg);
			}
		}else{
			$(".relatedTerms").html(prompt);
		}
	});

	$scope.goSearch = function(e,time){
		$(e.target).addClass("active");
		$(e.target).siblings().removeClass("active");
		$("#hotsearchdate").val(time);
		$("#searchKeyword1").val($("#searchKeyword1")[0].defaultValue);
		$("#searchKeyword2").val($("#searchKeyword2")[0].defaultValue);
		goSearchLoad();
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
	}).service('utils',function(){
	this.getUperOrDow = function(){
		var list = [0,1];
		var index = parseInt(Math.random() * list.length);
		return list[index];
	},
		this.getYuanSize = function(lo,num) {
			if(num==0){
				return 20;
			}
			var a=lo*80/num;
			var c=a/2;
			if(c<20){
				c=20;
			}
			if(c>30){
				c=30;
			}
			return c;
		},
		this.getAssociation = function(list,relatedWord){
			var scatterList = [];
			var xy1= ["40,30","60,75","33,60","67,40","40,75","60,30","50,20","50,92","75,60","30,30"];
			var xy2= ["20,15","15,45","20,75","50,-8","30,100","40,115","70,0","70,100","85,30","85,60"];
			var xy3= ["0,30","100,75","10,0","95,0","0,75","96,100","10,105","100,30","-5,50","105,50"];
			for(var i = 0;i < list.length;i++){
				var obj = list[i];
				if(i<3){
					var xyAxis = xy1[i].split(",");
				}else if(i<6){
					var xyAxis = xy2[i].split(",");
				}else{
					var xyAxis = xy3[i].split(",");
				}
				var scatterObj = [];
				scatterObj[0] = xyAxis[0];
				scatterObj[1] = xyAxis[1];
				scatterObj[2] = obj.name;
				scatterObj[3] = this.getYuanSize(obj.num,relatedWord.num);
				scatterObj[4] = this.getUperOrDow();
				scatterObj[5] = obj.percent*100;
				scatterList[i] = scatterObj;
			}
			return scatterList;
		}

});

//angularjs自启动
angular.bootstrap(document.documentElement,["ngApp"]);
