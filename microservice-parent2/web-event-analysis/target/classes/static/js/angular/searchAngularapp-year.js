angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive){
	var prompt = "<div align='center' style='padding-top:50px'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p><p>热度君暂时计算不出您的热度</p><p>换个词试试呢~</p></div></div>";
	// 热度指数
	bigpipe.ready('doHeatValue',function(result){
		console.log("热度指数");
		console.log(result);
		$("#circle1").show();
		$("#hotsSpinner1").hide();
		if(result.status){
			var str = "";
			console.log(result.obj.length);
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
			//$("title").text("一起来比比热度指数吧~"+$("#searchKeyword1").val()+"的热度指数在"+str+",快戳");
			$scope.$apply(function(){
				$scope.hots= result.obj;
				$scope.hotratioHotCustom = "<0.01";
			});
		}
	});
	// 热度走势
	bigpipe.ready('doEventTrends',function(result){
		console.log("热度走势");
		console.log(result);
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["line"]),colors = ['#39ae7d','#588FE5','#A4D720'];
			opt.legend.left	 = "30";
			opt.color = colors;
			var alertMsg = "从热度指数变化趋势来看,",g = 0;

			for(var key in result.obj){
				opt.legend.data[g] = result.obj[key].name;
				opt.legend.data[g] = $.extend(true,opt.legend.data[g],{name:result.obj[key].name,textStyle:{color:colors[g%3]}})

				opt.series[g] = $.extend(true,{},echartsOpts["line"]).series[0];
				opt.series[g].name = result.obj[key].name;

				if(!result.obj[key].statStatListList.length){
					continue;
				}
				var topValue = result.obj[key].statStatListList[0].total.toFixed(2),topTime,subMsg;
				var topTime = result.obj[key].statStatListList[0].name;
				for(var i = 0;i<result.obj[key].statStatListList.length;i++){
					opt.series[g].showAllSymbol = false;
					opt.series[g].data[i] = result.obj[key].statStatListList[i].total.toFixed(2);
					opt.xAxis[0].data[i] = result.obj[key].statStatListList[i].name+":00";

					if(parseFloat(result.obj[key].statStatListList[i].total) > parseFloat(topValue)){
						topValue = result.obj[key].statStatListList[i].total.toFixed(2);
						topTime = result.obj[key].statStatListList[i].name;
					}
				}
				if(key==0){
					subMsg = $("#searchKeyword1").val();
					alertMsg += '<font class="f_c17">';
				}
				if(key==1){
					subMsg = $("#searchKeyword2").val();
					alertMsg += '<font class="f_c1">';
				}
				if(key==2){
					subMsg = $("#searchKeyword3").val();
					alertMsg += '<font class="f_c18">';
				}
				alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c17">' + topValue + '</font>的峰值。';
				g++;
			}
			$("#hotTrends_msg").html(alertMsg)
			setEchartsOpion({$id:$("#hotTrends"),opt:opt});
		}else{
			$("#hotTrends").html(prompt);
		}
	});
	// 热度聚类
	bigpipe.ready('doHotCluster',function(result){
		if(result.status){
			$scope.$apply(function(){
				$scope.hotclusters= result.obj;
			});
		}
	});

	// 模块二：印象词云
	bigpipe.ready('doWeiBoWordCloud',function(result){
		var alertMsg = "";
		var g = 0;
		for(var key in result.obj){
			var topValue="",topName="",subMsg;
			for(var i = 0;i<result.obj[key].length;i++){
				if(i<3){
					//topValue += (result.obj[key][i].name+"("+result.obj[key][i].percentStr) +")、";
					topName += result.obj[key][i].name + "、";
				}
			}
			if(key==0){
				subMsg = "<font class='f_c17'>"+ $("#searchKeyword1").val()+"</font>";
			}
			if(key==1){
				subMsg = "<font class='f_c1'>"+ $("#searchKeyword2").val()+"</font>";
			}
			if(key==2){
				subMsg =  "<font class='f_c18'>"+ $("#searchKeyword3").val()+"</font>";
			}
			//topValue = topValue.substring(0,topValue.length-1);
			topName = topName.substring(0,topName.length-1);
			alertMsg += "在与"+subMsg+"相关的全部信息中,被提及频次最高的词语分别为" + topName+";";
			g++;
		}
		if(alertMsg){
			alertMsg = alertMsg.substring(0,alertMsg.length-1);
			alertMsg +="。";
		}
		$("#weiBoWordCloud_msg").html(alertMsg);
		$scope.wordClouds = result.obj;
		$scope.wordCloudChange(0)
	});
	//切换关键词 sequence(区分是第几个词)
	$scope.changeSearchword = function(sequence){
		console.log(sequence);
		if(sequence == 0){
			$("#search1").addClass("cur");
			$("#search2").removeClass("cur");
			$("#search3").removeClass("cur");
			$("#statistics1").css("display","block");
			$("#statistics2").css("display","none");
			$("#statistics3").css("display","none");
			$("#data1").css("display","block");
			$("#data2").css("display","none");
			$("#data3").css("display","none");
			var opts = {
				colorStart: '#fff',   // Colors
				colorStop: '#fff',    // just experiment with them
				strokeColor: '#fcc7a1',   // to see which ones work best for you
				lineWidth: 0.05, // The line thickness
			};
			demoGauge = new Donut(document.getElementById("canvas-preview")).setOptions(opts);
			demoGauge.setTextField(document.getElementById("preview-textfield"));
			demoGauge.maxValue = 100;
			console.log($("#hotvalue1").text().length);
			var value = $("#hotvalue1").text();
			console.log(value);
			console.log(value.indexOf("<0.01"));
			if(value.indexOf("<0.01")==0){
				value = 0.01;
			}
			demoGauge.set(value);
		}else if(sequence == 1){
			$("#search2").addClass("cur");
			$("#search1").removeClass("cur");
			$("#search3").removeClass("cur");
			$("#statistics2").css("display","block");
			$("#statistics1").css("display","none");
			$("#statistics3").css("display","none");
			$("#data2").css("display","block");
			$("#data1").css("display","none");
			$("#data3").css("display","none");
			var opts = {
				colorStart: '#fff',   // Colors
				colorStop: '#fff',    // just experiment with them
				strokeColor: '#fcc7a1',   // to see which ones work best for you
				lineWidth: 0.05, // The line thickness
			};
			demoGauge = new Donut(document.getElementById("canvas-preview")).setOptions(opts);
			demoGauge.setTextField(document.getElementById("preview-textfield"));
			demoGauge.maxValue = 100;
			var value = $("#hotvalue2").text();
			console.log(value);
			console.log(value.indexOf("<0.01"));

			if(value.indexOf("<0.01")==0){
				value = 0.01;
			}
			demoGauge.set(value);
		}else if(sequence == 2){
			$("#search3").addClass("cur");
			$("#search1").removeClass("cur");
			$("#search2").removeClass("cur");
			$("#statistics3").css("display","block");
			$("#statistics2").css("display","none");
			$("#statistics1").css("display","none");
			$("#data3").css("display","block");
			$("#data2").css("display","none");
			$("#data1").css("display","none");
			var opts = {
				colorStart: '#fff',   // Colors
				colorStop: '#fff',    // just experiment with them
				strokeColor: '#fcc7a1',   // to see which ones work best for you
				lineWidth: 0.05, // The line thickness
			};
			demoGauge = new Donut(document.getElementById("canvas-preview")).setOptions(opts);
			demoGauge.setTextField(document.getElementById("preview-textfield"));
			demoGauge.maxValue = 100;
			var value = $("#hotvalue3").text();
			console.log(value);
			console.log(value.indexOf("<0.01"));

			if(value.indexOf("<0.01")==0){
				value = 0.01;
			}
			demoGauge.set(value);
		}
	}
	$scope.wordCloudChange = function(index){
		$(".weiBoWordCloud").hide();
		$(".weiBoWordCloud:eq("+index+")").show();
		$(".weiBoWordtabs a").removeClass("f_c12");
		$(".weiBoWordtabs a:eq("+index+")").addClass("f_c12");

		var opt = $.extend(true,{},echartsOpts["wordCloud"]),maskImage = new Image(),
			wordCloud;

		if($scope.wordClouds)
			wordCloud = $scope.wordClouds[index];
		if(wordCloud && wordCloud.length){
			colors = ["#3fad7e","#0086ce","#5573b6"];
			opt.maskImage = maskImage;
			for(var i = 0;i<wordCloud.length;i++){

				opt.series[0].data[i] = {name:wordCloud[i].name,value:wordCloud[i].num*100, textStyle:{normal:{color:colors[i%3]}}};

			}

			maskImage.onload = function(){
				opt.series[0].maskImage;

				setEchartsOpion({$id:$(".weiBoWordCloud:eq("+index+")"),opt:opt});
			}
//
			if(window.screen.width>=320 && window.screen.width<375){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'120%',top:'-6%'});
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
				maskImage.src = njxBasePath+'/images/cloud-320.png';
			}
			if(window.screen.width>=375 && window.screen.width<412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'109%',top:'-2%'});
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
				maskImage.src = njxBasePath+'/images/cloud-375.png';
			}
			if(window.screen.width>=412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'98%',top:'-5%'});
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
				maskImage.src = njxBasePath+'/images/cloud.png';
			}
		}else{
			$(".weiBoWordCloud:eq("+index+")").html(prompt);
		}
	}

	// 媒体来源
	bigpipe.ready('doSourceStatistics',function(result){
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["bar"]);
			opt = $.extend(true,opt,{legend: {data:[],x:30,y:20},xAxis:[{
					type : 'value',
					axisLabel : {
						formatter:function(v){
							if(v>=1000){
								return (v/1000)+"k";
							}else{
								return v;
							}
						}
					}
				}],yAxis:[{
					type : 'category',
					data : []
				}],color:['#39ae7d','#588FE5','#A4D720']});
			var alertMsg = "";

			opt.legend.data[0] = $("#searchKeyword1").val();
			opt.series[0].name = $("#searchKeyword1").val();
			var topValue="",topName="",subMsg;
			result.obj[0].reverse();
			for(var i = 0;i<result.obj[0].length;i++){
				opt.series[0].data[i] = {value:result.obj[0][i].num,name:result.obj[0][i].name};
				opt.yAxis[0].data[i] = result.obj[0][i].name;
				if(i<3){
					topValue += (result.obj[0][result.obj[0].length-1-i].name+"("+result.obj[0][result.obj[0].length-1-i].percentStr) +")、";
				}
			}
			topValue = topValue.substring(0,topValue.length-1);
			subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
			alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+"。";
			if(!alertMsg){
				$("#sourceStatistics").html(prompt);
			}else{
				$("#sourceStatistics_msg").html(alertMsg);
				setEchartsOpion({$id:$("#sourceStatistics"),opt:opt});
			}
		}else{
			$("#sourceStatistics").html(prompt);
		}
	});
	// 地域分布
	bigpipe.ready('doVirtualSolution',function(data){
		$scope.virtualSolutions = data.obj;
		$scope.virtualSolutionChange(0);
		var opt = $.extend(true,{},echartsOpts["bar"]);
		opt = $.extend(true,opt,{legend: {data:[],x:30,y:25},xAxis:[{
				type : 'value',
				boundaryGap : [0, 0.01],
				axisLabel : {
					formatter:function(v){
						if(v>=1000){
							return (v/1000)+"k";
						}else{
							return v;
						}
					}
				}
			}],yAxis:[{
				type : 'category',
				data : []
			}],color:['#39ae7d','#588fe5','#A4D720']});
		var g = 0,subMsg = "",topName = "",alertMsg = "从地域分布来看,";
		for(var key in data.obj){
			opt.legend.data[g] = $("#searchKeyword"+(parseInt(key)+1)).val();
			opt.series[g] = $.extend(true,{},echartsOpts["bar"]).series[0];
			opt.series[g].name = $("#searchKeyword"+(parseInt(key)+1)).val();

			data.obj[key].reverse();

			for(var j = data.obj[key].length-10,i=0;j<data.obj[key].length;j++,i++){
				if(j<0){
					j=0;
				}
				if(g>0){
					opt.series[g].data[i] = getNum(opt.yAxis[0].data[i],data.obj[key]);

				}else{
					opt.series[g].data[i] = data.obj[key][j].num;

					opt.yAxis[0].data[i] = data.obj[key][j].name;
				}

				if(j>data.obj[key].length-4){
					topName = data.obj[key][j].name +"("+data.obj[key][j].num +")条、" +topName;
				}

				if(key==0){
					subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
				}
			}
			topName = topName.substring(0,topName.length-1);
			alertMsg += "与"+subMsg+"相关的信息主要来源"+ topName + ";";
			topName = "";
			g++;
		}
		if(g==0){
			$(".virtualSolutions").hide();
		}else{
			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
			}
			$("#virtualSolutions_msg").html(alertMsg);
			setEchartsOpion({$id:$(".virtualSolutions"),opt:opt});
		}
	});
	$scope.virtualSolutionChange = function(index){
		$(".virtualSolution").hide();
		$(".virtualSolution:eq("+index+")").show();
		$(".virtualtabs a").removeClass("f_c12");
		$(".virtualtabs a:eq("+index+")").addClass("f_c12");
		var opt = $.extend(true,{},echartsOpts["map"]),
			virtualSolution;
		if($scope.virtualSolutions)
			virtualSolution = $scope.virtualSolutions[index];
		if(virtualSolution && virtualSolution.length){
			var max = virtualSolution[0].num;
			for(var i = 0;i<virtualSolution.length;i++){
				opt.series[0].data[i] = {value:virtualSolution[i].num,name:virtualSolution[i].name};
				if(virtualSolution[i].num > max){
					max = virtualSolution[i].num;
				}
			}
			opt.visualMap.max = max;
			opt.series[0].label.normal={show:true,textStyle:{color:'#595959'}};
			opt.visualMap.inRange.color = ["#6F95D4","#57A1CA","#4CAFAF"];
			opt.series[0].itemStyle.emphasis={show:true,areaColor:"#bdd3f5"};

			setEchartsOpion({$id:$(".virtualSolution:eq("+index+")"),opt:opt});
		}else{
			$(".virtualSolution:eq("+index+")").html(prompt);
		}
	}

	// 相关词
	bigpipe.ready('doRelatedTerms',function(result){
		if(result.status){

			var opt = $.extend(true,{},echartsOpts["graph"]);
			opt.legend.left = "30";
			var colors = ['#39ae7d','#588FE5','#A4D720'];
			var i = 0,g = 0, alertMsg = "";
			for(var key in result.obj){

				if(result.obj[key][0]){
					opt.legend.data[g] = result.obj[key][0].name;
					opt.series[0].data[0] ={name:result.obj[key][0].name,itemStyle:{normal:{color:colors[g%3]}},symbolSize:30};
					opt.series[0].name = "相关词";
					var topName="",subMsg,code = result.obj[key][0].name;
					for(j = 0;j<result.obj[key][0].relatedWordList.length;j++){
						console.log("colors:"+g+":"+colors[g]);
						opt.series[0].data[i+1] = {name:result.obj[key][0].relatedWordList[j].name,itemStyle:{normal:{color:colors[g%3]}}};//设置每一个节点的数据
						opt.series[0].links[i] = {source:result.obj[key][0].name,target:result.obj[key][0].relatedWordList[j].name};
						if(j<3){
							topName += result.obj[key][0].relatedWordList[j].name+"("+ (result.obj[key][0].relatedWordList[j].percent*100).toFixed(2) + "%)、";
						}
						i++;
					}
					topName = topName.substring(0,topName.length-1);
					if(key==0){
						subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
						code = "<font class='f_c17'>"+code+"</font>";
					}
					if(key==1){
						subMsg = "<font class='f_c1'>"+$("#searchKeyword2").val()+"</font>";
						code = "<font class='f_c1'>"+code+"</font>";
					}
					if(key==2){
						subMsg =  "<font class='f_c18'>"+$("#searchKeyword3").val()+"</font>";
						code = "<font class='f_c18'>"+code+"</font>";
					}
					alertMsg += "通过对与"+subMsg+"相关的信息进行分析后可看出，与其核心词"+ code + "关联度最高的词语为" + topName + ";";
					g++;
				}
			}
			if(!alertMsg){
				$("#relatedTerms").html(prompt);
			}else{
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#relatedTerms_msg").html(alertMsg);
				setEchartsOpion({$id:$("#relatedTerms"),opt:opt});
			}
		}else{
			$("#relatedTerms").html(prompt);
		}
	});
	function getNum(name,list){
		for(var i = 0;i < list.length;i++){
			if(list[i].name == name){
				return list[i].num;
			}
		}
		return 0;
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
	});

//angularjs自启动
angular.bootstrap(document.documentElement,["ngApp"]);
