
//-----------------------------------------------微博事件简介-------------------------------------------------------
function profileCallback(data){
    if (data==null||data==""){
        $("#introduce").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px;position:absolute;left:125px;top:50px;\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        return false;
    }else{
        if(data[2]!=null&&data[2]!=''&&data[2].length>0){
            $("#introduce").html(data[2]);
        }else{
            $("#introduce").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px;position:absolute;left:125px;top:50px;\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        }

        /*  if(data[0]==null){
              $("#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
          }else{
              var html = "<h2>"+data[1]+"</h2>";
              html += " <div class='more'><span class='packUp2'><a href='javascript:void(0)'><label>展开事件进展&nbsp;<i></i></label></a></span></div>";
              html +='<ul style="padding-bottom: 0;" id="trend">';
              $.each(data[0],function(){
                  var published = new Date(this.published);
                  var li = '<li><div class="float_l"><span class="line"></span><span class="round"></span><span class="line"></span></div>'
                          + '<div class="sz"></div><div class="text text2"><div class="textShow">['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                          +'点]'+this.title+'['+this.captureWebsiteName+'] 相同文章数：'+this.repeatNum
                          +'</div><div class="editBox"><textarea class="inputList" id="textarea3">['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                          +'点]'+this.title+'[ '+this.captureWebsiteName+' ] 相同文章数：'+this.repeatNum
                          +'</textarea><div class="rel"><div class="tool tooledit" style="position: initial;"><button href="#" class="button button4">'
                          +'<i class="icon-dele"></i>删除</button></div></div></div></div></li>';
                  html += li;
              });
              html += '</ul>';
              html += '<ul  style="padding-top: 2px;"><li><div class="float_l"><span class="roundEnd">完</span></div></li></ul>';
              $("#eventProfile").html(html);
          }*/

    }
}




//-----------------------------------------------微博趋势统计------------------------------------------------------
function LineCallBack(data){
    var c1 = document.getElementById("container1");
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
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

                animation : false,
                calculable : false,
                series : data.data
            }
            chart1.setOption(option);
            chart1.setTheme('infographic');
            var enConfig = require('echarts/config');
        }
    );
}

//--------------------------------------- 传播途径 --------------------------------------------

function pathCallback(data){
    if(data!=null&&data!=''&&data.length>0){
        var stattime = new Date();
        if(data[0]!=null&&eval(data[0])!=null&&eval(data[0]).published!=null){
            var formatStart = eval(data[0]).published;
            stattime = new Date(formatStart);
        }

        var html = "<h2>"+(stattime.getMonth()+1)+'月'+stattime.getDate()+'日 '+stattime.getHours()+"点</h2>";
        html += " <div class='more'><span class='packUp2'><a href='javascript:void(0)'><label>展开事件进展&nbsp;<i></i></label></a></span></div>";
        html +='<ul style="padding-bottom: 0;" id="trend">';
        //console.log(data);

        $.each(data,function(n){
            if(this.content.length>500)
                this.content = this.content.substring(0,500)+"...";
            var published = new Date(this.published);
            var li = '<li><div class="float_l"><span class="line"></span><span class="round"></span><span class="line"></span></div>'
                + '<div class="sz"></div><div class="text text2"><div class="textShow" style="padding-bottom: 10px;">['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                +'点]   【'+this.author+'】'+this.content;
            //+'['+this.captureWebsiteName+'] 相同文章数：'+this.repeatNum
            if(n==0){
                li += ' <span style="position: absolute;bottom: 0;right: 0;color:#0185d8">(可能是事件源头，仅供参考)</span>';
            }
            li +='</div><div class="editBox"><textarea class="inputList" id="textarea3">['+ (published.getMonth()+1)+'月'+published.getDate()+'日 '+published.getHours()
                +'点]   【'+this.author+'】'+this.content
                //                      +'点]'+this.title+'[ '+this.captureWebsiteName+' ] 相同文章数：'+this.repeatNum
                +'</textarea><div class="rel"><div class="tool tooledit" style="position: initial;"><button href="#" class="button button4">'
                +'<i class="icon-dele"></i>删除</button></div></div></div></div></li>';
            html += li;
        });


        html += '</ul>';
        var endTxt = "续";
        if(data.length<5){
            endTxt = "完";
        }
        html += '<ul  style="padding-top: 2px;"><li><div class="float_l"><span class="roundEnd">'+endTxt+'</span></div></li></ul>';
        $("#eventProfile").html(html);

        //展开收缩的js事件
        $("#trend > li:gt(2)").hide();
        var num=1;
        $(".more").on("click",function(){
            if(num){
                num--;
                $("#trend > li:gt(2)").show();
                $(this).html("<span class='packUp'><a href='javascript:void(0)'><label>收拢事件进展&nbsp;<i></i></label></a></span>");
                $(".roundEnd").text("完");
            }else{
                num++;
                $("#trend > li:gt(2)").hide();
                $(this).html("<span class='packUp2'><a href='javascript:void(0)'><label>展开事件进展&nbsp;<i></i></label></a></span>");
                $(".roundEnd").text("续");
            }
        });

    }else{
        $("#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:80px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
    }
}




//-------------------------------------热点关键词云--------------------------------------------

function relatedWordCallback(data){
    var c3 = document.getElementById("container9");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
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
                            color : '#e57a24'
                        }
                    }
                }
                nodes.push(rootNode);
            }else{
                total += data[i].num;
                var node = {
                    name : data[i].name,
                    value : data[i].num,
                    // Custom properties
                    id : i,
                    depth : 1,
                    category : 0
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
                        gravity: 1.1,
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




//------------------------------------------- 词云 ----------------------------------------------------------
function cloudChart(data,dom){
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
                    data: data
                }]
            };
            chart2.setOption(option);
            var enConfig = require('echarts/config');
        }
    );
}


function cloudCallback(data){
    var c3 = document.getElementById("container7");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var _chartColumn10 = cloudChart(eval(data[0]),"container7");
    }
}




//----------------------------------数据类型-------------------------------------------------------------------
function dataTypeCallback(data){
    if(data!=null&&data!=""){
        BarCallBack(data[0],'container6_1',1);	//粉丝分布（柱状图）
        GaugeCallBack(data[1],'container6_2');	//敏感分布（仪表盘）
        CircleCallBack(data[2],'container6_3',0);	//根微博统计（饼状图）
        CircleCallBack(data[3],'container6_4',0);	//直接转发统计（饼状图）
        BarCallBack(data[4],'container6_5',2);	//地域分布（柱状图）
        CircleCallBack(data[5],'container6_6',2);	//发布设备（饼状图）
    }
}





//柱状图
function  BarCallBack(data,id,type){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            if(type==2){	//type为2则是地域分布  悬浮一个5行的数据
                var length = data[0].data.length;
                if(length>5){
                    length = 5;
                }
                var tbodyHtml = "";
                for(var i=0;i<length;i++){
                    var trHtml = "<tr><td>"+(i+1)+"</td><td>"+data[0].legend[i]+"</td><td>"+data[0].data[i].value+"</td></tr>";
                    tbodyHtml += trHtml ;
                }
                $("#container6_7").html(tbodyHtml);
            }
            var _chartColumn10 = BarChart(data[0],id);
        }

    }
}

//柱状图 画图
function BarChart(data,id){
    var config = require(
        [
            'echarts',
            'echarts/chart/bar',
        ],
        function (ec) {
            var chart = ec.init(document.getElementById(id));
            var option = {
                tooltip : {
                    trigger: 'axis',
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
                xAxis:[{
                    type : 'category',
                    data : data.datetime,
                    axisLabel : {
                        formatter: function(value){
                            if(value.indexOf('TO')!=-1){
                                value = value.replace('TO','-');
                            }
                            if(value.length>6){
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
                    axisLine: {
                        lineStyle:{
                            color: ['#eee'],
                            width: 0.1,
                            type: 'solid'
                        }
                    },
                    axisTick:{
                        show:true,
                        lineStyle:{
                            color: '#eee',
                            width: 1,
                            type: 'solid'
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
                    }, splitArea : {
                        show: true,
                        areaStyle:{
                            color:['#FFF','#F7F7F7']
                        }
                    },
                    axisLine: {
                        lineStyle:{
                            color: ['#eee'],
                            width: 0.1,
                            type: 'solid'
                        }
                    },
                    splitLine:{lineStyle:{width:0.1}}
                }],

                color:['#87cefa','#ff7f50'],
                calculable : false,
                animation:false,
                series : [{
                    name:'数量',
                    type:'bar',
                    data:data.data
                }]
            }
            chart.setOption(option);
            var enConfig = require('echarts/config');
        }
    );



}


//仪表图
function  GaugeCallBack(data,id){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            var _chartColumn10 = GaugeChart(data[0],id,1);
        }

    }
}


//仪表盘画图
function GaugeChart(data,id,type){
    if(type==1){		//数据类型
        var minObj =  data.label;
        var minName = minObj.name;
        var minValue = parseFloat(minObj.value).toFixed(2);
    }else if(type==2){	//情绪分析
        var minTotal = 0;
        var total = 0;	//总数量
        var minName = '负面';	//敏感名称
        var minValue = 0;
        $.each(data.data,function(i,n){
            if(n.name.indexOf('[* TO -1]')!=-1){
                minTotal = n.value;
            }
            total += n.value;
        });
        minValue = (minTotal*100/total).toFixed(2);


    }

    var config = require(
        [
            'echarts',
            'echarts/chart/gauge',
        ],
        function (ec) {
            var chart = ec.init(document.getElementById(id));
            data =  eval(data);
            var option = {
                animation : false,
                calculable : false,
                tooltip : {
                    trigger: 'axis'
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

                series : [
                    {
                        type:'gauge',
                        startAngle: 180,
                        endAngle: 0,
                        center : ['50%', '90%'],    // 默认全局居中
                        radius : 180,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                width: 90,
                                color: [[0.2, '#72c1be'],[0.8, '#277bc0'],[1, '#d44e24']]
                            }
                        },
                        axisTick: {            // 坐标轴小标记
                            splitNumber: 10,   // 每份split细分多少段
                            length :12,        // 属性length控制线长
                        },
                        axisLabel: {           // 坐标轴文本标签，详见axis.axisLabel
                            formatter: function(v){
                                switch (v+''){
                                    case '10': return '低';
                                    case '50': return '中';
                                    case '90': return '高';
                                    default: return '';
                                }
                            },
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 15,
                                fontWeight: 'bolder'
                            }
                        },
                        pointer: {
                            width:50,
                            length: '90%',
                            color: 'rgba(255, 255, 255, 0.8)'
                        },
                        title : {
                            show : true,
                            offsetCenter: [0, '-60%'],       // x, y，单位px
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                color: '#fff',
                                fontSize: 30
                            }
                        },
                        detail : {
                            show : true,
                            backgroundColor: 'rgba(0,0,0,0)',
                            borderWidth: 0,
                            borderColor: '#ccc',
                            width: 100,
                            height: 40,
                            offsetCenter: [0, -40],       // x, y，单位px
                            formatter:'{value}%',
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontSize : 50
                            }
                        },
                        data:[{value: minValue, name: minName}]
                    }]}
            chart.setOption(option);
            chart.setTheme('macarons');
            var enConfig = require('echarts/config');
        });




}




//---------------------------------------------------------热门信息----------------------------------------------------------------------
function hotNewsCallback(data){
    if (data==null||data==""){
        $("#hotWBNews").html("<br> <div align=\"center\" style=\"padding-top:30px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath+"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        return;
    }else{
        var ulHtml = "<ul class='tab_menu clear'>";   //  标题
        for(var i=0;i<data.length;i++){
            if(i==0){
                ulHtml +="<li class='current'>"+data[i]["name"]+"</li>";
            }else{
                ulHtml +="<li>"+data[i]["name"]+"</li>";
            }
        }
        ulHtml += "</ul>";

        var divHtml = "<div class='tab_box'>";		//内容
        for(var i=0;i<data.length;i++){
            var subDivHtml = "<div class='hide'>";
            if(i==0){
                subDivHtml = "<div>";
            }
            var subList = data[i]["iContentCommonNetList"];
            if(subList==null||subList==""){
                subDivHtml += "<br> <div align=\"center\" style=\"padding-top:30px\"><p style=\"display:inline;font-size: 14px\"><img src='"+staticResourcePath+"/images/shouye/warn.png' style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            }else{
                subDivHtml +="<div class='mwblist mwblist2'><ul>";

                for(var j=0;j<subList.length;j++){
                    var imgUrl =  subList[j]["profileImageUrl"];
                    if(imgUrl==null||imgUrl==''){
                        imgUrl = staticResourcePath+"/images/user/001.jpg";
                    }
                    var liHtml = "<li><div class='tx'><img src='"+imgUrl+"'></div>";
                    liHtml += "<p class='mscrame'><a href='"+subList[j]["webpageUrl"]+"' class='a1'>"+subList[j]["author"]+"</a>";
                    if(subList[j]["verifyType"]==0){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博个人认证 ' class='W_icon icon_approve'></i></a>";	//橙V
                    }else if(subList[j]["verifyType"]>=1&&subList[j]["verifyType"]<=7){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博机构认证 ' class='W_icon icon_approve_co'></i></a>";	//蓝V
                    }else if(subList[j]["verifyType"]==200||subList[j]["verifyType"]==220){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博达人 ' class='W_icon icon_club'></i></a>";	//达人
                    }
                    liHtml += "</p><p class='wbText'>"+subList[j]["content"]+"</p>";
                    liHtml += "<p style='margin-top: 5px;'>";
                    liHtml += "<span class='time'>"+new Date(subList[j]["published"]).format('yyyy-MM-dd hh:mm')+"</span>"
                    liHtml += "<a href='"+subList[j]["webpageUrl"]+"' target='_blank' class='wblink'><i></i>原文链接</a></p>";
                    liHtml += "<p class='msfs' style='margin-left: 10px; text-align: right;'><span><span><font class='f_c1'>阅读</font>&nbsp;"+subList[j]["views"];
                    liHtml += "</span><span><font class='f_c1'>转发</font>&nbsp;"+subList[j]["forwardNumber"];
                    liHtml += "</span><span><font  class='f_c1'>评论</font>&nbsp;"+subList[j]["comments"]+"</span><span><font  class='f_c1'>点赞</font>&nbsp;"+subList[j]["praiseNum"]+"</span></span></p></li>";
                    subDivHtml += liHtml;
                    if(j%2==1){
                        subDivHtml += "</ul><ul>";
                    }
                }
                subDivHtml += "</ul></div>";
            }
            subDivHtml += "</div>";


            divHtml += subDivHtml;
        }

        ulHtml += divHtml;

        $("#hotWBNews").html(ulHtml);



        $('#nav2').onePageNav();
        $('.yqtabs').Tabs({
            event:'click'
        });
    }

}

//--------------------------------------------------------------博主分析-------------------------------------------

function blogAnalysisCallback(data){
    if(data!=null&&data!=""){
        BloggerTop10CallBack(data[0],"c5_top10");	//前十博主
        MapCallBack(data[1],"container5_1");	//地域统计
        CircleCallBack(data[2],'container5_2',1);	//男女比例
        CircleCallBack(data[3],'container5_3',0);	//用户认证
        CircleCallBack(data[4],'container5_4',0);	//境内外统计
        CircleCallBack(data[5],'container5_5',0);	//水军统计
        // QGPieCallBack(data[0],"container2");
        // QGPieCallBack(data[1],"container3");

    }


}

function MapCallBack(data){
    var c5 = document.getElementById("container5_1");
    if (data==null||data==""){
        c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
            c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;

        }
        var html = "";
        /*var length = res[0].data.length>10?10:res[0].data.length;*/
        for(var i=0;i<res[0].data.length;i++){
            html += '<tr><td>&nbsp;'+res[0].data[i].name+'</td><td>&nbsp;'+res[0].data[i].value+'</td></tr>';
        }
        $("#c5_tb").html(html);
        var _chartColumn10 = MapChart(data,"container5_1");
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
            var chart5 = ec.init(document.getElementById(dom));
            data =  eval(data);

            chart5.showLoading( {
                text : "正在努力加载数据中,请耐心等待。。。",
                textStyle : {
                    fontSize : 20
                }
            });
            var option = {
                animation : false,
                calculable : false,
                title : {
                    text : '',
                    subtext : '',
                    x : 'center',
                    y : 'top',
                },
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
                series : [ {
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
                }, {
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
                                    fontSize : 10
                                },
                            },
                            borderColor:'#FFF',
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
                    markPoint: {
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
                        data: [
                            {
                                name: "钓鱼岛",
                                value: '',
                            },
                            {
                                name: "赤尾屿",
                                value: ''
                            }
                        ]
                    },
                }
                ]
            };
            chart5.hideLoading();
            chart5.setOption(option);
            var enConfig = require('echarts/config');
        }
    );
}


//前十的博主

function BloggerTop10CallBack(data){
    if (data==null||data==""){
        $("#c5_top10").html("<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px;position:absolute;left:125px;top:100px;\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].iContentCommonNetList==null || res[0].iContentCommonNetList.length==0){
            $("#c5_top10").html("<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px;position:absolute;left:125px;top:100px;\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
            return;
        }
        var html = "";
        var topList = res[0].iContentCommonNetList;
        var topLength = topList.length;
        if(topList.length>10){
            topLength = 10;
        }
        for(var i=0;i<topLength;i++){
            var fansNumber = topList[i].fansNumber;	//粉丝数
            var weiboNums = topList[i].weiboNums;		//微博数
            var province = topList[i].userLocation;
            if(fansNumber==null||fansNumber==''||fansNumber==undefined){
                fansNumber = 0;
            }
            if(weiboNums==null||weiboNums==''||weiboNums==undefined){
                weiboNums = 0;
            }
            if(province == undefined){
                province = topList[i].province;
            }
            html += '<tr><td><a style="color: #595959" target="_blank" href="'+topList[i].webpageUrl+'">'+topList[i].author+'</a></td><td>'+province+'</td><td>'+fansNumber+'</td><td>'+weiboNums+'</td><td>'+topList[i].forwardNumber+'</td></tr>';
        }
        $("#c5_top10").html(html);
    }
}

//饼图：type：1（实心饼图）  0（空心饼图）2(南丁格尔图）

function CircleCallBack(data,id,type){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            if(type==1){
                var label = data[0].label;
                var html = "";
                for(var i=0;i<label.length;i++){
                    var sexValue = label[i].value;
                    if(sexValue!=null&&sexValue!=''){
                        sexValue = sexValue.toFixed(2);
                    }
                    if(label[i].name=='男性'){
                        html += "<div class='gender male'><i></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>男性</div>";
                    }else if(label[i].name=='女性'){
                        html += "<div class='gender female'><i></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>女性</div>";
                    }
                }
                $("#biliBox_"+id).html(html);
            }else{
                var label = data[0].label;
                var html = "";
                for(var i=0;i<label.length;i++){
                    var labelValue = label[i].value;
                    if(labelValue!=null&&labelValue!=''){
                        labelValue = labelValue.toFixed(2);
                    }
                    var liHtml = "<div class='bili b_"+(i+1)+"'>	<i></i><strong>"+label[i].name+"</strong>  "+labelValue+"%</div>";
                    html+=liHtml;
                }
                $("#biliBox_"+id).html(html);
            }

            if(type==2){
                var _chartColumn10 = SouthCircleChart(data[0],id);
            }else{
                var _chartColumn10 = CircleChart(data[0],id,type);
            }
        }

    }
}
//空心圆（饼图）
function CircleChart(data,dom,type){
    var radius = ['35%', '60%'];
    /*if(type==1){
        radius = '50%';
    }*/
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
                        restore : {show: false},
                        saveAsImage : {
                            show: false,
                            name:data.title,
                            type:'jpeg',
                            lang : ['点击保存']
                        }
                    }
                },
                calculable : false,
                /*  legend:{
                      orient : 'vertical',
                      x : 'left',
                      data:data.legend
                  },*/
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        radius : radius,
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal',
                                        color: '#FFF'
                                    },
                                    formatter: "{b} : {d}%"
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '20',
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


//南丁格尔图
function SouthCircleChart(data,dom){
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
                    //formatter: "{a} <br/>{b} : {c} ({d}%)"
                    formatter: "{a} <br/>{b} : {d}%"
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
                calculable : true,
                /*
                legend:{
                    orient : 'vertical',
                    //x : '150',
                    y : '150',
                    data:data.legend,
                    formatter:function(v){
                        return v.length>30? v.substr(0,30):v;
                    }
                },*/
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
            chart4.setTheme("macarons");
            var enConfig = require('echarts/config');
        }
    );

}




//----------------------------------------------------------意见领袖----------------------------------------------------------
function hotPeopleCallBack(data){
    if(data==null||data==''){
        $("#opinionLeader").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
    }else{
        var data = eval(data);
        var ulHtml = "<ul>";
        for(var i=0;i<data.length;i++){
            var liHtml = "";
            var content = data[i].content;
            if(content!=null&&content!=''&&content.length >70){
                content = content.substring(0,70)+"...";
            }
            var author = data[i].author;
            if(author!=null&&author.length>=14){
                author = author.substring(0,14);
            }
            var liHtml = " <li><div class='tx'><img src='"+data[i].profileImageUrl+"'/></div><p class='mscrame'><a target='_blank' href='"+data[i].webpageUrl+"' class='a1'>"+author+"</a>";
            if(data[i].verifyType==0){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博个人认证 ' class='W_icon icon_approve'></i></a>";	//橙V
            }else if(data[i].verifyType>=1&&data[i].verifyType<=7){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博机构认证 ' class='W_icon icon_approve_co'></i></a>";	//蓝V
            }else if(data[i].verifyType==200||data[i].verifyType==220){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='微博达人 ' class='W_icon icon_club'></i></a>";	//达人
            }
            liHtml +="</p><p class='msfs'>" +
                "<span><font class='f_c1'>微博</font> "+data[i].weiboNums+"</span><span><font class='f_c1'>粉丝</font> "+data[i].fansNumber+"</span>" +
                "<span><font class='f_c1'>关注</font> "+data[i].friendsCount+"</span></p>" +
                "<p class='wbText'>"+content+"</p></li>";
            ulHtml += liHtml;

            if(i%3==2){
                ulHtml +="</ul><ul>";
            }
        }
        ulHtml +="</ul>";
        $("#opinionLeader").html(ulHtml);
    }
}


//----------------------------------------------------------情绪分析----------------------------------------------------------
function emoteAnalysisCallback(data){
    ////console.log(data);
    if(data!=null&&data!=''){
        //QXMapCallBack(data[0],"container7_1");	//情绪地域统计
        //QXGaugeBack(data[1],'container7_2');	//情绪占比图
        forwardCallBack(data[2],data[3],'container7_3');	//转发表情分析
    }
}


//情绪地域分析图
function QXMapCallBack(data){
    var c7 = document.getElementById("container7_1");
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }
        var _chartColumn10 = MapChart(data,"container7_1");
    }
}



//情绪占比图
function QXGaugeBack(data,id){
    //仪表图
    var c7 = document.getElementById(id);
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            var _chartColumn10 = GaugeChart(data[0],id,2);
        }

    }
}

//情绪分析（转发表情分析）
function forwardCallBack(data,plData,id){
    var c7 = document.getElementById(id);
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
            return;
        }else{
            var html = "";
            var maxValue = data[0]['num'];
            var maxPlValue = 0;
            if(plData!=null&&plData!=''){
                plData = eval(plData);
                maxPlValue = plData[0]['num'];
            }
            var size = data.length;
            if(size>12){
                size = 12;
            }
            for(var i=0;i<size;i++){
                var plNum = 0;
                for(var i=0;i<size;i++){
                    var plNum = 0;
                    if(plData!=null&&plData!=''&&plData.length>i){
                        plNum = plData[i]['num'];
                    }
                    html += '	<li style="margin-right:60px;margin-bottom:5px;padding-top:5px;list-style-type:none;">';
                    html += '		<p style="float:left;height:22px;width:22px;margin-right:6px;vertical-align:middle;">'+data[i]['name']+'</p>';
                    html += '		<div style="display:inline-block;width:90%;vertical-align:middle;">';
                    html += '			<p style="width:' + (data[i]['num'] / maxValue * 100) + '%;background-color:#607cbe;background-image:linear-gradient(to right, #fff, #607cbe);background-repeat:repeat-x;border-radius:0 2px 2px 0;box-shadow:1px 1px 2px rgba(0, 0, 0, 0.1);height:10px;position:relative;margin:1px;">';
                    html += '				<span style="color: #a1a1a1; font-family: arial; font-size: 12px; font-weight: 700; line-height: 10px; position: absolute; right: -68px; width: 60px;">';
                    if (data[i]['num'] > 0)
                        html += data[i]['num'];
                    html += '				</span>';
                    html += '			</p>';
                    html += '			<p style="width:' + (plNum / maxValue * 100) + '%;background-color: #e6b053; background-image: linear-gradient(to right, #fff, #e6b053); background-repeat: repeat-x; margin-top: 2px;border-radius: 0 2px 2px 0; box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); height: 10px; position: relative; margin: 1;">';
                    html += '				<span style="color: #a1a1a1; font-family: arial; font-size: 12px; font-weight: 700; line-height: 10px; position: absolute; right: -68px; width: 60px;">';
                    if (plNum > 0)
                        html += plNum;
                    html += '				</span>';
                    html += '			</p>';
                    html += '		</div>';
                    html += '	</li>';
                    //var liHtml = "<p style='width:100%'><p style='height:22px;float:left;'>"+data[i]["name"]+"</p><div style='width:80%;float:left;'><p  style='height:20px;width:"+data[i]["percent"]+"%;background-color:#607cbe;'></p></div>"+data[i]["num"]+"</p>";
                    //html += liHtml;
                }
            }
            $("#"+id).html(html);
        }

    }

}

/****************************************************核心传播人信息*************************************************************/
//  <div class="mwblist mwblist4">
//	<ul>
//		<li>
//			<div class="tx">
//				<img src="../images/eventAnalysis/user_logo.jpg">
//			</div>
//			<p class="mscrame">
//				<a href="#" class="a1">王高飞王高飞王高飞</a> <a href="#"><i
//					title="微博个人认证 " class="W_icon icon_approve"></i></a>
//			</p>
//		</li>
//	</ul>
//</div>
function coreTranInfoCallBack(data1){
    var size = 5;//核心传播人每行显示条数
    if(data1==null||data1==''){
        $("#myTabContent").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
    }else{
        var data = eval(data1);
//    		console.log(data);
//    		console.log("0-----"+data[0][0]['iContentCommonNetList'].length);
//    		console.log("1-----"+data[0][1]['iContentCommonNetList']);
//    		console.log("2-----"+data[0][2]['iContentCommonNetList']);
//    		console.log("3-----"+data[0][3]['iContentCommonNetList']);
        var ulHtmlGov ="<ul>";
        var ulHtmlOrv = "<ul>";
        var ulHtmlNor = "<ul>";
        var ulHtmlBluv = "<ul>";
        var listGLength = data[0][0]['iContentCommonNetList'];
        //政V
        if(listGLength.length==0){
            $("#modelOne").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
            document.getElementById("modelOne").setAttribute("isNull","0");
        }else{
            var itemNum=0;;
            if(listGLength.length%size!=0){
                itemNum = listGLength.length/size +1;
            }else{
                itemNum = listGLength.length/size;
            }
            for(var t=0;t<itemNum;t++){
                var temp = "";
                for(var f = t*size;f<size*(t+1);f++){
                    if(f<listGLength.length){
                        var picUrl = listGLength[f].profileImageUrl;
                        if(picUrl == ""||picUrl == null||picUrl == undefined){
                            picUrl = staticResourcePath + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                            "<div class='tx'>"+
                            "<img src='"+picUrl+"'>"+
                            "</div>"+
                            "<p class='mscrame'>"+
                            "<a href='"+listGLength[f].webpageUrl+"' target='io' class='a1'>"+listGLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='微博个人认证' class='W_icon icon_approve_co'></i></a>"+
                            "</p>"+
                            "<p class='msfs'><span><font class='f_c1'>转发数  </font>"+listGLength[f].forwardNumber+"</span></p>"+
                            "</li>";
                    }
                }
                ulHtmlGov = ulHtmlGov+temp;
            }
            ulHtmlGov = ulHtmlGov+"</ul>";
            $("#tab_modelOne").html(ulHtmlGov);
        }
        //橙V
        var listOLength = data[0][1]['iContentCommonNetList'];

        if(listOLength.length==0){
            $("#modelThree").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
            document.getElementById("modelThree").setAttribute("isNull","0");
        }else{
            var itemNum=0;;
            if(listOLength.length%size!=0){
                itemNum = listOLength.length/size +1;
            }else{
                itemNum = listOLength.length/size;
            }
            for(var t=0;t<itemNum;t++){
                var temp = "";
                for(var f = t*size;f<size*(t+1);f++){
                    if(f<listOLength.length){
                        var picUrl = listOLength[f].profileImageUrl;
                        if(picUrl == ""||picUrl == null||picUrl == undefined){
                            picUrl = staticResourcePath + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                            "<div class='tx'>"+
                            "<img src='"+picUrl+"'>"+
                            "</div>"+
                            "<p class='mscrame'>"+
                            "<a href='"+listOLength[f].webpageUrl+"' target='io' class='a1'>"+listOLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='微博个人认证' class='W_icon icon_approve'></i></a>"+
                            "</p>"+
                            "<p class='msfs'><span><font class='f_c1'>转发数  </font>"+listOLength[f].forwardNumber+"</span></p>"+
                            "</li>";
                    }
                }
                ulHtmlOrv = ulHtmlOrv+temp;
            }
            ulHtmlOrv = ulHtmlOrv+"</ul>";
            $("#tab_modelThree").html(ulHtmlOrv);
        }
        //普
        var listNLength = data[0][2]['iContentCommonNetList'];
        if(listNLength.length==0){
            $("#modelTFour").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
            document.getElementById("modelFour").setAttribute("isNull","0");
        }else{
            var itemNum=0;;
            if(listNLength.length%size!=0){
                itemNum = listNLength.length/size +1;
            }else{
                itemNum = listNLength.length/size;
            }
            for(var t=0;t<itemNum;t++){
                var temp = "";
                for(var f = t*size;f<size*(t+1);f++){
                    if(f<listNLength.length){
                        var picUrl = listNLength[f].profileImageUrl;
                        if(picUrl == ""||picUrl == null||picUrl == undefined){
                            picUrl = staticResourcePath + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                            "<div class='tx'>"+
                            "<img src='"+picUrl+"'>"+
                            "</div>"+
                            "<p class='mscrame'>"+
                            "<a href='"+listNLength[f].webpageUrl+"' target='io' class='a1'>"+listNLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='微博个人认证' class='W_icon icon'></i></a>"+
                            "</p>"+
                            "<p class='msfs'><span><font class='f_c1'>转发数  </font>"+listNLength[f].forwardNumber+"</span></p>"+
                            "</li>";
                    }
                }
                ulHtmlNor = ulHtmlNor+temp;
            }
            ulHtmlNor = ulHtmlNor+"</ul>";
            $("#tab_modelFour").html(ulHtmlNor);
        }
        //蓝V
        var listBLength = data[0][3]['iContentCommonNetList'];
        if(listBLength.length==0){
            $("#modelTwo").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>");
            document.getElementById("modelTwo").setAttribute("isNull","0");
        }else{
            var itemNum=0;;
            if(listBLength.length%size!=0){
                itemNum = listBLength.length/size +1;
            }else{
                itemNum = listBLength.length/size;
            }
            for(var t=0;t<itemNum;t++){
                var temp = "";
                for(var f = t*size;f<size*(t+1);f++){
                    if(f<listBLength.length){
                        var picUrl = listBLength[f].profileImageUrl;
                        if(picUrl == ""||picUrl == null||picUrl == undefined){
                            picUrl = staticResourcePath + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                            "<div class='tx'>"+
                            "<img src='"+picUrl+"'>"+
                            "</div>"+
                            "<p class='mscrame'>"+
                            "<a href='"+listBLength[f].webpageUrl+"' target='io' class='a1'>"+listBLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='微博个人认证' class='W_icon icon_approve_co'></i></a>"+
                            "</p>"+
                            "<p class='msfs'><span><font class='f_c1'>转发数  </font>"+listBLength[f].forwardNumber+"</span></p>"+
                            "</li>";
                    }
                }
                ulHtmlBluv = ulHtmlBluv+temp;
            }
            ulHtmlBluv = ulHtmlBluv+"</ul>";
            $("#tab_modelTwo").html(ulHtmlBluv);
        }
        //设置显示优先级
        if(listGLength==0){
            if(listOLength!=0){
                $("#modelOne").attr("class","tab-pane fade in active hide");
                $("#modelTwo").attr("class","tab-pane fade in active");
                $("#modelThree").attr("class","tab-pane fade in active hide");
                $("#modelFour").attr("class","tab-pane fade in active hide");
                $("#li1").attr("class","");
                $("#li2").attr("class","current");
                $("#li3").attr("class","");
                $("#li4").attr("class","");
            }else if(listNLength!=0){
                $("#modelOne").attr("class","tab-pane fade in active hide");
                $("#modelTwo").attr("class","tab-pane fade in active hide");
                $("#modelThree").attr("class","tab-pane fade in active");
                $("#modelFour").attr("class","tab-pane fade in active hide");
                $("#li1").attr("class","");
                $("#li2").attr("class","");
                $("#li3").attr("class","current");
                $("#li4").attr("class","");
            }else if(listBLength!=0){
                $("#modelOne").attr("class","tab-pane fade in active hide");
                $("#modelTwo").attr("class","tab-pane fade in active hide");
                $("#modelThree").attr("class","tab-pane fade in active hide");
                $("#modelFour").attr("class","tab-pane fade in active");
                $("#li1").attr("class","");
                $("#li2").attr("class","");
                $("#li3").attr("class","");
                $("#li4").attr("class","current");
            }
        }
    }
}
         
  
  
      