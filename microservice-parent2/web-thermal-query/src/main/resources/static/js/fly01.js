//加入收藏的飞入js效果
$(function(){
	var offset = $("#end").offset(); 
	var userId = $("#userId",parent.window.document).val();
	var silderRightDiv = $("#izl_rmenu",parent.window.document);
	var tipSucai = $("#tipSucai",parent.window.document);
	var tipCollect = $("#tipCollect",parent.window.document);
	var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find("span");  //获取右侧素材 对象
	var countCollectObj = $(silderRightDiv).find("a").eq(2).find("div").find("span");  //获取右侧收藏 对象
	var countCartObj = $(silderRightDiv).find("a").eq(2).find("div").find("span");  //获取右侧购物车对象
	var offset1 = $(silderRightDiv).find("a").eq(0).offset();
	//当点击的是加入素材（单条）时
	 $(".addSucai").one("click",function(event){ 
		var scrollTopValue = $(parent.window).scrollTop();
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
                top: event.pageY //开始位置（必填） 
            }, 
            end: { 
            	 left: offset.left+50, //结束位置（必填） 
                 top: parseInt(offset1.top)+parseInt(scrollTopValue)-280 //结束位置（必填） 
            }, 
            onEnd: function(){ //结束回调 
            	if(scrollTopValue>200){
            		$(tipSucai).css({top:"260px"});
            	}
                $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingleSenior(2,'0',userId);
            /*    $(countSucaiObj).html(parseInt($(countSucaiObj).html())+1);
                var bottomSucaiCount =  $("#bottomSucaiCount",parent.window.document);
                $(bottomSucaiCount).html(parseInt($(bottomSucaiCount).html())+1);*/
                $(this).empty(); //移除dom 
                //this.destory();
            } 
        }); 
    }); 
	
	
	
	//当点击的是加入收藏时
    $(".addCollect").one("click",function(event){ 
    	var scrollTopValue = $(parent.window).scrollTop();
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
                top: event.pageY //开始位置（必填） 
            }, 
            end: { 
            	 left: offset.left+50, //结束位置（必填） 
                 top: parseInt(offset1.top)+parseInt(scrollTopValue)-230 //结束位置（必填） 
            }, 
            onEnd: function(){ //结束回调 
            	if(scrollTopValue>200){
            		$(tipCollect).css({top:"310px"});
            	}
                $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(1,'0',userId);
              // $(countCollectObj).html(parseInt($(countCollectObj).html())+1);
                $(this).empty(); //移除dom 
                //this.destory();
            } 
        }); 
    }); 
    
    
    
    //当点击的是加入素材（多条）时
	 $("#addSucais").click(function(event){ 
	  var scrollTopValue = $(parent.window).scrollTop();
      var flyer = $('<span class="u-flyer"></span>'); 
      flyer.fly({ 
           start: { 
              left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
              top: event.pageY //开始位置（必填） 
          }, 
          end: { 
        	  left: offset.left+50, //结束位置（必填） 
              top: parseInt(offset1.top)+parseInt(scrollTopValue)-280 //结束位置（必填） 
          }, 
          onEnd: function(){ //结束回调 
        	  if(scrollTopValue>200){
          		$(tipSucai).css({top:"260px"});
          	  }
              $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
              //addCart.css("cursor","default").removeClass('orange').unbind('click'); 
              importNewsSenior(2,'0',userId);
              $(this).empty(); //移除dom 
              //this.destory();
          } 
      }); 
  }); 
    
	 
	 
	   
	    //当点击的是加入收藏（多条）时
		 $("#addCollects").click(function(event){ 
		  var scrollTopValue = $(parent.window).scrollTop();
	      var flyer = $('<span class="u-flyer"></span>'); 
	      flyer.fly({ 
	           start: { 
	              left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
	              top: event.pageY //开始位置（必填） 
	          }, 
	          end: { 
	        	  left: offset.left+50, //结束位置（必填） 
	              top: parseInt(offset1.top)+parseInt(scrollTopValue)-230 //结束位置（必填） 
	          }, 
	          onEnd: function(){ //结束回调 
	        	  if(scrollTopValue>200){
	            		$(tipCollect).css({top:"310px"});
	              }
	              $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
	              importNewsSenior(1,'0',userId);
	              $(this).empty(); //移除dom 
	          } 
	      }); 
	  }); 
	  
	   //当点击的是加入收藏夹（多条）时
	 $("#addColles").click(function(event){ 
       var flyer = $('<span class="u-flyer"></span>'); 
       flyer.fly({ 
            start: { 
               left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
               top: event.pageY //开始位置（必填） 
           }, 
           end: { 
               left: offset.left+50, //结束位置（必填） 
               top: offset.top+100 //结束位置（必填） 
           }, 
           onEnd: function(){ //结束回调 
               $(tipSucai).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
               addCollects();
               $(this).empty(); //移除dom 
               //this.destory();
           } 
       }); 
   }); 
	    
    
    
});