angular.module('ngApp', []).controller('initController',function($scope,$http,httpSerive){
	// ģ�����ӡ������
	bigpipe.ready('doEventTrend',function(data){
		var opt = $.extend(true,{},echartsOpts["line"]);
		if(data.status){
	       var line = data.obj;
	       opt.series[0].name = $("#keyword").val();
	       for(var i = 0;i<line.length;i++){
	    	   opt.series[0].data[i] = {name:line[i].name,value:line[i].value};
	    	   opt.xAxis[0].data[i] = line[i].name;
        	}
	        setEchartsOpion({$id:$("#eventTrend"),opt:opt});
	    }else{
	    	$("#eventTrend").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	// ģ�����ӡ�����
	bigpipe.ready('doWeiBoWordCloud',function(data){
		var opt = $.extend(true,{},echartsOpts["wordCloud"]);
		if(data.status){
	       var wordCloud = data.obj,
	       colors = ["#72c1be","#f29300","#a05623","#277bc0"];
	       for(var i = 0;i<wordCloud.length;i++){
	    	   opt.series[0].data[i] = {name:wordCloud[i].name,value:wordCloud[i].value, itemStyle:{normal:{color:colors[i%4]}}};
        	}
	        setEchartsOpion({$id:$(".WordCloudTable"),opt:opt});
	    }else{
	    	$(".WordCloudTable").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	// ģ�����ӡ�����
	bigpipe.ready('dotoMeWeibo',function(data){
		var opt = $.extend(true,{},echartsOpts["bar1"]);
		if(data.status){
        	//����
	       var bar = data.obj,
	       colors = ["#e6b322","#c5c669","#f0be9e","#a2d8ed","#37b48a","#f6b768"];
	       
	       for(var i = 0;i<bar.length;i++){
	    	   opt.series[0].data[i] = {name:bar[i].name,value:bar[i].value, itemStyle:{normal:{color:colors[i%6]}}};
	    	   opt.yAxis[0].data[i] = bar[i].name.replace(/<[^>]+>/g,"");
        	}
	        var height = bar.length*45>150?bar.length*45:150;
	        $("#fweiboBar").height(height);
	        setEchartsOpion({$id:$("#fweiboBar"),opt:opt});
	    }else{
	    	 $("#fweiboBar").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	// ģ�����ӡ�����
	bigpipe.ready('dotoMeWeibo1',function(data){
		var opt = $.extend(true,{},echartsOpts["bar1"]);
		if(data.status){
        	//����
	       var bar = data.obj,
	       colors = ["#e6b322","#c5c669","#f0be9e","#a2d8ed","#37b48a","#f6b768"];
	       for(var i = 0;i<bar.length;i++){
	    	   opt.series[0].data[i] = {name:bar[i].name,value:bar[i].value, itemStyle:{normal:{color:colors[i%6]}}};
	    	   opt.yAxis[0].data[i] = bar[i].name.replace(/<[^>]+>/g,"");
        	}
	        var height = bar.length*45>150?bar.length*45:150;
	        $("#fweiboBar1").height(height);
	        console.log(JSON.stringify(opt));
	        setEchartsOpion({$id:$("#fweiboBar1"),opt:opt});
	    }else{
	    	 $("#fweiboBar1").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	// ģ�����ӡ����Դ
//	bigpipe.ready('doSoucreCount',function(data){
//		var opt = $.extend(true,{},echartsOpts["pie1"]);
//		if(data.status){
//	       var pie1 = data.obj,
//	       sum = 0;
//	       for(var i = 0;i<pie1.length;i++){
//	    	   sum += pie1[i].num;
//	       }
//	       console.log(sum);
//	       for(var i = 0;i<pie1.length;i++){
//	    	   var seriesclone = $.extend(true,{},opt.series[0]);
//	    	   opt.series[i] = seriesclone;
//	    	   console.log(pie1[i].num+","+pie1[i].percent+","+Math.round(pie1[i].num/100*pie1[i].percent));
//	    	   var num = Math.round(pie1[i].num/100*pie1[i].percent);
//	    	   opt.series[i].data[0] = {name:"",value:sum-pie1[i].num, itemStyle:labelBottom()};
//	    	   opt.series[i].data[1] = {name:pie1[i].name,value:pie1[i].num, itemStyle:labelTop()};
//	    	   var center = [20,40];
//	    	   console.log(i%3);
//	    	   if(i%3==0){
//	    		   opt.series[i].x = "0%";
//	    		   center[0] = "20%";
//	    	   }else if(i%3==1){
//	    		   opt.series[i].x = "30%";
//	    		   center[0] = "50%";
//	    	   }else{
//	    		   opt.series[i].x = "90%";
//	    		   center[0] = "80%";
//	    	   }
//	    	   center[1] = (parseInt(i/3)+1)*center[1]+"%";
//	    	   opt.series[i].center = center;
//        	}
//	       	//var height = pie1.length*50>150?pie1.length*50:150;
//	        //$("#soucrePie").height(height);
//			 setEchartsOpion({$id:$("#soucrePie"),opt:opt});
//	     }else{
//	    	 $("#soucrePie").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
//	     }
//	});
	// ģ�����ӡ����Դ
	bigpipe.ready('doSoucreCount',function(data){
		var opt = $.extend(true,{},echartsOpts["pie"]);
		if(data.status){
	       var pie = data.obj
	       for(var i = 0;i<pie.length;i++){
	    	   opt.series[0].data[i] = {name:pie[i].name,value:pie[i].value};
	    	}
//	        var center = [20,40];
//	    	opt.series[i].center = center;
			setEchartsOpion({$id:$("#soucrePie"),opt:opt});
	     }else{
	    	 $("#soucrePie").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	     }
	});
	// ����ֲ�
	bigpipe.ready('doProvinceAtMe',function(result){
		$scope.$apply(function(){
			$scope.areaList= result.obj;
		});
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["map"]);
			
			for(var i = 0;i<result.obj.length;i++){
				opt.series[0].data[i] = {value:result.obj[i].value,name:result.obj[i].name};
			}
			//$("#container6").height(400);
			setEchartsOpion({$id:$("#container6"),opt:opt});
	    }else{
	    	$("#container6").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	
	});
	// ����·��
	bigpipe.ready('doPropagationPath',function(result){
		if(result.status){
			var opt = $.extend(true,{},echartsOpts["tree"]);
			var treeformat = treeData(result.obj,{});
			console.log(treeformat);
			opt.series[0].data[0] = treeformat;
			if(treeformat.children.length>0){
				treeformat.children[0].name = treeformat.children[0].name+"(�׷�)";
			}
			setEchartsOpion({$id:$("#container8"),opt:opt});
	    }else{
	    	$("#container8").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	// ģ�������Ҫ��Ϣtop10
	bigpipe.ready('doTop',function(data){
		if(data.status){
	        $scope.$apply(function(){
	        	console.log(data.obj);
	        	$scope.importantInfoLists = data.obj;
			});
	    }else{
	    	$(".main_list").html("<br> <div align='center' style='padding-top:50px'><p style='display:inline;font-size: 14px'><img src='"+njxBasePath+"/images/warn.png' style='width:60px'><br/>��ʱ���������Ϣ</p></div>");
	    }
	});
	
	//�ݹ�
	function treeData(obj,treeformat){
		var tree = obj,
		treeformat = {name:tree.stat.name,children:[]};
		if(tree.statListList){
			for(var i =0;i<tree.statListList.length;i++){
				treeformat.children[i] = treeData(tree.statListList[i],treeformat);
			}
		}
		return treeformat;
	}
})
//����ajax������� 
.config(function ($httpProvider) {
    $httpProvider.defaults.transformRequest = function(data){
        if (data === undefined) {
            return data;
        }
        return $.param(data);
    }
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
})
//����html��ǩ��
.filter('trustAsHtml',function($sce){
	return function(html){
		return $sce.trustAsHtml(html);
	}
})
//����html��ǩ��
.filter('removeHtml',function(){
	return function(html){
		var reg = new RegExp("<a[^>]*>(.*?)</a>");
		if(!reg.exec(html)){
			return "΢�� weibo.com";
		}
		return reg.exec(html)[1];
	}
})
//��ǿ����
.service('httpSerive',function($http,$q){
	this.query = function(param) {
		if(!param){
			throw new Error("param is empty");
		}
        var deferred=$q.defer();
        var promise=deferred.promise;
        $http({
        	url:param.url,
        	method:param.method,
        	data:param.data
        }).success(function(data,status,headers,config){
            deferred.resolve(data);//ִ�гɹ�
        }).error(function(data,status,headers,config){
            deferred.reject();//ִ��ʧ��
        })
        return promise;
    }
});

//angularjs������
angular.bootstrap(document.documentElement,["ngApp"]);
