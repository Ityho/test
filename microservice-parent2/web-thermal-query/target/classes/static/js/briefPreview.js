function GetRandom(n){
	GetRandomn=Math.floor(Math.random()*n+1);
	return GetRandomn;
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
function sendPost(param,url,callback,elementId){
    $.ajax({
        url : url,
        type : "post",
        data : param,
        success : function(data){
            callback(data,elementId);
        }/*,
        error:function(){
            sendPost(param,url,callback,elementId);
        }*/
    })
};
$(function() {
    $( "#sortable ul" ).sortable({ handle: ".tool .icon-move" });
});

$(function() {
    //�ı���ʽѡ��
	 $(".textSele>li").on("click",function(i) {
			if ( $(this).html().indexOf("����1")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����1 <i class=\"icon-angle-down\"></i>");
		}
		else if( $(this).html().indexOf("����2")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("����2 <i class=\"icon-angle-down\"></i>");
		}
		else if ( $(this).html().indexOf("����")>0)
		{
			$(this).parents(".btn-group").find(".dropdown-toggle").html("���� <i class=\"icon-angle-down\"></i>");
		}
        $(this).parent(".textSele").find("li").removeClass("active");
        $(this).addClass("active");
    });
});

$(function() {
    //ɾ������ģ��
    $(" .row-fluid .textShow .tool .icon-trash").on("click",function() {
        $(this).parents("#sortable >ul>li").remove();
    });
    $(" #page-bg").on("click",function() {
 	   // $("body").removeClass("finalDraft").css("background-color","rgba(0, 0, 0,.1)");
 	   // $(".tool").css("display","inline-block");
 	   // $(".tit .btn-group").css("display","inline-block");
 	   // $(" [contenteditable=false]").addClass("contenteditable");
 	   // $("[contenteditable=false]").attr("contenteditable","true");
 	    
 	    history.go(-1);
 	    
 	});
});

//--------------------------------------------------------end------------------------------------------------
//-----------------------------------------------�򱨸���-------------------------------------------------------
function profileCallback(data,elementId){
        if (data==null||data==""){
            $("#"+elementId).html("<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>");
            return false;
        }else{
            $("#"+elementId).html(data[3]);
        }
    }




//-----------------------------------------------��Ϣʱ����������ͼ------------------------------------------------------
function LineCallback(data,elementId){
	// console.log(data);
        var c1 = document.getElementById(elementId);
        if (data==null||data==""){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }else{
            data = data[0];
           
            data = eval(data);
            if(data[0].data==null || data[0].data.length==0){
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
               // stat1.innerHTML = '';
                return;
            }else{
                var _chartColumn10 = LineChart(data[0],elementId);
               // $("#container1 + .text").text(data1);
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
                    chart1.setTheme('infographic');
                    chart1.setOption(option);
                    var enConfig = require('echarts/config');
                }
        );
    }
    
    function BarTimeCallBack(data,elementId){
        var c1 = document.getElementById(elementId);
        if (data==null||data==""){
            c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }else{
            data = eval(data[2]);
            if(data[0].data==null || data[0].data.length==0){
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                //stat1.innerHTML = '';
                return;
            }else{
                var _chartColumn10 = BarTimeChart(data[0],elementId);
            }
        }
    }
    function BarTimeChart(data,dom){
    	//console.log(data)
   	 var config = require(
   	           [  
   	            'echarts',
   	            'echarts/chart/bar'
   	           ],
   	           function (ec) {
   	        	   	var chart = ec.init(document.getElementById(dom));
   	                var option = {
   	                	    tooltip : {
   	                	        trigger: 'axis',
   	                	        axisPointer : {            // ������ָʾ���������ᴥ����Ч
   	                	            type : 'shadow'        // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
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
   	                                    lang: ['������ͼ', '�ر�', 'ˢ��']
   	                                },
   	                                restore : {show: true},
   	                                saveAsImage : {
   	                                    show: true,
   	                                    name:data.name
   	                                },
   	                            }
   	                        },
   	                	    calculable : true,
   	                	    yAxis : [
   	                	        {
   	                	            type : 'value',
   	                	         axisLine: {
                                     onZero: false,
                                     show:false
                                 },
                                 splitLine:{
                                     show:false
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
            	               	 },
   	                	        }
   	                	    ],
   	                	    xAxis : [
   	                	        {
   	                	            type : 'category',
   	                	            data : data.datetime
   	                	        }
   	                	    ],
   	                	    series : data.data
   	                	    }
   	                chart.setOption(option);
   	                chart.setTheme('infographic');
   	                var enConfig = require('echarts/config');
   	               
   	           }
   	       );
 }
    
    
    

    
//---------------------------------------------------------����ֲ���״ͼ-----------------------------------------------
   function  BarMapCallBack(data,elementId){
	    
	   // console.log(data);
        var c4 = document.getElementById(elementId);
        if (data==null||data==""){
            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+base+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return false;
        }else{
        	data = data[4];
            data = eval(data);
            if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
                c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else{
            	
            	var _chartColumn10 = BarMapChart(data[0],elementId);
            }

        }
    }
    
    //��״ͼ ��ͼ
   function BarMapChart(data,id){
	   
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
          		    	y1:10,
          	     		y2:60
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
//----------------------------------------------------------��׼�Ǳ�--------------------------------------------------------------------------
   function  WatchCallback(data,elementId){
   	var eleId = $("#"+elementId).attr("value");
    	        var c4 = document.getElementById(elementId);
    	        if (data==null||data==""){
    	            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+base+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
    	            return false;
    	        }else{
    	        	if(eleId==10){//���
    	           		data = data[0];
    	           	}else if(eleId==11){   //������
    	           		data = data[1];
    	           	}
    	            data = eval(data);
    	            if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
    	                c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
    	                return;
    	            }else{
    	            	var _chartColumn10 = WatchChart(data[0],elementId);
    	            }

    	        }
    }
   
   
   function WatchChart(data,id){
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
					                  splitNumber: 10,       // �ָ������Ĭ��Ϊ5
					                  axisLine: {            // ��������
					                      lineStyle: {       // ����lineStyle����������ʽ
					                          color: [[0.2, '#228b22'],[0.8, '#48b'],[1, '#ff4500']], 
					                          width: 8
					                      }
					                  },
					                  axisTick: {            // ������С���
					                      splitNumber: 10,   // ÿ��splitϸ�ֶ��ٶ�
					                      length :12,        // ����length�����߳�
					                      lineStyle: {       // ����lineStyle����������ʽ
					                          color: 'auto'
					                      }
					                  },
					                  axisLabel: {           // �������ı���ǩ�����axis.axisLabel
					                      textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
					                          color: 'auto'
					                      }
					                  },
					                  splitLine: {           // �ָ���
					                      show: true,        // Ĭ����ʾ������show������ʾ���
					                      length :30,         // ����length�����߳�
					                      lineStyle: {       // ����lineStyle�����lineStyle������������ʽ
					                          color: 'auto'
					                      }
					                  },
					                  pointer : {
					                      width : 5
					                  },
					                  title : {
					                      show : true,
					                      offsetCenter: [0, '-40%'],       // x, y����λpx
					                      textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
					                          fontWeight: 'bolder'
					                      }
					                  },
					                  detail : {
					                      formatter:'{value}%',
					                      textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
					                          color: 'auto',
					                          fontWeight: 'bolder'
					                      }
					                  },
					                  data:[{value: minValue, name: minName}]
					              }
					          ]}
						       chart.setOption(option);
                   			   chart.setTheme('macarons');
						       var enConfig = require('echarts/config');            
               });
	   
	   
	   
	   
   }
   
 //---------------------------------------------------------����Ǳ�ͼ����з����������⣩-------------------------------------------------------------------------
   function  AreaWatchCallback(data,elementId){
		var eleId = $("#"+elementId).attr("value");
    	        var c4 = document.getElementById(elementId);
    	        if (data==null||data==""){
    	            c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
    	            return false;
    	        }else{
    	        	if(eleId==10){//���
    	           		data = data[0];
    	           	}else if(eleId==11){   //������
    	           		data = data[1];
    	           	}
    	            data = eval(data);
    	            if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
    	                c4.innerHTML = "<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
    	                return;
    	            }else{
    	            	var _chartColumn10 = AreaWatchChart(data[0],elementId,1);
    	            }

    	        }
    }
   
   
   function AreaWatchChart(data,id,type){
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
					            //center : ['50%', '90%'],    // Ĭ��ȫ�־���
                                center : ['50%', '65%'],    // Ĭ��ȫ�־���
					            radius : 180,
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
   
   
   //----------------------------------------------------------��Ҫ����-------------------------------------------------------------------------------------
   function MainYqCallback(data,elementId){
	   //$("#"+elementId).html('<img src="'+ njxBasePath +'"/images/imagesvr2/loading_c.gif" style="width: 15px;height: 15px;">');
	  //console.log(elementId)
	   //console.log(data)
  	 if (data==null||data==""){
           $("#"+elementId).html("<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxBasePath+"/images/shouye/warn.png' style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>");
           return;
       }else{
    	   var clickType=$("#clickType").val();
    	  var arr = new Array();
    	arry = elementId.split("_");
    	var seq = arry[1];
    	if(arry[0]=="containerLeft"){
    		seq = "0"+seq;
    	}
    	  var titleColumn = $("#titleColumn_"+seq).val();
    	  if(titleColumn!=null&&titleColumn!=""){
    	  var html='<thead><tr>';
    	  for(var i=0;i<titleColumn.length;i++){
    		     arr[i] = titleColumn.substring(i,i+1);
    		  if(arr[i]==1){
    			 html +='<th style="width:40px;">����</th>'; 
    		  }else if(arr[i]==2){
    			  html +='<th style="width:500px;">����</th>';
    		  }else if(arr[i]==3){
    			  html +='<th style="width:100px;">��Դվ��</th>';
    		  }else if(arr[i]==4){
    			  html +='<th style="width:150px;">����</th>';
    		  }else if(arr[i]==5){
    			  html +='<th style="width:80px;">����������</th>';
    		  }else if(arr[i]==6){
    			  html +='<th style="width:150px;">��������Դ</th>';
    		  }else if(arr[i]==7){
    			  html +='<th style="width:60px;">����</th>';
    		  }else if(arr[i]==8){
    			  html +='<th style="width:500px;">������ժҪ</th>';
    		  }
    		 }
    	  if(clickType==2){
    		  html+='<th style="width:40px;">����</th>';
    	  }
    	  html +='</tr></thead><tbody>';
    	  
      	 for(var i=0;i<data.length;i++){
             /*var title = delHtmlTag(data[i].title);
      		 if(title!=""&&title!=null&&title.length>35){
      			title = title.substring(0,35);
      		 }*/
             var title = data[i].title;
      		 var captureWebsiteName = data[i].captureWebsiteName;
      		 var date = new Date(data[i]["published"]).format('yyyy-MM-dd hh:mm');
      		 var num = data[i].repeatNum;
      		 if(num == 0){
      			num = 1;
      		 }
      		 /*var summary = delHtmlTag(data[i].summary);
      		 if(summary!=""&&summary!=null&&summary.length>100){
      			summary = summary.substring(0,100);
      		 }*/
             var summary = data[i].summary;
      		 var custom = data[i].customFlag1;
             var webpageUrl = data[i].webpageUrl;
      		 var id = data[i].id;
      		html +='<tr class="Tr_'+id+'">';
      		 for(var j=0;j<titleColumn.length;j++){
      			arr[j] = titleColumn.substring(j,j+1);
      		    	
      			if(arr[j]==1){
      				html +='<td>'+(i+1)+'</td>';
      			}else if(arr[j]==2){
      				html +='<td class="title_'+id+'" style="font-weight: bold;">';
                    html += '<a href="'+webpageUrl+'" target="_blank" style="color:#595959;">'+title+'</a>';
                    html += '</td>';
      			}else if(arr[j]==3){
      				html +='<td class="captureWeb_'+id+'">'+captureWebsiteName+'</td>';
      			}else if(arr[j]==4){
      				html +='<td class="date_'+id+'">'+date+'</td>';
      			}else if(arr[j]==5){
      				html +='<td >'+num+'</td>';
      			}else if(arr[j]==6){
      				html +='<td class="capture_'+id+'">'+captureWebsiteName+'</br>'+date+'</td>';
      			}else if(arr[j]==7){
      				html +='<td class="customFlag1_'+id+'">';
      				if(custom==4){
      					html +='������';
      				}else{
      					html +='<font color="red">����</font>';
      				}
      				html +='</td>'
      			}else if(arr[j]==8){
      				if(captureWebsiteName=="����΢��" || captureWebsiteName=="��Ѷ΢��"){
      					html +='<td class="summary_'+id+'"><span style="text-align: left; float:left"><span style="font-weight: bold;">ժҪ��</span>'+summary+'</span></td>';
      				}else{
      					html +='<td class="summary_'+id+'"><span style="margin:0 auto;font-weight: bold;">'+title+'</span></br><span style="text-align: left; float:left"><span style="font-weight: bold;">ժҪ��</span>'+summary+'</span></td>';
      				}
      			}
      		 }
      		
      		html +='<td class="operation"><div class="btn-group">' +
           			'<a class="btn dropdown-toggle" data-toggle="dropdown" href="javascript:void(0)">' +
           			'<i class="icon-edit"></i></a><ul class="dropdown-menu align_l">' +
           			'<li><a href="#form_addMaterial" data-toggle="modal" onclick="addBriefReportSource('+"'"+elementId+"'"+');">����</a></li>' +
           			'<li><a href="#form_addMaterial" data-toggle="modal" onclick="editBriefReportSource('+"'"+id+"'"+');">�޸�</a></li>' +
           			'<li><a href="javascript:void(0)" onclick="deleteSignalNews('+"'"+id+"'"+');">ɾ��</a></li>' +
           			/*'<li><a href="javascript:void(0)">����</a></li>'+*/
           			'</ul></div></td></tr>';
      		 
      	 }
      	html +='</tbody>';
        	$("#"+elementId).html(html);
       }else{
    	   
    	   $("#"+elementId).html("<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxBasePath+"/images/shouye/warn.png' style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
    	   
       }
        	
       }
  	
  }

function delHtmlTag(str) {
    if (str) {
    return str.replace(/<[^>]+>/g,"");//ȥ�����е�html���
    }
    return "";
}
    //---------------------------------------------------------���鵼��----------------------------------------------------------------------
    function introduceCallback(data,elementId){
    	var styleId = $("#"+elementId).attr("value");
    	 if (data==null||data==""){
             $("#"+elementId).html("<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src='"+njxBasePath+"/images/shouye/warn.png' style=\"width:60px\"><br/>��ʱ���������Ϣ</p></div>");
             return;
         }else{
        	 $("#"+elementId).html("");
        	 var html="";
        	 for(var i=0;i<data.length;i++){
        		 
            	var customFlag1 = data[i].customFlag1;
            	var customFlag="";
            	if(customFlag1==4){
            		customFlag = "������";
            	}else{
            		customFlag = "<font color='red'>����<font>";
            	}
            	var date =new Date(data[i]["published"]).format('yyyy-MM-dd hh:mm');
            	var webpageUrl = data[i].webpageUrl;
            	var captureWebsiteName = data[i].captureWebsiteName;
            	/*var content = delHtmlTag(data[i].content);
            	if(content!=""&&content!=null&&content.length>100){
            		content = content.substring(0,100)+"...";
            	}
            	var summary = delHtmlTag(data[i].summary);
            	if(summary!=""&&summary!=null&&summary.length>100){
            		summary = summary.substring(0,100)+"...";
            	}
            	var title = delHtmlTag(data[i].title);
            	if(title!=""&&title!=null&&title.length>60){
            		title = title.substring(0,60)+"...";            	
            	}
            	*/
                 var content = data[i].content;
                 var summary = data[i].summary;
                 var title = data[i].title;
            	var author = data[i].author;
            	if(author==null ||author==""){
            		author = "��"
            	}
            	//��ʽһ
            	if(styleId==19){
            		//console.log(data)
            		 html += "<div class='row-fluid'  class='Tr_"+data[i].id+"'><p><em >��"+(i+1)+"��<span class='title_"+data[i].id+"'>"+title+"</span></em></p>" +
            		"<div class='yqddStyle1'>" +
            		"<table width='100%' border='0' cellspacing='0' cellpadding='0' class='table table2 table3'>" +
            		"<tbody><tr><th width='13%'>����</th>" +
        			"<th width='17%' class='customFlag1_"+data[i].id+"'>"+customFlag+"</th>" +
        			"<th width='15%' >������Դ</th>" +
        			"<th width='25%' class='captureWeb_"+data[i].id+"'>"+captureWebsiteName+"</th>" +
        			"<th width='10%'>ʱ��</th>" +
        			"<th width='25%' class='date_"+data[i].id+"'>"+date+"</th></tr>" +
        			"<tr><td  colspan='3' style='font-size: 14px;font-weight: 600;color: #595959;'><span style='float: left;'>��ͬ������:</span><span>"+data[i].repeatNum+"</span></td>" +
        			"<td  colspan='4' style='font-size: 14px;font-weight: 600;color: #595959;'><span style='float: left;'>����:</span><span>"+author+"</span></td></tr>"+
        			"<tr><td>����</td><td colspan='5' class='tdCon'><p class='content_"+data[i].id+"'>"+content+"</p></td></tr>" +
        			"<tr><td colspan='6' style='text-align:left; padding:10px;'>" +
		        		"<p style=' width:750px;'  class='webpageUrl_"+data[i].id+"'>" +
		        		"<a href='"+webpageUrl+"' target='_blank'>ԭ�����ӣ�"+webpageUrl+"</a></p></td></tr></tbody></table></div></div>";
            	}else if(styleId==20){
            		//��ʽ��
                	 html += "<div class='row-fluid' class='Tr_"+data[i].id+"'><p><em >��"+(i+1)+"��<span class='title_"+data[i].id+"'>"+title+"</span></em></p>" +
                	 		"<div class='yqddStyle2' style='display: block;'>" +
                	 		"<dl><dd class='span6'><b>����ý�壺</b><span class='captureWeb_"+data[i].id+"'>"+captureWebsiteName+"</span></dd><dd class='span6'>"+date+"</dd></dl>";
                	 html +="<dl><dd class='span6'><b>��ͬ��������</b><span >"+data[i].repeatNum+"</span></dd><dd class='span6'><b>���ߣ�</b><span >"+author+"</span></dd></dl>";
                	 html += "<dl><dd class='span12'><b>������ַ:</b><span class='webpageUrl_"+data[i].id+"'>" +
                	 		"<a href='"+webpageUrl+"'>"+webpageUrl+"</a></span></dd></dl>";
                	 html += "<div class='cont'><h1>����ժҪ��</h1><p>"+summary+"</p></div></div></div>"

            	}
            	
 
        	 }
        	 $("#"+elementId).html(html);
         }
    	
    }
    
   
//---------------------------------------------��ͼ������ֲ���-------------------------------------------------------------------------
    function MapCallBack(data,elementId){
    	
        var c5 = document.getElementById(elementId);
        if (data==null||data==""){
            c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	data = data[2];
            var res = eval(data);
          //  console.log(res);
            if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
                c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;

            }
            var length;
            var html = "";
            if(res[0].data.length>10){
            	length = 10;
            }else{
            	length = res[0].data.length;
            }
            //alert(length)
            //var length = res[0].data.length>10?10:res[0].data.length;
            for(var i=0;i<10;i++){
            	html +='<tr style="height:35px;">';
            	if(res[0].data[i]!=null &&res[0].data[i]!=""){
            		html +='<td>'+res[0].data[i].name+'</td><td>'+res[0].data[i].value+'</td>';
            	}else{
            		html +='<td></td><td></td>';
            	}
                html += '</tr>';
            }
            $(".c5_tb").html(html);
            var _chartColumn10 = MapChart(data,elementId);
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
                            calculable : true,
                            text:['��','��'],
                            color: ['orangered','yellow','lightskyblue']
                        },
                        series : [ {
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
    
    
  
   
   
    //------------------------------------------------��վͳ��  --------------------------------------------------------------------------
    function WebsitCallback(data,elementId){
    	//console.log(11111)
        if (data==null||data==""){
            $("#"+elementId).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }
     //  console.log(data)
        if(data!=""&&data!=null){
        	var html="";
        	for(var i=0;i<data.length;i++){
        		var obj=data[i];
        		var tr="<tr>";
        		tr+="<td>"+obj.name+"</td>";
        		//ƴ���¼�list
        		if(obj.statList!=null){
        		var next_row=obj.statList.length;
        		if(next_row>0){
        			//��ѭ������������
	        		var td_num="";
	        		//��ѭ��������ú������
	        		var td_name="";
	        		var td_index="";
	        		
	        		for(var j=0;j<next_row;j++){	        			
	        			var name = obj.statList[j].name;
	        			var num = obj.statList[j].num;
	        			//td_index+="<dd>"+(j+1)+"</dd>";
	  					td_name+="<dd>"+name+"</dd>";
	  					td_num+="<dd>"+num+"</dd>";
	        			
	        		}
	        		//��2��td�ŵ�tr��ȥ
	        		//tr+="<td><dl class='jcdx'>"+td_index+"</dl></td>";
	        		tr+="<td><dl class='jcdx'>"+td_name+"</dl></td>";
	        		tr+="<td><dl class='jcdx'>"+td_num+"</dl></td>";
	        		}
        		
        		}
        		tr+="</tr>";
        		html +=tr;
        	}
        	 $("#"+elementId).html(html);
        }else{
        	$("#"+elementId).innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }
    }
    
    //-------------------------------------------��״ͼ====��ý���Ծ�ȣ�-------------------------------------------------------------------------
    function WTColumnarCallBack(result,elementId){
    	//console.log(result)
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
            var data = eval(result[0]);
           // console.log(result[0])
            if(data==null || data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            	}else{
            		 var _chartColumn10 = WTBarChart(data[0],elementId);
            	}
               
                
            }

        }
    }
    function WTBarChart(data,dom){
    	//console.log(data)
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
                                        value = value.substring(0,4);
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

                        /* color:['#87cefa','#ff7f50'], */
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
                }
        );
    }
    //--------------------------------------------------�������ͼ----------------------------------------------------------------
    
    function AreaMovesCallback(result,elementId){    		
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	result = result[0];
            var data = eval(result);
            if(data[0].data==null || data[0].data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            	    return;
            	}else{
            		 var _chartColumn10 = AreaMovesChart(data[0],elementId);
            	}
               
                
            }

        }
    }
    function AreaMovesChart(data,dom){
    	//console.log(data)
        var config = require(
                [
                    'echarts',
                    'echarts/chart/line'
                ],
                function (ec) {
                    var chart2 = ec.init(document.getElementById(dom));
                    var option = {
                    	    
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
                    	    calculable : true,
                    	    xAxis : [
                    	        {
                    	            type : 'category',
                    	            boundaryGap : false,
                    	            data : data.datetime
                    	        }
                    	    ],
                    	    yAxis : [
                    	        {
                    	            type : 'value'
                    	        }
                    	    ],
                    	    series : [
                    	        {
                    	        	name:'����',
                    	            type:'line',
                    	            smooth:true,
                    	            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                    	            data:data.data
                    	        }
                    	       
                    	    ]}
                    chart2.setTheme("infographic");
                    chart2.setOption(option);
                    var enConfig = require('echarts/config');
                }
        );
    }
    //--------------------------------------------------������Ʊ�ע---------------------------------------------------------------------
    function AreaLableMovesCallback(result,elementId){
    	var data = result[2];
    	//console.log(data)
    var c3 = document.getElementById(elementId);
    if (result==null||result==""){
        c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
        return;
    }else{
        var data = eval(result[2]);
        if(data[0].data==null || data[0].data.length==0){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else {
            var _chartColumn10 = AreaLableMovesChart(data[0],result[1],elementId);
            
        }

    }
}
function AreaLableMovesChart(data,data1,dom){
	//console.log(data)
	//console.log(data1)
	  var timebr =[];
		$.each(data.datetime, function(i, n) {
			timebr.push(n);
		});
	$("#"+dom).highcharts({
        title: {
            text: null
        },
        xAxis: {
            categories: timebr,
            tickInterval: 1,
            lineWidth: 1,
            labels: {
            	formatter: function() {
            		if (typeof this.value == 'string')
            			return new Date(this.value.split('-').join('/')).format('MM-dd hh:mm').replace(' ', '<br />');
                }
            }
        },
        yAxis: {
            min: 0,
            gridLineWidth: 1,
            gridLineColor: "rgba(150,150,150,0.8)",
            title: {
                text: null
            },
        },
        legend: {
            enabled: false
        },
        tooltip: {
            shared: true,
            formatter: function() {/*
            	var _this = this;
            	if (_this.points) {
            		var v = _this.x;
            		
            		for (var i = 0, l = _this.points.length; i < l; i++)
            			v += '<br/>' + _this.points[i].series.name + ' : ' + _this.points[i].y;
            		
//            		v += '<br/>�ؼ�ת���ߣ�' + keyUser + '<br/>����ת����' + keyUserCount;
            		
            		return v;
            	} else {
            		var users = [];
                 	var value;
                 	var user;
                 	$.each(keyUserTop, function(i, n){
                    	if (n.userId == _this.point.userId)
                    		user = n;
                 	});
                 	if (user != null) {
                        return '�û��ǳƣ�' + user.userScreenName + '<br />'
                                + '����ת����' + user.statusRepostsCount + '<br />' 
                                + 'ת��ʱ�䣺' + user.statusCreatedAt;
                 	} else {
                 		return '';
                 	}
            	}
            */}
        },
        plotOptions: {
            area: {
                lineWidth: 2,
                marker: {
                    radius: 2.5,
                    enabled: true
                },
                fillOpacity: 0.3
            },
            bubble: {
                minSize: 7,
                maxSize: 25
            }
        },
        series: [{
        	name: "ת��",
            type: "area",
            color: "#ff6633",
            data: data
        }, {
        	name: "�ؼ���",
            type: "bubble",
            color: "#f29b22",
            data: (function () {/*
                var d = [];
                $.each(keyUserTop, function(i, n) {
                	var value = n.statusCreatedAt.split('-').join('/');
                	
                	var obj = {};
                	obj.name = 'name' + i;
                	obj.x = result.legend.indexOf(new Date(value).format("yyyy-MM-dd hh:00:00"));
                	if (obj.x == -1)
                		obj.x = result.legend.indexOf(new Date(value).format("yyyy-MM-dd 00:00:00"));
                	var min = 0, max = result.repostsList[obj.x];
                	obj.y = Math.floor(min + Math.random() * (max - min));
                	obj.z = n.statusRepostsCount;
                	obj.userId = n.userId;
                	
                  	d.push(obj);
                });
                return d;
        	*/})()
        }]
    });

	 /*var config = require(
             [
                 'echarts',
                 'echarts/chart/scatter',
                 'echarts/chart/line'
             ],
             function (ec) {
                 var chart2 = ec.init(document.getElementById(dom));
                 var option = {
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
                		    dataRange: {
                		        min: 0,
                		        max: 100,
                		        orient: 'horizontal',
                		        y: 'top',
                		        //text:['��','��'],           // �ı���Ĭ��Ϊ��ֵ�ı�
                		        color:['lightgreen'],
                		        splitNumber: 1
                		    },
                		    xAxis : [
                		        {
                		            type : 'category',
                		            boundaryGap : false,
                		            data : function (){
                		            	var time = data.datetime;
                		                return time;
                		            }()
                		        },
                		        {
                		            type : 'value',
                		            scale : true,
                		            splitNumber: 29,
                		            axisLabel: {show:false},
                		            splitLine: {show:false}
                		        }
                		    ],
                		    yAxis : [
                		        {
                		            type : 'value'
                		        },
                		        {
                		            type : 'value'
                		        }
                		    ],
                		    animation: false,
                		    series : [
                		        {
                		            name:'ɢ��',
                		            type:'scatter',
                		            tooltip : {
                		                trigger: 'item',
                		                formatter : function (params) {
                		                	console.log(params)
                		                   return '2013-03-' + params.value[0] + '<br/>'
                		                           + params.seriesName + ' : ' 
                		                           + params.value[1] + ', ' 
                		                           + params.value[2]; 
                		                }
                		            },
                		            yAxisIndex:1,
                		            xAxisIndex:1,
                		            symbol: 'circle',
                		            symbolSize: function (value){
                		            	//console.log(value)
                		                return Math.round(value[2]/10);
                		            },
                		            data: (function () {
                		                var d = [];
                		                $.each(data1, function(i, n) {                                        	
                                        	var obj = new Array;
                                        	obj[0] = Math.round(Math.random()*10) + 1;
                                        	obj[1] = n.repeatNum;
                                        	
                                        	//obj[2] = data.datetime.indexOf(new Date(n.published).format("yyyy-MM-dd hh"));
                                        	//var min = 0, max = data.datetime[obj[2]];
                                        	//console.log(max)
                                        	//(Math.random()*30).toFixed(2)
                                        	obj[2] =  (Math.random()*30).toFixed(2) - 0;

                                        	
            	                          	d.push(obj);
                                        });
                		                console.log(d);
                		                return d;
                		                
                		            })()
                		        },
                		        {
                		            name:'����',
                		            type:'line',
                		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
                		            data:function (){
                		                var list = [];
                		                for (var i = 1; i <= data.data; i++) {
                		                	
                		                    list.push(Math.round(Math.random()* 30));
                		                }
                		                return data.data;
                		            }()
                		        }
                		    ]}
                 chart2.setOption(option);
                 var enConfig = require('echarts/config');
             }
     );*/

}
    //---------------------------------------------��Դ����  ��èצͼ��------------------------------------------------------------------------------
    
    
    function CatCallback(data,elementId){
        if (data==null||data==""){
            $("#"+elementId)[0].innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }
        if(data[0]!="" && data[0]!=null){
            var lis = $("#"+elementId+" li");
            var total = 0;
            var percent = 0;
            $.each(data[0],function(n){
                total += this.num;
                percent = (this.percent).toFixed(1)+"%";
                //console.log(percent)
                lis.eq(n+1).find("p:eq(0)").text(this.name);
                lis.eq(n+1).find("p:eq(1)").text(this.num);
                lis.eq(n+1).find("p:eq(2)").text(percent);
            });
            lis.eq(0).find("p:eq(1)").text(total);
        }else{
        	 $("#"+elementId)[0].innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
             return;
        }
    }
    
  //---------------------------------------------------------��׼��ͼ---����Դ���ͣ�------------------------------------------------------------------
    function PieCallback(result,elementId){ 
        
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	result = result[1];
            var data = eval(result);
            if(data[0].data==null || data[0].data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            		return;
            	}else{
            		var _chartColumn10 = PieChart(data[0],elementId);
            	}
                

            }

        }
    }
    function PieChart(data,dom){
		//console.log(data);
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
		                	     legend:{
		                	    	//  orient : 'vertical',
		                	        //  x : 'left',
		                	    	  y:'8%',
		                	    	  data:data.legend
		                	    },  
		                	    calculable : false,
		                	    /* color:['#cd5c5c','#ba55d3','#ff69b4','#6495ed','#32cd32','#87cefa','#ff7f50','#8d98b3','#a16661'], */
		                	    series : [
		                	        {
		                	        	name:data.title,
		                	            type:'pie',
		                	           //  radius : ['35%', '55%'], 
		                	            radius : '45%',
		                	            //center: ['45%', '50%'],
		                	            startAngle:0,
		                	            minAngle:5,
		                	            roseType:'null',

		                	            itemStyle : {
		                	                normal : {
		                	                    label : {
		                	                        show : true,
		                	                        textStyle:{
		                	                        	fontSize:'14',
		                	                        	fontWeight:'bold'
		                	                        },
		                	                        position:'outer',
	                                                formatter: "{b} : {d}%"
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
		                

		           });
	}
 //----------------------------------------------------------����ͼ�������⡢��з�����-----------------------------------------------------
    function RingCallback(result,elementId){ 
    	var eleId = $("#"+elementId).attr("value");
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	if(eleId==10){//���
        		result = result[5];
        	}else if(eleId==11){   //������
        		result = result[6];
        	}
            var data = eval(result);
            console.log(result);
            if(data==null || data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            		return;
            	}else{
                var _chartColumn10 = RingChart(data[0],elementId);
            	}
            }

        }
    }
    function RingChart(data,dom){
    	//console.log(data)
        var config = require(
                [
                    'echarts',
                    'echarts/chart/pie',
                ],
                function (ec) {
                    var chart4 = ec.init(document.getElementById(dom));
                    var dataStyle = {
                    	    normal: {
                    	        label: {show:false},
                    	        labelLine: {show:false},
                    	      //  color:['#f29300']
                    	    }
                    	};
                    
                    	
                    var option = {
                    		title: {
                    	        text: data.title,
                    	        x: 'center',
                    	        y: 'center',
                    	        itemGap: 20,
                    	        textStyle : {
                    	            color : 'rgba(30,144,255,0.8)',
                    	            fontFamily : '΢���ź�',
                    	            fontSize : 20,
                    	            fontWeight : 'bolder'
                    	        }
                    		},
                    	    tooltip : {
                    	        show: true,
                    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    	    },
                    	    legend: {
                    	    	orient : 'vertical',
                                x : 'left',
                    	    	orient : 'vertical',
                    	    	//x : document.getElementById(dom).offsetWidth / 2,
                    	    	y : 60,
                    	        itemGap:12,
                                data:data.legend,
                                show:true
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
                    	    series : [
                    	        {
                    	            type:'pie',
                    	            clockWise:false,
                    	            radius : [100, 125],
                    	           itemStyle : dataStyle,
                    	            data:data.data[0].data
                    	        },
                    	        {
                    	            type:'pie',
                    	            clockWise:false,
                    	            radius : [75, 100],
                    	           itemStyle : dataStyle,
                    	            data:data.data[1].data
                    	        },
                    	        
                    	    ]
                            }
                    
                    chart4.setOption(option);
                    chart4.setTheme("infographic");
                    var enConfig = require('echarts/config');
                }
        );

    }
 //------------------------------------------------------------Բ��ͼ-----����Դ���͡������⡢��з�����   ---------------------------------------------------------

    function AreaRingCallback(result,elementId){ 
    	//console.log(result)
    	//console.log(elementId)
    	var eleId = $("#"+elementId).attr("value");
        
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	if(eleId==10){//���
        		result = result[7];
        		//console.log(result)
        	}else if(eleId==11){   //������
        		result = result[8];
        	}else{   
        		result = result[1];
        	}
            var data = eval(result);
            if(data==null || data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            		return;
            	}else{
                var _chartColumn10 = AreaRingChart(data[0],elementId);
            	}
            }

        }
    }
    function AreaRingChart(data,dom){
    	//console.log(data)
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
                            y:10,
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
                            //orient : 'vertical',
                           // x : 'left',
                        	y:'10%',
                            data:data.legend
                        },
                        series : [
                            {
                                name:data.title,
                                type:'pie',
                                radius : ['30%', '50%'],
                               // radius : '45%',
                                //center: ['45%', '70%'],
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
                }
        );

    }
   //---------------------------------------õ��ͼ-----------------------------------------------------------------------------------
    
    
    function RoseCallBack(result,elementId){
      // console.log(result);
      // console.log(elementId)
    	var eleId = $("#"+elementId).attr("value");
        var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	if(eleId==9){
        		result = result[1];
        		//console.log(result)
        	}else{
        		result = result[2];
        	}
        	 
            var data = eval(result);
            if(data==null || data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
                return;
            }else {
            	if(data[0].data==null || data[0].data.length==0){
            		c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            		return;
            	}else{
                var _chartColumn10 = rosePieChart(data[0],elementId);
            	}
            }

        }
    }
    function rosePieChart(data,dom){
    //	console.log(data)
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
                                //formatter: "{a} <br/>{b} : {c} ({d}%)"
                                formatter: "{a} <br/>{b} : {d}%"
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
                            calculable : true,
                           
                            legend:{
                                //orient : 'vertical',
                                //x : 'left',
                            	x : '15%',
                                data:data.legend,
                                formatter:function(v){
                                    return v.length>30? v.substr(0,30):v;
                                }
                            },
                        series : [
                            {
                                name:data.title,
                                type:'pie',
                                center:['45%','45%'],
                              /*  radius : ['20%','55%'],*/
                                radius :'55%',
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
    
 //-----------------------------------------------------������-------------------------------------------------------------------------------
    
    
    function MonitorCallback(result,elementId){

    	var c3 = document.getElementById(elementId);
        if (result==null||result==""){
            c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+njxBasePath+"/images/shouye/warn.png\" style=\"width:60px; height: 60px;\"><br/>��ʱ���������Ϣ</p></div>";
            return;
        }else{
        	var data = result[3];
        	//console.log(data)
        	c3.innerHTML = data;
        }
    }
 