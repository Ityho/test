
var geoCoordMap = {
	'钓鱼岛': [123.0254, 25.1986],
	'赤尾屿': [126.0054, 26.1986]
};

var mapFeatures = echarts.getMap('china').geoJson.features;
mapFeatures.forEach(function(v) {
	var name = v.properties.name;
	geoCoordMap[name] = v.properties.cp;
});
var convertData = function(data) {
	var res = [];
	for(var i = 0; i < data.length; i++) {
		var geoCoord = geoCoordMap[data[i].name];
		if(geoCoord) {
			res.push({
				name: data[i].name,
				value: geoCoord.concat(data[i].value)
			});
		}
	}
	return res;
};
var dataT = [{
	name: '钓鱼岛',
	value: 5
}, {
	name: "赤尾屿",
	value: 14
}, ];


var myChart = [], echartsOpts = {

	hotline :{
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'line',
				lineStyle: {
					color: 'rgba(0,0,0,0)'
				}
			},
			backgroundColor: 'rgb(58, 58, 58)',

		},
		toolbox: {
			orient: 'vertical',
		},
		legend: {
			top: '0px',
			data: ['']
		},
		grid: {
			left: '2%',
			right: '12%',
			top: '20%',
			bottom: '12%',
			containLabel: true
		},
		xAxis: [{
			type: 'category',
			axisLine: { //坐标轴轴线 默认 true,
				show: true,
				lineStyle: {
					color: '#F18D00',
					width: 1,
					type: 'solid'
				}
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#8F9DBA'
				}
			},
			boundaryGap: false,
			data:[]
		}],
		yAxis: [{
			type: 'value',
			axisLine: { //坐标轴轴线 默认 true,
				show: false
			},
			axisTick: { //坐标轴刻度
				show: false
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				textStyle: {
					color: '#8F9DBA'
				}
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(106, 116, 139, 0.1)',

				}
			},
		}],

		series: [{
			name: '',
			type: 'line',
			smooth: true, //这句就是让曲线变平滑的
			showAllSymbol: true,
			symbol: 'circle', //拐点样式
			symbolSize: 3,
			markPoint: {
				data: [{
					type: 'max',
					name: '最大值',
					symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
					symbolOffset: [0, '-100%'],
					symbolSize: [50, 21],
					label: {
						normal: {
							show: true,
							position: 'insideTop',
							textStyle: {
								fontSize: '12',
								fontWeight: 'bold',
								color: '#fff',
							}
						}
					}
				}, {
					symbol: 'circle',
					type: 'max',
					symbolSize: [10, 10],
					itemStyle: {
						normal: {
							color: "#f18d00",
							borderColor: 'rgba(241,141,0, .3)', //rgba(255, 199, 43, .3)
							borderWidth: 10,
							shadowColor: '#ffc72b',
							shadowBlur: 30
						}
					},
					label: {
						normal: {
							show: false
						}
					}
				}]
			},
			markLine: {
				data: [{
					type: 'average',
					name: '平均值',
					label: {
						normal: {
							show: true,
							textStyle: {
								fontSize: '12',
								fontWeight: 'bold',
								color: '#F18D00',
							},
						}
					}
				}]
			},
			itemStyle: {
				normal: {
					color: '#F18D00'
				}
			},
			lineStyle: {
				normal: {
					color: '#F18D00',
					width: 1.5,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
						offset: 0,
						color: 'rgba(241, 141, 1, 0.5)'
					}, {
						offset: 1,
						color: 'rgba(241, 141, 1, 0)'
					}], false),
					shadowColor: 'rgba(0, 0, 0, 0.1)',
					shadowBlur: 10
				}
			},
			data : []
		}]
	},
	wordCloud:{// 字符词云
		tooltip : {},
		maskImage :"",
		series : [ {
			type : 'wordCloud',
			shape : 'circle',
			width : '80%',
			height : '55%',
			gridSize : 0, // 词间距
			sizeRange : [ 12, 30 ],// 字体大小 范围
			rotationRange : [0, 0 ], // 字体旋转角度范围
			textStyle : {
				normal : {
					color : function() {
						var colors = [ "#f18d00", "#f1b192", "#b9a7af" ];// 词云颜色
						var i = parseInt(Math.random() * 3);
						return colors[i];
					}
				},
				emphasis : {
					shadowBlur : 10,
					shadowColor : '#333'
				}
			},
			data : []
		} ]
	},
	emotionProportion :{
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			top: '0',
			left: 'center',
			bottom: '20',
			itemGap: 10,
			itemWidth: 10,
			itemHeight: 10,
			borderRadius: 0,
			padding: 10,
			data: []
		},

		series: [{
			name: '情绪构成',
			type: 'pie',
			radius: ['30%', '60%'],
			center: ['50%', '65%'],
			data: [],
			label: {
				normal: {
					textStyle: {
						fontSize: 12
					},
					formatter: function(param) {
						return param.name + ':\n' + param.percent + '%';
					}
				}
			},

		}]
	},
	emotionProportion2:{
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		toolbox: {
			orient: 'vertical',
		},
		grid: {
			left: '10%',
			right: '8%',
			bottom: '10%',
			containLabel: true
		},

		xAxis: [{
			name: '',
			nameTextStyle: {
				color: '#0086ce'
			},
			type: 'value',
			axisLine: { //坐标轴轴线 默认 true,
				show: false,
				lineStyle: {
					color: '#dbdbdb',
					width: 1,
					type: 'solid'
				}
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			splitLine: {
				show: false
			},
			axisLabel: { //坐标轴刻度标签
				show: false,
				textStyle: {
					color: '#888'
				}
			}
		}],
		yAxis: [{
			type: 'category',
			axisLine: { //坐标轴轴线 默认 true,
				show: true,
				lineStyle: {
					color: '#dbdbdb',
					width: 1,
					type: 'solid'
				}
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				textStyle: {
					color: '#888'
				}
			},

			boundaryGap: false,
			data: []
		}],
		series: [{
			name: '情绪构成',
			type: 'bar',
			barWidth: '40%',
			itemStyle: {
				normal: {
					color: '#3fad7e'
				}
			},
			data: []
		}]
	},
	emotionMap:
	// 	{
	// 	legend:{show:false},
	// 	tooltip : {
	// 		trigger : 'item'
	// 	},
	// 	visualMap : {
	// 		show : true,
	// 		type : 'piecewise',
	// 		orient : 'vertical',
	// 		x : 'left',
	// 		y : '10',
	// 		inverse : true,
	// 		pieces : []
	// 	},
	// 	series : [ {
	// 		name : '',
	// 		type : 'map',
	// 		mapType : 'china',
	// 		showLegendSymbol : false,
	// 		roam : false,
	// 		zoom : 1.2,
	// 		itemStyle : {
	// 			normal : {
	// 				label : {
	// 					show : false
	// 				},
	// 				// borderWidth : 1,
	// 				borderColor : '#959595',
	// 			},
	// 			emphasis : {
	// 				label : {
	// 					show : true
	// 				}
	// 			}
	// 		},
	// 		data : [{
	// 			name : '南海诸岛',
	// 			"value" : 0,
	// 			itemStyle : {
	// 				normal : {
	// 					borderColor : '#959595',
	// 					areaColor : '#efefef',
	// 				}
	// 			}
	// 		}],
	// 		tooltip : {
	// 			textStyle : {
	// 				align : 'left'
	// 			}
	// 		}
	// 	} ]
	// },

		{
		visualMap: {
			show: true,
			type: 'piecewise',
			orient: 'vertical',
			x: 'left',
			y: 'bottom',
			inverse: true,
			categories: ['喜悦', '愤怒', '悲伤', '惊奇', '恐惧', '中性'],
			pieces: [],
			textStyle: {
				color: '#8F9DBA'
			}
		},
		animation: false,
		tooltip: {
			trigger: 'item',
			show: false,
		},geo: {
				map: 'china',
				left: 'center',
				layoutCenter: ['50%', '40%'],
				layoutSize: '90%',
				selectedMode: 'single',
				label: {
					normal: {
						textStyle: {
							color: '#000'
						},
						show: false
					},
				},
				itemStyle: {
					normal: {
						borderColor: '#959595',
						areaColor: 'transparent', //地图背景颜色
					},

				},
			},
		series: [
			{
				name: '钓鱼岛',
				type: 'scatter',
				coordinateSystem: 'geo',
				data: convertData(dataT),
				symbolSize: function(val) {
					return val[2] / 10000;
				},
				label: {
					normal: {
						formatter: '{b}',
						position: 'top',
						show: true,
						textStyle: {
							color: "#fff",
							fontSize: 8
						}
					},

				},

			},
			{
			name: '愤怒',
			type: 'map',
			mapType: 'china',
			roam: false,
			left: 'center',
			layoutCenter: ['50%', '40%'],
			layoutSize: '90%',
			label: {
				normal: {
					show: false,
					textStyle: {
						color: "#3a3a3a", //地图文字颜色
						fontSize: 8
					}
				}

			},
			itemStyle: {
				normal: {
					borderColor: '#959595',
					areaColor: 'transparent',
					// areaColor: '#f18d00', //地图背景颜色
				},
			},
			data: []
		},
		]
	},
	trendsLine:{
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			show: true,
			x: 'center',
			top: '2%',
			icon: 'stack',
			itemWidth: 14,
			itemHeight: 10,
			borderRadius: 3,
			data:[]
		},
		grid: {
			left: '2%',
			right: '10%',
			top: '18%',
			bottom: '5%',
			containLabel: true
		},
		xAxis: [{
			type: 'category',
			axisLine: { //坐标轴轴线 默认 true,
				show: false
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(106,116,139,0.1)'
				}
			},
			data: [],
			axisLabel: { //坐标轴刻度标签
				show: true,
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#8F9DBA',
					fontSize: 10,
				},
				formatter: function(params) {
					return params.split(' ')[0] + '\n' + params.split(' ')[1]
				}
			},
			boundaryGap: false,

		}],
		yAxis: [{
			type: 'value',
			axisLine: { //坐标轴轴线 默认 true,
				show: false,
				textStyle: {
					color: '#8F9DBA'
				}
			},
			axisTick: { //坐标轴刻度
				show: false
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: 'rgba(106,116,139,0.1)'
				}
			},

			axisLabel: { //坐标轴刻度标签
				show: true,
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#8F9DBA'
				}
			},
		}],
		series: []
	},
	emotionSex:{
		tooltip: {
			show: true,
			trigger: 'item',
			formatter: '{c}',
			axisPointer: {
				type: 'shadow',
			}
		},
		legend: {
			type: 'plain',
			itemGap: 150,
			data: [{
				name: "男",
				textStyle: {
					fontSize: 13,
					color: '#8F9DBA',
				}

			}, {
				name: "女",
				textStyle: {
					fontSize: 13,
					color: '#8F9DBA',
				}
			}],
			width: '100%',
			show: true,
			orient: 'horizontal',
			itemWidth: 0,
			itemHeight: 0
		},
		grid: [{
			show: false,
			left: '6%',
			top: '8%',
			bottom: '10%',
			containLabel: false,
			width: '35%'
		}, {
			show: false,
			left: '52%',
			top: '8%',
			bottom: '11%',
			width: '20%'
		}, {
			show: false,
			right: '6%',
			top: '8%',
			bottom: '10%',
			containLabel: false,
			width: '35%'
		}, ],
		xAxis: [{
			gridIndex: 0,
			type: 'value',
			position: 'bottom',
			inverse: true,
			axisLine: {
				show: false,
				lineStyle: {
					color: "#798091"
				}
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				show: true
			},
			splitLine: {
				show: false,
			},
			splitNumber: 1,
			max: 500
		}, {
			gridIndex: 1,
			type: 'value',
			show: false,
		}, {
			gridIndex: 2,
			type: 'value',
			axisLine: {
				show: false,
				lineStyle: {
					color: "#798091"
				}
			},
			axisTick: {
				show: false,
			},

			axisLabel: {
				show: true,
			},
			splitLine: {
				show: false,
			},
			splitNumber: 1,
			max: 500
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
			data: ['喜悦', '愤怒', '悲伤', '惊奇', '恐惧', '中性'],
			axisLabel: {
				show: true,
				textStyle: {
					align: 'center',
					color: '#8F9DBA',
					fontSize: 13
				},
			},
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
			name: '男',
			type: 'bar',
			xAxisIndex: 0,
			yAxisIndex: 0,
			z: 10,
			barWidth: 20,
			barCategoryGap: '10%',
			data: [{
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#F08E00',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#F08E00',
						}

					},
				},
			}, {
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#CE431E',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#CE431E',
						}

					},
				},
			}, {
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#147BC0',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#147BC0',
						}

					},
				},
			}, {
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#46B585',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#46B585',
						}

					},
				},
			}, {
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#6360d9',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#6360d9',
						}

					},
				},
			}, {
				name: '',
				value: '',
				itemStyle: {
					normal: {
						color: '#9DA7B5',
					}
				},
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#9DA7B5',
						}

					},
				},
			}]
		}, {
			name: '男',
			type: 'bar',
			xAxisIndex: 0,
			yAxisIndex: 0,
			silent: false,
			barGap: '-100%',
			barWidth: 20,
			tooltip: {
				show: false,
			},
			label: {
				normal: {
					show: false,
					position: 'left',
					textStyle: {
						color: '#888888',
					}

				},
			},
			itemStyle: {
				normal: {
					show: false,
					color: '#262336',

				}
			},
			data: []
		},

			{
				name: '女',
				type: 'bar',
				z: 10,
				barCategoryGap: '20%',
				barWidth: 20,
				xAxisIndex: 2,
				yAxisIndex: 2,
				data: [{
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#F08E00'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#F08E00'
							}

						},
					},
				}, {
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#CE431E'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#CE431E'
							}

						},
					},
				}, {
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#147BC0'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#147BC0'
							}

						},
					},
				}, {
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#46B585'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#46B585'
							}

						},
					},
				}, {
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#6360d9'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#6360d9'
							}

						},
					},
				}, {
					name: '',
					value: '',
					itemStyle: {
						normal: {
							color: '#9DA7B5'
						}
					},
					label: {
						normal: {
							show: true,
							position: 'right',
							textStyle: {
								color: '#9DA7B5'
							}

						},
					},
				}]
			}, {
				name: '女',
				type: 'bar',
				xAxisIndex: 2,
				yAxisIndex: 2,
				silent: false,
				barGap: '-100%',
				cursor: "pointer",
				barWidth: 20,
				tooltip: {
					show: false,
				},
				label: {
					normal: {
						show: false,
					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#262336',

					}
				},
				data: []
			}
		]
	},
	emotionType:{
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend: {
			top: '10',
			left: 'center',
			bottom: '5',
			itemGap: 10,
			itemWidth: 14,
			itemHeight: 10,
			padding: 10,
			data: [{
				name: '喜悦',
				textStyle: {
					color: '#F08E00',
					fontSize: 12

				}
			}, {
				name: '愤怒',
				textStyle: {
					color: '#CE431E',
					fontSize: 12,
					padding: 4
				}
			}, {
				name: '悲伤',
				textStyle: {
					color: '#147BC0',
					fontSize: 12,
					padding: 4
				}
			}, {
				name: '惊奇',
				textStyle: {
					color: '#46B585',
					fontSize: 12,
					padding: 4
				}
			}, {
				name: '恐惧',
				textStyle: {
					color: '#6360d9',
					fontSize: 12,
					padding: 4
				}
			}, {
				name: '中性',
				textStyle: {
					color: '#9DA7B5',
					fontSize: 12,
					padding: 4
				}
			}, ]
		},
		grid: {
			top: "12%",
			left: '3%',
			right: '3%',
			bottom: '0',
			containLabel: true
		},
		xAxis: {
			type: 'value',
			axisLine: { //坐标轴轴线 默认 true,
				show: false,
				lineStyle: {
					color: '#dbdbdb',
					width: 1,
					type: 'solid'
				}
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			splitLine: {
				show: false
			},
			axisLabel: { //坐标轴刻度标签
				show: false,
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#888'
				}
			}
		},
		yAxis: {
			type: 'category',
			axisLine: { //坐标轴轴线 默认 true,
				show: false,
				lineStyle: {
					color: '#dbdbdb',
					width: 1,
					type: 'solid'
				}
			},
			axisTick: { //坐标轴刻度
				show: false,
				lineStyle: {
					color: '#888',
					width: 1,
					type: 'solid'
				}
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				textStyle: {
					fontSize: 13,
					color: '#8F9DBA'
				}
			},

			data: ['普通用户', '橙V用户', '蓝V用户', '达人用户']
		},
		series: [{
			name: '喜悦',
			type: 'bar',
			stack: '总量',
			barWidth: '16',
			label: {
				normal: {
					show: false,
					position: 'insideRight'
				}
			},
			itemStyle: {
				normal: {
					color: '#F08E00',
				}
			},
			data: []
		}, {
			name: '愤怒',
			type: 'bar',
			stack: '总量',
			barWidth: '16',
			label: {
				normal: {
					show: false,
					position: 'insideRight'
				}
			},
			itemStyle: {
				normal: {
					color: '#CE431E',
				}
			},
			data: []
		}, {
			name: '悲伤',
			type: 'bar',
			stack: '总量',
			barWidth: '16',
			label: {
				normal: {
					show: false,
					position: 'insideRight'
				}
			},
			itemStyle: {
				normal: {
					color: '#147BC0',
				}
			},
			data: []
		},
			{
				name: '惊奇',
				type: 'bar',
				stack: '总量',
				barWidth: '16',
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
					normal: {
						color: '#46B585',
					}
				},
				data: []
			},
			{
				name: '恐惧',
				type: 'bar',
				stack: '总量',
				barWidth: '16',
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
					normal: {
						color: '#6360d9',
					}
				},
				data: []
			}, {
				name: '中性',
				type: 'bar',
				stack: '总量',
				barWidth: '16',
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
					normal: {
						color: '#9DA7B5',
					}
				},
				data: []
			},
		]
	},
	emotionSex_M:{
		tooltip: {
			trigger: 'item',
			show: false

		},
		title: {
			text: '男',
			left: 'center',
			top: '44%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 13,
				align: 'center'
			}
		},
		grid: {
			bottom: 10
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
		series: [{
			name: '',
			center: ['50%', '50%'],
			radius: ['40', '60'],
			type: 'pie',
			labelLine: {
				normal: {
					show: false
				}
			},label:{
				normal:{
					show:false
				}
			},
			data: [{
				value:'',
				name: '敏感',
				itemStyle: {
					normal: {
						color: '#CA6E58'
					},
				},
				label: {
					normal: {
						position: 'center',
						show: false,
						textStyle: {
							fontSize: '13',
							color: '#8F9DBA'
						}
					}
				}
			}, {
				value: '',
				name: '非敏感',
				tooltip: {
					show: false
				},
				itemStyle: {
					normal: {
						color: '#387BB8'
					},
					emphasis: {
						color: '#387BB8'
					}
				},
				hoverAnimation: false
			}]
		}]
	},
	emotionSex_F:{
		tooltip: {
			trigger: 'item',
			show: false
		},
		title: {
			text: '女',
			left: 'center',
			top: '44%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 13,
				align: 'center'
			}
		},
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
		series: [{
			name: '',
			center: ['50%', '50%'],
			radius: ['40', '60'],
			type: 'pie',
			labelLine: {
				normal: {
					show: false
				}
			},label:{
				normal:{
					show:false
				}
			},
			data: [{
				value: '',
				name: '敏感',
				itemStyle: {
					normal: {
						color: '#CA6E58'
					},
				},

				label: {
					normal: {
						position: 'center',
						show: false,
						textStyle: {
							fontSize: '13',
							color: '#8F9DBA'
						}
					}
				}
			}, {
				value: '',
				name: '非敏感',
				tooltip: {
					show: false
				},
				itemStyle: {
					normal: {
						color: '#387BB8'
					},
					emphasis: {
						color: '#387BB8'
					}
				},
				hoverAnimation: false
			}]
		}]
	},
	emotionType2:{
		legend: {
			show: false,
			type: 'plain',
			show: false,
			data: ['敏感', '非敏感'],
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
		grid: [{
			show: false,
			left: '14%',
			top: '5%',
			bottom: 20,
			containLabel: false,
			width: '28%'
		}, {
			show: false,
			left: '52%',
			top: '7%',
			bottom: 30,
			width: '10%',
		}, {
			show: false,
			right: '12%',
			top: '5%',
			bottom: 20,
			containLabel: false,
			width: '28%'
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
					color: '#8F9DBA',
					fontSize: 12,
				}

			},
			data: ['普通用户', '橙V用户', '蓝V用户', '达人']

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
			name: '敏感',
			type: 'bar',
			xAxisIndex: 0,
			yAxisIndex: 0,
			z: 10,
			barCategoryGap: '40%',
			barWidth: '25%',
			label: {
				normal: {
					show: false,
					position: 'insideRight',
					textStyle: {
						color: '#387BB8',
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
					color: '#387BB8',
				}
			},
			data: []
		},
			{
				name: '敏感',
				type: 'bar',
				xAxisIndex: 0,
				yAxisIndex: 0,
				silent: false,
				barGap: '-100%',
				barWidth: '25%',
				label: {
					normal: {
						show: true,
						position: 'left',
						textStyle: {
							color: '#387BB8',
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#262336',
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
						color: '#262336',
					}
				},
				data: []
			},
			{
				name: '非敏感',
				type: 'bar',
				z: 10,
				barCategoryGap: '40%',
				xAxisIndex: 2,
				yAxisIndex: 2,
				barWidth: '25%',
				label: {
					normal: {
						show: false,
						position: 'insideLeft',
						textStyle: {
							color: '#CA6E58',
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
						color: '#CA6E58',
					}

				},
				data: [],
			},
			{
				name: '非敏感',
				type: 'bar',
				xAxisIndex: 2,
				yAxisIndex: 2,
				silent: false,
				barGap: '-100%',
				cursor: "pointer",
				barWidth: '25%',
				label: {
					normal: {
						show: true,
						position: 'right',
						textStyle: {
							color: '#CA6E58',
						}

					},
				},
				itemStyle: {
					normal: {
						show: false,
						color: '#262336',
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
						color: '#262336',
					},
				},
				data: []
			}]
	},
	emotionFans:{
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		legend: {
			show: false,
			left: '2%',
			bottom: "2%",
			data: [],
			itemWidth: 15,
			itemHeight: 15
		},
		grid: {
			top: '5%',
			bottom: '30%'
		},
		xAxis: [{
			type: 'category',
			data: [],
			axisLine: {
				show: false,
				lineStyle: {
					color: "rgba(38, 35, 54, 1)"
				}
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				interval: 0,
				rotate: 45,
				show: true,
				splitNumber: 15,
				textStyle: {
					fontFamily: "微软雅黑",
					fontSize: 12,
					color: '#8F9DBA'
				}
			},
		}],
		yAxis: [{
			type: 'value',
			show: false
		}],
		series: [{
			type: 'bar',
			itemStyle: {
				normal: {
					color: 'rgba(38, 35, 54, 1)'
				}
			},
			barGap: '-100%',
			barWidth: '40%',
			data: [],
			animation: false
		}, {
			name: '表示正面',
			type: 'bar',
			stack: '广告',
			barWidth: '40%',
			data: [],
			label: {
				normal: {
					show: false
				}
			},
			itemStyle: {
				normal: {
					color: '#387BB8'
				}
			}
		}, {
			name: '表示负面',
			type: 'bar',
			stack: '广告',
			barWidth: '40%',
			data: [],
			label: {
				normal: {
					show: false
				}
			},
			itemStyle: {
				normal: {
					color: '#CA6E58'
				}
			}
		}, ]
	},
	emotionLevel1:{
		title: {
			text: '一级转发',
			top: '44%',
			left: '22%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 12
			}
		},
		legend: {
			orient: 'vertical',
			show: false
		},
		series: [
			{
			type: "pie",
			center: ["30%", "50%"],
			radius: ["70%", "75%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#CA6E58"
					}
				}
			},
				{
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		},
			{
			type: "pie",
			center: ["30%", "50%"],
			radius: ["85%", "90%"],
			hoverAnimation: false,
			data: [
				{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#387BB8"
					}
				}
			},
				{ //画剩余的刻度圆环
					name:'',
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}]
	},
	emotionLevel2:{
		title: {
			text: '二级转发',
			top: '44%',
			left: '22%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 12
			}
		},
		legend: {
			orient: 'vertical',
			show: false
		},
		series: [{
			type: "pie",
			center: ["30%", "50%"],
			radius: ["70%", "75%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#CA6E58"
					}
				}
			},

				{
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}, {
			type: "pie",
			center: ["30%", "50%"],
			radius: ["85%", "90%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#387BB8"
					}
				}
			} , { //画剩余的刻度圆环
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}]
	},
	emotionLevel3:{
		title: {
			text: '三级转发',
			top: '44%',
			left: '22%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 12
			}
		},
		legend: {
			orient: 'vertical',
			show: false
		},
		series: [{
			type: "pie",
			center: ["30%", "50%"],
			radius: ["70%", "75%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#CA6E58"
					}
				}
			},
				{
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}, {
			type: "pie",
			center: ["30%", "50%"],
			radius: ["85%", "90%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#387BB8"
					}
				}
			}, { //画剩余的刻度圆环
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}]
	},
	emotionLevel4:{
		title: {
			text: '四级转发',
			top: '44%',
			left: '22%',
			textStyle: {
				color: '#8F9DBA',
				fontSize: 12
			}
		},
		legend: {
			orient: 'vertical',
			show: false
		},
		series: [{
			type: "pie",
			center: ["30%", "50%"],
			radius: ["70%", "75%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#CA6E58"
					}
				}
			},
				{
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}, {
			type: "pie",
			center: ["30%", "50%"],
			radius: ["85%", "90%"],
			hoverAnimation: false,
			data: [{
				name: "Line",
				value: '',
				label: {
					normal: {
						show: false,
					}
				},
				labelLine: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: "#387BB8"
					}
				}
			},  { //画剩余的刻度圆环
					name: "",
					value: '',
					label: {
						normal: {
							show: false,
						}
					},
					labelLine: {
						normal: {
							show: false,
						}
					},
					itemStyle: {
						normal: {
							color: '#262336'
						}
					}
				}
			]
		}]
	},
	zmHotWord:{
		tooltip: {
			show: false
		},
		series: [{
			name: '全部',
			type: 'treemap',
			visibleMin: 300,
			width: "100%",
			height: '100%',
			roam: false,
			nodeClick: false,
			upperLabel: true,
			breadcrumb: {
				show: false
			},
			data: [],
			leafDepth: 2,
			label: {
				fontSize: 13,
			},
			levels: [{
				itemStyle: {
					normal: {
						color: "#387BB8",
						borderColor: '#1B172C',
						borderWidth: 2,
						gapWidth: 2
					}
				}
			},
				{
					colorSaturation: [0.3, 0.6],
					itemStyle: {
						normal: {
							borderColor: '#1B172C',
							gapWidth: 2,
							borderWidth: 2
						}
					}
				}
			]
		}]
	},
	fmHotWord:{
		tooltip: {
			show: false
		},
		series: [{
			name: '全部',
			type: 'treemap',
			visibleMin: 300,
			width: "100%",
			height: '100%',
			roam: false,
			nodeClick: false,
			upperLabel: true,
			breadcrumb: {
				show: false
			},
			data: [],
			leafDepth: 2,
			label: {
				fontSize: 13,
			},
			levels: [{
				itemStyle: {
					normal: {
						color: "#CA6E58",
						borderColor: '#1B172C',
						borderWidth: 2,
						gapWidth: 2
					}
				}
			},
				{
					colorSaturation: [0.3, 0.6],
					itemStyle: {
						normal: {
							borderColor: '#1B172C',
							gapWidth: 2,
							borderWidth: 2
						}
					}
				}
			]
		}]
	}

};

//刷新图表数据
refresh = function($id,opt){
	if(!opt.noDataText){
		opt.noDataText = "暂无数据";
	}
	opt.noDataLoadingechartsOpts = {
		text : opt.noDataText,
		effect : 'bubble',
		textStyle : {
			fontSize : 16
		}
	}
	myChart[$id.prop("id")].setOption(opt,true);
};

//设置图表数据
setEchartsOpion = function(opts){
	var type = opts.$id.data("type");
	// if (type != null){
	// 	// myChart[opts.$id.prop("id")].dispose();
	// 	myChart[opts.$id.prop("id")] = echarts.init(opts.$id[0],"macarons");
	// 	if(opts.event){
	// 		myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK,opts.event);
	// 	}
	// 	//深度拷贝
	// 	refresh(opts.$id,$.extend(true,{},echartsOpts[type],opts.opt));
	// }

	myChart[opts.$id.prop("id")] = echarts.init(opts.$id[0],"macarons");
	if(opts.event){
		myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK,opts.event);
	}
	//深度拷贝
	refresh(opts.$id,$.extend(true,{},echartsOpts[type],opts.opt));

};
