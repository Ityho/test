//排行榜查看详情
	function detailsBtn(me,n,rdzs,pm) {
		var trDetailsMould = $("#trDetailsMould").html();
		var $tbody = $(me).parents("tbody").siblings("tbody");
		$tbody.find(".trDate").removeClass("active");
		$tbody.find(".trDetails").hide();
		$tbody.find(".trDate").find("i").html("&#xe714;");
		$(me).parents(".trDate").toggleClass("active");
		$(me).parents(".trDate").next(".trDetails").toggle();
		if($(me).parents(".trDate").hasClass("active")) {
			$(me).find("i").html("&#xe713;");
// 			setTimeout( function(){
// 	           (rankingInformationCharts1+n).resize();
// 	           (rankingInformationCharts2+n).resize();
// 	       }, 300 );
			setTimeout( function(){
		        goHotChangeShow("rankingInformationCharts1"+n,rdzs,pm);
		        goAjaxFourShow(n);
           }, 300 );
		} else {
			$(me).find("i").html("&#xe714;");
		}
	}
	
	function goAjaxFourShowShow(dom,leng,dat,maxs){
		 //信息量
        var rankingInformationCharts2 = echarts.init(document.getElementById(dom));

        // 指定图表的配置项和数据
        var option = {
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        left: '3%',
        right: '16%',
        bottom: '10%',
        top:'10%',
        containLabel: false
    },
   
    xAxis : [
        {
            axisLine:{//坐标轴轴线 默认 true,
            	show: false
            	
            },
            axisTick:{//坐标轴刻度
            	show: false
            },
            splitLine:{
            	show: false
            },
            axisLabel:{//坐标轴刻度标签
            	show: false
            }
        }
    ],
     yAxis : [
        {
            type : 'category',
            axisLine:{//坐标轴轴线 默认 true,
            	show: false
            },
            axisTick:{//坐标轴刻度
            	show: false
            },
            axisLabel:{//坐标轴刻度标签
            	show: true,
            	interval:0,
            	//rotate: 30,  //旋转角度
            	textStyle:{
            		color:'#888'
            	}
            },
            inverse: true,
            boundaryGap : true,
            data : leng
        }      
    ],
    series : [ 
    {
            type: 'bar',
            itemStyle: {
                normal: {
                	color: 'rgba(0,0,0,0.1)',
                	barBorderRadius: 100
                	}
            },
            barGap:'-100%',
            barCategoryGap:'20%',
             data:maxs,
            animation: false,
            tooltip : {show:false}
        },
        {
            name:'信息量',
            type:'bar',
            label: {
            	normal:{
            		show: true,
            	    position: 'right'
            	}
            	
            },
            barWidth: '40%',
            itemStyle:{
            	normal:{
            		color: new echarts.graphic.LinearGradient(
                    	0,0,1,0,[{
                    		offset:0,
                    		color:'#fe5e00'
                    	},{
                    		offset:1,
                    		color:'#fe8b00'
                    	}]
                    ),
            		barBorderRadius: 100
            	}
            },
            data:dat
            
        }
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        rankingInformationCharts2.setOption(option);
	}
	
	function goHotChangeShow(dom,rdzs,pm){
		//热度值
        var rankingInformationCharts1 = echarts.init(document.getElementById(dom));

        // 指定图表的配置项和数据
       option = {   
    tooltip: {
    },
    
    series: [{
        name: '指数变化',
        type: 'pie',
        radius: ['68%', '70%'],
        center:['35%','50%'],
        color:'#ff7800',
        label: {
            normal: {
                position: 'center'
            }
        },
        data: [ {
            value: 100,
            name: '指数变化',
            label: {
                normal: {
                	formatter: '{name|指数变化\n}  {per|'+rdzs+'}',
                     rich: {
                     	name: {
                            color: '#444',
                            fontSize:14
                       },
                        per: {
                            color: '#ff7800',
                            fontSize:18,
                            padding: [0,0, 5,0]
                        }
                     }
                    
                }
            },
            itemStyle: {
                normal: {
                    color: '#ff7800'
                },
                emphasis: {
                    color: '#ff7800'
                }
            },
            tooltip : {show: false}
        }]
    },{
        name: '排名变化',
        type: 'pie',
        radius: ['68%', '70%'],
        center:['65%','50%'],
        color:'#473C8B',
        label: {
            normal: {
                position: 'center'
            }
        },        
        data: [{
            value: 50,
            name: '排名变化',
            label: {
                normal: {
                	formatter: '{name|排名变化\n}  {per|'+pm+'}',
                     rich: {
                     	name: {
                            color: '#444',
                            fontSize:14
                       },
                        per: {
                            color: '#ff7800',
                            fontSize:18,
                            padding: [0,0, 5,0]
                        }
                     }
                    
                }
            },
            
            itemStyle: {
                normal: {
                    color: '#ff7800'
                },
                emphasis: {
                    color: '#ff7800'
                }
            },  
             tooltip : {show:false}
        }]
    }]
};
        // 使用刚指定的配置项和数据显示图表。
        rankingInformationCharts1.setOption(option);
	}

    function GotoPage(i){
        $("#page").val(i);
        goRanking();
    }

    function sort(sort){
        var order = $("#order").val();
        var s = $("#sort").val();
        if(sort==s){
            order = order==1?2:1;
        }else{
            order = 1;
        }
        $("#sort").val(sort);
        $("#order").val(order);
        goRanking();
    }