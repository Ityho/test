function profileCallback(data){
    if (data==null||data==""){
        $("#introduce,#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        return false;
    }else{
        $("#introduce").html(data[2]);
        if(data[0]==null){
            $("#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px；position:absolute;left:125px;top:50px;\"><img src='"+staticResourcePath +"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        }else{
            var html = "<h2>"+data[1]+'</h2>';
            if(data[0].length>4){
                html += '<div class="more"><span class="packUp2"><a href="javascript:void(0)"><label>展开事件进展&nbsp;<i></i></label></a></span></div>';
            }
            html += '<ul id="trend" style="padding-bottom: 0;">';
            $.each(data[0],function(n){
                var published = new Date(this.published);
                if(this.title.length>500)
                    this.title = this.content.substring(0,500)+"...";
                var li = '<li><div class="float_l"><span class="line"></span><span class="round"></span><span class="line"></span></div>'
                    + '<div class="sz"></div><div class="text text2"><a style="color: #000" target="_blank" href="'+this.webpageUrl+'"><div class="textShow"';
                if(n==0){
                    li += ' style="padding-bottom: 10px;"';
                }
                li += '>['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                    +'点]'+this.title+'['+this.captureWebsiteName+'] 相同文章数：'+this.repeatNum;
                if(n==0){
                    li += ' <span style="position: absolute;bottom: 0;right: 0;color:#0185d8">(可能是事件源头，仅供参考)</span>';
                }
                li +='</div></a><div class="editBox"><textarea class="inputList" id="textarea3">['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                    +'点]'+this.title+'[ '+this.captureWebsiteName+' ] 相同文章数：'+this.repeatNum
                    +'</textarea><div class="rel"><div class="tool tooledit" style="position: initial;"><button href="#" class="button button4">'
                    +'<i class="icon-dele"></i>删除</button></div></div></div></div></li>';

                html += li;
            });
            html += '</ul>';
            var endTxt = "续";
            if(data[0].length<5){
                endTxt = "完";
            }
            html += '<ul style="padding-top: 2px;"><li><div class="float_l"><span class="roundEnd">'+endTxt+'</span></div></li></ul>';
            $("#eventProfile").html(html);
            $("#trend > li:gt(3)").hide();
            var num=1;
            $(".more").on("click",function(){
                if(num){
                    num--;
                    $("#trend > li:gt(3)").show();
                    $(this).html("<span class='packUp'><a href='javascript:void(0)'><label>收拢事件进展&nbsp;<i></i></label></a></span>");
                    $(".roundEnd").text("完");
                }else{
                    num++;
                    $("#trend > li:gt(3)").hide();
                    $(this).html("<span class='packUp2'><a href='javascript:void(0)'><label>展开事件进展&nbsp;<i></i></label></a></span>");
                    $(".roundEnd").text("续");
                }
            });
        }

    }
}
function LineCallBack(data){
    var c1 = document.getElementById("container1");
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineChart(data[0],"container1");
            $("#container1 + .text").text(data1);
        }
    }
}
function LineChart(data,dom){

    var splitNum = 0;
    if(data.datetime.length>12){
        splitNum = 2;
    }
    $.each(data.data,function(){
        this.symbolSize = 6;
        this.itemStyle={'normal':{'lineStyle':{'width':2.8}}};
    });
    var config = require(
        [
            'echarts',
            'echarts/chart/line'
        ],
        function (ec) {
            var chart1 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                tooltip : {
                    trigger: 'axis',
                    formatter:function(params){
                        v = params[0].name;
                        for (var i = 0, l = params.length; i < l; i++) {
                            v += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
                        }

                        return v;
                    }
                },
                toolbox: {
                    show : false,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.name
                        },
                    }
                },
                legend: {
                    data:data.legend
                },
                grid:{
                    x:50,
                    x2:50
                },
                xAxis:[{
                    type : 'category',//category|time
                    boundaryGap: false ,
                    data : data.datetime,
                    axisLine: {
                        onZero: false,
                        show:false
                    },
                    splitLine:{
                        show:false
                    },
                    splitNumber:splitNum,
                    axisLabel : {
                        textStyle : {
                            decoration: 'none',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: 12,
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
                series : data.data
            }
            chart1.setOption(option);
            chart1.setTheme('infographic');
            var enConfig = require('echarts/config');
            /*chart1.on(enConfig.EVENT.CLICK, function (param){
                var timeL = param.name;
                var filterOrigina=0;
                if(param.seriesName=="全部"){
                    filterOrigina = 0;
                }else if(param.seriesName=="网站"){
                    filterOrigina=1;
                }else if(param.seriesName=="论坛"){
                    filterOrigina=2;
                }else if(param.seriesName=="博客"){
                    filterOrigina=3;
                }else if(param.seriesName=="微博"){
                    filterOrigina=5;
                }else if(param.seriesName=="外媒"){
                    filterOrigina=6;
                }else if(param.seriesName=="微信"){
                    filterOrigina=8;
                }
                var startFormat = "yyyy-MM-dd 00:00:00";
                var endFormat = "yyyy-MM-dd 23:59:59";
                if(isHourSeach=='true'){
                    startFormat = "yyyy-MM-dd hh:00:00";
                    endFormat = "yyyy-MM-dd hh:59:59";
                }
                var starttime = new Date(timeL).format(startFormat) ;
                var endtime = new Date(timeL).format(endFormat) ;
                //alert(starttime+"\n"+endtime)
                var kid = document.getElementById("kw.keywordId").value;
                var newlstSelect = document.getElementById("newlstSelect").value;
                var solrType = document.getElementById("solrType").value;
                var isRecommended = document.getElementById("isRecommended").value;	//系统优选
// 	            		window.open("listLoading.action?clickTimeDomain=0&starttime="+starttime+"&endtime="+endtime+"&clickFilterOrigina="+filterOrigina+"&clickOtherAttribute=0&clickPaixu=2&kw.keywordId="+kid+"&clickNewlstSelect="+newlstSelect+"&clickComblineflg=1&type=2","_blank");

                var params = {'clickTimeDomain':0, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':filterOrigina, 'clickOtherAttribute':0, 'clickPaixu':2, 'kw.keywordId':kid, 'clickNewlstSelect':newlstSelect, 'clickComblineflg':1, 'type':2,'solrType':solrType,'isRecommended':isRecommended};
                sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
            });*/
        }
    );
}



function QGPieCallBack(data,id){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            var _chartColumn10 = QGPieChart(data[0],id);
        }

    }
}
function QGPieChart(data,dom){
    var config = require(
        [
            'echarts',
            'echarts/chart/pie',
        ],
        function (ec) {
            var chart4 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                toolbox: {
                    show : false,
                    orient:'vertical',//vertical|horizontal
                    y:30,
                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['点击保存']
                        }
                    }
                },
                calculable : false,
                legend:{
                    orient : 'vertical',
                    x : 'left',
                    data:data.legend
                },
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        radius : ['20%', '55%'],
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : true,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                    },
                                    formatter: "{b} : {d}%"
                                },
                                labelLine : {
                                    show : true,
                                    length : 4
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:data.data
                    }
                ]
            }
            chart4.setOption(option);
            var enConfig = require('echarts/config');
            /*chart4.on(enConfig.EVENT.CLICK, function (param){
                var otherAttribute = 0;
                if(param.name == '敏感'){
                    otherAttribute = 3;
                }else if(param.name == '非敏感'){
                    otherAttribute = 4;
                }
                var starttime = document.getElementById("starttime").value;
                var endtime = document.getElementById("endtime").value;
                var keywordId = document.getElementById("kw.keywordId").value;
                var newlstSelect = document.getElementById("newlstSelect").value;
                var timeDomain = document.getElementById("timeDomain").value;
                var isRecommended = document.getElementById("isRecommended").value;	//系统优选

// 		                    window.open("listLoading.action?kw.keywordId="+keywordId+"&clickTimeDomain="+timeDomain+"&starttime="+starttime+"&endtime="+endtime+"&clickFilterOrigina=0&clickOtherAttribute="+otherAttribute+"&clickPaixu=2&clickNewlstSelect="+newlstSelect+"&type=2","_blank");

                var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'starttime':starttime, 'endtime':endtime, 'clickFilterOrigina':0, 'clickOtherAttribute':otherAttribute, 'clickPaixu':2, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType,'isRecommended':isRecommended};
                sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

            });*/
        }
    );

}
function QGPieAdapter(data){
    if(data!=null&&data!=""){
        QGPieCallBack(data[0],"container2");
        QGPieCallBack(data[1],"container3");
        MapCallBack(data[2],"container6");
    }else{
        $("#container2,#container3,#container6").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
    }

}
function MapCallBack(data){
    var c6 = document.getElementById("container6");
    if (data==null||data==""){
        c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
            c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;

        }
        var html = "";
        /*var length = res[0].data.length>10?10:res[0].data.length;*/
        for(var i=0;i<res[0].data.length;i++){
            html += '<tr><td>'+res[0].data[i].name+'</td><td>'+res[0].data[i].value+'</td></tr>';
        }
        $("#c6_tb").html(html);
        var _chartColumn10 = MapChart(data,"container6");
    }
}
function MapChart(data,dom){
    var min = 10000;
    var max = 0;
    $.each(eval(data)[0].data, function(i, n) {
        delete n.itemStyle;
        if (n.value > max)
            max = n.value;
        if (n.value < min)
            min = n.value;
    });
    var config = require(
        [
            'echarts',
            'echarts/chart/map',
        ],
        function (ec) {
            var chart6 = ec.init(document.getElementById(dom));
            data =  eval(data);

            chart6.showLoading( {
                text : "正在努力加载数据中,请耐心等待。。。",
                textStyle : {
                    fontSize : 20
                }
            });
            var option = {
                title : {
                    text : '',
                    subtext : '',
                    x : 'center',
                    y : 'top',
                },
                animation : false,
                calculable : false,
                tooltip : {
                    trigger : 'item',
                    enterable:true,
                    textStyle : {
                        fontSize : 12
                    }
                },
                toolbox: {
                    show : false,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                dataRange: {
                    min: min,
                    max: max,
                    calculable : false,
                    text:['高','低'],
                    color: ['#d44e24','#f29300','#f3d647']
                },
                series : [
                    {
                        name: '',
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
                                borderColor: '#fff',
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
                                        label:{
                                            show : true,
                                        },
                                        borderColor: '#959595',
                                        areaColor: '#efefef',
                                    }
                                }
                            },


                        ]
                    },{
                    name : '数量',
                    type : 'map',
                    mapType : 'china',
                    selectedMode : 'single',//single|multiple
                    roam:false,
                    itemStyle : {
                        normal : {
                            label : {
                                show : true,
                                textStyle : {

                                }
                            },
                            borderColor:'#FFF'
                        },
                        emphasis : {
                            label : {
                                show : true
                            }
                        }
                    },
                    data :data[0].data,
                    geoCoord: {
                        "北京":[116.46,39.92],
                        "辽宁":[123.14,41.66],
                        "安徽":[116.98,32.62],
                        "山东":[117.52,36.23],
                        "上海":[121.42,31.12],
                        "江苏":[119.72,33.66],
                        "湖北":[111.99,31.54],
                        "浙江":[119.87,29.24],
                        "广东":[112.73,24.02],
                        "福建":[117.73,26.17],
                        "湖南":[111.33,27.75],
                        "澳门":[113.57,22.51],
                        "河南":[113.57,33.86],
                        "海南":[109.53,19.15],
                        "新疆":[85.90,40.98],
                        "内蒙古":[113.87,43.49],
                        "西藏":[87.01,32.53],
                        "青海":[95.03,36.19],
                        "云南":[101.25,24.48],
                        "四川":[102.20,30.77],
                        "贵州":[106.62,26.94],
                        "甘肃":[96.46,40.19],
                        "广西":[108.31,23.60],
                        "江西":[115.52,27.60],
                        "黑龙江":[127.37,47.73],
                        "吉林":[126.49,43.49],
                        "山西":[111.99,37.20],
                        "河北":[115.01,38.31],
                        "陕西":[108.75,34.09],
                        "重庆":[106.56,29.64],
                        "宁夏":[105.98,37.25],
                        "天津":[117.14,39.29],
                        "台湾":[120.90,23.81],
                        '钓鱼岛': [123.0254, 25.1986],
                        '赤尾屿': [126.0054, 26.1986]
                    },
                    markPoint : {
                        symbolSize: 1, // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
                        itemStyle: {
                            normal: {
                                color: '#000',
                                borderColor: '#87cefa',
                                borderWidth: 1, // 标注边线线宽，单位px，默认为1
                                label: {
                                    show: true,
                                    formatter: "{b}",
                                    textStyle: {
                                        color: '#444',
                                    }
                                }
                            },
                            emphasis: {
                                label: {
                                    show: false
                                }
                            }
                        },
                        data:[
                            {
                                name: "钓鱼岛",
                                value: '',
                            },
                            {
                                name: "赤尾屿",
                                value: ''
                            }
                        ]
                    }
                }
                ]
            };
            chart6.hideLoading();
            chart6.setOption(option);
            var enConfig = require('echarts/config');
            /*chart6.on(enConfig.EVENT.CLICK, function (param){
                if(param.value > 0) {
// 		                	window.location.href = "listLoading.action?kw.keywordId=${kw.keywordId}&province="+encodeURIComponent(encodeURIComponent(param.name))+"&clickOtherAttribute=0&clickPaixu=2&starttime=" + $("#starttime").val() + "&endtime=" + $("#endtime").val() + "&newlstSelect=" + $("#newlstSelect").val() + "&clickFilterOrigina=0&flag=map&clickTimeDomain="+$("#timeDomain").val()+"&type=2";
                    var params = {'kw.keywordId':'${kw.keywordId}', 'province':param.name, 'clickOtherAttribute':0, 'clickPaixu':2, 'starttime':$("#starttime").val(), 'endtime':$("#endtime").val(), 'newlstSelect':$("#newlstSelect").val(), 'clickFilterOrigina':0, 'flag':'map', 'clickTimeDomain':$("#timeDomain").val(), 'type':2,'solrType':$("#solrType").val(),'isRecommended':$("#isRecommended").val()};
                    sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
                }
            });*/
        }
    );
}
function hotNewsCallback(data){
    var html = "";
    if(phone){
        $("#hotTime").addClass("phone_hide");
    }
    // //console.log(data);
    $.each(data,function(){
        var date = new Date(this.published);
        var min = date.getMinutes().toString().length==1?"0"+date.getMinutes():date.getMinutes();
        var hour = date.getHours().toString().length==1?"0"+date.getHours():date.getHours();
        var dateStr = date.toLocaleDateString();
        if(phone){
            dateStr += "&nbsp;"+hour+":"+min;
        }else{
            dateStr += "<br/>"+hour+":"+min;
        }
        var title = this.title;
        /*if(title!=null&&title!=""){
            title = title.length>30?title.substr(0,30)+"...":title;
        }*/
        var tr = '<tr><td>'+title;
        if(phone){
            tr += '<p class="phone_show time">'+dateStr+'</p>';
            tr += '</td><td>'+this.captureWebsiteName+'</td><td class="phone_hide">'+dateStr+'</td><td>'+this.num+'</td></tr>';
        }else{
            tr += '</td><td>'+this.captureWebsiteName+'</td><td>'+dateStr+'</td><td>'+this.num+'</td></tr>';
        }
        html += tr;

    });
    $("#hotNews").html(html);
}
function WTCallBack(result){
    var c3 = document.getElementById("container5");
    if (result==null||result==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var data = eval(result);
        if(data[0].data==null || data[0].data.length==0){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else {
            var _chartColumn10 = WTBarChart(data[0],"container5");

        }

    }
}
function WTBarChart(data,dom){
    var config = require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        function (ec) {
            var chart2 = ec.init(document.getElementById(dom));
            var option = {
                tooltip : {         // Option config. Can be overwrited by series or data
                    trigger: 'axis',

                },
                toolbox: {
                    show : false,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                xAxis:[{
                    type : 'category',
                    data : data.datetime,
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
                    splitArea: {
                        show: false
                    }
                }],
                grid:{
                    y2:80,
                    x:30,
                    x2:30

                },
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
                        textStyle : {
                            decoration: 'none',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: 12,
                        },
                        formatter:function(v){
                            if(v>=1000){
                                return (v/1000)+"k";
                            }else{
                                return v;
                            }
                        }
                    }
                }],

                /*color:['#87cefa','#ff7f50'],*/
                calculable : false,
                animation:false,
                series : [{
                    name:'数量',
                    type:'bar',
                    data:data.data
                }]
            }
            chart2.setOption(option);
            var enConfig = require('echarts/config');
            /*chart2.on(enConfig.EVENT.CLICK, function (param){
// 	                	  var ws= encodeURIComponent(encodeURIComponent(param.name));
                var starttime = document.getElementById("starttime").value;
                var endtime = document.getElementById("endtime").value;
                var keywordId = document.getElementById("kw.keywordId").value;
                var newlstSelect = document.getElementById("newlstSelect").value;
                var timeDomain = document.getElementById("timeDomain").value;
                var solrType = document.getElementById("solrType").value;
// 	                	  window.open("listLoading.action?kw.keywordId="+keywordId+"&clickTimeDomain="+timeDomain+"&clickOtherAttribute=0&website="+ws+"&clickclickComblineflg=2&clickPaixu=2&starttime="+starttime+"&endtime="+endtime+"&clickNewlstSelect="+newlstSelect+"&type=2","_blank");

                var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'clickOtherAttribute':0, 'website':param.name, 'clickclickComblineflg':2, 'clickPaixu':2, 'starttime':starttime, 'endtime':endtime, 'clickNewlstSelect':newlstSelect, 'type':2,'solrType':solrType};
                sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);

            });*/
        }
    );
}
function mediaCallback(data){
    if (data==null||data==""){
        $("#container5")[0].innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }
    if(data[0]!=""){
        var lis = $("#container4 li");
        var total = 0;
        $.each(data[0],function(n){
            total += this.num;
            lis.eq(n+1).find("p:eq(0)").text(this.name);
            lis.eq(n+1).find("p:eq(1)").text(this.num);
        });
        lis.eq(0).find("p:eq(1)").text(total);
    }
    /*if(data[1]!=""&&data[1]!=null){
        var data1 = eval(data[1]);
        var eData = data1[0].data;
        for(var i=0;i<eData.length;i++){
            var val = eData[i];
            if(i<3){
                var itemStyle = {
                    normal : {
                        color:'#f5613d'
                    }
                }
                eData[i]={
                    value:val,
                    itemStyle:itemStyle
                }
            }else if(i>2&&i<7){
                var itemStyle = {
                    normal : {
                        color:'#32cf78'
                    }
                }
                eData[i]={
                    value:val,
                    itemStyle:itemStyle
                }
            }else{
                break;
            }
        }
        data1[0].data=eData;
        data[1] =data1;
    }*/
    WTCallBack(data[1]);

}
function createRandomItemStyle() {
    return {
        normal: {
            color: 'rgb(' + [
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160),
                Math.round(Math.random() * 160)
            ].join(',') + ')'
        }
    };
}
function cloudChart(data,dom){
    //	//console.log(data);
    var config = require(
        [
            'echarts',
            'echarts/chart/wordCloud'
        ],
        function (ec) {
            var chart2 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                calculable : false,
                tooltip: {
                    show: true,
                    formatter:function(params){
                        var num = params.value;
                        num = parseInt(num)/10;
                        return params.name+" : "+ num;
                    }
                },
                toolbox: {
                    show : false,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                series: [{
                    /*name: 'Google Trends',*/
                    type: 'wordCloud',
                    size: ['90%', '90%'],
                    textRotation : [0, 45, 90, -45],
                    textPadding: 2,
                    autoSize: {
                        enable: true,
                        minSize: 18
                    },
                    data: data,


                }]
            };
            chart2.setOption(option);
            var enConfig = require('echarts/config');
        }
    );
}
function cloudCallback(data){
    //	//console.log(eval(data[0]));
    var c3 = document.getElementById("container7");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var _chartColumn10 = cloudChart(eval(data[0]),"container7");
    }
}

/* var nodes = [];
var links = [];
var constMaxRadius = 10;
var constMinRadius = 4;
var constMaxDepth = 1;
var constMaxChildren = 2;
var constMinChildren = 1;

function rangeRandom(min, max) {
    return Math.random() * (max - min) + min;
}

function createRandomNode(depth) {
    var node = {
        name : 'NODE_' + nodes.length,
        value : rangeRandom(constMinRadius, constMaxRadius),
        // Custom properties
        id : nodes.length,
        depth : depth,
        category : depth === constMaxDepth ? 0 : 1
    }
    nodes.push(node);

    return node;
}

function forceMockThreeData() {
    var depth = 0;
    var rootNode = {
        name : 'ROOT',
        value : rangeRandom(constMinRadius, constMaxRadius),
        // Custom properties
        id : 0,
        depth : 0,
        category : 2
    }
    nodes.push(rootNode);

    function mock(parentNode, depth) {
        var nChildren = Math.round(rangeRandom(constMinChildren, constMaxChildren));

        for (var i = 0; i < nChildren; i++) {
            var childNode = createRandomNode(depth);
            links.push({
                source : parentNode.id,
                target : childNode.id,
                weight : 1
            });
            if (depth < constMaxDepth) {
                mock(childNode, depth + 1);
            }
        }
    }

    mock(rootNode, 0);
}

forceMockThreeData();*/
function forceChart(data,dom){
    var config = require(
        [
            'echarts',
            'echarts/chart/force',
        ],
        function (ec) {
            var chart5 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                calculable : false,
                tooltip : {
                    trigger: 'item',
                    formatter: '{b}'
                },
                toolbox: {
                    show : false,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                /*legend: {
                    x: 'left',
                    data:['叶子节点','非叶子节点', '根节点']
                },*/
                series : [
                    {
                        type:'force',
                        name : "Force tree",
                        ribbonType: false,
                        categories : [
                            {
                                name: '叶子节点'
                            },
                            {
                                name: '非叶子节点'
                            },
                            {
                                name: '根节点'
                            }
                        ],
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true,
                                    position: 'bottom',
                                    textStyle:{
                                        fontSize:14,
                                        color:"#406bc6"
                                    }
                                },
                                labelLine : {
                                    show : true
                                },
                                nodeStyle : {
                                    brushType : 'both',
                                    borderColor : 'rgba(255,215,0,0.6)',
                                    borderWidth : 1 ,
                                    color : '#87cffb'
                                }
                            }

                        },
                        minRadius : 15,
                        maxRadius : 15,
                        coolDown: 0.995,
                        steps: 10,
                        gravity: 1,
                        scaling: 1.2,
                        linkSymbol: 'arrow',
                        nodes : data[0],
                        links : data[1],
                        roam:false
                    }
                ]
            };
            chart5.setOption(option);
            var enConfig = require('echarts/config');

        });
}



function relatedWordCallback(data){
    var c3 = document.getElementById("container9");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var nodes = [];
        var links = [];
        var rootNode;
        var total = 0;
        for(var i=0;i<data.length;i++){
            if(i==0){
                rootNode = {
                    name : data[i].name,
                    value : 0,
                    // Custom properties
                    id : i,
                    depth : 0,
                    category : 2,
                    itemStyle:{
                        normal: {
                            color : '#f29300'
                        }
                    }
                }
                nodes.push(rootNode);
            }else{
                total += data[i].num;
                var node = {
                    name : data[i].name+"("+data[i].num+")",
                    label:data[i].name,
                    value : data[i].num,
                    // Custom properties
                    id : i,
                    depth : 1,
                    category : 0,
                    itemStyle:{
                        normal: {
                            color : '#277bc0'
                        }
                    }
                }
                nodes.push(node);
                links.push({
                    source : rootNode.id,
                    target : node.id,
                    weight : 1
                });
            }

        }
        rootNode.value=total/10;
        forceChart([nodes,links],"container9");
    }
}

function treeChart(data,dom){
//    	var chart5 = ec.init(document.getElementById(dom));
    var chart5 = echarts.init(document.getElementById(dom));
    chart5.setOption(option = {
        animation : false,
        calculable : false,
        tooltip : {
            trigger: 'item',
            formatter: "{b}"
        },
        toolbox: {
            show : false,
            orient:'vertical',
            y:30,
            x:'right',

            feature : {
                mark : {show: false},
                dataView : {
                    show: false,
                    readOnly: false,
                    lang: ['数据视图', '关闭', '刷新']
                },
                restore : {show: true},
                saveAsImage : {
                    show: true
                }
            }
        },
        series : [
            {
                name:'树图',
                type:'tree',
                orient: 'horizontal',  // vertical horizontal
                rootLocation: {x: 10,y: '40%'}, // 根节点位置  {x: 100, y: 'center'}
                nodePadding: 10,
                layerPadding: 180,
                hoverable: false,
                roam: true,
                /*symbolSize: 3,*/
                itemStyle: {
                    normal: {
                        color: '#4883b4',
                        label: {
                            show: true,
                            position: 'right',
                            formatter: "{b}",
                            textStyle: {
                                color: '#000',
                                fontSize: 12
                            }
                        },
                        lineStyle: {
                            color: '#ccc',
                            type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                        }
                    },
                    emphasis: {
                        color: '#4883b4',
                        label: {
                            show: false
                        },
                        borderWidth: 0
                    }
                },

                data: data,
                expandAndCollapse: false
            }
        ]
    });
}
function genaretPathData(data){
    var icn = data.iContentCommonNet;
    var icnList = data.iContentCommonNetList;
    var node = {
        name: icn.title,
        label:icn.captureWebsiteName,
        value: 10,
        symbolSize: 15,
        itemStyle: {
            normal: {
                color: '#fa6900',
                label: {
                    show: true,
                    position: 'right'
                }

            },
            emphasis: {
                label: {
                    show: false
                },
                borderWidth: 0
            }
        }
    }
    node.children = new Array();
    if(icnList.length>0){
        $.each(icnList,function(){
            node.children.push(genaretPathData(this));
        });
        return node;
    }else{
        return node;
    }
}

function pathCallback(data){
    var c3 = document.getElementById("container8");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var txt = data[1];
        $("#pathTxt").html(txt);
        data = data[0];
        data = eval(data);
        treeChart(data,"container8");
    }
}

function typicalPieCallBack(data,id){
    console.log(data);
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else if(id=="container12"){
            if(data[0].data!=null && data[0].data.length>10){
                data[0].data=data[0].data.slice(0,10);
            }
            if(data[0].legend!=null && data[0].legend.length>10){
                data[0].legend=data[0].legend.slice(0,10);
            }
            var _chartColumn10 = typicalPieChart2(data[0],id);
        }else{
            //typicalPieChart2(data[0],id);
            viewBarChart(data[0],id);
        }

    }
}
function typicalPieChart1(data,dom){
    var config = require(
        [
            'echarts',
            'echarts/chart/pie',
        ],
        function (ec) {
            var chart4 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                calculable : false,
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                toolbox: {
                    show : false,
                    orient:'vertical',//vertical|horizontal
                    y:30,
                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['点击保存']
                        }
                    }
                },
                calculable : false,
                legend:{
                    orient : 'vertical',
                    x : '60%',
                    y : 'center',
                    data:data.legend,
                    formatter:function(v){
                        return v.length>30? v.substr(0,30):v;
                    }
                },
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        center:['25%','50%'],
                        radius : '55%',
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : true,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : true
                                }
                            },
                            emphasis : {
                                label : {
                                    show : false,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:data.data
                    }
                ]
            }
            chart4.setOption(option);
            var enConfig = require('echarts/config');

        }
    );

}
function typicalPieChart2(data,dom){
    var config = require(
        [
            'echarts',
            'echarts/chart/pie',
        ],
        function (ec) {
            var chart4 = ec.init(document.getElementById(dom));
            var option = {
                animation : false,
                calculable : false,
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                toolbox: {
                    show : false,
                    orient:'vertical',//vertical|horizontal
                    y:30,
                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['点击保存']
                        }
                    }
                },
                calculable : false,
                legend:{
                    orient : 'vertical',
                    x : 'center',
                    y : '350',
                    data:data.legend,
                    formatter:function(v){
                        return v.length>22? v.substr(0,22)+"...":v;
                    }
                },
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        center:['50%',200],
                        radius : ['20%','45%'],
                        roseType : 'radius',
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : true,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : true
                                }
                            },
                            emphasis : {
                                label : {
                                    show : false,
                                    position : 'top',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:data.data
                    }
                ]
            }
            chart4.setOption(option);
            var enConfig = require('echarts/config');

        }
    );

}

function viewBarChart(data,dom){

    var num = 0;
    $.each(data.data,function(i){
        num =i;
    });
    num++;
    var height = num*45+60;
    $("#"+dom).css("height",height+"px");
    var config = require(
        [
            'echarts',
            'echarts/chart/bar'
        ],
        function (ec) {
            var chart2 = ec.init(document.getElementById(dom));
            var option = {
                tooltip : {         // Option config. Can be overwrited by series or data
                    trigger: 'axis',
                    formatter:"{b}  影响力:{c}"
                },
                toolbox: {
                    show : false,
                    orient:'horizontal',
                    y:5,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['数据视图', '关闭', '刷新']
                        },
                        restore : {show: false},
                        saveAsImage : {
                            show: false,
                            name:data.title
                        }
                    }
                },
                xAxis:[{
                    type : 'value',
                    /*axisLine: {
                     onZero: false,
                     show:false
                     },
                     splitArea: {
                     show: false
                     },*/
                    splitLine:{
                        show:false
                    },
                    axisLabel:{
                        textStyle : {
                            decoration: 'none',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: 12,
                            color:"#000"
                        },
                        formatter:function(v){
                            if(v>=1000){
                                return (v/1000)+"k";
                            }else{
                                return v;
                            }
                        }
                    }
                }],
                grid:{
                    y:30,
                    y2:30,
                    x:30,
                    x2:30,
                    borderWidth:0
                },
                yAxis : [{
                    show:false,
                    type : 'category',
                    data : data.legend,
                    axisLabel : {
                        formatter: function(value){
                            if(value.length>10){
                                value = value.substring(0,10);
                            }
                            return value;
                        },
                        /*rotate:45,*/
                        textStyle : {
                            decoration: 'none',
                            fontFamily: 'Microsoft YaHei',
                            fontSize: 12,
                            color:"#000"
                        }

                    },
                    /*axisLine: {
                     onZero: false,
                     show:false
                     },
                     splitLine:{
                     show:false
                     },*/
                    splitArea:{
                        show:true,
                        areaStyle:{
                            color:['#08172c','#08172c']
                        }
                    }

                }],

                /*color:['#87cefa','#ff7f50'],*/
                calculable : false,
                animation:false,
                series : [{
                    name:'数量',
                    type:'bar',
                    itemStyle : {
                        normal: {label : {
                            show: true, position: 'insideLeft',
                            formatter:function(param){
                                var value = param.name;
                                if(value.length>30){
                                    value = value.substring(0,30);
                                }
                                return value;
                            },
                            textStyle:{fontSize:12,color:"#000"}
                        }
                        },
                        emphasis:{label : {show: true,textStyle:{fontSize:12,color:"#000"}}}},
                    data:data.data
                }]
            }
            chart2.setOption(option);
            var enConfig = require('echarts/config');

        }
    );
}

function typicalListAdapter(data){
    if (data[4]==null||data[4]==""){
        $("#container10List").hide();
    }else{
        var list = eval(data[4]);
        typicalListJoin(list,"container10List");
    }
    if (data[5]==null||data[5]=="[]"){
        $("#container11List").hide();
    }else{
        var list = eval(data[5]);
        typicalListJoin(list,"container11List");
    }
    if (data[6]==null||data[6]=="[]"){
        $("#container12List").hide();
    }else{
        var list = eval(data[6]);
        typicalListJoin(list,"container12List");
    }
}
function typicalListJoin(data,dom){
    var lenght = data.length>4?4:data.length;
    var title = "网友";
    if(dom=="container10List"){
        title = "媒体";
    }
    var html = '<div class="title"><h1 contenteditable="false" class="contenteditable"><i class="icon-users"></i> '+title+'观点主要表现在如下几个方面：</h1></div><ul><li>';
    for(var i=0;i<lenght;i++){
        var stat = data[i];
        html += '<li><h2 contenteditable="false" class="contenteditable"><i class="deleLi"></i>'+stat.name+'('+stat.percentStr+')</h2>';
        var ICNList = stat.iContentCommonNetList;
        var len = ICNList.length>3?3:ICNList.length;
        for(var j=0;j<len;j++){
            var icn = ICNList[j];
            if(icn){
                var author = icn.author;
                if(!author||author==null||author==""){
                    author = icn.captureWebsiteName;
                }
                html += '<a class="shareA" href="'+icn.webpageUrl+'" target="_blank"><p contenteditable="false" class="contenteditable">'+author+"："+icn.title+'。</p></a>';
            }
        }
        html += '</li>';
    }
    html += '</li></ul>';
    $("#"+dom).html(html);
}


function typicalPieAdapter(data){
    typicalPieCallBack(data[0],"container10");
    typicalPieCallBack(data[1],"container11");
    typicalPieCallBack(data[2],"container12");
    typicalListAdapter(data);
    $("#summary").html(data[3]);
}

function hotPeopleCallback(data){
    var hot = document.getElementById("hotPeople");
    if (data==null||data==""){
        $(hot).find("div").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxBasePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        return false;
    }else{
        var txt = data[0];
        $("#peopleTxt").html(txt);
        data = data[1];
        var all = data[0].iContentCommonNetList;
        var forum = data[1].iContentCommonNetList;
        var blogs = data[2].iContentCommonNetList;
        var weibo = data[3].iContentCommonNetList;

        if(all==null ||all.length==0){
            $("#hotPeople>div:eq(0)").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxBasePath +"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        }else{
            var html = '<div class="mwblist"><ul>';
            $.each(all,function(){
                var verifyType = this.verifyType;
                var type = "";
                if(verifyType==0){
                    type = " icon_approve";
                }else if(verifyType>0&&verifyType<8){
                    type = " icon_approve_co";
                }else if(verifyType==200||verifyType==220){
                    type = " icon_club";
                }
                var imageUrl = this.profileImageUrl;
                var originType = this.originType;
                if(imageUrl==null||imageUrl==""){
                    if(originType=="lt"){
                        imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-bbs.jpg";
                    }else if(originType=="bk"){
                        imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-blog.jpg";
                    }else{
                        imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-weibo.jpg";
                    }
                }
                var li = '<li><div class="tx"><img src="'+imageUrl+'"></div><p class="mscrame">'
                    +'<a href="'+this.webpageUrl+'" target="_blank" class="a1">'+this.author+'</a><a href="javascript:void(0)">' +
                    '<i class="W_icon'+type+'"></i></a>'
                    +'</p><p class="msfs">发帖数：'+this.num+'</p></li>' ;
                html += li;
            });
            html += "</ul></div>";
            $("#hotPeople>div:eq(0)").html(html);
        }

        if(forum==null ||forum.length==0){
            $("#hotPeople>div:eq(2)").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style='width:60px'><br/>此时间段暂无信息</p></div>");
        }else{
            var html = '<div class="mwblist"><ul>';
            $.each(forum,function(){
                var imageUrl = this.profileImageUrl;
                if(imageUrl==null||imageUrl==""){
                    imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-bbs.jpg";
                }
                var li = '<li><div class="tx"><img src="'+imageUrl+'"></div><p class="mscrame">'
                    +'<a href="'+this.webpageUrl+'" target="_blank" class="a1">'+this.author+'</a><a href="javascript:void(0)">' +
                    '<i title=" " class="W_icon"></i></a>'
                    +'</p><p class="msfs">发帖数：'+this.num+'</p></li>' ;
                html += li;
            });
            html += "</ul></div>";
            $("#hotPeople>div:eq(2)").html(html);
        }

        if(blogs==null ||blogs.length==0){
            $("#hotPeople>div:eq(3)").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style='width:60px'><br/>此时间段暂无信息</p></div>");
        }else{
            var html = '<div class="mwblist"><ul>';
            $.each(blogs,function(){
                var imageUrl = this.profileImageUrl;
                if(imageUrl==null||imageUrl==""){
                    imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-blog.jpg";
                }
                var li = '<li><div class="tx"><img src="'+imageUrl+'"></div><p class="mscrame">'
                    +'<a href="'+this.webpageUrl+'" target="_blank" class="a1">'+this.author+'</a><a href="javascript:void(0)">' +
                    '<i title=" " class="W_icon"></i></a>'
                    +'</p><p class="msfs">发帖数：'+this.num+'</p></li>' ;
                html += li;
            });
            html += "</ul></div>";
            $("#hotPeople>div:eq(3)").html(html);
        }

        if(weibo==null ||weibo.length==0){
            $("#hotPeople>div:eq(1)").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath +"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        }else{
            var html = '<div class="mwblist"><ul>';
            $.each(weibo,function(){
                var verifyType = this.verifyType;
                var type = "";
                if(verifyType==0){
                    type = " icon_approve";
                }else if(verifyType>0&&verifyType<8){
                    type = " icon_approve_co";
                }else if(verifyType==200||verifyType==220){
                    type = " icon_club";
                }
                var imageUrl = this.profileImageUrl;
                if(imageUrl==null||imageUrl==""){
                    imageUrl = staticResourcePath +"/images/userlogo/app-more-icon-weibo.jpg";
                }
                var li = '<li><div class="tx"><img src="'+imageUrl+'"></div><p class="mscrame">'
                    +'<a href="'+this.webpageUrl+'" target="_blank" class="a1">'+this.author+'</a><a href="javascript:void(0)">' +
                    '<i title="" class="W_icon '+type+'"></i></a>'
                    +'</p><p class="msfs">发帖数：'+this.num+'</p></li>' ;
                html += li;
            });
            html += "</ul></div>";
            $("#hotPeople>div:eq(1)").html(html);
        }

    }
}

var num=1;
$(".more").on("click",function(){
    if(num){
        num--;
        $("#trend > li:gt(3)").show();
        $(this).html("<span class='packUp'><a href='javascript:void(0)'><label>收拢事件进展&nbsp;<i></i></label></a></span>");
    }else{
        num++;
        $("#trend > li:gt(3)").hide();
        $(this).html("<span class='packUp2'><a href='javascript:void(0)'><label>展开事件进展&nbsp;<i></i></label></a></span>");
    }
});
