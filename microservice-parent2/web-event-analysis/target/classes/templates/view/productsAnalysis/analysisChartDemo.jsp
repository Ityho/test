<%@ page language="java" pageEncoding="gbk"%><% request.setAttribute("page_title",""); %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/view/productsAnalysis/top.jsp"%>
<html>
<head>
<!-- saved from url=(0014)index:internet -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<meta charset="GBK">
<title>${title}�ĶԱȷ��������������ע��TA�ж�ţ��-΢�ȵ�</title>
<META name="keywords" content="΢�ȵ�_������Ϣ���">
<META name="description" content="����΢�ȵ����й�����΢���ȵ����۷���ƽ̨���ṩ��ҳ��΢����΢�ŵ�ȫý���ȵ���Ϣ���Լ�������Ϣ,�����¼�����,��Ϣ����,��Ȯ����, �ȶ��¼���������,��Ϣ���, ��Ϣ������,����ý����,�����鱨�����">
<link href="<%=staticResourcePathH5 %>/css/productsAnalysis/style.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet">
<link href="<%=njxBasePath %>/css/newcommon.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet">
<link href="<%=staticResourcePathH5 %>/css/productsAnalysis/common.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet">
<link href="<%=staticResourcePathH5 %>/css/productsAnalysis/productsAnalysis.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet">
<link href="<%=staticResourcePathH5 %>/css/productsAnalysis/tips.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet">
<link href="<%=staticResourcePathH5 %>/css/productsAnalysis/zzsc.css?v=<%=SYSTEM_INIT_TIME %>" rel="stylesheet"><!--��������ʽ-->

<script src="<%=staticResourcePathH5 %>/js/productsAnalysis/jquery-1.10.2.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5 %>/js/productsAnalysis/index.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5 %>/js/productsAnalysis/jquery.JPlaceholder.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5 %>/js/productsAnalysis/zzsc.js?v=<%=SYSTEM_INIT_TIME %>"></script><!--������JS-->
<script src="<%=staticResourcePathH5 %>/js/echarts/echarts3.js?v=<%=SYSTEM_INIT_TIME %>" charset="utf-8"></script>
<script src="<%=staticResourcePathH5 %>/js/echarts/dist/wordcloud.min.js?v=<%=SYSTEM_INIT_TIME %>"></script>
<script src="<%=staticResourcePathH5 %>/js/echarts/dist/map/china.js?v=<%=SYSTEM_INIT_TIME %>" charset="utf-8"></script>

<%@ include file="../commonJsp/dwr.ftl" %>
<script type='text/javascript' src='<%=njxBasePath %>/dwr/interface/EChartsDwr.js'></script>
<%-- <%@ include file="../commonJsp/echarts.jsp" %> --%>
<script type='text/javascript' src='<%=njxBasePath%>/dwr/interface/NewsOperate.js'></script>
<script type='text/javascript' src='<%=njxBasePath %>/dwr/interface/ProductsAnalysisDwr.js'></script>

<!-- app����ʱ�ṩͼƬ -->
<img src='<%=njxBasePath %>/images/fenxiang/wxfx-pic2.jpg' style="width:0px; height:0px; overflow:hidden;" />
<!--[if lt IE 10]>
<script src="PIE.js"></script>
<![endif]-->
    <%
        String shareCode = request.getParameter("shareCode");
    %>

    <script language="javascript">
        href=window.location.href;
        //�����
function GetRandom(n){
	GetRandomn=Math.floor(Math.random()*n+1);
	return GetRandomn;
}

$(function() {
     //���ѡ��Ч��
  /*  $("#time-group>li").on("click",function(){
        $("#time-group>li").removeClass("selected");
        $(this).addClass("selected");
    }); */
    var params = {"analysisTaskTickets":"${pab.ticket}","createTime":"${pab.createStr}",
            'starttime':"${pab.startStr}",'endtime':"${pab.endStr}",
            'userId':"${pab.userId}",'productsAnalysisId':"${pab.productsAnalysisId}",
            'pabId':"${pab.id}"};
    var sign=$("#sign").val();
  	//���ѡ��Ч��
    var shareCode ='<%=request.getParameter("shareCode")%>';
    var userId=$("#userId").val();
    $("#shareCode").val(shareCode);
    if(sign==1){
         $("#sign").val(2);
         if( document.querySelector(".fenxiang"))
         document.querySelector(".fenxiang").style.display="none";
         if( document.querySelector(".fenxiang2"))
         document.querySelector(".fenxiang2").style.display="none";
         goLineChart();	//����ͼ
         goLineChartNegative();//��������
       //goLineChartHeatNetwork();//�����ȶ�
         goMapChart();	//��ͼ
         goGauge();	//�Ǳ���
         goWordCloud();//����
         goHighFrequency();//��Ƶ��
         goMTChart();	//ý��ֲ�ͼ
         goOrigin();	//ý����Դ�Ա�
         goCaptureWebsite();	//�ص�����վ
         //createDocPdfHtml()//���ɾ�̬����
       	 $(".fenxiang").hide();
       	 $(".fenxiang2").hide();
       	 
    }else{
        document.title="${pab.title}�ĶԱȷ��������������ע��TA�ж�ţ��-΢�ȵ�";
        $(".tit3").find("h1").text('${pab.title}�ĶԱȷ���');
        var idd = "${pab.productsAnalysisId}";
        params.fileName = 'spreadTrend';
        spreadTrend(params);
        params.fileName = 'negativeTrend';
        negativeTrend(params);
        
        //heatNetworkTrend();
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'emotion';
    	}else{
    		//�µ�
    		params.fileName = 'sensitiveStatistics';
    	}
        emotion(params);
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'territory';
    	}else{
    		//�µ�
    		params.fileName = 'areaStatistics';
    	}
        territory(params);//��Ϣ����ֲ�
        //params.fileName = 'positiveWord';
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'highFrequency';
    	}else{
    		//�µ�
    		params.fileName = 'positiveWord';
    	}
        highFrequency(params);
        /* if(idd>0){
    		
    	}else{
    		//�µ�   �����Ƶ��
    		params.fileName = 'negativeWord';
    		negativeHighFrequency(params);
    	} */
        /* params.fileName = 'negativeWord';//�����Ƶ�ʻ�
		negativeHighFrequency(params); */
		if(idd>0){
    		//�ɵ�
    		params.fileName = 'wordcloud';
    	}else{
    		//�µ�
    		params.fileName = 'wordCloud';
    	}
		//�ؼ�����
        wordCloud(params);
        //params.fileName = 'sourceType';
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'source';
    	}else{
    		//�µ�
    		params.fileName = 'sourceType';
    	}
        source(params);//��Դ���ͶԱ�
        //params.fileName = 'sourceMedia';//ý���Ծ�Ա�
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'mediaActive';
    	}else{
    		//�µ�
    		params.fileName = 'sourceMedia';
    	}
        mediaActive(params);
        //params.fileName = 'mediaMonitoring';//�ص�ý����
        if(idd>0){
    		//�ɵ�
    		params.fileName = 'pointMedia';
    	}else{
    		//�µ�
    		params.fileName = 'mediaMonitoring';
    		$("#container6").height(300);
    	}
        pointMedia(params)
        //$(".actions").hide();
        shareReportCallBack("${shareCode}")
    }
});
function negativeHighFrequency(param){
	//debugger;
    $.ajax({
        url : 'spreadTrend.action',
        type : "post",
        data : param,
        success : function(data){
       		//�µ�
       		negativeHighFrequencyCallback(data);
        }
    });
}
function negativeHighFrequencyCallback(result){
    var data = eval(result[0]);
    if (data == null|| data == ""){
        //noData(dom);
    }else{
        $.each(data, function(i, n){
            var col6 = '<div class="wordsHeard mb20"><h1>�����Ƶ��</h1></div>';
            var mb10 = '';
            $.each(n.subGroups, function(j, k){
                if(j<9){
                	if(j % 3 == 0){
                        if(j != 0){
                            mb10 += '</div>';
                        }
                        mb10 += '<div class="row clearfix mb10">';
                    }
                    mb10 += '<div class="col-xs-4"><span class="btn btn-block btn-primary-o" data-original-title="����:32" title="' + k.total +
                        '" data-placement="top" data-toggle="tooltip">' + k.groupValue +
                        '</span></div>';
                }

            });
            mb10 += '</div>';
            col6 += mb10;

            $(".fmWord:eq("+i+")").html(col6);
        });

    }
}

//����doc,pdf,html
/* function createDocPdfHtml(){
	var contextPath = $("#contextPath").val() ;
	console.log("${pab.id}"+","+contextPath);
	ProductsAnalysisDwr.createDocPdfHtml("${pab.id}","${admin.userId}",GetRandom(30),contextPath,function(){
		NewsOperate.shareReport(parseInt("${admin.userId}"), parseInt("${pab.id}"), shareReportCallBack);
	});
} */

        function shareReportCallBack(data){
           if(data!=null&&data!="") {
                var url ="${viewUri}/lookShareCodeReport.action?shareCode=" + data;
                jiathis_config={
                    siteNum:4,
                    sm:"tsina,weixin,cqq,qzone",
                    url:url,
                    summary:" ",
                    title:"${pab.title}�Աȷ��������������ע��TA�ж�ţ��",
                    boldNum:4,
                    shortUrl:true,
                    hideMore:false,
                    pic:staticResourcePathH5 +"/images/fenxiang/weibowyq-icon300.jpg"
                }
            }
        }

function chartloading(id){
	var stat = document.getElementById(id);
	stat.innerHTML = '<div class="spinner" style="margin-left:10px;margin-top:10px;display: block;"><div class="bounce1"></div></div>';
	<%-- <img src=\"<%=staticResourcePath %>/images/loading_b.gif\"> --%>
}


//�������ƶԱ�
function goLineChart(){
	 	chartloading("container1");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getLineChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,LineChartCallBack);
}



function LineChart(data,dom){
    $("#container1").empty();
    var colors = ['#b5c334','#c1232b','#00F5FF','#FFFF00','#7B68EE'];
    var t=0; 
    $.each(data.data,function(){
        this.symbolSize = 6;
        this.itemStyle={'normal':{'color':colors[t],'lineStyle':{'width':2.8}}};
        t++;
    });
      var chart1 = echarts.init(document.getElementById(dom));
      var option = {
                    tooltip : {
                        trigger: 'axis',
                       /* formatter:function(params){
                         var now = new Date();
                         now.setTime(params[0].name);
                         v = now.format(data.dateFormat);
                         for (var i = 0, l = params.length; i < l; i++) {
                         v += '<br/>'+ params[i].seriesName +':'+ params[i].value;
                         }

                         return v;
                         }*/
                         },
                    toolbox: {
                        show : false,
                        orient:'vertical',
                        top:30,
                        left:'right',
                        feature : {
                            restore : {show: true},
                            saveAsImage : {
                                show: true,
                                name:data.name
                            },
                        }
                    },
                    legend: {
                        data:data.legend,
                        left:'center',
                        top:'3%'
                    },
                    
                    //color:['#ff7f4f','#87cefa','#da70d6','#32cd33','#6395ec','#ff69b3'],
                    xAxis:[{
                        type : 'category',//category|time
                        boundaryGap: false ,
                        splitLine:{show:false},
                        data : data.datetime,
                        axisLine: {
                            onZero: false
                        },
                        axisPointer:{
                        	show:true,
                        	type:'line',
                        	lineStyle:{
                        		color:'#27727b',
                        		type:'dashed',
                        		width:2
                        	}
                        },
                        axisLine: {
                        	show:true,
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
                        axisLabel : {
                            show:true,
                            textStyle:{
                            	color:'#222'
                            }
                        },
                    }
                    ],
                    yAxis : [{
                        type : 'value',
                        axisLabel:{
                        	show:true,
                        	textStyle:{
                        		color:'#222'
                        	},
                            formatter:function(v){
                                if(v>=1000){
                                    return (v/1000)+"k";
                                }else{
                                    return v;
                                }
                            }
                        },
                        
                        axisLine: {
                            lineStyle:{
                                color: ['#eee'],
                                width: 0.1,
                                type: 'solid'
                            }
                        },
                        splitArea : {
                            show: true,
                            areaStyle:{
                                color:['#FFF','#F7F7F7']
                            }
                        },
                        splitLine:{lineStyle:{width:0.1}}
                    }],
                   
                    calculable : false,
                    series :data.data
                }
      		
                chart1.setOption(option);
                chart1.setTheme('infographic');
}

	
	
	//�Ǳ���
	function goGauge(){
		chartloading("container3");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getGaugeChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,GaugeCallBack);
		
	}
	function GaugeCallBack(data){
		console.log("qinggan---"+data);
		var c3 = document.getElementById("container3");
		if (data==null||data==""){
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var datt1;
			var datt2;
			if(data.indexOf("#s#")>=0){
				datt1=data.split("#s#")[0];
				datt2=data.split("#s#")[1];
			}else{
				datt1=data;
				datt2=null;
			}
            var res = eval(datt1);
			if(res==null || res[0]==null){
				c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				console.log("...111");
				var listData = eval(res[0].data);
					if(listData==null){
						c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
                        var arrSen = [];
						var alertMsg = " ����ͼ��ʾ,";
						var allHtml = "<div class='row-fluid'>";
						for(var i=0;i<listData.length;i++){
							if(i==3){
								allHtml += "</div><div class='row-fluid'>";
							}
							
							var divHtml = "<div class='span3 borderBox chartBox float_l' style='height: 280px;'><div class='tit'><h2>"+listData[i].name+"</h2></div><div id='container3_"+i+"' style='height:250px;'></div></div>"
							allHtml += divHtml;
							var minganValue = eval(listData[i]).data[0].value;
							alertMsg+='"'+listData[i].name+'"��������Ϣռ��Ϊ<font class="f_c2">'+minganValue+'%</font>;';
                            arrSen[i]=parseFloat(minganValue);
						}

                        var maxIndexSen=arrSen.indexOf(Math.max.apply(Math, arrSen));

                        alertMsg+='��ʱ�����,"'+listData[maxIndexSen].name+'"��ע�⻥�������Լ��Ĳ���������������������';
						allHtml += "</div>";
						c3.innerHTML = allHtml;
						$("#container3_msg").html(alertMsg);
						for(var i = 0;i<listData.length;i++){
							var _chartColumn10 = GaugeChart(listData[i],"container3_"+i);
						}
						
					}
			}
			
		}
	}
	
	<%-- function GaugeCallBack(data){
		var c3 = document.getElementById("container3");
		if (data==null||data==""){
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var datt1;
			var datt2;
			
			var name = [];
            var zmData = [];
            var fmData = [];
            var newData=eval(data);
            $.each(newData[0].data, function(){
                var total = this.data[0] + this.data[1];
                var zmPer = (this.data[1]*100/total).toFixed(2);
                var fmPer = (this.data[0]*100/total).toFixed(2);
                name.push(this.name);
                zmData.push(zmPer);
                fmData.push(fmPer);
            })
			/* if(data.indexOf("#s#")>=0){
				datt1=data.split("#s#")[0];
				datt2=data.split("#s#")[1];
			}else{
				datt1=data;
				datt2=null;
			} */
            var res = data
			if(res==null || res[0]==null){
				c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				console.log("...111");
				var listData = eval(res);
					if(listData==null){
						c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
                        var arrSen = [];
						var alertMsg = " ����ͼ��ʾ,";
						var allHtml = "<div class='row-fluid'>";
						for(var i=0;i<name.length;i++){
							if(i==3){
								allHtml += "</div><div class='row-fluid'>";
							}
							
							var divHtml = "<div class='span3 borderBox chartBox float_l' style='height: 280px;'><div class='tit'><h2>"+name[i]+"</h2></div><div id='container3_"+i+"' style='height:250px;'></div></div>"
							allHtml += divHtml;
							var minganValue = eval(listData[i]).data[0].value;
							alertMsg+='"'+listData[i].name+'"��������Ϣռ��Ϊ<font class="f_c2">'+minganValue+'%</font>;';
                            arrSen[i]=parseFloat(minganValue);
						}

                        var maxIndexSen=arrSen.indexOf(Math.max.apply(Math, arrSen));

                        alertMsg+='��ʱ�����,"'+listData[maxIndexSen].name+'"��ע�⻥�������Լ��Ĳ���������������������';
						allHtml += "</div>";
						c3.innerHTML = allHtml;
						$("#container3_msg").html(alertMsg);
						for(var i = 0;i<listData.length;i++){
							var _chartColumn10 = GaugeChart(listData[i],"container3_"+i);
						}
						
					}
			}
			
		}
	} --%>
	
	
	
	function GaugeChart(data,dom){
		var minganValue = 0;
		var minganName ;
	 	$.each(eval(data).data, function(i, n) {
	 			minganName = n.name;
	 			if(n.value==0||data.value==0){
	 				minganValue = 0;
	 			}else{
	 				minganValue = n.value;
	 				//minganValue = Math.round(n.value/data.value*100);
	 			}
				
			/* 	if(minganValue ==0){
					minganValue=100;
					minganName = "������";
				} */
		}); 
	 	
		
		        	   	var chart2 = echarts.init(document.getElementById(dom));
		        	   	data =  eval(data);
		        		 chart2.showLoading( {
		        			text : "����Ŭ������������,�����ĵȴ�������",
		        			textStyle : {
		        				fontSize : 20
		        			}
		        		}); 
		        		 option = {
		        				 	
		        				    tooltip : {
		        				        formatter: "{a} <br/>{b} : {c}%"
		        				    },
		        				    toolbox: {
		        				        show : false,
		        				        orient:'vertical',
		        				        feature : {
		        				            restore : {show: true},
		        				            saveAsImage : {show: true}
		        				        }
		        				    },
		        				    series : [
		        				        {
		        				            name:data.name,
		        				            type:'gauge',
		        				            
		        				            startAngle: 180,
		        				            endAngle: 0,
		        				            center : ['50%', '90%'],    // Ĭ��ȫ�־���
		        				            radius : 180,
		        				            axisLine: {            // ��������
		        				                lineStyle: {       // ����lineStyle����������ʽ
		        				                    width: 90,
		        				                    color: [[0.2, '#72c1be'],[0.8, '#277bc0'],[1, '#d44e24']]
		        				                }
		        				            },
		        				            axisTick: {            // ������С���
		        				            	show:false,
		        				                splitNumber: 10,   // ÿ��splitϸ�ֶ��ٶ�
		        				                length :12,        // ����length�����߳�
		        				            },
		        				            splitLine:{
		        				            	show:false
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
		        				                width:20,
		        				                length: '90%'
		        				                
		        				            },
		        				            itemStyle:{
		        				            	normal:{
		        				            		color:'#fff',
		        				            		opacity:0.7
		        				            	}
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
		        				                    fontSize : 50
		        				                }
		        				            },
		        				            data:[{value: minganValue, name: minganName}]
		        				        }
		        				    ]
		        				};
		        		chart2.hideLoading();
		                chart2.setOption(option);
		                //window.onresize = chart2.resize;
	                 	$("#dcDataUseStatisticContainer").resize(function(){
		                  $(chart2).resize();
		                });
	                 	chart2.setTheme('macarons');
	        
	}
	
	
	
	
	

	//����ֲ�
	function goMapChart(){
		chartloading("container2");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getMapChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,MapCallBack);

	}
	 var sum=0;
	function MapCallBack(data){
		var c2 = document.getElementById("container2");
		var mapTop10 = document.getElementById("mapTop10");
		if (data==null||data==""){
			c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var res = eval(data);
			console.log("map---"+data);
			if(res==null || res[0]==null){
				c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				var topData = res[0].topData;
				var ulHtml = "";
				if(topData==null || topData.length==0|| topData[0]==null){
					mapTop10.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				}else{
					var msgAlert = "����Ϣ�ֲ��ĵ����Ͽ���";
					if(topData.length==1||topData.length==2||topData.length==4){	//����Ϊ1,2,4ʱһ������
						ulHtml += "<ul class='mapTable mapTable2' >";
					}else{
						ulHtml += "<ul class='mapTable' >";
					}
					for(var i=0;i<topData.length;i++){
						if(topData.length==1||topData.length==2||topData.length==4){	//����Ϊ1,2,4ʱһ������
							if(i>1&&i==2){
								ulHtml +="</ul><ul class='mapTable mapTable2'>";
							}
						}else{
							if(i>2&&i==3){
								ulHtml +="</ul><ul class='mapTable'>";
							}
						}
						var subMsgAlert="";
						var subData = eval(topData[i]).topData;
						if(subData!=null&&subData.length>0){
							subMsgAlert +=topData[i]["name"];
						}
						var liHtml = "<li><div class='tit'>"+eval(topData[i]).name+"</div><table border='0' cellspacing='0' cellpadding='0' style='height:250px;background-color:#fff'><tbody>";
						if(subData==null || subData[0]==null){
							liHtml += "<tr><td colspan='3'style='border:#ccc 1px dashed;background-color:#fff;'> <div align=\"center\" style=\"padding-top:50px;background-color:#fff;color:#000;\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div></td></tr></tbody></table></li>";
						}else{
							//subMsgAlert +="�ֲ����ʡ����"+subData[0]["name"]+"���ἰ����Ϣ��Ϊ��"+subData[0]["value"]+"��";
							for(var j=0;j<10;j++){
								var topName = "";
								var topValue= "";
								if(j<subData.length){
									topName = subData[j]["name"];
									topValue = subData[j]["value"];
								}
								var trHtml = "<tr><td>"+(j+1)+"</td><td>"+topName+"</td><td>"+topValue+"</td></tr>";

								liHtml +=trHtml;
							}

                            for(var k=0;k<subData.length;k++){
                                topValue = subData[k]["value"];
                                sum += parseInt(topValue);
                            }

                            if(sum>0){
                                var first = ((parseInt(parseInt(subData[0]["value"])) / sum) * 100).toFixed(2);
                                msgAlert+=subData[0]["name"]+'������"'+subMsgAlert+'"���ἰ�����ռ��Ϊ<font class="f_c2">'+first+'%</font>;';
                            }
							liHtml += "</tbody></table></li>";
						}
						ulHtml += liHtml;
						//msgAlert +=  subMsgAlert;
					}
					ulHtml += "</ul>";
				}
				mapTop10.innerHTML = ulHtml;
				$("#container2_msg").html(msgAlert);
				var _chartColumn10 = MapChart(res[0],"container2");
			}
			
		}
	}
	//��ͼ���
	function MapChart(data,dom){
		var min = 0;
		var max = 100000;
		 var colors = ['#ff7f50','#87cefa'],t=0; 
	 	$.each(eval(data).data, function(i, n) {
	 		$.each(n,function(j,m){
	 			delete m.itemStyle;
				if (m.value > max)
					max = m.value;
				if (m.value < min)
					min = m.value;
	 		});
	 		this.itemStyle={'normal':{'color':colors[t],'lineStyle':{'width':2.8}}};
		    t++;
		}); 
	
		        	   	var chart2 = echarts.init(document.getElementById(dom));
		        	   	data =  eval(data);
		        		 chart2.showLoading( {
		        			text : "����Ŭ������������,�����ĵȴ�������",
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
		        				animation : true,
		        				tooltip : {
		        					trigger : 'item',
		        					enterable:true,
		        					textStyle : {
		        						fontSize : 12
		        					}
		        				},
		        				legend: {
		        			        orient: 'horizontal',
		        			        left: 'center',
		        			        top:'5%',
		        			        data:data.legend
		        			    },
		        				 toolbox: {
			                	        show : false,
			                	        orient:'vertical',
			                	        y:30,
			                	        feature : {
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
								        calculable : true,
								        text:['��','��'],
								        color: ['#d44e24','#f29300','#f3d647']
								    },
		        				series :data.data
		        			};
		        		chart2.hideLoading();
		                chart2.setOption(option);
		        
	}
	
	
	//ý��ֲ�ͼ
	function goMTChart(){
		chartloading("container4");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getMTChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,MTCallBack);
	}
	
	
	
	
	function MTCallBack(data){
		$("#container4").height(350);
		var c4 = document.getElementById("container4");
		if (data==null||data==""){
			c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var res = eval(data);
			if(res==null || res[0]==null){
				c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
                    var arrSource = [];
					var listData = eval(res[0]);
					if(listData==null){
						c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
						var alertMsg = "�ڷ���ʱ����ڣ�";
						/* for(var i=0;i<listData.legend.length;i++){
							var subMsg = listData.datetime[0]+"���"+listData.legend[i]+"�ı�����࣬�ﵽ��<font class='f_c2'>"+listData.data[i].data[0]+"</font>����";
							alertMsg += subMsg;
						} */
						var topData = listData.topData;
						//console.log(listData);
						for(var i=0;i<topData.length;i++){
                            alertMsg+= '���"'+topData[i]["label"]+'"�ı�����Ҫ������'+topData[i]["name"]+'���ﵽ��<font class="f_c2">'+topData[i]["value"]+'</font>����';
							//alertMsg += subMsg;
                            arrSource[i]=topData[i]["value"];
						}

                        var maxIndexSource=arrSource.indexOf(Math.max.apply(Math, arrSource));

                        alertMsg+='�Ա�����"'+topData[maxIndexSource]["label"]+'"����������';

						$(".container4_msg").html(alertMsg);
						var _chartColumn10 = MTChart(listData,"container4");
					}
			}
			
		}
	}
	//ý��ֲ�ͼ
	function MTChart(data,dom){
		data =  eval(data);
		var colors = ['#ff7f50','#87cefa'],t=0; 
	    $.each(data.data,function(){
	        this.itemStyle={'normal':{'color':colors[t]}};
	        t++;
	    });
		$("#container4").empty();
		
		        	   	var chart4 = echarts.init(document.getElementById(dom));
		        	    	chart4.showLoading( {
		        			text : "����Ŭ������������,�����ĵȴ�������",
		        			textStyle : {
		        				fontSize : 20
		        			}
		        		});  
		        		 option = {
		        				  /*  title : {
		        				        text: data.title
		        				    },*/
		        				    tooltip : {
		        				        trigger: 'axis'
		        				    },
		        				    legend: {
		        				        data:data.legend
		        				    },
		        				    toolbox: {
		        				        show : false,
		        				        orient:'vertical',
		        				        feature : {
		        				            restore : {show: true},
		        				            saveAsImage : {show: true}
		        				        }
		        				    },
		        				    grid:{
			                	        x:40,
			                	        y:50,
			                	        x2:40,
			                	        y2:40
			                	      },
		        				    calculable : false,
		        				    xAxis : [
		        				        {
		        				            type : 'category',
		        				            data : data.datetime,
		        				            axisLine: {
		   		       	                	 lineStyle:{
		   		       	                		    color: ['#eee'],
		   		       	                		    width: 0.1,
		   		       	                		    type: 'solid',
		   		       	                		  
		   		       	                		    }
		   		       	               		 },
		   		       	               		 axisLabel:{
				   		       	               	 textStyle:{
			        				        			color:'#222' 
			        				        		 }
		   		       	               		 },
	   		       	               		 axisTick:{
	   			       	               		show:true,
	   			       	               		lineStyle:{
	   				                		    color: '#eee',
	   				                		    width: 1,
	   				                		    type: 'solid'
	   				                		    }
	   			       	               		}
		        				        }
		        				    ],
		        				    yAxis : [
		        				        {
		        				        	 axisLabel:{
		        				        		 textStyle:{
		        				        			color:'#222' 
		        				        		 },
			                     	             formatter:function(v){
			                     	            	 if(v>=1000){
			                     	            		 return (v/1000)+"k";
			                     	            	 }else{
			                     	            		 return v;
			                     	            	 }
			                     	             }
			        	               	 	},
		        				            type : 'value',
		        				            splitArea : {
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
		        				        }
		        				    ],
		        				    series : data.data
		        				};
		        		 chart4.hideLoading();
		        		 chart4.setOption(option);
	}
	
	
	
	
	
	//��άͼ ʱ�� ��Դ���� �Լ��ؼ���
	function MTChart1(data,dom){
		data =  eval(data);
		var datalist0 = new Array();
		var datalist = new Array();
			 $.each(data.legend,function(i,n){
				 var data1 = convertData(data.data,n,data.datetime[0]);
				 var serieStr = {type:'bar',name:n,data:data1.data};
				 datalist0.push(serieStr);
			}); 
			 
			 //ѭ��ʱ�� ����㣬ͼ�����ڲ�
			 $.each(data.datetime,function(i,n){
					//���ڵ�һ��Ĭ��ʱ�� �����ݼ�
					if(i==0){
						 var param = {
								 title : {'text':data.title},
     				            tooltip : {'trigger':'axis'},
     				            legend : {
     				                x:'right',
     				                'data':data.legend,
     				            	},
     				            toolbox : {
     				               'show':false, 
     				                orient : 'vertical',
     				                x: 'right', 
     				                y: 60,
     				                'feature':{
     				                    'restore':{'show':true},
     				                    'saveAsImage':{'show':true}
     				                }
     				            },
     				            calculable : true,
     				            grid : {'y':80,'y2':100},
     				            xAxis : [{
     				                'type':'category',
     				                'axisLabel':{'interval':0},
     				                'data':data.label
     				            }],
     				            yAxis : [
     				                {
     				                    'type':'value',
     				                    splitArea : {
	        	                                 show: true,
	        	                                 areaStyle:{
	        	                                     color:['#FFF','#F7F7F7']
	        	                                 }
	        	                             },
	        	                             splitLine:{lineStyle:{width:0.1}}
     				                }
     				            ],
								 series:datalist0
						 };
						datalist.push(param);
					}else{
						var datalist_param = new Array();
						 $.each(data.legend,function(j,keyword){
							 var dataj = convertData(data.data,keyword,n);
							 var serieStr = {data:dataj.data};
							 datalist_param.push(serieStr);
						 });
						 var param1 = {series:datalist_param};
						 datalist.push(param1);
					}
			 });
			 
			/*  $.each(data.legend,function(i,n){
				 var data1 = convertData(data.data,n,data.datetime[1]);
				 var serieStr = {type:'bar',name:n,data:data1.data};
				 datalist1.push(serieStr);
			});  */
		
		
		        	   	var chart4 = echarts.init(document.getElementById(dom));
		        	   	
		        	   /* 	chart4.showLoading( {
		        			text : "����Ŭ������������,�����ĵȴ�������",
		        			textStyle : {
		        				fontSize : 20
		        			}
		        		});  */
		        		 option = {
		        				    timeline:{
		        				        data:data.datetime,
		        				        label : {
		        				            formatter : function(s) {
		        				                return s.slice(5, 13);
		        				            }
		        				        },
		        				        autoPlay : true,
		        				        playInterval : 1000
		        				    },
		        				    options:datalist
		        				};
		        		 //chart4.hideLoading();
		        		 chart4.setOption(option);
	                
	}
	function sensitiveChart(name, zmData, fmData, dom){
        //�°���з���ռ�ȣ���״ͼ��
        var chart = echarts.init(dom);
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: { // ������ָʾ���������ᴥ����Ч
                    type: 'shadow' // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            legend: {
                data: ['���У�%��', '�����У�%��']
            },
            grid: {
                left: '3%',
                right: '5%',
                bottom: '3%',
                containLabel: true

            },
            xAxis: [{
                type: 'category',
                splitNumber:6,
                axisTick: {
                    alignWithLabel: false
                },
                data: name
            }],
            yAxis: [{
                type: 'value',
                name:'��λ��%��',
                min: 0,
                max: 100,
                position: 'left',
                axisLabel: {
                    formatter: '{value} %'
                }
            }],
            series: [{
                name: '�����У�%��',
                type: 'bar',
                stack: 's1',
                barWidth:45,
                itemStyle: {
                    normal: {
                        color: '#0e7ec0',
                        borderColor: '#fbfbfd'
                    }
                },
                data: zmData
            },{
                name: '���У�%��',
                type: 'bar',
                stack: 's1',
                barWidth:45,
                itemStyle: {
                    normal: {
                        color: '#ce421e',
                        borderColor: '#fbfbfd',
                    }
                },
                data: fmData
            }]
        };
        chart.setOption(option);
    }
	
	//ת������
	function convertData(data,name,time){
		for(var i=0;i<data.length;i++){
			if(name==data[i]['name']){
				var tempData = data[i]['data'];	//	ͨ��legend������������
				for(var j=0;j<tempData.length;j++){
					if(time==tempData[j]['name']){
						return tempData[j];
					}
				}
			}
		}
		
	}
	
		function strToJson(str){ 
			return JSON.parse(str); 
		}
	
	
	
	
	
	
	//ý����Դ�Ա�
	function goOrigin(){
		chartloading("container5");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getOTChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,OriginCallBack);
		
	}
	
	//�����α���
	function OriginCallBack(data){
		var c3 = document.getElementById("container5");
		if (data==null||data==""){
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
				var msgAlert = "����ͼ��ʾ��";
				var divHtml ="";
				divHtml = "<ul class='tab_menu clear'>";
				for(var i=0;i<data.length;i++){
					var title = data[i]["groupValue"];
					if(title.length>6){
						title = title.substring(0,6);
					}
					if(i==0){
						divHtml += "<li class='current'>"+title+"</li>";
					}else{
						divHtml += "<li>"+title+"</li>";
					}
					
				}
				divHtml += "</ul><div class='tab_box'>";
				
				for(var i = 0;i<data.length;i++){
						var dataHtml = "";
						if(i==0){
							dataHtml = "<div>";
						}else{
							dataHtml = "<div class='hide'>";
						}
						//dataHtml += "<div class='chartData'>";
					    var map = data[i]["subGroups"];
					    if(map==null||map.length==0){
					    	dataHtml+="<div style='text-align:center;padding-top:90px' ><p style='display:inline;font-size: 14px;'><img src='<%=staticResourcePath %>/images/shouye/warn.png'><br/>��ʱ���������Ϣ</p></div></div>";
					    }else{
                            if(map.length>8){
                                map.length=8;
                            }
					    	dataHtml += "<div class='triangle'>";
					    	var subMsg = data[i]["groupValue"]+"�����ݷ�����Ҫ������";
					    	subMsg += map[0]["groupValue"]+",������<font class='f_c2'>"+map[0]["total"]+"</font>��������������"+map[map.length-1]["groupValue"]+",������<font class='f_c2'>"+map[map.length-1]["total"]+"</font>����";
							for(var j=0;j<map.length;j++){
								var liHtml = "<div class='st s"+(j+1)+"'><div class='text'>";
								liHtml += "<p>"+map[j]["groupValue"].slice(0,4)+"</p><p>"+map[j]["total"]+"</p></div><canvas id='myCanvas_"+(i+1)+"_"+(j+1)+"' class='sc'></canvas></div>";
								dataHtml += liHtml;
								}
							
								dataHtml +="</div></div>";
								msgAlert+= subMsg ;
					    }
					    divHtml +=dataHtml;
				}
				divHtml +="</div>";
				c3.innerHTML = divHtml;
				$("#container5_msg").html(msgAlert);
				//tab�ĵ���¼�
				$('.tabs-mthydb').Tabs({
					event:'click'
				});
				
				//�����Ǳ���
				for(var i = 0;i<data.length;i++){
				    var map = data[i]["subGroups"];
				    if(map!=null||map.length>0){
						for(var j=0;j<map.length;j++){
							canvasTrigger(i+1,j+1);
						}

				    }
				}
				
		}
	}
	
	
	//canvas �����Ǳ���
	function canvasTrigger(i,j){
		var c=document.getElementById("myCanvas_"+i+"_"+j);
		var ctx=c.getContext("2d");
		ctx.beginPath();
		ctx.scale(1.4,1.4);
		ctx.moveTo(110,20);
		ctx.lineTo(20,100);
		ctx.lineTo(200,100);
		ctx.lineTo(110,20);
		if(j==1||j==4||j==7||j==8){
			ctx.strokeStyle = "#80a1e5";
		}else{
			ctx.strokeStyle = "#f0a23d";
		}
		if(j==1||j==8){
			ctx.fillStyle="rgba(128,160,229,0.2)";
		}
		if(j==2){
			ctx.fillStyle="rgba(255,183,90,0.5)";
		}
		if(j==3||j==5){
			ctx.fillStyle="rgba(255,183,90,0.2)";
		}
		if(j==4){
			ctx.fillStyle="rgba(128,160,229,0.4)";
		}
		if(j==6){
			ctx.fillStyle="rgba(255,183,90,0.3)";
		}
		if(j==7){
			ctx.fillStyle="rgba(128,160,229,0.3)";
		}
		ctx.stroke();
		ctx.fill();
	}
	//�ص�ý����
	function goCaptureWebsite(){
		chartloading("container6");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getCaptureWebsiteProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,CaptureWebsiteCallBack);

	}
	function CaptureWebsiteCallBack(data){
		var c3 = document.getElementById("container6");
		if (data==null||data==""){
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var res = eval(data);
			if(res==null || res[0]==null){
				c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				var listData = eval(res[0].data);
					if(listData==null||listData[0]==null){
						c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
                        var msg_6="";
                        var arrMeadia = [];
						var leg = res[0].legend;
						var divHtml = "";
						for(var i=0;i<listData.length;i++){
							var trHtml = "<tr><td>"+(i+1)+"</td><td>"+listData[i]["name"]+"</td><td>";
							var ulHtml = "<ul class='jcdx'>";
   							for(var j=0;j<leg.length;j++){
   								var liHtml = "<li>"+leg[j]+"</li>";
   								ulHtml+=liHtml;
   							}
    						ulHtml += "</ul></td><td><ul class='jcdx'>";	
    						var data_l = listData[i].data;
                            var sumCount=0;
    						for(var k=0;k<data_l.length;k++){
   								var liHtml = "<li>"+data_l[k]+"</li>";
                                sumCount += parseInt(data_l[k]);
   								ulHtml+=liHtml;
                                arrMeadia[i]=sumCount;
   							}
                            msg_6+='"'+listData[i]["name"]+'"��ȫ��8����Ҫý����ἰ��Ϊ<font class="f_c2">'+sumCount+'</font>��;';
    						ulHtml += "</ul></td></tr>";		   
    						trHtml += ulHtml;	
    						divHtml += trHtml;
						}
                        var maxIndexMeadia=arrMeadia.indexOf(Math.max.apply(Math, arrMeadia));

                        msg_6+='"'+listData[maxIndexMeadia]["name"]+'"�Ĵ�������Ȼ���á�';

                        $("#container6_msg").html(msg_6);
						$("#container6").html(divHtml);
					}
			}
			
		}
	}
	<%-- function CaptureWebsiteCallBack(data){
		/* var newdata=eval(data);
		var nnewdata=eval(newdata[0]);
		var nnnewdata=nnewdata[0];
		debugger; */
		var c3 = document.getElementById("container6");
		if (data==null||data==""){
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var res = eval(data);
			if(res==null || res[0]==null){
				c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				var listData = eval(res[0].data);
					if(listData==null||listData[0]==null){
						c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
                        var msg_6="";
                        var arrMeadia = [];
						var leg = res[0].legend;
						var divHtml = "";
						for(var i=0;i<data.length;i++){
							var indexData=data[i];
							var trHtml = "<tr><td>"+(i+1)+"</td><td>"+data[i].groupValue+"</td><td>";
							var ulHtml = "<ul class='jcdx'>";
   							for(var j=0;j<indexData.subGroups.length;j++){
   								var liHtml = "<li>"+indexData.subGroups[j].groupValue+"</li>";
   								ulHtml+=liHtml;
   							}
   							ulHtml += "</ul></td><td><ul class='jcdx'>";
   							var sumCount=0;
   							for(var j=0;j<indexData.subGroups.length;j++){
   								var liHtml = "<li>"+indexData.subGroups[j].total+"</li>";
   								sumCount += parseInt(indexData.subGroups[j].total);
   								ulHtml+=liHtml;
   								arrMeadia[i]=sumCount;
   							}
   							/* ulHtml += "</ul></td><td><ul class='jcdx'>";	
    						var data_l = indexData.subGroups[j];
                            var sumCount=0;
    						for(var k=0;k<data_l.length;k++){
   								var liHtml = "<li>"+data_l[k].total+"</li>";
                                sumCount += parseInt(data_l[k].total);
   								ulHtml+=liHtml;
                                arrMeadia[i]=sumCount;
   							} */
    						
                            msg_6+='"'+data[i].groupValue+'"��ȫ��8����Ҫý����ἰ��Ϊ<font class="f_c2">'+sumCount+'</font>��;';
    						ulHtml += "</ul></td></tr>";		   
    						trHtml += ulHtml;	
    						divHtml += trHtml;
						}
                        var maxIndexMeadia=arrMeadia.indexOf(Math.max.apply(Math, arrMeadia));

                        msg_6+='"'+data[maxIndexMeadia].groupValue+'"�Ĵ�������Ȼ���á�';

                        $("#container6_msg").html(msg_6);
						$("#container6").html(divHtml);
					//}
			}
			
		}
	} --%>


function goLineChartNegative(){
    chartloading("container8");
    var starttime = $("#starttime").val();
    if(starttime==null||starttime==""){
        starttime = "${starttime}";
    }
    var endtime = $("#endtime").val();
    if(endtime==null||endtime==""){
        endtime = "${endtime}";
    }
    EChartsDwr.getNegativeLineChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,LineChartNegativeCallBack);
}

function LineChartNegativeCallBack(result){
    var c1 = document.getElementById("container8");
    var data = eval(result);
    if (data==null||data==""){
        c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
        return false;
    }
    else{
        if(data[0].data==null || data[0].data.length==0){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else {
            data = data[0];
            //var arrNegative = [];
            var arrSen = [];
            var nameArr = [];
            var zeroArr=[];
            var alertMsg = "�ڷ���ʱ����ڣ�";
            var newArr = [];
            for (var i = 0; i < data.data.length; i++) {
            	var newD = {"time":data.data[i].label.name,"value":data.data[i].label.value,"name":data.data[i].name};
            	newArr.push(newD);
                var listData = data.data;
                var maxSum = 0;
                for (var j = 0; j < listData[i].data.length; j++) {
                    maxSum += listData[i].data[j] * (-1);
                }
                nameArr[i] =data.data[i].name ;
                arrSen[i] = parseInt(maxSum);
            }

            var maxIndexSen = arrSen.indexOf(Math.max.apply(Math, arrSen));
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
			}
            newArr.sort(compare);
//             var topTime = data.data[maxIndexSen]["label"].name;
            var topTime = newArr[0].time.split(":")[0];
//             topTime = topTime.split(":")[0];
            var topValue = data.data[maxIndexSen]["label"].value;
            alertMsg += '"' + newArr[0].name + '"�ڻ����������б�������࣬����' + topTime + 'ʱ�ﵽ���壬Ϊ<font class="f_c2">' +commafy(newArr[0].value)  + '</font>��';

            for(var j=0;j<arrSen.length;j++){
                if(arrSen[j]==0){
                    zeroArr[j]=j;
                    continue;
                }
                zeroArr[j]=-1;
            }

            for(var i=1;i<arrSen.length;i++){
                for(var j=0;j<arrSen.length-i;j++){
                    if(arrSen[j]<arrSen[j+1]){
                        var temp = arrSen[j];
                        arrSen[j] = arrSen[j + 1];
                        arrSen[j + 1] = temp;
                        var temp2 = nameArr[j];
                        nameArr[j] = nameArr[j + 1];
                        nameArr[j + 1] = temp2;
                    }
                }
            }
            //var maxIndexNegative=arrNegative.indexOf(Math.max.apply(Math, arrNegative));

            var zeroArrName=[];
            if(zeroArr.length>0){
                var n=0;
                for(var i=0;i<zeroArr.length;i++){
                    if(zeroArr[i]!=-1){
                        zeroArrName[n]=data.data[zeroArr[i]].name;
                        n++;
                    }
                }
            }

            if (arrSen.length > 1){
                var next=true;
                var nextNum=0;
                        for (var j =1; j < nameArr.length; j++) {
                            for(var k=0;k<zeroArrName.length;k++){
                                if(zeroArrName[k]==nameArr[j]){
                                    next=false;
                                    break;
                                }
                            }
                            if(next) {
                                if(nextNum==0){
                                    alertMsg +="�����Ϊ";
                                    nextNum++;
                                }
                                alertMsg += '"' + newArr[j].name + '"';
//                                 alertMsg += '"' + nameArr[j] + '"';
                                if (j != (arrSen.length - 1)) {
                                    alertMsg += '��';
                                }
                            }else{
                                continue;
                            }
                        }
                    }
           var alertMsg1=alertMsg.substr(alertMsg.length-1,1);
            if(alertMsg1=='��'){
                alertMsg = alertMsg.substring(0, alertMsg.length-1);
            }
            alertMsg+='��';
            //alertMsg+='�������������ƶԱ���������������"'+listData[maxIndexNegative].name+'"�����б������࣬���һ��������';
            $("#container8_msg").html(alertMsg);
            var _chartColumn1 = LineChartNegative(data,"container8");
        }
    }
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
		var re =/(-?\d+)(\d{3})/
		while(re.test(intPart)){
			intPart =intPart.replace(re,"$1,$2")
		}
		num = intPart+"."+pointPart;
	}else{
		num = num.toString();
		var re =/(-?\d+)(\d{3})/
		while(re.test(num)){
			num =num.replace(re,"$1,$2")
		}
	}
	return num;
	
}

function findSecondMax(a) {
    var result = -1;
    var b = a;//��������

    for (var i = 0; i < a.length; i++) {
        for (var j = 1; j < a.length; j++) {
            if (a[j] < a[j - 1]) {
                var temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
            }
        }
    }
    var x = a[a.length - 2];

    for (var i = 0; i < b.length; i++) {
        if (x == b[i]) {
            result = i;
        }
    }
    return result;
}
function LineChartHeatNetwork(data,dom){
    $("#container11").empty();
    $.each(data.data,function(){
        this.itemStyle={'normal':{'areaStyle':{'type':'default'}}};
    });
    var config = require(
            [
                'echarts',
                'echarts/chart/line'
            ],
            function (ec) {
                var chart8 = ec.init(document.getElementById(dom));
                var option = {
                    tooltip : {
                        trigger: 'axis',
                       /* formatter:function(params){
                            var now = new Date();
                            now.setTime(params[0].name);
                            v = now.format(data.dateFormat);
                            for (var i = 0, l = params.length; i < l; i++) {
                                v += '<br/>'+ params[i].seriesName +':'+ params[i].value;
                            }

                            return v;
                        }*/
                    },
                    toolbox: {
                        show : false,
                        orient:'vertical',
                        y:5,
                        x:'right',
                        feature : {
                            restore : {show: true},
                            saveAsImage : {
                                show: true,
                                name:data.name
                            }
                        }
                    },
                    grid:{
                        x:40,
                        y:40,
                        x2:40,
                        y2:40
                    },
                    legend: {
                        data: data.legend
                    },
                    calculable: false,
                    xAxis:[{
                        type : 'category',//category|time
                        boundaryGap: false ,
                        splitLine:{show:false},
                        data : data.datetime,
                        axisLine: {
                            onZero: false
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
                        }/*,
                        axisLabel : {
                            formatter:function(v){
                                v = new Date(v).format(data.dateFormat);
                                return v;
                            }

                        },*/
                    }],
                    yAxis : [{
                        type : 'value',
                        axisLabel:{
                            formatter:function(v){
                                if(v>=1000){
                                    return (v/1000)+"k";
                                }else{
                                    return v;
                                }
                            }
                        },
                        axisLine: {
                            lineStyle:{
                                color: ['#eee'],
                                width: 0.1,
                                type: 'solid'
                            }
                        },
                        splitArea : {
                            show: true,
                            areaStyle:{
                                color:['#FFF','#F7F7F7']
                            }
                        },
                        splitLine:{lineStyle:{width:0.1}}
                    }],
                    series:data.data
                };
                chart8.setOption(option);
                chart8.setTheme('macarons');
                var enConfig = require('echarts/config');
            }
    );
}

/*function LineChartNegative(data,dom){
    $("#container8").empty();
    $.each(data.data,function(){
        this.itemStyle={'normal':{'areaStyle':{'type':'default'}}};
    });
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart8 = ec.init(document.getElementById(dom));
                    var option = {
                        tooltip : {
                            trigger: 'axis',
                            formatter:function(params){
                                var now = new Date();
                                now.setTime(params[0].name);
                                v = now.format(data.dateFormat);
                                for (var i = 0, l = params.length; i < l; i++) {
                                    v += '<br/>'+ params[i].seriesName +':'+ params[i].value;
                                }

                                return v;
                            }
                        },
                        toolbox: {
                            show : true,
                            orient:'vertical',
                            y:5,
                            x:'right',
                            feature : {
                                restore : {show: true},
                                saveAsImage : {
                                    show: true,
                                    name:data.name
                                }
                            }
                        },
                        grid:{
                            x:40,
                            y:40,
                            x2:40,
                            y2:40
                        },
                        /* tooltip: {
                             trigger: 'axis'
                         },
                        legend: {
                            data: data.legend
                        },
                        calculable: false,
                       /* xAxis: [
                            {
                                type: 'category',
                                boundaryGap: false,
                                data: ['��һ', '�ܶ�', '����', '����', '����', '����', '����']
                            }
                        ],
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            splitLine:{show:false},
                            data : data.datetime,
                            axisLine: {
                                onZero: false
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
                            axisLabel : {
                                formatter:function(v){
                                    v = new Date(v).format(data.dateFormat);
                                    return v;
                                }

                            },
                        }],
                        yAxis : [{
                        type : 'value',
                        axisLabel:{
                            formatter:function(v){
                                if(v>=1000){
                                    return (v/1000)+"k";
                                }else{
                                    return v;
                                }
                            }
                        },
                        axisLine: {
                            lineStyle:{
                                color: ['#eee'],
                                width: 0.1,
                                type: 'solid'
                            }
                        },
                        splitArea : {
                            show: true,
                            areaStyle:{
                                color:['#FFF','#F7F7F7']
                            }
                        },
                        splitLine:{lineStyle:{width:0.1}}
                    }],
                        series: data.data
                    };
                    chart8.setOption(option);
                    chart8.setTheme('infographic');
                    chart8.setTheme('macarons');
                    var enConfig = require('echarts/config');
                }
        );
}*/
function LineChartNegative(data,dom){
    $("#container8").empty();
     /*var color=[];
     color[0]='#84fde8';
     color[1]='#426bf2';
     color[2]='#fca77f';
     color[3]='#25dbe6';
     color[4]='#0949aa';
     color[5]='#80a9ee';
     var i=0;*/
     var i = 0,lineColor=['#fbbba3','#c3e6fc'],areaColor = ['#fbbba3','#c3e6fc'];
    $.each(data.data,function(){
        this.itemStyle={'normal':{'color':lineColor[i]}};
        this.areaStyle={'normal':{'color':areaColor[i]}};
        //this.itemStyle={'normal':{'areaStyle':{'color':color[i],'type':'default'}}};
        i++;
    });
                var chart8 = echarts.init(document.getElementById(dom));
                var option = {
                    tooltip : {
                        trigger: 'axis',
                        /*formatter:function(params){
                            var now = new Date();
                            now.setTime(params[0].name);
                            v = now.format(data.dateFormat);
                            for (var i = 0, l = params.length; i < l; i++) {
                                v += '<br/>'+ params[i].seriesName +':'+ params[i].value;
                            }

                            return v;
                        }*/
                    },
                    
                    toolbox: {
                        show : false,
                        orient:'vertical',
                        top:5,
                        left:'right',
                        feature : {
                            restore : {show: true},
                            saveAsImage : {
                                show: true,
                                name:data.name
                            }
                        }
                    },
                    grid:{
                        left:'10%',
                        right:'5%',
                        top:'20%',
                        bottom:'15%'
                    },
                    legend: {
                    	top:'4%',
                    	left:'center',
                        data: data.legend
                    },
                    
                    calculable: false,
                    xAxis:[{
                        type : 'category',//category|time
                        boundaryGap: false ,
                        position:'top',
                        splitLine:{show:false},
                        data : data.datetime,
                        axisLine: {
                            onZero: false
                        },
                        axisLine: {
                            lineStyle:{
                                color: ['#eee'],
                                width: 0.1,
                                type: 'solid'
                            }
                        },
                        axisLabel:{
                        	textStyle:{
                        		color:'#222'
                        	}
                        },
                        axisTick:{
                            show:true,
                            lineStyle:{
                                color: '#eee',
                                width: 1,
                                type: 'solid'
                            }
                        }
                    }],
                    yAxis : [{
                        type : 'value',
                        axisLabel:{
                        	textStyle:{
                        		color:'#222'
                        	},
                            formatter:function(v){
                                if(v<=(-1000)){
                                    return "-"+(v/(-1000))+"k";
                                }else{
                                    return v;
                                }
                            }
                        },
                        axisLine: {
                            lineStyle:{
                                color: ['#eee'],
                                width: 0.1,
                                type: 'solid'
                            }
                        },
                     
                        splitArea : {
                            show: true,
                            areaStyle:{
                                color:['#FFF','#F7F7F7']
                            }
                        },
                        splitLine:{lineStyle:{width:0.1}}
                    }],
                    series: data.data
                };
                chart8.setOption(option);
                chart8.setTheme('shine');
	}

	
	//��Ů����
	function goSexCount(){
		chartloading("container7");
		var starttime = $("#starttime").val();
		if(starttime==null||starttime==""){
			starttime = "${starttime}";
		}
		var endtime = $("#endtime").val();
		if(endtime==null||endtime==""){
			endtime = "${endtime}";
		}
		EChartsDwr.getSexChartProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),SexCallBack);
		
	}
	
	function SexCallBack(data){
		var c7 = document.getElementById("container7");
		if (data==null||data==""){
			c7.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			return;
		}else{
			var res = eval(data);
			if(res==null || res[0]==null){
				c7.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
				return;
			}else{
				var listData = eval(res[0].data);
					if(listData==null||listData[0]==null){
						c7.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
						return;
					}else{
						var allHtml = "";
						for(var i=0;i<listData.length;i++){
							var divHtml = "<div class='span6 borderBox chartBox float_l'><div class='tit'><h2>"+listData[i].name+"</h2></div><div id='container7_"+i+"' style='height:350px;'></div></div>"
							allHtml += divHtml;
						}
						c7.innerHTML = allHtml;
						
						for(var i = 0;i<listData.length;i++){
							var _chartColumn10 = SexChart(listData[i],"container7_"+i);
						}
						//var _chartColumn10 = MapChart(res[0],"container3");
					}
			}
			
		}
	}

	function cloudChart(data,dom){
	  
	                var chart2 = echarts.init(document.getElementById(dom));
	                var option = {
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
	                                lang: ['������ͼ', '�ر�', 'ˢ��']
	                            },
	                            restore : {show: true},
	                            saveAsImage : {
	                                show: true,
	                                //name:data.title
	                            }
	                        }
	                    },
	                    series: [{
	                        /*name: 'Google Trends',*/
	                        type: 'wordCloud',
	                        size: ['90%', '90%'],
	                        textRotation : [0, 45, 90, -45],
	                        textPadding: 2,
	                        textStyle:{
	                        	normal:{
	                        		color: function () {
	                                    // Random color 
	                                    return 'rgb(' + [
	                                        Math.round(Math.random() * 160),
	                                        Math.round(Math.random() * 160),
	                                        Math.round(Math.random() * 160)
	                                    ].join(',') + ')';
	                                }
	                        	}
	                        },
	                        center:['50%','50%'],
	                        autoSize: {
	                            enable: true,
	                            minSize: 12
	                        },
	                        data:data
	                    }]
	                };
	        
	               chart2.setOption(option);
	}
	 function cloudCallback(data){
         var c9 = document.getElementById("container9");
         if (data==null||data==""){
             c9.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
             return;
         }else{
             var names="${title}";
             var allHtml = "<div class='row-fluid'>";
             for(var i=0;i<data.length;i++){
                
                 var name=names.split("��")[i];
                 if(data.length==1){
                     var divHtml = "<div class='span12 borderBox chartBox float_l' style='height: 350px;'><div class='tit'><h2>"+name+"</h2></div><div id='container9_"+i+"' style='height: 500px;'></div></div>";
                 }

                 if(data.length==3||data.length==5){
                     if(i==3){
                         allHtml += "</div><div class='row-fluid'>";
                     }
                     var divHtml = "<div class='span3 borderBox chartBox float_l' style='height: 350px;'><div class='tit'><h2>"+name+"</h2></div><div id='container9_"+i+"' style='height: 320px;'></div></div>";
                 }

                 if(data.length==2||data.length==4||data.length==6){
                     if(i==2||i==4){
                         allHtml += "</div><div class='row-fluid'>";
                     }
                     var divHtml = "<div class='span6 borderBox chartBox float_l' style='height: 350px;'><div class='tit'><h2>"+name+"</h2></div><div id='container9_"+i+"' style='height: 320px;'></div></div>";
                 }

                
                 allHtml += divHtml;
             }
             allHtml += "</div>";
             c9.innerHTML = allHtml;
             var colors = ["#f29300","#277bc0","#a05623","#72c1be"];//1.�� 2.�� 3.�� 4.��
             for(var i=0;i<data.length;i++){
            	var chartData = eval(data[i]);
            	for(var m = 0;m<chartData.length;m++){
            		chartData[m] = $.extend(true,chartData[m],{textStyle:{normal:{color:colors[m%4]}}})//�޸���ɫ
            	}
                 var _chartColumn9 = cloudChart(chartData,"container9_"+i);
             }
         }
     }
	 
	//����
	function goWordCloud(){
	    chartloading("container9");
	    var starttime = $("#starttime").val();
	    if(starttime==null||starttime==""){
	        starttime = "${starttime}";
	    }
	    var endtime = $("#endtime").val();
	    if(endtime==null||endtime==""){
	        endtime = "${endtime}";
	    }
	    EChartsDwr.getWordCloudProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,cloudCallback);

	}

    //��Ƶ��
    function goHighFrequency(){
        chartloading("container10");
        var starttime = $("#starttime").val();
        if(starttime==null||starttime==""){
            starttime = "${starttime}";
        }
        var endtime = $("#endtime").val();
        if(endtime==null||endtime==""){
            endtime = "${endtime}";
        }
         EChartsDwr.getPositiveNegativeHighFrequencyProductsAnalysis('${pa.id}','${admin.userId}',starttime, endtime, GetRandom(30),null,null,getHighFrequencyCallback);
    }


    function getHighFrequencyCallback(data){
        var c10 = document.getElementById("container10");
        if (data==null||data==""){
            c10.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	console.log(data);
            var names="${title}";
            var alertMsg = "����ͼ��ʾ��";
            var allHtml = "<div class='row-fluid'>";
            for(var i=0;i<data.length;i++){
            	if(data[i].length == 0){
            		continue;
            	}
               if(i==2||i==4){
                    allHtml += "</div><div class='row-fluid'>";
                }

                //var names=$("#title").val();
                var name=names.split("��")[i];
                if(i==1||i==3||i==5){
                    var divHtml = "<div class='span12 borderBox chartBox float_r' style='height:auto;'><div class='tit'><h2>"+name+"</h2></div><div class='wordsGroup span6 float_l' id='wc1'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";
                }else{
                    var divHtml = "<div class='span12 borderBox chartBox' style='height:auto;'><div class='tit'><h2>"+name+"</h2></div><div class='wordsGroup span6 float_l' id='wc1'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";
                }
                //var length1 = data[i].length>9?9:data[i].length;
                var h=0;
                for(var j=0;j<data[i].length;j++) {
                    if (h < 9) {
                        if (data[i][j].type == 2) {
                            divHtml += "<dd>" + data[i][j].name + "<em>"+data[i][j].num+"</em></dd>";
                            h++;
                        }
                    }
                }

                divHtml+="</dl></div><div class='wordsGroup wordsGroup2 span6 float_r' id='wc2'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";

                //var length2 = data[i].length>9?9:data[i].length;
                var f=0;
                var farray = [];
                for(var j=0;j<data[i].length;j++){
                    if(f<9) {
                        if (data[i][j].type == 1) {
                           divHtml += "<dd>" + data[i][j].name +"<em>"+data[i][j].num+"</em></dd>";
                           if(f<3){
                        	   farray[f] = data[i][j].name;
                           }
                           f++;
                        }
                    }
                }
                /*if(data[i].length>0){
                    alertMsg+='"'+name+'"�ĸ����Ƶ�ʻ���Ҫ��"'+data[i][0].name +'��'+data[i][1].name+'��'+data[i][2].name+'"Ϊ��;';
                }*/
                   /*  if(f>0) {
                        alertMsg += '"' + name + '"�ĸ����Ƶ�ʻ���Ҫ��"' + data[i][f - f].name;
                    }
                    if(f>1){
                        alertMsg += '��' + data[i][f - (f - 1)].name;
                    }
                    if(f>2){
                        alertMsg +='��' + data[i][f - (f - 2)].name
                    } */
                    
                    if(farray.length>0) {
                        alertMsg += '"' + name + '"�ĸ����Ƶ�ʻ���Ҫ��"' + farray[0];
                    }
                    if(farray.length>1){
                        alertMsg += '��' + farray[1];
                    }
                    if(farray.length>2){
                        alertMsg +='��' + farray[2]
                    }
                    alertMsg +='";'
                divHtml+="</dl></div></div>";
                allHtml += divHtml;
            }
            allHtml += "</div>";
            c10.innerHTML = allHtml;
            $("#container10_msg").html(alertMsg);
        }
    }

    
	function SexChart(data,dom){
		var minganValue = 0;
		var minganName ;
	 	$.each(eval(data).data, function(i, n) {
	 			minganName = n.name;
				minganValue = Math.round(n.value/data.value*100);
		}); 
	 	
		
		        	   	var chart2 = echarts.init(document.getElementById(dom));
		        	   	data =  eval(data);
		        		 chart2.showLoading( {
		        			text : "����Ŭ������������,�����ĵȴ�������",
		        			textStyle : {
		        				fontSize : 20
		        			}
		        		}); 
		        		 option = {
		        				    tooltip : {
		        				        formatter: "{a} <br/>{b} : {c}%"
		        				    },
		        				    toolbox: {
		        				        show : false,
		        				        feature : {
		        				            restore : {show: true},
		        				            saveAsImage : {show: true}
		        				        }
		        				    },
		        				    series : [
		        				        {
		        				            name:data.name,
		        				            type:'gauge',
		        				            startAngle: 180,
		        				            endAngle: 0,
		        				            center : ['50%', '90%'],    // Ĭ��ȫ�־���
		        				            radius : 220,
		        				            axisLine: {            // ��������
		        				                lineStyle: {       // ����lineStyle����������ʽ
		        				                    width: 100,
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
		        				                    fontSize : 50
		        				                }
		        				            },
		        				            data:[{value: minganValue, name: minganName}]
		        				        }
		        				    ]
		        				};
		        		chart2.hideLoading();
		                chart2.setOption(option);
		                //window.onresize = chart2.resize;
	                 	$("#dcDataUseStatisticContainer").resize(function(){
		                  $(chart2).resize();
		                });
	                 	chart2.setTheme('infographic');
	               
	}
	
	
	
	//ʱ���������ת
	function selectTime(n,obj){
		$(obj).addClass("selected");
		$(obj).siblings('li').removeClass('selected');
		var curDate = new Date(); 
		var preDate = new Date(curDate.getTime() - parseInt(n)*24*60*60*1000);  //ǰn��
		var endtime = curDate.format('yyyy-MM-dd hh:mm:ss');
		var starttime = preDate.format('yyyy-MM-dd hh:mm:ss');
		$("#endtime").val(endtime);
		$('#starttime').val(starttime);
		$("#starttime_show").html(starttime);
		$("#endtime_show").html(endtime);
		//ʱ���޸��˺��ٴε��� ������ͼjs
		goLineChart();	//����ͼ
  		goMapChart();	//��ͼ
   		goGauge();	//�Ǳ���
   		goMTChart();	//ý��ֲ�ͼ
   		goOrigin();	//ý����Դ�Ա�
  		goCaptureWebsite();	//�ص�����վ
		
		
	}
	
	/* function selectTime(n){
		$("#selectedTime").val(n);
	}
	 */
	
	
	 function exportReport(){
		 if("${title}"!=null&&"${title}"!=''){
			document.frmPopWin.action = "<%=njxBasePath%>/exportPABrief.shtml";
			document.frmPopWin.target = "";
			document.frmPopWin.submit();
		 }else{
			 showMsgInfo(0,"��Ʒ��������Ϊ�ղ��ܵ����ļ���",0);
			 return;
		 }
		
	 }


    function spreadTrend(param){
        $.ajax({
            url : 'spreadTrend.action',
            type : "post",
            data :param,
            success : function(data){
            	var id = "${pab.productsAnalysisId}";
            	if(id>0){
            		//�ɵ�
                    LineChartCallBack(data);
            	}else{
            		//�µ�
//             		newLineChartCallBack(data,"container1");
            		newLineChartCallBack(data,"container1","container1_msg");
            	}
                //LineChartCallBack(data,"container1");
            }
        });
    }
    function LineChartCallBack(result){
        var c1 = document.getElementById("container1");
        var data = eval(result);
        if (data==null||data==""){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }
        else{
            if(data[0].data==null || data[0].data.length==0){
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else{
                var arr = [];
                data = data[0];
                var alertMsg = "�ڷ���ʱ�����,";
                for(var i=0;i<data.data.length;i++){
                    var listData = data.data;
                    var subMsg =  listData[i].name;
                    var topTime = eval(listData[i]["label"]).name;
                    /*topTime = new Date(topTime).format("yyyy-MM-dd hhʱ");*/
                    topTime = topTime.split(":")[0];
                    var topValue = eval(listData[i]["label"]).value;
                    alertMsg+= '"'+subMsg+'"�������Ϣ��'+topTime+'ʱ�ﵽ������߷�ֵ��������<font class="f_c2">'+topValue+'</font>����';
                    //alertMsg += subMsg;
                    arr[i]=topValue;
                }
                var maxInNumbers = Math.max.apply(Math, arr);
                var minInNumbers = Math.min.apply(Math, arr);

                var subtractNumbers=parseInt(maxInNumbers)-parseInt(minInNumbers);

                var maxIndex=arr.indexOf(Math.max.apply(Math, arr));
                var minIndex=arr.indexOf(Math.min.apply(Math, arr));

                alertMsg+='�������������Ա�����,"'+listData[maxIndex].name+'"�ڻ������ϵĴ���������"'+listData[minIndex].name+'"����<font class="f_c2">'+subtractNumbers+'</font>����';
                $("#container1_msg").html(alertMsg);
                var _chartColumn1 = LineChart(data,"container1");
            }
        }
    }
    function newLineChart(data, dom){
        /*$.each(data.data,function(){
            this.symbolSize = 9;
            this.symbol='emptyCircle';
        });*/
        var position = 'bottom';
        if(dom.id == "container8"){
            position = 'top';
        }
        var chart1 = echarts.init(dom, 'shine');
        var option = {
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show : true,
                orient:'vertical',
                y:5,
                x:'right',
                feature : {
                    restore : {show: true},
                    saveAsImage : {
                        show: true,
                        name:data.name
                    }
                }
            },
            color:['#3fad7e','#f18d00','#5573b6','#6f7786','#0086ce','#b67e48'],
            legend: {
                data: data.legend
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',//category|time
                boundaryGap: false,
                position:position,
                splitLine: {show: false},
                data: data.datetime,
                axisLine: {
                    onZero: false
                },
                axisLine: {
                    lineStyle: {
                        color: ['#333333'],
                        width: 0.1,
                        type: 'solid'
                    }
                },
                axisTick: {
                    show: true,
                    lineStyle: {
                        color: '#333333',
                        width: 1,
                        type: 'solid'
                    }
                }
            }
            ],
            yAxis : [{
                type : 'value',
                axisLabel:{
                    formatter: function(v){
                        if(v>=1000){
                            return (v/1000)+"k";
                        }else if(v<=-1000){
                            return -(v/1000)+"k";
                        }else{
                            return v;
                        }
                    }
                },
                axisLine: {
                    lineStyle:{
                        color: ['#333333'],
                        width: 0.1,
                        type: 'solid'
                    }
                },
                splitArea : {show: true
                },
                splitLine:{
                    lineStyle:{width:0.1}}
            }],
            calculable : false,
            series :data.data
        }
        chart1.setOption(option);
    }

   function negativeTrend(param){
       $.ajax({
           url : 'spreadTrend.action',
           type : "post",
           data :param,
           success : function(data){
               //LineChartNegativeCallBack(data);
        	   //newLineChartCallBack(data,"container8");
        	   var id = "${pab.productsAnalysisId}";
	           	if(id>0){
	           		//�ɵ�
	           		LineChartNegativeCallBack(data);
	           	}else{
	           		//�µ�
// 	           		newLineChartCallBack(data,"container8");
	           		newLineChartCallBack(data,"container8","container8_msg");
	           	}
           },
           error:function(){
           		$("#container8").parents(".wrapCon").remove();
           }
       });
   }
   function newLineChartCallBack(result, id,ms){
       var dom = document.getElementById(id);
       var data = eval(result[0]);
       if (data == null || data == ""){
       	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
           return;
       }else{
           if(data[0].data==null || data[0].data.length==0){
           	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
               return;
           }else{
           	newLineChart(data[0], dom);
           	var arr = data[0].data;
           	var val = [];
           	var namr = [];
           	var msg = '�ڷ���ʱ�����,';
           	for(var i=0;i<arr.length;i++){
           		val[i] = arr[i].label.value;
           		namr[i] = arr[i].name;
           		msg += '"'+arr[i].name+'"�������Ϣ��'+arr[i].label.name+'ʱ�ﵽ������߷�ֵ��������<span style="color:red">'+arr[i].label.value+'</span>��';
           		msg += '��';
           	}
               var maxIndex=val.indexOf(Math.max.apply(Math, val));
               var minIndex=val.indexOf(Math.min.apply(Math, val));
           	msg+='�������������Ա�����,"'+namr[maxIndex]+'"�ڻ������ϵĴ���������"'+namr[minIndex]+'"����<font class="f_c2">'+commafy((parseInt(val[maxIndex])-parseInt(val[minIndex])))+'</font>����';
           	$("#"+ms).html(msg);
           }
       }
   }
//    function newLineChartCallBack(result, id){
//        var dom = document.getElementById(id);
//        var data = eval(result[0]);
//        if (data == null || data == ""){
<%--        	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//            return;
//        }else{
//            if(data[0].data==null || data[0].data.length==0){
<%--            	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//                return;
//            }else{
//            	newLineChart(data[0], dom);
//            }
//        }
//    }
   function newLineChart(data, dom){
       /*$.each(data.data,function(){
           this.symbolSize = 9;
           this.symbol='emptyCircle';
       });*/
       var position = 'bottom';
       if(dom.id == "container8"){
           position = 'top';
       }
       var chart1 = echarts.init(dom, 'shine');
       var option = {
           tooltip: {
               trigger: 'axis'
           },
           toolbox: {
               show : true,
               orient:'vertical',
               y:5,
               x:'right',
               feature : {
                   restore : {show: true},
                   saveAsImage : {
                       show: true,
                       name:data.name
                   }
               }
           },
           color:['#3fad7e','#f18d00','#5573b6','#6f7786','#0086ce','#b67e48'],
           legend: {
               data: data.legend
           },
           grid: {
               left: '3%',
               right: '4%',
               bottom: '3%',
               containLabel: true
           },
           xAxis: [{
               type: 'category',//category|time
               boundaryGap: false,
               position:position,
               splitLine: {show: false},
               data: data.datetime,
               axisLine: {
                   onZero: false
               },
               axisLine: {
                   lineStyle: {
                       color: ['#333333'],
                       width: 0.1,
                       type: 'solid'
                   }
               },
               axisTick: {
                   show: true,
                   lineStyle: {
                       color: '#333333',
                       width: 1,
                       type: 'solid'
                   }
               }
           }
           ],
           yAxis : [{
               type : 'value',
               axisLabel:{
                   formatter: function(v){
                       if(v>=1000){
                           return (v/1000)+"k";
                       }else if(v<=-1000){
                           return -(v/1000)+"k";
                       }else{
                           return v;
                       }
                   }
               },
               axisLine: {
                   lineStyle:{
                       color: ['#333333'],
                       width: 0.1,
                       type: 'solid'
                   }
               },
               splitArea : {show: true
               },
               splitLine:{
                   lineStyle:{width:0.1}}
           }],
           calculable : false,
           series :data.data
       }
       chart1.setOption(option);
   }
   function heatNetworkTrend(){
       $.ajax({
           url : 'heatNetworkTrend.action',
           type : "post",
           data :{"pabId":'${pab.id}'},
           success : function(data){
               LineChartHeatNetworkCallBack(data);
           }
       });
   }
function wordCloud(param){
    $.ajax({
        url : 'spreadTrend.action',
        type : "post",
        data :param,
        success : function(data){
            /* var data=eval(data);
            cloudCallback(data); */
        	//newCloudCallback(data,"container9");
        	var id = "${pab.productsAnalysisId}";
        	if(id>0){
        		//�ɵ�
        		var data=eval(data);
                cloudCallback(data);
        	}else{
        		//�µ�
        		newCloudCallback(data,"container9");
        	}
        },
        error:function(){
        	$("#container9").parents(".wrapCon").remove();
        }
    });
}
function newCloudCallback(data, id){
    var dom = document.getElementById(id);
    if (data==null||data==""){
    	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var names = $("#title").val();
        var html='';
        var name=names.split("��");
        var h=0;
        var color=["army_green","orange","cobalt_blue","dark_grey","blue","brown"];
        for(var i=0;i<data.length;i++){
            if((h==0) || (h==3 && data.length!=4) || (data.length==4 && h==2)){
                html+='<div class="row clearfix">';
            }
            if(data.length==2 || data.length==4 ){
                html += '<div class="col-md-6">';
            }else{
                html += '<div class="col-md-4">';
            }
            html+='<div class="panel panel-top-border '+color[i]+'">';
            html+='<div class="panel-heading align_c">';
            html+='<h2 class="tit">';
            html+=name[i];
            html+='</h2>';
            html+='</div>';
            html+='<div class="panel-body bg_gray-50">';
            html+='<div class="chart-bady" id="sentimentAnalysisChart'+i+'" style="height: 300px;">';
            html+='</div>';
            html+='</div>';
            html+='</div>';
            html+='</div>';
            h++;
            if((h==3&& data.length!=4) || h==6){
                html+='</div>';
            }
        }
        html+='</div>';
        dom.innerHTML = html;

        var col=[["#3fa579","#72d2a9","#28a0ab"],["#f18d00","#f1b192","#b9a7af"],["#526dab","#829bd5","#6c54b0"],["#6e7584","#b1b5c2","#b1b5c2"],["#b1b5c2","#b1b5c2","#127ea1"],["#b17b47","#d8a677","#b25047"]];
        for(var i=0;i<data.length;i++){
            if(data[i] ==null||data[i]==""){
                var c = document.getElementById("sentimentAnalysisChart" + i);
                c.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
                continue;
            }
            newCloudChart(eval(data[i]),"sentimentAnalysisChart"+i,col[i]);
        }

    }
}
//��Ƶ��
function highFrequency(param){
    $.ajax({
        url : 'spreadTrend.action',
        type : "post",
        data :param,
        success : function(data){
            //var data=eval(data);
            //getHighFrequencyCallbackV2(data);
            //newHighFrequencyCallback(data,"container10");
        	var id = "${pab.productsAnalysisId}";
        	if(id>0){
        		//�ɵ�
        		//if(null!=data){
                    //var data=eval(data);
                  	var data=eval(data);
                    getHighFrequencyCallbackV2(data);
            	//}else{
            		//goHighFrequency2();
            	//}
        	}else{
        		//�µ�
        		newHighFrequencyCallback(data,"container10");
        	}
        },
        error:function(){
        	$("#container10").parents(".wrapCon").remove();
        }
    });
}
function getHighFrequencyCallbackV2(data){
    var c10 = document.getElementById("container10");
    if (data==null||data==""){
        c10.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
    	var names="${title}";
        var alertMsg = "����ͼ��ʾ��";
        var allHtml = "<div class='row-fluid'>";
        for(var i=0;i<data.length;i++){
            if(data[i] != null && data[i]!=""&&data[i].length>0){
                if(i==2||i==4){
                    allHtml += "</div>";
                }

                //var names=$("#title").val();
                var name=names.split("��")[i];

                if(i==1||i==3||i==5){
                    var divHtml = "<div class='row-fluid'><div class='span12 borderBox chartBox float_r' style='height:auto;'><div class='tit'><h2>"+name+"</h2></div><div class='row-fluid'><div class='wordsGroup span6 float_l' id='wc1'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";
                }else{
                    var divHtml = "<div class='row-fluid'><div class='span12 borderBox chartBox' style='height:auto;'><div class='tit'><h2>"+name+"</h2></div><div class='row-fluid'><div class='wordsGroup span6 float_l' id='wc1'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";
                }
                //var length1 = data[i].length>9?9:data[i].length;
                var h=0;
                for(var j=0;j<data[i].length;j++) {
                    if (h < 9) {
                        if (data[i][j].type == 2) {
                            divHtml += "<dd>" + data[i][j].name + "<em>"+data[i][j].num+"</em></dd>";
                            h++;
                        }
                    }
                }

                divHtml+="</dl></div><div class='wordsGroup wordsGroup2 span6 float_r' id='wc2'><h1>�����Ƶ�ʻ�</h1><dl style='height: 140px;'>";

                //var length2 = data[i].length>9?9:data[i].length;
                alertMsg += '"' + name + '"';
                var f=0;
                for(var j=0;j<data[i].length;j++){
                    if(f<9) {
                        if (data[i][j].type == 1) {
                            divHtml += "<dd>" + data[i][j].name +"<em>"+data[i][j].num+"</em></dd>";
                            if(f<1){
                                alertMsg += '�ĸ����Ƶ�ʻ���Ҫ��"'+data[i][j].name+'"';
                            }else if (f < 3) {
                                alertMsg += '��"'+data[i][j].name+'"';
                            }
                            f++;
                        }
                    }
                }
                if(f!=0) {
                    alertMsg += 'Ϊ��;'
                }else{
                    alertMsg += '���޸���ʻ�;'
                }
                divHtml+="</dl></div></div></div></div>";
                allHtml += divHtml;
            }
        }
        allHtml += "</div>";
        c10.innerHTML = allHtml;
        $("#container10_msg").html(alertMsg);
    }
}
function newHighFrequencyCallback(result, id){
    var dom = document.getElementById(id);
    var data = eval(result[0]);
    if (data == null|| data == ""){
    	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var html='<div classs="row clearfix">';
        $.each(data, function(i, n){
            var col6 = '<div class="col-md-6"><div class="panel panel-top-border army_green">';
            col6 += '<div class="panel-heading align_c"><h3 class="panel-title">' + n.groupValue+'</h3></div>';
            col6 += '<div class="panel-body bg_gray-50"><div class="wordsGroup blue mb20">';
            col6 += '<div class="wordsHeard mb20"><h1>�����Ƶ��</h1></div>';
            var mb10 = '';
            $.each(n.subGroups, function(j, k){
            	if(j<9){
                    if(j % 3 == 0){
                        if(j != 0){
                            mb10 += '</div>';
                        }
                        mb10 += '<div class="row clearfix mb10">';
                    }
                    mb10 += '<div class="col-xs-4"><span class="btn btn-block btn-primary-o" data-original-title="����:32" title="' + k.total +
                        '" data-placement="top" data-toggle="tooltip">' + k.groupValue +
                        '</span></div>';
            	}

            });
            mb10 += '</div>';
            col6 += mb10 + '</div>';
            col6 += '<div class="wordsGroup red fmWord"></div>';
            col6 += '</div></div></div>';
            html += col6;
        });
        html += '</div>';

        $(dom).html(html);
        var params = {"analysisTaskTickets":"${pab.ticket}","createTime":"${pab.createStr}",
                'starttime':"${pab.startStr}",'endtime':"${pab.endStr}",
                'userId':"${pab.userId}",'productsAnalysisId':"${pab.productsAnalysisId}",'pabId':"${pab.id}"};
        var idd = "${pab.productsAnalysisId}";
			if(idd>0){
    		
    	}else{
    		//�µ�   �����Ƶ��
    		params.fileName = 'negativeWord';
    		negativeHighFrequency(params);
    	}
    }
}
var url = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAbEAAAEmCAYAAADss65KAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3BpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTM4IDc5LjE1OTgyNCwgMjAxNi8wOS8xNC0wMTowOTowMSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo5NjhlYWU0MC0wYmI0LTRhYWItODBlYy1iNGIxM2RkOTc5NmEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6REFENEIzMEIxREE0MTFFN0FGM0FCRTE2NjhGMjAzQzYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6REFENEIzMEExREE0MTFFN0FGM0FCRTE2NjhGMjAzQzYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4QUVDM0JGQTFEMDYxMUU3ODhCMUExMEQwN0Y2OEQ1RCIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4QUVDM0JGQjFEMDYxMUU3ODhCMUExMEQwN0Y2OEQ1RCIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PqXMrRYAABNMSURBVHja7N1rrGVleQfwdWaGGYaLWJhRYLhURm1FvCA2ClqxJo21aQejNIY2tfOhNU1pm06Tkhiahg+maTHxg2lLQ1sofpBIadRpSyJpKWq5aCoIyHgpqJRrGWYEyjAzzOX0eVn71MNwztlr773u6/dLnhz0wJ4571pr/8+z33e9a25+fj4DgC5aZQgAEGIAIMQAQIgB0HNrxv0Lc3NzRgm6Z2vUe6J+KurUqI1R66JWp8u6hNefH9WhqGejnox6OOr+qH+IusMhYOaTrMjCw/QvzVJA494fdV/U86NQmW9BHY46EPVU1E1RJzpMlJ0/L2aQkIJOOTvq8qhHWxRYk1QKtnuiLhVslBFyOjFovytGoXWwg6E1rvZHfS9qi8MspHRi0B9bo57rYWiN+wjysVG3CTox6Jgrs3wOaV69GGgPRH3MaaETW6nmxgWR1YlQqTQv9K2ok7NyVg32UZr7+2bU2w3F8EJunFVFXkQnBqVLHUaaD9oVdYoAW1G6LeC8UYe2O+pcQzKMTqy+FwGK+mrWzVWFbVwQcp3TadghV+jjxHF83AiFfDfqdTqu0qW5s89F/aqh6GZItaOdA5Zzj66ptoUgOjOdmE4MSrIj6g2GoZHO7PNRFxsKnZhODCZ3zeiNVHfU/M4gbqDWienEoKBtWX6f1xpD0SrPRF0w6ozpYSfmPjGYXbpB+STD0Gr3Rr3FMPQv5NwnBtNLq+LmBVgnvDnLb20wV9aykJo1X3RiMLnNWb7LxtGGopP+O+pMw6AT04kxROnZWA8IsE47Q1emE4MhSrvKH2sYeiV11G8yDDox6LMrsnzZvADrn3OyfDn+JkPRzU5MSMHK7svcczWUutzp3q2Qc58YrGxf1DrDMCjfzjyUs9aQmpU5MXi5C6P+PbNZ71A9n/nouDMhZ04MXurTUbcKsEE7JstXL55oKKoPqVnzZWyIpU5spYIeuS3q9wwDo/fG9MDSbYaiOmXkizkxyD0adaphYAmfifoNw1BNJzZzEJoTg+x/o44zDKwgzZG+zzC0L+R0YgydFYgU9c2ocw2DTgza4oWoowwDE/hB1FmGQScGTUu7NHj2F9OwgbBODAQYnfZI1OmGQScGAoyu8tGiTgxqtT9qrWGgRHbB14lBLfZk+U4MULbbo95lGJrpxOzYwRDsFGBU6IKo6wzDdE3QrPkydm7Ax4103I6oDYaBin00y+fIrjAU9XZi5sTos5uiPmAYqNHbou42DPWFnDkx+mpr1LWGgbrfd7MC0zToxGAl6REauwwDDUmrYI82DDoxmJZ7wWiam6Fr6sSsTqRvdgowWuC0qKsNQ/X5ohOjTz4bdYlhoGVh9qhhqK4TMydGX6RHZNxlGGiZQz4ZqDbkdGL06c3CqjDa6PHMU8Mr68TMidEHDwowWuyUqMsNQzX5ohOj6y6N+gvDQAecFLXbMJTbiZkTo+sOp9PUMNABz0UdbxjKDTl7Jw7blix/jMTmqJ/J8vta1ketzvKP54oc3HSCpPmodG9W2in+O1m+7c53s3x37yq34HlMgNEhx2X5svuPGYryOrEXX2SlojfeH/W1qKdHgXN4FEBVV/pz9mX5MuPPl/jzbK3p769U2UVJ+ZTKnFh/pY5qe9R7svY9CDKddM9E/WXUH0/5GlYj0lVPRW00DDoxXu7KLN/upq4uq6x6IeqOrPiNyjv8Nq86Xpd6u9KJkbsw6h+zfNPbPhyM1GHdl+U3Ly/l/Cyfa4MuS79orhZS7hMbqhRYN47e8G/N8qW7fTkY6cJ+6+i31b1Rlx3x/ZsdfnogvfduH/oguE9seFIX8sVseJ+np99avx71z1GfcBrQp/dxndiMA2gJfWfC65bMM4qgb9Kq3dMMw/Qht6rIi1j40ZiLs/wGydsFGPTSptEvqYMNqVnzRSfWTmnO66EsvzkS6Ld0u8krDYNOrC++FbVLgMFgnJAtvxpXJ6YT64xPR/1uZhslGKI9fnGdrhOzOrEdnh79NgYM10XZwJbdW53YfZ+K2mYYAN2YTqxrdkZtMAzAIu+N+rKQ0om1WZrA/c/M5rXAyz0edaph0Im11XVRHzUMwEpvq0JKJ9ZGaXf5TYYBGOPObMA3QOvE2iltZGvHDaDQe3c2kOkGu9i3X9p545AAAybpDbJ86qH/P6hd7Fvt7CzffcMAAZPaP4Rffs2JtVdagXiXYQBm8NqoB4c8APZObMYlAgwowV8PIaTsndguF2b5k5YBZpUeBrtaJzZjiI19ASG3YHPUA4YBKNEbo3YMOaR0YvVIqxCfyiziAMqVtqd7lU5MJ1a1tIzeNlJA6e/jfX5vcZ9YO+wTYEBF0ptsb5904T6x5j2a2awTqNZ/Rb1eJ7a0NUJqajcKMKAGZ/W5E5s15HRi09kS9UXXFlCT07L8kx+d2KQhphNbUrp/w8AAdbkh6iND+6F1YtXYHfUTrimgRmkB2Xqd2MuZE5vMNQIMaEAvNwM2J1avdEPzLtcS0JBfyfIFZTqxSUJMJ/b/nos61jAADentUvtZQm7NrC8ykJC7WoABDTtjiCGlE5tdmkzdk1mNCDT8np8NbHcgzxMrxw4BBrRAeh/a0reQmjVf7J24sk1RP+naAVrig71KZXsnVm5v1tOlrUAnPRj12j51YrNyn9jytgkwoGV6tbjDfWLV2h+11jUDtKl5yXq0uEMnVp3LBBjQxuZFJ6YT04UBXfbGLF81rRPLrE5cytkCDGixP+tTJ2Z1YvlsLwW02VNRG3ViOXNiL3W+AANa7vg+dWKzhpxO7KW+H/Ua1wjQYumhvKt1YgVDbGCdmCc2A51oYobwQ9rFfjLbBBiATqyrLOgAuuKiqO1Czi72iwkwoCve3ZeQmjVfrE7MfdY1AXTIWX34IcpYnWhOLPch1wTQIa/uSyc2K51Y/uTmda4JoENO0onpxBb8uesB6JhX6MR0Ygsucj0AHdOLT490YuU4zfUAdO39XyemE0vSfNgq1wPQMWv68EPoxGb3664FQCfW3U5s6Dt2PBR1husB6Jg9Ucf1/YfUiY23ybUA0N1ObOhzYqudRgDNMCemCwMG2sToxHRif+o6ADrqkE5MJ/Yu1wGATqyrNjiFgI46qBPTia13HQAdtV8nphNb4zoAOmqPTkwnNuc6ADrqRzqxYXdim4UY0GFP68SG3Ym91jUAdNj3dWLD7sTOdA0AHfZvOrFhd2IbXQNAh92oExt2J2Z5PYBOrLMht9bpA3S1genND6ITm9rxrgOgow725QfRiQEMzzM6MZ3YAdcB0FF36cR0YvtcB0BHbdOJ/diqIkm5UnXU864DoKN+qU+d2Kz5MjdrEnY0yM6Out+1AHS5kYl6Nupfoz4TtX2IndjcABdubIm6LuqVrgGgRw5H7Y76o6i/70VKFwi5oXRiqfP6p6jXZDb+BYbh8agPR92hE+uu26LeEbXa+QwMuEO7N+qCqL06sfaH3IlZvsPzCc5dgJdID9P8+bZ0Z1YnvtTW0W8ZuwQYwJKOjbo96oWsBUv1rU7MXRH18cx+iACTSltYXRX1+13txLo8J3buqCVe5zwEmDnM0iKQVi3T7+ucWHqMyg+iXu28AyhV2pcxLQDZoROrxm2jAQagOt/O8luTWt+JrSryIitVTdJHhwcEGEAt3hB1KOriqkNq1nzpQid2T9SbnVMAjfhhlm8U0cpOrM1zYpujvpMV2GkfgEqlG6bfF/XlukNqnLbeJ3ZN1AMCDKAVUlbcGvWlMl+0r/eJPRm10TkD0EppBWMpG6j3cXXiAd0XQOuljxePyyrei7FLqxPTCph5AQbQCSk70sOFL501pPqwOjFtefLbzgmATroh6iNNdWJNz4ndFPUB5wBAp90ZdX4VIdXmTsz9XwD9kbYDPGsondh9Uec45gC9kp7luLnvndj3ol7nWAP00hNRp/S1E7s76q2OMYCOrGud2C1RP+fYAgzCN6LeXnUnVtd9YlcJMIBBOS/qxqrzpY69E9PNcO4DAxie9LToyyrMl8rnxNJTmJ93HAEG7bSoR5fqxGZV9ZzYoSLdHgC9lvZaTBu7757kP2p6deLTUSc4dgCEPVm+aXCpnVhVc2LbBRgAixwbdVsJ+TJZiE2xemRr1C87XgAc4YJs0c73bd3F3jwYAMv2RkUzokiQrZn1RY4IuYcEGAArxUbUzqiNbZsT2xp1huMDwBgbora17T6xw6OEBYBxIn7mZ/7kbuzHiQXT8HsCDIDJeqC5R7L8RujlUm7si5SxOvHczKNVAJjcpqgLm16duDfqaMcCgCm8ELWukU4sXC7AAJjB2qhPNNWJuScMgFmlhYGra+3Esvw5MQIMgFmlLLml7k5MFwZAWV62k0dlnVj4lAADoESpY7q6rk5MFwZApd1YJZ1YuFKAAVB1N1ZVJ7Y/y5dEAkDZDkYdVUknFrYIMAAqlLZDvKSqTiw9asVO9QBU6X+iTi4SZBPtYh+BNm9sAahaZFOhTeUneZ7YlYYVgDpE5lxT6N8r/Lnj3JwFHQDU5WDk01FldWKbBRgANVoT2XPuzCE26tSuMZ4A1OxvxjZaBVcn7suWed4LAFTkhciodTN1Yln+5E0BBkDd1kYjtWnWEPs14whAQ35rpW8W+Tjx8fhysnEEoAFPRU5tnCXE7FgPQFMipuaXzaAi4STAAGhK9FJz66cKqPgPrzJ+ADTs75bNqZU+TowQ2xlfNhg/ABq0K7JqwzQhdiDLt8UHgKYciqxaMovGzXetNnYANGzZLFq1QheWHoA5Z+wAaFpk0sWTdmIfNmwAtMSHJg2xnzZmALTE6ycNsTONGQAtccakIXaCMQOgJV4xaYh5CCYAbXHUpCFmZSIAbTEnxAAYTIgBQKsJMQCEGAAIMQAQYgAIMQAQYgAgxABAiAEgxABAiAGAEANAiAGAEAOAmkNs3vAA0BLzk4bYQWMGQEscnDTEnjVmALTEs5OG2JPGDICWeHLSEHvYmAHQEj+cNMS2GzMAWuJflvo/5+bnl16EODc3d2J82WXcAGiBkyKvdhcOsVGQHcrcSwZAsw5HVq1e6hvjAmqvsQOgYctm0bgQ+7qxA6Bhy2bRuI8T18eX540fAA06JrJq78QhNgoy208B0JjIqbnlvldk0YZODICm7Fnpm0VC7HPGEICG3LDSN4t8nHhufLnLOALQgLdFTt09dYiNgsz9YgDUbdn7wxaMDaYIsPTlIWMJQM3GZs/YEBt1apcZSwBq9gdjG60Cc2IL/+gjRQDqMvajxMKd2Cjo7jamANSkUOZM0om9mGnGFYAaFFp4WLgTG73YHuMKQMX2FAmwQiGWOrGFClcbWwAqdvURnwLO1q4dEWoWeABQlUILOqbqxEbJeLsxBqAity/Km/I7sVGwWeABQOlW2rG+lE5slI73G2oASnb/EVlTTSemGwOg6S5s6k5MNwZAlV1YHZ3Y+swDMwEoxzGRR3vr7MTSH2alIgCz+krKlFo7sUUh574xAKY10X1hpXVii5LyTxwDAKb08RXypZZOLH1Jc2PrHQsAJrA3MuiYmTJowl3sl3Ni1C7HA4AJnBS1e7lvlr6L/VI1kv4SNzseABR0c2TI7gL5UksntuCFqKMcGwDGZMW6Ik1UXZ3Ygnc4NgCM8c4p8mW6ECuwOnGx9Djp6x0fAJZx/SgrJs2XpTOqpNWJR/pR1CsdKwCOyIYTF/7HrPlTKMQmnBNbzE3QACw4HDXRTc1NzIkt9nbHDIDlMqGNc2KLpc88P+m4AQzeJ0eZUFa+/Pg1KpoTW+yrUe92DAEG6T+ifnapb7R9Tmyxx6NOdiwBBuWJqFOm/Y+LhFwdndiC56KOdUwBBmFP1HGzhlRbOrEF+7ICd2kD0Gn7o46e9UXa1oktsDUVQH8diFpbVki1rRMTZAACrNOd2OJ2c61jDtALhTb17UsntsDDNAG6L72Xl75wr+2d2IKdURucAwCd9FTUxqpCapwqd+woKv3w9zoPADrn3mkDrKx8qXLvxEm8Jepa5wNAZ1w7eu+eWhn50vSc2JG2RH0h/bHOD4BWSqHxwajtlf9BHZkTW4oFHwDtU+oCjr7MiS3lmKivOF8AWuMrWckrELuyi/0s3h91U+bhmgBNSQ+z/MWoL5X9wn24T6yo+6LOcS4B1OpbUW9q6g9v+snOZUqD+AtRB51TAJU7OHrPrTTA2v5k57KlVjbtt5iWdc47xwDKz5XRe+xRWQUfH1aRL22fE1vJw1GnOecASvFI1Om1JmaPVycWcfooxJ5w7gFM7YnRe+npdf/BQ+/EFtsUdU/USc5HgEJ2ZfmOG4829RcY0urESTw8CjW7fgAckRuj0Dq9E3/ZHq1OnMTpo58rbV9lNSNA/l74hdF7Y2sCrI97J1YhdWXfiHqV7gwYWNf1ZNR5WYMfGerEZpcO3smjn/WTWf45sCX6QF+Da9fovW7V6L2vtQGmE5vNJVG/E/XOqDXOfaCj0keFd0b9VdT1vUrkDu9i34T0GJhPRZ0xCjUfPQJt7LQOZPkCtj/MangcStMhpROb3tlRvxn1jqhTs/zppUePWnQBB1QZVGnT3X1RO6Mei/pa1N9G7RjUQNTRiQFAUzziBAAhBgBCDAAK+j8BBgAfFp0ddCTwbQAAAABJRU5ErkJggg==';
var maskImage = new Image();
maskImage.src = url;
function newCloudChart(data,dom,col) {
    data = data[0].data;
    var sizeMin=12;
    var gridSize=0.7;//���
    var chart2 = echarts.init(document.getElementById(dom));
    var colors = col;
    var option = {
        tooltip: {
            show: true,
            formatter: function (params) {
                var num = params.value;
                num = parseInt(num) / 10;
                return params.name + " : " + num;
            }
        },
        toolbox: {
            show: true,
            orient: 'vertical',
            y: 30,
            x: 'right',

            feature: {
                mark: {show: false},
                dataView: {
                    show: false,
                    readOnly: false,
                    lang: ['������ͼ', '�ر�', 'ˢ��']
                },
                restore: {show: true},
                saveAsImage: {
                    show: true
                }
            }
        },
        textStyle: {
            normal: {
                color: function() {
                    var i = parseInt(Math.random() * 4);
                    return colors[i];
                }
            }
        },
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
function goNewMTChrt(dom,da,na,dt,co){
    var cv=[];
    for(var i=0;i<11;i++){
        cv.push(Math.max.apply(null, da)+100);
    }
    var mapDisChart = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // ������ָʾ���������ᴥ����Ч
                type : 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
            }
        },
        legend: {
            data:na
        },
        grid: {
            left: '3%',
            right: '6%',
            bottom: '0%',
            top:'10%',
            containLabel: true
        },

        xAxis : [
            {
                axisLine:{//���������� Ĭ�� true,
                    show: false

                },
                axisTick:{//������̶�
                    show: false
                },
                splitLine:{
                    show: false
                },
                axisLabel:{//������̶ȱ�ǩ
                    show: false
                }
            }
        ],
        yAxis : [
            {
                type : 'category',
                inverse:true,
                axisLine:{//���������� Ĭ�� true,
                    show: false
                },
                axisTick:{//������̶�
                    show: false
                },
                axisLabel:{//������̶ȱ�ǩ
                    show: true,
                    textStyle:{
                        color:'#888'
                    }
                },

                boundaryGap : true,
                data : dt
            },
            {
                inverse:true,
                axisLine:{//���������� Ĭ�� true,
                    show: false
                },
                axisTick:{//������̶�
                    show: false
                },
                boundaryGap: true,
                data:da
            }
        ],
        series : [
            {
                type: 'bar',
                itemStyle: {
                    normal: {color: 'rgba(0,0,0,0.2)'}
                },
                barGap:'-100%',
                barCategoryGap:'20%',
                data:cv,
                animation: false
            },
            {
                name:na[0],
                type:'bar',
                barWidth: '40%',
                itemStyle:{
                    normal:{
                        color: co
                    }
                },
                data:da
            }
        ]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    mapDisChart.setOption(option);
}

    function emotion(param){
        $.ajax({
            url : 'spreadTrend.action',
            type : "post",
            data :param,
            success : function(data){
            	//GaugeCallBack(data[0]);
            	//sensitiveCallBack(data,"container3");
            	var id = "${pab.productsAnalysisId}";
            	if(id>0){
            		//�ɵ�
            		GaugeCallBack(data);
            	}else{
            		//�µ�
            		sensitiveCallBack(data,"container3");
            	}
            }
        });
    }
    function sensitiveCallBack(result, id){
        var dom = document.getElementById(id);
        var data = eval(result[0]);
        if (data == null || data == ""){
        	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            if(data[0].data==null || data[0].data.length==0){
            	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else{
                var name = [];
                var zmData = [];
                var fmData = [];
                $.each(data[0].data, function(){
                    var total = this.data[0] + this.data[1];
                    var zmPer = (this.data[1]*100/total).toFixed(2);
                    var fmPer = (this.data[0]*100/total).toFixed(2);
                    name.push(this.name);
                    zmData.push(zmPer);
                    fmData.push(fmPer);
                })
                sensitiveChart(name, zmData, fmData, dom);
            }
        }
    }
    function sensitiveChart(name, zmData, fmData, dom){
        //�°���з���ռ�ȣ���״ͼ��
        var chart = echarts.init(dom);
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: { // ������ָʾ���������ᴥ����Ч
                    type: 'shadow' // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
                }
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            legend: {
                data: ['���У�%��', '�����У�%��']
            },
            grid: {
                left: '3%',
                right: '5%',
                bottom: '3%',
                containLabel: true

            },
            xAxis: [{
                type: 'category',
                splitNumber:6,
                axisTick: {
                    alignWithLabel: false
                },
                data: name
            }],
            yAxis: [{
                type: 'value',
                name:'��λ��%��',
                min: 0,
                max: 100,
                position: 'left',
                axisLabel: {
                    formatter: '{value} %'
                }
            }],
            series: [{
                name: '�����У�%��',
                type: 'bar',
                stack: 's1',
                barWidth:45,
                itemStyle: {
                    normal: {
                        color: '#0e7ec0',
                        borderColor: '#fbfbfd'
                    }
                },
                data: zmData
            },{
                name: '���У�%��',
                type: 'bar',
                stack: 's1',
                barWidth:45,
                itemStyle: {
                    normal: {
                        color: '#ce421e',
                        borderColor: '#fbfbfd',
                    }
                },
                data: fmData
            }]
        };
        chart.setOption(option);
    }
    function territory(param){
        $.ajax({
            url : 'spreadTrend.action',
            type : "post",
            data :param,
            success : function(data){
            	/* var d = JSON.parse(data);
            	if(d.length>1){
            		data = d[0];
            	}
                MapCallBack(data); */
            	//�µ�
        		//newMapCallBack(data,"container2");
                
            	var id = "${pab.productsAnalysisId}";
            	if(id>0){
            		//�ɵ�
            		var d = JSON.parse(data);
                	if(d.length>1){
                		data = d[0];
                	}
                    MapCallBack(data);
            	}else{
            		//�µ�
            		$("#container2").height(450);
            		newMapCallBack(data,"container2");
            	}
            }
        });
    }
//     var newChinaMapDate = "";
//     function newMapCallBack(data, id){
//         var dom = document.getElementById(id);
//         var mapTop10 = document.getElementById("mapTop10");
//         if (data.length == 0){
<%--         	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//             return;
//         }else{
//             var res = eval(data[0]);
//             newChinaMapDate=res[0].data;
//             var chinaHtml='';
//             var topData = res[0].topData;
//             var html = '';
//             if(topData==null || topData.length==0|| topData[0]==null){
<%--             	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//                 return;
//             }else{
//                 var a = ["bg_army_green", "bg_orange","bg_cobalt_blue","bg_dark_grey", "bg_blue","bg_brown"];
//                 var h=0;
//                 for(var i=0;i<topData.length;i++){
//                     if((h==0) || (h==3 && topData.length!=4) || (topData.length==4 && h==2)){
//                         html+='<div class="row row5 clearfix">';
//                     }
//                     if(topData.length==2 || topData.length==4 ){
//                         html += '<div class="col-md-6">';
//                     }else{
//                         html += '<div class="col-md-4">';
//                     }
//                     html+='<div class="panel panel-sm">';
//                     html+='<div class="panel-heading '+a[i]+' fc_white align_c">';
//                     html+='<h3 class="panel-title">';
//                     html+=eval(topData[i]).name;
//                     chinaHtml+='<label class="btn btn-sm" id="labelActive'+i+'" onclick="goNewChinaShow('+i+')">';
//                     chinaHtml+=eval(topData[i]).name;
//                     chinaHtml+='</label>';
//                     html+='</h3>';
//                     html+='</div>';
//                     html+='<div class="panel-body pading0">';
//                     html+='<div class="table-in-noborder">';
//                     html+='<table border="0" cellspacing="0" cellpadding="0" class="table table-sm" width="100%">';
//                     html+='<tbody>';
//                     var subData = topData[i].topData;
//                     if(subData==null || subData[0]==null){
<%--                         html += "<tr><td colspan='3'style='border:#ccc 1px dashed;background-color:#fff;'> <div align=\"center\" style=\"padding-top:50px;background-color:#fff;color:#000;\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div></td></tr></tbody></table></li>"; --%>
//                     }else{
//                         for(var j=0;j<10;j++){
//                             var topName = "";
//                             var topValue= "";
//                             if(j<subData.length){
//                                 topName = subData[j]["name"];
//                                 topValue = subData[j]["value"];
//                             }
//                             html+='<tr>';
//                             html+='<td>'+(j+1)+'</td>';
//                             html+='<td>'+topName+'</td>';
//                             html+='<td>'+commafy(topValue)+'</td>';
//                             html+='</tr>';
//                         }
//                         var sum=0;
//                         for(var k=0;k<subData.length;k++){
//                             topValue = subData[k]["value"];
//                             sum += parseInt(topValue);
//                         }

//                     }
//                     html+='</tbody>';
//                     html+='</table>';
//                     html+='</div>';
//                     html+='</div>';
//                     html+='</div>';
//                     html+='</div>';
//                     h++;
//                     if(h==3&& topData.length!=4){
//                         html+='</div>';
//                     }
//                 }
//                 if(h>2){
//                     html+='</div>';
//                 }
//                 mapTop10.innerHTML = html;
//                 $("#container2_sel").html(chinaHtml);
//                 goNewChinaShow(0);
//             }
//         }
//     }
    function goNewChinaShow(flag){
    	var data;
        var color;
        var title;
        var col=[["#b8ebd5","#72d2a9","#3fa579"],["#e3dcdf","#f1b192","#f18d00"],["#d0e3ff","#829bd5","#526dab"],["#b1b5c2","#c6b9a1","#6e7584"],["#a8dbf8","#43a6e0","#0e7dc0"],["#d8a677","#b17b47","#b25048"]];
        var a = ["bg_army_green", "bg_orange","bg_cobalt_blue","bg_dark_grey", "bg_blue","brown"];
        for(var i=0;i<newChinaMapDate.length;i++){
            if(flag==i){
                var cc=$("#container2_sel").find(".active").attr("class","btn btn-sm");
                $("#labelActive"+i).attr("class","btn btn-sm active "+a[i]);
                color=col[i];
                data=newChinaMapDate[i].data;
                title=newChinaMapDate[i].name;
            }
        }
        goNewGoChinaMap(data,"container2",color,title);
    }
    function goNewGoChinaMap(data,dom,color,title){
        //����ֲ�
        var mapChart = echarts.init(document.getElementById(dom));
        function randomData() {
            return Math.round(Math.random()*1000);
        }
        // ָ��ͼ��������������
        var option = {

            tooltip: {
                trigger: 'item'
            },
            toolbox: {
                orient:'vertical',
                feature: {
                    saveAsImage: {}
                }
            },
            visualMap: {
                min: 0,
                max: data[0].value,
                left: '0',
                bottom: '20',
                text: ['��','��'],           // �ı���Ĭ��Ϊ��ֵ�ı�
                calculable: true,
                inRange: {
                    color: color
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
                            borderColor: '#fff',
                            areaColor: '#efefef',//��ͼ������ɫ
                        },
                        emphasis: {
                            areaColor: '#bdd3f5'
                        }
                    }
                },
                {
                    name: title,
                    type: 'map',
                    mapType: 'china',
                    label: {
                        normal: {
                            show: true
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:data
                }

            ]
        };

        // ʹ�ø�ָ�����������������ʾͼ��
        mapChart.setOption(option);
    }
    function source(param){
        $.ajax({
            url : 'spreadTrend.action',
            type : "post",
            data :param,
            success : function(data){
                /* MTCallBack(data); */
            	//newMTCallBack(data,"container4");
            	var id = "${pab.productsAnalysisId}";
            	if(id>0){
            		//�ɵ�
            		 MTCallBack(data);
            	}else{
            		//�µ�
            		//$("#container4").height();
            		newMTCallBack(data,"container4");
            	}
            }
        });
    }
    var newChinaMapDate = "";
    function newMapCallBack(data, id){
        var dom = document.getElementById(id);
        var mapTop10 = document.getElementById("mapTop10");
        var str='';
        if (data.length == 0){
        	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            var res = eval(data[0]);
            newChinaMapDate=res[0].data;
            var chinaHtml='';
            var topData = res[0].topData;
            var html = '';
            str+='�ڷ���ʱ����ڣ�';
            if(topData==null || topData.length==0|| topData[0]==null){
            	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else{
                var a = ["bg_army_green", "bg_orange","bg_cobalt_blue","bg_dark_grey", "bg_blue","bg_brown"];
                var h=0;
                for(var i=0;i<topData.length;i++){
                    if((h==0) || (h==3 && topData.length!=4) || (topData.length==4 && h==2)){
                        html+='<div class="row row5 clearfix">';
                    }
                    if(topData.length==2 || topData.length==4 ){
                        html += '<div class="col-md-6">';
                    }else{
                        html += '<div class="col-md-4">';
                    }
                    html+='<div class="panel panel-sm">';
                    html+='<div class="panel-heading '+a[i]+' fc_white align_c">';
                    html+='<h3 class="panel-title">';
                    html+=eval(topData[i]).name;
                    chinaHtml+='<label class="btn btn-sm" id="labelActive'+i+'" onclick="goNewChinaShow('+i+')">';
                    chinaHtml+=eval(topData[i]).name;
                    chinaHtml+='</label>';
                    html+='</h3>';
                    html+='</div>';
                    html+='<div class="panel-body pading0">';
                    html+='<div class="table-in-noborder">';
                    html+='<table border="0" cellspacing="0" cellpadding="0" class="table table-sm" width="100%">';
                    html+='<tbody>';
                    var subData = topData[i].topData;
                    if(subData==null || subData[0]==null){
                        html += "<tr><td colspan='3'style='border:#ccc 1px dashed;background-color:#fff;'> <div align=\"center\" style=\"padding-top:50px;background-color:#fff;color:#000;\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div></td></tr></tbody></table></li>";
                    }else{
                        for(var j=0;j<10;j++){
                            var topName = "";
                            var topValue= "";
                            if(j<subData.length){
                                topName = subData[j]["name"];
                                topValue = subData[j]["value"];
                            }
                            html+='<tr>';
                            html+='<td>'+(j+1)+'</td>';
                            html+='<td>'+topName+'</td>';
                            html+='<td>'+commafy(topValue)+'</td>';
                            html+='</tr>';
                        }
                        var sum=0;
                        for(var k=0;k<subData.length;k++){
                            topValue = subData[k]["value"];
                            sum += parseInt(topValue);
                        }
                        if(sum>0){
                            var first = ((parseInt(parseInt(subData[0]["value"])) / sum) * 100).toFixed(2);
                            str+=subData[0]["name"]+'������"'+eval(topData[i]).name+'"���ἰ�����ռ��Ϊ<font class="f_c2">'+first+'%</font>;';
                            $("#container2_msg").html(str);
                        }
                    }
                    html+='</tbody>';
                    html+='</table>';
                    html+='</div>';
                    html+='</div>';
                    html+='</div>';
                    html+='</div>';
                    h++;
                    if(h==3&& topData.length!=4){
                        html+='</div>';
                    }
                }
                if(h>2){
                    html+='</div>';
                }
                mapTop10.innerHTML = html;
                $("#container2_sel").html(chinaHtml);
                goNewChinaShow(0);
                
            }
        }
    }
//     var newChinaMapDate = "";
//     function newMapCallBack(data, id){
//         var dom = document.getElementById(id);
//         var mapTop10 = document.getElementById("mapTop10");
//         var str='';
//         if (data.length == 0){
<%--         	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//             return;
//         }else{
//             var res = eval(data[0]);
//             newChinaMapDate=res[0].data;
//             var chinaHtml='';
//             var topData = res[0].topData;
//             var html = '';
//             str+='�ڷ���ʱ����ڣ�';
//             if(topData==null || topData.length==0|| topData[0]==null){
<%--             	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//                 return;
//             }else{
//                 var a = ["bg_army_green", "bg_orange","bg_cobalt_blue","bg_dark_grey", "bg_blue","bg_brown"];
//                 var h=0;
//                 for(var i=0;i<topData.length;i++){
//                     if((h==0) || (h==3 && topData.length!=4) || (topData.length==4 && h==2)){
//                         html+='<div class="row row5 clearfix">';
//                     }
//                     if(topData.length==2 || topData.length==4 ){
//                         html += '<div class="col-md-6">';
//                     }else{
//                         html += '<div class="col-md-4">';
//                     }
//                     html+='<div class="panel panel-sm">';
//                     html+='<div class="panel-heading '+a[i]+' fc_white align_c">';
//                     html+='<h3 class="panel-title">';
//                     html+=eval(topData[i]).name;
//                     chinaHtml+='<label class="btn btn-sm" id="labelActive'+i+'" onclick="goNewChinaShow('+i+')">';
//                     chinaHtml+=eval(topData[i]).name;
//                     chinaHtml+='</label>';
//                     html+='</h3>';
//                     html+='</div>';
//                     html+='<div class="panel-body pading0">';
//                     html+='<div class="table-in-noborder">';
//                     html+='<table border="0" cellspacing="0" cellpadding="0" class="table table-sm" width="100%">';
//                     html+='<tbody>';
//                     var subData = topData[i].topData;
//                     if(subData==null || subData[0]==null){
<%--                         html += "<tr><td colspan='3'style='border:#ccc 1px dashed;background-color:#fff;'> <div align=\"center\" style=\"padding-top:50px;background-color:#fff;color:#000;\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div></td></tr></tbody></table></li>"; --%>
//                     }else{
//                         for(var j=0;j<10;j++){
//                             var topName = "";
//                             var topValue= "";
//                             if(j<subData.length){
//                                 topName = subData[j]["name"];
//                                 topValue = subData[j]["value"];
//                             }
//                             html+='<tr>';
//                             html+='<td>'+(j+1)+'</td>';
//                             html+='<td>'+topName+'</td>';
//                             html+='<td>'+commafy(topValue)+'</td>';
//                             html+='</tr>';
//                         }
//                         var sum=0;
//                         for(var k=0;k<subData.length;k++){
//                             topValue = subData[k]["value"];
//                             sum += parseInt(topValue);
//                         }
//                         if(sum>0){
//                             var first = ((parseInt(parseInt(subData[0]["value"])) / sum) * 100).toFixed(2);
//                             str+=subData[0]["name"]+'������"'+eval(topData[i]).name+'"���ἰ�����ռ��Ϊ<font class="f_c2">'+first+'%</font>;';
//                             $("#container2_msg").html(str);
//                         }
//                     }
//                     html+='</tbody>';
//                     html+='</table>';
//                     html+='</div>';
//                     html+='</div>';
//                     html+='</div>';
//                     html+='</div>';
//                     h++;
//                     if(h==3&& topData.length!=4){
//                         html+='</div>';
//                     }
//                 }
//                 if(h>2){
//                     html+='</div>';
//                 }
//                 mapTop10.innerHTML = html;
//                 $("#container2_sel").html(chinaHtml);
//                 goNewChinaShow(0);
                
//             }
//         }
//     }
    function newMTCallBack(data, id){
        var dom = document.getElementById(id);
        if (data==null||data==""){
        	 dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
             return;
        }else{
            var res = eval(data[0]);
            if(res==null || res[0]==null){
            	 dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
                 return;
            }else{
            	var alertMsg = '';
                data = res[0];
                var arrSource = [];
                var dateTime = data.legend;
                var html = '';
                for(var i=0;i<dateTime.length;i++) {
                    if((i==0) || (i==3 && dateTime.length!=4) || (dateTime.length==4 && i==2)){
                        html += '<div class="row clearfix">';
                    }
                    if(dateTime.length==2 || dateTime.length==4 ){
                        html += '<div class="col-md-6">';
                    }else{
                        html += '<div class="col-md-4">';
                    }
                    html += '<div class="chart-bady" id="mapDisChart'+i+'" style="height: 400px;"></div>';
                    html += '</div>';
                    if(i==3&& dateTime.length!=4){
                        html+='</div>';
                    }
                }
                var topData = data.topData;
                for(var i=0;i<topData.length;i++) {
                    if (typeof(topData[i]) != "undefined" && topData[i] != null) {
                        alertMsg += '���"' + topData[i]["label"] + '"�ı�����Ҫ������' + topData[i]["name"] + '���ﵽ��<font class="f_c2">' +commafy(topData[i]["value"])  + '</font>����';
                        arrSource[i] = topData[i]["value"];
                    }
                }
                var maxIndexSource=arrSource.indexOf(Math.max.apply(Math, arrSource));
                alertMsg+='�Ա�����"'+topData[maxIndexSource]["label"]+'"ȫ����Ϣ������';
                $("#container4_msg").html(alertMsg);
                if(dateTime.length>2){
                    html+='</div>';
                }
                dom.innerHTML = html;

                var color = ["#3fad7e","#f18d00","#5573b6","#6f7786","#0086ce","#b67e48"];
                for(var i=0;i<dateTime.length;i++) {
                	goNewMTChrt("mapDisChart"+i,data.data[i].data,[dateTime[i]],data.datetime,color[i]);
                }
            }

        }
    }
//     function newMTCallBack(data, id){
//         var dom = document.getElementById(id);
//         if (data==null||data==""){
<%--         	 dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//              return;
//         }else{
//             var res = eval(data[0]);
//             if(res==null || res[0]==null){
<%--             	 dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>"; --%>
//                  return;
//             }else{
//                 data = res[0];
//                 var arrSource = [];
//                 var dateTime = data.legend;
//                 var html = '';
//                 for(var i=0;i<dateTime.length;i++) {
//                     if((i==0) || (i==3 && dateTime.length!=4) || (dateTime.length==4 && i==2)){
//                         html += '<div class="row clearfix">';
//                     }
//                     if(dateTime.length==2 || dateTime.length==4 ){
//                         html += '<div class="col-md-6">';
//                     }else{
//                         html += '<div class="col-md-4">';
//                     }
//                     html += '<div class="chart-bady" id="mapDisChart'+i+'" style="height: 400px;"></div>';
//                     html += '</div>';
//                     if(i==3&& dateTime.length!=4){
//                         html+='</div>';
//                     }
//                 }
//                 if(dateTime.length>2){
//                     html+='</div>';
//                 }
//                 dom.innerHTML = html;

//                 var color = ["#3fad7e","#f18d00","#5573b6","#6f7786","#0086ce","#b67e48"];
//                 for(var i=0;i<dateTime.length;i++) {
//                 	goNewMTChrt("mapDisChart"+i,data.data[i].data,[dateTime[i]],data.datetime,color[i]);
//                 }
//             }

//         }
//     }

function mediaActive(param){
    $.ajax({
        url : 'spreadTrend.action',
        type : "post",
        data :param,
        success : function(data){
            var data=eval(data);
            //OriginCallBack(data);
            //newOriginCallBack(data,"container5");
            var id = "${pab.productsAnalysisId}";
        	if(id>0){
        		//�ɵ�
        		var data=eval(data);
                OriginCallBack(data);
        	}else{
        		//�µ�
        		data.length;
        		var newData=eval(data[0]);
            	var lengh=newData.length;
            	var c5_height=lengh*360;
            	$("#container5").height(c5_height);
        		newOriginCallBack(data,"container5");
        	}
        }
    });
}
function newOriginCallBack(data, id){
    var dom = document.getElementById(id);
    if (data==null||data.length < 1){
    	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        data = eval(data[0]);
        var html = '';
        var h=0;
        var a = ["bg_army_green", "bg_orange","bg_cobalt_blue","bg_dark_grey", "bg_blue","bg_brown"];
        for(var i = 0;i<data.length;i++){
            var title = data[i]["groupValue"];
            if(title.length>6){
                title = title.substring(0,6);
            }
            if((h==0) || (h==3 && data.length!=4) || (data.length==4 && h==2)){
                html += '<div class="row clearfix">';
            }
            if(data.length==2 || data.length==4 ){
                html += '<div class="col-md-6">';
            }else{
                html += '<div class="col-md-4">';
            }
            html += '<div class="panel">';
            html += '<div class="panel-heading '+a[i]+' fc_white align_c">';
            html += '<h3 class="panel-title">';
            html += title;
            html += '</h3>';
            html += '</div>';
            html += '<div class="panel-body bg_gray-50">';
            html += '<div class="chart-bady" id="mediaChart1'+i+'" style="height: 300px;"></div>';
            html += '</div>';
            html += '</div>';
            html += '</div>';
            h++;
            if(h==3&& data.length!=4){
                html+='</div>';
            }
        }
        if(h>2){
            html+='</div>';
        }
        dom.innerHTML = html;
        var color = ["#f28d09","#e89164","#ce421f","#b07b47","#924f24","#6e7683","#3ba479","#516dac"];
        for(var i = 0;i<data.length;i++){
            var map = data[i]["subGroups"];
            if(map==null||map.length==0){
                $("#mediaChart1"+i).html("<div style='text-align:center;padding-top:90px' ><p style='display:inline;font-size: 14px;'><img src='<%=staticResourcePath %>/images/shouye/warn.png'><br/>��ʱ���������Ϣ</p></div></div>");
            }else{
                if(map.length>8){
                    map.length=8;
                }
                var da=[];
                var length=[];
                for(var j=0;j<map.length;j++){
                    var gv=map[j]["groupValue"].slice(0,4);
                    var tot=map[j]["total"];
                    if(tot!=0){
                        var dat = {value:tot, name:gv,itemStyle:{normal:{color:color[j]}},label:{normal:{textStyle:{fontSize:12}}}};
                        da.push(dat);
                        length.push(gv);
                    }
                }
                if(da.length>0){
                	goNewCanvasTrigger("mediaChart1"+i,da,data[i]["groupValue"],length);
                }else{
                    $("#mediaChart1"+i).html("<div style='text-align:center;padding-top:90px' ><p style='display:inline;font-size: 14px;'><img src='<%=staticResourcePath %>/images/shouye/warn.png'><br/>��ʱ���������Ϣ</p></div></div>");
                }
            }
        }
    }
}
function goNewCanvasTrigger(dom,data,title,leng){
    var fole = false;
    if(leng.length==1 && leng[0]=='����΢��'){
        fole = true;
    }
    //ý���Ծ�ȶԱ�
    var mediaChart1 = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
    var option = {
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            itemGap: 10,
            itemWidth: 10,
            itemHeight: 10,
            x: 'left',
            data: leng,
            selected: {
                '����΢��': fole
            }
        },
        series : [
            {
                name:title,
                type:'pie',
                radius : [30, 80],
                center : ['50%', '50%'],
                roseType : 'area',
                data:data
            }
        ]
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    mediaChart1.setOption(option);
}

function pointMedia(param){
    $.ajax({
        url : 'spreadTrend.action',
        type : "post",
        data :param,
        success : function(data){
            //CaptureWebsiteCallBack(arrData);
        	//newCaptureWebsiteCallBack(data,"container6");
        	var id = "${pab.productsAnalysisId}";
        	if(id>0){
        		//�ɵ�
        		CaptureWebsiteCallBack(data);
        	}else{
        		//�µ�
        		//var arrData=JSON.parse( data );;
        		newCaptureWebsiteCallBack(data,"container6");
        	}
        }
    });
}
function newCaptureWebsiteCallBack(data, id){
    var dom = document.getElementById(id);
    if (data==null||data.length < 1){
    	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var res = eval(data[0]);
        if(res==null || res[0]==null){
        	dom.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	goNewCaptureWebsite(id, res[0]);
        }
    }
}
function goNewCaptureWebsite(dom, data){
    $.each(data.data, function(){
        this.barWidth = '10%';
    });
    //��״ͼ����
    var keyMediaChart = echarts.init(document.getElementById(dom));

    // ָ��ͼ��������������
    var option = {
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // ������ָʾ���������ᴥ����Ч
                type : 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
            }
        },
        legend: {
            top: '5px',
            data:data.legend
        },
        grid: {
            left: '0%',
            right: '1%',
            bottom: '5%',
            containLabel: true
        },
        yAxis : [
            {
                type : 'value',
                axisLine:{//���������� Ĭ�� true,
                    show: true,
                    lineStyle:{
                        color:'#dbdbdb',
                        width: 3,
                        type: 'solid'
                    }
                },
                axisTick:{//������̶�
                    show: false

                },
                splitLine:{
                    show: true,
                    lineStyle:{
                        color:'rgba(0,0,0,0.05)'
                    }
                },
                splitArea:{
                    show: true,
                    areaStyle:{
                        color: ['rgba(0,0,0,0.05)','rgba(250,250,250,0)']
                    }
                },
                axisLabel:{//������̶ȱ�ǩ
                    show: false

                }
            }
        ],
        xAxis : [
            {
                type : 'category',
                axisLine:{//���������� Ĭ�� true,
                    show: true,
                    lineStyle:{
                        color:'#dbdbdb',
                        width: 3,
                        type: 'solid'
                    }
                },
                splitLine:{
                    show: true,
                    lineStyle:{
                        color:'rgba(0,0,0,0.1)'
                    }
                },
                axisTick:{//������̶�
                    show: false

                },
                axisLabel:{//������̶ȱ�ǩ
                    show: true,
                    //rotate: 30,  //��ת�Ƕ�
                    textStyle:{
                        color:'#888'
                    }
                },

                boundaryGap : true,
                data : data.datetime
            }
        ],
        color : ["#3fad7e","#f18d00","#5573b6","#6f7786","#0086ce","#b67e48"],
        series : data.data
    };

    // ʹ�ø�ָ�����������������ʾͼ��
    keyMediaChart.setOption(option);
}

	 //���ص���Ʒ������ҳ
	 function goBack(){
		 <%-- window.location.href="<%=njxBasePath%>/productsAnalysis.shtml"; --%>
		 window.history.back();
	 }

</script>

    <style id = "style">
        @media only screen and (max-width:600px ) {
            .mobileStyle #top,.mobileStyle #head nav,.mobileStyle #head .nav{display: none;}
            .mobileStyle #head{position: fixed; z-index: 100; top: 0; width: 100%;}
            .mobileStyle #head .logo{margin-left: 10px;}
            .mobileStyle #head .r_btn{right: 15px;}
            .mobileStyle .phoneTopHeight{margin-top: 70px;}
            .mobileStyle #footer,.mobileStyle .h35{display: none;}
            .mobileStyle #head .logo{border: none;}
        }
    </style>
</head>
<body class="mobileStyle">
<img  src="<%=staticResourcePathH5 %>/images/case-analysis-n.png?v=<%=SYSTEM_INIT_TIME %>" style="width: 100%;">
<input value="${isApp}" type="hidden" id = "isApp">
<!-- header start -->
	<s:include value="/view/productsAnalysis/tips.jsp">
		</s:include>
	<s:include value="/view/productsAnalysis/navigate.jsp">
		<s:param name="currentPage">productsAnalysis</s:param>
	</s:include>
	<script type="text/javascript">saveOperateLog('��Ʒ�����ԱȽ��','1025');</script> 
	<s:if test = 'isApp!=1'>
		 <section class="section" style="border-left:solid 6px #fd8c25;">
            <h2 class="float_l">��������</h2>
            <div style="margin-top: 12px;margin-right:20px;" class="float_r"><a href="<%=njxBasePath %>/productsReport.shtml" class="">����</a></div>
        </section>
	</s:if>
<!-- header end -->
<div class="page-container2 phoneTopHeight" <s:if test = "#attr.isApp == 1">style = 'margin-top:0px;'</s:if>>
	<!--�������� start-->
<div class="content">
	<div class="row-fluid">
		<div class="wrapCon">
			<div class="con Report-header">
	             <div style="width:100%;height:65px;">
	                 <div class="info" style="background-color:#FFF;">
			               	<div class="float_l align_l" style="width:100%;font-weight:bold;">
			                   	<p>${title}�ĶԱȷ���</p>                        
			               	</div>
			               	<div class="float_l">
			               		<img style="height:20px;width:20px;" src="<%=staticResourcePathH5 %>/images/daily/report.jpg?v=<%=SYSTEM_INIT_TIME %>">
			               		<span style="margin-left:10px;">΢�ȵ�</span>
			               		<img style="height:15px;width:15px;" src="<%=staticResourcePathH5 %>/images/daily/v_blue.png?v=<%=SYSTEM_INIT_TIME %>">
			               	</div>
			               	<div style="margin-left:20px;float:left;color:#a6a6a6;weight:1px;line-height: 22px;height: 22px;font-size: 14px;">|</div>
			               	<div class="float_l align_l" style="margin-left:20px;line-height: 22px;height: 22px;">
			               	<span id="starttime_show"><fmt:formatDate value="${pab.startTime}" pattern="yyyy-MM-dd"/></span>��<span id="endtime_show"><fmt:formatDate value="${pab.endTime}" pattern="yyyy-MM-dd"/></span>
			               	</div>
	             			<div class="clear"></div>
	         			</div>
	             	</div>
	            </div>
		</div>
	</div>
	<div class="row-fluid">
       	<form name="frmPopWin" method="post" action="">
	       	<input type="hidden" name="selectedTime" id="selectedTime" value="1"/>
	       	<input type="hidden" name="pab.title" value="${title}"/>
	       	<input type="hidden" name="pab.startTime" id="starttime" value="${starttime}"/>
	       	<input type="hidden" name="pab.endTime" id="endtime" value="${endtime}"/>
	       	<input type="hidden" name="pab.productsAnalysisId"  value="${pa.id}"/>
	       	<input type="hidden" id="contextPath" name="contextPath" value="${contextPath}"/>
			<input type="hidden" id="viewPath" name="viewPath" value="${viewPath}"/>
       	</form>
       	<!--�������Ʒ����Ա� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>�������Ʒ����Ա�</h2><%-- <span class="small">��${starttime}��${endtime}</span> --%>
    	    </div>
    	    <div class="row-fluid">
    			<div class="span7 borderBox chartBox float_l" id="container1" style="height:350px;">
    				<!-- ��������ͼ -->
    			</div>
    			<div class="span5 borderBox chartBox float_r">
    				<div class="descriptionCon">
    					<h3><img src="<%=staticResourcePathH5 %>/images/circle_help_question-mark-16.png"/> ����˵����</h3>                                                     
						<p id="container1_msg">
<%-- 						${pab.tendDesc} --%>
						</p>
    				</div>
                  
    			</div>
    		</div>
       	</div>
       	<!--�������Ʒ����Ա� end-->

   		<!--�������ƶԱ� start-->
       <div class="wrapCon">
            <div class="tit" style="border-bottom: 1px solid #e8eddd;">
                <h2>�������ƶԱ�</h2>
            </div>
            <div class="row-fluid">
                <div class="span7 borderBox chartBox float_l" id="container8" style="height: 350px;">

                </div>
                <div class="span5 borderBox chartBox float_r">
                    <div class="descriptionCon">
                        <h3>
                            <img src="<%=staticResourcePathH5 %>/images/circle_help_question-mark-16.png">
                            ��������˵����
                        </h3>
                        <p id="container8_msg">
<%-- 							${pab.negativeDesc} --%>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <!--�������ƶԱ� end-->
         <!--�����ȶȶԱ� start-->
         <!-- 
            <div class="wrapCon">
                <div class="tit" style="border-bottom: 1px solid #e8eddd;">
                    <h2>�����ȶȶԱ�</h2>
                </div>
                <div class="row-fluid">
                    <div class="span7 borderBox chartBox float_l" id="container11" style="height: 350px;">

                    </div>
                    <div class="span5 borderBox chartBox float_r">
                        <div class="descriptionCon">
                            <h3>
                                <img src="images/circle_help_question-mark-16.png">
                                �����ȶ�˵����
                            </h3>
                            <p id="container11_msg">

                            </p>
                        </div>
                    </div>
                </div>
            </div> -->
            <!--�����ȶȶԱ� end-->
       	<!--��з����Ա� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>��з����Ա�</h2>
    	    </div>
    	    <div class="alert" id="container3_msg">
    	    	
    	    </div>
    	    <div id="container3" style="height:280px;">
    			<!-- �����Ǳ��� -->
    		</div>
       	</div>
       	<!--��з����Ա� end-->

        <!-- ��Ƶ�ʻ� start-->
        <div class="wrapCon">
            <div class="tit" style="border-bottom: 1px solid #e8eddd;">
                <h2>��Ƶ�ʻ�</h2>
            </div>
            <div class="alert" id="container10_msg"></div>
            <div id="container10" ></div>
        </div>
        <!-- ��Ƶ�ʻ� end-->

        <!--�ؼ����� start-->
        <div class="wrapCon">
            <div class="tit" style="border-bottom: 1px solid #e8eddd;">
                <h2>�ؼ�����</h2>
            </div>
            <div id="container9" style="background-repeat:no-repeat;"></div>
            <%-- <div id="container9" style="height:500px; background-image:url('<%=staticResourcePathH5 %>/images/cloud.png?v=<%=SYSTEM_INIT_TIME %>');background-repeat:no-repeat;"></div> --%>
        </div>
        <!--�ؼ����� end-->
       	<!--��Ϣ����ֲ� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>��Ϣ����ֲ�</h2>
    	    </div>
    	    <div class="alert" id="container2_msg">
    	    	<!-- ����ֲ���ʾ��Ϣ -->
<%--     	    	${pab.areaDesc} --%>
    	    </div>
    	    <div class="row-fluid">
    			<div class="span12 borderBox chartBox2 float_l">
    				<!-- <div class="tit">
    					<h2>����֤ȯ</h2>
    				</div> -->
    				<div class="span12">
    					<div class="abs" style="top: 0; left: 0; z-index: 2;">
				  			<div class="btn-group btn-groupAll " data-toggle="buttons" id="container2_sel">
<!-- 												<label class="btn btn-sm active">����</label> -->
<!-- 												<label class="btn btn-sm">����</label> -->
<!-- 												<label class="btn btn-sm">����</label> -->
						   </div>
				  		</div>
    					<div class="span6 float_l" id="container2" style="height: 550px">
    					</div>
    					
    					<div class="span6 float_r" id="mapTop10">
    							<!-- ����ֲ�ǰʮ -->
    					</div>
    				</div>
    				
    			</div>
    			 
    		</div>
       	</div>
       	<!--��Ϣ����ֲ� end-->
       	
       <!--ý���Ծ�Ա� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>��Դ���ͶԱ�</h2>
    	    </div>
    	    <!-- <div class="alert container4_msg">
    	    	
    	    </div> -->
    	    <div class="row-fluid">
    			<div class="span7 borderBox chartBox float_l" id="container4">
    			<!-- ��Ϣ��Դͼ -->
    			</div>
    			<div class="span5 borderBox chartBox float_r">
    				<div class="descriptionCon">
    					<h3><img src="<%=staticResourcePathH5 %>/images/circle_help_question-mark-16.png"/> ��Դ����˵����</h3>                                                     
						<p class="container4_msg" id="container4_msg"></p>
    				</div>
    			</div>
    		</div>
       	</div>
       	<!--ý���Ծ�Ա� end-->
       	
       	<!--ý����Դ�Ա� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>ý���Ծ�Ա�</h2>
    	    </div>
    	   <!--  <div class="alert" id="container5_msg">
    	    	ý����Դ��ʾ��Ϣ
    	    </div> -->
    	    <div class="row-fluid" >
    	    	<div class="span7 borderBox chartBox tabs tabs2 tabs-mthydb float_l" id="container5">
    				<!-- ý����Դ�Ա����� -->
    			</div>
    			<div class="span5 borderBox chartBox float_r">
    				<div class="descriptionCon">
    					<h3><img src="<%=staticResourcePathH5 %>/images/circle_help_question-mark-16.png"/> ��Ծ��˵����</h3>                                                     
						<p id="container5_msg"></p>
    				</div>
                  
   				</div>
    		</div>
    		
       	</div>
       	<!--ý����Դ�Ա� end-->
       	
       	<!--�ص�ý���� start-->
       	<div class="wrapCon">
       		<div class="tit">
    		<h2>�ص�ý����</h2>
    	    </div>
    	     
    	    <div class="row-fluid">
    	    	<div id="container6_msg" class="alert"></div>
    			<div class="span12  chartBox2 float_l">
    					<table border="0" cellspacing="0" cellpadding="0" class="table table2 table3 table4">
    						<!-- <thead>
    						<tr>
    							<th>���к�</th>
    							<th>������</th>
    							<th>�ص�ý��</th>
    							<th>��������</th>
    						</tr>
    						</thead> -->
    						<tbody id="container6">
    						</tbody>
    					</table>
    			</div>
    			
    		</div>
       	</div>
       	<!--ý����Դ�Ա� end-->
       	
       	<!--��Ⱥ��λ start-->
     <%--   	<div class="wrapCon">
       		<div class="tit">
    		<h2>��Ⱥ��λ</h2><span class="small">��2016-01-20��2016-01-27</span>
    	    </div>  
    	    <div class="row-fluid">
    			<div class="span6 borderBox chartBox float_l">
    				<div class="tit">
    					<h2>����֤ȯ</h2>
    				</div>
    				<img src="images/u207.png" width="100%" height="80%"/>
    			</div>
    			<div class="span6 borderBox chartBox float_r">
    				 <div class="tit">
    					<h2>����ͨ��</h2>
    				</div>
    				<img src="images/u207.png" width="100%" height="80%"/>
                  
    			</div>
    		</div>
       	</div> --%>
       	<!--��Ⱥ��λ end-->
       	<s:if test = 'isApp!=1'>
	        <div class="fenxiang phone_hide float_r" >
	            <span>������</span>
	            <a class="jiathis_button_tsina icon icon_wb" target="_blank" title="΢��"></a>
	            <!-- <a href="#" class="jiathis_button_weixin icon icon_wx" title="΢��"></a> -->
	            <%-- <a href="#" class="icon icon_pyq" title="����Ȧ"></a>--%>
	            <a class="jiathis_button_cqq icon icon_qq" target="_blank" title="QQ"></a>
	            <a class="jiathis_button_qzone icon icon_qqkj" target="_blank" title="QQ�ռ�"></a>
	        </div>
	    </s:if>
    </div>


    <!--����������� start-->
  <%--  <div class="row-fluid">
        <div class="bottomEWM">
            <img src="images/code.png">
        </div>
        <div class="bottomText">
            <p><a href="http://wyq.sina.com">���ྫ�ʣ����ڡ�΢�ȵ㡱</a></p>
            <p>�ֻ��ϴ�΢���ͻ���ɨ���ά��</p>
            <!--<p>������ά��ʶ�����ؿͻ���</p>-->
            <p><a href="http://wyq.sina.com">wyq.sina.com</a></p>
        </div>
    </div>--%>
    <!--����������� end-->
	<s:if test = 'isApp!=1'>
	    <div class="row-fluid" id="fx2">
	    <%-- 
	        <div class="fenxiang2">
	            <a class="jiathis_button_tsina icon icon_wb" target="_blank" title="΢��"></a>
	            <!-- <a href="javascript:;" onclick="" class="jiathis_button_weixin icon icon_wx" title="΢��"></a> -->
	            <a href="javascript:;" onclick="" class="icon icon_pyq" title="����Ȧ"></a>
	            <a class="jiathis_button_cqq icon icon_qq" target="_blank" title="QQ"></a>
	            <a class="jiathis_button_qzone icon icon_qqkj" target="_blank" title="QQ�ռ�"></a>
	        </div>--%>
	        <script src="http://v3.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
	    </div>
	</s:if>
    <div class="row-fluid foot" <s:if test = 'isApp==1'>
		style = 'display:none;'
	</s:if>>
       <!--  <a href="http://a.app.qq.com/o/simple.jsp?pkgname=com.xd.wyq">
            <img src="images/bottomPic.jpg"/>
        </a> -->
    </div>
    
	<div class="clear" style="height: 35px;"></div>
	<s:if test="#parameters.platform == '' || #parameters.platform ==null">
	<section id = "appdownload" class="section subscribe">
			<div class="padding8" style="height:45px;">
				<div style="position: absolute;top:12px;left:10px;height:100%;right:0%;color:#FFF;font-size:16px;"></div>
				<div class = "subBtn" style="background-color:#fd9237;border-radius:5px;position: absolute;top:8px;font-size:14px;text-align:center;line-height:30px;width: 60%;height:30px;right:20%;color:#FFF;cursor:pointer;" onClick="goSubscribe();">��ҲҪ����</div>
			</div>
		</section>
	</s:if>
</div>
</div>
<!--�������� end-->


<input type="hidden" value="${pa.createTime}" id="createTime"/>
<input type="hidden" value="${pab.id}" id="pabId"/>
<input type="hidden" value="${sign}" id="sign"/>
<input type="hidden" value="${title}" id="title"/>


<!--�ײ����ִ��� start-->
<s:include value="/view/productsAnalysis/bottom.jsp">
</s:include>
<!--�ײ����ִ��� end-->


<script src="<%=staticResourcePath %>/js/jquery.tabs.js"></script>
<script src="<%=staticResourcePath %>/js/jquery.lazyload.js"></script>
<script src="<%=staticResourcePath %>/js/jquery.slider1.js"></script>
<script>
$(function(){
	$('.tabs-mthydb').Tabs({
		event:'click'
	});
	//var title = $("title").text();
	$(".jiathis_button_tsina").on("mousedown",function(){
		jiathis_config.title = "${title}�ĶԱȷ����뿴����";
		jiathis_config.summary = " ��з��������������ơ���Ƶ�ʻ㡢�ؼ����ơ�����ֲ�����Դ���͵ȶ�ά�ȳ��ֵľ�׼��������";
	});
	/* $(".jiathis_button_weixin").on("click",function(){
		jiathis_config.title = "ѽ~��ܿ���������ɵĶ��ַ����鱨";
		jiathis_config.summary = "��ά�ȳ��֣����";
	}); */
	$(".jiathis_button_cqq").on("mousedown",function(){
		jiathis_config.title = "${title}�ĶԱȷ����뿴����";
		jiathis_config.summary = " ��з��������������ơ���Ƶ�ʻ㡢�ؼ����ơ�����ֲ�����Դ���͵ȶ�ά�ȳ��ֵľ�׼��������";
	});
	$(".jiathis_button_qzone").on("mousedown",function(){
		jiathis_config.title = "${title}�ĶԱȷ����뿴����";
		jiathis_config.summary = " ��з��������������ơ���Ƶ�ʻ㡢�ؼ����ơ�����ֲ�����Դ���͵ȶ�ά�ȳ��ֵľ�׼��������";
	});
});
function goSubscribe(){
	if("${admin}"){
		location.href = actionBase+"/productsAnalysis.shtml";
	}else{
		location.href = actionBase+"/indexLocal.shtml";
	}
}
</script>
 <%@ include file="../../buttom.jsp" %>
</body>
</html>