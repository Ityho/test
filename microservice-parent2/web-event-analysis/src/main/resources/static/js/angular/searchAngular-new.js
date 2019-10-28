angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive,utils){
	var prompt = "<div align='center' style='padding-top:50px'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p><p>热度君暂时计算不出您的热度</p><p>换个词试试呢~</p></div></div>";

	// 热度指数
	bigpipe.ready('doHeatValue',function(result){
		console.log("doHeatValue");
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["gauge"]);
			opt.series[0].data[0].value = result.obj[0].ratioHotCustom.toFixed(2);
			opt.series[1].data[0].value = result.obj[0].ratioHotCustom.toFixed(2);
			setEchartsOpion({$id:$("#circle1"),opt:opt});

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
			$("#circle1").html(prompt);
		}
	});

	// 热度走势
	bigpipe.ready('doEventTrends',function(result){
		console.log("doEventTrends");
		if(!(window.top==window.self)){
//			parent.handleSealedData(result,"doEventTrends");
		}else{
//			handleSealedData(data,urlaction);
		}
		if(result.status){
			$scope.eventTrendsObj = result.obj;
		}
		$scope.doEventTrends($scope.eventTrendsObj);
	});

	// 热度走势
	$scope.doEventTrends = function(obj){
		if(obj){
			var opt = $.extend(true,{},echartsOpts["hotline"]);
			var alertMsg = "从热度指数变化趋势来看,";
			opt.series[0] = $.extend(true,{},echartsOpts["hotline"]).series[0];
			opt.series[0].name = obj[0].name;

			if(obj[0].statList.length){
				//峰值
				var topValue = obj[0].statList[0].total.toFixed(2),topTime,subMsg;
				var topTime = obj[0].statList[0].name;
				var avgValue= 0;
				var lastOneHour=0;//一小时热度变化趋势
				var startTime=0;
				var endTime=0;
				for(var i = 0;i < obj[0].statList.length;i++){
					/*opt.series[0].showAllSymbol = false;*/
					opt.series[0].data[i] = obj[0].statList[i].total.toFixed(2);
					if(i==0){
						startTime=obj[0].statList[i].name+":00"
					}
					if(i==(obj[0].statList.length-1)){
						endTime=obj[0].statList[i].name+":00"
					}
					opt.xAxis[0].data[i] = obj[0].statList[i].name+":00";
					if(parseFloat(obj[0].statList[i].total) > parseFloat(topValue)){
						topValue = obj[0].statList[i].total.toFixed(2);
						topTime = obj[0].statList[i].name;
					}
					//均值计算
					avgValue=(avgValue+parseFloat(obj[0].statList[i].total.toFixed(2)));
					if(i==(obj[0].statList.length-1)){
						lastOneHour=(obj[0].statList[i].total-obj[0].statList[i-1].total);
					}
				}
				avgValue=avgValue/obj[0].statList.length;
				if(lastOneHour<0){
					$("#hot_avg_qs").html('<i class="iconfont icon-xiajiang fz22 f_c10 fw600"></i>'+Math.abs(lastOneHour.toFixed(2)));
				}else{
					$("#hot_avg_qs").html('<i class="iconfont icon-shangsheng fz22 f_c2"></i>'+Math.abs(lastOneHour.toFixed(2)));
				}

				$("#hot_avg").html(avgValue.toFixed(2));
				$("#top_hot_avg").html(topValue);
				subMsg = $("#searchKeyword1").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
				$("#hotTrends_msg").html(alertMsg)
				setEchartsOpion({$id:$("#hotTrends"),opt:opt});
			}else{
				$("#hotTrends").html(prompt);
			}
		}else{
			$("#hotTrends").html(prompt);
		}
	};

	bigpipe.ready('ratioCustom',function(result){
		// var hot_time=document.getElementById("hot_time");
		// var text=hot_time.innerText;
	});

	bigpipe.ready('date',function(result){
		if (result.obj == 24) {
			$("#hot_time").html("24小时");
		} else if (result.obj == 72) {
			$("#hot_time").html("72小时");
		}
		$("#hotsearchdate").val(result.obj);
	});

	// 热门信息
	bigpipe.ready('doHotMessage',function(datt){
		if(datt==''){
			$("#hotMessage").hide();
		}else{
			$("#hotMessage").show();
			var data;
			var data2;
			try {
				data = datt.obj[0].jsons;
				data2 = datt.obj[0].jsons2;
			}
			catch(err){
				$("#hotMessage").hide()
			}
			var html="";
			var dd=[];
			if(null!=data && null!=data2){
				$("#hotMessage").show();
				console.log("null!=data && null!=data2")
				var num=0;
				if(data2.length>=3){
					num=3;
				}else{
					num=data2.length;
				}
				if(num!=0){
					for(var i=0;i<num;i++){
						var da=jQuery.parseJSON(data2[i]);
						var title=da.title;
						var sourceWebsite=da.sourceWebsite;
						var publishTime=da.publishTime;
						var similarCount=da.similarCount;
						var url=da.url;
						var v1={ title:title , sourceWebsite:sourceWebsite , publishTime:publishTime ,similarCount :similarCount , url: url};
						dd.push(v1);
					}
				}
				for(var i=0;i<(5-num);i++){
					var da=jQuery.parseJSON(data[i]);
					var title=da.title;
					var sourceWebsite=da.sourceWebsite;
					var publishTime=da.publishTime;
					var similarCount=da.similarCount;
					var url=da.url;
					var v1={ title:title , sourceWebsite:sourceWebsite , publishTime:publishTime ,similarCount :similarCount , url: url};
					dd.push(v1);
				}
				var compare = function (obj1, obj2) {
					var val1 = parseFloat(obj1.similarCount);
					var val2 = parseFloat(obj2.similarCount);
					if (val1 > val2) {
						return -1;
					} else if (val1 < val2) {
						return 1;
					} else {
						return 0;
					}
				}
				dd.sort(compare);
				html+="<thead>";
				html+="<tr>";
				html+="<th width='45%'>热门信息</th>";
				html+="<th width='15%'>信息来源</th>";
				html+="<th width='15%'>发布时间</th>";
				html+="<th width='20%'>相似信息量</th>";
				html+="</tr>";
				html+="</thead>";
				html+="<tbody>";

				for(var i=0;i<dd.length;i++){
					html+="<tr>";
					html+="<td style='text-align: left; margin-bottom: -1px;line-height: 30px'>";
					html+="<a style='margin-left:10px;display: table;' onclick=hotMessageClick('"+dd[i].url+"')>"
					var searchKeyword=$('#searchKeyword').val();
					var top_td=delHtmlTag(dd[i].title.substring(0, 140))
					var new_td=top_td.replace(eval("/"+searchKeyword+"/ig"),"<span style='color: red;'>$&</span>");
					html+=new_td;
					html+="</a>";
					html+="</td>";
					html+="<td>";
					html+=dd[i].sourceWebsite;
					html+="</td>";
					html+="<td>";
					html+=dd[i].publishTime;
					html+="</td>";
					html+="<td>";
					html+=commafy(dd[i].similarCount);
					html+="</td>";
					html+="</tr>";
				}
				html+="";
				$("#hotMessageDivId").html(html);
				/*			$(".table-striped tr .table-txt").on("mouseenter",function() {
            //	 				$(this).removeClass("nowrap");
            //	 				$(this).addClass("clamp");
                                $(this).removeClass("clamp");
                            });
                            $(".table-striped tr .table-txt").on("mouseleave",function() {
            //	 				$(this).addClass("nowrap");
                                $(this).addClass("clamp");
            //	 				$(this).removeClass("clamp");
                            })*/
			}else{
				console.log("000000000")
				$("#hotMessageDivId").html(prompt);
				$("#hotMessage").hide();
			}
		}

	});
	// 相关词
	bigpipe.ready('doRelatedTerms',function(result){
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["scatter"]);
			var i = 0, alertMsg = "";
			if(result.obj[0][0]){
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
	// 信息来源
	bigpipe.ready('doInfoFrom',function(data){
		if(data.status){
			var opt = $.extend(true,{},echartsOpts["infoMedia"]);
			if (null!=data.obj[0].sb) {
				var dataTable=data.obj[0].sb;
				if(dataTable) {
					var kk = jQuery.parseJSON(dataTable);
					if (kk != null || kk != "") {
						var data1 =[];
						var sumNum = parseInt(kk.weibo)+parseInt(kk.kehuduan)+parseInt(kk.weixin)+parseInt(kk.luntan)+parseInt(kk.wangzhan);
						var v0={value:sumNum , name:"全部"};
						var v1={value:kk.weibo , name:"微博"};
						var v2={value:kk.kehuduan , name:"客户端"};
						var v3={value:kk.weixin , name:"微信"};
						var v4={value:kk.luntan , name:"论坛"};
						var v5={value:kk.wangzhan , name:"网站"};
						data1.push(sumNum);
						data1.push(parseInt(kk.weibo));
						data1.push(parseInt(kk.kehuduan));
						data1.push(parseInt(kk.weixin));
						data1.push(parseInt(kk.luntan));
						data1.push(parseInt(kk.wangzhan));
						opt.series[1].data=data1;
						var lend = ["全部","微博","客户端","微信","论坛","网站"];
						var dd=[];
						for(var i=0;i<data.length;i++){
							if(i==0 || i==data.length-1){
								dd.push(0);
							}else{
								var snum=0;
								for(var j=i+1;j<data.length;j++){
									snum+=data[j];
								}
								dd.push(snum);
								opt.series[0].data=dd;
							}
						}
						setEchartsOpion({$id:$("#mediaActivity"),opt:opt});
					}
				}
			}
		}else{
			$("#mediaActivity").html(prompt);
		}
	});

	// 热度走势
	bigpipe.ready('doEventTrends2',function(result){
		console.log("doEventTrends2");
		if(result.status){
			$scope.eventTrendsObj = result.obj;
		}
		$scope.doEventTrends2($scope.eventTrendsObj);
	});

	// 热度走势
	$scope.doEventTrends2 = function(obj){
		if(obj){
			var opt = $.extend(true,{},echartsOpts["line2"]);
			var alertMsg = "从热度指数变化趋势来看,";
			opt.series[0] = $.extend(true,{},echartsOpts["line2"]).series[0];
			opt.series[0].name = obj[0].name;

			if(obj[0].statStatListList.length){
				var topValue = obj[0].statStatListList[0].total.toFixed(2),topTime,subMsg;
				var topTime = obj[0].statStatListList[0].name;
				for(var i = 0;i < obj[0].statStatListList.length;i++){
					/*opt.series[0].showAllSymbol = false;*/
					opt.series[0].data[i] = obj[0].statStatListList[i].total.toFixed(2);
					opt.xAxis[0].data[i] = obj[0].statStatListList[i].name+":00";
					if(parseFloat(obj[0].statStatListList[i].total) > parseFloat(topValue)){
						topValue = obj[0].statStatListList[i].total.toFixed(2);
						topTime = obj[0].statStatListList[i].name;
					}
				}
				subMsg = $("#searchKeyword1").val();
				alertMsg += '<font class="f_c17">';
				alertMsg += subMsg + '</font>在' + topTime + '时达到了<font class="f_c22">' + topValue + '</font>的峰值。';
				$("#hotTrends_msg").html(alertMsg)
				setEchartsOpion({$id:$("#hotTrends"),opt:opt});
			}else{
				$("#hotTrends").html(prompt);
			}
		} else {
			$("#hotTrends").html(prompt);
		}
	};

	// 热度聚类
	bigpipe.ready('doHotCluster',function(result){
		console.log("doHotCluster");
		if(result.status){
			$scope.$apply(function(){
				$scope.hotclusters= result.obj;
			});
		}
	});
	/**
	 * 去掉所有的html标记
	 */
	function delHtmlTag(str){
		return str.replace(/<[^>]+>/g,"");
	}
	// 声量图
	bigpipe.ready('doVolumeMap',function(result){
		if(result.status){
			$scope.volumeMapObj = result.obj;
		}
	});

	// 声量图
	$scope.doVolumeMap = function(obj){
		console.log("doVolumeMap");

		if(obj){
			var opt = $.extend(true,{},echartsOpts["line"]);
			var alertMsg = "从声量变化趋势来看,";
			opt.series[0] = $.extend(true,{},echartsOpts["line"]).series[0];
			opt.series[0].name = obj[0].name;

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
			$("#hotTrends_msg").html(alertMsg)
			setEchartsOpion({$id:$("#hotTrends"),opt:opt});
		}else{
			$("#hotTrends").html(prompt);
		}
	};

	// 模块二：印象词云
	bigpipe.ready('doWordcloud',function(result){
		console.log("doWordcloud");
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["wordCloud"]),maskImage = new Image(),wordCloud;
			var alertMsg = "",topValue="",topName="",subMsg;

			var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色
			opt.maskImage = maskImage;
			maskImage.onload = function(){
				opt.series[0].maskImage;
				setEchartsOpion({$id:$("#weiBoWordCloud"),opt:opt});
			}
			if (!result.obj[0]){
				$("#weiBoWordCloud").html(prompt);
				return;
			}
			for(var i = 0;i<result.obj[0].length;i++){
				opt.series[0].data[i] = {name:result.obj[0][i].name,value:result.obj[0][i].num*100, textStyle:{normal:{color:colors[i%3]}}};
				if(i<3){
					topName += "<font class='f_c22'>"+result.obj[0][i].name + "</font>、";
				}
			}

			if(window.screen.width>=320 && window.screen.width<375){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'120%',height:"100%",top:'-6%'});
				opt.series[0].sizeRange = [12,20];
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
				maskImage.src = njxBasePath+'/images/cloud-320.png';
			}
			if(window.screen.width>=375 && window.screen.width<412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'109%',height:"100%",top:'-2%'});
				opt.series[0].sizeRange = [12,32];
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
				maskImage.src = njxBasePath+'/images/cloud-375.png';
			}
			if(window.screen.width>=412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'100%',height:"102%",top:'-5%'});
				opt.series[0].sizeRange = [12,40];
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
				maskImage.src = njxBasePath+'/images/cloud.png';
			}

			subMsg = "<font class='f_c17'>"+ $("#searchKeyword1").val()+"</font>";
			topName = topName.substring(0,topName.length-1);
			alertMsg += "在与"+subMsg+"相关的全部信息中,被提及频次最高的词语分别为" + topName+";";

			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
			}
			$("#weiBoWordCloud_msg").html(alertMsg);
		}else{
			$("#weiBoWordCloud").html(prompt);
		}
	});

	$scope.doWordcloud = function(){
		var data = {date:$("#hotsearchdate").val(),searchKeyword1:$("#searchKeyword").val(),keyword1:$("#keyword1").val(),filterKeyword1:$("#filterKeyword1").val(),shareCode:$("#shareCode").val()}
		var urlaction=actionBase+"/view/hotSearch/doWordcloudV4.action";
		$http.post(actionBase+"/view/hotSearch/doWordcloudV4.action",data).success(function(result){
			if(!(window.top==window.self)){
//				parent.handleSealedData(result,urlaction);
			}else{
//				handleSealedData(data,urlaction);
			}
			if(result.status){
				var opt = $.extend(true,{},echartsOpts["wordCloud"]),maskImage = new Image(),wordCloud;
				var alertMsg = "",topValue="",topName="",subMsg;

				var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色
				opt.maskImage = maskImage;
				maskImage.onload = function(){
					opt.series[0].maskImage;
					setEchartsOpion({$id:$("#weiBoWordCloud"),opt:opt});
				}
				if (!result.obj[0]){
					$("#weiBoWordCloud").html(prompt);
					return;
				}
				for(var i = 0;i<result.obj[0].length;i++){
					opt.series[0].data[i] = {name:result.obj[0][i].name,value:result.obj[0][i].num*100, textStyle:{normal:{color:colors[i%3]}}};
					if(i<3){
						topName += "<font class='f_c22'>"+result.obj[0][i].name + "</font>、";
					}
				}

				if(window.screen.width>=320 && window.screen.width<375){
					opt.series[0] = $.extend(true,opt.series[0],{ width:'120%',height:"100%",top:'-6%'});
					opt.series[0].sizeRange = [12,20];
					$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
					maskImage.src = njxBasePath+'/images/cloud-320.png';
				}
				if(window.screen.width>=375 && window.screen.width<412){
					opt.series[0] = $.extend(true,opt.series[0],{ width:'109%',height:"100%",top:'-2%'});
					opt.series[0].sizeRange = [12,32];
					$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
					maskImage.src = njxBasePath+'/images/cloud-375.png';
				}
				if(window.screen.width>=412){
					opt.series[0] = $.extend(true,opt.series[0],{ width:'100%',height:"102%",top:'-5%'});
					opt.series[0].sizeRange = [12,40];
					$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud.png')");
					maskImage.src = njxBasePath+'/images/cloud.png';
				}

				subMsg = "<font class='f_c17'>"+ $("#searchKeyword1").val()+"</font>";
				topName = topName.substring(0,topName.length-1);
				alertMsg += "在与"+subMsg+"相关的全部信息中,被提及频次最高的词语分别为" + topName+";";

				if(alertMsg){
					alertMsg = alertMsg.substring(0,alertMsg.length-1);
					alertMsg +="。";
				}
				$("#weiBoWordCloud_msg").html(alertMsg);
			}else{
				$("#weiBoWordCloud").html(prompt);
			}
		});
	}

	$scope.goSearch = function(e,time){
		console.log("=====goSearch6565");
		if(time==24){
			var hot_time=$("#hot_time")
			hot_time.html("24小时");
		}else if(time==72){
			var hot_time=$("#hot_time")
			hot_time.html("72小时");
		}
		$(e.target).addClass("active");
		$(e.target).siblings().removeClass("active");
		$("#hotsearchdate").val(time);
		$("#searchKeyword1").val($("#searchKeyword1")[0].defaultValue);
		goSearchLoad();
	}
})
//配置ajax请求参数
	.config(function($httpProvider) {
		$httpProvider.defaults.transformRequest = function(data) {
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
			var deferred = $q.defer();
			var promise = deferred.promise;
			$http({
				url : param.url,
				method : param.method,
				data : param.data
			}).success(function(data, status, headers, config) {
				deferred.resolve(data);// 执行成功
			}).error(function(data, status, headers, config) {
				deferred.reject();// 执行失败
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
angular.bootstrap(document.documentElement,["ngApp"]);