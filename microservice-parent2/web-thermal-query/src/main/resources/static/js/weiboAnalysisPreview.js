
//-----------------------------------------------΢���¼����-------------------------------------------------------
function profileCallback(data){
    if (data==null||data==""){
        $("#introduce").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
        return false;
    }else{
        if(data[2]!=null&&data[2]!=''&&data[2].length>0){
            $("#introduce").html(data[2]);
        }else{
            $("#introduce").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
        }

        /*  if(data[0]==null){
         $("#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
         }else{
         var html = "<h2>"+data[1]+"</h2>";
         html += " <div class='more'><span class='packUp2'><a href='javascript:void(0)'><label>չ���¼���չ&nbsp;<i></i></label></a></span></div>";
         html +='<ul style="padding-bottom: 0;" id="trend">';
         $.each(data[0],function(){
         var published = new Date(this.published);
         var li = '<li><div class="float_l"><span class="line"></span><span class="round"></span><span class="line"></span></div>'
         + '<div class="sz"></div><div class="text text2"><div class="textShow">['+ (published.getMonth()+1)+'��'+published.getDate()+'�� '+published.getHours()
         +'��]'+this.title+'['+this.captureWebsiteName+'] ��ͬ��������'+this.repeatNum
         +'</div><div class="editBox"><textarea class="inputList" id="textarea3">['+ (published.getMonth()+1)+'��'+published.getDate()+'�� '+published.getHours()
         +'��]'+this.title+'[ '+this.captureWebsiteName+' ] ��ͬ��������'+this.repeatNum
         +'</textarea><div class="rel"><div class="tool tooledit" style="position: initial;"><button href="#" class="button button4">'
         +'<i class="icon-dele"></i>ɾ��</button></div></div></div></div></li>';
         html += li;
         });
         html += '</ul>';
         html += '<ul  style="padding-top: 2px;"><li><div class="float_l"><span class="roundEnd">��</span></div></li></ul>';
         $("#eventProfile").html(html);
         }*/

    }
}




//-----------------------------------------------΢������ͳ��------------------------------------------------------
function LineCallBack(data){
    var c1 = document.getElementById("container1");
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineChart(data[0],"container1");
            //$("#container1 + .text").text(data1);
            $("#declare").text(data1);
        }
    }
}

function LineCallBack1(data){
    var c1 = document.getElementById("container1");
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineChart(data[0],"container1");
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
                    show : true,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
                        show:true/*,
                         areaStyle:{
                         color:['#FFF','#F7F7F7']
                         }*/
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
//------------------------------------------΢���������ͼ---------------------------------------
function LineAreaCallBack(data,el){
    var c1 = document.getElementById(el);
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            var _chartColumn10 = LineAreaChart(data[0],el);
        }
    }
}

function declare(data,el){
    var c1 = document.getElementById(el);
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        var data1 = data[1];
        data = eval(data[0]);
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            stat1.innerHTML = '';
            return;
        }else{
            $("#declare").text(data1);
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
                        show:true/*,
                         areaStyle:{
                         color:['#FFF','#F7F7F7']
                         }*/
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
//--------------------------------------- ����;�� --------------------------------------------

function pathCallback(data){
    if(data!=null&&data!=''&&data.length>0){
        var stattime = new Date();
        if(data[0]!=null&&eval(data[0])!=null&&eval(data[0]).published!=null){
            var formatStart = eval(data[0]).published;
            stattime = new Date(formatStart);
        }

        //var html = "<h2><div><span class='contenteditable' contenteditable='false'>"+(stattime.getMonth()+1)+'��'+stattime.getDate()+'�� '+stattime.getHours()+"��</span></div></h2>";
        var html = "<h2><div><span class='contenteditable' contenteditable='false'>"+(stattime.getMonth()+1)+'��'+stattime.getDate()+'�� '+"</span></div></h2>";

        if(data.length>4){
            html += " <div class='more'><span class='packUp2'><a href='javascript:void(0)'><label>չ���¼���չ&nbsp;<i></i></label></a></span></div>";
        }
        html +='<ul style="padding-bottom: 0;" id="trend">';
        $.each(data,function(n){
            var published = new Date(this.published);
            var li = '<li><div class="float_l"><span class="line"></span><span class="round"></span><span class="line"></span></div>'
                + '<div class="sz"></div><div class="text text2"><i class="icon-arrows yb-move" style="display: none;"></i><i class="icon-trash-o yb-delete" style="display: none;" onclick="del(this);"></i><i class="icon-control_point yb-add" style="display: none;" onclick="add();"></i><div class="contenteditable" contenteditable="false"><a target="_blank" style="color: #000;text-decoration:none;" href="'+this.webpageUrl+'">['+ (published.getMonth()+1)+'��'+published.getDate()+'�� '+published.getHours()
                +'��]   ��'+this.author+'��';
            
            if(this.content.length>=140){
            	li+=this.content.substring(0,140)+"...";
//					html+='<a style="cursor: pointer;" onclick=hotMessageClick("'+dd[i].url+'") title="'+dd[i].title+'">'+addFontByTitle(dd[i].title.substring(0,140),$("#title").val())+"..."+'</font></a>';
			}else{
				li+=this.content;
//					html+='<a style="cursor: pointer;" onclick=hotMessageClick("'+dd[i].url+'") title="'+dd[i].title+'">'+addFontByTitle(dd[i].title,$("#title").val())+'</a>';
			} 
                
                
            //+'['+this.captureWebsiteName+'] ��ͬ��������'+this.repeatNum
            if(n==0){
                //li += ' <span style="position: absolute;bottom: 0;right: 0;color:#0185d8">(�������¼�Դͷ�������ο�)</span>';
                li += ' <p class="f_c1">(�������¼�Դͷ�������ο�)</p>';
            }
            //li +='</a></div><div class="editBox"><textarea class="inputList" id="textarea3">['+ (published.getMonth()+1)+'��'+published.getDate()+'�� '+published.getHours()
            //+'��]   ��'+this.author+'��'+this.content
//                      +'��]'+this.title+'[ '+this.captureWebsiteName+' ] ��ͬ��������'+this.repeatNum
            //+'</textarea><div class="rel"><div class="tool tooledit" style="position: initial;"><button href="#" class="button button4">'
            //+'<i class="icon-dele"></i>ɾ��</button></div></div></div></div></li>';
            li += "<p style='font-size: 12px;' class='msfs'><span class='float_l'><span><font class='f_c1'>�Ķ�</font>&nbsp;"+this.views+"<span style='margin-left: 20px;color: #b7b7b7;'>|</span>";
            li += "</span><span style='margin-left: 20px;'><font class='f_c1'>ת��</font>&nbsp;"+this.forwardNumber+"<span style='margin-left: 20px;color: #b7b7b7;'>|</span>";
            li += "</span><span style='margin-left: 20px;'><font  class='f_c1'>����</font>&nbsp;"+this.comments+"<span style='margin-left: 20px;color: #b7b7b7;'>|</span>"+"</span><span style='margin-left: 20px;'><font  class='f_c1'>����</font>&nbsp;"+this.praiseNum+"</span></span></p>";
            li +='</a></div></div></li>';
            
            html += li;
        });
        html += '</ul>';
        var endTxt = "��";
        if(data.length<5){
            endTxt = "��";
        }
        html += '<ul  style="padding-top: 10px;"><li><div class="float_l"><span class="roundEnd">'+endTxt+'</span></div></li></ul>';
        $("#eventProfile").html(html);

        //չ��������js�¼�
        $("#trend > li:gt(2)").hide();
        var num=1;
        $(".more").on("click",function(){
            if(num){
                num--;
                $("#trend > li:gt(2)").show();
                $(this).html("<span class='packUp'><a href='javascript:void(0)'><label>��£�¼���չ&nbsp;<i></i></label></a></span>");
                $(".roundEnd").text("��");
            }else{
                num++;
                $("#trend > li:gt(2)").hide();
                $(this).html("<span class='packUp2'><a href='javascript:void(0)'><label>չ���¼���չ&nbsp;<i></i></label></a></span>");
                $(".roundEnd").text("��");
            }
        });

    }else{
        $("#eventProfile").html("<br> <div align=\"center\" style=\"padding-top:80px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
    }

    $( "#trend" ).sortable({
        connectWith: "#trend",
        handle: ".yb-move",
        placeholder: "portlet-placeholder ui-corner-all"
    });
}




//-------------------------------------�ȵ�ؼ�����--------------------------------------------

function relatedWordCallback(data){
    var c3 = document.getElementById("container9");
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
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
                tooltip : {
                    trigger: 'item',
                    formatter: '{b}'
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
                 data:['Ҷ�ӽڵ�','��Ҷ�ӽڵ�', '���ڵ�']
                 },*/
                series : [
                    {
                        type:'force',
                        name : "Force tree",
                        ribbonType: false,
                        categories : [
                            {
                                name: 'Ҷ�ӽڵ�'
                            },
                            {
                                name: '��Ҷ�ӽڵ�'
                            },
                            {
                                name: '���ڵ�'
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




//-------------------------------------------�ȵ�� ���� ----------------------------------------------------------
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var _chartColumn10 = cloudChart(eval(data[0]),"container7");
    }
}

//-------------------�ȵ��  ������ͼ-------------------------------
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
                 text: '�ֻ�ռ����',
                 subtext: '�鹹����'
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
                        restore : {show: true},
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
                        name:'����ͼ',
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
function treeMapCallback(data,el){

    var c3 = document.getElementById(el);
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var _chartColumn10 = treeMapChart(eval(data[0]),el);
    }
}
//-----------------------�ȵ�� ƽ�д���------------------------------

function cloudParallelCallback(data,el){
    var c3 = document.getElementById(el);
    if (data==null||data==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var _chartColumn10 = cloudParallelChart(eval(data[0]),el);
    }
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
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
//----------------------------------��������-------------------------------------------------------------------
function dataTypeCallback(data){
    if(data!=null&&data!=""){
        BarCallBack(data[0],'container6_1',1);	//��˿�ֲ�����״ͼ��
        GaugeCallBack(data[1],'container6_2');	//���зֲ����Ǳ��̣�
        CircleCallBack(data[2],'container6_3',0);	//��΢��ͳ�ƣ���״ͼ��
        CircleCallBack(data[3],'container6_4',0);	//ֱ��ת��ͳ�ƣ���״ͼ��
        BarCallBack(data[4],'container6_5',2);	//����ֲ�����״ͼ��
        CircleCallBack(data[5],'container6_6',2);	//�����豸����״ͼ��
    }
}

//��״ͼ
function  BarCallBack(data,id,type){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            if(type==2){	//typeΪ2���ǵ���ֲ�  ����һ��5�е�����
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

//��״ͼ ��ͼ
function BarChart(data,id){
    var config = require(
        [
            'echarts',
            'echarts/chart/bar',
        ],
        function (ec) {
            var chart = ec.init(document.getElementById(id));
            var option = {
                animation : false,
                tooltip : {
                    trigger: 'axis',
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['�������']
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
                            /*if(value.length>6){
                             value = value.substring(0,6);
                             }*/
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
                    y:30,
                    y2:70,
                    x:50,
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
                        show: true/*,
                         areaStyle:{
                         color:['#FFF','#F7F7F7']
                         }*/
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
                series : [{
                    name:'����',
                    type:'bar',
                    data:data.data
                }]
            }
            chart.setOption(option);
            var enConfig = require('echarts/config');
        }
    );



}

//�Ǳ�ͼ
function  GaugeCallBack(data,id,type){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else if(id=="container6_2"&&type==12){
            var _chartColumn10 = GaugeChart1(data[0],id);
        }else{
            var _chartColumn10 = GaugeChart(data[0],id,1);
        }

    }
}


function GaugeChart1(data,id){
    var minObj =  data.label;
    var minName = minObj.name;
    var minValue = parseFloat(minObj.value).toFixed(2);
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
                tooltip : {
                    trigger: 'axis'
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                calculable : false,
                series : [
                    {
                        name:'������Դ',
                        type:'gauge',
                        radius : ['50%', '70%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
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
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:[{value: minValue, name: minName}]
                    }
                ]
            };
            chart.setOption(option);
            chart.setTheme('macarons');
            var enConfig = require('echarts/config');
        });
}

//�Ǳ��̻�ͼ
function GaugeChart(data,id,type){
    if(type==1){		//��������
        var minObj =  data.label;
        var minName = minObj.name;
        var minValue = parseFloat(minObj.value).toFixed(2);
    }else if(type==2){	//��������
        var minTotal = 0;
        var total = 0;	//������
        var minName = '����';	//��������
        var minValue = 0;
        $.each(data.data,function(i,n){
            if(n.name.indexOf('�������')!=-1){
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
                tooltip : {
                    trigger: 'axis'
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
                        center : ['50%', '90%'],    // Ĭ��ȫ�־���
                        radius : 150,
                        axisLine: {            // ��������
                            lineStyle: {       // ����lineStyle����������ʽ
                                width: 90,
                                color: [[0.2, '#72c1be'],[0.8, '#277bc0'],[1, '#d44e24']]
                            }
                        },
                        axisTick: {            // ������С���
                            splitNumber: 10,   // ÿ��splitϸ�ֶ��ٶ�
                            length :12,        // ����length�����߳�
                        },
                        axisLabel: {           // �������ı���ǩ�����axis.axisLabel
                            formatter: function(v){
                                switch (v+''){
                                    case '10': return '��';
                                    case '50': return '��';
                                    case '90': return '��';
                                    default: return '';
                                }
                            },
                            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
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
                            offsetCenter: [0, '-60%'],       // x, y����λpx
                            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
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
                            offsetCenter: [0, -40],       // x, y����λpx
                            formatter:'{value}%',
                            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
                                fontSize : 25
                            }
                        },
                        data:[{value: minValue, name: minName}]
                    }]}
            chart.setOption(option);
            chart.setTheme('macarons');
            var enConfig = require('echarts/config');
        });




}


//--------------------�������ͱ༭ͼ��--------------------------

function dataTypeCallbacks(data,id,type){
    if(data!=null&&data!=""){
        if(id=='container6_1'){
            BarCallBack1(data[0],id,type,1);	//��˿�ֲ�����״ͼ��
        }
        if(id=='container6_2'){
            GaugeCallBack(data[1],id,type);	//���зֲ����Ǳ��̣�
        }
        if(id=='container6_3'){
            CircleCallBack1(data[2],id,type,0);	//��΢��ͳ�ƣ���״ͼ��
        }
        if(id=='container6_4'){
            CircleCallBack1(data[3],id,type,0);	//ֱ��ת��ͳ�ƣ���״ͼ��
        }
        if(id=='container6_5'){
            BarCallBack1(data[4],id,type,2);	//����ֲ�����״ͼ��
        }
        if(id=='container6_6'){
            CircleCallBack1(data[5],id,type,2);	//�����豸����״ͼ��
        }
    }
}

function CircleCallBack1(data,id,type,charType){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else {
            if (charType == 1) {
                var label = data[0].label;
                var html = "";
                for (var i = 0; i < label.length; i++) {
                    var sexValue = label[i].value;
                    if (sexValue != null && sexValue != '') {
                        sexValue = sexValue.toFixed(2);
                    } else {
                        sexValue = 0;
                    }
                    if(label[i].name=='����'){
            			html += "<div class='gender2 male'><i class='icon-gender-male'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>����</div>";
            		}else if(label[i].name=='Ů��'){
            			html += "<div class='gender2 female'><i class='icon-gender-female'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>Ů��</div>";
            		}else{
            			html += "<div class='gender2 other'><i class='icon-gender-other'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>����</div>";
            		}
                }
                $("#biliBox_" + id).html(html);
            } else {
                var label = data[0].label;
                var html = "";
                for (var i = 0; i < label.length; i++) {
                    var labelValue = label[i].value;
                    if (labelValue != null && labelValue != '' && labelValue != 'null') {
                        labelValue = labelValue.toFixed(2);
                    } else {
                        labelValue = 0;
                    }
                    var liHtml = "<div class='bili b_" + (i + 1) + "'>	<i></i><strong>" + label[i].name + "</strong>  " + labelValue + "%</div>";
                    html += liHtml;
                }
                $("#biliBox_" + id).html(html);
            }
            if(id=='container6_6'&&type==14){
                var _chartColumn10 = CircleChart(data[0], id,1);
            } else if (type==6||type == 14) {//����ͼ
                var _chartColumn10 = CircleChart(data[0], id, 0);
            } else if(id=='container6_6'&&type==10){
                var _chartColumn10 = WTPieChart(data[0], id,1);
            }else if (type==7||type == 10) {//��ͼ
                var _chartColumn10 = WTPieChart(data[0], id);
            }else if(id=='container6_6'&&type==11){
                var _chartColumn10 = SouthCircleChart(data[0],id);
            }else{//�϶����õ��
                var _chartColumn10 = SouthCircleChart1(data[0],id);
            }
        }

    }
}
//��״ͼ
function  BarCallBack1(data,id,type,charType){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            if(charType==2){	//typeΪ2���ǵ���ֲ�  ����һ��5�е�����
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
            if(type==9){//��״ͼ
                var _chartColumn10 = BarChart(data[0],id);
            }else if(id=='container6_1'&&type==10){
                var _chartColumn10 = WTPieChart(data[0],id,1);
            }else if(type==10){//��ͼ
                var _chartColumn10 = WTPieChart(data[0],id,3);

            }else if(id=='container6_1'&&type==11){
                var _chartColumn10 = SouthCircleChart(data[0],id,1);
            }else if(type==11){//�϶����õ��
                var _chartColumn10 = SouthCircleChart(data[0],id,3);

            }
        }
    }
}

//��״ͼ ��ͼ
function BarChart(data,id){
    var config = require(
        [
            'echarts',
            'echarts/chart/bar',
        ],
        function (ec) {
            var chart = ec.init(document.getElementById(id));
            var option = {
                animation : false,
                tooltip : {
                    trigger: 'axis',
                },
                toolbox: {
                    show : true,
                    orient:'vertical',//vertical|horizontal
                    y:30,
                    x:985,
                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['�������']
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
                            /*if(value.length>6){
                             value = value.substring(0,6);
                             }*/
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
                	y:30,
                    y2:70,
                    x:50,
                    x2:40
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
                        show: true/*,
                         areaStyle:{
                         color:['#FFF','#F7F7F7']
                         }*/
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
                series : [{
                    name:'����',
                    type:'bar',
                    data:data.data
                }]
            }
            chart.setOption(option);
            var enConfig = require('echarts/config');
        }
    );
}


//---------------------------------------------------------������Ϣ----------------------------------------------------------------------
function hotNewsCallback(data){
    if (data==null||data==""){
        $("#hotWBNews").html("<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxImgSrc+"/images/shouye/warn.png' style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
        return;
    }else{
        var ulHtml = "<ul class='tab_menu clear' id='menuHot'>";   //  ����
        for(var i=0;i<data.length;i++){
            if(i==0){
                ulHtml +="<li class='current'>"+data[i]["name"]+"</li>";
            }else{
                ulHtml +="<li>"+data[i]["name"]+"</li>";
            }
        }
        ulHtml += "</ul>";

        var divHtml = "<div class='tab_box'>";		//����
        for(var i=0;i<data.length;i++){
            var subDivHtml = "<div class='hide'>";
            if(i==0){
                subDivHtml = "<div>";
            }
            var subList = data[i]["iContentCommonNetList"];
            if(subList==null||subList==""){
                subDivHtml += "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxImgSrc+"/images/shouye/warn.png' style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            }else{
                subDivHtml +="<div class='mwblist mwblist2' id='hotList'><ul>";

                for(var j=0;j<subList.length;j++){
                    var imgUrl =  subList[j]["profileImageUrl"];
                    if(imgUrl==null||imgUrl==''){
                        imgUrl = njxImgSrc+"/images/user/001.jpg";
                    }
                    var liHtml = "<li class='hotIn'><ul class='control'><li><b class='icon-trash-o deleLi' onclick='delHot(this);' title='ɾ��'></b></li> </ul><div class='tx'><img src='"+imgUrl+"'></div>";
                    liHtml += "<p class='mscrame'><a target='_blank' href='"+subList[j]["webpageUrl"]+"' class='a1'>"+subList[j]["author"]+"</a>";
                    if(subList[j]["verifyType"]==0){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢��������֤ ' class='W_icon icon_approve'></i></a>";	//��V
                    }else if(subList[j]["verifyType"]>=1&&subList[j]["verifyType"]<=7){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢��������֤ ' class='W_icon icon_approve_co'></i></a>";	//��V
                    }else if(subList[j]["verifyType"]==200||subList[j]["verifyType"]==220){
                        liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢������ ' class='W_icon icon_club'></i></a>";	//����
                    }
                    liHtml += "</p><p class='wbText'>"+subList[j]["content"]+"</p>";
                    liHtml += "<p style='margin-top: 5px;'>";
                    liHtml += "<span class='time'>"+new Date(subList[j]["published"]).format('yyyy-MM-dd hh:mm')+"</span>"
                    liHtml += "<a href='"+subList[j]["webpageUrl"]+"' target='_blank' class='wblink'><i></i>ԭ������</a></p>";
                    liHtml += "<p class='msfs'><span class='float_l'><span><font class='f_c1'>�Ķ�</font>&nbsp;"+subList[j]["views"];
                    liHtml += "</span><span><font class='f_c1'>ת��</font>&nbsp;"+subList[j]["forwardNumber"];
                    liHtml += "</span><span><font  class='f_c1'>����</font>&nbsp;"+subList[j]["comments"]+"</span><span><font  class='f_c1'>����</font>&nbsp;"+subList[j]["praiseNum"]+"</span></span></p></li>";
                    subDivHtml += liHtml;
                    /*if(j%2==1){
                     subDivHtml += "</ul><ul>";
                     }*/
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
    if(phone){
        $("ul.control").hide();
    }

}

//--------------------------------------------------------------��������-------------------------------------------

function blogAnalysisCallback(data){
    if(data!=null&&data!=""){
        BloggerTop10CallBack(data[0],"c5_top10");	//ǰʮ����
        MapCallBack(data[1],"container5_1");	//����ͳ��
        CircleCallBack(data[2],'container5_2',1);	//��Ů����
        CircleCallBack(data[3],'container5_3',0);	//�û���֤
        CircleCallBack(data[4],'container5_4',0);	//������ͳ��
        CircleCallBack(data[5],'container5_5',0);	//ˮ��ͳ��
        // QGPieCallBack(data[0],"container2");
        // QGPieCallBack(data[1],"container3");

    }
}

function MapCallBack(data){
    var c5 = document.getElementById("container5_1");
    if (data==null||data==""){
        c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
            c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
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
                text : "����Ŭ������������,�����ĵȴ�������",
                textStyle : {
                    fontSize : 20
                }
            });
            var option = {
                animation : false,

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
                    show : true,
                    orient:'vertical',
                    y:30,
                    x:'right',

                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['������ͼ', '�ر�', 'ˢ��']
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
                    text:['��','��'],
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
				color: "#3a3a3a",//��ͼ������ɫ
				fontSize: 8
			}
         }
         
            },
            itemStyle: {
                normal: {
                    borderColor: '#fff',
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
                    name : '����',
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
                        "����":[116.46,39.92],
                        "����":[123.14,41.66],
                        "����":[116.98,32.62],
                        "ɽ��":[117.52,36.23],
                        "�Ϻ�":[121.42,31.12],
                        "����":[119.72,33.66],
                        "����":[111.99,31.54],
                        "�㽭":[119.87,29.24],
                        "�㶫":[112.73,24.02],
                        "����":[117.73,26.17],
                        "����":[111.33,27.75],
                        "����":[113.57,22.51],
                        "����":[113.57,33.86],
                        "����":[109.53,19.15],
                        "�½�":[85.90,40.98],
                        "���ɹ�":[113.87,43.49],
                        "����":[87.01,32.53],
                        "�ຣ":[95.03,36.19],
                        "����":[101.25,24.48],
                        "�Ĵ�":[102.20,30.77],
                        "����":[106.62,26.94],
                        "����":[96.46,40.19],
                        "����":[108.31,23.60],
                        "����":[115.52,27.60],
                        "������":[127.37,47.73],
                        "����":[126.49,43.49],
                        "ɽ��":[111.99,37.20],
                        "�ӱ�":[115.01,38.31],
                        "����":[108.75,34.09],
                        "����":[106.56,29.64],
                        "����":[105.98,37.25],
                        "���":[117.14,39.29],
                        "̨��":[120.90,23.81]
                    },
                    markPoint : {
                        symbol:'circle',
                        symbolSize:2,
                        large:true,
                        effect : {
                            show: true,
                            type: 'scale',
                            loop: true,
                            period: 5,
                            scaleSize : 1,
                            bounceDistance: 10,
                            color : '#FF0000',
                            shadowColor : '#FF0000',
                            shadowBlur : 0
                        },
                        data:data
                    }
                }
                ]
            };
            chart5.hideLoading();
            chart5.setOption(option);
            var enConfig = require('echarts/config');
        }
    );
}


//ǰʮ�Ĳ���

function BloggerTop10CallBack(data){
    if (data==null||data==""){
        $("#c5_top10").html("<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].iContentCommonNetList==null || res[0].iContentCommonNetList.length==0){
            $("#c5_top10").html("<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
            return;
        }
        var html = "";
        var topList = res[0].iContentCommonNetList;
        var topLength = topList.length;
        if(topList.length>10){
            topLength = 10;
        }
        for(var i=0;i<topLength;i++){
            var fansNumber = topList[i].fansNumber;	//��˿��
            var weiboNums = topList[i].weiboNums;		//΢����
            if(fansNumber==null||fansNumber==''||fansNumber==undefined){
                fansNumber = 0;
            }
            if(weiboNums==null||weiboNums==''||weiboNums==undefined){
                weiboNums = 0;
            }
            var province = topList[i].province;
            if(province == null || province == "")	//����������
            	province = topList[i].userLocation;
            html += '<tr><td><a style="color: #595959" target="_blank" href="'+topList[i].webpageUrl+'">'+topList[i].author+'</a></td><td>'+province+'</td><td>'+fansNumber+'</td><td>'+weiboNums+'</td><td>'+topList[i].forwardNumber+'</td></tr>';
        }
        $("#c5_top10").html(html);
    }
}

//��ͼ��type��1��ʵ�ı�ͼ��  0�����ı�ͼ��2(�϶����ͼ��

function CircleCallBack(data,id,type){
    var c4 = document.getElementById(id);
    if (data==null||data==""){
        c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            if(type==1){
                var label = data[0].label;
                var html = "";
                for(var i=0;i<label.length;i++){
                    var sexValue = label[i].value;
                    if(sexValue!=null&&sexValue!=''){
                        sexValue = sexValue.toFixed(2);
                    }else{
                        sexValue = 0;
                    }
                    if(label[i].name=='����'){
            			html += "<div class='gender2 male'><i class='icon-gender-male'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>����</div>";
            		}else if(label[i].name=='Ů��'){
            			html += "<div class='gender2 female'><i class='icon-gender-female'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>Ů��</div>";
            		}else if(label[i].name=='����'){
            			html += "<div class='gender2 other'><i class='icon-gender-other'></i><strong id='comments_male-fans-scale'>"+sexValue+"%</strong>����</div>";
            		}
                }
                $("#biliBox_"+id).html(html);
            }else{
                var label = data[0].label;
                var html = "";
                for(var i=0;i<label.length;i++){
                    var labelValue = label[i].value;
                    if(labelValue!=null&&labelValue!=''&&labelValue!='null'){
                        labelValue = labelValue.toFixed(2);
                    }else{
                        labelValue=0;
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
//����Բ����ͼ��
function CircleChart(data,dom,type){
    var radius = ['35%', '60%'];
    var flag=false;
    var flagLen=false;
    var bl=true;
    var center=50;
    if(type==1){
        flag=true;
    }else if(type==2){
        flagLen=true;
        flag=true;
        bl=false;
        center=25;
    }
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['�������']
                        }
                    }
                },
                calculable : false,
                legend:{
                    show:flagLen,
                    orient : 'vertical',
                    //x : 'left',
                    x : '60%',
                    y : 'center',
                    data:data.legend
                },
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        radius : radius,
                        center:[center+'%','50%'],
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : flag,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                        //color: '#FFF'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : flag
                                }
                            },
                            emphasis : {
                                label : {
                                    show : bl,
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


//�϶����ͼ
function SouthCircleChart(data,dom,type){
    var point = "35%";
    var flagLen=false;
    var flag=true;
    var c="{a} <br/>{b} : {d}%";
    if(phone||type==1){
        point = "50%";
    }
    if(type==1){
        c="{a} <br/>{c} ({d}%)";
    }else if(type==3){
        flagLen=true;
        flag=false;
        var color=[];
        color=['#277BC0','#F3D647','#B9C5E2','#FA6200','#B5C334','#CE4D7A','#91261A','#C8BC85','#F4E38B','#869E79','#F97D23','#84fde8','#426bf2','#fca77f','#25dbe6','#0949aa','#80a9ee','#F29300','#D44E24','#EC6A3A','#A05623','#9E9D9E','#CBCEDF','#9A5EA4','#FAB4C2','#0307C5','#D48454','#FF806A','#6C3012','#CD6C3D','#FB6900','#B46522','#FCCA59','#DDE1E4','#008083','#F46E3D'];
        var i=0;
        $.each(data.data,function(){
            this.itemStyle={'normal':{'color':color[i]}};
            i++;
        });
    }
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
                    formatter: c
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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['�������']
                        }
                    }
                },
                calculable : false,
                legend:{
                    show:flagLen,
                    orient : 'vertical',
                    x : '70%',
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
                        center:[point,'50%'],
                        radius : ['20%','60%'],
                        roseType : 'radius',
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : flag,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : flag
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


//-------------------------���������༭ͼ----------------------

function blogAnalysisCallbacks(data,id,type){
    if(data!=null&&data!=""){
        if(id=='c5_top10'){
            BloggerTop10CallBack(data[0],"c5_top10");	//ǰʮ����
        }
        if(id=='container5_1'){
            MapCallBack(data[1],"container5_1");	//����ͳ��
        }
        if(id=='container5_2') {
            CircleCallBack1(data[2], id,type,1);	//��Ů����
        }
        if(id=='container5_3'){
            CircleCallBack1(data[3],id,type,0);	//�û���֤
        }
        if(id=='container5_4'){
            CircleCallBack1(data[4],id,type,0);	//������ͳ��
        }
        if(id=='container5_5'){
            CircleCallBack1(data[5],id,type,0);	//ˮ��ͳ��
        }
    }
}

function SouthCircleChart1(data,dom){

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
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title,
                            type:'jpeg',
                            lang : ['�������']
                        }
                    }
                },
                calculable : false,
                legend:{
                    show:false,
                    orient : 'vertical',
                    x : '60%',
                    y : 'center',
                    data:data.legend.reverse(),
                    formatter:function(v){
                        return v.length>30? v.substr(0,30):v;
                    }
                },
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        //center:['25%','50%'],
                        radius : ['35%','60%'],
                        roseType : 'radius',
                        startAngle:0,
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : false
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

function WTPieChart(data,dom,type){
    var flag=false;
    var c="{a} <br/>{b} : {c} ({d}%)";
    var flagLen=false;
    var center=50;
    if(type==1){
        flag=true;
        c="{a} <br/>{c} ({d}%)";
    }else if(type==2){
        flag=true;
        flagLen=true;
        center=25;
    }else if(type==3){
        flagLen=true;
        var color=[];
        color=['#84fde8','#426bf2','#fca77f','#25dbe6','#0949aa','#80a9ee','#F29300','#D44E24','#EC6A3A','#A05623','#9E9D9E','#277BC0','#F3D647','#B9C5E2','#FA6200','#B5C334','#CE4D7A','#CBCEDF','#9A5EA4','#FAB4C2','#0307C5','#D48454','#FF806A','#6C3012','#CD6C3D','#FB6900','#B46522','#FCCA59','#DDE1E4','#008083','#F46E3D','#91261A','#C8BC85','#F4E38B','#869E79','#F97D23'];
        var i=0;
        $.each(data.data,function(){
            this.itemStyle={'normal':{'color':color[i]}};
            i++;
        });
    }
    var config = require(
        [
            'echarts',
            'echarts/chart/pie',
        ],
        function (ec) {
            var chart3 = ec.init(document.getElementById(dom));
            var option = {
                tooltip : {
                    trigger: 'item',
                    formatter:c
                },
                toolbox: {
                    show : true,
                    orient:'vertical',
                    y:30,
                    feature : {
                        mark : {show: false},
                        dataView : {
                            show: false,
                            readOnly: false,
                            lang: ['������ͼ', '�ر�', 'ˢ��']
                        },
                        restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data.title
                        }
                    }
                },
                legend:{
                    show:flagLen,
                    orient : 'vertical',
                    //x : 'left',
                    x : '60%',
                    y : 'center',
                    data:data.legend
                },
                animation : false,
                calculable : false,
                series : [
                    {
                        name:data.title,
                        type:'pie',
                        /*  radius : ['35%', '70%'], */
                        radius : '60%',
                        center:[center+'%','50%'],
                        startAngle:0,
                        minAngle:5,
                        roseType:'null',
                        itemStyle : {
                            normal : {
                                label : {
                                    show : flag,
                                    textStyle:{
                                        fontSize:'12',
                                        fontWeight:'normal'
                                        //color: '#FFF'
                                    },
                                    formatter: "{d}%"
                                },
                                labelLine : {
                                    show : flag
                                }
                            },
                            emphasis : {
                                label : {
                                    show : false,
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
            chart3.setOption(option);
            var enConfig = require('echarts/config');
        });
}

//----------------------------------------------------------�������----------------------------------------------------------
function hotPeopleCallBack(data){
    if(data==null||data==''){
        $("#opinionLeader").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
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
            var picUrl = data[i].profileImageUrl;
            if(picUrl == ""||picUrl == null||picUrl == undefined){
                picUrl = njxImgSrc + "/images/weibo/head_pic.jpg";
            }
            var liHtml = " <li style='height: 220px;'><div class='tx'><img src='"+picUrl+"'/></div><p class='mscrame'><a target='_blank' href='"+data[i].webpageUrl+"' class='a1'>"+author+"</a>";
            if(data[i].verifyType==0){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢��������֤ ' class='W_icon icon_approve'></i></a>";	//��V
            }else if(data[i].verifyType>=1&&data[i].verifyType<=7){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢��������֤ ' class='W_icon icon_approve_co'></i></a>";	//��V
            }else if(data[i].verifyType==200||data[i].verifyType==220){
                liHtml += "<a href='http://verified.weibo.com/verify' target='_blank'><i title='΢������ ' class='W_icon icon_club'></i></a>";	//����
            }
            liHtml +="</p><p class='msfs'>" +
            "<span><font class='f_c1'>΢��</font> "+data[i].weiboNums+"</span><span><font class='f_c1'>��˿</font> "+data[i].fansNumber+"</span>" +
            "<span><font class='f_c1'>��ע</font> "+data[i].friendsCount+"</span></p>" +
            "<p class='wbText'>"+content+"</p>";
            liHtml += "<div class='abs' style='bottom: 10px;width: 93%;padding-top: 10px;left: 10px;right: 10px;margin: auto;border-top: 1px solid #e2e2e2;'><p style='margin-left: 20px;margin-bottom: 5;' class='msfs'><span class=''><span class='inline-block' style='width: 40%;display: inline-block;margin-left: 40px;'><font class='f_c1'>�Ķ�</font>&nbsp;"+data[i].views;
            liHtml += "</span><span style='width: 38%;display: inline-block;margin-right: 0;'><font class='f_c1'>ת��</font>&nbsp;"+data[i].forwardNumber;
            liHtml += "</span></span></p><p style='margin-left: 20px;margin-bottom: 5;' class='msfs'><span class=''><span class='inline-block' style='width:40%;display: inline-block;margin-left: 40px;'><font  class='f_c1'>����</font>&nbsp;"+data[i].comments+"</span><span style='width: 38%;display: inline-block;margin-right: 0;'><font  class='f_c1'>����</font>&nbsp;"+data[i].praiseNum+"</span></span></p></div></li>";
            ulHtml += liHtml;

            if(i%3==2){
                ulHtml +="</ul><ul>";
            }
        }
        ulHtml +="</ul>";
        $("#opinionLeader").html(ulHtml);
    }
}


//----------------------------------------------------------��������----------------------------------------------------------
function emoteAnalysisCallback(data){
    if(data!=null&&data!=''){
        //QXMapCallBack(data[0],"container7_1");	//��������ͳ��
        //QXGaugeBack(data[1],'container7_2');	//����ռ��ͼ
        forwardCallBack(data[2],data[3],'container7_3', data[5]);	//ת���������

    }
}


//�����������ͼ
function QXMapCallBack(data){
    var c7 = document.getElementById("container7_1");
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var res = eval(data);
        if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }
        var _chartColumn10 = MapChart(data,"container7_1");
    }
}



//����ռ��ͼ
function QXGaugeBack(data,id){
    //�Ǳ�ͼ
    var c7 = document.getElementById(id);
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            var _chartColumn10 = GaugeChart(data[0],id,2);
        }

    }
}

//����������ת�����������
function forwardCallBack(data,plData,id, maxVlue){
    var c7 = document.getElementById(id);
    if (data==null||data==""){
        c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }else{
        data = eval(data);
        if(data==null ||data.length==0){
            c7.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            var html = "";
            var maxValue = data[0]['num'];
            var maxPlValue = 0;
            if(plData!=null&&plData!=''){
                plData = eval(plData);
                for(n in plData){
                	if(maxPlValue < plData[n]['num']){
                		maxPlValue = plData[n]['num'];
                	}
                }
            }
            var size = data.length;
            if(size>12){
                size = 12;
            }
            for(var i=0;i<size;i++){
                var plNum = 0;
                if(plData!=null&&plData!=''&&plData.length>i){
                    plNum = plData[i]['num'];
                }
                html += '	<li style="margin-bottom:5px;padding-top:5px;list-style-type:none;">';
                html += '		<p class="smallImage" style="float:left;height:22px;margin-right:6px;vertical-align:middle;">'+data[i]['name']+'</p>';
                html += '		<div style="display:inline-block;width:90%;vertical-align:middle;">';
                html += '			<p style="width:' ;
                if(maxValue==0 && data[i]['num']>0){
                	html += 100 ;
                }else if(maxValue==0 && data[i]['num']==0){
                	html += 0 ;
                }else{
                    html += (data[i]['num'] / maxValue * 100) ;
                }
                html += '%;background-color:#607cbe;background-image:linear-gradient(to right, #fff, #607cbe);background-repeat:repeat-x;border-radius:0 2px 2px 0;box-shadow:1px 1px 2px rgba(0, 0, 0, 0.1);height:10px;position:relative;margin:1px;">';
                html += '				<span style="color: #a1a1a1; font-family: arial; font-size: 12px; font-weight: 700; line-height: 10px; position: absolute; right: -68px; width: 60px;">';
                if (data[i]['num'] > 0)
                    html += data[i]['num'];
                html += '				</span>';
                html += '			</p>';
                html += '			<p style="width:' ;
                if(maxPlValue==0 && plNum>0){
                	html += 100 ;
                }else if(plNum==0 && maxPlValue==0){
                	html += 0 ;
                }else{
                	html += (plNum / maxPlValue * 100) ;
                }
                html += '%;background-color: #e6b053; background-image: linear-gradient(to right, #fff, #e6b053); background-repeat: repeat-x; margin-top: 2px;border-radius: 0 2px 2px 0; box-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1); height: 10px; position: relative; margin: 1;">';
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
            $("#"+id).html(html);
        }

    }

}

/****************************************************���Ĵ�������Ϣ*************************************************************/
//    <div class="mwblist mwblist4">
//	<ul>
//		<li>
//			<div class="tx">
//				<img src="../images/eventAnalysis/user_logo.jpg">
//			</div>
//			<p class="mscrame">
//				<a href="#" class="a1">���߷����߷����߷�</a> <a href="#"><i
//					title="΢��������֤ " class="W_icon icon_approve"></i></a>
//			</p>
//		</li>
//	</ul>
//</div>
function coreTranInfoCallBack(data1){
    var size = 5;//���Ĵ�����ÿ����ʾ����
    if(data1==null||data1==''){
        $("#myTabContent").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
    }else{
        var data = eval(data1);
        var ulHtmlGov ="<ul>";
        var ulHtmlOrv = "<ul>";
        var ulHtmlNor = "<ul>";
        var ulHtmlBluv = "<ul>";
        var listGLength = data[0][0]['iContentCommonNetList'];
        //��V
        if(listGLength.length==0){
            $("#modelOne").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
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
                            picUrl = njxImgSrc + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                        "<div class='tx'>"+
                        "<img src='"+picUrl+"'>"+
                        "</div>"+
                        "<p class='mscrame'>"+
                        "<a href='"+listGLength[f].webpageUrl+"' target='io' class='a1'>"+listGLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='΢��������֤' class='W_icon icon_approve_co'></i></a>"+
                        "</p>"+
                        "<p class='msfs'><span><font class='f_c1'>ת����  </font>"+listGLength[f].forwardNumber+"</span></p>"+
                        "</li>";
                    }
                }
                ulHtmlGov = ulHtmlGov+temp;
            }
            ulHtmlGov = ulHtmlGov+"</ul>";
            $("#tab_modelOne").html(ulHtmlGov);
        }
        //��V
        var listOLength = data[0][1]['iContentCommonNetList'];
        if(listOLength.length==0){
            $("#modelThree").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
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
                            picUrl = njxImgSrc + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                        "<div class='tx'>"+
                        "<img src='"+picUrl+"'>"+
                        "</div>"+
                        "<p class='mscrame'>"+
                        "<a href='"+listOLength[f].webpageUrl+"' target='io' class='a1'>"+listOLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='΢��������֤' class='W_icon icon_approve'></i></a>"+
                        "</p>"+
                        "<p class='msfs'><span><font class='f_c1'>ת����  </font>"+listOLength[f].forwardNumber+"</span></p>"+
                        "</li>";
                    }
                }
                ulHtmlOrv = ulHtmlOrv+temp;
            }
            ulHtmlOrv = ulHtmlOrv+"</ul>";
            $("#tab_modelThree").html(ulHtmlOrv);
        }
        //��
        var listNLength = data[0][2]['iContentCommonNetList'];
        if(listNLength.length==0){
            $("#modelTFour").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
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
                            picUrl = njxImgSrc + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                        "<div class='tx'>"+
                        "<img src='"+picUrl+"'>"+
                        "</div>"+
                        "<p class='mscrame'>"+
                        "<a href='"+listNLength[f].webpageUrl+"' target='io' class='a1'>"+listNLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='΢��������֤' class='W_icon icon'></i></a>"+
                        "</p>"+
                        "<p class='msfs'><span><font class='f_c1'>ת����  </font>"+listNLength[f].forwardNumber+"</span></p>"+
                        "</li>";
                    }
                }
                ulHtmlNor = ulHtmlNor+temp;
            }
            ulHtmlNor = ulHtmlNor+"</ul>";
            $("#tab_modelFour").html(ulHtmlNor);
        }
        //��V
        var listBLength = data[0][3]['iContentCommonNetList'];
        if(listBLength.length==0){
            $("#modelTwo").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxImgSrc+"/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
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
                            picUrl = njxImgSrc + "/images/weibo/head_pic.jpg";
                        }
                        temp = temp + "<li>"+
                        "<div class='tx'>"+
                        "<img src='"+picUrl+"'>"+
                        "</div>"+
                        "<p class='mscrame'>"+
                        "<a href='"+listBLength[f].webpageUrl+"' target='io' class='a1'>"+listBLength[f].author+"</a> <a href='http://company.verified.weibo.com/verify/orgapply'><i title='΢��������֤' class='W_icon icon_approve_co'></i></a>"+
                        "</p>"+
                        "<p class='msfs'><span><font class='f_c1'>ת����  </font>"+listBLength[f].forwardNumber+"</span></p>"+
                        "</li>";
                    }
                }
                ulHtmlBluv = ulHtmlBluv+temp;
            }
            ulHtmlBluv = ulHtmlBluv+"</ul>";
            $("#tab_modelTwo").html(ulHtmlBluv);
        }
        //������ʾ���ȼ�
        if(listGLength==0){
            if(listBLength!=0){
                $("#modelOne").attr("class","tab-pane fade in active hide");
                $("#modelTwo").attr("class","tab-pane fade in active");
                $("#modelThree").attr("class","tab-pane fade in active hide");
                $("#modelFour").attr("class","tab-pane fade in active hide");
                $("#li1").attr("class","");
                $("#li2").attr("class","current");
                $("#li3").attr("class","");
                $("#li4").attr("class","");
            }else if(listOLength!=0){
                $("#modelOne").attr("class","tab-pane fade in active hide");
                $("#modelTwo").attr("class","tab-pane fade in active hide");
                $("#modelThree").attr("class","tab-pane fade in active");
                $("#modelFour").attr("class","tab-pane fade in active hide");
                $("#li1").attr("class","");
                $("#li2").attr("class","");
                $("#li3").attr("class","current");
                $("#li4").attr("class","");
            }else if(listNLength!=0){
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

