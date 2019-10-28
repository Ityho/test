var myChart = [], echartsOpts = {
	line : { // 热度指数趋势折线图
		animation : false,
		legend:{data:[],top:20},
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'line',
				lineStyle : {
					color : '#f18d01',
					width : 2
				}
			},
			formatter : function(params) {
				v = params[0].name;
				for (var i = 0, l = params.length; i < l; i++) {
					v += '<br/>' + params[i].seriesName + ' : '
							+ params[i].value;
				}
				return v;
			}
		},
		grid : {
			left : '10%',
			right : '5%',
			top : '15%'
		},
		xAxis : [ {
			type : 'category',// category|time
			boundaryGap : false,
			data : [],
			axisLine : {// 坐标轴轴线 默认 true,
				show : false
			},
			axisTick : {// 坐标轴刻度
				show : false,
				lineStyle : {
					color : '#888',
					width : 1,
					type : 'solid'
				}
			},
			axisLabel : {
				textStyle : {
					decoration : 'none',
					fontFamily : 'Microsoft YaHei',
					fontSize : 12,
				},
				formatter : function(v) {
					return v.substring(5, v.length);
				}
			},
		} ],
		yAxis : [ {
			type : 'value',
			axisLine : {// 坐标轴轴线 默认 true,
				show : false
			},
			axisTick : {// 坐标轴刻度
				show : false
			},
			axisLabel : {
				textStyle : {
					color : '#888'
				},
				formatter : function(v) {
					if (v >= 1000) {
						return (v / 1000) + "k";
					} else {
						return v;
					}
				}
			},
		} ],
		calculable : false,
		series : [ {
			type : 'line',
			symbol : 'emptyCircle',
			symbolSize : 8,
			itemStyle : {
				normal : {
					color : '#f18d01'
				}
			},
			lineStyle : {
				normal : {
					color : '#f18d01',
					width : 1.5,
					type : 'solid'
				}
			},
			areaStyle : {
				normal : {
					color : new echarts.graphic.LinearGradient(0, 0, 0, 1, [ {
						offset : 0,
						color : 'rgba(241, 141, 1, 0.5)'
					}, {
						offset : 1,
						color : 'rgba(241, 141, 1, 0)'
					} ], false),
					shadowColor : 'rgba(0, 0, 0, 0.1)',
					shadowBlur : 10
				}
			},
			data : []
		} ]
	},
	line2:{
		tooltip: {
			trigger: 'axis'
		},
		toolbox: {
			orient: 'vertical',
		},
/*		legend: {
			top: '0px',
			data: ['']
		},*/
		grid: {
			left: '5%',
			right: '10%',
			top: '20%',
			bottom: '12%',
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
				},
				formatter : function(v) {
					return v.substring(5, v.length);
				}
			},
			boundaryGap: false,
			data: []
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
					color: '#888'
				}
			},
		}],

		series: [{
				name: '',
				type: 'line',
				smooth: true, //这句就是让曲线变平滑的
				symbol: 'none', //拐点样式
				markPoint: {
	              data: [{
	                  type: 'max',
	                  name: '最大值',
	                  symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
	                  symbolOffset: [0, '-100%'],
	                  symbolSize: [50, 25],
	                   label: {
	                    normal: {
	                        show: true,
	                        position:'insideTop',
	                        textStyle: {
	                            fontSize: '12',
	                            fontWeight: 'bold',
	                            color: '#fff',
	                        }
	                    }
	                }
	              }, {
	                symbol:'circle',
	                type: 'max',
	                symbolSize: [10, 10],
	                itemStyle: {
	                    normal: {
	                        color: "#f18d00",
	                        borderColor:'rgba(241,141,0, .3)',//rgba(255, 199, 43, .3)
	                        borderWidth:10,
	                        shadowColor:'#ffc72b',
	                        shadowBlur:30
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
						color: '#f18d01'
					}
				},
				lineStyle: {
					normal: {
						color: '#f18d01',
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
				smooth: false,
				data: [],
				markLine : {
				  symbol : 'none',
				  itemStyle : {
				    normal : {
				      color:'#1e90ff',
				      label : {
				        show:true
				      }
				    }
				  },
				  data : [{type : 'average', name: '平均值'}]
				}
			}
			
		]
	},
	hotline:{
		tooltip: {
			trigger: 'axis'
		},
		toolbox: {
			orient: 'vertical',
		},
/*		legend: {
			top: '0px',
			data: ['']
		},*/
		grid: {
			left: '5%',
			right: '15%',
			top: '15%',
			bottom: '8%',
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
				},
				formatter : function(v) {
					return v.substring(5, v.length);
				}
			},
			boundaryGap: false,
			data: []
		}],
		yAxis: [{
			type: 'value',
			axisLine: { //坐标轴轴线 默认 true,
				show: false
			},
			splitLine:{
	            show: true,
	            lineStyle:{
	                color: 'rgba(240, 239, 239, 0.8)',
	                width: 1,
	                type: 'solid'
	            }
	        },
			axisTick: { //坐标轴刻度
				show: false
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				textStyle: {
					color: '#888'
				}
			},
		}],

		series: [{
				name: '',
				type: 'line',
				smooth: true, //这句就是让曲线变平滑的
				symbol: 'none', //拐点样式
				markPoint: {
	              data: [{
	                  type: 'max',
	                  name: '最大值',
	                  symbol: 'images://https://www.w3.org/TR/SVG/images/paths/triangle01.svg',
	                  symbolOffset: [0, '-100%'],
	                  symbolSize: [85, 25],
	                   label: {
	                    normal: {
	                        show: true,
	                        position:'insideTop',
	                        formatter: '热度峰值: {c}',
	                        textStyle: {
	                            fontSize: '11',
	                            fontWeight: 'bold',
	                            color: '#fff',
	                        }
	                    }
	                }
	              }, {
	                symbol:'circle',
	                type: 'max',
	                symbolSize: [10, 10],
	                itemStyle: {
	                    normal: {
	                        color: "#f18d00",
	                        borderColor:'rgba(241,141,0, .3)',//rgba(255, 199, 43, .3)
	                        borderWidth:10,
	                        shadowColor:'#ffc72b',
	                        shadowBlur:30
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
						color: '#f18d01'
					}
				},
				lineStyle: {
					normal: {
						color: '#f18d01',
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
				smooth: false,
				data: [],
				markLine : {
					silent: true,
				  symbol : 'none',
				  precision: 2,
				  label: {
	                    normal: {
	                        position:"end",
	                        formatter: '热度均值\n{c}',
	                        textStyle: {
	                            fontSize: '11'
	                        }
	                        
	                    }
	                },
	                lineStyle: {
	                    normal: {
	                        color: '#FF9B01',
	                        width:1.5,
	                    }
	                },
//				  itemStyle : {
//				    normal : {
//				      color:'#1e90ff',
//				      label : {
//				        show:true
//				      }
//				    }
//				  },
				  data : [{type : 'average', name: '平均值'}]
				}
			}
			
		]
	}
	,
	wordCloud : {// 字符词云
		tooltip : {},
		maskImage : null,
		series : [ {
			type : 'wordCloud',
			shape : 'circle',
			width : '100%',
			height : '100%',
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
	emap:{
		animation : false,
        title : {
            text : '',
            subtext : '',
            x : 'center',
            y : 'top',
        },
        tooltip : {
            trigger : 'item',
            enterable:true,
            textStyle : {
                fontSize : 12
            }
        },
//        toolbox: {
//            show : true,
//            orient:'vertical',
//            y:30,
//            x:'right',
//            feature : {
//                mark : {show: false},
//                dataView : {
//                    show: false,
//                    readOnly: false,
//                    lang: ['数据视图', '关闭', '刷新']
//                },
//                restore : {show: true},
//                saveAsImage : {
//                    show: true,
//                    name:""
//                }
//            }
//        },
        dataRange: {
            min: 0,
            max: 10000,
            calculable : false,
            text:['高','低'],
            color: ['#d44e24','#f29300','#f3d647'],
            orient:[]
        },
        series : [ {
            name : '数量',
            type : 'map',
            mapType : 'china',
            selectedMode : 'single',//single|multiple
            roam:false,
            itemStyle : {
                normal : {
                    label : {
                        show : true
                    },
                    borderColor:'#FFF'
                },
                emphasis : {label : {show : true} }
            },
            data :[]
        }]
	}
	,
	map : {
		animation : false,
		left : 'center',
		top : 'center',
		title : {
			text : '',
			subtext : '',
		},
		tooltip : {
			trigger : 'item',
			enterable : true,
			textStyle : {
				fontSize : 12
			}
		},
		visualMap : {
			type : 'continuous',
			min : 0,
			max : 10000,
			itemHeight : 100,
			left : 'left',
			top : 'bottom',
			text : [ '高', '低' ], // 文本，默认为数值文本
			calculable : true,
			inRange : {
				color : [ '#f3d647', '#f29300', '#d44e24' ]
			}
		},
		series : [ {
			type : 'map',
			map : 'china',
			roam : false,
			selectedMode : 'single',// single|multiple
			label : {
				normal : {
					show : true
				},
				emphasis : {
					show : true
				}
			},
			itemStyle : {
				normal : {
					label : {
						show : true
					},
					borderColor : '#FFF'
				},
				emphasis : {
					label : {
						show : true
					}
				}
			},
			data : []
		} ]
	},
	map1 : {
		series : [ {
			name : 'Map',
			type : 'map',
			map : 'china',
			mapLocation : {
				top : 'center',
				left : 'center'
			},
			label : {
				normal : {
					show : true
				},
				emphasis : {
					show : true,
					textStyle : {
						color : '#fff'
					}
				}
			},
			itemStyle : {
				normal : {
					borderWidth : 2,
					borderColor : '#d5d5d5',
					color : '#ccc',

				},
				emphasis : { // 也是选中样式
					borderWidth : 2,
					borderColor : '#d5d5d5',
					color : '#92D0FD',
				}
			},
			data : [ {
				name : '江西',
				value : Math.round(Math.random() * 1000),
				label : {
					normal : {
						show : true,
						textStyle : {
							color : '#fff',
							fontSize : 15
						}
					},
					emphasis : {
						show : false,
						textStyle : {
							color : '#92D0FD'
						}
					}
				},
				itemStyle : {
					normal : {
						color : '#92D0FD'
					},
					emphasis : { // 也是选中样式
						borderWidth : 1,
						borderColor : '#d5d5d5',
						color : '#92D0FD'
					}
				}
			} ]
		} ]
	},
	bar : {
		animation : false,
		color:['#39ae7d','#fff','#A4D720'],
		tooltip : { 
			trigger : 'axis',
		},
		xAxis : [ {
			type : 'value',
			data : [],
			axisLine : {
				onZero : false,
				show : false
			},
			axisLabel : {
				formatter:function(v){
                    if(v>=1000){
                        return (v/1000)+"k";
                    }else{
                        return v;
                    }
                },
				textStyle : {
					decoration : 'none',
					fontFamily : 'Microsoft YaHei',
					fontSize : 12,
				}
			},
			axisTick : {
				show : false
			},
			splitArea : {
				show : false
			},
			splitLine : {
				show : false
			}
		} ],
		grid : {
			y2 : 80,
			x : 55,
			x2 : 30
		},
		yAxis : [ {
			type : 'category',
			data : [],
			axisLine : {
				onZero : false,
				show : false
			},
			splitLine : {
				show : false
			},

			splitArea : {
				show : true,
				areaStyle : {
					color : [ '#FFF', '#F7F7F7' ]
				}
			},
			axisLabel : {
				textStyle : {
					decoration : 'none',
					fontFamily : 'Microsoft YaHei',
					fontSize : 12,
				},
				formatter : function(v) {
					if (v >= 1000) {
						return (v / 1000) + "k";
					} else {
						return v;
					}
				}
			},
			axisPointer : {
				show : true,
				lineStyle : {
					color : '#008acd',
					width : 2
				}
			}
		} ],
		series : [ {
			name : '数量',
			type : 'bar',
			itemStyle : {
				normal : {
					color : function(d) {// 颜色判断 这里用到两种 你可以设置多种
						if (d.dataIndex % 2 === 0) {
							return '#b9a7af';
						} else {
							return '#f1b192';
						}
					}
				}
			},
			data : [],
		} ]
	},
	graph : {
		symbolSize : 10, // 关系图节点大小
		tooltip : {
			trigger : 'item',
			formatter : function(params) {
				if (params.indicator2) { // is edge
					return params.indicator2 + ' ' + params.name + ' '
							+ params.indicator;
				} else { // is node
					return params.name
				}
			}
		},
		legend : {
			x : 'left',
			// textStyle:{
			// color:'#39ae7d'
			// },
			data : [ {
				name : null,
				icon : 'rect'
			} ]
		},
		animationDurationUpdate : 1500,
		animationEasingUpdate : 'quinticInOut',
		series : [ {
			name : '',
			type : 'graph',
			layout : 'circular',// 和弦图
			circular : {
				rotateLabel : true
			// 启用旋转标签
			},
			// draggable:true,//是否可拖拽
			roam : true,
			label : {
				normal : {
					show : true,
					position : 'right',
					formatter : '{b}'
				}
			},
			lineStyle : {
				normal : {
					color : 'source',
					curveness : 0.3
				// 边的曲度，支持从 0 到 1 的值，值越大曲度越大。
				}
			},
			// 使用 nodes links 表达和弦图
			data : [],
			links : []
		} ]
	},
	gauge : {
		title : {
			x : "center",
			bottom : '20%',
			text : '热度值',
			textStyle : {
				fontSize : 14,
				fontStyle : 'normal',
				color : '#333'
			}
		},
		tooltip : {
			formatter : "{a} <br/>{b} : {c}%"
		},
		series : [
				{
					name : '热度值',
					type : 'gauge',
					radius : '69%',
					splitLine : {
						show : true,
						length : 20,
						splitNumber : 10,
						lineStyle : {
							width : 2,
							color : '#f1730b'
						}
					},
					axisTick : {
						show : true,
						length : 12,
						splitNumber : 10,
						lineStyle : {
							width : 2,
							color : '#fff'
						}
					},
					axisLine : {
						width : 20,
						show : true,
						lineStyle : {
							width : 0,
							shadowBlur : 0
						}
					},
					axisLabel : {
						textStyle : {
							color : '#ada9a8'
						}
					},
					pointer : {
						length : '50%',
						width : 35
					},
					itemStyle : {
						normal : {
							color : '#e0521e'
						}
					},
					detail : {
						show : true,
						formatter : '{value}',
						offsetCenter : [ 0, 0 ],
						width : 50,
						textStyle : {
							color : '#fff',
							fontSize : 30
						},
					},
					data : [ {
						value : 50
					} ],
					silent : true,
					z : 3
				},
				{
					name : '指针',
					type : 'gauge',
					radius : '26%',
					min : 0,
					max : 360,
					startAngle : 90,
					endAngle : -269.9999,
					splitLine : {
						show : false
					},
					axisTick : {
						show : false
					},
					axisLine : {
						show : true,
						lineStyle : {
							color : [ [ 0, '#e0521e' ], [ 1, '#e0521e' ] ],
							width : 100
						}
					},
					axisLabel : {
						show : false
					},
					pointer : {
						show : false
					},
					itemStyle : {
						normal : {
							color : '#e0521e'
						}
					},
					detail : {
						show : true,
						formatter : '{value}',
						offsetCenter : [ 0, 0 ],
						width : 50,
						textStyle : {
							color : '#fff',
							fontSize : 30
						},
					},
					data : [ {
						value : 50
					} ],
					z : 2
				},
				{
					tooltip : {
						show : false
					},
					name : '背景颜色',
					type : 'pie',
					radius : [ '63%', '85%' ],
					center : [ '50%', '50%' ],
					hoverAnimation : false,
					startAngle : 225,
					labelLine : {
						normal : {
							show : false
						}
					},
					label : {
						normal : {
							position : 'center'
						}
					},
					data : [
							{
								value : 75,
								itemStyle : {
									"normal" : {
										"color" : new echarts.graphic.LinearGradient(
												1, 0, 0, 0, [ {
													"offset" : 0,
													"color" : '#f1611a'
												}, {
													"offset" : 1,
													"color" : '#f3a43c'
												} ]),
									}
								},
							}, {
								value : 25,
								itemStyle : {
									"normal" : {
										color : '#fff'
									}
								}
							} ],
					z : 0
				} ]
	},
	funnel : {
		color : [ '#f18d00', '#faac5f', '#eeb587', '#f4c1a8', '#cab6be' ],
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c}%"
		},
		legend : {
			orient : 'vertical',
			itemGap : 20,
			itemWidth : 15,
			itemHeight : 10,
			left : '3%',
			data : [ {
				name : '微博',
				icon : 'rect'
			}, {
				name : '客户端',
				icon : 'rect'
			}, {
				name : '微信',
				icon : 'rect'
			}, {
				name : '论坛',
				icon : 'rect'
			}, {
				name : '网站',
				icon : 'rect'
			} ]
		},
		calculable : true,
		series : [ {
			name : '媒体来源',
			type : 'funnel',
			left : '25%',
			width : '50%',
			min : 0,
			max : 100,
			minSize : '0%',
			maxSize : '100%',
			sort: 'ascending',
			label : {
				normal : {
					formatter : '{b}'
				},
				emphasis : {
					position : 'inside',
					formatter : '{b}媒体来源: {c}%'
				}
			},
			labelLine : {
				normal : {
					show : false
				}
			},
			itemStyle : {
				normal : {
					borderWidth : 0,
					opacity : 0.7
				}
			},
			data : [ {
				value : 100,
				name : '微博'
			}, {
				value : 80,
				name : '客户端'
			}, {
				value : 60,
				name : '微信'
			}, {
				value : 40,
				name : '论坛'
			}, {
				value : 20,
				name : '网站'
			}

			],
			tooltip : {
				show : false
			}
		}, {
			name : '实际',
			type : 'funnel',
			left : '25%',
			width : '50%',
			maxSize : '50%',
			sort: 'ascending',
			label : {
				normal : {
					position : 'inside',
					formatter : '{c}%',
					textStyle : {
						color : '#fff'
					}
				},
				emphasis : {
					position : 'inside',
					formatter : '{b}实际: {c}%'
				}
			},
			itemStyle : {
				normal : {
					opacity : 0,
					borderWidth : 0
				}
			},
			data : [ {
				value : 80,
				name : '微博'
			}, {
				value : 60,
				name : '客户端'
			}, {
				value : 40,
				name : '微信'
			}, {
				value : 20,
				name : '论坛'
			}, {
				value : 10,
				name : '网站'
			} ]
		} ]
	},
	scatter : {
		backgroundColor : 'rgba(85,122,194,0.02)',
		tooltip : {
			trigger : 'item',
			backgroundColor : '#fff',
			textStyle : {
				color : '#999'
			},
			formatter : function(item) {
				if (item.data[2]) {
					return item.data[2];
				}
			}
		},
		xAxis : [ {
			gridIndex : 0,
			type : 'value',
			show : false,
			min : 0,
			max : 100,
			nameLocation : 'middle',
			nameGap : 30

		} ],
		yAxis : [ {
			gridIndex : 0,
			min : 0,
			show : false,
			max : 100,
			nameLocation : 'middle',
			nameGap : 30,
		} ],
		series : []
	},
	infoMedia:{
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},

		grid: {
			top: "5%",
			left: '3%',
			right: '4%',
			bottom: '6%',
			containLabel: true
		},
		xAxis: [{
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
				show: false
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				//			rotate: 45, //旋转角度
				interval: '0',
				textStyle: {
					color: '#888'
				}
			},

			boundaryGap: true,
			data: ['全部', '微博', '客户端', '微信', '论坛', '网站']
		}],
		yAxis: [{
			type: 'value',
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

			},
			splitLine: {
				show: false
			},
			splitArea: {
				"show": true,
			},
			axisLabel: { //坐标轴刻度标签
				show: true,
				//			rotate: 30,  //旋转角度
				textStyle: {
					color: '#888',
                     fontSize:12
				},
                formatter:function(v){
                    if(v>=1000){
                        return (v/1000)+"k";
                    }else{
                        return v;
                    }
                }
			}
		}],

		series: [{
            name: '辅助',
            type: 'bar',
            stack:  '总量',
            barWidth:28,
            itemStyle: {
                normal: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'
                },
                emphasis: {
                    barBorderColor: 'rgba(0,0,0,0)',
                    color: 'rgba(0,0,0,0)'
                }
            },
            data:[]
//	            	[0, 1700, 1400, 1200, 300, 0, 200]
        },
        {
            name: '信息量',
            type: 'bar',
            stack: '总量',
            barWidth:"40%",
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
             itemStyle: {
                normal: {
                    barBorderColor: '#f18d00',
                    color: '#f18d00'
                },
               
            },
            data:[]
//	            	[2900, 1200, 300, 200, 900, 300,400]
        }]
	},
	
	activeMedia:{
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
//			orient: 'vertical',
			top: '10',
			left: '20',
			bottom: '0',
			itemGap: 10,
			itemWidth: 10,
			itemHeight: 10,
			borderRadius: 0,
			data: [],
			selected: {
	            
	        }
		},
		toolbox: {
			feature: {
//					saveAsImage: {}
			}
		},
		
		series: [{
			name: '',
			type: 'pie',
			radius: ['20%', '55%'],
			center: ['50%', '60%'],
			roseType: 'area',
			data: [],
			label: {
				normal: {
					textStyle: {
						fontSize: 10
					},
					formatter: function(param) {
						return param.name + ':\n' + param.percent + '%';
					}
				}
			},

		}]
	}
	,
	friendscatter:{
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
			indicator: []
		},
		series: [
			{
				areaStyle: {
					color: '#f9a34e'
				}
			}
		]
	}
	,
	emotionSex:{
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
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
					name: [],
					value: '110',
				}, {
					name: [],
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
					value: [],
					itemStyle: {
						normal: {
							color: '#0E7DC0'
						}
					}

				}, {
					name: "敏感",
					value: [],
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
					name: [],
					value: '-110',
				}, {
					name: [],
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
					value: [],
					itemStyle: {
						normal: {
							color: '#0E7DC0'
						}
					}

				}, {
					name: "敏感",
					value: [],
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
	}
	,
	emotionType:{
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
		grid: [{
			show: false,
			left: '13%',
			top: '5%',
			bottom: 20,
			containLabel: false,
			width: '29%'
		}, {
			show: false,
			left: '51.5%',
			top: '6%',
			bottom: 30,
			width: '10%',
		}, {
			show: false,
			right: '14%',
			top: '5%',
			bottom: 20,
			containLabel: false,
			width: '29%'
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
					fontSize: 12,
				}

			},
			data: []

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
				barWidth: '25%',
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
				data: []
			},
			{
				name: '非敏感',
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
							color: '#888888',
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
				data: []
			},
			{
				name: '敏感',
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
				data: []
			}, {
				name: '敏感',
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
							color: '#888888',
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
				data: []
			}
		]
	}
	,
	emotionFans:{

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
			data: [],
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
			data: [],
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
			data: [],
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
	}
	,
	emotionLevel:{
		tooltip: {
			trigger: 'item',

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
		series: []
	}
	,
	//微博情绪
		//情绪占比饼
	emotionProportion:{
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
			data: [{
				name: '喜悦',
				textStyle: {
					color: '#F18D00',
					fontSize: 14,

				}
			}, {
				name: '愤怒',
				textStyle: {
					color: '#CF421E',
					fontSize: 14,
					padding: 4,
				}
			}, {
				name: '悲伤',
				textStyle: {
					color: '#0C7DC0',
					fontSize: 14,
					padding: 4,
				}
			}, {
				name: '惊奇',
				textStyle: {
					color: '#45B485',
					fontSize: 14,
					padding: 4,
				}
			}, {
				name: '恐惧',
				textStyle: {
					color: '#2F3237',
					fontSize: 14,
					padding: 4,
				}
			}, {
				name: '中性',
				textStyle: {
					color: '#9DA7B5',
					fontSize: 14,
					padding: 4,
				}
			}, ]
		},

		series: [{
			name: '情绪构成',
			type: 'pie',
			radius: ['30%', '60%'],
			center: ['50%', '55%'],
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
	}
	,
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
	}
	,
	liquidFill : {
		series : [ {
			color : [ '#0e7dc0' ],
			type : 'liquidFill',
			name : '媒体友好度',
			data : [  ],
			radius : '72%',
			itemStyle : {
				normal : {
					shadowBlur : 0,
					color : '#f9a34e'
				}
			},
			outline : {
				show : false
			},
			backgroundStyle : {
				borderColor : '#f9a34e',
				borderWidth : 4,
				color : '#fff'
			},
			label : {
				normal : {
					position : [ '50%', '50%' ],
					formatter : function(v) {
						return (v.data * 100).toFixed(2) + "%" + '\n媒体友好度';
					},
					textStyle : {
						fontSize : 18,
						color : '#f9a34e'
					}
				}
			}
		} ]
	}
}, echartsFunc = {
	scatter1 : function(obj) {
		var bg = {
			name : '相关背景',
			type : 'pie',
			avoidLabelOverlap : false,
			labelLine : {
				normal : {
					show : false
				}
			},
			data : [ {
				value : 1
			} ],
			animation : false
		};

		var dot = {
			name : '关联度',
			type : 'scatter',
			xAxisIndex : 0,
			yAxisIndex : 0,
			symbol : 'circle',
			symbolSize : 40,
			label : {
				normal : {
					show : true,
					textStyle : {
						color : '#555'
					},
					position : 'bottom',
					formatter : function(param) {
						return param.data[2];
					},
				},
			},
			itemStyle : {
				normal : {
					color : '#9bca63',
					opacity : 1

				},
			},

			data : [],
		}

		var dataBody = [ {
			name : '弱相关',
			type : 'scatter',
			xAxisIndex : 0,
			yAxisIndex : 0,
			symbol : 'circle',
			symbolSize : 120,
			label : {
				normal : {
					show : true,
					textStyle : {
						fontSize : '20'
					},
					formatter : function(param) {
						return param.data[2];
					},
				},

			},

			itemStyle : {
				normal : {
					color : '#eaa269'
				}
			},
			data : [ obj.data ],
			markLine : {}
		}, $.extend(true, {}, bg, {
			radius : [ '0%', '20%' ],
			itemStyle : {
				normal : {
					color : 'rgba(231,145,107,0.3)',
				},
				emphasis : {
					color : 'rgba(231,145,107,0.3)',
				}
			}
		}), $.extend(true, {}, bg, {
			radius : [ '20%', '60%' ],
			itemStyle : {
				normal : {
					color : 'rgba(231,145,107,0.2)',
				},
				emphasis : {
					color : 'rgba(231,145,107,0.2)',
				}
			}
		}), $.extend(true, {}, bg, {
			radius : [ '60%', '100%' ],
			itemStyle : {
				normal : {
					color : 'rgba(231,145,107,0.1)',
				},
				emphasis : {
					color : 'rgba(231,145,107,0.1)',
				}
			}
		}), $.extend(true, {}, bg, {
			radius : [ '100%', '150%' ],
			itemStyle : {
				normal : {
					color : 'rgba(231,145,107,0.07)',
				},
				emphasis : {
					color : 'rgba(231,145,107,0.07)',
				}
			}
		}), $.extend(true, {}, bg, {
			radius : [ '100%', '150%' ],
			radius : [ '150%', '200%' ],
			itemStyle : {
				normal : {
					color : 'rgba(231,145,107,0.04)',
				},
				emphasis : {
					color : 'rgba(231,145,107,0.04)',
				}
			}
		}) ]

		var datalist = obj.dataList;

		var dataMap = datalist.map(function(item) {
			return $.extend(true, {}, dot, {
				symbolSize : item[3],
				itemStyle : {
					normal : {
						color : item[4] == 1 ? '#eaa269' : '#b9a7af',
						opacity : 1
					}
				},
				data : [ item ],
				zlevel : 2
			});
		});

		dataBody = dataMap.concat(dataBody);
		return dataBody;
	},
	scatter2 : function(obj) {
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
			    symbolSize: 40,
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
			            color: '#5378c5',
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
			        symbolSize: 120,
			        label: {
			            normal: {
			                show: true,
			                textStyle: {
			                    fontSize: '20'
			                },
			                formatter: function(param) {
			                    return param.data[2];
			                },
			            },

			        },

			        itemStyle: {
			            normal: {
			                color: '#5378c5'
			            }
			        },
			        data : [ obj.data ],
			        markLine: {}
			   },
			    $.extend(true, {}, bg,{
			       radius: ['0%', '20%'],
			            itemStyle: {
			                normal: {
			                    color: 'rgba(85,122,194,0.3)',
			                },
			                emphasis: {
			                    color: 'rgba(85,122,194,0.3)',
			                }
			            }
			    }),
			    $.extend(true, {}, bg,{
			         radius: ['20%', '60%'],
			            itemStyle: {
			                normal: {
			                    color: 'rgba(85,122,194,0.2)',
			                },
			                emphasis: {
			                    color: 'rgba(85,122,194,0.2)',
			                }
			            }
			    }),
			    $.extend(true, {}, bg,{
			        radius: ['60%', '100%'],
			            itemStyle: {
			                normal: {
			                    color: 'rgba(85,122,194,0.1)',
			                },
			                emphasis: {
			                    color: 'rgba(85,122,194,0.1)',
			                }
			            }
			    }),
			     $.extend(true, {}, bg,{
			         radius: ['100%', '150%'],
			            itemStyle: {
			                normal: {
			                    color: 'rgba(85,122,194,0.07)',
			                },
			                emphasis: {
			                    color: 'rgba(85,122,194,0.07)',
			                }
			            }
			    }),
			     $.extend(true, {}, bg,{
			         radius: ['100%', '150%'],
			            radius: ['150%', '200%'],
			            itemStyle: {
			                normal: {
			                    color: 'rgba(85,122,194,0.04)',
			                },
			                emphasis: {
			                    color: 'rgba(85,122,194,0.04)',
			                }
			            }
			    })
			]

			var datalist = obj.dataList;


			var dataMap = datalist.map(function(item){
			    return $.extend(true, {}, dot,{
			        symbolSize: item[3],
			        itemStyle: {
			            normal: {
			                color: item[4]==1? '#5378c5':'#e6c74b',
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
		return dataBody;
	}
}

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
	}
	myChart[$id.prop("id")].setOption(opt);
},

// 设置图表数据
setEchartsOpion = function(opts) {
	console.log(opts)
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