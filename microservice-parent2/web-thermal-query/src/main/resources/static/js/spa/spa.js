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
        };

        $(function() {
        	if($("#code").val()=="0000"){
                if ($('#showFlag').val() == 1) {
                    $('#keywordTitle').html('“' + $('#keyword').val() + '”<span id="keywordTitleTime">24小时</span>信息监测');
                } else if ($('#showFlag').val() == 2) {
                    $('#keywordTitle').text('“' + $('#keyword').val() + '”网络热点信息监测');
                }

                var now = new Date();
                $('#starttime').val(new Date(now.getTime() - 1 * 24 * 3600 * 1000).format('yyyy-MM-dd hh:mm:ss'));
                $('#endtime').val(now.format('yyyy-MM-dd hh:mm:ss'));
                $('#timeDomain').val(24);
                $('#isHourSeach').val(true);

                //goChartAndListInfo();
                //heatReport();

                $('#timerange-selector li').click(function() {
                    $('#timerange-selector li').each(function(i, n) {
                        $(n).removeClass('selected');
                    });
                    $(this).addClass('selected');

                    var now = new Date();
                    var ev = $(this).attr('data-cmd');
                    if (ev > 0) {
                        if (ev == 24) {
                            $('#starttime').val(new Date(now.getTime() - 1 * 24 * 3600 * 1000).format('yyyy-MM-dd hh:mm:ss'));
                            $('#keywordTitleTime').text('24小时');
                        } else if (ev == 3) {
                            $('#starttime').val(new Date(now.getTime() - 2 * 24 * 3600 * 1000).format('yyyy-MM-dd 00:00:00'));
                            $('#isHourSeach').val(false);
                            $('#keywordTitleTime').text('3天');
                        } else if (ev == 7) {
                            $('#starttime').val(new Date(now.getTime() - 6 * 24 * 3600 * 1000).format('yyyy-MM-dd 00:00:00'));
                            $('#isHourSeach').val(false);
                            $('#keywordTitleTime').text('7天');
                        }
                        $('#timeDomain').val(ev);
                        $('#page').val(1);

                        goChartAndListInfo();
                    }
                });

                $('#attrButtonsGroup label').click(function() {
                    var ev = $(this).attr('data-cmd');
                    if (ev >= 0) {
                        $('#otherAttribute').val(ev);
                        $('#page').val(1);
                        subForm();
                    }
                });

                $('#sortButtonsGroup label').click(function() {
                    var ev = $(this).attr('data-cmd');
                    if (ev >= 0) {
                        $('#paixu').val(ev);
                        $('#page').val(1);
                        subForm();
                    }
                });

                $('div.button-call-to-action, a.button-call-to-action').click(function() {
                    window.open('www.wrd.cn/home.shtml');
                });

                $('div.gb_tab li').click(function() {
                    $.each($('div.gb_tab li'), function(i, n) {
                        $(this).removeClass('on');
                    });
                    $(this).addClass('on');
                });
            }
        });

        function goChartAndListInfo() {
        	goLineChart();
        	goWTChart();
        	goQGPieChart();
        	goOTPieChart();
        	goMapChart();
        	subForm();
        }

        function goLineChart() {
        	chartloading('satusTree1');
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	var timeDomain = $('#timeDomain').val();
            var otherAttribute = $('#otherAttribute').val();
            if ($('#showFlag').val() == 1)
            	EChartsDwr.getLineChart($('#userId').val(), timeDomain, $('#keywordId').val(), 3, starttime, endtime, 2, otherAttribute, 0, GetRandom(30), LineCallBack);
            else if ($('#showFlag').val() == 2)
            	EChartsDwr.getLineChartSpaKeyword($('#userId').val(), timeDomain, $('#keyword').val(), 3, starttime, endtime, 2, otherAttribute, 0, GetRandom(30), LineCallBack);
        }
        function LineCallBack(data) {
        	var stat1 = document.getElementById("satusTree1");
        	stat1.innerHTML = '';
        	var c1 = document.getElementById('container1');

        	if (data == null || data == "") {
        		c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        		return false;
        	} else {
        		data = eval(data);
        		if(data[0].data == null || data[0].data.length == 0) {
        			c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        			return;
        		} else {
        			var _chartColumn10 = LineChart(data[0], 'container1');
        		}
        	}
        }
        function LineChart(data, dom){
        	var isHourSeach = $('#isHourSeach').val();

        	var splitNum = 0;
        	if(data.datetime.length > 12) {
        		splitNum = 2;
        	}
            $.each(data.data, function() {
                this.symbolSize = 6;
                this.itemStyle = {'normal':{'lineStyle':{'width':2.8}}};
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
                		        formatter:function(params) {
                       		        	if(isHourSeach == 'true'){
                     	            		v = new Date(params[0].name).format('MM-dd hh:00');
                     	            	 }else {
                     	            		v = new Date(params[0].name).format('yyyy-MM-dd hh:00');
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
                             	             },
                             	             formatter:function(v){

                             	            	 if(isHourSeach=="true"){
                             	            		v = new Date(v).format("MM-dd hh:00");
                             	            	 }else {
                             	            		v = new Date(v).format("yyyy-MM-dd hh:00");
                             	            	 }
                             	            	 return v;
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
                       chart1.setTheme('infographic');
                        var enConfig = require('echarts/config');
                   }
               );
        }

        function goWTChart(){
        	chartloading("satusTree2");
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	var timeDomain = $('#timeDomain').val();
            var otherAttribute = $('#otherAttribute').val();
            if ($('#showFlag').val() == 1)
            	EChartsDwr.getWTChart($('#userId').val(), timeDomain, $('#keywordId').val(), 3, starttime, endtime, 2, otherAttribute, 0, GetRandom(30), WTCallBack);
            else if ($('#showFlag').val() == 2)
            	EChartsDwr.getWTChartSpaKeyword($('#userId').val(), timeDomain, $('#keyword').val(), 3, starttime, endtime, 2, otherAttribute, 0, GetRandom(30), WTCallBack);
        }
        function WTCallBack(result){
        	var stat2 = document.getElementById("satusTree2");
        	stat2.innerHTML = '';
        	var c2 = document.getElementById("container2");

        	if (result==null||result==""){
        		c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        		return;
        	} else {
        		var data = eval(result[0]);
        		if(data[0].data==null || data[0].data.length==0){
        			c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        			return;
        		}else {
        			var _chartColumn10 = WTBarChart(data[0],"container2");
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
        	                		        //show: true,   //default true
        	                		        /* showDelay: 0,
        	                		        hideDelay: 50,
        	                		        transitionDuration:0,
        	                		        backgroundColor : 'rgba(0,0,0,0.7)',
        	                		        borderColor : '#333',
        	                		        borderRadius : 4,
        	                		        borderWidth: 1,
        	                		        padding: 5,    // [5, 10, 15, 20]
        	                		        position : function(p) {
        	                		            return [p[0] + 10, p[1] - 10];
        	                		        },
        	                		        textStyle : {
        	                		            decoration: 'none',
        	                		            fontFamily: 'Verdana, sans-serif',
        	                		            fontSize: 15,
        	                		            fontStyle: 'italic',
        	                		            fontWeight: 'bold'
        	                		        },
        	                		        formatter: function (params,ticket,callback) {
        	                		            //console.log(params)
        	                		            var res = params[0].name;
        	                		            for (var i = 0, l = params.length; i < l; i++) {
        	                		                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
        	                		            }
        	                		            setTimeout(function (){
        	                		                callback(ticket, res);
        	                		            }, 500)
        	                		            return 'loading';
        	                		        } */
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
                                        x:40,
                                        x2:40,
                                        y:10
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

                     	         	color:['#87cefa'],
        	                	    calculable : false,
        	                	    animation:false,
        	                	    series : [{
        		                	    	name:'数量',
        		                	    	type:'bar',
        		                	    	//data:data.data
        		                	    	data:data.data
        	                	    }]
        	                }
        	                chart2.setOption(option);
        	                var enConfig = require('echarts/config');
        	           }
        	       );
        }

        function goQGPieChart(){
        	chartloading("satusTree4");
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	var timeDomain = $('#timeDomain').val();
            var otherAttribute = $('#otherAttribute').val();
            if ($('#showFlag').val() == 1)
            	EChartsDwr.getQGPieChart($('#userId').val(),timeDomain,$('#keywordId').val(),3,starttime,endtime,2,otherAttribute,0,GetRandom(30),QGPieCallBack);
            else if ($('#showFlag').val() == 2)
            	EChartsDwr.getQGPieChartSpaKeyword($('#userId').val(),timeDomain,$('#keyword').val(),3,starttime,endtime,2,otherAttribute,0,GetRandom(30),QGPieCallBack);
        }
        function QGPieCallBack(data){
        	var stat4 = document.getElementById("satusTree4");
        	stat4.innerHTML = '';
        	var c4 = document.getElementById("container4");
        	if (data==null||data==""){
        		c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        		return false;
        	}else{
        		data = eval(data[0]);
        		if(data==null ||data.length==0|| data[0].data==null || data[0].data.length==0){
        			c4.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        			return;
        		}else{
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
        	                	            radius : ['35%', '60%'],
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
                                                        length : 50
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

        function goOTPieChart(){
        	chartloading("satusTree5");
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	var timeDomain = $('#timeDomain').val();
            var otherAttribute = $('#otherAttribute').val();
            if ($('#showFlag').val() == 1)
            	EChartsDwr.getOTPieChart($('#userId').val(),timeDomain,$('#keywordId').val(),3,starttime,endtime,2,otherAttribute,0,GetRandom(30),OTPieCallBack);
            else if ($('#showFlag').val() == 2)
            	EChartsDwr.getOTPieChartSpaKeyword($('#userId').val(),timeDomain,$('#keyword').val(),3,starttime,endtime,2,otherAttribute,0,GetRandom(30),OTPieCallBack);
        }
        function OTPieCallBack(data){
        	var stat5 = document.getElementById("satusTree5");
        	stat5.innerHTML = '';
        	var c5 = document.getElementById("container5");
        	if (data==null||data==""){
        		c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        		return false;
        	} else {
        		data = eval(data);
        		if(data[0].data==null || data[0].data.length==0){
        			c5.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        			return false;
        		}else{
        			var _chartColumn10 = OTPieChart(data[0],"container5");
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
        	                	            	lang: ['数据视图', '关闭', '刷新']
        	                	            },
        	                	            restore : {show: true},
        	                	            saveAsImage : {
        	                	            	show: true,
        	                	            	name:"来源类型统计图"
        	                	            }
        	                	        }
        	                	    },
        	                	    legend:{
        	                	    	orient : 'vertical',
        	                	        x : 'left',
        	                	    	data:data.legend,

        	                	    },
        	                	    calculable : false,
        	                	    /* color:['#cd5c5c','#ba55d3','#ff69b4','#6495ed','#32cd32','#87cefa','#ff7f50'], */
        	                	    series : [
        	                	        {
        	                	        	name:data.title,
        	                	            type:'pie',
        	                	            radius : ['35%', '60%'],
        	                	            startAngle:0,
        	                	            minAngle:5,
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
                                                        length:50
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
        	           });
        }

        function goMapChart(){
        	chartloading("satusTree6");
        	var starttime = $('#starttime').val();
        	var endtime = $('#endtime').val();
        	var timeDomain = $('#timeDomain').val();
            var otherAttribute = $('#otherAttribute').val();
            if ($('#showFlag').val() == 1)
            	EChartsDwr.getMapChartFX($('#userId').val(),timeDomain,$('#keywordId').val(),3, starttime,endtime, 2, otherAttribute,0,GetRandom(30), MapCallBack);
            else if ($('#showFlag').val() == 2)
            	EChartsDwr.getMapChartFXSpaKeyword($('#userId').val(),timeDomain,$('#keyword').val(),3, starttime,endtime, 2, otherAttribute,0,GetRandom(30), MapCallBack);
        }
        function MapCallBack(data){
        	var stat6 = document.getElementById("satusTree6");
        	stat6.innerHTML = '';
        	var c6 = document.getElementById("container6");
        	if (data==null||data==""){
        		c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        		return;
        	}else{
        		var res = eval(data);
        		if(res==null || res[0]==null || res[0].data==null || res[0].data.length==0){
        			c6.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"" + staticResourcePath + "/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
        			return;

        		}
                var html = "";
                var length = res[0].data.length>10?10:res[0].data.length;
                for(var i=0;i<length;i++){
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
        	        				animation : true,
        	        				tooltip : {
        	        					trigger : 'item',
        	        					enterable:true,
        	        					textStyle : {
        	        						fontSize : 12
        	        					}
        	        				},
        	        				  dataRange: {
        							        min: min,
        							        max: max,
        							        calculable : true,
        							        text:['高','低'],
        							        color: ['#d44e24','#f29300','#f3d647']
        							    },
        	        				series : [ {
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

        	        								},
        	        							},
        	        							/* color : '#87CEEB' */
        	        						},
        	        						emphasis : {
        	        							label : {
        	        								show : true
        	        							}
        	        						}
        	        					},
        	        					/* data : data[0].data, */
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
        	        						 "台湾":[120.90,23.81]
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
        	        			  ]
        	        			};
        	        		chart6.hideLoading();
        	                chart6.setOption(option);
        	                var enConfig = require('echarts/config');
        	           }
        	       );
        }

        function subForm() {
        	chartloading("findlistDiv");
        	
        	var starttime =  $("#starttime").val();
        	var endtime =  $("#endtime").val();
        	var timeDomain = $("#timeDomain").val();
        	var paixu = $("#paixu").val();
        	var otherAttribute = $("#otherAttribute").val();
        	var page =	$("#page").val() || 1;
        	
            if ($('#showFlag').val() == 1) {
            	$.ajax({
            		type:  "post",
            		url: actionBase + '/goSPAResultList.action?kw.keywordId=' + $('#keywordId').val(),
            	 	data: {clickTimeDomain:timeDomain,clickPaixu:paixu,starttime:starttime,endtime:endtime,clickOtherAttribute:otherAttribute,clickNewlstSelect:-1,clickComblineflg:-1,clickZhaiyaoShow:-1,page:page,mcFilterOrigina:0,directSet:0,clickSolrType:3,solrSecondType:3,secondSearchWord:'',clickDirectSet:'',isRecommended:0,clickMcFilterOrigina:''},
            		cache:false,
            		success:function(data, textStatus){
            			if(textStatus == 'success') {
            				if(data&&$.trim(data)!="")
            					 $("#findlistDiv").html(data);
            			}
            		}
            	});
            } else if ($('#showFlag').val() == 2) {
            	$.ajax({
                    type:  "post",
                    url: actionBase + "/searchSPAResult.action",
                    data: {checkedIds:'', selectedId:'', searchKeyword:$('#keyword').val(), filterKeyword:'', searchPipeiType:2, pinglunShow:0, zhaiyaoShow:1, duplicateShow:0, paixu:paixu, starttime:starttime, endtime:endtime, clickFilterOrigina:-1, filterOrigina:0, clickPaixu:paixu, clickOtherAttribute:otherAttribute, fontSizeType:1, clickFontSizeType:-1, newlstSelect:2, clickNewlstSelect:-1, comblineflg:1, clickComblineflg:-1, clickZhaiyaoShow:-1, timeDomain:timeDomain, clickTimeDomain:timeDomain, secondSearchWord:'', flag:'', page:page, hotNews:0, search:1, toolbarSwitch:0, selectOtherTime:0, 'ctk.categoryId':'', searchType:0, isRecommended:0},
                    cache:false,
                    success:function(data, textStatus){
                        if(textStatus == 'success') {
                            if(data&&$.trim(data)!="")
                                $("#findlistDiv").html(data);
                        }
                    }
                });
            }
        }

        function chartloading(id){
            var parent = $("#"+id).parent();
            $("#"+id).remove();
            parent.html('<div id="'+id+'" style="width: 100%; height:400px;" class="chart_shuiyin"></div>');
            $('#' + id).html('<img src=\"' + staticResourcePath + '/images/loading_c.gif\" width="15">');
        }

        function chartloadingTab(id){
            $('#' + id).html('<img src=\"' + staticResourcePath + '/images/loading_c.gif\" width="15">');
        }

        function GetRandom(n){
        	GetRandomn=Math.floor(Math.random()*n+1);
        	return GetRandomn;
        }

        //热度概况AND热度趋势
        function goHotTableAndLineChart(keywords,userId,date,title,filterKeyword){
            chartloadingTab("hotTable");
            chartloading("container1");
            EChartsDwr.getHotTableAndLine(keywords,userId,date,title,filterKeyword,null,null,GetRandom(30),hotTableAndLineCallback);
        }

        function hotTableAndLineCallback(data){

            if (data[0] == null || data[0] == "") {
                $("#container0").html("<div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>");
                //return false;
            } else {
                var html="";
                var name=$("#title").val();
                var dataTable=data[0];
                for(var i=0;i<dataTable.length;i++){
                	for (var i = 0; i < dataTable.length; i++) {
                        var obj = eval('(' + dataTable[i] + ')');
                        if (obj != null || obj != "") {
//                             name = names.split(",")[i];
//                             $("#reDuName").html(name);
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
                $("#hotTable").html(html);
            }
            var c1 = document.getElementById("container1");
            if (!data[1] || data[1].length == 0) {
                c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px;margin-top: 70px;\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                return false;
            } else {
                var dataLine = eval(data[1]);
                if (dataLine[0].data == null || dataLine[0].data.length == 0) {
                    c1.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                    return;
                } else {
                    var data1 = dataLine[0];
                    var alertMsg = "从热度指数的变化趋势来看，";
                    for (var i = 0; i < data1.data.length; i++) {
                        var listData = data1.data;
                        var subMsg = listData[i].name;
                        var topTime = listData[i]["label"].name;
                        topTime = topTime.split(":")[0];
                        topTime=topTime.replace("-","月");
                        topTime=topTime.replace(" ","日 ");
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
                        alertMsg += subMsg + '</font>的热度在' + topTime + '时达到了<font class="f_c2">' + topValue + '</font>的峰值。';
                    }
                    alertMsg+='';
//                     var areaSty= {
//                         normal: {
//                             color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
//                                 offset: 0,
//                                 color: 'rgba(241, 141, 1, 0.5)'
//                             }, {
//                                 offset: 1,
//                                 color: 'rgba(241, 141, 1, 0)'
//                             }], false),
//                             shadowColor: 'rgba(0, 0, 0, 0.1)',
//                             shadowBlur: 10
//                         }
//                     };
//                     var agent = navigator.userAgent.toLowerCase() ;
//         			if(agent.indexOf("edge")<0){
// //                         dataLine[0].data[0]["areaStyle"]=areaSty;
//         			}
//                     jquery('#container1').loader('hide',loaders);
                    $("#container1_msg").html(alertMsg);
                    var _chartColumn1 = LineChartHeatNetwork(dataLine,"container1");
                }
            }
        }
        }
        function LineChartHeatNetwork(data,dom){
        	$("#container1").empty();
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
                            show : true,
                            orient:'vertical',
                            y:5,
                            x:'right',
                            feature : {
                                restore : {show: true}
                                /*saveAsImage : {
                                 show: true,
                                 name:data[0].title
                                 }*/
                            }
                        },
                        grid:{
                            x:40,
                            y:40,
                            x2:40,
                            y2:40
                        },
                        color:['#FF7F50','#588FE5','#A4D720'],
                        legend: {
                            data: data[0].legend
                        },
                        calculable: false,
                        xAxis:[{
                            type : 'category',//category|time
                            boundaryGap: false ,
                            splitLine:{show:false},
                            data : data[0].datetime,
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
                                    /* color:['#F7F7F7'] */
                                }
                            },
                            splitLine:{show: false}
                        }],
                        series:data[0].data
                    };
                    chart8.setOption(option);
                    chart8.setTheme('macarons');
                    var enConfig = require('echarts/config');
                }
            );
     }

           

        //关键词云AND关联词
        function goHotWordRelatedChart(keywords,userId,date,title,filterKeyword){
            chartloading("container2");
            chartloading("container6");
            EChartsDwr.getHotWordRelatedChart(keywords,userId,date,title,filterKeyword,null,null,GetRandom(30),hotWordRelatedChartCallBack);
        }

        function hotWordRelatedChartCallBack(data){
            var c2 = document.getElementById("container2");
            var c3 = document.getElementById("container6");
            var mapTop10 = document.getElementById("mapTop10");
            if (data[0].length==0){
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }else{
                var name=$("#title").val();
                var allHtml = "";
                var subMsg = "";
                for (var i = 0; i < data[0].length; i++) {
                    if(name.length>10){
                        name=name.substring(0,10)+"...";
                    }
                    subMsg += "在与";
                    if (i == 0) {
                        subMsg += "<font style='color:#FF7F50'>";
                    }
                    if(data[0][i]!=""&&data[0][i]!=null) {
                        var data1 = eval(data[0][i]);
                        subMsg += name+"</font>相关的全部信息中， 被提及频次最高的词语分别为<font color='#C0504D'>" +data1[0].name + "</font>、<font color='#C0504D'>" + data1[1].name + "</font>和<font color='#C0504D'>" + data1[2].name + "</font>。";
                    }else{
                        subMsg += name +"</font>的报道暂无数据。";
                    }
                }
                $("#container2_msg").html(subMsg);
                    if(data[0][0]==null||data[0][0]==""){
                        var c = document.getElementById("container2");
                        c.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                        return;
                    }
                    var _chartColumn2 = cloudChart(eval(data[0][0]), "container2" );
            }

            if (data[1].length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                return;
            }else{
                var dataRelate=data[1];
                var obj = eval("("+data[1][dataRelate.length-1]+")");
                var mapHtml = "";
                if(obj==null||obj==""||data[1][0]==null||data[1][0]==""){
                    c3.innerHTML = "<br> <div style=\"padding-top:50px;margin-top: 80px;\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                    return;
                }else {
                    chordChart(obj, "container6");
                    var relHtml = "";
                    var nameRel = $("#title").val();
                    mapHtml += "<div class='row-fluid'>";
                    for(var j=0;j<dataRelate.length-1;j++) {
                        if(nameRel.length>10){
                            nameRel=nameRel.substring(0,10)+"...";
                        }
                        relHtml += "通过对与";
                        relHtml += "<font style=\"color:#FF7F50\">";
                        relHtml+=nameRel+"</font>相关的信息进行分析后可看出，与其核心词";
                        var obj1 = eval('(' + dataRelate[j] + ')');
                        mapHtml += "<div class='span12'>";
                        mapHtml += " <table border='' cellspacing='' cellpadding='' class='map_table map_table-red'>";
                        mapHtml +="<tr><th colspan='3' class='kw_th'>"+nameRel+"</th></tr>";
                        mapHtml += "<th width='33%'>核心词</th>";
                        mapHtml += "<th width=''>关联词</th>";
                        mapHtml += "<th width=''>关联度</th>";
                        mapHtml += "</tr>";
                        for (var i = 0; i < obj1.length; i++) {
                            mapHtml += "<tr>";
                            if(i==0){
                                mapHtml += "<td rowspan='11' style='border-right: 1px dashed #E8E8E8;border-left: 1px dashed #E8E8E8;'>" + obj1[i].relatedName + "</td>";
                                relHtml+="<font color='#C0504D'>"+obj1[i].relatedName+"</font>关联度最高的词语为";
                            }else{
                                if (i > 0 && i < 4) {
                                    relHtml += "<font color=\"#C0504D\">" + obj1[i].relatedName+"</font>("+obj1[i].relatedPer+")";
                                    if (i < 2) {
                                        relHtml += "、";
                                    } else if (i > 0 && i < 3) {
                                        relHtml += "和";
                                    } else {
                                        relHtml += "。";
                                    }
                                }
                                mapHtml += "<td style='border-right: 1px dashed #E8E8E8;'>" + obj1[i].relatedName + "</td>";
                                var per="";
                                if(obj1[i].relatedPer<0.01){
                                    per="< 0.01%";
                                }else{
                                    per=obj1[i].relatedPer;
                                }
                                mapHtml += "<td>" + per + "</td>";
                                mapHtml += "</tr>";
                            }

                        }
                        mapHtml += "</table>";
                        mapHtml += "</div>";

                    }
                    mapHtml+="</div>";
                    $("#mapTop10").html(mapHtml);
                    $("#container6_msg").html(relHtml);

                }
            }
        }

        function cloudChart(data,dom){
            var config = require(
                [
                    'echarts',
                    'echarts/chart/wordCloud'
                ],
                function (ec) {
                    var chart2 = ec.init(document.getElementById(dom));
                    var option = {
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
                                restore : {show: true}
                            }
                        },
                        series: [{
                            type: 'wordCloud',
                            size: ['100%', '100%'],
                            textRotation : [0,0],//, 45, 90, -45
                            textPadding: 2,
                            center:['50%','50%'],
                            autoSize: {
                                enable: true
                                /*minSize: 12*/
                            },
                            data:data
                        }]
                    };
                    chart2.setOption(option);
                    var enConfig = require('echarts/config');
                }
            );
        }

        function chordChart(data,dom){
            var config = require(
                [
                    'echarts',
                    'echarts/chart/chord',
                    'echarts/chart/force',
                ],
                function (ec) {
                    var chart3 = ec.init(document.getElementById(dom));
                    chart3.showLoading( {
                        text : "正在努力加载数据中,请耐心等待。。。",
                        textStyle : {
                            fontSize : 20
                        }
                    });
                    var option = {
                        tooltip : {
                            trigger: 'item',
                            formatter: function (params) {
                                if (params.indicator2) {    // is edge
                                    return params.indicator2 + ' ' + params.name + ' ' + params.indicator;
                                } else {    // is node
                                    return params.name
                                }
                            }
                        },
                        toolbox: {
                            show : true,
                            orient:'vertical',
                            x:'left',
                            y:150,
                            feature : {
                                restore : {show: true},
                                magicType: {show: true, type: ['force', 'chord']}
                            }
                        },
                        color:['#FF7F50','#588FE5','#A4D720'],
                        legend: {
                            x: 'left',
                            data: data.legend
                        },
                        series : [
                            {
                                type:'chord',
                                sort : 'ascending',
                                sortSub : 'descending',
                                ribbonType: false,
                                radius: '60%',
                                itemStyle : {
                                    normal : {
                                        label : {
                                            rotate : true,
                                            show: true,
                                            position: 'bottom',
                                            textStyle:{
                                                fontSize:14,
                                                color:"#454545"
                                            }
                                        }
                                    }
                                },
                                minRadius: 7,
                                maxRadius: 20,
                                // 使用 nodes links 表达和弦图
                                nodes: data.nodes,
                                links: data.links
                            }
                        ]
                    };
                    chart3.hideLoading();
                    chart3.setOption(option);
                    var enConfig = require('echarts/config');

                }
            );
        }

        //来源类型
        function goOrigin(keywords,userId,date,title,filterKeyword){
            chartloading("container3");
            EChartsDwr.getHotOTChart(keywords,userId,date,title,filterKeyword,null,null,GetRandom(30),OriginCallBack);
        }

        function OriginCallBack(data){
            var c3 = document.getElementById("container3");
            if (data.length==0){
                c3.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                return;
            }else{
                $("#container3_msg").html(data[1]);
                var names=$("#title2").val();
                var res = eval(data[0]);
                MTChart(res[0],"container3",1);
                /*for (var j = 0; j <data.length-1; j++) {
                    if(data[j]==null||data[j]==""){
                        var c = document.getElementById("container3" );
                        c.innerHTML = "<br> <div align=\"center\" style=\"padding-top:50px\"><p style=\"display:inline;font-size: 14px\"><img src=\"<%=staticResourcePath %>/images/shouye/warn.png\" style=\"width:60px\"><br/>此时间段暂无信息</p></div>";
                        continue;
                    }
                    QGPieChart1(eval(data[j]),"container3");
                }*/
            }
        }


        //圆环图
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
                                    name:data[0].title,
                                    type:'jpeg',
                                    lang : ['点击保存']
                                }
                            }
                        },
                        calculable : false,
                        legend:{
                            show:true,
                            x:'center',
                            data:data[0].legend.reverse()
                        },
                        series : [
                            {
                                name:data.title,
                                type:'pie',
                                radius : ['35%', '60%'],
                                startAngle:0,
                                data:data[0].data
                            }
                        ]
                    }
                    chart4.setOption(option);
                    var enConfig = require('echarts/config');
                }
            );

        }

        function MTChart(data,dom,flag){

            if(flag==1){
                var config = require(
                    [
                        'echarts',
                        'echarts/chart/bar',
                    ],
                    function (ec) {
                        var chart3 = ec.init(document.getElementById(dom));
                        var option = {
                            /*tooltip : {
                             trigger: 'axis'
                             },*/
                            tooltip: {
                                trigger: 'item',
                                formatter:function(params){
                                    return params.seriesName+"<br/>"+params.name;

                                }
                            },
                            color:['#FF7F50','#588FE5','#A4D720'],
                            legend: {
                                data:data.legend
                            },
                            toolbox: {
                                show : true,
                                orient:'vertical',
                                y:30,
                                feature : {
                                    restore : {show: true}
                                    /*saveAsImage : {
                                     show: true,
                                     name:data.title
                                     }*/
                                }
                            },
                            calculable : false,
                            xAxis : [
                                {
                                    type : 'value',
                                    name:'单位/条',
                                    boundaryGap : [0, 0.01],
                                    splitLine:{show:false}
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'category',
                                    data :data.datetime,
                                    splitLine:{show:false},
                                    axisLabel: {
                                        formatter: function (v) {
                                            return v.split(":")[0];
                                        }
                                    }
                                }
                            ],
                            series :data.data.reverse()
                        };
                        chart3.setOption(option);
                        var enConfig = require('echarts/config');

                    }
                );
            }else{
                var config = require(
                    [
                        'echarts',
                        'echarts/chart/bar',
                    ],
                    function (ec) {
                        var chart3 = ec.init(document.getElementById(dom));
                        var option = {
                            tooltip : {
                                trigger: 'item'
                                //trigger: 'axis'
                            },
                            color:['#FF7F50','#588FE5','#A4D720'],
                            legend: {
                                data:data.legend
                            },
                            toolbox: {
                                show : true,
                                orient:'vertical',
                                y:30,
                                feature : {
                                    restore : {show: true}
                                    /*saveAsImage : {
                                     show: true,
                                     name:data.title
                                     }*/
                                }
                            },
                            calculable : false,
                            xAxis : [
                                {
                                    type : 'value',
                                    name:'单位/条',
                                    boundaryGap : [0, 0.01],
                                    splitLine:{show:false}
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'category',
                                    data :data.datetime,
                                    splitLine:{show:false}
                                }
                            ],
                            series :data.data.reverse()
                        };
                        chart3.setOption(option);
                        var enConfig = require('echarts/config');

                    }
                );
            }

        }

        //地域分布
        function goMapChart(keywords,userId,date,title,filterKeyword){


            chartloading("container4");
            chartloading("container5");
            EChartsDwr.getHotAreaChart(keywords,userId,date,title,filterKeyword,null,null,GetRandom(30),AreaCallBack);
        }

        function AreaCallBack(data){
            var c2 = document.getElementById("container4");
            if (data.length==0){
                c2.innerHTML = "<br> <div align=\"center\" style=\"padding-top:130px\"><p style=\"display:inline;font-size: 14px\"><img src=\""+staticResourcePath+"/images/shouye/warn.png\"><br/>此时间段暂无信息</p></div>";
                return;
            }else{
                var res = eval(data[0]);
                var res1 = eval(data[1]);
                var _chartColumn4 = HeatMapChart(res[0],eval("("+data[2]+")"),"container4");
                var _chartColumn5 = MTChart(res1[0],"container5",2);
                $("#container4_msg").html(data[3]);
            }
        }

        function HeatMapChart(data,hideLegend,dom){
            var min = 0;
            var max = 0;
            $.each(data.data, function(i, n) {
                $.each(n.data,function(j,m){
                    delete m.itemStyle;
                    if (m.value > max)
                        max = m.value;
                    if (m.value < min)
                        min = m.value;
                });
            });
            var config = require(
                [
                    'echarts',
                    'echarts/chart/map',
                ],
                function (ec) {
                    var chart2 = ec.init(document.getElementById(dom));
                    chart2.showLoading( {
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
                            left: 'left',
                            data:data.legend,
                            selectedMode:'single',
                            selected:hideLegend
                        },
                        toolbox: {
                            show : true,
                            orient:'vertical',
                            y:30,
                            feature : {
                                restore : {show: true}
                                /*saveAsImage : {
                                 show: true,
                                 name:data.title
                                 }*/
                            }
                        },
                        dataRange: {
                            min: min,
                            max: max,
                            calculable : true,
                            text:['高','低'],
                            color: ['#d44e24','#f29300','#f3d647']
                        },
                        series : data.data
                    };
                    chart2.hideLoading();
                    chart2.setOption(option);
                    var enConfig = require('echarts/config');
                }
            );
        }

        function SetRQ(date) {
            $("#date").val(date);
            var MyDate = new Date();
            var seperator2 = ":";
            var year = MyDate.getFullYear();
            var month = MyDate.getMonth() + 1;
            var strDate = MyDate.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }

            var mc=MyDate.getMinutes();
            var minutesCur =mc> 9 ? mc.toString() : "0" + mc.toString();
            var sc=MyDate.getSeconds();
            var secondsCur=sc> 9 ? sc.toString() : "0" + sc.toString();

            var currentdate = year + "-" + month + "-" + strDate
                + " " + MyDate.getHours() + seperator2 + /*MyDate.getMinutes()*/minutesCur
                + seperator2 + secondsCur;

            if(date==24) {
                //获得昨天的日期
                var yesterday_miliseconds = MyDate.getTime() - 1000 * 60 * 60 * 24;
                var Yesterday = new Date();
                Yesterday.setTime(yesterday_miliseconds);

                var yesterday_year = Yesterday.getFullYear().toString();
                var month_temp = Yesterday.getMonth() + 1;
                var yesterday_month = month_temp > 9 ? month_temp.toString() : "0" + month_temp.toString();
                var d = Yesterday.getDate();
                var Day = d > 9 ? d.toString() : "0" + d.toString();
                var my=Yesterday.getMinutes();
                var minutesYes=my>9?my.toString():"0"+my.toString();
                var sy=Yesterday.getSeconds();
                var secondsYes=sy>9?sy.toString():"0"+sy.toString();

                var time = yesterday_year + "-" + yesterday_month + "-" + Day
                    + " " + Yesterday.getHours() + seperator2 + /*Yesterday.getMinutes()*/minutesYes + seperator2 +/* Yesterday.getSeconds()*/secondsYes;

                $("#time").text("数据时间：" + time + "至" + currentdate+"     全网");
            }else if(date==72){
                //获得三天的日期
                var three_miliseconds = MyDate.getTime() - 1000 * 3 *60 * 60 * 24;
                var threeDay = new Date();
                threeDay.setTime(three_miliseconds);

                var three_year = threeDay.getFullYear().toString();
                var month_temps = threeDay.getMonth() + 1;
                var three_month = month_temps > 9 ? month_temps.toString() : "0" + month_temps.toString();
                var d = threeDay.getDate();
                var Day = d > 9 ? d.toString() : "0" + d.toString();
                var mt=threeDay.getMinutes()
                var minutesThree=mt>9? mt.toString():"0"+ mt.toString();
                var st=threeDay.getSeconds();
                var secondsThree=st>9?st.toString():"0"+st.toString();

                var time3 = three_year + "-" + three_month + "-" + Day
                    + " " + threeDay.getHours() + seperator2 + /*threeDay.getMinutes()*/minutesThree + seperator2 + /*threeDay.getSeconds()*/secondsThree;

                $("#time").text("数据时间：" +  time3 + "至" + currentdate+"   全网");
            }
        }

        function heatReport(){
            var keywords=$("#heatKeyword").val();
            var userId=$("#userId").val();
            var title=$("#title").val();
            var filterKeyword=$("#heatFilterKeyword").val();
            var date=$("#date").val();

            if(date==24){
                if($("#option2").parent("label").hasClass("active")){
                    $("#option2").parent("label").removeClass("active");
                }
                if($("#selected72").hasClass("selected")){
                    $("#selected72").removeClass("selected")
                }
                $("#option1").parent("label").addClass("active");
                $("#selected24").addClass("selected");
            }else if(date==72){
                if($("#option1").parent("label").hasClass("active")){
                    $("#option1").parent("label").removeClass("active");
                }
                if($("#selected24").hasClass("selected")){
                    $("#selected24").removeClass("selected")
                }
                $("#option2").parent("label").addClass("active");
                $("#selected72").addClass("selected");
            }

            SetRQ(date);
            goHotTableAndLineChart(keywords,userId,date,title,filterKeyword);//热度概况、趋势
            goHotWordRelatedChart(keywords,userId,date,title,filterKeyword);//关键词云、关联词
            goOrigin(keywords,userId,date,title,filterKeyword);//来源类型
            goMapChart(keywords,userId,date,title,filterKeyword);//地域分布
        }

        function queryTime(date){
            $("#time").text("");
            $("#date").val(date);
            SetRQ(date);
            heatReport();
        }
