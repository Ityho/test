
function chartloading(id){
	var stat = document.getElementById(id);
	//stat.innerHTML = '<img src=\"<%=base%>/images/loading.gif\">';
}
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	}
	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

//hot����ͼ��΢������ͼ
function goLineChartComm7(id,type,userId,kwId,starttime,endtime,solrType,otherAttrbute,rank){
	chartloading(id);
	EChartsDwr.getLineChartAnalysis(type,userId,kwId,starttime,endtime,solrType,otherAttrbute,rank,null,LineCallBack7);
}

function LineCallBack7(result){
	var conId = "container7";
	var stat1 = document.getElementById("satusTree7");
	var c1 = document.getElementById("container7");
	if(result[0]=="3"){
		stat1 = document.getElementById("satusTree8");
		c1 = document.getElementById("container8");
		conId = "container8";
	}	
	stat1.innerHTML = '';
	if (result[1]==null||result[1]==""){
		c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat1.innerHTML = '';
		return false;
	}
	else{
		var data = eval(result[1]);
		if(data[0].data==null || data[0].data.length==0){
			c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat1.innerHTML = '';
			return;
		}else{
			var _chartColumn17 = LineChart7(data[0],conId);
		}
	}
}
function LineChart7(data,dom){
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
            		        	      var now = new Date();
                	            	  now.setTime(params[0].name);
	             	            		v = now.format(data.dateFormat);
			               		         for (var i = 0, l = params.length; i < l; i++) {
			               	                v += '<br/>�� ��: ' + params[i].value;
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
	                	            },
	                	        }
	                	    },
	                	    /*legend: {
	                	        data:data.legend
	                	    },*/
	                	    xAxis:[{
            	                 type : 'category',//category|time
            	              	 boundaryGap: false ,
            	                 data : data.datetime,
            	                 axisLine: {
            	                	 onZero: false
            	                },
            	                axisLabel : {
            	                		 textStyle : {
   	                     	                 decoration: 'none',
   	                     	                 fontFamily: 'Microsoft YaHei',
   	                     	                 fontSize: 12,
   	                     	             },
   	                     	            formatter:function(v){
   	                     	            	var now = new Date();
   	                     	            	now.setTime(v);
   	                     	            	v = now.format(data.dateFormat);
   	                     	            	 return v;
   	                     	             	} 
	                	    		
            	                	 },
	                	            }
	                	         ],
            	         yAxis : [{
            	                 type : 'value',
            	               	 axisLabel:{
	                	               	  /* textStyle : {
	                     	                 decoration: 'none',
	                     	                 fontFamily: 'Microsoft YaHei',
	                     	                 fontSize: 12,
	                     	             }, */
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
	                	    series :[{
	                	    		type:'line',
	                	    		name:'',
	                	    		data:data.data
	                	    }]
	                }
	                chart1.setOption(option);
	                var enConfig = require('echarts/config');
	           }
	       );
}


//����ͼ
function goLineChartComm(type,userId,kwId,starttime,endtime,solrType,otherAttrbute,rank){
	chartloading("satusTree1");
	EChartsDwr.getLineChartAnalysis(type,userId,kwId,starttime,endtime,solrType,otherAttrbute,rank,null,LineCallBack);
}
function LineCallBack(result){
	var stat1 = document.getElementById("satusTree1");
	stat1.innerHTML = '';
	var c1 = document.getElementById("container1");

	if (result[1]==null||result[1]==""){
		c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat1.innerHTML = '';
		return false;
	}
	else{
		var data = eval(result[1]);
		if(data[0].data==null || data[0].data.length==0){
			c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat1.innerHTML = '';
			return;
		}else{
			
			var _chartColumn10 = LineChart(data[0],"container1");
		}
	}
}
function LineChart(data,dom){
	//ʱ���
	var starttime = $("#starttime").val();
	var endtime = $("#endtime").val();
	var end_stamp = Date.parse(new Date(endtime.replace(/-/g, "/")));
	var start_stamp  = Date.parse(new Date(starttime.replace(/-/g, "/")));
	var isHourSeach = false;  //Ĭ���ǰ����ѯ
	if((end_stamp-start_stamp)/3600/1000/24 <= 3){
		isHourSeach = true;
	}else{
		isHourSeach = false;
	}
	
	var splitNum = 0;
	if(data.datetime.length>12){
		splitNum = 2;
	} 
	
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
			               		        	if(isHourSeach==true){
			             	            		v = new Date(params[0].name).format("MM-dd hh:00");
			             	            	 }else {
			             	            		v = new Date(params[0].name).format("yyyy-MM-dd hh:00");
			             	            	 }
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
	                	            },
	                	        }
	                	    },
	                	    legend: {
	                	        data:data.legend
	                	    },
	                	    xAxis:[{
            	                 type : 'category',//category|time
            	              	 boundaryGap: false ,
            	                 data : data.datetime,
            	                 axisLine: {
            	                	 onZero: false
            	                },
            	                splitNumber:splitNum,
            	                axisLabel : {
            	                		 textStyle : {
   	                     	                 decoration: 'none',
   	                     	                 fontFamily: 'Microsoft YaHei',
   	                     	                 fontSize: 12,
   	                     	             },
   	                     	            formatter:function(v){
   	                     	            	v = new Date(v).format(data.dateFormat);
   	                     	            	 return v;
   	                     	             	} 
	                	    		
            	                	 },
	                	            }
	                	         ],
            	         yAxis : [{
            	                 type : 'value',
            	               	 axisLabel:{
	                	               	  /* textStyle : {
	                     	                 decoration: 'none',
	                     	                 fontFamily: 'Microsoft YaHei',
	                     	                 fontSize: 12,
	                     	             }, */
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
	                var enConfig = require('echarts/config');
	                 chart1.on(enConfig.EVENT.CLICK, function (param){
	                    var timeL = param.name;
	                    var originType='';
        				if(param.seriesName=="����"){
        					originType='baokan';
        				}
        				if(param.seriesName=="����"){
        					originType='zw';
        				}
        				if(param.seriesName=="����"){
        					originType='xw';
        				}
        				if(param.seriesName=="��վ"){
        					originType='wz';
        				}
        				if(param.seriesName=="��̳"){
        					originType='lt';
        				}
        				if(param.seriesName=="����"){
        					originType='bk';
        				}
        				if(param.seriesName=="΢��"){
        					originType='wb';
        				}
        				if(param.seriesName=="��ý"){
        					originType='jw';
        				}
        				if(param.seriesName=="΢��"){
        					originType='wx';
        				}
        				if(param.seriesName=="��Ƶ"){
        					originType='sp';
        				}
        				if(param.seriesName=="�ͻ���"){
        					originType='app';
        				}
	                    var startFormat = "yyyy-MM-dd 00:00:00";
	                    var endFormat = "yyyy-MM-dd 23:59:59";
	                    if(isHourSeach==true){
	                    	startFormat = "yyyy-MM-dd hh:00:00";
	                    	endFormat = "yyyy-MM-dd hh:59:59";
	                    }
	                    starttime = new Date(timeL).format(startFormat) ;  
	            		endtime = new Date(timeL).format(endFormat) ;
	            		var kwId = document.getElementById("kwId").value;
	            		var comblineflg = document.getElementById("comblineflg").value;
	                	var paixu = document.getElementById("paixu").value;
                         var otherAttribute = document.getElementById("otherAttribute").value;
	                	var params = {'kw.id':kwId,'comblineflg':comblineflg, 'starttime':starttime, 'endtime':endtime,'paixu':paixu,'originType':originType,'clickOtherAttribute':otherAttribute};
	                	sendPostForm(njxImgSrc + '/analysisListByWebsite.shtml', '_blank', params);
	                    
	                    
	                    
	                }); 
	           }
	       );
}





//��վ��Դͼ
function goWTChartComm(flag,userId,kwId,type,starttime,endtime,rank){
	if(flag=="1"){
		chartloading("satusTree2");
	}
	chartloading("satusTree3");
	EChartsDwr.getWTChartAnalysis(userId,kwId,type,starttime,endtime,rank,WTCallBack);
}



function WTCallBack(result){
	if(document.getElementById("lyAll")==null){
		var stat2 = document.getElementById("satusTree2");
		stat2.innerHTML = '';
		var c2 = document.getElementById("container2");
	}
	var stat3 = document.getElementById("satusTree3");
	stat3.innerHTML = '';
	var c3 = document.getElementById("container3");
	if (result==null||result==""){
		c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat2.innerHTML = '';
		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat3.innerHTML = '';
		return;
	}
	else{
		var data = eval(result[0]);
		if(data[0].data==null || data[0].data.length==0){
			c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat2.innerHTML = '';
			c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat3.innerHTML = '';
			return;
		}else {
			if(document.getElementById("lyAll")==null){
				var _chartColumn10 = WTBarChart(data[0],"container2");
			}
			if(document.getElementById("lyAll")!=null){
				document.getElementById("lyAll").innerHTML=result[1];
			}
			if(document.getElementById("lymsg")!=null){
				var type1 = result[2];
				if(type1=='wz'){
					type1="��վ����";
				}
				if(type1=='lt'){
					type1="��̳��Ϣ";
				}
				if(type1=='wb'){
					type1="΢����Ϣ";
				}
				if(type1=='wx'){
					type1="΢��";
				}
				if(type1=='bk'){
					type1="����";
				}
				if(type1=='jw'){
					type1="����";
				}
				if(type1=='xw'){
					type1="����";
				}
				if(type1=='sp'){
					type1="��Ƶ";
				}
				if(type1=='baokan'){
					type1="����";
				}
				if(type1=='zw'){
					type1="����";
				}
				if(type1=='app'){
					type1="�ͻ���";
				}
				if(result[4]!=null){
					var type2 = result[4];
					if(type2=='wz'){
						type2="��վ����";
					}
					if(type2=='lt'){
						type2="��̳��Ϣ";
					}
					if(type2=='wb'){
						type2="΢����Ϣ";
					}
					if(type2=='wx'){
						type2="΢��";
					}
					if(type2=='bk'){
						type2="����";
					}
					if(type2=='jw'){
						type2="����";
					}
					if(type2=='xw'){
						type2="����";
					}
					if(type2=='sp'){
						type2="��Ƶ";
					}
					if(type2=='baokan'){
						type2="����";
					}
					if(type2=='zw'){
						type2="����";
					}
					if(type2=='app'){
						type2="�ͻ���";
					}
					document.getElementById("lymsg").innerHTML=type1+"��"+result[3]+"����"+type2+"��"+result[5]+"��";
				}else{
					document.getElementById("lymsg").innerHTML=type1+"��"+result[3]+"��";
				}
				
			}
			var datax = eval(result[0]);
			var _chartColumn3 = WTPieChart(datax[0],"container3");
			
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
	                		 tooltip : {     
	                		        trigger: 'axis',
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
	                	            },
	                	        }
	                	    },
	                	    xAxis:[{
             	                 type : 'category',
             	                 data : data.datetime,
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
            	         		y2:80
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
             	               	 }
             	         	}],
             	         
             	         	color:['#87cefa','#ff7f50'],
	                	    calculable : false,
	                	    animation:false,
	                	    series : [{
		                	    	name:'����',
		                	    	type:'bar',
		                	    	data:data.data
	                	    }]
	                }
	                chart2.setOption(option);
	                var enConfig = require('echarts/config');
	               /*  chart2.on(enConfig.EVENT.CLICK, function (param){
	                	  var starttime = document.getElementById("starttime").value;
	                	  var endtime = document.getElementById("endtime").value;
	                	  var keywordId = document.getElementById("kw.keywordId").value;
	                	  var newlstSelect = document.getElementById("newlstSelect").value;
	                	  var timeDomain = document.getElementById("timeDomain").value;
	                	  
	                	  var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'clickOtherAttribute':0, 'website':param.name, 'clickclickComblineflg':2, 'clickPaixu':2, 'starttime':starttime, 'endtime':endtime, 'clickNewlstSelect':newlstSelect, 'type':2};
	                	  sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
		                   
	                }); */
	           }
	       );
}





function WTPieChart(data,dom){
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
	                	        formatter:"{a} <br/>{b} : {c} ({d}%)"
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
	                	   /*  legend:{
	                	    	  orient : 'vertical',
	                	          x : 'left',
	                	    	  data:data.legend
	                	    },  */
	                	    calculable : false,
	                	    color:['#cd5c5c','#ba55d3','#ff69b4','#6495ed','#32cd32','#87cefa','#ff7f50','#8d98b3','#a16661'],
	                	    series : [
	                	        {
	                	        	name:data.title,
	                	            type:'pie',
	                	           /*  radius : ['35%', '70%'], */
	                	            radius : '75%',
	                	            startAngle:0,
	                	            minAngle:20,
	                	            roseType:'null',
	                	            
	                	            itemStyle : {
	                	                normal : {
	                	                    label : {
	                	                        show : true,
	                	                        textStyle:{
	                	                        	fontSize:'14',
	                	                        	fontWeight:'bold'
	                	                        },
	                	                        position:'outer'
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
	                	                            fontSize : '16',
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
	             /*    chart3.on(enConfig.EVENT.CLICK, function (param){
	                	  var starttime = document.getElementById("starttime").value;
	                	  var endtime = document.getElementById("endtime").value;
	                	  var keywordId = document.getElementById("kw.keywordId").value;
	                	  var newlstSelect = document.getElementById("newlstSelect").value;
	                	  var timeDomain = document.getElementById("timeDomain").value;
	                	  var params = {'kw.keywordId':keywordId, 'clickTimeDomain':timeDomain, 'clickFilterOrigina':0, 'clickOtherAttribute':0, 'website':param.name, 'clickPaixu':2, 'starttime':starttime, 'endtime':endtime, 'clickNewlstSelect':newlstSelect, 'type':2};
	                	  sendPostForm(njxImgSrc + '/list.shtml', '_blank', params);
		                   
	                }); */

	           });
}


//��зֲ�ͼ
function goQGPieChartComm(userId,kwId,type,starttime,endtime,solrType,otherAttribute,rank){
	chartloading("satusTree4");
	EChartsDwr.getQGPieChartAnalysis(userId,kwId,type,starttime,endtime,solrType,otherAttribute,rank,null,QGPieCallBack);

}
function QGPieCallBack(data){
	var stat4 = document.getElementById("satusTree4");
	stat4.innerHTML = '';
	var c4 = document.getElementById("container4");
	if (data==null||data==""){
		c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat4.innerHTML = '';
		document.getElementById("num1").innerHTML = "0 ";
		if(document.getElementById("num3")!=null){
			document.getElementById("num3").innerHTML = "0 ";
		}
		
		document.getElementById("num2").innerHTML= "&nbsp; 0%";
		/* document.getElementById("num3").innerHTML= "&nbsp; 0%"; */
		return false;
	}else{
		var num1 = data[1];
		var num2 = data[4];
		data = eval(data[0]);
		if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
			c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat4.innerHTML = '';
			 document.getElementById("num1").innerHTML = "0 ";
			 if(document.getElementById("num3")!=null){
				document.getElementById("num3").innerHTML = "0 ";
			}
			document.getElementById("num2").innerHTML= "&nbsp; 0%"; 
			return;
		}else{
			 document.getElementById("num1").innerHTML = num1;
			 if(document.getElementById("num3")!=null){
				 document.getElementById("num3").innerHTML = num1;
			 }
			document.getElementById("num2").innerHTML= "&nbsp;<font color='#e7312c'>"+ num2+"</font>";
			if(document.getElementById("qg_main")!=null){
				if(num2>='50%'){
					document.getElementById("qg_main").innerHTML="&nbsp;<font color='#e7312c'>����</font>";
				}else{
					document.getElementById("qg_main").innerHTML="&nbsp;�����Կ͹�";
				}
			}
				
			var _chartColumn10 = QGPieChart(data[0],"container4");
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
	                	    	   orient : 'vertical',
	                	           x : 'left',
	                	           data:data.legend
	                	    },
	                	    series : [
	                	        {
	                	            name:data.title,
	                	            type:'pie',
	                	            radius : ['35%', '70%'],
	                	            startAngle:0,
	                	            itemStyle : {
	                	                normal : {
	                	                    label : {
	                	                        show : true,
	                	                        textStyle:{
	                	                        	fontSize:'14',
	                	                        	fontWeight:'bold'
	                	                        },
                                                formatter: "{b} : {d}%"
	                	                    },
	                	                    labelLine : {
	                	                        show : true
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
	                 chart4.on(enConfig.EVENT.CLICK, function (param){
	                	 var otherAttribute = 0;
		                    if(param.name == '����'){
		                    	otherAttribute = 3;
		                    }else if(param.name == '������'){
		                    	otherAttribute = 4;
		                    }
		                    var starttime = document.getElementById("starttime").value;
		                	var endtime = document.getElementById("endtime").value;
		                	var comblineflg = document.getElementById("comblineflg").value;
		                	var paixu = document.getElementById("paixu").value;
		                	var kwId = document.getElementById("kwId").value;
		                	var params = {'kw.id':kwId,'comblineflg':comblineflg, 'starttime':starttime, 'endtime':endtime,'paixu':paixu,'clickOtherAttribute':otherAttribute};
		                	sendPostForm(njxImgSrc + '/analysisListByWebsite.shtml', '_blank', params);
	                }); 
	           }
	       );
	
} 

//��վ��Դ�ܱ�ͼ
function goOTPieChartComm(userId,kwId,type,starttime,endtime,solrType,otherAttribute,rank){
	chartloading("satusTree5");
	EChartsDwr.getOTPieChartAnalysis(userId,kwId,type,starttime,endtime,solrType,otherAttribute,rank,null,OTPieCallBack);

}
function OTPieCallBack(result){
	var data = result[0];
	var total = result[1];
	var type1 = result[2];
	var type = result[3];
	var stat5 = document.getElementById("satusTree5");
	stat5.innerHTML = '';
	var c5 = document.getElementById("container5");
	if (data==null||data==""){
		c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat5.innerHTML = '';
		return false;
	}
	else{
		data = eval(data);
		if(data[0].data==null || data[0].data.length==0){
			c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat5.innerHTML = '';
			return false;
		}else{
			var _chartColumn10 = OTPieChart(data[0],"container5");
		}
		if(type==2){
			if(total>0){
				$("#lyAll").html(total);
				if(type1=='wz'){
					type1="��վ����";
				}
				if(type1=='lt'){
					type1="��̳��Ϣ";
				}
				if(type1=='wb'){
					type1="΢����Ϣ";
				}
				if(type1=='wx'){
					type1="΢��";
				}
				if(type1=='bk'){
					type1="����";
				}
				if(type1=='jw'){
					type1="����";
				}
				if(type1=='xw'){
					type1="����";
				}
				if(type1=='sp'){
					type1="��Ƶ";
				}
				if(type1=='baokan'){
					type1="����";
				}
				if(type1=='zw'){
					type1="����";
				}
				if(type1=='app'){
					type1="�ͻ���";
				}
				$("#lymsg").html(type1);
			}else{
				$("#lyAll").html(0);
				$("#lyAll").html('');
			}
		}
	
	}
	
}
function OTPieChart(data,dom){
	 var config = require(
	           [  
	            'echarts',
	            'echarts/chart/pie',
	           ],
	           function (ec) {
	        	   	var chart5 = ec.init(document.getElementById(dom));
	                var option = {
	                		tooltip : {
	                	        trigger: 'item',
	                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
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
	                	            	name:data.name
	                	            }
	                	        }
	                	    },
	                	    legend:{
	                	    	orient : 'vertical',
	                	        x : 'left',
	                	    	data:data.legend,
                	    	   
	                	    },
	                	    calculable : false,
	                	    color:['#cd5c5c','#ba55d3','#ff69b4','#6495ed','#32cd32','#87cefa','#ff7f50'],
	                	    series : [
	                	        {
	                	        	name:data.title,
	                	            type:'pie',
	                	            radius : ['35%', '70%'],
	                	            startAngle:0,
	                	            minAngle:15,
	                	            itemStyle : {
	                	                normal : {
	                	                    label : {
	                	                        show : true,
	                	                        textStyle:{
	                	                        	fontSize:'14',
	                	                        	fontWeight:'bold'
	                	                        },
                                                formatter: "{b} : {d}%"
	                	                    },
	                	                    labelLine : {
	                	                        show : true
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
	                	            data:data.data,
	                	        }
	                	    ]
	                }
	                chart5.setOption(option);
	                var enConfig = require('echarts/config');
	                 chart5.on(enConfig.EVENT.CLICK, function (param){
	                	var originType='',filterOrigina='';
        				if(param.name=="����"){
        					originType='baokan';
                            filterOrigina=9;
        				}
        				if(param.name=="����"){
        					originType='zw';
                            filterOrigina=10;
        				}
        				if(param.name=="����"){
        					originType='xw';
                            filterOrigina=7;
        				}
        				if(param.name=="��վ"){
        					originType='wz';
                            filterOrigina=1;
        				}
        				if(param.name=="��̳"){
        					originType='lt';
                            filterOrigina=2;
        				}
        				if(param.name=="����"){
        					originType='bk';
                            filterOrigina=3;
        				}
        				if(param.name=="΢��"){
        					originType='wb';
                            filterOrigina=4;
        				}
        				if(param.name=="��ý"){
        					originType='jw';
                            filterOrigina=6;
        				}
        				if(param.name=="΢��"){
        					originType='wx';
                            filterOrigina=5;
        				}
        				if(param.name=="��Ƶ"){
        					originType='sp';
                            filterOrigina=8;
        				}
        				if(param.name=="�ͻ���"){
        					originType='app';
                            filterOrigina=11;
        				}
        				var starttime = document.getElementById("starttime").value;
	                	var endtime = document.getElementById("endtime").value;
	                	var comblineflg = document.getElementById("comblineflg").value;
	                	var paixu = document.getElementById("paixu").value;
	                	var kwId = document.getElementById("kwId").value;
                         var otherAttribute = document.getElementById("otherAttribute").value;
	                	var params = {'kw.id':kwId,'comblineflg':comblineflg, 'starttime':starttime, 'endtime':endtime,'paixu':paixu, 'clickFilterOrigina':filterOrigina,'originType':originType,'clickOtherAttribute':otherAttribute};
	                	sendPostForm(njxImgSrc + '/analysisListByWebsite.shtml', '_blank', params);
				
	                }); 

	           });
}



//�¼��Ľ�չ
function process(){
	chartloading("process");
	starttime = $("#starttime1").val();
	endtime = $("#endtime1").val();
	EChartsDwr.getProcess('${admin.userId}',"${kw.id}",1,starttime,endtime,GetRandom(30),ProcessCallBack);

}


function ProcessCallBack(result){
	var ulHtml = "";
	$("#process").html("");
	if(result!=null){
		for(var i=0;i<result.length;i++){
			var liHtml = "";
			if(i==1||i==5||i==9||i==13||i==17||i==19){
				liHtml = "<li class='set_nz'>";
			}else{
				liHtml = "<li>";
			}
			liHtml +="<div class='nz'></div><div  class='float_l'><span class='line'></span><span class='round'></span><span class='line'></span>"
	            +"</div><div class='sz'><img src='<%=njxBasePath%>/images/eventAnalysis/ggggg_10.png' alt=''></div>"
	            +result[i]['published'].format("yyyy-MM-dd hh:MM")+result[i]['title']+"  ["+result[i]['captureWebsiteName']+"] ת������"+result[i]['repeatNum']+"</li>"	;
			ulHtml+=liHtml;
		}
		
		$("#process").append(ulHtml);
	}
}



//����ֲ�
function goMapChartComm(userId,kwId,type,starttime,endtime,solrType,otherAttribute,rank){
	chartloading("satusTree6");
	 EChartsDwr.getMapChartFXAnalysis(userId,kwId,type, starttime,endtime,solrType,otherAttribute,rank,null, MapCallBack);

}
function MapCallBack(data){
	var stat6 = document.getElementById("satusTree6");
	stat6.innerHTML = '';
	var c6 = document.getElementById("container6");
	if (data==null||data==""){
		c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
		stat6.innerHTML = '';
		return;
	}else{
		var res = eval(data);
		if(res==null || res[0]==null){
			c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>��ʱ���������Ϣ</p></div>";
			stat6.innerHTML = '';
			return;
		}else{
			if(document.getElementById("province")!=null){
				document.getElementById("province").innerHTML=data[2]+"��"+data[4]+"��"+data[6];
			}
			if(document.getElementById("prov_percent")!=null){
					document.getElementById("prov_percent").innerHTML=data[2]+"ռ��"+data[3]+"��"+data[4]+"ռ��"+data[5]+"��"+data[6]+"ռ��"+data[7];
			}
			var _chartColumn10 = MapChart(data[0],"container6");
		}
		
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
	        				 toolbox: {
		                	        show : true,
		                	        orient:'vertical',
		                	        y:30,
		                	        feature : {
		                	            restore : {show: true},
		                	            saveAsImage : {
		                	            	show: true,
		                	            	name:data[0].title
		                	            }
		                	        }
		                	    },
		                	    dataRange: {
							        min: min,
							        max: max,
							        calculable : true,
							        text:['��','��'],
							        color: ['orangered','yellow','lightskyblue']
							    },
	        				series : [ {
	        					name : '����',
	        					type : 'map',
	        					mapType : 'china',
	        					selectedMode : 'single',//single|multiple
	        					mapLocation: {
	        		                x: 'center'
	        		            },
	        					roam:false,
	        					itemStyle : {
	        						normal : {
	        							label : {
	        								show : false,
	        								textStyle : {
	        									fontSize : 10
	        								},
	        							},
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
	        			                /*itemStyle: {
	        			                    normal: {
	        			                        borderColor: '#87cefa',
	        			                        borderWidth: 1,            
	        			                        label: {
	        			                            show: false
	        			                        }
	        			                    },
	        			                    emphasis: {
	        			                        borderColor: '#1e90ff',
	        			                        borderWidth: 5,
	        			                        label: {
	        			                            show: false
	        			                        }
	        			                    }
	        			                },*/
	        			                //data :data[0].data
	        							data:data
	        				    }
	        		       }
	        				/*,
	        		        {
	        		            name:'',
	        		            type:'pie',
	        		            roseType : 'area',
	        		            tooltip: {
	        		                trigger: 'item',
	        		                formatter: "{a} <br/>{b} : {c} ({d}%)"
	        		            },
	        		            center: [$("#"+dom).width()-140, $("#"+dom).height()/2],
	        		            radius: [40, 80],
	        		            data:data[0].topData
	        		        }*/
	        			  ]
	        			};
	        		chart6.hideLoading();
	                chart6.setOption(option);
	                var enConfig = require('echarts/config');
	                chart6.on(enConfig.EVENT.CLICK, function (param){
	                	if(param.value > 0) {
	                		var starttime = document.getElementById("starttime").value;
		                	var endtime = document.getElementById("endtime").value;
		                	var comblineflg = document.getElementById("comblineflg").value;
		                	var paixu = document.getElementById("paixu").value;
		                	var kwId = document.getElementById("kwId").value;
                            var otherAttribute = document.getElementById("otherAttribute").value;
		                	var params = {'kw.id':kwId, 'province':param.name, 'comblineflg':comblineflg, 'starttime':starttime, 'endtime':endtime,'paixu':paixu,'clickOtherAttribute':otherAttribute};
		                	sendPostForm(njxImgSrc + '/analysisListByWebsite.shtml', '_blank', params);
	                	}
	                });
	           }
	       );
}