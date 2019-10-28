angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive,utils){
	var prompt = "<div align='center' style='padding-top:50px'><div style='display:inline;font-size: 14px;color:   #C0C0C0;'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><p>emm~您的信息量太少了</p><p>热度君暂时计算不出您的热度</p><p>换个词试试呢~</p></div></div>";


	// 声量图
	bigpipe.ready('doVolumeMap',function(result){
		var wname = $("#searchKeyword1").val();
		if(result.status){
			var obj = result.obj;
			if(obj){
				var opt = $.extend(true,{},echartsOpts["line2"]);
				var alertMsg = "从声量变化趋势来看,";
				opt.series[0] = $.extend(true,{},echartsOpts["line2"]).series[0];
				opt.series[0].name = wname;

				var topValue = obj[0][0].num,
					topTime = obj[0][0].key,subMsg;
				if(obj[0].length){
					for(var i = 0;i < obj[0].length;i++){
						/*opt.series[0].showAllSymbol = false;*/
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
		}else{
			$("#hotTrends").html(prompt);
		}
	});


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
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-320.png')");
				maskImage.src = njxBasePath+'/images/cloud-320.png';
			}
			if(window.screen.width>=375 && window.screen.width<412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'109%',height:"100%",top:'-2%'});
				$(".weiBoWordClouds").css("background-image","url('"+staticResourcePathH5+"/images/cloud-375.png')");
				maskImage.src = njxBasePath+'/images/cloud-375.png';
			}
			if(window.screen.width>=412){
				opt.series[0] = $.extend(true,opt.series[0],{ width:'100%',height:"102%",top:'-5%'});
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

	// 相关排行切换
	$scope.relatedTermsChange = function(index){
		var relatedTerms = $scope.hotRelatedLists[index];
		$(".relatedTerms a").removeClass("f_c17");
		$(".relatedTerms a:eq("+index+")").addClass("f_c17");
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
			var opt = $.extend(true,{},echartsOpts["funnel"]);
			var alertMsg = "";
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
			if(!alertMsg){
				$("#sourceStatistics").html(prompt);
			}else{
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#sourceStatistics_msg").html(alertMsg);
				setEchartsOpion({$id:$("#sourceStatistics"),opt:opt});
			}
		}else{
			$("#sourceStatistics").html(prompt);
		}
	});

	// 媒体友好度
	bigpipe.ready('doMediaFriend',function(result){
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["liquidFill"]);
			var position = parseInt(100-result.obj[0].percent/2);
			opt.series[0].data[0] = (result.obj[0].percent / 100).toFixed(2);
			opt.series[0].label.normal.position = ['50%', position+'%'];

			var alertMsg = "",topValue="",subMsg;
			for(var i = 0;i < result.obj[0].statList.length;i++){
				var $mediaFriend = $(".mediaSource ul li:eq("+i+")");
				$mediaFriend.find("p:eq(0)").text(result.obj[0].statList[i].name);
				$mediaFriend.find("p:eq(1)").text(result.obj[0].statList[i].percent.toFixed(2)+"%");
				if(i<3){
					topValue += "<font class='f_c22'>"+(result.obj[0].statList[i].name+"("+result.obj[0].statList[i].percent.toFixed(2)) +"%)</font>、";
				}
			}
			topValue = topValue.substring(0,topValue.length-1);
			subMsg = "<font class='f_c17'>"+$("#searchKeyword1").val()+"</font>";
			alertMsg += "与"+subMsg+"相关的信息主要来源于"+ topValue+";";
			if(!alertMsg){
				$("#mediaFriendlyChart").html(prompt);
			}else{
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
				$("#mediaFriendlyChart_msg").html(alertMsg);
				setEchartsOpion({$id:$("#mediaFriendlyChart"),opt:opt});
			}
		}else{
			$("#mediaFriendlyChart").html(prompt);
		}
	});

	// 地域分布
	bigpipe.ready('doVirtualSolution',function(result){
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["map"]);
			opt.series[0].name = $("#searchKeyword1").val();
			var max = result.obj[0][0].num;
			for(var i = 0;i<result.obj[0].length;i++){
				opt.series[0].data[i] = {value:result.obj[0][i].num,name:result.obj[0][i].name};
				if(result.obj[0][i].num > max){
					max = result.obj[0][i].num;
				}
			}
			opt.series[0].data.push({
				name: '南海诸岛',
				itemStyle: {
					normal: {
						borderColor: '#959595',
						areaColor: '#efefef',
					}
				}
			});
			opt.visualMap.max = max;
			opt.series[0].label.normal={show:false,textStyle:{color:'#595959'}};
			opt.visualMap.inRange.color = ['#fab85e','#fa7d43', '#e34743'];
			opt.series[0].itemStyle.emphasis={show:true,areaColor:"#bdd3f5"};
			setEchartsOpion({$id:$(".virtualSolution"),opt:opt});

			var opt = $.extend(true,{},echartsOpts["bar"]);
			var subMsg = "",topName = "",alertMsg = "从地域分布来看,";
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

			topName = topName.substring(0,topName.length-1);
			alertMsg += "与"+subMsg+"相关的信息主要来源"+ topName + ";";
			topName = "";
			if(alertMsg){
				alertMsg = alertMsg.substring(0,alertMsg.length-1);
				alertMsg +="。";
			}
			$("#virtualSolutions_msg").html(alertMsg);
			setEchartsOpion({$id:$(".virtualSolutions"),opt:opt});
		}else{
			$(".virtualSolution").html(prompt);
			$(".virtualSolutions").html(prompt);
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

	// 活跃媒体
	bigpipe.ready('doActiveMedia',function(datt){
		if(datt.status){
			var opt = $.extend(true,{},echartsOpts["activeMedia"]);
			if (null!=datt.obj[0].sb) {
				var data=datt.obj[0].sb;
				var yD=[];
				var zName=[];
				var zD=[];
				var col=['#F08C00','#F4A952','#D0411C','#B37D4B','#924F24','#6B7282','#45A67E','#3F5FA3'];
				for(var i=0;i<data.length;i++){
					if(i<8){
						var da=jQuery.parseJSON(data[i]);
						//	 				var dat = {value:da.count, name:da.name,itemStyle:{normal:{color:col[i],borderColor: '#fff',borderWidth: 2}},label:{normal:{textStyle:{fontSize:12}}}};
						var dat = {
							value: da.count,
							name: da.name,
							itemStyle: {
								normal: {
									label: {
										show: true,
										textStyle: {
											fontSize: 12
										},
										position: 'outside'
									},
									//	 			                    labelLine: {
									//	 			                        show: true,
									//	 			                        length: 100,
									//	 			                        smooth: 0.5
									//	 			                    },
									color:col[i],
									borderWidth: 1,
									shadowBlur: 1,
									borderColor: col[i],
									//	 			                    borderColor: "#000",
									shadowColor: 'rgba(0, 0, 0, 0)' //边框阴影
								}
							},
							label: {
								normal: {
									textStyle: {
										fontSize: 15
									}
								}
							}
						};
						zName.push(da.name);
						zD.push(dat);

						console.log(opt.legend.selected[0])
						opt.legend.data=zName;
						opt.series[0].data=zD;
					}
				}
				var fole = false;
				if(zName.length==1 && zName[0]=='新浪微博'){
					fole = true;
				}
				opt.legend.selected={'新浪微博':fole};
				setEchartsOpion({$id:$("#sourceTypeChart2"),opt:opt});
			}
		}
		else{
			$("#sourceTypeChart2").html(prompt);
		}
	});

	// 热点网民
	bigpipe.ready('doHotPeople',function(datt){
		if(datt.status){
			if(null!=datt.obj[0].jsons){
				var data=datt.obj[0].jsons;
				var html="";
//	 			var data=[{"name":"nih","count":"1"}];
				var h=0;
				for(var i=0;i<data.length;i++){
					if(i<8){
						var da=jQuery.parseJSON(data[i]);
						var nu=da.verifiedType;
						if(h==0 || h==4 ){
							html+="<ul class='clearfix'>";
						}
						html+="<li onclick=hotPeopleClick('"+da.originAuthorId+"')>";
						html+="<div class='wangmin' style='height: 54px;'>";
						html+="<span class='network-logo'>";
						if(isEmpty(da.headImg)){
							html+="<img src='"+njxBasePath+"/images/defaultHead.jpg'/>";
						}else{
							html+="<img src='"+da.headImg+"'/>";
						}

						html+="</span>";
						html+="<div class='network-txt'>";
						html+="<h3><a title='"+da.name+"' class='fz16' >"+da.name+"</a> </h3>";
						html+="<p class='fz14 fc_dark_grey nowrap'><span>提及："+da.count+"</span></p>";
//	 					html+="<p class='fc_dark_grey nowrap hotPopleSize'>粉丝数："+da.fansNumber+"</p>";
						html+="</div>";
						if(nu=="-1"){
							html+="<i class='weibosign weibosign2'>普通</i>";
						}else if(nu=="0"){
							html+="<i class='weibosign weibosign4'>橙V</i>";
						}else if(nu=="200" || nu=="220"){
							html+="<i class='weibosign weibosign3'>达人</i>";
						}else{
							html+="<i class='weibosign weibosign1'>蓝V</i>";
						}
						html+="</div>";
						html+="</li>";
						if(h==3 || h==7 ){
							html+="</ul>";
						}
						h++;
					}
				}
				$("#hotPeopleDivId").html(html);
			}
		}
		else{
			$("#hotPeopleDivId").html(prompt);
		}
	});

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
function hotPeopleClick(href){
	if(href!="" || href.length>0){
		window.open(href,"_blank");
	}
}
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