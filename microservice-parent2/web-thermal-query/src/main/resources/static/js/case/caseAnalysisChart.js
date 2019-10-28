
 //事件走势
var eventTrendChart = echarts.init(document.getElementById('eventTrendChart'));

// 指定图表的配置项和数据
    var option = {
tooltip : {
    trigger: 'axis'
},
toolbox: {
	  orient:'vertical',
    feature: {
        saveAsImage: {},
        restore:{}
    }
},
 
grid: {
    left: '2%',
right: '5%',
top:'10%',
bottom: '8%',
    containLabel: true
},
xAxis : [
    {
        type : 'category',
    axisLine:{//坐标轴轴线 默认 true,
    	show: false
    },
    axisTick:{//坐标轴刻度
    	show: false,
    	lineStyle:{
    		color:'#888',
    		width: 1,
    		type: 'solid'
    	}
    },
    axisLabel:{//坐标轴刻度标签
    	show: true,
    	//rotate: 30,  //旋转角度
    	textStyle:{
    		color:'#888'
    	}
    },
    boundaryGap : false,
    data: (function (){
        var now = new Date();
        var res = [];
        var len = 10;
        while (len--) {
            res.unshift(now.toLocaleTimeString().replace(/^\D*/,''));
                now = new Date(now - 2000);
            }
            return res;
        })()
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
            name:'陈伟霆',
    type:'line',
    symbol: 'emptyCircle',
    symbolSize: 8,
    itemStyle:{
    	normal:{
    		color: '#f18d01'
    	}
    },
    lineStyle:{
    	normal:{
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
            data:[20,2000, 5840, 3921, 5804, 8250, 4280, 3910,2950, 10]
        }
        
    ]
};

        // 使用刚指定的配置项和数据显示图表。
eventTrendChart.setOption(option);

 //来源类型
var sourceTypeChart = echarts.init(document.getElementById('sourceTypeChart'));
     
var plantCap = [{
    name: '各类型媒体报道',
    value: '281950',
    per: '100%',
}, {
    name: '微博',
    value: '234122',
    per: '80.0%',
}, {
    name: '网站',
    value: '13075',
    per: '4.6%',
}, {
    name: '微信',
    value: '12861',
    per: '4.6%',
}, {
    name: '新闻',
    value: '9098',
    per: '3.2%',
}, {
    name: '客户端',
    value: '5936',
    per: '2.1%',
}, {
    name: '论坛',
    value: '4451',
    per: '1.6%',
}, {
    name: '博客',
    value: '1474',
    per: '0.5%',
}, {
    name: '政务',
    value: '418',
    per: '0.1%',
}, {
    name: '报刊',
    value: '334',
    per: '0.1%',
}, {
    name: '视频',
    value: '115',
    per: '0.0%',
}, {
    name: '境外',
    value: '66',
    per: '0.0%',
}];

var datalist = [{
    offset: [50, 50],
    symbolSize: 160,
    opacity: 1,
    color: '#cb6704'
}, {
    offset: [24, 66],
    symbolSize: 80,
    opacity: .95,
    color: '#d67009'
}, {
    offset: [37, 86],
    symbolSize: 78,
    opacity: .88,
    color: '#d67009'
}, {
    offset: [55, 90],
    symbolSize: 75,
    opacity: .8,
    color: '#d67009'
}, {
    offset: [70, 78],
    symbolSize: 70,
    opacity: .75,
    color: '#d67009'
}, {
    offset: [78, 56],
    symbolSize: 70,
    opacity: .7,
    color: '#d67009'
}, {
    offset: [75, 34],
    symbolSize: 65,
    opacity: .68,
    color: '#d67009'
}, {
    offset: [64, 17],
    symbolSize: 60,
    opacity: .6,
    color: '#d67009'
}, {
    offset: [49, 12],
    symbolSize: 55,
    opacity: .6,
    color: '#d67009'
}, {
    offset: [38, 18],
    symbolSize: 50,
    opacity: .6,
    color: '#d67009'
}, {
    offset: [29, 30],
    symbolSize: 50,
    opacity: .6,
    color: '#d67009'
}, {
    offset: [22, 45],
    symbolSize: 50,
    opacity: .6,
    color: '#d67009'
}];
var datas = [];
for (var i = 0; i < plantCap.length; i++) {
    var item = plantCap[i];
    var itemToStyle = datalist[i];
    datas.push({
        name: item.name + '\n' + item.value + '\n' + item.per,
        value: itemToStyle.offset,
        symbolSize: itemToStyle.symbolSize,
        label: {
            normal: {
                textStyle: {
                    fontSize: 11
                }
            }
        },
        itemStyle: {
            normal: {
                color: itemToStyle.color,
                opacity: itemToStyle.opacity
            }
        },
    })
}
option = {
    grid: {
        show: false,
        top: 10,
        bottom: 10
    },
    xAxis: [{
        gridIndex: 0,
        type: 'value',
        show: false,
        min: 0,
        max: 100,
        nameLocation: 'middle',
        nameGap: 5
    }],
    yAxis: [{
        gridIndex: 0,
        min: 0,
        show: false,
        max: 100,
        nameLocation: 'middle',
        nameGap: 30
    }],
    series: [{
        type: 'scatter',
        symbol: 'circle',
        symbolSize: 120,
        label: {
            normal: {
                show: true,
                formatter: '{b}',
                color: '#fff',
                textStyle: {
                    fontSize: '20'
                }
            },
        },
        itemStyle: {
            normal: {
                color: '#00acea'
            }
        },
        data: datas
    }]
};
// 使用刚指定的配置项和数据显示图表。
sourceTypeChart.setOption(option);


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
                color: '#eaa269'
            }
        },
        data: [
            [50, 50, '手机', '#eaa269']
        ],
        markLine: {}
    },
    $.extend(true, {}, bg,{
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
    $.extend(true, {}, bg,{
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
    $.extend(true, {}, bg,{
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
     $.extend(true, {}, bg,{
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
     $.extend(true, {}, bg,{
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


var dataMap = datalist.map(function(item){
    return $.extend(true, {}, dot,{
        symbolSize: item[3],
        itemStyle: {
            normal: {
                color: item[4]==1? '#eaa269':'#b9a7af',
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
        formatter: function(item){
            if (item.data[2]) {
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
        
 //情绪占比
var emotionProChart = echarts.init(document.getElementById('emotionProChart'));
var dataAll = [335, 310, 448, 234, 135, 135];
var yAxisData = ['喜悦','悲伤','中性','愤怒','恐惧','惊奇'];
var colorList = ['#f38a0b','#077fc3','#9fa7b4','#cd4515','#2f3237','#46b382']
// 指定图表的配置项和数据
var option = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
    	orient: 'vertical',
    	itemGap: 10,
        itemWidth: 10,
        itemHeight: 10,
        x : 'left',
        data:['喜悦','悲伤','中性','愤怒','恐惧','惊奇']
    },
    grid: [
	        {x: '50%', y: '7%', width: '45%', height: '90%'},
	    ],
    xAxis: [
	        {gridIndex: 0, axisTick: {show:false},
	        axisLabel: {show:false},splitLine: {show:false},
	        axisLine: {show:false }},
	    ],
	    yAxis: [
	         {  gridIndex: 0,inverse: true, interval:0,data:yAxisData,
	            axisTick: {show:false}, axisLabel: {show:true},splitLine: {show:false},
	            axisLine: {show:true,lineStyle:{color:"#6173a3"}},
	        }
	    ],
    calculable : true,
    series : [
        {
            name:'情绪占比pie',
            type:'pie',
            radius : ['40%', '80%'],
            center : ['25%', '50%'],
            color: colorList,
            label:{normal:{formatter: '{b}\n{d}%',textStyle:{fontSize:12}}},
            itemStyle:{normal:{borderColor:'#fff',borderWidth: 2}},
            roseType : 'area',
             data:[
                {value:335, name:'喜悦'},
                {value:310, name:'悲伤'},
                {value:448, name:'中性'},
                {value:234, name:'愤怒'},
                {value:135, name:'恐惧'},
                {value:135, name:'惊奇'}
                
            ]
        },
        {
	            name: '情绪占比bar',
	            type: 'bar',xAxisIndex: 0,yAxisIndex: 0,barWidth:'45%',
	            color:colorList,
	            itemStyle:{
	            	normal:{
	            		color: function(params) {
                                var num=colorList.length;
                                return colorList[params.dataIndex%num]
                            },
	            	}
	            	},
	            label:{normal:{show:true, position:"right",textStyle:{color:"#9EA7C4"}}},
	            data: dataAll.sort(),
	        },
    ]
};

        // 使用刚指定的配置项和数据显示图表。
emotionProChart.setOption(option);
        
//地域分布
var mapChart = echarts.init(document.getElementById('mapChart'));
function randomData() {
    return Math.round(Math.random()*1000);
   }
// 指定图表的配置项和数据
var option = {
 
tooltip: {
    trigger: 'item'
},    
visualMap: {
    min: 0,
    max: 2500,
    left: '0',
top: '10',
orient: 'vertical',
text: ['高','低'],           // 文本，默认为数值文本
calculable: true,
inRange: {
        color: ['#fab85e','#fa7d43', '#e34743']
        }
},
geo:{
	map: 'china-contour',
itemStyle: {
        normal: {
            borderWidth: 2,
            shadowBlur: 50,
            shadowColor: 'rgba(0, 0, 0, 0.2)'
            }
        }
},
series: [
    {
        name: '已转发数',
    type: 'map',
    mapType: 'china',
    roam: false,
    left:'center',
    label: {
    	 normal: {
            show: false,
            textStyle: {
				color: "#3a3a3a",//地图文字颜色
				fontSize: 8
			}
         }
         
            },
            itemStyle: {
                normal: {
                    borderColor: '#959595',
                    areaColor: '#efefef',//地图背景颜色
                },
                emphasis: {
                    areaColor: '#bdd3f5'
                }
            },
    data:[
        
        {name: '湖北',value: randomData() }
        
    ]
},
{
    name: '成功接收状态报告数',
    type: 'map',
    mapType: 'china',
    label: {
        normal: {
            show: true
        },
        emphasis: {
            show: true
        }
    },
    data:[
        {name: '四川',value: randomData() },
        {name: '河南',value: randomData() },
        {name: '安徽',value: randomData() },
        {name: '江西',value: randomData() }
                
            ]
        }
         
    ]
};

        // 使用刚指定的配置项和数据显示图表。
mapChart.setOption(option);        


//地域信息量
var mapDisChart = echarts.init(document.getElementById('mapDisChart'));

var dyData = [4230, 6812, 3301, 3334, 4890, 2130, 15520,3950,1850,4750]
        // 指定图表的配置项和数据
var option = {
    title: {
        text: '地域分布Top10',
        textStyle:{fontWeight:'normal'},
        left:'20',
        top:'10'
    },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '12%',
        bottom: '10%',
        top:'10%',
        containLabel: true
    },
   
    xAxis : [
        {
        	name:'单位/条',
        	nameTextStyle:{
        		color:'#0086ce'
        	},
            type : 'value',
            axisLine:{//坐标轴轴线 默认 true,
            	show: true,
            	lineStyle:{
            		color:'#dbdbdb',
            		width: 1,
            		type: 'solid'
            	}
            },
            axisTick:{//坐标轴刻度
            	show: true,
            	lineStyle:{
            		color:'#888',
            		width: 1,
            		type: 'solid'
            	}
            },
            splitLine:{
            	show: false
            	 
            },
            axisLabel:{//坐标轴刻度标签
            	show: true,
            	formatter:function(v){
                        if(v>=1000){
                            return (v/1000)+"k";
                        }else{
                            return v;
                        }
                    },

            	//rotate: 30,  //旋转角度
            	textStyle:{
            		color:'#888'
            	}
            }
        }
    ],
     yAxis : [
        {
            type : 'category',
            inverse: true,
            axisLine:{//坐标轴轴线 默认 true,
            	show: true,
            	lineStyle:{
            		color:'#dbdbdb',
            		width: 1,
            		type: 'solid'
            	}
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
            	//rotate: 30,  //旋转角度
            	textStyle:{
            		color:'#888'
            	}
            },
            splitLine:{
            	show: true,
            	lineStyle:{
            		color:'#dbdbdb',
            		width: 1,
            		type: 'solid'
            	}
            },
            boundaryGap : true,
            data : ['上海','海南','浙江','安徽','海外','江苏','山东','广东','北京','四川']
        }
       
    ],
    series : [ 
        {
            name:'鹰眼舆情',
            type:'bar',

            barWidth: '40%',
             itemStyle:{
           normal:{
               color:function(d){//颜色判断  这里用到两种  你可以设置多种
                   if(d.dataIndex%2===0){
                       return '#b9a7af';
                   }else{
                       return '#f1b192';
                   }
               }
           }
       },
       label: {
        normal: {
    	 	formatter:function(param){
                if(param.data){
                    return String(param.data).replace(/(\d)(?=(\d{3})+$)/g, "$1,");
                }else{
                    return param;
                }
             },
 
            show: true,
            textStyle: {
               color: '#444'
            },
            position: 'right'
        }
     },
            data:dyData
        }
    ]
};

        // 使用刚指定的配置项和数据显示图表。
mapDisChart.setOption(option);
        
//同类型事件热度对比图
var typeEventHotChart = echarts.init(document.getElementById('typeEventHotChart'));

var exps = ['01', '02', '03', '04', '05', '06', '07',
    '08', '09', '10', '11', '12', '13', '14',
    '15', '16', '17', '18', '19', '20', '21',
    '22', '23', '24'
];
var students = ['事件A', '事件B', '事件C', '事件D', '事件E', '事件F', '事件G'];

var data = [
    [0, 0, 75],
    [0, 1, 60],
    [0, 2, 0],
    [0, 3, 0],
    [0, 4, 20],
    [0, 5, 0],
    [0, 6, 0],
    [0, 7, 0],
    [0, 8, 0],
    [0, 9, 0],
    [0, 10, 0],
    [0, 11, 20],
    [0, 12, 40],
    [0, 13, 10],
    [0, 14, 65],
    [0, 15, 30],
    [0, 16, 40],
    [0, 17, 60],
    [0, 18, 40],
    [0, 19, 45],
    [0, 20, 30],
    [0, 21, 30],
    [0, 22, 20],
    [0, 23, 50],
    [1, 0, 70],
    [1, 1, 0],
    [1, 2, 0],
    [1, 3, 0],
    [1, 4, 0],
    [1, 5, 0],
    [1, 6, 0],
    [1, 7, 0],
    [1, 8, 0],
    [1, 9, 0],
    [1, 10, 50],
    [1, 11, 20],
    [1, 12, 25],
    [1, 13, 60],
    [1, 14, 90],
    [1, 15, 100],
    [1, 16, 65],
    [1, 17, 75],
    [1, 18, 80],
    [1, 19, 95],
    [1, 20, 5],
    [1, 21, 5],
    [1, 22, 70],
    [1, 23, 2],
    [2, 0, 1],
    [2, 1, 1],
    [2, 2, 0],
    [2, 3, 0],
    [2, 4, 0],
    [2, 5, 0],
    [2, 6, 0],
    [2, 7, 0],
    [2, 8, 0],
    [2, 9, 0],
    [2, 10, 3],
    [2, 11, 2],
    [2, 12, 1],
    [2, 13, 9],
    [2, 14, 8],
    [2, 15, 10],
    [2, 16, 6],
    [2, 17, 5],
    [2, 18, 5],
    [2, 19, 5],
    [2, 20, 7],
    [2, 21, 4],
    [2, 22, 2],
    [2, 23, 4],
    [3, 0, 7],
    [3, 1, 3],
    [3, 2, 0],
    [3, 3, 0],
    [3, 4, 0],
    [3, 5, 0],
    [3, 6, 0],
    [3, 7, 0],
    [3, 8, 1],
    [3, 9, 0],
    [3, 10, 5],
    [3, 11, 4],
    [3, 12, 7],
    [3, 13, 14],
    [3, 14, 13],
    [3, 15, 12],
    [3, 16, 9],
    [3, 17, 5],
    [3, 18, 5],
    [3, 19, 10],
    [3, 20, 6],
    [3, 21, 4],
    [3, 22, 4],
    [3, 23, 1],
    [4, 0, 1],
    [4, 1, 3],
    [4, 2, 0],
    [4, 3, 0],
    [4, 4, 0],
    [4, 5, 1],
    [4, 6, 0],
    [4, 7, 0],
    [4, 8, 0],
    [4, 9, 2],
    [4, 10, 4],
    [4, 11, 4],
    [4, 12, 2],
    [4, 13, 4],
    [4, 14, 4],
    [4, 15, 14],
    [4, 16, 12],
    [4, 17, 1],
    [4, 18, 8],
    [4, 19, 5],
    [4, 20, 3],
    [4, 21, 7],
    [4, 22, 3],
    [4, 23, 0],
    [5, 0, 2],
    [5, 1, 1],
    [5, 2, 0],
    [5, 3, 3],
    [5, 4, 0],
    [5, 5, 0],
    [5, 6, 0],
    [5, 7, 0],
    [5, 8, 2],
    [5, 9, 0],
    [5, 10, 4],
    [5, 11, 1],
    [5, 12, 5],
    [5, 13, 10],
    [5, 14, 5],
    [5, 15, 7],
    [5, 16, 11],
    [5, 17, 6],
    [5, 18, 0],
    [5, 19, 5],
    [5, 20, 3],
    [5, 21, 4],
    [5, 22, 2],
    [5, 23, 0],
    [6, 0, 1],
    [6, 1, 0],
    [6, 2, 0],
    [6, 3, 0],
    [6, 4, 0],
    [6, 5, 0],
    [6, 6, 0],
    [6, 7, 0],
    [6, 8, 0],
    [6, 9, 0],
    [6, 10, 1],
    [6, 11, 0],
    [6, 12, 2],
    [6, 13, 1],
    [6, 14, 3],
    [6, 15, 4],
    [6, 16, 0],
    [6, 17, 0],
    [6, 18, 0],
    [6, 19, 0],
    [6, 20, 1],
    [6, 21, 2],
    [6, 22, 2],
    [6, 23, 6]  
];

data = data.map(function(item) {
    return [item[1], item[0], item[2] || '-'];
});

option = {
    tooltip: {
        position: 'top'
    },
    animation: false,
    grid: {
        height: '60%',
        y: '10%'
    },
    xAxis: {
        type: 'category',
        data: exps,
        splitArea: {
            show: true
        }
    },
    yAxis: {
        type: 'category',
        inverse: true,
        data: students,
        splitArea: {
            show: true
        }
    },
    visualMap: {
        min: 0,
        max: 100,
        calculable: true,
        orient: 'vertical',
        left: '0',
        bottom: '25%'
    },
    series: [{
        name: '学习进度（%）',
        type: 'heatmap',
        data: data,
        label: {
            normal: {
                show: true
            }
        },
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }]
};

// 使用刚指定的配置项和数据显示图表。
typeEventHotChart.setOption(option);


//媒体友好度

// 指定图表的配置项和数据
var containers = document.getElementsByClassName('mediaFriendlyChart');
var option = [{
	
    series: [{
    	color: ['#0e7dc0'],
        type: 'liquidFill',
        name: '媒体友好度',
        data: [0.58] ,
        radius: '72%',
        itemStyle: {
            normal: {
                shadowBlur: 0,
                color:'#f9a34e'
                }
            },
            outline: {
                 show: false
             },
            backgroundStyle: {
	          borderColor: '#f9a34e',
              borderWidth: 4,
              color: '#fff'
            },
   label: {
     normal: {
         position: ['50%', '52%'],
         align: 'center',
      formatter: function(v) {
        return (v.data*100).toFixed(0)+"%" + '\n媒体友好度';
    },
    textStyle: {
        fontSize: 18,
        color:'#f9a34e'
        }
     }
   }
            }]
    }];

var charts = [];
for (var i = 0; i < option.length; ++i) {
    var chart = echarts.init(containers[i]);
    chart.setOption(option[i]);
    charts.push(chart);
};

//setInterval(update, 3000);
//重新定义所有图表自适应容器大小
setTimeout(function() {
	window.onresize = function() {
		eventTrendChart.resize();
		sourceTypeChart.resize();
		relevantChart.resize();
		emotionProChart.resize();
		mapChart.resize();
		mapDisChart.resize();
	}

});