document.write("<script language=javascript src="+actionBase+"/js/utils/utils.js></script>");
angular.module('ngApp_opinion', []).controller('initController',function($scope,$http,httpSerive,utils){
	var prompt = "<br> <div align='center' style='padding-top:20px;padding-bottom: 20px;'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p> <p>情绪君暂时计算不出您的情绪</p><p>换个词试试呢~</p></div></div>";
	var prompt2 = "<br> <div align='center' style='padding-top:20px;padding-bottom: 20px;'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p> <p>情绪君暂时计算不出您的情绪</p><p>换个词试试呢~</p></div></div>";
	var loadMore_flag=true;
	var loadMore_flag_two=true;
	var unsealedFalg = true;




	$(function(){
//		if(($("#platform").val() == "android") || ($("#platform").val() == "ios")){
		$(window).scroll(function(){
			var scrollH = document.documentElement.scrollHeight;
			var clientH = document.documentElement.clientHeight;

//		        console.log("scrollH="+scrollH+"|clientH="+clientH);
//		        console.log("document.documentElement.scrollTop="+document.documentElement.scrollTop+"|document.body.scrollTop="+document.body.scrollTop);
//		        console.log("$(document).scrollTop()="+$(document).scrollTop()+"|window.pageYOffset="+window.pageYOffset);


//		        if (scrollH == (document.documentElement.scrollTop | document.body.scrollTop) + clientH){
			//加载新数据
			var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop || window.parent.document.documentElement.scrollTop;;
			if(scrollTop>1300){
				if(loadMore_flag){
					console.log("加载新数据");
					$scope.initLoadMore();
					loadMore_flag=false;
				}
			}
			if(scrollTop>3100){
				if(loadMore_flag_two){
					console.log("加载新数据Two");
					$scope.initLoadMoreTwo();
					loadMore_flag_two=false;
				}
			}
//		        }
		});
//		}

	})
//	$scope.doScopeScroll=function(scrollTop){
//		 console.log("scrollTop="+scrollTop);
//		if(scrollTop>1300){
//        	if(loadMore_flag){
//        		console.log("加载新数据");
//        		$scope.loadMore();
//        		loadMore_flag=false;
//        	}
//        }
//        if(scrollTop>3100){
//        	if(loadMore_flag_two){
//        		console.log("加载新数据Two");
//        		$scope.loadMoreTwo();
//        		loadMore_flag_two=false;
//        	}
//        }
//	}


	$scope.sealReload=function(){//解封之后刷新
		if(isiOS && !($("#platform").val() == "ios")){//IOS浏览器 已经加载过的模块
			$scope.loadMore();
			$scope.loadMoreTwo();
		}else if(($("#platform").val() == "ios")){//IOS APP已经加载过的模块
			if(!loadMore_flag){
				$scope.loadMore();
			}
			if(!loadMore_flag_two){
				$scope.loadMoreTwo();
			}
		}else if($("#platform").val() == "android"){//如果是Android APP

		}else if(isAndroid && !($("#platform").val() == "android")){//Android 浏览器
			if(!loadMore_flag){
				$scope.loadMore();
			}
			if(!loadMore_flag_two){
				$scope.loadMoreTwo();
			}
		}else{//电脑浏览器
			if(!loadMore_flag){
				$scope.loadMore();
			}
			if(!loadMore_flag_two){
				$scope.loadMoreTwo();
			}
		}

//		if(!loadMore_flag || ($("#platform").val() == "ios")){
//			$scope.loadMore();
//		}
//		if(!loadMore_flag_two || ($("#platform").val() == "ios")){
//			$scope.loadMoreTwo();
//		}

		if(!doEmotionProportion_flag){
			$scope.doEmotionProportion();
		}

		if(!doEmotionMap_falg || ($("#platform").val() == "android")){
			$scope.doEmotionMap();
		}
		if(!doContentTrends_flag || ($("#platform").val() == "android")){
			$scope.doContentTrends();
		}

	}
	var doEmotionProportion_flag=true;
	/*****************************************************************/
	//情绪占比
	$scope.doEmotionProportion = function(){
		if(isiOS && !($("#platform").val() == "ios")){//ios浏览器
			if(loadMore_flag){
				$scope.initLoadMore();
			}
			if(loadMore_flag_two){
				$scope.initLoadMoreTwo();
			}

		}
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionProportionV2.action";
		$http.post(urlaction,data)
			.success(function(data){
				if(!(window.top==window.self)){
					doEmotionProportion_flag=parent.handleSealedData(data,urlaction);
				}else{
					doEmotionProportion_flag=handleSealedData(data,urlaction);
				}
				if (!isEmpty(data.obj.data)) {
					if (data.obj.data.length==0) {
						$("#emotionpieEchart").html(prompt);
						$("#emotionpieEchart2").html(prompt);

						$("#emotionpieEchart_msg").text(data.message);
						$("#emotionpieEchart_msg").attr("name",data.msg);
					}else{
						$("#emotionpieEchart").attr("name",data.obj.msg);
						var opt = $.extend(true,{},echartsOpts["emotionProportion"]);
						var colors = data.obj.color;
						var data = data.obj.data;
						var datas = new Array();
						var legend = new Array();

						for(n in data){
							var color = "";
							if(data[n].name == "喜悦"){
								color = colors.xy;
							}else if(data[n].name == "愤怒"){
								color = colors.fn;
							}else if(data[n].name == "悲伤"){
								color = colors.bs;
							}else if(data[n].name == "惊奇"){
								color = colors.jq;
							}else if(data[n].name == "恐惧"){
								color = colors.kj;
							}else if(data[n].name == "中性"){
								color = colors.zx;
							}
							legend.push({
								name : data[n].name,
								textStyle: {
									color: color,
								}
							});
							datas.push({
								value: data[n].value,
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

						var opt2 = $.extend(true,{},echartsOpts["emotionProportion2"]);

						var yAxis = new Array();
						var datas2 = new Array();
						data.reverse();
						for(n in data){
							var color = "";
							if(data[n].name == "喜悦"){
								color = colors.xy;
							}else if(data[n].name == "愤怒"){
								color = colors.fn;
							}else if(data[n].name == "悲伤"){
								color = colors.bs;
							}else if(data[n].name == "惊奇"){
								color = colors.jq;
							}else if(data[n].name == "恐惧"){
								color = colors.kj;
							}else if(data[n].name == "中性"){
								color = colors.zx;
							}

							yAxis.push(data[n].name);
							datas2.push({
								name: data[n].name,
								value: data[n].value,
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
					$("#emotionpieEchart").html(prompt);
					$("#emotionpieEchart2").html(prompt);
				}
			})
	}
	var doEmotionMap_falg=true;
	//情绪地图
	$scope.doEmotionMap = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var url=actionBase+"/view/moodMap/doEmotionMap.action";
		$http.post(url,data).success(function(resultData){
			var result=resultData;
			if(!(window.top==window.self)){
				doEmotionMap_falg=parent.handleSealedData(result,url);
			}else{
				doEmotionMap_falg=handleSealedData(result,url);
			}
			if(result.obj.code=="0000" && result.status==1 ){
				var emotions = result.obj.map1[1];
				var colors = result.obj.map1[3];
				var color_fn=['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
				var color_kj=['#313131','#423e3d','#544d4d','#665e5b','#79716d'];
				var color_xy=['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
				var color_jq=['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
				var color_bs=['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
				var color_zx=['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];
//				var color_fn=['#f6c3b9', '#feaa98','#FF8268'];
//				var color_kj=['#a9c3d5', '#7fb2d5','#68a9d5'];
//				var color_xy=['#f8c7ab', '#ea9a6d', '#fe8e50'];
//				var color_jq=['#fcefbb', '#f8e28b','#ffdf5f'];
//				var color_bs=['#a9f4ff', '#4ae0f6','#00DFFF'];
//				var color_zx=['#98ddd1', '#60d9c3','#30dabb'];
				var opt = $.extend(true,{},echartsOpts["map2"]);
				opt.visualMap.categories = emotions;
				opt.visualMap.pieces = [{
					value : 0,
					label : emotions[0],
					color : colors.xy
				}, {
					value : 1,
					label : emotions[1],
					color : colors.zx
				}, {
					value : 2,
					label : emotions[2],
					color : colors.fn
				}, {
					value : 3,
					label : emotions[3],
					color : colors.bs
				}, {
					value : 4,
					label : emotions[4],
					color : colors.jq
				}, {
					value : 5,
					label : emotions[5],
					color : colors.kj
				}];
				opt.series[0].name = "显著情绪";
				opt.series[0].data = result.obj.map1[2].concat(opt.series[0].data);
				opt.series[0].tooltip = {
					textStyle : {
						align : 'left'
					},
					formatter : function(a) {
						var label = '显著情绪 - ' + emotions[a.value] + "<br/>";
						var emotion = a.data.emotion;
						if (emotion != null) {
							for (var i = 0; i < emotion.length; i++) {
								label += emotion[i].name + " : ";
								if (emotion[i].value > 0) {
									label += emotion[i].value;
								} else {
									label += "-";
								}
								label += "<br/>";
							}
						} else {
							label = "暂无信息";
						}
						return label;
					}
				};
				setEchartsOpion({$id:$("#mapChart1"),opt:opt});
				$("#mapChart1").attr("name",result.obj.msg);
				$scope.tableStatus = 1
				$scope.otherEmotions = result.obj.map2 || [];
				$scope.areaStatistics = result.obj.map1[0];
				$("#xzMoodTop10Desc").text("微博声量TOP10地区显著情绪：");
				$("#mapChart1").attr("name",result.obj.msg);
			}else{
				$("#mapChart1").html(prompt);
				if(!isEmpty(result.message)){
					$("#emotionpieEchart_msg").text(result.message);
				}
				$("#emotionpieEchart_msg").attr("name",result.msg);
			}
		})
	}
	function isEmpty(obj){
		if(typeof obj == "undefined" || obj == null || obj == ""){
			return true;
		}else{
			return false;
		}
	}
	//各情绪地图
	$scope.doChangeEmotionMap = function(index,e){
		$("#areaStatistics .echarts").hide();
		console.log($("#mapChart" + (index+1)));
		$("#mapChart" + (index+1)).show();
		if(e){
			$(e.target).addClass("active");
			$(e.target).siblings().removeClass("active");
			var text = $(e.target).text();
		}

		if(text == "显著"){
			$("#xzMoodTop10Desc").text("微博声量TOP10地区显著情绪：");
			$scope.tableStatus = 1;
		}else{
			var opt = $.extend(true,{},echartsOpts["map2"]);
//			if(index == 1){
//				var colormap = ["#cf421e","#d3582d","#d86b3d","#dd7f52","#e19167"];
//			}else if(index == 2){
//				var colormap = ["#313131","#423e3d","#544d4b","#665e5b","#79716d"];
//			}else if(index == 3){
//				var colormap = ["#0e7dc0","#4381ba","#5e8dc1","#759ac8","#8ba8d0"];
//			}else if(index == 4){
//				var colormap = ["#3fa579","#5da782","#74b08e","#8aba9c","#9dc4aa"];
//			}else if(index == 5){
//				var colormap = ["#f18d00","#ea9630","#eca147","#efae5e","#f2ba76"];
//			}else if(index == 6){
//				var colormap = ["#9098a5","#9ba0ab","#a6aab4","#b2b4bd","#bdbec6"];
//			}
			if(index == 1){
				var colormap = ['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
			}else if(index == 2){
				var colormap = ['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];
			}else if(index == 3){
				var colormap = ['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
			}else if(index == 4){
				var colormap = ['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
			}else if(index == 5){
				var colormap = ['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
			}else if(index == 6){
				var colormap = ['#313131','#423e3d','#544d4d','#665e5b','#79716d']
			}
			$scope.emotionData = $scope.otherEmotions[index-1];
			if($scope.emotionData && $scope.emotionData.data && $scope.emotionData.data.length>0){
				var max=0,min=0,svg=0;
				max = $scope.emotionData.data[0].value;
				min = $scope.emotionData.data[$scope.emotionData.data.length-1].value;
				svg = Math.ceil((max-min)/5);
				if(svg==0){
					svg=max;
				}
				var pieces = [
					{min: min,max: svg,color:colormap[4]},
					{min: svg+1,max: svg*2,color:colormap[3]},
					{min: svg*2+1,max: svg*3,color:colormap[2]},
					{min: svg*3+1,max: svg*4,color:colormap[1]},
					{min: svg*4+1,max: max,color:colormap[0]}
				];
				opt.legend={show:false};
				opt.series[0].name = text+"情绪";
				opt.series[0].data = $scope.emotionData.data.concat(opt.series[0].data);
				opt.visualMap = {
					type: 'piecewise', // 分段型。
					splitNumber: 5,
					color: '#ebdba4',
					pieces:pieces,
					left: 'left',
					top: 'bottom',// 文本，默认为数值文本
					calculable: true,
					inverse : false
				};
				setEchartsOpion({$id:$("#mapChart"+(index+1)),opt:opt});
				$("#xzMoodTop10Desc").text(text+"TOP10地区显著情绪：");
				$scope.tableStatus = 0;
			}else{
				$("#mapChart"+(index+1)).html(prompt);
				$("#xzMoodTop10Desc").text("");
				$scope.tableStatus = 2;
				if(!isEmpty(result.message)){
					$("#emotionpieEchart_msg").text(result.message);
				}
			}
		}
	}
	var doContentTrends_flag=true;
	//情绪走势
	$scope.doContentTrends = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var url=actionBase+"/view/moodMap/doMoodContentTrends2.action";
		$http.post(url,data)
			.success(function(result){
				if(!(window.top==window.self)){
					doContentTrends_flag=parent.handleSealedData(result,url);
				}else{
					doContentTrends_flag=handleSealedData(result,url);
				}
				$("#contentTrends1").attr("name",result.obj.msg);
//			if(result.status){
				if(!isEmpty(result.obj.data) && result.obj.code=="0000"){
					var contentTrends1 = result.obj.data;
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
							},smooth: true,data:[]
						};
						for(var j = 0;j<contentTrends1[i].statList.length;j++){
							opt.xAxis[0].data[j] = contentTrends1[i].statList[j].name;
							opt.series[i].data[j] = contentTrends1[i].statList[j].num;
						}
						if(contentTrends1[i].name == "愤怒"){
							opt.series[i].itemStyle.normal.color = "#ec4444";
							opt.series[i].itemStyle.normal.borderColor = "#ec4444";
							opt.series[i].lineStyle.normal.color = "#ec4444";
						}else if(contentTrends1[i].name == "恐惧"){
							opt.series[i].itemStyle.normal.color = "#333";
							opt.series[i].itemStyle.normal.borderColor = "#333";
							opt.series[i].lineStyle.normal.color = "#333";
						}else if(contentTrends1[i].name == "悲伤"){
							opt.series[i].itemStyle.normal.color = "#24AEEB";
							opt.series[i].itemStyle.normal.borderColor = "#24AEEB";
							opt.series[i].lineStyle.normal.color = "#24AEEB";
						}else if(contentTrends1[i].name == "惊奇"){
							opt.series[i].itemStyle.normal.color = "#80ab10";
							opt.series[i].itemStyle.normal.borderColor = "#80ab10";
							opt.series[i].lineStyle.normal.color = "#80ab10";
						}else if(contentTrends1[i].name == "喜悦"){
							opt.series[i].itemStyle.normal.color = "#ff8500";
							opt.series[i].itemStyle.normal.borderColor = "#ff8500";
							opt.series[i].lineStyle.normal.color = "#ff8500";
						}else if(contentTrends1[i].name == "中性"){
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
	var doEmotionSex_flag=true;
	//六元情绪洞察-性别情绪分布
	$scope.doEmotionSex = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionSex2.action";
		$http.post(urlaction,data)
			.success(function(data){
				if(!(window.top==window.self)){
					doEmotionSex_flag=parent.handleSealedData(data,urlaction);
				}else{
					doEmotionSex_flag=handleSealedData(data,urlaction);
				}
				$("#emotionBar5").attr("name",data.obj.msg);
				if (!isEmpty(data.status) && data.status==1 && data.obj.code=="0000") {
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
	var doEmotionType_flag=true;
	//六元情绪洞察-用户认证类型情绪分布
	$scope.doEmotionType = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionType2.action";
		$http.post(urlaction,data)
			.success(function(data){
				if(!(window.top==window.self)){
					doEmotionType_flag=parent.handleSealedData(data,urlaction);
				}else{
					doEmotionType_flag=handleSealedData(data,urlaction);
				}
				$("#emotionBar6").attr("name",data.obj.msg);
				if (!isEmpty(data.status) && data.status==1 && data.obj.code=="0000") {
					var opt = $.extend(true,{},echartsOpts["emotionType"]);
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
	var doEmotionSexV2_flag=true;
	//二元情绪洞察-性别情绪分布
	$scope.doEmotionSexV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionSexV2.action";
		$http.post(urlaction,data)
			.success(function(data){
				if(!(window.top==window.self)){
					doEmotionSexV2_flag=parent.handleSealedData(data,urlaction);
				}else{
					doEmotionSexV2_flag=handleSealedData(data,urlaction);
				}
				$("#emotionBar1").attr("name",data.obj[0].msg);
				if (!isEmpty(data.status) && data.status==1 && !isEmpty(data.obj[0]) && data.obj[0].code=="0000") {
					var opt = $.extend(true,{},echartsOpts["emotionSex_kb"]);


					opt.series[0].data[0].name=data.obj[0].zm[0].toFixed(2) + '%';
					opt.series[0].data[1].name=data.obj[0].fm[0].toFixed(2) + '%';

					opt.series[2].data[0].value=data.obj[0].zm[0];
					opt.series[2].data[1].value=data.obj[0].fm[0];
					if(data.obj[0].zm[1]!==null){
						opt.series[3].data[0].name=data.obj[0].zm[1].toFixed(2) + '%';
					}
					if(data.obj[0].fm[1]!==null){
						opt.series[3].data[1].name=data.obj[0].fm[1].toFixed(2) + '%';
					}

					if(data.obj[0].zm[1]!==null){
						opt.series[5].data[0].value='-' + data.obj[0].zm[1].toFixed(2);
					}
					if(data.obj[0].fm[1]!==null){
						opt.series[5].data[1].value='-' + data.obj[0].fm[1].toFixed(2);
					}
					setEchartsOpion({$id:$("#emotionBar1"),opt:opt});

				}else{
					$("#emotionBar1").html(prompt);
				}
			})
	}
	var doEmotionTypeV2_flag=true;
	//二元情绪洞察-用户认证类型情绪分布
	$scope.doEmotionTypeV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionTypeV2.action";
		$http.post(urlaction,data)
			.success(function(data){
				if(!(window.top==window.self)){
					doEmotionTypeV2_flag=parent.handleSealedData(data,urlaction);
				}else{
					doEmotionTypeV2_flag=handleSealedData(data,urlaction);
				}
				$("#emotionBar2").attr("name",data.obj[0].msg);
				if (data.status) {
					var opt = $.extend(true,{},echartsOpts["emotionType_kb"]);
					var zmData = new Array();
					var fmData = new Array();
					for(n in data.obj[0].zm){
						zmData.push({
							name: data.obj[0].zm[n].toFixed(2) + '%',
							value: '100'
						})
					}
					for(n in data.obj[0].fm){
						fmData.push({
							name: data.obj[0].fm[n].toFixed(2) + '%',
							value: '100'
						})
					}

					opt.yAxis[1].data=data.obj[0].legend;
					opt.series[0].data=data.obj[0].zm;
					opt.series[1].data=zmData;
					opt.series[2].data=data.obj[0].fm;
					opt.series[3].data=fmData;
					setEchartsOpion({$id:$("#emotionBar2"),opt:opt});
				}else{
					$("#emotionBar2").html(prompt);
				}
			});
	}
	var doEmotionFansV2_flag=true;
	//二元情绪洞察-粉丝数量分布
	$scope.doEmotionFansV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionFansV2.action";
		$http.post(urlaction,data).success(function(data){
			if(!(window.top==window.self)){
				doEmotionFansV2_flag=parent.handleSealedData(data,urlaction);
			}else{
				doEmotionFansV2_flag=handleSealedData(data,urlaction);
			}
			$("#emotionBar3").attr("name",data.obj[0].msg);
			if (data.status) {
				var opt = $.extend(true,{},echartsOpts["emotionFans"]);

				opt.xAxis[0].data=data.obj[0].xAxis;
				opt.series[0].data=data.obj[0].zm;
				opt.series[1].data=data.obj[0].fm;
				setEchartsOpion({$id:$("#emotionBar3"),opt:opt});
			}else{
				$("#emotionBar3").html(prompt);
			}
		});
	}
	var doEmotionLevelV2_flag=true;
	//二元情绪洞察-转发层级
	$scope.doEmotionLevelV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionLevelV2.action";
		$http.post(urlaction,data).success(function(data){
			if(!(window.top==window.self)){
				doEmotionLevelV2_flag=parent.handleSealedData(data,urlaction);
			}else{
				doEmotionLevelV2_flag=handleSealedData(data,urlaction);
			}
			$("#emotionBar4").attr("name",data.obj[0].msg);
			if (data.status) {
				var opt = $.extend(true,{},echartsOpts["emotionLevel"]);
				var zm = data.obj[0].zm;
				var fm = data.obj[0].fm;
				var name = data.obj[0].name;
				var seriesData = new Array();
				var center = ['12%', '35%', '58%', '81%'];
				var html = "";
				var x=1;
				for(n in name){
					if(zm[n] == 0 && fm[n] == 0){
						seriesData.push({
							name: name[n],
							center: ['28%', center[n]],
							radius: ['19%', '26%'],
							type: 'pie',
							labelLine: {
								normal: {
									show: false
								}
							},
							data: [{
								value: zm[n],
								name: '非敏感',
								itemStyle:{
									normal: {
										color: '#EEEEEE'
									},
								},
								label: {
									normal: {
										formatter: name[n],
										position: 'center',
										show: true,
										textStyle: {
											fontSize: '14',
											fontWeight: 'bold',
											color: '#000'
										}
									}
								}
							}, {
								value: fm[n],
								name: '敏感',
								itemStyle: {
									normal: {
										color: '#EEEEEE'
									},
									emphasis: {
										show:false
									}
								},
								label: {
									normal: {
										show: false,
									}
								},
								hoverAnimation: false
							}]
						})
					}else{
						seriesData.push({
							name: name[n],
							center: ['28%', center[n]],
							radius: ['19%', '26%'],
							type: 'pie',
							labelLine: {
								normal: {
									show: false
								}
							},
							data: [{
								value: zm[n],
								name: '非敏感',
								itemStyle:{
									normal: {
										color: '#367DB7'
									},
								},
								label: {
									normal: {
										formatter: name[n],
										position: 'center',
										show: true,
										textStyle: {
											fontSize: '14',
											fontWeight: 'bold',
											color: '#000'
										}
									}
								}
							}, {
								value: fm[n],
								name: '敏感',
								itemStyle: {
									normal: {
										color: '#CC6E54'
									},
									emphasis: {
										color: '#CC6E54'
									}
								},
								label: {
									normal: {
										show: false,
									}
								},
								hoverAnimation: true
							}]
						})
					}
					var total = parseInt(zm[n]) + parseInt(fm[n]);
					var zmPre = 0;
					var fmPre = 0;
					if(total != 0){
						zmPre = (100*parseInt(zm[n])/total).toFixed(2);
						fmPre = (100*parseInt(fm[n])/total).toFixed(2);
					}

					html += '<li class="onepotion'+x+'"><p class="pieColor1">非敏感 ' + zmPre + '%</p><p class="pieColor2">敏感 ' + fmPre + '%</p></li>'
					x++;
				}
				$("#ulemotionBar4").html(html);
				opt.series=seriesData;
				setEchartsOpion({$id:$("#emotionBar4"),opt:opt});
			}else{
				$("#emotionBar4").html(prompt);
			}
		});
	}
	var doEmotionHobbyV2_flag=true;
	//二元情绪洞察-兴趣标签
	$scope.doEmotionHobbyV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doEmotionHobbyV2.action";
		$http.post(urlaction,data).success(function(data){
			if(!(window.top==window.self)){
				doEmotionHobbyV2_flag=parent.handleSealedData(data,urlaction);
			}else{
				doEmotionHobbyV2_flag=handleSealedData(data,urlaction);
			}
			$("#interestWordCloud-zm").attr("name",data.obj[0].msg);
			if (data.status) {
				var zm = data.obj[0].zm;
				var fm = data.obj[0].fm;
				var html = '';
				if(zm == null || zm.length == 0){
					$("#interestWordCloud-zm").html(prompt2);
					$("#interestWordCloud-zm").removeClass("positive-con");
				}else{
					for(n in zm){
						var index = parseInt(n)+1;
						html += '<p class="posiab_' + index + '">';
						html += '<a href="javascript:;" data-toggle="tooltip" data-placement="right" title="" data-original-title="' + zm[n].name + '：' + zm[n].value + '">' + zm[n].name + '</a></p>';
					}
					$("#interestWordCloud-zm").html(html);
				}

				html = '';
				if(fm == null || fm.length == 0){
					$("#interestWordCloud-fm").html(prompt2);
					$("#interestWordCloud-fm").removeClass("positive-con");
				}else{
					for(n in fm){
						var index = parseInt(n)+1;
						html += '<p class="posiab_' + index + '">';
						html += '<a href="javascript:;" data-toggle="tooltip" data-placement="right" title="" data-original-title="' + fm[n].name + '：' + fm[n].value + '">' + fm[n].name + '</a></p>';
					}
					$("#interestWordCloud-fm").html(html);
				}
			}
		});
	}
	var doZMHotWordV2_flag=true;
	//二元情绪洞察-正面热词
	$scope.doZMHotWordV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doZMHotWordV2.action";
		$http.post(urlaction,data).success(function(data){
			if(!(window.top==window.self)){
				doZMHotWordV2_flag=parent.handleSealedData(data,urlaction);
			}else{
				doZMHotWordV2_flag=handleSealedData(data,urlaction);
			}
			$("#zmHotWord").attr("name",data.obj[0].msg);
			if (data.status) {
				var zmHotWords = data.obj[0].zmWords;
				if(zmHotWords.length==0){
					$("#zmHotWord").html(prompt2);
				}else{
					var html = "";
					for (n in zmHotWords){
						var name = zmHotWords[n].name;
						if(name.length > 5){
							name = name.substring(0, 4) + "...";
						}
						html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + zmHotWords[n].name + "：" + zmHotWords[n].count + "\">" + name +  "</a></li>";
					}
					$("#zmHotWord-ul").html(html);
				}
			}else{
				$("#zmHotWord").html(prompt2);
			}
		});
	}
	var doFMHotWordV2_flag=true;
	//二元情绪洞察-负面热词
	$scope.doFMHotWordV2 = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/moodMap/doFMHotWordV2.action";
		$http.post(urlaction,data).success(function(data){
			if(!(window.top==window.self)){
				doFMHotWordV2_flag=parent.handleSealedData(data,urlaction);
			}else{
				doFMHotWordV2_flag=handleSealedData(data,urlaction);
			}
			$("#fmHotWord").attr("name",data.obj[0].msg);
			if (data.status) {
				var fmHotWords = data.obj[0].fmWords;
				if(fmHotWords.length==0){
					$("#fmHotWord").html(prompt2);
				}else{
					var html = "";
					for (n in fmHotWords){
						var name = fmHotWords[n].name;
						if(name.length > 5){
							name = name.substring(0, 4) + "...";
						}
						html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + fmHotWords[n].name + "：" + fmHotWords[n].count + "\">" + name +  "</a></li>";
					}
					$("#fmHotWord-ul").html(html);
				}
			}else{
				$("#fmHotWord").html(prompt2);
			}
		});
	}

	$scope.changeMood = function(type,e){
		$(e.target).addClass('active').siblings().removeClass('active');
		var index = $(e.target).index();
		$(".legend-con>.legend-item").eq(index).addClass('active').siblings().removeClass('active');
		if(type == 2){
			$scope.doEmotionSexV2();
			$scope.doEmotionTypeV2();
			$scope.doEmotionFansV2();
			$scope.doEmotionLevelV2();
			$scope.doEmotionHobbyV2();
			$scope.doZMHotWordV2();
			$scope.doFMHotWordV2();
		}
	}
	$scope.loadMore=function(){

		/**
		 * 六元情绪洞察
		 */
		//性别[情绪分布]
		if(!doEmotionSex_flag){
			$scope.doEmotionSex();
		}

		//用户认证类型
		if(!doEmotionType_flag){
			$scope.doEmotionType();
		}

		/**
		 * 二元情感洞察
		 */
		//性别
		if(!doEmotionSexV2_flag){
			$scope.doEmotionSexV2();
		}

		//用户认证类型
		if(!doEmotionTypeV2_flag){
			$scope.doEmotionTypeV2();
		}

	}
	$scope.initLoadMore=function(){

		/**
		 * 六元情绪洞察
		 */
		//性别[情绪分布]
		$scope.doEmotionSex();

		//用户认证类型
		$scope.doEmotionType();

		/**
		 * 二元情感洞察
		 */
		//性别
		$scope.doEmotionSexV2();

		//用户认证类型
		$scope.doEmotionTypeV2();
		loadMore_flag=false;
	}
	$scope.loadMoreTwo=function(){
		if(!doEmotionFansV2_flag){
			$scope.doEmotionFansV2();
		}
		if(!doEmotionLevelV2_flag){
			$scope.doEmotionLevelV2();
		}
		if(!doEmotionHobbyV2_flag){
			$scope.doEmotionHobbyV2();
		}
		if(!doZMHotWordV2_flag){
			$scope.doZMHotWordV2();
		}
		if(!doFMHotWordV2_flag){
			$scope.doFMHotWordV2();
		}

	}
	$scope.initLoadMoreTwo=function(){
		$scope.doEmotionFansV2();
		$scope.doEmotionLevelV2();
		$scope.doEmotionHobbyV2();
		$scope.doZMHotWordV2();
		$scope.doFMHotWordV2();
		loadMore_flag_two=false;
	}

	/****************************************************************/

	$scope.goSearch = function(e,time){
		$(e.target).addClass("active");
		$(e.target).siblings().removeClass("active");
		$("#hotsearchdate").val(time);
		$("#searchKeyword1").val($("#searchKeyword1")[0].defaultValue);
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
	})
	.service('utils',function(){
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
					scatterObj[5] = obj.percent * 100;
					scatterList[i] = scatterObj;
				}
				return scatterList;
			}
	});

function commafy(num){
	if((num.toString()).trim()==""){
		return"";
	}
	if(isNaN(num)){
		return"";
	}
	num = num.toString();
	if(/^.*\..*$/.test(num)){
		var pointIndex =num.lastIndexOf(".");
		var intPart = num.substring(0,pointIndex);
		var pointPart =num.substring(pointIndex+1,num.length);
		intPart = intPart +"";
		var re =/(-?\d+)(\d{3})/
		while(re.test(intPart)){
			intPart =intPart.replace(re,"$1,$2")
		}
		num = intPart+"."+pointPart;
	}else{
		num = num.toString();
		var re =/(-?\d+)(\d{3})/
		while(re.test(num)){
			num =num.replace(re,"$1,$2")
		}
	}
	return num;

}
//angularjs自启动
angular.bootstrap(document.documentElement,["ngApp_opinion"]);