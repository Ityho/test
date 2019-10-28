function infoNumPieChart(total,mgNum) {
    //����������״ͼ

    var com_num_pie = echarts.init(document.getElementById('com_num_pie'));

    // ָ��ͼ��������������
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },

        series: [{
            name: 'ȫ����Ϣ',
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

    // ʹ�ø�ָ�����������������ʾͼ��
    com_num_pie.setOption(option);

    //����������״ͼ
    var mg_num_pie = echarts.init(document.getElementById('mg_num_pie'));

    // ָ��ͼ��������������"{b}: {c} ({d}%)"
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: function (value) {
                if (value.dataIndex==0){
                    return "��������Ϣ:"+value.value+"("+value.percent+"%)";
                }else{
                    return value.name+":"+value.value+"("+value.percent+"%)";
                }
            }
        },

        series: [{
            name: '������Ϣ',
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
                    name:'������Ϣ',
                    value: mgNum,
                    label: {normal: {formatter: '{d}%', textStyle: {color: '#333', fontSize: 18}}},
                    itemStyle: {normal: {color: '#e72815'}}
                },
            ]
        }]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    mg_num_pie.setOption(option);

    window.onresize = function() {
        com_num_pie.resize();
        mg_num_pie.resize();
    }
}
//��Ϣ����ͼ
function infoLineChart(data,dom) {
    var isHourSeach = $("#timeToggle label[class='btn btn-sm btn-half active'] input").val();
    isHourSeach = isHourSeach==1?"true":"false";
    $.each(data.data, function(i, n) {
        n.symbolSize = 10;
        n.smooth = true;
    });
    
    var informationChart = echarts.init(document.getElementById(dom));
    // ָ��ͼ��������������
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
                axisLine: {//���������� Ĭ�� true,
                    show: false
                },
                axisTick: {//������̶�
                    show: false,
                    lineStyle: {
                        color: '#888',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
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
                axisLine: {//���������� Ĭ�� true,
                    show: false
                },
                axisTick: {//������̶�
                    show: false
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
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

    // ʹ�ø�ָ�����������������ʾͼ��
    informationChart.setOption(option);
    informationChart.on('click', function (param){
        var timeL = (parseFloat(param.name));
        var filterOrigina=1;
        if(param.seriesName=="ȫ��"){
            filterOrigina = 1;
        }else if(param.seriesName=="��վ"){
            filterOrigina=4;
        }else if(param.seriesName=="��̳"){
            filterOrigina=3;
        }else if(param.seriesName=="����"){
            filterOrigina=8;
        }else if(param.seriesName=="΢��"){
            filterOrigina=2;
        }else if(param.seriesName=="��ý"){
            filterOrigina=11;
        }else if(param.seriesName=="΢��"){
            filterOrigina=5;
        }else if(param.seriesName=="����"){
            filterOrigina=7;
        }else if(param.seriesName=="����"){
            filterOrigina=9;
        }else if(param.seriesName=="����"){
            filterOrigina=10;
        }else if(param.seriesName=="��Ƶ"){
            filterOrigina=12;
        }else if(param.seriesName=="�ͻ���"){
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
        if(secondSearchWord=="�ڽ��������"){
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
    //ý���Ծ��
    var mediaActivity = echarts.init(document.getElementById(dom));
    $.each(data.data, function(i, n) {
        delete n.itemStyle;
    });
    // ָ��ͼ��������������
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // ������ָʾ���������ᴥ����Ч
                type: 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
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
                axisLine: {//���������� Ĭ�� true,
                    show: true,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisTick: {//������̶�
                    show: false
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    rotate: 45,  //��ת�Ƕ�
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
                axisLine: {//���������� Ĭ�� true,
                    show: false,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisTick: {//������̶�
                    show: false,

                },
                splitLine: {
                    show: true
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
                    textStyle: {
                        color: '#888'
                    }
                }
            }
        ],

        series: [
            {
                name:'����',
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

    // ʹ�ø�ָ�����������������ʾͼ��
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
        if(secondSearchWord=="�ڽ��������"){
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
    if(data.legend.length==1 && data.legend[0]=='����΢��'){
        fole = true;
    }
    //ý����Դռ��
    var mediaSources = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
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
                '����΢��': fole
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
                name: 'ý����Դռ��',
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

    // ʹ�ø�ָ�����������������ʾͼ��
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
        if(secondSearchWord=="�ڽ��������"){
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
	        if(this.name=='����'){
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
	    if(data1.name=='����'){
	    	data.data[0] = data2;
	    	data.data[1] = data1;
	    }
    }
    var splitNum = 0;
    //������Ϣ��������ͼ
    var dayInfoAttr = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
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
                axisLine: {//���������� Ĭ�� true,
                    show: false
                },
                axisTick: {//������̶�
                    show: true,
                    lineStyle: {
                        color: '#888',
                        width: 1,
                        type: 'solid'
                    }
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
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
                axisLine: {//���������� Ĭ�� true,
                    show: false
                },
                axisTick: {//������̶�
                    show: false
                },
                axisLabel: {//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
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

    // ʹ�ø�ָ�����������������ʾͼ��
    dayInfoAttr.setOption(option);
    dayInfoAttr.on('click', function (param){
        var otherAttribute = 1;
        var timeL = (parseFloat(param.name));
        if(param.seriesName == '����'){
            otherAttribute = 2;
        }else if(param.seriesName == '������'){
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
        if(secondSearchWord=="�ڽ��������"){
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
//	color ָ�����ɫ
//	data = {data:[{itemStyle:{normal:{color:"#A020F0"}},name:"����",value:"3"},{itemStyle:{normal:{color:"#030303"}},name:"������",value:"305"}],legend:["����","������"],title:"��зֲ�ͼ"};
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
    if (data.data[0].name=="������"){
        data.data[0].value="0";
        data.data[0].name="����";
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
                name:'��Ϣ���Էֲ�ͼ',
                type: 'gauge',
                "title": {
                    "show": false,
                },
                axisLine: {
                    lineStyle: {
                    	//������ɫ
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
        if(param.name == '����'){
            otherAttribute = 2;
        }else if(param.name == '������'){
            otherAttribute = 3;
        }
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="�ڽ��������"){
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
        if(n.name=="����"){
            this.itemStyle={'normal':{'color':'#dd4119'}};
        }else{
            this.itemStyle={'normal':{'color':'#39ad7e'}};
        }

    });
    //��Ϣ���Էֲ�ͼ
    var inforAttrDis = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
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
                name: '��Ϣ���Էֲ�',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['50%', '50%'],
                data: data.data

            }
        ]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    inforAttrDis.setOption(option);
    inforAttrDis.on("click", function (param){
        var otherAttribute = 1;
        if(param.name == '����'){
            otherAttribute = 2;
        }else if(param.name == '������'){
            otherAttribute = 3;
        }
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var keywordId = document.getElementById("kw.keywordId").value;
        var newlstSelect = document.getElementById("newlstSelect").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="�ڽ��������"){
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
    //��Դ����ͳ��
    var sourceTypeChart = echarts.init(document.getElementById(dom));
    var legend = data.legend;
    var title = data.title;
    var data = data.data;
    var wbNum = 0;
    var total = 0;
    var isShowWb = true;
    for(var i=0; i<data.length; i++){
    	if(data[i].name == '΢��')
    		wbNum = parseInt(data[i].value);
    	total += parseInt(data[i].value);
    }
    if(total != null && wbNum*100/total > 80){
    	isShowWb = false;
    	//$("#sourceTypeChart-cue").show();
    }else{
    	//$("#sourceTypeChart-cue").hide();
    }
    
    // ָ��ͼ��������������
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
                '΢��': isShowWb
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        series: [
            {
                name: '������Դ',
                type: 'pie',
                radius: ['40%', '70%'],
                center: ['60%', '50%'],
                data: data

            }
        ]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    sourceTypeChart.setOption(option);
    sourceTypeChart.on("click", function (param){
        var keywordId = document.getElementById("kw.keywordId").value;
        var starttime = document.getElementById("starttime").value;
        var endtime = document.getElementById("endtime").value;
        var timeDomain = document.getElementById("timeDomain").value;
        var solrType = document.getElementById("solrType").value;
        var mcFilterOrigina = $("#mcFilterOrigina").val();
        var secondSearchWord = $("#secondSearchWord").val();
        if(secondSearchWord=="�ڽ��������"){
            secondSearchWord="";
        }
        var directSet = $("#directSet").val();
        directSet = directSet==""?0:directSet;
        var filterOrigina=1;
        if(param.name=="ȫ��"){
            filterOrigina = 1;
        }else if(param.name=="��վ"){
            filterOrigina=4;
        }else if(param.name=="��̳"){
            filterOrigina=3;
        }else if(param.name=="����"){
            filterOrigina=8;
        }else if(param.name=="΢��"){
            filterOrigina=2;
        }else if(param.name=="��ý"){
            filterOrigina=11;
        }else if(param.name=="΢��"){
            filterOrigina=5;
        }else if(param.name=="����"){
            filterOrigina=7;
        }else if(param.name=="����"){
            filterOrigina=9;
        }else if(param.name=="����"){
            filterOrigina=10;
        }else if(param.name=="��Ƶ"){
            filterOrigina=12;
        }else if(param.name=="�ͻ���"){
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
    //����ֲ�
    var mapChart = echarts.init(document.getElementById(dom));


    // ָ��ͼ��������������
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
            text: ['��', '��'],           // �ı���Ĭ��Ϊ��ֵ�ı�
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
name: '��ת����',
type: 'map',
mapType: 'china',
roam: false,
left:'center',
label: {
	 normal: {
        show: false,
        textStyle: {
			color: "#3a3a3a",//��ͼ������ɫ
			fontSize: 8
		}
     }
     
        },
        itemStyle: {
            normal: {
                borderColor: '#ffffff',
                areaColor: '#efefef',//��ͼ������ɫ
            },
            emphasis: {
                areaColor: '#bdd3f5'
            }
        }
            ,
    data:[
    
        {
			name: '�Ϻ��',
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
                name: '��Ϣ��',
                type: 'map',
                mapType: 'china',
                roam: false,
                left: 'center',
                label: {
                    normal: {
                        show: false,
                        textStyle: {
                            color: "#3a3a3a",//��ͼ������ɫ
                            fontSize: 8
                        }
                    }

                },
//                 itemStyle: {
//                     normal: {
//                         borderColor: '#ffffff',
//                         areaColor: '#efefef',//��ͼ������ɫ
//                     },
//                     emphasis: {
//                         areaColor: '#bdd3f5'
//                     }
//                 },
                data: data.data
            }

        ]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
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


//��������ͼ
function heatMapChart(data,dom) {
	var domDiv = document.getElementById(dom);
    var heatMapChart = echarts.init(domDiv);
    // ָ��ͼ��������������

    var hours = ['0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', '10', '11','12','13','14','15','16','17','18',
            '19','20','21','22','23'];
    var days = ['����', '����', '����', '����', '�ܶ�', '��һ', '����'];
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
                text: ['��', '��'],           // �ı���Ĭ��Ϊ��ֵ�ı�
                calculable: true,
                inRange: {
                    color: ['#e3dcdf','#f1b192','#f18d00']
                }
    	    },
    	    series: [{
    	        name: '��Ϣ��',
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
    	            	var label = week + " , " + value.data[0] + "ʱ~" + (parseInt(value.data[0])+1) + "ʱ<br/>";
    	            	label += value.seriesName + " : " + value.data[2];
    	               return label;
    	            }
    	        }
    	    }]
    	};

    // ʹ�ø�ָ�����������������ʾͼ��
    heatMapChart.setOption(option);
    heatMapChart.on('click', function (param){
    	var hourInDay = null;	//Сʱ
    	var week = null;	//����
    	if(param.componentType == 'series'){
    		hourInDay = param.data[0];	//Сʱ
    		week = param.data[1];	//����
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
        if(secondSearchWord=="�ڽ��������"){
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






