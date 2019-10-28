function treeMapCallback(data,el){

        var c3 = document.getElementById(el);
        if (data==null||data==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            var _chartColumn10 = treeMapChart(eval(data[0]),el);
        }
    }
function LineAreaCallBack(data,el){
    /*var c1 = document.getElementById("container1");*/
    var c1 = document.getElementById(el);
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineAreaChart(data[0],el);
            $("#container1 + .text").text(data1);
        }
    }
}
function LineAreaChart(data,dom){

    var splitNum = 0;
    if(data.datetime.length>12){
        splitNum = 2;
    }

    $.each(data.data,function(){
        this.symbolSize = 6;
        this.itemStyle={'normal':{'areaStyle':{'type':'default'}}};
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
                        show : true,
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
                            }
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
            }
    );
}


function treeMapChart(data,dom){
    var colors = ["#00b2f4","#2c4f79","#00b2f4","#2c4f79"];
    $.each(data,function(i){
        this.itemStyle.normal.color = colors[i%4];
    });
    var config = require(
            [
                'echarts',
                'echarts/chart/treemap',
            ],
            function (ec) {
                var chart4 = ec.init(document.getElementById(dom));
                var option = {
                    /*title : {
                     text: '手机占有率',
                     subtext: '虚构数据'
                     },*/
                    tooltip : {
                        trigger: 'item',
                        formatter: "{b}: {c}"
                    },
                    toolbox: {
                        show : false,
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true} ,
                            saveAsImage : {show: true} 
                        }
                    },
                    animation : false,
                    calculable : false,
                    /*grid:{
                     y2:80,
                     x:30,
                     x2:30

                     },*/
                    color:["#00b2f4","#2c4f79"],
                    series : [
                        {
                            name:'矩形图',
                            type:'treemap',
                            size:['95%','95%'],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true,
                                        formatter: "{b}",
                                        /*x:50,
                                         y:50,*/
                                        textStyle: {
                                            fontSize:'18',
                                            color:"#fff"
                                            /*fontWeight:'bold'*/

                                        }
                                    },
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        show: true
                                    }
                                },
                                breadcrumb:{
                                    show:true
                                }
                            },
                            data:data
                        }
                    ]
                }
                chart4.setOption(option);
                var enConfig = require('echarts/config');

            }
    );

}
function LineStoreCallBack(data,el){
    //var c1 = document.getElementById("container1");
    var c1 = document.getElementById(el);
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineStoreChart(data[0],el);
            $("#container1 + .text").text(data1);
        }
    }
}
function LineStoreChart(data,dom){

    var splitNum = 0;
    if(data.datetime.length>12){
        splitNum = 2;
    }

    $.each(data.data,function(){
        this.itemStyle={'normal':{'areaStyle':{'type':'default'}}};
    });
    var config = require(
            [
                'echarts',
                'echarts/chart/line'
            ],
            function (ec) {
                var chart1 = ec.init(document.getElementById(dom));
                var option = {
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
                    legend: {
                        data:data.legend
                    },
                    toolbox: {
                        show : true,
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
                            restore : {show: true} ,
                            saveAsImage : {
                                show: true,
                                name:data.name
                            } 
                        }
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

                    animation : false,
                    calculable : false,
                    splitNumber:splitNum,
                    series : data.data
                };

                chart1.setOption(option);
                var enConfig = require('echarts/config');
            }
    );
}




function cloudParallelChart(data,dom){
    var config = require(
            [
                'echarts',
                'echarts/chart/wordCloud'
            ],
            function (ec) {
                var chart2 = ec.init(document.getElementById(dom));
                var option = {
                    animation : false,
                    tooltip: {
                        show: true,
                        formatter:function(params){
                            var num = params.value;
                            num = parseInt(num)/10;
                            return params.name+" : "+ num;
                        }
                    },
                    toolbox: {
                        show : true,
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
                            restore : {show: true} ,
                            saveAsImage : {
                                show: true,
                                name:data.title
                            } 
                        }
                    },
                    calculable : false,
                    series: [{
                        /*name: 'Google Trends',*/
                        type: 'wordCloud',
                        size: ['90%', '90%'],
                        textRotation : [0, 0, 0, 0],
                        textPadding: 2,
                        autoSize: {
                            enable: true,
                            minSize: 18
                        },
                        data: data
                    }]
                };
                chart2.setOption(option);
                var enConfig = require('echarts/config');
            }
    );
}
function cloudParallelCallback(data,el){
    var c3 = document.getElementById(el);
    //var c3 = document.getElementById("container7");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        //var _chartColumn10 = cloudParallelChart(eval(data[0]),"container7");
        var _chartColumn10 = cloudParallelChart(eval(data[0]),el);
    }
}
function typicalPieCallBack8(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            QGPieChart1(data[0],el);//环形图
        }
    }
}
function typicalPieCallBack9(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            viewBarChart(data[0],el);//柱状图
        }
    }
}
function forceChart1(data,dom){
    var config = require(
            [
                'echarts',
                'echarts/chart/force',
            ],
            function (ec) {
                var chart5 = ec.init(document.getElementById(dom));
                var option = {
                    tooltip : {
                        trigger: 'item',
                        formatter: '{a} : {b}'
                    },
                    toolbox: {
                        show : true,
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
                            restore : {show: true} ,
                            saveAsImage : {
                                show: true,
                                name:data.title
                            } 
                        }
                    },
                    /*    legend: {
                     x: 'left',
                     data:['叶子节点','非叶子节点', '根节点']
                     },*/
                    animation : false,
                    calculable : false,
                    series : [
                        {
                            type: 'force',
                            /*name: "Force tree",*/
                            ribbonType: false,
                            categories: [
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
                                        show: false
                                    },
                                    nodeStyle: {
                                        brushType: 'both',
                                        borderColor: 'rgba(255,215,0,0.6)',
                                        borderWidth: 1
                                    }
                                }
                            },
                            minRadius: 10,
                            maxRadius: 25,
                            coolDown: 0.995,
                            steps: 10,
                            nodes: data[0],
                            links: data[1],
                            steps: 1

                        }],
                };
                chart5.setOption(option);
                var enConfig = require('echarts/config');

            });
}

function relatedWordCallback2(data,el){
    var c3 = document.getElementById(el);
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        /*var txt = data[1];
         $("#pathTxt").html(txt);*/
        /*data = data[0];
         data = eval(data);*/

        treeChart(eval(data[0]),el);
    }
}
function QGPieChart1(data,dom){
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
                        show : true,
                        orient:'vertical',//vertical|horizontal
                        y:30,
                        feature : {
                            mark : {show: false},
                            dataView : {
                                show: false,
                                readOnly: false,
                                lang: ['数据视图', '关闭', '刷新']
                            },
                            restore : {show: true} ,
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
                        show:true,
                        orient : 'vertical',
                        x : '60%',
                        y:'center',
                        data:data.legend.reverse()
                    },
                    series : [
                        {
                            name:data.title,
                            type:'pie',
                            radius : ['35%', '60%'],
                            center:['25%','50%'],
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
                                            fontSize : '12',
                                            fontWeight : 'normal'
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

function typicalPieAdapter1(data,el){
    typicalPieCallBack1(data[0],el);
}
function typicalPieAdapter2(data,el){
    typicalPieCallBack2(data[0],el);
}
function typicalPieAdapter3(data,el){
    typicalPieCallBack3(data[0],el);
}

function typicalPieAdapter4(data,el){
    typicalPieCallBack4(data[1],el);
}
function typicalPieAdapter5(data,el){
    typicalPieCallBack5(data[1],el);

}
function typicalPieAdapter6(data,el){
    typicalPieCallBack6(data[1],el);
}

function typicalPieAdapter7(data,el){
    typicalPieCallBack7(data[2],el);
}
function typicalPieAdapter8(data,el){
    typicalPieCallBack8(data[2],el);
}
function typicalPieAdapter9(data,el){
    typicalPieCallBack9(data[2],el);
}
function typicalPieCallBack1(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            WTPieChart(data[0],el);//饼图
        }
    }
}
function typicalPieCallBack2(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            QGPieChart1(data[0],el);//环形图
        }
    }
}
function typicalPieCallBack3(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            typicalPieChart2(data[0],el);//南丁格尔玫瑰
        }
    }
}

function typicalPieCallBack4(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            WTPieChart(data[0],el);//饼图
        }
    }
}
function typicalPieCallBack5(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            QGPieChart1(data[0],el);//环形图
        }
    }
}
function typicalPieCallBack6(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            typicalPieChart2(data[0],el);//南丁格尔玫瑰
        }
    }
}

function typicalPieCallBack7(data,el) {
    var c4 = document.getElementById(el);
    if (data == null || data == "") {
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    } else {
        data = eval(data);
        if (data == null || data.length == 0 || data[0].data == null || data[0].data.length == 0) {
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+actionBase+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        } else {
            WTPieChart(data[0],el);//饼图
        }
    }
}

function exist(id){
    var s=document.getElementById(id);
    if(s){
        return true;
    }else{
        return false;
    }
}
function saveReport(){
    var eleIds = "{\"elementList\":[";
    var sum=0;
    var list=$(".ui-sortable>div.row-fluid");

    $.each(list,function(n) {
        var thisId = $(this).attr("id");
        var pos = thisId.lastIndexOf("-");
        sum++;
        var eId = parseInt(thisId.substring(pos + 1, thisId.length));
        eleIds += "{\"elementId\":" + eId + ",\"name\":\"" + Trim($(".modification").eq(sum - 1).text().replace(/"|'/g,"")) + "\"";

        if(thisId=="ui-accordion-1"){
            var captions=$(this).find(".textShow ul.ui-sortable li .text div.contenteditable");
            if(captions.length>0){
                eleIds += ",\"content\":\"";
                var cont="";
                for (var i = 0; i < captions.length; i++) {
                    var coption = captions[i];
                    $(coption).find('br:last').remove();
                    cont+= $(coption).html();
                    if(i!=(captions.length-1)){
                    	cont+= "</br>";
                    }
                }
                var arr = cont.split("\"");
                for(var i=0;i<arr.length;i++){
                	eleIds+=arr[i]+"'";
                }
                eleIds=eleIds.substring(0,eleIds.length-1);
                eleIds+= "\"";
            }
        }

        if(thisId=="ui-accordion-2") {
            var elementId = thisId.substr(thisId.length-1);
            var treeTxts=$(this).find(".textShow .ui-sortable .text div.texteditor");
            if(treeTxts.length>0){
                eleIds += ",\"content\":\"";
                for (var i = 0; i < treeTxts.length; i++) {
                    var treeTxt=treeTxts[i];
                    $(treeTxt).find('br:last').remove();
                    //eleIds +=  $(treeTxt).text();
                    eleIds +=  $(treeTxt).html();
                    if(i!=(treeTxts.length-1)){
                        eleIds+= "</br>"
                    }
                }
                eleIds+= "\"";
            }

            var treeTime=$("#eventProfile").find("h2 div span.contenteditable");
            if($.trim(treeTime.text())!=""){
                eleIds += ",\"treeTime\":\"" + $(treeTime).text().replace(/"|'/g,"") + "\"";
            }

            var treeTexts = $("#trend>li>.text2").find("div");
            if (treeTexts.length > 0) {
                eleIds += ",\"treeText\":\"";
                for (var j = 0; j < treeTexts.length; j++) {
                    var treeText = treeTexts[j];
                    var url=$(treeText).find("a")[0];
                    if($(url).length>0){
                        eleIds+=url.href+"<n/>";
                    }
                    eleIds += $(treeText).text().replace(/"|'/g,"");
                    if(j!=(treeTexts.length-1)){
                        eleIds+= "</br>"
                    }
                }
                eleIds += "\"";
            }
        }

        if(thisId=="ui-accordion-6"){
            var hotTxts=$(this).find(".textShow .ui-sortable .text div.texteditor");
            if(hotTxts.length>0){
                eleIds += ",\"content\":\"";
                for (var i = 0; i < hotTxts.length; i++) {
                    var hotTxt=hotTxts[i];
                    $(hotTxt).find('br:last').remove();
                    eleIds += $(hotTxt).html();
                    if(i!=(hotTxts.length-1)){
                        eleIds+= "</br>"
                    }
                }
                eleIds+= "\"";
            }

            var hotTexts = $("#hotNews>tr");
            if(hotTexts.length>0){
                eleIds += ",\"hotText\":\"";
                for (var j = 0; j < hotTexts.length; j++) {
                    var hotText = hotTexts[j];
                    var hotSubTexts=$(hotText).find("td").find("div");
                    if(hotSubTexts.length>0){
                        for(var k=0;k<hotSubTexts.length;k++){
                            var hotSubText = hotSubTexts[k];
                            var hotUrl=$(hotSubText).find("a")[0];
                            if($(hotUrl).length>0){
                                eleIds+=hotUrl.href+"<c/>";
                            }
                            eleIds+=$(hotSubText).text().replace(/"|'/g,"")+"<n/>";
                        }
                    }
                    if(j!=(hotTexts.length-1)){
                        eleIds+= "</br>"
                    }
                }
                eleIds += "\"";
            }
        }

        if(thisId=="ui-accordion-7") {
            var hotPeopleTxts=$(this).find(".textShow .ui-sortable li .text div.texteditor");
            if(hotPeopleTxts.length>0){
                eleIds += ",\"content\":\"";
                for (var i = 0; i < hotPeopleTxts.length; i++) {
                    var hotPeopleTxt=hotPeopleTxts[i];
                    $(hotPeopleTxt).find('br:last').remove();
                    eleIds +=  $(hotPeopleTxt).html();
                    if(i!=(hotPeopleTxts.length-1)){
                        eleIds+= "</br>";
                    }
                }
                eleIds+= "\"";
            }

            if($("#listBox").is(":hidden")){
                eleIds += ",\"picStyle\":" + 2 + "";
            }else{
                eleIds += ",\"picStyle\":" + 1 + "";
            }
        }

        if(thisId=="ui-accordion-11"){
            var summarys=$(this).find(".textShow ul.ui-sortable li .text div.contenteditable");
            if(summarys.length>0){
                eleIds += ",\"content\":\"";
                for (var i = 0; i < summarys.length; i++) {
                    var summary=summarys[i];
                    $(summary).find('br:last').remove();
                    eleIds +=  $(summary).html();
                    if(i!=(summarys.length-1)){
                        eleIds+= "</br>"
                    }
                }
                eleIds+= "\"";
            }
        }
        eleIds+=",\"elementNextList\":[";


        if(thisId=="ui-accordion-3") {
            var elementId = thisId.substr(thisId.length-1);
            var modelNextTexts=$(this).find(".textShow .ui-sortable li .text .texteditor");
            if(modelNextTexts.length>0){
                for(var j=0;j<modelNextTexts.length;j++){
                    var modelNextText=modelNextTexts[j];
                    eleIds += "{\"elementId\":" + elementId+",\"modelNextText\":\"" + Trim($(modelNextText).text().replace(/"|'/g,""))+"\"}";
                    if((modelNextTexts.length-1)!=j){
                        eleIds+=",";
                    }
                }
            }
            var li =$(this).find(".textShow .ui-sortable .chartMapBK");
            if(li.length>0){
                if(modelNextTexts.length>0)
                {
                    eleIds += ",";
                }
                for(var k=0;k<li.length;k++){
                    //var id1="#container1_"+k;
                    var id1=$(li[k]).find(".chartMap div.picBox").attr("id");
                    eleIds += "{\"elementId\":" + elementId+",\"elementStyleId\":" + $("#"+id1).parents("div.chartMap").find("div.chartChoice ul li.clicked p img").attr("id")+"}";
                    if((li.length-1)!=k){
                        eleIds+=",";
                    }
                }
            }
        }

        if(thisId=="ui-accordion-4") {
            var elementId1 = thisId.substr(thisId.length-1);
            var sourceNextTexts=$(this).find(".textShow .ui-sortable li .text .texteditor");
            if(sourceNextTexts.length>0){
                for(var j=0;j<sourceNextTexts.length;j++) {
                    var sourceNextText=sourceNextTexts[j];
                    eleIds += "{\"elementId\":" + elementId1 + ",\"modelNextText\":\"" + Trim($(sourceNextText).text().replace(/"|'/g,"")) + "\"}";
                    if((sourceNextTexts.length-1)!=j){
                        eleIds+=",";
                    }
                }
            }

            var sourcePics=$(this).find(".textShow .ui-sortable li .text .textCon .row-fluid .wyqBorder");
            if(sourcePics.length>0){
                if(sourceNextTexts.length>0){
                    eleIds+=",";
                }
                var type=0;
                for(var j=0;j<sourcePics.length;j++){
                    var sourcePic=sourcePics[j];
                    var title=$(sourcePic).find("h2");
                    var picId=$(sourcePic).find("div").attr("id");
                    if(picId=="container2")
                        type=11;
                    if(picId=="container3")
                        type=16;
                    if(picId=="container4")
                        type=12;
                    if(picId=="container5")
                        type=13;
                    if(picId=="container6")
                        type=14;
                    eleIds += "{\"elementId\":" + elementId1 +  ",\"elementStyleId\":" + type +",\"modelNextName\":\"" + Trim($(title).text().replace(/"|'/g,"")) + "\"}";
                    if((sourcePics.length-1)!=j){
                        eleIds+=",";
                    }
                }
            }
        }

        if(thisId=="ui-accordion-5") {
            var elementId2 = thisId.substr(thisId.length - 1);

            var wordNextTexts = $(this).find(".textShow .ui-sortable li .text .texteditor");
            if (wordNextTexts.length > 0) {
                for (var j = 0; j < wordNextTexts.length; j++) {
                    var wordNextText = wordNextTexts[j];
                    eleIds += "{\"elementId\":" + elementId2 + ",\"modelNextText\":\"" + Trim($(wordNextText).text().replace(/"|'/g,"")) + "\"}";
                    if ((wordNextTexts.length - 1) != j) {
                        eleIds += ",";
                    }
                }
            }
            var li = $(this).find(".textShow .ui-sortable .chartMapBK");
            if (li.length > 0) {
                if (wordNextTexts.length > 0) {
                    eleIds += ",";
                }
                for (var k = 0; k < li.length; k++) {
                    var id2=$(li[k]).find(".chartMap div.picBox").attr("id");
                    eleIds += "{\"elementId\":" + elementId2 + ",\"elementStyleId\":" + $("#"+id2).parents("div.chartMap").find("div.chartChoice ul li.clicked p img").attr("id") + "}";
                    if ((li.length - 1) != k) {
                        eleIds += ",";
                    }
                }
            }
        }


        if(thisId=="ui-accordion-8") {
            var elementId4 = thisId.substr(thisId.length - 1);
            var spreadWays = $(this).find(".textShow .ui-sortable li div.texteditor");
            if (spreadWays.length > 0) {
                for (var i = 0; i < spreadWays.length; i++) {
                    var spreadWay = spreadWays[i];
                    eleIds += "{\"elementId\":" + elementId4 + ",\"modelNextText\":\"" + Trim($(spreadWay).text().replace(/"|'/g,"")) + "\"}";
                    if(i!=(spreadWays.length-1)){
                        eleIds += ",";
                    }
                }
            }
            var picWays=$(this).find(".textShow .ui-sortable div.chartMapBK");
            if(picWays.length>0){
                if(spreadWays.length>0){
                    eleIds += ",";
                }
                for(var j=0;j<picWays.length;j++){
                    //var id3 = "#container8_" + j;
                    var id3=$(picWays[j]).find(".chartMap div.picBox").attr("id");
                    eleIds += "{\"elementId\":" + elementId4 + ",\"elementStyleId\":" + $("#"+id3).parents("div.chartMap").find("div.chartChoice ul li.clicked p img").attr("id") + "}";
                    if ((picWays.length - 1) != j) {
                        eleIds += ",";
                    }
                }
            }
            var pathText=$(this).find(".textShow .ui-sortable li div.tool .pathTxt");
            if(pathText.length>0){
                if(picWays.length>0||spreadWays.length>0){
                    eleIds += ",";
                }
                eleIds += "{\"elementId\":" + elementId4 + ",\"modelNextName\":\"" + "pathTxt" +"\",\"modelNextText\":\"" + Trim($(pathText).text().replace(/"|'/g,"")) + "\"}";
            }


        }

        if(thisId=="ui-accordion-9") {
            var elementId3 = thisId.substr(thisId.length - 1);
            var relatedWords = $(this).find(".textShow .ui-sortable li div.texteditor");
            if (relatedWords.length > 0) {
                for (var i = 0; i < relatedWords.length; i++) {
                    var relatedWord = relatedWords[i];
                    eleIds += "{\"elementId\":" + elementId3 + ",\"modelNextText\":\"" + Trim($(relatedWord).text().replace(/"|'/g,""))+ "\"}";
                    if(i!=(relatedWords.length-1)){
                        eleIds += ",";
                    }
                }
            }
            var picRelatedWords=$(this).find(".textShow .ui-sortable div.chartMapBK");
            if(picRelatedWords.length>0){
                if(relatedWords.length>0){
                    eleIds += ",";
                }
                for(var j=0;j<picRelatedWords.length;j++){
                    //var id4 = "#container9_" + j;
                    var id4=$(picRelatedWords[j]).find(".chartMap div.picBox").attr("id");
                    eleIds += "{\"elementId\":" + elementId3 + ",\"elementStyleId\":" + $("#"+id4).parents("div.chartMap").find("div.chartChoice ul li.clicked p img").attr("id") + "}";
                    if ((picRelatedWords.length - 1) != j) {
                        eleIds += ",";
                    }
                }
            }
        }

        if(thisId=="ui-accordion-10") {
            var elementId3 = thisId.substr(thisId.length-2);
            var viewpointTexts=$(this).find(".textShow .ui-sortable li .text .texteditor");
            if(viewpointTexts.length>0){
                for(var j=0;j<viewpointTexts.length;j++) {
                    var viewpointText=viewpointTexts[j];
                    eleIds += "{\"elementId\":" + elementId3 + ",\"modelNextText\":\"" + Trim($(viewpointText).text().replace(/"|'/g,"")) + "\"}";
                    if((viewpointTexts.length-1)!=j){
                        eleIds+=",";
                    }
                }
            }

            var viewpointPics=$(this).find(".textShow .ui-sortable li .chartMapBK");
            if(viewpointPics.length>0) {
                if (viewpointTexts.length > 0) {
                    eleIds += ",";
                }
                for (var j = 0; j < viewpointPics.length; j++) {
                    var viewpointPic = viewpointPics[j];
                    var viewpointId = $(viewpointPic).find(".chartMap div.picBox").attr("id");
                    var titleViewpoint = $(viewpointPic).find("h2:eq(0)");
                    eleIds += "{\"elementId\":" + elementId3 +  ",\"elementStyleId\":" + $("#"+viewpointId).parents("div.chartMap").find("div.chartChoice ul li.clicked p img").attr("id") +",\"modelNextName\":\"" + $.trim(titleViewpoint.text().replace(/"|'/g,"")) +"\",\"containerId\":\"" + "#"+viewpointId + "\"}";
                    var pointId=$(viewpointPic).find(".chartMap div.viewpoint").attr("id");
                    var li=$("#"+pointId).find("ul li");
                    if(li.length>0){
                        eleIds+=",";
                        for(var t=0;t<li.length;t++){
                            eleIds += "{\"elementId\":" + elementId3+",\"containerId\":\"" + "#"+pointId + "\"";
                            var liPoint=li[t];
                            if($(liPoint).is(":hidden")){
                                eleIds += ",\"viewpoint\":\""+null+"\"";
                                eleIds += ",\"weiboUrl\":\""+null+"\"";
                            }else{
                                var h2Point=$(liPoint).find("h2");
                                var weiboUrl=$(liPoint).find("input").val();
                                if(h2Point.length>0){
                                    eleIds += ",\"viewpoint\":\""+ Trim($(h2Point).text().replace(/"|'/g,""))+"\"";
                                    eleIds += ",\"weiboUrl\":\""+ weiboUrl+"\"";
                                }
                                var pPoints=$(liPoint).find("p");
                                if(pPoints.length){
                                    eleIds += ",\"viewpointText\":\"";
                                    for(var z=0;z<pPoints.length;z++){
                                        var pPoint=pPoints[z];
                                    if($(pPoint).attr("style")=="opacity: 0.2;"){
                                        eleIds+=" ";
                                    }else{
                                        eleIds+=Trim($(pPoint).text().replace(/"|'/g,""));
                                    }
                                        if((pPoints.length-1)!=z){
                                            eleIds+="</br>";
                                        }
                                    }
                                    eleIds+= "\"";
                                }
                            }
                            eleIds+="}";
                            if((li.length-1)!=t){
                                eleIds+=",";
                            }
                        }
                    }
                    if((viewpointPics.length-1)!=j){
                        eleIds+=",";
                    }
                }
            }


        }

        eleIds += "]";
        eleIds += "},";
    });

    eleIds = eleIds.substring(0,eleIds.length-1);

    eleIds += "]}";
    eleIds.replace(/"|'/g,"");

    if(eleIds=='{"elementList":[]}'){
        alert("请选择需要修改的模板");
        return false;
    }else{
        document.getElementById("checkedIds").value = eleIds;
    }
    var title=$("#analysisTitle").text().replace(/"|'/g,"");
    document.getElementById("reportName").value = Trim(title);

    if(title==null || title==""||title=="<br>"){
        alert("标题为空，请填写标题");
        return false;
    }
    if(Trim(title).length>20){
        alert("标题长度过长，请重新填写");
        return false;
    }
    console.log(eleIds)
    $("#analysisLoading").removeAttr("onClick");
    $("#analysisLoading").html("保存报告<image style='width: 15px; height: 15px;' src=\""+actionBase+"/images/loading_c.gif\">");
    frmPopWin.action="rebuildAnalysisReport.action";
    frmPopWin.target="";
    frmPopWin.submit();


}



function typicalPieAdapter(data){
    typicalPieCallBack(data[0],"container10");
    typicalPieCallBack(data[1],"container11");
    typicalPieCallBack(data[2],"container12");
    typicalListAdapter(data);
    $("#summary").html(data[3]);
}

function typicalPieAdapter13(data){
    typicalListAdapter1(data);
}

function typicalListAdapter1(data){
    if (data[4]==null||data[4]=="[]"){
        $("#container10List").hide();
    }else{
        var list = eval(data[4]);
        typicalListJoin1(list,"container10List");
    }
    if (data[5]==null||data[5]=="[]"){
        $("#container11List").hide();
    }else{
        var list = eval(data[5]);
        typicalListJoin1(list,"container11List");
    }
    if (data[6]==null||data[6]=="[]"){
        $("#container12List").hide();
    }else{
        var list = eval(data[6]);
        typicalListJoin1(list,"container12List");
    }

    //隐藏显示观点 li p
    $('.viewpoint ul li p .showLi').click(function(e){
        if($(this).parents("p").css("opacity") == 1){
            $(this).parents("p").css("opacity","0.2");
        }else{
            $(this).parents("p").css("opacity","1");
        }
    });
}

