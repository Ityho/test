var myChart = [], echartsOpts = {
    bar1:{
        series: [{
            color: ["#0A5BDE", "#DCDCDC"],
            name: '访问来源',
            type: 'pie',
            radius: ['80%', '85%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: true,
                    position: 'center'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [{
                value:0,
                name: '75%',
                label: {
                    normal: {
                        formatter: function (param) {
                            return param.data.value
                        },
                        textStyle: {
                            fontSize: '20',
                            color: '#0A5BDE',
                            fontWeight: 'bold'
                        }
                    }
                },
            },
                {
                    value: 24.97,
                    name: '同期',
                    label: {
                        normal: {
                            show:false
                            // formatter: function (param) {
                            //     return param.data.name + '32'
                            // },
                            // textStyle: {
                            //     fontSize: '12',
                            //     color: '#0A5BDE',
                            //     fontWeight: 'bold'
                            // },
                            // padding: [60, 0, 0, 0]
                        }
                    },
                    itemStyle:{
                        normal:{
                            color:'#02A9ED'
                        }
                    }
                }
            ]
        }]
    },
    bar2:{
        series: [{
            color: ["#0A5BDE", "#DCDCDC"],
            name: '访问来源',
            type: 'pie',
            radius: ['80%', '85%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: true,
                    position: 'center'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data: [{
                value: '100',
                name: '变化',
                label: {
                    normal: {
                        formatter:function(param){
                            return param.data.value
                        },
                        textStyle: {
                            fontSize: '20',
                            color:'#02A9ED',
                            fontWeight: 'bold'
                        }
                    }
                },
                itemStyle:{
                    normal:{
                        color:'#02A9ED'
                    }
                }
            }
            ]

        }]
    },
    bar3:{
        tooltip: {
            trigger: 'item',
            formatter: "{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            textStyle: {
                color: '#fff',
                fontSize: 12,
            },
            left: '0',
            top: '15%',
            data:""
        },
        series: [{
            type: 'pie',
            radius: ['57%', '75%'],
            center: ['75%', '50%'],
            clockwise: false,
            z: 10,
            itemStyle: {
                normal: {
                    color: function(params) {
                        // build a color map as your need.
                        let colorList = ["#f6d54a", "#f69846", "#44aff0", "#45dbf7", "#f845f1", "#ad46f3", "#5045f6", "#4777f5","#6BE990", "#F1627A", "#44aff0", "#45dbf7", "#FF3D3D", "#ad46f3", "#5045f6", "#4777f5"];
                        return colorList[params.dataIndex]
                    },
                    shadowBlur: 20,
                    shadowColor: 'rgba(0, 0, 0, 0.3)'
                }
            },
            label: {
                normal: {
                    show:false
                }
            },
            data:""
        }]
    },
    bar4_1:{
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {d}% <br/> {c}"
        },
        legend: {
            show:false,
        },
        series: [{
            type: 'pie',
            radius: ['30%', '40%'],
            center: ['50%', '50%'],
            color: ['#53C1C3', '#0A5ADE'],
            data: [{
                value: 0,
                name: '敏感'
            },
                {
                    value: 0,
                    name: '非敏感'
                }
            ],
            labelLine: {
                normal: {
                    show: true,
                    length: 8,
                    length2: 8,
                    lineStyle: {
                        width: 2
                    }
                }
            },
            label: {
                normal: {
                    formatter:'{b}\n{d}%\n({c})',
                    textStyle:{
                        fontSize:12
                    }
                }
            }
        }]
    },
    bar4_2:{
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line',
                lineStyle: {
                    opacity: 0
                }
            },
            formatter: function(params) {
                return params[0].name + params[0].value
            }
        },
        legend: {
            data: []
        },
        grid: {
            top:0,
            left: '14%',
            right: '18%',
            bottom: 0,
            containLabel: true
        },
        xAxis: {
            type: 'value',
            show:false,
        },
        yAxis: {
            type: 'category',
            data: ['政务', '新闻', '报刊'],
            axisLabel: {
                color: '#fff',
                fontWeight: '50'

            },
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
        },
        series: [{
            type: 'bar',
            barCategoryGap: '20%',
            barWidth: '30%',
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    color: '#fff'
                }
            },
            itemStyle: {
                normal: {
                    color: '#EDCC32',
                }

            },
            data: [22, 33, 44]
        }]

    },
    bar4_1_1:{
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {d}% <br/> {c}"
        },
        legend: {
            show:false,
        },
        series: [{
            type: 'pie',
            radius: ['30%', '40%'],
            center: ['50%', '50%'],
            color: ['#53C1C3', '#0A5ADE'],
            data: [{
                value: 0,
                name: '敏感'
            },
                {
                    value: 0,
                    name: '非敏感'
                }
            ],
            labelLine: {
                normal: {
                    show: true,
                    length: 8,
                    length2: 8,
                    lineStyle: {
                        width: 2
                    }
                }
            },
            label: {
                normal: {
                    formatter:'{b}\n{d}%\n({c})',
                    textStyle:{
                        fontSize:12
                    }
                }
            }
        }]
    },
    bar4_2_1:{
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line',
                lineStyle: {
                    opacity: 0
                }
            },
            formatter: function(params) {
                return params[0].name + params[0].value
            }
        },
        legend: {
            data: []
        },
        grid: {
            top:0,
            left: '14%',
            right: '18%',
            bottom: 0,
            containLabel: true
        },
        xAxis: {
            type: 'value',
            show:false,
        },
        yAxis: {
            type: 'category',
            data: ['政务', '新闻', '报刊'],
            axisLabel: {
                color: '#fff',
                fontWeight: '50'

            },
            axisLine: {
                show: false
            },
            axisTick: {
                show: false
            },
        },
        series: [{
            type: 'bar',
            barCategoryGap: '20%',
            barWidth: '30%',
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    color: '#fff'
                }
            },
            itemStyle: {
                normal: {
                    color: '#EDCC32',
                }

            },
            data: [22, 33, 44]
        }]
    },
    line : {
        tooltip : {
            trigger : 'axis'
        },
        legend : {
            show:false
        },
        grid : {
            left : '3%',
            right : '5%',
            top : '20%',
            bottom : '8%',
            containLabel : true
        },
        xAxis : [ {
            type : 'category',
            axisLine : { // 坐标轴轴线 默认 true,
                show : false
            },
            axisTick : { // 坐标轴刻度
                show : false,
                lineStyle : {
                    color : '#9AA1BD',
                    width : 1,
                    type : 'solid'
                }
            },
            axisLabel : { // 坐标轴刻度标签
                show : true,
                // rotate: 30, //旋转角度
                color : '#9AA1BD'
            },
            boundaryGap : false,
            data : [ ]
        } ],
        yAxis : [ {
            type : 'value',
            axisLine : { // 坐标轴轴线 默认 true,
                show : false
            },
            splitLine : {
                show : true,
                lineStyle : {
                    color : '#1C2654',
                    width : 1,
                    type : 'solid'
                }
            },
            axisTick : { // 坐标轴刻度
                show : false
            },
            axisLabel : { // 坐标轴刻度标签
                show : true,
                color : '#9AA1BD'
            },
        } ],
        series : [ {
            name : '24小时热度趋势',
            type : 'line',
            symbol : 'circle', // 拐点样式
            data : [ ],
            markPoint : {
                data : [ {
                    type : 'max',
                    name : '最大值',
                    symbol : 'rect',
                    symbolOffset : [ 0, '-100%' ],
                    symbolSize : [ 60, 22 ],
                    label : {
                        normal : {
                            show : true,
                            //position : 'insideTop',
                            formatter : '{c}',
                            fontSize : '12',
                            fontWeight : 'bold',
                            color : '#fff',
                        }
                    }
                }, {
                    symbol : 'circle',
                    type : 'max',
                    symbolSize : [ 10, 10 ],
                    itemStyle : {
                        normal : {
                            color : "#FAB421",
                            borderColor : 'rgba(241,141,0, .3)',
                            borderWidth : 10,
                            shadowColor : '#ffc72b',
                            shadowBlur : 30
                        }
                    },
                    label : {
                        normal : {
                            show : false
                        }
                    }
                } ]
            },
            itemStyle : {
                normal : {
                    color : '#FAB421'
                }
            },
            lineStyle : {
                normal : {
                    color : '#FAB421',
                    width : 1.5,
                    type : 'solid'
                }
            },
            smooth : false,
            markLine : {
                silent : true,
                data : [ {
                    type : 'average',
                    name : '24小时热度均值',
                } ],
                precision : 0,
                label : {
                    normal : {
                        position:"end",
                        show:true,
                        color:'#000',
                        backgroundColor:'#FAB421',
                        padding:[2,2]

                    }
                },
                lineStyle : {
                    normal : {
                        color : '#FF9B01',
                        width : 1.5,
                        formatter : function(param) {
                            return (Math.floor(param.value * 10000) / 100);
                        }
                    }
                }
            },
        }, ]
    },

    pie : {
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {d}% <br/> {c}"
		},
		legend : {
			show : false,
		},
		series : [ {
			type : 'pie',
			radius : [ '50%', '65%' ],
			center : [ '50%', '50%' ],
			color : [ '#53C1C3', '#0782FF' ],
			data : [ {
				value : 1,
				name : '敏感'
			}, {
				value : 1,
				name : '非敏感'
			} ],
			labelLine : {
				normal : {
					show : true,
					length : 8,
					length2 : 8,
					lineStyle : {
						width : 2
					}
				}
			},
			label : {
				normal : {
					formatter : '{b}\n{d}%\n({c})',
                    fontSize : 12
				}
			}
		} ]
	},
	bar : {
		grid : {
			top : 35,
			bottom : 15,
			containLabel : true
		},
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line',
                lineStyle: {
                    opacity: 0
                }
            }
        },
        xAxis : {
			type : 'category',
			data : [ '新闻', '政务', '报刊' ],
			axisLine : {
				show : false
			},
			axisTick : {
				show : false,
			},
			axisLabel : {
				show : true,
				interval : 0,
				color : '#A1A5CD',
				fontSize : 12,
			}
		},
		yAxis : [ {
			name : '',
			type : 'value',
			nameTextStyle : {
				fontSize : 8,
				color : '#fff'
			},
			axisLine : {
				show : false
			},
			axisTick : {
				show : false
			},
			axisLabel : {
				show : true,
				color : '#A1A5CD',
				fontSize : 12,
			},
			splitLine : {
				show : false,
			},
		} ],
		legend : {
			show : false,
		},

		series : [ {
			name : '',
			type : 'bar',
			barWidth : 20,
            label:{
                normal:{
                    show:true,
                    position:'top',
                    color:'#ffffff'
                }
            },
            itemStyle : {
				normal : {
					color : {
						type : 'linear',
						x : 0,
						y : 0,
						x2 : 0,
						y2 : 1,
						"colorStops" : [ {
							offset : 0,
							color : '#1A99EE',
						}, {
							offset : 1,
							color : '#0068D3',
						}, ]
					}
				},
			},
			data : [ 0, 0, 0 ],
		} ],
	}
};

// 刷新图表数据
refresh = function($id, opt) {
	if (!opt.noDataText) {
		opt.noDataText = "暂无数据";
	}
	opt.noDataLoadingechartsOpts = {
		text : opt.noDataText,
		effect : 'bubble',
		textStyle : {
			fontSize : 16
		}
	};
	myChart[$id.prop("id")].setOption(opt, true);

},
// 设置图表数据
setEchartsOpion = function(opts) {
	var type = opts.$id.data("type");
        myChart[opts.$id.prop("id")] = echarts
            .init(opts.$id[0], "macarons");
	if (opts.event) {
		myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK,
				opts.event);
	}
	// 深度拷贝
	refresh(opts.$id, $.extend(true, {}, echartsOpts[type], opts.opt));
};