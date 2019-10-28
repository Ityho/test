$(document).ready(function(){
var option = { 
	    "series": [{
	        "name": "内圈重要信息",
	        "center": [
	            "50%",
	            "50%"
	        ],
	        "radius": [
	            "78%",
	            "79%"
	        ],
	        "startAngle":90,
	        "clockWise": true,
	        "silent":true,
	        "hoverAnimation": false,
	        "type": "pie",
	        "data": [{
	            "value": 84,
	            "name": "",
	              "itemStyle": {
	                "normal": {
	                    "label": {
	                        "show": false
	                    },
	                    "labelLine": {
	                        "show": false
	                    },
	                    "color": 'red',
	                    "borderColor": new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
	                        offset: 0,
	                        color: '#7f2834'
	                    }, {
	                        offset: 1,
	                        color: '#10050b'
	                    }]),
	                    "borderWidth": 15,   
	                }
	            },
	        }, {
	            
	            "label": {
	                "normal": {
	                    "show": false,
	                  
	                    "formatter": '{d} %',
	                    "textStyle": {
	                        "fontSize": 30,
	                        "color":'#ff564b'	
	                    },
	                    "position": 'center'
	                }
	            },
	            "labelLine": {
	                "show": false
	            },
	            "name": " ",
	            "value": 16,
	            "silent":true,
	            "itemStyle": {
	                "normal": {
	                    "label":{
	                        "show" :false
	                    },
	                    labelLine:{
	                       show:false
	                    },
	                    "color": "#5886f0",
	                    "borderColor": '#ff564b',
	                    
	                    "borderWidth": 15,
	                }
	               
	            }
	        }]
	    }]
	},
	barOption = {
		
		    xAxis:  {
		        type: 'value',
		        axisTick : {show: false},
		        axisLine: {
		            show: false
		        },
		        splitLine: {
		            show: false
		        },
		        axisLabel:{
		        	show:false
		        }
		    },
		    yAxis: [
		            {
		                type: 'category',
		                
		                axisTick : {show: false},
		                axisLine: {
		                    show: false,
		                    lineStyle:{
		                        color:'#fff',
		                    }
		                },
		                data: ['']
		            },
		            {
		                type: 'category',
		                axisLine: {show:false},
		                axisTick: {show:false},
		                axisLabel: {show:false},
		                splitArea: {show:false},
		                splitLine: {show:false},
		               data:['']
		            },
		            
		    ],
		    series: [
		        {
		            name: '总量',
		            type: 'bar',
		            yAxisIndex:1,
		            barWidth:'80%',
		            itemStyle:{
		                normal: {
		                    show: true,
		                    color: '#631f2a',
		                    barBorderRadius:50,
		                    borderWidth:0,
		                    borderColor:'#333',
		                }
		            },
		          
		            data: [100]
		        },
		        {
		            name: '当前量',
		            type: 'bar',
		            barWidth:'80%',
		            itemStyle:{
		                normal: {
		                    show: true,
		                    color: '#ff564b',
		                    barBorderRadius:50,
		                    borderWidth:0,
		                    borderColor:'#333',
		                }
		            },
		          
		            data: [8]
		        }
		       
		    ]
		},
		lineOption = {
			grid:{
				left:'10%',
				width:'90%',
				height:'70%',
				containLabel:false
			},
			xAxis:{
				type:'category',
				axisLine:{
					show : false
				},
				axisTick:{
					show : false
				},
				axisLabel:{
					show :true,
					textStyle:{
						color:'#9b9b9b',
						fontSize:14
					}
				},
				
				data:['7.31','8.1','8.2','8.3','8.4','8.5','8.6']
			},
			yAxis:{
				axisLine:{
					show : false
				},
				axisTick:{
					show : false
				},
				axisLabel:{
					show :true,
					textStyle:{
						color:'#9b9b9b',
						fontSize:14
					}
				},
				splitLine:{
					show : true,
					lineStyle:{
						color:'#ff564b',
						width:1,
						type:'dashed'
					}
				}
			},
			series:[{
				type:'line',
				name:'热度指数',
				lineStyle:{
					normal:{
						color:'#ff564b',
						width:2,
						type:'solid'
					}
				},
				symbol:'circle',
				symbolSize:0,
				smooth:true,
				markPoint:{
					data:[{
						type:'max',
						symbol:'image://'+njxImgSrc+'/images/dailygame/symbol.png',
						symbolSize:'20',
						label:{
							normal:{
								show:false
							}
						}
					}]
				},
				data:[1.2,0.99,2.73,1.93,3.18,2.11,0.83]
			}]
		};

var senstiveChart = echarts.init(document.getElementById("senstive-chart")),
	hotpointChart = echarts.init(document.getElementById("bar-chart")),
	lineChart = echarts.init(document.getElementById("line-chart"));
hotpointChart.setOption(barOption);
senstiveChart.setOption(option);
lineChart.setOption(lineOption);
	
})