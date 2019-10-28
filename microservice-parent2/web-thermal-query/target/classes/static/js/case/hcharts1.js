var chart = Highcharts.chart('emotionpieEchart', {
	chart: {
		plotBackgroundColor: null,
		plotBorderWidth: null,
		plotShadow: false,
		spacing: [0, 0, 0, 0]
	},
	title: {
		floating: true,
		text: ''
	},
	credits: {
		enabled: false // 禁用版权信息
	},
	tooltip: {
		enabled: true,
		pointFormat: '{point.percentage:.1f}%</b>'
	},
	legend: {
		align: 'left',
		x: 80,
		y: 20,
		verticalAlign: 'top',
		layout: 'vertical',
	},
	plotOptions: {
		pie: {
			allowPointSelect: true,
			cursor: 'pointer',
			showInLegend: true,
			dataLabels: {
				enabled: true,
				format: '<b>{point.name}</b>: {point.percentage:.1f} %',
				style: {
					color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
				}
			},
			point: {
				events: {
					legendItemClick: function() {
						return false;
					}
				}
			},
		}
	},
	series: [{
		type: 'pie',
		innerSize: '70%',
		name: '',
		data: [{
				name: '喜悦',
				y: 45.0,
				url: 'http://bbs.hcharts.cn',
				color: '#f18d00'
			},
			{
				name: '悲伤',
				y: 26.8,
				color: '#0c7dc0'
			},
			{
				name: '中性',
				y: 12.8,
				color: '#9da7b5',
			},
			{
				name: '愤怒',
				y: 8.5,
				color: '#cf421e',
			},
			{
				name: '惊奇',
				y: 6.2,
				color: '#45b485',
			},
			{
				name: '恐惧',
				y: 0.7,
				color: '#2f3237',
			}
		]
	}]
}, function(c) {
	// 环形图圆心
	var centerY = c.series[0].center[1],
		titleHeight = parseInt(c.title.styles.fontSize);
	c.setTitle({
		y: centerY + titleHeight / 2
	});
	chart = c;
});

var informationChart = echarts.init(document.getElementById('informationChart'));

// 指定图表的配置项和数据
var option = {
	tooltip: {
		trigger: 'axis'
	},
	legend: {
		top: '0px',
		data: ['喜悦', '愤怒', '悲伤', '惊奇', '恐惧', '中性']
	},

	grid: {
		left: '2%',
		right: '2%',
		top: '18%',
		bottom: '15%',
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
		axisLabel: { //坐标轴刻度标签
			show: true,
			//rotate: 30,  //旋转角度
			textStyle: {
				color: '#888'
			}
		},
		boundaryGap: false,
		data: (function() {
			var now = new Date();
			var res = [];
			var len = 14;
			while(len--) {
				res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
				now = new Date(now - 2000);
			}
			return res;
		})()
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
			//rotate: 30,  //旋转角度
			textStyle: {
				color: '#888'
			}
		},
	}],
	series: [{
			name: '喜悦',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#F08C00',
					borderColor: '#F08C00',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#F08C00',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [50, 10, 221, 104, 450, 380, 610, 50, 10, 221, 104, 450, 380, 610]
		},
		{
			name: '愤怒',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#CF421E',
					borderColor: '#CF421E',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#CF421E',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [800, 482, 621, 404, 720, 510, 790, 300, 482, 621, 404, 720, 510, 790]
		}, {
			name: '悲伤',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#0E7DC0',
					borderColor: '#0E7DC0',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#0E7DC0',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [100, 100, 100, 100]
		}, {
			name: '惊奇',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#3FA579',
					borderColor: '#3FA579',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#3FA579',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [200, 200, 200, 200]
		}, {
			name: '恐惧',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#313131',
					borderColor: '#313131',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#313131',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [300, 300, 300, 300]
		}, {
			name: '中性',
			type: 'line',
			symbol: 'emptyCircle',
			symbolSize: 10,
			itemStyle: {
				normal: {
					color: '#6E7584',
					borderColor: '#6E7584',
					borderWidth: 2
				}
			},
			lineStyle: {
				normal: {
					color: '#6E7584',
					width: 2,
					type: 'solid'
				}
			},
			areaStyle: {
				normal: {
					color: 'rgba(0,0,0,0.5)',
					opacity: 0
				}
			},
			smooth: true,
			data: [400, 400, 400, 400]
		}

	]
};

// 使用刚指定的配置项和数据显示图表。
informationChart.setOption(option);