//加载统计数字
	function loadData(userId,kwId,keyword){
		StatDwr.getStatList(userId, kwId, keyword,1,'','',0,GetRandom(30),'1',3,7,statCallback);
	}

	function statCallback(result) {
		$("#statList").html("");
		if(result!=null){
			result= result.replace('all[', '<li onclick="setOriginTypeCommon(0)" id="li_0"  class="g_25 click"><p>');
			result = result.replace(']all,','</p><p class="g_text">文章数</p></li>');
			result= result.replace('wb[', '<li  onclick="setOriginTypeCommon(1)" id="li_1"  class="g_25"><p>');
			result = result.replace(']wb,','</p><p class="g_text">微博</p></li>');
			result= result.replace('wz[', '<li  onclick="setOriginTypeCommon(2)" id="li_2"  class="g_25"><p>');
			result = result.replace(']wz,','</p><p class="g_text">网站</p></li>');
			result= result.replace('lt[', '<li  onclick="setOriginTypeCommon(3)" id="li_3"  class="g_25"><p>');
			result = result.replace(']lt,','</p><p class="g_text">论坛</p></li>');
		    $("#statList").html(result);
		    moveClickColor();
		}
	}
	
	//初始化拖拽图片的数据
	
	 function initDragParamBaidu(){
	    	hljs.initHighlightingOnLoad();
	    	'use strict';
            var ease = document.querySelector('.js_ease');
            lory(ease, {
                infinite: 1,
                slidesToScroll: 1,
                slideSpeed: 300,
                ease: 'cubic-bezier(0.455, 0.03, 0.515, 0.955)'
            });
          
	    }
	 function initDragParamWeibo(){
		 hljs.initHighlightingOnLoad();
	    	'use strict';
    	  var ease2 = document.querySelector('.js_ease2');
            lory(ease2, {
                infinite: 1,
                slidesToScroll: 1,
                slideSpeed: 300,
                ease2: 'cubic-bezier(0.455, 0.03, 0.515, 0.955)'
            });
	 }     
	
	
	
	
	//加载百度热门事件
	function loadBaiduHot(kwId,keyword){
		StatDwr.loadHotEvent(kwId, keyword,1,GetRandom(30),4,statLoadBaiduCallback);
	}

	function statLoadBaiduCallback(result){
		$("#baiduTuijian").html('');
		if(result!=null){
			var ulHtml = '';
			for(var i=0;i<result.length;i++){
				var liHtml = "<li class='js_slide'><a class='check' href=\"javascript:void(0)\" onclick=\"javascript:goSearch('"+result[i]["event"]+"')\"> ";
				var hplist = result[i]["hpList"];
				if(hplist!=null){
					if(hplist.length>3) hplist.length = 3;
					for(var j=0;j<hplist.length;j++){
						var imgHtml ="";
						if(hplist[j]["url"]!=null&&hplist[j]["url"]!=''){
							imgHtml = "<img src="+hplist[j]["url"]+" class='img2'> ";
						}
						liHtml = liHtml+imgHtml;
					}
				}
				var time = '';
				if(result[i]["createTime"]!=null){
					time = result[i]["createTime"].format("yyyy-MM-dd hh:mm:ss"); 
				}
				liHtml +="<div class='g_12'><span class='tit'>"+result[i]["event"]+"</span><span class='float_r g_text daybetween'>"+time+"</span></div></a></li>";
				ulHtml +=liHtml;
			}
			$("#baiduTuijian").html(ulHtml);
			$("#baiduPrev").css("display","block");
			$("#baiduNext").css("display","block");
			dayBetween();			//时间格式化
			initDragParamBaidu();	//推荐图片移动
		}
		
	}



	//加载新浪热门事件
	function loadWeiboHot(kwId,keyword){
		StatDwr.loadHotEvent(kwId, keyword,2,GetRandom(30),4,statLoadWeiboCallback);
	}


	function statLoadWeiboCallback(result){
		$("#weiboTuijian").html('');
		if(result!=null){
			var ulHtml = '';
			for(var i=0;i<result.length;i++){
				var liHtml = "<li class='js_slide'><a class='check' href=\"javascript:void(0)\" onclick=\"javascript:goSearch('"+result[i]["event"]+"')\">";
				var hplist = result[i]["hpList"];
				if(hplist!=null&&hplist.length>0){
						var imgHtml = "<div class='g_2 weibo_Photo'><img src="+hplist[0]["url"]+" class='img'></div> ";
						liHtml = liHtml+imgHtml;
				}
				var time = '';
				if(result[i]["createTime"]!=null){
					time = result[i]["createTime"].format("yyyy-MM-dd hh:mm:ss"); 
				}
				liHtml +="<div class='g_8' style='margin-left: 10px;'><p class='htTit'>"+result[i]["event"]+"</p><p class='htCon'>"+result[i]["brief"]+"</p>"
					   +"<div class='g_12 g_text'><span>相同文章："+result[i]["num"]+"</span><span class='float_r daybetween'>"+time+"</span></div></div></a></li>";
				ulHtml +=liHtml;
			}
			$("#weiboTuijian").html(ulHtml);
			$("#weiboPrev").css("display","block");
			$("#weiboNext").css("display","block");
			dayBetween();			//时间格式化
			initDragParamWeibo();	//推荐的微博图片移动
		}
	}

	//混合型推荐（监测前推荐）
	function loadHotList(){
		StatDwr.loadHotEvent(0, '',0,GetRandom(30),4,statLoadHotCallback);
	}

	function statLoadHotCallback(result){
		if(result!=null){
			var ulHtml = '';
			for(var i=0;i<result.length;i++){
				var time = '';
				if(result[i]["published"]!=null){
					time = result[i]["published"].format("yyyy-MM-dd hh:mm:ss"); 
				}
				var liHtml = "<article class='context'><a class='check' href=\"javascript:void(0)\" onclick=\"javascript:goSearch('"+result[i]["event"]+"')\"> "
					+"<ul><li class='g_12'><div class='con'><p class='mb10'><span class='tit'><strong> "+result[i]["event"]
					+"</strong><i class='tit_mark'>相同"+result[i]["num"]+"</i></span></p><p class='g_text'><span class='daybetween'>"+time+"</span></p></div></li></ul></a></article>";
				ulHtml +=liHtml;
			}
			$("#tuijian").append(ulHtml);
			dayBetween();			//时间格式化
		}
	}
	
	
	
	 //监测后的推荐
    function loadKwHotList(userId,timeDomain,keywordId){
    	StatDwr.getRelateList(userId,timeDomain,keywordId,1,"","",GetRandom(30),kwHotCallBackList);
    }
    
    function kwHotCallBackList(result){
    	$("#tujianList").remove();
    	if(null!=result && result.length>0){
    		if(result!=null){
    			var ulHtml = '';
    			for(var i=0;i<result.length;i++){
    				var time = '';
    				if(result[i]["published"]!=null){
    					time = result[i]["published"].format("yyyy-MM-dd hh:mm:ss"); 
    				}
    				var liHtml = "<article class='context'><a class='check' href='"+result[i]["url"]+"'> "
    					+"<ul><li class='g_12'><div class='con'><p class='mb10'><span class='tit'><strong> "+result[i]["title"]
    					+"</strong><i class='tit_mark'>相同"+result[i]["num"]+"</i></span></p><p class='g_text'><span class='daybetween'>"+time+"</span></p></div></li></ul></a></article>";
    				ulHtml +=liHtml;
    			}
    			$("#tuijian").append(ulHtml);
    			dayBetween();			//时间格式化
    		}
   		 }else{
   			$("#tuijian").append("<div style='width:100%;height:60px;text-align:center;line-height:60px;'>推荐榜上空空哒～</div>");
   		 }
    }
	
	function keywordMenu() {
		if ($('#keywordId').val() == null || $('#keywordId').val() == '')
			return false;
		var modifyNum = $("#modifyNum").val();
		if(modifyNum > 49){
			 $("#modifyPOP").fadeIn(300);
	         $(".prompPOP").addClass('scaleShow'); 
	         $(".prompPOP").removeClass('scaleOut'); 
	         $(".zhezhao").fadeIn(300);
	         setTimeout(function(){
	         	$(".zhezhao").fadeOut(300);
	         	$("#modifyPOP").fadeOut(300);
	         	$(".prompPOP").addClass('scaleOut'); 
	         	$(".prompPOP").removeClass('scaleShow'); 
	         },2000);
	         return false;
		}else{
			$("#modifyPOP").fadeIn(300);
	         $(".prompPOP").addClass('scaleShow'); 
	         $(".prompPOP").removeClass('scaleOut'); 
	         $(".zhezhao").fadeIn(300);
	         setTimeout(function(){
	         	$(".zhezhao").fadeOut(300);
	         	$("#modifyPOP").fadeOut(300);
	         	$(".prompPOP").addClass('scaleOut'); 
	         	$(".prompPOP").removeClass('scaleShow'); 
	         	  location.href = njxBasePath + '/view/usercenter/keywordMenu.action?keywordId=' + $('#keywordId').val();
	         },2000);
	       
		}
		
		
	}
	
	
	
	function moveClickColor(){
		$(".g_25").each(function(){
			$(this).mouseover(function(){
				$(this).addClass('click');
				$(this).siblings('li').removeClass('click');
			});
		});
	}