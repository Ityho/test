var yibiaopan = echarts.init(document.getElementById('um-dashboard-echart'));
var numbers = {
	"max_achievement": 100,
	"this_achievement": 66.98
};
var axislineColor = new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
		offset: 0,
		color: '#fad202'
	},
	{
		offset: 1,
		color: '#fef901'
	}
]);
var option = {
//	backgroundColor: "rgba(0,0,0,0)",
	series: [{
			name: '刻度',
			type: 'gauge',
			radius: '74%',
			min: 0,
			max: 100,
			splitNumber: 10, //刻度数量
			startAngle: 200,
			endAngle: -20,
			axisLine: {
				show: true,
				lineStyle: {
					width: 1,
					color: [
						[1, '#877d83']
					]
				}
			}, //仪表盘轴线
			axisLabel: {
				show: true,
				color: '#877d83',
				distance: 30
			}, //刻度标签。
			axisTick: {
				show: false,
				lineStyle: {
					color: '#877d83',
					width: 1
				},
				length: -8
			}, //刻度样式
			splitLine: {
				show: false,
				length: -20,
				lineStyle: {
					color: '#877d83'
				}
			}, //分隔线样式
			detail: {
				show: false
			},
			pointer: {
				show: false
			}
		},
		{

			min: 0,
			max: numbers.max_achievement,
			type: 'gauge',
			radius: '91%',
			center: ['50%', '50%'],
			splitNumber: 0, //刻度数量
			startAngle: 200,
			endAngle: -20,
			axisLine: {
				show: true,
				lineStyle: {
					width: 15,
					color: [
						[numbers.this_achievement / numbers.max_achievement, axislineColor],
						[
							1, 'rgba(0,0,0,0)'
						]
					],
					shadowColor: '#9a4110', //默认透明
					shadowOffsetX: 0,
					shadowOffsetY: 0,
					shadowBlur: 20,
					opacity: 1,
				}
			},
			splitLine: {
				show: false,
			},
			axisLabel: {
				show: false
			},
			axisTick: {
				show: false
			},
			pointer: {
				show: false
			},
			title: {
				show: false,
				offsetCenter: [0, '-20%'], // x, y，单位px
				textStyle: {
					color: '#ddd',
					fontSize: 10
				}
			},
			//仪表盘详情，用于显示数据。
			detail: {
				show: false,
				offsetCenter: [0, 0],
				color: '#ddd',
				formatter: function(params) {
					return params
				},
				textStyle: {
					fontSize: 10
				}
			},
			data: [{
				name: "当前用户总数",
				value: numbers.this_achievement,
			}]
		}
	]
};
yibiaopan.setOption(option);
setTimeout(function() {
	window.onresize = function() {
		yibiaopan.resize();
	}
});