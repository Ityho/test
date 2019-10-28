angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive,utils){
	var prompt = "<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/shouye/warn.png' style='width:60px'><br/>此时间段暂无信息</p></div>";
	
	// 友好度分布
	bigpipe.ready('doFriendScatter',function(data){
		if (data.status) {
			var opt = $.extend(true,{},echartsOpts["friendscatter"]);
			if(data.obj[0].medias!=null){
			var medias=data.obj[0].medias.medias;
			var indicator1 = [];
			for(var i=0; i< medias.length; i++){
					indicator1.push({
						text: medias[i],
						max: 100
					});
			}
			var dd =
					{
						type: 'radar',
						symbol: 'none',
						tooltip: {
							backgroundColor: 'rgba(50,50,50,0.7)',
							formatter: function(a){
									var html = data.obj[0].medias.title + '<br/>';
									for(var i=0; i<medias.length; i++){
										html += medias[i] + ' : ' + a.value[i].toFixed(0) + '%<br/>';
									}
									return html;
								}
						},
						itemStyle: {
							normal: {
								lineStyle: {
									width: 1
								},
								areaStyle: {
									color: '#f9a34e'
								}
							}
						},
						areaStyle: {
							normal:{
								color: '#f9a34e',
								opacity:'0.5'
							}
						},
						data: [{
							value: data.obj[0].medias.data,
							name: data.obj[0].medias.title
						}]
					}
			
			opt.radar.indicator = indicator1;
			/*opt.series[0].data.value=data.obj[0].medias.data;
			opt.series[0].data.name=data.obj[0].medias.title;*/
			opt.series=dd;
			setEchartsOpion({$id:$("#mediaFriendlyChart2"),opt:opt});
			}
		}else{
			$("#mediaFriendlyChart2").html(prompt);
		}
	});
	
	// 性别敏感
	bigpipe.ready('doEmotionSex',function(data){
		if (data.status) {
			var opt = $.extend(true,{},echartsOpts["emotionSex"]);
		
				
			opt.series[0].data[0].name=data.obj[0].zm[0].toFixed(2) + '%';
			opt.series[0].data[1].name=data.obj[0].fm[0].toFixed(2) + '%';
			
			opt.series[2].data[0].value=data.obj[0].zm[0];
			opt.series[2].data[1].value=data.obj[0].fm[0];
			
			opt.series[3].data[0].name=data.obj[0].zm[1].toFixed(2) + '%';
			opt.series[3].data[1].name=data.obj[0].fm[1].toFixed(2) + '%';
			
			opt.series[5].data[0].value='-' + data.obj[0].zm[1].toFixed(2);
			opt.series[5].data[1].value='-' + data.obj[0].fm[1].toFixed(2);
			
			setEchartsOpion({$id:$("#emotionBar1"),opt:opt});
			
		}else{
			$("#emotionBar1").html(prompt);
		}
	});
	
	// 用户类型
	bigpipe.ready('doEmotionType',function(data){
		if (data.status) {
			var opt = $.extend(true,{},echartsOpts["emotionType"]);
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
	
	// 粉丝类型
	bigpipe.ready('doEmotionFans',function(data){
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
	
	// 转发层级
	bigpipe.ready('doEmotionLevel',function(data){
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
	
	
	// 兴趣标签
	bigpipe.ready('doEmotionHobby',function(data){
		if (data.status) {
			var zm = data.obj[0].zm;
			var fm = data.obj[0].fm;
			var html = '';
			if(zm == null || zm.length == 0){
				$("#interestWordCloud-zm").html(prompt);
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
				$("#interestWordCloud-fm").html(prompt);
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
	
	// 正面热词
	bigpipe.ready('doZMHotWord',function(data){
		if (data.status) {
			var zmHotWords = data.obj[0].zmWords;
			var html = "";
			for (n in zmHotWords){
				var name = zmHotWords[n].name;
				if(name.length > 5){
					name = name.substring(0, 4) + "...";
				}
				html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + zmHotWords[n].name + "：" + zmHotWords[n].count + "\">" + name +  "</a></li>";
			}
			$("#zmHotWord-ul").html(html);
		}else{
			$("#zmHotWord-ul").html(prompt);
		}
	});
	
	// 负面热词
	bigpipe.ready('doFMHotWord',function(data){
		if (data.status) {
			var fmHotWords = data.obj[0].fmWords;
			var html = "";
			for (n in fmHotWords){
				var name = fmHotWords[n].name;
				if(name.length > 5){
					name = name.substring(0, 4) + "...";
				}
				html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + fmHotWords[n].name + "：" + fmHotWords[n].count + "\">" + name +  "</a></li>";
			}
			$("#fmHotWord-ul").html(html);
		}else{
			$("#fmHotWord-ul").html(prompt);
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
	
	
	// 情绪占比
	bigpipe.ready('doEmotionProportion',function(data){
		if (data.status) {
			var opt = $.extend(true,{},echartsOpts["emotionProportion"]);
			var opt2 = $.extend(true,{},echartsOpts["emotionProportion2"]);
			var colors = data.obj[0].color;
			var data = data.obj[0].data;
			var datas = new Array();
			var legend = new Array();
			
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
			opt.series[0].data=datas;
			
			opt2.yAxis[0].data=yAxis;
			opt2.series[0].data=datas2;
			setEchartsOpion({$id:$("#emotionpieEchart"),opt:opt});
			setEchartsOpion({$id:$("#emotionpieEchart2"),opt:opt2});
		}else{
			$("#emotionpieEchart").html(prompt);
			$("#emotionpieEchart2").html(prompt);
			
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