var params = {"analysisTaskTicket":$("#analsisTask").val(),"reportTime":$("#reportTime").val(),"createTime":$("#createTime").val()};
function actionRunReviewAnalysis(){
    $(".loadingMask").show();
    /*actionRun();*/
    var timeid=setInterval(actionRun,5000);
    $("#clearInterval").val(timeid);
}

var ticket=$("#analsisTask").val();
var newUrl = $("#fromLink").val();
var loaders = '<i class="icon-reload-a fa-spin"></i>';
//初始化ajax
function actionRun(){
    var timeId=$("#clearInterval").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/findReviewAnalysisStatus.action",
        type:"POST",
        data:{"analysisTaskTicket":ticket},
        success:function (data) {
            if(data != null){
                var struts=data[0].analysisSolrStatus;
                $("#reportTime").val(data[0].createTime);
                if(struts==1||struts==0){
                    clearInterval(timeId);
                    var html='<span class="waiting"><a class="closeBtn" href="javascript:;" onclick="javascript:location.href = '+"'"+actionBase+"/view/reviewAnalysis/history.shtml"+"'"+';" >×</a>您还未支付分析费用<br>可前往分析记录中完成支付哦~^0^~</span>';
                    $("#loadingMaskalert").html(html);
                }else if(struts==5){
                    clearInterval(timeId);
                    updateReview(data[0].analysisTaskTicket,1);
                    $(".loadingMask").hide();
                    if(0 == data[0].participateNumber){
                    	var demo=document.getElementById('com_num_pie');
                        var reportTime = $("#reportTime").val();
                        var weiboNewURL = $("#fromLink").val();
                        $.ajax({
                            url:actionBase+"/nra/reviewAnalysis/comNumPie.action",
                            type:'POST',
                            data:{"analysisTaskTicket":ticket,"reportTime":reportTime,"weiboNewURL":weiboNewURL},
                            beforeSend:function(){
                                $("#commentNumber").loader('show',loaders);
                            },
                            success:function (data) {
                                $("#commentNumber").loader('hide');
                                if(data!=null){
                                	$("#participateNumber").text(data[0].value+data[1].value+"人");
                                }else{
                                	$("#participateNumber").text("0人");
                                }
                            }
                        })
                    }else{
                    	$("#participateNumber").text(data[0].participateNumber+"人");
                    }
                    
                    $("#webname").text(data[0].webname);
//                    $("#published").text(data[0].published.substring(0,16));
                    console.log("+++++++++++++++++ "+data[0].published)
                    $("#published").text(new Date(data[0]["published"]).format('yyyy-MM-dd hh:mm:ss'));
                    comnumPieAction();
                    sensitivePieAction();
                    wordCloudAction();
                    reviewTimeAction();
                    reviewAreaAction();
                    forwordAreaAction();
                    reviewAnddianzanAction();
                    faceAnalysisChartAction();
                    radarChartAction();
                }else if(struts != 5){
                    if(data[0].status == 0){
                        clearInterval(timeId);
                        var html='<span class="waiting"><a class="closeBtn" href="javascript:;" onclick="javascript:location.href = '+"'"+actionBase+"/view/reviewAnalysis/history.shtml"+"'"+';" >×</a>分析失败<br>可前往分析记录重新分析哦~^0^~</span>';
                        $("#loadingMaskalert").html(html);
                        showMsgInfo(0, "分析失败,是否重新分析？", 1);
                        updateReview(data[0].analysisTaskTicket,2);
                        $("#submitBtn").one("click",function(){
                            goretryAnalysis($("#reviewAnalysisId").val(), $.trim($('#fromLink').val()), true);
                        });
                    }
                }
            }
        }
    })
}
//无信息时

//评论点赞--ajax
function comnumPieAction(){
    var demo=document.getElementById('com_num_pie');
    var reportTime = $("#reportTime").val();
    var weiboNewURL = $("#fromLink").val();
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/comNumPie.action",
        type:'POST',
        data:params,
        beforeSend:function(){
            $("#commentNumber").loader('show',loaders);
        },
        success:function (data) {
            $("#commentNumber").loader('hide');
            if(data!=null){
               $("#commentNumber_fz16").text(data[0].value+"条");
                $("#spotFabulous_fz16").text(data[1].value+"个");
                commumPie(data,demo);
            }else{
                $("#commentNumber_fz16").text("0条");
                $("#spotFabulous_fz16").text("0个");
                $('#com_num_pie').html('<div style="height: 42%;text-align: center;"><img src="'+actionBase+'/images/shouye/warn.png" style="margin: 40px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无关键词信息</span></div>');
            }
        }
    })
}

//评论点赞--饼状图
function commumPie(data,demo){
    var comnumPie = echarts.init(demo);
    $.each(data,function(i,n){
        if(i==0){
            this.itemStyle={'normal':{'color': "#feaf53" }};
        }else{
            this.itemStyle={'normal':{'color': "#3fad7e" }};
            this.label={'normal': {'textStyle': {'color': '#333','fontSize': 15}}};
        }
    });
    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },

        series: [{
            type: 'pie',
            radius: ['66%', '86%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show:false,
                    position: 'center'
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '15',
                        fontWeight: 'bold'
                    },
                    formatter: '{b}\n{d}%'
                }
            },
            data: data
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    comnumPie.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            comnumPie.resize();
        }
    });
}


//敏感占比--ajax
function sensitivePieAction(){
    var demo=document.getElementById('sen_con_pie');
    var reportTime = $("#reportTime").val();
    var weiboNewURL = $("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/seConPie.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#ConPie").loader('show',loaders);
        },
        success:function(data){
            $("#ConPie").loader('hide');
            if(data!=null){
              sensitivePie(eval('(' + data + ')'),demo);
            }else {
                $("#sen_con_pie").html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin: 28px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无信息</span>');
            }
        }
    })
}

//敏感占比--仪表图
function sensitivePie(data,demo){
    var width="70";
    if ($("#minganId").find(".full").find("i").hasClass("icon-full")) {
        $("#ConPie").css("height", "200px");
        $("#ConPie").find(".chart-bady").css("height", "160px");
    } else {
        $("#ConPie").css("height", window.innerHeight + "px");
        $("#ConPie").find(".chart-bady").css("height", window.innerHeight - 40 + "px");
        width='300';
    }
    var sensitivePie = echarts.init(demo);

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            formatter: "{a}{b} : {c}%"
        },
        series: [{
            name: '敏感占比',
            type: 'gauge',
            radius: '100%',
            center: ["50%", "50%"], //整体的位置设置
            startAngle: 180, //总的360，设置180就是半圆
            endAngle: 0,
            axisLine: {
                lineStyle: {
                    width: width, //柱子的宽度
                    color: [
                        [0.2, '#feaf53'],
                        [0.8, '#f18d00'],
                        [1, '#db421b']
                    ]
                }
            },
            axisLabel: { // 坐标轴文本标签，详见axis.axisLabel
                formatter: function(v) {
                    switch(v + '') {
                        case '10':
                            return '低';
                        case '50':
                            return '中';
                        case '90':
                            return '高';
                        default:
                            return '';
                    }
                },
                textStyle: { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                    color: '#fff',
                    fontSize: 18
                }
            },
            axisTick: { show: false },
            splitLine: { show: false },
            pointer: {
                width: 25, //指针的宽度
                length: "80%" //指针长度，按照半圆半径的百分比
            },
            itemStyle: {
                normal: {
                    color: '#fff',
                    opacity: 0.4
                }
            },
            detail: { formatter: function (value) {
                return value.toFixed(2)+"%";
            }, offsetCenter: [0, '0%'], textStyle: { color: '#333', fontSize: 25 } },
            data:data
        }]
    };

    setInterval(function() {
        option.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
        /*myChart.setOption(option, true);*/
    }, 2000);

    // 使用刚指定的配置项和数据显示图表。
    sensitivePie.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            sensitivePie.resize();
        }
    });
}

//敏感刷新
$("#minganId").find(".full").click(function() {
    sensitivePieAction();
});


//评论关键词--ajax
function wordCloudAction(){
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var reportTime = $("#reportTime").val();
    var errorHtml = '<div style="color: #d5d3cf;font-size: 14px;margin-top: 120px;height: 23%;"><p><img src="'+actionBase+'/images/shouye/warn.png"></p><p>暂无关键词信息</p></div>';
    var weiboNewURL = $("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/wordCloud.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse3").find(".panel-body").loader('show',loaders);
        },
        success:function (data) {
            var html = '';
            if(data!=null){
                $("#collapse3").find(".panel-body").loader('hide');
                if (data.length>0) {
                    var length=(data.length)%4 == 0 ? (data.length)/4 : parseInt(data.length/4)+1;
                    for (var i = 0; i < length; i++) {
                        if(i==6){
                            break;
                        }
                        if (i == 0) {
                            html += '<div class="row clearfix">';
                        } else {
                            html += '<div class="row clearfix mt10">';
                        }

                        for (var x = 0; x < 4; x++) {
                            if(parseInt(x) + parseInt(4 * i) < data.length){
                                if (data[parseInt(x) + parseInt(4 * i)].name != "") {
                                    html += '<div class="col-md-3">';
                                    html += '<a class="btn btn-block btn-primary" data-toggle="tooltip" title="'+data[x + 4 * i].num+'">' + data[x + 4 * i].name + '</a>';
                                    html += '</div>';
                                }
                            }
                        }
                        html += '</div>';
                    }

                    $("#wordclode").html(html);
                    $("[data-toggle='tooltip']").tooltip();
                }else{
                    $("#collapse3").find(".panel-body").html(errorHtml);
                }

            }else{
                $("#collapse3").find(".panel-body").html(errorHtml);
            }

        }
    })

}


//时间走势--ajax
function reviewTimeAction() {
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var demo=document.getElementById('reviewTime');
    var reportTime = $("#reportTime").val();
    var weiboNewURL = $("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/reviewTime.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse4").find(".panel-body").loader('show',loaders);
        },
        success:function (result) {
            $("#collapse4").find(".panel-body").loader('hide');
            if(result!=null){
                reviewTimeLine(result,demo);
            }else{
                $("#reviewTime").html('<div style="color: #d5d3cf;font-size: 14px;margin-top: 120px;"><p><img src="'+actionBase+'/images/shouye/warn.png"></p><p>暂无时间走势信息</p></div>');
            }
        }
    })
}

//时间走势--折线图
function reviewTimeLine(data,demo){
    var reviewTime = echarts.init(demo);
    option = {
        tooltip : {
            trigger: 'axis',
            backgroundColor: 'rgba(255,255,255,0.8)',
            borderColor: 'rgba(236,97,0,0.9)',
            borderWidth: 1,
            axisPointer:{type:'none'},
            formatter: function(params) {
                var res ='';
                res +='<div style="color: #333;">';
                res +='<span>'+params[0].name+'</span><br>';
                 res +='<span>'+params[1].seriesName+':'+params[1].value+'</span><br>';
                res +='<span>'+params[0].seriesName+':'+params[0].value+'</span>';
                return res
            }
        },

        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : data[0]
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'评论数',
                type:'line',
                areaStyle: {
                    normal: {
                        color: '#f9d2c4'
                    }
                },
                lineStyle:{
                    normal:{
                        color:'#ec6a3a'
                    }
                },
                data:data[1]
            },
            {
                name:'点赞数',
                type:'line',
                label: {
                    normal: {
                        show: true,
                        position: 'top'
                    }
                },
                lineStyle:{
                    normal:{
                        color:'#277bc0'
                    }
                },
                areaStyle: {normal: {color:'#bed7ec'}},
                data:data[2]
            },
            {
                name: 'nick',
                type: 'scatter',
                itemStyle: {
                    normal: {
                        opacity: 0.5,
                        shadowBlur: 2,
                        shadowOffsetX: 0,
                        shadowOffsetY: 0,
                        shadowColor: '#f19302',
                        color:'#f29300'
                    }
                },
                symbolSize:20
            }
        ]
    };
    if(data[3] != null && data[3].length > 0){
        option.series[2].tooltip = { // Series config.
            trigger: 'item',
                backgroundColor: 'rgba(255,255,255,0.8)',
                borderColor: 'rgba(242,147,0,1)',
                borderWidth: 1,
                formatter: function(params) {
                var res ='';
                /* res += '<span style="color: #333;">时间:'+data[3][0][params.dataIndex][0]+'<br>';*/
                res += '<div style="color: #333; text-align: left;">作者:'+data[3][1][params.dataIndex]+' <br>';
                res +='内容:'+data[3][0][params.dataIndex][2]+' <br>';
                res += '点赞数:'+data[3][0][params.dataIndex][1]+'</div>';

                return res
            }
        }
        option.series[2].data = data[3][0].map(function (item, index) {
            item = item.slice();
            item[0] = item[0];
            return item;
        })
    }
    reviewTime.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            reviewTime.resize();
        }
    });
}


//评论地域--初始化
function reviewAreaAction(){
    var color=new Array();
    color.push('#bde3d3');
    color.push('#3fad7e');
    reviewForwordArea(actionBase+"/nra/reviewAnalysis/reviewArea.action",'reviewersArea','5',color);
}

//评论地域--全屏
$("#review_4").find(".full").click(function() {
    if ($("#review_4").find(".full").find("i").hasClass("icon-full")) {
        $("#collapse_5").css("height", window.innerHeight + "px");
        $("#collapse_5").find(".chart-bady").css("height", window.innerHeight - 40 + "px");
    } else {
        $("#collapse_5").css("height", "310px");
        $("#collapse_5").find(".chart-bady").css("height", "280px");
    }
    reviewAreaAction();
})


//转发地域--初始化
function forwordAreaAction(){
    var color=new Array();
    color.push('#ffe4c4');
    color.push('#feaf53');
    reviewForwordArea(actionBase+"/nra/reviewAnalysis/reviewForwardArea.action",'reviewersArea2','6',color);
}

//转发地域--全屏
$("#review_5").find(".full").click(function() {
    if ($("#review_5").find(".full").find("i").hasClass("icon-full")) {
        $("#collapse_6").css("height", window.innerHeight + "px");
        $("#collapse_6").find(".chart-bady").css("height", window.innerHeight - 40 + "px");
    } else {
        $("#collapse_6").css("height", "310px");
        $("#collapse_6").find(".chart-bady").css("height", "280px");
    }
    forwordAreaAction();
})


//评论/转发--地域--ajax
function reviewForwordArea(url,demo,loaderDemo,color){
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var reviewArea2=document.getElementById(demo);
    var reportTime = $("#reportTime").val();
    var weiboNewURL = $("#fromLink").val();
    $.ajax({
        url:url,
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse_"+loaderDemo).find(".panel-body").loader('show',loaders);
        },
        success:function (result) {
            $("#collapse_"+loaderDemo).find(".panel-body").loader('hide');
            if(result!=null){
                var myArray=new Array();
                var myArray2=new Array();
                var myArray3=new Array();
                $.each(result, function(i, n) {
                   if(n.num!=0){
                        myArray.push(n.name);
                        myArray2.push(n.num);
                    }

                });
                if(myArray2[0]==0 || myArray2.length==0){
                    if(loaderDemo=="5"){
                        $("#collapse_"+loaderDemo).html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论者地域</span>');
                    }else{
                        $("#collapse_"+loaderDemo).html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无转发评论者地域</span>');
                    }

                }else{
                    myArray3.push(myArray);
                    myArray3.push(myArray2);
                    reviewersAreaBar(myArray3,reviewArea2,color);
                    var html="";
                    $.each(result, function(i, n) {
                        if (i > 4)
                            return false;

                        if(n.percentStr!="0%")
                            html+="<tr><td>"+(i+1)+"</td><td>"+n.name+"</td><td>"+n.percentStr+"</td></tr>";
                    })
                    $("#map_table_"+loaderDemo).find("tbody").html(html);
                }
            }else{
                if(loaderDemo=="5"){
                    $("#collapse_"+loaderDemo).html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论者地域</span>');
                }else{
                    $("#collapse_"+loaderDemo).html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无转发评论者地域</span>');
                }

            }
        }
    })
}

//评论/转发--柱状图
function reviewersAreaBar(data,demo,color) {
    var reviewersArea = echarts.init(demo);

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'item',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '0%',
            right: '0%',
            top: '3%',
            bottom: '5%',
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
                    rotate: 20,  //旋转角度
                    interval:0,
                    textStyle: {
                        color: '#888'
                    }
                },

                boundaryGap: true,
                data: data[0]
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

                type: 'bar',
                barWidth: '20',
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(
                            0, 1, 0, 0, [{
                                offset: 0,
                                color: color[0]
                            }, {
                                offset: 1,
                                color: color[1]
                            }]
                        ),
                        barBorderRadius: 0
                    }
                },
                data: data[1]
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    reviewersArea.setOption(option);
    setTimeout(function (){
        window.onresize = function () {
            reviewersArea.resize();
        }
    });
}

//评论/转发--全屏
$("#review_6").find(".full").click(function() {
    if ($("#review_6").find(".full").find("i").hasClass("icon-full")) {
        $("#collapse4").css("height", window.innerHeight + "px");
        $("#collapse4").find(".chart-bady").css("height", window.innerHeight - 40 + "px");
    } else {
        $("#collapse4").css("height", "280px");
        $("#collapse4").find(".chart-bady").css("height", "250px");
    }
    reviewTimeAction();
})


//主要观点代表--ajax
function reviewAnddianzanAction() {
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var reportTime = $("#reportTime").val();
    var weiboNewURL=$("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/reviewAnddianzan.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse8").find(".panel-body").loader('show',loaders);
        },
        success:function (data) {
            $("#collapse8").find(".panel-body").loader('hide');
            var html = "";
            if (data != null) {
                if (data.length>0) {
                    $.each(data, function (i, n) {
                        if ($.trim(n.content) == "" || $.trim(n.content) == null){
                            return true;
                        }
                        if(i>7){
                            html += '<tr><td>' + n.author + '</td><td data-trigger="hover" data-html="true" data-placement="top" data-container="body"  data-toggle="popover" data-content="'+n.content+'"><span style="height: 22px; overflow: hidden; display:block;">' + n.content + '</span></td><td>' + n.praiseNum + '</td></tr>';
                        }else{
                            html += '<tr><td>' + n.author + '</td><td data-trigger="hover" data-html="true" data-placement="bottom" data-container="body"  data-toggle="popover" data-content="'+n.content+'"><span style="height: 22px; overflow: hidden; display:block;">' + n.content + '</span></td><td>' + n.praiseNum + '</td></tr>';
                        }
                    });
                    $("[data-toggle='popover']").popover();
                    $("#reviewAndDianzan").html(html);
                    $("[data-toggle='popover']").popover();
                }else {
                    $("#collapse8").find(".panel-body").html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无主要观点代表</span>');
                }
            }else{
                $("#collapse8").find(".panel-body").html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无主要观点代表</span>');
            }
        }
    })

}


//表情--ajax
function faceAnalysisChartAction(){
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var demo=document.getElementById('faceAnalysisChart');
    var reportTime = $("#reportTime").val();
    var faceIndex=$("#faceIndex").val();
    var weiboNewURL=$("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/facereview.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse7").find(".panel-body").loader('show',loaders);
        },
        success:function (result) {
            if (result != null) {
                var myArray = new Array();
                var arr= new Array();
                $("#collapse7").find(".panel-body").loader('hide');
                var html = "";
                $.each(result.faceList, function (i, n) {

                    if(n.faceUrl==null||n.faceUrl==""){
                        /*html += '<li style="margin: 8px 0;">'+n.faceName+'</li> ';*/

                    }else{
                        var index1=n.faceName.indexOf("[")+1;
                        var index2=n.faceName.indexOf("]");
                        arr.push(n.faceName.substring(index1,index2));
                        html += '<li style="margin: 8px 0;"><img src="' + n.faceUrl + '" border="0" title="' + n.faceName + '" alt="' + n.faceName + '" class="img_bq"></li> ';
                        myArray.push(n.faceCount[0]);
                    }
                });
                if(myArray.length>0){
                    $(".resultFaceOne").find("ul").html(html);
                    faceHeight(myArray.length);

                    faceAnalysisChart(myArray.reverse(),arr.reverse(), demo);
                }else{
                        $("#collapse7").find(".panel-body").html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论者表情</span>');
                }
            }else{
                $("#collapse7").find(".panel-body").html('<img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论者表情</span>');
            }
        }
    })
}

//表情高度
function faceHeight(length){
    if(length<5){
        $(".resultFaceOne").css("left","30px");
        $("#faceAnalysisChart").css("height",60+30*length);
        $("#faceAnalysisChart").css("margin-top","100px");
        $(".resultFaceOne").css("margin-top","100px");

    }else{
        $(".resultFaceOne").find("ul").find("li").eq(0).css("padding-top","6px");
        $("#faceAnalysisChart").css("height",80+30*length);
    }
}

//表情全屏
$("#faceId").find(".full").click(function(){
    faceAnalysisChartAction();
});

//表情横向--柱状图
function faceAnalysisChart(data,data2,demo) {
    var faceAnalysisChart = echarts.init(demo);

    // 指定图表的配置项和数据
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (param) {
                return data2[param[0].dataIndex]+':'+param[0].value;
            }
        },

        grid: {
            top: '5%',
            left: '3%',
            right: '10%',
            bottom: '10%',
            containLabel: true
        },

        xAxis: [
            {
                type: 'value',
                axisLine: {//坐标轴轴线 默认 true,
                    show: true,
                    lineStyle: {
                        color: '#dbdbdb',
                        width: 1,
                        type: 'solid'
                    }
                },
                /*minInterval:1,*/
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
                    },
                }
            }
        ],
        yAxis: [
            {
                type: 'category',
                interval:1,
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
                data: ['', '']
            }
        ],
        series: [
            {
                name: '',
                type: 'bar',
                barWidth: '20',
                itemStyle: {
                    normal: {
                        color: '#3fad7e'
                    }
                },
                data: data
            }
        ]
    };
    $("#faceId").find(".full").click(function(){
        /*faceAnalysisChart();*/
            window.onresize = function () {
                faceAnalysisChart.resize();
            }
    });
    // 使用刚指定的配置项和数据显示图表。
    faceAnalysisChart.setOption(option);

}


//评论观点聚类--ajax
function  radarChartAction() {
    //ticket = ticket.replace("\\","").replace("/","").replace(":","").replace("*","").replace("?","").replace("\"","").replace("<","").replace(">","").replace("|","");
    var reportTime = $("#reportTime").val();
    var weiboNewURL=$("#fromLink").val();
    $.ajax({
        url:actionBase+"/nra/reviewAnalysis/radarChart.action",
        type:"POST",
        data:params,
        beforeSend:function(){
            $("#collapse9").find(".panel-body").loader('show',loaders);
        },
        success:function (data) {
            $("#collapse9").find(".panel-body").loader('hide');
            if (data!=null) {
                if((typeof data=='string')&&data.constructor==String){
                    $("#collapse9").html('<div style="height: 50%;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论观点聚类</span></div>');
                }else{
                    radarChart(data[0]);
                    var html = '';
                    $.each(data[1], function (i, n) {
                        if (i>7){
                            html += '<tr><td>' + (i + 1) + '</td><td data-trigger="hover" data-html="true" data-placement="top" data-container="body"  data-toggle="popover" data-content="'+n.name+'"><span>' + n.name + '</span></td><td>' + n.percentStr + '</td></tr>';
                        }else{
                            html += '<tr><td>' + (i + 1) + '</td><td data-trigger="hover" data-html="true" data-placement="bottom" data-container="body"  data-toggle="popover" data-content="'+n.name+'"><span>' + n.name + '</span></td><td>' + n.percentStr + '</td></tr>';
                        }
                    });
                    $("[data-toggle='popover']").popover();

                    $("#reviewPointTbody").html(html);
                    $("[data-toggle='popover']").popover();
                }
            }else{
                $("#collapse9").html('<div style="height: 50%;"><img src="'+actionBase+'/images/shouye/warn.png" style="width:60px; margin-top: 120px;"><br><span style="color: #d5d3cf;font-size: 14px;">暂无评论观点聚类</span></div>');
            }
        }
    })
}

 //评论观点聚类--饼状图
function radarChart(data) {

    var radarChart = echarts.init(document.getElementById('radarChart'));
    var dataStyle = {
        normal: {
            label: {show:false},
            labelLine: {show:false}
        }
    };
    var placeHolderStyle = {
        normal : {
            color: '#E8E8E8',
            label: {show:false},
            labelLine: {show:false}
        },
        emphasis : {
            color: '#E8E8E8'
        }
    };
    var ra=150;
    var rad=175;
    var legendData=new Array();normal : {color: ''}
    var styleColor=["#39ae7d","#5573b6","#f09566","#95d2d9","#0086ce","#9db3dc"];
    $.each(data,function (i,n) {
        var colorStyle = {
            normal : {
                color: styleColor[i]
            }
        };
        this.clockWise=false;
        var radius= new Array();
        radius.push(ra);
        radius.push(rad);
        this.radius=radius;
        this.itemStyle =dataStyle;
        this.data[0].itemStyle=colorStyle;
        this.data[1].itemStyle=placeHolderStyle;
        ra=ra-25;
        rad=rad-25;
        var  name = this.data[0].name;
        if(name.length>20){
            this.data[0].name = name.substring(0,20)+"...";
        }else{
            this.data[0].name = name;
        }
        legendData.push(this.data[0].name);
    })
    // 指定图表的配置项和数据

    option = {
        title: {
            text: '',
            x: 'center',
            y: 'center',
            itemGap: 20
        },
        tooltip : {
            show: true,
            formatter: "评论观点聚类<br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient : 'vertical',
            x : document.getElementById('radarChart').offsetWidth / 2,
            y : 45,
            itemGap:0,
            data:legendData
        },
        series : data
    };

    // 使用刚指定的配置项和数据显示图表。
    radarChart.setOption(option);
    setTimeout(function() {
        window.onresize = function() {
            radarChart.resize();
        }

    });
}


