var myChart = [],
 echartsOpts = {
	line:{	//热度指数趋势折线图
       	 animation : false,
       	 tooltip : {
	          trigger: 'axis',
	          axisPointer:{
	        	type:'line',
	        	lineStyle:{
	        		color:'#00bcd4',
	        		width:2
	        	}
	          },
	          formatter:function(params){
	            v = params[0].name;
	            for (var i = 0, l = params.length; i < l; i++) {
	              v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
	            }
	            return v;
	          }
	        },
	        legend: {
	        	top:'5%',
	        	left:'3%',
	        	textStyle:{
	        		color:'#39ae7d',
	        		fontSize:12,
	        		fontWeight:500
	        	},
	        	data:[]
	        },
	        grid:{
	          left:'10%',
	          right:'5%',
	          top:'15%'
	        },
	        xAxis:[{
	          type : 'category',//category|time
	          boundaryGap: false ,
	          data : [],
	          axisLine: {
	            onZero: false,
	            show:false
	          },
	          splitLine:{
	            show:false
	          },
	          splitNumber:0,
	          axisLabel : {
	            textStyle : {
	              decoration: 'none',
	              fontFamily: 'Microsoft YaHei',
	              fontSize: 12,
	            },
	            formatter:function(v){
	            
	                return v.substring(5,v.length);
	            }
	          },
	        }
	        ],
	        yAxis : [{
	          type : 'value',
	          axisLine: {
	            onZero: false,
	            show:false
	          },
	          splitLine:{
	            show:false
	          },
	          splitArea:{
	            show:true,
	            areaStyle:{
	              color:['#FFF','#F7F7F7']
	            }
	          },
	          axisLabel:{
	            formatter:function(v){
	              if(v>=1000){
	                return (v/1000)+"k";
	              }else{
	                return v;
	              }
	            }
	          },
	        }],
	        calculable : false,
	        series : [{
	        	showAllSymbol:true,
	        	smooth:true,  //设置曲线变平滑显示  
	        	type:'line',
	        	data:[]
	        }]
	    },
	wordCloud:{//字符词云
		 tooltip : {}, 
		 maskImage:null,
	     series : [ {  
	            type : 'wordCloud',  
	            shape:'circle', 
	            width: '100%',
	            height: '100%',
	            gridSize : 1,  //词间距
	            sizeRange : [ 12, 60 ],//字体大小 范围
	            rotationRange : [ -90, 90 ],  //字体旋转角度范围
	            textStyle : {  
	                normal : {  
	                    color : function() {  
	                        return 'rgb('  
	                                + [ Math.round(Math.random() * 160),  
	                                        Math.round(Math.random() * 160),  
	                                        Math.round(Math.random() * 160) ]  
	                                        .join(',') + ')';  
	                    }  
	                },  
	                emphasis : {  
	                    shadowBlur : 10,  
	                    shadowColor : '#333'  
	                }  
	            },  
	          data : []  
	     }]  
	},
	map:{
		   animation : false,
		   left:'center',
		   top:'center',
	        title : {
	            text : '',
	            subtext : '',
	        },
	        tooltip : {
	            trigger : 'item',
	            enterable:true,
	            textStyle : {
	                fontSize : 12
	            }
	        },
	        visualMap:{
	        	type:'continuous',
	        	min: 0,
	            max: 10000,
	            itemHeight:100,
	            left: 'left',
	            top: 'bottom',
	            text: ['高','低'],           // 文本，默认为数值文本
	            calculable: true,
	            inRange:{
	            	color:['#f3d647','#f29300','#d44e24']
	            }
	        },
			series:[{
				type:'map',
				map:'china',
				roam:false,
				selectedMode : 'single',//single|multiple
				label: {
		                normal: {
		                    show: true
		                },
		                emphasis: {
		                    show: true
		                }
		        },
		        itemStyle : {
	                normal : {
	                    label : {
	                        show : true
	                    },
	                    borderColor:'#FFF'
	                },
	                emphasis : {label : {show : true} }
	            },
	            data:[]
			}]
	},
	 map1:{
         series : [
             {
                 name: 'Map',
                 type: 'map',
                 map:'china',
                 mapLocation: {
                     top: 'center',
                     left: 'center'
                 },
                 label:{
                 	normal:{
                 		show:true	
                 	},
                 	emphasis:{
                 		show: true,
                         textStyle: {
                             color: '#fff'
                         }
                 	}
                 },
                 itemStyle: {
                     normal: {
                         borderWidth: 2,
                         borderColor: '#d5d5d5',
                         color: '#ccc',
                         
                     },
                     emphasis: {                 // 也是选中样式
                         borderWidth: 2,
                         borderColor: '#d5d5d5',
                         color: '#92D0FD',
                     }
                 },
                 data: [
                     {
                         name: '江西',
                         value: Math.round(Math.random() * 1000),
                         label:{
                         	normal:{
                         		 show: true,
                                  textStyle: {
                                      color: '#fff',
                                      fontSize: 15
                                  }
                         	},
                         	emphasis:{
                         		 show: false,
                                  textStyle: {
                                      color: '#92D0FD'
                                  }
                         	}
                         },
                         itemStyle: {
                             normal: {
                                 color: '#92D0FD'
                             },
                             emphasis: {                 // 也是选中样式
                                 borderWidth: 1,
                                 borderColor: '#d5d5d5',
                                 color: '#92D0FD' 
                             }
                         }
                     }
                 ]
             }
         ]
     },
    bar:{
    	animation : false,
        tooltip : {         // Option config. Can be overwrited by series or data
            trigger: 'axis',
        },
//        legend:{
//        	 textStyle:{
//             	color:'#39ae7d'
//             },
//        },
        xAxis:[{
            type : 'category',
            data : [],
            axisLine: {
                onZero: false,
                show:false
            },
            
            axisLabel : {
                formatter: function(value){
                    if(value.length>4){
                        value = value.substring(0,6);
                    }
                    return value;
                },
                rotate:45,
                textStyle : {
                    decoration: 'none',
                    fontFamily: 'Microsoft YaHei',
                    fontSize: 12,
                }

            },
            axisTick:{
            	show:false
            },
            splitArea: {
                show: false
            },
            splitLine:{
            	show:false
            }
        }],
        grid:{y2:80, x:55, x2:30},
        yAxis : [{type : 'value', data : [],axisLine: {onZero: false,show:false},
            splitLine:{show:false},
            
            splitArea:{show:true, areaStyle:{ color:['#FFF','#F7F7F7']}},
            axisLabel:{textStyle : {decoration: 'none',fontFamily: 'Microsoft YaHei',fontSize: 12,},
                formatter:function(v){
                    if(v>=1000){
                        return (v/1000)+"k";
                    }else{
                        return v;
                    }
                }
            },
            axisPointer:{
            	show:true,
            	lineStyle:{
            		color:'#008acd',
            		width:2
            	}
            }
        }],
        /*color:['#87cefa','#ff7f50'],*/
        series : [{
            name:'数量',
            type:'bar',
            data:[],
        }]
    },
    graph:{
    	symbolSize:10, //关系图节点大小
    	tooltip : {
	        trigger: 'item',
	        formatter: function (params) {
	            if (params.indicator2) {    // is edge
	                return params.indicator2 + ' ' + params.name + ' ' + params.indicator;
	            } else {    // is node
	                return params.name
	            }
	        }
	    },
	    legend: {
	        x: 'left',
//	        textStyle:{
//	        	color:'#39ae7d'
//	        },
	        data:[{
	        	name:null,
	        	icon:'rect'
	        }]
	    },
	    animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
	    series : [
	        {
	            name: '',
	            type:'graph',
	            layout : 'circular',//和弦图
	            circular: {
	            	rotateLabel:true//启用旋转标签
	            },
	        //    draggable:true,//是否可拖拽
	            roam: true,
                label: {
                    normal: {
                    	show:true,
                        position: 'right',
                        formatter: '{b}'
                    }
                },
                lineStyle: {
                    normal: {
                        color: 'source',
                        curveness: 0.3 //边的曲度，支持从 0 到 1 的值，值越大曲度越大。
                    }
                },
	            // 使用 nodes links 表达和弦图
                data: [],
	            links: []
	        }
	    ]
    }
},

//刷新图表数据
refresh = function($id,opt){
	if(!opt.noDataText){
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

//设置图表数据
setEchartsOpion = function(opts){
    var type = opts.$id.data("type");
    myChart[opts.$id.prop("id")] = echarts.init(opts.$id[0],"macarons");
    if(opts.event){
    	 myChart[opts.$id.prop("id")].on(echarts.config.EVENT.CLICK,opts.event);
    }
    //深度拷贝
    refresh(opts.$id,$.extend(true,{},echartsOpts[type],opts.opt));
};