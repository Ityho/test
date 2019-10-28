/*  //折线图 3
        var hotTrendChart = echarts.init(document.getElementById('hotTrendChart'));

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
        top:'6%',
        bottom: '15%',
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
                var len = 19;
                while (len--) {
                    res.unshift(now.toLocaleTimeString().replace(/^\D*//*,''));
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
     "dataZoom": [{
        "show": true,
        "height": 25,
        "xAxisIndex": [
            0
        ],
        bottom: 5,
        "start": 0,
        "end": 100,
        handleIcon: 'path://M8.2,13.6V3.9H6.3v9.7H3.1v14.9h3.3v9.7h1.8v-9.7h3.3V13.6H8.2z M9.7,24.4H4.8v-1.4h4.9V24.4z M9.7,19.1H4.8v-1.4h4.9V19.1z',
        handleSize: '100%',
        handleStyle:{
            color:"#d3dee5",
            borderColor: 'rgba(0,0,0,.3)'
        },
        showDetail: false,
           textStyle:{
            color:"#000"},
           borderColor:"#90979c"
        
        
    }, {
        "type": "inside",
        "zoomLock": true,
        "show": true,
        "height": 15,
        "start": 1,
        "end": 35
    }],
    series : [
        {
            name:'三星',
            type:'line',
            symbol: 'circle',
            symbolSize: 10,
            itemStyle:{
            	normal:{
            		color: '#fff',
            		borderColor: '#db421b',
            		borderWidth: 2
            	}
            },
            lineStyle:{
            	normal:{
            		color: '#db421b',
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
            smooth: true,
            data:[50, 10, 221, 104, 450, 380, 610,50, 10, 221, 104, 450, 380, 610]
        },
        {
            name:'华为',
            type:'line',
            symbol: 'circle',
            symbolSize: 10,
            itemStyle:{
            	normal:{
            		color: '#fff',
            		borderColor: '#3fad7e',
            		borderWidth: 2
            	}
            },
            lineStyle:{
            	normal:{
            		color: '#3fad7e',
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
            smooth: true,
            data:[800, 482, 621, 404, 720, 510, 790,300, 482, 621, 404, 720, 510, 790]
        },
        {
            name:'小米',
            type:'line',
            symbol: 'circle',
            symbolSize: 10,
            itemStyle:{
            	normal:{
            		color: '#fff',
            		borderColor: '#5573b6',
            		borderWidth: 2
            	}
            },
            lineStyle:{
            	normal:{
            		color: '#5573b6',
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
            smooth: true,
            data:[200, 400, 291, 84, 690, 30, 390,200, 400, 291, 84, 690, 30, 390]
        }
        
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        hotTrendChart.setOption(option);


//柱状图横向
        var sourceTypeChart = echarts.init(document.getElementById('sourceTypeChart'));

        // 指定图表的配置项和数据
        var option = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    toolbox: {
    	  orient:'vertical',
        feature: {
            saveAsImage: {}
        }
    },
    legend: {
    	top: '5px',
      data:[{name:'鹰眼舆情',icon:'rect'}]
    },
    grid: {
        left: '0%',
        right: '5%',
        bottom: '5%',
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
            
            boundaryGap : true,
            data : ['微博','论坛','网站','新闻','微信','客户端','博客','政务','报刊','视频','境外']
        }
    ],
    series : [
        {
            name:'鹰眼舆情',
            type:'bar',
            barWidth: '50%',
            itemStyle:{
            	normal:{
            		color: '#3fad7e'
            	}
            },
            data:[1230, 6812, 301, 3334, 4890, 2130, 8520,950,1850,4750,1234]
        }
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        sourceTypeChart.setOption(option);
        
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
    toolbox: {
    	  orient:'vertical',
        feature: {
            saveAsImage: {}
        }
    },
    visualMap: {
        min: 0,
        max: 2500,
        left: '0',
        bottom: '20',
        text: ['高','低'],           // 文本，默认为数值文本
        calculable: true,
        inRange: {
                color: ['#7097cd','#5aa2c7', '#52aeb0']
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
                    show: true,
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


//柱状图横向
        var mapDisChart = echarts.init(document.getElementById('mapDisChart'));

        // 指定图表的配置项和数据
        var option = {
        	backgroundColor:'#fbfbfd',
    title: {
        text: '地域',
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
    toolbox: {
    	  orient:'vertical',
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '12%',
        bottom: '5%',
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
            
            boundaryGap : true,
            data : ['10 四川','9 北京','8 广东','7 山东','6 江苏','5 海外','4 安徽','3 浙江','2 海南','1 上海']
        }
    ],
    series : [ 
    {
            type: 'bar',
            itemStyle: {
                normal: {color: 'rgba(0,0,0,0.2)'}
            },
            barGap:'-100%',
            barCategoryGap:'20%',
             data:[10000, 10000, 10000, 10000, 10000, 10000, 10000,10000,10000,10000],
            animation: false
        },
        {
            name:'鹰眼舆情',
            type:'bar',
 
            barWidth: '40%',
            itemStyle:{
            	normal:{
            		color: '#0086ce'
            	}
            },
            data:[4230, 6812, 3301, 3334, 4890, 2130, 8520,3950,1850,4750]
        }
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        mapDisChart.setOption(option);
        */

        //重新定义所有图表自适应容器大小
        /*setTimeout(function() {
        	window.onresize = function() {
        		hotTrendChart.resize();
        		sourceTypeChart.resize();
        		mapChart.resize();
        		mapDisChart.resize();
        	}

        });*/
function goAssociationView2(dom,zx,pa){
	//饼状图
    var scatterChart = echarts.init(document.getElementById(dom));

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
    symbolSize: 80,
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
            color: '#5378c5',
            opacity: 1
        }
    },
    data: [zx
//        [50, 50, '手机', '#8570b0']
    ],
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

var datalist = pa
//	[
///**
// * x坐标 
// * y坐标 
// * name 标签名称
// * symbolSize 点大小
// * 趋势，1:上升 0:下降
// */
//[14, 16, '苹果', 50, 1],
//[70, 50, '华为华为华为华为华为华为华为华为华为华为', 30, 1],
//[90, 50, '助手', 20, 1],
//[85, 90, '小米', 47, 1],
//[52, 83, '360', 10, 0],
//[72, 83, '定位', 20, 0],
//[47, 110, '下载', 30, 0]
//]


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
    zlevel: 2,
	label: {
		normal: {
			textStyle: {
				fontSize: 15
			}
		}
	}
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
    scatterChart.setOption(option);
    
    //重新定义所有图表自适应容器大小
   setTimeout(function (){
     window.onresize = function () {
        scatterChart.resize();
     }

   });
}
function goAssociationView(dom,zx,pa){
	 //饼状图
    var scatterChart = echarts.init(document.getElementById(dom));

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
    symbolSize: 80,
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
            color: '#eaa269',
            opacity: 1
        }
    },
    data: [zx
//        [50, 50, '手机', '#8570b0']
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

var datalist = pa
//	[
///**
// * x坐标 
// * y坐标 
// * name 标签名称
// * symbolSize 点大小
// * 趋势，1:上升 0:下降
// */
//[14, 16, '苹果', 50, 1],
//[70, 50, '华为华为华为华为华为华为华为华为华为华为', 30, 1],
//[90, 50, '助手', 20, 1],
//[85, 90, '小米', 47, 1],
//[52, 83, '360', 10, 0],
//[72, 83, '定位', 20, 0],
//[47, 110, '下载', 30, 0]
//]


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
    zlevel: 2,
	label: {
		normal: {
			textStyle: {
				fontSize: 15
			}
		}
	}
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
    scatterChart.setOption(option);
    
    //重新定义所有图表自适应容器大小
   setTimeout(function (){
     window.onresize = function () {
        scatterChart.resize();
     }

   });
		}

  //查询页面
  function toggleHeatTab(type,obj){
      if($("#userId").val()<=0){
          jumpLogin();
          return;
      }
      if($(obj).parent("li").hasClass("active")) {
          return;
      }
      if(type == 1){//热度查询
          location.href = actionBase + "/heatRankingList.action";
      }else if(type == 2){//查询记录
          location.href = actionBase + "/findLogSearch.action?page=1";
      }else if(type == 3){//热词订阅
          location.href = actionBase + "/findHotReport.action";
      }else if(type == 4){//排行榜
      }else if(type == 5) {//热度报告
          location.href = actionBase + "/findHotReportRecord.action";
      }
  }

