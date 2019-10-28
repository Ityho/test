//pie
var pieChart = echarts.init(document.getElementById('pieChart'));
var option = {
    legend: {
        top: "0%",
        left: "5%",
        itemHeight: 15,
        itemWidth: 15,
        data: ['正面指数 0.71', '负面指数 0.8239'],
        textStyle: {
            color: '#333'
        },

        selectedMode: true,
        orient: "vertical",

    },
    series: [{
            name: 'Line 1',
            type: 'pie',
            clockWise: true,
            radius: ['87%', '94%'],
            itemStyle: {
                normal: {
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    },
                }

            },
            hoverAnimation: false,

            data: [{
                    value: 60,
                    name: '正面指数 0.71',
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#3C9EFF'
                            }, {
                                offset: 1,
                                color: '#4687fb'
                            }])
                        }
                    }
                }, {
                    value: 40,
                    name: 'invisible',
                    itemStyle: {
                        normal: {
                            color: 'rgba(0,0,0,0)',
                            label: {
                                show: false
                            },
                            labelLine: {
                                show: false
                            }
                        },
                        emphasis: {
                            color: 'rgba(0,0,0,0)'
                        }
                    }
                }

            ]
        }, {
            name: 'Line 2',
            type: 'pie',
            clockWise: true,
            radius: ['70%', '78%'],
            itemStyle: {
                normal: {
                    label: {
                        show: false
                    },
                    labelLine: {
                        show: false
                    },

                }
            },
            hoverAnimation: false,

            data: [{
                value: 30,
                name: '负面指数 0.8239',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: '#fc9303'
                        }, {
                            offset: 1,
                            color: '#fb5602'
                        }])
                    }
                }
            }, {
                value: 70,
                name: 'invisible',
                itemStyle: {
                    normal: {
                        color: 'rgba(0,0,0,0)',
                        label: {
                            show: false
                        },
                        labelLine: {
                            show: false
                        }
                    },
                    emphasis: {
                        color: 'rgba(0,0,0,0)'
                    }
                }
            }]
        }

    ]
};
pieChart.setOption(option);
var bareChart = echarts.init(document.getElementById('bareChart'));
var option = {
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
		data: [],
		show: false,

	},
	grid: [{
		left: '18%',
		top: '2%',
		bottom: '2%',
		right: '1%'
	}, {
		left: '8%',
		top: '2%',
		bottom: '2%'
	}],
	xAxis: [{
		gridIndex: 0,
		type: 'value',
		nameTextStyle: {
			color: '#0086ce'
		},
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
		splitLine: {
			show: true
		},
		axisLabel: { //坐标轴刻度标签
			show: false,
			//rotate: 30,  //旋转角度
			textStyle: {
				color: '#888'
			}
		}

	}, {
		gridIndex: 1,
		type: 'value',
		show: false,

	}],
	yAxis: [{
		gridIndex: 0,
		type: 'category',
		show: false,
		data: []
	}, {
		gridIndex: 1,
		type: 'category',
		data: ['动词', '名词', '标点','时间词','机构团体','地名' ],
		inverse: true,
		position: 'left',
		offset:-10,
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
				fontSize: 12,
			}

		},
	}],
	series: [

		{
			name: '女',
			type: 'bar',
			z: 10,
			barCategoryGap: '20%',
			barWidth: 15,
			xAxisIndex: 0,
			yAxisIndex: 0,
			label: {
				normal: {
					show: true,
					position: 'right',
					textStyle: {
						color: '#000000',
					}

				},
			},
			itemStyle: {
			 normal: {
                    barBorderRadius: 30,
                    color: new echarts.graphic.LinearGradient(
                        1, 0, 0, 0, [{
                                offset: 0,
                                color: '#FD8C00'
                            },
                            {
                                offset: 0.5,
                                color: '#FD5C00'
                            },
                            {
                                offset: 1,
                                color: '#FB4503'
                            }
                        ]
                    )
                }

			},
			data: [200,100,30,40,300,50]
		},
		{
			name: '',
			type: 'bar',
			xAxisIndex: 0,
			yAxisIndex: 0,
			silent: false,
			barGap: '-100%',
			barWidth: 15,
			label: {
				normal: {
					show: false,
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
					color: '#F1F1F1',
					barBorderRadius: 50,
					borderWidth: 0,
				}

			},
			data: [500,500,500,500,500,500]
		}
	]
};
bareChart.setOption(option);

//graph



//相关度
var relevantChart = echarts.init(document.getElementById('relevantChart'));

// 指定图表的配置项和数据

var bg = {
	name: '相关背景',
	type: 'pie',
	avoidLabelOverlap: false,
	labelLine: {
		normal: {
			show: false
		}
	},
	data: [{
		value: 1
	}],
	animation: false
};

var dot = {
	name: '关联度',
	type: 'scatter',
	xAxisIndex: 0,
	yAxisIndex: 0,
	symbol: 'circle',
	symbolSize: 20,
	label: {
		normal: {
			show: true,
			textStyle: {
				color: '#555'
			},
			position: 'bottom',
			formatter: function(param) {
				return param.data[2];
			},
		},
	},
	itemStyle: {
		normal: {
			color: '#9bca63',
			opacity: 1

		},
	},

	data: [],
}

var dataBody = [{
		name: '弱相关',
		type: 'scatter',
		xAxisIndex: 0,
		yAxisIndex: 0,
		symbol: 'circle',
		symbolSize: 60,
		label: {
			normal: {
				show: true,
				textStyle: {
					fontSize: '12'
				},
				formatter: function(param) {
					return param.data[2];
				},
			},

		},

		itemStyle: {
			normal: {
				color: '#eaa269'
			}
		},
		data: [
			[50, 50, '手机', '#eaa269']
		],
		markLine: {}
	},
	$.extend(true, {}, bg, {
		radius: ['0%', '20%'],
		itemStyle: {
			normal: {
				color: 'rgba(231,145,107,0.3)',
			},
			emphasis: {
				color: 'rgba(231,145,107,0.3)',
			}
		}
	}),
	$.extend(true, {}, bg, {
		radius: ['20%', '60%'],
		itemStyle: {
			normal: {
				color: 'rgba(231,145,107,0.2)',
			},
			emphasis: {
				color: 'rgba(231,145,107,0.2)',
			}
		}
	}),
	$.extend(true, {}, bg, {
		radius: ['60%', '100%'],
		itemStyle: {
			normal: {
				color: 'rgba(231,145,107,0.1)',
			},
			emphasis: {
				color: 'rgba(231,145,107,0.1)',
			}
		}
	}),
	$.extend(true, {}, bg, {
		radius: ['100%', '150%'],
		itemStyle: {
			normal: {
				color: 'rgba(231,145,107,0.07)',
			},
			emphasis: {
				color: 'rgba(231,145,107,0.07)',
			}
		}
	}),
	$.extend(true, {}, bg, {
		radius: ['100%', '150%'],
		radius: ['150%', '200%'],
		itemStyle: {
			normal: {
				color: 'rgba(231,145,107,0.04)',
			},
			emphasis: {
				color: 'rgba(231,145,107,0.04)',
			}
		}
	})
]

var datalist = [
	/**
	 * x坐标 
	 * y坐标 
	 * name 标签名称
	 * symbolSize 点大小
	 * 趋势，1:上升 0:下降
	 */
	[14, 16, '苹果', 50, 1],
	[70, 50, '华为华为华为华为华为华为华为华为华为华为', 30, 1],
	[90, 50, '助手', 20, 1],
	[85, 90, '小米', 47, 1],
	[52, 83, '360', 10, 0],
	[72, 83, '定位', 20, 0],
	[47, 110, '下载', 30, 0]
]

var dataMap = datalist.map(function(item) {
	return $.extend(true, {}, dot, {
		symbolSize: item[3],
		itemStyle: {
			normal: {
				color: item[4] == 1 ? '#eaa269' : '#b9a7af',
				opacity: 1
			}
		},
		data: [
			item
		],
		zlevel: 2
	});
});

dataBody = dataMap.concat(dataBody)
var option = {
	backgroundColor: 'rgba(231,145,107,0.02)',
	tooltip: {
		trigger: 'item',
		backgroundColor: '#fff',
		textStyle: {
			color: '#999'
		},
		formatter: function(item) {
			if(item.data[2]) {
				return item.data[2];
			}
		}

	},
	xAxis: [{
		gridIndex: 0,
		type: 'value',
		show: false,
		min: 0,
		max: 100,
		nameLocation: 'middle',
		nameGap: 30

	}],
	yAxis: [{
		gridIndex: 0,
		min: 0,
		show: false,
		max: 100,
		nameLocation: 'middle',
		nameGap: 30,
	}],
	series: dataBody
};

// 使用刚指定的配置项和数据显示图表。
relevantChart.setOption(option);

//graph
var graphChart = echarts.init(document.getElementById('graphChart'));

var size = 50;
var size1 = 12;
var yy =45;
var yy1 = 15;
var option = {
    tooltip: {
        formatter: '{b}'
    },
    toolbox: {
        show: true,
      
    },
    animationDuration: 1000,
    animationEasingUpdate: 'quinticInOut',
    series: [{
        name: '知识体系',
        type: 'graph',
        layout: 'force',
        force: {
            repulsion: 20,
            gravity: 0.3,
            edgeLength: 5,
            layoutAnimation: true,
        },
        data: [{
            "name": "文本内容",
            x: 30,
            y: 30,
            "symbolSize": 70,
            "category": "文本内容",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#f18d00'
            }
         },

        }, {
            "name": "经管学部",
            x: 10,
            y: yy1,
            "symbolSize": size,
            "category": "经管学部",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },

        }, {
            "name": "商学院",
            x: 15,
            y: yy,
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 30,
            y: yy1,
            "name": "文化发展研究所",
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 35,
            y: yy,
            "name": "经济与管理学院",
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            "value": 1,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 40,
            y: yy1,
            "name": "雄安新区发展研究院",
            "symbolSize": size1,
            "category": "雄安新区发展研究院",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 45,
            y: yy,
            "name": "文法学部",
            "symbolSize": size,
            "category": "文法学部",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
            
        }, {
            x: 50,
            y: yy1,
            "name": "汉语国际教育专业",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 55,
            y: yy,
            "name": "文学院",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 60,
            y: yy1,
            "name": "政治与法律学院",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 65,
            y: 45,
            "name": "直属学院",
            "symbolSize": size,
            "category": "直属学院",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#009EF0'
                }
            },
            
        }, {
            x: 70,
            y: yy1,
            "name": "协同创新中心",
            "symbolSize": size,
            "category": "协同创新中心",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
            
        }, {
            x: 75,
            y: yy,
            "name": "新媒体研究院",
            "symbolSize": size1,
            "category": "协同创新中心",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
             itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
        }, {
            x: 10,
            y: yy1,
            "name": "传媒科学研究所",
            "symbolSize": size1,
            "category": "协同创新中心",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
             itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
        }],
        links: [{
            "source": "文本内容",
            "target": "经管学部"
        }, {
            "source": "文本内容",
            "target": "文法学部"
        }, {
            "source": "文本内容",
            "target": "直属学院"
        }, {
            "source": "文本内容",
            "target": "协同创新中心"
        }, {
            "source": "经管学部",
            "target": "商学院"
        }, {
            "source": "经管学部",
            "target": "经济与管理学院"
        }, {
            "source": "经管学部",
            "target": "文化发展研究所"
        }, {
            "source": "经管学部",
            "target": "雄安新区发展研究院"
        }, {
            "source": "文法学部",
            "target": "汉语国际教育专业"
        }, {
            "source": "文法学部",
            "target": "文学院"
        }, {
            "source": "文法学部",
            "target": "政治与法律学院"
        }, {
            "source": "协同创新中心",
            "target": "新媒体研究院"
        }, {
            "source": "协同创新中心",
            "target": "传媒科学研究所"
        }],
        categories: [{
            'name': '文本内容'
        }, {
            'name': '经管学部'
        }, {
            'name': '雄安新区发展研究院'
        }, {
            'name': '文法学部'
        }, {
            'name': '直属学院（机构）'
        }, {
            'name': '协同创新中心'
        }],
        roam: true,
        label: {
            normal: {
                show: true,
                position: 'inside',
                formatter: '{b}',
                fontSize: 12,
                fontStyle: '600',
            }
        },
        
        lineStyle: {
            normal: {
                width: 2,
                color: 'source',
                curveness: 0,
                type: "solid"
            }
        }
    }]
};
graphChart.setOption(option);

var graphChart2 = echarts.init(document.getElementById('graphChart2'));
var size = 50;
var size1 = 12;
var yy =45;
var yy1 = 15;
var option = {
    tooltip: {
        formatter: '{b}'
    },
    toolbox: {
        show: true,
    },
    animationDuration: 1000,
    animationEasingUpdate: 'quinticInOut',
    series: [{
        name: '知识体系',
        type: 'graph',
        layout: 'force',
        force: {
            repulsion: 20,
            gravity: 0.3,
            edgeLength: 5,
            layoutAnimation: true,
        },
        data: [{
            "name": "文本内容",
            x: 30,
            y: 30,
            "symbolSize": 70,
            "category": "文本内容",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#f18d00'
            }
         },

        }, {
            "name": "经管学部",
            x: 10,
            y: yy1,
            "symbolSize": size,
            "category": "经管学部",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },

        }, {
            "name": "商学院",
            x: 15,
            y: yy,
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 30,
            y: yy1,
            "name": "文化发展研究所",
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 35,
            y: yy,
            "name": "经济与管理学院",
            "symbolSize": size1,
            "category": "经管学部",
            "draggable": "true",
            "value": 1,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 40,
            y: yy1,
            "name": "雄安新区发展研究院",
            "symbolSize": size1,
            "category": "雄安新区发展研究院",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#05C7E8'
                }
            },
        }, {
            x: 45,
            y: yy,
            "name": "文法学部",
            "symbolSize": size,
            "category": "文法学部",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
            
        }, {
            x: 50,
            y: yy1,
            "name": "汉语国际教育专业",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 55,
            y: yy,
            "name": "文学院",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 60,
            y: yy1,
            "name": "政治与法律学院",
            "symbolSize": size1,
            "category": "文法学部",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
            itemStyle:{
            normal:{
                color:'#7460FE'
                }
            },
        }, {
            x: 65,
            y: 45,
            "name": "直属学院",
            "symbolSize": size,
            "category": "直属学院",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#009EF0'
                }
            },
            
        }, {
            x: 70,
            y: yy1,
            "name": "协同创新中心",
            "symbolSize": size,
            "category": "协同创新中心",
            "draggable": "true",
            itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
            
        }, {
            x: 75,
            y: yy,
            "name": "新媒体研究院",
            "symbolSize": size1,
            "category": "协同创新中心",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
             itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
        }, {
            x: 10,
            y: yy1,
            "name": "传媒科学研究所",
            "symbolSize": size1,
            "category": "协同创新中心",
            "draggable": "true",
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    formatter: '{b}',
                    fontSize: 12,
                    fontStyle: '600',
                }
            },
             itemStyle:{
            normal:{
                color:'#1863FC'
                }
            },
        }],
        links: [{
            "source": "文本内容",
            "target": "经管学部"
        }, {
            "source": "文本内容",
            "target": "文法学部"
        }, {
            "source": "文本内容",
            "target": "直属学院"
        }, {
            "source": "文本内容",
            "target": "协同创新中心"
        }, {
            "source": "经管学部",
            "target": "商学院"
        }, {
            "source": "经管学部",
            "target": "经济与管理学院"
        }, {
            "source": "经管学部",
            "target": "文化发展研究所"
        }, {
            "source": "经管学部",
            "target": "雄安新区发展研究院"
        }, {
            "source": "文法学部",
            "target": "汉语国际教育专业"
        }, {
            "source": "文法学部",
            "target": "文学院"
        }, {
            "source": "文法学部",
            "target": "政治与法律学院"
        }, {
            "source": "协同创新中心",
            "target": "新媒体研究院"
        }, {
            "source": "协同创新中心",
            "target": "传媒科学研究所"
        }],
        categories: [{
            'name': '文本内容'
        }, {
            'name': '经管学部'
        }, {
            'name': '雄安新区发展研究院'
        }, {
            'name': '文法学部'
        }, {
            'name': '直属学院（机构）'
        }, {
            'name': '协同创新中心'
        }],
        roam: true,
        label: {
            normal: {
                show: true,
                position: 'inside',
                formatter: '{b}',
                fontSize: 12,
                fontStyle: '600',
            }
        },
        
        lineStyle: {
            normal: {
                width: 2,
                color: 'source',
                curveness: 0,
                type: "solid"
            }
        }
    }]
};
graphChart2.setOption(option);


setTimeout(function() {
	window.onresize = function() {
		pieChart.resize();
		bareChart.resize();
		relevantChart.resize();
		graphChart.resize();
		graphChart2.resize();

	}

});