var warnHtml = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml1 = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 60px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml2 = '<div style="text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 30px;"><br><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';

/*情绪构成start*/
//情绪构成
function emotionStatAnalysisChartCallback(data, index){


	if(data == null || data.data == null || data.data.length == 0){
		if(index == ""){
			$("#emotionpieEchart").html(warnHtml1);
			$("#emotionpieEchart2").html(warnHtml1);
		}else{
			$("#emotionpieEchart" + index).html(warnHtml1);
		}
	}else{
		if(index == ""){
			emotionStatAnalysisChart1(data, index);
			emotionStatAnalysisChart2(data);
		}else{
			emotionStatAnalysisChart1(data, index);
		}
	}
}

function emotionStatAnalysisChart1(data, index){
	if(data == null || data.data == null || data.data.length == 0){
		$('#emotionpieEchart' + index).html(warnHtml1);
		return;
	}
	$('#emotionpieEchart' + index).removeAttr("_echarts_instance_");
	
	var colors = data.color;
	var data = data.data;
	var datas = new Array();
	var legend = new Array();
	for(n in data){
		var color = "";
		if(data[n].name == "喜悦"){
			color = colors.xy;
		}else if(data[n].name == "愤怒"){
			color = colors.fn;
		}else if(data[n].name == "悲伤"){
			color = colors.bs;
		}else if(data[n].name == "惊奇"){
			color = colors.jq;
		}else if(data[n].name == "恐惧"){
			color = colors.kj;
		}else if(data[n].name == "中性"){
			color = colors.zx;
		}
		legend.push({
			name : data[n].name,
			textStyle: {
				color: color,
				/*fontSize: 12,*/

			}
		});
		
		datas.push({
			value: data[n].value,
			name: data[n].name,
			itemStyle: {
				normal: {
					color: color,
					borderColor: '#fff',
					borderWidth: 2
				}
			}
		});
	}
	
	var chartDemo = echarts.init(document.getElementById('emotionpieEchart' + index));
	// 指定图表的配置项和数据
	var option = {
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		grid: {
			top:'0',
			left: '10%',
			right: '15%',
			bottom: '6%',
		},
		legend: {
			orient: 'vertical',
			top: '10',
			left: 'left',
			itemGap: 10,
			itemWidth: 20,
			itemHeight: 15,
			borderRadius: 0,
			data: legend
		},
		/*toolbox: {
			feature: {
				saveAsImage: {}
			}
		},*/
		series: {
			name: '情绪构成',
			type: 'pie',
			radius: ['40%', '60%'],
			center: ['55%', '55%'],
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
			data: datas,

		}
	};
	// 使用刚指定的配置项和数据显示图表。
	chartDemo.setOption(option);
	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}

function emotionStatAnalysisChart2(data){
	if(data == null || data.data == null || data.data.length == 0){
		$('#emotionpieEchart2').html(warnHtml1);
		return;
	}
	$('#emotionpieEchart2').removeAttr("_echarts_instance_");
	var colors = data.color;
	var data = data.data;

	data.reverse();
	var datas = new Array();
	var yAxis = new Array();
	for(n in data){
		var color = "";
		if(data[n].name == "喜悦"){
			color = colors.xy;
		}else if(data[n].name == "愤怒"){
			color = colors.fn;
		}else if(data[n].name == "悲伤"){
			color = colors.bs;
		}else if(data[n].name == "惊奇"){
			color = colors.jq;
		}else if(data[n].name == "恐惧"){
			color = colors.kj;
		}else if(data[n].name == "中性"){
			color = colors.zx;
		}
		yAxis.push(data[n].name);
		datas.push({
			name: data[n].name,
			value: data[n].value,
			itemStyle: {
				normal: {
					color: color,
					barBorderRadius: [0, 5, 5, 0]
				}
			}
		});
	}
	var chartDemo = echarts.init(document.getElementById('emotionpieEchart2'));

	// 指定图表的配置项和数据
	var option = {
		tooltip: {
			trigger: 'axis',
			axisPointer: { // 坐标轴指示器，坐标轴触发有效
				type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
			}
		},
		/*toolbox: {
			orient: 'vertical',
			feature: {
				saveAsImage: {}
			}
		},*/
		grid: {
			left: '5%',
			right: '5%',
			bottom: '15%',
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
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#888'
				}
			}
		}],
		yAxis: [{
			type: 'category',
			position: 'bottom',
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
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#888'
				}
			},

			boundaryGap: false,
			data: yAxis
		}],
		series: [{
			name: '情绪声量',
			type: 'bar',
			barWidth: 24,
			itemStyle: {
				normal: {
					color: '#3fad7e'
				}
			},
			data: datas
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	chartDemo.setOption(option);

	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}


/*情绪构成end*/

/*情绪地图start*/
function emotionMapChartsAjax(demoIndex) {
	var params;
	if(demoIndex != "")
		params = getParams(parseInt(demoIndex)-1);
	else
		params = getParams();
    $.ajax({
        url: actionBase + '/view/openTools/emotionMapChartOTChart.action',
        type:'POST',
        data:params,
        beforeSend:function(){
            
            for(var i=1;i<=7;i++) {
                loading("mapChart" + i + demoIndex);
                loading("tablemapChart" + i + demoIndex);
            }
        },
        success:function (result) {
        	if(result!=null && result.map1 != null && result.map2 != null){
                callEmotionMapXZ(result.map1,'mapChart1'+demoIndex);	//显著情绪
                callEmotionMapEvery(result.map2, demoIndex);	//各情绪
        	}else{
                for(var i=1;i<=7;i++) {
                    $("#mapChart" + i + demoIndex).html(warnHtml);
                    $("#tablemapChart" + i + demoIndex).html(warnHtml);
                }
        	}
        }
    })
}
function callEmotionMapXZ(result,demo) {
    if(result!=null){
        emotionIndexTable(result[0],demo,null,1,2);	//top10
        emotionMapCharts(result,demo);	//显著情绪地图
    }
}

//top10
function emotionIndexTable(result,demo,emotion,type,home){
    var classz="";
    if (home==1){
        classz="table table-th-lg";
    }else{
        classz="table table2 table3 table-striped table-th-w";
    }
    if (result != null) {
        var html = '';
        html+='<table border="0" cellspacing="0" cellpadding="0" class="'+classz+'" width="100%">';
        html+='<thead>';
        html+='<tr>';
        html+='<th width="80px">排名</th>';
        html+='<th width="">地区</th>';
        if(emotion==null){
            html+='<th width="125px">微博总声量</th>';
        }else{
            html+='<th width="125px">微博声量</th>';
        }
        if(emotion==null) {
            html += '<th width="105px">显著情绪</th>';
        }else{
            html += '<th width="105px">情绪标签</th>';
        }
        html+='</tr>';
        html+='</thead>';
        html+='<tbody>';
        var index = 0;
        $.each(result, function (i, n) {
            if (index == 10 || n == null) {
                return true;
            }
            var province = "";
            var num = 0;
            if(type==1){
                province = n[0];
                num = formatNum(n[1]);
                emotion = n[2];
            }else{
            	province = n.name;
                num = n.value;
            }
            html += '<tr>';
            html += '<td class="num"><em>' + (index + 1) + '</em></td>';
            html += '<td>' + province + '</td>';
            html += '<td>' + num + '</td>';
            if (emotion == "愤怒")
                html += '<td><span class="badge red">愤怒</span></td>';
            if (emotion == "恐惧")
                html += '<td><span class="badge black">恐惧</span></td>';
            if (emotion == "喜悦")
                html += '<td><span class="badge yellow">喜悦</span></td>';
            if (emotion == "惊奇")
                html += '<td><span class="badge yellowGreen">惊奇</span></td>';
            if (emotion == "悲伤")
                html += '<td><span class="badge blue">悲伤</span></td>';
            if (emotion == "中性")
                html += '<td><span class="badge" style="background-color:#d6d6d6;">中性</span></td>';
            html += '</tr>';
            index++;
        });
        if (index==0){
            $("#table" + demo).html(warnHtml);
        }
        html += '</tbody>';
        html += '</table>';
        //html += '<p style="text-align: left;margin-top: 5px;">仅展示我国34个省级行政区域（包括23个省，5个自治区，4个直辖市，以及香港，澳门2个特别行政区）的声量TOP10</p>';
        $("#table" + demo).html(html);
    }else{
        $("#table" + demo).html(warnHtml);
    }
}

//显著情绪地图
function emotionMapCharts(data,demo){
    $("#"+demo).empty();
    $("#"+demo).removeAttr('_echarts_instance_');
	var emotions = data[1];
    var color_option = data[3];
	data[2].push({
		name: '南海诸岛',
		itemStyle: {
			normal: {
				borderColor: '#959595',
				areaColor: '#efefef', 
			}
		}
	});
    var myChart = echarts.init(document.getElementById(demo));
    var option = {
		tooltip : {
			trigger: 'item'
		},
		visualMap:{
			show:true,
			type: 'piecewise',
			orient: 'vertical',
			x:'left',
			y:'10',
			categories: data[1],
			inverse: true,
			pieces: [
				{value: 0, label: emotions[0], color: color_option.xy}, 
				{value: 1, label: emotions[1], color: color_option.zx}, 
				{value: 2, label: emotions[2], color: color_option.fn},
				{value: 3, label: emotions[3], color: color_option.bs},
				{value: 4, label: emotions[4], color: color_option.jq},
				{value: 5, label: emotions[5], color: color_option.kj},
				]
		},
		/*toolbox: {
			show: true,
			orient: 'vertical',
			left: 'right',
			top: 'center',
			feature: {
				saveAsImage: {}
			}
		},*/
		series : [
			{
				name: '显著情绪',
				type: 'map',
				mapType: 'china',
				showLegendSymbol:false,
				roam: false,
				zoom: 1.2,
				itemStyle:{
					normal:{
						label:{show:false},
						borderWidth:1,
						borderColor:'#FFFFFF'
					},
					emphasis:{
						label:{show:true}
						
						
					}
				},
				data:data[2],
				tooltip:{
					textStyle:{
						align:'left'
					},
					formatter:function(a){
						var label = '显著情绪 - ' + emotions[a.value] + "<br/>";
						var emotion = a.data.emotion;
						if(emotion != null){
							for(var i=0; i<emotion.length; i++){
								label += emotion[i].name + " : ";
								if(emotion[i].value > 0){
									label += emotion[i].value;
								}else{
									label += "-";
								}
								label += "<br/>";
							}
						}else{
							label = "暂无信息";
						}
						return label;
					}
				}
			}
		]
	};
    
    var title = $("#emotionMapTitle").val();
    if(title != null && title != ''){
    	option.title = {
            text: title,
            left: 'center',
            textStyle: {
                color: '#717171',
                fontWeight: 'normal',
                fontSize:14
            }
        };
    }
	myChart.setOption(option);
	setTimeout(function (){
		window.onresize = function () {
			myChart.resize();
		}
	})
}


function callEmotionMapEvery(result, demoIndex) {
	var lis = $(".legend" + demoIndex + " li");
    for(var i=0;i<6;i++){
        if(result==null){
            $("#mapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml);
            $("#tablemapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml);
        }else{
            var data=result[i];
			
			var index = i+1;
        	var className = "";
        	var active = false;
        	if($(lis[index]).hasClass("active"))
        		active = true;
        	$(lis[index]).attr("class","");
        	if(data.name == "愤怒"){
        		className = "qx_2";
        	}else if(data.name == "恐惧"){
        		className = "qx_3";
        	}else if(data.name == "悲伤"){
        		className = "qx_6";
        	}else if(data.name == "惊奇"){
        		className = "qx_5";
        	}else if(data.name == "喜悦"){
        		className = "qx_4";
        	}else if(data.name == "中性"){
        		className = "qx_7";
        	}
        	$(lis[index]).addClass(className);
        	if(active)
        		$(lis[index]).addClass("active");
        	$(lis[index]).text(data.name);
            if(data != null && data.data != null && data.data.length>0){
                try{
                    emotionIndexTable(data.data,"mapChart"+(parseInt(i) + parseInt(2) + demoIndex),data.name,0);	//top10
                    emotionMapCharts2(data,"mapChart"+(parseInt(i) + parseInt(2)) + demoIndex);
                }catch (e){
                    console.log(e.message);
                }
            }else{
                $("#mapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml);
                $("#tablemapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml);
            }
        }
    }
}

//各情绪地图
function emotionMapCharts2(data,demo){
    $("#"+demo).empty();
    $("#"+demo).removeAttr('_echarts_instance_');
    var color_option_fn=['#cf421e','#d3582d','#d86b3d','#dd7f52','#e19167'];
    var color_option_kj=['#313131','#423e3d','#544d4d','#665e5b','#79716d'];
    var color_option_xy=['#f18d00','#ea9630','#eca147','#efae5e','#f2ba76'];
    var color_option_jq=['#3fa579','#5da782','#74b08e','#8aba9c','#9dc4aa'];
    var color_option_bs=['#0e7dc0','#4381ba','#5e8dc1','#759ac8','#8ba8d0'];
    var color_option_zx=['#9098a5','#9ba0ab','#a6aab4','#b2b4bd','#bdbec6'];
    var colormap=[];
    if(data.name=="愤怒"){
        colormap = color_option_fn;
    }
    if(data.name=="恐惧"){
        colormap = color_option_kj;
    }
    if(data.name=="喜悦"){
        colormap = color_option_xy;
    }
    if(data.name=="惊奇"){
        colormap = color_option_jq;
    }
    if(data.name=="悲伤"){
        colormap = color_option_bs;
    }
    if(data.name=="中性"){
        colormap = color_option_zx;
    }
    var max=0;
    var min=0;
    var svg=0;
    if(data.data.length>0){
        max=data.data[0].value;
        min=data.data[data.data.length-1].value;
        svg=Math.ceil((max-min)/5);
        if(svg==0){
            svg=max;
        }
		data.data.push({
			name: '南海诸岛',
			itemStyle: {
				normal: {
					borderColor: '#959595',
					areaColor: '#efefef', 
				}
			}
		});
    }
    var sourceTypeChart = echarts.init(document.getElementById(demo));
    var pieces = [
        {min: min,max: svg,color:colormap[4]},
        {min: svg+1,max: svg*2,color:colormap[3]},
        {min: svg*2+1,max: svg*3,color:colormap[2]},
        {min: svg*3+1,max: svg*4,color:colormap[1]},
        {min: svg*4+1,max: max,color:colormap[0]}
        ];
    option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            show:false,
            data:data[1]
        },
        visualMap: {
            type: 'piecewise', //分段型。
            splitNumber: 5,
            color: '#ebdba4',
            pieces:pieces,
            left: 'left',
            top: 'bottom',          // 文本，默认为数值文本
            calculable: true
        },
        /*toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
                saveAsImage: {}
            }
        },*/
        series: [
            {
                name: data.name,
                type: 'map',
                mapType: 'china',
                roam: false,
                showLegendSymbol:false,
                label: {
                    normal: {
                        show: false,
                    },
                    emphasis: {
                        show: false
                    }
                },
                zoom:1.2,
                itemStyle:{
                    normal:{
                        borderWidth:1,
                        borderColor:'#FFF'
                    }
                },
                data:data.data
            }
        ]
    };
    sourceTypeChart.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            sourceTypeChart.resize();
        }
    });
}



//格式化数字，从右向左每三个一个逗号xx,xxx
function formatNum(number){
    var n=0;
    number="'"+number+"'";
    if(n != 0 ){
        n = (n > 0 && n <= 20) ? n : 2;
    }
    number = parseFloat((number + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var sub_val = number.split(".")[0].split("").reverse();
    var sub_xs = number.split(".")[1];

    var show_html = "";
    for (i = 0; i < sub_val.length; i++){
        show_html += sub_val[i] + ((i + 1) % 3 == 0 && (i + 1) != sub_val.length ? "," : "");
    }
    if(n == 0 ){
        return show_html.split("").reverse().join("");
    }else{
        return show_html.split("").reverse().join("") + "." + sub_xs;
    }

}
/*情绪地图end*/
/*情绪走势start*/

function emotionLineChartCallback(data, demo){
	
	if(data == null){
		$("#"+demo).html(warnHtml);
		return;
	}
	var informationChart = echarts.init(document.getElementById(demo));
	var option = {
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			top: '0px',
			data: data.legend
		},

		grid: {
			left: '2%',
			right: '2%',
			top: '18%',
			bottom: '15%',
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
			data: data.dates
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
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#888'
				}
			},
		}],
		series: [{
				name: '喜悦',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#F08C00',
						borderColor: '#F08C00',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#F08C00',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.xy
			},
			{
				name: '愤怒',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#CF421E',
						borderColor: '#CF421E',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#CF421E',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.fn
			}, {
				name: '悲伤',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#0E7DC0',
						borderColor: '#0E7DC0',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#0E7DC0',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.bs
			}, {
				name: '惊奇',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#3FA579',
						borderColor: '#3FA579',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#3FA579',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.jq
			}, {
				name: '恐惧',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#313131',
						borderColor: '#313131',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#313131',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.kj
			}, {
				name: '中性',
				type: 'line',
				symbol: 'emptyCircle',
				symbolSize: 10,
				itemStyle: {
					normal: {
						color: '#6E7584',
						borderColor: '#6E7584',
						borderWidth: 2
					}
				},
				lineStyle: {
					normal: {
						color: '#6E7584',
						width: 2,
						type: 'solid'
					}
				},
				areaStyle: {
					normal: {
						color: 'rgba(0,0,0,0.5)',
						opacity: 0
					}
				},
				smooth: true,
				data: data.data.zx
			}

		]
	};

	// 使用刚指定的配置项和数据显示图表。
	informationChart.setOption(option);
	setTimeout(function (){
        window.onresize = function () {
            informationChart.resize();
        }
    });

}

/*情绪走势end*/

/*微博表情start*/
//情绪表情聚类
function weiboExpressionChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	var html = "";
	var max = data.max;
	var datas = data.data;
	for(n in datas){
		/*var color = "#0E7DC0";
		if(n%2 == 0)
			color = "#F4AA53";*/
		var pre = 100*datas[n].value/max;
		html += '<li><p class="weiboIcon-pic"><img src="' + datas[n].name + '"/></p><div class="weiboIcon-bar">';
		html += '<p style="width: ' + pre + '%;"><span>' + datas[n].value + '</span></p>';
		html += '<p style="width: 100%;background-color: #edeef0;margin-top: -21px; z-index: 1;box-shadow: none;"></p></div></li>';
	}						
	$("#" + demo).html(html);
}
/*微博表情end*/

/*用户情绪观洞察start*/

//性别情绪分布
function sexEmotionAnalysisChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}
	var emotionBar5 = echarts.init(document.getElementById(demo));
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
			itemGap: 150,
			data: [{
				name: "男",
				textStyle: {
					fontSize: 16,
					color: '#0E7DC0',
				}

			}, {
				name: "女",
				textStyle: {
					fontSize: 16,
					color: '#CF421E',
				}
			}],
			width: '100%',
			show: true,
			orient: 'horizontal',
			itemWidth: 0,
			itemHeight: 0
		},
		grid: [{
			show: false,
			left: '8%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '46%'
		}, {
			show: false,
			left: '51%',
			top: '10%',
			bottom: 40,
			width: '10%'
		}, {
			show: false,
			right: '8%',
			top: '10%',
			bottom: 20,
			containLabel: true,
			width: '46%'
		}, ],
		xAxis: [{
			gridIndex: 0,
			type: 'value',
			position: 'bottom',
			inverse: true,
			axisLine: {
				show: true,
				lineStyle:{
					color:"#798091"
				}
			},
			axisTick: {
				show: false,
			},
			axisLabel: {
				show: true,
			},
			splitLine: {
				show: false,
			},
			splitNumber: 1,
			max: data.max
		}, {
			gridIndex: 1,
			type: 'value',
			show: false,
		}, {
			gridIndex: 2,
			type: 'value',
			axisLine: {
				show: true,
				lineStyle:{
					color:"#798091"
				}
			},
			axisTick: {
				show: false,
			},

			axisLabel: {
				show: true,
			},
			splitLine: {
				show: false,
			},
			splitNumber: 1,
			max: data.max
		}, ],
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
					fontSize: 13,
				}

			},
			data: ['喜悦', '中性', '愤怒', '悲伤', '惊奇', '恐惧']

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
			splitLine: {
				show:true,
				lineStyle:{
					width:20,
					color:'#EEEEEE'
				}
			},
			data: [],
		}, ],
		series: [{
				name: '男',
				type: 'bar',
				stack: '男性情绪分布',
				xAxisIndex: 0,
				yAxisIndex: 0,
				z: 10,
				barWidth: 20,
				barCategoryGap: '10%',
				label: {
					normal: {
						show: true,
						position: 'left'
					}
				},
				data: [{
					name: '',
					value: data.m.xy,
					itemStyle: {
						normal: {
							color: data.color.xy,
						}
					}
				}, {
					name: '',
					value: data.m.zx,
					itemStyle: {
						normal: {
							color: data.color.zx,
						}
					}
				}, {
					name: '',
					value: data.m.fn,
					itemStyle: {
						normal: {
							color: data.color.fn,
						}
					}
				}, {
					name: '',
					value: data.m.bs,
					itemStyle: {
						normal: {
							color: data.color.bs,
						}
					}
				}, {
					name: '',
					value: data.m.jq,
					itemStyle: {
						normal: {
							color: data.color.jq,
						}
					}
				}, {
					name: '',
					value: data.m.kj,
					itemStyle: {
						normal: {
							color: data.color.kj,
						}
					}
				}]
			},
			{
				name: '空白',
				type: 'bar',
				stack: '男性情绪分布',
				xAxisIndex: 0,
				yAxisIndex: 0,
				z: 5,
				barWidth: 20,
				barCategoryGap: '10%',
				label: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: '#EEEEEE',
					},
					emphasis: {
						color: '#EEEEEE',
					},
				},
				tooltip:{
				  show:false  
				},
				data: [data.max-data.m.xy,
					data.max-data.m.zx,
					data.max-data.m.fn,
					data.max-data.m.bs,
					data.max-data.m.jq,
					data.max-data.m.kj,
				]
			},
			

			{
				name: '女',
				type: 'bar',
				stack: '女性情绪分布',
				z: 10,
				barCategoryGap: '20%',
				barWidth: 20,
				xAxisIndex: 2,
				yAxisIndex: 2,
				label: {
					normal: {
						show: true,
						position: 'right'
					}
				},
				data: [{
					name: '',
					value: data.f.xy,
					itemStyle: {
						normal: {
							color: data.color.xy,
						}
					}
				}, {
					name: '',
					value: data.f.zx,
					itemStyle: {
						normal: {
							color: data.color.zx,
						}
					}
				}, {
					name: '',
					value: data.f.fn,
					itemStyle: {
						normal: {
							color: data.color.fn,
						}
					}
				}, {
					name: '',
					value: data.f.bs,
					itemStyle: {
						normal: {
							color: data.color.bs,
						}
					}
				}, {
					name: '',
					value: data.f.jq,
					itemStyle: {
						normal: {
							color: data.color.jq,
						}
					}
				}, {
					name: '',
					value: data.f.kj,
					itemStyle: {
						normal: {
							color: data.color.kj,
						}
					}
				}]
			},
			{
				name: '空白',
				type: 'bar',
				stack: '女性情绪分布',
				xAxisIndex: 2,
				yAxisIndex: 2,
				z: 5,
				barWidth: 20,
				label: {
					normal: {
						show: false,
					}
				},
				itemStyle: {
					normal: {
						color: '#EEEEEE',
					},
					emphasis: {
						color: '#EEEEEE',
					},
				},
				tooltip:{
				  show:false  
				},
				data: [data.max-data.f.xy,
					data.max-data.f.zx,
					data.max-data.f.fn,
					data.max-data.f.bs,
					data.max-data.f.jq,
					data.max-data.f.kj,
				]
			}
		]
	};

	emotionBar5.setOption(option);
	setTimeout(function (){
        window.onresize = function () {
            emotionBar5.resize();
        }
    });
}

//微博等级情绪统计
function weiboLevelEmotionAnalysisChartCallback(data, demo){
	if(data == null){
		$("#"+demo).html(warnHtml1);
		return;
	}

	var indicator = new Array();
	for(n in data.emotion){
		indicator.push({
			name: data.emotion[n],
			max: 100
		});
	}
	
	var emotionBar6 = echarts.init(document.getElementById(demo));

	var option = {
		tooltip : {
			trigger: 'axis',
			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
				type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			},
			formatter: function(param){
				var html = param[0].name + "<br/>";
				for(n in param){
					html += param[n].marker + param[n].seriesName + ": " + param[n].value.toFixed(2) + "%<br/>";
				}
				return html;
			}
		},
		legend: {
		   data: data.emotion
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis:  {
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
				//rotate: 30,  //旋转角度
				textStyle: {
					color: '#888'
				}
			}
		},
		yAxis: {
			type: 'category',
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
			axisLabel: { //坐标轴刻度标签
				show: true,
				textStyle: {
					color: '#888'
				}
			},

			data: data.userType
		},
		series: [
			{
				name: '喜悦',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.xy,
						}
					},
				data: data.xy[0],
			},{
				name: '中性',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.zx,
						}
					},
				data: data.zx[0],
			},
			{
				name: '愤怒',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.fn,
						}
					},
				data: data.fn[0],
			},
			{
				name: '悲伤',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.bs,
						}
					},
				data: data.bs[0],
			},
			{
				name: '惊奇',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.jq,
						}
					},
				data: data.jq[0],
			},
			{
				name: '恐惧',
				type: 'bar',
				stack: '用户情绪',
				barWidth: 24,
				label: {
					normal: {
						show: false,
						position: 'insideRight'
					}
				},
				itemStyle: {
						normal: {
							color: data.color.kj,
						}
					},
				data: data.kj[0],
			}
		]
	};

	// 使用刚指定的配置项和数据显示图表。
	emotionBar6.setOption(option);

	setTimeout(function (){
        window.onresize = function () {
            emotionBar6.resize();
        }
    });
}
/*用户情绪观洞察end*/












