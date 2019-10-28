//加入收藏的飞入js效果
$(function(){
	var offset = $("#end").offset(); 
	var userId = $("#userId").val();
	var silderRightDiv = $("#izl_rmenu");
	var tipSucai = $("#tipSucai");
	var tipCollect = $("#tipCollect");
	var countSucaiObj = $(silderRightDiv).find("a").eq(0).find("div").find("span");  //获取右侧素材 对象
	var countCollectObj = $(silderRightDiv).find("div[class='btn btn-scj rel']").find("span");  //获取右侧收藏 对象
	//当点击的是加入素材（单条）时
	 $(".addSucai").one("click",function(event){ 
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
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(2,userId);
                $(countSucaiObj).html(parseInt($(countSucaiObj).html())+1);
                var bottomSucaiCount =  $("#bottomSucaiCount");
                $(bottomSucaiCount).html(parseInt($(bottomSucaiCount).html())+1);
                $(this).empty(); //移除dom 
                //this.destory();
            } 
        }); 
    }); 
	
	
	
	//当点击的是加入收藏时
    $(".addCollect").one("click",function(event){ 
        var flyer = $('<span class="u-flyer"></span>'); 
        flyer.fly({ 
             start: { 
                left: event.pageX, //开始位置（必填）#fly元素会被设置成position: fixed 
                top: event.pageY //开始位置（必填） 
            }, 
            end: { 
                left: offset.left+50, //结束位置（必填） 
                top: offset.top+160 //结束位置（必填） 
            }, 
            onEnd: function(){ //结束回调 
                $(tipCollect).show().animate({width: '200px'}, 200).fadeOut(500); //提示信息 
                $(this).css("cursor","default").removeClass('orange').unbind('click'); 
                importNewsSingle(1,userId);
                $(countCollectObj).html(parseInt($(countCollectObj).html())+1);
                $(this).empty(); //移除dom 
                //this.destory();
            } 
        }); 
    });  
    
    //批量加入素材（单层的只有收藏夹中有该功能）
    
  //当点击的是加入素材（多条）时
	 $("#addSucais").one("click",function(event){ 
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
               addJianBaoSucais();
               $(this).empty(); //移除dom 
               //this.destory();
           } 
       }); 
   }); 
    
    
    
    
});