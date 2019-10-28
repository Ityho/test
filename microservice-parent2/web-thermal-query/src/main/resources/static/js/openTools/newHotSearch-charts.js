function LineChartHeatNetworkTwo(data1,leng,data2,dom,title){
    	var titArr = title.split(",");
    	var a=[];
    	for(var i=0;i<titArr.length;i++){
    		a.push(titArr[i])
    	}
        	//热度指数趋势
        	var hotTrendChart = echarts.init(document.getElementById(dom));

        	// 指定图表的配置项和数据
        	var option = {

        		tooltip: {
        			trigger: 'axis'
        		},
        		toolbox: {
        			orient: 'vertical',
        			feature: {
//        				saveAsImage: {},
//        				restore: {}
        			}
        		},
        		legend: {
        			top:'0px',
                     data: a
                 },
        		grid: {
        			left: '2%',
        			right: '5%',
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
        				}
        			},
        			boundaryGap: false,
        			data: leng
        			
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
        				},
                        formatter:function(v){
                            if(v>=1000){
                                return (v/1000)+"k";
                            }else{
                                return v;
                            }
                        }
        			},
        		}],

        		series: [{
        				name: titArr[0],
        				type: 'line',
        				smooth: true, //这句就是让曲线变平滑的
        				symbol: 'none', //拐点样式
        				markPoint: {
        	              data: [{
        	                  type: 'max',
        	                  name: '最大值',
        	                  symbol: '<%=njxBasePath%>/images/slider/mp.png',
//         	                  symbol: 'image://http://10.1.0.174:8020/wyq-svn/newhotSearch/images/mp.png',
        	                  symbolOffset: [0, '-100%'],
        	                  symbolSize: [80, 30],
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
        				data: data1
        			},
        			{
        				name: titArr[1],
        				type: 'line',
        				smooth: true, //这句就是让曲线变平滑的
        				symbol: 'none', //拐点样式
        				markPoint: {
        	              data: [{
        	                  type: 'max',
        	                  name: '最大值',
        	                  symbol: '<%=njxBasePath%>/images/slider/mp.png',
//         	                  symbol: 'image://http://10.1.0.174:8020/wyq-svn/newhotSearch/images/mp.png',
        	                  symbolOffset: [0, '-100%'],
        	                  symbolSize: [80, 30],
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
        						color: '#456dc0'
        					}
        				},
        				lineStyle: {
        					normal: {
        						color: '#456dc0',
        						width: 1.5,
        						type: 'solid'
        					}
        				},
        				areaStyle: {
        					normal: {
        						color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                    offset: 0,
                                    color: 'rgba(69, 109, 192, 0.5)'
                                }, {
                                    offset: 1,
                                    color: 'rgba(69, 109, 192, 0)'
                                }], false),
                                shadowColor: 'rgba(0, 0, 0, 0.1)',
                                shadowBlur: 10
        					}
        				},
        				smooth: false,
        				data: data2
        			}

        		]
        	};

        	// 使用刚指定的配置项和数据显示图表。
        	hotTrendChart.setOption(option);
        }
function LineChartHeatNetwork2(data,leng,dom,title){
		var a=[];
		a.push(title);
    	//热度指数趋势
    	var hotTrendChart = echarts.init(document.getElementById(dom));

    	// 指定图表的配置项和数据
    	var option = {

    		tooltip: {
    			trigger: 'axis'
    		},
    		toolbox: {
    			orient: 'vertical',
    			feature: {
//    				saveAsImage: {},
//    				restore: {}
    			}
    		},
    		legend: {
    			top:'0px',
                 data: a
             },
    		grid: {
    			left: '2%',
    			right: '5%',
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
    				}
    			},
    			boundaryGap: false,
    			data: leng
    			
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
    				name: title,
    				type: 'line',
    				smooth: true, //这句就是让曲线变平滑的
    				symbol: 'none', //拐点样式
    				markPoint: {
    	              data: [{
    	                  type: 'max',
    	                  name: '最大值',
    	                  symbol: '<%=njxBasePath%>/images/slider/mp.png',
//     	                  symbol: 'image://http://10.1.0.174:8020/wyq-svn/newhotSearch/images/mp.png',
    	                  symbolOffset: [0, '-100%'],
    	                  symbolSize: [80, 30],
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
    				data: data
    			}

    		]
    	};

    	// 使用刚指定的配置项和数据显示图表。
    	hotTrendChart.setOption(option);
    }