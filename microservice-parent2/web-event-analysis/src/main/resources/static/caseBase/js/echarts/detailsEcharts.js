//饼图
var echarts1 = echarts.init(document.getElementById('echarts1'));
var option = {
	title: {
		text: 20,
		x: 'center',
		y: 'center',
		textStyle: {
			fontWeight: 'normal',
			color: '#000000',
			fontSize: '25'
		}
	},
	color: ['rgba(226, 226, 226, 1)'],
	legend: {
		show: false
	},

	series: [{
		name: '热度指数',
		type: 'pie',
		clockWise: true,
		radius: ['50%', '60%'],
		itemStyle: {
			normal: {
				label: {
					show: false
				},
				labelLine: {
					show: false
				}
			}
		},
		hoverAnimation: false,
		data: [{
			value: 20,
			name: '01',
			itemStyle: {
				normal: {
					color: '#1E77CE',
					label: {
						show: false
					},
					labelLine: {
						show: false
					}
				}
			}
		}, {
			name: '02',
			value: 80
		}]
	}]
}
// 使用刚指定的配置项和数据显示图表。
echarts1.setOption(option);
//折线图
var echarts2 = echarts.init(document.getElementById('echarts2'));
var option = {
	tooltip: {
		trigger: 'axis',
		formatter: '{b} : {c}',
		position: ['50%', '50%']
	},
	grid: {
		left: '5%',
		right: '5%',
		top: '15%',
		bottom: '5%',
		containLabel: true
	},
	xAxis: [{
		type: 'category',
		axisLine: {
			show: false
		},
		axisTick: {
			show: false,
			lineStyle: {
				color: '#888',
				width: 1,
				type: 'solid'
			}
		},
		axisLabel: { // 坐标轴刻度标签
			show: true,
			// rotate: 30,  //旋转角度
			textStyle: {
				color: '#888'
			}
		},
		boundaryGap: false,
		data: ['2019-01-22 00', '2019-01-22 00', '2019-01-22 00', '2019-01-22 00', '2019-01-22 00', '2019-01-22 00', ]
	}],
	yAxis: [{
		type: 'value',
		axisLine: { // 坐标轴轴线 默认 true,
			show: false
		},
		axisTick: { // 坐标轴刻度
			show: false
		},
		axisLabel: { // 坐标轴刻度标签
			show: true,
			// rotate: 30,  //旋转角度
			textStyle: {
				color: '#888'
			}
		}
	}],
	series: [{
		name: '',
		type: 'line',
		symbol: 'emptyCircle',
		symbolSize: 8,
		markPoint: {
			data: [{
				type: 'max',
				name: '最大值',
				symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
				symbolOffset: [0, '-100%'],
				symbolSize: [68, 22],
				label: {
					normal: {
						show: true,
						position: 'insideTop',
						textStyle: {
							fontSize: '11',
							fontWeight: 'bold',
							color: '#fff'
						}
					}
				}
			}, {
				symbol: 'circle',
				type: 'max',
				symbolSize: [10, 10],
				itemStyle: {
					normal: {
						color: '#1E77CE',
						borderColor: 'rgba(30, 119, 206, 0.3)',
						borderWidth: 10
					}
				},
				label: {
					normal: {
						show: false
					}
				}
			}]
		},
		itemStyle: {
			normal: {
				color: '#1E77CE'
			}
		},
		lineStyle: {
			normal: {
				color: '#1E77CE',
				width: 1.5,
				type: 'solid'
			}
		},
		areaStyle: {
			normal: {
				color: '#1E77CE',
				shadowColor: 'rgba(0, 0, 0, 0.1)',
				shadowBlur: 10
			}
		},
		smooth: false,
		data: [100, 200, 400, 300, 200, 300, 100]
	}],
}
// 使用刚指定的配置项和数据显示图表。
echarts2.setOption(option);
//来源类型start
//来源类型end
var ciyunEchart = echarts.init(document.getElementById('ciyunEchart'));
var imgDom = "data:image/png;base64,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";
var wyimg = imgDom; //节点1
var wcData = [{
	"name": "崔永元",
	"value": 55757
}, {
	"name": "范冰冰",
	"value": 41886
}, {
	"name": "合同",
	"value": 28621
}, {
	"name": "冯小刚",
	"value": 17967
}, {
	"name": "曝光",
	"value": 14871
}, {
	"name": "新浪",
	"value": 14535
}, {
	"name": "恩怨",
	"value": 13669
}, {
	"name": "工作室",
	"value": 12002
}, {
	"name": "娱乐",
	"value": 11468
}, {
	"name": "手机",
	"value": 11381
}, {
	"name": "税务",
	"value": 10626
}, {
	"name": "声明",
	"value": 10539
}, {
	"name": "明星",
	"value": 10232
}, {
	"name": "演员",
	"value": 9693
}, {
	"name": "偷税",
	"value": 9207
}, {
	"name": "阴阳合同",
	"value": 9206
}, {
	"name": "律师",
	"value": 9052
}, {
	"name": "涉嫌",
	"value": 8951
}, {
	"name": "侵权",
	"value": 8913
}, {
	"name": "公开",
	"value": 8774
}, {
	"name": "偷税漏税",
	"value": 8719
}, {
	"name": "违法行为",
	"value": 8359
}, {
	"name": "片酬",
	"value": 8307
}, {
	"name": "机关",
	"value": 8071
}, {
	"name": "表态",
	"value": 8056
}, {
	"name": "真实",
	"value": 8033
}, {
	"name": "怒怼",
	"value": 7713
}, {
	"name": "认可",
	"value": 7708
}, {
	"name": "持续",
	"value": 7546
}, {
	"name": "涉密",
	"value": 7464
}, {
	"name": "侵犯",
	"value": 7457
}, {
	"name": "合约",
	"value": 7410
}, {
	"name": "合法权益",
	"value": 7366
}, {
	"name": "真实性",
	"value": 7248
}, {
	"name": "曝料",
	"value": 7229
}, {
	"name": "可的",
	"value": 7215
}, {
	"name": "发酵",
	"value": 7191
}, {
	"name": "天价",
	"value": 7072
}, {
	"name": "内幕",
	"value": 6761
}, {
	"name": "行业",
	"value": 6607
}, {
	"name": "电影",
	"value": 6560
}, {
	"name": "小崔",
	"value": 6430
}, {
	"name": "发声",
	"value": 6256
}, {
	"name": "舆论",
	"value": 6195
}, {
	"name": "沸沸扬扬",
	"value": 5987
}, {
	"name": "咋舌",
	"value": 5973
}, {
	"name": "采访",
	"value": 5941
}, {
	"name": "浮出水面",
	"value": 5820
}, {
	"name": "开拍",
	"value": 5805
}, {
	"name": "上映",
	"value": 5774
}, {
	"name": "抽屉",
	"value": 5705
}, {
	"name": "主角",
	"value": 5635
}, {
	"name": "焦点",
	"value": 5543
}, {
	"name": "引爆",
	"value": 5510
}, {
	"name": "下水",
	"value": 5471
}, {
	"name": "故事",
	"value": 5456
}, {
	"name": "连续",
	"value": 5376
}, {
	"name": "回应",
	"value": 5164
}, {
	"name": "娱乐圈",
	"value": 4990
}, {
	"name": "炮轰",
	"value": 4702
}, {
	"name": "否认",
	"value": 4520
}, {
	"name": "中国",
	"value": 4453
}, {
	"name": "手撕",
	"value": 4434
}, {
	"name": "监管部门",
	"value": 4324
}, {
	"name": "法律",
	"value": 4154
}, {
	"name": "大轰炸",
	"value": 3914
}, {
	"name": "详解",
	"value": 3886
}, {
	"name": "逃税",
	"value": 3858
}, {
	"name": "北京",
	"value": 3717
}, {
	"name": "日报",
	"value": 3355
}, {
	"name": "媒体",
	"value": 3044
}, {
	"name": "评论",
	"value": 2898
}, {
	"name": "后续",
	"value": 2712
}, {
	"name": "大腕",
	"value": 2709
}, {
	"name": "采访中",
	"value": 2442
}, {
	"name": "关系",
	"value": 2430
}, {
	"name": "新浪财经",
	"value": 2395
}, {
	"name": "接受",
	"value": 2280
}, {
	"name": "实话实说",
	"value": 2066
}, {
	"name": "声援",
	"value": 2030
}, {
	"name": "侮辱",
	"value": 1896
}, {
	"name": "保护",
	"value": 1832
}, {
	"name": "诽谤",
	"value": 1828
}, {
	"name": "精神",
	"value": 1805
}, {
	"name": "女人",
	"value": 1761
}, {
	"name": "自由",
	"value": 1713
}, {
	"name": "制裁",
	"value": 1710
}, {
	"name": "观点",
	"value": 1701
}, {
	"name": "演出",
	"value": 1685
}, {
	"name": "公众",
	"value": 1673
}, {
	"name": "主持人",
	"value": 1657
}, {
	"name": "讳言",
	"value": 1628
}, {
	"name": "澄清",
	"value": 1618
}, {
	"name": "狐狸",
	"value": 1583
}, {
	"name": "表达",
	"value": 1579
}, {
	"name": "徐帆",
	"value": 1579
}, {
	"name": "婊子",
	"value": 1529
}, {
	"name": "片子",
	"value": 1524
}, {
	"name": "疯了",
	"value": 1513
}, {
	"name": "言论自由",
	"value": 1492
}, {
	"name": "记者采访",
	"value": 1427
}, {
	"name": "爆料",
	"value": 1340
}, {
	"name": "女儿",
	"value": 1323
}, {
	"name": "收入",
	"value": 1306
}, {
	"name": "说事",
	"value": 1286
}, {
	"name": "严厉",
	"value": 1284
}, {
	"name": "小心",
	"value": 1249
}, {
	"name": "通知",
	"value": 1246
}, {
	"name": "伤害",
	"value": 1220
}, {
	"name": "贡献",
	"value": 1206
}, {
	"name": "疯子",
	"value": 1195
}, {
	"name": "4天",
	"value": 1188
}, {
	"name": "写照",
	"value": 1159
}, {
	"name": "纳税",
	"value": 1152
}, {
	"name": "叔叔",
	"value": 1151
}, {
	"name": "现金",
	"value": 1142
}, {
	"name": "策划",
	"value": 1115
}, {
	"name": "专家",
	"value": 1109
}, {
	"name": "科学家",
	"value": 1102
}, {
	"name": "200万",
	"value": 1102
}, {
	"name": "麻袋",
	"value": 1097
}, {
	"name": "监制",
	"value": 1087
}, {
	"name": "点评",
	"value": 1082
}, {
	"name": "离开",
	"value": 1059
}, {
	"name": "律管",
	"value": 1046
}, {
	"name": "工作者",
	"value": 1035
}, {
	"name": "作出",
	"value": 1009
}, {
	"name": "军人",
	"value": 998
}, {
	"name": "意见",
	"value": 995
}, {
	"name": "下发",
	"value": 990
}, {
	"name": "调侃",
	"value": 985
}, {
	"name": "猛料",
	"value": 981
}, {
	"name": "姐姐",
	"value": 978
}, {
	"name": "税务部门",
	"value": 965
}, {
	"name": "万金",
	"value": 964
}, {
	"name": "袁隆平",
	"value": 945
}, {
	"name": "税务局",
	"value": 927
}, {
	"name": "热度",
	"value": 923
}, {
	"name": "巨匠",
	"value": 913
}, {
	"name": "范爷",
	"value": 911
}, {
	"name": "核潜艇",
	"value": 910
}, {
	"name": "医学界",
	"value": 909
}, {
	"name": "谈笑",
	"value": 872
}, {
	"name": "顺序",
	"value": 810
}, {
	"name": "超出",
	"value": 799
}, {
	"name": "刘晓庆",
	"value": 778
}, {
	"name": "秘密",
	"value": 763
}, {
	"name": "艺人",
	"value": 718
}, {
	"name": "透露",
	"value": 693
}, {
	"name": "商业",
	"value": 686
}];

var CYCOLORS = ['#1E77CE', '#FAA67D', '#4581BC', '#46C7FA']; //词云颜色
var CYtextStyle = {
	normal: {
		color: function(v) {
			var color = CYCOLORS;
			var num = Math.floor(Math.random() * (4));
			return color[num];
		},
		shadowBlur: 20,
		shadowColor: 'rgba(0,0,0,0.1)'
	}
};
var data = {
	value: wcData,
	image: wyimg
}
//温馨提示：image 选取有严格要求不可过大；，否则firefox不兼容  iconfont上面的图标可以
var maskImage = new Image()
maskImage.src = data.image

maskImage.onload = function() {
	ciyunEchart.setOption({
		tooltip: {
			trigger: 'item',
			formatter: "{b}: {c} "
		},
		legend: {
			show: false
		},
		xAxis: [{
			type: 'category',
			show: false

		}],
		series: [{
			type: 'wordCloud',
			gridSize: 8,
			sizeRange: [10, 30],
			rotationRange: [0, 0],
			maskImage: maskImage,
			textStyle: CYtextStyle,
			left: 'center',
			top: 'center',
			width: '90%',
			height: '65%',
			data: data.value
		}]

	});
};

// 使用刚指定的配置项和数据显示图表。
//ciyunEchart.setOption(option);
var echarts5 = echarts.init(document.getElementById('echarts5'));
var option = {
	series: [{
		name: '媒体友好度',
		type: 'liquidFill',
		data: ['0.4'],
		radius: '55%',
		color: ['#1E77CE'],
		waveAnimation: true,
		animationDuration: 0,
		animationDurationUpdate: 0,
		outline: {
			borderDistance: 0,
			itemStyle: {
				borderWidth: 5,
				borderColor: '#1E77CE'
			}
		},
		backgroundStyle: {
			borderColor: '#1E77CE',
			borderWidth: 2,
			color: '#fff'
		},
		label: {
			normal: {
				position: ['50%', '52%'],
				align: 'center',
				formatter: function(v) {
					return(v.data * 100).toFixed(0) + '%' + '\n媒体友好度'
				},
				textStyle: {
					fontSize: 14,
					color: '#1E77CE'
				}
			}
		}
	}]
}
// 使用刚指定的配置项和数据显示图表。
echarts5.setOption(option, true);
var echarts6 = echarts.init(document.getElementById('echarts6'));
var colorList = ['#778DCC', '#8BE3EC', '#C7DEF5', '#1E77CE', '#7BBA89', '#5AC8E0']
var option = {
	tooltip: {
		trigger: 'item',
		formatter: '{b} : {d}%'
	},
	legend: {
		orient: 'vertical',
		top: 'center',
		right: '10',
		formatter: function(name) {
			let oa = option.series[0].data
			let num = oa[0].value + oa[1].value + oa[2].value + oa[3].value + oa[4].value + oa[5].value
			for(let i = 0; i < option.series[0].data.length; i++) {
				if(name === oa[i].name) {
					return name + '' + (oa[i].value / num * 100).toFixed(2) + '%'
				}
			}
		},
		textStyle: {
			fontSize: 12
		},
		data: ['愤怒', '喜悦', '中性', '悲伤', '恐惧', '惊奇']
	},
	series: [{
		name: '情绪占比',
		type: 'pie',
		radius: '55%',
		center: ['35%', '50%'],
		color: colorList,
		itemStyle: {
			emphasis: {
				shadowBlur: 10,
				shadowOffsetX: 0,
				shadowColor: 'rgba(0, 0, 0, 0.5)'
			}
		},
		label: {
			normal: {
				show: false
			}
		},
		data: [{
			"name": "愤怒",
			"value": 1800260
		}, {
			"name": "喜悦",
			"value": 1476625
		}, {
			"name": "中性",
			"value": 1273265
		}, {
			"name": "悲伤",
			"value": 700977
		}, {
			"name": "恐惧",
			"value": 452315
		}, {
			"name": "惊奇",
			"value": 35924
		}]
	}]

}
// 使用刚指定的配置项和数据显示图表。
echarts6.setOption(option, true);
var mapEcharts = echarts.init(document.getElementById('mapEcharts'));
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
var option = {
	tooltip: {
		trigger: 'item',
		formatter: '{b} : {c}'
	},
	visualMap: {
		min: 0,
		max: 5000,
		left: '0',
		bottom: '10',
		orient: 'vertical',
		text: ['高', '低'],
		calculable: true,
		inRange: {
			color: ['#D3E8FD', '#66A4E1', '#1E77CE']
		}
	},
	geo: {
		map: 'china',
		top: 'center',
		left: 'center',
		selectedMode: 'single',
		label: {
			normal: {
				textStyle: {
					color: '#000'
				},
				show: false
			},
		},
	},
	series: [{
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
					
					textStyle:{
						fontSize:10,
						color: '#000',
					}
				},

			},

		},
		{
			name: '全国',
			type: 'map',
			mapType: 'china',
			label: {
				normal: {
					show: false
				},
				emphasis: {
					show: true
				}
			},
			data: [{
				name: '上海',
				value: '2000'
			}, {
				name: '北京',
				value: '2000'
			}, {
				name: '广州',
				value: '2000'
			}, {
				name: '深圳',
				value: '2000'
			}, {
				name: '山东',
				value: '2000'
			}, {
				name: '青海',
				value: '2000'
			}, ]
		}, {
			name: '南海诸岛',
			type: 'map',
			mapType: 'china',
			label: {
				normal: {
					show: false
				},
				emphasis: {
					show: true
				}
			},
			data: [{
				name: '南海诸岛',
				itemStyle: {
					normal: {
						borderColor: '#959595',
						areaColor: '#efefef'
					}
				}
			}]
		}
	]
}
// 使用刚指定的配置项和数据显示图表。
mapEcharts.setOption(option, true);
var echarts8 = echarts.init(document.getElementById('echarts8'));
var option = {
	title: {
		text: '地域分布Top10',
		textStyle: {
			fontWeight: 'normal',
			fontSize: 16
		},
		left: '15',
		top: '5'
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: { // 坐标轴指示器，坐标轴触发有效
			type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
		},
		formatter: '{b} : {c}'
	},
	grid: {
		left: '3%',
		right: '12%',
		bottom: '10%',
		top: '10%',
		containLabel: true
	},
	xAxis: [{
		type: 'value',
		axisLine: {
			show: false
		},
		axisTick: {
			show: false
		},
		splitLine: {
			show: false

		},
		axisLabel: {
			show: false
		}
	}],
	yAxis: [{
		type: 'category',
		inverse: true,
		axisLine: {
			show: false
		},
		axisTick: {
			show: false
		},
		axisLabel: {
			show: true,
			textStyle: {
				color: '#888'
			}
		},
		splitLine: {
			show: false
		},
		boundaryGap: true,
		data: ['北京', '上海', '深圳', '北京', '上海', '深圳', '北京', '上海', '深圳', '北京']
	}],
	series: [{
		name: '微热点',
		type: 'bar',
		barWidth: '40%',
		itemStyle: {
			normal: {
				color: '#1E77CE'
			}
		},
		label: {
			normal: {
				formatter: function(param) {
					if(param.data) {
						return String(param.data).replace(/(\d)(?=(\d{3})+$)/g, '$1,')
					} else {
						return param
					}
				},
				position: 'right',
				show: true,
				textStyle: {
					color: '#444'
				}
			}
		},
		data: ['500', '400', '300', '200', '100', '90', '50', '40', '20', '10']
	}]
}
// 使用刚指定的配置项和数据显示图表。
echarts8.setOption(option, true);

setTimeout(function() {
	window.onresize = function() {
		echarts1.resize();
		echarts2.resize();
		//      echarts3.resize();
		ciyunEchart.resize();
		echarts5.resize();
		echarts6.resize();
		mapEcharts.resize();
		echarts8.resize();

	}
});