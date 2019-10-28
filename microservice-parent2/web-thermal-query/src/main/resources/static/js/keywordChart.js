function infoNumPieChart(total,mgNum) {
    //评论条数饼状图

    var com_num_pie = echarts.init(document.getElementById('com_num_pie'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },

        series: [{
            name: '全部信息',
            type: 'pie',
            radius: ['65%', '85%'],
            center: ['50%', '50%'],
            label: {
                normal: {
                    position: 'center'
                }
            },
            data: [
                {value: 0, itemStyle: {normal: {color: "#dbdbdb"}}},
                {
                    value: total,
                    label: {normal: {formatter: '{d} %', textStyle: {color: '#333', fontSize: 18}}},
                    itemStyle: {normal: {color: '#3fad7e'}}
                },
            ]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    com_num_pie.setOption(option);

    //评论条数饼状图
    var mg_num_pie = echarts.init(document.getElementById('mg_num_pie'));

    // 指定图表的配置项和数据"{b}: {c} ({d}%)"
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: function (value) {
                if (value.dataIndex==0){
                    return "非敏感信息:"+value.value+"("+value.percent+"%)";
                }else{
                    return value.name+":"+value.value+"("+value.percent+"%)";
                }
            }
        },

        series: [{
            name: '敏感信息',
            type: 'pie',
            radius: ['65%', '85%'],
            center: ['50%', '50%'],
            label: {
                normal: {
                    position: 'center'
                }
            },
            data: [
                {name:'',value: total-mgNum, itemStyle: {normal: {color: "#dbdbdb"}}},
                {
                    name:'敏感信息',
                    value: mgNum,
                    label: {normal: {formatter: '{d}%', textStyle: {color: '#333', fontSize: 18}}},
                    itemStyle: {normal: {color: '#e72815'}}
                },
            ]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    mg_num_pie.setOption(option);

    window.onresize = function() {
        com_num_pie.resize();
        mg_num_pie.resize();
    }
}
//信息走势图
function infoLineChart(data,dom) {
    var isHourSeach = $("#timeToggle label[class='btn btn-sm btn-half active'] input").val();
    isHourSeach = isHourSeach==1?"true":"false";
    $.each(data.data, function(i, n) {
        n.symbolSize = 10;
        n.smooth = true;
    });
    
    var informationChart = echarts.init(document.getElementById(dom));
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis',
            formatter:function(params){
                if(isHourSeach=="true"){
                    v = new Date(parseFloat(params[0].name)).format("MM-dd hh:00");
                }else {
                    v = new Date(parseFloat(params[0].name)).format("yyyy-MM-dd");
                }
                for (var i = 0, l = params.length; i < l; i++) {
                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                }

                return v;
            }
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
        xAxis: [
            {
                type: 'category',
                axisLine: {//坐标轴轴线 默认 true,
                    show: false
                },
                axisTick: {//坐标轴刻度
                    show: false,
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
                    },
                    formatter:function(v){

                        if(isHourSeach=="true"){
                            v = new Date(parseFloat(v)).format("MM-dd hh:00");
                        }else {
                            v = new Date(parseFloat(v)).format("yyyy-MM-dd");
                        }
                        return v;
                    }
                },
                boundaryGap: false,
                data: data.datetime
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {//坐标轴轴线 默认 true,
                    show: false
                },
                axisTick: {//坐标轴刻度
                    show: false
                },
                axisLabel: {//坐标轴刻度标签
                    show: true,
                    //rotate: 30,  //旋转角度
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
                }
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
            handleStyle: {
                color: "#d3dee5",
                borderColor: 'rgba(0,0,0,.3)'
            },
            showDetail: false,
            textStyle: {
                color: "#000"
            },
            borderColor: "#90979c"


        }, {
            "type": "inside",
            "zoomLock": true,
            "show": true,
            "height": 15,
            "start": 1,
            "end": 35
        }],
        series: data.data
    };

    // 使用刚指定的配置项和数据显示图表。
    informationChart.setOption(option);
    informationChart.on('click', function (param){
        var timeL = (parseFloat(param.name));
        var filterOrigina=1;
        if(param.seriesName=="全部"){
            filterOrigina = 1;
        }else if(param.seriesName=="网站"){
            filterOrigina=4;
        }else if(param.seriesName=="论坛"){
            filterOrigina=3;
        }else if(param.seriesName=="博客"){
            filterOrigina=8;
        }else if(param.seriesName=="微博"){
            filterOrigina=2;
        }else if(param.seriesName=="外媒"){
            filterOrigina=11;
        }else if(param.seriesName=="微信"){
            filterOrigina=5;
        }else if(param.seriesName=="新闻"){
            filterOrigina=7;
        }else if(param.seriesName=="政务"){
            filterOrigina=9;
        }else if(param.seriesName=="报刊"){
            filterOrigina=10;
        }else if(param.seriesName=="视频"){
            filterOrigina=12;
        }else if(param.seriesName=="客户端"){
            filterOrigina=6;
        }
        var startFormat = "yyyy-MM-dd 00:00:00";
        var endFormat = "yyyy-MM-dd 23:59:59";
        if(isHourSeach=='true'){
            startFormat = "yyyy-MM-dd hh:00:00";
            endFormat = "yyyy-MM-dd hh:59:59";
        }
        
        var starttime = new Date(timeL).format(startFormat);
        var endtime = new Date(timeL).format(endFormat) ;
        //alert(starttime+"\n"+endtime)
        var kid = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var solrType = document.getElementById("solrType").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directType = $("#directType").val();
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var params = {'clickTimeDomain':-1, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':filterOrigina, 'clickMcFilterOrigina':filterOrigina,'clickOtherAttribute':$("#clickOtherAttribute").val(), 'clickPaixu':2, 'kw.keywordId':kid, 'clickNewlstSelect':newlstSelect, 'clickComblineflg':1, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
    });
    window.onresize = function() {
        informationChart.resize();
    }
}

function mediaBarChart(data,dom) {
    //媒体活跃度
    var mediaActivity = echarts.init(document.getElementById(dom));
    $.each(data.data, function(i, n) {
        delete n.itemStyle;
    });
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },

        grid: {
            left: '0%',
            right: '0%',
            top: '8%',
            bottom: '11%',
            containLabel: true
        },
        xAxis: [
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
                    show: false
                },
                axisLabel: {//坐标轴刻度标签
                    show: true,
                    rotate: 45,  //旋转角度
                    interval: '0',
                    textStyle: {
                        color: '#888'
                    }
                },

                boundaryGap: true,
                data: data.datetime
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {//坐标轴轴线 默认 true,
                    show: false,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisTick: {//坐标轴刻度
                    show: false,

                },
                splitLine: {
                    show: true
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

        series: [
            {
                name:'数量',
                type: 'bar',
                barWidth: '30',
                itemStyle: {
                    normal: {
                        color: '#5673b5',
                        barBorderRadius: 0
                    }
                },
                data: data.data
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    mediaActivity.setOption(option);
    mediaActivity.on("click", function (param){
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var solrType = document.getElementById("solrType").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'clickOtherAttribute':$("#clickOtherAttribute").val(), 'website':param.name, 'clickclickComblineflg':2, 'clickPaixu':2, 'starttime':starttime, 'endtime':endtime, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

    });

    window.onresize = function() {
        mediaActivity.resize();
    }
}

function mediaPieChart(data,dom) {
    var fole = false;
    if(data.legend.length==1 && data.legend[0]=='新浪微博'){
        fole = true;
    }
    //媒体来源占比
    var mediaSources = echarts.init(document.getElementById(dom));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            x: 'left',
            data: data.legend,
            selected: {
                '新浪微博': fole
            },
            formatter:function(name){
            	if(name.length > 4){
            		name = name.substring(0, 4) + '...';
            	}
            	return name;
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        calculable: true,
        series: [
            {
                name: '媒体来源占比',
                type: 'pie',
                radius: ['45%', '80%'],
                center: ['50%', '50%'],
                roseType: 'area',
                labelLine:{
                    normal:{length:2}
                },
                label:{
                	normal:{
                		formatter:function(data){
                			var name = data.name;
                			if(name.length > 4){
                        		name = name.substring(0, 4) + '...';
                        	}
                			return name;
                		}
                	}
                },
                data: data.data
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    mediaSources.setOption(option);
    mediaSources.on("click", function (param){
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var solrType = document.getElementById("solrType").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'clickOtherAttribute':$("#clickOtherAttribute").val(), 'website':param.name, 'clickPaixu':2, 'starttime':starttime, 'endtime':endtime, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

    });

    window.onresize = function() {
        mediaSources.resize();
    }
}

function sensitiveLineChart(data,dom) {
    var isHourSeach = $("#timeToggle2 label[class='btn btn-sm btn-half active'] input").val();
    isHourSeach = isHourSeach==1?"true":"false";
    var datat = new Array();
    $.each(data.data,function(){
    	if(this != null){
	        this.symbolSize = 10;
	        this.symbol= 'emptyCircle';
	        if(this.name=='敏感'){
	        	 this.itemStyle={'normal':{'color':'#dd4119','lineStyle':{'width':1}}};
	             this.areaStyle= {
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
	             }
	        }else{
	        	this.itemStyle={'normal':{'color':'#36ae7e','lineStyle':{'width':1}}};
	        	this.areaStyle= {
	    			normal: {
		                 color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                     offset: 0,
		                     color: '#8ae0bd'
		                 }, {
		                     offset: 1,
		                     color: '#fff'
		                 }], false),
		                 shadowColor: 'rgba(0, 0, 0, 0.1)',
		                 shadowBlur: 10
	   	             }
	        	}
	           
	        }
	    	datat.push(this);
    	}

    });
    data.data = datat;
    if(data.data.length > 1){
	    var data1 = data.data[0];
	    var data2 = data.data[1];
	    if(data1.name=='敏感'){
	    	data.data[0] = data2;
	    	data.data[1] = data1;
	    }
    }
    var splitNum = 0;
    //今日信息属性走势图
    var dayInfoAttr = echarts.init(document.getElementById(dom));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis',
            formatter:function(params){
                if(isHourSeach=="true"){
                    v = new Date(parseFloat(params[0].name)).format("MM-dd hh:00");
                }else {
                    v = new Date(parseFloat(params[0].name)).format("yyyy-MM-dd");
                }
                for (var i = 0, l = params.length; i < l; i++) {
                    v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                }

                return v;
            }
        },
        legend: {
            top: '5px',
            data: data.legend
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '5%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                axisLine: {//坐标轴轴线 默认 true,
                    show: false
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
                    },
                    formatter:function(v){

                        if(isHourSeach=="true"){
                            v = new Date(parseFloat(v)).format("MM-dd hh:00");
                        }else {
                            v = new Date(parseFloat(v)).format("yyyy-MM-dd");
                        }
                        return v;
                    }
                },
                boundaryGap: false,
                data: data.datetime
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLine: {//坐标轴轴线 默认 true,
                    show: false
                },
                axisTick: {//坐标轴刻度
                    show: false
                },
                axisLabel: {//坐标轴刻度标签
                    show: true,
                    //rotate: 30,  //旋转角度
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
            }
        ],
        series:data.data
    };

    // 使用刚指定的配置项和数据显示图表。
    dayInfoAttr.setOption(option);
    dayInfoAttr.on('click', function (param){
        var otherAttribute = 1;
        var timeL = (parseFloat(param.name));
        if(param.seriesName == '敏感'){
            otherAttribute = 2;
        }else if(param.seriesName == '非敏感'){
            otherAttribute = 3;
        }
        var startFormat = "yyyy-MM-dd 00:00:00";
        var endFormat = "yyyy-MM-dd 23:59:59";
        if(isHourSeach=='true'){
            startFormat = "yyyy-MM-dd hh:00:00";
            endFormat = "yyyy-MM-dd hh:59:59";
        }
        var starttime = new Date(timeL).format(startFormat) ;
        var endtime = new Date(timeL).format(endFormat) ;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':-1, 'starttime':starttime, 'endtime':endtime, 'clickOtherAttribute':otherAttribute, 'clickPaixu':2, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
    });

    window.onresize = function() {
        dayInfoAttr.resize();
    }
}

function infoAttrPieChart(data,dom) {
//	color 指针的颜色
//	data = {data:[{itemStyle:{normal:{color:"#A020F0"}},name:"敏感",value:"3"},{itemStyle:{normal:{color:"#030303"}},name:"非敏感",value:"305"}],legend:["敏感","非敏感"],title:"情感分布图"};
//	console.log(data)
    var num1 = data.data[0].value;
    var num2 = 0;
    if(data.data.length>1){
        num2 = data.data[1].value;
    }

    data.data[0].value=((Number(parseInt(num1)/(parseInt(num1)+parseInt(num2))))*100).toFixed(2);
    data.data[0].fontSize=12;
    var inforAttrDis = echarts.init(document.getElementById(dom));
//    console.log(data);
//    console.log(data.data[0]);
    if (data.data[0].name=="非敏感"){
        data.data[0].value="0";
        data.data[0].name="敏感";
    }
    var option = {
        title: {
            x: "left",
            top: 10
        },
        tooltip : {
            formatter: "{a} <br/>{b} : {c}%"
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [
            {
                name:'信息属性分布图',
                type: 'gauge',
                "title": {
                    "show": false,
                },
                axisLine: {
                    lineStyle: {
                    	//环形颜色
                        color: [
                            [0.2, '#feaf53'],
                            [0.6, '#f17700'],
                            [0.8, '#f17700'],
                            [1, '#cf421e']
                        ]
                    }
                },
                detail: {formatter:'{value}%',textStyle: {
                    fontSize: 20
                }},
                data: [data.data[0]]
            }
        ]
    };
    inforAttrDis.setOption(option);
    inforAttrDis.on("click", function (param){
        var otherAttribute = 1;
        if(param.name == '敏感'){
            otherAttribute = 2;
        }else if(param.name == '非敏感'){
            otherAttribute = 3;
        }
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':0, 'clickOtherAttribute':otherAttribute, 'clickPaixu':2, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

    });
    window.onresize = function() {
        inforAttrDis.resize();
    }
}
function infoAttrPieChart23(data,dom) {
    $.each(data.data, function(i, n) {
        delete n.itemStyle;
        if(n.name=="敏感"){
            this.itemStyle={'normal':{'color':'#dd4119'}};
        }else{
            this.itemStyle={'normal':{'color':'#39ad7e'}};
        }

    });
    //信息属性分布图
    var inforAttrDis = echarts.init(document.getElementById(dom));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            data: data.legend
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '信息属性分布',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['50%', '50%'],
                data: data.data

            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    inforAttrDis.setOption(option);
    inforAttrDis.on("click", function (param){
        var otherAttribute = 1;
        if(param.name == '敏感'){
            otherAttribute = 2;
        }else if(param.name == '非敏感'){
            otherAttribute = 3;
        }
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':0, 'clickOtherAttribute':otherAttribute, 'clickPaixu':2, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

    });

    window.onresize = function() {
        inforAttrDis.resize();
    }
}

function sourcePieChart(data,dom) {
    //来源类型统计
    var sourceTypeChart = echarts.init(document.getElementById(dom));
    var legend = data.legend;
    var title = data.title;
    var data = data.data;
    var wbNum = 0;
    var total = 0;
    var isShowWb = true;
    for(var i=0; i<data.length; i++){
    	if(data[i].name == '微博')
    		wbNum = parseInt(data[i].value);
    	total += parseInt(data[i].value);
    }
    if(total != null && wbNum*100/total > 80){
    	isShowWb = false;
    	//$("#sourceTypeChart-cue").show();
    }else{
    	//$("#sourceTypeChart-cue").hide();
    }
    
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            top: '0',
            left: 'left',
            bottom: '0',
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            data: legend,
            selected: {
                '微博': isShowWb
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['60%', '50%'],
                data: data

            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    sourceTypeChart.setOption(option);
    sourceTypeChart.on("click", function (param){
        var keywordId = document.getElementById("kw.keywordId").value;
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var solrType = document.getElementById("solrType").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var filterOrigina=1;
        if(param.name=="全部"){
            filterOrigina = 1;
        }else if(param.name=="网站"){
            filterOrigina=4;
        }else if(param.name=="论坛"){
            filterOrigina=3;
        }else if(param.name=="博客"){
            filterOrigina=8;
        }else if(param.name=="微博"){
            filterOrigina=2;
        }else if(param.name=="外媒"){
            filterOrigina=11;
        }else if(param.name=="微信"){
            filterOrigina=5;
        }else if(param.name=="新闻"){
            filterOrigina=7;
        }else if(param.name=="政务"){
            filterOrigina=9;
        }else if(param.name=="报刊"){
            filterOrigina=10;
        }else if(param.name=="视频"){
            filterOrigina=12;
        }else if(param.name=="客户端"){
            filterOrigina=6;
        }
        var directType = $("#directType").val();
        var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':filterOrigina, 'clickMcFilterOrigina':filterOrigina, 'clickComblineflg':2, 'clickOtherAttribute':$("#clickOtherAttribute").val(), 'clickPaixu':2, 'type':2,'solrType':solrType,'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType,'byShouye':0};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

    });

    window.onresize = function() {
        sourceTypeChart.resize();
    }
}
    

function areaMapChart(data,dom) {
    var min = 10000;
    var max = 0;
    $.each(data.data, function(i, n) {
        delete n.itemStyle;
        if (n.value > max)
            max = n.value;
        if (n.value < min)
            min = n.value;
    });
    //地域分布
    var mapChart = echarts.init(document.getElementById(dom));


    // 指定图表的配置项和数据
    var option = {

        tooltip: {
            trigger: 'item'
        },
        toolbox: {
            orient: 'vertical',
            feature: {
                saveAsImage: {}
            }
        },
        visualMap: {
            min: 0,
            max: max,
            left: '0',
            bottom: '35',
            text: ['高', '低'],           // 文本，默认为数值文本
            calculable: true,
            inRange: {
                color: ['#e3dcdf','#f1b192','#f18d00']
            }
        },
        geo: {
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
                borderColor: '#ffffff',
                areaColor: '#efefef',//地图背景颜色
            },
            emphasis: {
                areaColor: '#bdd3f5'
            }
        }
            ,
    data:[
    
        {
			name: '南海诸岛',
			itemStyle: {
				normal: {
					borderColor: '#959595',
					areaColor: '#efefef', 
				}
			}
		},

    
    ]
},
            {
                name: '信息量',
                type: 'map',
                mapType: 'china',
                roam: false,
                left: 'center',
                label: {
                    normal: {
                        show: false,
                        textStyle: {
                            color: "#3a3a3a",//地图文字颜色
                            fontSize: 8
                        }
                    }

                },
//                 itemStyle: {
//                     normal: {
//                         borderColor: '#ffffff',
//                         areaColor: '#efefef',//地图背景颜色
//                     },
//                     emphasis: {
//                         areaColor: '#bdd3f5'
//                     }
//                 },
                data: data.data
            }

        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    mapChart.setOption(option);
    mapChart.on("click", function (param){
        var keywordId = document.getElementById("kw.keywordId").value;
        if(param.value > 0) {
            var params = {'kw.keywordId':keywordId, 'province':param.name, 'clickOtherAttribute':$("#clickOtherAttribute").val(), 'clickPaixu':2, 'starttime':$("#starttime").val(), 'endtime':$("#endtime").val(), 'newlstSelect':$("#newlstSelect").val(), 'clickFilterOrigina':0, 'flag':'map', 'clickTimeDomain':$("#timeDomain").val(), 'type':2,'solrType':$("#solrType").val(),'isRecommended':$("#isRecommended").val(),'byShouye':0};
            sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
        }
    });

    window.onresize = function() {
        mapChart.resize();
    }
}


//热力分析图
function heatMapChart(data,dom) {
	var domDiv = document.getElementById(dom);
    var heatMapChart = echarts.init(domDiv);
    // 指定图表的配置项和数据

    var hours = ['0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '10', '11','12','13','14','15','16','17','18',
            '19','20','21','22','23'];
    var days = ['周六', '周五', '周四', '周三', '周二', '周一', '周日'];
    var maxNum = data[1];
    data = eval(data[0]);
    data = data.map(function (item) {
        return [item[1], item[0], item[2] || '-'];
    });
    var width = document.getElementById(dom).offsetWidth;
    width = width*0.85;
    console.log(width);
    var option = {
    	    animation: false,
    	    tooltip: {
    	        position: 'top'
    	    },
    	    toolbox: {
                orient: 'vertical',
                feature: {
                    saveAsImage: {}
                }
            },
    	    grid: {
    	        height: '70%',
    	        width: '85%',
    	        left: '55',
    	        bottom: '90',
    	       
    	    },
    	    xAxis: {
    	        type: 'category',
    	        data: hours,
    	        triggerEvent: true,
    	        splitArea: {
    	            show: true
    	        }
    	    },
    	    yAxis: {
    	        type: 'category',
    	        data: days,
    	        triggerEvent: true,
    	        splitArea: {
    	            show: true
    	        }
    	    },
    	    visualMap: {
    	    	min: 0,
                max: maxNum,
                orient: 'horizontal',
                itemHeight: width,
                left: '30',
                align:'left',
                bottom: '20',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true,
                inRange: {
                    color: ['#e3dcdf','#f1b192','#f18d00']
                }
    	    },
    	    series: [{
    	        name: '信息量',
    	        type: 'heatmap',
    	        data: data,
    	        label: {
    	            normal: {
    	                show: false
    	            }
    	        },
    	        itemStyle: {
    	            emphasis: {
    	                shadowBlur: 10,
    	                shadowColor: 'rgba(0, 0, 0, 0.5)'
    	            }
    	        },
    	        tooltip:{
    	            formatter: function (value) {
    	            	var week = value.data[1];
    	            	var label = week + " , " + value.data[0] + "时~" + (parseInt(value.data[0])+1) + "时<br/>";
    	            	label += value.seriesName + " : " + value.data[2];
    	               return label;
    	            }
    	        }
    	    }]
    	};

    // 使用刚指定的配置项和数据显示图表。
    heatMapChart.setOption(option);
    heatMapChart.on('click', function (param){
    	var hourInDay = null;	//小时
    	var week = null;	//星期
    	if(param.componentType == 'series'){
    		hourInDay = param.data[0];	//小时
    		week = param.data[1];	//星期
    	}else if(param.componentType == 'xAxis'){
    		hourInDay = param.value;
    	}else if(param.componentType == 'yAxis'){
    		week = param.value;
    	}
        var kid = document.getElementById("kw.keywordId").value;
    	var newlstSelect = $("#newlstSelect").val();
        var starttime = $("#starttime").val();
        var endtime = $("#endtime").val();
        var timeDomain = $("#timeDomain").val();
        var solrType = $("#solrType").val();
        var otherAttribute=$("#otherAttribute").val();
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var clickMcFilterOrigina = $("#clickMcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="在结果中搜索"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var directType = $("#directType").val();
        var params = {'clickTimeDomain':-1, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':clickMcFilterOrigina, 'clickMcFilterOrigina':clickMcFilterOrigina,'clickOtherAttribute':$("#clickOtherAttribute").val(), 'clickPaixu':2, 'kw.keywordId':kid, 'clickNewlstSelect':newlstSelect, 'clickComblineflg':1, 'type':2,'solrType':solrType,
        		'mcFilterOrigina':mcFilterOrigina,'secondSearchWord':secondSearchWord,'directSet':directSet,'directType':directType, 'publishedHourInDay':hourInDay, 'publishedWeek':week};
        sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
    });
    window.onresize = function() {
    	heatMapChart.resize();
    }
}






