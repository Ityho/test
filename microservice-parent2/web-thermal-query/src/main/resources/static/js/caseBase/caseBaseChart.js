   //近30天热点事件走势图
   var hotEventsChart = echarts.init(document.getElementById('hotEventsChart'));

   // 指定图表的配置项和数据
        
 option = {
 	color: ['#053e6f', '#04749e', '#2dafd3' ,'#35d6d4' ,'#a9a9a9' ,'#e1873e' ,'#ddaa0d' ,'#027aeb'],
     legend: {
        top:'5%',
        data: ['时事', '社会', '体育' ,'科技' ,'自然灾害' ,'娱乐' ,'人物' ,'财经']
    },
    grid:{
        bottom:'15%',
        right:'5%',
        left:'10%',
        top:'22%'
    },
    xAxis: {
         splitLine: {
            show:false
        },
        axisLine: {
        	lineStyle: {
        		color: '#f0f0f0',
        	}
        },
        axisLabel: {
            interval: 0,
            textStyle:{
                fontSize:12,
                color:'#444'
            },},
        data: ['3-1', '3-6', '3-11', '3-16', '3-21',  '3-26', '3-31']
    },
    yAxis: {
        axisLabel: {
        	textStyle:{
                fontSize:12,
                color:'#444'
           },
            formatter: '{value}'
        },
        axisLine: {
        	lineStyle: {
        		color: '#f0f0f0',
        	}
        },
        splitLine: {
        	show:false
            
        }
    },
     tooltip: {
        trigger: 'item',
        formatter: '{b}<br/>{a}:{c}'
         
     },
    series: [{
        name:'时事',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: 'rgb(164,298, 250)'
                }, {
                    offset: 1,
                    color: 'rgb(33, 207, 209)'
                }]),
       data:[40,20,0,16,0,0,56]
    },{
        name:'社会',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#F2CB64'
                }]),
        data:[70,0,75,0,0,40,0]
    },{
        name:'体育',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[0,60,0,0,0,45,0]
    },{
        name:'科技',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[20,40,0,0,0,0,0]
    },{
        name:'自然灾害',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[0,0,0,40,0,0,0]
    },{
        name:'娱乐',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[0,0,60,40,0,0,0]
    },{
        name:'人物',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[0,80,0,0,40,20,0]
    },{
        name:'财经',
        symbolSize: function (data) {
            return Math.sqrt(data) * 5;
        },
        type: 'scatter',
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
                    offset: 0,
                    color: '#98DFBA'
                }]),
        data:[0,0,0,0,0,55,0]
    }]
};

    // 使用刚指定的配置项和数据显示图表。
    hotEventsChart.setOption(option);
 
    //热点事件区间占比图
   var hotEventsIntervalChart = echarts.init(document.getElementById('hotEventsIntervalChart'));

   // 指定图表的配置项和数据
        
 placeHolderStyle = {
    normal: {
        label: {
            show: false,
            position: "center"
        },
        labelLine: {
            show: false
        },
        color: "#dedede",
        borderColor: "#dedede",
        borderWidth: 0
    },
    emphasis: {
        color: "#dedede",
        borderColor: "#dedede",
        borderWidth: 0
    }
};
option = {
    color: ['#fb6602', '#fff', '#ffca13', '#fff', "#009fd9"],    
    series: [{
        name: '热度指数区间<40',
        type: 'pie',
        clockWise: true, //顺时加载
        hoverAnimation: false, //鼠标移入变大
        radius: ['89', '90'],
        center: ['30%', '50%'],
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    position: 'outside',
                    formatter: '{b}\n{a}%'
                },
                labelLine: {
                    show: true,
                    length: 50,
                    smooth: 0
                },
                borderWidth: 2,
                shadowBlur: 40,
                borderColor: "#fb6602",
                shadowColor: 'rgba(0, 0, 0, 0)' //边框阴影
            }
        },
        data: [{
            value: 5,
            name: '50%'
        }, {
            value: 5,
            name: '',
            itemStyle: placeHolderStyle
        }]
    }, {
        name: '白',
        type: 'pie',
        clockWise: false,
        radius: ['69', '70'],
        center: ['30%', '50%'],
        hoverAnimation: false,
        data: [{
            value: 1
        }]
    }, {
        name: '热度指数区间>80',
        type: 'pie',
        clockWise: true,
        hoverAnimation: false,
        radius: ['69', '70'],
        center: ['30%', '50%'],
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    position: 'outside',
                    formatter: '{b}\n{a}%'
                },
                labelLine: {
                    show: true,
                    length: 80,
                    smooth: 0
                },
                borderWidth: 2,
                shadowBlur: 40,
                borderColor: "#fecc1e",
                shadowColor: 'rgba(0, 0, 0, 0)' //边框阴影
            }
        },
        data: [{
            value: 3,
            name: '30%'
        }, {
            value: 7,
            name: '',
            itemStyle: placeHolderStyle
        }]
    }, {
        name: '白',
        type: 'pie',
        clockWise: false,
        hoverAnimation: false,
        radius: [140, 140],
        data: [{
            value: 1
        }]
    }, {
        name: '热度指数区间40~80',
        type: 'pie',
        clockWise: true,
        hoverAnimation: false,
        radius: ['49', '50'],
        center: ['30%', '50%'],
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    position: 'outside',
                    formatter: '{b}\n{a}%'
                },
                labelLine: {
                    show: true,
                    length: 80,
                    smooth: 0
                },
                borderWidth: 2,
                shadowBlur: 40,
                borderColor: "#009fd9",
                shadowColor: 'rgba(0, 0, 0, 0)' //边框阴影
            }
        },
        data: [{
            value: 2,
            name: '20%'
        }, {
            value: 8,
            name: '',
            itemStyle: placeHolderStyle
        }]
    } ]
};
    // 使用刚指定的配置项和数据显示图表。
    hotEventsIntervalChart.setOption(option);
 
 //事件分析时间
        var eventAnalysisTimeChart = echarts.init(document.getElementById('eventAnalysisTimeChart'));

        // 指定图表的配置项和数据
        var option = {
    tooltip : {
        trigger: 'axis'
    },         
    grid: {
    	top: '15%',
        left: '1%',
        right: '2%',
        bottom: '5%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            axisLine:{//坐标轴轴线 默认 true,
            	show: false
            },
            axisTick:{//坐标轴刻度
            	show: true,
            	lineStyle:{
            		color:'#888',
            		width: 1,
            		type: 'solid'
            	}
            },
            axisLabel:{//坐标轴刻度标签
            	show: true,
            	interval: '0',
            	//rotate: 30,  //旋转角度
            	textStyle:{
            		color:'#888'
            	}
            },
            boundaryGap : false,
            data : ['10:00','12:00','14:00','16:00','18:00','20:00','22:00','24:00','02:00','04:00','06:00','08:00','10:00']
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLine:{//坐标轴轴线 默认 true,
            	show: false
            },
            axisTick:{//坐标轴刻度
            	show: false
            },
            axisLabel:{//坐标轴刻度标签
            	show: true,
            	//rotate: 30,  //旋转角度
            	textStyle:{
            		color:'#888'
            	}
            },
        }
    ],
    series : [
        {
            name:'事件分析时间',
            type:'line',
            symbol: 'circle',
            //symbol: 'emptyCircle',//圆圈
            symbolSize: 10,
            itemStyle:{
            	normal:{
            		color: '#fc8503',
            		borderColor: '#f2fbff',
            		borderWidth: 3
            	}
            },
            lineStyle:{
            	normal:{
            		color: '#fc8503',
            		width: 2,
            		type: 'solid'
            	}
            },
            areaStyle:{
            	normal:{
            		color:'rgba(0,0,0,0.5)',
            		opacity: 0
            	}
            },
            smooth: false,
            data:[8, 25, 9, 26, 51, 24, 41, 65, 32, 58, 72, 95, 73, 97]
        }   
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        eventAnalysisTimeChart.setOption(option);
        
//重新定义所有图表自适应容器大小
setTimeout(function() {
	window.onresize = function() {
		hotEventsChart.resize();
		hotEventsIntervalChart.resize();
	}

});