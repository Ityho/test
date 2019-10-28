<#--<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="gbk"%>-->
<#--<%@ taglib prefix="s" uri="/struts-tags"%>-->
<#--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>-->
<#--<c:set var="ctx" value="${pageContext.request.contextPath}" />-->
<#include "../common/resourcePath.ftl"/>
<link rel="stylesheet" type="text/css" href="${staticResourcePath}/css/indexV4/base/css/font-icon.css">
<link href="${staticResourcePath}/css/common.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/datatable.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${staticResourcePath}/css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="${staticResourcePath}/css/emotion/swiper.min.css">
<link href="${staticResourcePath}/js/openTools/newHotSearch-charts.js" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/openTools/hotSearch.css" rel="stylesheet" type="text/css">
<link href="${staticResourcePath}/css/openTools/custom-icon.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${staticResourcePath}/js/bootstrap/bootstrap-tooltip.js"></script>
<SCRIPT type="text/javascript" src="${staticResourcePath}/js/hotSearchchart.js" charset="utf-8"></SCRIPT>

<style type="text/css">

    .table3 td {
        font-size: 14px;
        color: #595959;
        line-height: 25px;
        padding: 10px 0;
        border-bottom: solid 1px #eaeaea;
        text-align: center;
        word-break: break-all;
        cursor: pointer ! important;
    }
    i.icon-help.fc_blue.fz18.mr10 {
        color: #878d9a ! important;
    }
</style>
	<body class="layoutBody">
    <!--热度概况 start -->
    <div class="row clearfix">
        <div class="col-md-12">
            <div class="panel panel-default" id="">
                <div class="panel-heading titlBar">
                    <h3 class="panel-title mr10">全网热度概况</h3>
                    <div class="tools">
                        <a href="javascript:reloadSelect(1);" class="reload" data-toggle="tooltip" data-placement="top" title="" data-original-title="刷新">
                            <i class="icon-reload"></i>
                        </a>
                        <a href="javascript:reloadSelect(1)" class="full" data-toggle="tooltip" data-placement="top" title="" data-original-title="全屏">
                            <i class="icon-full"></i>
                        </a>

                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1" class="collapse">
                            <i class="icon-angle-down" data-toggle="tooltip" data-placement="top" title="" data-original-title="收起"></i>
                        </a>
                    </div>
                    <i class="icon-help mr10"></i>

                </div>
                <div class="tipinfo showing" style="margin-bottom: 10px;">
                    <div class="tiparro"><i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i><i class="icon-remove close"></i></div>
                    <div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词的全网热度指数值。</div>
                </div>
                <div class="panel-collapse collapse in" id="collapse1" aria-expanded="true">
                    <div class="panel-body" style="padding: 0;">
                        <div class="abs fc_gray" style="bottom: 10px; right: 10px; z-index: 2;">&copy;<span class="time22"></span>
                        </div>
                        <div class="pading20 pading-left-25 pading-right-25 align_c">
                            <span class="small fc_dark_grey fz20"><i class="icon-square fc-orange fz12"></i> <span id="reDuName"></span></span>
                        </div>

                        <div class="chart-bady pading-bottom-20" id="redugaikuang">
                            <div class="row clearfix">
                                <div class="col-md-3 boright_after">
                                    <div class="widget-des">
                                        <p class="fz30 fc_orange mb10"><span id="timeRange"></span></p>
                                        <p class="fz14 fc666">查询时间段</p>
                                    </div>
                                </div>
                                <div class="col-md-3 boright_after">
                                    <div class="widget-des">
                                        <p class="fz30 fc_orange mb10"><span id="hotAvgId"></span></p>
                                        <p class="fz14 fc666">热度指数均值</p>
                                    </div>
                                </div>
                                <div class="col-md-3 boright_after">
                                    <div class="widget-des">
                                        <p class="fz30 fc_orange mb10"><span id="hotAvgTrend"></span></p>
                                        <p class="fz14 fc666">
                                            <span class="v_a_m">一小时热度变化趋势</span>
                                            <span class="v_a_m" rel="drevil" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="该数据表示：当前一小时与上一小时的热度对比情况">
															<i class="icon fz16 v_a_m">&#xe72a;</i>
														</span>

                                        </p>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="widget-des">
                                        <p class="fz30 fc_orange mb10"><span id="hotMaxId"></span></p>
                                        <p class="fz14 fc666">热度指数峰值</p>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>
    <!--热度概况 end -->

    <!--热度指数变化趋势 start -->
    <div class="row clearfix">
        <div class="col-md-12">
            <div class="panel panel-default" id="reDuZhiShuQuShi">

                <div class="panel-heading titlBar">
                    <h3 class="panel-title mr10">全网热度指数趋势</h3>
                    <i class="icon-help fc_blue fz18 mr10"></i>

                    <div class="tools">
                        <a href="javascript:reloadSelect(2);" class="reload" data-toggle="tooltip" data-placement="top" title="" data-original-title="刷新">
                            <i class="icon-reload"></i>
                        </a>
                        <a href="javascript:reloadSelect(2);" class="full" data-toggle="tooltip" data-placement="top" title="" data-original-title="全屏">
                            <i class="icon-full"></i>
                        </a>

                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2" class="collapse">
                            <i class="icon-angle-down" data-toggle="tooltip" data-placement="top" title="" data-original-title="收起"></i>
                        </a>
                    </div>
                </div>
                <div class="tipinfo showing">
                    <div class="tiparro"><i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i><i class="icon-remove close"></i></div>
                    <div class="tipcont">该数据为您显示：<br>在指定时间范围内，该关键词分时段的热度值变化走势。</div>
                </div>
                <div class="panel-collapse collapse in" id="collapse2" aria-expanded="true">
                    <div class="panel-body">
                        <div class="abs fc_gray" style="bottom: 2px; right: 10px; z-index: 2;">&copy;<span class="time22"></span>
                        </div>
                        <div class="pading20 pading-left-25 pading-right-25 align_c">
										<span class="small fc_dark_grey fz20" >
											<div id="reduNameless10">
												<span class="fz16"><i class="legend-icon"></i> <span class="reduNameClass"></span>热度趋势</span>
												<span class="fz16 ml30"><i class="legend-icon-average"></i> <span class="reduNameClass"></span>热度均值</span>
											</div>
											<div id="reduNamemore10">
												<div class="inline-block">
													<span class="fz16 block mb15"><i class="legend-icon"></i> <span class="reduNameClass"></span>热度趋势</span>
													<span class="fz16 block"><i class="legend-icon-average"></i> <span class="reduNameClass"></span>热度均值</span>
												</div>
											</div>
										</span>
                        </div>
                        <div class="chart-bady" id="container1" style="height: 340px;"></div>

                    </div>
                    <div class="panel-footer panel-footer-padd" style="border-bottom: 1px solid #ddd;" id="container1_msg">
                    </div>
                </div>

            </div>
        </div>
    </div>
    <!--热度指数趋势 end -->

    <!--关键词云/关联词 start -->
    <div class="row clearfix">
        <div class="col-md-12">
            <div class="panel panel-default" id="guanJianCiYun">
                <div class="panel-heading titlBar">
                    <h3 class="panel-title mr10">全网关键词云</h3>
                    <i class="icon-help fc_blue fz18 mr10"></i>

                    <div class="tools">

                        <a href="javascript:reloadSelect(5);" class="reload" data-toggle="tooltip" data-placement="top" title="" data-original-title="刷新
"><i class="icon-reload"></i></a>
                        <a href="javascript:reloadSelect(5);" class="full" data-toggle="tooltip" data-placement="top" title="" data-original-title="全屏
">
                            <i class="icon-full"></i>
                        </a>

                        <a data-toggle="collapse" data-parent="#accordion" href="#collapse5" class="collapse">
                            <i class="icon-angle-down" data-toggle="tooltip" data-placement="top" title="" data-original-title="收起"></i>
                        </a>

                    </div>
                </div>
                <div class="tipinfo showing">
                    <div class="tiparro"><i class="icon-arrow_drop_up tipAup" style="left: 68px;"></i><i class="icon-remove close"></i></div>
                    <div class="tipcont">该数据为您显示：<br>利用自然语义分析法，对事件、人物、品牌、地域中所提及的关键词进行分词聚合，呈现出被提及次数最多的关键词；字号越大的词组，被提及次数越多。</div>
                </div>

                <div class="panel-collapse collapse in" id="collapse5">
                    <div class="panel-body" style="padding: 0;">

                        <div class="abs fc_gray" style="bottom: 10px; right: 10px; z-index: 2;">&copy;<span class="time22"></span>
                        </div>
                        <div class="pading20 pading-left-25 pading-right-25 align_c">
                            <span class="small fc_dark_grey fz20"><i class="icon-square fc-orange fz12"></i> <span id="containerName"></span></span>
                        </div>
                        <div class="chart-bady text-center" style="height: 340px;" id="container2">
                        </div>

                    </div>
                    <div class="panel-footer panel-footer-padd" id="container2_msg">
                    </div>
                </div>

            </div>
        </div>

    </div>
    <!--关键词云/关联词 end -->

    </body>
	<script type="text/javascript" src="${staticResourcePath}/js/module.js" charset="utf-8"></script>
	<script type="text/javascript">
        var jquery = $;
        var loaders = '<i class="icon-reload-a fa-spin"></i>';
        var loaders2 = '<i class=" fa-spin"></i>';
        //随机数
        function GetRandom(n){
            GetRandomn=Math.floor(Math.random()*n+1);
            return GetRandomn;
        }
        //热度指数  和   热度值。。
        function goHotWorth(){
            var heat = location.href;
            sendChartPost("${staticResourcePath}/hot/opentools/goHotWorthOTChart", getParams(), worthCallback, "");
        }
        var hotWorthData;
        var hotAvgValue;
        function worthCallback(data){
            hotWorthData=data.data;
            jquery('#timeToggle2').loader('hide',loaders2);
            jquery('#selectTimeWait').loader('hide',loaders2);
            jquery('#doubleSelectId').loader('hide',loaders2);
            jquery('#container1').loader('hide',loaders);
            jquery('#degreeHeatChart').loader('hide',loaders);
            jquery('#qiamwamgTuan').loader('hide',loaders);
            jquery('.counter').loader('hide',loaders);
// 		$("#labelChangeClass1").attr("class","btn btn-sm active");
// 		$("#labelChangeClass2").attr("class","btn btn-sm");
            if(null == data){
                $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:115px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $(".counter").html("<div align=\"center\" style=\"padding-top:0px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                return;
            }
            tag = 0;
            var date = $("#date").val();
            if ((data.length==1&&data.jsons == null) || (data.length==1&&data.jsons == "")) {
                $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:115px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $(".counter").html("<div align=\"center\" style=\"padding-top:0px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
            } else {
                var html="";
                var names=$("#title").val();
                var name="";
                var dataTable=data.jsons;
                if(dataTable&&dataTable.length>0) {
                    for (var i = 0; i < dataTable.length; i++) {
                        var obj = eval('(' + dataTable[i] + ')');
                        if (obj != null || obj != "") {
                            name = names.split(",")[i];
                            $("#reDuName").html(name);
                            $("#reDuName2").html(name);
                            if (name.length > 10) {
                                name = name.substring(0, 10) + "...";
                            }
                            var cus = "";
                            if (obj.ratioHotCustom < 0.01) {
                                cus = "< 0.01";
                            } else {
                                cus = obj.ratioHotCustom;
                            }

                            html += "<tr><td>";
                            if (i == 0) {
                                html += "<font style='color:#3FAD7E'>";
                                $("#hotCus").val(cus);
                            } else if (i == 1) {
                                html += "<font style='color:#758DC4'>";
                            } else if (i == 2) {
                                html += "<font style='color:#0e7dc0'>";
                            }

                            html += name + "</font></td><td>" + cus + "</td>";
                            if(date && date == 24){
                                html += "<td>" + obj.ratioCustom + "%";
                                var ratCus = "" + obj.ratioCustom;
                                if (ratCus.indexOf("-") != -1) {

                                    html += "<i class='icon-arrow-down f_c4'>";
                                } else if (ratCus.length == 1 && ratCus.substring(0, 1) == "0") {

                                } else {
                                    html += "<i class='icon-arrow-up f_c2'>";
                                }
                                html += "</td>";
                                html += "<td>" + obj.differenceCustom;
                                var diffCus = "" + obj.differenceCustom;
                                if (diffCus.indexOf("-") != -1) {
                                    html += "<i class='icon-arrow-down f_c4'>";

                                } else if (diffCus.length == 1 && diffCus.substring(0, 1) == "0") {

                                } else {
                                    html += "<i class='icon-arrow-up f_c2'>";
                                }
                                html += "</td>";
                            }else {
                                html += "<td>" + obj.weiboNum + "</td>";
                                html += "<td>" + obj.weixinNum + "</td>";
                            }
                            html += "<td>" + obj.numberCustom + "</td>";
                            html += "</tr>";
                        }
                    }
                    jquery('#redugaikuang').loader('hide',loaders);
                    jquery('.counter').loader('hide',loaders);
                    $("#hotAvgId").html(data.avgHot+"");
                    $("#hotMaxId").html(Math.max.apply(Math,data.jsonLine)+"");
                    var cha = (data.jsonLine[data.jsonLine.length-1] - data.jsonLine[data.jsonLine.length-2]).toFixed(2);
                    if(cha>0){
                        $("#hotAvgTrend").html("<p class='fz30 fc_orange mb10'><i class='icon fc_red icon-shangsheng'></i>"+cha+"</p>");
                    }else if(cha==0){
                        $("#hotAvgTrend").html("<p class='fz30 fc_orange mb10'>"+cha+"</p>");
                    }else{
                        $("#hotAvgTrend").html("<p class='fz30 fc_orange mb10'><i class='icon fc_green icon-xiajiang'></i>"+Math.abs(cha)+"</p>");
                    }
                    if($("#date").val()==3){
                        $("#timeRange").html("3天");
                    }else if($("#date").val()==5){
                        $("#timeRange").html("5天");
                    }else if($("#date").val()==7){
                        $("#timeRange").html("7天");
                    }else{
                        $("#timeRange").html("24小时");
                    }
                }else{
                    $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:115px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $(".counter").html("<div align=\"center\" style=\"padding-top:0px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                }
            }

            var c1 = document.getElementById("container1");
            if (data.jsonLine==null || data.dates==null) {
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                return false;
            } else {
                var dad=[];
                for (var i = 0; i < data.jsonLine.length; i++) {
                    var v1={value:data.jsonLine[i] , name:data.dates[i]};
                    dad.push(v1);
                }
                var compare = function (obj1, obj2) {
                    var val1 = parseFloat(obj1.value);
                    var val2 = parseFloat(obj2.value);
                    if (val1 > val2) {
                        return -1;
                    } else if (val1 < val2) {
                        return 1;
                    } else {
                        return 0;
                    }
                };
                var alertMsg = "从热度指数的变化趋势来看，<font style='color:#3FAD7E'>";
                dad.sort(compare);
                alertMsg += $("#title").val() + '</font>的热度在' + dad[0].name + '时达到了<font class="f_c2">' + commafy(dad[0].value) + '</font>的峰值。';
                var hhm = "";
                for(var i=0;i<dad.length;i++){
                    if(i<6){
                        hhm += '<li>';
                        hhm += '<p><span class="ml30"><span style="margin-left: -20px !important;" class="ml30">' + dad[i].name + '</span></span></p>';
                        hhm += '<p><span class="ml30"><span style="margin-left: -50px !important;" class="ml30">' + getAllString(dad[i].value) + '</span></span></p>';
                        hhm += '</li>';
                    }
                }
                $("#goHotWorthTop6").html(hhm);

                $("#container1_msg").html(alertMsg);
                hotAvgValue = data.avgHot;
                LineChartHeatNetwork2(data.jsonLine,data.dates,"container1",$("#title").val(),data.avgHot);
            }


        }

        function getAllString(str){
            str = str+"";
            var string = "";
            if(str.indexOf(".")>=0){
                var arr = str.split(".",str.length);
                if(arr[1].length==1){
                    string = str+"0";
                }else{
                    string = str;
                }
            }else{
                string = str+".00";
            }
            return string;
        }

        function LineChartHeatNetwork2(data,leng,dom,title,avg){
            var a=[];
            a.push(title);
            //热度指数趋势
            var hotTrendChart = echarts.init(document.getElementById(dom));

            // 指定图表的配置项和数据
            var option = {
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    orient: 'vertical',
                    feature: {
//         				saveAsImage: {},
//         				restore: {}
                    }
                },
//         		legend: {
//         			top:'0px',
//                      data: a
//                  },
                grid: {
                    left: '2%',
                    right: '5%',
                    top: '15%',
                    bottom: '12%',
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
                    data: leng

                }],
                yAxis: [{
                    type: 'value',
                    axisLine: { //坐标轴轴线 默认 true,
                        show: false
                    },
                    splitLine:{
                        show: true,
                        lineStyle:{
                            color: 'rgba(240, 239, 239, 0.8)',
                            width: 1,
                            type: 'solid'
                        }
                    },
                    axisTick: { //坐标轴刻度
                        show: false
                    },
                    axisLabel: { //坐标轴刻度标签
                        show: true,
                        textStyle: {
                            color: '#69747D'
                        }
                    },
                }],

                series: [{
                    name: title,
                    type: 'line',
                    smooth: true, //这句就是让曲线变平滑的
                    symbol: 'none', //拐点样式
                    data: data,
                    markPoint: {
                        data: [{
                            type: 'max',
                            name: '最大值',
                            symbol: 'rect',
                            symbolOffset: [0, '-100%'],
                            symbolSize: [100, 26],
                            label: {
                                normal: {
                                    show: true,
                                    position: 'insideTop',
                                    formatter: '热度峰值: {c}',
                                    textStyle: {
                                        fontSize: '13',
                                        color: '#fff',
                                    }
                                }
                            }
                        }, {
                            symbol: 'circle',
                            type: 'max',
                            symbolSize: [10, 10],
                            itemStyle: {
                                normal: {
                                    color: "#f18d00",
                                    borderColor: 'rgba(241,141,0, .3)', //rgba(255, 199, 43, .3)
                                    borderWidth: 10,
                                    shadowColor: '#ffc72b',
                                    shadowBlur: 30
                                }
                            },
                            label: {
                                normal: {
                                    show: false
                                }
                            }
                        }]
                    },

                    itemStyle: {
                        normal: {
                            color: '#f18d01'
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: '#f18d01',
                            width: 1.5,
                            type: 'solid'
                        }
                    },
                    areaStyle: {
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
                    },
                    markLine: {
                        silent: true,
                        data: [{
                            type: 'average',
                            name: '平均值',

                        }],
                        precision: 2,
                        label: {
                            normal: {
                                position:"end",
                                formatter: '热度均值: \n '+avg,
                                textStyle: {
                                    fontSize: '13'
                                }

                            }
                        },
                        lineStyle: {
                            normal: {
                                color: '#FF9B01',
                                width:1.5,
                            }
                        }
                    },
                }

                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            hotTrendChart.setOption(option);
        }

        //  热度值
        function degreeHeatChartFun(dom,data){
            //热度值
            var degreeHeatChart = echarts.init(document.getElementById(dom));

            // 指定图表的配置项和数据
            option = {
                title: {
                    x: "center",
                    bottom: '20%',
                    text: '热度值',
                    textStyle: {
                        fontSize: 14,
                        fontStyle: 'normal',
                        color: '#333'
                    }
                },
                tooltip : {
                    formatter: "{a} <br/>{b} : {c}%"
                },
                toolbox: {
                    feature: {
// 			            restore: {},
// 			            saveAsImage: {}
                    }
                },
                series: [
                    {
                        name: '热度值',
                        type: 'gauge',
                        radius: '69%',
                        splitLine:{
                            show: true,
                            length: 20,
                            splitNumber: 10,
                            lineStyle: {
                                width: 2,
                                color: '#f1730b'
                            }
                        },
                        axisTick:{
                            show: true,
                            length: 12,
                            splitNumber: 10,
                            lineStyle: {
                                width: 2,
                                color: '#fff'
                            }
                        },
                        axisLine: {
                            width: 20,
                            show: true,
                            lineStyle: {
                                width:0,
                                shadowBlur: 0
                            }
                        },
                        axisLabel:{
                            textStyle:{
                                color: '#ada9a8'
                            }
                        },
                        pointer:{
                            length: '50%',
                            width: 35
                        },
                        itemStyle:{
                            normal:{
                                color: '#e0521e'
                            }
                        },
                        detail: {
                            show: true,
                            formatter:'{value}',
                            offsetCenter: [0, 0],
                            width: 50,
                            textStyle:{color: '#fff',fontSize: 20},
                        },
                        data: [{value: data}],
                        silent: true,
                        z: 3
                    },
                    {
                        name: '指针',
                        type: 'gauge',
                        radius: '26%',
                        min: 0,
                        max: 360,
                        startAngle: 90,
                        endAngle: -269.9999,
                        splitLine:{
                            show: false
                        },
                        axisTick:{
                            show: false
                        },
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: [
                                    [0, '#e0521e'],
                                    [1, '#e0521e']
                                ],
                                width: 100
                            }
                        },
                        axisLabel:{
                            show: false
                        },
                        pointer:{
                            show: false
                        },
                        itemStyle:{
                            normal:{
                                color: '#e0521e'
                            }
                        },
                        detail: {
                            show: true,
                            formatter:'{value}',
                            offsetCenter: [0, 0],
                            width: 50,
                            textStyle:{color: '#fff',fontSize: 20},
                        },
                        data: [{value: 50}],
                        z: 2
                    },
                    {
                        tooltip: {show: false},
                        name:'背景颜色',
                        type: 'pie',
                        radius: ['63%', '85%'],
                        center: ['50%', '50%'],
                        hoverAnimation: false,
                        startAngle: 225,
                        labelLine: {
                            normal: {show: false}
                        },
                        label: {normal: {position: 'center'}
                        },
                        data: [{
                            value: 75,
                            itemStyle: {
                                "normal": {
                                    "color": new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                                        "offset": 0,
                                        "color": '#f1611a'
                                    }, {
                                        "offset": 1,
                                        "color": '#f3a43c'
                                    }]),
                                }
                            },


                        }, {
                            value: 25,
                            itemStyle: {
                                "normal": {
                                    color: '#fff'
                                }
                            }

                        }
                        ],
                        z: 0
                    }

                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            degreeHeatChart.setOption(option);

        }

        function LineChartHeatNetwork(data,dom,type,dataList){
//      $("#container1").empty();
            var tooltip = "";
            var chart1 = echarts.init(document.getElementById(dom));
            if(type == 2){
                var par1 = ['E','D','C','B','A'];
                var par2 = ['D','C','B','A'];
                var par3 = ['C','B','A'];
                var par4 = ['B','A'];
                var par5 = ['A'];
                $.each(data[0].data, function(i,n) {
                    data[0].data[i].markPoint.itemStyle = {
                        normal: {
                            label: {
                                show: true,
                                formatter: function (param) {
                                    for (var k = 0; k < dataList.length; k++) {
                                        if (dataList[k] == param.data.yAxis) {
                                            if (dataList.length > 4) {
                                                return par1[k];
                                            } else if (dataList.length > 3) {
                                                return par2[k];
                                            } else if (dataList.length > 2) {
                                                return par3[k];
                                            } else if (dataList.length > 1) {
                                                return par4[k];
                                            } else {
                                                return par5[k];
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

                tooltip = {
                    trigger: 'axis',
                    formatter: function (params) {
                        if(params.componentType  == 'markPoint') {
                            return params.seriesName + '<br/>' + params.name;
                        }else{
                            return params[0].seriesName + '<br/>' + params[0].value + "<br/>日期：" + params[0].name;
                        }
                    }
                }
            }else{
                tooltip = {
                    trigger: 'axis'
                }
            }

            var option = {
                tooltip : tooltip,
                toolbox: {
                    orient:'vertical',
                    feature: {
//                  restore : {show: true},
                        saveAsImage : {
                            show: true,
                            name:data[0].title
                        }
                    }
                },
                legend: {
                    data: data[0].legend
                },
                color:['#f18d01','#758DC4','#0e7dc0'],//颜色
                grid: {
                    left: '2%',
                    right: '5%',
                    /* top:'6%',
                     bottom: '15%',*/
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        axisLine:{//坐标轴轴线 默认 true,
                            show: false
                        },
                        axisTick:{//坐标轴刻度
                            show: false,
                            lineStyle:{
                                color:'#888',
                                width: 1,
                                type: 'solid'
                            }
                        },
                        axisLabel:{//坐标轴刻度标签
                            show: true,
                            //rotate: 30,  //旋转角度
                            textStyle:{
                                color:'#888',
                                fontSize:15
                            }
                        },
                        boundaryGap : false,
                        data : data[0].datetime
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLine:{//坐标轴轴线 默认 true,
                            show: false
                        },
                        axisTick:{//坐标轴刻度
                            show: false
                        },
                        axisLabel:{//坐标轴刻度标签
                            show: true,
                            //rotate: 30,  //旋转角度
                            textStyle:{
                                color:'#888',
                                fontSize:15
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
                    handleStyle:{
                        color:"#d3dee5",
                        borderColor: 'rgba(0,0,0,.3)'
                    },
                    showDetail: false,
                    textStyle:{
                        color:"#000"},
                    borderColor:"#90979c"


                }, {
                    "type": "inside",
                    //"zoomLock": true,
                    "show": true,
                    "height": 15,
                    "start": 1,
                    "end": 35
                }],
                calculable : false,
                series :data[0].data
            };
            chart1.setOption(option);
        }

        //词云
        function goRelatedChart(){
            sendChartPost("${staticResourcePath}/hot/opentools/goMoreWordsOTChart", getParams(), relatedCallBack, "");
        }
        var relatedData;
        function relatedCallBack(data){
            relatedData=data.data;
            jquery('#collapse5').loader('hide',loaders);
            var c2 = document.getElementById("container2");
            if(null==data){
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }
            if(null!=data.li&&null!=data.sb){
                var subMsg = "";
                subMsg += "在与<font style='color:#3FAD7E'>";
                var dx=data.sb.split(",");
                for(var i=0;i<dx.length;i++){
                    if (i == 0) {
                        subMsg += data.title + "</font>相关的全部信息中， 提及频次最高的词语依次为<font class='fc_red'>" + dx[i] + "</font>";
                    } else if (i == 1) {
                        subMsg += "<font class='fc_red'>、" + dx[i] + "</font>";
                    } else if (i == 2) {
                        subMsg += "和<font class='fc_red'>" + dx[i] + "</font>"+"。";
                    }
                }
                $("#container2_msg").html(subMsg);
                var _chartColumn2 = cloudChart(eval(data.li), 1, "container2" , 1);
            }else{
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";

            }
        }
        function relatedCallBack2(data){
            relatedData=data;
            jquery('#collapse5').loader('hide',loaders);
            var c2 = document.getElementById("container2");
            if(null==data){
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }
            if(null!=data.li&&null!=data.sb){
                var subMsg = "";
                subMsg += "在与<font style='color:#3FAD7E'>";
                var dx=data.sb.split(",");
                for(var i=0;i<dx.length;i++){
                    if (i == 0) {
                        subMsg += data.title + "</font>相关的全部信息中， 提及频次最高的词语依次为<font class='fc_red'>" + dx[i] + "</font>";
                    } else if (i == 1) {
                        subMsg += "<font class='fc_red'>、" + dx[i] + "</font>";
                    } else if (i == 2) {
                        subMsg += "和<font class='fc_red'>" + dx[i] + "</font>"+"。";
                    }
                }
                $("#container2_msg").html(subMsg);
                var _chartColumn2 = cloudChart2(eval(data.li), 1, "container2" , 1);
            }else{
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";

            }
        }

        var url = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAbEAAAEmCAYAAADss65KAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3BpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5NjhlYWU0MC0wYmI0LTRhYWItODBlYy1iNGIxM2RkOTc5NmEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6REFENEIzMEIxREE0MTFFN0FGM0FCRTE2NjhGMjAzQzYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6REFENEIzMEExREE0MTFFN0FGM0FCRTE2NjhGMjAzQzYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4QUVDM0JGQTFEMDYxMUU3ODhCMUExMEQwN0Y2OEQ1RCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4QUVDM0JGQjFEMDYxMUU3ODhCMUExMEQwN0Y2OEQ1RCIvPiA8L3Jk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';
        var maskImage = new Image();
        maskImage.src = url;

        function cloudChart(data,length,dom,type){
            var sizeMin=25;
            var gridSize=5;//间隔
            if(type == 2) {
                sizeMin=20;
                gridSize=5;
            }

            //间隔
            if(phone){
                sizeMin=15;
                gridSize=5;
            }
            if(length>2){
                sizeMin=20;//字体大小
                gridSize=5;
            }else if(length>1){
                sizeMin=25;
                gridSize=8;
            }
            var chart2 = echarts.init(document.getElementById(dom));
            var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色
            var option = {
                toolbox: {
                    show : true,
                    orient:'vertical',
                    y:15,
                    x:'right',
                    feature : {
                        mark : {show: false},
//                     dataView : {
//                         show: false,
//                         readOnly: false,
//                         lang: ['数据视图', '关闭', '刷新']
//                     },
//                     restore : {show: true},
//                     saveAsImage : {
//                         show: true
//                         //name:data.title
//                     }
                    }
                },
                calculable : false,
                series: [{
                    type: 'wordCloud',
                    textStyle: {
                        normal: {
                            fontFamily: 'STHeiti sans-serif',
                            color: function() {
                                var i = parseInt(Math.random() * 3);
                                return colors[i];
                            }
                        },emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    width: '90%',
                    height: '90%',
                    top: 0,
                    bottom:0,
                    maskImage: maskImage,
                    center:['50%','50%'],
                    rotationRange: [0, 0],
                    rotationStep: 45,
                    gridSize: gridSize,
                    sizeRange: [sizeMin, 50],
                    data:data
                }]
            };
            chart2.setOption(option);
        }
        function cloudChart2(data,length,dom,type){
            var sizeMin=25;
            var gridSize=10;//间隔
            if(type == 2) {
                sizeMin=20;
                gridSize=5;
            }

            //间隔
            if(phone){
                sizeMin=15;
                gridSize=5;
            }
            if(length>2){
                sizeMin=20;//字体大小
                gridSize=5;
            }else if(length>1){
                sizeMin=25;
                gridSize=8;
            }
            var chart2 = echarts.init(document.getElementById(dom));
            var colors = ["#f18d00","#f1b192","#b9a7af"];//词云颜色
            var option = {
                toolbox: {
                    show : true,
                    orient:'vertical',
                    y:15,
                    x:'right',
                    feature : {
                        mark : {show: false},
//                     dataView : {
//                         show: false,
//                         readOnly: false,
//                         lang: ['数据视图', '关闭', '刷新']
//                     },
//                     restore : {show: true},
//                     saveAsImage : {
//                         show: true
//                         //name:data.title
//                     }
                    }
                },
                calculable : false,
                series: [{
                    type: 'wordCloud',
                    textStyle: {
                        normal: {
                            fontFamily: 'STHeiti sans-serif',
                            color: function() {
                                var i = parseInt(Math.random() * 3);
                                return colors[i];
                            }
                        },emphasis: {
                            shadowBlur: 10,
                            shadowColor: '#333'
                        }
                    },
                    width: 'auto',
                    height: 'auto',
                    top: 'auto',
                    bottom:'auto',
                    maskImage: maskImage,
                    center:['100%','100%'],
                    rotationRange: [0, 0],
                    rotationStep: 45,
                    gridSize: gridSize,
                    sizeRange: [50, 80],
                    data:data
                }]
            };
            chart2.setOption(option);
        }

        function goHotMessage(){
            sendChartPost("${staticResourcePath}/hot/opentools/goHotMessageOTChart.action", getParams(), goHotMessageCallBack, "");
        }
        function goHotMessageCallBack(datt){
            jquery('#hotMessageDivId').loader('hide',loaders);
            if(null==datt){
                $("#hotMessageDivId").html("<div align=\"center\" style=\"padding-top:177px; padding-bottom:150px; \"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                return;
            }
            var data = datt.jsons;
            var data2 = datt.jsons2;
            var html="";
            var dd=[];
            var num=0;
// 		if(null!=data || null!=data2){
            if(null==data && null==data2){
                $("#hotMessageDivId").html("<div align=\"center\" style=\"padding-top:177px; padding-bottom:150px; \"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                return;
            }
            if(null==data && null!=data2){
                num = 6;
            }
            if(null!=data && null==data2){
                num=0;
            }
            if(null!=data && null!=data2){

                if(data2.length>=3){
                    num=3;
                }else{
                    num=data2.length;
                }

            }

            if(num!=0){
                for(var i=0;i<num;i++){
//					var da=jQuery.parseJSON(data2[i]);
                    var da=eval("(" + data2[i] + ")");
                    var title=da.title.replace(/<\/?.+?>/g,"");
                    var sourceWebsite=da.sourceWebsite;
                    var publishTime=da.publishTime;
                    var similarCount=da.similarCount;
                    var url=da.url;
                    var profileImageUrl=da.profileImageUrl;
                    var author=da.author;
                    if(author==null || author=="" || author.length<0){
                        author=sourceWebsite;
                    }
                    var originType=da.originType;
                    var captureWebsiteName=da.captureWebsiteName;
                    var v1={ captureWebsiteName:captureWebsiteName,originType:originType,author:author,title:title , sourceWebsite:sourceWebsite , publishTime:publishTime ,similarCount :similarCount , url: url,profileImageUrl:profileImageUrl,};
                    dd.push(v1);
                }
            }
            if((6-num)>0){
                for(var i=0;i<(6-num);i++){
                    //				var da=jQuery.parseJSON(data[i]);
                    if(data.length>=(i+1)){
                        var da=eval("(" + data[i] + ")");
                        var title=da.title.replace(/<\/?.+?>/g,"");
                        var sourceWebsite=da.sourceWebsite;
                        var publishTime=da.publishTime;
                        var similarCount=da.similarCount;
                        var url=da.url;
                        var profileImageUrl=da.profileImageUrl;
                        var author=da.author;
                        if(author==null || author=="" || author.length<0){
                            author=sourceWebsite;
                        }
                        var originType=da.originType;
                        var captureWebsiteName=da.captureWebsiteName;
                        var v1={ captureWebsiteName:captureWebsiteName,originType:originType,author:author,title:title , sourceWebsite:sourceWebsite , publishTime:publishTime ,similarCount :similarCount , url: url,profileImageUrl:profileImageUrl,};
                        dd.push(v1);
                    }
                }
            }



            var compare = function (obj1, obj2) {
                var val1 = parseFloat(obj1.similarCount);
                var val2 = parseFloat(obj2.similarCount);
                if (val1 > val2) {
                    return -1;
                } else if (val1 < val2) {
                    return 1;
                } else {
                    return 0;
                }
            };
            dd.sort(compare);
            for(var i=0;i<dd.length;i++){
                html += '<li>';
                html += '<div class="widget widget-shadow">';
                html += '<div class="widget-content pading20 bg-white">';
                html += '<div class="media">';
                html += '<div class="clearfix">';
                html += '<a style="cursor: pointer;" class="media-left float_l mr20" onclick=hotMessageClick("'+dd[i].url+'")>';
                var imag;
                if(null!=dd[i].profileImageUrl&&""!=dd[i].profileImageUrl && dd[i].profileImageUrl.length>5){
                    imag=dd[i].profileImageUrl;
                }else{
                    if(dd[i].originType == "sp"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-video.jpg";
                    }else if(dd[i].originType == "wx"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-weixin.jpg";
                    }else if(dd[i].originType == "xw"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-news.jpg";
                    }else if(dd[i].originType == "lt"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-bbs.jpg";
                    }else if(dd[i].originType == "bk"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-blog.jpg";
                    }else if(dd[i].originType == "app"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-app.jpg";
                    }else if(dd[i].originType == "zw"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-affairs.jpg";
                    }else if(dd[i].originType == "baokan"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-press.jpg";
                    }else if(dd[i].originType == "jw"){
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-media.jpg";
                    }else if(dd[i].originType == "wb"){
                        if(dd[i].captureWebsiteName == "腾讯微博"){
                            imag="${staticResourcePath}/images/userlogo/app-more-icon-txwb.jpg";
                        }else{
                            imag="${staticResourcePath}/images/userlogo/app-more-icon-weibo.jpg";
                        }
                    }else{
                        imag="${staticResourcePath}/images/userlogo/app-more-icon-web.jpg";
                    }
                }
                html += '<img class="media-object img-circle" src="'+imag+'" width="50px" height="50px" alt="媒体对象">';
                html += '</a>';
                html += '<div class="media-heading">';
                html += '<h3 style="cursor: pointer;" onclick=hotMessageClick("'+dd[i].url+'") class="fz16 mb5">'+dd[i].author+'</h3>';
                html += '<p class="fc_dark_grey">'+dd[i].publishTime+'</p>';
                html += '</div>';
                html += '</div>';
                html += '<div class="media-body mt10 dot" style="height: 120px;">';
                if(dd[i].title.length>=140){
                    html+='<a style="cursor: pointer;" onclick=hotMessageClick("'+dd[i].url+'") title="'+dd[i].title+'">'+addFontByTitle(dd[i].title.substring(0,140),$("#title").val())+"..."+'</font></a>';
                }else{
                    html+='<a style="cursor: pointer;" onclick=hotMessageClick("'+dd[i].url+'") title="'+dd[i].title+'">'+addFontByTitle(dd[i].title,$("#title").val())+'</a>';
                }
                html += '</div>';
                html += '<div class="media-footer fc_dark_grey clearfix">';
                html += '<div class="float_l">来源：'+dd[i].sourceWebsite+'</div>';
                html += '<div class="float_r">相似信息量：'+commafy(dd[i].similarCount)+'</div>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</li>';
            }
            $("#hotMessageDivId").html(html);
        }
        function hotMessageClick(href){
            if(href!="" || href.length>0){
                window.open(href,"_blank");
            }
        }

        function addFontByTitle(str,name){
            if(str.indexOf(name)>=0){
                var s = str.split(name);
                var res = '';
                for(var i=0;i<s.length;i++){
                    if(i!=s.length-1){
                        res+=s[i]+"<font color='red'>"+name+"</font>";
                    }else{
                        res+=s[i];
                    }
                }
                return res;
            }else{
                return str;
            }
        }
        //关联度
        function goAssociation(){
            sendChartPost("${staticResourcePath}/hot/opentools/goAssociationOTChart.action", getParams(), goAssociationCallBack, "");
        }
        var goAssociationData;
        function goAssociationCallBack(data){
            goAssociationData=data;
            if(null!=data){
                if(null!=data.sb || null!=data.li){
                    var zhongxin=[];
                    var pa=[];
                    var da=jQuery.parseJSON(data.sb);
                    var dx=jQuery.parseJSON(data.li);
                    var title=data.title;
                    if(null!=da && da!=""){
                        zhongxin=[50, 50, da.na, '#eaa269','100%','100%'];
                    }
                    var htm="<p>通过对<font style='color:#3FAD7E'>"+title+"</font>的相关信息进行分析，与核心词"+da.na+"关联词最高的词语依次为<font class='fc_red'>";
                    if(null!=dx&&dx!=""){
                        for(var i=0;i<dx.length;i++){
                            var s=[];
                            s.push(dx[i].xVal);
                            s.push(dx[i].yVal);
                            s.push(dx[i].name);
                            s.push(dx[i].size);
                            s.push(dx[i].u);
                            var p=(dx[i].per)*1;
                            var c=p.toFixed(2)+"%";
                            s.push(c);
                            pa.push(s);
                            if(i==0){
                                htm+=dx[i].name+"、";
                            }
                            if(i==1){
                                htm+=dx[i].name+"</font>和<font class='fc_red'>";
                            }
                            if(i==2){
                                htm+=dx[i].name+"。</font></p>";
                            }
                        }
                    }
                    jquery('#collapse6').loader('hide',loaders);

                    $("#relevantChart_msg").html(htm);
//				<p>通过对与王俊凯相关的信息进行分析后可看出，核心词<font class="fc_red">王俊凯</font>关联度最高的词语为失望(17.5%)、晚安(13.66%)和月亮(13.55%)。</p>
                    goAssociationView("relevantChart",zhongxin,pa);
                }else{
                    jquery('#collapse6').loader('hide',loaders);
                    $("#collapse6").html("<div align=\"center\" style=\"padding-top:162px; padding-bottom:150px; \"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                }
            }else{
                jquery('#collapse6').loader('hide',loaders);
                $("#relevantChart").html("<div align=\"center\" style=\"padding-top:80px; padding-bottom:150px; \"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
            }
        }

        function chartloading(id){
            jquery('#' + id).loader('show', loaders);
            if("counter"==id){
                jquery('.' + id).loader('show', loaders);
            }
        }

        function reloadSelect(n){
            //热度值
            if(n==1){
                var clazz=$("#reDuGaiKuang").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("degreeHeatChart","1");
                }else{
                    //放大
                    chartloadingReloadSelect("degreeHeatChart","2");
                }
                $("#reDuName").html($("#title").val());
                var heat = location.href;
                if(location.href.indexOf("/heatPreview")!=-1){
<#--// 				if('${ticket}'!=""){-->
// 					if(yearSummaryData.msg){
// 	                    HeatReportDwr.getHotYearSummaryAndlineChart(yearSummaryData.dataYearLine,$("#title").val(),GetRandom(30),yearWorthCallback);
// 	                }else{
// 	                    var c1 = document.getElementsByClassName("counter");
// 	                    var c2 = document.getElementById("container1");
// 	                    var c3 = document.getElementById("clusterList");
// <%-- 	                    c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// <%-- 	                    c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// <%-- 	                    c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>

// 	                }
// 				}else{
                    goHotWorthToBig(hotWorthData,"1");
// 				}
                }else{
                    goHotWorthToBig(hotWorthData,"1");
                }
            }else if(n==2){
                // 热度指数趋势
                var clazz=$("#reDuZhiShuQuShi").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("container1","1");
                }else{
                    //放大
                    chartloadingReloadSelect("container1","2");
                }
                $("#container1Name").html($("#title").val());
                if(location.href.indexOf("/heatPreview")!=-1){
<#--// 				if('${ticket}'!=""){-->
// 					if(yearSummaryData.msg){
// 	                    HeatReportDwr.getHotYearSummaryAndlineChart(yearSummaryData.dataYearLine,$("#title").val(),GetRandom(30),yearWorthCallback);
// 	                }else{
// 	                    var c1 = document.getElementsByClassName("counter");
// 	                    var c2 = document.getElementById("container1");
// 	                    var c3 = document.getElementById("clusterList");
// <%-- 	                    c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// <%-- 	                    c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// <%-- 	                    c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>

// 	                }
// 				}else{
                    goHotWorthToBig(hotWorthData,"2");
// 				}
                }else{
                    goHotWorthToBig(hotWorthData,"2",hotAvgValue);
                }
            }else if(n==3){
                //媒体来源
                var clazz=$("#meiTiFrom").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("mediaSourceChart","1");
                }else{
                    //放大
                    chartloadingReloadSelect("mediaSourceChart","2");
                }
// 			if(location.href.indexOf("/heatPreview")!=-1){
<#--// 				if('${ticket}'!=""){-->
// 	                if(yearOriginData.msg){
// 	                    HeatReportDwr.getHotYearOTChart(yearOriginData.dataYearSource,$("#title").val(),GetRandom(30),OriginCallBack);
// 	                }else{
// 	                    var c2 = document.getElementById("container3");
// <%-- 	                    c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// 	                }
// 				}else{
                mediaFromCallBack(goMediaData);
// 				}
// 			}else{
// 				mediaFromCallBack(goMediaData);
// 			}
            }else if(n==4){
                //媒体友好度
                chartloading("collapse4");//媒体友好度
                setTimeout(function(){
                    jquery('#collapse4').loader('hide',loaders);
                },3000)
//				var clazz=$("#meiTiFriend").attr("class");
//				if(clazz.indexOf("fullCon")<0){
//					//缩小
//					chartloadingReloadSelect("mediaFriendlyChart","1");
//				}else{
//					//放大
//					chartloadingReloadSelect("mediaFriendlyChart","2");
//				}

//				mediaFriendCallBack(goMediaData);
            }else if(n==5){
                //词云
                var clazz=$("#guanJianCiYun").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("container2","1");
                }else{
                    //放大
                    chartloadingReloadSelect("container2","2");
                }

                $("#containerName").html($("#title").val());
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    relatedCallBack(relatedData);
                }else{
                    //放大
                    relatedCallBack2(relatedData);
                }

            }else if(n==6){
                //关联词
                var clazz=$("#guanLianCi").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("relevantChart","1");
                }else{
                    //放大
                    chartloadingReloadSelect("relevantChart","2");
                }
                $("#relevantName").html($("#title").val());
                goAssociationCallBack(goAssociationData);
            }else if(n==7){
                //地域分析
                var clazz=$("#diYuFenXi").attr("class");
                if(clazz.indexOf("fullCon")<0){
                    //缩小
                    chartloadingReloadSelect("container4","1");
                    chartloadingReloadSelect("container5","1");
                }else{
                    //放大
                    chartloadingReloadSelect("container4","2");
                    chartloadingReloadSelect("container5","2");
                }
// 			if(location.href.indexOf("/heatPreview")!=-1){
<#--// 				if('${ticket}'!=""){-->
// 	                if(yearAreaData.msg){
// 	                    HeatReportDwr.getHotYearAreaChart(yearAreaData.dataYearArea,$("#title").val(),GetRandom(30),AreaCallBack);
// 	                }else{
// 	                    var c2 = document.getElementById("container4");
// <%-- 	                    c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>"; --%>
// 	                }
// 				}else{
// 					goChinaCallBack(goChinaData);
// 				}
// 			}else{
                goChinaCallBack(goChinaData);
// 			}
            }
        }

        function goHotWorthToBig(data,flag,avg){
            jquery('#selectTimeWait').loader('hide',loaders2);
            jquery('#doubleSelectId').loader('hide',loaders2);
            jquery('#container1').loader('hide',loaders);
            jquery('#degreeHeatChart').loader('hide',loaders);
            jquery('.counter').loader('hide',loaders);
            $("#labelChangeClass1").attr("class","btn btn-sm active");
            $("#labelChangeClass2").attr("class","btn btn-sm");
            if(flag=="1"){
                //热度值
                var date = $("#date").val();
                if ((data.length==1&&data.jsons == null) || (data.length==1&&data.jsons == "")) {
                    $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:50px;margin-top: 90px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $(".counter").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                } else {
                    var html="";
                    var names=$("#title").val();
                    var name="";
                    var dataTable=data.jsons;
                    if(dataTable&&dataTable.length>0) {
                        for (var i = 0; i < dataTable.length; i++) {
                            var obj = eval('(' + dataTable[i] + ')');
                            if (obj != null || obj != "") {
                                name = names.split(",")[i];
                                $("#reDuName").html(name);
                                $("#reDuName2").html(name);
                                if (name.length > 10) {
                                    name = name.substring(0, 10) + "...";
                                }
                                var cus = "";
                                if (obj.ratioHotCustom < 0.01) {
                                    cus = "< 0.01";
                                } else {
                                    cus = obj.ratioHotCustom;
                                }

                                html += "<tr><td>";
                                if (i == 0) {
                                    html += "<font style='color:#3FAD7E'>";
                                    $("#hotCus").val(cus);
                                } else if (i == 1) {
                                    html += "<font style='color:#758DC4'>";
                                } else if (i == 2) {
                                    html += "<font style='color:#0e7dc0'>";
                                }

                                html += name + "</font></td><td>" + cus + "</td>";
                                if(date && date == 24){
                                    html += "<td>" + obj.ratioCustom + "%";
                                    var ratCus = "" + obj.ratioCustom;
                                    if (ratCus.indexOf("-") != -1) {

                                        html += "<i class='icon-arrow-down f_c4'>";
                                    } else if (ratCus.length == 1 && ratCus.substring(0, 1) == "0") {

                                    } else {
                                        html += "<i class='icon-arrow-up f_c2'>";
                                    }
                                    html += "</td>";
                                    html += "<td>" + obj.differenceCustom;
                                    var diffCus = "" + obj.differenceCustom;
                                    if (diffCus.indexOf("-") != -1) {
                                        html += "<i class='icon-arrow-down f_c4'>";

                                    } else if (diffCus.length == 1 && diffCus.substring(0, 1) == "0") {

                                    } else {
                                        html += "<i class='icon-arrow-up f_c2'>";
                                    }
                                    html += "</td>";
                                }else {
                                    html += "<td>" + obj.weiboNum + "</td>";
                                    html += "<td>" + obj.weixinNum + "</td>";
                                }
                                html += "<td>" + obj.numberCustom + "</td>";
                                html += "</tr>";
                            }
                        }
                        jquery('#degreeHeatChart').loader('hide',loaders);
                        jquery('.counter').loader('hide',loaders);
                        $("#rdtbID").html(obj.ratioCustom+"%");
                        $("#rdbhID").html(obj.differenceCustom+"");
                        $("#wbxxlID").html(commafy(obj.weiboNum));
                        $("#qwxxlID").html(commafy(obj.numberCustom)+" ");
                        $("#wbxxlID2").html(commafy(obj.weiboNum));
                        $("#qwxxlID2").html(commafy(obj.numberCustom)+"");
                        $("#weixinId").html(commafy(obj.weixinNum)+"");
                        $("#hotCus").val(obj.ratioHotCustom);
                        //热度值
// 	                degreeHeatChartFun("degreeHeatChart",obj.ratioHotCustom+"");
                    }else{
                        $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                        $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:50px;margin-top: 90px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                        $(".counter").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    }
                }
            }else if("2"==flag){
                //热度指数趋势
                var c1 = document.getElementById("container1");
                if (data.jsonLine==null || data.dates==null) {
                    c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                    return false;
                } else {
                    var dad=[];
                    for (var i = 0; i < data.jsonLine.length; i++) {
                        var v1={value:data.jsonLine[i] , name:data.dates[i]};
                        dad.push(v1);
                    }
                    var compare = function (obj1, obj2) {
                        var val1 = parseFloat(obj1.value);
                        var val2 = parseFloat(obj2.value);
                        if (val1 > val2) {
                            return -1;
                        } else if (val1 < val2) {
                            return 1;
                        } else {
                            return 0;
                        }
                    };
                    var alertMsg = "从热度指数的变化趋势来看，<font style='color:#3FAD7E'>";
                    dad.sort(compare);
                    alertMsg += $("#title").val() + '</font>的热度在' + dad[0].name + '时达到了<font class="f_c2">' + commafy(dad[0].value) + '</font>的峰值。';
                    $("#container1_msg").html(alertMsg);
                    LineChartHeatNetwork2(data.jsonLine,data.dates,"container1",$("#title").val(),avg);
                }
            }
        }

        function chartloadingTab(id){
            if(id == "counter"){
                jquery('.' + id).loader('show',loaders);
            }else if(id == "selectTimeWait"){
                jquery('#' + id).loader('show',loaders2);
            }else if(id == "doubleSelectId"){
                jquery('#' + id).loader('show',loaders2);
            }else if(id == "timeToggle2"){
                jquery('#' + id).loader('show',loaders2);
            }else{
                jquery('#' + id).loader('show',loaders);
            }
        }
        function chartloadingReloadSelect(id,flag){
            if(id=="mediaFriendlyChart"){
                var parent = $("."+id).parent();
                $("."+id).remove();
                if(flag=="1"){
                    var heigth = 277;
                }else{
                    var heigth = 600;
                }
                parent.html('<div class="'+id+'" style="width: 100%; height:'+heigth+'px;" class="chart_shuiyin"></div>');
                jquery('.' + id).loader('show', loaders);
            }else{
                var parent = $("#"+id).parent();
                $("#"+id).remove();
                if(flag=="1"){
                    if(id=="degreeHeatChart"){
                        var heigth = 320;
                    }else if(id=="container1"){
                        var heigth = 450;
                    }else if(id=="mediaSourceChart"){
                        var heigth = 320;
                    }else if(id=="container2"){
                        var heigth = 320;
                    }else if(id=="relevantChart"){
                        var heigth = 320;
                    }else if(id=="container4"){
                        var heigth = 450;
                    }else if(id=="container5"){
                        var heigth = 450;
                    }
                }else{
                    if(id=="container1"){//container2
                        var heigth = 450;
                    }if(id=="container2"){//
                        var heigth = 400;
                    }if(id==""){//
                        var heigth = 320;
                    }else{
                        var heigth = 500;
                    }
                }

                if(id=="container1"){//container2
                    var smg='';
                    smg+='<div class="abs fc_gray" style="bottom: 2px; right: 28px; z-index: 2;height: 7%;">&copy;${allTimeTe}';
                    smg+='</div>';
                    smg+='<div class="pading10 pading-left-25 pading-right-25 align_c">';
                    smg+='<span class="small fc_dark_grey"><i class="icon-square fc_gray_ot"></i> <span id="container1Name"></span></span>';
                    smg+='</div>';
                    smg+='<div class="chart-bady" id="container1" style="height: 450px;">';
                    smg+='</div>';
                    parent.html(smg);
                }else if(id=="container2"){
                    var smg='';
                    smg+='<div class="abs fc_gray" style="bottom: 10px; left: 10px; z-index: 2;">';
                    smg+='(注：图中词语字号越大表示该词语提及量越大，反之越小)';
                    smg+='</div>';
                    smg+='<div class="abs fc_gray" style="bottom: 10px; right: 28px; z-index: 2;height: 5%;">&copy;${allTimeTe}';
                    smg+='</div>';
                    smg+='<div class="pading10 pading-left-25 pading-right-25 align_c">';
                    smg+='<span class="small fc_dark_grey"><i class="icon-square fc_gray_ot"></i> <span id="containerName"></span></span>';
                    smg+='</div>';
                    smg+='<div id="'+id+'" style="height:'+heigth+';" class="chart-bady"></div>';
                    parent.html(smg);
                }else if(id=="relevantChart"){
                    var smg='';
                    smg+='<div class="abs fc_gray" style="bottom: 10px; right: 28px; z-index: 2;height: 5%;">&copy;${allTimeTe}';
                    smg+='</div>';
                    smg+='<div class="pading10 pading-left-25 pading-right-25 align_c">';
                    smg+='<span class="small fc_dark_grey"><i class="icon-square fc_gray_ot"></i> <span id="relevantName"></span></span>';
                    smg+='<div class="abs" style="top: 10px; left: 10px;">';
                    smg+='<span class="v_a_m fc_red">相关性：</span> <span class="v_a_m fc_gray">强</span> <i class="icon-dian fz18" style="color: rgba(231,145,107,0.3);"></i><i class="icon-dian fz18" style="color: rgba(231,145,107,0.1);"></i><i class="icon-dian fz18" style="color: rgba(231,145,107,0.07);"></i><span class="v_a_m fc_gray">弱</span>';
                    smg+='</div>';
                    smg+='</div>';
                    smg+='<div id="'+id+'" style="height:'+heigth+';" class="chart-bady"></div>';
                    parent.html(smg);
                }else if(id=="degreeHeatChart"){
                    var smg='';
                    smg+='<div class="abs fc_gray" style="bottom: 10px; right: 28px; z-index: 2;height: 5%;">&copy;';
                    smg+='${allTimeTe}';
                    smg+='</div>';
                    smg+='<div class="pading10 pading-left-25 pading-right-25 align_c">';
                    smg+='<span class="small fc_dark_grey"><i class="icon-square fc_gray_ot"></i> <span id="reDuName"></span></span>';
                    smg+='</div>';
                    smg+='<div class="chart-bady" id="degreeHeatChart" style="height: 320px;">';
                    smg+='</div>';
                    parent.html(smg);
                }else{
                    parent.html('<div id="'+id+'" style="width: 100%; height:'+heigth+'px;" class="chart_shuiyin"></div>');
                }
                jquery('#' + id).loader('show', loaders);
            }
        }

        function chartloadingTabCluster(id){
            var parent = $("#"+id).parent();
            $("#"+id).remove();
            if(id=="degreeHeatChart"){
                var heigth = 800;
            }else{
                var heigth = 400;
            }
            parent.html('<div class="abs fc_gray" style="bottom: 10px; right: 28px; z-index: 2;height: 5%;">&copy;<span class="time22"></span></div>');
            parent.html('<div id="'+id+'" style="width: 100%; height:'+heigth+'px;" class="chart_shuiyin"></div>');
//     $("#"+id).find("tr").remove();
//     $("#"+id).find("div").remove();
//     if(id == 'clusterList') {
            jquery('#' + id).loader('show', loaders);
//     }
        }

        function commafy(num){
            if((num.toString()).trim()==""){
                return"";
            }
            if(isNaN(num)){
                return"";
            }
            num = num.toString();
            if(/^.*\..*$/.test(num)){
                var pointIndex =num.lastIndexOf(".");
                var intPart = num.substring(0,pointIndex);
                var pointPart =num.substring(pointIndex+1,num.length);
                intPart = intPart +"";
                var re =/(-?\d+)(\d{3})/;
                while(re.test(intPart)){
                    intPart =intPart.replace(re,"$1,$2")
                }
                num = intPart+"."+pointPart;
            }else{
                num = num.toString();
                var re =/(-?\d+)(\d{3})/;
                while(re.test(num)){
                    num =num.replace(re,"$1,$2")
                }
            }
            return num;

        }
        function getParams(){
            var title = $("#title").val().trim();
            $("#container1Name").html(title);
            $("#containerName").html(title);
            $("#relevantName").html(title);
            $("#hotMessageName").html(title);
            if(title.length>10){
                $("#reduNamemore10").show();
                $("#reduNameless10").hide();
            }else{
                $("#reduNamemore10").hide();
                $("#reduNameless10").show();
            }
            $(".reduNameClass").html(title);
            var heat = location.href;
            var notCheck;
            if(location.href.indexOf("/heatPreview")!=-1){
                notCheck = "1";
            }
            var params = {
                'title' : $("#title").val(),
                'keyword' : $("#keyword").val(),
                'filterKeyword' : $("#filterKeyword").val(),
                'categoryId' : $("#categoryId").val(),
                'categoryType' : $("#categoryType").val(),
                'secondCategory' : $("#secondCategory").val(),
                'date' : "${date}",
                'categoryLevel' : $("#categoryLevel").val(),
                'startTime' : $("#starttime").val(),
                'endTime' : $("#endtime").val(),
                'secondClassifyName' : $("#secondClassifyName").val(),
                'threeClassifyName' : $("#threeClassifyName").val(),
                'isAll' : $("#isAll").val(),
                'shareCode' : '${shareCode}',
                'notCheck' : notCheck
            };
            return params;
        }

        function beginQuery(){
            chartloading("collapse5");//词云
//     	chartloading("degreeHeatChart");//热度值
//     	chartloading("collapse6");//关联度
            chartloadingTab("qiamwamgTuan");
            chartloadingTab("container1");
            chartloadingTab("redugaikuang");
            var heat = location.href;
            if(location.href.indexOf("/heatPreview")!=-1){
                //热门信息
                chartloadingTab("hotMessageDivId");
                goHotMessage();
//         	style="display: none;" id="displayHotMessage">
                $("#displayHotMessage").attr("style","");
            }
            //折现图
            goHotWorth();
            //关键词云   以前调接口的
            goRelatedChart();
            //关联度
// 		goAssociation();

        }

        function summaryandline(){

            var filterKeyword = $("#filterKeyword").val();
            var date = $("#date").val();
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();

            var date1 = $("#date").val();
            var title='${title}';
            var titleArr = title.split(",");
            var keyword= $("#keywords").val();
            var keywordArr = keyword.split(",");
            var shareCode = $("#shareCode").val();

            $.ajax({
                url : '${staticResourcePath}/shareReport/summaryandline1.action',
                type : "post",
                data :{"shareCode":$("#shareCode").val()},
                success : function(data){
                    if(data.msg){
                        EChartsDwr.getHotTableAndLine3(2,titleArr[0],data.dataLine,"2",worthCallback);
                    }else{
                        goHotWorth(keywordArr[0],date,titleArr[0],filterKeyword,starttime,endtime,"2");
                    }
                }
            });
        }
        var wordAndRelData;
        function wordandrelated(){
            var title='${title}';
            var tit=$("#title").val();
            $("#containerName").html(tit);
            var titleArr = title.split(",");
            var shareCode = $("#shareCode").val();
            $.ajax({
                url : '${staticResourcePath}/shareReport/wordandrelated1.action',
                type : "post",
                data :{"shareCode":$("#shareCode").val()},
                success : function(data){
                    wordAndRelData=data;
                    if(data.msg){
                        EChartsDwr.getHotWordRelatedChart3(1,titleArr[0],data.dataWordRel,relatedCallBack);
                    }else{
                        var dd=[null,null,"1",titleArr[0]];
                        relatedCallBack(dd);
                    }
                }
            });
        }
        function relatedTwo(){
            var tit=$("#title").val();
            $("#relevantName").html(tit);
            var title='${title}';
            var titleArr = title.split(",");
            var shareCode = $("#shareCode").val();
            $.ajax({
                url : '${staticResourcePath}/shareReport/relatedTwo1.action',
                type : "post",
                data :{"shareCode":$("#shareCode").val()},
                success : function(data){
                    if(data.msg){
                        EChartsDwr.relatedTwo3(1,titleArr[0],data.dataLine,goAssociationCallBack);
                    }else{
                        var dd=[null,null,titleArr[0],"1"];
                        goAssociationCallBack(dd);
                    }
                }
            });
        }
        function hotMessageSolidify(){
            var tit=$("#title").val();
            $("#hotMessageName").html(tit);
            var title='${title}';
            var titleArr = title.split(",");
            var shareCode = $("#shareCode").val();
            $.ajax({
                url : '${staticResourcePath}/shareReport/hotMessageSolidify1.action',
                type : "post",
                data :{"shareCode":$("#shareCode").val()},
                success : function(data){
                    if(data.msg){
                        EChartsDwr.hotMessageSolidify2(1,titleArr[0],data.dataLine,goHotMessageCallBack);
                    }else{
                        goHotMessageCallBack(null);
                    }
                }
            });
        }
        $(function(){
            $("[data-toggle='tooltip']").tooltip();//全局提示效果

            $(".time22").text('${allTimeTe}');
            beginQuery();
        });

        var yearWordData;
        function yearWordCloud(shareCode){
            $("#containerName").html($("#title").val());
            $.ajax({
                url : '${staticResourcePath}/shareReport/yearHotWordCloud.action',
                type : "post",
                data :{"shareCode":shareCode},
                success : function(data){
                    yearWordData=data;
                    if(data.msg){
                        sendChartPost("${staticResourcePath}/hot/opentools/goMoreWordsOTChart", getParams(data.dataYearWordCloud), worthCallback, "");
//                     HeatReportDwr.getHotYearWordCloudChart(data.dataYearWordCloud,$("#title").val(),GetRandom(30),yearWordCloudCallBack);
                    }else{
                        var c2 = document.getElementById("container2");
                        c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                    }
                }
            });
        }

        //热度指数趋势
        var yearSummaryData;
        function yearSummaryandline(shareCode){
            $("#reDuName").html($("#title").val());
            $("#reDuName2").html($("#title").val());
            $("#container1Name").html($("#title").val());
            $.ajax({
                url : '${staticResourcePath}/shareReport/yearHotStatAndLine.action',
                type : "post",
                data :{"shareCode":shareCode},
                success : function(data){
                    yearSummaryData=data;
                    if(data.msg){
                        sendChartPost("${staticResourcePath}/hot/opentools/yearGoHotWorth.action", getParams(data.dataYearLine), yearWorthCallback, "");
//                     HeatReportDwr.getHotYearSummaryAndlineChart(data.dataYearLine,$("#title").val(),GetRandom(30),yearWorthCallback);
                    }else{
                        var c1 = document.getElementsByClassName("counter");
                        var c2 = document.getElementById("container1");
                        var c3 = document.getElementById("degreeHeatChart");
                        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                        c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";

                    }
                }
            });
        }

        function yearWorthCallback(data){
            if ((data.length==1&&data.jsons == null) || (data.length==1&&data.jsons == "")) {
                $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:115px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                $(".counter").html("<div align=\"center\" style=\"padding-top:0px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
            } else {
                var html="";
                var names=$("#title").val();
                var name="";
                var dataTable=data.jsons;
                if(dataTable) {
                    for (var i = 0; i < dataTable.length; i++) {
                        var obj = eval('(' + dataTable[i] + ')');
                        if (obj != null || obj != "") {
                            name = names.split(",")[i];
                            if (name.length > 10) {
                                name = name.substring(0, 10) + "...";
                            }
                            var cus = "";
                            if (obj.ratioHotCustom < 0.01) {
                                cus = "< 0.01";
                            } else {
                                cus = obj.ratioHotCustom;
                            }

                            html += "<tr><td>";
                            if (i == 0) {
                                html += "<font style='color:#3FAD7E'>";
                                $("#hotCus").val(cus);
                            } else if (i == 1) {
                                html += "<font style='color:#758DC4'>";
                            } else if (i == 2) {
                                html += "<font style='color:#0e7dc0'>";
                            }

                            html += name + "</font></td><td>" + cus + "</td>";
                            html += "<td>" + obj.weiboNum+"</td>";
                            html += "<td>" + obj.weixinNum+"</td>";
                            html += "<td>" + obj.numberCustom + "</td>";
                            html += "</tr>";
                        }
                    }
                    $("#qwxxlID").html(commafy(obj.numberCustom)+"");
                    //热度值
                    degreeHeatChartFun("degreeHeatChart",obj.ratioHotCustom+"");

                }else{
                    $("#container1").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $("#degreeHeatChart").html("<div align=\"center\" style=\"padding-top:50px;margin-top: 90px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                    $(".counter").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                }
            }

            var c1 = document.getElementById("container1");
            var dataLine = eval(data.jsonLine);
            if (data.clusterCount.length<1 || dataLine == null || dataLine == "") {
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                return false;
            } else {
                if (dataLine[0].data == null || dataLine[0].data.length == 0) {
                    c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"${staticResourcePath }/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";

                } else {
                    var data1 = dataLine[0];
                    var alertMsg = "从指数变化趋势来看，";
                    for (var i = 0; i < data1.data.length; i++) {
                        var listData = data1.data;
                        var subMsg = listData[i].name;
                        var topTime = listData[i]["label"].name;
                        topTime = topTime.split(":")[0];
                        topTime=topTime.replace("-","月");
                        topTime=topTime.replace("-","日 ");
                        var topValue = listData[i]["label"].value;
                        if(i==0){
                            alertMsg += "<font style='color:#3FAD7E'>";
                        }
                        if(i==1){
                            alertMsg += "<font style='color:#758DC4'>";
                        }
                        if(i==2){
                            alertMsg += "<font style='color:#0e7dc0'>";
                        }
                        alertMsg += subMsg + '</font>的热度在' + topTime + '达到了<font class="f_c2">' + topValue + '</font>的峰值。';
                    }
                    var areaSty= {
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
                    };
                    dataLine[0].data.jsons["areaStyle"]=areaSty;
                    $("#container1_msg").html(alertMsg);
                    var _chartColumn1 = LineChartHeatNetwork(dataLine,"container1",2,data.listDouble);
                }
            }
        }

    </script>
