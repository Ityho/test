var loaders = '<i class="icon-reload-a fa-spin"></i>';
var warnHtml = '<div style="text-align: center;margin-bottom: 120px;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';
var warnHtml1 = '<div style="text-align: center;margin-bottom: 120px;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 154px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span></div>';

function initChart() {
    emotionCountAjax();
	refushMapChart();
    emotionPieAjax();
    emotionLineAjax();
}
//地图刷新
function refushMapChart(){
    var date = $("#date").val();
    var keyword= $("#keyword1").val();
    var filterKeyword = $("#filterKeyword").val();
    var includeOthers = $("#includeOthers").val();
    emotionMapChartsAjax(keyword,filterKeyword,'mapChart', date,includeOthers, '');
}
//柱状图刷新
function refushBarPieChart() {
    emotionPieAjax();
}
//折线图刷新
function refushLineChart() {
    emotionLineAjax();
}


/*情绪地图start*/
function emotionMapChartsAjax(keyword, filterKeyword, demo, date, includeOthers, demoIndex) {
	
    if (includeOthers==""||includeOthers==null){
        includeOthers=1;
    }
    $.ajax({
        url:actionBase+'/en/emotion/emotionMapCharts.action',
        type:'POST',
        data:{'keyword':keyword,'date':date,'filterKeyword':filterKeyword,'includeOthers':includeOthers},
        
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
function callEmotionMapXZ(result,demo, demoIndex) {
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
            $("#table" + demo).html(warnHtml1);
        }
        html += '</tbody>';
        html += '</table>';
        //html += '<p style="text-align: left;margin-top: 5px;">仅展示我国34个省级行政区域（包括23个省，5个自治区，4个直辖市，以及香港，澳门2个特别行政区）的声量TOP10</p>';
        $("#table" + demo).html(html);
    }else{
        $("#table" + demo).html(warnHtml1);
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
						for(var i=0; i<emotion.length; i++){
							label += emotion[i].name + " : ";
							if(emotion[i].value > 0){
								label += emotion[i].value;
							}else{
								label += "-";
							}
							label += "<br/>";
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
            $("#tablemapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml1);
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
            if(data != null && data.data.length>0){
                try{
                    emotionIndexTable(data.data,"mapChart"+(parseInt(i) + parseInt(2) + demoIndex),data.name,0);	//top10
                    emotionMapCharts2(data,"mapChart"+(parseInt(i) + parseInt(2)) + demoIndex);
                }catch (e){
                    console.log(e.message);
                }
            }else{
                $("#mapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml);
                $("#tablemapChart"+(parseInt(i) + parseInt(2)) + demoIndex).html(warnHtml1);
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


function sendPostForm(url, target, params) {
    var temp = document.createElement("form");
    temp.action = url;
    temp.method = 'POST';
    temp.target = target;
    for(var x in params) {
        var opt = document.createElement('input');
        opt.type = 'hidden';
        opt.name = x;
        opt.value = params[x];
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
}

//柱状图ajax
function emotionPieAjax() {
    var date = $("#date").val();
    var keyword= $("#keyword1").val();
    var filterKeyword = $("#filterKeyword").val();
    var includeOthers = $("#includeOthers").val();
    if (includeOthers==""||includeOthers==null){
        includeOthers=1;
    }
    $.ajax({
        url:actionBase+'/en/emotion/emotionPie.action',
        type:'POST',
        data:{'keyword':keyword,'date':date,'filterKeyword':filterKeyword,'includeOthers':includeOthers},
        beforeSend:function(){
            $("#collapse5").loader('show',loaders);
            $("#emotionType").parents(".col-md-12").loader('show',loaders);
        },
        success:function (result) {
            $("#collapse5").loader('hide');
            $("#emotionType").parents(".col-md-12").loader('hide');
            if(result!=null){
                xinazhuEmotion(result);
                emotionBarChart(result,'sourceTypeChart');
                /*emotionPieChart(result,'pieChart');*/
            }else{
                $("#emotionType").html(warnHtml);
                $("#sourceTypeChart").html(warnHtml)
            }
        }
    })
}
//
function xinazhuEmotion(data){
	var emotion = "";
	if(data && data.data && data.data.length > 0){
		emotion = data.data[data.data.length-1].name;
	}else{
		return false;
	}
    $("#emotionType").html(emotion);
    if(emotion=="愤怒"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-fennu");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_red");
    }
    if(emotion=="恐惧"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-kongju");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_dark_grey");
    }
    if(emotion=="喜悦"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-xiyue");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_orange");
    }
    if(emotion=="惊奇"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-jingqi");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_yellowGreen");
    }
    if(emotion=="悲伤"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-beishang");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_blue");
    }
    if(emotion=="中性"){
        $("#emotionIcon").attr("class","fz60 abs icon-bq-zx");
        $("#emotionType").parents(".dashboard-stat").attr("class","dashboard-stat rel bg_gray");
    }
}
//
function emotionCountAjax() {
    var date = $("#date").val();
    var keyword= $("#keyword1").val();
    var filterKeyword = $("#filterKeyword").val();
    var includeOthers = $("#includeOthers").val();
    if (includeOthers==""||includeOthers==null){
        includeOthers=1;
    }
    $.ajax({
        url:actionBase+'/en/emotion/emotionCount.action',
        type:'POST',
        data:{'keyword':keyword,'date':date,'filterKeyword':filterKeyword,'includeOthers':includeOthers},
        beforeSend:function(){
            $(".bg_cobalt_blue").loader('show',loaders);
        },
        success:function (result) {
            $(".bg_cobalt_blue").loader('hide');
            if(result!=null){
                $("#emotionCount").html(formatNum(result));
            }else{
                $("#emotionCount").html(0);
            }
        }
    })
}

//饼状图
function emotionBarChart(data,demo) {
	
	$('#' + demo).removeAttr("_echarts_instance_");
	var colors = data.color;
	var data = data.data;

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
			value: data[n].count,
			itemStyle: {
				normal: {
					color: color,
					barBorderRadius: [0, 5, 5, 0]
				}
			}
		});
	}
	var chartDemo = echarts.init(document.getElementById(demo));

	// 指定图表的配置项和数据
	var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'line', // 默认为直线，可选为：'line' | 'shadow'
                lineStyle:{
                    width:0
                }
            }
        },
        legend: {
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            data: data[1]
        },
        toolbox: {
            orient: 'vertical',
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '0%',
            right: '5%',
            bottom: '5%',
           /* width:'70%',*/
            containLabel: true
        },

        xAxis: [
            {
                name: '条',
                nameTextStyle: {
                    color: '#0086ce'
                },
                type: 'value',
                axisLine: {//坐标轴轴线 默认 true,
                    show: true,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisTick: {//坐标轴刻度
                    show: true,
                    lineStyle: {
                        color: '#888',
                        width: 1,
                        type: 'solid'
                    }
                },
                splitLine: {
                    show: false
                },
                axisLabel: {//坐标轴刻度标签
                    show: true,
                    //rotate: 30,  //旋转角度
                    textStyle: {
                        color: '#888'
                    }
                }
            }
        ],
        yAxis: [
            {
                type: 'category',
                axisLine: {//坐标轴轴线 默认 true,
                    show: true,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisTick: {//坐标轴刻度
                    show: true,
                    lineStyle: {
                        color: '#888',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisLabel: {//坐标轴刻度标签
                    show: true,
                    //rotate: 30,  //旋转角度
                    textStyle: {
                        color: '#888'
                    }
                },

                boundaryGap: true,
                data: yAxis
            }
        ],
        series: [
            {
                name: '情绪声量',
                type: 'bar',
                barWidth: '30',
                itemStyle: {
                    normal: {
                        color: '#3fad7e'
                    }
                },
                data: datas
            }
        ]
    };
	// 使用刚指定的配置项和数据显示图表。
	chartDemo.setOption(option);

	setTimeout(function() {
		window.onresize = function() {
			chartDemo.resize();
		}
	});
}
//饼状图
function emotionPieChart(data,demo) {
    var borderWidth=0;
    if (data[1].length>1){
        borderWidth=2;
    }
    $.each(data[1],function () {
        this.label={'normal': {'textStyle': {'fontSize': 12}}};
        if(this.name=='愤怒'){
            this.itemStyle={'normal': {'color':'#cf421e', 'borderColor': '#fff', 'borderWidth': borderWidth}};
        }
        if(this.name=='恐惧'){
            this.itemStyle={'normal': {'color':'#333', 'borderColor': '#fff', 'borderWidth': borderWidth}};
        }
        if(this.name=='悲伤'){
            this.itemStyle={'normal': {'color':'#0e7dc0', 'borderColor': '#fff', 'borderWidth': borderWidth}};
        }
        if(this.name=='惊奇'){
            this.itemStyle={'normal': {'color':'#3fa579', 'borderColor': '#fff', 'borderWidth': borderWidth}};
        }
        if(this.name=='喜悦'){
            this.itemStyle={'normal': {'color':'#f18d00', 'borderColor': '#fff', 'borderWidth':borderWidth}};
        }
        if(this.name=='中性'){
            this.itemStyle={'normal': {'color':'#b6becc', 'borderColor': '#fff', 'borderWidth': borderWidth}};
        }
        this.icon='circle';
    })
    var pieChart = echarts.init(document.getElementById(demo));
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            x: 'left',
            data: data[1]
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '情绪类别及声量',
                type: 'pie',
                radius: ['20%', '45%'],
                label: {
                    normal: {
                        show: true,
                        position: 'outer',
                        formatter: '{b}({d}%)'
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: data[1]
            }
        ]
    };
// 使用刚指定的配置项和数据显示图表。
    pieChart.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            pieChart.resize();
        }
    });
}
//折线图ajax
function emotionLineAjax() {
    var date = $("#date").val();
    var keyword= $("#keyword1").val();
    var filterKeyword = $("#filterKeyword").val();
    var includeOthers = $("#includeOthers").val();
    if (includeOthers==""||includeOthers==null){
        includeOthers=1;
    }
    $.ajax({
        url:actionBase+'/en/emotion/emotionLine.action',
        type:'POST',
        data:{'keyword':keyword,'date':date,'filterKeyword':filterKeyword,'includeOthers':includeOthers},
        beforeSend:function(){
            $("#collapse6").loader('show',loaders);
        },
        success:function (result) {
            $("#collapse6").loader('hide');
            if(result!=null){
                emotionLineHtml(result);
            }else{
                $("#hotTrendChart").html(warnHtml);
            }
        }
    })
}

function emotionLineHtml(result) {
    if (result!=null) {
         emotionLine(result, 'hotTrendChart');
    }else{
        $("#hotTrendChart").html(warnHtml);
    }
}

 //折线图 3'hotTrendChart'
function emotionLine(data,demo) {
	
    $("#"+demo).empty();
    $("#"+demo).removeAttr('_echarts_instance_');
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
