var warnHtml = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml1 = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 60px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml2 = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 30px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';

function friendlyChartCallback(data, demoIndex){
	
	if(demoIndex == ""){
		if(data == null){
			$("#totalFriendlyChart").html(warnHtml1);
			$("#totalFriendlyChart").parent().removeClass('mediaCircle');
			$("#mediaFriendlyChart").html(warnHtml1);
		}else{
			if(data.total){
				$("#totalFriendlyChart").parent().addClass('mediaCircle');
				totalFriendlyChartCallback(data.total, "totalFriendlyChart", "");
			}else{
				$("#totalFriendlyChart").html(warnHtml1);
				$("#totalFriendlyChart").parent().removeClass('mediaCircle');
			}
			if(data.medias){
				mediaFriendlyChartCallback(data.medias, "mediaFriendlyChart");
			}else{
				$("#mediaFriendlyChart").html(warnHtml1);
			}
		}
	}else{
		if(data == null){
			$("#totalFriendlyChart" + demoIndex).html(warnHtml1);
			$("#totalFriendlyChart" + demoIndex).parent().removeClass('mediaCircle');
		}else{
			if(data.total){
				$("#totalFriendlyChart" + demoIndex).parent().addClass('mediaCircle');
				totalFriendlyChartCallback(data.total, "totalFriendlyChart" + demoIndex, demoIndex);
			}else{
				$("#totalFriendlyChart" + demoIndex).html(warnHtml1);
				$("#totalFriendlyChart" + demoIndex).parent().removeClass('mediaCircle');
			}
			if(data.medias){
				mediaFriendlyChartCallback2(data.medias, "mediaFriendlyChart" + demoIndex);
			}
		}
	}
}

//总体友好度
function totalFriendlyChartCallback(data, demo, demoIndex){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	$("#"+demo).removeAttr("_echarts_instance_");
	var color = '#f9a34e';
	if(demoIndex == "2")
		color = '#0f6db7';
	
	// 指定图表的配置项和数据
	var chartDemo = echarts.init(document.getElementById(demo));
	var option = {

		series: [{
			color: ['#0e7dc0'],
			type: 'liquidFill',
			name: '总体友好度',
			data: [data],
			radius: '72%',
			itemStyle: {
				normal: {
					shadowBlur: 0,
					color: color
				}
			},
			outline: {
				show: false
			},
			backgroundStyle: {
				borderColor: color,
				borderWidth: 4,
				color: '#fff'
			},
			label: {
				normal: {
					position: ['50%', '70%'],
					formatter: function(v) {
						return (v.data * 100).toFixed(2) + "%" + '\n总体友好度';
					},
					textStyle: {
						fontSize: 18,
						color: color
					}
				}
			}
		}]
	};
	chartDemo.setOption(option);
	//重新定义所有图表自适应容器大小
	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}

function mediaFriendlyChartCallback(data, demo){
	
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	$("#"+demo).removeAttr("_echarts_instance_");
	var indicator = new Array();
	var medias = data.medias;
	if(data != null){
		for(var i=0; i<medias.length; i++){
			indicator.push({
				text: medias[i],
				max: 100
			});
		}
	}
	var chartDemo = echarts.init(document.getElementById(demo));
	var option = {
		tooltip: {
			trigger: 'item',
			backgroundColor: 'rgba(0,0,250,0.2)'
		},
		visualMap: {
			show: false,
			color: ['#F9A14C', '#D45D3F']
		},
		radar: {
			name: {
				textStyle: {
					color: '#6f7786',
					fontSize: 14
				}
			},
			indicator: indicator
		},
		series: [{
				type: 'radar',
				symbol: 'none',
				tooltip: {
					backgroundColor: 'rgba(50,50,50,0.7)',
					formatter: function(a){
							var html = data.title + '<br/>';
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
				data: [{
					value: data.data,
					name: data.title
				}]
			}]
	};
	chartDemo.setOption(option);
	//重新定义所有图表自适应容器大小
	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}

function mediaFriendlyChartCallback2(data, demo){
	if(data && data.medias){
		var html = "";
		for(i in data.medias){
			html += '<li><p>' + data.medias[i] + '</p><p><span>' + data.data[i].toFixed(2) + '%</span></p></li>';
		}
		$("#" + demo).html(html);
	}
}

function zmHotWordChartCallback(data, index){
	hotWordChartCallback(data, index, 2)
}

function fmHotWordChartCallback(data, index){
	hotWordChartCallback(data, index, 3)
}

function hotWordChartCallback(data, index, option){
	
	if(data == null){
		if(option == 2)
			$("#zmHotWord-ul" + index).html(warnHtml1);
		if(option == 3)
			$("#fmHotWord-ul" + index).html(warnHtml1);
		return;
	}
	
	if(option == 2){
		var zmHotWords = data.zmWords;
		var html = "";
		if(zmHotWords.length > 0){
			for (n in zmHotWords){
				var name = zmHotWords[n].name;
				if(name.length > 5){
					name = name.substring(0, 4) + "...";
				}
				html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + zmHotWords[n].name + "：" + zmHotWords[n].value + "\">" + name +  "</a></li>";
			}
		}else{
			html = warnHtml1;
		}
		$("#zmHotWord-ul" + index).html(html);
	}
	
	if(option == 3){
		var fmHotWords = data.fmWords;
		var html = "";
		if(fmHotWords.length > 0){
			for (n in fmHotWords){
				var name = fmHotWords[n].name;
				if(name.length > 5){
					name = name.substring(0, 4) + "...";
				}
				html += "<li><a href=\"javascript:;\" data-toggle=\"tooltip\" data-placement=\"right\" title=\"\" data-original-title=\"" + fmHotWords[n].name + "：" + fmHotWords[n].value + "\">" + name +  "</a></li>";
			}
		}else{
			html = warnHtml1;
		}
		$("#fmHotWord-ul" + index).html(html);
	}
	$("[data-toggle='tooltip']").tooltip();//全局提示效果
}

function hotViewpointChartCallback2(data, index){
	
	if(index == "1")
		hotViewpointChartCallbackData1 = data;
	else if(index == "2")
		hotViewpointChartCallbackData2 = data;
	if(hotViewpointChartDataBackNum == 0){
		hotViewpointChartDataBackNum++;
	}else{
		var data1 = {
			zmViewpoint : null,
			fmViewpoint : null
		}
		var data2 = {
			zmViewpoint : null,
			fmViewpoint : null
		}
		if(hotViewpointChartCallbackData1 != null){
			if(hotViewpointChartCallbackData1.zmViewpoint)
				data1.zmViewpoint = hotViewpointChartCallbackData1.zmViewpoint;
			if(hotViewpointChartCallbackData1.fmViewpoint)
				data2.zmViewpoint = hotViewpointChartCallbackData1.fmViewpoint;
		}
		if(hotViewpointChartCallbackData2 != null){
			if(hotViewpointChartCallbackData2.zmViewpoint)
				data1.fmViewpoint = hotViewpointChartCallbackData2.zmViewpoint;
			if(hotViewpointChartCallbackData2.fmViewpoint)
				data2.fmViewpoint = hotViewpointChartCallbackData2.fmViewpoint;
		}
		hotViewpointChartCallback(data1, "1");
		hotViewpointChartCallback(data2, "2");
		hotViewpointChartDataBackNum = 0;
		hotViewpointChartCallbackData1 = null;
		hotViewpointChartCallbackData2 = null;
	}
}

function hotViewpointChartCallback(data, index){
	
	if(data == null){
		$("#hotViewpointChart" + index).html(warnHtml1);
		return;
	}
	$("#hotViewpointChart" + index).removeAttr("_echarts_instance_");
	var color1 = {
		normal : "#77B9DF",
		emphasis: "#94C7E4"
	};
	var color2 = {
		normal : "#E09987",
		emphasis: "#F9B8A8"
	};
	var legend = ['非敏感典型口碑', '敏感典型口碑'];
	if(index == "1"){
		color2 = color1;
		legend = ['非敏感典型口碑'];
	}else if(index == "2"){
		color1 = color2;
		legend = ['敏感典型口碑'];
	}
		
	var zmViewpoint = new Array();
	var fmViewpoint = new Array();
	var zmMarkPoint = new Array();
	var fmMarkPoint = new Array();
	if(data.zmViewpoint){
		for(n in data.zmViewpoint){
			var value = data.zmViewpoint[n].percent.toFixed(2);
			var context = data.zmViewpoint[n].name;
			var url = data.zmViewpoint[n].url;
			zmViewpoint.push({
				name: context,
				value: value,
				webUrl : url
			});
			if(value != 0){
				zmMarkPoint.push({
					name: value + '%',
					value: '100',
					viewContext : context,
					webUrl : url
				});
			}
		}
	}
	if(data.fmViewpoint){
		for(n in data.fmViewpoint){
			var value = data.fmViewpoint[n].percent.toFixed(2);
			var context = data.fmViewpoint[n].name;
			var url = data.fmViewpoint[n].url;
			fmViewpoint.push({
				name: context,
				value: value,
				webUrl : url
			});
			if(value != 0){
				fmMarkPoint.push({
					name: value + '%',
					value: '100',
					viewContext : context,
					webUrl : url
				});
			}
		}
	}
	
	var chartDemo = echarts.init(document.getElementById("hotViewpointChart" + index));
	var option = {
		tooltip:{
			show:true,
			trigger:'item',
			position:'top'
		},
		legend: {
			type: 'plain',
			data: legend,
			orient:'horizontal',
			textStyle: {
				color: '#000000',
				align: 'left',
				fontSize:16
			},
			itemGap: 550,
			itemWidth: 5,
			itemHeight: 15
		},
		grid: [{
			show: false,
			left: '8%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '40%'
		}, {
			show: false,
			left: '50.5%',
			top: '10%',
			bottom: 40,
			width: '0%'
		}, {
			show: false,
			right: '8%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '40%'
		}, ],
		xAxis: [{
			gridIndex: 0,
			type: 'value',
			position: 'bottom',
			inverse: true,
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				show: false,
			},
			splitLine: {
				show: false,
			}
		}, {
			gridIndex: 1,
			type: 'value',
			show: false,
		}, {
			gridIndex: 2,
			type: 'value',
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false,
			},

			axisLabel: {
				show: false,
			},
			splitLine: {
				show: false,
			},
		}, ],
		yAxis: [{
			type: 'category',
			inverse: true,
			position: 'right',
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: false,
			},
			data: [],

		}, {
			gridIndex: 1,
			type: 'category',
			inverse: true,
			position: 'left',
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: true,
				textStyle: {
					align: 'center',
					color: '#888888',
					fontSize: 13,
					padding: 5,
				}

			},
			data: ['TOP1', 'TOP2', 'TOP3', 'TOP4', 'TOP5', 'TOP6', 'TOP7', 'TOP8']

		}, {
			gridIndex: 2,
			type: 'category',
			inverse: true,
			position: 'left',
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: false,
			},
			data: [],
		}, ],
		series: [{
				name: '非敏感典型口碑',
				type: 'bar',
				xAxisIndex: 0,
				yAxisIndex: 0,
				z: 10,
				barCategoryGap: '70%',
				label: {
					normal: {
						show: true,
						position: 'insideTopRight',
						offset:[-24, 5],
						distance: -24,
						textStyle: {
							color: '#595959',
						}

					}
				},
				tooltip:{
					formatter: function(data) {
						var context = data.name;
						return '<p style="max-width:400px; white-space:normal">' + context + '</p>';
					},
				},
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'inner',
							formatter: function(data) {
								if(data.name.length > 35)
									data.name = data.name.substring(0, 35) + '...';
								return data.name
							},
						},
						color: color1.normal,
					}
				},
				data: zmViewpoint
			},
			{
				name: '非敏感典型口碑',
				type: 'bar',
				xAxisIndex: 0,
				yAxisIndex: 0,
				silent: false,
				barGap: '-100%',
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				tooltip:{
					formatter: function(data) {
						var context = data.data.viewContext;
						return '<p style="max-width:400px; white-space:normal">' + context + '</p>';
					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#EEEEEE',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},
					},
					emphasis: {
						color: '#EEEEEE',
					}
				},
				data: zmMarkPoint
			},
			{
				name: '敏感典型口碑',
				type: 'bar',
				z: 10,
				barCategoryGap: '70%',
				xAxisIndex: 2,
				yAxisIndex: 2,
				label: {
					normal: {
						show: true,
						position: 'insideTopLeft',
						offset:[24, 5],
						distance: -24,
						textStyle: {
							color: '#595959',
						}

					},
				},
				tooltip:{
					formatter: function(data) {
						var context = data.name;
						return '<p style="max-width:400px; white-space:normal">' + context + '</p>';
					},
				},
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'inner',
							formatter: function(data) {
								if(data.name.length > 35)
									data.name = data.name.substring(0, 35) + '...';
								return data.name
							},
						},
						color: color2.normal,
					}
				},
				data: fmViewpoint
			}, {
				name: '敏感典型口碑',
				type: 'bar',
				xAxisIndex: 2,
				yAxisIndex: 2,
				silent: false,
				barGap: '-100%',
				cursor: "pointer",
				label: {
					normal: {
						show: true,
						position: 'right',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				tooltip:{
					formatter: function(data) {
						var context = data.data.viewContext;
						return '<p style="max-width:400px; white-space:normal">' + context + '</p>';
					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#EEEEEE',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},

					},
					emphasis: {
						color: '#EEEEEE',
					},
				},
				data: fmMarkPoint
			}
		]
	}
	chartDemo.setOption(option);
	chartDemo.on('click', function(param){
		if(param.componentType == 'series'){
			var url = param.data.webUrl;
			if(url != null && url != '')
				window.open(url, "_blank");
			else
				return false;
		}
	});
	//重新定义所有图表自适应容器大小
	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}

/*用户情绪观洞察start*/
function emotionSexAnalysisChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		$("#"+demo).next('.echartSex-line').hide();
		return;
	}else{
		$("#"+demo).next('.echartSex-line').show();
	}
	var emotionBar1 = echarts.init(document.getElementById(demo));
	
	var option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		/*toolbox: {
			feature: {
				saveAsImage: {}
			}
		},*/
		grid: {
			left: '10%',
			right: '20%',
			top: '5%',
			bottom: '0',
			containLabel: true
		},
		legend: {
			show: false,
			selectedMode:false,
			orient: 'vertical',
			left: '10%',
			data: [{
				name: '非敏感',
				textStyle: {
					color: '#0E7DC0',
					fontSize: 16,

				}
			}, {
				name: '敏感',
				textStyle: {
					color: '#C14B2D',
					fontSize: 16,

				}
			}, ],
			itemWidth: 15,
			itemHeight: 15
		},
		xAxis: [{
			type: 'category',
			data: [],
			axisLine: {
				show: false,
				lineStyle: {
					color: "#000"
				}
			},
			axisTick: {
				show: false,
			}
		}],
		yAxis: [{
			type: 'value',
			show: false
		}],
		series: [{
				type: 'bar',
				barGap: '-100%',
				barWidth: '20%',
				data: [{
					name: (data.zm[0].toFixed(2) + '%'),
					value: '110',
				}, {
					name: (data.fm[0].toFixed(2) + '%'),
					value: '110',
				}],
				label: {
					normal: {
						show: true,
						position: 'top',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: 'rgba(0,0,0,0.05)',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},
					},
					emphasis: {
						color: 'rgba(0,0,0,0.05)'
					}
				}
			},
			{
				name: '男',
				type: 'bar',
				stack: '性别统计',
				barWidth: '20%',
				data: [10, 10],
				label: {
					normal: {
						show: false
					}
				},
				itemStyle: {
					normal: {
						color: '#ffffff'
					}
				}
			}, {
				name: '非敏感',
				type: 'bar',
				stack: '性别统计',
				barWidth: '20%',
				data: [{
					name: "非敏感",
					value: data.zm[0],
					itemStyle: {
						normal: {
							color: '#0E7DC0'
						}
					}

				}, {
					name: "敏感",
					value: data.fm[0],
					itemStyle: {
						normal: {
							color: '#C14B2D'
						}
					}
				}],
				label: {
					normal: {
						show: false
					}
				},
				itemStyle: {
					normal: {
						color: '#0E7DC0'
					}
				}
			}, {
				type: 'bar',
				barGap: '-100%',
				barWidth: '20%',
				data: [{
					name: (data.zm[1].toFixed(2) + '%'),
					value: '-110',
				}, {
					name: (data.fm[1].toFixed(2) + '%'),
					value: '-110',
				}],
				label: {
					normal: {
						show: true,
						position: 'bottom',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: 'rgba(0,0,0,0.05)',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},
					},
					emphasis: {
						color: 'rgba(0,0,0,0.05)'
					}
				},
			}, {
				name: '女',
				type: 'bar',
				stack: '性别统计',
				barWidth: '20%',
				data: [-10, -10],
				label: {
					normal: {
						show: false
					}
				},
				itemStyle: {
					normal: {
						color: '#ffffff'
					}
				}
			}, {
				name: '敏感',
				type: 'bar',
				stack: '性别统计',
				barWidth: '20%',
				data: [{
					name: "非敏感",
					value: ("-" + data.zm[1].toFixed(2)),
					itemStyle: {
						normal: {
							color: '#0E7DC0'
						}
					}

				}, {
					name: "敏感",
					value: ("-" + data.fm[1].toFixed(2)),
					itemStyle: {
						normal: {
							color: '#C14B2D'
						}
					}
				}],
				label: {
					normal: {
						show: false
					}
				},
				itemStyle: {
					normal: {
						color: '#C14B2D'
					}
				}
			},
		]
	};

	emotionBar1.setOption(option);
	setTimeout(function (){
        window.onresize = function () {
            emotionBar1.resize();
        }
    });
}

//微博等级分析
function emotionWeiboLevelAnalysisChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	
	var zmData = new Array();
	var fmData = new Array();
	for(n in data.zm){
		zmData.push({
			name: data.zm[n].toFixed(2) + '%',
			value: '100'
		})
	}
	for(n in data.fm){
		fmData.push({
			name: data.fm[n].toFixed(2) + '%',
			value: '100'
		})
	}

	var emotionBar2 = echarts.init(document.getElementById(demo));

	var option = {
		legend: {
			type: 'plain',
			show: false,
			data: ['非敏感', '敏感'],
			orient: 'horizontal',
			textStyle: {
				color: '#000000',
				align: 'left',
				fontSize: 16
			},
			itemGap: 550,
			itemWidth: 5,
			itemHeight: 15
		},
		/*toolbox: {
			feature: {
				saveAsImage: {}
			}
		},*/
		grid: [{
			show: false,
			left: '12%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '41%'
		}, {
			show: false,
			left: '51%',
			top: '10%',
			bottom: 40,
			width: '14%',
		}, {
			show: false,
			right: '12%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '41%'
		}, ],
		xAxis: [{
			gridIndex: 0,
			type: 'value',
			position: 'bottom',
			inverse: true,
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				show: false,
			},
			splitLine: {
				show: false,
			},
			max: 100
		}, {
			gridIndex: 1,
			type: 'value',
			show: false,
		}, {
			gridIndex: 2,
			type: 'value',
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false,
			},

			axisLabel: {
				show: false,
			},
			splitLine: {
				show: false,
			},
			max: 100
		}],
		yAxis: [{
			type: 'category',
			inverse: true,
			position: 'right',
			axisLine: {
				show: false,
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: false,
			},
			data: [],

		}, {
			gridIndex: 1,
			type: 'category',
			inverse: true,
			position: 'left',
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: true,
				textStyle: {
					align: 'center',
					color: '#888888',
					fontSize: 13,
					padding: 5,
				}

			},
			data: data.legend

		}, {
			gridIndex: 2,
			type: 'category',
			inverse: true,
			position: 'left',
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				show: false,
			},
			data: [],
		} ],
		series: [{
				name: '非敏感',
				type: 'bar',
				xAxisIndex: 0,
				yAxisIndex: 0,
				z: 10,
				barCategoryGap: '40%',
				barWidth: '40%',
				label: {
					normal: {
						show: false,
						position: 'insideRight',
						textStyle: {
							color: '#000000',
						}

					}
				},
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'inner',
							formatter: function(data) {
								return data.name
							},
						},
						color: '#357CBA',
					}
				},
				data: data.zm
			},
			{
				name: '非敏感',
				type: 'bar',
				xAxisIndex: 0,
				yAxisIndex: 0,
				silent: false,
				barGap: '-100%',
				barWidth: '40%',
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#F4F4F4',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},
					},
					emphasis: {
						color: '#F4F4F4',
					}
				},
				data: zmData
			},
			{
				name: '敏感',
				type: 'bar',
				z: 10,
				barCategoryGap: '40%',
				xAxisIndex: 2,
				yAxisIndex: 2,
				barWidth: '40%',
				label: {
					normal: {
						show: false,
						position: 'insideLeft',
						textStyle: {
							color: '#000000',
						}

					},
				},
				itemStyle: {
					normal: {
						label: {
							show: true,
							position: 'inner',
							formatter: function(data) {
								return data.name
							},
						},
						color: '#C77055',
					}

				},
				data: data.fm
			}, {
				name: '敏感',
				type: 'bar',
				xAxisIndex: 2,
				yAxisIndex: 2,
				silent: false,
				barGap: '-100%',
				cursor: "pointer",
				barWidth: '40%',
				label: {
					normal: {
						show: true,
						position: 'right',
						textStyle: {
							color: '#666666',
							fontSize: 14,
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#F4F4F4',
						label: {
							show: false,
							position: 'right',
							color: '#000',
							formatter: function(params) {
								return params.name
							},
						},

					},
					emphasis: {
						color: '#EEEEEE',
					},
				},
				data: fmData
			}
		]
	};
	
	emotionBar2.setOption(option);
	
	setTimeout(function (){
        window.onresize = function () {
            emotionBar2.resize();
        }
    });
}

//粉丝分布
function emotionFansAnalysisChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	var emotionBar3 = echarts.init(document.getElementById(demo));
	var option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		/*toolbox: {
			feature: {
				saveAsImage: {}
			}
		},*/
		grid: {
			left: '0',
			right: '10%',
			bottom: '10%',
			containLabel: true
		},
		xAxis: [{
			type: 'category',
			data: data.xAxis,
			'axisLine': {
				show: true,
				lineStyle:{
					color:"#6f7786"
				}
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				interval: 0,
				rotate: 30,
				show: true,
				splitNumber: 15,
				textStyle: {
					fontFamily: "微软雅黑",
					fontSize: 12
				}
			},
			splitLine: {
				show:true,
				lineStyle:{
					width:24,
					color:'#EEEEEE'
				}
			},
			boundaryGap: false
		}],
		yAxis: [{
			type: 'value',
			show: false
		}],
		series: [{
			name: '非敏感',
			type: 'bar',
			stack: '粉丝分布',
			barWidth: 24,
			data: data.zm,
			label: {
				normal: {
					show: false
				}
			},
			itemStyle: {
				normal: {
					color: '#0E7DC0'
				}
			}
		}, {
			name: '敏感',
			type: 'bar',
			stack: '粉丝分布',
			barWidth: 24,
			data: data.fm,
			label: {
				normal: {
					show: false
				}
			},
			itemStyle: {
				normal: {
					color: '#D9684B'
				}
			}
		}, ]
	};

	emotionBar3.setOption(option);

	setTimeout(function (){
        window.onresize = function () {
            emotionBar3.resize();
        }
    });
}

//转发层级
function emotionRevertLevelAnalysisChartCallback(data, demo){
	if(data == null || !data.zm || !data.fm || data.zm.length!=4 || data.fm.length!=4){
		$("#"+demo).html(warnHtml1);
		return;
	}
	var zm = data.zm;
	var fm = data.fm;
	var name = data.name;
	var seriesData = new Array();
	var center = ['12%', '37%', '62%', '87%'];

	var html = "";
	for(n in name){
		if(zm[n] == 0 && fm[n] == 0){
			seriesData.push({
				name: name[n],
				center: [center[n], '40%'],
				radius: ['23%', '30%'],
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
						emphasis: {
							show:false
						}
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
					hoverAnimation: true
				}]
			})
		}else{
			seriesData.push({
				name: name[n],
				center: [center[n], '40%'],
				radius: ['23%', '30%'],
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
		html += '<li><p class="pieColor1">非敏感 ' + zmPre + '%</p><p class="pieColor2">敏感 ' + fmPre + '%</p></li>'
	}
	$("#ul" + demo).html(html);
	
	var emotionBar4 = echarts.init(document.getElementById(demo));
	var option = {
		tooltip: {
			trigger: 'item',

		},
		/*toolbox: {
			feature: {
				saveAsImage: {}
			}
		},*/
		grid: {
			bottom: 30
		},
		xAxis: [{
			type: 'category',
			axisLine: {
				show: false
			},
			axisTick: {
				show: false
			},
			axisLabel: {
				interval: 0
			},
			data: []
		}],
		yAxis: [{
			show: false
		}],
		series: seriesData
	};
	emotionBar4.setOption(option);
	
	setTimeout(function (){
        window.onresize = function () {
            emotionBar4.resize();
        }
    });
}

//兴趣词云
function interestWordCloudChartCallback(data, demoIndex){
	if(data == null){
		$("#interestWordCloud-zm" + demoIndex).html(warnHtml1);
		$("#interestWordCloud-fm" + demoIndex).html(warnHtml1);
		return;
	}
	var zm = data.zm;
	var fm = data.fm;
	var html = '';
	if(zm == null || zm.length == 0){
		$("#interestWordCloud-zm" + demoIndex).html(warnHtml1);
	}else{
		for(n in zm){
			var index = parseInt(n)+1;
			html += '<p class="posiab_' + index + '">';
			html += '<a href="javascript:;" data-toggle="tooltip" data-placement="right" title="" data-original-title="' + zm[n].name + '：' + zm[n].value + '">' + zm[n].name + '</a></p>';
		}
		$("#interestWordCloud-zm" + demoIndex).html(html);
	}
	
	html = '';
	if(fm == null || fm.length == 0){
		$("#interestWordCloud-fm" + demoIndex).html(warnHtml1);
	}else{
		for(n in fm){
			var index = parseInt(n)+1;
			html += '<p class="posiab_' + index + '">';
			html += '<a href="javascript:;" data-toggle="tooltip" data-placement="right" title="" data-original-title="' + fm[n].name + '：' + fm[n].value + '">' + fm[n].name + '</a></p>';
		}
		$("#interestWordCloud-fm" + demoIndex).html(html);
	}
	$("[data-toggle='tooltip']").tooltip();//全局提示效果
}
/*用户情绪观洞察end*/

